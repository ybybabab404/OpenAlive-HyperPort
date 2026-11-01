package com.miui.clock.rhombus;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
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
import com.miui.clock.module.Effect;
import com.miui.clock.module.FontSilkSerif;
import com.miui.clock.module.FontWholeHourCopperplate;
import com.miui.clock.module.FontWholeMinuteCopperplate;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.MiuiBlurUtils;
import com.miui.clock.utils.SvgUtils;
import java.lang.reflect.Method;
import java.util.Map;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class MiuiRhombusSingleClock extends MiuiRhombusBase {
    public FontWholeHourCopperplate hourCopperplate;
    public View mColon1View;
    public View mColon2View;
    public ImageView mColonView;
    public float mDeviceScale;
    public View mHourContainer;
    public MiuiRhombusSingleClockInfo mInfo;
    public View mMinuteContainer;
    public ViewGroup mTimeContainer;
    public FontWholeMinuteCopperplate minuteCopperplate;
    public boolean supportAccessibilityScale;

    /* JADX INFO: renamed from: com.miui.clock.rhombus.MiuiRhombusSingleClock$1, reason: invalid class name */
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
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_COLON.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_HOUR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_MINUTE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public MiuiRhombusSingleClock(Context context) {
        super(context);
        this.mDeviceScale = -1.0f;
        this.supportAccessibilityScale = true;
    }

    public MiuiRhombusSingleClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDeviceScale = -1.0f;
        this.supportAccessibilityScale = true;
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        if (ClockEffectUtils.isLessThanOS2()) {
            return;
        }
        ClockEffectUtils.clearClockEffectsContainer(this, this.mInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mHour1View, this.mInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mHour2View, this.mInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mMinute1View, this.mInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mMinute2View, this.mInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mColonView, this.mInfo, z);
        ClockEffectUtils.clearClockGradualEffect(this.mTimeContainer, this.mInfo, z);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public int getClockHeight() {
        return getDimen(R.dimen.miui_rhombus_clock_height);
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
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i == 1) {
            return this.mColon1View;
        }
        if (i == 2) {
            return this.mColon2View;
        }
        if (i == 3) {
            return this.mColonView;
        }
        if (i != 4) {
            return i != 5 ? super.getIClockView(clockViewType) : this.mMinuteContainer;
        }
        return this.mHourContainer;
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getMagazineColor() {
        return super.getMagazineColor();
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

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.style = new FontSilkSerif();
        this.mColon1View = findViewById(R.id.colon1);
        this.mColon2View = findViewById(R.id.colon2);
        this.mTimeContainer = (ViewGroup) findViewById(R.id.time_container);
        this.mColonView = (ImageView) findViewById(R.id.colon_view);
        this.mHourContainer = findViewById(R.id.hour_container);
        this.mMinuteContainer = findViewById(R.id.minute_container);
        updateTime();
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
        if (ClockEffectUtils.isGradualType(this.mInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            updateGradientEffectColor(null);
        } else if ((!ClockEffectUtils.isDifferenceType(this.mInfo.getClockEffect()) || !DeviceConfig.supportAdvanceVisualEffect(this.mContext)) && ClockEffectUtils.isGlowType(this.mInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            Effect.Glow glowParam = Effect.GlowEffect.getGlowParam(this.mInfo.getBlendColor());
            MiuiBlurUtils.setGlowEffectContainer(this);
            MiuiBlurUtils.setGlowEffectMethod(this.mHour1View, glowParam, true, false);
            MiuiBlurUtils.setGlowEffectMethod(this.mHour2View, glowParam, true, false);
            MiuiBlurUtils.setGlowEffectMethod(this.mMinute1View, glowParam, true, false);
            MiuiBlurUtils.setGlowEffectMethod(this.mMinute2View, glowParam, true, false);
            MiuiBlurUtils.setGlowEffectMethod(this.mColonView, glowParam, true, false);
        }
        updateTime();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        MiuiRhombusSingleClockInfo miuiRhombusSingleClockInfo = (MiuiRhombusSingleClockInfo) clockStyleInfo;
        this.mInfo = miuiRhombusSingleClockInfo;
        setClockStyle(miuiRhombusSingleClockInfo.getClockStyle());
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
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public void setMagazineInfoVisible(boolean z) {
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public void setScaleRatio(float f) {
        super.setScaleRatio(f);
        this.mDeviceScale = f;
        updateGradientEffectColor(null);
        updateTime();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setSuperSaveOpen(boolean z) {
        super.setSuperSaveOpen(z);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public void updateGradientEffectColor(float[] fArr) {
        super.updateGradientEffectColor(fArr);
        MiuiRhombusSingleClockInfo miuiRhombusSingleClockInfo = this.mInfo;
        if (miuiRhombusSingleClockInfo != null && ClockEffectUtils.isGradualType(miuiRhombusSingleClockInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            ClockEffectUtils.setClockGradualEffect(null, this.mTimeContainer, this.mInfo, true, false, getScaleByGradientDesign() * Math.abs(this.mDeviceScale * getClockSizeScale()));
        }
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        if (this.mInfo == null) {
            return;
        }
        this.mTimeContainer.setContentDescription(DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
        this.mInfo.calculateFinalAODColor(this.mContext);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mTimeContainer.getLayoutParams();
        float clockSizeScale = getClockSizeScale();
        float f = this.mDeviceScale;
        if (f > 0.0f) {
            clockSizeScale *= f;
        }
        Log.d("MiuiClock", "radio = " + clockSizeScale + ", mDeviceScale = " + this.mDeviceScale);
        layoutParams.width = (int) (((float) getDimen(R.dimen.miui_rhombus_clock_width)) * clockSizeScale);
        layoutParams.height = (int) (((float) getDimen(R.dimen.miui_rhombus_clock_height)) * clockSizeScale);
        ViewGroup.LayoutParams layoutParams2 = this.mHourContainer.getLayoutParams();
        int i = R.dimen.miui_rhombus_clock_inner_height;
        layoutParams2.height = (int) (((float) getDimen(i)) * clockSizeScale);
        this.mMinuteContainer.getLayoutParams().height = (int) (getDimen(i) * clockSizeScale);
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
            this.mHour1View.setFontStyle(this.hourCopperplate).setAngel(0.0f).setScaleRadio(clockSizeScale).setCenterPoint(0, 0).setCenterOffset(0, 0).setIndex((i2 * 10) + i3).setTextColor(this.mInfo.getAodHourColor()).setDisplayType(((MiuiRhombusClockBaseInfo) this.mInfo).displayType).build();
            this.mMinute1View.setFontStyle(this.minuteCopperplate).setAngel(0.0f).setScaleRadio(clockSizeScale).setCenterPoint(0, 0).setCenterOffset(0, 0).setIndex((i4 * 10) + i5).setTextColor(this.mInfo.getAodMinuteColor()).setDisplayType(((MiuiRhombusClockBaseInfo) this.mInfo).displayType).build();
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
            MiuiClockNumberView index = this.mHour1View.setFontStyle(this.style).setIndex(i2);
            float f6 = this.mDensity;
            index.setCenterPoint((int) (dSin * 0.5d * ((double) f6) * d3), (int) (dCos2 * 0.5d * ((double) f6) * d3)).setTextColor(this.mInfo.getAodHourColor()).setAngel(fArr[0]).setScaleRadio(clockSizeScale).setCenterOffset(0, 0).setDisplayType(((MiuiRhombusClockBaseInfo) this.mInfo).displayType).build();
            MiuiClockNumberView index2 = this.mHour2View.setFontStyle(this.style).setIndex(i3);
            float f7 = this.mDensity;
            index2.setCenterPoint((int) (dCos3 * 0.5d * ((double) f7) * d3), (int) (dCos4 * 0.5d * ((double) f7) * d3)).setTextColor(this.mInfo.getAodHourColor()).setAngel(fArr[1]).setScaleRadio(clockSizeScale).setCenterOffset(0, 0).setDisplayType(((MiuiRhombusClockBaseInfo) this.mInfo).displayType).build();
            MiuiClockNumberView index3 = this.mMinute1View.setFontStyle(this.style).setIndex(i4);
            float f8 = this.mDensity;
            index3.setCenterPoint((int) (dSin2 * 0.5d * ((double) f8) * d3), (int) (dCos6 * 0.5d * ((double) f8) * d3)).setTextColor(this.mInfo.getAodMinuteColor()).setAngel(fArr2[0]).setScaleRadio(clockSizeScale).setCenterOffset(0, 0).setDisplayType(((MiuiRhombusClockBaseInfo) this.mInfo).displayType).build();
            MiuiClockNumberView index4 = this.mMinute2View.setFontStyle(this.style).setIndex(i5);
            float f9 = this.mDensity;
            index4.setCenterPoint((int) (dCos7 * 0.5d * ((double) f9) * d3), (int) (dCos8 * 0.5d * ((double) f9) * d3)).setTextColor(this.mInfo.getAodMinuteColor()).setAngel(fArr2[1]).setScaleRadio(clockSizeScale).setCenterOffset(0, 0).setDisplayType(((MiuiRhombusClockBaseInfo) this.mInfo).displayType).build();
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
            this.mHour1View.setFontStyle(this.style).setAngel(0.0f).setScaleRadio(clockSizeScale).setCenterPoint(0, 0).setCenterOffset(0, 0).setIndex(i2).setTextColor(this.mInfo.getAodHourColor()).setDisplayType(((MiuiRhombusClockBaseInfo) this.mInfo).displayType).build();
            this.mHour2View.setFontStyle(this.style).setAngel(0.0f).setScaleRadio(clockSizeScale).setCenterPoint(0, 0).setCenterOffset(0, 0).setIndex(i3).setTextColor(this.mInfo.getAodHourColor()).setDisplayType(((MiuiRhombusClockBaseInfo) this.mInfo).displayType).build();
            this.mMinute1View.setFontStyle(this.style).setAngel(0.0f).setScaleRadio(clockSizeScale).setCenterPoint(0, 0).setCenterOffset(0, 0).setIndex(i4).setTextColor(this.mInfo.getAodMinuteColor()).setDisplayType(((MiuiRhombusClockBaseInfo) this.mInfo).displayType).build();
            this.mMinute2View.setFontStyle(this.style).setAngel(0.0f).setScaleRadio(clockSizeScale).setCenterPoint(0, 0).setCenterOffset(0, 0).setIndex(i5).setTextColor(this.mInfo.getAodMinuteColor()).setDisplayType(((MiuiRhombusClockBaseInfo) this.mInfo).displayType).build();
        }
        ConstraintLayout.LayoutParams layoutParams7 = (ConstraintLayout.LayoutParams) this.mColon1View.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams8 = (ConstraintLayout.LayoutParams) this.mColon2View.getLayoutParams();
        int colonSize = (int) (this.style.getColonSize() * this.mDensity * clockSizeScale);
        ((ViewGroup.MarginLayoutParams) layoutParams7).width = colonSize;
        ((ViewGroup.MarginLayoutParams) layoutParams7).height = colonSize;
        ((ViewGroup.MarginLayoutParams) layoutParams8).width = colonSize;
        ((ViewGroup.MarginLayoutParams) layoutParams8).height = colonSize;
        ((ViewGroup.MarginLayoutParams) layoutParams8).topMargin = (int) (this.style.getColonInterval() * this.mDensity * clockSizeScale);
        Drawable drawable = ContextCompat.getDrawable(getContext(), this.style.getColonResource());
        if (this.style.getFontStyle() != BaseFontStyle.Style.SKPupok || !(drawable instanceof AnimatedVectorDrawable)) {
            this.mColon1View.setBackground(SvgUtils.initSvgColor(drawable, this.mInfo.getAodColonColor()));
            this.mColon2View.setBackground(SvgUtils.initSvgColor(drawable, this.mInfo.getAodColonColor()));
            return;
        }
        try {
            VectorDrawable vectorDrawable = (VectorDrawable) AnimatedVectorDrawable.class.getMethod("getVectorDrawable", new Class[0]).invoke(drawable, new Object[0]);
            if (vectorDrawable != null) {
                Method declaredMethod = VectorDrawable.class.getDeclaredMethod("getTargetByName", String.class);
                declaredMethod.setAccessible(true);
                Object objInvoke = declaredMethod.invoke(vectorDrawable, "_R_G_L_0_G_D_1_P_0");
                if (objInvoke != null) {
                    Method declaredMethod2 = objInvoke.getClass().getDeclaredMethod("setStrokeColor", Integer.TYPE);
                    declaredMethod2.setAccessible(true);
                    declaredMethod2.invoke(objInvoke, Integer.valueOf(this.mInfo.getAodColonColor()));
                }
            }
        } catch (Exception e) {
            Log.e("MiuiClock", "AnimatedVectorDrawable reflect fail", e);
        }
        this.mColon1View.setBackground(drawable);
        this.mColon2View.setBackground(drawable);
    }
}
