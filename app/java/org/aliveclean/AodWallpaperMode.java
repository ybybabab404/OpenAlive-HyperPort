package org.aliveclean;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import java.lang.reflect.Method;

/**
 * {@code full_screen_aod_on} 的唯一负责人：进入我们的壁纸时记下用户原值（capture），
 * 切回别人的壁纸时按需还原（restore），行为需要时只 hook 读取、绝不写存储值。
 *
 * <p>判据分两个槽：{@code alive()} 看系统槽，{@link #lockShowsOurs} 看锁屏槽。
 * 锁屏上真正显示的是锁屏槽，只用系统槽判断会误判成"我们的壁纸"（2026-11-16 的双层问题）。</p>
 */
final class AodWallpaperMode {
    static final String KEY_MODE = "full_screen_aod_on";
    /**
     * "把壁纸组件交还 MIUI"的跨进程信号。
     *
     * <p>交还组件要 {@code SET_WALLPAPER_COMPONENT} 权限，只有 system_server 拿着，
     * 所以在 WallpaperManagerService 里注册接收者，应用进程发一个受签名权限保护的广播。</p>
     */
    static final String ACTION_RELEASE_WALLPAPER = "org.aliveclean.action.RELEASE_WALLPAPER";
    static final String PERMISSION_RELEASE_WALLPAPER =
            "org.aliveclean.permission.RELEASE_WALLPAPER";

    /** 最近一次"把组件交还给系统"的时间；这之后的一小段时间内不许再把壁纸抢回来。 */
    private static volatile long releaseAtMs = 0L;

    static long lastReleaseAtMs() {
        return releaseAtMs;
    }

    /** 用户刚把壁纸交还给系统，恢复探测必须让路（否则会把 ImageWallpaper 又换回我们的）。 */
    static boolean releaseInFlight() {
        return android.os.SystemClock.elapsedRealtime() - releaseAtMs < 10_000L;
    }    static final String KEY_RESTORE = "alive_aod_restore_value";
    static final String KEY_HAS_RESTORE = "alive_aod_restore_set";
    static final String KEY_ACTIVE = "alive_aod_active";
    private static final String TAG = "AliveHSHooks";
    private static final String HOME_TAG = "AliveClean";
    private static final int UNSET = -1;
    /**
     * 兜底探测的节奏：以前是 5 次 × 1 秒（最多 5 秒才改回去），慢得能被用户看见。
     * 正常路径已经把「写 1」在写侧拦住了（见 {@code AodFullScreen#blockFullAodWrite}），
     * 这里只处理历史遗留的 1，所以两次快速确认足够。
     */
    private static final long[] RESTORE_PROBE_MS = {150L, 350L};
    /** 首次解锁后系统仍会重绑几次；独立探测桌面槽，最多覆盖约 5 秒。 */
    private static final long[] HOME_PROBE_MS = {150L, 350L, 700L, 1200L, 2000L};
    private static final ComponentName CLEAN_WALLPAPER = new ComponentName(
            "org.aliveclean", "org.aliveclean.CleanWallpaper");
    private static final Object RESTORE_LOCK = new Object();
    private static final Object HOME_RESTORE_LOCK = new Object();
    private static boolean restoreInFlight;
    private static boolean homeRestoreInFlight;

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

    /**
     * 锁屏上实际显示的是不是我们的壁纸。
     *
     * <p>锁屏有自己的独立壁纸时以它为准：live 壁纸看组件包名，静态图（`getWallpaperId>=0`
     * 但 `getWallpaperInfo` 为空）说明锁屏是 MIUI 自己的图 —— 这时 MIUI 的景深层属于它自己，
     * 我们的 {@code KeyguardDepthHider} 一旦去清空，MIUI 会重建一份，锁屏上就出现
     * "两个景深上层上下重叠"。只有锁屏没有独立壁纸（继承系统壁纸）时，才用系统槽判断。</p>
     */
    static boolean lockShowsOurs(Context context) {
        try {
            WallpaperManager manager = WallpaperManager.getInstance(context);
            if (Build.VERSION.SDK_INT >= 34) {
                WallpaperInfo lock = manager.getWallpaperInfo(WallpaperManager.FLAG_LOCK);
                if (lock != null) return isOurs(lock);
                if (manager.getWallpaperId(WallpaperManager.FLAG_LOCK) >= 0) return false;
            }
            return alive(context);
        } catch (Throwable error) {
            AliveLog.w(TAG, "lock wallpaper lookup failed", error);
            return false;
        }
    }

    private static boolean isOurs(WallpaperInfo info) {
        return info != null && info.getComponent() != null
                && CLEAN_WALLPAPER.getPackageName().equals(info.getComponent().getPackageName());
    }

    private static boolean isCleanWallpaper(WallpaperInfo info) {
        return info != null && CLEAN_WALLPAPER.equals(info.getComponent());
    }

