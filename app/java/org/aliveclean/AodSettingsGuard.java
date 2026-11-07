package org.aliveclean;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public final class AodSettingsGuard {
    private static final String TAG = "AliveHSHooks";
    private static final String LABEL = "AliveHS 样式";
    private static final String VIEW = "com.miui.aod.settings.SettingsView";

    private AodSettingsGuard() {}

    public static void install(ClassLoader loader) {
        Class<?> view = Xp.findClassIfExists(VIEW, loader);
        if (view == null) {
            Log.w(TAG, "aod SettingsView missing");
            return;
        }
        hook(view, "onFinishInflate");
        hook(view, "updateState");
        hook(view, "updateSpinnerState");

        Class<?> utils = Xp.findClassIfExists("com.miui.aod.Utils", loader);
        if (utils == null) return;
        try {
            Xp.findAndHookMethod(utils, "setFsAodStyle", Context.class, int.class,
                    new Xp.MethodHook() {
                        @Override
                        protected void beforeHookedMethod(Xp.MethodHookParam param) {
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
            Xp.findAndHookMethod(view, method, new Xp.MethodHook() {
                @Override
                protected void afterHookedMethod(Xp.MethodHookParam param) {
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
