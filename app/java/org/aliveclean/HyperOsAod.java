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
                android.os.Handler handler = new android.os.Handler(android.os.Looper.getMainLooper());
                // SharedPreferences does not notify across processes, so the scene
                // chosen in the app is only visible if we rebuild the host here.
                handler.postDelayed(new Runnable() {
                    @Override public void run() { refreshScene(); }
                }, 900);
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

    private static java.lang.ref.WeakReference<ViewGroup> aodRoot;

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
        Context context = root.getContext();
        aodRoot = new java.lang.ref.WeakReference<>(root);
        if (!aodSceneEnabled(context)) return;
        if (root.findViewWithTag(TAG) != null) return;
        // The inner com.miui.aod.AODView paints the AOD wallpaper full screen, so a
        // surface added to the outer AodView sits *under* that wallpaper. Inject into
        // the inner container instead: wallpaper below, stock clock above.
        ViewGroup target = findInnerAod(root);
        if (target == null) {
            target = root;
            Log.i(TAG, "inner AODView not up yet, falling back to outer host");
            root.postDelayed(new Runnable() {
                @Override public void run() { injectIntoAod(); }
            }, 400);
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

            @Override public void run() {
                if (surface.getParent() == null) {
                    ViewGroup host = findInnerAod(root);
                    (host != null ? host : root).addView(surface, 0,
                            new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT));
                    Log.i(TAG, "scene re-attached after detach");
                }
                ViewGroup inner = findInnerAod(root);
                if (inner != null && surface.getParent() != inner) {
                    if (surface.getParent() instanceof ViewGroup) {
                        ((ViewGroup) surface.getParent()).removeView(surface);
                    }
                    inner.addView(surface, 0, new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    Log.i(TAG, "scene moved into inner AODView");
                }
                if (++tries < 20) root.postDelayed(this, 300);
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



    /** The always-on sphere takes its colours from the wallpaper the user applied, the
     * way the stock effect does. WallpaperManager hands SystemUI the palette directly,
     * which works for live wallpapers too (there is no bitmap to read). */
    private static void applyWallpaperPalette(RenderLoop loop, Context host) {
        try {
            WallpaperManager manager = WallpaperManager.getInstance(host);
            android.app.WallpaperColors colors = manager.getWallpaperColors(WallpaperManager.FLAG_SYSTEM);
            if (colors == null) colors = manager.getWallpaperColors(WallpaperManager.FLAG_LOCK);
            if (colors == null) { Log.i(TAG, "no wallpaper colours"); return; }
            int primary = colors.getPrimaryColor().toArgb();
            android.graphics.Color secondaryColor = colors.getSecondaryColor();
            android.graphics.Color tertiaryColor = colors.getTertiaryColor();
            int secondary = secondaryColor != null ? secondaryColor.toArgb() : primary;
            int tertiary = tertiaryColor != null ? tertiaryColor.toArgb() : primary;
            loop.aodPalette(primary, secondary, tertiary);
            Log.i(TAG, "wallpaper palette " + Integer.toHexString(primary) + "/" + Integer.toHexString(secondary));
        } catch (Throwable failure) {
            Log.w(TAG, "wallpaper palette unavailable", failure);
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

        @Override public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
            surface = new Surface(texture);
            loop = new RenderLoop(appContext);
            applyWallpaperPalette(loop, context);
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
