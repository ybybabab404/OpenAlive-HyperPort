package org.aliveclean;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * LOCAL HOTFIX, NOT FOR MAINLINE.
 *
 * MIUI's com.miui.aod settings page builds a two-entry spinner when full-screen AOD is reported
 * as supported, but restores its selection from a stored index that can be 2; AdapterView then
 * throws IndexOutOfBounds during onFinishInflate. Nothing is written back: the spinner is only
 * clamped in memory so the page can open.
 */
public final class AodSettingsGuard {
    private static final String TAG = "HyperAliveHooks";
    private AodSettingsGuard() {}

    public static void install(ClassLoader cl) {
        Class<?> view = XposedHelpers.findClassIfExists("com.miui.aod.settings.SettingsView", cl);
        if (view == null) return;
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
}
