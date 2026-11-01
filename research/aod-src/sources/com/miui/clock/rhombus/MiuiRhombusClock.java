package com.miui.clock.rhombus;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.miui.clock.MiuiClockNumberView;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.BaseFontStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.DiffusionType;
import com.miui.clock.module.Effect;
import com.miui.clock.module.FontWholeHourCopperplate;
import com.miui.clock.module.FontWholeMinuteCopperplate;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.GlobalColorUtils;
import com.miui.clock.utils.MiuiBlurUtils;
import com.miui.clock.utils.SvgUtils;
import java.util.Map;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class MiuiRhombusClock extends MiuiRhombusBase {
    public FontWholeHourCopperplate hourCopperplate;
    public ViewGroup mAccessibilityContainer;
    public View mColon1View;
    public View mColon2View;
    public ImageView mColonView;
    public ViewGroup mDateContainer;
    public TextView mDateText;
    public float mDeviceScale;
    public boolean mHasUserDefinedColor;
    public View mHourContainer;
    public boolean mInFaceUnlockStatus;
    public ViewGroup mMagazineGroup;
    public View mMinuteContainer;
    public TextView mNotificationDateText;
    public ViewGroup mTimeContainer;
    public TextView mWeekText;
    public FontWholeMinuteCopperplate minuteCopperplate;
    public boolean supportAccessibilityScale;

    /* JADX INFO: renamed from: com.miui.clock.rhombus.MiuiRhombusClock$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.COLON1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.COLON2.ordinal()] = 2;
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
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_HOUR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_MINUTE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_TIME.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_COLON.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.MAGAZINE_INFO.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.NOTIFICATION_DATE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    public MiuiRhombusClock(Context context) {
        super(context);
        this.mDeviceScale = -1.0f;
        this.supportAccessibilityScale = true;
        this.mHasUserDefinedColor = false;
    }

    public MiuiRhombusClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDeviceScale = -1.0f;
        this.supportAccessibilityScale = true;
        this.mHasUserDefinedColor = false;
    }

    public final float calculateScaleGradientTranslateY(float f, float f2, int i, int i2) {
        float f3;
        float dimen = getDimen(R.dimen.miui_rhombus_clock_height) * f;
        float f4 = -(((DeviceConfig.inLargeScreen(getContext()) ? i2 : Math.max(i2, i)) - dimen) / 2.0f);
        int[] iArr = new int[2];
        ClockViewType clockViewType = ClockViewType.HOUR1;
        getClockViewPosition(clockViewType, iArr);
        int largeClockHeight = (int) (this.style.getLargeClockHeight() * this.mDensity * f);
        int rhombusClockHeight = (int) (getRhombusClockHeight(clockViewType) * this.mDensity * f);
        BaseFontStyle.Style fontStyle = this.style.getFontStyle();
        BaseFontStyle.Style style = BaseFontStyle.Style.Copperplate;
        if (fontStyle != style && this.style.enableRotate()) {
            rhombusClockHeight = (int) (getRotateRhombusClockInfo(clockViewType).maxHeight * this.mDensity * f);
        }
        if (this.style.getFontStyle() == style) {
            f3 = dimen;
        } else {
            if (this.style.enableRotate()) {
                largeClockHeight = rhombusClockHeight;
            }
            f3 = largeClockHeight;
        }
        float f5 = 1.0f - f2;
        float f6 = 0.5f * f5;
        int i3 = iArr[1];
        float f7 = (dimen * f6) + (i3 * f2);
        float f8 = f6 * f3;
        float f9 = (f7 - f8) + f4;
        float f10 = i3 * f;
        float f11 = (f9 - f10) + f8 + (f5 * f10);
        Log.d("MiuiRhombusClock", "mHour1Height: " + f3 + " hour1Top " + f10 + " timeTranslateY " + f11 + " hour1AodY " + f9);
        return f11;
    }

    public final void clearBlur() {
        MiuiBlurUtils.clearContainerPassBlur(this);
        MiuiBlurUtils.clearMemberBlendColor(this.mHour1View);
        MiuiBlurUtils.clearMemberBlendColor(this.mHour2View);
        MiuiBlurUtils.clearMemberBlendColor(this.mMinute1View);
        MiuiBlurUtils.clearMemberBlendColor(this.mMinute2View);
        MiuiBlurUtils.clearMemberBlendColor(this.mColonView);
        MiuiBlurUtils.clearMemberBlendColor(this.mNotificationDateText);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        if (this.mInfo == null) {
            return;
        }
        if (ClockEffectUtils.isLessThanOS2()) {
            if (this.mInfo.isAutoPrimaryColor() && DeviceConfig.supportBackgroundBlur(this.mContext) && !DeviceConfig.isHighTextContrastEnabled(this.mContext)) {
                return;
            }
            clearBlur();
            return;
        }
        ClockEffectUtils.clearClockEffectsContainer(this, this.mInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mHour1View, this.mInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mHour2View, this.mInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mMinute1View, this.mInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mMinute2View, this.mInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mColonView, this.mInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mNotificationDateText, this.mInfo, z);
        ClockEffectUtils.clearClockGradualEffect(this.mTimeContainer, this.mInfo, z);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ Rect getDepthAvoidRect() {
        return super.getDepthAvoidRect();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public float[] getGradientAnimationParams() {
        if (this.mInfo == null) {
            return Effect.EMPTY_GRADIENT;
        }
        int i = this.mScreenWidth;
        int i2 = this.mScreenHeight;
        float clockSizeScale = getClockSizeScale();
        float dimen = (int) (getDimen(R.dimen.miui_rhombus_clock_width) * clockSizeScale);
        float f = (i - dimen) / 2.0f;
        float f2 = ((dimen * 0.19999999f) + f) - f;
        float fCalculateScaleGradientTranslateY = calculateScaleGradientTranslateY(clockSizeScale, 0.6f, i, i2);
        float[] aodGradientParams = this.mInfo.getAodGradientParams();
        Log.d("MiuiRhombusClock", " timeTranslateY " + fCalculateScaleGradientTranslateY + " timeTranslateX " + f2);
        return ClockEffectUtils.scaleGradientParams(aodGradientParams, clockSizeScale * 0.6f * getScaleByGradientDesign(), f2, fCalculateScaleGradientTranslateY);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        switch (AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()]) {
            case 1:
                return this.mColon1View;
            case 2:
                return this.mColon2View;
            case 3:
                return this.mDateText;
            case 4:
                return this.mWeekText;
            case 5:
                return this.mHourContainer;
            case 6:
                return this.mMinuteContainer;
            case 7:
                return this.mTimeContainer;
            case 8:
                return this.mColonView;
            case 9:
                return this.mMagazineGroup;
            case 10:
                return this.mNotificationDateText;
            default:
                return super.getIClockView(clockViewType);
        }
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public int getMagazineColor() {
        MiuiRhombusClockBaseInfo miuiRhombusClockBaseInfo = this.mInfo;
        if (miuiRhombusClockBaseInfo != null) {
            return miuiRhombusClockBaseInfo.getInfoAreaColor();
        }
        return 0;
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ TextView getTimeView() {
        return super.getTimeView();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getTimeZone() {
        return super.getTimeZone();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void ignoreAccessibilityScale(boolean z) {
        this.supportAccessibilityScale = !z;
    }

    public final boolean isSvgAnimation() {
        return getStyle() != null && this.mHour1View.isUserDefinedResources() && DateFormatUtils.isSystemUI(getContext()) && this.mHour1View.isSvgAnimation();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mDateText = (TextView) findViewById(R.id.current_date);
        this.mWeekText = (TextView) findViewById(R.id.current_week);
        this.mMagazineGroup = (ViewGroup) findViewById(R.id.magazine_info_container);
        this.mTimeContainer = (ViewGroup) findViewById(R.id.time_container);
        this.mAccessibilityContainer = (ViewGroup) findViewById(R.id.accessibility_container);
        this.mDateContainer = (ViewGroup) findViewById(R.id.date_container);
        this.mColon1View = findViewById(R.id.colon1);
        this.mColon2View = findViewById(R.id.colon2);
        this.mHourContainer = findViewById(R.id.hour_container);
        this.mMinuteContainer = findViewById(R.id.minute_container);
        this.mColonView = (ImageView) findViewById(R.id.colon_view);
        this.mNotificationDateText = (TextView) findViewById(R.id.notification_date);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public void onHighTextContrastEnabledChanged(boolean z) {
        if (!z) {
            setClockPalette(this.mChoosePaletteType, this.mTextDark, this.mPalette, this.mWallpaperSupportDepth);
            return;
        }
        clearColorEffect();
        MiuiRhombusClockBaseInfo miuiRhombusClockBaseInfo = this.mInfo;
        if (miuiRhombusClockBaseInfo != null) {
            miuiRhombusClockBaseInfo.setLightMinuteColor(0);
            this.mInfo.setDarkMinuteColor(0);
            updateColor();
            updateTimeColor();
        }
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase
    public void onLanguageChanged(String str) {
        updateMagazineLayoutAndTime();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void resetClock() {
        MiuiRhombusClockBaseInfo miuiRhombusClockBaseInfo = this.mInfo;
        if (miuiRhombusClockBaseInfo == null) {
            Log.d("MiuiRhombusClock", "resetClock mInfo is null");
            return;
        }
        ClockEffectUtils.clearClockEffectsView(this.mColon1View, miuiRhombusClockBaseInfo, true);
        ClockEffectUtils.clearClockEffectsView(this.mColon2View, this.mInfo, true);
        ClockEffectUtils.clearClockEffectsView(this.mColonView, this.mInfo, true);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        super.setClockPalette(i, z, map, z2);
        if (this.mInfo == null || DeviceConfig.isHighTextContrastEnabled(this.mContext)) {
            return;
        }
        updateColor();
        updateViewBackgroundBlurContainer(this, getDimen(R.dimen.miui_rhombus_clock_blur_radius), this.mHour1View, this.mHour2View, this.mMinute1View, this.mMinute2View, this.mColonView, this.mNotificationDateText);
        boolean zIsFullAODType = ClockStyleInfo.isFullAODType(this.mInfo.getDisplayType());
        if (ClockEffectUtils.isBlurMixType(this.mInfo.getClockEffect()) && DeviceConfig.supportBackgroundBlur(this.mContext)) {
            int fullAodBlendColor = zIsFullAODType ? this.mInfo.getFullAodBlendColor() : this.mInfo.getBlendColor();
            MiuiClockNumberView miuiClockNumberView = this.mHour1View;
            MiuiBlurUtils.setMemberBlendColors(miuiClockNumberView, z, fullAodBlendColor, miuiClockNumberView.getTextColor());
            MiuiClockNumberView miuiClockNumberView2 = this.mHour2View;
            MiuiBlurUtils.setMemberBlendColors(miuiClockNumberView2, z, fullAodBlendColor, miuiClockNumberView2.getTextColor());
            MiuiClockNumberView miuiClockNumberView3 = this.mMinute1View;
            MiuiBlurUtils.setMemberBlendColors(miuiClockNumberView3, z, fullAodBlendColor, miuiClockNumberView3.getTextColor());
            MiuiClockNumberView miuiClockNumberView4 = this.mMinute2View;
            MiuiBlurUtils.setMemberBlendColors(miuiClockNumberView4, z, fullAodBlendColor, miuiClockNumberView4.getTextColor());
            MiuiBlurUtils.setMemberBlendColors(this.mColonView, z, fullAodBlendColor, this.mHour1View.getTextColor());
            MiuiBlurUtils.setMemberBlendColors(this.mNotificationDateText, z, fullAodBlendColor, this.mInfo.getPrimaryColor());
        } else if (ClockEffectUtils.isGradualType(this.mInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            float[] currentGradientParams = getCurrentGradientParams();
            ViewGroup viewGroup = this.mTimeContainer;
            MiuiRhombusClockBaseInfo miuiRhombusClockBaseInfo = this.mInfo;
            ClockEffectUtils.setClockGradualEffect(currentGradientParams, viewGroup, miuiRhombusClockBaseInfo, false, ClockStyleInfo.isFullAODType(miuiRhombusClockBaseInfo.getDisplayType()));
        } else if (ClockEffectUtils.isDifferenceType(this.mInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            int[] hourAndMinuteBlendColor = this.mInfo.getHourAndMinuteBlendColor();
            int iTransformFullAodColor = hourAndMinuteBlendColor[0];
            int iTransformFullAodColor2 = hourAndMinuteBlendColor[1];
            if (zIsFullAODType) {
                iTransformFullAodColor = GlobalColorUtils.transformFullAodColor(iTransformFullAodColor);
                iTransformFullAodColor2 = GlobalColorUtils.transformFullAodColor(iTransformFullAodColor2);
            }
            MiuiBlurUtils.setMemberDiffEffectColor(this.mHour1View, iTransformFullAodColor);
            MiuiBlurUtils.setMemberDiffEffectColor(this.mHour2View, iTransformFullAodColor);
            MiuiBlurUtils.setMemberDiffEffectColor(this.mMinute1View, iTransformFullAodColor2);
            MiuiBlurUtils.setMemberDiffEffectColor(this.mMinute2View, iTransformFullAodColor2);
            MiuiBlurUtils.setMemberDiffEffectColor(this.mColonView, iTransformFullAodColor);
            MiuiBlurUtils.setMemberDiffEffectColor(this.mNotificationDateText, iTransformFullAodColor);
        }
        updateTimeColor();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        setClockStyle(this.mInfo.getClockStyle());
        clearColorEffect(false);
        updateColor();
        updateTime();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public void setInfoTextColorDark(boolean z) {
        if (this.mInfo == null) {
            return;
        }
        int color = !z ? Color.parseColor("#99ffffff") : Color.parseColor("#99000000");
        this.mInfo.setOriginInfoAreaColor(color);
        int infoAreaColor = this.mInfo.getInfoAreaColor();
        if (infoAreaColor == 0 || infoAreaColor == Color.parseColor("#99ffffff") || infoAreaColor == Color.parseColor("#99000000")) {
            this.mInfo.setInfoAreaColor(color);
            updateColor();
        }
    }

    public void setInfoTextPalette() {
        if (this.mInfo == null || DeviceConfig.isHighTextContrastEnabled(this.mContext) || !this.mInfo.isAutoPrimaryColor() || !DeviceConfig.supportBackgroundBlur(this.mContext)) {
            return;
        }
        int blendColor = this.mInfo.getBlendColor();
        MiuiBlurUtils.setMemberBlendColors(this.mDateText, this.mInfo.isInfoTextDark(), blendColor, this.mInfo.getPrimaryColor());
        MiuiBlurUtils.setMemberBlendColors(this.mWeekText, this.mInfo.isInfoTextDark(), blendColor, this.mInfo.getPrimaryColor());
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public void setMagazineInfoVisible(boolean z) {
        super.setMagazineInfoVisible(z);
        this.mMagazineInfoVisible = z;
        this.mMagazineGroup.setVisibility(z ? 0 : 8);
        updateMagazineLayoutAndTime();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public void setScaleRatio(float f) {
        super.setScaleRatio(f);
        this.mDeviceScale = f;
        updateTime();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setSuperSaveOpen(boolean z) {
        super.setSuperSaveOpen(z);
    }

    public void setUserDefineColor(boolean z) {
        this.mHasUserDefinedColor = z;
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        super.updateColor();
        MiuiRhombusClockBaseInfo miuiRhombusClockBaseInfo = this.mInfo;
        if (miuiRhombusClockBaseInfo == null || this.mHasUserDefinedColor) {
            return;
        }
        this.mDateText.setTextColor(miuiRhombusClockBaseInfo.getInfoAreaColor());
        this.mWeekText.setTextColor(this.mInfo.getInfoAreaColor());
        if (!isSvgAnimation()) {
            Drawable drawableInitSvgColor = SvgUtils.initSvgColor(ContextCompat.getDrawable(getContext(), this.style.getColonResource()), this.mInfo.getSecondaryColor());
            this.mColon1View.setBackground(drawableInitSvgColor);
            this.mColon2View.setBackground(drawableInitSvgColor);
        }
        int primaryColor = this.mInfo.getHourAndMinuteColor(this.mContext)[1];
        int primaryColor2 = this.mInfo.getHourAndMinuteColor(this.mContext)[0];
        if (primaryColor == 0) {
            primaryColor = this.mInfo.getPrimaryColor();
        }
        if (primaryColor2 == 0) {
            primaryColor2 = this.mInfo.getPrimaryColor();
        }
        if (this.style.getFontStyle() == BaseFontStyle.Style.Copperplate) {
            MiuiClockNumberView miuiClockNumberView = this.mHour1View;
            if (!this.mInfo.isDiffHourMinuteColor()) {
                primaryColor2 = this.mInfo.getPrimaryColor();
            }
            miuiClockNumberView.setTextColor(primaryColor2);
            MiuiClockNumberView miuiClockNumberView2 = this.mMinute1View;
            if (!this.mInfo.isDiffHourMinuteColor()) {
                primaryColor = this.mInfo.getPrimaryColor();
            }
            miuiClockNumberView2.setTextColor(primaryColor);
        } else {
            this.mHour1View.setTextColor(this.mInfo.isDiffHourMinuteColor() ? primaryColor2 : this.mInfo.getPrimaryColor());
            MiuiClockNumberView miuiClockNumberView3 = this.mHour2View;
            if (!this.mInfo.isDiffHourMinuteColor()) {
                primaryColor2 = this.mInfo.getPrimaryColor();
            }
            miuiClockNumberView3.setTextColor(primaryColor2);
            this.mMinute1View.setTextColor(this.mInfo.isDiffHourMinuteColor() ? primaryColor : this.mInfo.getPrimaryColor());
            MiuiClockNumberView miuiClockNumberView4 = this.mMinute2View;
            if (!this.mInfo.isDiffHourMinuteColor()) {
                primaryColor = this.mInfo.getPrimaryColor();
            }
            miuiClockNumberView4.setTextColor(primaryColor);
        }
        this.mNotificationDateText.setTextColor(this.mInfo.getPrimaryColor());
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public void updateGradientEffectColor(float[] fArr) {
        super.updateGradientEffectColor(fArr);
        MiuiRhombusClockBaseInfo miuiRhombusClockBaseInfo = this.mInfo;
        if (miuiRhombusClockBaseInfo != null && ClockEffectUtils.isGradualType(miuiRhombusClockBaseInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            MiuiBlurUtils.setGradientBlurMethod(this.mTimeContainer, fArr);
        }
    }

    public final void updateMagazineLayoutAndTime() {
        boolean z = this.mMagazineInfoVisible;
        int i = z ? R.string.miui_lock_screen_date : R.string.miui_rhombus_clock_date;
        int i2 = z ? R.string.miui_rhombus_clock_week : R.string.miui_rhombus_clock_week_full;
        Calendar calendar = this.mCalendar;
        Context context = this.mContext;
        String str = calendar.format(context, context.getString(i));
        Calendar calendar2 = this.mCalendar;
        Context context2 = this.mContext;
        String str2 = calendar2.format(context2, context2.getString(i2));
        boolean z2 = FontUtils.isFrLanguage() && !this.mMagazineInfoVisible;
        this.mDateText.setText(z2 ? str2 : str);
        TextView textView = this.mWeekText;
        if (!z2) {
            str = str2;
        }
        textView.setText(str);
        TextView textView2 = this.mNotificationDateText;
        Calendar calendar3 = this.mCalendar;
        Context context3 = this.mContext;
        textView2.setText(calendar3.format(context3, context3.getString(R.string.miui_lock_screen_date)));
        this.mNotificationDateText.setTextSize(0, getDimen(R.dimen.miui_rhombus_small_date_text_size));
        ((RelativeLayout.LayoutParams) this.mNotificationDateText.getLayoutParams()).topMargin = getDimen(R.dimen.miui_rhombus_small_clock_margin_top);
        this.mWeekText.setVisibility(this.mMagazineInfoVisible ? 8 : 0);
        TextView textView3 = this.mDateText;
        int i3 = R.dimen.miui_rhombus_date_text_size;
        textView3.setTextSize(0, getDimen(i3));
        this.mWeekText.setTextSize(0, getDimen(i3));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mDateContainer.getLayoutParams();
        int i4 = R.dimen.miui_rhombus_clock_date_text_margin_start;
        layoutParams.setMarginStart(getDimen(i4));
        layoutParams.setMarginEnd(getDimen(i4));
        layoutParams.topMargin = this.mInFaceUnlockStatus ? getDimen(R.dimen.miui_rhombus_clock_date_text_margin_top_with_face) : getDimen(R.dimen.miui_rhombus_clock_date_text_margin_top);
        this.mDateContainer.setLayoutParams(layoutParams);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        if (this.mInfo == null) {
            return;
        }
        updateMagazineLayoutAndTime();
        this.mAccessibilityContainer.setContentDescription(DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mTimeContainer.getLayoutParams();
        float clockSizeScale = getClockSizeScale();
        float f = this.mDeviceScale;
        if (f > 0.0f) {
            clockSizeScale *= f;
        }
        layoutParams.width = (int) (getDimen(R.dimen.miui_rhombus_clock_width) * clockSizeScale);
        layoutParams.height = (int) (getDimen(R.dimen.miui_rhombus_clock_height) * clockSizeScale);
        ViewGroup.LayoutParams layoutParams2 = this.mHourContainer.getLayoutParams();
        int i = R.dimen.miui_rhombus_clock_inner_height;
        layoutParams2.height = (int) (getDimen(i) * clockSizeScale);
        this.mMinuteContainer.getLayoutParams().height = (int) (getDimen(i) * clockSizeScale);
        this.mTimeContainer.setTranslationY(-((int) (this.mScreenHeight * RhombusClockStyle.getTranslationScaleY())));
        int[] iArr = this.mCurrentTimeArray;
        int i2 = iArr[0];
        int i3 = iArr[1];
        int i4 = iArr[2];
        int i5 = iArr[3];
        int[] iArr2 = this.style.getHourOffset()[i2][i3];
        int[] iArr3 = this.style.getMinuteOffset()[i4][i5];
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) this.mHour1View.getLayoutParams();
        FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) this.mHour2View.getLayoutParams();
        FrameLayout.LayoutParams layoutParams5 = (FrameLayout.LayoutParams) this.mMinute1View.getLayoutParams();
        FrameLayout.LayoutParams layoutParams6 = (FrameLayout.LayoutParams) this.mMinute2View.getLayoutParams();
        if (this.style.getFontStyle() == BaseFontStyle.Style.Copperplate) {
            this.mHour2View.setVisibility(8);
            this.mMinute2View.setVisibility(8);
            layoutParams3.width = -1;
            layoutParams3.height = -1;
            layoutParams3.topMargin = 0;
            layoutParams3.setMarginEnd(0);
            layoutParams5.width = -1;
            layoutParams5.height = -1;
            layoutParams5.topMargin = 0;
            layoutParams5.setMarginEnd(0);
            this.mHour1View.setLayoutParams(layoutParams3);
            this.mMinute1View.setLayoutParams(layoutParams5);
            if (this.hourCopperplate == null) {
                this.hourCopperplate = new FontWholeHourCopperplate();
            }
            if (this.minuteCopperplate == null) {
                this.minuteCopperplate = new FontWholeMinuteCopperplate();
            }
            this.mHour1View.setFontStyle(this.hourCopperplate).setIndex((i2 * 10) + i3).setScaleRadio(clockSizeScale).setAngel(0.0f).setCenterPoint(0, 0).setCenterOffset(0, 0).setDiffusionType(this.mInfo.isEnableDiffusion() ? DiffusionType.BOTH : DiffusionType.NONE).setDisplayType(this.mInfo.displayType).build();
            this.mMinute1View.setFontStyle(this.minuteCopperplate).setAngel(0.0f).setScaleRadio(clockSizeScale).setCenterPoint(0, 0).setCenterOffset(0, 0).setIndex((i4 * 10) + i5).setDiffusionType(this.mInfo.isEnableDiffusion() ? DiffusionType.BOTH : DiffusionType.NONE).setDisplayType(this.mInfo.displayType).build();
        } else if (this.style.enableRotate()) {
            this.mHour2View.setVisibility(0);
            this.mMinute2View.setVisibility(0);
            int i6 = this.style.getLargeClockWidth()[i2];
            int i7 = this.style.getLargeClockHeights()[i2];
            double radians = Math.toRadians(this.style.getMaxRotateDegree());
            double d = i6;
            double dCos = Math.cos(radians) * d;
            double d2 = i7;
            double dSin = dCos + (Math.sin(radians) * d2);
            double dCos2 = (d2 * Math.cos(radians)) + (d * Math.sin(radians));
            float f2 = this.mDensity;
            double d3 = clockSizeScale;
            layoutParams3.width = (int) (((double) f2) * dSin * d3);
            layoutParams3.height = (int) (((double) f2) * dCos2 * d3);
            layoutParams3.setMarginEnd((int) (iArr2[0] * f2 * clockSizeScale));
            layoutParams3.topMargin = (int) (iArr2[1] * this.mDensity * clockSizeScale);
            double d4 = this.style.getLargeClockWidth()[i3];
            double d5 = this.style.getLargeClockHeights()[i3];
            double dCos3 = (Math.cos(radians) * d4) + (Math.sin(radians) * d5);
            double dCos4 = (d5 * Math.cos(radians)) + (d4 * Math.sin(radians));
            float f3 = this.mDensity;
            layoutParams4.width = (int) (((double) f3) * dCos3 * d3);
            layoutParams4.height = (int) (((double) f3) * dCos4 * d3);
            layoutParams4.setMarginStart((int) (iArr2[2] * f3 * clockSizeScale));
            layoutParams4.topMargin = (int) (iArr2[3] * this.mDensity * clockSizeScale);
            int i8 = this.style.getLargeClockWidth()[i4];
            int i9 = this.style.getLargeClockHeights()[i4];
            double d6 = i8;
            double dCos5 = Math.cos(radians) * d6;
            double d7 = i9;
            double dSin2 = dCos5 + (Math.sin(radians) * d7);
            double dCos6 = (d7 * Math.cos(radians)) + (d6 * Math.sin(radians));
            float f4 = this.mDensity;
            layoutParams5.width = (int) (((double) f4) * dSin2 * d3);
            layoutParams5.height = (int) (((double) f4) * dCos6 * d3);
            layoutParams5.setMarginEnd((int) (iArr3[0] * f4 * clockSizeScale));
            layoutParams5.topMargin = (int) (iArr3[1] * this.mDensity * clockSizeScale);
            double d8 = this.style.getLargeClockWidth()[i5];
            double d9 = this.style.getLargeClockHeights()[i5];
            double dCos7 = (Math.cos(radians) * d8) + (Math.sin(radians) * d9);
            double dCos8 = (d9 * Math.cos(radians)) + (d8 * Math.sin(radians));
            float f5 = this.mDensity;
            layoutParams6.width = (int) (((double) f5) * dCos7 * d3);
            layoutParams6.height = (int) (((double) f5) * dCos8 * d3);
            layoutParams6.setMarginStart((int) (iArr3[2] * f5 * clockSizeScale));
            layoutParams6.topMargin = (int) (iArr3[3] * this.mDensity * clockSizeScale);
            this.mHour1View.setLayoutParams(layoutParams3);
            this.mHour2View.setLayoutParams(layoutParams4);
            this.mMinute1View.setLayoutParams(layoutParams5);
            this.mMinute2View.setLayoutParams(layoutParams6);
            float[] fArr = this.style.getHourRotateDegree()[i2][i3];
            float[] fArr2 = this.style.getMinuteRotateDegree()[i4][i5];
            MiuiClockNumberView centerOffset = this.mHour1View.setFontStyle(this.style).setIndex(i2).setCenterOffset(0, 0);
            float f6 = this.mDensity;
            centerOffset.setCenterPoint((int) (dSin * 0.5d * ((double) f6) * d3), (int) (dCos2 * 0.5d * ((double) f6) * d3)).setAngel(fArr[0]).setScaleRadio(clockSizeScale).setDiffusionType(this.mInfo.isEnableDiffusion() ? DiffusionType.LEFT : DiffusionType.NONE).setDisplayType(this.mInfo.displayType).build();
            MiuiClockNumberView centerOffset2 = this.mHour2View.setFontStyle(this.style).setIndex(i3).setCenterOffset(0, 0);
            float f7 = this.mDensity;
            centerOffset2.setCenterPoint((int) (dCos3 * 0.5d * ((double) f7) * d3), (int) (dCos4 * 0.5d * ((double) f7) * d3)).setAngel(fArr[1]).setScaleRadio(clockSizeScale).setDiffusionType(this.mInfo.isEnableDiffusion() ? DiffusionType.RIGHT : DiffusionType.NONE).setDisplayType(this.mInfo.displayType).build();
            MiuiClockNumberView centerOffset3 = this.mMinute1View.setFontStyle(this.style).setIndex(i4).setCenterOffset(0, 0);
            float f8 = this.mDensity;
            centerOffset3.setCenterPoint((int) (dSin2 * 0.5d * ((double) f8) * d3), (int) (dCos6 * 0.5d * ((double) f8) * d3)).setAngel(fArr2[0]).setScaleRadio(clockSizeScale).setDiffusionType(this.mInfo.isEnableDiffusion() ? DiffusionType.LEFT : DiffusionType.NONE).setDisplayType(this.mInfo.getDisplayType()).build();
            MiuiClockNumberView centerOffset4 = this.mMinute2View.setFontStyle(this.style).setIndex(i5).setCenterOffset(0, 0);
            float f9 = this.mDensity;
            centerOffset4.setCenterPoint((int) (dCos7 * 0.5d * ((double) f9) * d3), (int) (dCos8 * 0.5d * ((double) f9) * d3)).setAngel(fArr2[1]).setScaleRadio(clockSizeScale).setDiffusionType(this.mInfo.isEnableDiffusion() ? DiffusionType.RIGHT : DiffusionType.NONE).setDisplayType(this.mInfo.displayType).build();
        } else {
            this.mHour2View.setVisibility(0);
            this.mMinute2View.setVisibility(0);
            layoutParams3.width = -2;
            float largeClockHeight = this.style.getLargeClockHeight();
            float f10 = this.mDensity;
            layoutParams3.height = (int) (largeClockHeight * f10 * clockSizeScale);
            layoutParams3.topMargin = (int) (iArr2[1] * f10 * clockSizeScale);
            layoutParams3.setMarginEnd((int) (iArr2[0] * f10 * clockSizeScale));
            layoutParams4.width = -2;
            float largeClockHeight2 = this.style.getLargeClockHeight();
            float f11 = this.mDensity;
            layoutParams4.height = (int) (largeClockHeight2 * f11 * clockSizeScale);
            layoutParams4.setMarginStart((int) (iArr2[2] * f11 * clockSizeScale));
            layoutParams4.topMargin = (int) (iArr2[3] * this.mDensity * clockSizeScale);
            layoutParams5.width = -2;
            float largeClockHeight3 = this.style.getLargeClockHeight();
            float f12 = this.mDensity;
            layoutParams5.height = (int) (largeClockHeight3 * f12 * clockSizeScale);
            layoutParams5.setMarginEnd((int) (iArr3[0] * f12 * clockSizeScale));
            layoutParams5.topMargin = (int) (iArr3[1] * this.mDensity * clockSizeScale);
            layoutParams6.width = -2;
            float largeClockHeight4 = this.style.getLargeClockHeight();
            float f13 = this.mDensity;
            layoutParams6.height = (int) (largeClockHeight4 * f13 * clockSizeScale);
            layoutParams6.setMarginStart((int) (iArr3[2] * f13 * clockSizeScale));
            layoutParams6.topMargin = (int) (iArr3[3] * this.mDensity * clockSizeScale);
            this.mHour1View.setLayoutParams(layoutParams3);
            this.mHour2View.setLayoutParams(layoutParams4);
            this.mMinute1View.setLayoutParams(layoutParams5);
            this.mMinute2View.setLayoutParams(layoutParams6);
            this.mHour1View.setFontStyle(this.style).setCenterPoint(0, 0).setCenterOffset(0, 0).setIndex(i2).setAngel(0.0f).setScaleRadio(clockSizeScale).setDiffusionType(this.mInfo.isEnableDiffusion() ? DiffusionType.LEFT : DiffusionType.NONE).setDisplayType(this.mInfo.displayType).build();
            this.mHour2View.setFontStyle(this.style).setCenterPoint(0, 0).setCenterOffset(0, 0).setIndex(i3).setAngel(0.0f).setScaleRadio(clockSizeScale).setDiffusionType(this.mInfo.isEnableDiffusion() ? DiffusionType.RIGHT : DiffusionType.NONE).setDisplayType(this.mInfo.displayType).build();
            this.mMinute1View.setFontStyle(this.style).setCenterPoint(0, 0).setCenterOffset(0, 0).setIndex(i4).setAngel(0.0f).setScaleRadio(clockSizeScale).setDiffusionType(this.mInfo.isEnableDiffusion() ? DiffusionType.LEFT : DiffusionType.NONE).setDisplayType(this.mInfo.displayType).build();
            this.mMinute2View.setFontStyle(this.style).setCenterPoint(0, 0).setCenterOffset(0, 0).setIndex(i5).setAngel(0.0f).setScaleRadio(clockSizeScale).setDiffusionType(this.mInfo.isEnableDiffusion() ? DiffusionType.RIGHT : DiffusionType.NONE).setDisplayType(this.mInfo.displayType).build();
        }
        ConstraintLayout.LayoutParams layoutParams7 = (ConstraintLayout.LayoutParams) this.mColon1View.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams8 = (ConstraintLayout.LayoutParams) this.mColon2View.getLayoutParams();
        int colonSize = (int) (this.style.getColonSize() * this.mDensity * clockSizeScale);
        ((ViewGroup.MarginLayoutParams) layoutParams7).width = colonSize;
        ((ViewGroup.MarginLayoutParams) layoutParams7).height = colonSize;
        ((ViewGroup.MarginLayoutParams) layoutParams8).width = colonSize;
        ((ViewGroup.MarginLayoutParams) layoutParams8).height = colonSize;
        ((ViewGroup.MarginLayoutParams) layoutParams8).topMargin = (int) (this.style.getColonInterval() * this.mDensity * clockSizeScale);
    }

    public final void updateTimeColor() {
        if (this.style.getFontStyle() == BaseFontStyle.Style.Copperplate) {
            this.mHour1View.updateColor();
            this.mMinute1View.updateColor();
        } else {
            this.mHour1View.updateColor();
            this.mHour2View.updateColor();
            this.mMinute1View.updateColor();
            this.mMinute2View.updateColor();
        }
    }
}
