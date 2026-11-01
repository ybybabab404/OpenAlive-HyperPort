package com.miui.clock.padexclusive.c;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.miui.clock.MiuiGalleryBaseClock;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.Effect;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.GlobalColorUtils;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class PadExclusiveCBase extends MiuiGalleryBaseClock {
    public PadExclusiveCBaseInfo mClockInfo;
    public Typeface mDateFontType;
    public Boolean mHasFaceIcon;
    public String mHourStr;
    public String mMinuteStr;
    public Typeface mTimeFontType;

    public PadExclusiveCBase(Context context) {
        super(context);
        this.mHasFaceIcon = Boolean.FALSE;
    }

    public PadExclusiveCBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHasFaceIcon = Boolean.FALSE;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        PadExclusiveCBaseInfo padExclusiveCBaseInfo = this.mClockInfo;
        if (padExclusiveCBaseInfo == null) {
            return;
        }
        int clockEffect = padExclusiveCBaseInfo.getClockEffect();
        if (z || !ClockEffectUtils.isGradualType(clockEffect)) {
            resetGradientParams();
        }
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
    public float[] getGradientParams() {
        PadExclusiveCBaseInfo padExclusiveCBaseInfo = this.mClockInfo;
        return padExclusiveCBaseInfo == null ? Effect.EMPTY_GRADIENT : ClockEffectUtils.scaleGradientParams(padExclusiveCBaseInfo.getLockGradientParams(), getScaleByGradientDesign());
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
            return getDimen(this.mHasFaceIcon.booleanValue() ? R.dimen.miui_pad_c_notification_magazine_face_margin_top : R.dimen.miui_pad_c_notification_magazine_margin_top);
        }
        return getDimen(this.mHasFaceIcon.booleanValue() ? R.dimen.miui_pad_c_notification_face_margin_top : R.dimen.miui_pad_c_notification_margin_top);
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
        return (this.mSmallestScreenWidth / DeviceConfig.getDesignDp(this.mContext)) / 2.8579466f;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void init(Context context) {
        super.init(context);
        this.mTimeFontType = FontUtils.getInterMono(context);
        this.mDateFontType = FontUtils.getMiSans(450);
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void onOrientationChange(int i) {
        super.onOrientationChange(i);
        PadExclusiveCBaseInfo padExclusiveCBaseInfo = this.mClockInfo;
        if (padExclusiveCBaseInfo == null) {
            return;
        }
        padExclusiveCBaseInfo.updateGradientTypeByOrientation(i);
        if (Effect.needHiddenGradient(getCurrentGradientParams())) {
            return;
        }
        updateGradientEffectColor(getGradientParams());
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

    /* JADX WARN: Code duplicated, block: B:31:0x00ac  */
    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        int primaryColor;
        Integer numValueOf;
        Integer numValueOf2;
        super.setClockPalette(i, z, map, z2);
        if (this.mClockInfo == null || DeviceConfig.isHighTextContrastEnabled(this.mContext) || this.mClockInfo.isSuperSaveOpen()) {
            updateHighTextColors(this.mClockInfo);
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
            if (this.mClockInfo.isAutoPrimaryColor() && this.mClockInfo.isAutoSecondaryColor()) {
                if (map != null) {
                    numValueOf2 = map.get("primary93");
                    numValueOf = map.get("primary80");
                } else {
                    numValueOf2 = null;
                    numValueOf = null;
                }
            } else if (this.mClockInfo.isAutoPrimaryColor()) {
                int secondaryColor = this.mClockInfo.getSecondaryColor();
                numValueOf = Integer.valueOf(secondaryColor);
                numValueOf2 = Integer.valueOf(GlobalColorUtils.addColorTone(secondaryColor, 13.0d));
            } else if (this.mClockInfo.isAutoSecondaryColor()) {
                int primaryColor2 = this.mClockInfo.getPrimaryColor();
                Integer numValueOf3 = Integer.valueOf(primaryColor2);
                numValueOf = Integer.valueOf(GlobalColorUtils.addColorTone(primaryColor2, -13.0d));
                numValueOf2 = numValueOf3;
            } else {
                numValueOf2 = null;
                numValueOf = null;
            }
            if (numValueOf2 == null) {
                numValueOf2 = Integer.valueOf(this.mClockInfo.getPrimaryColor());
            }
            if (numValueOf == null) {
                numValueOf = Integer.valueOf(this.mClockInfo.getSecondaryColor());
            }
            this.mClockInfo.setPrimaryColor(numValueOf2.intValue());
            this.mClockInfo.setSecondaryColor(numValueOf.intValue());
            updateColor();
        } else if (ClockEffectUtils.isBlurMixType(this.mClockInfo.getClockEffect())) {
            int color = Color.parseColor("#FFFFFF");
            int color2 = Color.parseColor(z ? "#666666" : "#808080");
            if (z) {
                primaryColor = (map == null || map.get("neutral-variant30") == null) ? this.mClockInfo.getPrimaryColor() : map.get("neutral-variant30").intValue();
            } else {
                primaryColor = (map == null || map.get("primary95") == null) ? this.mClockInfo.getPrimaryColor() : map.get("primary95").intValue();
            }
            this.mClockInfo.setPrimaryColor(color);
            this.mClockInfo.setSecondaryColor(color2);
            PadExclusiveCBaseInfo padExclusiveCBaseInfo = this.mClockInfo;
            padExclusiveCBaseInfo.setBlendColor(padExclusiveCBaseInfo.isAutoPrimaryColor() ? primaryColor : this.mClockInfo.getBlendColor());
            PadExclusiveCBaseInfo padExclusiveCBaseInfo2 = this.mClockInfo;
            if (!padExclusiveCBaseInfo2.isAutoSecondaryColor()) {
                primaryColor = this.mClockInfo.getSecondaryBlendColor();
            }
            padExclusiveCBaseInfo2.setSecondaryBlendColor(primaryColor);
        } else if (ClockEffectUtils.isDifferenceType(this.mClockInfo.getClockEffect())) {
            if (this.mClockInfo.isAutoPrimaryColor() && this.mClockInfo.isAutoSecondaryColor()) {
                if (z) {
                    if (map != null && map.get("secondary15") != null) {
                        int iIntValue = map.get("secondary15").intValue();
                        this.mClockInfo.setPrimaryColor(iIntValue);
                        this.mClockInfo.setBlendColor(iIntValue);
                    }
                    if (map != null && map.get("secondary30") != null) {
                        int iIntValue2 = map.get("secondary30").intValue();
                        this.mClockInfo.setSecondaryColor(iIntValue2);
                        this.mClockInfo.setSecondaryBlendColor(iIntValue2);
                    }
                } else {
                    if (map != null && map.get("primary95") != null) {
                        int iIntValue3 = map.get("primary95").intValue();
                        this.mClockInfo.setPrimaryColor(iIntValue3);
                        this.mClockInfo.setBlendColor(iIntValue3);
                    }
                    if (map != null && map.get("primary85") != null) {
                        int iIntValue4 = map.get("primary85").intValue();
                        this.mClockInfo.setSecondaryColor(iIntValue4);
                        this.mClockInfo.setSecondaryBlendColor(iIntValue4);
                    }
                }
            } else if (this.mClockInfo.isAutoPrimaryColor()) {
                int secondaryBlendColor = this.mClockInfo.getSecondaryBlendColor();
                int iAddColorTone = z ? GlobalColorUtils.addColorTone(secondaryBlendColor, -15.0d) : GlobalColorUtils.addColorTone(secondaryBlendColor, 10.0d);
                this.mClockInfo.setPrimaryColor(iAddColorTone);
                this.mClockInfo.setBlendColor(iAddColorTone);
            } else if (this.mClockInfo.isAutoSecondaryColor()) {
                int blendColor = this.mClockInfo.getBlendColor();
                int iAddColorTone2 = z ? GlobalColorUtils.addColorTone(blendColor, 15.0d) : GlobalColorUtils.addColorTone(blendColor, -10.0d);
                this.mClockInfo.setSecondaryColor(iAddColorTone2);
                this.mClockInfo.setSecondaryBlendColor(iAddColorTone2);
            }
        } else if (ClockEffectUtils.isGlowType(this.mClockInfo.getClockEffect())) {
            DeviceConfig.supportAdvanceVisualEffect(this.mContext);
        }
        if (this.mClockInfo != null) {
            Log.d("PadExclusiveCBase", "setClockPalette complete : " + this.mClockInfo);
        }
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        PadExclusiveCBaseInfo padExclusiveCBaseInfo = (PadExclusiveCBaseInfo) clockStyleInfo;
        this.mClockInfo = padExclusiveCBaseInfo;
        padExclusiveCBaseInfo.updateGradientTypeByOrientation(this.mOrientation);
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

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        this.mHourStr = ClassicClockTimeUtils.getTimeString(ClassicClockTimeUtils.getHourInt(this.m24HourFormat, this.mCalendar), true);
        this.mMinuteStr = ClassicClockTimeUtils.getTimeString(ClassicClockTimeUtils.getMinInt(this.mCalendar), true);
    }
}
