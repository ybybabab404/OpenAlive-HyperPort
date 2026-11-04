package org.aliveclean;

import android.app.AndroidAppHelper;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import org.json.JSONObject;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * Owns the one system key that actually selects MIUI's full-screen AOD mode.
 *
 * Evidence (com.miui.aod, OS4.0.0.33):
 *   Utils.getFsAodStyle()            reads Settings.Secure."full_screen_aod_on" (default 1
 *                                    on non-fold devices), Utils.java:522-524.
 *   Utils.isFsAodSameToLock()        isSupportFsAod() && getFsAodStyle()==1, Utils.java:530-532.
 *   SettingsView.udpateFsAodStyleDropView builds the two-entry spinner and writes the selected
 *                                    index through Utils.setFsAodStyle(), SettingsView.java:298-324.
 *   AnalyticManager                  labels value 1 as "和锁屏一致" and 0 as "自定义",
 *                                    AnalyticManager.java:195.
 *   full_screen_aod_support          is only the capability bit (Utils.java:542-547); it is not
 *                                    the user's mode.
 *
 * The app process has no WRITE_SECURE_SETTINGS, so the value is written by the system-UID
 * wallpaper adapter (SceneApply, UID 1000) or by the wallpaper service hooks (miwallpaper /
 * system_server), never by an Activity. The cross-process record therefore lives in
 * Settings.Secure under module-owned keys; SharedPreferences("aod.guard") is the app-side
 * mirror used for diagnostics and for the "was it locked?" answer.
 */
final class AodModeGuard {
    private static final String TAG = "HyperAliveAodMode";

    static final String SECURE_FULL_AOD = "full_screen_aod_on";
    static final String SECURE_LOCKED = "alive_aod_mode_locked";
    static final String SECURE_ORIGINAL = "alive_aod_mode_original";
    static final String SECURE_SAVED_AT = "alive_aod_mode_saved_at";

    /** App-side mirror. Authoritative copy is Settings.Secure; this is what the UI can read. */
    static final String PREFS = "aod.guard";
    static final String PREF_LOCKED = "locked";
    static final String PREF_ORIGINAL = "original";
    static final String PREF_SAVED_AT = "saved_at";

    private static final String PKG = "org.aliveclean";

    private AodModeGuard() {
    }

    static final class Result {
        final boolean ok;
        final String action;
        final int before;
        final int after;
        final String error;

        Result(boolean ok, String action, int before, int after, String error) {
            this.ok = ok;
            this.action = action;
            this.before = before;
            this.after = after;
            this.error = error == null ? "" : error;
        }

        static Result ok(String action, int before, int after) {
            return new Result(true, action, before, after, "");
        }

        static Result skipped(String action) {
            return new Result(true, action + "-skipped", -1, -1, "");
        }

        static Result error(String action, String error) {
            return new Result(false, action, -1, -1, error);
        }

        String json() {
            try {
                return new JSONObject().put("ok", ok).put("action", action)
                        .put("before", before).put("after", after).put("error", error).toString();
            } catch (Exception impossible) {
                return "{\"ok\":false,\"action\":\"" + action + "\",\"error\":\"json\"}";
            }
        }

        static Result parse(String text) {
            if (text == null || text.isEmpty()) return skipped("parse");
            try {
                JSONObject value = new JSONObject(text);
                return new Result(value.optBoolean("ok", false), value.optString("action", "unknown"),
                        value.optInt("before", -1), value.optInt("after", -1), value.optString("error", ""));
            } catch (Exception malformed) {
                return error("parse", malformed.toString());
            }
        }

        static Result parseAfter(String output, String marker) {
            if (output == null) return skipped("parse");
            for (String line : output.split("\\n")) {
                int at = line.indexOf(marker);
                if (at < 0) continue;
                String tail = line.substring(at + marker.length()).trim();
                if (tail.startsWith("{")) return parse(tail);
            }
            return skipped("parse");
        }
    }

