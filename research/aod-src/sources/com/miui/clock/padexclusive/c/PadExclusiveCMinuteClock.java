package com.miui.clock.padexclusive.c;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.WeatherBean;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.MiuiBlurUtils;
import com.miui.clock.utils.WeatherFetcherController;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class PadExclusiveCMinuteClock extends PadExclusiveCBase {
    public int mCurrentUserId;
    public TextView mDate;
    public boolean mHasPresetData;
    public TextView mNotificationDate;
    public TextView mRegion;
    public TextView mTemperature;
    public TextView mTimeMinute;
    public ConstraintLayout mTimeMinuteParent;
    public TextView mWeather;
    public WeatherFetcherController mWeatherFetcherController;
    public TextView mWeek;
    public WeatherBean weatherBean;

    /* JADX INFO: renamed from: com.miui.clock.padexclusive.c.PadExclusiveCMinuteClock$1, reason: invalid class name */
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
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.MIN1.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.DATE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.WEEK.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.TEMPERATURE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.REGION.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.WEATHER.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.NOTIFICATION_DATE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$uTW7WGJEme0ndSCHFZXBjvFL368(PadExclusiveCMinuteClock padExclusiveCMinuteClock, WeatherBean weatherBean) {
        if (padExclusiveCMinuteClock.isAttachedToWindow()) {
            padExclusiveCMinuteClock.setWeatherBean(weatherBean);
            padExclusiveCMinuteClock.updateWeatherInfo(weatherBean);
        }
    }

    public PadExclusiveCMinuteClock(Context context) {
        super(context);
    }

    public PadExclusiveCMinuteClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private boolean isTibetan() {
        return "bo".equals(getResources().getConfiguration().locale.getLanguage());
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        PadExclusiveCBaseInfo padExclusiveCBaseInfo = this.mClockInfo;
        if (padExclusiveCBaseInfo == null) {
            return;
        }
        ClockEffectUtils.clearClockEffectsContainer(this, padExclusiveCBaseInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mTimeMinute, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mDate, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mWeek, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mTemperature, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mRegion, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mWeather, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mNotificationDate, this.mClockInfo, z);
        ClockEffectUtils.clearClockGradualEffect(this.mTimeMinute, this.mClockInfo, z);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        switch (AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()]) {
            case 1:
                return this.mTimeMinuteParent;
            case 2:
                return this.mTimeMinute;
            case 3:
                return this.mDate;
            case 4:
                return this.mWeek;
            case 5:
                return this.mTemperature;
            case 6:
                return this.mRegion;
            case 7:
                return this.mWeather;
            case 8:
                return this.mNotificationDate;
            default:
                return super.getIClockView(clockViewType);
        }
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getMagazineColor() {
        return super.getMagazineColor();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockTop() {
        return getDimen(this.mHasFaceIcon.booleanValue() ? R.dimen.miui_pad_c_notification_face_time_margin_top : R.dimen.miui_pad_c_notification_time_margin_top);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiBaseClock2
    public View getNotificationClockTopView() {
        return this.mTimeMinuteParent;
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiBaseClock2
    public void init(Context context) {
        super.init(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        WeatherFetcherController weatherFetcherController = this.mWeatherFetcherController;
        if (weatherFetcherController != null) {
            weatherFetcherController.reset();
            this.mWeatherFetcherController = null;
        }
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTimeMinuteParent = (ConstraintLayout) findViewById(R.id.pad_c_minute_parent);
        this.mTimeMinute = (TextView) findViewById(R.id.pad_c_minute);
        this.mDate = (TextView) findViewById(R.id.pad_c_date);
        this.mWeek = (TextView) findViewById(R.id.pad_c_week);
        this.mTemperature = (TextView) findViewById(R.id.pad_c_temperature);
        this.mRegion = (TextView) findViewById(R.id.pad_c_region);
        this.mWeather = (TextView) findViewById(R.id.pad_c_weather);
        this.mNotificationDate = (TextView) findViewById(R.id.pad_c_notification_date);
        this.mTimeMinute.setTypeface(this.mTimeFontType);
        this.mDate.setTypeface(this.mDateFontType);
        this.mWeek.setTypeface(this.mDateFontType);
        this.mTemperature.setTypeface(this.mDateFontType);
        this.mRegion.setTypeface(this.mDateFontType);
        this.mWeather.setTypeface(this.mDateFontType);
        this.mNotificationDate.setTypeface(this.mDateFontType);
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void onLanguageChanged() {
        super.onLanguageChanged();
        queryDataAsync();
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        ConstraintLayout constraintLayout = this.mTimeMinuteParent;
        constraintLayout.setPivotX(constraintLayout.getMeasuredWidth() / 2.0f);
        this.mTimeMinuteParent.setPivotY(0.0f);
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void queryDataAsync() {
        Context specialContext = WeatherFetcherController.Companion.getSpecialContext(this.mContext, this.mCurrentUserId);
        if (this.mWeatherFetcherController == null) {
            this.mWeatherFetcherController = new WeatherFetcherController();
        }
        this.mWeatherFetcherController.fetchWeatherData(specialContext, this.mHasPresetData, new WeatherFetcherController.Callback() { // from class: com.miui.clock.padexclusive.c.PadExclusiveCMinuteClock$$ExternalSyntheticLambda0
            @Override // com.miui.clock.utils.WeatherFetcherController.Callback
            public final void updateWeatherInfo(WeatherBean weatherBean) {
                PadExclusiveCMinuteClock.$r8$lambda$uTW7WGJEme0ndSCHFZXBjvFL368(this.f$0, weatherBean);
            }
        });
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        super.setClockPalette(i, z, map, z2);
        if (DeviceConfig.isHighTextContrastEnabled(this.mContext) || this.mClockInfo == null) {
            return;
        }
        ClockEffectUtils.setClockEffectsContainer(this, getDimen(R.dimen.miui_pad_exclusive_c_clock_blur_radius), this.mClockInfo, false, false);
        TextView textView = this.mTimeMinute;
        PadExclusiveCBaseInfo padExclusiveCBaseInfo = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView, padExclusiveCBaseInfo, z, padExclusiveCBaseInfo.getSecondaryBlendColor(), this.mClockInfo.getSecondaryColor(), false, false);
        ClockEffectUtils.setClockGradualEffect(getCurrentGradientParams(), this.mTimeMinute, this.mClockInfo, false, false, 1.0f);
        if (DeviceConfig.supportBackgroundBlur(this.mContext)) {
            TextView textView2 = this.mDate;
            PadExclusiveCBaseInfo padExclusiveCBaseInfo2 = this.mClockInfo;
            ClockEffectUtils.setClockEffectsView(textView2, padExclusiveCBaseInfo2, z, padExclusiveCBaseInfo2.getBlendColor(), this.mClockInfo.getPrimaryColor(), false, false);
            TextView textView3 = this.mWeek;
            PadExclusiveCBaseInfo padExclusiveCBaseInfo3 = this.mClockInfo;
            ClockEffectUtils.setClockEffectsView(textView3, padExclusiveCBaseInfo3, z, padExclusiveCBaseInfo3.getBlendColor(), this.mClockInfo.getPrimaryColor(), false, false);
            TextView textView4 = this.mTemperature;
            PadExclusiveCBaseInfo padExclusiveCBaseInfo4 = this.mClockInfo;
            ClockEffectUtils.setClockEffectsView(textView4, padExclusiveCBaseInfo4, z, padExclusiveCBaseInfo4.getBlendColor(), this.mClockInfo.getPrimaryColor(), false, false);
            TextView textView5 = this.mRegion;
            PadExclusiveCBaseInfo padExclusiveCBaseInfo5 = this.mClockInfo;
            ClockEffectUtils.setClockEffectsView(textView5, padExclusiveCBaseInfo5, z, padExclusiveCBaseInfo5.getBlendColor(), this.mClockInfo.getPrimaryColor(), false, false);
            TextView textView6 = this.mWeather;
            PadExclusiveCBaseInfo padExclusiveCBaseInfo6 = this.mClockInfo;
            ClockEffectUtils.setClockEffectsView(textView6, padExclusiveCBaseInfo6, z, padExclusiveCBaseInfo6.getBlendColor(), this.mClockInfo.getPrimaryColor(), false, false);
            TextView textView7 = this.mNotificationDate;
            PadExclusiveCBaseInfo padExclusiveCBaseInfo7 = this.mClockInfo;
            ClockEffectUtils.setClockEffectsView(textView7, padExclusiveCBaseInfo7, z, padExclusiveCBaseInfo7.getBlendColor(), this.mClockInfo.getPrimaryColor(), false, false);
        }
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        clearColorEffect(false);
        queryDataAsync();
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setCurrentUserId(int i) {
        this.mCurrentUserId = i;
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    public void setWeatherBean(WeatherBean weatherBean) {
        this.weatherBean = weatherBean;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        super.updateColor();
        this.mTimeMinute.setTextColor(this.mClockInfo.getSecondaryColor());
        this.mDate.setTextColor(this.mClockInfo.getPrimaryColor());
        this.mWeek.setTextColor(this.mClockInfo.getPrimaryColor());
        this.mTemperature.setTextColor(this.mClockInfo.getPrimaryColor());
        this.mRegion.setTextColor(this.mClockInfo.getPrimaryColor());
        this.mWeather.setTextColor(this.mClockInfo.getPrimaryColor());
        this.mNotificationDate.setTextColor(this.mClockInfo.getPrimaryColor());
    }

    public final void updateDateLayoutParams() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mDate.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mWeek.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mTemperature.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.mRegion.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams5 = (ConstraintLayout.LayoutParams) this.mWeather.getLayoutParams();
        layoutParams.startToStart = 0;
        layoutParams.topToTop = 0;
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_pad_c_date_margin_top);
        layoutParams2.startToStart = 0;
        layoutParams2.topToTop = 0;
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimen(R.dimen.miui_pad_c_week_margin_top);
        layoutParams3.startToStart = 0;
        layoutParams3.topToTop = 0;
        ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin = getDimen(R.dimen.miui_pad_c_temperature_margin_top);
        layoutParams4.startToStart = 0;
        layoutParams4.topToTop = 0;
        ((ViewGroup.MarginLayoutParams) layoutParams4).topMargin = getDimen(R.dimen.miui_pad_c_region_margin_top);
        layoutParams5.startToStart = 0;
        layoutParams5.topToTop = 0;
        ((ViewGroup.MarginLayoutParams) layoutParams5).topMargin = getDimen(R.dimen.miui_pad_c_weather_margin_top);
        String str = this.mMinuteStr;
        if (str == null || str.isEmpty() || this.mMinuteStr.charAt(0) != '1') {
            int i = R.dimen.miui_pad_c_date_margin_left;
            ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = getDimen(i);
            ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin = getDimen(i);
            ((ViewGroup.MarginLayoutParams) layoutParams3).leftMargin = getDimen(i);
            ((ViewGroup.MarginLayoutParams) layoutParams4).leftMargin = getDimen(i);
            ((ViewGroup.MarginLayoutParams) layoutParams5).leftMargin = getDimen(i);
        } else {
            int i2 = R.dimen.miui_pad_c_date_margin_left_shift;
            ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = getDimen(i2);
            ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin = getDimen(i2);
            ((ViewGroup.MarginLayoutParams) layoutParams3).leftMargin = getDimen(i2);
            ((ViewGroup.MarginLayoutParams) layoutParams4).leftMargin = getDimen(i2);
            ((ViewGroup.MarginLayoutParams) layoutParams5).leftMargin = getDimen(i2);
        }
        this.mDate.setLayoutParams(layoutParams);
        this.mWeek.setLayoutParams(layoutParams2);
        this.mTemperature.setLayoutParams(layoutParams3);
        this.mRegion.setLayoutParams(layoutParams4);
        this.mWeather.setLayoutParams(layoutParams5);
        this.mDate.setTextSize(0, getDimen(isTibetan() ? R.dimen.miui_pad_c_tibetan_date_size : R.dimen.miui_pad_c_date_size));
        this.mWeek.setTextSize(0, getDimen(isTibetan() ? R.dimen.miui_pad_c_tibetan_date_size : R.dimen.miui_pad_c_date_size));
        this.mTemperature.setTextSize(0, getDimen(isTibetan() ? R.dimen.miui_pad_c_tibetan_date_size : R.dimen.miui_pad_c_date_size));
        this.mRegion.setTextSize(0, getDimen(isTibetan() ? R.dimen.miui_pad_c_tibetan_date_size : R.dimen.miui_pad_c_date_size));
        this.mWeather.setTextSize(0, getDimen(isTibetan() ? R.dimen.miui_pad_c_tibetan_date_size : R.dimen.miui_pad_c_date_size));
        this.mDate.setGravity(17);
        this.mWeek.setGravity(17);
        this.mTemperature.setGravity(17);
        this.mRegion.setGravity(17);
        this.mWeather.setGravity(17);
        updateNotificationDateLayoutParams();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateGradientEffectColor(float[] fArr) {
        super.updateGradientEffectColor(fArr);
        PadExclusiveCBaseInfo padExclusiveCBaseInfo = this.mClockInfo;
        if (padExclusiveCBaseInfo != null && ClockEffectUtils.isGradualType(padExclusiveCBaseInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            MiuiBlurUtils.setGradientBlurMethod(this.mTimeMinute, fArr);
        }
    }

    public final void updateNotificationDateLayoutParams() {
        int i;
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mNotificationDate.getLayoutParams();
        layoutParams.startToStart = 0;
        layoutParams.endToEnd = 0;
        layoutParams.topToTop = 0;
        if (this.mHasFaceIcon.booleanValue()) {
            i = this.mMagazineInfoVisible ? R.dimen.miui_pad_c_notification_magazine_face_date_margin_top : R.dimen.miui_pad_c_notification_face_date_margin_top;
        } else {
            i = R.dimen.miui_pad_c_notification_date_margin_top;
        }
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(i);
        this.mNotificationDate.setLayoutParams(layoutParams);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        this.mTimeMinute.setText(this.mMinuteStr);
        int i = this.m24HourFormat ? R.string.miui_clock_time_format_24 : R.string.miui_clock_time_format_12;
        TextView textView = this.mTimeMinute;
        Calendar calendar = this.mCalendar;
        Context context = this.mContext;
        textView.setContentDescription(calendar.format(context, context.getString(i)));
        StringBuilder sb = new StringBuilder();
        sb.append(ClassicClockTimeUtils.getDateWithWeekStringStyle2(getContext(), this.mCalendar));
        sb.append(" ");
        Calendar calendar2 = this.mCalendar;
        Context context2 = this.mContext;
        int i2 = R.string.miui_lock_eastern_art_week_am;
        sb.append(calendar2.format(context2, context2.getString(i2)));
        sb.append(" ");
        String string = sb.toString();
        this.mDate.setText(ClassicClockTimeUtils.getDateWithWeekStringStyle2(getContext(), this.mCalendar));
        TextView textView2 = this.mWeek;
        Calendar calendar3 = this.mCalendar;
        Context context3 = this.mContext;
        textView2.setText(calendar3.format(context3, context3.getString(i2)));
        this.mNotificationDate.setText(string);
        this.mDate.setContentDescription(new SimpleDateFormat(getResources().getString(R.string.miui_rhombus_clock_date), Locale.getDefault()).format(Long.valueOf(this.mCalendar.getTimeInMillis())));
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mTimeMinuteParent.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mTimeMinute.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mMagazineGroup.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.miui_pad_c_time_parent_width);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.miui_pad_c_time_parent_height);
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_pad_c_time_parent_margin_top);
        layoutParams.startToStart = 0;
        layoutParams.endToEnd = 0;
        layoutParams.topToTop = 0;
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = getDimen(R.dimen.miui_pad_c_time_width);
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = getDimen(R.dimen.miui_pad_c_time_height);
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimen(R.dimen.miui_pad_c_time_margin_top);
        layoutParams2.endToEnd = 0;
        layoutParams2.topToTop = 0;
        this.mTimeMinuteParent.setLayoutParams(layoutParams);
        this.mTimeMinute.setLayoutParams(layoutParams2);
        this.mTimeMinute.setTextSize(0, getDimen(R.dimen.miui_pad_c_time_size));
        this.mTimeMinute.setGravity(17);
        ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin = getDimen(R.dimen.miui_pad_c_magazine_info_margin_top);
        layoutParams3.setMarginStart(getDimen(R.dimen.miui_pad_c_date_margin_left));
        this.mMagazineGroup.setLayoutParams(layoutParams3);
        updateDateLayoutParams();
    }

    public void updateWeatherInNoWeatherInfo() {
        this.mTemperature.setText("-°");
        this.mRegion.setText("---");
        this.mWeather.setText("-");
        TextView textView = this.mTemperature;
        Resources resources = getResources();
        int i = R.string.weather_no_data;
        textView.setContentDescription(resources.getString(i));
        this.mRegion.setContentDescription(getResources().getString(i));
        this.mWeather.setContentDescription(getResources().getString(i));
    }

    public void updateWeatherInfo(WeatherBean weatherBean) {
        if (weatherBean == null) {
            Log.d("PadExclusiveCMinuteClock", "queryDataSync:  please open weather app");
            updateWeatherInNoWeatherInfo();
            return;
        }
        TextView textView = this.mTemperature;
        StringBuilder sb = new StringBuilder();
        sb.append(weatherBean.getTemperature());
        Resources resources = getResources();
        int i = R.string.temperature_unit;
        sb.append(resources.getString(i));
        textView.setText(sb.toString());
        this.mRegion.setText(weatherBean.getCityName());
        this.mWeather.setText(weatherBean.getDescription());
        this.mTemperature.setContentDescription(weatherBean.getTemperature() + getResources().getString(i));
        this.mRegion.setContentDescription(weatherBean.getCityName());
        this.mWeather.setContentDescription(weatherBean.getDescription());
    }
}
