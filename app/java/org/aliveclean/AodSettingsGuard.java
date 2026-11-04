package org.aliveclean;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.lang.reflect.Field;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * com.miui.aod settings support.
 *
 * Two independent jobs:
 *  1. Clamp MIUI's out-of-range full-AOD spinner index before inflation (crash hotfix).
 *  2. While HyperAlive owns the full-screen AOD mode, show that choice as
 *     "HyperAlive 样式" and freeze both the spinner and the setter. The label is
 *     cosmetic: the stored system value remains MIUI's own 1 (= "和锁屏一致"),
 *     which is the mode the renderer needs.
 *
 * Evidence for the spinner layout and label source:
 *   SettingsView.udpateFsAodStyleDropView() adds
 *     R.string.aod_resting_screen_define      (index 0, "自定义")
 *     R.string.aod_resting_screen_same_to_lock(index 1, "和锁屏一致")
 *   and writes the selected index with Utils.setFsAodStyle(), SettingsView.java:288-324.
 */
public final class AodSettingsGuard {
    private static final String TAG = "HyperAliveHooks";
    private static final String LABEL = "HyperAlive 样式";

    private AodSettingsGuard() {}

    public static void install(ClassLoader cl) {
        Class<?> view = XposedHelpers.findClassIfExists("com.miui.aod.settings.SettingsView", cl);
        if (view != null) {
            try {
                XposedHelpers.findAndHookMethod(view, "onFinishInflate", new XC_MethodHook() {
                    @Override protected void beforeHookedMethod(MethodHookParam param) {
                        clampAll((View) param.thisObject);
                    }
                });
            } catch (Throwable t) { Log.w(TAG, "aod guard inflate", t); }
            try {
                XposedHelpers.findAndHookMethod(view, "updateAodSpinnerContentDescription", new XC_MethodHook() {
                    @Override protected void beforeHookedMethod(MethodHookParam param) {
                        if (clampAll((View) param.thisObject)) param.setResult(null);
                    }
                });
            } catch (Throwable t) { Log.w(TAG, "aod guard spinner", t); }
            try {
                XposedHelpers.findAndHookMethod(view, "udpateFsAodStyleDropView", new XC_MethodHook() {
                    @Override protected void afterHookedMethod(MethodHookParam param) {
                        decorateLocked((View) param.thisObject);
                    }
                });
            } catch (Throwable t) { Log.w(TAG, "aod guard drop view", t); }
            try {
                XposedHelpers.findAndHookMethod(view, "updateSpinnerState", new XC_MethodHook() {
                    @Override protected void afterHookedMethod(MethodHookParam param) {
                        decorateLocked((View) param.thisObject);
                    }
                });
            } catch (Throwable t) { Log.w(TAG, "aod guard spinner state", t); }
        }
        try {
            AodModeGuard.installAodSettings(cl);
        } catch (Throwable t) { Log.w(TAG, "aod mode settings", t); }
    }

    private static boolean clampAll(View view) {
        boolean fixed = false;
        if (view instanceof Spinner) {
            Spinner spinner = (Spinner) view;
            try {
                int count = spinner.getAdapter() == null ? 0 : spinner.getAdapter().getCount();
                int position = spinner.getSelectedItemPosition();
                if (count > 0 && (position < 0 || position >= count)) {
                    spinner.setSelection(0, false);
                    fixed = true;
                    Log.i(TAG, "aod spinner clamped " + position + " -> 0 (count " + count + ")");
                }
            } catch (Throwable t) { Log.w(TAG, "aod guard clamp", t); }
            return fixed;
        }
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                if (clampAll(group.getChildAt(i))) fixed = true;
            }
        }
        return fixed;
    }

    /** Replaces only the label of MIUI's own second item; never replaces the adapter. */
    private static void decorateLocked(View root) {
        Context context = root == null ? null : root.getContext();
        if (context == null || !AodModeGuard.isLocked(context)) return;
        Spinner spinner = findFullAodSpinner(root);
        if (spinner == null) return;
        try {
            SpinnerAdapter adapter = spinner.getAdapter();
            if (adapter instanceof ArrayAdapter) {
                ArrayAdapter<?> array = (ArrayAdapter<?>) adapter;
                if (array.getCount() >= 2) {
                    Field objects = ArrayAdapter.class.getDeclaredField("mObjects");
                    objects.setAccessible(true);
                    Object backing = objects.get(array);
                    if (backing instanceof List) {
                        @SuppressWarnings("unchecked")
                        List<Object> items = (List<Object>) backing;
                        if (items.size() >= 2 && !LABEL.equals(String.valueOf(items.get(1)))) {
                            items.set(1, LABEL);
                            array.notifyDataSetChanged();
                            Log.i(TAG, "aod mode label -> " + LABEL);
                        }
                    }
                }
            }
            if (spinner.getSelectedItemPosition() != 1) spinner.setSelection(1, false);
            spinner.setEnabled(false);
            ViewGroup parent = spinner.getParent() instanceof ViewGroup ? (ViewGroup) spinner.getParent() : null;
            if (parent != null) parent.setEnabled(false);
            spinner.setContentDescription(LABEL + "，已锁定");
        } catch (Throwable failure) {
            Log.w(TAG, "aod mode label failed", failure);
        }
    }

    private static Spinner findFullAodSpinner(View view) {
        if (view instanceof Spinner) {
            Spinner spinner = (Spinner) view;
            SpinnerAdapter adapter = spinner.getAdapter();
            if (adapter != null && adapter.getCount() >= 2
                    && "自定义".equals(String.valueOf(adapter.getItem(0)))) {
                return spinner;
            }
        }
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                Spinner found = findFullAodSpinner(group.getChildAt(i));
                if (found != null) return found;
            }
        }
        return null;
    }
}