    /**
     * Forces the system AOD mode to "same as lock screen" and remembers the previous value.
     * Reading is done first: if the key is absent it is recorded as -1 and will be deleted on
     * restore; if it contains anything other than 0/1 nothing is written at all.
     */
    static Result lock(ContentResolver resolver) {
        if (resolver == null) return Result.error("lock", "no resolver");
        try {
            String raw = Settings.Secure.getString(resolver, SECURE_FULL_AOD);
            int before;
            if (raw == null) {
                before = -1;
            } else {
                try {
                    before = Integer.parseInt(raw.trim());
                } catch (NumberFormatException bad) {
                    return Result.error("lock", "unreadable " + SECURE_FULL_AOD + "=" + raw);
                }
                if (before != 0 && before != 1) {
                    return Result.error("lock", "unexpected " + SECURE_FULL_AOD + "=" + before);
                }
            }

            if (Settings.Secure.getInt(resolver, SECURE_LOCKED, 0) == 1) {
                int saved = Settings.Secure.getInt(resolver, SECURE_ORIGINAL, -2);
                if (saved != -1 && saved != 0 && saved != 1) {
                    return Result.error("lock", "locked but original missing");
                }
                if (!Settings.Secure.putInt(resolver, SECURE_FULL_AOD, 1)) {
                    return Result.error("lock", "cannot re-assert " + SECURE_FULL_AOD);
                }
                if (Settings.Secure.getInt(resolver, SECURE_FULL_AOD, -2) != 1) {
                    return Result.error("lock", "re-assert verification failed");
                }
                Log.i(TAG, "already locked; kept original=" + saved);
                return Result.ok("lock", saved, 1);
            }

            if (!Settings.Secure.putInt(resolver, SECURE_ORIGINAL, before)) {
                return Result.error("lock", "cannot save original");
            }
            if (!Settings.Secure.putInt(resolver, SECURE_SAVED_AT, (int) (System.currentTimeMillis() / 1000L))) {
                return Result.error("lock", "cannot save timestamp");
            }
            if (!Settings.Secure.putInt(resolver, SECURE_FULL_AOD, 1)) {
                clearOriginal(resolver);
                return Result.error("lock", "cannot write " + SECURE_FULL_AOD);
            }
            if (Settings.Secure.getInt(resolver, SECURE_FULL_AOD, -2) != 1) {
                writeFull(resolver, before);
                clearOriginal(resolver);
                return Result.error("lock", "verification failed");
            }
            if (!Settings.Secure.putInt(resolver, SECURE_LOCKED, 1)) {
                writeFull(resolver, before);
                clearOriginal(resolver);
                return Result.error("lock", "cannot set lock flag");
            }
            Log.i(TAG, "locked " + SECURE_FULL_AOD + " " + before + " -> 1");
            return Result.ok("lock", before, 1);
        } catch (Throwable failure) {
            Log.w(TAG, "lock failed", failure);
            return Result.error("lock", failure.toString());
        }
    }

    /**
     * Restores the value captured by the last lock. No lock flag means no write at all, so a
     * device that never ran this code is untouched. A missing/unreadable original aborts.
     */
    static Result restore(ContentResolver resolver) {
        if (resolver == null) return Result.error("restore", "no resolver");
        try {
            if (Settings.Secure.getInt(resolver, SECURE_LOCKED, 0) != 1) {
                return Result.skipped("restore");
            }
            int original = Settings.Secure.getInt(resolver, SECURE_ORIGINAL, -2);
            if (original != -1 && original != 0 && original != 1) {
                return Result.error("restore", "original missing, refusing to guess");
            }
            // Clear the lock first: the AOD-settings hook rewrites every putInt while locked.
            if (!Settings.Secure.putInt(resolver, SECURE_LOCKED, 0)) {
                return Result.error("restore", "cannot clear lock flag");
            }
            if (!writeFull(resolver, original)) {
                Settings.Secure.putInt(resolver, SECURE_LOCKED, 1);
                return Result.error("restore", "cannot write original");
            }
            boolean restored = original == -1
                    ? Settings.Secure.getString(resolver, SECURE_FULL_AOD) == null
                    : Settings.Secure.getInt(resolver, SECURE_FULL_AOD, -2) == original;
            if (!restored) {
                Settings.Secure.putInt(resolver, SECURE_LOCKED, 1);
                return Result.error("restore", "verification failed");
            }
            clearOriginal(resolver);
            Log.i(TAG, "restored " + SECURE_FULL_AOD + " -> " + original);
            return Result.ok("restore", original, original);
        } catch (Throwable failure) {
            Log.w(TAG, "restore failed", failure);
            return Result.error("restore", failure.toString());
        }
    }

