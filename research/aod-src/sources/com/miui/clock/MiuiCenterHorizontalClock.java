package com.miui.clock;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.utils.DeviceConfig;
import java.util.Map;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class MiuiCenterHorizontalClock extends MiuiBaseClock {
    public TextView mTimeText;

    public MiuiCenterHorizontalClock(Context context) {
        this(context, null);
    }

    public MiuiCenterHorizontalClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ View getBackgroundBlurContainer() {
        return super.getBackgroundBlurContainer();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ ClockStyleInfo getClockStyleInfo() {
        return super.getClockStyleInfo();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ Rect getDepthAvoidRect() {
        return super.getDepthAvoidRect();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getMagazineColor() {
        return super.getMagazineColor();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationClockTop() {
        return super.getNotificationClockTop();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ TextView getTimeView() {
        return super.getTimeView();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getTimeZone() {
        return super.getTimeZone();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public float getTopMargin() {
        return this.mContext.getResources().getDimensionPixelSize(R.dimen.miui_center_clock_magin_top);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiBaseClock, android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override // com.miui.clock.MiuiBaseClock, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTimeText = (TextView) findViewById(R.id.current_time);
        updateTime();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setBackgroundBlurContainer(View view) {
        super.setBackgroundBlurContainer(view);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setClockPalette(int i, boolean z, Map map, boolean z2) {
        super.setClockPalette(i, z, map, z2);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGradientIndex(int i) {
        super.setGradientIndex(i);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setSuperSaveOpen(boolean z) {
        super.setSuperSaveOpen(z);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void setTextColorDark(boolean z) {
        super.setTextColorDark(z);
        int color = z ? getResources().getColor(R.color.miui_common_time_dark_text_color) : -1;
        this.mTimeText.setTextColor(color);
        setInfoDarkMode(color);
        setTextFontDark(z);
    }

    @Override // com.miui.clock.MiuiBaseClock
    public void setTextFontDark(boolean z) {
        super.setTextFontDark(z);
        setFontStyleTextView(z, this.mTimeText);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        this.mTimeText.setText(DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
    }

    @Override // com.miui.clock.MiuiBaseClock
    public void updateViewsLayoutParams() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        layoutParams.topMargin = this.mHasTopMargin ? (int) (this.mScaleRatio * this.mResources.getDimensionPixelSize(R.dimen.miui_center_clock_magin_top)) : 0;
        setLayoutParams(layoutParams);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mCurrentDate.getLayoutParams();
        layoutParams2.topMargin = (int) (this.mScaleRatio * this.mResources.getDimensionPixelSize(R.dimen.miui_clock_date_info_top_margin));
        this.mCurrentDate.setLayoutParams(layoutParams2);
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mLunarCalendarInfo.getLayoutParams();
        layoutParams3.topMargin = (int) (this.mScaleRatio * this.mResources.getDimensionPixelSize(R.dimen.miui_clock_lunar_calendar_top_margin));
        this.mLunarCalendarInfo.setLayoutParams(layoutParams3);
        LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) this.mOwnerInfo.getLayoutParams();
        layoutParams4.topMargin = (int) (this.mScaleRatio * this.mResources.getDimensionPixelSize(R.dimen.miui_clock_owner_info_top_margin));
        this.mOwnerInfo.setLayoutParams(layoutParams4);
    }

    @Override // com.miui.clock.MiuiBaseClock
    public void updateViewsTextSize() {
        super.updateViewsTextSize();
        this.mTimeText.setTextSize(0, (int) (this.mScaleRatio * DeviceConfig.getDimen(R.dimen.miui_clock_center_time_text_size, this.mContext)));
    }
}
