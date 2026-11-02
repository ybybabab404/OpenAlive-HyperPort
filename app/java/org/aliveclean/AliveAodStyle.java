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
        // The page the user actually sees is the classic preference screen: its spinner takes
        // its labels from AODSettings.getShowStyleEntries(). Appending one label is the whole
        // UI change; the value mapping is handled below.
        Class<?> aodSettings = XposedHelpers.findClassIfExists("com.miui.aod.widget.AODSettings", loader);
        if (aodSettings != null) {
            XposedHelpers.findAndHookMethod(aodSettings, "getShowStyleEntries", Context.class, new XC_MethodHook() {
                @Override protected void afterHookedMethod(MethodHookParam call) {
                    Object result = call.getResult();
                    if (!(result instanceof CharSequence[])) return;
                    CharSequence[] original = (CharSequence[]) result;
                    CharSequence[] extended = new CharSequence[original.length + 1];
                    System.arraycopy(original, 0, extended, 0, original.length);
                    extended[original.length] = "HyperAlive 样式";
                    call.setResult(extended);
                    Trace.log("T23", "style entries -> " + extended.length);
                }
            });
        }
        // Diagnostic: which string array feeds the display-mode dialog.
        try {
            XposedHelpers.findAndHookMethod(android.content.res.Resources.class, "getStringArray", int.class, new XC_MethodHook() {
                @Override protected void afterHookedMethod(MethodHookParam call) {
                    Object result = call.getResult();
                    if (!(result instanceof String[])) return;
                    String[] values = (String[]) result;
                    if (values.length == 0 || values.length > 8) return;
                    StringBuilder joined = new StringBuilder();
                    for (String value : values) joined.append(value).append('|');
                    Trace.log("T24", "res=" + call.args[0] + " -> " + joined);
                }
            });
            XposedHelpers.findAndHookMethod(android.content.res.Resources.class, "getTextArray", int.class, new XC_MethodHook() {
                @Override protected void afterHookedMethod(MethodHookParam call) {
                    Object result = call.getResult();
                    if (!(result instanceof CharSequence[])) return;
                    CharSequence[] values = (CharSequence[]) result;
                    if (values.length == 0 || values.length > 8) return;
                    StringBuilder joined = new StringBuilder();
                    for (CharSequence value : values) joined.append(value).append('|');
                    Trace.log("T25", "res=" + call.args[0] + " -> " + joined);
                }
            });
        } catch (Throwable error) {
            Trace.log("T24", "array probe failed", error);
        }
        // Never replace MIUI's adapter: its item layout carries the font/colour styling.
        // We only append one virtual entry through method-level injection on the instance.
        try {
            Class<?> spinner = XposedHelpers.findClassIfExists("miuix.appcompat.widget.Spinner", loader);
            if (spinner != null) {
                XposedHelpers.findAndHookMethod(spinner, "setAdapter", android.widget.SpinnerAdapter.class, new XC_MethodHook() {
                    @Override protected void afterHookedMethod(MethodHookParam call) {
                        if (extending) return;
                        Object adapter = call.args[0];
                        if (!(adapter instanceof android.widget.ArrayAdapter)) return;
                        android.widget.ArrayAdapter<?> array = (android.widget.ArrayAdapter<?>) adapter;
                        if (array.getCount() != 2) return;
                        if (!"自定义".equals(String.valueOf(array.getItem(0)))) return;
                        if (!"和锁屏样式一致".equals(String.valueOf(array.getItem(1)))) return;
                        // Append into the adapter's own backing list: the adapter, its item
                        // layout, fonts and selection highlight all stay MIUI's.
                        try {
                            java.lang.reflect.Field objectsField = android.widget.ArrayAdapter.class.getDeclaredField("mObjects");
                            objectsField.setAccessible(true);
                            Object backing = objectsField.get(array);
                            if (!(backing instanceof List)) return;
                            @SuppressWarnings("unchecked")
                            List<Object> items = (List<Object>) backing;
                            if (items.contains("HyperAlive 样式")) return;
                            boolean mutable = true;
                            try {
                                items.add("HyperAlive 样式");
                            } catch (UnsupportedOperationException immutable) {
                                mutable = false;
                            }
                            if (!mutable) {
                                List<Object> copy = new ArrayList<>(items);
                                copy.add("HyperAlive 样式");
                                objectsField.set(array, copy);
                            }
                            array.notifyDataSetChanged();
                            styleAdapter = new java.lang.ref.WeakReference<>(array);
                        ensureObserver(array.getContext());
                            Trace.log("T28", "style item appended, count=" + array.getCount());
                        } catch (Throwable failure) {
                            Trace.log("T28", "append failed", failure);
                        }
                    }
                });
                XposedHelpers.findAndHookMethod(spinner, "setOnItemSelectedListener",
                        android.widget.AdapterView.OnItemSelectedListener.class, new XC_MethodHook() {
                    @Override protected void beforeHookedMethod(MethodHookParam call) {
                        Object listener = call.args[0];
                        if (listener == null || listener instanceof OurListener) return;
                        call.args[0] = new OurListener((android.widget.AdapterView.OnItemSelectedListener) listener);
                    }
                });
            }
        } catch (Throwable error) {
            Trace.log("T28", "style spinner hook failed", error);
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

    private static java.lang.ref.WeakReference<android.widget.ArrayAdapter<?>> styleAdapter;
    private static boolean extending;
    private static boolean observerReady;

    /** Registered once, from a context that actually belongs to this process. */
    private static void ensureObserver(final android.content.Context context) {
        if (observerReady || context == null) return;
        observerReady = true;
        try {
            final android.content.ContentResolver resolver = context.getContentResolver();
            resolver.registerContentObserver(
                    android.provider.Settings.Secure.getUriFor(KEY_SHOW_STYLE), false,
                    new android.database.ContentObserver(new android.os.Handler(android.os.Looper.getMainLooper())) {
                        @Override public void onChange(boolean selfChange) {
                            if (System.currentTimeMillis() - lastOwnWrite < 1500) return;
                            int ours = android.provider.Settings.Secure.getInt(resolver, KEY_OURS, 0);
                            if (ours == 0) return;
                            android.provider.Settings.Secure.putInt(resolver, KEY_OURS, 0);
                            Trace.log("T31", "stock style changed, flag cleared");
                        }
                    });
            Trace.log("T20", "style observer registered");
        } catch (Throwable error) {
            Trace.log("T31", "observer failed", error);
        }
    }
    private static volatile long lastOwnWrite;

    /** Delegates native positions, owns the appended one. */
    private static final class OurListener implements android.widget.AdapterView.OnItemSelectedListener {
        private final android.widget.AdapterView.OnItemSelectedListener delegate;

        OurListener(android.widget.AdapterView.OnItemSelectedListener delegate) {
            this.delegate = delegate;
        }

        @Override public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
            if (position == 2) {
                // Let MIUI do its own work for the value it understands ("和锁屏样式一致"),
                // then mark the choice as ours. The delegate sees the native index 1.
                lastOwnWrite = System.currentTimeMillis();
                delegate.onItemSelected(parent, view, 1, id);
                android.content.Context context = parent.getContext();
                android.content.ContentResolver cr = context.getContentResolver();
                android.provider.Settings.Secure.putInt(cr, KEY_SHOW_STYLE, 3);
                android.provider.Settings.Secure.putInt(cr, KEY_FULL_AOD, 1);
                android.provider.Settings.Secure.putInt(cr, KEY_OURS, 1);
                lastOwnWrite = System.currentTimeMillis();
                Trace.log("T29", "picked HyperAlive style: aod_show_style="
                        + android.provider.Settings.Secure.getInt(cr, KEY_SHOW_STYLE, -1)
                        + " ours=" + android.provider.Settings.Secure.getInt(cr, KEY_OURS, -1));
                return;
            }
            delegate.onItemSelected(parent, view, position, id);
            // Choosing any native mode must switch our effect off: the flag is the only
            // thing the renderer consults, so clearing it is the whole state change.
            android.content.ContentResolver resolver = parent.getContext().getContentResolver();
            int previous = android.provider.Settings.Secure.getInt(resolver, KEY_OURS, 0);
            android.provider.Settings.Secure.putInt(resolver, KEY_OURS, 0);
            if (previous != 0) Trace.log("T29", "native mode chosen, our flag cleared");
        }

        @Override public void onNothingSelected(android.widget.AdapterView<?> parent) {
            delegate.onNothingSelected(parent);
        }
    }
}
