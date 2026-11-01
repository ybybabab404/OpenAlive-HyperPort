package com.miui.clock.eastern.b;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.VectorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.WeatherBean;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.WeatherFetcherController;
import java.util.Arrays;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class EasternArtBAodClock extends EasternArtBBase {
    public ImageView mColon;
    public VectorDrawable mColonBg;
    public ConstraintLayout mConstraintLayoutParent;
    public int mCurrentUserId;
    public TextView mDateMonthDay;
    public TextView mDateWeather;
    public TextView mDateWeek;
    public TextView mDateYear;
    public boolean mHasPresetData;
    public TextView mHour1;
    public TextView mHour2;
    public TextView mMinute1;
    public TextView mMinute2;
    public WeatherBean mWeatherBean;
    public WeatherFetcherController mWeatherFetcherController;

    /* JADX INFO: renamed from: com.miui.clock.eastern.b.EasternArtBAodClock$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.HOUR1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.HOUR2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.MIN1.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.MIN2.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_DATE_WEEK.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_COLON.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.WEATHER.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_YEAR.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_DATE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$lnxyQeGB_UHO2S7akOw7iJgBPfM(EasternArtBAodClock easternArtBAodClock, WeatherBean weatherBean) {
        if (easternArtBAodClock.isAttachedToWindow()) {
            if (weatherBean != null) {
                easternArtBAodClock.setWeatherBean(weatherBean);
                easternArtBAodClock.updateWeatherInfo(weatherBean);
            } else {
                easternArtBAodClock.setWeatherBean(null);
                easternArtBAodClock.clearWeatherInfo();
            }
        }
    }

    public EasternArtBAodClock(Context context) {
        super(context);
    }

    public EasternArtBAodClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private boolean hasWeatherType() {
        return true;
    }

    public final void clearWeatherInfo() {
        this.mDateWeather.setText("--°");
        if (isChineseLanguageOrCountry()) {
            return;
        }
        this.mDateMonthDay.setText("--");
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        switch (AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()]) {
            case 1:
                return this.mHour1;
            case 2:
                return this.mHour2;
            case 3:
                return this.mMinute1;
            case 4:
                return this.mMinute2;
            case 5:
                return this.mDateWeek;
            case 6:
                return this.mColon;
            case 7:
                return this.mDateWeather;
            case 8:
                return this.mDateYear;
            case 9:
                return this.mDateMonthDay;
            default:
                return super.getIClockView(clockViewType);
        }
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getMagazineColor() {
        return super.getMagazineColor();
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockBottom() {
        return getDimen(R.dimen.miui_eastern_art_b_horizontal_upper_notification);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public float getTopMargin() {
        return getDimen(R.dimen.miui_eastern_art_b_horizontal_upper_aod_data_week_margin_top);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
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
        this.mHour1 = (TextView) findViewById(R.id.eastern_art_b_h_aod_time_below_view_hour1);
        this.mHour2 = (TextView) findViewById(R.id.eastern_art_b_h_aod_time_below_view_hour2);
        this.mMinute1 = (TextView) findViewById(R.id.eastern_art_b_h_aod_time_below_view_minute1);
        this.mMinute2 = (TextView) findViewById(R.id.eastern_art_b_h_aod_time_below_view_minute2);
        this.mDateWeek = (TextView) findViewById(R.id.eastern_art_b_h_aod_week_zh);
        this.mDateYear = (TextView) findViewById(R.id.eastern_art_b_h_aod_year_zh);
        this.mDateMonthDay = (TextView) findViewById(R.id.eastern_art_b_h_aod_data_zh);
        this.mDateWeather = (TextView) findViewById(R.id.eastern_art_b_h_aod_weather_zh);
        ImageView imageView = (ImageView) findViewById(R.id.eastern_art_b_h_aod_time_below_view_colon);
        this.mColon = imageView;
        this.mColonBg = (VectorDrawable) imageView.getBackground();
        this.mConstraintLayoutParent = (ConstraintLayout) findViewById(R.id.eastern_art_b_h_aod_time_below_view_parent);
        updateTypeface();
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiBaseClock2
    public void onLanguageChanged() {
        super.onLanguageChanged();
        updateViewsLayoutParams();
        queryDataAsync();
        updateTypeface();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void queryDataAsync() {
        Context specialContext = WeatherFetcherController.Companion.getSpecialContext(this.mContext, this.mCurrentUserId);
        if (hasWeatherType()) {
            if (this.mWeatherFetcherController == null) {
                this.mWeatherFetcherController = new WeatherFetcherController();
            }
            this.mWeatherFetcherController.fetchWeatherData(specialContext, this.mHasPresetData, new WeatherFetcherController.Callback() { // from class: com.miui.clock.eastern.b.EasternArtBAodClock$$ExternalSyntheticLambda1
                @Override // com.miui.clock.utils.WeatherFetcherController.Callback
                public final void updateWeatherInfo(WeatherBean weatherBean) {
                    EasternArtBAodClock.$r8$lambda$lnxyQeGB_UHO2S7akOw7iJgBPfM(this.f$0, weatherBean);
                }
            });
        }
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        clearColorEffect(false);
        updateViewsLayoutParams();
        updateColor();
        queryDataAsync();
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setCurrentUserId(int i) {
        this.mCurrentUserId = i;
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    public void setWeatherBean(WeatherBean weatherBean) {
        this.mWeatherBean = weatherBean;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        Log.e("EasternArtBAodClock", "isFullAODType:" + ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()));
        this.mHour1.setTextColor(this.mClockInfo.getAodPrimaryColor());
        this.mHour2.setTextColor(this.mClockInfo.getAodPrimaryColor());
        this.mMinute1.setTextColor(this.mClockInfo.getAodPrimaryColor());
        this.mMinute2.setTextColor(this.mClockInfo.getAodPrimaryColor());
        this.mDateWeek.setTextColor(this.mClockInfo.getAodPrimaryColor());
        this.mDateYear.setTextColor(this.mClockInfo.getAodPrimaryColor());
        this.mDateMonthDay.setTextColor(this.mClockInfo.getAodPrimaryColor());
        this.mDateWeather.setTextColor(this.mClockInfo.getAodPrimaryColor());
        this.mColonBg.setColorFilter(new PorterDuffColorFilter(this.mClockInfo.getAodSecondaryColor(), PorterDuff.Mode.SRC_IN));
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        String timeString = ClassicClockTimeUtils.getTimeString(getHourInt(this.m24HourFormat, this.mCalendar), true);
        this.mHour1.setText(Character.toString(timeString.charAt(0)));
        this.mHour2.setText(Character.toString(timeString.charAt(1)));
        String timeString2 = ClassicClockTimeUtils.getTimeString(getMinuteInt(this.m24HourFormat, this.mCalendar), true);
        this.mMinute1.setText(Character.toString(timeString2.charAt(0)));
        this.mMinute2.setText(Character.toString(timeString2.charAt(1)));
        if (isChineseLanguageOrCountry()) {
            this.mDateWeek.setText(this.mCalendar.format(getContext(), getResources().getString(R.string.miui_eastern_art_b_week_view_format)));
            this.mDateYear.setText(ClassicClockTimeUtils.convertYearToChinese(this.mCalendar.format(getContext(), getResources().getString(R.string.miui_eastern_art_b_year_view_format))));
            String str = this.mCalendar.format(getContext(), getResources().getString(R.string.miui_eastern_art_b_data_view_format));
            String lunarCalendarString = ClassicClockTimeUtils.getLunarCalendarString(getContext(), this.mCalendar);
            this.mDateMonthDay.setText(getResources().getString(R.string.format_lunar_calendar_solar_terms, str, getResources().getString(R.string.format_lunar_calendar_solar_terms_continuous, ClassicClockTimeUtils.getSexagenaryCycleString(getContext(), this.mCalendar), lunarCalendarString)));
        } else {
            this.mDateWeek.setText(this.mCalendar.format(getContext(), getResources().getString(R.string.format_full_day_of_week)));
            this.mDateYear.setText(this.mCalendar.format(getContext(), getResources().getString(R.string.miui_smart_frame_clock_date)));
        }
        updateViewsLayoutParams();
    }

    public final void updateTypeface() {
        setTextTypeFace(this.mHour1, 2, 430, 20, false);
        setTextTypeFace(this.mHour2, 2, 430, 20, false);
        setTextTypeFace(this.mMinute1, 2, 430, 20, false);
        setTextTypeFace(this.mMinute2, 2, 430, 20, false);
        Arrays.asList(this.mDateWeek, this.mDateYear, this.mDateMonthDay, this.mDateWeather).forEach(new Consumer() { // from class: com.miui.clock.eastern.b.EasternArtBAodClock$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.setTextTypeFace((TextView) obj, 2, 330, 0, true);
            }
        });
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mConstraintLayoutParent.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mDateWeek.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mDateYear.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.mDateMonthDay.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams5 = (ConstraintLayout.LayoutParams) this.mDateWeather.getLayoutParams();
        layoutParams.setMargins(0, getDimen(R.dimen.miui_eastern_art_b_horizontal_aod_below_time_margin_top), 0, 0);
        this.mConstraintLayoutParent.setLayoutParams(layoutParams);
        layoutParams2.setMargins(0, getDimen(R.dimen.miui_eastern_art_b_horizontal_upper_aod_data_week_margin_top), 0, 0);
        this.mDateWeek.setLayoutParams(layoutParams2);
        layoutParams3.setMargins(0, getDimen(R.dimen.miui_eastern_art_b_horizontal_upper_aod_data_year_margin_week_top), 0, 0);
        this.mDateYear.setLayoutParams(layoutParams3);
        int i = R.dimen.miui_eastern_art_b_horizontal_textview_margin_top;
        layoutParams4.setMargins(0, getDimen(i), 0, 0);
        this.mDateMonthDay.setLayoutParams(layoutParams4);
        layoutParams5.setMargins(0, getDimen(i), 0, 0);
        this.mDateWeather.setLayoutParams(layoutParams5);
        float dimen = getDimen(R.dimen.miui_eastern_art_b_horizontal_below_time_text_size);
        this.mHour1.setTextSize(0, dimen);
        this.mMinute1.setTextSize(0, dimen);
        this.mHour2.setTextSize(0, dimen);
        this.mMinute2.setTextSize(0, dimen);
        final int dimen2 = getDimen(R.dimen.miui_eastern_art_b_horizontal_upper_text_size);
        Arrays.asList(this.mDateWeek, this.mDateYear, this.mDateMonthDay, this.mDateWeather).forEach(new Consumer() { // from class: com.miui.clock.eastern.b.EasternArtBAodClock$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((TextView) obj).setTextSize(0, dimen2);
            }
        });
        ConstraintLayout.LayoutParams layoutParams6 = (ConstraintLayout.LayoutParams) this.mColon.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams6).width = getDimen(R.dimen.miui_eastern_art_b_horizontal_below_colon_width);
        ((ViewGroup.MarginLayoutParams) layoutParams6).height = getDimen(R.dimen.miui_eastern_art_b_horizontal_below_colon_height);
        this.mColon.setLayoutParams(layoutParams6);
        ConstraintLayout.LayoutParams layoutParams7 = (ConstraintLayout.LayoutParams) this.mHour1.getLayoutParams();
        layoutParams7.setMarginEnd(getDimen(R.dimen.miui_eastern_art_b_horizontal_below_time_margin_right_value1));
        this.mHour1.setLayoutParams(layoutParams7);
        ConstraintLayout.LayoutParams layoutParams8 = (ConstraintLayout.LayoutParams) this.mHour2.getLayoutParams();
        int i2 = R.dimen.miui_eastern_art_b_horizontal_below_time_margin_right_value2;
        layoutParams8.setMarginEnd(getDimen(i2));
        this.mHour2.setLayoutParams(layoutParams8);
        ConstraintLayout.LayoutParams layoutParams9 = (ConstraintLayout.LayoutParams) this.mMinute1.getLayoutParams();
        layoutParams9.setMarginStart(getDimen(i2));
        this.mMinute1.setLayoutParams(layoutParams9);
        ConstraintLayout.LayoutParams layoutParams10 = (ConstraintLayout.LayoutParams) this.mMinute2.getLayoutParams();
        layoutParams10.setMarginStart(getDimen(R.dimen.miui_eastern_art_b_horizontal_below_time_margin_left_value1));
        this.mMinute2.setLayoutParams(layoutParams10);
    }

    public void updateWeatherInfo(WeatherBean weatherBean) {
        if (weatherBean == null) {
            return;
        }
        if (isChineseLanguageOrCountry()) {
            TextView textView = this.mDateWeather;
            StringBuilder sb = new StringBuilder();
            sb.append(getResources().getString(R.string.format_lunar_calendar_solar_terms, getResources().getString(R.string.format_lunar_calendar_solar_terms_continuous, weatherBean.getDescription(), ""), "" + weatherBean.getTemperature()));
            sb.append("°");
            textView.setText(sb.toString());
            return;
        }
        TextView textView2 = this.mDateWeather;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getResources().getString(R.string.format_lunar_calendar_solar_terms, weatherBean.getDescription(), "" + weatherBean.getTemperature()));
        sb2.append("°");
        textView2.setText(sb2.toString());
        this.mDateMonthDay.setText(weatherBean.getCityName());
    }
}
