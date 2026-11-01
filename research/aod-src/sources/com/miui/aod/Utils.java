package com.miui.aod;

import android.app.Activity;
import android.app.UiModeManager;
import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.text.TextUtilsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.gson.Gson;
import com.miui.aod.analy.AnalyticManager;
import com.miui.aod.attention.AttentionManager;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.category.TextStyleCategoryInfo;
import com.miui.aod.common.MainThread;
import com.miui.aod.flip.FlipLinkageStyleController;
import com.miui.aod.resource.CachedSettings;
import com.miui.aod.util.BroadcastDispatcher;
import com.miui.aod.util.DeviceInfo;
import com.miui.aod.util.DisplayUtils;
import com.miui.aod.util.ScreenResolutionUtils;
import com.miui.aod.util.SystemPropertiesUtils;
import com.miui.aod.util.WindowUtils;
import com.miui.aod.utils.CommonUtils;
import com.miui.aod.utils.LooperUtils;
import com.miui.aod.utils.MiuiSettingsUtils;
import com.miui.aod.utils.PowerManagerUtils;
import com.miui.aod.widget.AODSettings;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.xiaomi.onetrack.util.ac;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import miui.os.Build;
import miui.util.FeatureParser;
import miui.util.ITouchFeature;
import miui.util.MiuiMultiDisplayTypeInfo;
import miuix.device.DeviceUtils;
import miuix.os.DeviceHelper;

/* JADX INFO: loaded from: classes.dex */
public class Utils {
    public static final Long BROADCAST_SLOW_DELIVERY_THRESHOLD;
    public static final Long BROADCAST_SLOW_DISPATCH_THRESHOLD;
    public static final int FOD_SOLUTION;
    public static final boolean GXZW_SENSOR;
    public static final boolean SUPPORT_DIMLAYER;
    public static final boolean SUPPORT_LOW_BRIGHTNESS_FOD;
    public static ExecutorService backgroundExecutor;
    public static BroadcastDispatcher broadcastDispatcher;
    public static Pair mGxzwIconPosition;
    public static float mStrokeWidth;
    public static int[] sLightValues;
    public static Toast successToast;
    public static final int[] weights;
    public static final boolean SUPPORT_AOD_AON = FeatureParser.getBoolean("support_aod_aon", false);
    public static final boolean SUPPORT_AOD_OUTER_SCREEN_AON = FeatureParser.getBoolean("support_aod_outer_screen_aon", false);
    public static final boolean SUPPORT_FULL_AOD = FeatureParser.getBoolean("support_aod_fullscreen", false);
    public static final boolean INNER_SCREEN_SUPPORT_FULL_AOD = FeatureParser.getBoolean("inner_screen_support_aod_fullscreen", false);
    public static boolean sMiuiOptimizationDisabled = false;

    public static /* synthetic */ void $r8$lambda$3AMJFkLhCGdB6s1oE9bPoGsF0WA() {
        Toast toast = successToast;
        if (toast != null) {
            toast.cancel();
            successToast = null;
        }
    }

    public static /* synthetic */ void $r8$lambda$vEK_ZsIG0GxAThocbIZKwN5C7k0(Context context) {
        cancelSuccessToast();
        Toast toastMakeText = Toast.makeText(context, context.getResources().getString(R.string.apply_succeed), 0);
        successToast = toastMakeText;
        toastMakeText.show();
        AnalyticManager.getInstance(context).trackAodApplyClickAction();
    }

    static {
        SystemPropertiesUtils systemPropertiesUtils = SystemPropertiesUtils.INSTANCE;
        boolean z = systemPropertiesUtils.getBoolean("ro.vendor.fod.dimlayer.enable", false) || systemPropertiesUtils.getBoolean("ro.fod.dimlayer.enable", false);
        SUPPORT_DIMLAYER = z;
        if (FeatureParser.hasFeature("support_low_brightness_fod", 1)) {
            SUPPORT_LOW_BRIGHTNESS_FOD = FeatureParser.getBoolean("support_low_brightness_fod", false);
        } else if (FeatureParser.hasFeature("fod_solution", 2)) {
            SUPPORT_LOW_BRIGHTNESS_FOD = FeatureParser.getInteger("fod_solution", -1) == 1;
        } else {
            String str = Build.DEVICE;
            SUPPORT_LOW_BRIGHTNESS_FOD = ("equuleus".equals(str) || "ursa".equals(str) || z) ? false : true;
        }
        FOD_SOLUTION = FeatureParser.getInteger("fod_solution", -1);
        GXZW_SENSOR = systemPropertiesUtils.getBoolean("ro.hardware.fp.fod", false);
        weights = new int[]{100, 300, 400, 500, TextStyleCategoryInfo.MAX_FONT_WEIGHT, 900};
        sLightValues = FeatureParser.getIntArray("aod_brightness_level");
        successToast = null;
        BROADCAST_SLOW_DISPATCH_THRESHOLD = 1000L;
        BROADCAST_SLOW_DELIVERY_THRESHOLD = 1000L;
        broadcastDispatcher = new BroadcastDispatcher();
    }

    public static int acquireNavBarHeight() {
        int dimensionPixelSize = 0;
        try {
            int identifier = AODApplication.sInstance.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (identifier <= 0) {
                return 0;
            }
            dimensionPixelSize = AODApplication.sInstance.getResources().getDimensionPixelSize(identifier);
            Log.d("MiuiAod.Utils", "mNavBarHeight = " + dimensionPixelSize + ", application density = " + AODApplication.sInstance.getResources().getDisplayMetrics().density);
            return dimensionPixelSize;
        } catch (Exception e) {
            Log.e("MiuiAod.Utils", "acquireNavBarHeight", e);
            return dimensionPixelSize;
        }
    }

    public static boolean aodDisabledForScheduleMode(Context context) {
        if (isTimingStyle(context)) {
            long aodStartTime = getAodStartTime(context);
            long aodEndTime = getAodEndTime(context);
            Calendar calendar = Calendar.getInstance();
            long j = (((long) ((calendar.get(11) * 60) + calendar.get(12))) * 60000) + 1;
            if (aodStartTime < aodEndTime && (j < aodStartTime || j > aodEndTime)) {
                return true;
            }
            if ((j < aodStartTime && j > aodEndTime) || aodStartTime == aodEndTime) {
                return true;
            }
        }
        return false;
    }

