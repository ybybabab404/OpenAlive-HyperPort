package com.miui.clock.aesthetics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.Guideline;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.WeatherBean;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.MutiDeviceDimenAdapter;
import com.xiaomi.onetrack.util.ac;
import java.util.Locale;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class MiuiAestheticsASingleClock extends MiuiAestheticsABase {
    public Guideline guidelineV50;
    public ConstraintLayout liClockContainer;
    public TextView tvData;
    public TextView tvDataWeek;
    public TextView tvHour;
    public TextView tvMinute;
    public TextView tvWeather;
    public TextView tvWeatherUnit;

    /* JADX INFO: renamed from: com.miui.clock.aesthetics.MiuiAestheticsASingleClock$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.FULL_HOUR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_MINUTE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_DATE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_WEEK.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.WEATHER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.WEATHER_UNIT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public MiuiAestheticsASingleClock(Context context) {
        super(context);
    }

    public MiuiAestheticsASingleClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MiuiAestheticsASingleClock(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private void initTextParams() {
        Typeface miSans = FontUtils.getMiSans(450);
        Typeface aestheticsTypeFace = FontUtils.getAestheticsTypeFace(this.clockIsWide);
        this.tvHour.setTypeface(aestheticsTypeFace);
        this.tvMinute.setTypeface(aestheticsTypeFace);
        this.tvWeather.setTypeface(aestheticsTypeFace);
        this.tvWeatherUnit.setTypeface(aestheticsTypeFace);
        this.tvData.setTypeface(miSans);
        this.tvDataWeek.setTypeface(miSans);
        float multiDeviceDimen = this.clockIsWide ? MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.miui_aesthetics_a_hour_wide_text_size, true) : getDimen(R.dimen.miui_aesthetics_a_hour_narrow_text_size);
        float multiDeviceDimen2 = this.clockIsWide ? MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.miui_aesthetics_a_data_wide_text_size, true) : getDimen(R.dimen.miui_aesthetics_a_data_narrow_text_size);
        this.tvHour.setTextSize(0, multiDeviceDimen);
        this.tvMinute.setTextSize(0, multiDeviceDimen);
        this.tvWeather.setTextSize(0, multiDeviceDimen);
        this.tvWeatherUnit.setTextSize(0, multiDeviceDimen);
        this.tvData.setTextSize(0, multiDeviceDimen2);
        this.tvDataWeek.setTextSize(0, multiDeviceDimen2);
    }

    private void initViewParams() {
        Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(-2, -2);
        layoutParams.topToTop = 0;
        layoutParams.leftToLeft = 0;
        layoutParams.rightToRight = 0;
        Constraints.LayoutParams layoutParams2 = new Constraints.LayoutParams(-2, -2);
        Constraints.LayoutParams layoutParams3 = new Constraints.LayoutParams(-2, -2);
        Constraints.LayoutParams layoutParams4 = new Constraints.LayoutParams(-2, -2);
        Constraints.LayoutParams layoutParams5 = new Constraints.LayoutParams(-2, -2);
        Constraints.LayoutParams layoutParams6 = new Constraints.LayoutParams(-2, -2);
        Constraints.LayoutParams layoutParams7 = new Constraints.LayoutParams(-2, -2);
        Constraints.LayoutParams layoutParams8 = new Constraints.LayoutParams((int) (DeviceConfig.calculateScale(getContext()) * 195.0f), (int) (DeviceConfig.calculateScale(getContext()) * 55.0f));
        layoutParams2.topToTop = this.liClockContainer.getId();
        layoutParams2.leftToLeft = this.liClockContainer.getId();
        layoutParams2.rightToRight = this.liClockContainer.getId();
        layoutParams3.topToTop = this.liClockContainer.getId();
        layoutParams3.leftToLeft = this.liClockContainer.getId();
        layoutParams3.rightToRight = this.liClockContainer.getId();
        layoutParams4.topToTop = this.liClockContainer.getId();
        layoutParams4.leftToLeft = this.liClockContainer.getId();
        layoutParams4.rightToRight = this.liClockContainer.getId();
        layoutParams5.topToTop = this.liClockContainer.getId();
        layoutParams5.leftToLeft = this.liClockContainer.getId();
        layoutParams5.rightToRight = this.liClockContainer.getId();
        layoutParams6.topToTop = this.liClockContainer.getId();
        layoutParams6.rightToRight = this.tvMinute.getId();
        layoutParams7.topToTop = this.liClockContainer.getId();
        layoutParams7.leftToRight = this.tvMinute.getId();
        layoutParams8.topToTop = this.liClockContainer.getId();
        layoutParams8.leftToLeft = this.liClockContainer.getId();
        layoutParams8.rightToRight = this.liClockContainer.getId();
        if (this.clockIsWide) {
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.miui_aesthetics_a_clock_wide_single_margin_top, false);
            layoutParams3.setMargins(0, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.miui_aesthetics_a_clock_wide_min_margin_top, true), 0, 0);
            layoutParams4.setMargins(0, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.miui_aesthetics_a_clock_wide_data_margin_top, true), 0, 0);
            layoutParams5.setMargins(0, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.miui_aesthetics_a_clock_wide_week_margin_top, true), 0, 0);
        } else {
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.miui_aesthetics_a_clock_narrow_single_margin_top, true);
            layoutParams3.setMargins(0, getDimen(R.dimen.miui_aesthetics_a_clock_narrow_min_margin_top), 0, 0);
            layoutParams4.setMargins(0, getDimen(R.dimen.miui_aesthetics_a_clock_narrow_data_margin_top), 0, 0);
            layoutParams5.setMargins(0, getDimen(R.dimen.miui_aesthetics_a_clock_narrow_week_margin_top), 0, 0);
            int i = R.dimen.miui_aesthetics_a_clock_narrow_weather_margin_top;
            layoutParams6.setMargins(0, getDimen(i), 0, 0);
            layoutParams7.setMargins(0, getDimen(i), 0, 0);
            layoutParams8.setMargins(0, getDimen(R.dimen.miui_aesthetics_a_clock_narrow_battery_margin_top), 0, 0);
        }
        this.liClockContainer.setLayoutParams(layoutParams);
        this.tvHour.setLayoutParams(layoutParams2);
        this.tvMinute.setLayoutParams(layoutParams3);
        this.tvData.setLayoutParams(layoutParams4);
        this.tvDataWeek.setLayoutParams(layoutParams5);
        this.tvWeather.setLayoutParams(layoutParams6);
        this.tvWeatherUnit.setLayoutParams(layoutParams7);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        switch (AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()]) {
            case 1:
                return this.tvHour;
            case 2:
                return this.tvMinute;
            case 3:
                return this.tvData;
            case 4:
                return this.tvDataWeek;
            case 5:
                return this.tvWeather;
            case 6:
                return this.tvWeatherUnit;
            default:
                return super.getIClockView(clockViewType);
        }
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public float getTopMargin() {
        return this.clockIsWide ? MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.miui_aesthetics_a_clock_wide_single_margin_top, false) : MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.miui_aesthetics_a_clock_narrow_single_margin_top, true);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase
    public void initView() {
        super.initView();
        this.liClockContainer = (ConstraintLayout) findViewById(R.id.li_clock_container);
        int i = R.id.guideline;
        Guideline guideline = (Guideline) findViewById(i);
        this.guidelineV50 = guideline;
        guideline.setGuidelinePercent(0.5f);
        this.guidelineV50 = (Guideline) findViewById(i);
        this.tvHour = (TextView) findViewById(R.id.tv_hour);
        this.tvMinute = (TextView) findViewById(R.id.tv_minute);
        this.tvWeather = (TextView) findViewById(R.id.tv_weather);
        this.tvWeatherUnit = (TextView) findViewById(R.id.tv_weather_unit);
        this.tvData = (TextView) findViewById(R.id.tv_data);
        this.tvDataWeek = (TextView) findViewById(R.id.tv_data_week);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase
    public void setClockStyle(int i) {
        super.setClockStyle(i);
        this.tvWeather.setVisibility(this.clockIsWide ? 8 : 0);
        this.tvWeatherUnit.setVisibility(this.clockIsWide ? 8 : 0);
        initViewParams();
        initTextParams();
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setMagazineInfoVisible(boolean z) {
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        super.updateColor();
        this.tvHour.setTextColor(this.mClockInfo.getAodPrimaryColor());
        this.tvMinute.setTextColor(this.mClockInfo.getAodPrimaryColor());
        this.tvWeather.setTextColor(this.mClockInfo.getAodPrimaryColor());
        this.tvData.setTextColor(this.mClockInfo.getAodSecondaryColor());
        this.tvDataWeek.setTextColor(this.mClockInfo.getAodSecondaryColor());
        if (ClockEffectUtils.isBlurMixType(this.mClockInfo.getClockEffect()) && this.mClockInfo.isAutoPrimaryColor()) {
            this.tvWeatherUnit.setTextColor(Color.parseColor("#999999"));
            this.tvWeatherUnit.setAlpha(1.0f);
        } else {
            this.tvWeatherUnit.setTextColor(this.mClockInfo.getAodPrimaryColor());
            this.tvWeatherUnit.setAlpha(0.6f);
        }
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
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
        this.tvMinute.setContentDescription(this.mCalendar.format(getContext(), getResources().getString(this.m24HourFormat ? R.string.miui_aesthetics_a_notification_format_24 : R.string.miui_aesthetics_a_notification_format_12)));
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
