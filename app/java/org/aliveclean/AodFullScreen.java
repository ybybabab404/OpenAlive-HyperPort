package org.aliveclean;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Makes MIUI's full-AOD path usable while our wallpaper is active, without permanently
 * changing global support flags.
 *
 * <p>There are two independent consumers:</p>
 * <ul>
 *   <li>system_server's {@code MiuiFullAodManager}: it needs {@code isDeviceSupport} and
 *       {@code isWallpaperTypeSupport} to be true, and it reads {@code MiuiConfigs}
 *       directly in its constructor and in a few other places.</li>
 *   <li>{@code com.miui.aod}'s settings app: it needs {@code Utils.isSupportFsAod()} to
 *       be true, otherwise MIUI hides the style row and never creates the spinner that
 *       {@link AodSettingsGuard} decorates.</li>
 * </ul>
 *
 * <p>All writes are conditional on the current wallpaper being ours.  The original
 * {@code MiuiConfigs} values are captured once and restored when the wallpaper is not
 * ours, so another full-AOD module and the native settings page see their own values
 * again after the user switches back to a system wallpaper.</p>
 */
final class AodFullScreen {
    private static final String TAG = AliveLog.TAG;
    private static final String UTILS = "com.miui.aod.Utils";
    private static final String CONFIGS = "com.miui.utils.configs.MiuiConfigs";
    private static final String INITIALIZER = "com.android.systemui.SystemUIInitializer";
    private static final String FULL_AOD = "com.android.keyguard.fullaod.MiuiFullAodManager";
    private static final String[] FLAGS = {"SUPPORT_FULL_AOD", "INNER_SCREEN_SUPPORT_FULL_AOD"};
    private static final long[] UNSAFE_SLOT = {0L};
    private static final Object MANAGER_LOCK = new Object();
    private static final Map<Object, ManagerOriginal> MANAGER_ORIGINAL = new WeakHashMap<>();
    private static final Map<Object, Boolean> DOZE_ORIGINAL = new WeakHashMap<>();
    private static final Object DOZE_LOCK = new Object();
    private static volatile ClassLoader loaderRef;
    private static volatile boolean settingsProcess;
    /**
     * 我们自己调用 MIUI 的 onWallpaperChanged()/updateSettings() 时置位，
     * 让本类的 hook 直接放行，否则会递归。
     */
    private static final ThreadLocal<Boolean> RECONCILING = new ThreadLocal<>();
    private static long lastReconcileAt;
    private static final long RECONCILE_MIN_GAP_MS = 1500L;
    private static final android.os.Handler MAIN =
            new android.os.Handler(android.os.Looper.getMainLooper());
    private static WeakReference<Object> lastManager = new WeakReference<>(null);
    private static WeakReference<Object> lastDozeHost = new WeakReference<>(null);
    private static boolean flagsCaptured;
    private static boolean[] originalFlags;
    private static String lastFlags = "";

    private AodFullScreen() {
    }

    /**
     * @param settingsProcess true when called from com.miui.aod.  The settings app only
     *                        needs the two Utils predicates; it must never touch
     *                        MiuiConfigs or MiuiFullAodManager.
     */
    static void install(ClassLoader loader, boolean settingsProcess) {
        loaderRef = loader;
        AodFullScreen.settingsProcess = settingsProcess;
        int hooks = 0;
        if (settingsProcess) {
            hooks += hookSupport(loader);
        } else {
            hooks += hookManager(loader);
            hooks += hookInitializer(loader);
            hooks += hookWallpaperType(loader);
            hooks += hookDoze(loader);
        }
        AliveLog.i(TAG, "full aod hooks=" + hooks + " process=" + (settingsProcess ? "aod" : "systemui"));
        // The settings process only needs Utils predicates.  Writing MiuiConfigs there
        // would mutate system_server-owned state without having the manager to restore it.
        if (!settingsProcess) {
            syncFlags("install");
            // 开机/重启后补一次核对：上一次会话可能停在"我们的壁纸 + 已改标志"的状态，
            // 现在壁纸已经不是我们就必须交还给 MIUI 自己算。
            MAIN.postDelayed(new Runnable() {
                @Override public void run() {
                    try {
                        Context context = Xp.currentApplication();
                        if (controlsFullAod(context)) {
                            forceManager(lastManager.get(), context);
                        } else {
                            reconcileNative(lastManager.get(), context);
                        }
                    } catch (Throwable error) {
                        AliveLog.w(TAG, "full aod boot reconcile failed", error);
                    }
                }
            }, 2500);
        }
    }

