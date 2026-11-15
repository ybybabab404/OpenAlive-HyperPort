package org.aliveclean;

import android.app.Application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Recording double for the Xposed facade.  Product code only needs the class lookup,
 * the hook registration and the hook parameter object; tests fire callbacks directly.
 */
public final class Xp {
    public static final Map<String, Class<?>> CLASSES = new LinkedHashMap<>();
    public static final Map<String, List<MethodHook>> HOOKS = new LinkedHashMap<>();
    public static Application application;
    public static Object objectFieldValue;

    private Xp() {}

    public static Class<?> findClassIfExists(String name, ClassLoader loader) {
        return CLASSES.get(name);
    }

    public static Set<Unhook> hookAllMethods(Class<?> clazz, String methodName, MethodHook callback) {
        HOOKS.computeIfAbsent(clazz.getName() + "#" + methodName, key -> new ArrayList<>())
                .add(callback);
        return Collections.singleton(new Unhook());
    }

    public static Application currentApplication() { return application; }

    public static Object getObjectField(Object object, String name) { return objectFieldValue; }

    public abstract static class MethodHook {
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        }

        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        }
    }

    public static final class MethodHookParam {
        public Object thisObject;
        public Object[] args = new Object[0];
        private Object result;

        public Object getResult() { return result; }

        public void setResult(Object value) { result = value; }
    }

    public static final class Unhook {
    }
}
