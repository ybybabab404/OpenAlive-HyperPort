package org.aliveclean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

final class AodFullScreen {
    private static final String TAG = AliveLog.TAG;
    private static final String UTILS = "com.miui.aod.Utils";
    private static final String CONFIGS = "com.miui.utils.configs.MiuiConfigs";
    private static final String INITIALIZER = "com.android.systemui.SystemUIInitializer";
    private static final String FULL_AOD = "com.android.keyguard.fullaod.MiuiFullAodManager";
    private static final String[] FLAGS = {"SUPPORT_FULL_AOD", "INNER_SCREEN_SUPPORT_FULL_AOD"};
    private static final long[] UNSAFE_SLOT = {0L};
    private static volatile ClassLoader loaderRef;
    private static String lastFlags = "";

    private AodFullScreen() {
    }

    static void install(ClassLoader loader) {
        loaderRef = loader;
        int hooks = 0;
        hooks += hookSupport(loader);
        hooks += hookManager(loader);
        hooks += hookInitializer(loader);
        AliveLog.i(TAG, "force full aod: hooks=" + hooks);
        writeFlags("install");
    }

    static void onActivation(android.content.Context context) {
        writeFlags("activation");
    }

    private static int hookSupport(ClassLoader loader) {
        Class<?> clazz = Xp.findClassIfExists(UTILS, loader);
        if (clazz == null) {
            AliveLog.warnOnce(TAG, "full aod support", "force full aod: com.miui.aod.Utils missing", null);
            return 0;
        }
        int count = 0;
        count += hookTrue(clazz, "isSupportFsAod");
        count += hookTrue(clazz, "isInnerScreenSupportFsAod");
        return count;
    }

    private static int hookTrue(Class<?> clazz, String name) {
        try {
            Xp.findAndHookMethod(clazz, name, new Xp.MethodHook() {
                @Override
                protected void beforeHookedMethod(Xp.MethodHookParam param) {
                    param.setResult(Boolean.TRUE);
                }
            });
            return 1;
        } catch (Throwable error) {
            AliveLog.warnOnce(TAG, "full aod hook " + name, "force full aod hook failed: " + name, error);
            return 0;
        }
    }

    private static int hookManager(ClassLoader loader) {
        Class<?> clazz = Xp.findClassIfExists(FULL_AOD, loader);
        if (clazz == null) {
            AliveLog.warnOnce(TAG, "full aod manager", "force full aod: MiuiFullAodManager missing", null);
            return 0;
        }
        try {
            Xp.hookAllConstructors(clazz, new Xp.MethodHook() {
                @Override
                protected void beforeHookedMethod(Xp.MethodHookParam param) {
                    writeFlags("manager");
                }

                @Override
                protected void afterHookedMethod(Xp.MethodHookParam param) {
                    try {
                        Xp.setField(param.thisObject, "isDeviceSupport", Boolean.TRUE);
                        AliveLog.once(TAG, "full aod manager", "force full aod: manager device support");
                    } catch (Throwable error) {
                        AliveLog.warnOnce(TAG, "full aod manager field", "force full aod: manager field failed", error);
                    }
                }
            });
        } catch (Throwable error) {
            AliveLog.warnOnce(TAG, "full aod manager hook", "force full aod: manager hook failed", error);
            return 0;
        }
        return 1;
    }

    private static int hookInitializer(ClassLoader loader) {
        Class<?> clazz = Xp.findClassIfExists(INITIALIZER, loader);
        if (clazz == null) {
            AliveLog.warnOnce(TAG, "full aod initializer", "force full aod: SystemUIInitializer missing", null);
            return 0;
        }
        try {
            Xp.findAndHookMethod(clazz, "init", boolean.class, new Xp.MethodHook() {
                @Override
                protected void afterHookedMethod(Xp.MethodHookParam param) {
                    writeFlags("initializer");
                }
            });
            return 1;
        } catch (Throwable error) {
            AliveLog.warnOnce(TAG, "full aod initializer hook", "force full aod: initializer hook failed", error);
            return 0;
        }
    }

    private static void writeFlags(String reason) {
        ClassLoader loader = loaderRef;
        if (loader == null) return;
        Class<?> clazz = Xp.findClassIfExists(CONFIGS, loader);
        if (clazz == null) {
            AliveLog.warnOnce(TAG, "full aod configs", "force full aod: MiuiConfigs missing", null);
            return;
        }
        StringBuilder report = new StringBuilder();
        boolean ok = true;
        for (String name : FLAGS) {
            Field field = findField(clazz, name);
            if (field == null) {
                ok = false;
                report.append(' ').append(name).append("=missing");
                continue;
            }
            boolean applied = force(clazz, field, true);
            ok &= applied;
            report.append(' ').append(name).append('=').append(applied ? Boolean.TRUE.toString() : "failed");
        }
        String line = "force full aod flags " + reason + ":" + report;
        if (!line.equals(lastFlags)) {
            lastFlags = line;
            if (ok) AliveLog.i(TAG, line);
            else AliveLog.w(TAG, line);
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
            AliveLog.warnOnce(TAG, "full aod unsafe", "force full aod unsafe write failed: " + field.getName(), error);
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
