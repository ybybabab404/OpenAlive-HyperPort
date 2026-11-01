package org.aliveclean;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
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

    private static void attach(ViewGroup root) {
        Context context = root.getContext();
        if (!ownsWallpaper(context)) return;
        if (root.findViewWithTag(TAG) != null) return;
        AodSurface surface = new AodSurface(context);
        surface.setTag(TAG);
        surface.setAlpha(0.85f);
        root.addView(surface, 0, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Log.i(TAG, "ambient surface attached");
    }

    private static boolean ownsWallpaper(Context context) {
        try {
            WallpaperInfo info = WallpaperManager.getInstance(context).getWallpaperInfo();
            return info != null && "org.aliveclean".equals(info.getPackageName());
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
