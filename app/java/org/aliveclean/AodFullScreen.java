package org.aliveclean;

import android.content.Context;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

final class AodFullScreen {
    private static final String TAG = AliveLog.TAG;
    private static final String UTILS = "com.miui.aod.Utils";
    private static final String CONFIGS = "com.miui.utils.configs.MiuiConfigs";
    private static final String INITIALIZER = "com.android.systemui.SystemUIInitializer";
    private static final String FULL_AOD = "com.android.keyguard.fullaod.MiuiFullAodManager";
    private static final String DOZE_INJECTOR = "com.android.keyguard.injector.DozeServiceHostInjector";
    private static final String[] FLAGS = {"SUPPORT_FULL_AOD", "INNER_SCREEN_SUPPORT_FULL_AOD"};
    private static final long[] UNSAFE_SLOT = {0L};
    private static volatile ClassLoader loaderRef;

    private AodFullScreen() {}

    static void install(ClassLoader loader) {
        loaderRef = loader;
        int hooks = 0;
        hooks += hookTrue(loader, UTILS, "isSupportFsAod");
        hooks += hookTrue(loader, UTILS, "isInnerScreenSupportFsAod");
        hooks += hookTrue(loader, UTILS, "isFsAodSameToLock", Context.class);
        hooks += hookTrue(loader, UTILS, "isCurrentScreenSupportFsAod", Context.class);
        hooks += hookStyle(loader);
        hooks += hookManager(loader);
        hooks += hookDoze(loader);
        hooks += hookInitializer(loader);
        AliveLog.i(TAG, "force full aod: hooks=" + hooks);
        writeFlags("install");
    }

    static void onActivation(Context context) {
        writeFlags("activation");
    }

    private static int hookTrue(ClassLoader loader, String className, String method, Object... types) {
        try {
            Class<?> clazz = Xp.findClassIfExists(className, loader);
            if (clazz == null) return 0;
            Xp.findAndHookMethod(clazz, method, append(types, new Xp.MethodHook() {
                @Override
                protected void beforeHookedMethod(Xp.MethodHookParam param) {
                    param.setResult(Boolean.TRUE);
                }
            }));
            return 1;
        } catch (Throwable error) {
            AliveLog.w(TAG, "force full aod " + simple(className) + "#" + method + " failed", error);
            return 0;
        }
    }

    private static int hookStyle(ClassLoader loader) {
        try {
            Class<?> clazz = Xp.findClassIfExists(UTILS, loader);
            if (clazz == null) return 0;
            Xp.findAndHookMethod(clazz, "getFsAodStyle", Context.class, new Xp.MethodHook() {
                @Override
                protected void beforeHookedMethod(Xp.MethodHookParam param) {
                    param.setResult(1);
                }
            });
            return 1;
        } catch (Throwable error) {
            AliveLog.w(TAG, "force full aod style failed", error);
            return 0;
        }
    }

    private static int hookManager(ClassLoader loader) {
        try {
            Class<?> clazz = Xp.findClassIfExists(FULL_AOD, loader);
            if (clazz == null) return 0;
            Xp.hookAllConstructors(clazz, new Xp.MethodHook() {
                @Override
                protected void beforeHookedMethod(Xp.MethodHookParam param) {
                    writeFlags("manager");
                }

                @Override
                protected void afterHookedMethod(Xp.MethodHookParam param) {
                    try {
                        Xp.setField(param.thisObject, "isDeviceSupport", Boolean.TRUE);
                        AliveLog.i(TAG, "force full aod: manager device support");
                    } catch (Throwable error) {
                        AliveLog.w(TAG, "force full aod manager field failed", error);
                    }
                }
            });
            return 1;
        } catch (Throwable error) {
            AliveLog.w(TAG, "force full aod manager hook failed", error);
            return 0;
        }
    }

    private static int hookDoze(ClassLoader loader) {
        try {
            Class<?> clazz = Xp.findClassIfExists(DOZE_INJECTOR, loader);
            if (clazz == null) return 0;
            Xp.findAndHookMethod(clazz, "isCurrentScreenFullAodEnabled", new Xp.MethodHook() {
                @Override
                protected void beforeHookedMethod(Xp.MethodHookParam param) {
                    param.setResult(Boolean.TRUE);
                }
            });
            return 1;
        } catch (Throwable error) {
            AliveLog.w(TAG, "force full aod doze hook failed", error);
            return 0;
        }
    }

    private static int hookInitializer(ClassLoader loader) {
        try {
            Class<?> clazz = Xp.findClassIfExists(INITIALIZER, loader);
            if (clazz == null) return 0;
            Xp.findAndHookMethod(clazz, "init", boolean.class, new Xp.MethodHook() {
                @Override
                protected void afterHookedMethod(Xp.MethodHookParam param) {
                    writeFlags("initializer");
                }
            });
            return 1;
        } catch (Throwable error) {
            AliveLog.w(TAG, "force full aod initializer hook failed", error);
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
            Boolean value = force(clazz, name);
            report.append(' ').append(name).append('=').append(value);
            ok &= Boolean.TRUE.equals(value);
        }
        if (ok) AliveLog.i(TAG, "force full aod flags " + reason + ":" + report);
        else AliveLog.warnOnce(TAG, "full aod flags", "force full aod flags " + reason + ":" + report, null);
    }

    private static Boolean force(Class<?> clazz, String name) {
        Field field = findField(clazz, name);
        if (field == null) return null;
        if (read(field)) return Boolean.TRUE;
        try {
            field.setAccessible(true);
            field.setBoolean(null, true);
        } catch (Throwable ignored) {
        }
        if (read(field)) return Boolean.TRUE;
        if (viaHelpers(clazz, name) && read(field)) return Boolean.TRUE;
        try {
            viaUnsafe(field);
        } catch (Throwable error) {
            AliveLog.w(TAG, "force full aod unsafe write failed: " + name, error);
        }
        return read(field);
    }

    private static boolean viaHelpers(Class<?> clazz, String name) {
        try {
            ClassLoader loader = clazz.getClassLoader();
            Class<?> helpers = Class.forName("de.robv.android.xposed.XposedHelpers", false, loader);
            Method setter = helpers.getMethod("setStaticBooleanField", Class.class, String.class, boolean.class);
            setter.invoke(null, clazz, name, true);
            return true;
        } catch (Throwable error) {
            AliveLog.w(TAG, "force full aod helpers unavailable: " + name, error);
            return false;
        }
    }

    private static void viaUnsafe(Field field) throws Throwable {
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
        putBoolean.invoke(unsafe, field.getDeclaringClass(), (long) target, Boolean.TRUE);
    }

    private static boolean read(Field field) {
        try {
            field.setAccessible(true);
            return field.getBoolean(null);
        } catch (Throwable error) {
            return false;
        }
    }

    private static Field findField(Class<?> clazz, String name) {
        for (Class<?> type = clazz; type != null; type = type.getSuperclass()) {
            try {
                return type.getDeclaredField(name);
            } catch (Throwable ignored) {
            }
        }
        return null;
    }

    private static Object[] append(Object[] types, Object hook) {
        Object[] all = new Object[types.length + 1];
        System.arraycopy(types, 0, all, 0, types.length);
        all[types.length] = hook;
        return all;
    }

    private static String simple(String className) {
        int dot = className.lastIndexOf('.');
        return dot < 0 ? className : className.substring(dot + 1);
    }
}
