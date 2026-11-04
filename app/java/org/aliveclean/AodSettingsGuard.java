package org.aliveclean;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * com.miui.aod 的「息屏」页把「息屏样式」做成 SettingsView 里的一个 Spinner：
 *   R.id.aod_resting_screen_style      整行容器
 *   R.id.aod_resting_screen_choose     Spinner（值文案就是它当前选中的 item）
 * 点击整行 → mAodSpinner.performClick() 弹出 Spinner 下拉 → onItemSelected() 调
 * Utils.setFsAodStyle(context, position) 写 full_screen_aod_on。
 *
 * 模块壁纸生效期间这里做三件事：值文案换成 HyperAlive 样式、吞掉点击、
 * 把 Utils.setFsAodStyle 的写入强制成 1。
 */
public final class AodSettingsGuard {
    private static final String TAG = "HyperAliveHooks";
    private static final String LABEL = "HyperAlive 样式";
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

        applyLabel(context, spinner);
        lock(row, spinner);
    }

    private static int id(Context context, String name) {
        return context.getResources().getIdentifier(name, "id", "com.miui.aod");
    }

    /** 两个 item：0 保持系统原文案，1 换成 HyperAlive 样式（值文案即选中项）。 */
    private static void applyLabel(Context context, Spinner spinner) {
        SpinnerAdapter current = spinner.getAdapter();
        if (current != null && current.getCount() >= 2
                && LABEL.contentEquals(String.valueOf(current.getItem(1)))) {
            if (spinner.getSelectedItemPosition() != 1) spinner.setSelection(1, false);
            return;
        }
        int itemLayout = context.getResources().getIdentifier(
                "miuix_appcompat_simple_spinner_layout_integrated", "layout", "com.miui.aod");
        int dropLayout = context.getResources().getIdentifier(
                "miuix_appcompat_simple_spinner_dropdown_item", "layout", "com.miui.aod");
        if (itemLayout == 0) itemLayout = android.R.layout.simple_spinner_item;
        if (dropLayout == 0) dropLayout = android.R.layout.simple_spinner_dropdown_item;

        String original = current != null && current.getCount() > 0
                ? String.valueOf(current.getItem(0)) : "自定义";
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(context, itemLayout, android.R.id.text1);
        adapter.setDropDownViewResource(dropLayout);
        adapter.add(original);
        adapter.add(LABEL);
        spinner.setAdapter(adapter);
        spinner.setSelection(1, false);
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
