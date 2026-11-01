package com.miui.clock.magazine;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.icu.text.SimpleDateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.miui.clock.MiuiGalleryBaseClock;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.Effect;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.ColorUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.GlobalColorUtils;
import com.miui.clock.utils.MiuiBlurUtils;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class MiuiMagazineBBase extends MiuiGalleryBaseClock {
    public ViewGroup mClockContainer;
    public MiuiMagazineBBaseInfo mClockInfo;
    public TextView mDateText;
    public TextView mTimeText;
    public TextView mWeekText;

    /* JADX INFO: renamed from: com.miui.clock.magazine.MiuiMagazineBBase$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.FULL_DATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_TIME.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_WEEK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.CLOCK_CONTAINER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public MiuiMagazineBBase(Context context) {
        super(context);
    }

    public MiuiMagazineBBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        if (this.mClockInfo == null) {
            return;
        }
        if (!ClockEffectUtils.isLessThanOS2()) {
            ClockEffectUtils.clearClockEffectsContainer(this, this.mClockInfo, z);
            ClockEffectUtils.clearClockEffectsView(this.mClockContainer, this.mClockInfo, z);
        } else if (DeviceConfig.supportBackgroundBlur(this.mContext) || DeviceConfig.isHighTextContrastEnabled(this.mContext)) {
            MiuiBlurUtils.clearContainerPassBlur(this);
            MiuiBlurUtils.clearMemberBlendColor(this.mClockContainer);
        }
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getBackgroundBlurContainer() {
        return this;
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public ClockStyleInfo getClockStyleInfo() {
        return this.mClockInfo;
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
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i == 1) {
            return this.mDateText;
        }
        if (i == 2) {
            return this.mTimeText;
        }
        if (i != 3) {
            return i != 4 ? super.getIClockView(clockViewType) : this.mClockContainer;
        }
        return this.mWeekText;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getMagazineColor() {
        MiuiMagazineBBaseInfo miuiMagazineBBaseInfo = this.mClockInfo;
        if (miuiMagazineBBaseInfo != null) {
            return ColorUtils.blendColor(miuiMagazineBBaseInfo.getOriginMagazineColor(), 0.6f);
        }
        return 0;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockBottom() {
        return this.mMagazineInfoVisible ? getDimen(R.dimen.notification_bottom_magazine_b_with_gallery) : getDimen(R.dimen.notification_bottom_magazine_b);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockTop() {
        return getDimen(R.dimen.miui_magazine_b_clock_notification_margin_top);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mDateText = (TextView) findViewById(R.id.current_date);
        this.mWeekText = (TextView) findViewById(R.id.current_week);
        this.mTimeText = (TextView) findViewById(R.id.current_time);
        this.mClockContainer = (ViewGroup) findViewById(R.id.clock_container);
        updateViewsLayoutParams();
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
        String str;
        int iTransformAodColor;
        int color;
        int originMagazineColor;
        super.setClockPalette(i, z, map, z2);
        if (map != null) {
            str = "secondary30=" + map.get("secondary30");
        } else {
            str = "null";
        }
        Log.d("ClockPalette", "setClockPalette: type = " + i + ", textDark = " + z + ", palette = " + str);
        if (this.mClockInfo == null || DeviceConfig.isHighTextContrastEnabled(this.mContext)) {
            return;
        }
        int clockEffect = this.mClockInfo.getClockEffect();
        boolean zSupportBackgroundBlur = DeviceConfig.supportBackgroundBlur(this.mContext);
        if (!ClockEffectUtils.needPickSolidColor(clockEffect) || this.mClockInfo.isAutoPrimaryColor()) {
            if (!ClockEffectUtils.isGradualType(clockEffect) || !DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                if (ClockEffectUtils.isDifferenceType(clockEffect)) {
                    if (this.mClockInfo.isAutoPrimaryColor()) {
                        if (z) {
                            if (map != null && map.get("secondary15") != null) {
                                int iIntValue = map.get("secondary15").intValue();
                                if (ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType())) {
                                    this.mClockInfo.setPrimaryColor(GlobalColorUtils.transformAodColor(iIntValue));
                                } else {
                                    this.mClockInfo.setPrimaryColor(iIntValue);
                                }
                                this.mClockInfo.setBlendColor(iIntValue);
                            }
                        } else if (map != null && map.get("secondary85") != null) {
                            int iIntValue2 = map.get("secondary85").intValue();
                            if (ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType())) {
                                this.mClockInfo.setPrimaryColor(GlobalColorUtils.transformAodColor(iIntValue2));
                            } else {
                                this.mClockInfo.setPrimaryColor(iIntValue2);
                            }
                            this.mClockInfo.setBlendColor(iIntValue2);
                        }
                    }
                    if (!ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()) && DeviceConfig.supportBackgroundBlur(this.mContext)) {
                        updateClockEffectsContainer();
                        updateClockBlendColor();
                        MiuiBlurUtils.setMemberDiffEffectColor(this.mClockContainer, this.mClockInfo.getBlendColor());
                    }
                    this.mClockInfo.convertAnimationColor();
                    MiuiMagazineBBaseInfo miuiMagazineBBaseInfo = this.mClockInfo;
                    miuiMagazineBBaseInfo.setOriginMagazineColor(miuiMagazineBBaseInfo.getBlendColor());
                } else if (ClockEffectUtils.isGlowType(clockEffect) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                    int blendColor = this.mClockInfo.getBlendColor();
                    Effect.Glow glowParam = Effect.GlowEffect.getGlowParam(blendColor);
                    MiuiBlurUtils.setGlowEffectContainer(this);
                    MiuiBlurUtils.setGlowEffectMethod(this.mClockContainer, glowParam, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()));
                    this.mClockInfo.setOriginMagazineColor(blendColor);
                } else {
                    int primaryColor = -1;
                    if (ClockEffectUtils.isBlurMixType(clockEffect) && zSupportBackgroundBlur) {
                        if (this.mClockInfo.isAutoPrimaryColor()) {
                            if (z) {
                                color = (map == null || map.get("neutral-variant30") == null) ? this.mClockInfo.getPrimaryColor() : map.get("neutral-variant30").intValue();
                            } else {
                                color = Color.parseColor("#FFABABAB");
                            }
                            if (z) {
                                iTransformAodColor = (map == null || map.get("secondary70") == null) ? color : map.get("secondary70").intValue();
                            } else {
                                iTransformAodColor = Color.parseColor("#FFBEBEBE");
                            }
                            if (z) {
                                originMagazineColor = (map == null || map.get("neutral-variant30") == null) ? this.mClockInfo.getOriginMagazineColor() : map.get("neutral-variant30").intValue();
                            } else {
                                originMagazineColor = -1;
                            }
                            this.mClockInfo.setBlendColor(color);
                            this.mClockInfo.setFullAodSecondaryBlendColor(Color.parseColor("#FFABABAB"));
                            this.mClockInfo.setOriginMagazineColor(originMagazineColor);
                        } else {
                            int blendColor2 = this.mClockInfo.getBlendColor();
                            iTransformAodColor = GlobalColorUtils.transformAodColor(blendColor2);
                            this.mClockInfo.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(blendColor2));
                            this.mClockInfo.setOriginMagazineColor(blendColor2);
                        }
                        this.mClockInfo.setPrimaryColor(-1);
                        this.mClockInfo.setAodPrimaryColor(iTransformAodColor);
                        this.mClockInfo.setAodBlendColor(iTransformAodColor);
                        if (!ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType())) {
                            updateClockEffectsContainer();
                            updateClockBlendColor();
                        }
                    } else {
                        if (ClockEffectUtils.isBlurMixType(clockEffect)) {
                            if (!this.mClockInfo.isAutoPrimaryColor()) {
                                this.mClockInfo.convertPrimaryColorByBlendColor(true);
                            }
                            if (!this.mClockInfo.isAutoSecondaryColor()) {
                                this.mClockInfo.convertSecondaryColorByBlendColor(true);
                            }
                        }
                        int primaryColor2 = this.mClockInfo.getPrimaryColor();
                        if (this.mClockInfo.isAutoPrimaryColor()) {
                            if (z) {
                                primaryColor = (map == null || map.get("secondary30") == null) ? this.mClockInfo.getPrimaryColor() : map.get("secondary30").intValue();
                            }
                            primaryColor2 = primaryColor;
                        }
                        this.mClockInfo.setPrimaryColor(primaryColor2);
                        this.mClockInfo.setOriginMagazineColor(primaryColor2);
                    }
                    this.mClockInfo.convertAnimationColor();
                }
            }
            updateColor();
        }
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        this.mClockInfo = (MiuiMagazineBBaseInfo) clockStyleInfo;
        clearColorEffect(false);
        updateTime();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
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

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2, int i3, int i4) {
        if (this.mClockInfo != null) {
            boolean zUpdateTextDark = updateTextDark(this.mTextDark);
            int textDarkAlpha = getTextDarkAlpha(this.mTextDark);
            ViewGroup viewGroup = this.mClockContainer;
            MiuiMagazineBBaseInfo miuiMagazineBBaseInfo = this.mClockInfo;
            ClockEffectUtils.setClockEffectsView(viewGroup, miuiMagazineBBaseInfo, zUpdateTextDark, i, miuiMagazineBBaseInfo.getPrimaryColor(), 0, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()), textDarkAlpha);
        }
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockEffectsContainer() {
        if (this.mClockInfo == null) {
            return;
        }
        super.updateClockEffectsContainer();
        updateViewBackgroundBlurContainer(this, getDimen(R.dimen.miui_magazine_b_clock_blur_radius), this.mClockContainer);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        if (ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType())) {
            this.mDateText.setTextColor(this.mClockInfo.getAodPrimaryColor());
            this.mWeekText.setTextColor(this.mClockInfo.getAodPrimaryColor());
            this.mTimeText.setTextColor(this.mClockInfo.getAodPrimaryColor());
        } else {
            this.mDateText.setTextColor(this.mClockInfo.getPrimaryColor());
            this.mWeekText.setTextColor(this.mClockInfo.getPrimaryColor());
            this.mTimeText.setTextColor(this.mClockInfo.getPrimaryColor());
        }
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        if (this.mClockInfo != null) {
            this.mTimeText.setText(DateFormatUtils.formatTime(this.mContext.getString(this.m24HourFormat ? R.string.miui_clock_time_format_24 : R.string.miui_clock_time_format_12)));
            this.mDateText.setText(DateFormatUtils.formatTime(this.mContext.getString(R.string.miui_magazine_clock_date)).toUpperCase());
            TextView textView = this.mDateText;
            Calendar calendar = this.mCalendar;
            Context context = this.mContext;
            textView.setContentDescription(calendar.format(context, context.getString(R.string.miui_magazine_clock_date_content_description)));
            this.mWeekText.setText(new SimpleDateFormat("EE", Locale.US).format(new Date()).toUpperCase());
            TextView textView2 = this.mWeekText;
            Calendar calendar2 = this.mCalendar;
            Context context2 = this.mContext;
            textView2.setContentDescription(calendar2.format(context2, context2.getString(R.string.miui_magazine_clock_week)));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mDateText.getLayoutParams();
            if (!DeviceConfig.isJ18() || DeviceConfig.inLargeScreen(this.mContext)) {
                TextView textView3 = this.mTimeText;
                int i = R.dimen.miui_magazine_b_clock_text_size;
                textView3.setTextSize(0, getDimen(i));
                this.mDateText.setTextSize(0, getDimen(i));
                this.mWeekText.setTextSize(0, getDimen(i));
                layoutParams.setMarginEnd(getDimen(R.dimen.miui_magazine_b_clock_date_margin_end));
            } else {
                TextView textView4 = this.mTimeText;
                int i2 = R.dimen.miui_magazine_b_clock_text_size_j18;
                textView4.setTextSize(0, getDimen(i2));
                this.mDateText.setTextSize(0, getDimen(i2));
                this.mWeekText.setTextSize(0, getDimen(i2));
                layoutParams.setMarginEnd(getDimen(R.dimen.miui_magazine_b_clock_date_margin_end_j18));
            }
            this.mDateText.setLayoutParams(layoutParams);
            updateColor();
        }
    }
}
