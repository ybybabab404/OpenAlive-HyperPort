package org.aliveclean;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

/** HyperOS AOD host. HyperOS 4 renders the always-on display inside SystemUI
 * (com.android.keyguard.widget.AodView); older builds used a standalone app.
 * The scene must therefore be rendered inside that process: this hook inserts a
 * SurfaceView at the bottom of the AOD view hierarchy and drives RenderLoop on
 * it, leaving the stock clock, notifications and gestures untouched above. */
final class HyperOsAod {
    private static final String TAG = "HyperAliveAod";
    private static java.lang.ref.WeakReference<AodSurface> liveSurface;
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
                    final ViewGroup root = (ViewGroup) param.thisObject;
                    root.post(new Runnable() {
                        @Override public void run() {
                            try {
                                attach(root);
                            } catch (Throwable error) {
                                Log.e(TAG, "attach failed", error);
                            }
                        }
                    });
                }
            });
            Trace.log("T01", "hooks installed for " + name);
            installed = true;
        }
        if (!installed) Log.i(TAG, "no AOD view class in this process");
    }

    /** HyperOS 4 keeps the AOD inside the doze service; a window overlay is the
     * only place a third party scene can live without touching the stock clock. */
    static void installDoze(ClassLoader loader) {
        Class<?> service = Xp.findClassIfExists("com.android.keyguard.doze.MiuiDozeService", loader);
        if (service == null) { Log.i(TAG, "no MiuiDozeService"); return; }
        Xp.hookAllMethods(service, "onDreamingStarted", new Xp.MethodHook() {
            @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                final android.app.Service svc = (android.app.Service) param.thisObject;
                Trace.log("T07", "dreaming started");
                // The injected TextureView is destroyed when dozing stops, so the next
                // entry already builds a fresh RenderLoop and re-reads the scene options.
                // A second rebuild here decoded the photo and recreated the whole EGL
                // stack in the middle of the doze transition, which dropped ~30 frames.
                AodOverlay.show(svc);
            }
        });
        Xp.hookAllMethods(service, "onDreamingStopped", new Xp.MethodHook() {
            @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                Trace.log("T08", "dreaming stopped");
                AodOverlay.hide();
            }
        });
        Log.i(TAG, "doze service hook installed");
    }


    private static java.lang.ref.WeakReference<ViewGroup> aodRoot;

    /** Injects into the stock AOD window itself, which keeps the stock clock above. */
    static boolean hasInjectedView() {
        ViewGroup root = aodRoot == null ? null : aodRoot.get();
        return root != null && root.findViewWithTag(TAG) != null;
    }

    static boolean injectIntoAod() {
        ViewGroup root = aodRoot == null ? null : aodRoot.get();
        if (root == null) return false;
        attach(root);
        return root.findViewWithTag(TAG) != null;
    }

    private static void attach(ViewGroup root) {
        // When our own window is up, injecting into MIUI's tree would draw a second
        // sphere and reintroduce the detach churn, so that path stays off.
        Context context = root.getContext();
        aodRoot = new java.lang.ref.WeakReference<>(root);
        if (!aodSceneEnabled(context)) return;
        // Exactly one scene surface per process: MIUI builds several AodView hosts while
        // entering doze (keyguard one, then the doze one). A second surface means two
        // balls, and whichever host is torn down makes the visible one restart.
        AodSurface existing = liveSurface == null ? null : liveSurface.get();
        if (existing != null && existing.isAttachedToWindow() && existing.isShown()) {
            Trace.log("T03", "attach skipped, live surface id=" + System.identityHashCode(existing));
            return;
        }
        if (existing != null) {
            // A transient keyguard host can disappear while the doze host is being built.
            // Never let a hidden, stale host block the new host from creating a surface.
            Trace.log("T03", "attach replacing hidden surface id=" + System.identityHashCode(existing));
            liveSurface = null;
        }

        if (root.findViewWithTag(TAG) != null) return;
        // The inner com.miui.aod.AODView paints the AOD wallpaper full screen, so a
        // surface added to the outer AodView sits *under* that wallpaper. Inject into
        // the inner container instead: wallpaper below, stock clock above.
        ViewGroup target = findInnerAod(root);
        if (target == null) {
            // The stock clock lives above the inner AODView. Attaching to the outer root
            // would cover it, and moving it later makes the whole screen blink, so wait
            // for the real container instead of attaching anywhere else.
            Log.i(TAG, "inner AODView not up yet, waiting");
            root.postDelayed(new Runnable() {
                @Override public void run() { injectIntoAod(); }
            }, 200);
            return;
        }
        AodSurface surface = new AodSurface(context);
        surface.setTag(TAG);
        surface.setAlpha(1f);
        target.addView(surface, 0, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        liveSurface = new java.lang.ref.WeakReference<>(surface);
        Trace.log("T04", "surface#create id=" + System.identityHashCode(surface)
                + " host=" + System.identityHashCode(target) + " children=" + target.getChildCount());
        // supervise() re-attached on every transient detach; each re-attach destroyed the
        // SurfaceTexture and restarted the flow. Attachment is driven by doze start only.
        // supervise(root, surface);
    }

    /** The inner com.miui.aod.AODView is inflated after the outer AodView, so the
     * first attach usually lands on the outer host and stays hidden behind the AOD
     * wallpaper. Watch for the inner container and move the scene into it. */
    private static void supervise(final ViewGroup root, final AodSurface surface) {
        root.postDelayed(new Runnable() {
            int tries;
            int misses;

            @Override public void run() {
                // MIUI rebuilds its AOD children while dreaming, so the surface is
                // briefly parentless on healthy frames. Re-adding on the first miss
                // rebuilt the SurfaceTexture and restarted the flow, which is what the
                // flicker was. Require three consecutive misses before touching it.
                if (surface.getParent() == null) {
                    if (++misses < 8) { next(); return; }
                    ViewGroup host = findInnerAod(root);
                    (host != null ? host : root).addView(surface, 0,
                            new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT));
                    misses = 0;
                    Log.i(TAG, "scene re-attached after misses");
                } else {
                    misses = 0;
                    ViewGroup inner = findInnerAod(root);
                    if (inner != null && surface.getParent() != inner) {
                        ((ViewGroup) surface.getParent()).removeView(surface);
                        inner.addView(surface, 0, new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
                        Log.i(TAG, "scene moved into inner AODView");
                    }
                }
                next();
            }

            private void next() {
                if (++tries < 100) root.postDelayed(this, 300);
                else Log.i(TAG, "supervise done, parent="
                        + (surface.getParent() == null ? "null"
                        : surface.getParent().getClass().getName()));
            }
        }, 300);
    }

    /** Finds the full-screen com.miui.aod.AODView that paints the AOD wallpaper. */
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

    /** The AOD lives in SystemUI, so its context resolves SystemUI's assets and data
     * directory. Everything the renderer reads (shaders, scene options) belongs to our
     * own package, so build a context for it instead of borrowing the host's. */
    private static Context moduleContext(Context base) {
        try {
            return base.createPackageContext("org.aliveclean", 0);
        } catch (Throwable unavailable) {
            Log.w(TAG, "module context unavailable, using the host context", unavailable);
            return base;
        }
    }

    /** The AOD scene belongs to this module and is independent of whichever live
     * wallpaper currently runs behind the lock screen, so key it off the scene
     * settings instead of the wallpaper component. */
    private static boolean aodSceneEnabled(Context base) {
        try {
            Context app = moduleContext(base);
            android.content.SharedPreferences prefs =
                    app.getSharedPreferences(SceneOptions.APPLIED, 0);
            // aod == -1 is the only value that means "leave the stock AOD alone";
            // 0 is a real scene, not "off".
            return prefs.getInt("aod", 0) != -1;
        } catch (Throwable unavailable) {
            Log.w(TAG, "cannot read scene settings", unavailable);
            return false;
        }
    }

    /** A TextureView, not a SurfaceView: the AOD window paints an opaque wallpaper
     * over its own buffer, so a SurfaceView's separate layer stays hidden below it.
     * A TextureView renders into the window buffer and therefore composites in
     * normal view order: above the wallpaper, below the stock clock. */
    private static final class AodSurface extends TextureView implements TextureView.SurfaceTextureListener {
        private final Context appContext;
        private RenderLoop loop;
        private Surface surface;

        AodSurface(Context context) {
            super(context);
            appContext = moduleContext(context);
            setSurfaceTextureListener(this);
            setOpaque(false);
        }



        @Override public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
            surface = new Surface(texture);
            loop = new RenderLoop(appContext);
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
