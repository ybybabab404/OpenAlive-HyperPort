package com.miui.clock.padexclusive.a;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.miui.clock.MiuiGalleryBaseClock;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.Effect;
import com.miui.clock.padexclusive.a.clockinfo.PadExclusiveABaseInfo;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.GlobalColorUtils;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class PadExclusiveABase extends MiuiGalleryBaseClock {
    public PadExclusiveABaseInfo mClockInfo;

    public PadExclusiveABase(Context context) {
        super(context);
    }

    public PadExclusiveABase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        PadExclusiveABaseInfo padExclusiveABaseInfo = this.mClockInfo;
        if (padExclusiveABaseInfo == null) {
            return;
        }
        int clockEffect = padExclusiveABaseInfo.getClockEffect();
        if (z || !ClockEffectUtils.isGradualType(clockEffect)) {
            resetGradientParams();
        }
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public ClockStyleInfo getClockStyleInfo() {
        return this.mClockInfo;
    }

    public float getDimDp(int i) {
        return this.mContext.getResources().getDimensionPixelSize(i) / this.mContext.getResources().getDisplayMetrics().density;
    }

    public int getDimPXByPadDesignWidth(int i) {
        return (int) (((double) this.mContext.getResources().getDimensionPixelSize(i)) * 1.0d * ((double) DeviceConfig.calculateScaleByScreenWidth(this.mContext, (int) (((double) getDimDp(R.dimen.miui_pad_exclusive_a_full_width)) + 0.5d))));
    }

    public int getDimenPX(int i) {
        return getDimen(i);
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
        PadExclusiveABaseInfo padExclusiveABaseInfo = this.mClockInfo;
        return padExclusiveABaseInfo == null ? Effect.EMPTY_GRADIENT : ClockEffectUtils.scaleGradientParams(padExclusiveABaseInfo.getLockGradientParams(), getScaleByGradientDesign());
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
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
        if (this.mMagazineInfoVisible) {
            getDimenPX(R.dimen.miui_pad_exclusive_a_time_notification_magazine_margin_top);
        }
        return getDimenPX(R.dimen.miui_pad_exclusive_a_time_notification_margin_top);
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public View getNotificationClockTopView() {
        return null;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public float getScaleByGradientDesign() {
        float f = isOrientation90() ? 3048.0f : 2032.0f;
        float f2 = isOrientation90() ? 1137.0f : 711.0f;
        float f3 = this.mScreenWidth;
        Context context = this.mContext;
        return (f3 / DeviceConfig.getDesignDp(context, DeviceConfig.getScreenType(context))) / (f / f2);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    public int getTimeTextSize() {
        return getDimPXByPadDesignWidth(R.dimen.miui_pad_exclusive_a_time_size);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    public boolean isOrientation90() {
        return this.mOrientation == 2;
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void onOrientationChange(int i) {
        super.onOrientationChange(i);
        PadExclusiveABaseInfo padExclusiveABaseInfo = this.mClockInfo;
        if (padExclusiveABaseInfo == null) {
            return;
        }
        padExclusiveABaseInfo.updateGradientTypeByOrientation(i);
        if (Effect.needHiddenGradient(getCurrentGradientParams())) {
            return;
        }
        updateGradientEffectColor(this.mClockInfo.getLockGradientParams());
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
        int secondaryColor;
        int primaryColor;
        int secondaryColor2;
        int primaryColor2;
        int secondaryColor3;
        int secondaryColor4;
        int primaryColor3;
        super.setClockPalette(i, z, map, z2);
        if (this.mClockInfo == null || DeviceConfig.isHighTextContrastEnabled(this.mContext) || this.mClockInfo.isSuperSaveOpen()) {
            updateHighTextColors(this.mClockInfo);
            return;
        }
        if (this.mClockInfo != null) {
            boolean zSupportBackgroundBlur = DeviceConfig.supportBackgroundBlur(this.mContext);
            int clockEffect = this.mClockInfo.getClockEffect();
            if (ClockEffectUtils.needPickSolidColor(this.mClockInfo.getClockEffect(), zSupportBackgroundBlur)) {
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
                        primaryColor3 = (map == null || map.get("primary15") == null) ? this.mClockInfo.getPrimaryColor() : map.get("primary15").intValue();
                    } else {
                        primaryColor3 = (map == null || map.get("primary93") == null) ? this.mClockInfo.getPrimaryColor() : map.get("primary93").intValue();
                    }
                    this.mClockInfo.setPrimaryColor(primaryColor3);
                }
                if (this.mClockInfo.isAutoSecondaryColor()) {
                    if (z) {
                        secondaryColor4 = (map == null || map.get("primary50") == null) ? this.mClockInfo.getSecondaryColor() : map.get("primary50").intValue();
                    } else {
                        secondaryColor4 = (map == null || map.get("primary80") == null) ? this.mClockInfo.getSecondaryColor() : map.get("primary80").intValue();
                    }
                    this.mClockInfo.setSecondaryColor(secondaryColor4);
                }
                if (z) {
                    secondaryColor3 = (map == null || map.get("secondary15") == null) ? this.mClockInfo.getSecondaryColor() : map.get("secondary15").intValue();
                } else {
                    secondaryColor3 = (map == null || map.get("primary98") == null) ? this.mClockInfo.getSecondaryColor() : map.get("primary98").intValue();
                }
                this.mClockInfo.setPlainColor(secondaryColor3);
            } else if (ClockEffectUtils.isBlurMixType(this.mClockInfo.getClockEffect())) {
                if (this.mClockInfo.isAutoPrimaryColor()) {
                    if (z) {
                        primaryColor2 = (map == null || map.get("neutral-variant30") == null) ? this.mClockInfo.getPrimaryColor() : map.get("neutral-variant30").intValue();
                    } else {
                        primaryColor2 = Color.parseColor("#ABABAB");
                    }
                    this.mClockInfo.setBlendColor(primaryColor2);
                    this.mClockInfo.setPrimaryColor(-1);
                } else {
                    this.mClockInfo.setPrimaryColor(-1);
                    PadExclusiveABaseInfo padExclusiveABaseInfo = this.mClockInfo;
                    padExclusiveABaseInfo.setBlendColor(padExclusiveABaseInfo.getBlendColor());
                }
                if (this.mClockInfo.isAutoSecondaryColor()) {
                    if (z) {
                        secondaryColor2 = (map == null || map.get("primary50") == null) ? this.mClockInfo.getSecondaryColor() : map.get("primary50").intValue();
                    } else {
                        secondaryColor2 = (map == null || map.get("primary80") == null) ? this.mClockInfo.getSecondaryColor() : map.get("primary80").intValue();
                    }
                    this.mClockInfo.setSecondaryColor(secondaryColor2);
                }
                int color = Color.parseColor("#999999");
                if (z) {
                    primaryColor = (map == null || map.get("neutral-variant30") == null) ? this.mClockInfo.getPrimaryColor() : map.get("neutral-variant30").intValue();
                } else {
                    primaryColor = Color.parseColor("#ABABAB");
                }
                this.mClockInfo.setPlainBlendColor(primaryColor);
                this.mClockInfo.setPlainColor(color);
            } else if (ClockEffectUtils.isDifferenceType(this.mClockInfo.getClockEffect())) {
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
                if (this.mClockInfo.isAutoSecondaryColor()) {
                    if (z) {
                        secondaryColor = (map == null || map.get("primary50") == null) ? this.mClockInfo.getSecondaryColor() : map.get("primary50").intValue();
                    } else {
                        secondaryColor = (map == null || map.get("primary80") == null) ? this.mClockInfo.getSecondaryColor() : map.get("primary80").intValue();
                    }
                    this.mClockInfo.setSecondaryColor(secondaryColor);
                }
                if (z) {
                    if (map != null && map.get("secondary7") != null) {
                        int iIntValue3 = map.get("secondary7").intValue();
                        if (ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType())) {
                            this.mClockInfo.setPlainColor(GlobalColorUtils.transformAodColor(iIntValue3));
                        } else {
                            this.mClockInfo.setPlainColor(iIntValue3);
                        }
                        this.mClockInfo.setPlainBlendColor(iIntValue3);
                    }
                } else if (map != null && map.get("secondary90") != null) {
                    int iIntValue4 = map.get("secondary90").intValue();
                    if (ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType())) {
                        this.mClockInfo.setPlainColor(GlobalColorUtils.transformAodColor(iIntValue4));
                    } else {
                        this.mClockInfo.setPlainColor(iIntValue4);
                    }
                    this.mClockInfo.setPlainBlendColor(iIntValue4);
                }
            } else if (ClockEffectUtils.isGlowType(this.mClockInfo.getClockEffect())) {
                DeviceConfig.supportAdvanceVisualEffect(this.mContext);
            }
        }
        if (this.mClockInfo != null) {
            Log.d("PadExclusiveABase", "setClockPalette complete : " + this.mClockInfo);
        }
        updateColor();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        PadExclusiveABaseInfo padExclusiveABaseInfo = (PadExclusiveABaseInfo) clockStyleInfo;
        this.mClockInfo = padExclusiveABaseInfo;
        padExclusiveABaseInfo.updateGradientTypeByOrientation(this.mOrientation);
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

    public void setTextTypeFace(TextView textView, int i) {
        Typeface mono;
        if (i == 1) {
            mono = FontUtils.getMono();
        } else if (i != 2) {
            mono = i != 3 ? FontUtils.getMiSans(450) : FontUtils.getMiSans(450);
        } else {
            mono = FontUtils.getSilkSerifRegular();
        }
        textView.setTypeface(mono);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateHourFormat() {
        this.m24HourFormat = DateFormatUtils.is24HourFormat(this.mContext);
    }
}
