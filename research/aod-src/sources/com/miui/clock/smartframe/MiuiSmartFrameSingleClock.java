package com.miui.clock.smartframe;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.utils.BaseLineSpaceView;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.GlobalColorUtils;
import com.miui.clock.utils.SvgUtils;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class MiuiSmartFrameSingleClock extends MiuiSmartFrameBase {
    public FrameLayout mBatteryContainer;
    public View mClockContainer;
    public BaseLineSpaceView mHourSpace;
    public ImageView mImagePath;
    public BaseLineSpaceView mMinuteSpace;

    public MiuiSmartFrameSingleClock(Context context) {
        super(context);
    }

    public MiuiSmartFrameSingleClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void setTextColor() {
        int color = Color.parseColor("#D7D7D7");
        this.mTimeHour1Text.setTextColor(color);
        this.mTimeHour2Text.setTextColor(color);
        this.mTimeMinute1Text.setTextColor(color);
        this.mTimeMinute2Text.setTextColor(color);
        this.mDateText.setTextColor(color);
        this.mWeekText.setTextColor(color);
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
        if (clockViewType == ClockViewType.BATTERY_CONTAINER) {
            return this.mBatteryContainer;
        }
        return clockViewType == ClockViewType.LINE ? this.mImagePath : super.getIClockView(clockViewType);
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

    public final void layoutWeekText() {
        float dimen = getDimen(R.dimen.miui_smart_frame_single_clock_date_text_size);
        this.mWeekText.setTextSize(0, dimen);
        this.mDateText.setTextSize(0, dimen);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mWeekText.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mDateText.getLayoutParams();
        layoutParams2.setMarginStart(getDimen(R.dimen.miui_smart_frame_single_clock_date_margin_start));
        int i = R.dimen.miui_smart_frame_single_clock_date_margin_top;
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimen(i);
        layoutParams.setMarginStart(getDimen(R.dimen.miui_smart_frame_single_clock_week_margin_start));
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(i);
        this.mWeekText.setLayoutParams(layoutParams);
        this.mDateText.setLayoutParams(layoutParams2);
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mClockContainer = findViewById(R.id.clock_container);
        this.mImagePath = (ImageView) findViewById(R.id.image_path);
        this.mBatteryContainer = (FrameLayout) findViewById(R.id.clock_battery_container);
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

    public final void setImageAndColor() {
        MiuiSmartFrameClockBaseInfo miuiSmartFrameClockBaseInfo = this.mClockInfo;
        if (miuiSmartFrameClockBaseInfo != null) {
            int clockStyle = miuiSmartFrameClockBaseInfo.getClockStyle();
            int i = R.drawable.miui_smart_frame_path_1;
            switch (clockStyle) {
                case 2:
                    i = R.drawable.miui_smart_frame_path_2;
                    break;
                case 3:
                    i = R.drawable.miui_smart_frame_path_3;
                    break;
                case 4:
                    i = R.drawable.miui_smart_frame_path_4;
                    break;
                case 5:
                    i = R.drawable.miui_smart_frame_path_5;
                    break;
                case 6:
                    i = R.drawable.miui_smart_frame_path_6;
                    break;
            }
            int iTransformSmartFrameAodColor = GlobalColorUtils.transformSmartFrameAodColor(this.mClockInfo.getSecondaryColor());
            this.mImagePath.setImageDrawable(SvgUtils.initSvgColor(ContextCompat.getDrawable(getContext(), i), iTransformSmartFrameAodColor));
        }
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setMagazineInfoVisible(boolean z) {
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

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.smartframe.MiuiSmartFrameBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        updateViewsLayoutParams();
        if (this.mClockInfo != null) {
            setTextColor();
            setImageAndColor();
        }
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mClockContainer.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.miui_smart_frame_single_clock_width);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.miui_smart_frame_single_clock_height);
        if (DeviceConfig.FOLD_DEVICE && DeviceConfig.inFoldLargeScreen(this.mContext)) {
            layoutParams.startToStart = 0;
            layoutParams.endToEnd = -1;
            layoutParams.topToTop = 0;
            layoutParams.setMarginStart(getDimen(R.dimen.miui_smart_frame_single_clock_margin_start));
        } else {
            layoutParams.startToStart = 0;
            layoutParams.endToEnd = 0;
            layoutParams.topToTop = 0;
            layoutParams.setMarginStart(0);
        }
        ((ViewGroup.MarginLayoutParams) ((ConstraintLayout.LayoutParams) this.mHourSpace.getLayoutParams())).topMargin = getDimen(R.dimen.miui_smart_frame_single_clock_hour_margin_top);
        ((ViewGroup.MarginLayoutParams) ((ConstraintLayout.LayoutParams) this.mMinuteSpace.getLayoutParams())).topMargin = getDimen(R.dimen.miui_smart_frame_single_clock_minute_margin_top);
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mTimeHour1Text.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mTimeHour2Text.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.mTimeMinute1Text.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams5 = (ConstraintLayout.LayoutParams) this.mTimeMinute2Text.getLayoutParams();
        int dimen = getDimen(R.dimen.miui_smart_frame_single_clock_time_margin_start);
        int dimen2 = getDimen(R.dimen.miui_smart_frame_single_clock_time_letter_space);
        int dimen3 = getDimen(R.dimen.miui_smart_frame_single_clock_minute1_margin_start);
        int dimen4 = getDimen(R.dimen.miui_smart_frame_single_clock_minute2_margin_start);
        if (DeviceConfig.isRTL()) {
            layoutParams2.setMarginStart(dimen2);
            layoutParams3.setMarginStart(dimen);
            layoutParams4.setMarginStart(dimen4);
            layoutParams5.setMarginStart(dimen3);
        } else {
            layoutParams2.setMarginStart(dimen);
            layoutParams3.setMarginStart(dimen2);
            layoutParams4.setMarginStart(dimen3);
            layoutParams5.setMarginStart(dimen4);
        }
        this.mTimeHour1Text.setLayoutParams(layoutParams2);
        this.mTimeHour2Text.setLayoutParams(layoutParams3);
        this.mTimeMinute1Text.setLayoutParams(layoutParams4);
        this.mTimeMinute2Text.setLayoutParams(layoutParams5);
        float dimen5 = getDimen(R.dimen.miui_smart_frame_single_clock_time_text_size);
        this.mTimeHour1Text.setTextSize(0, dimen5);
        this.mTimeHour2Text.setTextSize(0, dimen5);
        this.mTimeMinute1Text.setTextSize(0, dimen5);
        this.mTimeMinute2Text.setTextSize(0, dimen5);
        layoutWeekText();
        ConstraintLayout.LayoutParams layoutParams6 = (ConstraintLayout.LayoutParams) this.mBatteryContainer.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams6).topMargin = getDimen(R.dimen.miui_smart_frame_single_clock_battery_margin_top);
        layoutParams6.setMarginEnd(getDimen(R.dimen.miui_smart_frame_single_clock_battery_margin_end));
        this.mBatteryContainer.setLayoutParams(layoutParams6);
        this.mClockContainer.setLayoutParams(layoutParams);
    }
}