    private static String wallpaperComponent(WallpaperInfo info) {
        return info == null || info.getComponent() == null ? "static/none"
                : info.getComponent().flattenToShortString();
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
        // 2026-11-16：应用壁纸时不再写 full_screen_aod_on（用户的选择是权威），
        // 但要**记下用户当时的值**：设置页被我们锁在 index 1（"AliveHS 样式" = 和锁屏样式一致），
        // MIUI 自己会把这个 1 写进存储；切回系统壁纸时若不留底，就永远回不到用户原来的模式。
        capture(context);
        AliveLog.i(TAG, "aod mode enter skipped: user setting is authoritative ("
                + (wantsFullAod(context) ? "full" : "custom") + ")");
    }

    /**
     * 记下用户当前的真实值，供 {@link #restore(Context)} 使用。
     *
     * <p>只捕获一次（marker 已存在就不覆盖），因此多次应用壁纸始终还原到用户最初的选择。
     * 模块 app 进程里的 {@link #getInt} 走 root `settings get`，拿到的是**真实存储值**，
     * 不会被 {@code AodFullScreen} 那个"读取即返回 1"的 override 影响。</p>
     */
    private static void capture(Context context) {
        if (context == null) return;
        try {
            if (getInt(context, KEY_HAS_RESTORE, 0) == 1) return;
            int current = getInt(context, KEY_MODE, UNSET);
            putInt(context, KEY_RESTORE, current);
            putInt(context, KEY_HAS_RESTORE, 1);
            AliveLog.i(TAG, "aod mode captured=" + current);
        } catch (Throwable error) {
            AliveLog.w(TAG, "aod mode capture failed", error);
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
                    int stepIndex = misses[0] + ours[0];
                    if (alive(context)) {
                        if (misses[0] != 0) {
                            AliveLog.i(TAG, "aod restore cancelled after " + misses[0] + " miss");
                            finishRestoreProbe();
                            return;
                        }
                        ours[0]++;
                        if (ours[0] >= RESTORE_PROBE_MS.length) {
                            AliveLog.i(TAG, "aod restore cancelled: wallpaper is still ours");
                            finishRestoreProbe();
                            return;
                        }
                    } else {
                        misses[0]++;
                        if (misses[0] >= RESTORE_PROBE_MS.length) {
                            AliveLog.i(TAG, "aod foreign confirmed x" + misses[0]);
                            restore(context);
                            finishRestoreProbe();
                            return;
                        }
                    }
                    long delay = RESTORE_PROBE_MS[Math.min(stepIndex, RESTORE_PROBE_MS.length - 1)];
                    handler.postDelayed(step[0], delay);
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
     * system_server 侧恢复桌面槽。开机首次解锁时锁屏槽可能仍是我们的，而系统槽已经
     * 被 WPMS 换成 MIUI 静态壁纸；因此这里只查当前/system 槽，绝不复用 lockShowsOurs()。
     */
    /**
     * 用户应用了**系统壁纸**时，把壁纸组件从我们的动态壁纸交还给 MIUI。
     *
     * <p>为什么需要：MIUI 的编辑页/个性化页"应用系统壁纸"走的是
     * {@code WallpaperManagerService.setWallpaper(位图)}，它只替换静态壁纸图，
     * **不会清掉动态壁纸组件**。我们的 CleanWallpaper 还在，桌面就继续画我们的画面，
     * 用户看到的就是"应用了系统壁纸却还是项目壁纸"。</p>
     *
     * <p>只在 setWallpaper / clearWallpaper 这两种"位图应用"来源上动手：
     * 我们自己切换场景走的是 setWallpaperComponentWithFlags，不会被误伤。</p>
     */
    static void releaseWhenSystemWallpaperApplied(final Context context, final String source) {
        if (context == null) return;
        if (!"setWallpaper".equals(source) && !"clearWallpaper".equals(source)) return;
        try {
            WallpaperManager manager = WallpaperManager.getInstance(context);
            if (!isCleanWallpaper(manager.getWallpaperInfo())) return;
            // 交还动作要先记账：紧跟着的 bindWallpaperDescriptionLocked 会把
            // "壁纸变成系统静态图"当成异常，开机 5 分钟内还会把我们的壁纸恢复回去。
            releaseAtMs = android.os.SystemClock.elapsedRealtime();
            // MIUI 的静态壁纸组件；拿不到就交给系统默认（null）。
            ComponentName target = null;
            for (String candidate : new String[]{
                    "com.miui.miwallpaper/com.miui.miwallpaper.wallpaperservice.ImageWallpaper",
                    "com.miui.miwallpaper/com.miui.miwallpaper.MiWallpaper"}) {
                ComponentName parsed = ComponentName.unflattenFromString(candidate);
                if (parsed == null) continue;
                try {
                    context.getPackageManager().getServiceInfo(parsed, 0);
                    target = parsed;
                    break;
                } catch (Throwable ignored) {
                }
            }
            try {
                Method method = WallpaperManager.class.getMethod(
                        "setWallpaperComponent", ComponentName.class);
                method.invoke(manager, target);
                AliveLog.i(HOME_TAG, "release wallpaper to system source=" + source
                        + " target=" + target);
            } catch (Throwable error) {
                AliveLog.w(HOME_TAG, "release wallpaper failed source=" + source, error);
            }
        } catch (Throwable error) {
            AliveLog.w(HOME_TAG, "release wallpaper check failed source=" + source, error);
        }
    }

    static void restoreHomeWhenForeign(final Context context, final String source) {
        if (context == null) return;
        // 用户刚在编辑页点了「应用」（把壁纸交还系统），这时候"壁纸不是我们的"是他要的结果，
        // 不能按"重启后壁纸回落"那条逻辑抢回来。
        if (releaseInFlight()) {
            AliveLog.i(HOME_TAG, "home wallpaper restore skipped (release in flight) source=" + source);
            return;
        }
        synchronized (HOME_RESTORE_LOCK) {
            if (homeRestoreInFlight) {
                AliveLog.i(HOME_TAG, "home wallpaper restore coalesced source=" + source);
                return;
            }
            homeRestoreInFlight = true;
        }
        final Handler handler = new Handler(Looper.getMainLooper());
        final int[] attempt = new int[]{0};
        final Runnable[] step = new Runnable[1];
        step[0] = new Runnable() {
            @Override
            public void run() {
                try {
                    WallpaperManager manager = WallpaperManager.getInstance(context);
                    WallpaperInfo info = manager.getWallpaperInfo();
                    AliveLog.i(HOME_TAG, "home wallpaper probe source=" + source
                            + " attempt=" + (attempt[0] + 1) + " component="
                            + wallpaperComponent(info));
                    if (isCleanWallpaper(info)) {
                        AliveLog.i(HOME_TAG, "home wallpaper restore complete: already ours source=" + source);
                        finishHomeRestoreProbe();
                        return;
                    }
                    if (attempt[0] == 0 || attempt[0] + 1 == HOME_PROBE_MS.length) {
                        try {
                            // @hide in the SDK android.jar; system_server has the required permission.
                            Method method = WallpaperManager.class.getMethod(
                                    "setWallpaperComponent", ComponentName.class);
                            method.invoke(manager, CLEAN_WALLPAPER);
                            AliveLog.i(HOME_TAG, "home wallpaper restore requested source=" + source
                                    + " attempt=" + (attempt[0] + 1));
                        } catch (Throwable error) {
                            AliveLog.w(HOME_TAG, "home wallpaper restore request failed source=" + source, error);
                        }
                    }
                    attempt[0]++;
                    if (attempt[0] >= HOME_PROBE_MS.length) {
                        WallpaperInfo after = manager.getWallpaperInfo();
                        if (isCleanWallpaper(after)) {
                            AliveLog.i(HOME_TAG, "home wallpaper restore complete source=" + source);
                        } else {
                            AliveLog.w(HOME_TAG, "home wallpaper restore exhausted source=" + source
                                    + " component=" + wallpaperComponent(after));
                        }
                        finishHomeRestoreProbe();
                        return;
                    }
                    handler.postDelayed(step[0], HOME_PROBE_MS[attempt[0] - 1]);
                } catch (Throwable error) {
                    AliveLog.w(HOME_TAG, "home wallpaper restore probe failed source=" + source, error);
                    finishHomeRestoreProbe();
                }
            }
        };
        handler.post(step[0]);
    }

    private static void finishHomeRestoreProbe() {
        synchronized (HOME_RESTORE_LOCK) {
            homeRestoreInFlight = false;
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

    /**
     * 只清掉本模块自己的旁路键（`alive_aod_*`），**绝不写 `full_screen_aod_on`**。
     *
     * <p>2026-11-17 二次修：上一版为「历史遗留的 1」保留了写回分支，结果只要探针判定一次
     * 「壁纸不是我们的」（把确认加速到 150/350ms 后，壁纸重绑的瞬间很容易误判），就会把捕获到的
     * 0 写回，用户刚选的「和锁屏样式一致」再次被清掉。这个键一律不写。
     *
     * <p>2026-11-17 修：旧实现会把捕获时的旧值写回去。用户的 `alive_aod_restore_value` 是 0，
     * 于是之后每次 restore 都把 `full_screen_aod_on` 覆盖成 0——用户在设置里选「和锁屏样式一致」
     * 也会被改回「自定义」，全屏 AOD 不再生效，息屏时壁纸层（动画）就整个消失。这个键属于用户，
     * 本模块只读不写。</p>
     */
    static void restore(Context context) {
        try {
            putString(context, KEY_RESTORE, null);
            putString(context, KEY_HAS_RESTORE, null);
            putString(context, KEY_ACTIVE, null);
            AliveLog.i(TAG, "aod bookkeeping cleared; full_screen_aod_on left to the user");
        } catch (Throwable error) {
            AliveLog.w(TAG, "aod mode restore failed", error);
        }
    }

}
