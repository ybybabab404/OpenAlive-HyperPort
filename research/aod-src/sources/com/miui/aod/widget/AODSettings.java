package com.miui.aod.widget;

import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.miui.aod.AODApplication;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.attention.AttentionManager;
import com.miui.aod.category.CategoryFactory;
import com.miui.aod.category.SmartCoverStyleInfo;
import com.miui.aod.category.SunCategoryInfo;
import com.miui.aod.category.TextStyleCategoryInfo;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.doze.SettingsHolder;
import com.miui.aod.util.CategoryPrefs;
import com.miui.aod.util.SystemPropertiesUtils;
import com.miui.aod.utils.MiuiSettingsUtils;
import java.util.Locale;
import java.util.TimeZone;
import miui.os.Build;
import miui.util.FeatureParser;
import miuix.pickerwidget.date.Calendar;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AODSettings {
    public static ContentObserver sTemplateInfoObserver;
    public static final String[] SMARTCOVER_ENABLE_DEVICE = {"umi", "cmi", "cas"};
    public static final int AOD_SHOW_STYLE_DEF = !isDefaultTemporaryStyle() ? 1 : 0;
    public static String sTemplateId = "";
    public static boolean mSmartCoverStateClosed = false;
    public static boolean mNeedLinkageWallpaperAnim = false;
    public static boolean sIsFulAod = false;

    public static StyleInfo getClockStyleInfo(Context context) {
        return CategoryFactory.createStyleInfo(context, Utils.getAodUsingCategoryName(context));
    }

    public static String getDateFormat(Context context, boolean z, boolean z2) {
        if (z2) {
            return context.getResources().getString(R.string.aod_lock_screen_date_lunar);
        }
        return context.getResources().getString(z ? R.string.aod_lock_screen_date : R.string.aod_lock_screen_date_12);
    }

    public static int getInt(Context context, String str, int i) {
        int i2 = MiuiSettingsUtils.Secure.getInt(context.getContentResolver(), str, -1);
        return i2 == -1 ? CategoryPrefs.getInt(context, str, i) : i2;
    }

    public static CharSequence[] getShowStyleEntries(Context context) {
        String[] stringArray = Utils.getTemporaryStyleDuration() == 5000 ? context.getResources().getStringArray(R.array.show_style_entries_5) : context.getResources().getStringArray(R.array.show_style_entries);
        if (isKeycodeGotoEnable()) {
            return (!onlySupportKeycodeGoto() || AttentionManager.supportAttention(context)) ? stringArray : new CharSequence[]{stringArray[0]};
        }
        int length = stringArray.length - 1;
        CharSequence[] charSequenceArr = new CharSequence[length];
        System.arraycopy(stringArray, 1, charSequenceArr, 0, length);
        return charSequenceArr;
    }

    public static String getTemplateId(final Context context) {
        if (TextUtils.isEmpty(sTemplateId)) {
            sTemplateId = parseTemplateIdFromSettings(context);
            if (sTemplateInfoObserver == null) {
                sTemplateInfoObserver = new ContentObserver(null) { // from class: com.miui.aod.widget.AODSettings.1
                    @Override // android.database.ContentObserver
                    public void onChange(boolean z) {
                        AODSettings.sTemplateId = AODSettings.parseTemplateIdFromSettings(context);
                    }
                };
                context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("constant_template_editor_info"), false, sTemplateInfoObserver);
            }
        }
        return sTemplateId;
    }

    public static boolean isDefaultTemporaryStyle() {
        return onlySupportKeycodeGoto() || FeatureParser.getBoolean("is_default_temporary_style", false);
    }

    public static boolean isDualClock(Context context) {
        String string;
        boolean z;
        if (Utils.isAodProcess()) {
            boolean z2 = MiuiSettingsUtils.System.getInt(context.getContentResolver(), "auto_dual_clock", 0) == 1;
            string = MiuiSettingsUtils.System.getString(context.getContentResolver(), "resident_timezone");
            z = z2;
        } else {
            z = SettingsHolder.mAutoDualClock == 1;
            string = SettingsHolder.mResidentTimezone;
        }
        return (!z || string == null || TimeZone.getDefault().getID().equals(string)) ? false : true;
    }

    public static boolean isFullScreenGestureNav(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) != 0;
    }

    public static boolean isKeycodeGotoEnable() {
        return FeatureParser.getBoolean("aod_support_keycode_goto_dismiss", false) || isSupportKeycodeByProperty();
    }

    public static boolean isNeedTimeChangedStyle(Context context) {
        StyleInfo clockStyleInfo = getClockStyleInfo(context);
        return (clockStyleInfo instanceof SunCategoryInfo) || clockStyleInfo.isChangeColorAlongTime();
    }

    public static boolean isO2SDevice() {
        return "dijun".equals(SystemPropertiesUtils.INSTANCE.get("ro.product.device", "UNKNOWN"));
    }

    public static boolean isSameDate(Calendar calendar, Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            return false;
        }
        return calendar.format(AODApplication.sInstance, "yyyyMMdd").equals(calendar2.format(AODApplication.sInstance, "yyyyMMdd"));
    }

    public static boolean isSmartCoverClose() {
        return mSmartCoverStateClosed;
    }

    public static boolean isSunStyle(Context context) {
        return getClockStyleInfo(context) instanceof SunCategoryInfo;
    }

    public static boolean isSuperSaveModeOpen(Context context) {
        return (!Utils.isAodProcess() ? SettingsHolder.mPowerSuperSaveModeOpen : MiuiSettingsUtils.System.getInt(context.getContentResolver(), "power_supersave_mode_open", 0)) != 0;
    }

    public static boolean isSupportAodWallpaper(Context context) {
        int identifier = context.getResources().getIdentifier("config_dozeSupportsAodWallpaper", "bool", "android");
        return (identifier > 0 ? context.getResources().getBoolean(identifier) : false) && isSupportNoBlanking();
    }

    public static boolean isSupportKeycodeByProperty() {
        int i = SystemPropertiesUtils.INSTANCE.getInt("ro.vendor.touchfeature.type", 0);
        Log.i("AODSettings", "touchfeature type:" + i);
        return ((i >> 4) & 1) == 1;
    }

    public static boolean isSupportNoBlanking() {
        return true;
    }

    public static boolean isSupportSmartCoverAodStyle() {
        return !Utils.isPadDevice() && isSupportedSmartCoverDevice();
    }

    public static boolean isSupportedDevice(String[] strArr) {
        String str = Build.DEVICE;
        for (String str2 : strArr) {
            if (str.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSupportedSmartCoverDevice() {
        int identifier = Resources.getSystem().getIdentifier("config_smartCoverEnabled", "bool", "android.miui");
        return identifier > 0 ? Resources.getSystem().getBoolean(identifier) : isSupportedDevice(SMARTCOVER_ENABLE_DEVICE);
    }

    public static boolean needFullAod() {
        return sIsFulAod;
    }

    public static boolean needKeepScreenOnAtFirst() {
        if (needFullAod()) {
            return needLinkageWallpaperAnim();
        }
        if (!isSupportNoBlanking() || Utils.isMiuiOptimizationDisabled() || Utils.aodGone(AODApplication.sInstance)) {
            return false;
        }
        if (Utils.isUsingSuperWallpaperStyle(AODApplication.sInstance)) {
            return true;
        }
        return Utils.isLinkageState(AODApplication.sInstance) && mNeedLinkageWallpaperAnim;
    }

    public static boolean needLinkageWallpaperAnim() {
        return mNeedLinkageWallpaperAnim;
    }

    public static boolean notificationOneIconMode(StyleInfo styleInfo) {
        return (styleInfo instanceof TextStyleCategoryInfo) || (styleInfo instanceof SmartCoverStyleInfo);
    }

    public static boolean onlySupportKeycodeGoto() {
        return FeatureParser.getBoolean("is_only_support_keycode_goto", false);
    }

    public static String parseTemplateIdFromSettings(Context context) {
        try {
            JSONObject jSONObjectOptJSONObject = new JSONObject(Settings.Secure.getString(context.getContentResolver(), "constant_template_editor_info")).optJSONObject("lockscreenInfo");
            JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject == null ? null : jSONObjectOptJSONObject.optJSONObject("clockInfo");
            return jSONObjectOptJSONObject2 == null ? "" : jSONObjectOptJSONObject2.optString("templateId");
        } catch (Exception e) {
            Log.w("AODSettings", "parseTemplateIdFromSettings: error: " + e.getMessage());
            return "";
        }
    }

    public static void putInt(Context context, String str, int i) {
        MiuiSettingsUtils.Secure.putInt(context.getContentResolver(), str, i);
    }

    public static void resetThreeGestureState(Context context) {
        Log.w("AODSettings", "resetThreeGestureState: ");
        MiuiSettingsUtils.System.putInt(context.getContentResolver(), "enable_three_gesture", 1);
    }

    public static void setNeedFullAod(boolean z) {
        sIsFulAod = z;
    }

    public static void setNeedLinkageWallpaperAnim(boolean z) {
        mNeedLinkageWallpaperAnim = z;
    }

    public static void setSmartCoverState(boolean z) {
        mSmartCoverStateClosed = z;
    }

    public static boolean supportShowLunarDate(Context context) {
        return !isDualClock(context) && Locale.CHINESE.getLanguage().equals(Locale.getDefault().getLanguage());
    }

    public static int translateShowStyleValue2Index(int i) {
        return isKeycodeGotoEnable() ? i : i - 1;
    }
}
