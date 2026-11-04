package org.aliveclean;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Looper;
import java.lang.reflect.InvocationTargetException;

/** System-UID adapter that binds the Flyme scene wallpaper without the OEM picker.
 * HyperOS' live picker reports RESULT_CANCELED for third-party components, so the
 * editor applies the component transactionally and verifies it instead. */
public final class SceneApply {
    static AodModeGuard.Result apply(Context context) throws Exception {
        if (android.os.Process.myUid() / 100000 != 0) throw new java.io.IOException("目前仅支持主用户");
        String path = context.getApplicationInfo().sourceDir;
        String command = "CLASSPATH='" + path.replace("'", "'\"'\"'") + "' /system/bin/app_process /system/bin org.aliveclean.SceneApply";
        Process process = new ProcessBuilder("su", "1000", "-c", command).redirectErrorStream(true).start();
        StringBuilder result = new StringBuilder();
        Thread reader = new Thread(() -> {
            try (java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream(), "UTF-8"))) {
                for (String line; (line = in.readLine()) != null; ) synchronized (result) { if (result.length() < 8192) result.append(line).append('\n'); }
            } catch (Exception ignored) { }
        }, "SceneApplyOutput");
        reader.start();
        if (!process.waitFor(25, java.util.concurrent.TimeUnit.SECONDS)) { process.destroyForcibly(); throw new java.io.IOException("壁纸应用超时，请检查 Root 授权"); }
        reader.join(1000);
        String output; synchronized (result) { output = result.toString(); }
        if (process.exitValue() != 0 || !output.contains("SCENE_APPLIED")) throw new java.io.IOException("系统未完成壁纸应用：" + output.trim());
        // The adapter already verified the wallpaper; now mirror the AOD-mode transaction into
        // the app's SharedPreferences. Failure to lock is reported but never fails the wallpaper.
        AodModeGuard.Result locked = AodModeGuard.Result.parseAfter(output, "SCENE_APPLIED");
        AodModeGuard.remember(context, locked);
        return locked;
    }

    public static void main(String[] args) {
        try {
            if (android.os.Process.myUid() != 1000) throw new SecurityException("System UID required");
            Looper.prepareMainLooper();
            Class<?> thread = Class.forName("android.app.ActivityThread");
            Object instance = thread.getMethod("systemMain").invoke(null);
            Context context = (Context) thread.getMethod("getSystemContext").invoke(instance);
            WallpaperManager manager = WallpaperManager.getInstance(context);
            ComponentName component = new ComponentName("org.aliveclean", "org.aliveclean.CleanWallpaper");
            try {
                WallpaperManager.class.getMethod("setWallpaperComponentWithFlags", ComponentName.class, int.class)
                        .invoke(manager, component, WallpaperManager.FLAG_SYSTEM | WallpaperManager.FLAG_LOCK);
            } catch (InvocationTargetException wrapped) {
                WallpaperInfo actual = manager.getWallpaperInfo();
                if (!(wrapped.getCause() instanceof SecurityException) || actual == null || !component.equals(actual.getComponent())) throw wrapped;
            }
            WallpaperInfo actual = manager.getWallpaperInfo();
            if (actual == null || !component.equals(actual.getComponent())) throw new IllegalStateException("Wallpaper verification failed");
            // Runs as UID 1000, which holds WRITE_SECURE_SETTINGS. Read the old value first;
            // AodModeGuard.lock() refuses to write anything if it is missing or unreadable.
            AodModeGuard.Result aod = AodModeGuard.lock(context.getContentResolver());
            System.out.println("SCENE_APPLIED " + aod.json());
            System.exit(0);
        } catch (Throwable error) {
            System.out.println("SCENE_FAILED " + error);
            System.exit(1);
        }
    }

    private SceneApply() { }
}
