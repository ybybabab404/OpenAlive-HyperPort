package org.aliveclean;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/** HyperOS AOD host.
 *
 * HyperOS draws the always-on display inside com.miui.aod instead of composing
 * the wallpaper's ambient layer the way ColorOS does. The only way a third
 * party scene can stay alive behind the stock clock is to render it inside that
 * process, so this hook inserts a SurfaceView at the bottom of the AOD view
 * hierarchy and drives RenderLoop on it. The stock clock, notifications and
 * system AOD gestures stay untouched above it.
 */
final class HyperOsAod {
    private static final String TAG = "HyperAliveAod";

    static void install(ClassLoader loader) {
        try {
            Class<?> view = XposedHelpers.findClass("com.miui.aod.AODView", loader);
            XposedHelpers.findAndHookMethod(view, "onAttachedToWindow", new XC_MethodHook() {
                @Override protected void afterHookedMethod(MethodHookParam param) {
                    try {
                        attach((ViewGroup) param.thisObject);
                    } catch (Throwable error) {
                        XposedBridge.log(TAG + ": attach failed: " + error);
                    }
                }
            });
            XposedBridge.log(TAG + ": AOD view hook installed");
        } catch (Throwable missing) {
            XposedBridge.log(TAG + ": com.miui.aod.AODView unavailable: " + missing);
        }
    }

    private static void attach(ViewGroup root) {
        Context context = root.getContext();
        if (!ownsWallpaper(context)) return;
        if (root.findViewWithTag(TAG) != null) return;
        AodSurface surface = new AodSurface(context);
        surface.setTag(TAG);
        root.addView(surface, 0, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        XposedBridge.log(TAG + ": ambient surface attached");
    }

    private static boolean ownsWallpaper(Context context) {
        try {
            WallpaperManager manager = WallpaperManager.getInstance(context);
            WallpaperInfo info = manager.getWallpaperInfo();
            if (info == null) return false;
            return "org.aliveclean".equals(info.getPackageName());
        } catch (Throwable unavailable) {
            return false;
        }
    }

    /** One AOD session: a surface that owns its own RenderLoop. */
    private static final class AodSurface extends SurfaceView implements SurfaceHolder.Callback {
        private RenderLoop loop;

        AodSurface(Context context) {
            super(context);
            getHolder().addCallback(this);
            setZOrderMediaOverlay(false);
            getHolder().setFormat(PixelFormat.TRANSLUCENT);
        }

        @Override public void surfaceCreated(SurfaceHolder holder) {
            loop = new RenderLoop(getContext());
            loop.mode(0);
            loop.attach(holder.getSurface(), getWidth(), getHeight());
            XposedBridge.log(TAG + ": surface created " + getWidth() + "x" + getHeight());
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
