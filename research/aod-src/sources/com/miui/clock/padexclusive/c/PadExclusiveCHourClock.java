package com.miui.clock.padexclusive.c;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.MiuiBlurUtils;
import java.util.Map;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class PadExclusiveCHourClock extends PadExclusiveCBase {
    public TextView mTimeHour;
    public ConstraintLayout mTimeHourParent;

    /* JADX INFO: renamed from: com.miui.clock.padexclusive.c.PadExclusiveCHourClock$1, reason: invalid class name */
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
        }
    }

    public PadExclusiveCHourClock(Context context) {
        super(context);
    }

    public PadExclusiveCHourClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        PadExclusiveCBaseInfo padExclusiveCBaseInfo = this.mClockInfo;
        if (padExclusiveCBaseInfo == null) {
            return;
        }
        ClockEffectUtils.clearClockEffectsContainer(this, padExclusiveCBaseInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mTimeHour, this.mClockInfo, z);
        ClockEffectUtils.clearClockGradualEffect(this.mTimeHour, this.mClockInfo, z);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i != 1) {
            return i != 2 ? super.getIClockView(clockViewType) : this.mTimeHour;
        }
        return this.mTimeHourParent;
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getMagazineColor() {
        return super.getMagazineColor();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockTop() {
        return getDimen(this.mHasFaceIcon.booleanValue() ? R.dimen.miui_pad_c_notification_face_time_margin_top : R.dimen.miui_pad_c_notification_time_margin_top);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiBaseClock2
    public View getNotificationClockTopView() {
        return this.mTimeHourParent;
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTimeHourParent = (ConstraintLayout) findViewById(R.id.pad_c_hour_parent);
        TextView textView = (TextView) findViewById(R.id.pad_c_hour);
        this.mTimeHour = textView;
        textView.setTypeface(this.mTimeFontType);
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        ConstraintLayout constraintLayout = this.mTimeHourParent;
        constraintLayout.setPivotX(constraintLayout.getMeasuredWidth() / 2.0f);
        this.mTimeHourParent.setPivotY(0.0f);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        super.setClockPalette(i, z, map, z2);
        if (DeviceConfig.isHighTextContrastEnabled(this.mContext) || this.mClockInfo == null) {
            return;
        }
        ClockEffectUtils.setClockEffectsContainer(this, getDimen(R.dimen.miui_pad_exclusive_c_clock_blur_radius), this.mClockInfo, false, false);
        TextView textView = this.mTimeHour;
        PadExclusiveCBaseInfo padExclusiveCBaseInfo = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView, padExclusiveCBaseInfo, z, padExclusiveCBaseInfo.getBlendColor(), this.mClockInfo.getPrimaryColor(), false, false);
        ClockEffectUtils.setClockGradualEffect(getCurrentGradientParams(), this.mTimeHour, this.mClockInfo, false, false, 1.0f);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        clearColorEffect(false);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        super.updateColor();
        this.mTimeHour.setTextColor(this.mClockInfo.getPrimaryColor());
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateGradientEffectColor(float[] fArr) {
        super.updateGradientEffectColor(fArr);
        PadExclusiveCBaseInfo padExclusiveCBaseInfo = this.mClockInfo;
        if (padExclusiveCBaseInfo != null && ClockEffectUtils.isGradualType(padExclusiveCBaseInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            MiuiBlurUtils.setGradientBlurMethod(this.mTimeHour, fArr);
        }
    }

    @Override // com.miui.clock.padexclusive.c.PadExclusiveCBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        this.mTimeHour.setText(this.mHourStr);
        int i = this.m24HourFormat ? R.string.miui_clock_time_format_24 : R.string.miui_clock_time_format_12;
        TextView textView = this.mTimeHour;
        Calendar calendar = this.mCalendar;
        Context context = this.mContext;
        textView.setContentDescription(calendar.format(context, context.getString(i)));
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mTimeHourParent.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mTimeHour.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.miui_pad_c_time_parent_width);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.miui_pad_c_time_parent_height);
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_pad_c_time_parent_margin_top);
        layoutParams.startToStart = 0;
        layoutParams.endToEnd = 0;
        layoutParams.topToTop = 0;
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = getDimen(R.dimen.miui_pad_c_time_width);
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = getDimen(R.dimen.miui_pad_c_time_height);
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimen(R.dimen.miui_pad_c_time_margin_top);
        ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin = getDimen(R.dimen.miui_pad_c_hour_margin_left);
        layoutParams2.startToStart = 0;
        layoutParams2.topToTop = 0;
        this.mTimeHourParent.setLayoutParams(layoutParams);
        this.mTimeHour.setLayoutParams(layoutParams2);
        this.mTimeHour.setTextSize(0, getDimen(R.dimen.miui_pad_c_time_size));
    }
}
