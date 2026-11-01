package com.miui.clock.aesthetics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.Guideline;
import androidx.exifinterface.media.ExifInterface;
import com.miui.clock.MiuiClockPreviewBaseView;
import com.miui.clock.R;
import com.miui.clock.module.WeatherBean;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.DataUtils;
import com.miui.clock.utils.FontUtils;
import com.xiaomi.onetrack.util.ac;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Locale;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class MiuiAestheticsAPreviewView extends MiuiClockPreviewBaseView {
    public boolean clockIsWide;
    public Space clockSpace;
    public Guideline guidelineV50;
    public Constructor mConstructorUserHandle;
    public int mCurrentUserId;
    public Method mMethodCreateContextAsUser;
    public WeatherBean mWeatherBean;
    public TextView tvData;
    public TextView tvDataWeek;
    public TextView tvHour;
    public TextView tvMinute;
    public TextView tvWeather;
    public TextView tvWeatherUnit;

    public MiuiAestheticsAPreviewView(@NonNull Context context) {
        super(context);
    }

    public MiuiAestheticsAPreviewView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MiuiAestheticsAPreviewView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public int getHourInt(boolean z, Calendar calendar) {
        if (z) {
            return calendar.get(18);
        }
        int i = calendar.get(18);
        if (i == 0) {
            return 12;
        }
        return i < 13 ? i : i - 12;
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public float getScaleRadio() {
        return 0.5f;
    }

    public final Context getSpecialContext(Context context) {
        if (this.mCurrentUserId == 0) {
            return context;
        }
        String packageName = context.getPackageName();
        if ("com.android.systemui".equals(packageName) || "com.miui.aod".equals(packageName)) {
            try {
                Method method = this.mMethodCreateContextAsUser;
                Class cls = Integer.TYPE;
                if (method == null) {
                    this.mMethodCreateContextAsUser = Context.class.getMethod("createContextAsUser", UserHandle.class, cls);
                }
                if (this.mConstructorUserHandle == null) {
                    this.mConstructorUserHandle = UserHandle.class.getConstructor(cls);
                }
                return (Context) this.mMethodCreateContextAsUser.invoke(context, (UserHandle) this.mConstructorUserHandle.newInstance(Integer.valueOf(this.mCurrentUserId)), 1);
            } catch (Exception e) {
                Log.e("MiuiAestheticsAPreviewView", "get special context fail", e);
            }
        }
        return context;
    }

    public final WeatherBean getWeatherBeanSync(Context context) {
        return DataUtils.getWeatherBean(new WeakReference(context), ExifInterface.GPS_MEASUREMENT_2D);
    }

    public final boolean hasWeatherType() {
        return this.mStyle == 2;
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void init() {
        super.init();
        View viewInflate = View.inflate(this.mContext, R.layout.miui_clock_layout_aesthetics_a_preview, this);
        int i = R.id.guideline;
        Guideline guideline = (Guideline) viewInflate.findViewById(i);
        this.guidelineV50 = guideline;
        guideline.setGuidelinePercent(0.5f);
        this.guidelineV50 = (Guideline) viewInflate.findViewById(i);
        this.clockSpace = (Space) viewInflate.findViewById(R.id.clock_space);
        this.tvHour = (TextView) viewInflate.findViewById(R.id.tv_hour);
        this.tvMinute = (TextView) viewInflate.findViewById(R.id.tv_minute);
        this.tvWeather = (TextView) viewInflate.findViewById(R.id.tv_weather);
        this.tvWeatherUnit = (TextView) viewInflate.findViewById(R.id.tv_weather_unit);
        this.tvData = (TextView) viewInflate.findViewById(R.id.tv_data);
        this.tvDataWeek = (TextView) viewInflate.findViewById(R.id.tv_data_week);
    }

    public final void initTextParams() {
        Typeface miSans = FontUtils.getMiSans(450);
        Typeface aestheticsTypeFace = FontUtils.getAestheticsTypeFace(this.clockIsWide);
        this.tvHour.setTypeface(aestheticsTypeFace);
        this.tvMinute.setTypeface(aestheticsTypeFace);
        this.tvWeather.setTypeface(aestheticsTypeFace);
        this.tvWeatherUnit.setTypeface(aestheticsTypeFace);
        this.tvData.setTypeface(miSans);
        this.tvDataWeek.setTypeface(miSans);
        float dimenWithScale = this.clockIsWide ? getDimenWithScale(R.dimen.miui_aesthetics_a_hour_wide_text_size) : getDimenWithScale(R.dimen.miui_aesthetics_a_hour_narrow_text_size);
        float dimenWithScale2 = this.clockIsWide ? getDimenWithScale(R.dimen.miui_aesthetics_a_data_wide_text_size) : getDimenWithScale(R.dimen.miui_aesthetics_a_data_narrow_text_size);
        this.tvHour.setTextSize(0, dimenWithScale);
        this.tvMinute.setTextSize(0, dimenWithScale);
        this.tvWeather.setTextSize(0, dimenWithScale);
        this.tvWeatherUnit.setTextSize(0, dimenWithScale);
        this.tvData.setTextSize(0, dimenWithScale2);
        this.tvDataWeek.setTextSize(0, dimenWithScale2);
    }

    public final void initViewParams() {
        Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(-1, -2);
        layoutParams.topToTop = 0;
        layoutParams.leftToLeft = this.guidelineV50.getId();
        layoutParams.rightToRight = this.guidelineV50.getId();
        Constraints.LayoutParams layoutParams2 = new Constraints.LayoutParams(-2, -2);
        Constraints.LayoutParams layoutParams3 = new Constraints.LayoutParams(-2, -2);
        Constraints.LayoutParams layoutParams4 = new Constraints.LayoutParams(-2, -2);
        Constraints.LayoutParams layoutParams5 = new Constraints.LayoutParams(-2, -2);
        Constraints.LayoutParams layoutParams6 = new Constraints.LayoutParams(-2, -2);
        Constraints.LayoutParams layoutParams7 = new Constraints.LayoutParams(-2, -2);
        layoutParams2.topToTop = this.clockSpace.getId();
        layoutParams2.leftToLeft = this.clockSpace.getId();
        layoutParams2.rightToRight = this.clockSpace.getId();
        layoutParams3.topToTop = this.clockSpace.getId();
        layoutParams3.leftToLeft = this.clockSpace.getId();
        layoutParams3.rightToRight = this.clockSpace.getId();
        layoutParams4.topToTop = this.clockSpace.getId();
        layoutParams4.leftToLeft = this.clockSpace.getId();
        layoutParams4.rightToRight = this.clockSpace.getId();
        layoutParams5.topToTop = this.clockSpace.getId();
        layoutParams5.leftToLeft = this.clockSpace.getId();
        layoutParams5.rightToRight = this.clockSpace.getId();
        layoutParams6.topToTop = this.clockSpace.getId();
        layoutParams6.rightToRight = this.tvMinute.getId();
        layoutParams7.topToTop = this.clockSpace.getId();
        layoutParams7.leftToRight = this.tvMinute.getId();
        if (this.clockIsWide) {
            ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimenWithScale(R.dimen.miui_aesthetics_a_pre_wide_height);
            layoutParams.setMargins(0, getDimenWithScale(R.dimen.miui_aesthetics_a_pre_wide_margin_top), 0, 0);
            layoutParams3.setMargins(0, getDimenWithScale(R.dimen.miui_aesthetics_a_clock_wide_min_margin_top), 0, 0);
            layoutParams4.setMargins(0, getDimenWithScale(R.dimen.miui_aesthetics_a_clock_wide_data_margin_top), 0, 0);
            layoutParams5.setMargins(0, getDimenWithScale(R.dimen.miui_aesthetics_a_clock_wide_week_margin_top), 0, 0);
        } else {
            ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimenWithScale(R.dimen.miui_aesthetics_a_pre_narrow_height);
            layoutParams.setMargins(0, getDimenWithScale(R.dimen.miui_aesthetics_a_pre_narrow_margin_top), 0, 0);
            layoutParams3.setMargins(0, getDimenWithScale(R.dimen.miui_aesthetics_a_clock_narrow_min_margin_top), 0, 0);
            layoutParams4.setMargins(0, getDimenWithScale(R.dimen.miui_aesthetics_a_clock_narrow_data_margin_top), 0, 0);
            layoutParams5.setMargins(0, getDimenWithScale(R.dimen.miui_aesthetics_a_clock_narrow_week_margin_top), 0, 0);
            int i = R.dimen.miui_aesthetics_a_clock_narrow_weather_margin_top;
            layoutParams6.setMargins(0, getDimenWithScale(i), 0, 0);
            layoutParams7.setMargins(0, getDimenWithScale(i), 0, 0);
        }
        this.clockSpace.setLayoutParams(layoutParams);
        this.tvHour.setLayoutParams(layoutParams2);
        this.tvMinute.setLayoutParams(layoutParams3);
        this.tvData.setLayoutParams(layoutParams4);
        this.tvDataWeek.setLayoutParams(layoutParams5);
        this.tvWeather.setLayoutParams(layoutParams6);
        this.tvWeatherUnit.setLayoutParams(layoutParams7);
    }

    public final void queryDataSync() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        WeatherBean weatherBeanSync = hasWeatherType() ? getWeatherBeanSync(getSpecialContext(this.mContext)) : null;
        Log.i("MiuiAestheticsAPreviewView", "getWeatherBeanSync use time = " + (System.currentTimeMillis() - jCurrentTimeMillis));
        this.mWeatherBean = weatherBeanSync;
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void setClockDarkMode(boolean z) {
        if (z) {
            TextView textView = this.tvHour;
            Resources resources = getResources();
            int i = R.color.miui_clock_preview_dark_mode_color_90;
            textView.setTextColor(resources.getColor(i));
            this.tvMinute.setTextColor(getResources().getColor(i));
            this.tvWeather.setTextColor(getResources().getColor(i));
            TextView textView2 = this.tvWeatherUnit;
            Resources resources2 = getResources();
            int i2 = R.color.miui_clock_preview_light_mode_color_BA;
            textView2.setTextColor(resources2.getColor(i2));
            this.tvData.setTextColor(getResources().getColor(i2));
            this.tvDataWeek.setTextColor(getResources().getColor(i2));
            return;
        }
        TextView textView3 = this.tvHour;
        Resources resources3 = getResources();
        int i3 = R.color.miui_clock_preview_light_mode_color;
        textView3.setTextColor(resources3.getColor(i3));
        this.tvMinute.setTextColor(getResources().getColor(i3));
        this.tvWeather.setTextColor(getResources().getColor(i3));
        TextView textView4 = this.tvWeatherUnit;
        Resources resources4 = getResources();
        int i4 = R.color.miui_clock_preview_light_mode_color_60;
        textView4.setTextColor(resources4.getColor(i4));
        this.tvData.setTextColor(getResources().getColor(i4));
        this.tvDataWeek.setTextColor(getResources().getColor(i4));
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void setStyle(int i) {
        super.setStyle(i);
        if (hasWeatherType()) {
            queryDataSync();
        }
        boolean z = i == 1;
        this.clockIsWide = z;
        this.tvWeather.setVisibility(z ? 8 : 0);
        this.tvWeatherUnit.setVisibility(this.clockIsWide ? 8 : 0);
        initViewParams();
        initTextParams();
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void updateTime() {
        String string;
        String str;
        super.updateTime();
        int i = this.mCalendar.get(20);
        String timeString = ClassicClockTimeUtils.getTimeString(getHourInt(this.m24HourFormat, this.mCalendar), true);
        String timeString2 = ClassicClockTimeUtils.getTimeString(i, true);
        this.tvHour.setText(timeString);
        this.tvHour.setContentDescription(this.mCalendar.format(getContext(), getResources().getString(this.m24HourFormat ? R.string.miui_aesthetics_a_notification_format_24 : R.string.miui_aesthetics_a_notification_format_12)));
        this.tvMinute.setText(timeString2);
        String language = Locale.getDefault().getLanguage();
        String country = Locale.getDefault().getCountry();
        if (language.equals("zh") && (country.equals("CN") || country.equals("HK") || country.equals("TW"))) {
            TextView textView = this.tvData;
            Calendar calendar = this.mCalendar;
            Context context = getContext();
            Resources resources = getResources();
            int i2 = R.string.miui_aesthetics_a_data_format;
            textView.setText(calendar.format(context, resources.getString(i2)));
            this.tvData.setContentDescription(this.mCalendar.format(getContext(), getContext().getString(i2)));
            String solarTerms = ClassicClockTimeUtils.getSolarTerms(getContext(), this.mCalendar);
            String lunarCalendarString = ClassicClockTimeUtils.getLunarCalendarString(getContext(), this.mCalendar);
            if (TextUtils.isEmpty(solarTerms)) {
                String sexagenaryCycleString = ClassicClockTimeUtils.getSexagenaryCycleString(getContext(), this.mCalendar);
                string = getResources().getString(R.string.format_lunar_calendar_solar_terms, sexagenaryCycleString, lunarCalendarString);
                str = sexagenaryCycleString + ac.b + lunarCalendarString;
            } else {
                string = getResources().getString(R.string.format_lunar_calendar_solar_terms, lunarCalendarString, solarTerms);
                str = lunarCalendarString + ac.b + solarTerms;
            }
            this.tvDataWeek.setText(string);
            this.tvDataWeek.setContentDescription(str);
        } else {
            this.tvData.setText(this.mCalendar.format(getContext(), getResources().getString(R.string.miui_rhombus_clock_date)).toUpperCase());
            this.tvDataWeek.setText(this.mCalendar.format(getContext(), getResources().getString(R.string.miui_rhombus_clock_week_full)).toUpperCase());
        }
        this.tvWeatherUnit.setText(getResources().getString(R.string.temperature_unit));
        WeatherBean weatherBean = this.mWeatherBean;
        if (weatherBean == null) {
            this.tvWeather.setText(getResources().getString(R.string.no_data_placeholder_string));
            this.tvWeather.setContentDescription(getResources().getString(R.string.weather_no_data));
        } else if (!weatherBean.getTemperatureValid()) {
            this.tvWeather.setText(getResources().getString(R.string.no_data_placeholder_string));
            this.tvWeather.setContentDescription(getResources().getString(R.string.weather_no_data));
        } else {
            String quantityString = getResources().getQuantityString(R.plurals.weather_temperature_desc, this.mWeatherBean.getTemperature(), this.mWeatherBean.getTemperatureWithoutUnit(), this.mWeatherBean.getDescription());
            this.tvWeather.setText(this.mWeatherBean.getTemperatureWithoutUnit());
            this.tvWeather.setContentDescription(quantityString);
        }
    }
}
