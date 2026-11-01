package com.miui.clock.classic.max;

import android.content.Context;
import android.graphics.Rect;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.ViewGroup;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.utils.ClassicClockTimeUtils;

/* JADX INFO: loaded from: classes.dex */
public class ClassicMaxAodClockView extends ClassicMaxClockBaseView {
    public ViewGroup mMagazineInfoContainer;

    public ClassicMaxAodClockView(Context context) {
        super(context);
    }

    public ClassicMaxAodClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ClassicMaxAodClockView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView
    public boolean isAODType() {
        return true;
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mMagazineInfoContainer = (ViewGroup) findViewById(R.id.magazine_info_container);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        ((ClassicMaxClockBaseView) this).mSignatureText.setVisibility(8);
        this.mMagazineInfoContainer.setVisibility(8);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        int hourInt = ClassicClockTimeUtils.getHourInt(this.m24HourFormat, this.mCalendar);
        SpannableString spannableString = new SpannableString(this.mContext.getString(R.string.format_hour_minute, ClassicClockTimeUtils.getTimeString(hourInt, this.m24HourFormat), ClassicClockTimeUtils.getTimeString(this.mCalendar.get(20), true)));
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(this.mClockStyleInfo.getAodPrimaryColor());
        ForegroundColorSpan foregroundColorSpan2 = new ForegroundColorSpan(this.mClockStyleInfo.getAodSecondaryColor());
        ForegroundColorSpan foregroundColorSpan3 = new ForegroundColorSpan(this.mClockStyleInfo.getAodPrimaryColor());
        int i = (hourInt >= 10 || this.m24HourFormat) ? 2 : 1;
        spannableString.setSpan(foregroundColorSpan, 0, i, 17);
        int i2 = i + 1;
        spannableString.setSpan(foregroundColorSpan2, i, i2, 17);
        spannableString.setSpan(foregroundColorSpan3, i2, spannableString.length(), 17);
        this.mTimeView.setText(spannableString);
    }
}