    public static boolean aodGone(Context context) {
        boolean z = !isAodEnable(context) || AODSettings.isSuperSaveModeOpen(context);
        if ((!isFsAodSameToLock(context) || isCurrentScreenSupportFsAod(context)) && !z) {
            return aodDisabledForScheduleMode(context);
        }
        return true;
    }

    public static int aodModeStateUserSet(Context context) {
        return MiuiSettingsUtils.Secure.getInt(context.getContentResolver(), "aod_mode_user_set", -1);
    }

    public static void calculate(String str, TextView textView, Boolean bool, int i) {
        try {
            textView.setLineSpacing(textView.getTextSize() + i, 0.0f);
            if (bool.booleanValue()) {
                textView.setPadding(0, i, 0, i);
            }
        } catch (Exception e) {
            Log.e("MiuiAod.Utils", str + " setLineSpacing Exception :" + e);
        }
    }

    public static void cancelSuccessToast() {
        MainThread.execute(new Runnable() { // from class: com.miui.aod.Utils$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Utils.$r8$lambda$3AMJFkLhCGdB6s1oE9bPoGsF0WA();
            }
        });
    }

    public static int convertLevel2Weight(int i) {
        if (i >= 0) {
            int[] iArr = weights;
            if (i < iArr.length) {
                return iArr[i];
            }
        }
        return weights[0];
    }

    public static int convertWeight2Level(int i) {
        int i2 = Integer.MAX_VALUE;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int[] iArr = weights;
            if (i3 >= iArr.length) {
                return i4;
            }
            int iAbs = Math.abs(i - iArr[i3]);
            if (iAbs < i2) {
                i4 = i3;
                i2 = iAbs;
            }
            i3++;
        }
    }

    public static boolean disableAodInPowerSaveModePermitted(Context context) {
        return MiuiSettingsUtils.Secure.getInt(context.getContentResolver(), "permit_disable_aod_in_power_save_mode", 1) == 1;
    }

    public static int dp2Px(int i) {
        return DeviceInfo.dp2Px(i);
    }

    public static View findLowestVisibleLeafView(ViewGroup viewGroup) {
        int i;
        View view = null;
        if (viewGroup == null) {
            return null;
        }
        ArrayDeque arrayDeque = new ArrayDeque();
        for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
            arrayDeque.push(viewGroup.getChildAt(i2));
        }
        Rect rect = new Rect();
        int i3 = Integer.MIN_VALUE;
        while (!arrayDeque.isEmpty()) {
            View view2 = (View) arrayDeque.pop();
            if (view2 != null && view2.getVisibility() == 0 && view2.getAlpha() > 0.0f) {
                if (view2 instanceof ViewGroup) {
                    ViewGroup viewGroup2 = (ViewGroup) view2;
                    for (int i4 = 0; i4 < viewGroup2.getChildCount(); i4++) {
                        arrayDeque.push(viewGroup2.getChildAt(i4));
                    }
                } else if (view2.getGlobalVisibleRect(rect) && (i = rect.bottom) > i3) {
                    view = view2;
                    i3 = i;
                }
            }
        }
        return view;
    }

    public static String formatTimeFromMinutes(int i, Context context) {
        miuix.pickerwidget.date.Calendar calendar = new miuix.pickerwidget.date.Calendar();
        String string = DateFormat.is24HourFormat(context) ? context.getResources().getString(R.string.scheduled_mode_dialog_msg_24) : context.getResources().getString(R.string.scheduled_mode_dialog_msg_12);
        calendar.set(18, i / 60);
        calendar.set(20, i % 60);
        return calendar.format(context, string);
    }

    public static boolean fullAodDisabledForScheduleMode(Context context) {
        return AODSettings.needFullAod() && aodDisabledForScheduleMode(context);
    }

    public static long getAodEndTime(Context context) {
        return ((long) CachedSettings.Secure.getInt(context, "aod_end", 1380)) * 60000;
    }

    public static long getAodStartTime(Context context) {
        return ((long) CachedSettings.Secure.getInt(context, "aod_start", 420)) * 60000;
    }

    public static String getAodUsingCategoryName(Context context) {
        String string = MiuiSettingsUtils.Secure.getString(context.getContentResolver(), "aod_category_name");
        return !TextUtils.isEmpty(string) ? string : CategoryConstants.getDefaultCategoryName(context);
    }

    public static String getAodUsingCategoryParam(Context context, String str) {
        String string = MiuiSettingsUtils.Secure.getString(context.getContentResolver(), str);
        return !TextUtils.isEmpty(string) ? string : CategoryConstants.DEFAULT_PARAM;
    }

    public static int getAonConnectDuration() {
        return 5000;
    }

    public static ExecutorService getBackgroundExecutor() {
        ExecutorService executorService = backgroundExecutor;
        if (executorService == null || executorService.isShutdown()) {
            backgroundExecutor = Executors.newSingleThreadExecutor();
        }
        return backgroundExecutor;
    }

    public static String getCurrentTemplateInfo() {
        return MiuiSettingsUtils.Secure.getString(AODApplication.sInstance.getContentResolver(), DeviceUtil.INSTANCE.isVersionOs3(AODApplication.sInstance) ? "constant_template_editor_info" : "constant_lockscreen_info");
    }

    public static String getDataColumn(Context context, Uri uri, String str, String[] strArr) {
        String[] strArr2 = {"_data"};
        String string = null;
        try {
            Cursor cursorQuery = context.getContentResolver().query(uri, strArr2, str, strArr, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst()) {
                        string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow(strArr2[0]));
                    }
                } catch (Throwable th) {
                    try {
                        cursorQuery.close();
                        throw th;
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                        throw th;
                    }
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        } catch (Exception unused) {
        }
        return string;
    }

    public static int getDefaultShowStyle(Context context) {
        if (DeviceUtil.INSTANCE.isPad()) {
            return 3;
        }
        if (isFsAodSameToLock(context) && AttentionManager.supportAttention(context)) {
            return 3;
        }
        return AODSettings.AOD_SHOW_STYLE_DEF;
    }

    public static int getDisplayMode(Context context) {
        return MiuiSettingsUtils.Secure.getInt(context.getContentResolver(), "aod_show_style", getDefaultShowStyle(context));
    }

    public static float getFontScale(Context context) {
        return Settings.System.getFloat(context.getContentResolver(), "font_scale", 1.0f);
    }

    public static int getFsAodStyle(Context context) {
        return MiuiSettingsUtils.Secure.getInt(context.getContentResolver(), "full_screen_aod_on", (!isFoldDevice() || isInnerScreenSupportFsAod()) ? 1 : 0);
    }

    public static Pair getGxzwIconPosition() {
        Pair pair = mGxzwIconPosition;
        if (pair != null) {
            return pair;
        }
        String stringFromSystemProperties = MiuiSettingsUtils.getStringFromSystemProperties("persist.sys.fp.fod.location.X_Y", "");
        if (stringFromSystemProperties.isEmpty()) {
            stringFromSystemProperties = MiuiSettingsUtils.getStringFromSystemProperties("persist.vendor.sys.fp.fod.location.X_Y", "");
        }
        String stringFromSystemProperties2 = MiuiSettingsUtils.getStringFromSystemProperties("persist.sys.fp.fod.size.width_height", "");
        if (stringFromSystemProperties2.isEmpty()) {
            stringFromSystemProperties2 = MiuiSettingsUtils.getStringFromSystemProperties("persist.vendor.sys.fp.fod.size.width_height", "");
        }
        if (stringFromSystemProperties.isEmpty() || stringFromSystemProperties2.isEmpty()) {
            return new Pair(-1, -1);
        }
        try {
            float screenRatio = ScreenResolutionUtils.getInstance().getScreenRatio();
            Pair pair2 = new Pair(Integer.valueOf((int) (Integer.parseInt(stringFromSystemProperties.split(ac.b)[0]) * screenRatio)), Integer.valueOf((int) (Integer.parseInt(stringFromSystemProperties.split(ac.b)[1]) * screenRatio)));
            mGxzwIconPosition = pair2;
            return pair2;
        } catch (Exception e) {
            Log.e("MiuiAod.Utils", "get gxzwIconPosition ERROR: " + e.getMessage());
            return new Pair(-1, -1);
        }
    }

    public static String getHourMinformat(Context context) {
        String pattern = ((SimpleDateFormat) DateFormat.getTimeFormat(context)).toPattern();
        return pattern.contains("a") ? pattern.replace("a", "").trim() : pattern;
    }

    public static float getImageShowStrokeWidth() {
        if (mStrokeWidth == 0.0f) {
            mStrokeWidth = AODApplication.sInstance.getResources().getDimension(R.dimen.image_show_stroke_width);
        }
        return mStrokeWidth;
    }

    public static int getKeyguardNotificationStatus(Context context) {
        int i = 1;
        boolean z = Settings.Global.getInt(context.getContentResolver(), "new_device_after_support_notification_animation", 0) != 0;
        if ("perseus".equals(android.os.Build.DEVICE) || (z && isSupportAodAnimateDevice(context))) {
            i = 2;
        }
        return MiuiSettingsUtils.System.getInt(context.getContentResolver(), "wakeup_for_keyguard_notification", i);
    }

    public static int getNavigationBarHeight(Activity activity) {
        try {
            int navigationBarInsetsHeight = getNavigationBarInsetsHeight(activity);
            if (navigationBarInsetsHeight <= 0) {
                return acquireNavBarHeight();
            }
            Log.d("MiuiAod.Utils", "naviBarRealHeight = " + navigationBarInsetsHeight + ", activity density = " + activity.getResources().getDisplayMetrics().density + ", application density = " + activity.getResources().getDisplayMetrics().density);
            return navigationBarInsetsHeight;
        } catch (Exception e) {
            Log.e("MiuiAod.Utils", "getNavigationBarHeight", e);
            return acquireNavBarHeight();
        }
    }

    public static int getNavigationBarInsetsHeight(Activity activity) {
        WindowInsetsCompat rootWindowInsets;
        if (activity == null || activity.getWindow() == null || (rootWindowInsets = ViewCompat.getRootWindowInsets(activity.getWindow().getDecorView())) == null) {
            return 0;
        }
        return rootWindowInsets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom;
    }

    public static int getNotificationOffFallbackWidth(Context context) {
        return WindowUtils.INSTANCE.getWidthPixel(context) - ((inLargeScreen(context) ? context.getResources().getDimensionPixelSize(R.dimen.aod_notification_clock_margin_full) : context.getResources().getDimensionPixelSize(R.dimen.aod_notification_clock_margin)) * 2);
    }

    public static int getNotificationOffScreenWidth(View view) {
        int i;
        if (view == null) {
            return 0;
        }
        View view2 = view;
        while (view2.getParent() instanceof View) {
            view2 = (View) view2.getParent();
            if (view2.getId() == R.id.layout_aod_notification_screen_off) {
                ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                if (layoutParams != null && (i = layoutParams.width) > 0) {
                    return (i - view2.getPaddingLeft()) - view2.getPaddingRight();
                }
                if (view2.getWidth() <= 0) {
                    break;
                }
                return (view2.getWidth() - view2.getPaddingLeft()) - view2.getPaddingRight();
            }
        }
        return getNotificationOffFallbackWidth(view.getContext());
    }

    public static String getRealPathFromUri(Context context, Uri uri) {
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) {
                return getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "_id=?", new String[]{documentId.split(":")[1]});
            }
            if (isDownloadsDocument(uri)) {
                return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(documentId)), null, null);
            }
        } else {
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            }
            if ("file".equals(uri.getScheme())) {
                return uri.getPath();
            }
        }
        return null;
    }

    public static int getShowStyle(Context context) {
        if (isMiuiOptimizationDisabled()) {
            return 2;
        }
        if (AODSettings.onlySupportKeycodeGoto() && !AttentionManager.supportAttention(context)) {
            return 0;
        }
        if (!isAodProcess()) {
            return CachedSettings.Secure.getInt(context, "aod_show_style", getDefaultShowStyle(context));
        }
        int i = MiuiSettingsUtils.Secure.getInt(context.getContentResolver(), "aod_show_style", getDefaultShowStyle(context));
        if (AODSettings.isO2SDevice() && isFsAodSameToLock(context) && (i == 2 || i == 1)) {
            return 0;
        }
        return i;
    }

    public static int getSmallViewHeight(Context context) {
        return (int) (getSmallViewWidth(context) * context.getResources().getFloat(R.dimen.aod_item_ratio));
    }

    public static int getSmallViewWidth(Context context) {
        if (context == null) {
            context = AODApplication.sInstance;
        }
        if (isFoldDevice()) {
            return dp2Px(143);
        }
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.list_margin);
        return (((WindowUtils.INSTANCE.getWidthPixel(context) - (dimensionPixelSize * 2)) - context.getResources().getDimensionPixelSize(R.dimen.list_margin_start)) / 5) * 2;
    }

    public static int getSmartStyleDuration() {
        return 7000;
    }

    public static String getStringFromSettings(String str) {
        try {
            Context contextCreatePackageContext = AODApplication.sInstance.createPackageContext("com.android.settings", 2);
            int identifier = contextCreatePackageContext.getResources().getIdentifier(str, "string", "com.android.settings");
            if (identifier > 0) {
                return contextCreatePackageContext.getString(identifier);
            }
            return null;
        } catch (Exception e) {
            Log.e("MiuiAod.Utils", "getStringFromSettings fail " + e.toString());
            return null;
        }
    }

    public static int getStyleItemWidth(Context context) {
        int smallViewWidth = getSmallViewWidth(context);
        int iCeil = (int) Math.ceil(((double) smallViewWidth) * 1.2d);
        Point windowSize = WindowUtils.INSTANCE.getWindowSize(context);
        return Math.max((int) (iCeil / (windowSize.y / windowSize.x)), smallViewWidth);
    }

    public static int getTemporaryStyleDuration() {
        String str = android.os.Build.DEVICE;
        return ((str.equals("psyche") || str.equals("zeus") || str.equals("cupid")) && !isUsingSuperWallpaperStyle(AODApplication.sInstance)) ? 5000 : 10000;
    }

    public static long getVersionCode(Context context, String str) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 0);
            if (packageInfo == null) {
                Log.i("MiuiAod.Utils", "getVersionCode: packageInfo null");
                return 0L;
            }
            Log.i("MiuiAod.Utils", "getVersionCode: " + str + "  " + packageInfo.getLongVersionCode());
            return packageInfo.getLongVersionCode();
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("MiuiAod.Utils", "error...", e);
            return 0L;
        }
    }

    public static boolean hideSoftInput(Context context, View view) {
        return ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean inLargeScreen(Context context) {
        int i = Resources.getSystem().getConfiguration().screenLayout & 15;
        if (i == 3 || i == 4) {
            return DeviceUtils.isFoldDevice() || DeviceUtils.isTablet(context);
        }
        return false;
    }

    public static boolean isAmbientDisplaySuppressed(Context context) {
        try {
            return PowerManagerUtils.isAmbientDisplaySuppressed((PowerManager) context.getSystemService("power"));
        } catch (Exception e) {
            Log.wtf("MiuiAod.Utils", "call isAmbientDisplaySuppressed failed", e);
            return false;
        }
    }

    public static boolean isAodAnimateEnable(Context context) {
        return getKeyguardNotificationStatus(context) == 2;
    }

    public static boolean isAodEnable(Context context) {
        if (isFlipDevice()) {
            FlipLinkageStyleController flipLinkageStyleController = FlipLinkageStyleController.INSTANCE;
            if (flipLinkageStyleController.isFlipped()) {
                return flipLinkageStyleController.isUsingFlip(context);
            }
        }
        return isAodSettingsEnabled(context);
    }

    public static boolean isAodProcess() {
        return AODApplication.sysuiContext == null;
    }

    public static boolean isAodSettingsEnabled(Context context) {
        return CachedSettings.Secure.getInt(context, "doze_always_on", 0) == 1;
    }

    public static boolean isCJKCharacter(int i) {
        Character.UnicodeBlock unicodeBlockOf = Character.UnicodeBlock.of(i);
        return unicodeBlockOf == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || unicodeBlockOf == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || unicodeBlockOf == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || unicodeBlockOf == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || unicodeBlockOf == Character.UnicodeBlock.HANGUL_SYLLABLES || unicodeBlockOf == Character.UnicodeBlock.HANGUL_JAMO || unicodeBlockOf == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO || unicodeBlockOf == Character.UnicodeBlock.HIRAGANA || unicodeBlockOf == Character.UnicodeBlock.KATAKANA || unicodeBlockOf == Character.UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS || unicodeBlockOf == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || unicodeBlockOf == Character.UnicodeBlock.GENERAL_PUNCTUATION;
    }

    public static boolean isCloseAnimator(Context context) {
        return context != null && Settings.Global.getFloat(context.getContentResolver(), "animator_duration_scale", 1.0f) == 0.0f;
    }

    public static boolean isControlCharactor(int i) {
        return i < 32 || i == 127;
    }

    public static boolean isControlCharactorNoLf(int i) {
        return isControlCharactor(i) && i != 10;
    }

    public static boolean isCpuAndGpuHighDevice() {
        return DeviceUtil.INSTANCE.isCpuAndGpuHighLevel();
    }

    public static boolean isCurrentScreenSupportFsAod(Context context) {
        return !inLargeScreen(context) || isInnerScreenSupportFsAod();
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isEmojiCharacter(int i) {
        if (128 <= i && i <= 191) {
            return true;
        }
        if (768 <= i && i <= 1023) {
            return true;
        }
        if (1536 <= i && i <= 1791) {
            return true;
        }
        if (3072 <= i && i <= 3199) {
            return true;
        }
        if (7616 <= i && i <= 7679) {
            return true;
        }
        if (7680 <= i && i <= 7935) {
            return true;
        }
        if (8192 <= i && i <= 8351) {
            return true;
        }
        if (8400 <= i && i <= 8527) {
            return true;
        }
        if (8592 <= i && i <= 9215) {
            return true;
        }
        if (9312 <= i && i <= 9727) {
            return true;
        }
        if (9728 <= i && i <= 10239) {
            return true;
        }
        if (10496 <= i && i <= 10751) {
            return true;
        }
        if (11008 <= i && i <= 11263) {
            return true;
        }
        if (11360 <= i && i <= 11391) {
            return true;
        }
        if (11776 <= i && i <= 11903) {
            return true;
        }
        if (42128 <= i && i <= 42191) {
            return true;
        }
        if (57344 <= i && i <= 63743) {
            return true;
        }
        if (65024 <= i && i <= 65039) {
            return true;
        }
        if (65072 <= i && i <= 65103) {
            return true;
        }
        if (917536 <= i && i <= 917631) {
            return true;
        }
        if (126976 <= i && i <= 127231) {
            return true;
        }
        if (127232 <= i && i <= 127487) {
            return true;
        }
        if (127488 <= i && i <= 127743) {
            return true;
        }
        if (127744 <= i && i <= 128511) {
            return true;
        }
        if (128512 <= i && i <= 128591) {
            return true;
        }
        if (128592 <= i && i <= 128639) {
            return true;
        }
        if (128640 <= i && i <= 128767) {
            return true;
        }
        if (128768 <= i && i <= 128895) {
            return true;
        }
        if (128896 <= i && i <= 129023) {
            return true;
        }
        if (129024 <= i && i <= 129279) {
            return true;
        }
        if (129280 <= i && i <= 129535) {
            return true;
        }
        if (129536 > i || i > 129647) {
            return 129648 <= i && i <= 129791;
        }
        return true;
    }

    public static boolean isFingerprintDisabled(Context context, DozeHost dozeHost) {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
        if (devicePolicyManager == null || (devicePolicyManager.getKeyguardDisabledFeatures(null) & 32) == 0) {
            return dozeHost != null && dozeHost.isSimPinSecure();
        }
        return true;
    }

    public static boolean isFlipDevice() {
        return MiuiMultiDisplayTypeInfo.isFlipDevice();
    }

    public static boolean isFodAodShowEnable(Context context) {
        return MiuiSettingsUtils.Secure.getInt(context.getContentResolver(), "gxzw_icon_aod_show_enable", 1) == 1;
    }

    public static boolean isFoldDevice() {
        return MiuiMultiDisplayTypeInfo.isFoldDevice();
    }

    public static boolean isFolded(Context context) {
        if (isFlipDevice()) {
            return DeviceHelper.isTinyScreen(context);
        }
        if (isFoldDevice()) {
            return !DeviceHelper.isWideScreen(context);
        }
        return false;
    }

    public static boolean isFsAodSameToLock(Context context) {
        return isSupportFsAod() && getFsAodStyle(context) == 1;
    }

    public static boolean isFullAodNotificationEnable(Context context) {
        return MiuiSettingsUtils.Secure.getInt(context.getContentResolver(), "full_screen_aod_notification", 1) == 1;
    }

    public static boolean isFullAodSupport(Context context) {
        if (isAodProcess()) {
            return MiuiSettingsUtils.Secure.getInt(context.getContentResolver(), "full_screen_aod_support", 1) == 1;
        }
        return CachedSettings.Secure.getInt(context, "full_screen_aod_support", 0) == 1;
    }

    public static boolean isFullScreenGestureNavigationMode(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) != 0;
    }

    public static boolean isGxzwSensor() {
        return GXZW_SENSOR;
    }

    public static boolean isHardWareVendorMediaTek() {
        if (!android.os.Build.HARDWARE.startsWith("mt")) {
            return false;
        }
        Log.i("MiuiAod.Utils", "MediaTek platform");
        return true;
    }

    public static boolean isImageShareAvailable(Context context) {
        long versionCode = getVersionCode(context, "com.xiaomi.vipaccount");
        return (versionCode >= 2029 && versionCode < 10000) || versionCode > 12072;
    }

    public static boolean isInnerScreenSupportFsAod() {
        return INNER_SCREEN_SUPPORT_FULL_AOD;
    }

    public static boolean isIntentResolved(Context context, Intent intent) {
        List<ResolveInfo> listQueryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
        return (listQueryIntentActivities == null || listQueryIntentActivities.isEmpty()) ? false : true;
    }

    public static boolean isInvertColorsEnable(Context context) {
        return MiuiSettingsUtils.Secure.getInt(context.getContentResolver(), "accessibility_display_inversion_enabled", 0) == 1;
    }

    public static boolean isKeyGuardFodShowEnable(Context context) {
        return MiuiSettingsUtils.Secure.getInt(context.getContentResolver(), "miui_keyguard", 2) == 2;
    }

    public static boolean isLayoutRtl() {
        return TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
    }

    public static boolean isLinkageState(Context context) {
        if (isAodProcess() ? MiuiSettingsUtils.Secure.getInt(context.getContentResolver(), "linkage_state", 0) != 1 : CachedSettings.Secure.getInt(context, "linkage_state", 0) != 1) {
            FlipLinkageStyleController flipLinkageStyleController = FlipLinkageStyleController.INSTANCE;
            if (!flipLinkageStyleController.isUsingFlip(AODApplication.sInstance) || !flipLinkageStyleController.isFlipped()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isMiuiDeskTopMode(Context context) {
        return 1 == Settings.System.getInt(context.getContentResolver(), "miui_dkt_mode", 0);
    }

    public static boolean isMiuiOptimizationDisabled() {
        return sMiuiOptimizationDisabled;
    }

    public static boolean isNeedSetScreenOff(Context context) {
        return DisplayUtils.getDisplayState(context) == 3 || DisplayUtils.getDisplayState(context) == 4;
    }

    public static boolean isNeedToGrayScale() {
        return FeatureParser.hasFeature("is_aod_need_grayscale", 1) ? FeatureParser.getBoolean("is_aod_need_grayscale", false) : FeatureParser.getBoolean("is_ltpo_screen", false);
    }

    public static boolean isNightMode(Context context) {
        UiModeManager uiModeManager = (UiModeManager) context.getSystemService("uimode");
        return uiModeManager != null && uiModeManager.getNightMode() == 2;
    }

    public static boolean isPadDevice() {
        return miuix.os.Build.IS_TABLET;
    }

    public static boolean isPowerSaveMode(Context context) {
        return MiuiSettingsUtils.System.getInt(context.getContentResolver(), "POWER_SAVE_MODE_OPEN", 0) == 1 || (MiuiSettingsUtils.Secure.getInt(context.getContentResolver(), "EXTREME_POWER_MODE_ENABLE", 0) == 1);
    }

    public static boolean isShowAlwaysWhenCharging(Context context) {
        return DeviceUtil.INSTANCE.isPad() && 2 == getShowStyle(context);
    }

    public static boolean isShowFingerprintIcon(Context context, DozeHost dozeHost) {
        return dozeHost != null && dozeHost.isGxzwIconShown() && isUnlockWithFingerprintPossible(context, dozeHost);
    }

    public static boolean isShowFingerprintIconQuick(DozeHost dozeHost) {
        return dozeHost != null && dozeHost.isFingerprintUnlockAvailable() && dozeHost.isGxzwIconShown();
    }

    public static boolean isShowSmart(Context context) {
        return isAodEnable(context) && 3 == getShowStyle(context);
    }

    public static boolean isShowTemporary(Context context) {
        return isAodEnable(context) && getShowStyle(context) == 0;
    }

    public static boolean isSuperWallpaperCategory(Context context) {
        String aodUsingCategoryName = getAodUsingCategoryName(context);
        return CategoryConstants.CATEGORY_SUPER_WALLPAPER.equals(aodUsingCategoryName) || CategoryConstants.CATEGORY_BLANK_SUPER_WALLPAPER.equals(aodUsingCategoryName);
    }

    public static boolean isSupportAodAnimateDevice(Context context) {
        return !isPadDevice() && supportAod(context);
    }

    public static boolean isSupportFsAod() {
        return SUPPORT_FULL_AOD;
    }

    public static boolean isSupportGestureWakeup() {
        return FeatureParser.getBoolean("support_gesture_wakeup", false);
    }

    public static boolean isSupportLinkageStyle() {
        return !DeviceUtil.INSTANCE.isPad();
    }

    public static boolean isTimingStyle(Context context) {
        return isAodEnable(context) && getShowStyle(context) == 1;
    }

    public static boolean isUiThread(Thread thread) {
        return thread == Looper.getMainLooper().getThread();
    }

    public static boolean isUnlockWithFingerprintPossible(Context context, DozeHost dozeHost) {
        FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService("fingerprint");
        return fingerprintManager != null && fingerprintManager.isHardwareDetected() && !isFingerprintDisabled(context, dozeHost) && CommonUtils.getEnrolledFingerprintsSize(fingerprintManager) > 0;
    }

    public static boolean isUri(String str) {
        return str != null && str.startsWith("content://");
    }

    public static boolean isUsingLinkageStyle(Context context) {
        if (isAodProcess() ? MiuiSettingsUtils.Secure.getInt(context.getContentResolver(), "aod_style_state", 0) != 1 : CachedSettings.Secure.getInt(context, "aod_style_state", 0) != 1) {
            FlipLinkageStyleController flipLinkageStyleController = FlipLinkageStyleController.INSTANCE;
            if (!flipLinkageStyleController.isUsingFlip(AODApplication.sInstance) || !flipLinkageStyleController.isFlipped()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isUsingSuperWallpaperStyle(Context context) {
        if (isAodProcess()) {
            return MiuiSettingsUtils.Secure.getInt(context.getContentResolver(), "aod_using_super_wallpaper", 0) == 1;
        }
        boolean z = CachedSettings.Secure.getInt(context, "aod_using_super_wallpaper", 0) == 1;
        if (isFlipDevice()) {
            return z && !FlipLinkageStyleController.INSTANCE.isFlipped();
        }
        return z;
    }

    public static boolean isVideoScreenDevice() {
        return FeatureParser.getBoolean("is_video_screen", false);
    }

    public static boolean isWallPaperSuperWallpaper() {
        return CategoryConstants.CATEGORY_SUPER_WALLPAPER.equals(AODApplicationDelegate.mMiuiWallpaperManager.getMiuiWallpaperType(1));
    }

    public static boolean needDelayRegisterSensor() {
        return FOD_SOLUTION != 3;
    }

    public static boolean needDisableAodInPowerSaveMode(Context context) {
        return (isPowerSaveMode(context) && disableAodInPowerSaveModePermitted(context)) || AODSettings.isSuperSaveModeOpen(context);
    }

    public static void onBackHome(Activity activity) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        activity.startActivity(intent);
    }

    public static void onDozeCreate(Context context) {
        String aodUsingCategoryName = getAodUsingCategoryName(context);
        if ((CategoryConstants.CATEGORY_MAML.equals(aodUsingCategoryName) || CategoryConstants.CATEGORY_IMAGE_BG_SELECTOR.equals(aodUsingCategoryName)) && !AODStyleController.getStyleInfo(context, aodUsingCategoryName).isValidForShow(context)) {
            Log.w("MiuiAod.Utils", "StyleInfo is not valid for show, normally because of the aod app's cache was cleaned. Reset to default style. categoryName: " + aodUsingCategoryName);
            setAodUsingCategoryName(context, CategoryConstants.getDefaultCategoryName(context));
        }
        if (isSupportLinkageStyle() || !isUsingLinkageStyle(context)) {
            return;
        }
        Log.w("MiuiAod.Utils", "onDozeCreate not support linkage style but is using linkage style");
        setAodUsingStyleState(context, 2);
    }

    public static Looper provideBroadcastRunningLooper() {
        HandlerThread handlerThread = new HandlerThread("BroadcastRunning", 10);
        handlerThread.start();
        LooperUtils.setSlowLogThresholdMs(handlerThread.getLooper(), BROADCAST_SLOW_DISPATCH_THRESHOLD.longValue(), BROADCAST_SLOW_DELIVERY_THRESHOLD.longValue());
        return handlerThread.getLooper();
    }

    public static void putKeyguardNotificationStatus(Context context, int i) {
        MiuiSettingsUtils.System.putInt(context.getApplicationContext().getContentResolver(), "wakeup_for_keyguard_notification", i);
    }

    public static int px2Dp(int i) {
        return DeviceInfo.px2Dp(i);
    }

    public static boolean reStartActivityMultiWindow(Activity activity) {
        Intent intent = activity.getIntent();
        if (!activity.isInMultiWindowMode() || (intent.getFlags() & 268435456) != 0) {
            return false;
        }
        if (DeviceUtil.INSTANCE.isPhone()) {
            Toast.makeText(activity, R.string.aod_multi_window_not_support, 0).show();
            onBackHome(activity);
        }
        intent.removeFlags(536870912);
        intent.addFlags(268468224);
        activity.startActivity(intent);
        activity.finish();
        return true;
    }

    public static miuix.pickerwidget.date.Calendar refreshCalendar(miuix.pickerwidget.date.Calendar calendar, TimeZone timeZone, long j) {
        if (calendar == null) {
            return new miuix.pickerwidget.date.Calendar(timeZone);
        }
        calendar.setTimeZone(timeZone);
        calendar.setTimeInMillis(j);
        return calendar;
    }

    public static int resolveShowStyle(Context context, int i) {
        if (DeviceUtil.INSTANCE.isPad() && i != 2 && i != 3) {
            i = 3;
        }
        if (i != 0) {
            if (i == 1 || i == 2) {
                if (AODSettings.onlySupportKeycodeGoto()) {
                    return 0;
                }
            } else if (i == 3 && !AttentionManager.supportAttention(context)) {
                return AODSettings.isKeycodeGotoEnable() ? 0 : 2;
            }
        } else if (!AODSettings.isKeycodeGotoEnable()) {
            return 2;
        }
        return i;
    }

    public static void selectPicture(Context context) {
        if (context == null) {
            return;
        }
        if (!useSystemPhotoPicker(context)) {
            selectPictureLegacy(context);
            return;
        }
        try {
            Intent intent = new Intent("android.provider.action.PICK_IMAGES");
            intent.setType("image/*");
            intent.putExtra("android.provider.extra.PICK_IMAGES_MAX", 1);
            intent.putExtra("android.provider.extra.PICK_IMAGES_LAUNCH_TAB", 1);
            ((Activity) context).startActivityForResult(intent, 1);
        } catch (Exception e) {
            Log.e("MiuiAod.Utils", "System PhotoPicker unavailable, using fallback solution", e);
            selectPictureLegacy(context);
        }
    }

    public static void selectPictureLegacy(Context context) {
        try {
            Intent intent = new Intent();
            intent.setPackage("com.miui.gallery");
            if (isIntentResolved(context, intent)) {
                intent.setAction("android.intent.action.PICK");
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                ((Activity) context).startActivityForResult(intent, 1);
            } else {
                intent.setAction("android.intent.action.GET_CONTENT");
                intent.setPackage("com.google.android.documentsui");
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                ((Activity) context).startActivityForResult(intent, 1);
            }
        } catch (ActivityNotFoundException e) {
            Log.e("MiuiAod.Utils", "error...", e);
        }
    }

    public static void setAodEnable(Context context, boolean z) {
        Log.i("MiuiAod.Utils", "setAodEnable: " + z);
        MiuiSettingsUtils.Secure.putInt(context.getContentResolver(), "doze_always_on", z ? 1 : 0);
    }

    public static void setAodModeUserSet(Context context, boolean z) {
        Log.i("MiuiAod.Utils", "setAodModeUserSet: " + z);
        MiuiSettingsUtils.Secure.putInt(context.getContentResolver(), "aod_mode_user_set", z ? 1 : 0);
    }

    public static void setAodUsingCategory(Context context, String str, String str2) {
        Log.i("MiuiAod.Utils", "setAodUsingCategory: " + str + " - " + str2);
        MiuiSettingsUtils.Secure.putString(context.getContentResolver(), "aod_category_name", str);
        MiuiSettingsUtils.Secure.putString(context.getContentResolver(), str, str2);
    }

    public static void setAodUsingCategoryName(Context context, String str) {
        Log.i("MiuiAod.Utils", "setAodUsingCategoryName: " + str);
        MiuiSettingsUtils.Secure.putString(context.getContentResolver(), "aod_category_name", str);
    }

    public static void setAodUsingCategoryParam(Context context, String str, String str2) {
        Log.i("MiuiAod.Utils", "setAodUsingCategoryParam: " + str2);
        MiuiSettingsUtils.Secure.putString(context.getContentResolver(), str, str2);
    }

    public static void setAodUsingStyleState(Context context, int i) {
        Log.i("MiuiAod.Utils", "setAodUsingStyleState:" + i);
        MiuiSettingsUtils.Secure.putInt(context.getContentResolver(), "aod_style_state", i);
    }

    public static void setDisableAodInPowerSaveModePermitted(Context context, boolean z) {
        MiuiSettingsUtils.Secure.putInt(context.getContentResolver(), "permit_disable_aod_in_power_save_mode", z ? 1 : 0);
    }

    public static void setDisplayMode(Context context, int i) {
        MiuiSettingsUtils.Secure.putInt(context.getContentResolver(), "aod_show_style", i);
    }

    public static boolean setFsAodStyle(Context context, int i) {
        return MiuiSettingsUtils.Secure.putInt(context.getContentResolver(), "full_screen_aod_on", i);
    }

    public static boolean setFullAodNotificationEnable(Context context, boolean z) {
        return MiuiSettingsUtils.Secure.putInt(context.getContentResolver(), "full_screen_aod_notification", z ? 1 : 0);
    }

    public static void setLineSpacing(String str, View view, TextView textView) {
        setLineSpacing(str, view, textView, Boolean.FALSE);
    }

    public static void setLineSpacing(String str, View view, TextView textView, Boolean bool) {
        String strValueOf = String.valueOf(view.getResources().getConfiguration().getLocales().get(0));
        Log.d("MiuiAod.Utils", "language is " + strValueOf + "  isSetPadding is " + bool);
        if (strValueOf.equals("bo_CN")) {
            calculate(str, textView, bool, view.getResources().getDimensionPixelOffset(R.dimen.bo_CN_text_size));
        }
    }

    public static void setMiuiOptimizationDisabled(boolean z) {
        sMiuiOptimizationDisabled = z;
    }

    public static void setShowStyle(Context context, int i) {
        Settings.Secure.putInt(context.getContentResolver(), "aod_show_style", i);
    }

    public static boolean setTouchMode(int i, int i2, int i3) {
        try {
            Log.i("MiuiAod.Utils", "setTouchMode: touchId" + i + " mode:" + i2 + " value" + i3);
            ITouchFeature iTouchFeature = ITouchFeature.getInstance();
            if (iTouchFeature != null) {
                return iTouchFeature.setTouchMode(i, i2, i3);
            }
            return false;
        } catch (Exception e) {
            Log.i("MiuiAod.Utils", e.toString());
            return false;
        }
    }

    public static boolean setUsingSuperWallpaperStyleState(Context context, boolean z) {
        Log.i("MiuiAod.Utils", "setUsingSuperWallpaperStyleState: " + z);
        if (z) {
            setAodUsingStyleState(context, 0);
        }
        return MiuiSettingsUtils.Secure.putInt(context.getContentResolver(), "aod_using_super_wallpaper", z ? 1 : 0);
    }

    public static void showSuccessToast(Context context) {
        final Context applicationContext = context.getApplicationContext();
        MainThread.execute(new Runnable() { // from class: com.miui.aod.Utils$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                Utils.$r8$lambda$vEK_ZsIG0GxAThocbIZKwN5C7k0(applicationContext);
            }
        });
    }

    public static boolean stepToImageCommunity(Context context) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("mio://web.vip.miui.com/page/info/mio/mio/singleBoard?boardId=5767010&page=1&ref=aod&page=2&fallback_url=https%3A%2F%2Fweb.vip.miui.com%2Fpage%2Finfo%2Fmio%2Fmio%2FsingleBoard%3FboardId%3D5767010%26page%3D1%26ref%3Daod"));
            intent.addFlags(268435456);
            context.startActivity(intent);
            AnalyticManager.getInstance(context).trackAodStepToImageCommunityAction();
            return true;
        } catch (Exception e) {
            Log.e("MiuiAod.Utils", "error...", e);
            return false;
        }
    }

    public static boolean stepToImageShare(Context context, ArrayList arrayList) {
        try {
            Uri uri = Uri.parse("mio://vipaccount.miui.com/publish?topicId=5577218&checkImageOrigin=true&images=" + new Gson().toJson(arrayList));
            Log.i("MiuiAod.Utils", "stepToImageShare: " + uri.toString());
            Intent intent = new Intent("android.intent.action.VIEW", uri);
            intent.addFlags(268435456);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            Log.e("MiuiAod.Utils", "error...", e);
            return false;
        }
    }

    public static boolean supportAod(Context context) {
        int identifier = context.getResources().getIdentifier("config_dozeAlwaysOnDisplayAvailable", "bool", "android");
        if (identifier > 0) {
            return context.getResources().getBoolean(identifier);
        }
        return false;
    }

    public static boolean supportDisplayModePage(Context context) {
        return supportAod(context) && !isPadDevice();
    }

    public static void updateTouchMode(int i, Context context, boolean z) {
        setTouchMode(i, 11, (getShowStyle(context) == 0 && z) ? 1 : 0);
    }

    public static void updateTouchMode(Context context, boolean z, boolean z2) {
        if (isFoldDevice() || isFlipDevice()) {
            updateTouchMode(1, context, isAodSettingsEnabled(context));
        }
        if (z2) {
            updateTouchMode(0, context, isAodSettingsEnabled(context));
        }
    }

    public static void updateTouchModeForKeepScreenOnIfNeeded(int i, int i2) {
        setTouchMode(i, 19, i2);
    }

    public static void updateTouchModeForKeepScreenOnIfNeeded(int i, boolean z) {
        if (!isFoldDevice() && !isFlipDevice()) {
            updateTouchModeForKeepScreenOnIfNeeded(0, i);
            return;
        }
        Log.d("MiuiAod.Utils", "updateTouchModeForKeepScreenOnIfNeeded: " + z);
        if (i != 1) {
            updateTouchModeForKeepScreenOnIfNeeded(1, i);
            updateTouchModeForKeepScreenOnIfNeeded(0, i);
        } else if (z) {
            updateTouchModeForKeepScreenOnIfNeeded(1, i);
        } else {
            updateTouchModeForKeepScreenOnIfNeeded(0, i);
        }
    }

    public static boolean useSystemPhotoPicker(Context context) {
        Bundle bundle;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo("com.miui.gallery", 128);
            return android.os.Build.VERSION.SDK_INT >= 36 && (bundle = applicationInfo.metaData) != null && bundle.containsKey("os_gallery_cmp_version") && applicationInfo.metaData.getInt("os_gallery_cmp_version", 0) > 0;
        } catch (Exception unused) {
        }
    }

    public static int verifyShowStyle(Context context, int i) {
        int iResolveShowStyle = resolveShowStyle(context, i);
        Log.d("MiuiAod.Utils", "showStyle=" + i + ", newStyle=" + iResolveShowStyle);
        if (i != iResolveShowStyle) {
            setShowStyle(context, iResolveShowStyle);
        }
        return iResolveShowStyle;
    }
}