    private static boolean writeFull(ContentResolver resolver, int value) {
        if (value == -1) return Settings.Secure.putString(resolver, SECURE_FULL_AOD, null);
        return Settings.Secure.putInt(resolver, SECURE_FULL_AOD, value);
    }

    private static void clearOriginal(ContentResolver resolver) {
        try {
            Settings.Secure.putInt(resolver, SECURE_ORIGINAL, -1);
            Settings.Secure.putInt(resolver, SECURE_SAVED_AT, 0);
        } catch (Throwable ignored) {
        }
    }

    static boolean isLocked(Context context) {
        return context != null && isLocked(context.getContentResolver());
    }

    static boolean isLocked(ContentResolver resolver) {
        try {
            return Settings.Secure.getInt(resolver, SECURE_LOCKED, 0) == 1;
        } catch (Throwable unavailable) {
            return false;
        }
    }

    /** App-side mirror. Called after SceneApply returns, i.e. when the wallpaper is confirmed. */
    static void remember(Context context, Result result) {
        if (context == null || result == null) return;
        try {
            SharedPreferences preferences = context.getSharedPreferences(PREFS, 0);
            boolean locked = result.ok && "lock".equals(result.action);
            preferences.edit()
                    .putBoolean(PREF_LOCKED, locked)
                    .putInt(PREF_ORIGINAL, result.before)
                    .putLong(PREF_SAVED_AT, System.currentTimeMillis())
                    .putString("last_action", result.action)
                    .putString("last_error", result.error)
                    .commit();
        } catch (Throwable failure) {
            Log.w(TAG, "mirror write failed", failure);
        }
    }

    /**
     * Installed in com.miui.aod. It is the setter all MIUI AOD paths use:
     * SettingsView spinner, AodStateScreen AI action, backup restore and ThemeReceiver.
     */
    static void installAodSettings(ClassLoader loader) {
        try {
            Class<?> utils = XposedHelpers.findClassIfExists("com.miui.aod.Utils", loader);
            if (utils != null) {
                XposedHelpers.findAndHookMethod(utils, "setFsAodStyle", Context.class, int.class,
                        new XC_MethodHook() {
                            @Override protected void beforeHookedMethod(MethodHookParam param) {
                                Context context = (Context) param.args[0];
                                if (!isLocked(context)) return;
                                int requested = (Integer) param.args[1];
                                if (requested != 1) {
                                    param.args[1] = 1;
                                    Log.i(TAG, "blocked setFsAodStyle(" + requested + ") while locked");
                                }
                            }
                        });
                Log.i(TAG, "com.miui.aod.Utils.setFsAodStyle hooked");
            }
        } catch (Throwable failure) {
            Log.w(TAG, "setFsAodStyle hook failed", failure);
        }
        try {
            XposedHelpers.findAndHookMethod(android.provider.Settings.Secure.class, "putInt",
                    ContentResolver.class, String.class, int.class, new XC_MethodHook() {
                        @Override protected void beforeHookedMethod(MethodHookParam param) {
                            if (!SECURE_FULL_AOD.equals(param.args[1])) return;
                            if (!isLocked((ContentResolver) param.args[0])) return;
                            int requested = (Integer) param.args[2];
                            if (requested != 1) {
                                param.args[2] = 1;
                                Log.i(TAG, "blocked direct " + SECURE_FULL_AOD + "=" + requested + " while locked");
                            }
                        }
                    });
            Log.i(TAG, "Settings.Secure.putInt(" + SECURE_FULL_AOD + ") guarded");
        } catch (Throwable failure) {
            Log.w(TAG, "Secure.putInt hook failed", failure);
        }
    }

