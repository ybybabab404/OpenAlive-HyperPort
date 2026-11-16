package org.aliveclean;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Replaces the MIUI full-AOD style row with a three-item list while our wallpaper is active:
 * native item 0, native item 1, then "AliveHS 样式". The native adapter is never discarded:
 * it is wrapped and restored when the wallpaper stops being ours.
 *
 * <p>The previous implementation replaced the adapter with a new two-item ArrayAdapter and
 * returned early when the wallpaper changed. If the AOD settings page stayed open, the
 * AliveHS item and the disabled row survived the wallpaper switch. It also called
 * {@link AodWallpaperMode#restore(Context)} from the UI process, which normally has no
 * WRITE_SECURE_SETTINGS permission; that made the visible state and the actual setting
 * disagree. Restoration is now done by system_server, this class only restores the view.</p>
 */
public final class AodSettingsGuard {
    private static final String TAG = "AliveHSHooks";
    private static final String LABEL = "AliveHS 样式";
    private static final String VIEW = "com.miui.aod.settings.SettingsView";
    private static final Object LABEL_TAG = new Object();
    private static final Map<View, Saved> SAVED = new WeakHashMap<>();

    private AodSettingsGuard() {}

    public static void install(ClassLoader loader) {
        Class<?> view = Xp.findClassIfExists(VIEW, loader);
        if (view == null) {
            AliveLog.w(TAG, "aod SettingsView missing");
            return;
        }
        hookBeforeInflate(view);
        hookSpinnerDescription(view);
        hookAfter(view, "onFinishInflate");
        hookAfter(view, "updateState");
        hookAfter(view, "updateSpinnerState");

        Class<?> utils = Xp.findClassIfExists("com.miui.aod.Utils", loader);
        if (utils == null) return;
        try {
            Xp.findAndHookMethod(utils, "setFsAodStyle", Context.class, int.class,
                    new Xp.MethodHook() {
                        @Override
                        protected void beforeHookedMethod(Xp.MethodHookParam param) {
                            Context context = (Context) param.args[0];
                            if (context == null || !AodWallpaperMode.uiAlive(context)) return;
                            param.args[1] = 1;
                        }
                    });
        } catch (Throwable error) {
            AliveLog.w(TAG, "aod setFsAodStyle hook failed", error);
        }
        try {
            Xp.findAndHookMethod(utils, "getFsAodStyle", Context.class,
                    new Xp.MethodHook() {
                        @Override
                        protected void afterHookedMethod(Xp.MethodHookParam param) {
                            Context context = (Context) param.args[0];
                            if (context != null && AodWallpaperMode.uiAlive(context)) {
                                param.setResult(1);
                                return;
                            }
                            Object result = param.getResult();
                            if (result instanceof Integer && (Integer) result > 1) {
                                param.setResult(1);
                            }
                        }
                    });
        } catch (Throwable error) {
            AliveLog.w(TAG, "aod getFsAodStyle hook failed", error);
        }
    }

    /**
     * MIUI's own onFinishInflate calls updateAodSpinnerContentDescription -> getSelectedItem.
     * On some devices the persisted index is 2 while the native adapter has only two items,
     * so the page crashes before our after-hook can run. Clamp in the before-hook.
     */
    private static void hookBeforeInflate(Class<?> view) {
        try {
            Xp.findAndHookMethod(view, "onFinishInflate", new Xp.MethodHook() {
                @Override
                protected void beforeHookedMethod(Xp.MethodHookParam param) {
                    try {
                        clampSelection((View) param.thisObject);
                    } catch (Throwable error) {
                        AliveLog.w(TAG, "aod spinner clamp failed", error);
                    }
                }
            });
        } catch (Throwable error) {
            AliveLog.w(TAG, "aod onFinishInflate before hook failed", error);
        }
    }

    private static void hookSpinnerDescription(Class<?> view) {
        try {
            Xp.findAndHookMethod(view, "updateAodSpinnerContentDescription", new Xp.MethodHook() {
                @Override
                protected void beforeHookedMethod(Xp.MethodHookParam param) {
                    try {
                        clampSelection((View) param.thisObject);
                    } catch (Throwable error) {
                        AliveLog.w(TAG, "aod spinner description clamp failed", error);
                    }
                }
            });
        } catch (Throwable error) {
            AliveLog.w(TAG, "aod spinner description hook failed", error);
        }
    }

    private static void hookAfter(Class<?> view, String method) {
        try {
            Xp.findAndHookMethod(view, method, new Xp.MethodHook() {
                @Override
                protected void afterHookedMethod(Xp.MethodHookParam param) {
                    try {
                        bind((View) param.thisObject);
                    } catch (Throwable error) {
                        AliveLog.w(TAG, "aod bind failed", error);
                    }
                }
            });
        } catch (Throwable error) {
            AliveLog.w(TAG, "aod " + method + " hook failed", error);
        }
    }

    private static void clampSelection(View root) {
        int spinnerId = id(root.getContext(), "aod_resting_screen_choose");
        if (spinnerId == 0) return;
        View value = root.findViewById(spinnerId);
        if (!(value instanceof Spinner)) return;
        Spinner spinner = (Spinner) value;
        SpinnerAdapter adapter = spinner.getAdapter();
        if (adapter == null || adapter.getCount() == 0) return;
        int selected = spinner.getSelectedItemPosition();
        if (selected < 0 || selected >= adapter.getCount()) {
            spinner.setSelection(0, false);
            AliveLog.i(TAG, "aod spinner clamped " + selected + " -> 0 (count " + adapter.getCount() + ")");
        }
    }

    private static void bind(View root) {
        Context context = root.getContext();
        if (context == null) return;
        int rowId = id(context, "aod_resting_screen_style");
        int spinnerId = id(context, "aod_resting_screen_choose");
        if (rowId == 0 || spinnerId == 0) return;
        View row = root.findViewById(rowId);
        View value = root.findViewById(spinnerId);
        if (!(value instanceof Spinner)) {
            Saved stale = SAVED.get(root);
            if (stale != null) unbind(root, stale);
            return;
        }
        Spinner spinner = (Spinner) value;

        if (!AodWallpaperMode.uiAlive(context)) {
            Saved stale = SAVED.get(root);
            if (stale != null) unbind(root, stale);
            return;
        }

        Saved saved = SAVED.get(root);
        if (saved == null) {
            saved = new Saved(row, spinner);
            SAVED.put(root, saved);
        }
        applyLabel(context, spinner, saved);
        // 2026-11-16 12:3x 用户反馈：应用项目壁纸后下拉里同时出现「AliveHS 样式」和「自定义」，
        // 而「自定义」选了没有反应（因为我们的壁纸本来就走全屏 AOD）。产品预期是
        // 项目壁纸下只提供「AliveHS 样式」，所以恢复锁定。
        lock(row, spinner);
    }

    private static int id(Context context, String name) {
        return context.getResources().getIdentifier(name, "id", "com.miui.aod");
    }

    private static void applyLabel(Context context, Spinner spinner, Saved saved) {
        SpinnerAdapter current = spinner.getAdapter();
        if (current != null && !(current instanceof LabelAdapter) && current != saved.adapter) {
            // MIUI may rebuild its adapter after the page is created.  Wrap the new one
            // instead of keeping a stale reference; keep the selection it currently shows.
            saved.adapter = current;
            saved.selection = spinner.getSelectedItemPosition();
        }
        SpinnerAdapter base = saved.adapter;
        if (base == null) {
            base = saved.fallbackAdapter = fallbackAdapter(context);
            saved.adapter = base;
        }
        if (current instanceof LabelAdapter && ((LabelAdapter) current).base == base) {
            LabelAdapter existing = (LabelAdapter) current;
            saved.labelAdapter = existing;
            int label = existing.labelPosition;
            if (spinner.getSelectedItemPosition() != label) spinner.setSelection(label, false);
            return;
        }
        if (saved.labelAdapter != null) saved.labelAdapter.release();
        LabelAdapter wrapper = new LabelAdapter(context, base);
        saved.labelAdapter = wrapper;
        spinner.setAdapter(wrapper);
        spinner.setSelection(wrapper.labelPosition, false);
    }

    private static SpinnerAdapter fallbackAdapter(Context context) {
        int itemLayout = layout(context, "miuix_appcompat_simple_spinner_layout_integrated");
        int dropLayout = layout(context, "miuix_appcompat_simple_spinner_dropdown_item");
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(context, itemLayout, android.R.id.text1);
        adapter.setDropDownViewResource(dropLayout);
        adapter.add(text(context, "aod_resting_screen_define", "自定义"));
        adapter.add(text(context, "aod_resting_screen_same_to_lock", "和锁屏样式一致"));
        return adapter;
    }

    private static int layout(Context context, String name) {
        int value = context.getResources().getIdentifier(name, "layout", "com.miui.aod");
        if (value != 0) return value;
        return name.contains("dropdown")
                ? android.R.layout.simple_spinner_dropdown_item
                : android.R.layout.simple_spinner_item;
    }

    private static String text(Context context, String name, String fallback) {
        int id = context.getResources().getIdentifier(name, "string", "com.miui.aod");
        if (id == 0) return fallback;
        try {
            return context.getString(id);
        } catch (Throwable ignored) {
            return fallback;
        }
    }

    private static void lock(View row, Spinner spinner) {
        View.OnTouchListener swallow = (view, event) -> true;
        if (row != null) {
            // Do not call setOnClickListener(null): the native click listener is not
            // exposed by any getter and would be lost after unbind.  The touch listener
            // swallows the gesture and returning true prevents the native click.
            row.setEnabled(false);
            row.setClickable(false);
            row.setFocusable(false);
            row.setOnTouchListener(swallow);
        }
        spinner.setEnabled(false);
        spinner.setClickable(false);
        spinner.setLongClickable(false);
        spinner.setFocusable(false);
        spinner.setOnTouchListener(swallow);
    }

    private static void unbind(View root, Saved saved) {
        SAVED.remove(root);
        if (saved.row != null) {
            saved.row.setOnTouchListener(null);
            saved.row.setEnabled(saved.rowEnabled);
            saved.row.setClickable(saved.rowClickable);
            saved.row.setFocusable(saved.rowFocusable);
            saved.row.setFocusableInTouchMode(saved.rowFocusableInTouchMode);
            saved.row.setVisibility(saved.rowVisibility);
        }
        if (saved.labelAdapter != null) {
            saved.labelAdapter.release();
            saved.labelAdapter = null;
        }
        if (saved.spinner != null) {
            saved.spinner.setOnTouchListener(null);
            saved.spinner.setAdapter(saved.adapter);
            if (saved.adapter != null) {
                int position = saved.selection;
                if (position < 0 || position >= saved.adapter.getCount()) position = 0;
                saved.spinner.setSelection(position, false);
            }
            saved.spinner.setEnabled(saved.spinnerEnabled);
            saved.spinner.setClickable(saved.spinnerClickable);
            saved.spinner.setLongClickable(saved.spinnerLongClickable);
            saved.spinner.setFocusable(saved.spinnerFocusable);
            saved.spinner.setFocusableInTouchMode(saved.spinnerFocusableInTouchMode);
        }
        AliveLog.i(TAG, "aod settings row restored");
    }

    private static final class Saved {
        final View row;
        final Spinner spinner;
        SpinnerAdapter adapter;
        int selection;
        final int rowVisibility;
        final boolean rowEnabled;
        final boolean rowClickable;
        final boolean rowFocusable;
        final boolean rowFocusableInTouchMode;
        final boolean spinnerEnabled;
        final boolean spinnerClickable;
        final boolean spinnerLongClickable;
        final boolean spinnerFocusable;
        final boolean spinnerFocusableInTouchMode;
        SpinnerAdapter fallbackAdapter;
        LabelAdapter labelAdapter;

        Saved(View row, Spinner spinner) {
            this.row = row;
            this.spinner = spinner;
            this.adapter = spinner.getAdapter();
            this.selection = spinner.getSelectedItemPosition();
            this.rowVisibility = row != null ? row.getVisibility() : View.VISIBLE;
            this.rowEnabled = row == null || row.isEnabled();
            this.rowClickable = row != null && row.isClickable();
            this.rowFocusable = row != null && row.isFocusable();
            this.rowFocusableInTouchMode = row != null && row.isFocusableInTouchMode();
            this.spinnerEnabled = spinner.isEnabled();
            this.spinnerClickable = spinner.isClickable();
            this.spinnerLongClickable = spinner.isLongClickable();
            this.spinnerFocusable = spinner.isFocusable();
            this.spinnerFocusableInTouchMode = spinner.isFocusableInTouchMode();
        }
    }

    /**
     * A live wrapper around MIUI's own adapter. It never copies or discards native items;
     * the data-set observer keeps the spinner in sync if MIUI updates the native adapter.
     */
    private static final class LabelAdapter extends BaseAdapter {
        final SpinnerAdapter base;
        final int labelPosition;
        private final Context context;
        private final int itemLayout;
        private final int dropLayout;
        private final DataSetObserver observer = new DataSetObserver() {
            @Override public void onChanged() { notifyDataSetChanged(); }
            @Override public void onInvalidated() { notifyDataSetInvalidated(); }
        };

        LabelAdapter(Context context, SpinnerAdapter base) {
            this.context = context;
            this.base = base;
            // MIUI uses index 1 for "和锁屏样式一致" (full AOD).  Replace that item
            // in place instead of appending a fourth position: updateSpinnerState()
            // selects Utils.getFsAodStyle() (1), and appending would make our
            // after-hook select the last item again, producing an endless flicker.
            this.labelPosition = base.getCount() > 1 ? 1 : 0;
            this.itemLayout = layout(context, "miuix_appcompat_simple_spinner_layout_integrated");
            this.dropLayout = layout(context, "miuix_appcompat_simple_spinner_dropdown_item");
            base.registerDataSetObserver(observer);
        }

        @Override public int getCount() { return base.getCount(); }
        @Override public Object getItem(int position) {
            return position == labelPosition ? LABEL : base.getItem(position);
        }
        @Override public long getItemId(int position) {
            return base.getItemId(position);
        }
        @Override public boolean hasStableIds() { return base.hasStableIds(); }
        @Override public int getViewTypeCount() { return 1; }
        @Override public int getItemViewType(int position) { return 0; }

        @Override public View getView(int position, View convertView, ViewGroup parent) {
            if (position == labelPosition) return labelView(parent, false);
            return base.getView(position, null, parent);
        }

        @Override public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if (position == labelPosition) return labelView(parent, true);
            return base.getDropDownView(position, null, parent);
        }

        void release() {
            try {
                base.unregisterDataSetObserver(observer);
            } catch (Throwable ignored) {
            }
        }

        /**
         * Reuse the last native item's view hierarchy.  MIUI's spinner item layout is a
         * LinearLayout with a title and a summary, not a TextView; inflating it and casting
         * the root to TextView is what crashed AodShowModeSettingActivity.
         */
        private View labelView(ViewGroup parent, boolean dropdown) {
            View view = base.getCount() > 0
                    ? (dropdown ? base.getDropDownView(labelPosition, null, parent)
                                : base.getView(labelPosition, null, parent))
                    : null;
            if (view == null) {
                int layout = dropdown ? dropLayout : itemLayout;
                view = LayoutInflater.from(context).inflate(layout, parent, false);
            }
            TextView text = findText(view);
            if (text != null) {
                text.setText(LABEL);
            } else {
                view.setContentDescription(LABEL);
            }
            view.setTag(LABEL_TAG);
            return view;
        }

        private TextView findText(View view) {
            if (view instanceof TextView) return (TextView) view;
            if (view instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) view;
                for (int i = 0; i < group.getChildCount(); i++) {
                    TextView found = findText(group.getChildAt(i));
                    if (found != null) return found;
                }
            }
            return null;
        }
    }
}
