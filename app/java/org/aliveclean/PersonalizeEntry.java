package org.aliveclean;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * Adds a standalone "HyperAlive" card on top of the 系统个性化 page of 主题壁纸
 * (com.android.thememanager.settings.personalize.activity.PersonalizeActivity).
 *
 * The card is built from the app's own resources only: the native MIUI CardView class and the
 * native preference row layout, so the entry looks exactly like the eight rows below it while
 * staying in its own card (like 小米澎湃AI in Settings).
 */
public final class PersonalizeEntry {
    private static final String TAG = "HyperAliveHooks";
    private static final String HOST_PKG = "com.android.thememanager";
    private static final String ACTIVITY = HOST_PKG + ".settings.personalize.activity.PersonalizeActivity";
    private static final String SELF_PKG = "org.aliveclean";
    private static final String HOME_ACTIVITY = "org.aliveclean.HomeEntryActivity";
    private static final String TITLE = "HyperAlive";
    private static final String MARK = "hyperalive.personalize.card";
    private static final int GAP_DP = 12;

    private PersonalizeEntry() {}

    public static void install(ClassLoader cl) {
        Class<?> activityClass = XposedHelpers.findClassIfExists(ACTIVITY, cl);
        if (activityClass == null) {
            Log.w(TAG, "personalize: " + ACTIVITY + " not found");
            return;
        }
        XposedHelpers.findAndHookMethod(activityClass, "onResume", new XC_MethodHook() {
            @Override protected void afterHookedMethod(MethodHookParam param) {
                Activity activity = (Activity) param.thisObject;
                View decor = activity.getWindow() == null ? null : activity.getWindow().peekDecorView();
                if (decor == null) return;
                decor.post(() -> inject(activity, 0));
            }
        });
    }

