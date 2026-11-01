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
import android.widget.Space;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.WeatherBean;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.MutiDeviceDimenAdapter;
import com.xiaomi.onetrack.util.ac;
import java.util.Locale;
import java.util.Map;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class MiuiAestheticsAClock extends MiuiAestheticsABase {
    public Space clockSpace;
    public Guideline guidelineV50;
    public TextView tvData;
    public TextView tvDataWeek;
    public TextView tvMinute;
    public TextView tvWeather;
    public TextView tvWeatherUnit;

    /* JADX INFO: renamed from: com.miui.clock.aesthetics.MiuiAestheticsAClock$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.FULL_MINUTE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_DATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_WEEK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.WEATHER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.WEATHER_UNIT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public MiuiAestheticsAClock(Context context) {
        super(context);
    }

    public MiuiAestheticsAClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MiuiAestheticsAClock(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo = this.mClockInfo;
        if (miuiAestheticsClockBaseInfo == null) {
            return;
        }
        ClockEffectUtils.clearClockEffectsContainer(this, miuiAestheticsClockBaseInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.tvMinute, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.tvData, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.tvDataWeek, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.tvWeather, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.tvWeatherUnit, this.mClockInfo, z);
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
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i == 1) {
            return this.tvMinute;
        }
        if (i == 2) {
            return this.tvData;
        }
        if (i == 3) {
            return this.tvDataWeek;
        }
        if (i != 4) {
            return i != 5 ? super.getIClockView(clockViewType) : this.tvWeatherUnit;
        }
        return this.tvWeather;
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

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    public final void initTextParams() {
        Typeface miSans = FontUtils.getMiSans(450);
        Typeface aestheticsTypeFace = FontUtils.getAestheticsTypeFace(this.clockIsWide);
        this.tvMinute.setTypeface(aestheticsTypeFace);
        this.tvWeather.setTypeface(aestheticsTypeFace);
        this.tvWeatherUnit.setTypeface(aestheticsTypeFace);
        this.tvData.setTypeface(miSans);
        this.tvDataWeek.setTypeface(miSans);
        float multiDeviceDimen = this.clockIsWide ? MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.miui_aesthetics_a_hour_wide_text_size, true) : getDimen(R.dimen.miui_aesthetics_a_hour_narrow_text_size);
        float multiDeviceDimen2 = this.clockIsWide ? MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.miui_aesthetics_a_data_wide_text_size, true) : getDimen(R.dimen.miui_aesthetics_a_data_narrow_text_size);
        this.tvMinute.setTextSize(0, multiDeviceDimen);
        this.tvWeather.setTextSize(0, multiDeviceDimen);
        this.tvWeatherUnit.setTextSize(0, multiDeviceDimen);
        this.tvData.setTextSize(0, multiDeviceDimen2);
        this.tvDataWeek.setTextSize(0, multiDeviceDimen2);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase
    public void initView() {
        super.initView();
        int i = R.id.guideline;
        Guideline guideline = (Guideline) findViewById(i);
        this.guidelineV50 = guideline;
        guideline.setGuidelinePercent(0.5f);
        this.guidelineV50 = (Guideline) findViewById(i);
        this.clockSpace = (Space) findViewById(R.id.clock_space);
        this.tvMinute = (TextView) findViewById(R.id.tv_minute);
        this.tvWeather = (TextView) findViewById(R.id.tv_weather);
        this.tvWeatherUnit = (TextView) findViewById(R.id.tv_weather_unit);
        this.tvData = (TextView) findViewById(R.id.tv_data);
        this.tvDataWeek = (TextView) findViewById(R.id.tv_data_week);
    }

    public final void initViewParams() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.clockSpace.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = 0;
        ((ViewGroup.MarginLayoutParams) layoutParams).height = 0;
        layoutParams.topToTop = 0;
        layoutParams.leftToLeft = this.guidelineV50.getId();
        layoutParams.rightToRight = this.guidelineV50.getId();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.tvMinute.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.tvData.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.tvDataWeek.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams5 = (ConstraintLayout.LayoutParams) this.tvWeather.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams6 = (ConstraintLayout.LayoutParams) this.tvWeatherUnit.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = -2;
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = -2;
        ((ViewGroup.MarginLayoutParams) layoutParams3).width = -2;
        ((ViewGroup.MarginLayoutParams) layoutParams3).height = -2;
        ((ViewGroup.MarginLayoutParams) layoutParams4).width = -2;
        ((ViewGroup.MarginLayoutParams) layoutParams4).height = -2;
        ((ViewGroup.MarginLayoutParams) layoutParams5).width = -2;
        ((ViewGroup.MarginLayoutParams) layoutParams5).height = -2;
        ((ViewGroup.MarginLayoutParams) layoutParams6).width = -2;
        ((ViewGroup.MarginLayoutParams) layoutParams6).height = -2;
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
        layoutParams5.rightToRight = this.tvMinute.getId();
        layoutParams6.topToTop = this.clockSpace.getId();
        layoutParams6.leftToRight = this.tvMinute.getId();
        if (this.clockIsWide) {
            layoutParams.setMargins(0, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.miui_aesthetics_a_clock_wide_margin_top, false), 0, 0);
            layoutParams2.setMargins(0, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.miui_aesthetics_a_clock_wide_min_margin_top, true), 0, 0);
            layoutParams3.setMargins(0, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.miui_aesthetics_a_clock_wide_data_margin_top, true), 0, 0);
            layoutParams4.setMargins(0, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.miui_aesthetics_a_clock_wide_week_margin_top, true), 0, 0);
        } else {
            layoutParams.setMargins(0, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.miui_aesthetics_a_clock_narrow_margin_top, false), 0, 0);
            layoutParams2.setMargins(0, getDimen(R.dimen.miui_aesthetics_a_clock_narrow_min_margin_top), 0, 0);
            layoutParams3.setMargins(0, getDimen(R.dimen.miui_aesthetics_a_clock_narrow_data_margin_top), 0, 0);
            layoutParams4.setMargins(0, getDimen(R.dimen.miui_aesthetics_a_clock_narrow_week_margin_top), 0, 0);
            int i = R.dimen.miui_aesthetics_a_clock_narrow_weather_margin_top;
            layoutParams5.setMargins(0, getDimen(i), 0, 0);
            layoutParams6.setMargins(0, getDimen(i), 0, 0);
        }
        this.clockSpace.setLayoutParams(layoutParams);
        this.tvMinute.setLayoutParams(layoutParams2);
        this.tvData.setLayoutParams(layoutParams3);
        this.tvDataWeek.setLayoutParams(layoutParams4);
        this.tvWeather.setLayoutParams(layoutParams5);
        this.tvWeatherUnit.setLayoutParams(layoutParams6);
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

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        super.setClockPalette(i, z, map, z2);
        updateClockBlendColor();
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

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2, int i3, int i4) {
        boolean zIsAODType = ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType());
        boolean zIsFullAODType = ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType());
        boolean zUpdateTextDark = updateTextDark(this.mTextDark);
        int textDarkAlpha = getTextDarkAlpha(this.mTextDark);
        updateViewBackgroundBlurContainer(this, getDimen(R.dimen.miui_aesthetics_a_clock_blur_radius), this.tvMinute, this.tvData, this.tvDataWeek, this.tvWeather, this.tvWeatherUnit);
        TextView textView = this.tvMinute;
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView, miuiAestheticsClockBaseInfo, zUpdateTextDark, i, miuiAestheticsClockBaseInfo.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        TextView textView2 = this.tvData;
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo2 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView2, miuiAestheticsClockBaseInfo2, zUpdateTextDark, i2, miuiAestheticsClockBaseInfo2.getSecondaryColor(), i4, zIsAODType, zIsFullAODType, textDarkAlpha);
        TextView textView3 = this.tvDataWeek;
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo3 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView3, miuiAestheticsClockBaseInfo3, zUpdateTextDark, i2, miuiAestheticsClockBaseInfo3.getSecondaryColor(), i4, zIsAODType, zIsFullAODType, textDarkAlpha);
        TextView textView4 = this.tvWeather;
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo4 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView4, miuiAestheticsClockBaseInfo4, zUpdateTextDark, i, miuiAestheticsClockBaseInfo4.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        TextView textView5 = this.tvWeatherUnit;
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo5 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView5, miuiAestheticsClockBaseInfo5, zUpdateTextDark, i, miuiAestheticsClockBaseInfo5.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockColor(int i, int i2) {
        this.tvMinute.setTextColor(i);
        this.tvWeather.setTextColor(i);
        this.tvData.setTextColor(i2);
        this.tvDataWeek.setTextColor(i2);
        if (ClockEffectUtils.isBlurMixType(this.mClockInfo.getClockEffect()) && this.mClockInfo.isAutoPrimaryColor()) {
            this.tvWeatherUnit.setTextColor(Color.parseColor("#999999"));
            this.tvWeatherUnit.setAlpha(1.0f);
        } else {
            this.tvWeatherUnit.setTextColor(i);
            this.tvWeatherUnit.setAlpha(0.6f);
        }
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        super.updateColor();
        updateClockColor(this.mClockInfo.getPrimaryColor(), this.mClockInfo.getSecondaryColor());
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        String string;
        String str;
        super.updateTime();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mMagazineGroup.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = -2;
        ((ViewGroup.MarginLayoutParams) layoutParams).height = -2;
        layoutParams.topToTop = 0;
        layoutParams.startToStart = 0;
        layoutParams.endToEnd = 0;
        layoutParams.setMargins(0, getDimen(this.clockIsWide ? R.dimen.miui_aesthetics_a_wide_magazine_margin_top : R.dimen.miui_aesthetics_a_narrow_magazine_margin_top), 0, 0);
        this.mMagazineGroup.setLayoutParams(layoutParams);
        this.tvMinute.setText(ClassicClockTimeUtils.getTimeString(this.mCalendar.get(20), true));
        this.tvMinute.setContentDescription(this.mCalendar.format(getContext(), getResources().getString(this.m24HourFormat ? R.string.miui_aesthetics_a_notification_format_24 : R.string.miui_aesthetics_a_notification_format_12)));
        String language = Locale.getDefault().getLanguage();
        String country = Locale.getDefault().getCountry();
        if (language.equals("zh") && (country.equals("CN") || country.equals("HK") || country.equals("TW"))) {
            TextView textView = this.tvData;
            Calendar calendar = this.mCalendar;
            Context context = getContext();
            Resources resources = getResources();
            int i = R.string.miui_aesthetics_a_data_format;
            textView.setText(calendar.format(context, resources.getString(i)));
            this.tvData.setContentDescription(this.mCalendar.format(getContext(), getContext().getString(i)));
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