    static void onActivation(Context context) {
        if (!settingsProcess) {
            if (context != null) {
                if (!HyperOsAod.aodEnabled(context)) {
                    // The user turned AOD off.  Do not let activation repair
                    // full_screen_aod_on back to 1; restore our captured value.
                    AodWallpaperMode.restore(context);
                    syncFlags("activation-aod-off", context);
                    forceManager(lastManager.get(), context);
                    Object dozeHost = lastDozeHost.get();
                    if (dozeHost != null) restoreDoze(dozeHost);
                    return;
                }
                AodWallpaperMode.ensureEnabled(context);
                AodWallpaperMode.restoreWhenForeign(context);
            }
            syncFlags("activation", context);
            if (controlsFullAod(context)) {
                forceManager(lastManager.get(), context);
            } else {
                reconcileNative(lastManager.get(), context);
            }
        }
    }

    /**
     * 把 MiuiFullAodManager 交还给 MIUI 自己算，而不是把我们捕获的旧值写回去。
     *
     * <p>历史缺陷（2026-11-15 由反编译源码确认）：壁纸切回系统壁纸时只做
     * {@code restoreManager()}，把当年在"我们的壁纸"下捕获的
     * {@code isWallpaperTypeSupport=true} 又写了回去。MIUI 的
     * {@code updateSettings()} 用这个字段算出
     * {@code r4 = isLockScreenTemplateSupport && isWallpaperTypeSupport && isDeviceSupport}
     * = 1，于是把 {@code full_screen_aod_support=1} 写进 Secure，并且永远不会写
     * {@code full_screen_aod_on=0}。结果：全屏 AOD 在系统壁纸下也"永远受支持"，
     * AOD 样式页被按全屏 AOD 渲染 —— 用户看到的就是"怎么设都是自定义"。</p>
     */
    private static void reconcileNative(Object manager, Context context) {
        if (manager == null) return;
        if (Boolean.TRUE.equals(RECONCILING.get())) return;
        long now = android.os.SystemClock.uptimeMillis();
        synchronized (MANAGER_LOCK) {
            if (now - lastReconcileAt < RECONCILE_MIN_GAP_MS) return;
            lastReconcileAt = now;
        }
        RECONCILING.set(Boolean.TRUE);
        try {
            restoreManager(manager);
            // 让 MIUI 按"当前真实绑定的壁纸"重算 isWallpaperTypeSupport 与两个 Secure 键。
            // 临时压掉 mAodFullScreenEnable，避免 updateSettings 里弹
            // "该壁纸不支持全屏 AOD" 的 toast 打扰用户；算完马上还原。
            boolean aodFullScreen = readBool(manager, "mAodFullScreenEnable");
            setBool(manager, "mAodFullScreenEnable", false);
            Xp.callMethod(manager, "onWallpaperChanged");
            Xp.callMethod(manager, "updateSettings");
            setBool(manager, "mAodFullScreenEnable", aodFullScreen);
            AliveLog.once(TAG, "full aod reconcile", "full aod: handed back to MIUI (native recompute)");
        } catch (Throwable error) {
            AliveLog.w(TAG, "full aod reconcile failed", error);
        } finally {
            RECONCILING.remove();
        }
    }

    private static int hookSupport(ClassLoader loader) {
        Class<?> clazz = Xp.findClassIfExists(UTILS, loader);
        if (clazz == null) {
            AliveLog.warnOnce(TAG, "full aod support", "full aod: com.miui.aod.Utils missing", null);
            return 0;
        }
        int count = 0;
        count += hookAliveTrue(clazz, "isSupportFsAod");
        count += hookAliveTrue(clazz, "isInnerScreenSupportFsAod");
        return count;
    }

    /**
     * Only lies while our wallpaper is active.  When the user switches to a system
     * wallpaper the original method result is used again, so MIUI's own settings page
     * and a third-party full-AOD module keep their own visibility rules.
     */
    private static int hookAliveTrue(Class<?> clazz, String name) {
        try {
            Xp.findAndHookMethod(clazz, name, new Xp.MethodHook() {
                @Override
                protected void beforeHookedMethod(Xp.MethodHookParam param) {
                    Context context = Xp.currentApplication();
                    if (context != null
                            && AodWallpaperMode.alive(context)
                            && HyperOsAod.aodEnabled(context)) {
                        param.setResult(Boolean.TRUE);
                    }
                }
            });
            return 1;
        } catch (Throwable error) {
            AliveLog.warnOnce(TAG, "full aod hook " + name, "full aod hook failed: " + name, error);
            return 0;
        }
    }

