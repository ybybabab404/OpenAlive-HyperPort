package org.aliveclean;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
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

    static boolean injectIntoAod() {
        ViewGroup root = aodRoot == null ? null : aodRoot.get();
        if (root == null) return false;
        attach(root);
        return root.findViewWithTag(TAG) != null;
    }

    private static void attach(ViewGroup root) {
        Context context = root.getContext();
        aodRoot = new java.lang.ref.WeakReference<>(root);
        if (!ownsWallpaper(context)) return;
        if (root.findViewWithTag(TAG) != null) return;
        AodSurface surface = new AodSurface(context);
        surface.setTag(TAG);
        surface.setAlpha(0.85f);
        root.addView(surface, 0, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Log.i(TAG, "ambient surface attached");
        root.postDelayed(new Runnable() {
            @Override public void run() { dumpTree("after-attach"); }
        }, 1500);
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
        if (view instanceof SurfaceView) {
            line.append(" surfaceValid=").append(((SurfaceView) view).getHolder().getSurface().isValid());
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

    private static final class AodSurface extends SurfaceView implements SurfaceHolder.Callback {
        private RenderLoop loop;

        AodSurface(Context context) {
            super(context);
            getHolder().addCallback(this);
            getHolder().setFormat(PixelFormat.TRANSLUCENT);
        }

        @Override public void surfaceCreated(SurfaceHolder holder) {
            loop = new RenderLoop(getContext());
            loop.mode(0);
            loop.attach(holder.getSurface(), getWidth(), getHeight());
            Log.i(TAG, "surface created " + getWidth() + "x" + getHeight());
        }

        @Override public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (loop != null) loop.attach(holder.getSurface(), width, height);
        }

        @Override public void surfaceDestroyed(SurfaceHolder holder) {
            if (loop != null) { loop.close(); loop = null; }
        }

        @Override protected void onDetachedFromWindow() {
            if (loop != null) { loop.close(); loop = null; }
            super.onDetachedFromWindow();
        }
    }

    private HyperOsAod() {}
}
