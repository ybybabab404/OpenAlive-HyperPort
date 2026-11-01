package com.miui.clock.utils;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.widget.TextView;
import com.miui.clock.MiuiTextGlassView;
import com.miui.clock.R;
import com.miui.clock.module.BaseFontStyle;
import com.miui.clock.module.ClassicContentStyle;
import com.miui.clock.module.FontBeiHaiBei;
import com.miui.clock.module.FontMiSansDemiBold;
import com.miui.clock.module.FontMiSansExtraLight;
import com.miui.clock.module.FontMiSansNormal;
import com.miui.clock.module.FontMiSansRoundLock;
import com.miui.clock.module.FontMiSerif;
import com.miui.clock.module.MiuiClassicClockInfoBase;
import java.util.HashMap;
import java.util.Locale;
import miuix.animation.utils.EaseManager;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class ClassicClockTimeUtils {
    public static final String[] NUMBER = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
    public static String[] sSolarTermsCache;
    public static String sSolarTermsCountry;

    public static class FontTextInfo {
        public float letterSpacing;
        public float textSize;
        public int textSizeUnit;
        public Typeface typeface;

        public FontTextInfo(Typeface typeface, float f, int i, float f2) {
            this.typeface = typeface;
            this.textSize = f;
            this.letterSpacing = f2;
            this.textSizeUnit = i;
        }

        public static void setFontStyle(TextView textView, FontTextInfo fontTextInfo) {
            textView.setLetterSpacing(fontTextInfo.letterSpacing);
            textView.setTextSize(fontTextInfo.textSizeUnit, fontTextInfo.textSize);
            textView.setTypeface(fontTextInfo.typeface);
        }
    }

    public static void appendHour(StringBuilder sb, int i, boolean z) {
        if (i <= 10) {
            sb.append(NUMBER[i]);
        } else if (i < 20) {
            String[] strArr = NUMBER;
            sb.append(strArr[10]);
            sb.append(strArr[i % 10]);
        } else if (i == 20) {
            String[] strArr2 = NUMBER;
            sb.append(strArr2[2]);
            sb.append(strArr2[10]);
        } else {
            String[] strArr3 = NUMBER;
            sb.append(strArr3[i / 10]);
            if (z) {
                sb.append(strArr3[10]);
            }
            sb.append(strArr3[i % 10]);
        }
        if (FontUtils.isTraditionalChinese()) {
            sb.append("時");
        } else {
            sb.append("时");
        }
    }

    public static void appendMin(StringBuilder sb, int i, boolean z) {
        if (i == 0) {
            sb.append("整");
            return;
        }
        if (i < 10) {
            String[] strArr = NUMBER;
            sb.append(strArr[0]);
            sb.append(strArr[i]);
            return;
        }
        if (i == 10) {
            sb.append(NUMBER[10]);
            sb.append("分");
            return;
        }
        if (i < 20) {
            String[] strArr2 = NUMBER;
            sb.append(strArr2[10]);
            sb.append(strArr2[i % 10]);
            if (z) {
                sb.append("分");
                return;
            }
            return;
        }
        int i2 = i % 10;
        if (i2 != 0) {
            String[] strArr3 = NUMBER;
            sb.append(strArr3[i / 10]);
            if (z) {
                sb.append(strArr3[10]);
            }
            sb.append(strArr3[i2]);
            return;
        }
        String[] strArr4 = NUMBER;
        sb.append(strArr4[i / 10]);
        sb.append(strArr4[10]);
        if (z) {
            sb.append("分");
        }
    }

    public static String convertYearToChinese(String str) {
        HashMap map = new HashMap();
        map.put('0', "〇");
        map.put('1', "一");
        map.put('2', "二");
        map.put('3', "三");
        map.put('4', "四");
        map.put('5', "五");
        map.put('6', "六");
        map.put('7', "七");
        map.put('8', "八");
        map.put('9', "九");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            sb.append((String) map.get(Character.valueOf(str.charAt(i))));
        }
        return sb.toString();
    }

    public static FontTextInfo getBigFontTextInfo(Context context, int i, boolean z, int i2) {
        int i3;
        Typeface bigFontTypeface = getBigFontTypeface(i, z);
        float f = -0.02f;
        if (i != 25) {
            if (z) {
                i3 = R.dimen.classic_time_area_text_size_chs;
                f = -0.06f;
            } else {
                i3 = R.dimen.classic_time_area_text_size_other;
            }
        } else if (z) {
            i3 = R.dimen.classic_time_area_text_size_chs;
            f = -0.04f;
        } else {
            i3 = R.dimen.classic_time_area_text_size_other_beihaibei;
        }
        return new FontTextInfo(bigFontTypeface, (FontUtils.shouldScaleMultilingual(i2) ? 0.7f : 1.0f) * DeviceConfig.getDimen(i3, context), 0, f);
    }

    public static Typeface getBigFontTypeface(int i, boolean z) {
        boolean zIsTraditionalChinese = FontUtils.isTraditionalChinese();
        switch (i) {
            case 22:
                return z ? FontUtils.getMiSans(280) : FontUtils.getMiSans(250);
            case 23:
                return z ? FontUtils.getMiSans(520) : FontUtils.getMiSans(450);
            case 24:
                return z ? FontUtils.getMiSansRounded(430, !zIsTraditionalChinese) : FontUtils.getMiSansRounded(330, !zIsTraditionalChinese);
            case 25:
                return FontUtils.getOtherFont(!zIsTraditionalChinese ? "miclock-beihaibei-sc-regular" : "miclock-beihaibei-tc-regular");
            case 26:
                return FontUtils.getOtherFont(!zIsTraditionalChinese ? "miclock-serif-sc-regular" : "miclock-serif-tc-regular");
            default:
                return z ? FontUtils.getMiSans(330) : FontUtils.getMiSans(305);
        }
    }

    public static FontTextInfo getBigMsgStyleTextInfo(Context context, int i, int i2) {
        boolean zEquals = Locale.CHINESE.getLanguage().equals(Locale.getDefault().getLanguage());
        switch (i2) {
            case EaseManager.EaseStyleDef.PERLIN /* 201 */:
            case 203:
            case 204:
            case 206:
            case 207:
            case 208:
                return getBigFontTextInfo(context, i, zEquals, i2);
            case 202:
            case 205:
            case 209:
            case 210:
            default:
                return getBigFontTextInfo(context, i, false, i2);
            case 211:
            case 212:
                return getBigFontTextInfo(context, i, true, i2);
        }
    }

    public static String getChsNumber(int i, boolean z) {
        if (i < 10) {
            if (!z) {
                return NUMBER[i];
            }
            StringBuilder sb = new StringBuilder();
            String[] strArr = NUMBER;
            sb.append(strArr[0]);
            sb.append(strArr[i]);
            return sb.toString();
        }
        if (i == 10) {
            return NUMBER[10];
        }
        int i2 = i % 10;
        if (i < 20) {
            StringBuilder sb2 = new StringBuilder();
            String[] strArr2 = NUMBER;
            sb2.append(strArr2[10]);
            sb2.append(strArr2[i2]);
            return sb2.toString();
        }
        if (i2 == 0) {
            StringBuilder sb3 = new StringBuilder();
            String[] strArr3 = NUMBER;
            sb3.append(strArr3[i / 10]);
            sb3.append(strArr3[10]);
            return sb3.toString();
        }
        StringBuilder sb4 = new StringBuilder();
        String[] strArr4 = NUMBER;
        sb4.append(strArr4[i / 10]);
        sb4.append(strArr4[10]);
        sb4.append(strArr4[i2]);
        return sb4.toString();
    }

    public static String getChsNumberOnHour(int i) {
        StringBuilder sb = new StringBuilder();
        if (i < 10) {
            sb.append(NUMBER[i]);
        } else if (i < 20) {
            sb.append(NUMBER[10]);
        } else {
            sb.append(NUMBER[i / 10]);
        }
        return sb.toString();
    }

    public static String getChsNumberOnMinute(int i) {
        StringBuilder sb = new StringBuilder();
        if (i <= 10) {
            sb.append("時");
        } else {
            int i2 = i % 10;
            if (i2 > 0) {
                sb.append(NUMBER[i2]);
            } else {
                sb.append(NUMBER[10]);
            }
        }
        return sb.toString();
    }

    public static String getChsTime(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        if (i <= 10) {
            appendHour(sb, i, true);
            appendMin(sb, i2, true);
        } else if (i > 20 && i2 != 0) {
            appendHour(sb, i, false);
            appendMin(sb, i2, false);
        } else {
            appendHour(sb, i, true);
            appendMin(sb, i2, false);
        }
        return sb.toString();
    }

    public static String getChsTimeHour(int i, boolean z) {
        StringBuilder sb = new StringBuilder();
        if (i <= 10) {
            if (z) {
                sb.append(NUMBER[i]);
            } else {
                sb.append("時");
            }
        } else if (i < 20) {
            if (z) {
                sb.append(NUMBER[10]);
            } else {
                sb.append(NUMBER[i % 10]);
            }
        } else if (z) {
            sb.append(NUMBER[i / 10]);
        } else {
            int i2 = i % 10;
            sb.append(i2 == 0 ? NUMBER[10] : NUMBER[i2]);
        }
        return sb.toString();
    }

    public static String getChsTimeHour(boolean z, boolean z2, boolean z3, Calendar calendar) {
        int hourInt = getHourInt(z, calendar);
        return z2 ? getChsNumberOnHour(hourInt) : getChsTimeHour(hourInt, z3);
    }

    public static String getChsTimeMinute(boolean z, boolean z2, Calendar calendar) {
        StringBuilder sb = new StringBuilder();
        int i = calendar.get(20);
        int hourInt = getHourInt(z, calendar);
        if (i == 0) {
            return getChsNumberOnMinute(hourInt);
        }
        if (i < 10) {
            if (z2) {
                sb.append(NUMBER[0]);
            } else {
                sb.append(NUMBER[i]);
            }
        } else if (i == 10) {
            if (z2) {
                sb.append(NUMBER[10]);
            } else {
                sb.append("分");
            }
        } else if (z2) {
            int i2 = i / 10;
            if (i2 == 1) {
                sb.append(NUMBER[10]);
            } else {
                sb.append(NUMBER[i2]);
            }
        } else {
            int i3 = i % 10;
            if (i3 == 0) {
                sb.append(NUMBER[10]);
            } else {
                sb.append(NUMBER[i3]);
            }
        }
        return sb.toString();
    }

    public static String getChsTimeWholeHour(boolean z, Calendar calendar) {
        StringBuilder sb = new StringBuilder();
        int hourInt = getHourInt(z, calendar);
        if (hourInt <= 10) {
            sb.append(NUMBER[hourInt]);
        } else if (hourInt < 20) {
            String[] strArr = NUMBER;
            sb.append(strArr[10]);
            sb.append(strArr[hourInt % 10]);
        } else if (hourInt == 20) {
            String[] strArr2 = NUMBER;
            sb.append(strArr2[2]);
            sb.append(strArr2[10]);
        } else {
            String[] strArr3 = NUMBER;
            sb.append(strArr3[2]);
            sb.append(strArr3[hourInt % 10]);
        }
        sb.append("時");
        return sb.toString();
    }

    public static String getChsTimeWholeMinute(Calendar calendar) {
        StringBuilder sb = new StringBuilder();
        int minInt = getMinInt(calendar);
        if (minInt < 10) {
            String[] strArr = NUMBER;
            sb.append(strArr[0]);
            sb.append(strArr[minInt]);
        } else if (minInt == 10) {
            sb.append(NUMBER[10]);
            sb.append("分");
        } else if (minInt < 20) {
            String[] strArr2 = NUMBER;
            sb.append(strArr2[10]);
            sb.append(strArr2[minInt % 10]);
        } else {
            int i = minInt % 10;
            if (i == 0) {
                String[] strArr3 = NUMBER;
                sb.append(strArr3[minInt / 10]);
                sb.append(strArr3[10]);
            } else {
                String[] strArr4 = NUMBER;
                sb.append(strArr4[minInt / 10]);
                sb.append(strArr4[i]);
            }
        }
        return sb.toString();
    }

    public static float getClassicAodFontWeightRadio() {
        return 0.7f;
    }

    public static FontTextInfo getClassicGlassViewTextInfo(Context context, String str, int i, int i2, int i3, boolean z) {
        if (z) {
            return getPlusFontTextInfo(context, str, i, i2);
        }
        return (i3 == 301 || i3 == 304 || i3 == 305) ? getBigFontTextInfo(context, i, true, i3) : getBigFontTextInfo(context, i, false, i3);
    }

    public static int getClassicMaxClockTop(Context context) {
        Rect screenRect = DeviceConfig.getScreenRect(context);
        int iHeight = screenRect.height();
        int iWidth = screenRect.width();
        if (DeviceConfig.isPhoneDevice() || (DeviceConfig.isFoldDevice() && !DeviceConfig.inFoldLargeScreen(context))) {
            iHeight = Math.max(iHeight, iWidth);
        }
        return ((iHeight - (MutiDeviceDimenAdapter.getMultiDeviceDimen(context, R.dimen.miui_classic_max_time_area_text_height, true) + DeviceConfig.getDimen(R.dimen.miui_classic_max_time_area_margin_top, context))) / 2) - ((int) (iHeight * getClassicMaxOffsetRadio()));
    }

    public static float getClassicMaxOffsetRadio() {
        if (DeviceConfig.isFoldDevice()) {
            return 0.03f;
        }
        return DeviceConfig.isPadDevice() ? 0.026f : 0.0376f;
    }

    public static Typeface getClassicMaxTypeface(Context context, int i, int i2) {
        if (i2 <= 0) {
            i2 = 420;
        }
        switch (i) {
            case 22:
                return FontUtils.getMiSansRoundedFVF(context, i2);
            case 23:
                return FontUtils.getMiSerifFVF(context, i2);
            case 24:
                return FontUtils.getMiSansSquareFVF(context, i2);
            case 25:
                return FontUtils.getMiHausVF(context, i2);
            default:
                return FontUtils.getMiSansRCFVF(context, i2);
        }
    }

    /* JADX WARN: Code duplicated, block: B:4:0x0008  */
    public static String getClassicTime(Context context, Calendar calendar, boolean z, int i, boolean z2) {
        if (!ClassicContentStyle.Time.isTimeType(i)) {
            i = 300;
        } else if (z2) {
            switch (i) {
                case 302:
                case 303:
                    i = 300;
                    break;
                case 304:
                case 305:
                    i = 301;
                    break;
            }
        }
        int hourInt = getHourInt(z, calendar);
        int i2 = calendar.get(20);
        switch (i) {
            case 300:
                return context.getResources().getString(R.string.format_hour_minute, getTimeString(hourInt, z), getTimeString(i2, true));
            case 301:
                return getChsTime(hourInt, i2);
            case 302:
                return getTimeString(hourInt, true);
            case 303:
                return getTimeString(i2, true);
            case 304:
                return context.getResources().getString(R.string.format_chs_hour, getChsNumber(hourInt, false));
            case 305:
                return context.getResources().getString(R.string.format_chs_minute, getChsNumber(i2, i2 != 0));
            default:
                return "";
        }
    }

    public static String getClassicTimeDesc(Context context, Calendar calendar, boolean z, int i) {
        if (!ClassicContentStyle.Time.isTimeType(i)) {
            i = 300;
        }
        int hourInt = getHourInt(z, calendar);
        String timeString = getTimeString(hourInt, false);
        int i2 = calendar.get(20);
        String timeString2 = getTimeString(i2, i2 != 0);
        switch (i) {
            case 301:
                if (i2 == 0) {
                    return getChsNumber(hourInt, false) + "时整";
                }
                return getChsNumber(hourInt, false) + "时" + getChsNumber(i2, true) + "分";
            case 302:
                return context.getResources().getString(R.string.format_number_hour_desc, timeString);
            case 303:
                return context.getResources().getString(R.string.format_minute_desc, timeString2);
            case 304:
                return context.getResources().getString(R.string.format_chs_hour, timeString);
            case 305:
                return context.getResources().getString(R.string.format_chs_minute, timeString2);
            default:
                return context.getResources().getString(R.string.format_hour_minute, timeString, timeString2);
        }
    }

    public static BaseFontStyle getClassicTimeFontStyle(int i) {
        switch (i) {
            case 22:
                return new FontMiSansExtraLight();
            case 23:
                return new FontMiSansDemiBold();
            case 24:
                return new FontMiSansRoundLock();
            case 25:
                return new FontBeiHaiBei();
            case 26:
                return new FontMiSerif();
            default:
                return new FontMiSansNormal();
        }
    }

    public static String getDateWithWeekString(Context context, Calendar calendar) {
        return calendar.format(context, context.getString(R.string.format_month_day_week)).toUpperCase();
    }

    public static String getDateWithWeekStringStyle2(Context context, Calendar calendar) {
        return calendar.format(context, context.getString(R.string.miui_pad_c_date)).toUpperCase();
    }

    public static String getDayOfFullWeekString(Context context, Calendar calendar) {
        return calendar.format(context, context.getString(R.string.format_full_day_of_week));
    }

    public static int getDayOfMonthInt(Calendar calendar) {
        return calendar.get(9);
    }

    public static String getDayOfWeekString(Context context, Calendar calendar) {
        return calendar.format(context, context.getString(R.string.classic_calendar_day_of_week_format)).toUpperCase();
    }

    public static String getEngOrder(int i) {
        int i2 = i % 10;
        if (i2 == 1) {
            return "ST";
        }
        if (i2 != 2) {
            return i2 != 3 ? "TH" : "RD";
        }
        return "ND";
    }

    public static int getHourInt(boolean z, Calendar calendar) {
        if (z) {
            return calendar.get(18);
        }
        int i = calendar.get(18);
        if (i == 0) {
            return 12;
        }
        return i < 13 ? i : i - 12;
    }

    public static String getLunarCalendarString(Context context, Calendar calendar) {
        return calendar.format(context, "N月e");
    }

    public static int getMinInt(Calendar calendar) {
        return calendar.get(20);
    }

    public static int getMonthOfYearInt(Calendar calendar) {
        return calendar.get(5) + 1;
    }

    public static FontTextInfo getPlusFontTextInfo(Context context, String str, int i, int i2) {
        if (context == null) {
            return null;
        }
        return new FontTextInfo(getClassicMaxTypeface(context, i, i2), DeviceConfig.getDimen(R.dimen.classic_time_area_plus_text_size, context), 0, (str.equals("classic_max") || str.equals("classic_max_minute") || str.equals("classic_max_single")) ? -0.04f : -0.02f);
    }

    public static int getRightTopMsgMarginTopResId(int i) {
        return i == 25 ? R.dimen.classic_content_area_right_top_msg_font_e_margin_top : R.dimen.classic_content_area_right_top_msg_margin_top;
    }

    public static int getRightTopMsgWeatherMarginTopResId(int i) {
        return i == 25 ? R.dimen.classic_content_area_right_top_msg_weather_font_e_margin_top : R.dimen.classic_content_area_right_top_msg_weather_margin_top;
    }

    public static String getSexagenaryCycleString(Context context, Calendar calendar) {
        return calendar.format(context, "YY年");
    }

    public static String getSolarTerms(Context context, Calendar calendar) {
        int[] iArr = (int[]) ReflectUtils.getDeclaredFieldValue(calendar, Calendar.class, "mFields", int[].class);
        if (iArr == null || iArr.length <= 15) {
            return "";
        }
        int i = iArr[15];
        String country = context.getResources().getConfiguration().locale.getCountry();
        if (!"HK".equals(country) && !"TW".equals(country)) {
            country = "CN";
        }
        if (sSolarTermsCache == null || !country.equals(sSolarTermsCountry)) {
            sSolarTermsCache = context.getResources().getStringArray(R.array.miui_lock_solar_terms);
            sSolarTermsCountry = country;
        }
        if (i < 0) {
            return "";
        }
        String[] strArr = sSolarTermsCache;
        return i < strArr.length ? strArr[i] : "";
    }

    public static String getTimeString(int i, boolean z) {
        if (i >= 10 || !z) {
            return String.valueOf(i);
        }
        return "0" + i;
    }

    public static Boolean hasIcon(String str) {
        return Boolean.valueOf(str.contains("|"));
    }

    public static boolean isPlus(String str) {
        return TextUtils.equals(str, "classic_plus") || TextUtils.equals(str, "classic_plus_small") || TextUtils.equals(str, "classic_max") || TextUtils.equals(str, "classic_max_minute") || TextUtils.equals(str, "classic_max_single");
    }

    public static void setBigMsgViewStyle(TextView textView, int i, int i2) {
        if (textView == null) {
            return;
        }
        FontTextInfo.setFontStyle(textView, getBigMsgStyleTextInfo(textView.getContext(), i, i2));
    }

    public static void setClassicTimeColor(MiuiTextGlassView miuiTextGlassView, int i) {
        if (i != 0) {
            miuiTextGlassView.setTextColor(i);
        }
    }

    public static void setClassicTimeColor(MiuiTextGlassView miuiTextGlassView, MiuiClassicClockInfoBase miuiClassicClockInfoBase) {
        setClassicTimeColor(miuiTextGlassView, miuiClassicClockInfoBase.getPrimaryColor());
    }

    public static void setClassicTimeStyle(MiuiTextGlassView miuiTextGlassView, int i, int i2, int i3, boolean z, boolean z2) {
        if (miuiTextGlassView == null) {
            return;
        }
        if (i != 0) {
            miuiTextGlassView.setTextColor(i);
        }
        miuiTextGlassView.setEnableDiffusion(z);
        FontTextInfo.setFontStyle(miuiTextGlassView, getClassicGlassViewTextInfo(miuiTextGlassView.getContext(), "classic", i2, 420, i3, z2));
        if (z) {
            miuiTextGlassView.setFontStyle(getClassicTimeFontStyle(i2));
        }
    }

    public static void setClassicTimeStyle(MiuiTextGlassView miuiTextGlassView, MiuiClassicClockInfoBase miuiClassicClockInfoBase) {
        setClassicTimeStyle(miuiTextGlassView, miuiClassicClockInfoBase.getTemplateId(), miuiClassicClockInfoBase.getPrimaryColor(), miuiClassicClockInfoBase.getStyle(), miuiClassicClockInfoBase.getClockWeight(), miuiClassicClockInfoBase.getClassicLine2(), miuiClassicClockInfoBase.isEnableDiffusion(), isPlus(miuiClassicClockInfoBase.getType()));
    }

    public static void setClassicTimeStyle(MiuiTextGlassView miuiTextGlassView, String str, int i, int i2, int i3, int i4, boolean z, boolean z2) {
        if (miuiTextGlassView == null) {
            return;
        }
        if (i != 0) {
            miuiTextGlassView.setTextColor(i);
        }
        miuiTextGlassView.setEnableDiffusion(z);
        FontTextInfo.setFontStyle(miuiTextGlassView, getClassicGlassViewTextInfo(miuiTextGlassView.getContext(), str, i2, i3, i4, z2));
        if (z) {
            miuiTextGlassView.setFontStyle(getClassicTimeFontStyle(i2));
        }
    }
}
