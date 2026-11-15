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

    static void enter(Context context) {
        if (context == null) return;
        // The user-facing AOD switch wins.  Applying our wallpaper while AOD is
        // off must not silently write full_screen_aod_on=1; the setting is
        // repaired later by ensureEnabled() after the user turns AOD back on.
        if (!HyperOsAod.aodEnabled(context) || !aodSwitchFromShell()) {
            AliveLog.i(TAG, "aod mode enter skipped: AOD is disabled");
            return;
        }
        int original;
        try {
            original = getInt(context, KEY_MODE, UNSET);
        } catch (Throwable error) {
            AliveLog.w(TAG, "aod mode read failed; left untouched", error);
            return;
        }
        // Old builds could leave an out-of-range value behind; never persist it.
        if (original > 1) original = 1;
        try {
            if (getInt(context, KEY_HAS_RESTORE, 0) != 1) {
                // KEY_MODE may be absent on a fresh device. In that case the restore
                // marker still has to exist, otherwise the mode becomes non-reversible
                // and the next restore cannot remove the key we are about to create.
                if (original == UNSET) {
                    putString(context, KEY_RESTORE, null);
                } else {
                    putInt(context, KEY_RESTORE, original);
                }
                putInt(context, KEY_HAS_RESTORE, 1);
            }
            putInt(context, KEY_MODE, 1);
            putInt(context, KEY_ACTIVE, 1);
            AliveLog.i(TAG, "aod mode ->1 (original=" + original + ")");
        } catch (Throwable error) {
            AliveLog.w(TAG, "aod mode write failed", error);
        }
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

    /** 壁纸是我们的，但开关不是 1：说明被别的路径打回去了，重新写回。只在「我们本来开着」时动手。 */
    static void ensureEnabled(Context context) {
        if (context == null) return;
        try {
            if (!alive(context)) return;
            // The user-facing AOD switch wins.  Repairing full_screen_aod_on while
            // doze_always_on is off would silently turn the option back on.
            if (!HyperOsAod.aodEnabled(context) || !aodSwitchFromShell()) return;
            if (getInt(context, KEY_HAS_RESTORE, 0) != 1) {
                // The wallpaper may have been selected from MIUI's own picker, so
                // SceneApply.enter() never ran.  Establish the restore marker now.
                enter(context);
                return;
            }
            if (getInt(context, KEY_MODE, UNSET) == 1) return;
            putInt(context, KEY_MODE, 1);
            AliveLog.i(TAG, "aod mode repaired ->1");
        } catch (Throwable error) {
            AliveLog.w(TAG, "aod mode repair failed", error);
        }
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