    private static int hookManager(ClassLoader loader) {
        Class<?> clazz = Xp.findClassIfExists(FULL_AOD, loader);
        if (clazz == null) {
            AliveLog.warnOnce(TAG, "full aod manager", "full aod: MiuiFullAodManager missing", null);
            return 0;
        }
        try {
            Xp.hookAllConstructors(clazz, new Xp.MethodHook() {
                @Override
                protected void beforeHookedMethod(Xp.MethodHookParam param) {
                    if (Boolean.TRUE.equals(RECONCILING.get())) return;
                    syncFlags("manager", firstContext(param.args));
                }

                @Override
                protected void afterHookedMethod(Xp.MethodHookParam param) {
                    if (Boolean.TRUE.equals(RECONCILING.get())) return;
                    lastManager = new WeakReference<>(param.thisObject);
                    forceManager(param.thisObject, firstContext(param.args));
                }
            });
            return 1;
        } catch (Throwable error) {
            AliveLog.warnOnce(TAG, "full aod manager hook", "full aod: manager hook failed", error);
            return 0;
        }
    }

    private static int hookInitializer(ClassLoader loader) {
        Class<?> clazz = Xp.findClassIfExists(INITIALIZER, loader);
        if (clazz == null) {
            AliveLog.warnOnce(TAG, "full aod initializer", "full aod: SystemUIInitializer missing", null);
            return 0;
        }
        try {
            Xp.findAndHookMethod(clazz, "init", boolean.class, new Xp.MethodHook() {
                @Override
                protected void afterHookedMethod(Xp.MethodHookParam param) {
                    if (Boolean.TRUE.equals(RECONCILING.get())) return;
                    syncFlags("initializer", Xp.currentApplication());
                    forceManager(lastManager.get(), Xp.currentApplication());
                }
            });
            return 1;
        } catch (Throwable error) {
            AliveLog.warnOnce(TAG, "full aod initializer hook", "full aod: initializer hook failed", error);
            return 0;
        }
    }

    /**
     * The manager's cached mFullAodEnable can remain false on the first screen-off
     * after a boot where MiuiConfigs.SUPPORT_FULL_AOD was false.  MiuiDozeService
     * reads DozeServiceHostInjector.mFullAodEnable directly, so re-assert it at the
     * last possible moment as well.  This is intentionally conditional on our
     * wallpaper; foreign wallpapers keep the native value.
     */
    private static int hookDoze(ClassLoader loader) {
        Class<?> clazz = Xp.findClassIfExists("com.android.keyguard.doze.MiuiDozeService", loader);
        if (clazz == null) {
            AliveLog.warnOnce(TAG, "full aod doze", "full aod: MiuiDozeService missing", null);
            return 0;
        }
        try {
            Xp.findAndHookMethod(clazz, "onDreamingStarted", new Xp.MethodHook() {
                @Override
                protected void beforeHookedMethod(Xp.MethodHookParam param) {
                    Context context = contextOf(param.thisObject);
                    Object host = Xp.getObjectField(param.thisObject, "mDozeServiceHostInjector");
                    if (host == null) return;
                    lastDozeHost = new WeakReference<>(host);
                    if (!controlsFullAod(context)) {
                        restoreDoze(host);
                        return;
                    }
                    synchronized (DOZE_LOCK) {
                        if (!DOZE_ORIGINAL.containsKey(host)) {
                            DOZE_ORIGINAL.put(host, readBool(host, "mFullAodEnable"));
                        }
                    }
                    setBool(host, "mFullAodEnable", true);
                }
            });
            return 1;
        } catch (Throwable error) {
            AliveLog.warnOnce(TAG, "full aod doze hook", "full aod doze hook failed", error);
            return 0;
        }
    }

    private static int hookWallpaperType(ClassLoader loader) {
        Class<?> clazz = Xp.findClassIfExists(FULL_AOD, loader);
        if (clazz == null) return 0;
        int count = 0;
        count += hookManagerMethod(clazz, "onWallpaperChanged");
        count += hookManagerMethod(clazz, "updateSettings");
        return count;
    }

