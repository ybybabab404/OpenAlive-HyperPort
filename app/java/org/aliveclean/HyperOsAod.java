package org.aliveclean;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.SurfaceTexture;
import android.os.SystemClock;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

final class HyperOsAod {
    private static final String TAG = "AliveHSAod";
    private static java.lang.ref.WeakReference<AodSurface> liveSurface;
    private static java.lang.ref.WeakReference<ViewGroup> scheduledRoot;
    private static long attachGeneration;
    private static long scheduledAt;
    private static final long ATTACH_DELAY_MS = 950;
    private static final long FADE_MS = 180;
    private static final float BALL_CENTER_Y = 0.165f;
    private static final float BALL_RADIUS = 0.22f;
    private static final String[] VIEWS = {
            "com.android.keyguard.widget.AodView",
            "com.miui.aod.AODView"
    };

    static void install(ClassLoader loader) {
        boolean installed = false;
        for (String name : VIEWS) {
            Class<?> view = Xp.findClassIfExists(name, loader);
            if (view == null || !ViewGroup.class.isAssignableFrom(view)) continue;
            Xp.hookAllConstructors(view, new Xp.MethodHook() {
                @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                    scheduleAttach((ViewGroup) param.thisObject);
                }
            });
            Trace.log("T01", "hooks installed for " + name);
            installed = true;
        }
        if (!installed) AliveLog.i(TAG, "no AOD view class in this process");
    }

    static void installDoze(ClassLoader loader) {
        Class<?> service = Xp.findClassIfExists("com.android.keyguard.doze.MiuiDozeService", loader);
        if (service == null) { AliveLog.i(TAG, "no MiuiDozeService"); return; }
        Xp.hookAllMethods(service, "onDreamingStarted", new Xp.MethodHook() {
            @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                final android.app.Service svc = (android.app.Service) param.thisObject;
                Trace.log("T07", "dreaming started");

                AodOverlay.show(svc);
            }
        });
        Xp.hookAllMethods(service, "onDreamingStopped", new Xp.MethodHook() {
            @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                Trace.log("T08", "dreaming stopped");
                AodOverlay.hide();
            }
        });
        AliveLog.i(TAG, "doze service hook installed");
    }

    private static java.lang.ref.WeakReference<ViewGroup> aodRoot;

    static boolean hasInjectedView() {
        ViewGroup root = aodRoot == null ? null : aodRoot.get();
        return root != null && root.findViewWithTag(TAG) != null;
    }

    static boolean injectIntoAod() {
        ViewGroup root = aodRoot == null ? null : aodRoot.get();
        if (root == null) return false;
        AodSurface existing = liveSurface == null ? null : liveSurface.get();
        if (root.findViewWithTag(TAG) == null || existing == null
                || !existing.isAttachedToWindow() || !existing.isShown()) {
            scheduleAttach(root);
        }
        return true;
    }

    private static void scheduleAttach(final ViewGroup root) {
        if (root == null) return;
        long now = SystemClock.uptimeMillis();
        ViewGroup pending = scheduledRoot == null ? null : scheduledRoot.get();
        if (pending == root && now - scheduledAt < ATTACH_DELAY_MS) return;
        scheduledRoot = new java.lang.ref.WeakReference<>(root);
        scheduledAt = now;
        final long generation = ++attachGeneration;
        root.postDelayed(new Runnable() {
            @Override public void run() {
                if (generation != attachGeneration) return;
                ViewGroup current = scheduledRoot == null ? null : scheduledRoot.get();
                if (current != root) return;
                scheduledRoot = null;
                try {
                    attach(root);
                } catch (Throwable error) {
                    AliveLog.e(TAG, "attach failed", error);
                }
            }
        }, ATTACH_DELAY_MS);
    }

    /**
     * Removes the scene surface that was injected into MIUI's own AODView.  This is
     * separate from AodOverlay's WindowManager view: when the AOD is switched off (or
     * the system wallpaper is no longer ours) the injected child otherwise stays in the
     * AOD hierarchy and keeps drawing the default ball.
     */
    static void detach() {
        attachGeneration++;
        scheduledRoot = null;
        aodRoot = null;
        AodSurface surface = liveSurface == null ? null : liveSurface.get();
        liveSurface = null;
        if (surface == null) return;
        try {
            if (surface.getParent() instanceof ViewGroup) {
                ((ViewGroup) surface.getParent()).removeView(surface);
            }
        } catch (Throwable error) {
            AliveLog.w(TAG, "scene detach failed", error);
        }
    }

    private static void attach(ViewGroup root) {

        final long generation = attachGeneration;
        Context context = root.getContext();
        aodRoot = new java.lang.ref.WeakReference<>(root);
        if (!aodEnabled(context)) {
            detach();
            AliveLog.once(TAG, "aod-disabled", "AOD scene skipped: AOD is disabled");
            return;
        }
        if (!aodSceneEnabled(context)) {
            detach();
            return;
        }
        if (!AodWallpaperMode.alive(context)) {
            detach();
            AliveLog.once(TAG, "aod-wallpaper-foreign", "AOD scene skipped: wallpaper is not ours");
            return;
        }
        AliveLog.reset("aod-wallpaper-foreign");

        AodSurface existing = liveSurface == null ? null : liveSurface.get();
        if (existing != null && existing.isAttachedToWindow() && existing.isShown()) {
            Trace.log("T03", "attach skipped, live surface id=" + System.identityHashCode(existing));
            return;
        }
        if (existing != null) {
            Trace.log("T03", "attach replacing hidden surface id=" + System.identityHashCode(existing));
            if (existing.getParent() instanceof ViewGroup) {
                ((ViewGroup) existing.getParent()).removeView(existing);
            }
            liveSurface = null;
        }
        View stale = root.findViewWithTag(TAG);
        if (stale != null) {
            if (stale.getParent() instanceof ViewGroup) {
                ((ViewGroup) stale.getParent()).removeView(stale);
            }
            if (stale == existing) liveSurface = null;
        }

        ViewGroup target = findInnerAod(root);
        if (target == null) {

            AliveLog.once(TAG, "aod-view-pending", "inner AODView not up yet, waiting");
            root.postDelayed(new Runnable() {
                @Override public void run() {
                    // detach()/a newer attach may have replaced the root during
                    // these 200ms.  Never inject into a stale AOD hierarchy.
                    if (generation != attachGeneration) return;
                    ViewGroup current = aodRoot == null ? null : aodRoot.get();
                    if (current != root) return;
                    injectIntoAod();
                }
            }, 200);
            return;
        }
        AodSurface surface = new AodSurface(context);
        surface.setTag(TAG);
        surface.setAlpha(0f);
        // R.layout.aod_mode_layout has an opaque black View at index 0.  Sit
        // directly above it, but below the clock/content container, otherwise
        // the ball is painted behind the AOD background.
        target.addView(surface, Math.min(1, target.getChildCount()), new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        liveSurface = new java.lang.ref.WeakReference<>(surface);
        AliveLog.reset("aod-view-pending");
        Trace.log("T04", "surface#create id=" + System.identityHashCode(surface)
                + " host=" + System.identityHashCode(target) + " children=" + target.getChildCount());

    }

    private static void supervise(final ViewGroup root, final AodSurface surface) {
        root.postDelayed(new Runnable() {
            int tries;
            int misses;

            @Override public void run() {

                if (surface.getParent() == null) {
                    if (++misses < 8) { next(); return; }
                    ViewGroup host = findInnerAod(root);
                    ViewGroup target = host != null ? host : root;
                    target.addView(surface, Math.min(1, target.getChildCount()),
                            new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT));
                    misses = 0;
                    AliveLog.i(TAG, "scene re-attached after misses");
                } else {
                    misses = 0;
                    ViewGroup inner = findInnerAod(root);
                    if (inner != null && surface.getParent() != inner) {
                        ((ViewGroup) surface.getParent()).removeView(surface);
                        inner.addView(surface, Math.min(1, inner.getChildCount()),
                                new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT));
                        AliveLog.i(TAG, "scene moved into inner AODView");
                    }
                }
                next();
            }

            private void next() {
                if (++tries < 100) root.postDelayed(this, 300);
                else AliveLog.i(TAG, "supervise done, parent="
                        + (surface.getParent() == null ? "null"
                        : surface.getParent().getClass().getName()));
            }
        }, 300);
    }

    private static ViewGroup findInnerAod(ViewGroup root) {
        String own = root.getClass().getName();
        if ("com.miui.aod.AODView".equals(own) || "AODView".equals(root.getClass().getSimpleName())) {
            return root;
        }
        for (int i = 0; i < root.getChildCount(); i++) {
            View child = root.getChildAt(i);
            if (!(child instanceof ViewGroup)) continue;
            String name = child.getClass().getName();
            if ("com.miui.aod.AODView".equals(name)
                    || "AODView".equals(child.getClass().getSimpleName())) {
                return (ViewGroup) child;
            }
        }
        return null;
    }

    private static Context moduleContext(Context base) {
        try {
            return base.createPackageContext("org.aliveclean", 0);
        } catch (Throwable unavailable) {
            AliveLog.w(TAG, "module context unavailable, using the host context", unavailable);
            return base;
        }
    }

    static boolean aodEnabled(Context base) {
        try {
            return android.provider.Settings.Secure.getInt(
                    base.getContentResolver(), "doze_always_on", 0) == 1;
        } catch (Throwable unavailable) {
            AliveLog.w(TAG, "cannot read AOD switch; treating as off", unavailable);
            return false;
        }
    }

    private static boolean aodSceneEnabled(Context base) {
        try {
            Context app = moduleContext(base);
            android.content.SharedPreferences prefs =
                    app.getSharedPreferences(SceneOptions.APPLIED, 0);

            return prefs.getInt("aod", 0) != -1;
        } catch (Throwable unavailable) {
            AliveLog.w(TAG, "cannot read scene settings", unavailable);
            return false;
        }
    }

    private static final class AodSurface extends TextureView implements TextureView.SurfaceTextureListener {
        private final Context appContext;
        private RenderLoop loop;
        private Surface surface;

        AodSurface(Context context) {
            super(context);
            appContext = moduleContext(context);
            setSurfaceTextureListener(this);
            setOpaque(true);
            setOutlineProvider(new ViewOutlineProvider() {
                @Override public void getOutline(View view, Outline outline) {
                    int width = view.getWidth();
                    int height = view.getHeight();
                    float radius = BALL_RADIUS * width;
                    int cx = width / 2;
                    int cy = (int) (BALL_CENTER_Y * height);
                    outline.setOval((int) (cx - radius), (int) (cy - radius),
                            (int) (cx + radius), (int) (cy + radius));
                }
            });
            setClipToOutline(true);
        }

        @Override public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
            surface = new Surface(texture);
            loop = new RenderLoop(appContext);
            loop.onFirstFrame(new Runnable() {
                @Override public void run() {
                    post(new Runnable() {
                        @Override public void run() {
                            animate().alpha(1f).setDuration(FADE_MS).start();
                        }
                    });
                }
            });
            loop.mode(0);
            loop.attach(surface, width, height);
            Trace.log("T05", "texture#available id=" + System.identityHashCode(this) + " " + width + "x" + height);
        }

        @Override public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {
            if (loop != null && surface != null) loop.attach(surface, width, height);
        }

        @Override public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
            if (loop != null) { loop.close(); loop = null; }
            if (surface != null) { surface.release(); surface = null; }
            if (liveSurface != null && liveSurface.get() == this) liveSurface = null;
            return true;
        }

        @Override public void onSurfaceTextureUpdated(SurfaceTexture texture) {}

        @Override protected void onDetachedFromWindow() {
            if (loop != null) { loop.close(); loop = null; }
            if (surface != null) { surface.release(); surface = null; }
            if (liveSurface != null && liveSurface.get() == this) liveSurface = null;
            super.onDetachedFromWindow();
        }
    }

    private HyperOsAod() {}
}