    /**
     * Installed in com.miui.miwallpaper. Q() is the single place that calls
     * WallpaperManager.setWallpaperComponentWithFlags() for every component wallpaper
     * (MiuiWallpaperManagerService.java:810-831); S() is its static-image counterpart.
     */
    static void installWallpaperService(ClassLoader loader) {
        Class<?> service = XposedHelpers.findClassIfExists(
                "com.miui.miwallpaper.server.MiuiWallpaperManagerService", loader);
        if (service == null) {
            Log.i(TAG, "MiuiWallpaperManagerService missing");
            return;
        }
        try {
            XposedHelpers.findAndHookMethod(service, "Q", WallpaperManager.class, ComponentName.class, int.class,
                    new XC_MethodHook() {
                        @Override protected void afterHookedMethod(MethodHookParam param) {
                            if (param.hasThrowable()) return;
                            ComponentName component = (ComponentName) param.args[1];
                            Context context = appContext(param.thisObject);
                            if (context == null) return;
                            if (component != null && PKG.equals(component.getPackageName())) {
                                log(context, lock(context.getContentResolver()));
                            } else {
                                log(context, restore(context.getContentResolver()));
                            }
                        }
                    });
            Log.i(TAG, "miwallpaper Q hooked");
        } catch (Throwable failure) {
            Log.w(TAG, "miwallpaper Q hook failed", failure);
        }
        try {
            XposedHelpers.findAndHookMethod(service, "S", String.class, String.class, String.class, int.class,
                    new XC_MethodHook() {
                        @Override protected void afterHookedMethod(MethodHookParam param) {
                            if (param.hasThrowable()) return;
                            Context context = appContext(param.thisObject);
                            if (context != null) log(context, restore(context.getContentResolver()));
                        }
                    });
            Log.i(TAG, "miwallpaper S hooked");
        } catch (Throwable failure) {
            Log.w(TAG, "miwallpaper S hook failed", failure);
        }
    }

    /**
     * Installed in the "android" process. Covers wallpaper changes that bypass the MIUI
     * wallpaper app (gallery "set as wallpaper", third-party pickers, boot restore).
     */
    static void installSystemWallpaper(ClassLoader loader) {
        Class<?> service = XposedHelpers.findClassIfExists(
                "com.android.server.wallpaper.WallpaperManagerService", loader);
        if (service == null) {
            Log.i(TAG, "WallpaperManagerService missing");
            return;
        }
        XC_MethodHook hook = new XC_MethodHook() {
            @Override protected void afterHookedMethod(MethodHookParam param) {
                final Object serviceObject = param.thisObject;
                if (serviceObject == null) return;
                Context context = systemContext(serviceObject);
                if (context == null) return;
                // The wallpaper binder transaction can still be settling here. Check twice so a
                // component switch is never mistaken for a system-wallpaper restore.
                new Handler(Looper.getMainLooper()).postDelayed(() -> checkCurrent(context, 2), 700L);
            }
        };
        int hooked = 0;
        for (String name : new String[]{"setWallpaper", "setWallpaperComponent", "setWallpaperComponentWithFlags"}) {
            try {
                XposedBridge.hookAllMethods(service, name, hook);
                hooked++;
            } catch (Throwable failure) {
                Log.w(TAG, "system wallpaper hook " + name + " failed", failure);
            }
        }
        Log.i(TAG, "system wallpaper hooks=" + hooked);
    }

    private static void checkCurrent(Context context, int attempts) {
        try {
            WallpaperManager manager = WallpaperManager.getInstance(context);
            WallpaperInfo info = manager.getWallpaperInfo();
            boolean ours = info != null && PKG.equals(info.getPackageName());
            if (ours) {
                log(context, lock(context.getContentResolver()));
            } else if (attempts > 1) {
                new Handler(Looper.getMainLooper()).postDelayed(() -> checkCurrent(context, attempts - 1), 700L);
            } else {
                log(context, restore(context.getContentResolver()));
            }
        } catch (Throwable failure) {
            Log.w(TAG, "wallpaper state check failed", failure);
        }
    }

    private static Context appContext(Object service) {
        try {
            Context context = AndroidAppHelper.currentApplication();
            if (context != null) return context;
        } catch (Throwable ignored) {
        }
        try {
            Object value = XposedHelpers.getObjectField(service, "f2273g");
            if (value instanceof Context) return (Context) value;
        } catch (Throwable ignored) {
        }
        return null;
    }

    private static Context systemContext(Object service) {
        try {
            Object value = XposedHelpers.getObjectField(service, "mContext");
            if (value instanceof Context) return (Context) value;
        } catch (Throwable ignored) {
        }
        try {
            return AndroidAppHelper.currentApplication();
        } catch (Throwable ignored) {
            return null;
        }
    }

    private static void log(Context context, Result result) {
        if (result == null) return;
        Log.i(TAG, result.json());
        // Do not mirror here: this helper also runs in miwallpaper/system_server, whose
        // SharedPreferences are not the app's. The app mirrors its own SceneApply result.
    }
}
