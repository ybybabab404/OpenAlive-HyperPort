package org.aliveclean;

import android.app.Service;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.WindowManager;

final class AodOverlay {
    private static final String TAG = "AliveHSAod";
    private static SurfaceView view;
    private static RenderLoop loop;

    static void show(Service service) {
        Trace.log("T10", "overlay#show enter");
        if (view != null) { Trace.log("T10", "overlay#show already"); return; }
        if (!ownsWallpaper(service)) { AliveLog.i(TAG, "overlay skipped: wallpaper is not ours"); return; }
        if (HyperOsAod.injectIntoAod()) { AliveLog.i(TAG, "injected into the AOD view"); return; }
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
        try {
            android.app.WallpaperManager manager = android.app.WallpaperManager.getInstance(context);
            android.app.WallpaperInfo home = manager.getWallpaperInfo();
            if (home != null && "org.aliveclean".equals(home.getPackageName())) return true;
            android.app.WallpaperInfo lock = manager.getWallpaperInfo(android.app.WallpaperManager.FLAG_LOCK);
            return lock != null && "org.aliveclean".equals(lock.getPackageName());
        } catch (Throwable unavailable) { return false; }
    }

    private static final class AodView extends SurfaceView implements SurfaceHolder.Callback {
        AodView(Context context) {
            super(context);
            getHolder().addCallback(this);
            getHolder().setFormat(PixelFormat.TRANSLUCENT);
            setAlpha(0.9f);
        }
        @Override public void surfaceCreated(SurfaceHolder holder) {
            loop = new RenderLoop(getContext());
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
