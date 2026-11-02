package org.aliveclean;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/** HyperOS AOD host. HyperOS 4 renders the always-on display inside SystemUI
 * (com.android.keyguard.widget.AodView); older builds used a standalone app.
 * The scene must therefore be rendered inside that process: this hook inserts a
 * SurfaceView at the bottom of the AOD view hierarchy and drives RenderLoop on
 * it, leaving the stock clock, notifications and gestures untouched above. */
final class HyperOsAod {
    private static final String TAG = "HyperAliveAod";
    private static final String[] VIEWS = {
            "com.android.keyguard.widget.AodView",
            "com.miui.aod.AODView"
    };

    static void install(ClassLoader loader) {
        boolean installed = false;
        for (String name : VIEWS) {
            Class<?> view = XposedHelpers.findClassIfExists(name, loader);
            if (view == null || !ViewGroup.class.isAssignableFrom(view)) continue;
            XposedBridge.hookAllConstructors(view, new XC_MethodHook() {
                @Override protected void afterHookedMethod(MethodHookParam param) {
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
            Log.i(TAG, "AOD view hook installed on " + name);
            installed = true;
        }
        if (!installed) Log.i(TAG, "no AOD view class in this process");
    }

    /** HyperOS 4 keeps the AOD inside the doze service; a window overlay is the
     * only place a third party scene can live without touching the stock clock. */
    static void installDoze(ClassLoader loader) {
        Class<?> service = XposedHelpers.findClassIfExists("com.android.keyguard.doze.MiuiDozeService", loader);
        if (service == null) { Log.i(TAG, "no MiuiDozeService"); return; }
        XposedBridge.hookAllMethods(service, "onDreamingStarted", new XC_MethodHook() {
            @Override protected void afterHookedMethod(MethodHookParam param) {
                final android.app.Service svc = (android.app.Service) param.thisObject;
                AodOverlay.show(svc);
                // The host is built fresh on every doze entry and RenderLoop reads the
                // applied scene in its constructor, so rebuilding it again here only
                // detached the surface (clock blinked, flow restarted). Removed.
                handler.postDelayed(new Runnable() {
                    @Override public void run() { dumpTree("dozing"); }
                }, 3000);
            }
        });
        XposedBridge.hookAllMethods(service, "onDreamingStopped", new XC_MethodHook() {
            @Override protected void afterHookedMethod(MethodHookParam param) { AodOverlay.hide(); }
        });
        Log.i(TAG, "doze service hook installed");
    }


    /** The AOD window is torn down the instant dozing stops, so an animation inside it
     * can never be seen. The last frame is copied into its own overlay window first and
     * that copy does the leaving: it expands slightly and fades, so the sphere opens
     * back into the wallpaper instead of vanishing. */
    static void playExit() {
        AodSurface surface = activeSurface == null ? null : activeSurface.get();
        if (surface == null) return;
        try {
            int width = Math.max(1, surface.getWidth()), height = Math.max(1, surface.getHeight());
            android.graphics.Bitmap frame = surface.getBitmap(width / 2, height / 2);
            if (frame == null) { surface.detachNow(); return; }
            Context context = surface.getContext();
            android.widget.ImageView view = new android.widget.ImageView(context);
            view.setImageBitmap(frame);
            view.setScaleType(android.widget.ImageView.ScaleType.CENTER_CROP);
            android.view.WindowManager windows =
                    (android.view.WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            android.view.WindowManager.LayoutParams params = new android.view.WindowManager.LayoutParams(
                    android.view.WindowManager.LayoutParams.MATCH_PARENT,
                    android.view.WindowManager.LayoutParams.MATCH_PARENT,
                    android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                            | android.view.WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                    android.graphics.PixelFormat.TRANSLUCENT);
            windows.addView(view, params);
            view.animate().alpha(0f).scaleX(1.06f).scaleY(1.06f).setDuration(320L)
                    .withEndAction(() -> {
                        try { windows.removeView(view); } catch (Throwable ignored) { }
                        frame.recycle();
                    }).start();
            surface.detachNow();
            Log.i(TAG, "exit overlay started");
        } catch (Throwable failure) {
            Log.w(TAG, "exit overlay failed", failure);
            surface.detachNow();
        }
    }

    private static java.lang.ref.WeakReference<AodSurface> activeSurface;
    private static java.lang.ref.WeakReference<ViewGroup> aodRoot;
    private static java.lang.ref.WeakReference<View> overlayView;
    private static android.view.WindowManager overlayWindows;

    /** Hosts the scene in our own window. MIUI keeps clearing the children of its
     * AODView, which detached our surface and restarted the animation; owning the
     * window removes that whole class of flicker. The window covers only the upper
     * part of the screen, so the stock clock below stays untouched. */
    static void showOverlay(Context context) {
        hideOverlay();
        try {
            android.view.WindowManager windows =
                    (android.view.WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            android.util.DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            AodSurface surface = new AodSurface(context);
            surface.setTag(TAG);
            android.widget.FrameLayout frame = new android.widget.FrameLayout(context);
            frame.setBackgroundColor(0x00000000);
            frame.addView(surface, new android.widget.FrameLayout.LayoutParams(
                    android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                    android.widget.FrameLayout.LayoutParams.MATCH_PARENT));
            android.view.WindowManager.LayoutParams params = new android.view.WindowManager.LayoutParams(
                    android.view.WindowManager.LayoutParams.MATCH_PARENT,
                    (int) (metrics.heightPixels * 0.46f),
                    android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                            | android.view.WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                            | android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                            | android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
                            | android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    android.graphics.PixelFormat.TRANSLUCENT);
            params.gravity = android.view.Gravity.TOP | android.view.Gravity.CENTER_HORIZONTAL;
            windows.addView(frame, params);
            overlayView = new java.lang.ref.WeakReference<>(frame);
            overlayWindows = windows;
            Log.i(TAG, "scene overlay added " + metrics.widthPixels + "x" + params.height);
        } catch (Throwable failure) {
            Log.w(TAG, "scene overlay failed", failure);
        }
    }

    static void hideOverlay() {
        View view = overlayView == null ? null : overlayView.get();
        if (view == null || overlayWindows == null) return;
        try { overlayWindows.removeView(view); } catch (Throwable ignored) { }
        overlayView = null;
        Log.i(TAG, "scene overlay removed");
    }

    /** Injects into the stock AOD window itself, which keeps the stock clock above. */
    static boolean hasInjectedView() {
        ViewGroup root = aodRoot == null ? null : aodRoot.get();
        return root != null && root.findViewWithTag(TAG) != null;
    }

    /** Rebuilds the scene host so a style picked in the app (written to another
     * process) is re-read on every doze entry. */
    static void refreshScene() {
        ViewGroup root = aodRoot == null ? null : aodRoot.get();
        if (root == null) return;
        View old = root.findViewWithTag(TAG);
        if (old != null && old.getParent() instanceof ViewGroup) {
            ((ViewGroup) old.getParent()).removeView(old);
            Log.i(TAG, "scene host dropped for refresh");
        }
        attach(root);
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
        Log.i(TAG, "ambient surface attached to " + target.getClass().getName()
                + " children=" + target.getChildCount());
        supervise(root, surface);
        root.postDelayed(new Runnable() {
            @Override public void run() { dumpTree("after-attach"); }
        }, 1500);
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

    /** Diagnostic: prints the real hierarchy the AOD lives in, so we can see
     * which sibling covers the scene surface and where the depth wallpaper
     * foreground sits. Each node gets its own log line to stay under the
     * logcat line limit. */
    static void dumpTree(String why) {
        ViewGroup root = aodRoot == null ? null : aodRoot.get();
        if (root == null) { Log.i(TAG, "tree[" + why + "]: no aod root"); return; }
        View top = root.getRootView();
        Log.i(TAG, "tree[" + why + "]: rootView=" + top.getClass().getName()
                + " aod=" + root.getClass().getName()
                + " aodParent=" + (root.getParent() instanceof View
                        ? ((View) root.getParent()).getClass().getName() : String.valueOf(root.getParent())));
        walk(top, 0);
    }

    private static void walk(View view, int depth) {
        if (depth > 18) return;
        StringBuilder line = new StringBuilder(160);
        for (int i = 0; i < depth; i++) line.append("  ");
        line.append(view.getClass().getSimpleName())
            .append(" vis=").append(visibilityName(view.getVisibility()))
            .append(" a=").append(view.getAlpha())
            .append(" ").append(view.getWidth()).append('x').append(view.getHeight())
            .append(" @").append(view.getLeft()).append(',').append(view.getTop());
        if (view.getElevation() != 0f) line.append(" elev=").append(view.getElevation());
        if (view.getTranslationZ() != 0f) line.append(" tz=").append(view.getTranslationZ());
        if (view instanceof TextureView) {
            line.append(" textureAvailable=").append(((TextureView) view).isAvailable());
        }
        if (view.getTag() != null) line.append(" tag=").append(view.getTag());
        String name = view.getClass().getName();
        if (name.contains("Wallpaper") || name.contains("Scrim") || name.contains("Pictorial")) {
            line.append("  <<< ").append(name);
        }
        Log.i(TAG, "tree[" + depth + "] " + line);
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) walk(group.getChildAt(i), depth + 1);
        }
    }

    private static String visibilityName(int visibility) {
        switch (visibility) {
            case View.VISIBLE: return "V";
            case View.INVISIBLE: return "I";
            case View.GONE: return "G";
            default: return "?";
        }
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

    private static boolean ownsWallpaper(Context context) {
        try {
            WallpaperManager manager = WallpaperManager.getInstance(context);
            WallpaperInfo home = manager.getWallpaperInfo();
            if (home != null && "org.aliveclean".equals(home.getPackageName())) return true;
            WallpaperInfo lock = manager.getWallpaperInfo(WallpaperManager.FLAG_LOCK);
            return lock != null && "org.aliveclean".equals(lock.getPackageName());
        } catch (Throwable unavailable) {
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



        void detachNow() {
            if (getParent() instanceof ViewGroup) ((ViewGroup) getParent()).removeView(this);
        }

        /** Plays the same mode transition the editor preview uses, then detaches. */
        private boolean exitWithTransitionUnused() {
            if (loop == null) return false;
            loop.mode(1, true);
            postDelayed(() -> {
                if (getParent() instanceof ViewGroup) ((ViewGroup) getParent()).removeView(this);
                Log.i(TAG, "exit transition done");
            }, 1100L);
            return true;
        }

        @Override public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
            surface = new Surface(texture);
            activeSurface = new java.lang.ref.WeakReference<>(this);
            loop = new RenderLoop(appContext);
            loop.mode(0);
            loop.attach(surface, width, height);
            Log.i(TAG, "texture available " + width + "x" + height);
        }

        @Override public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {
            if (loop != null && surface != null) loop.attach(surface, width, height);
        }

        @Override public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
            if (loop != null) { loop.close(); loop = null; }
            if (surface != null) { surface.release(); surface = null; }
            return true;
        }

        @Override public void onSurfaceTextureUpdated(SurfaceTexture texture) {}

        @Override protected void onDetachedFromWindow() {
            if (loop != null) { loop.close(); loop = null; }
            if (surface != null) { surface.release(); surface = null; }
            super.onDetachedFromWindow();
        }
    }

    private HyperOsAod() {}
}
