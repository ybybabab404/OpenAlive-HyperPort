package com.miui.clock.eastern.c;

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
import com.miui.clock.eastern.c.clokcinfo.EasternArtCBaseInfo;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.ColorUtils;
import com.miui.clock.utils.DeviceConfig;
import java.util.Map;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class EasternArtCNotificationClock extends EasternArtCBase {
    public ViewGroup mClockContainer;
    public TextView mDateWeekView;
    public TextView mTimeView;

    public EasternArtCNotificationClock(Context context) {
        super(context);
    }

    public EasternArtCNotificationClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        EasternArtCBaseInfo easternArtCBaseInfo = this.mClockInfo;
        if (easternArtCBaseInfo == null) {
            return;
        }
        ClockEffectUtils.clearClockEffectsContainer(this, easternArtCBaseInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mTimeView, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mDateWeekView, this.mClockInfo, z);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getBackgroundBlurContainer() {
        return this.mClockContainer;
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getMagazineColor() {
        EasternArtCBaseInfo easternArtCBaseInfo = this.mClockInfo;
        if (easternArtCBaseInfo != null) {
            return ColorUtils.blendColor(easternArtCBaseInfo.getOriginMagazineColor(), 0.6f);
        }
        return -1;
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockBottom() {
        return this.mMagazineInfoVisible ? getDimen(R.dimen.miui_eastern_art_single_magazine_notification_margin_top) : getDimen(R.dimen.miui_eastern_art_single_notification_margin_top);
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public View getNotificationClockTopView() {
        return this.mClockContainer;
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mDateWeekView = (TextView) findViewById(R.id.date_and_week);
        this.mTimeView = (TextView) findViewById(R.id.time_hour_minute);
        this.mClockContainer = (ViewGroup) findViewById(R.id.clock_container);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        super.setClockPalette(i, z, map, z2);
        if (DeviceConfig.supportBackgroundBlur(this.mContext)) {
            updateClockBlendColor(this.mClockInfo.getBlendColor(), this.mClockInfo.getSecondaryBlendColor());
        } else {
            clearPassBlur();
        }
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        clearColorEffect(false);
        setTextTypeFace(this.mTimeView, this.mClockInfo.getClockStyle());
        updateTime();
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2, int i3, int i4) {
        if (!DeviceConfig.supportBackgroundBlur(this.mContext)) {
            updateClockColor(i, i2);
            return;
        }
        boolean zUpdateTextDark = updateTextDark(this.mTextDark);
        int textDarkAlpha = getTextDarkAlpha(this.mTextDark);
        ViewGroup viewGroup = this.mClockContainer;
        int dimen = getDimen(R.dimen.miui_eastern_art_c_clock_blur_radius);
        EasternArtCBaseInfo easternArtCBaseInfo = this.mClockInfo;
        ClockEffectUtils.setClockEffectsContainer(viewGroup, dimen, easternArtCBaseInfo, ClockStyleInfo.isAODType(easternArtCBaseInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()));
        TextView textView = this.mTimeView;
        EasternArtCBaseInfo easternArtCBaseInfo2 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView, easternArtCBaseInfo2, zUpdateTextDark, i, easternArtCBaseInfo2.getPrimaryColor(), i3, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()), textDarkAlpha);
        TextView textView2 = this.mDateWeekView;
        EasternArtCBaseInfo easternArtCBaseInfo3 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView2, easternArtCBaseInfo3, zUpdateTextDark, i, easternArtCBaseInfo3.getPrimaryColor(), i3, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()), textDarkAlpha);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockColor(int i, int i2) {
        updatePrimaryColor(i);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        updateClockColor(this.mClockInfo.getPrimaryColor(), this.mClockInfo.getPrimaryColor());
    }

    public void updatePrimaryColor(int i) {
        this.mTimeView.setTextColor(i);
        this.mDateWeekView.setTextColor(i);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        Calendar calendar = this.mCalendar;
        Context context = this.mContext;
        this.mDateWeekView.setText(calendar.format(context, context.getString(R.string.miui_lock_screen_date_12)));
        String chsTimeWholeHour = ClassicClockTimeUtils.getChsTimeWholeHour(this.m24HourFormat, this.mCalendar);
        if (!this.mIsOnHour) {
            chsTimeWholeHour = chsTimeWholeHour + ClassicClockTimeUtils.getChsTimeWholeMinute(this.mCalendar);
        }
        this.mTimeView.setText(chsTimeWholeHour);
        this.mTimeView.setContentDescription(this.mCalendar.format(getContext(), getResources().getString(this.m24HourFormat ? R.string.miui_aesthetics_a_notification_format_24 : R.string.miui_aesthetics_a_notification_format_12)));
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        ViewGroup viewGroup = this.mClockContainer;
        if (viewGroup != null) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) viewGroup.getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mTimeView.getLayoutParams();
            if (this.mClockInfo.getClockStyle() != 2) {
                ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_eastern_art_single_margin_top);
                this.mTimeView.setTextSize(0, getDimen(R.dimen.miui_eastern_art_single_time_text_size));
            } else {
                ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_eastern_art_cursive_single_margin_top);
                layoutParams2.topMargin = getDimen(R.dimen.miui_eastern_art_cursive_single_notification_time_margin_top);
                this.mTimeView.setLayoutParams(layoutParams2);
                this.mTimeView.setTextSize(0, getDimen(R.dimen.miui_eastern_art_cursive_single_time_text_size));
            }
            this.mClockContainer.setLayoutParams(layoutParams);
            this.mDateWeekView.setTextSize(0, getDimen(R.dimen.miui_eastern_art_single_date_text_size));
        }
    }
}