    /**
     * onWallpaperChanged computes isWallpaperTypeSupport from the wallpaper component;
     * updateSettings turns that into full_screen_aod_support and may write
     * full_screen_aod_on=0.  Re-assert our values after both, and restore the native
     * values when the wallpaper is no longer ours.
     */
    private static int hookManagerMethod(Class<?> clazz, String name) {
        try {
            return Xp.hookAllMethods(clazz, name, new Xp.MethodHook() {
                @Override
                protected void beforeHookedMethod(Xp.MethodHookParam param) {
                    // reconcileNative() 自己调这两个方法时必须放行，否则递归。
                    if (Boolean.TRUE.equals(RECONCILING.get())) return;
                    Context context = contextOf(param.thisObject);
                    syncFlags(name, context);
                    forceManager(param.thisObject, context);
                }

                @Override
                protected void afterHookedMethod(Xp.MethodHookParam param) {
                    if (Boolean.TRUE.equals(RECONCILING.get())) return;
                    Context context = contextOf(param.thisObject);
                    if (controlsFullAod(context)) {
                        forceManager(param.thisObject, context);
                        // Native updateSettings writes full_screen_aod_on=0 when its
                        // support calculation fails (doodle lock screen, video depth).
                        // Repair the key right after the native write.
                        if ("updateSettings".equals(name)) {
                            AodWallpaperMode.ensureEnabled(context);
                        }
                    } else {
                        // 不要 restoreManager()：那会把"我们的壁纸"时期的
                        // isWallpaperTypeSupport=true 写回去，MIUI 随后就会把
                        // full_screen_aod_support 重新算成 1（见 reconcileNative 注释）。
                        reconcileNative(param.thisObject, context);
                    }
                }
            }).size();
        } catch (Throwable error) {
            AliveLog.warnOnce(TAG, "full aod manager hook " + name, "full aod hook failed: " + name, error);
            return 0;
        }
    }

    private static void forceManager(Object manager, Context context) {
        if (manager == null) return;
        if (context == null) context = Xp.currentApplication();
        if (!controlsFullAod(context)) {
            restoreManager(manager);
            return;
        }
        synchronized (MANAGER_LOCK) {
            if (!MANAGER_ORIGINAL.containsKey(manager)) {
                MANAGER_ORIGINAL.put(manager, new ManagerOriginal(manager));
            }
        }
        setBool(manager, "isDeviceSupport", true);
        setBool(manager, "isWallpaperTypeSupport", true);
        setBool(manager, "isFullAodSupport", true);
        // A boot-time manager can be constructed while MiuiConfigs.SUPPORT_FULL_AOD is
        // false.  In that case its setting observers for doze_always_on are not
        // registered, so mAodEnable stays false even after full_screen_aod_on becomes 1.
        // Force both fields so the first screen-off does not fall back to the
        // custom AOD.  Do not call updateFullAodEnable() from the manager
        // constructor: it re-enters SystemUI's Dagger graph and can deadlock.
        setBool(manager, "mAodEnable", true);
        setBool(manager, "mAodFullScreenEnable", true);
        AliveLog.once(TAG, "full aod manager", "full aod: manager support forced while ours");
    }

    private static void restoreManager(Object manager) {
        if (manager == null) return;
        ManagerOriginal original;
        synchronized (MANAGER_LOCK) {
            original = MANAGER_ORIGINAL.remove(manager);
        }
        if (original != null) original.restore(manager);
    }

    private static void restoreDoze(Object host) {
        Boolean original;
        synchronized (DOZE_LOCK) {
            original = DOZE_ORIGINAL.remove(host);
        }
        if (original != null) setBool(host, "mFullAodEnable", original);
    }

    private static final class ManagerOriginal {
        final boolean deviceSupport;
        final boolean wallpaperTypeSupport;
        final boolean fullAodSupport;
        final boolean aodEnable;
        final boolean aodFullScreenEnable;

        ManagerOriginal(Object manager) {
            deviceSupport = readBool(manager, "isDeviceSupport");
            wallpaperTypeSupport = readBool(manager, "isWallpaperTypeSupport");
            fullAodSupport = readBool(manager, "isFullAodSupport");
            aodEnable = readBool(manager, "mAodEnable");
            aodFullScreenEnable = readBool(manager, "mAodFullScreenEnable");
        }

        void restore(Object manager) {
            setBool(manager, "isDeviceSupport", deviceSupport);
            setBool(manager, "isWallpaperTypeSupport", wallpaperTypeSupport);
            setBool(manager, "isFullAodSupport", fullAodSupport);
            setBool(manager, "mAodEnable", aodEnable);
            setBool(manager, "mAodFullScreenEnable", aodFullScreenEnable);
        }
    }

    private static void setBool(Object target, String field, boolean value) {
        try {
            Xp.setField(target, field, value);
        } catch (Throwable error) {
            AliveLog.warnOnce(TAG, "full aod field " + field, "full aod field failed: " + field, error);
        }
    }

