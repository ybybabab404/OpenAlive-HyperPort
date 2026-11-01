package com.miui.clock.magazine;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.MiuiTextGlassView;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.utils.BaseLineSpaceView;
import com.miui.clock.utils.DeviceConfig;
import com.xiaomi.onetrack.util.ac;
import java.util.Locale;
import java.util.Map;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class MiuiMagazineCNotificationClock extends MiuiMagazineCBase {
    public ViewGroup mClockContainer;
    public float mDeviceScale;
    public MiuiTextGlassView mHourText;
    public MiuiTextGlassView mMinuteText;
    public BaseLineSpaceView mSpaceTime;
    public boolean supportAccessibilityScale;

    public MiuiMagazineCNotificationClock(Context context) {
        super(context);
        this.mDeviceScale = 1.0f;
        this.supportAccessibilityScale = true;
    }

    public MiuiMagazineCNotificationClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDeviceScale = 1.0f;
        this.supportAccessibilityScale = true;
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        if (clockViewType != ClockViewType.FULL_TIME && clockViewType != ClockViewType.CLOCK_CONTAINER) {
            return super.getIClockView(clockViewType);
        }
        return this.mClockContainer;
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public View getNotificationClockTopView() {
        return this.mClockContainer;
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void ignoreAccessibilityScale(boolean z) {
        this.supportAccessibilityScale = !z;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mHourText = (MiuiTextGlassView) findViewById(R.id.current_time_hour_style1);
        this.mMinuteText = (MiuiTextGlassView) findViewById(R.id.current_time_minute);
        this.mClockContainer = (ViewGroup) findViewById(R.id.clock_container);
        this.mSpaceTime = (BaseLineSpaceView) findViewById(R.id.time_space);
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        super.setClockPalette(i, z, map, z2);
        updateTime();
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        MiuiMagazineCBaseInfo miuiMagazineCBaseInfo = this.mClockInfo;
        if (miuiMagazineCBaseInfo == null) {
            return;
        }
        this.mHourText.setTextColor(miuiMagazineCBaseInfo.getPrimaryColor());
        if (this.mClockInfo.getClockStyle() == 6) {
            this.mMinuteText.setTextColor(this.mClockInfo.getPrimaryColor());
        } else {
            this.mMinuteText.setTextColor(this.mClockInfo.getSecondaryColor());
        }
        if (this.mClockInfo.getClockStyle() == 6) {
            this.mMinuteText.setFontFeatureSettings(this.ssTags[this.mCurrentTimeArray[2]] + ac.b + this.ssTags[this.mCurrentTimeArray[3]]);
        } else if (this.mClockInfo.getClockStyle() == 9) {
            this.mHourText.setFontFeatureSettings("");
            this.mMinuteText.setFontFeatureSettings("");
        } else {
            this.mMinuteText.setFontFeatureSettings("");
        }
        MiuiTextGlassView miuiTextGlassView = this.mHourText;
        Locale locale = Locale.ENGLISH;
        miuiTextGlassView.setText(String.format(locale, "%d%d", Integer.valueOf(this.mCurrentTimeArray[0]), Integer.valueOf(this.mCurrentTimeArray[1])));
        this.mMinuteText.setText(String.format(locale, "%d%d", Integer.valueOf(this.mCurrentTimeArray[2]), Integer.valueOf(this.mCurrentTimeArray[3])));
        this.mClockContainer.setContentDescription(DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
        this.mMinuteText.setEnableDiffusion(this.mClockInfo.isEnableDiffusion());
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        if (this.mClockContainer != null) {
            float dimen = getDimen(R.dimen.miui_magazine_c_clock_style1_text_size) * 1.0f;
            this.mHourText.setTextSize(0, dimen);
            this.mMinuteText.setTextSize(0, dimen);
            ((ViewGroup.MarginLayoutParams) ((ConstraintLayout.LayoutParams) this.mSpaceTime.getLayoutParams())).topMargin = (int) (getDimen(R.dimen.miui_magazine_c_clock_style1_time_margin_top) * 1.0f);
            int dimen2 = getDimen(R.dimen.miui_magazine_c_clock_notification_width);
            int dimen3 = getDimen(R.dimen.miui_magazine_c_clock_notification_height);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mClockContainer.getLayoutParams();
            ((ViewGroup.MarginLayoutParams) layoutParams).width = (int) (dimen2 * 1.0f);
            ((ViewGroup.MarginLayoutParams) layoutParams).height = (int) (dimen3 * 1.0f);
            if (!DeviceConfig.isJ18() || DeviceConfig.inLargeScreen(this.mContext)) {
                layoutParams.setMarginStart(getDimen(R.dimen.miui_magazine_c_clock_notification_margin_start));
                ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_magazine_c_clock_notification_margin_top);
            } else {
                layoutParams.setMarginStart(getDimen(R.dimen.miui_magazine_c_clock_notification_margin_start_j18));
                ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_magazine_c_clock_notification_margin_top_j18);
            }
            this.mClockContainer.setLayoutParams(layoutParams);
        }
    }
}
