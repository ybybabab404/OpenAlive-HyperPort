package org.aliveclean;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

public final class AodPreviewHider {
    private static final String TAG = "AliveHSHooks";

    private static final int MAX_FRAMES = 180;

    private AodPreviewHider() {}

    public static void install(ClassLoader loader) {
        Class<?> activity = Xp.findClassIfExists(
                "com.miui.aod.settings.AodStyleCategoriesActivity", loader);
        if (activity == null) {
            AliveLog.w(TAG, "aod preview hide: activity missing");
            return;
        }
        try {
            Xp.MethodHook hook = new Xp.MethodHook() {
                @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                    if (!(param.thisObject instanceof Activity)) return;
                    watch((Activity) param.thisObject);
                }
            };
            Xp.findAndHookMethod(activity, "onCreate", Bundle.class, hook);
            Xp.findAndHookMethod(activity, "onResume", hook);
            AliveLog.i(TAG, "aod preview hide hook ok");
        } catch (Throwable error) {
            AliveLog.w(TAG, "aod preview hide hook failed", error);
        }
    }

    private static void watch(Activity page) {

        if (!AodWallpaperMode.alive(page)) return;
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
                        AliveLog.i(TAG, "aod preview hidden block="
                                + block.getClass().getSimpleName() + " frame=" + frames);

                        return false;
                    }
                });
    }

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
