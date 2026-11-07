package org.aliveclean;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import io.github.libxposed.api.XposedInterface;
import io.github.libxposed.api.XposedInterface.HookHandle;
import io.github.libxposed.api.XposedInterface.Hooker;
import io.github.libxposed.api.XposedInterface.Chain;

public final class Xp {
    private static volatile XposedInterface api;

    private Xp() {
    }

    public static void attach(XposedInterface value) {
        api = value;
    }

    public static void log(int level, String tag, String message, Throwable error) {
        XposedInterface value = api;
        if (value == null) return;
        try {
            if (error == null) value.log(level, tag, message);
            else value.log(level, tag, message, error);
        } catch (Throwable ignored) {
        }
    }

    private static XposedInterface api() {
        XposedInterface value = api;
        if (value == null) throw new IllegalStateException("Xposed API is not attached");
        return value;
    }

    public abstract static class MethodHook {
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        }

        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        }
    }

    public static final class MethodHookParam {
        public Object thisObject;
        public java.lang.reflect.Member method;
        public Object[] args;
        private Object result;
        private boolean resultSet;
        private boolean returnEarly;
        private Throwable throwable;

        public Object getResult() {
            return result;
        }

        public void setResult(Object value) {
            result = value;
            resultSet = true;
            returnEarly = true;
        }

        public Throwable getThrowable() {
            return throwable;
        }

        public boolean hasThrowable() {
            return throwable != null;
        }

        public void setThrowable(Throwable value) {
            throwable = value;
        }
    }

    public static final class Unhook {
        private final HookHandle handle;

        Unhook(HookHandle handle) {
            this.handle = handle;
        }

        public void unhook() {
            handle.unhook();
        }
    }

    private static Unhook install(Executable executable, MethodHook callback) {
        HookHandle handle = api().hook(executable).intercept(new Hooker() {
            @Override
            public Object intercept(Chain chain) throws Throwable {
                MethodHookParam param = new MethodHookParam();
                param.thisObject = chain.getThisObject();
                param.method = chain.getExecutable();
                List<Object> values = chain.getArgs();
                param.args = values == null ? new Object[0] : values.toArray(new Object[0]);
                try {
                    callback.beforeHookedMethod(param);
                } catch (Throwable error) {
                    AliveLog.e(AliveLog.TAG, describe(param.method) + " before failed", error);
                    param.throwable = null;
                }
                if (param.throwable != null) throw param.throwable;
                if (!param.returnEarly) {
                    try {
                        param.result = chain.proceed(param.args);
                        param.resultSet = true;
                    } catch (Throwable error) {
                        param.throwable = error;
                    }
                }
                try {
                    callback.afterHookedMethod(param);
                } catch (Throwable error) {
                    AliveLog.e(AliveLog.TAG, describe(param.method) + " after failed", error);
                    param.throwable = null;
                }
                if (param.throwable != null) throw param.throwable;
                return param.result;
            }
        });
        return new Unhook(handle);
    }

    static String describe(java.lang.reflect.Member member) {
        if (member == null) return "unknown hook";
        return member.getDeclaringClass().getName() + "#" + member.getName();
    }

    public static Class<?> findClass(String name, ClassLoader loader) throws ClassNotFoundException {
        return Class.forName(name, false, loader);
    }

    public static Class<?> findClassIfExists(String name, ClassLoader loader) {
        try {
            return findClass(name, loader);
        } catch (Throwable ignored) {
            return null;
        }
    }

    public static Set<Unhook> hookAllMethods(Class<?> clazz, String methodName, MethodHook callback) {
        Set<Unhook> hooks = new LinkedHashSet<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.getName().equals(methodName) || Modifier.isAbstract(method.getModifiers())) continue;
            try {
                hooks.add(install(method, callback));
            } catch (Throwable ignored) {
            }
        }
        return hooks;
    }

    public static Set<Unhook> hookAllConstructors(Class<?> clazz, MethodHook callback) {
        Set<Unhook> hooks = new LinkedHashSet<>();
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            try {
                hooks.add(install(constructor, callback));
            } catch (Throwable ignored) {
            }
        }
        return hooks;
    }

    public static Unhook hookMethod(java.lang.reflect.Executable executable, MethodHook callback) {
        return install(executable, callback);
    }

    public static Unhook hookMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes, MethodHook callback) {
        return install(findMethodExact(clazz, methodName, parameterTypes), callback);
    }

    public static Unhook findAndHookMethod(Class<?> clazz, String methodName, Object... parameterTypesAndCallback) {
        if (parameterTypesAndCallback.length == 0
                || !(parameterTypesAndCallback[parameterTypesAndCallback.length - 1] instanceof MethodHook)) {
            throw new IllegalArgumentException("last argument must be a MethodHook");
        }
        MethodHook callback = (MethodHook) parameterTypesAndCallback[parameterTypesAndCallback.length - 1];
        Class<?>[] types = new Class<?>[parameterTypesAndCallback.length - 1];
        for (int i = 0; i < types.length; i++) {
            Object value = parameterTypesAndCallback[i];
            if (value instanceof Class<?>) types[i] = (Class<?>) value;
            else if (value instanceof String) {
                try {
                    types[i] = findClass((String) value, clazz.getClassLoader());
                } catch (ClassNotFoundException error) {
                    throw new NoSuchMethodError("cannot resolve " + value);
                }
            } else {
                throw new IllegalArgumentException("parameter type must be Class or String");
            }
        }
        return hookMethod(clazz, methodName, types, callback);
    }

    private static Method findMethodExact(Class<?> clazz, String name, Class<?>[] types) {
        for (Class<?> current = clazz; current != null; current = current.getSuperclass()) {
            for (Method method : current.getDeclaredMethods()) {
                if (!method.getName().equals(name) || method.getParameterCount() != types.length) continue;
                Class<?>[] actual = method.getParameterTypes();
                boolean matches = true;
                for (int i = 0; i < actual.length; i++) {
                    if (!wrap(actual[i]).isAssignableFrom(wrap(types[i]))) {
                        matches = false;
                        break;
                    }
                }
                if (matches) return method;
            }
        }
        StringBuilder signature = new StringBuilder(clazz.getName()).append('#').append(name).append('(');
        for (int i = 0; i < types.length; i++) {
            if (i > 0) signature.append(',');
            signature.append(types[i] == null ? "null" : types[i].getName());
        }
        throw new NoSuchMethodError(signature.append(')').toString());
    }

    private static Class<?> wrap(Class<?> type) {
        if (type == null || !type.isPrimitive()) return type;
        if (type == int.class) return Integer.class;
        if (type == long.class) return Long.class;
        if (type == boolean.class) return Boolean.class;
        if (type == float.class) return Float.class;
        if (type == double.class) return Double.class;
        if (type == short.class) return Short.class;
        if (type == byte.class) return Byte.class;
        if (type == char.class) return Character.class;
        return type;
    }

    public static android.app.Application currentApplication() {
        try {
            Object value = callStaticMethod(Class.forName("android.app.ActivityThread"), "currentApplication");
            if (value instanceof android.app.Application) return (android.app.Application) value;
        } catch (Throwable ignored) {
        }
        return null;
    }

    public static Object getObjectField(Object object, String name) {
        if (object == null) return null;
        for (Class<?> current = object.getClass(); current != null; current = current.getSuperclass()) {
            try {
                Field field = current.getDeclaredField(name);
                field.setAccessible(true);
                return field.get(object);
            } catch (NoSuchFieldException ignored) {
            } catch (Throwable error) {
                throw new IllegalStateException("cannot read field " + name, error);
            }
        }
        return null;
    }

    public static Object getStaticObjectField(Class<?> clazz, String name) {
        for (Class<?> current = clazz; current != null; current = current.getSuperclass()) {
            try {
                Field field = current.getDeclaredField(name);
                field.setAccessible(true);
                return field.get(null);
            } catch (NoSuchFieldException ignored) {
            } catch (Throwable error) {
                throw new IllegalStateException("cannot read static field " + name, error);
            }
        }
        return null;
    }

    public static Object callStaticMethod(Class<?> clazz, String name, Object... args) {
        Method method = findCallable(clazz, name, args, true);
        try {
            method.setAccessible(true);
            return method.invoke(null, args);
        } catch (Throwable error) {
            throw new IllegalStateException("cannot call " + clazz.getName() + "#" + name, unwrap(error));
        }
    }

    public static Object callMethod(Object object, String name, Object... args) {
        if (object == null) throw new IllegalArgumentException("object is null");
        Method method = findCallable(object.getClass(), name, args, false);
        try {
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (Throwable error) {
            throw new IllegalStateException("cannot call " + object.getClass().getName() + "#" + name, unwrap(error));
        }
    }

    private static Method findCallable(Class<?> clazz, String name, Object[] args, boolean staticOnly) {
        List<Method> candidates = new ArrayList<>();
        for (Class<?> current = clazz; current != null; current = current.getSuperclass()) {
            for (Method method : current.getDeclaredMethods()) {
                if (!method.getName().equals(name) || method.getParameterCount() != args.length) continue;
                if (staticOnly && !Modifier.isStatic(method.getModifiers())) continue;
                Class<?>[] types = method.getParameterTypes();
                boolean matches = true;
                for (int i = 0; i < types.length; i++) {
                    if (args[i] == null) {
                        if (types[i].isPrimitive()) {
                            matches = false;
                            break;
                        }
                    } else if (!wrap(types[i]).isAssignableFrom(wrap(args[i].getClass()))) {
                        matches = false;
                        break;
                    }
                }
                if (matches) candidates.add(method);
            }
        }
        if (candidates.isEmpty()) throw new NoSuchMethodError(clazz.getName() + "#" + name);
        return candidates.get(0);
    }

    private static Throwable unwrap(Throwable error) {
        return error.getCause() == null ? error : error.getCause();
    }
}
