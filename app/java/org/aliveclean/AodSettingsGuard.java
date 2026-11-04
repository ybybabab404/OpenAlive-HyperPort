package org.aliveclean;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * com.miui.aod 的「息屏」页把「息屏样式」做成 SettingsView 里的一个 Spinner：
 *   R.id.aod_resting_screen_style      整行容器
 *   R.id.aod_resting_screen_choose     Spinner（值文案就是它当前选中的 item）
 * 点击整行 → mAodSpinner.performClick() 弹出 Spinner 下拉 → onItemSelected() 调
 * Utils.setFsAodStyle(context, position) 写 full_screen_aod_on。
 *
 * 模块壁纸生效期间这里做两件事：吞掉点击让它改不动、把 Utils.setFsAodStyle 的写入强制成 1。
 * 值文案保持系统原文案（也就是「和锁屏样式一致」），不替换成自定义标签。
 */
public final class AodSettingsGuard {
    private static final String TAG = "HyperAliveHooks";
    private static final String VIEW = "com.miui.aod.settings.SettingsView";

    private AodSettingsGuard() {}

    public static void install(ClassLoader loader) {
        Class<?> view = XposedHelpers.findClassIfExists(VIEW, loader);
        if (view == null) {
            Log.w(TAG, "aod SettingsView missing");
            return;
        }
        hook(view, "onFinishInflate");
        hook(view, "updateState");
        hook(view, "updateSpinnerState");

        Class<?> utils = XposedHelpers.findClassIfExists("com.miui.aod.Utils", loader);
        if (utils == null) return;
        try {
            XposedHelpers.findAndHookMethod(utils, "setFsAodStyle", Context.class, int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            if (!AodWallpaperMode.alive((Context) param.args[0])) return;
                            param.args[1] = 1;
                        }
                    });
        } catch (Throwable error) {
            Log.w(TAG, "aod setFsAodStyle hook failed", error);
        }
    }

    private static void hook(Class<?> view, String method) {
        try {
            XposedHelpers.findAndHookMethod(view, method, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    try {
                        bind((View) param.thisObject);
                    } catch (Throwable error) {
                        Log.w(TAG, "aod bind failed", error);
                    }
                }
            });
        } catch (Throwable error) {
            Log.w(TAG, "aod " + method + " hook failed", error);
        }
    }

    private static void bind(View root) {
        Context context = root.getContext();
        if (context == null) return;
        if (!AodWallpaperMode.alive(context)) {
            // 兜底：system_server 的绑定钩子没抓到（比如模块刚升级）时，页面打开也能恢复。
            AodWallpaperMode.restore(context);
            return;
        }
        int rowId = id(context, "aod_resting_screen_style");
        int spinnerId = id(context, "aod_resting_screen_choose");
        if (rowId == 0 || spinnerId == 0) return;
        View row = root.findViewById(rowId);
        View value = root.findViewById(spinnerId);
        if (!(value instanceof Spinner)) return;
        Spinner spinner = (Spinner) value;

        lock(row, spinner);
    }

    private static int id(Context context, String name) {
        return context.getResources().getIdentifier(name, "id", "com.miui.aod");
    }

    /** 吞掉整行和 Spinner 的触摸，点击不会弹下拉，也不会走到 onItemSelected。 */
    private static void lock(View row, Spinner spinner) {
        View.OnTouchListener swallow = (view, event) -> true;
        if (row != null) {
            row.setOnClickListener(null);
            row.setClickable(false);
            row.setOnTouchListener(swallow);
        }
        spinner.setOnTouchListener(swallow);
        spinner.setClickable(false);
        spinner.setLongClickable(false);
        spinner.setFocusable(false);
    }
}
