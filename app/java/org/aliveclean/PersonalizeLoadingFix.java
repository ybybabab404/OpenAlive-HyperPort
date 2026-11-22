package org.aliveclean;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 「系统个性化」页重进 / 应用之后整页停在「加载中」。
 *
 * <p>根因（反编译 + 真机日志）：宿主的 {@code SettingsTemplateView.restoreTemplate()}
 * 被混淆成 {@code xm(Function0)}，它的逻辑是
 * <pre>
 *   if (this.bv) { 显示 loading; return; }   // bv = "还在忙" 的状态位
 *   收起 loading; 播放应用动画;
 * </pre>
 * 而"重进页面"和"应用完成后重算列表"这两条路径宿主自己都不会再调它，
 * bv 又停在 true，于是页面永远显示 loading。</p>
 *
 * <p>这里在两处替宿主调用一次：页面 onResume 之后、以及模板就绪回调
 * {@code onTemplateReady} 之后。调用前把 bv 置 false（等价于"不忙了"）。</p>
 */
final class PersonalizeLoadingFix {
    private static final String TAG = AliveLog.TAG;
    private static final String VIEW = "com.miui.keyguard.editor.settings.SettingsTemplateView";
    private static final Handler MAIN = new Handler(Looper.getMainLooper());

    private PersonalizeLoadingFix() {}

    static void install(ClassLoader loader) {
        Class<?> view = Xp.findClassIfExists(VIEW, loader);
        if (view == null) {
            AliveLog.w(TAG, "personalize loading fix: " + VIEW + " missing");
            return;
        }
        int hooks = 0;
        try {
            Xp.hookAllMethods(view, "onTemplateReady", new Xp.MethodHook() {
                @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                    Object target = param.thisObject;
                    MAIN.postDelayed(() -> restore(target, "onTemplateReady"), 120);
                }
            });
            hooks++;
        } catch (Throwable error) {
            AliveLog.w(TAG, "personalize loading fix: onTemplateReady hook failed", error);
        }
        try {
            Class<?> activity = Xp.findClassIfExists(
                    "com.android.thememanager.settings.personalize.activity.PersonalizeActivity", loader);
            if (activity != null) {
                Xp.hookAllMethods(activity, "onResume", new Xp.MethodHook() {
                    @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                        Object activityObject = param.thisObject;
                        MAIN.postDelayed(() -> {
                            Object found = findTemplateView(activityObject);
                            if (found != null) restore(found, "onResume");
                        }, 900);
                    }
                });
                hooks++;
            }
        } catch (Throwable error) {
            AliveLog.w(TAG, "personalize loading fix: onResume hook failed", error);
        }
        AliveLog.i(TAG, "personalize loading fix hooks=" + hooks);
    }

    private static Object findTemplateView(Object activity) {
        if (!(activity instanceof Activity)) return null;
        View root = ((Activity) activity).getWindow() == null
                ? null : ((Activity) activity).getWindow().getDecorView();
        return root == null ? null : find(root);
    }

    private static Object find(View view) {
        if (view.getClass().getName().equals(VIEW)) return view;
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                Object found = find(group.getChildAt(i));
                if (found != null) return found;
            }
        }
        return null;
    }

    private static void restore(Object target, String source) {
        if (!(target instanceof View)) return;
        View view = (View) target;
        try {
            // bv: "还在忙"的状态位。为 true 时 restoreTemplate 只显示 loading 就返回。
            for (java.lang.reflect.Field field : view.getClass().getDeclaredFields()) {
                if (field.getType() != boolean.class || !field.getName().equals("bv")) continue;
                field.setAccessible(true);
                if (field.getBoolean(view)) field.setBoolean(view, false);
            }
        } catch (Throwable ignored) {
        }
        Method target0 = null;
        for (Method method : view.getClass().getDeclaredMethods()) {
            if (method.getName().equals("restoreTemplate")) { target0 = method; break; }
            if (method.getName().equals("xm") && method.getParameterTypes().length == 1) target0 = method;
        }
        if (target0 == null) {
            AliveLog.w(TAG, "personalize loading fix: restoreTemplate not found (" + source + ")");
            return;
        }
        try {
            target0.setAccessible(true);
            Object[] args = new Object[target0.getParameterTypes().length];
            for (int i = 0; i < args.length; i++) args[i] = noop(target0.getParameterTypes()[i]);
            target0.invoke(view, args);
            AliveLog.i(TAG, "personalize loading fix: restoreTemplate called (" + source + ")");
        } catch (Throwable error) {
            AliveLog.w(TAG, "personalize loading fix: restoreTemplate failed (" + source + ")", error);
        }
    }

    /** Kotlin 的 Function0 之类接口参数：用动态代理给一个不做事、返回 Unit 的实现。 */
    private static Object noop(Class<?> type) {
        if (!type.isInterface()) return primitiveDefault(type);
        try {
            return Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type},
                    new InvocationHandler() {
                        @Override public Object invoke(Object proxy, Method method, Object[] args)
                                throws Throwable {
                            Class<?> ret = method.getReturnType();
                            if (ret == void.class) return null;
                            if (ret == boolean.class) return false;
                            if (ret == int.class) return 0;
                            if (ret == long.class) return 0L;
                            return null;
                        }
                    });
        } catch (Throwable error) {
            return null;
        }
    }

    private static Object primitiveDefault(Class<?> type) {
        if (type == boolean.class) return false;
        if (type == int.class) return 0;
        if (type == long.class) return 0L;
        if (type == float.class) return 0f;
        if (type == double.class) return 0d;
        if (type == char.class) return (char) 0;
        if (type == byte.class) return (byte) 0;
        if (type == short.class) return (short) 0;
        return null;
    }
}
