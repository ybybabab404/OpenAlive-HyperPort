package com.miui.clock.oversize.a;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.FontNeumaticCompressedB;
import com.miui.clock.module.FontNeumaticCompressedBF;
import com.miui.clock.oversize.OversizeSvgView;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.MiuiBlurUtils;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class OversizeAHourClock extends OversizeABase {
    public TextView mBlurView;
    public LinearLayout mTimeView;

    /* JADX INFO: renamed from: com.miui.clock.oversize.a.OversizeAHourClock$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.FULL_HOUR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.HOUR1.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.HOUR2.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public OversizeAHourClock(Context context) {
        super(context);
    }

    public OversizeAHourClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void adaptFoldWeekView() {
        if (DeviceConfig.FOLD_DEVICE && !isCnLanguage()) {
            int adaptMargin = getAdaptMargin();
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mTimeView.getLayoutParams();
            if (layoutParams.getMarginStart() != adaptMargin) {
                layoutParams.setMarginStart(adaptMargin);
                this.mTimeView.setLayoutParams(layoutParams);
            }
        }
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        OversizeABaseInfo oversizeABaseInfo = this.mClockInfo;
        if (oversizeABaseInfo == null) {
            return;
        }
        ClockEffectUtils.clearClockEffectsContainer(this, oversizeABaseInfo, z);
        ClockEffectUtils.clearClockEffectsContainer(this.mBlurView, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mHour1View, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mHour2View, this.mClockInfo, z);
        ClockEffectUtils.clearClockGradualEffect(this.mTimeView, this.mClockInfo, z);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getBackgroundBlurContainer() {
        return (ClockEffectUtils.isBlurMixType(this.mClockInfo.getClockEffect()) || ClockEffectUtils.isDifferenceType(this.mClockInfo.getClockEffect())) ? this : this.mBlurView;
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i == 1) {
            return this.mTimeView;
        }
        if (i != 2) {
            return i != 3 ? super.getIClockView(clockViewType) : this.mHour2View;
        }
        return this.mHour1View;
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTimeView = (LinearLayout) findViewById(R.id.time_view);
        this.mBlurView = (TextView) findViewById(R.id.blur_view);
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiBaseClock2
    public void onLanguageChanged() {
        super.onLanguageChanged();
        adaptFoldWeekView();
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2, int i3, int i4) {
        super.updateClockBlendColor(i, i2, i3, i4);
        boolean zIsAODType = ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType());
        boolean zIsFullAODType = ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType());
        boolean zUpdateTextDark = updateTextDark(this.mTextDark);
        int textDarkAlpha = getTextDarkAlpha(this.mTextDark);
        updateClockEffectsContainer();
        OversizeSvgView oversizeSvgView = this.mHour1View;
        OversizeABaseInfo oversizeABaseInfo = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(oversizeSvgView, oversizeABaseInfo, !zIsFullAODType && zUpdateTextDark, i, oversizeABaseInfo.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        OversizeSvgView oversizeSvgView2 = this.mHour2View;
        OversizeABaseInfo oversizeABaseInfo2 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(oversizeSvgView2, oversizeABaseInfo2, !zIsFullAODType && zUpdateTextDark, i, oversizeABaseInfo2.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        ClockEffectUtils.setClockGradualEffect(getCurrentGradientParams(), this.mTimeView, this.mClockInfo, zIsAODType, zIsFullAODType, getScaleByGradientDesign());
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockEffectsContainer() {
        super.updateClockEffectsContainer();
        updateViewBackgroundBlurContainer(this, getDimen(R.dimen.miui_oversize_a_time_blur_radius), this.mHour1View, this.mHour2View);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        OversizeABaseInfo oversizeABaseInfo = this.mClockInfo;
        if (oversizeABaseInfo == null) {
            return;
        }
        if (ClockStyleInfo.isFullAODType(oversizeABaseInfo.getDisplayType())) {
            this.mHour1View.setTextColor(this.mClockInfo.getFullAodPrimaryColor());
            this.mHour2View.setTextColor(this.mClockInfo.getFullAodPrimaryColor());
        } else {
            this.mHour1View.setTextColor(this.mClockInfo.getPrimaryColor());
            this.mHour2View.setTextColor(this.mClockInfo.getPrimaryColor());
        }
        updateTime();
    }

    @Override // com.miui.clock.oversize.a.OversizeABase
    public void updateFontType() {
        OversizeABaseInfo oversizeABaseInfo = this.mClockInfo;
        if (oversizeABaseInfo == null) {
            return;
        }
        if (oversizeABaseInfo.getClockStyle() == 2) {
            this.fontStyle = new FontNeumaticCompressedB();
        } else {
            this.fontStyle = new FontNeumaticCompressedBF();
        }
        this.mHour1View.setFontStyle(this.fontStyle);
        this.mHour2View.setFontStyle(this.fontStyle);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateGradientEffectColor(float[] fArr) {
        super.updateGradientEffectColor(fArr);
        OversizeABaseInfo oversizeABaseInfo = this.mClockInfo;
        if (oversizeABaseInfo != null && ClockEffectUtils.isGradualType(oversizeABaseInfo.getClockEffect())) {
            MiuiBlurUtils.setGradientBlurMethod(this.mTimeView, fArr, getScaleByGradientDesign());
        }
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        if (this.mClockInfo == null) {
            return;
        }
        this.mHour1View.setIndex(this.mCurrentTimeArray[0]).build();
        this.mHour2View.setIndex(this.mCurrentTimeArray[1]).build();
        this.mTimeView.setContentDescription(DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
        adaptFoldWeekView();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        int dimen = getDimen(R.dimen.miui_oversize_a_time_w);
        int dimen2 = getDimen(R.dimen.miui_oversize_a_time_h);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mBlurView.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mTimeView.getLayoutParams();
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mHour1View.getLayoutParams();
        LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) this.mHour2View.getLayoutParams();
        if (DeviceConfig.FOLD_DEVICE && DeviceConfig.inFoldLargeScreen(this.mContext)) {
            int dimen3 = getResources().getConfiguration().orientation == 2 ? getDimen(R.dimen.miui_oversize_a_fold_left_landscape) : getDimen(R.dimen.miui_oversize_a_fold_left);
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = 0;
            layoutParams.topToTop = this.mTimeView.getId();
            layoutParams.bottomToBottom = this.mTimeView.getId();
            layoutParams.startToStart = 0;
            layoutParams.endToEnd = 0;
            layoutParams.setMarginStart(dimen3);
            layoutParams.setMarginEnd(dimen3);
            ((ViewGroup.MarginLayoutParams) layoutParams).height = 0;
            layoutParams2.startToStart = 0;
            layoutParams2.endToEnd = -1;
            layoutParams2.topToTop = 0;
            layoutParams2.bottomToBottom = 0;
            ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = 0;
            layoutParams2.setMarginStart(dimen3);
            int i = (int) (((double) dimen2) * 0.9d);
            layoutParams3.height = i;
            int i2 = (int) (((double) dimen) * 0.9d);
            layoutParams3.width = i2;
            layoutParams4.height = i;
            layoutParams4.width = i2;
        } else {
            int i3 = R.dimen.miui_oversize_a_hour_margin_top;
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(i3);
            layoutParams.topToTop = 0;
            layoutParams.bottomToBottom = -1;
            layoutParams.startToStart = this.mTimeView.getId();
            layoutParams.endToEnd = this.mTimeView.getId();
            layoutParams.setMarginEnd(0);
            layoutParams.setMarginStart(0);
            ((ViewGroup.MarginLayoutParams) layoutParams).height = (getDimen(R.dimen.miui_oversize_a_minute_margin_top) + dimen2) - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
            layoutParams2.startToStart = 0;
            layoutParams2.endToEnd = 0;
            layoutParams2.topToTop = 0;
            layoutParams2.bottomToBottom = -1;
            ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimen(i3);
            layoutParams2.setMarginStart(0);
            layoutParams3.height = dimen2;
            layoutParams3.width = dimen;
            layoutParams4.height = dimen2;
            layoutParams4.width = dimen;
        }
        this.mBlurView.setLayoutParams(layoutParams);
        this.mHour1View.setLayoutParams(layoutParams3);
        this.mHour2View.setLayoutParams(layoutParams4);
        this.mTimeView.setLayoutParams(layoutParams2);
    }
}
