package org.aliveclean;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * Adds a fourth display mode, "HyperAlive 样式", to MIUI's AOD display-mode row.
 *
 * Design rules (see REQUIREMENTS.md section 7):
 *  - never touch isSupportFsAod()/SUPPORT_FULL_AOD, never touch another module's state
 *  - the option is purely additive: we only append our own value to the choice list
 *  - when it is selected we write MIUI's own aod_show_style=3 (full-screen AOD) and remember
 *    our choice in a private key of our own; every native choice clears that key
 *  - reads of Utils.getShowStyle() are NOT intercepted, so rendering behaves exactly like
 *    the full-screen AOD path MIUI itself uses
 */
final class AliveAodStyle {
    private static final String SCREEN = "com.miui.aod.gemini.AodDisplayModeScreen";
    private static final String UTILS = "com.miui.aod.Utils";
    private static final String RESPONSE = "com.google.android.apps.nbu.paisa.inapp.schema.devicestate.v1.SetDeviceStateItemResponse";

    /** Our sentinel in the choice list. Never written to aod_show_style. */
    private static final int OUR_STYLE = 4;
    private static final String OUR_STYLE_TEXT = "4";

    private static final String KEY_OURS = "alive_aod_style";
    private static final String KEY_SHOW_STYLE = "aod_show_style";
    private static final String KEY_FULL_AOD = "full_screen_aod_on";

    static void install(ClassLoader loader) {
        Class<?> screen = XposedHelpers.findClassIfExists(SCREEN, loader);
        if (screen == null) { Trace.log("T20", "display mode screen missing"); return; }

        XposedHelpers.findAndHookMethod(screen, "getAvailableShowStyles", Context.class, new XC_MethodHook() {
            @Override protected void afterHookedMethod(MethodHookParam call) {
                Object result = call.getResult();
                if (!(result instanceof List)) return;
                List<Integer> list = new ArrayList<>((List<Integer>) result);
                if (!list.contains(OUR_STYLE)) list.add(OUR_STYLE);
                call.setResult(list);
                Trace.log("T20", "styles=" + list);
            }
        });

        XposedHelpers.findAndHookMethod(screen, "aodDisplayItemsStyle", Context.class, new XC_MethodHook() {
            @Override protected void beforeHookedMethod(MethodHookParam call) {
                Context context = (Context) call.args[0];
                if (isOurs(context)) call.setResult(OUR_STYLE_TEXT);
            }
        });

        XposedHelpers.findAndHookMethod(screen, "aodDisplayItemsSet", Context.class, String.class, new XC_MethodHook() {
            @Override protected void beforeHookedMethod(MethodHookParam call) {
                if (!OUR_STYLE_TEXT.equals(call.args[1])) return;
                Context context = (Context) call.args[0];
                ContentResolver cr = context.getContentResolver();
                Settings.Secure.putInt(cr, KEY_SHOW_STYLE, 3);
                Settings.Secure.putInt(cr, KEY_FULL_AOD, 1);
                Settings.Secure.putInt(cr, KEY_OURS, 1);
                Trace.log("T21", "picked HyperAlive style -> aod_show_style=3 full_aod=1");
                call.setResult(new Pair<>(Boolean.TRUE, success(context)));
            }
        });

        Class<?> utils = XposedHelpers.findClassIfExists(UTILS, loader);
        if (utils != null) {
            XposedHelpers.findAndHookMethod(utils, "setShowStyle", Context.class, int.class, new XC_MethodHook() {
                @Override protected void beforeHookedMethod(MethodHookParam call) {
                    Context context = (Context) call.args[0];
                    int value = (Integer) call.args[1];
                    if (value == OUR_STYLE) return;
                    Settings.Secure.putInt(context.getContentResolver(), KEY_OURS, 0);
                    Trace.log("T22", "native style chosen " + value + ", our flag cleared");
                }
            });
        }
        Trace.log("T20", "display mode hooks installed");
    }

    private static boolean isOurs(Context context) {
        try {
            return Settings.Secure.getInt(context.getContentResolver(), KEY_OURS, 0) == 1;
        } catch (Throwable error) {
            return false;
        }
    }

    /** Builds SetDeviceStateItemResponse(true, "4", null) without a compile-time dependency. */
    private static Object success(Context context) {
        try {
            Class<?> type = Class.forName("com.google.android.appfunctions.schema.devicestate.v1.SetDeviceStateItemResponse",
                    false, context.getClassLoader());
            return type.getConstructor(boolean.class, String.class, String.class)
                    .newInstance(true, OUR_STYLE_TEXT, null);
        } catch (Throwable error) {
            Trace.log("T21", "response build failed", error);
            return null;
        }
    }

    private AliveAodStyle() {
    }
}
