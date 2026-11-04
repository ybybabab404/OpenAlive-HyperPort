package org.aliveclean;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * com.miui.aod「息屏」页底部的「息屏预览」整块隐藏。
 *
 * 该块是列表里的一个独立条目：一个无名 ViewGroup，内含 TextView R.id.preview_title
 * 与 FrameLayout R.id.template_view（系统 FullAodTemplateView 在里面跑）。
 * 把条目本身设为 GONE 并压到 0 高，页面上只剩上面的设置卡片。
 *
 * 时机：条目是 RecyclerView 在布局阶段才创建的，onCreate 里还找不到，所以只能等布局。
 * 但等布局有两种等法，差别就是会不会闪一下：
 *   post()  → 布局和首帧都画完了才轮到我们，用户会看见预览出现再消失。
 *   onPreDraw → 在首帧送去合成之前动手，折叠完再让这一帧作废重排，全程没有可见帧。
 * 这里用后者。
 */
public final class AodPreviewHider {
    private static final String TAG = "HyperAliveHooks";
    /** 等不到 preview_title 时最多守多少帧，避免一直拦着绘制。 */
    private static final int MAX_FRAMES = 180;

    private AodPreviewHider() {}

    public static void install(ClassLoader loader) {
        Class<?> activity = XposedHelpers.findClassIfExists(
                "com.miui.aod.settings.AodStyleCategoriesActivity", loader);
        if (activity == null) {
            Log.w(TAG, "aod preview hide: activity missing");
            return;
        }
        try {
            XC_MethodHook hook = new XC_MethodHook() {
                @Override protected void afterHookedMethod(MethodHookParam param) {
                    if (!(param.thisObject instanceof Activity)) return;
                    watch((Activity) param.thisObject);
                }
            };
            XposedHelpers.findAndHookMethod(activity, "onCreate", Bundle.class, hook);
            XposedHelpers.findAndHookMethod(activity, "onResume", hook);
            Log.i(TAG, "aod preview hide hook ok");
        } catch (Throwable error) {
            Log.w(TAG, "aod preview hide hook failed", error);
        }
    }

    private static void watch(Activity page) {
        final View decor = page.getWindow().getDecorView();
        decor.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    private int frames;

                    @Override public boolean onPreDraw() {
                        ViewTreeObserver observer = decor.getViewTreeObserver();
                        if (!observer.isAlive()) return true;
                        View block = block(page, decor);
                        if (block == null) {
                            if (++frames > MAX_FRAMES) observer.removeOnPreDrawListener(this);
                            return true;
                        }
                        collapse(block);
                        observer.removeOnPreDrawListener(this);
                        Log.i(TAG, "aod preview hidden block="
                                + block.getClass().getSimpleName() + " frame=" + frames);
                        // 这一帧按折叠前的尺寸画的，作废；下一帧会按新高度重排。
                        return false;
                    }
                });
    }

    /** preview_title 的直接父级就是列表条目；再往上一层是 categories_container（整页列表）。 */
    private static View block(Activity page, View root) {
        int id = page.getResources().getIdentifier("preview_title", "id", "com.miui.aod");
        View title = id == 0 ? null : root.findViewById(id);
        if (title == null) return null;
        return title.getParent() instanceof View ? (View) title.getParent() : title;
    }

    private static void collapse(View view) {
        view.setVisibility(View.GONE);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null) {
            params.height = 0;
            view.setLayoutParams(params);
        }
    }
}
