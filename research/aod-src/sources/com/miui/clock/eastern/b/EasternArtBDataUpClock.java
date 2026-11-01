package com.miui.clock.eastern.b;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.WeatherBean;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.ColorUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.WeatherFetcherController;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class EasternArtBDataUpClock extends EasternArtBBase {
    public int mCurrentUserId;
    public TextView mData;
    public boolean mHasPresetData;
    public TextView mWeather;
    public WeatherBean mWeatherBean;
    public WeatherFetcherController mWeatherFetcherController;
    public TextView mWeek;
    public TextView mYear;

    /* JADX INFO: renamed from: com.miui.clock.eastern.b.EasternArtBDataUpClock$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.FULL_WEEK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_YEAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_DATE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.WEATHER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$eeWCyjXxuHuAtdZBK8NJAC_ZSmE(EasternArtBDataUpClock easternArtBDataUpClock, WeatherBean weatherBean) {
        if (easternArtBDataUpClock.isAttachedToWindow()) {
            if (weatherBean != null) {
                easternArtBDataUpClock.setWeatherBean(weatherBean);
                easternArtBDataUpClock.updateWeatherInfo(weatherBean);
            } else {
                easternArtBDataUpClock.setWeatherBean(null);
                easternArtBDataUpClock.clearWeatherInfo();
            }
        }
    }

    public EasternArtBDataUpClock(Context context) {
        super(context);
    }

    public EasternArtBDataUpClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCountry = Locale.getDefault().getCountry();
    }

    public EasternArtBDataUpClock(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private void clearWeatherInfo() {
        this.mWeather.setText("--°");
        if (!isChineseLanguageOrCountry()) {
            this.mData.setText("--");
        }
        Log.d("EasternArtB", "Clear Weather Info!");
    }

    private boolean hasWeatherType() {
        return true;
    }

    private void updateTypeface() {
        setTextTypeFace(this.mWeek, 2, 330, 0, true);
        setTextTypeFace(this.mYear, 2, 330, 0, true);
        setTextTypeFace(this.mData, 2, 330, 0, true);
        setTextTypeFace(this.mWeather, 2, 330, 0, true);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        EasternArtBBaseInfo easternArtBBaseInfo = this.mClockInfo;
        if (easternArtBBaseInfo == null) {
            return;
        }
        ClockEffectUtils.clearClockEffectsContainer(this, easternArtBBaseInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mWeek, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mYear, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mData, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mWeather, this.mClockInfo, z);
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
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i == 1) {
            return this.mWeek;
        }
        if (i == 2) {
            return this.mYear;
        }
        if (i != 3) {
            return i != 4 ? super.getIClockView(clockViewType) : this.mWeather;
        }
        return this.mData;
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getMagazineColor() {
        EasternArtBBaseInfo easternArtBBaseInfo = this.mClockInfo;
        if (easternArtBBaseInfo != null) {
            return ColorUtils.blendColor(easternArtBBaseInfo.getOriginMagazineColor(), 0.6f);
        }
        return -1;
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
        this.mWeek = (TextView) findViewById(R.id.data_upper_view_week_zh);
        this.mYear = (TextView) findViewById(R.id.data_upper_view_year_zh);
        this.mData = (TextView) findViewById(R.id.data_upper_view_data_zh);
        this.mWeather = (TextView) findViewById(R.id.data_upper_view_weather_zh);
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
            this.mWeatherFetcherController.fetchWeatherData(specialContext, this.mHasPresetData, new WeatherFetcherController.Callback() { // from class: com.miui.clock.eastern.b.EasternArtBDataUpClock$$ExternalSyntheticLambda0
                @Override // com.miui.clock.utils.WeatherFetcherController.Callback
                public final void updateWeatherInfo(WeatherBean weatherBean) {
                    EasternArtBDataUpClock.$r8$lambda$eeWCyjXxuHuAtdZBK8NJAC_ZSmE(this.f$0, weatherBean);
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

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        super.setClockPalette(i, z, map, z2);
        if (this.mClockInfo == null || DeviceConfig.isHighTextContrastEnabled(this.mContext) || this.mClockInfo.isSuperSaveOpen()) {
            return;
        }
        updateClockBlendColor();
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        clearColorEffect(false);
        updateViewsLayoutParams();
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

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2, int i3, int i4) {
        boolean zIsAODType = ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType());
        boolean zIsFullAODType = ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType());
        boolean zUpdateTextDark = updateTextDark(this.mTextDark);
        int textDarkAlpha = getTextDarkAlpha(this.mTextDark);
        updateViewBackgroundBlurContainer(this, getDimen(R.dimen.miui_eastern_art_c_clock_blur_radius), this.mWeek, this.mYear, this.mData, this.mWeather);
        TextView textView = this.mWeek;
        EasternArtBBaseInfo easternArtBBaseInfo = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView, easternArtBBaseInfo, zUpdateTextDark, i, easternArtBBaseInfo.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        TextView textView2 = this.mYear;
        EasternArtBBaseInfo easternArtBBaseInfo2 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView2, easternArtBBaseInfo2, zUpdateTextDark, i, easternArtBBaseInfo2.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        TextView textView3 = this.mData;
        EasternArtBBaseInfo easternArtBBaseInfo3 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView3, easternArtBBaseInfo3, zUpdateTextDark, i, easternArtBBaseInfo3.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        TextView textView4 = this.mWeather;
        EasternArtBBaseInfo easternArtBBaseInfo4 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView4, easternArtBBaseInfo4, zUpdateTextDark, i, easternArtBBaseInfo4.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockColor(int i, int i2) {
        updatePrimaryColor(i);
        updateSecondaryColor(i2);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        updateClockColor(this.mClockInfo.getPrimaryColor(), this.mClockInfo.getSecondaryColor());
    }

    public void updatePrimaryColor(int i) {
        this.mWeek.setTextColor(i);
        this.mYear.setTextColor(i);
        this.mData.setTextColor(i);
        this.mWeather.setTextColor(i);
    }

    public void updateSecondaryColor(int i) {
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        if (!isChineseLanguageOrCountry()) {
            this.mWeek.setText(this.mCalendar.format(getContext(), getResources().getString(R.string.format_full_day_of_week)));
            this.mYear.setText(this.mCalendar.format(getContext(), getResources().getString(R.string.miui_smart_frame_clock_date)));
            return;
        }
        this.mWeek.setText(this.mCalendar.format(getContext(), getResources().getString(R.string.miui_eastern_art_b_week_view_format)));
        this.mYear.setText(ClassicClockTimeUtils.convertYearToChinese(this.mCalendar.format(getContext(), getResources().getString(R.string.miui_eastern_art_b_year_view_format))));
        this.mData.setText(getResources().getString(R.string.format_lunar_calendar_solar_terms, this.mCalendar.format(getContext(), getResources().getString(R.string.miui_eastern_art_b_data_view_format)), getResources().getString(R.string.format_lunar_calendar_solar_terms_continuous, ClassicClockTimeUtils.getSexagenaryCycleString(getContext(), this.mCalendar), ClassicClockTimeUtils.getLunarCalendarString(getContext(), this.mCalendar))));
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mWeek.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mYear.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mData.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.mWeather.getLayoutParams();
        this.mWeek.setVisibility(0);
        this.mYear.setVisibility(0);
        this.mData.setVisibility(0);
        this.mWeather.setVisibility(0);
        layoutParams.setMargins(0, getDimen(R.dimen.miui_eastern_art_b_horizontal_upper_data_week_margin_top), 0, 0);
        this.mWeek.setLayoutParams(layoutParams);
        layoutParams2.setMargins(0, getDimen(R.dimen.miui_eastern_art_b_horizontal_upper_data_year_margin_week_top), 0, 0);
        this.mYear.setLayoutParams(layoutParams2);
        int i = R.dimen.miui_eastern_art_b_horizontal_textview_margin_top;
        layoutParams3.setMargins(0, getDimen(i), 0, 0);
        this.mData.setLayoutParams(layoutParams3);
        layoutParams4.setMargins(0, getDimen(i), 0, 0);
        this.mWeather.setLayoutParams(layoutParams4);
        TextView textView = this.mWeek;
        int i2 = R.dimen.miui_eastern_art_b_horizontal_upper_text_size;
        textView.setTextSize(0, getDimen(i2));
        this.mYear.setTextSize(0, getDimen(i2));
        this.mData.setTextSize(0, getDimen(i2));
        this.mWeather.setTextSize(0, getDimen(i2));
    }

    public void updateWeatherInfo(WeatherBean weatherBean) {
        if (weatherBean == null) {
            Log.d("EasternArtB", "updateWeatherInfo  bean == null");
            return;
        }
        if (!isChineseLanguageOrCountry()) {
            this.mWeather.setText(getResources().getString(R.string.format_lunar_calendar_solar_terms, weatherBean.getDescription(), "" + weatherBean.getTemperature() + "°"));
            this.mData.setText(weatherBean.getCityName());
            return;
        }
        TextView textView = this.mWeather;
        StringBuilder sb = new StringBuilder();
        sb.append(getResources().getString(R.string.format_lunar_calendar_solar_terms, getResources().getString(R.string.format_lunar_calendar_solar_terms_continuous, weatherBean.getDescription(), ""), "" + weatherBean.getTemperature()));
        sb.append("°");
        textView.setText(sb.toString());
    }
}
