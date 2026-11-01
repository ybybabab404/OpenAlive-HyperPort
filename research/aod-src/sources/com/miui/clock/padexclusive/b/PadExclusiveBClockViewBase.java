package com.miui.clock.padexclusive.b;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.aod.category.TextStyleCategoryInfo;
import com.miui.clock.MiuiClockController;
import com.miui.clock.MiuiGalleryBaseClock;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.Effect;
import com.miui.clock.module.WeatherBean;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.MiuiBlurUtils;
import com.miui.clock.utils.WeatherFetcherController;
import com.xiaomi.onetrack.h.d.a;
import com.xiaomi.onetrack.util.ac;
import java.util.Map;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class PadExclusiveBClockViewBase extends MiuiGalleryBaseClock implements MiuiClockController.IClockView {
    public static final int DEFAULT_BLEND_DARK_COLOR = Color.parseColor("#FFFFFF");
    public static final int DEFAULT_BLEND_DARK_COLOR_SECOND = Color.parseColor("#999999");
    public TextView airQuality;
    public TextView airQualityValue;
    public TextView date;
    public ConstraintLayout leftBottomView;
    public ConstraintLayout leftTopView;
    public ImageView localIcon;
    public TextView localName;
    public TextView localTemperature;
    public ConstraintLayout mCenterLargeView;
    public PadExclusiveBClockInfo mClockInfo;
    public int mCurrentUserId;
    public boolean mHasPresetData;
    public int mHorizontalPadding;
    public int mScreenHeight;
    public int mScreenWidth;
    public int mVerticalPadding;
    public WeatherFetcherController mWeatherFetcherController;
    public WindowManager mWm;
    public Map palette;
    public ConstraintLayout parent;
    public float ratio;
    public ConstraintLayout rightBottomView;
    public ConstraintLayout rightTopView;
    public int secondaryColor;
    public TextView smallDataWeekNotify;
    public boolean textDark;
    public TextView time;
    public TextView uvText;
    public TextView uvValue;
    public boolean wallpaperSupportDepth;
    public WeatherBean weatherBean;
    public TextView weatherDescription;
    public ImageView weatherIcon;
    public TextView weatherRange;
    public TextView week;

    /* JADX INFO: renamed from: com.miui.clock.padexclusive.b.PadExclusiveBClockViewBase$1, reason: invalid class name */
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
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_DATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_TIME.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.NOTIFICATION_DATA_INFO.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.LEFT_TOP_VIEW.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.LEFT_BOTTOM_VIEW.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.RIGHT_TOP_VIEW.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.RIGHT_BOTTOM_VIEW.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.CLOCK_CONTAINER.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.GRADIENT_CONTAINER.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$LyjCXS0sYZnJFrz3wYyVrpiTjx8(PadExclusiveBClockViewBase padExclusiveBClockViewBase, WeatherBean weatherBean) {
        if (padExclusiveBClockViewBase.isAttachedToWindow()) {
            if (weatherBean != null) {
                padExclusiveBClockViewBase.setWeatherBean(weatherBean);
                padExclusiveBClockViewBase.updateWeatherInfo(weatherBean);
            } else {
                padExclusiveBClockViewBase.setWeatherBean(null);
                padExclusiveBClockViewBase.clearWeatherInfo();
            }
        }
    }

    public PadExclusiveBClockViewBase(Context context) {
        super(context);
    }

    public PadExclusiveBClockViewBase(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void clearWeatherInfo() {
        setWeatherIcon(null);
        updateDrawableColor(this.weatherIcon, this.secondaryColor);
        this.weatherRange.setText("--");
        this.weatherDescription.setVisibility(4);
        this.uvValue.setText("--");
        this.localName.setText("--");
        this.localTemperature.setVisibility(4);
        this.airQualityValue.setText("--");
        Log.d("PadExclusiveBClockViewBase", "Clear Weather Info!");
    }

    private void setWeatherIcon(WeatherBean weatherBean) {
        if (weatherBean == null) {
            setImageResource(this.weatherIcon, R.drawable.weather_icon_null);
            return;
        }
        boolean z = ClockEffectUtils.isBlurMixType(this.mClockInfo.getClockEffect()) && DeviceConfig.supportBackgroundBlur(this.mContext);
        int i = (this.mCalendar.get(18) * 60) + this.mCalendar.get(20);
        setImageResource(this.weatherIcon, weatherBean.getIconResId(i < weatherBean.getSunriseMinuteTime() || i > weatherBean.getSunsetMinuteTime(), z));
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        PadExclusiveBClockInfo padExclusiveBClockInfo = this.mClockInfo;
        if (padExclusiveBClockInfo == null) {
            return;
        }
        int clockEffect = padExclusiveBClockInfo.getClockEffect();
        if (z || !ClockEffectUtils.isGradualType(clockEffect)) {
            resetGradientParams();
        }
        ClockEffectUtils.clearClockEffectsContainer(this, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.time, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.week, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.date, this.mClockInfo, z);
        ClockEffectUtils.clearClockGradualEffect(this.mCenterLargeView, this.mClockInfo, z);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getClockHeight() {
        return super.getClockHeight();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public ClockStyleInfo getClockStyleInfo() {
        return this.mClockInfo;
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public float getClockVisibleHeight() {
        return 0.0f;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public float[] getGradientParams() {
        PadExclusiveBClockInfo padExclusiveBClockInfo = this.mClockInfo;
        return padExclusiveBClockInfo == null ? Effect.EMPTY_GRADIENT : ClockEffectUtils.scaleGradientParams(padExclusiveBClockInfo.getLockGradientParams(), getScaleByGradientDesign());
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        switch (AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()]) {
            case 1:
                return this.week;
            case 2:
                return this.date;
            case 3:
                return this.time;
            case 4:
                return this.smallDataWeekNotify;
            case 5:
                return this.leftTopView;
            case 6:
                return this.leftBottomView;
            case 7:
                return this.rightTopView;
            case 8:
                return this.rightBottomView;
            case 9:
                return this.parent;
            case 10:
                return this.mCenterLargeView;
            default:
                return super.getIClockView(clockViewType);
        }
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getMagazineColor() {
        return super.getMagazineColor();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockBottom() {
        return this.mMagazineInfoVisible ? getDimen(R.dimen.miui_pad_exclusive_b_notification_magazine_margin_bottom) : getDimen(R.dimen.miui_pad_exclusive_b_notification_margin_bottom);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockTop() {
        return getDimen(R.dimen.miui_pad_exclusive_b_text_view_small_data_week_margin_time_view_top);
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public View getNotificationClockTopView() {
        return this.time;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    public float getRatio() {
        return this.ratio;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public float getScaleByGradientDesign() {
        return (this.mSmallestScreenWidth / DeviceConfig.getDesignDp(this.mContext)) / 2.5316455f;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public float getTopMargin() {
        return 0.0f;
    }

    public WeatherBean getWeatherBean() {
        return this.weatherBean;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    public final int getWeekTextSize(String str) {
        str.getClass();
        switch (str) {
            case "de":
            case "en":
            case "zh":
                return getDimen(R.dimen.miui_pad_exclusive_b_text_view_week_size);
            default:
                return getDimen(R.dimen.miui_pad_exclusive_b_text_view_tibetan_week_size);
        }
    }

    public final Typeface getWeekTypeface(String str) {
        str.getClass();
        if (str.equals(a.r)) {
            return FontUtils.getC800(this.mContext);
        }
        return !str.equals("zh") ? FontUtils.getMiSans(TextStyleCategoryInfo.MAX_FONT_WEIGHT) : FontUtils.getDelaGothicOne(this.mContext);
    }

    public final boolean isTibetan() {
        return "bo".equals(getResources().getConfiguration().locale.getLanguage());
    }

    @Override // com.miui.clock.MiuiBaseClock2, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
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
        this.parent = (ConstraintLayout) findViewById(R.id.pad_exclusive_b_parent);
        this.mCenterLargeView = (ConstraintLayout) findViewById(R.id.compose_pad_exclusive_b_center_large_container);
        this.time = (TextView) findViewById(R.id.tv_pad_exclusive_b_time);
        this.week = (TextView) findViewById(R.id.tv_pad_exclusive_b_week);
        this.date = (TextView) findViewById(R.id.tv_pad_exclusive_b_data);
        this.leftTopView = (ConstraintLayout) findViewById(R.id.compose_pad_exclusive_b_left_top);
        this.leftBottomView = (ConstraintLayout) findViewById(R.id.compose_pad_exclusive_b_left_bottom);
        this.rightTopView = (ConstraintLayout) findViewById(R.id.compose_pad_exclusive_b_right_top);
        this.rightBottomView = (ConstraintLayout) findViewById(R.id.compose_pad_exclusive_b_right_bottom);
        this.weatherIcon = (ImageView) findViewById(R.id.compose_pad_exclusive_b_lt_weather_icon);
        TextView textView = (TextView) findViewById(R.id.compose_pad_exclusive_b_lt_weather_range);
        this.weatherRange = textView;
        textView.setTypeface(FontUtils.getMiSans(450));
        TextView textView2 = (TextView) findViewById(R.id.compose_pad_exclusive_b_lt_weather_description);
        this.weatherDescription = textView2;
        textView2.setTypeface(FontUtils.getMiSans(450));
        TextView textView3 = (TextView) findViewById(R.id.compose_pad_exclusive_b_lb_UV_text);
        this.uvText = textView3;
        textView3.setTypeface(FontUtils.getMiSans(450));
        TextView textView4 = (TextView) findViewById(R.id.compose_pad_exclusive_b_lb_UV_value);
        this.uvValue = textView4;
        textView4.setTypeface(FontUtils.getMiSans(450));
        TextView textView5 = (TextView) findViewById(R.id.rb_air_quality);
        this.airQuality = textView5;
        textView5.setTypeface(FontUtils.getMiSans(450));
        TextView textView6 = (TextView) findViewById(R.id.rb_air_quality_value);
        this.airQualityValue = textView6;
        textView6.setTypeface(FontUtils.getMiSans(450));
        this.localIcon = (ImageView) findViewById(R.id.compose_pad_exclusive_b_rt_local_icon);
        TextView textView7 = (TextView) findViewById(R.id.compose_pad_exclusive_b_rt_local_name);
        this.localName = textView7;
        textView7.setTypeface(FontUtils.getMiSans(450));
        TextView textView8 = (TextView) findViewById(R.id.compose_pad_exclusive_b_rt_local_temperature);
        this.localTemperature = textView8;
        textView8.setTypeface(FontUtils.getMiSans(450));
        TextView textView9 = (TextView) findViewById(R.id.tv_pad_exclusive_b_small_data_week);
        this.smallDataWeekNotify = textView9;
        textView9.setTypeface(FontUtils.getMiSans(450));
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void onLanguageChanged() {
        updateViewsLayoutParams();
        queryDataAsync();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void onOrientationChange(int i) {
        super.onOrientationChange(i);
        PadExclusiveBClockInfo padExclusiveBClockInfo = this.mClockInfo;
        if (padExclusiveBClockInfo == null) {
            return;
        }
        padExclusiveBClockInfo.updateGradientTypeByOrientation(i);
        if (Effect.needHiddenGradient(getCurrentGradientParams())) {
            return;
        }
        updateGradientEffectColor(getGradientParams());
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void queryDataAsync() {
        Context specialContext = WeatherFetcherController.Companion.getSpecialContext(this.mContext, this.mCurrentUserId);
        if (this.mWeatherFetcherController == null) {
            this.mWeatherFetcherController = new WeatherFetcherController();
        }
        this.mWeatherFetcherController.fetchWeatherData(specialContext, this.mHasPresetData, new WeatherFetcherController.Callback() { // from class: com.miui.clock.padexclusive.b.PadExclusiveBClockViewBase$$ExternalSyntheticLambda0
            @Override // com.miui.clock.utils.WeatherFetcherController.Callback
            public final void updateWeatherInfo(WeatherBean weatherBean) {
                PadExclusiveBClockViewBase.$r8$lambda$LyjCXS0sYZnJFrz3wYyVrpiTjx8(this.f$0, weatherBean);
            }
        });
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        int color;
        int color2;
        super.setClockPalette(i, z, map, z2);
        this.textDark = z;
        this.palette = map;
        this.wallpaperSupportDepth = z2;
        if (this.mClockInfo == null || DeviceConfig.isHighTextContrastEnabled(this.mContext) || this.mClockInfo.isSuperSaveOpen() || this.mClockInfo == null) {
            return;
        }
        boolean zSupportBackgroundBlur = DeviceConfig.supportBackgroundBlur(this.mContext);
        int clockEffect = this.mClockInfo.getClockEffect();
        if (ClockEffectUtils.needPickSolidColor(clockEffect, zSupportBackgroundBlur)) {
            if (ClockEffectUtils.isBlurMixType(clockEffect)) {
                if (!this.mClockInfo.isAutoPrimaryColor()) {
                    this.mClockInfo.convertPrimaryColorByBlendColor(true);
                }
                if (!this.mClockInfo.isAutoSecondaryColor()) {
                    this.mClockInfo.convertSecondaryColorByBlendColor(true);
                }
            }
            if (this.mClockInfo.isAutoPrimaryColor()) {
                if (z) {
                    color2 = (map == null || map.get("secondary30") == null) ? this.mClockInfo.getPrimaryColor() : map.get("secondary30").intValue();
                } else {
                    color2 = Color.parseColor("#FFFFFFFF");
                }
                this.mClockInfo.setPrimaryColor(color2);
            }
            this.mClockInfo.setSecondaryColor(z ? Color.parseColor("#B4000000") : Color.parseColor("#FFFFFFFF"));
            if (ClockEffectUtils.isGradualType(this.mClockInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                ClockEffectUtils.setClockGradualEffect(getCurrentGradientParams(), this.mCenterLargeView, this.mClockInfo, false, false, 1.0f);
            }
        } else if (ClockEffectUtils.isDifferenceType(this.mClockInfo.getClockEffect())) {
            if (this.mClockInfo.isAutoPrimaryColor()) {
                if (z) {
                    if (map != null && map.get("secondary15") != null) {
                        int iIntValue = map.get("secondary15").intValue();
                        this.mClockInfo.setPrimaryColor(iIntValue);
                        this.mClockInfo.setBlendColor(iIntValue);
                    }
                } else if (map != null && map.get("secondary85") != null) {
                    int iIntValue2 = map.get("secondary85").intValue();
                    this.mClockInfo.setPrimaryColor(iIntValue2);
                    this.mClockInfo.setBlendColor(iIntValue2);
                }
            }
            if (DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                MiuiBlurUtils.setDiffEffectContainer(this, this.mClockInfo.isMultiWindowBlur());
                MiuiBlurUtils.setMemberDiffEffectColor(this.time, this.mClockInfo.getBlendColor());
                MiuiBlurUtils.setMemberDiffEffectColor(this.week, this.mClockInfo.getBlendColor());
                MiuiBlurUtils.setMemberDiffEffectColor(this.date, this.mClockInfo.getBlendColor());
            }
        } else if (ClockEffectUtils.isBlurMixType(this.mClockInfo.getClockEffect())) {
            int i2 = DEFAULT_BLEND_DARK_COLOR;
            int i3 = DEFAULT_BLEND_DARK_COLOR_SECOND;
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.miui_pad_exclusive_b_blur_radius);
            if (z) {
                color = (map == null || map.get("neutral-variant30") == null) ? this.mClockInfo.getPrimaryColor() : map.get("neutral-variant30").intValue();
            } else {
                color = Color.parseColor("#FFABABAB");
            }
            this.mClockInfo.setPrimaryColor(i2);
            this.mClockInfo.setSecondaryColor(i3);
            this.mClockInfo.setSecondaryBlendColor(color);
            if (this.mClockInfo.isAutoPrimaryColor()) {
                this.mClockInfo.setBlendColor(color);
            }
            if (DeviceConfig.supportBackgroundBlur(this.mContext)) {
                ClockEffectUtils.setClockEffectsContainer(this, dimensionPixelSize, this.mClockInfo, false, false);
                TextView textView = this.time;
                PadExclusiveBClockInfo padExclusiveBClockInfo = this.mClockInfo;
                ClockEffectUtils.setClockEffectsView(textView, padExclusiveBClockInfo, z, padExclusiveBClockInfo.getBlendColor(), this.mClockInfo.getPrimaryColor(), false, false);
                TextView textView2 = this.week;
                PadExclusiveBClockInfo padExclusiveBClockInfo2 = this.mClockInfo;
                ClockEffectUtils.setClockEffectsView(textView2, padExclusiveBClockInfo2, z, padExclusiveBClockInfo2.getBlendColor(), this.mClockInfo.getPrimaryColor(), false, false);
                TextView textView3 = this.date;
                PadExclusiveBClockInfo padExclusiveBClockInfo3 = this.mClockInfo;
                ClockEffectUtils.setClockEffectsView(textView3, padExclusiveBClockInfo3, z, padExclusiveBClockInfo3.getBlendColor(), this.mClockInfo.getPrimaryColor(), false, false);
                TextView textView4 = this.weatherRange;
                PadExclusiveBClockInfo padExclusiveBClockInfo4 = this.mClockInfo;
                ClockEffectUtils.setClockEffectsView(textView4, padExclusiveBClockInfo4, z, padExclusiveBClockInfo4.getSecondaryBlendColor(), this.mClockInfo.getSecondaryColor(), false, false);
                TextView textView5 = this.weatherDescription;
                PadExclusiveBClockInfo padExclusiveBClockInfo5 = this.mClockInfo;
                ClockEffectUtils.setClockEffectsView(textView5, padExclusiveBClockInfo5, z, padExclusiveBClockInfo5.getSecondaryBlendColor(), this.mClockInfo.getSecondaryColor(), false, false);
                TextView textView6 = this.uvText;
                PadExclusiveBClockInfo padExclusiveBClockInfo6 = this.mClockInfo;
                ClockEffectUtils.setClockEffectsView(textView6, padExclusiveBClockInfo6, z, padExclusiveBClockInfo6.getSecondaryBlendColor(), this.mClockInfo.getSecondaryColor(), false, false);
                TextView textView7 = this.uvValue;
                PadExclusiveBClockInfo padExclusiveBClockInfo7 = this.mClockInfo;
                ClockEffectUtils.setClockEffectsView(textView7, padExclusiveBClockInfo7, z, padExclusiveBClockInfo7.getSecondaryBlendColor(), this.mClockInfo.getSecondaryColor(), false, false);
                TextView textView8 = this.airQuality;
                PadExclusiveBClockInfo padExclusiveBClockInfo8 = this.mClockInfo;
                ClockEffectUtils.setClockEffectsView(textView8, padExclusiveBClockInfo8, z, padExclusiveBClockInfo8.getSecondaryBlendColor(), this.mClockInfo.getSecondaryColor(), false, false);
                TextView textView9 = this.airQualityValue;
                PadExclusiveBClockInfo padExclusiveBClockInfo9 = this.mClockInfo;
                ClockEffectUtils.setClockEffectsView(textView9, padExclusiveBClockInfo9, z, padExclusiveBClockInfo9.getSecondaryBlendColor(), this.mClockInfo.getSecondaryColor(), false, false);
                ImageView imageView = this.localIcon;
                PadExclusiveBClockInfo padExclusiveBClockInfo10 = this.mClockInfo;
                ClockEffectUtils.setClockEffectsView(imageView, padExclusiveBClockInfo10, z, padExclusiveBClockInfo10.getSecondaryBlendColor(), this.mClockInfo.getSecondaryColor(), false, false);
                TextView textView10 = this.localName;
                PadExclusiveBClockInfo padExclusiveBClockInfo11 = this.mClockInfo;
                ClockEffectUtils.setClockEffectsView(textView10, padExclusiveBClockInfo11, z, padExclusiveBClockInfo11.getSecondaryBlendColor(), this.mClockInfo.getSecondaryColor(), false, false);
                TextView textView11 = this.localTemperature;
                PadExclusiveBClockInfo padExclusiveBClockInfo12 = this.mClockInfo;
                ClockEffectUtils.setClockEffectsView(textView11, padExclusiveBClockInfo12, z, padExclusiveBClockInfo12.getSecondaryBlendColor(), this.mClockInfo.getSecondaryColor(), false, false);
            } else {
                this.mClockInfo.setSecondaryColor(z ? Color.parseColor("#4C000000") : Color.parseColor("#FFFFFFFF"));
                this.mClockInfo.setPrimaryColor(color);
            }
        }
        updateColor();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        if (clockStyleInfo instanceof PadExclusiveBClockInfo) {
            this.mClockInfo = (PadExclusiveBClockInfo) clockStyleInfo;
        }
        PadExclusiveBClockInfo padExclusiveBClockInfo = this.mClockInfo;
        if (padExclusiveBClockInfo == null) {
            return;
        }
        padExclusiveBClockInfo.updateGradientTypeByOrientation(this.mOrientation);
        clearColorEffect(false);
        queryDataAsync();
        updateViewsLayoutParams();
        updateColor();
        updateTime();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setCurrentUserId(int i) {
        this.mCurrentUserId = i;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    public final void setImageResource(ImageView imageView, int i) {
        if (imageView != null) {
            imageView.setImageResource(i);
        }
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setScaleRatio(float f) {
        this.ratio = f;
    }

    public final void setTemperatureText(TextView textView, int i) {
        if (textView != null) {
            textView.setText(i + getResources().getString(R.string.temperature_unit));
        }
    }

    public final void setText(TextView textView, String str) {
        if (textView != null) {
            textView.setText(str);
        }
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    public void setWeatherBean(WeatherBean weatherBean) {
        this.weatherBean = weatherBean;
        if (weatherBean == null) {
            updateWeatherInNoWeatherInfo();
        }
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        this.time.setTextColor(this.mClockInfo.getPrimaryColor() != 0 ? this.mClockInfo.getPrimaryColor() : -256);
        this.week.setTextColor(this.mClockInfo.getPrimaryColor() != 0 ? this.mClockInfo.getPrimaryColor() : -256);
        this.date.setTextColor(this.mClockInfo.getPrimaryColor() != 0 ? this.mClockInfo.getPrimaryColor() : -256);
        if (this.mClockInfo.getSecondaryColor() == 0) {
            this.secondaryColor = this.textDark ? -16777216 : -1;
        } else {
            this.secondaryColor = this.mClockInfo.getSecondaryColor();
        }
        Log.d("PadExclusiveBClockViewBase", "updateColor: textDark = " + this.textDark);
        updateDrawableColor(this.weatherIcon, this.secondaryColor);
        this.weatherRange.setTextColor(this.secondaryColor);
        this.weatherDescription.setTextColor(this.secondaryColor);
        this.uvText.setTextColor(this.secondaryColor);
        this.uvValue.setTextColor(this.secondaryColor);
        this.airQuality.setTextColor(this.secondaryColor);
        this.airQualityValue.setTextColor(this.secondaryColor);
        setImageResource(this.localIcon, R.drawable.miui_clock_location_icon);
        updateDrawableColor(this.localIcon, this.secondaryColor);
        this.localName.setTextColor(this.secondaryColor);
        this.localTemperature.setTextColor(this.secondaryColor);
        this.smallDataWeekNotify.setTextColor(this.secondaryColor);
    }

    public final void updateDrawableColor(ImageView imageView, int i) {
        Drawable drawable;
        if (imageView == null || (drawable = imageView.getDrawable()) == null || i == 0) {
            return;
        }
        drawable.setTint(i);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateGradientEffectColor(float[] fArr) {
        super.updateGradientEffectColor(fArr);
        PadExclusiveBClockInfo padExclusiveBClockInfo = this.mClockInfo;
        if (padExclusiveBClockInfo != null && ClockEffectUtils.isGradualType(padExclusiveBClockInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            MiuiBlurUtils.setGradientBlurMethod(this.mCenterLargeView, fArr);
        }
    }

    public final void updateScreenRealMetrics() {
        this.mWm = (WindowManager) this.mContext.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mWm.getDefaultDisplay().getRealMetrics(displayMetrics);
        boolean z = getResources().getConfiguration().orientation == 1;
        this.mScreenHeight = z ? Math.max(displayMetrics.heightPixels, displayMetrics.widthPixels) : Math.min(displayMetrics.heightPixels, displayMetrics.widthPixels);
        this.mScreenWidth = z ? Math.min(displayMetrics.heightPixels, displayMetrics.widthPixels) : Math.max(displayMetrics.heightPixels, displayMetrics.widthPixels);
        Log.d("PadExclusiveBClockViewBase", "port=" + z + " w=[" + this.mScreenWidth + "] h=[" + this.mScreenHeight + "] density=[" + displayMetrics.density + "] orientation=" + this.mContext.getResources().getConfiguration().orientation);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        this.m24HourFormat = DateFormatUtils.is24HourFormat(this.mContext);
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        this.date.setText(String.format("%s%s%s", Integer.valueOf(this.mCalendar.get(5) + 1), ".", Integer.valueOf(this.mCalendar.get(9))));
        TextView textView = this.date;
        Calendar calendar = this.mCalendar;
        Context context = this.mContext;
        textView.setContentDescription(calendar.format(context, context.getString(R.string.miui_magazine_clock_date_content_description)));
        this.time.setText(String.format("%s%s", ClassicClockTimeUtils.getTimeString(ClassicClockTimeUtils.getHourInt(this.m24HourFormat, this.mCalendar), true), ClassicClockTimeUtils.getTimeString(this.mCalendar.get(20), true)));
        this.time.setContentDescription(DateUtils.formatDateTime(getContext(), System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
        TextView textView2 = this.week;
        Calendar calendar2 = this.mCalendar;
        Context context2 = this.mContext;
        textView2.setText(calendar2.format(context2, context2.getString(R.string.classic_calendar_day_of_week_format)));
        this.smallDataWeekNotify.setText(this.mCalendar.format(this.mContext, getResources().getString(R.string.format_month_day_week)));
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTimeZone(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mCalendar.setTimeZone(TimeZone.getTimeZone(str));
        updateTime();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        updateScreenRealMetrics();
        this.mVerticalPadding = getDimen(R.dimen.miui_pad_exclusive_b_view_parent_padding_vertical);
        this.mHorizontalPadding = getDimen(R.dimen.miui_pad_exclusive_b_view_parent_padding_horizontal);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.parent.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mCenterLargeView.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.time.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.week.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams5 = (ConstraintLayout.LayoutParams) this.date.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams6 = (ConstraintLayout.LayoutParams) this.smallDataWeekNotify.getLayoutParams();
        int i = this.mScreenWidth;
        int i2 = this.mHorizontalPadding;
        ((ViewGroup.MarginLayoutParams) layoutParams).width = i - (i2 * 2);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = this.mScreenHeight - (this.mVerticalPadding * 2);
        layoutParams.setMarginStart(i2);
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = this.mVerticalPadding;
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = getDimen(R.dimen.miui_pad_exclusive_b_text_view_tibetan_week_margin_container_width);
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = -1;
        ((ViewGroup.MarginLayoutParams) layoutParams4).topMargin = getDimen(isTibetan() ? R.dimen.miui_pad_exclusive_b_text_view_tibetan_week_margin_container_top : R.dimen.miui_pad_exclusive_b_text_view_week_margin_container_top);
        ((ViewGroup.MarginLayoutParams) layoutParams5).topMargin = getDimen(isTibetan() ? R.dimen.miui_pad_exclusive_b_text_view_tibetan_data_margin_container_top : R.dimen.miui_pad_exclusive_b_text_view_data_margin_container_top);
        ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin = getDimen(R.dimen.miui_pad_exclusive_b_text_view_time_margin_container_parent_top);
        ((ViewGroup.MarginLayoutParams) layoutParams3).height = getDimen(R.dimen.miui_pad_exclusive_b_text_view_time_height);
        ((ViewGroup.MarginLayoutParams) layoutParams4).height = getDimen(isTibetan() ? R.dimen.miui_pad_exclusive_b_text_view_tibetan_week_height : R.dimen.miui_pad_exclusive_b_text_view_week_height);
        ((ViewGroup.MarginLayoutParams) layoutParams5).height = getDimen(R.dimen.miui_pad_exclusive_b_text_view_data_height);
        ((ViewGroup.MarginLayoutParams) layoutParams6).topMargin = getDimen(R.dimen.miui_pad_exclusive_b_text_view_small_data_week_margin_time_view_top);
        ConstraintLayout.LayoutParams layoutParams7 = (ConstraintLayout.LayoutParams) this.mMagazineGroup.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams7).topMargin = getDimen(R.dimen.miui_pad_exclusive_b_magazine_info_margin_top);
        this.mMagazineGroup.setLayoutParams(layoutParams7);
        Typeface c800 = FontUtils.getC800(this.mContext);
        this.time.setTypeface(c800);
        this.date.setTypeface(c800);
        this.week.setTypeface(getWeekTypeface(getResources().getConfiguration().locale.getLanguage()));
        this.time.setTextSize(0, getDimen(R.dimen.miui_pad_exclusive_b_text_view_time_size));
        this.week.setTextSize(0, getWeekTextSize(getResources().getConfiguration().locale.getLanguage()));
        this.date.setTextSize(0, getDimen(R.dimen.miui_pad_exclusive_b_text_view_data_size));
        ConstraintLayout.LayoutParams layoutParams8 = (ConstraintLayout.LayoutParams) this.weatherDescription.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams8).topMargin = getResources().getDimensionPixelSize(R.dimen.miui_pad_exclusive_b_view_weather_description_margin_top);
        ((ViewGroup.MarginLayoutParams) layoutParams8).height = getResources().getDimensionPixelSize(R.dimen.miui_pad_exclusive_b_view_weather_description_height);
        this.weatherDescription.setLayoutParams(layoutParams8);
        ConstraintLayout.LayoutParams layoutParams9 = (ConstraintLayout.LayoutParams) this.weatherRange.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams9).height = getResources().getDimensionPixelSize(R.dimen.miui_pad_exclusive_b_view_weather_range_height);
        this.weatherRange.setLayoutParams(layoutParams9);
        ConstraintLayout.LayoutParams layoutParams10 = (ConstraintLayout.LayoutParams) this.localName.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams10).height = getResources().getDimensionPixelSize(R.dimen.miui_pad_exclusive_b_view_local_name_height);
        this.localName.setLayoutParams(layoutParams10);
        ConstraintLayout.LayoutParams layoutParams11 = (ConstraintLayout.LayoutParams) this.localTemperature.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams11).topMargin = getResources().getDimensionPixelSize(R.dimen.miui_pad_exclusive_b_view_local_temperature_margin_top);
        ((ViewGroup.MarginLayoutParams) layoutParams11).height = getResources().getDimensionPixelSize(R.dimen.miui_pad_exclusive_b_view_local_temperature_height);
        this.localTemperature.setLayoutParams(layoutParams11);
        ConstraintLayout.LayoutParams layoutParams12 = (ConstraintLayout.LayoutParams) this.uvText.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams12).bottomMargin = getResources().getDimensionPixelSize(R.dimen.miui_pad_exclusive_b_view_uv_text_margin_bottom);
        ((ViewGroup.MarginLayoutParams) layoutParams12).height = getResources().getDimensionPixelSize(R.dimen.miui_pad_exclusive_b_view_uv_text_height);
        this.uvText.setLayoutParams(layoutParams12);
        ConstraintLayout.LayoutParams layoutParams13 = (ConstraintLayout.LayoutParams) this.uvValue.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams13).height = getResources().getDimensionPixelSize(R.dimen.miui_pad_exclusive_b_view_uv_value_height);
        this.uvValue.setLayoutParams(layoutParams13);
        ConstraintLayout.LayoutParams layoutParams14 = (ConstraintLayout.LayoutParams) this.airQuality.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams14).bottomMargin = getResources().getDimensionPixelSize(R.dimen.miui_pad_exclusive_b_view_air_quality_margin_bottom);
        ((ViewGroup.MarginLayoutParams) layoutParams14).height = getResources().getDimensionPixelSize(R.dimen.miui_pad_exclusive_b_view_air_quality_height);
        this.airQuality.setLayoutParams(layoutParams14);
        ConstraintLayout.LayoutParams layoutParams15 = (ConstraintLayout.LayoutParams) this.airQualityValue.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams15).height = getResources().getDimensionPixelSize(R.dimen.miui_pad_exclusive_b_view_air_quality_value_height);
        this.airQualityValue.setLayoutParams(layoutParams15);
        setText(this.airQuality, getResources().getString(R.string.miui_clock_os2_pad_exclusive_b_air_quality));
        setText(this.uvText, getResources().getString(R.string.miui_clock_os2_pad_exclusive_b_ultraviolet_radiation));
        Log.d("PadExclusiveBClockViewBase", "topBottomPadding=" + this.mVerticalPadding + " startEndPadding=" + this.mHorizontalPadding + " parent w=" + ((ViewGroup.MarginLayoutParams) layoutParams).width + " h=" + ((ViewGroup.MarginLayoutParams) layoutParams).height);
    }

    public void updateWeatherInNoWeatherInfo() {
        ConstraintLayout constraintLayout = this.leftTopView;
        Resources resources = getResources();
        int i = R.string.weather_no_data;
        constraintLayout.setContentDescription(resources.getString(i));
        this.leftBottomView.setContentDescription(getResources().getString(i));
        this.rightTopView.setContentDescription(getResources().getString(i));
        this.rightBottomView.setContentDescription(getResources().getString(i));
    }

    public void updateWeatherInfo(WeatherBean weatherBean) {
        String str;
        if (weatherBean == null) {
            Log.d("PadExclusiveBClockViewBase", "updateWeatherInfo  bean == null");
            return;
        }
        setWeatherIcon(weatherBean);
        updateDrawableColor(this.weatherIcon, this.secondaryColor);
        this.weatherDescription.setVisibility(0);
        int highestTemperature = weatherBean.getHighestTemperature();
        int lowestTemperature = weatherBean.getLowestTemperature();
        TextView textView = this.weatherRange;
        StringBuilder sb = new StringBuilder();
        sb.append(highestTemperature);
        Resources resources = getResources();
        int i = R.string.temperature_unit;
        sb.append(resources.getString(i));
        sb.append("/");
        sb.append(lowestTemperature);
        sb.append(getResources().getString(i));
        setText(textView, sb.toString());
        setText(this.weatherDescription, weatherBean.getDescription());
        TextView textView2 = this.uvText;
        Resources resources2 = getResources();
        int i2 = R.string.miui_clock_os2_pad_exclusive_b_ultraviolet_radiation;
        setText(textView2, resources2.getString(i2));
        setText(this.uvValue, getResources().getString(weatherBean.getUVILevelDescResID()) + "-" + weatherBean.getUVILevel());
        TextView textView3 = this.airQuality;
        Resources resources3 = getResources();
        int i3 = R.string.miui_clock_os2_pad_exclusive_b_air_quality;
        setText(textView3, resources3.getString(i3));
        TextView textView4 = this.airQualityValue;
        if (weatherBean.isAQIDateValid()) {
            str = weatherBean.getAQILevel() + "-" + getResources().getString(weatherBean.getAqiLevelDescResID());
        } else {
            str = "--";
        }
        setText(textView4, str);
        this.localTemperature.setVisibility(0);
        setText(this.localName, weatherBean.getCityName());
        setTemperatureText(this.localTemperature, weatherBean.getTemperature());
        String str2 = highestTemperature + getResources().getString(i) + "/" + lowestTemperature + getResources().getString(i) + ac.b + weatherBean.getDescription();
        String str3 = getResources().getString(i2) + ac.b + getResources().getString(weatherBean.getUVILevelDescResID());
        String str4 = weatherBean.getCityName() + ac.b + weatherBean.getTemperature() + getResources().getString(i);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getResources().getString(i3));
        sb2.append(ac.b);
        sb2.append(weatherBean.isAQIDateValid() ? getResources().getString(weatherBean.getAqiLevelDescResID()) : "--");
        String string = sb2.toString();
        this.leftTopView.setContentDescription(str2);
        this.leftBottomView.setContentDescription(str3);
        this.rightTopView.setContentDescription(str4);
        this.rightBottomView.setContentDescription(string);
        updateDrawableColor(this.localIcon, this.secondaryColor);
        Log.d("PadExclusiveBClockViewBase", "updateWeather: " + weatherBean + " secondaryColor=" + this.secondaryColor);
    }
}
