package com.miui.aod.theme.beans;

import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ThemeBean {

    @SerializedName("battery_enable")
    public int mBatteryEnable;

    @SerializedName("date_battery_color")
    public String mDateAndBatteryColor;

    @SerializedName("duration_expected")
    public int mDurationExpected;

    @SerializedName("i18n_theme_description")
    public Map<String, String> mI18nThemeDescription;

    @SerializedName("i18n_theme_name")
    public Map<String, String> mI18nThemeName;

    @SerializedName("theme_identity")
    public String mIdentity;

    @SerializedName("lunar_calendar_enable")
    public int mLunarCalendarEnable;

    @SerializedName("notification_enable")
    public int mNotificationEnable;

    @SerializedName("support_battery")
    public int mSupportBattery;

    @SerializedName("support_lunar_calendar")
    public int mSupportLunarCalendar;

    @SerializedName("support_notification")
    public int mSupportNotification;

    @SerializedName("temporary_theme")
    public int mTemporaryTheme;

    @SerializedName("theme_description")
    public String mThemeDescription;

    @SerializedName("theme_dir_name")
    public String mThemeDirName;

    @SerializedName("theme_name")
    public String mThemeName;

    @SerializedName("theme_type")
    public String mThemeType;

    private static String resolveLocalized(Map<String, String> map, String str) {
        if (map != null && !map.isEmpty()) {
            Locale locale = Locale.getDefault();
            String str2 = map.get(locale.toString());
            if (!TextUtils.isEmpty(str2)) {
                return str2;
            }
            String str3 = map.get(locale.getLanguage());
            if (!TextUtils.isEmpty(str3)) {
                return str3;
            }
            String str4 = map.get("fallback");
            if (!TextUtils.isEmpty(str4)) {
                return str4;
            }
        }
        return str;
    }

    public String getLocalizedThemeDescription() {
        return resolveLocalized(this.mI18nThemeDescription, this.mThemeDescription);
    }

    public String getLocalizedThemeName() {
        return resolveLocalized(this.mI18nThemeName, this.mThemeName);
    }

    public boolean isSameHistoryRecord(ThemeBean themeBean) {
        return isSameTheme(themeBean);
    }

    public boolean isSameTheme(ThemeBean themeBean) {
        String str;
        return (themeBean == null || (str = this.mIdentity) == null || !str.equals(themeBean.mIdentity)) ? false : true;
    }
}
