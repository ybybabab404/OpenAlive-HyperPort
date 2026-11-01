package com.miui.clock.oversize.b;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.Effect;
import com.miui.clock.oversize.OversizeSvgView;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.MiuiBlurUtils;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class OversizeBMinuteClock extends OversizeBBaseClock {
    public TextView mBackgroundBlurView;
    public Space mSpace;
    public LinearLayout mTimeMinute;

    public OversizeBMinuteClock(Context context) {
        super(context);
    }

    public OversizeBMinuteClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public OversizeBMinuteClock(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        OversizeBBaseInfo oversizeBBaseInfo = this.mClockInfo;
        if (oversizeBBaseInfo == null) {
            return;
        }
        ClockEffectUtils.clearClockEffectsContainer(this.mBackgroundBlurView, oversizeBBaseInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mMinute1View, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mMinute2View, this.mClockInfo, z);
        ClockEffectUtils.clearClockGradualEffect(this.mTimeMinute, this.mClockInfo, z);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getBackgroundBlurContainer() {
        return this.mBackgroundBlurView;
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public float[] getGradientAnimationParams() {
        if (this.mClockInfo == null) {
            return Effect.EMPTY_GRADIENT;
        }
        int i = R.dimen.miui_oversize_b_time_w;
        getDimen(i);
        getDimen(R.dimen.oversize_b_time_margin_start);
        getDimen(i);
        if (DeviceConfig.FOLD_DEVICE && DeviceConfig.inFoldLargeScreen(getContext())) {
            getDimen(R.dimen.oversize_b_aod_margin_start);
        }
        if (!this.mInFaceUnlockStatus || DeviceConfig.inFoldLargeScreen(this.mContext)) {
            getDimen(R.dimen.oversize_b_time_margin_top);
        } else {
            getDimen(R.dimen.oversize_b_time_face_margin_top);
        }
        getDimen(R.dimen.oversize_b_aod_margin_top);
        return ClockEffectUtils.scaleGradientParams(this.mClockInfo.getAodGradientParams(), getScaleByGradientDesign(), 0.0f, 0.0f);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        if (clockViewType == ClockViewType.FULL_MINUTE) {
            return this.mTimeMinute;
        }
        if (clockViewType == ClockViewType.MIN1) {
            return this.mMinute1View;
        }
        return clockViewType == ClockViewType.MIN2 ? this.mMinute2View : super.getIClockView(clockViewType);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiBaseClock2
    public View getNotificationClockTopView() {
        return this.mTimeMinute;
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mSpace = (Space) findViewById(R.id.space);
        this.mBackgroundBlurView = (TextView) findViewById(R.id.blur_view);
        this.mTimeMinute = (LinearLayout) findViewById(R.id.oversize_b_minute);
        updateViewsLayoutParams();
        setClipChildren(false);
        setClipToPadding(false);
        setClipToOutline(false);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        if (clockStyleInfo == null) {
            return;
        }
        updateColor();
        updateTimeFontType();
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2, int i3, int i4) {
        boolean zIsAODType = ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType());
        boolean zIsFullAODType = ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType());
        boolean zUpdateTextDark = updateTextDark(this.mTextDark);
        int textDarkAlpha = getTextDarkAlpha(this.mTextDark);
        updateClockEffectsContainer();
        OversizeSvgView oversizeSvgView = this.mMinute1View;
        OversizeBBaseInfo oversizeBBaseInfo = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(oversizeSvgView, oversizeBBaseInfo, !zIsFullAODType && zUpdateTextDark, i2, oversizeBBaseInfo.getSecondaryColor(), i4, zIsAODType, zIsFullAODType, textDarkAlpha);
        OversizeSvgView oversizeSvgView2 = this.mMinute2View;
        OversizeBBaseInfo oversizeBBaseInfo2 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(oversizeSvgView2, oversizeBBaseInfo2, !zIsFullAODType && zUpdateTextDark, i2, oversizeBBaseInfo2.getSecondaryColor(), i4, zIsAODType, zIsFullAODType, textDarkAlpha);
        ClockEffectUtils.setClockGradualEffect(getCurrentGradientParams(), this.mTimeMinute, this.mClockInfo, zIsAODType, zIsFullAODType);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockEffectsContainer() {
        super.updateClockEffectsContainer();
        updateViewBackgroundBlurContainer(this.mBackgroundBlurView, getDimen(R.dimen.oversize_b_time_blur_radius), this.mMinute1View, this.mMinute2View);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        super.updateColor();
        updateSecondaryColor(this.mClockInfo.getSecondaryColor());
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateGradientEffectColor(float[] fArr) {
        super.updateGradientEffectColor(fArr);
        OversizeBBaseInfo oversizeBBaseInfo = this.mClockInfo;
        if (oversizeBBaseInfo != null && ClockEffectUtils.isGradualType(oversizeBBaseInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            MiuiBlurUtils.setGradientBlurMethod(this.mTimeMinute, fArr);
        }
    }

    public void updateSecondaryColor(int i) {
        this.mMinute1View.updateColor(i);
        this.mMinute2View.updateColor(i);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        this.mMinute1View.setIndex(this.mCurrentTimeArray[2]).build();
        this.mMinute2View.setIndex(this.mCurrentTimeArray[3]).build();
        this.mTimeMinute.setContentDescription(DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock
    public void updateTimeFontType() {
        super.updateTimeFontType();
        this.mMinute1View.setFontStyle(this.mTimeFontStyle);
        this.mMinute2View.setFontStyle(this.mTimeFontStyle);
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mSpace.getLayoutParams();
        layoutParams.setMarginStart(getDimen(R.dimen.miui_oversize_b_space_margin_start));
        this.mSpace.setLayoutParams(layoutParams);
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mTimeMinute.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = (!this.mInFaceUnlockStatus || DeviceConfig.inFoldLargeScreen(this.mContext)) ? getDimen(R.dimen.oversize_b_time_margin_top) : getDimen(R.dimen.oversize_b_time_face_margin_top);
        ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin = getDimen(R.dimen.oversize_b_time_margin_start);
        this.mTimeMinute.setLayoutParams(layoutParams2);
        int dimen = getDimen(R.dimen.miui_oversize_b_time_w);
        int dimen2 = getDimen(R.dimen.miui_oversize_b_time_h);
        this.mMinute1View.setLayoutParams(new LinearLayout.LayoutParams(dimen, dimen2));
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(dimen, dimen2);
        layoutParams3.setMarginStart(getDimen(R.dimen.miui_oversize_b_time_overlapping));
        this.mMinute2View.setLayoutParams(layoutParams3);
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.mBackgroundBlurView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams4).height = getDimen(R.dimen.oversize_b_clock_blur_height);
        this.mBackgroundBlurView.setLayoutParams(layoutParams4);
        updateTimeFontType();
    }
}
