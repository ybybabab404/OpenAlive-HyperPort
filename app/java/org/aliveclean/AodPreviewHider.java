package org.aliveclean;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * com.miui.aod「息屏」页底部的「息屏预览」整块隐藏。
 *
 * 该块是列表里的一个独立条目：一个无名 ViewGroup，内含 TextView R.id.preview_title
 * 与 FrameLayout R.id.template_view（系统 FullAodTemplateView 在里面跑）。
 * 把条目本身设为 GONE 并压到 0 高，页面上只剩上面的设置卡片。
 */
public final class AodPreviewHider {
    private static final String TAG = "HyperAliveHooks";

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
                    final Activity page = (Activity) param.thisObject;
                    page.getWindow().getDecorView().post(() -> hide(page, 8));
                }
            };
            XposedHelpers.findAndHookMethod(activity, "onCreate", Bundle.class, hook);
            XposedHelpers.findAndHookMethod(activity, "onResume", hook);
            Log.i(TAG, "aod preview hide hook ok");
        } catch (Throwable error) {
            Log.w(TAG, "aod preview hide hook failed", error);
        }
    }

    private static void hide(Activity page, int tries) {
        try {
            View root = page.getWindow().getDecorView();
            int id = page.getResources().getIdentifier("preview_title", "id", "com.miui.aod");
            View title = id == 0 ? null : root.findViewById(id);
            if (title == null) {
                if (tries > 0) root.postDelayed(() -> hide(page, tries - 1), 300);
                return;
            }
            // preview_title 的直接父级就是列表里的那个条目 ViewGroup，它同时装着
            // preview_title 和 template_view；再往上一层是 categories_container（整页列表），
            // 折叠它会连带把上面的设置卡片一起藏掉。
            View block = title.getParent() instanceof View ? (View) title.getParent() : title;
            collapse(block);
            Log.i(TAG, "aod preview hidden block=" + block.getClass().getSimpleName()
                    + " hidden=" + (block.getVisibility() == View.GONE));
        } catch (Throwable error) {
            Log.w(TAG, "aod preview hide failed", error);
        }
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
