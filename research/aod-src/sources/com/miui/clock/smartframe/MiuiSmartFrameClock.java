package com.miui.clock.smartframe;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.utils.BaseLineSpaceView;
import com.miui.clock.utils.ColorUtils;
import com.miui.clock.utils.DeviceConfig;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class MiuiSmartFrameClock extends MiuiSmartFrameBase {
    public View mClockContainer;
    public BaseLineSpaceView mHourSpace;
    public View mLineView;
    public BaseLineSpaceView mMinuteSpace;

    public MiuiSmartFrameClock(Context context) {
        super(context);
    }

    public MiuiSmartFrameClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        return clockViewType == ClockViewType.LINE ? this.mLineView : super.getIClockView(clockViewType);
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    public final void layoutWeekText(float f) {
        float dimen = (int) (getDimen(R.dimen.miui_smart_frame_clock_date_text_size) * f);
        this.mWeekText.setTextSize(0, dimen);
        this.mDateText.setTextSize(0, dimen);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mWeekText.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mDateText.getLayoutParams();
        int dimen2 = (int) (getDimen(R.dimen.miui_smart_frame_clock_date_margin_bottom) * f);
        int dimen3 = (int) (getDimen(R.dimen.miui_smart_frame_clock_week_margin_end) * f);
        ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = dimen2;
        layoutParams.setMarginEnd(dimen3);
        if (measureWeekSize(this.mDateText.getText().toString() + this.mWeekText.getText().toString())) {
            layoutParams2.endToStart = -1;
            layoutParams2.endToEnd = 0;
            layoutParams2.bottomToTop = R.id.current_week;
            ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin = (int) (getDimen(R.dimen.miui_smart_frame_clock_week_twoline_margin_top) * f);
            layoutParams2.setMarginEnd(dimen3);
        } else {
            layoutParams2.endToStart = R.id.current_week;
            layoutParams2.endToEnd = -1;
            layoutParams2.bottomToTop = R.id.view_line;
            ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin = dimen2;
            layoutParams2.setMarginEnd((int) (getDimen(R.dimen.miui_smart_frame_clock_date_margin_end) * f));
        }
        this.mWeekText.setLayoutParams(layoutParams);
        this.mDateText.setLayoutParams(layoutParams2);
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mLineView = findViewById(R.id.view_line);
        this.mClockContainer = findViewById(R.id.clock_container);
        this.mHourSpace = (BaseLineSpaceView) findViewById(R.id.hour_space);
        this.mMinuteSpace = (BaseLineSpaceView) findViewById(R.id.minute_space);
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        super.setClockPalette(i, z, map, z2);
        MiuiSmartFrameClockBaseInfo miuiSmartFrameClockBaseInfo = this.mClockInfo;
        if (miuiSmartFrameClockBaseInfo == null || !miuiSmartFrameClockBaseInfo.isAutoPrimaryColor()) {
            return;
        }
        updateTime();
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    public final void setTextColor() {
        this.mTimeHour1Text.setTextColor(this.mClockInfo.getPrimaryColor());
        this.mTimeHour2Text.setTextColor(this.mClockInfo.getPrimaryColor());
        this.mTimeMinute1Text.setTextColor(this.mClockInfo.getPrimaryColor());
        this.mTimeMinute2Text.setTextColor(this.mClockInfo.getPrimaryColor());
        this.mLineView.setBackgroundColor(this.mClockInfo.getPrimaryColor());
        this.mDateText.setTextColor(ColorUtils.blendColor(this.mClockInfo.getPrimaryColor(), 0.6f));
        this.mWeekText.setTextColor(ColorUtils.blendColor(this.mClockInfo.getPrimaryColor(), 0.6f));
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        updateViewsLayoutParams();
        int i = getResources().getConfiguration().orientation;
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mClockContainer.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.miui_smart_frame_clock_width);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = (int) (getDimen(R.dimen.miui_smart_frame_clock_height) * 1.0f);
        if (DeviceConfig.PAD_DEVICE && i == 2) {
            layoutParams.startToStart = 0;
            layoutParams.endToEnd = -1;
            layoutParams.setMarginStart(getDimen(R.dimen.miui_smart_frame_clock_margin_start));
        } else {
            layoutParams.startToStart = 0;
            layoutParams.endToEnd = 0;
            layoutParams.setMarginStart(0);
        }
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_smart_frame_clock_margin_top);
        this.mClockContainer.setLayoutParams(layoutParams);
        layoutWeekText(1.0f);
        setTextColor();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        ((ViewGroup.MarginLayoutParams) ((ConstraintLayout.LayoutParams) this.mHourSpace.getLayoutParams())).topMargin = (int) (getDimen(R.dimen.miui_smart_frame_clock_hour_margin_top) * 1.0f);
        ((ViewGroup.MarginLayoutParams) ((ConstraintLayout.LayoutParams) this.mMinuteSpace.getLayoutParams())).topMargin = (int) (getDimen(R.dimen.miui_smart_frame_clock_minute_margin_top) * 1.0f);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mTimeHour1Text.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mTimeHour2Text.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mTimeMinute1Text.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.mTimeMinute2Text.getLayoutParams();
        int dimen = (int) (getDimen(R.dimen.miui_smart_frame_clock_time_margin_start) * 1.0f);
        int dimen2 = (int) (getDimen(R.dimen.miui_smart_frame_clock_time_letter_space) * 1.0f);
        if (DeviceConfig.isRTL()) {
            layoutParams.setMarginStart(dimen2);
            layoutParams2.setMarginStart(dimen);
            layoutParams3.setMarginStart(dimen2);
            layoutParams4.setMarginStart(dimen);
        } else {
            layoutParams.setMarginStart(dimen);
            layoutParams2.setMarginStart(dimen2);
            layoutParams3.setMarginStart(dimen);
            layoutParams4.setMarginStart(dimen2);
        }
        this.mTimeHour1Text.setLayoutParams(layoutParams);
        this.mTimeHour2Text.setLayoutParams(layoutParams2);
        this.mTimeMinute1Text.setLayoutParams(layoutParams3);
        this.mTimeMinute2Text.setLayoutParams(layoutParams4);
        float dimen3 = (int) (getDimen(R.dimen.miui_smart_frame_clock_time_text_size) * 1.0f);
        this.mTimeHour1Text.setTextSize(0, dimen3);
        this.mTimeHour2Text.setTextSize(0, dimen3);
        this.mTimeMinute1Text.setTextSize(0, dimen3);
        this.mTimeMinute2Text.setTextSize(0, dimen3);
        ConstraintLayout.LayoutParams layoutParams5 = (ConstraintLayout.LayoutParams) this.mLineView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams5).height = (int) (getDimen(R.dimen.miui_smart_frame_clock_line_height) * 1.0f);
        ((ViewGroup.MarginLayoutParams) layoutParams5).topMargin = (int) (getDimen(R.dimen.miui_smart_frame_clock_line_margin_top) * 1.0f);
    }
}
