package org.aliveclean;

import android.app.Service;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * 兜底渲染窗口：当系统 AODView 注入这条路走不通时，用 WindowManager 上的 SurfaceView
 * 自己画息屏动画。由 {@link HyperOsAod} 决定何时启用（正常路径不用它）。
 */
final class AodOverlay {
    private static final String TAG = "AliveHSAod";
    private static SurfaceView view;
    private static RenderLoop loop;
    private static int showToken;
    private static final long ATTACH_DELAY_MS = 950;

    static void show(Service service) {
        Trace.log("T10", "overlay#show enter");
        if (!HyperOsAod.aodEnabled(service)) {
            AliveLog.i(TAG, "overlay skipped: AOD is disabled");
            hide();
            return;
        }
        if (!ownsWallpaper(service)) {
            AliveLog.i(TAG, "overlay skipped: wallpaper is not ours");
            hide();
            return;
        }
        if (view != null) { Trace.log("T10", "overlay#show already"); return; }
        if (HyperOsAod.injectIntoAod()) {
            AliveLog.i(TAG, "injected into the AOD view");
            // 注入成功说明 MIUI 的 AOD 视图已经在跑；此时若存储值是「和锁屏样式一致」而 MIUI 内部
            // 还停在自定义，就让它重算一次（有 1.5 秒防抖，不会每轮都打扰）。
            AodFullScreen.ensureFullAodApplied(service);
            return;
        }
        final int token = ++showToken;
        new android.os.Handler(service.getMainLooper()).postDelayed(new Runnable() {
            @Override public void run() {
                if (token != showToken || view != null) return;
                add(service);
            }
        }, ATTACH_DELAY_MS);
    }

    private static void add(Service service) {
        try {
            view = new AodView(service);
            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,
                    (2038  ),
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                            | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                            | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                    PixelFormat.TRANSLUCENT);
            params.setTitle("AliveHSAod");
            service.getSystemService(WindowManager.class).addView(view, params);
            AliveLog.i(TAG, "overlay added");
        } catch (Throwable error) {
            AliveLog.e(TAG, "overlay add failed", error);
            view = null;
        }
    }

    static void hide() {
        Trace.log("T11", "overlay#hide");
        showToken++;
        HyperOsAod.detach();
        if (view == null) {
            if (loop != null) { loop.close(); loop = null; }
            return;
        }
        try { ((WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE)).removeView(view); }
        catch (Throwable error) { AliveLog.e(TAG, "overlay remove failed", error); }
        if (loop != null) { loop.close(); loop = null; }
        view = null;
    }

    private static boolean ownsWallpaper(Context context) {
        // The system (home) wallpaper is authoritative.  A live wallpaper left on
        // the lock slot must not keep the AOD overlay alive after the user switched
        // the home wallpaper back to MIUI.
        return AodWallpaperMode.alive(context);
    }

    private static final class AodView extends SurfaceView implements SurfaceHolder.Callback {
        AodView(Context context) {
            super(context);
            getHolder().addCallback(this);
            getHolder().setFormat(PixelFormat.TRANSLUCENT);
            setAlpha(0f);
        }
        @Override public void surfaceCreated(SurfaceHolder holder) {
            loop = new RenderLoop(getContext());
            final RenderLoop current = loop;
            loop.onFirstFrame(new Runnable() {
                @Override public void run() {
                    post(new Runnable() {
                        @Override public void run() {
                            if (loop == current) animate().alpha(0.9f).setDuration(180).start();
                        }
                    });
                }
            });
            loop.mode(0);
            loop.attach(holder.getSurface(), getWidth(), getHeight());
            AliveLog.i(TAG, "overlay surface " + getWidth() + "x" + getHeight());
        }
        @Override public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (loop != null) loop.attach(holder.getSurface(), width, height);
        }
        @Override public void surfaceDestroyed(SurfaceHolder holder) {
            if (loop != null) { loop.close(); loop = null; }
        }
    }

    private AodOverlay() { }
}
