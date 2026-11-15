package org.aliveclean;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import java.lang.reflect.Method;

final class AodWallpaperMode {
    static final String KEY_MODE = "full_screen_aod_on";
    static final String KEY_RESTORE = "alive_aod_restore_value";
    static final String KEY_HAS_RESTORE = "alive_aod_restore_set";
    static final String KEY_ACTIVE = "alive_aod_active";
    private static final String TAG = "AliveHSHooks";
    private static final int UNSET = -1;
    private static final int RESTORE_CONFIRMATIONS = 5;
    private static final long RESTORE_CONFIRM_MS = 1000L;
    private static final Object RESTORE_LOCK = new Object();
    private static boolean restoreInFlight;

    private AodWallpaperMode() {}

    private static boolean aodSwitchFromShell() {
        try {
            java.lang.Process process = new java.lang.ProcessBuilder(
                    "/system/bin/settings", "get", "secure", "doze_always_on")
                    .redirectErrorStream(true).start();
            java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getInputStream(), "UTF-8"));
            String line = reader.readLine();
            process.waitFor(2, java.util.concurrent.TimeUnit.SECONDS);
            return line != null && "1".equals(line.trim());
        } catch (Throwable error) {
            AliveLog.w(TAG, "cannot read doze_always_on from shell", error);
            return true;
        }
    }

    static boolean alive(Context context) {
        try {
            WallpaperManager manager = WallpaperManager.getInstance(context);
            // getWallpaperInfo() is non-null only for a LIVE system wallpaper.  A
            // static system wallpaper returns null; falling back to the lock slot in
            // that case would treat a stale live lock wallpaper as active and keep
            // the AOD overlay / depth hider running after the user switched the home
            // wallpaper back to MIUI.  The system slot is the single source of truth;
            // if it is not our live wallpaper, we are not alive.
            return isOurs(manager.getWallpaperInfo());
        } catch (Throwable error) {
            AliveLog.w(TAG, "aod wallpaper lookup failed", error);
            return false;
        }
    }

    private static boolean isOurs(WallpaperInfo info) {
        return info != null && info.getComponent() != null
                && "org.aliveclean".equals(info.getComponent().getPackageName());
    }

    /**
     * UI-process variant.  A static system wallpaper normally returns null from
     * getWallpaperInfo(); that must mean "not ours", not "unknown".  Only when the
     * wallpaper provider cannot be queried at all do we fall back to the marker written
     * by enter(), so a stale marker can never keep the settings row decorated after a
     * switch back to a system wallpaper.
     */
    static boolean uiAlive(Context context) {
        try {
            WallpaperManager manager = WallpaperManager.getInstance(context);
            WallpaperInfo info = manager.getWallpaperInfo();
            if (info != null) return isOurs(info);
            if (manager.getWallpaperId(WallpaperManager.FLAG_SYSTEM) >= 0) return false;
            return getInt(context, KEY_ACTIVE, 0) == 1;
        } catch (Throwable error) {
            try {
                return getInt(context, KEY_ACTIVE, 0) == 1;
            } catch (Throwable ignored) {
                return false;
            }
        }
    }

    /*
     * SceneApply runs in an app_process with a system Context that has no registered
     * application.  Plain Settings.Secure and even the *ForUser variants can fail with
     * "Unable to find app for caller ... when getting content provider settings".  The
     * module app therefore uses its existing su path for only its own private keys.
     */
    private static boolean isModuleApp(Context context) {
        return context != null && "org.aliveclean".equals(context.getPackageName());
    }

    /*
     * The wallpaper apply helper runs in an app_process with a system Context that
     * has no registered application, so even getIntForUser() fails before it reaches
     * the settings provider.  The module app itself already uses su for wallpaper
     * application; use the same root shell only for its own private restore keys.
     */
    private static String rootSettings(String command) throws Exception {
        Process process = new ProcessBuilder("su", "0", "-c", command)
                .redirectErrorStream(true).start();
        StringBuilder output = new StringBuilder();
        Thread reader = new Thread(() -> {
            try (java.io.BufferedReader in = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getInputStream(), "UTF-8"))) {
                for (String line; (line = in.readLine()) != null; ) {
                    if (output.length() < 4096) output.append(line).append('\n');
                }
            } catch (Exception ignored) { }
        }, "RootSettings");
        reader.start();
        if (!process.waitFor(5, java.util.concurrent.TimeUnit.SECONDS)) {
            process.destroyForcibly();
            throw new java.io.IOException("settings command timed out");
        }
        reader.join(1000);
        String result = output.toString().trim();
        if (process.exitValue() != 0) throw new java.io.IOException("settings failed: " + result);
        return result;
    }

    private static int getInt(Context context, String key, int fallback) {
        if (isModuleApp(context)) {
            try {
                String value = rootSettings("settings get secure " + key);
                int newline = value.lastIndexOf('\n');
                if (newline >= 0) value = value.substring(newline + 1).trim();
                return "null".equals(value) || value.isEmpty() ? fallback : Integer.parseInt(value);
            } catch (Throwable error) {
                throw new IllegalStateException("cannot read " + key, error);
            }
        }
        ContentResolver resolver = context.getContentResolver();
        try {
            return Settings.Secure.getInt(resolver, key, fallback);
        } catch (Throwable plain) {
            try {
                Method method = Settings.Secure.class.getDeclaredMethod(
                        "getIntForUser", ContentResolver.class, String.class, int.class, int.class);
                method.setAccessible(true);
                Object value = method.invoke(null, resolver, key, fallback, 0);
                return value instanceof Integer ? (Integer) value : fallback;
            } catch (Throwable hidden) {
                plain.addSuppressed(hidden);
                throw new IllegalStateException("cannot read " + key, plain);
            }
        }
    }

    private static void putInt(Context context, String key, int value) {
        if (isModuleApp(context)) {
            try {
                rootSettings("settings put secure " + key + " " + value);
                return;
            } catch (Throwable error) {
                throw new IllegalStateException("cannot write " + key, error);
            }
        }
        ContentResolver resolver = context.getContentResolver();
        try {
            Settings.Secure.putInt(resolver, key, value);
        } catch (Throwable plain) {
            try {
                Method method = Settings.Secure.class.getDeclaredMethod(
                        "putIntForUser", ContentResolver.class, String.class, int.class, int.class);
                method.setAccessible(true);
                method.invoke(null, resolver, key, value, 0);
            } catch (Throwable hidden) {
                plain.addSuppressed(hidden);
                throw new IllegalStateException("cannot write " + key, plain);
            }
        }
    }

    private static void putString(Context context, String key, String value) {
        if (isModuleApp(context)) {
            try {
                rootSettings(value == null
                        ? "settings delete secure " + key
                        : "settings put secure " + key + " " + value);
                return;
            } catch (Throwable error) {
                throw new IllegalStateException("cannot write " + key, error);
            }
        }
        ContentResolver resolver = context.getContentResolver();
        try {
            Settings.Secure.putString(resolver, key, value);
        } catch (Throwable plain) {
            try {
                Method method = Settings.Secure.class.getDeclaredMethod(
                        "putStringForUser", ContentResolver.class, String.class, String.class, int.class);
                method.setAccessible(true);
                method.invoke(null, resolver, key, value, 0);
            } catch (Throwable hidden) {
                plain.addSuppressed(hidden);
                throw new IllegalStateException("cannot write " + key, plain);
            }
        }
    }

    /**
     * 用户选的息屏样式：`full_screen_aod_on==1` 表示「和锁屏样式一致」（全屏 AOD），
     * 否则是「自定义」。模块**不再改写**这个键，只按它决定要不要补足全屏 AOD 能力。
     */
    static boolean wantsFullAod(Context context) {
        if (context == null) return false;
        try {
            return getInt(context, KEY_MODE, 0) == 1;
        } catch (Throwable error) {
            AliveLog.w(TAG, "cannot read full_screen_aod_on", error);
            return false;
        }
    }

    static void enter(Context context) {
        // 2026-11-16：不再写 full_screen_aod_on。
        // 旧设计在应用壁纸时把它强写成 1，导致「用户选自定义 → 切壁纸后又变回全屏 AOD」，
        // 而且一旦 marker 被误清就无法还原用户原值。现在模块完全尊重用户的选择，
        // 需要全屏 AOD 时由 AodFullScreen 去补足设备能力（本机 MiuiConfigs 原生是 false）。
        AliveLog.i(TAG, "aod mode enter skipped: user setting is authoritative ("
                + (wantsFullAod(context) ? "full" : "custom") + ")");
    }

    /**
     * 只有在「连续多次都确认壁纸不是我们」时才真的还原。
     * 装包更新、进程重启、系统重绑壁纸都会有几百毫秒到几秒的空窗，
     * 单次判空就还原会把 full_screen_aod_on 打回原值并且再无路径写回。
     */
    static void restoreWhenForeign(final Context context) {
        if (context == null) return;
        synchronized (RESTORE_LOCK) {
            if (restoreInFlight) return;
            restoreInFlight = true;
        }
        final Handler handler = new Handler(Looper.getMainLooper());
        final int[] misses = new int[]{0};
        final int[] ours = new int[]{0};
        final Runnable[] step = new Runnable[1];
        step[0] = new Runnable() {
            @Override
            public void run() {
                try {
                    if (alive(context)) {
                        if (misses[0] != 0) {
                            AliveLog.i(TAG, "aod restore cancelled after " + misses[0] + " miss");
                            finishRestoreProbe();
                            return;
                        }
                        // A mutation callback can arrive before WMS has rebound the
                        // wallpaper.  Give the live wallpaper a short grace period to
                        // disappear instead of cancelling on the first early probe.
                        ours[0]++;
                        if (ours[0] >= RESTORE_CONFIRMATIONS) {
                            AliveLog.i(TAG, "aod restore cancelled: wallpaper is still ours");
                            finishRestoreProbe();
                            return;
                        }
                    } else {
                        misses[0]++;
                        if (misses[0] >= RESTORE_CONFIRMATIONS) {
                            AliveLog.i(TAG, "aod foreign confirmed x" + misses[0]);
                            restore(context);
                            finishRestoreProbe();
                            return;
                        }
                    }
                    handler.postDelayed(step[0], RESTORE_CONFIRM_MS);
                } catch (Throwable error) {
                    AliveLog.w(TAG, "aod restore probe failed", error);
                    finishRestoreProbe();
                }
            }
        };
        handler.post(step[0]);
    }

    private static void finishRestoreProbe() {
        synchronized (RESTORE_LOCK) {
            restoreInFlight = false;
        }
    }

    /**
     * 2026-11-16 起不再改写 `full_screen_aod_on`：这个键完全属于用户。
     * 旧实现会把它"修回 1"，于是用户手动选「自定义」后又被改回「和锁屏样式一致」，
     * 这正是 Bug B 的表象之一。保留方法只为兼容旧调用点。
     */
    static void ensureEnabled(Context context) {
        if (context == null) return;
        // 不做任何写入；只把当前状态记一笔，便于排查。
        AliveLog.once(TAG, "aod mode untouched",
                "aod mode left to the user (" + (wantsFullAod(context) ? "full" : "custom") + ")");
    }

    static void restore(Context context) {
        try {
            if (getInt(context, KEY_HAS_RESTORE, 0) != 1) return;
            int original = getInt(context, KEY_RESTORE, UNSET);
            if (original == UNSET) {
                putString(context, KEY_MODE, null);
            } else {
                putInt(context, KEY_MODE, original);
            }
            putString(context, KEY_RESTORE, null);
            putString(context, KEY_HAS_RESTORE, null);
            putString(context, KEY_ACTIVE, null);
            AliveLog.i(TAG, "aod mode restored=" + original);
        } catch (Throwable error) {
            AliveLog.w(TAG, "aod mode restore failed", error);
        }
    }
}