    private static boolean readBool(Object target, String field) {
        try {
            Object value = Xp.getObjectField(target, field);
            return Boolean.TRUE.equals(value);
        } catch (Throwable ignored) {
            return false;
        }
    }

    private static Context contextOf(Object manager) {
        try {
            Object value = Xp.getObjectField(manager, "mContext");
            if (value instanceof Context) return (Context) value;
        } catch (Throwable ignored) {
        }
        return Xp.currentApplication();
    }

    private static Context firstContext(Object[] args) {
        if (args != null) {
            for (Object arg : args) {
                if (arg instanceof Context) return (Context) arg;
            }
        }
        return Xp.currentApplication();
    }

    /**
     * Capture MiuiConfigs once, then drive it to true only while our wallpaper is alive.
     * The settings process never gets here (it has no MiuiConfigs dependency), which is
     * intentional: it must not mutate flags that belong to system_server.
     */
    private static void syncFlags(String reason) {
        syncFlags(reason, Xp.currentApplication());
    }

    private static boolean controlsFullAod(Context context) {
        return context != null
                && AodWallpaperMode.alive(context)
                && HyperOsAod.aodEnabled(context);
    }

    private static void syncFlags(String reason, Context context) {
        ClassLoader loader = loaderRef;
        if (loader == null || context == null) return;
        boolean alive = controlsFullAod(context);
        Class<?> clazz = Xp.findClassIfExists(CONFIGS, loader);
        if (clazz == null) return;
        if (!flagsCaptured) {
            originalFlags = new boolean[FLAGS.length];
            for (int i = 0; i < FLAGS.length; i++) {
                Field field = findField(clazz, FLAGS[i]);
                originalFlags[i] = field != null && read(field);
            }
            flagsCaptured = true;
        }
        StringBuilder report = new StringBuilder();
        for (int i = 0; i < FLAGS.length; i++) {
            Field field = findField(clazz, FLAGS[i]);
            if (field == null) {
                report.append(' ').append(FLAGS[i]).append("=missing");
                continue;
            }
            boolean desired = alive || originalFlags[i];
            boolean applied = force(clazz, field, desired);
            report.append(' ').append(FLAGS[i]).append('=').append(applied ? desired : "failed");
        }
        String line = "full aod flags " + reason + " alive=" + alive + ":" + report;
        if (!line.equals(lastFlags)) {
            lastFlags = line;
            AliveLog.i(TAG, line);
        }
    }

    private static boolean force(Class<?> clazz, Field field, boolean value) {
        if (read(field) == value) return true;
        try {
            field.setAccessible(true);
            field.setBoolean(null, value);
        } catch (Throwable ignored) {
        }
        if (read(field) == value) return true;
        try {
            viaUnsafe(field, value);
        } catch (Throwable error) {
            AliveLog.warnOnce(TAG, "full aod unsafe", "full aod unsafe write failed: " + field.getName(), error);
        }
        return read(field) == value;
    }

    private static void viaUnsafe(Field field, boolean value) throws Throwable {
        Class<?> type = Class.forName("sun.misc.Unsafe");
        Field holder = type.getDeclaredField("theUnsafe");
        holder.setAccessible(true);
        Object unsafe = holder.get(null);
        Method objectFieldOffset = type.getMethod("objectFieldOffset", Field.class);
        Method getInt = type.getMethod("getInt", Object.class, long.class);
        Method putBoolean = type.getMethod("putBoolean", Object.class, long.class, boolean.class);
        long slot = UNSAFE_SLOT[0];
        if (slot == 0L) {
            Field offsetField = Field.class.getDeclaredField("offset");
            offsetField.setAccessible(true);
            slot = (Long) objectFieldOffset.invoke(unsafe, offsetField);
            UNSAFE_SLOT[0] = slot;
        }
        int target = (Integer) getInt.invoke(unsafe, field, slot);
        putBoolean.invoke(unsafe, field.getDeclaringClass(), (long) target, value);
    }

    private static Field findField(Class<?> clazz, String name) {
        Class<?> type = clazz;
        while (type != null) {
            try {
                Field field = type.getDeclaredField(name);
                field.setAccessible(true);
                return field;
            } catch (Throwable ignored) {
                type = type.getSuperclass();
            }
        }
        return null;
    }

    private static boolean read(Field field) {
        try {
            return field.getBoolean(null);
        } catch (Throwable error) {
            return false;
        }
    }
}
