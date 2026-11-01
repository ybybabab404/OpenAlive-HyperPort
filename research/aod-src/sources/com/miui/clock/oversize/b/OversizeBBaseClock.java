package com.miui.clock.oversize.b;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.CallSuper;
import com.miui.clock.MiuiGalleryBaseClock;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.BaseFontStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.Effect;
import com.miui.clock.module.FontHumaneMono;
import com.miui.clock.module.FontHumaneRegularMono;
import com.miui.clock.oversize.OversizeSvgView;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.ColorUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.GlobalColorUtils;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class OversizeBBaseClock extends MiuiGalleryBaseClock {
    public OversizeBBaseInfo mClockInfo;
    public int[] mCurrentTimeArray;
    public OversizeSvgView mHour1View;
    public OversizeSvgView mHour2View;
    public String mHourStr;
    public boolean mInFaceUnlockStatus;
    public OversizeSvgView mMinute1View;
    public OversizeSvgView mMinute2View;
    public String mMinuteStr;
    public BaseFontStyle mTimeFontStyle;

    public OversizeBBaseClock(Context context) {
        super(context);
        this.mCurrentTimeArray = new int[4];
        this.mTimeFontStyle = new FontHumaneRegularMono();
    }

    public OversizeBBaseClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurrentTimeArray = new int[4];
        this.mTimeFontStyle = new FontHumaneRegularMono();
    }

    public OversizeBBaseClock(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCurrentTimeArray = new int[4];
        this.mTimeFontStyle = new FontHumaneRegularMono();
    }

    public void clearColorEffect(boolean z) {
        OversizeBBaseInfo oversizeBBaseInfo = this.mClockInfo;
        if (oversizeBBaseInfo == null) {
            return;
        }
        int clockEffect = oversizeBBaseInfo.getClockEffect();
        if (z || !ClockEffectUtils.isGradualType(clockEffect)) {
            resetGradientParams();
        }
    }

    public int getBatteryMarginStart() {
        if (DeviceConfig.inFoldLargeScreen(this.mContext)) {
            return !DeviceConfig.isRTL() ? getDimen(R.dimen.oversize_b_time_margin_start) : (getScreenWidth() - (getDimen(R.dimen.miui_oversize_b_time_w) * 2)) - getDimen(R.dimen.oversize_b_time_margin_start);
        }
        return !DeviceConfig.isRTL() ? getDimen(R.dimen.oversize_b_date_margin_start) : getScreenWidth() / 2;
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public ClockStyleInfo getClockStyleInfo() {
        return this.mClockInfo;
    }

    public BaseFontStyle getFontStyle() {
        return this.mTimeFontStyle;
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
        OversizeBBaseInfo oversizeBBaseInfo = this.mClockInfo;
        if (oversizeBBaseInfo == null) {
            return Effect.EMPTY_GRADIENT;
        }
        return ClockEffectUtils.scaleGradientParams(ClockStyleInfo.isAODType(oversizeBBaseInfo.getDisplayType()) ? this.mClockInfo.getAodGradientParams() : this.mClockInfo.getLockGradientParams(), getScaleByGradientDesign());
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
    public int getMagazineColor() {
        OversizeBBaseInfo oversizeBBaseInfo = this.mClockInfo;
        if (oversizeBBaseInfo != null) {
            return ColorUtils.blendColor(oversizeBBaseInfo.getOriginMagazineColor(), 0.6f);
        }
        return -1;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockBottom() {
        if (this.mMagazineInfoVisible) {
            return this.mInFaceUnlockStatus ? getDimen(R.dimen.oversize_b_notify_top_with_magazine_face) : getDimen(R.dimen.oversize_b_notify_top_with_magazine);
        }
        return this.mInFaceUnlockStatus ? getDimen(R.dimen.oversize_b_notify_top_with_face) : getDimen(R.dimen.oversize_b_notify_top);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockTop() {
        return getDimen(this.mInFaceUnlockStatus ? R.dimen.oversize_b_time_face_margin_top : R.dimen.oversize_b_time_margin_top);
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public View getNotificationClockTopView() {
        return this.mHour1View;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    public int getScreenWidth() {
        return (DeviceConfig.inFoldLargeScreen(this.mContext) || getResources().getConfiguration().orientation != 2) ? DeviceConfig.getScreenRect(this.mContext).width() : DeviceConfig.getScreenRect(this.mContext).height();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    public boolean isFoldLandscape() {
        return DeviceConfig.FOLD_DEVICE && DeviceConfig.inFoldLargeScreen(this.mContext) && getResources().getConfiguration().orientation == 2;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mHour1View = (OversizeSvgView) findViewById(R.id.hour1);
        this.mHour2View = (OversizeSvgView) findViewById(R.id.hour2);
        this.mMinute1View = (OversizeSvgView) findViewById(R.id.minute1);
        this.mMinute2View = (OversizeSvgView) findViewById(R.id.minute2);
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

    /* JADX WARN: Code duplicated, block: B:48:0x0130  */
    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        String str;
        int iIntValue;
        Integer numValueOf;
        super.setClockPalette(i, z, map, z2);
        if (map != null) {
            str = "neutral-variant20" + map.get("neutral-variant20") + ",neutral-variant60=" + map.get("neutral-variant60") + ",primary85=" + map.get("primary85") + ",primary97=" + map.get("primary97");
        } else {
            str = "null";
        }
        Log.d("ClockPalette", "setClockPalette: type = " + i + ", textDark = " + z + ", palette = " + str);
        updateHighTextColors(this.mClockInfo);
        if (this.mClockInfo == null || DeviceConfig.isHighTextContrastEnabled(this.mContext) || this.mClockInfo.isSuperSaveOpen()) {
            return;
        }
        int clockEffect = this.mClockInfo.getClockEffect();
        Integer numValueOf2 = null;
        if (ClockEffectUtils.needPickSolidColor(clockEffect, DeviceConfig.supportBackgroundBlur(this.mContext))) {
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
                    numValueOf2 = z ? map.get("neutral-variant20") : map.get("primary97");
                    numValueOf = z ? map.get("neutral-variant60") : map.get("primary85");
                } else {
                    numValueOf = null;
                }
            } else if (this.mClockInfo.isAutoPrimaryColor()) {
                int secondaryColor = this.mClockInfo.getSecondaryColor();
                Integer numValueOf3 = Integer.valueOf(secondaryColor);
                numValueOf2 = Integer.valueOf(GlobalColorUtils.addColorTone(secondaryColor, z ? -40.0d : 12.0d));
                numValueOf = numValueOf3;
            } else if (this.mClockInfo.isAutoSecondaryColor()) {
                int primaryColor = this.mClockInfo.getPrimaryColor();
                numValueOf2 = Integer.valueOf(primaryColor);
                numValueOf = Integer.valueOf(GlobalColorUtils.addColorTone(primaryColor, z ? 40.0d : -12.0d));
            } else {
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
            this.mClockInfo.setOriginMagazineColor(numValueOf2.intValue());
            this.mClockInfo.setAodPrimaryColor(GlobalColorUtils.transformAodColor(numValueOf2.intValue()));
            this.mClockInfo.setAodSecondaryColor(GlobalColorUtils.transformAodColor(numValueOf.intValue()));
            this.mClockInfo.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(numValueOf2.intValue()));
            this.mClockInfo.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(numValueOf.intValue()));
            updateColor();
            if (ClockEffectUtils.isGradualType(this.mClockInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                updateClockBlendColor();
                return;
            }
            return;
        }
        if (ClockEffectUtils.isBlurMixType(clockEffect)) {
            int color = z ? Color.parseColor("#FFFFFF") : Color.parseColor("#E6E6E6");
            int color2 = z ? Color.parseColor("#666666") : Color.parseColor("#4C4C4C");
            this.mClockInfo.setPrimaryColor(color);
            this.mClockInfo.setSecondaryColor(color2);
            int color3 = Color.parseColor("#E6E6E6");
            int color4 = Color.parseColor("#4C4C4C");
            int color5 = Color.parseColor("#A1A1A1");
            this.mClockInfo.setFullAodPrimaryColor(color3);
            this.mClockInfo.setFullAodSecondaryColor(color4);
            if (this.mClockInfo.isAutoPrimaryColor() && this.mClockInfo.isAutoSecondaryColor()) {
                if (z) {
                    iIntValue = map != null ? map.get("neutral-variant20").intValue() : -1;
                } else {
                    iIntValue = Color.parseColor("#A1A1A1");
                }
                this.mClockInfo.setBlendColor(iIntValue);
                this.mClockInfo.setSecondaryBlendColor(iIntValue);
                if (map != null && map.get("primary95") != null) {
                    color = map.get("primary95").intValue();
                }
                this.mClockInfo.setAodPrimaryColor(color);
                this.mClockInfo.setAodBlendColor(color);
                this.mClockInfo.setFullAodBlendColor(color5);
                if (map != null && map.get("primary80") != null) {
                    color2 = map.get("primary80").intValue();
                }
                this.mClockInfo.setAodSecondaryColor(color2);
                this.mClockInfo.setAodSecondaryBlendColor(color2);
                this.mClockInfo.setFullAodSecondaryBlendColor(color5);
            } else if (this.mClockInfo.isAutoPrimaryColor()) {
                int secondaryBlendColor = this.mClockInfo.getSecondaryBlendColor();
                this.mClockInfo.setBlendColor(secondaryBlendColor);
                int iTransformAodColor = GlobalColorUtils.transformAodColor(secondaryBlendColor);
                int iTransformFullAodColor = GlobalColorUtils.transformFullAodColor(secondaryBlendColor);
                this.mClockInfo.setAodPrimaryColor(iTransformAodColor);
                this.mClockInfo.setAodBlendColor(iTransformAodColor);
                this.mClockInfo.setAodSecondaryColor(iTransformAodColor);
                this.mClockInfo.setAodSecondaryBlendColor(iTransformAodColor);
                this.mClockInfo.setFullAodBlendColor(iTransformFullAodColor);
                this.mClockInfo.setFullAodSecondaryBlendColor(iTransformFullAodColor);
            } else if (this.mClockInfo.isAutoSecondaryColor()) {
                int blendColor = this.mClockInfo.getBlendColor();
                this.mClockInfo.setSecondaryBlendColor(blendColor);
                int iTransformAodColor2 = GlobalColorUtils.transformAodColor(blendColor);
                int iTransformFullAodColor2 = GlobalColorUtils.transformFullAodColor(blendColor);
                this.mClockInfo.setAodPrimaryColor(iTransformAodColor2);
                this.mClockInfo.setAodBlendColor(iTransformAodColor2);
                this.mClockInfo.setAodSecondaryColor(iTransformAodColor2);
                this.mClockInfo.setAodSecondaryBlendColor(iTransformAodColor2);
                this.mClockInfo.setFullAodBlendColor(iTransformFullAodColor2);
                this.mClockInfo.setFullAodSecondaryBlendColor(iTransformFullAodColor2);
            } else {
                int iTransformAodColor3 = GlobalColorUtils.transformAodColor(this.mClockInfo.getBlendColor());
                this.mClockInfo.setAodPrimaryColor(iTransformAodColor3);
                this.mClockInfo.setAodBlendColor(iTransformAodColor3);
                OversizeBBaseInfo oversizeBBaseInfo = this.mClockInfo;
                oversizeBBaseInfo.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(oversizeBBaseInfo.getBlendColor()));
                int iTransformAodColor4 = GlobalColorUtils.transformAodColor(this.mClockInfo.getSecondaryBlendColor());
                this.mClockInfo.setAodSecondaryColor(iTransformAodColor4);
                this.mClockInfo.setAodSecondaryBlendColor(iTransformAodColor4);
                OversizeBBaseInfo oversizeBBaseInfo2 = this.mClockInfo;
                oversizeBBaseInfo2.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(oversizeBBaseInfo2.getSecondaryBlendColor()));
            }
            if (this.mClockInfo.isAutoPrimaryColor() && map != null) {
                numValueOf2 = z ? map.get("neutral-variant20") : map.get("primary95");
            }
            OversizeBBaseInfo oversizeBBaseInfo3 = this.mClockInfo;
            oversizeBBaseInfo3.setOriginMagazineColor(numValueOf2 != null ? numValueOf2.intValue() : oversizeBBaseInfo3.getBlendColor());
            updateColor();
            updateClockBlendColor();
            return;
        }
        if (ClockEffectUtils.isDifferenceType(this.mClockInfo.getClockEffect())) {
            if (this.mClockInfo.isAutoPrimaryColor() && this.mClockInfo.isAutoSecondaryColor()) {
                if (z) {
                    if (map != null && map.get("neutral-variant15") != null) {
                        int iIntValue2 = map.get("neutral-variant15").intValue();
                        this.mClockInfo.setPrimaryColor(iIntValue2);
                        this.mClockInfo.setBlendColor(iIntValue2);
                        this.mClockInfo.setAodBlendColor(GlobalColorUtils.transformAodColor(iIntValue2));
                        this.mClockInfo.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(iIntValue2));
                        this.mClockInfo.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(iIntValue2));
                    }
                    if (map != null && map.get("neutral-variant40") != null) {
                        int iIntValue3 = map.get("neutral-variant40").intValue();
                        this.mClockInfo.setSecondaryColor(iIntValue3);
                        this.mClockInfo.setSecondaryBlendColor(iIntValue3);
                        this.mClockInfo.setAodSecondaryBlendColor(GlobalColorUtils.transformAodColor(iIntValue3));
                        this.mClockInfo.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(iIntValue3));
                        this.mClockInfo.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(iIntValue3));
                    }
                } else {
                    if (map != null && map.get("secondary97") != null) {
                        int iIntValue4 = map.get("secondary97").intValue();
                        this.mClockInfo.setPrimaryColor(iIntValue4);
                        this.mClockInfo.setBlendColor(iIntValue4);
                        this.mClockInfo.setAodBlendColor(GlobalColorUtils.transformAodColor(iIntValue4));
                        this.mClockInfo.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(iIntValue4));
                        this.mClockInfo.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(iIntValue4));
                    }
                    if (map != null && map.get("secondary85") != null) {
                        int iIntValue5 = map.get("secondary85").intValue();
                        this.mClockInfo.setSecondaryColor(iIntValue5);
                        this.mClockInfo.setSecondaryBlendColor(iIntValue5);
                        this.mClockInfo.setAodSecondaryBlendColor(GlobalColorUtils.transformAodColor(iIntValue5));
                        this.mClockInfo.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(iIntValue5));
                        this.mClockInfo.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(iIntValue5));
                    }
                }
            } else if (this.mClockInfo.isAutoPrimaryColor()) {
                int secondaryBlendColor2 = this.mClockInfo.getSecondaryBlendColor();
                int iAddColorTone = z ? GlobalColorUtils.addColorTone(secondaryBlendColor2, -25.0d) : GlobalColorUtils.addColorTone(secondaryBlendColor2, 12.0d);
                this.mClockInfo.setPrimaryColor(iAddColorTone);
                this.mClockInfo.setBlendColor(iAddColorTone);
                this.mClockInfo.setAodBlendColor(GlobalColorUtils.transformAodColor(iAddColorTone));
                this.mClockInfo.setAodSecondaryBlendColor(GlobalColorUtils.transformAodColor(secondaryBlendColor2));
                this.mClockInfo.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(iAddColorTone));
                this.mClockInfo.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(secondaryBlendColor2));
                this.mClockInfo.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(iAddColorTone));
                this.mClockInfo.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(secondaryBlendColor2));
            } else if (this.mClockInfo.isAutoSecondaryColor()) {
                int blendColor2 = this.mClockInfo.getBlendColor();
                int iAddColorTone2 = z ? GlobalColorUtils.addColorTone(blendColor2, 25.0d) : GlobalColorUtils.addColorTone(blendColor2, -12.0d);
                this.mClockInfo.setSecondaryColor(iAddColorTone2);
                this.mClockInfo.setSecondaryBlendColor(iAddColorTone2);
                this.mClockInfo.setAodBlendColor(GlobalColorUtils.transformAodColor(blendColor2));
                this.mClockInfo.setAodSecondaryBlendColor(GlobalColorUtils.transformAodColor(iAddColorTone2));
                this.mClockInfo.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(blendColor2));
                this.mClockInfo.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(iAddColorTone2));
                this.mClockInfo.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(blendColor2));
                this.mClockInfo.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(iAddColorTone2));
            } else {
                OversizeBBaseInfo oversizeBBaseInfo4 = this.mClockInfo;
                oversizeBBaseInfo4.setAodBlendColor(GlobalColorUtils.transformAodColor(oversizeBBaseInfo4.getBlendColor()));
                OversizeBBaseInfo oversizeBBaseInfo5 = this.mClockInfo;
                oversizeBBaseInfo5.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(oversizeBBaseInfo5.getBlendColor()));
                OversizeBBaseInfo oversizeBBaseInfo6 = this.mClockInfo;
                oversizeBBaseInfo6.setAodSecondaryBlendColor(GlobalColorUtils.transformAodColor(oversizeBBaseInfo6.getSecondaryBlendColor()));
                OversizeBBaseInfo oversizeBBaseInfo7 = this.mClockInfo;
                oversizeBBaseInfo7.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(oversizeBBaseInfo7.getSecondaryBlendColor()));
                OversizeBBaseInfo oversizeBBaseInfo8 = this.mClockInfo;
                oversizeBBaseInfo8.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(oversizeBBaseInfo8.getBlendColor()));
                OversizeBBaseInfo oversizeBBaseInfo9 = this.mClockInfo;
                oversizeBBaseInfo9.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(oversizeBBaseInfo9.getSecondaryBlendColor()));
            }
            updateColor();
            updateClockBlendColor();
        }
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @CallSuper
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        this.mClockInfo = (OversizeBBaseInfo) clockStyleInfo;
        clearColorEffect(false);
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
        String time = DateFormatUtils.formatTime(this.m24HourFormat ? "HHmm" : "hhmm");
        for (int i = 0; i < time.length(); i++) {
            this.mCurrentTimeArray[i] = Integer.parseInt(String.valueOf(time.charAt(i)));
        }
    }

    public void updateTimeFontType() {
        BaseFontStyle baseFontStyle = this.mTimeFontStyle;
        if (baseFontStyle == null || baseFontStyle.getUserDefinedResources() == null) {
            if (DeviceConfig.FOLD_DEVICE && DeviceConfig.inFoldLargeScreen(this.mContext)) {
                this.mTimeFontStyle = new FontHumaneMono();
            } else {
                this.mTimeFontStyle = new FontHumaneRegularMono();
            }
        }
    }
}