    private static void inject(Activity activity, int attempt) {
        try {
            View anchor = view(activity, "theme_personlize_card_view");
            if (anchor == null || !(anchor.getParent() instanceof ViewGroup)) {
                if (attempt < 12) retry(activity, attempt);
                return;
            }
            ViewGroup host = (ViewGroup) anchor.getParent();
            if (host.findViewWithTag(MARK) != null) return;

            View card = newCard(activity, anchor);
            LinearLayout box = new LinearLayout(activity);
            box.setOrientation(LinearLayout.VERTICAL);
            box.addView(newRow(activity), new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ((ViewGroup) card).addView(box, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            card.setTag(MARK);

            /* stack the new card above the native one so both keep their own rounded card */
            LinearLayout stack = new LinearLayout(activity);
            stack.setOrientation(LinearLayout.VERTICAL);
            stack.setTag(MARK);
            int index = host.indexOfChild(anchor);
            host.removeView(anchor);
            host.addView(stack, index, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            stack.addView(card, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams anchorParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            anchorParams.topMargin = dp(activity, GAP_DP);
            stack.addView(anchor, anchorParams);
            stack.requestLayout();
            host.requestLayout();
            Log.i(TAG, "personalize: entry injected");
        } catch (Throwable t) {
            Log.w(TAG, "personalize: inject failed", t);
            if (attempt < 12) retry(activity, attempt);
        }
    }

    private static void retry(Activity activity, int attempt) {
        View decor = activity.getWindow().peekDecorView();
        if (decor == null) return;
        decor.postDelayed(() -> inject(activity, attempt + 1), 120);
    }

    /* ---------------------------------------------------------------- views */

    private static View newCard(Activity activity, View anchor) throws Exception {
        Class<?> cls = Class.forName("com.miui.support.cardview.CardView", false, activity.getClassLoader());
        View card = (View) cls.getConstructor(Context.class).newInstance(activity);
        int color = 0;
        try {
            Object state = anchor.getClass().getMethod("getCardBackgroundColor").invoke(anchor);
            if (state instanceof ColorStateList) color = ((ColorStateList) state).getDefaultColor();
        } catch (Throwable ignored) {}
        try {
            cls.getMethod("setCardBackgroundColor", int.class).invoke(card, color);
        } catch (Throwable ignored) {}
        try {
            float radius = ((Number) anchor.getClass().getMethod("getRadius").invoke(anchor)).floatValue();
            cls.getMethod("setRadius", float.class).invoke(card, radius);
        } catch (Throwable ignored) {}
        try {
            cls.getMethod("setCardElevation", float.class).invoke(card, 0f);
        } catch (Throwable ignored) {}
        return card;
    }

    private static View newRow(Activity activity) throws Exception {
        int theme = activity.getResources().getIdentifier("ThemePreferenceViewStyle", "style", HOST_PKG);
        Context themed = new ContextThemeWrapper(activity,
                theme != 0 ? theme : android.R.style.Theme_DeviceDefault);
        Class<?> rowClass = Class.forName("com.android.thememanager.view.ThemePreferenceView",
                false, activity.getClassLoader());
        View row = (View) rowClass.getConstructor(Context.class).newInstance(themed);
        ViewGroup group = (ViewGroup) row;
        group.removeAllViews();
        int layout = id(activity, "miuix_preference_main_layout", "layout");
        LayoutInflater.from(themed).inflate(layout, group, true);

        View nativeRow = view(activity, "aod_item");
        if (nativeRow != null && nativeRow != row) {
            row.setPadding(nativeRow.getPaddingLeft(), nativeRow.getPaddingTop(),
                    nativeRow.getPaddingRight(), nativeRow.getPaddingBottom());
            if (nativeRow.getLayoutParams() != null) {
                row.setMinimumHeight(nativeRow.getLayoutParams().height);
            }
        }

        TextView summary = row.findViewById(android.R.id.summary);
        if (summary != null) summary.setVisibility(View.GONE);

        ImageView icon = row.findViewById(android.R.id.icon);
        if (icon != null) {
            Drawable appIcon = roundedIcon(activity, appIcon(activity, SELF_PKG));
            if (appIcon != null) icon.setImageDrawable(appIcon);
            icon.setVisibility(appIcon == null ? View.GONE : View.VISIBLE);
        }

        TextView title = row.findViewById(android.R.id.title);
        if (title != null) title.setText(TITLE);

        row.setOnClickListener(v -> openHome(v.getContext()));
        return row;
    }

    private static Drawable appIcon(Activity activity, String pkg) {
        try {
            return activity.getPackageManager().getApplicationIcon(pkg);
        } catch (Throwable t) {
            Log.w(TAG, "personalize: icon for " + pkg + " failed: " + t);
            return null;
        }
    }

    /** Same geometry as the stock icons: 28dp squircle, ~16% corner radius, centred in the 34dp box. */
    private static Drawable roundedIcon(Activity activity, Drawable source) {
        if (source == null) return null;
        try {
            int size = dp(activity, 28);
            Bitmap raw = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            Canvas rawCanvas = new Canvas(raw);
            source.setBounds(0, 0, size, size);
            source.draw(rawCanvas);

            Bitmap out = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setShader(new BitmapShader(raw, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            new Canvas(out).drawRoundRect(new RectF(0, 0, size, size), size * 0.16f, size * 0.16f, paint);
            return new android.graphics.drawable.BitmapDrawable(activity.getResources(), out);
        } catch (Throwable t) {
            Log.w(TAG, "personalize: icon shape failed", t);
            return source;
        }
    }

    private static void openHome(Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setClassName(SELF_PKG, HOME_ACTIVITY);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Throwable t) {
            Log.w(TAG, "personalize: open home failed", t);
        }
    }

    /* ---------------------------------------------------------------- helpers */

    private static View view(Activity activity, String name) {
        int id = id(activity, name, "id");
        return id == 0 ? null : activity.findViewById(id);
    }

    private static int id(Context context, String name, String type) {
        return context.getResources().getIdentifier(name, type, HOST_PKG);
    }

    private static int dp(Context context, int value) {
        return (int) (value * context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
