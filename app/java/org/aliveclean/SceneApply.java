package org.aliveclean;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Looper;
import java.lang.reflect.InvocationTargetException;

public final class SceneApply {
    static void apply(Context context) throws Exception {
        if (android.os.Process.myUid() / 100000 != 0) throw new java.io.IOException("目前仅支持主用户");
        AliveLog.i(AliveLog.APPLY, "scene apply start");
        AodWallpaperMode.enter(context);
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
        if (!process.waitFor(25, java.util.concurrent.TimeUnit.SECONDS)) { process.destroyForcibly(); AodWallpaperMode.restore(context); throw new java.io.IOException("壁纸应用超时，请检查 Root 授权"); }
        reader.join(1000);
        String output; synchronized (result) { output = result.toString(); }
        if (process.exitValue() != 0 || !output.contains("SCENE_APPLIED")) {
            AliveLog.e(AliveLog.APPLY, "scene apply rejected exit=" + process.exitValue() + " output=" + output.trim());
            if (output.contains("AMBIENT_WALLPAPER"))
                { AodWallpaperMode.restore(context); throw new java.io.IOException("缺少「系统框架」作用域：请在 LSPosed 里给本模块勾选「系统框架」再重启手机"); }
            { AodWallpaperMode.restore(context); throw new java.io.IOException("系统未完成壁纸应用：" + output.trim()); }
        }
        // ActivityThread.systemMain() in the child may see a stale/default value
        // for doze_always_on; enforce the app process's real user choice.
        if (!HyperOsAod.aodEnabled(context)) {
            AodWallpaperMode.restore(context);
            AliveLog.i(AliveLog.APPLY, "scene applied with AOD off; full_screen_aod_on restored");
        }

    }

    private static boolean aodSwitchFromShell() {
        try {
            Process process = new ProcessBuilder("/system/bin/settings", "get", "secure", "doze_always_on")
                    .redirectErrorStream(true).start();
            java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getInputStream(), "UTF-8"));
            String line = reader.readLine();
            process.waitFor(2, java.util.concurrent.TimeUnit.SECONDS);
            return line != null && "1".equals(line.trim());
        } catch (Throwable error) {
            AliveLog.w(AliveLog.APPLY, "cannot read doze_always_on from shell", error);
            return true;
        }
    }

    public static void main(String[] args) {
        try {
            if (android.os.Process.myUid() != 1000) throw new SecurityException("System UID required");
            AliveLog.i(AliveLog.APPLY, "scene apply subprocess start uid=" + android.os.Process.myUid());
            Looper.prepareMainLooper();
            Class<?> thread = Class.forName("android.app.ActivityThread");
            Object instance = thread.getMethod("systemMain").invoke(null);
            Context context = (Context) thread.getMethod("getSystemContext").invoke(instance);
            WallpaperManager manager = WallpaperManager.getInstance(context);
            ComponentName component = new ComponentName("org.aliveclean", "org.aliveclean.CleanWallpaper");
            // Capture the user's AOD mode before MIUI's wallpaper pipeline gets a
            // chance to rewrite full_screen_aod_on.  The previous order (apply first,
            // enter afterwards) could capture the already-forced value 1 and restore
            // "和锁屏样式一致" instead of the user's original "自定义".
            // ActivityThread.systemMain() can expose a stale/default doze value
            // through Settings.Secure, so confirm the real setting from the shell.
            if (aodSwitchFromShell()) {
                AodWallpaperMode.enter(context);
            } else {
                AliveLog.i(AliveLog.APPLY, "aod mode enter skipped: AOD is disabled");
            }
            try {
                try {
                    WallpaperManager.class.getMethod("setWallpaperComponentWithFlags", ComponentName.class, int.class)
                            .invoke(manager, component, WallpaperManager.FLAG_SYSTEM | WallpaperManager.FLAG_LOCK);
                } catch (InvocationTargetException wrapped) {
                    WallpaperInfo actual = manager.getWallpaperInfo();
                    if (!(wrapped.getCause() instanceof SecurityException) || actual == null || !component.equals(actual.getComponent())) throw wrapped;
                }
                WallpaperInfo actual = manager.getWallpaperInfo();
                if (actual == null || !component.equals(actual.getComponent())) throw new IllegalStateException("Wallpaper verification failed");
            } catch (Throwable applyError) {
                AodWallpaperMode.restore(context);
                throw applyError;
            }
            ModuleActivation.request(context);
            AliveLog.i(AliveLog.APPLY, "scene applied " + component.flattenToShortString());
            System.out.println("SCENE_APPLIED");
            System.exit(0);
        } catch (Throwable error) {
            AliveLog.e(AliveLog.APPLY, "scene apply failed", error);
            error.printStackTrace(System.out);
            System.out.println("SCENE_FAILED " + error);
            System.exit(1);
        }
    }

    private SceneApply() { }
}
