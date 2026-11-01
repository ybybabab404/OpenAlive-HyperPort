package com.miui.clock.padexclusive.a;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.padexclusive.a.clockinfo.PadExclusiveABaseInfo;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.MiuiBlurUtils;
import java.util.Map;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class PadExclusiveAClock extends PadExclusiveABase {
    public TextView mDateView;
    public TextView mTimeView;
    public View mbackgroundBlurView;

    /* JADX INFO: renamed from: com.miui.clock.padexclusive.a.PadExclusiveAClock$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.FULL_TIME.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_DATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public PadExclusiveAClock(Context context) {
        super(context);
    }

    public PadExclusiveAClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        PadExclusiveABaseInfo padExclusiveABaseInfo = this.mClockInfo;
        if (padExclusiveABaseInfo == null) {
            return;
        }
        ClockEffectUtils.clearClockEffectsContainer(this.mbackgroundBlurView, padExclusiveABaseInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mTimeView, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mDateView, this.mClockInfo, z);
        ClockEffectUtils.clearClockGradualEffect(this.mTimeView, this.mClockInfo, z);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getBackgroundBlurContainer() {
        return this.mbackgroundBlurView;
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i != 1) {
            return i != 2 ? super.getIClockView(clockViewType) : this.mDateView;
        }
        return this.mTimeView;
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getMagazineColor() {
        return super.getMagazineColor();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockTop() {
        return getDimen(R.dimen.miui_pad_exclusive_a_date_margin_top);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiBaseClock2
    public View getNotificationClockTopView() {
        return this.mTimeView;
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiBaseClock2, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mbackgroundBlurView = findViewById(R.id.blur_view);
        this.mTimeView = (TextView) findViewById(R.id.time_view);
        this.mDateView = (TextView) findViewById(R.id.date);
        updateViewsLayoutParams();
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        TextView textView = this.mTimeView;
        textView.setPivotX(textView.getMeasuredWidth() / 2.0f);
        this.mTimeView.setPivotY(0.0f);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        super.setClockPalette(i, z, map, z2);
        updateClockBlendColor(this.mClockInfo.getBlendColor(), this.mClockInfo.getSecondaryBlendColor());
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        clearColorEffect(false);
        setTextTypeFace(this.mTimeView, 1);
        setTextTypeFace(this.mDateView, 3);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2, int i3, int i4) {
        if (this.mClockInfo != null) {
            boolean zUpdateTextDark = updateTextDark(this.mTextDark);
            int textDarkAlpha = getTextDarkAlpha(this.mTextDark);
            View view = this.mbackgroundBlurView;
            int dimen = getDimen(R.dimen.miui_pad_exclusive_a_clock_blur_radius);
            PadExclusiveABaseInfo padExclusiveABaseInfo = this.mClockInfo;
            ClockEffectUtils.setClockEffectsContainer(view, dimen, padExclusiveABaseInfo, ClockStyleInfo.isAODType(padExclusiveABaseInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()));
            float[] currentGradientParams = getCurrentGradientParams();
            TextView textView = this.mTimeView;
            PadExclusiveABaseInfo padExclusiveABaseInfo2 = this.mClockInfo;
            ClockEffectUtils.setClockGradualEffect(currentGradientParams, textView, padExclusiveABaseInfo2, ClockStyleInfo.isAODType(padExclusiveABaseInfo2.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()));
            MiuiBlurUtils.chooseBackgroundBlurContainer(this.mTimeView, this.mbackgroundBlurView);
            TextView textView2 = this.mTimeView;
            PadExclusiveABaseInfo padExclusiveABaseInfo3 = this.mClockInfo;
            ClockEffectUtils.setClockEffectsView(textView2, padExclusiveABaseInfo3, zUpdateTextDark, padExclusiveABaseInfo3.getBlendColor(), this.mClockInfo.getPrimaryColor(), i3, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()), textDarkAlpha);
            if (ClockEffectUtils.isGlowType(this.mClockInfo.getClockEffect()) || !DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                return;
            }
            MiuiBlurUtils.chooseBackgroundBlurContainer(this.mDateView, this.mbackgroundBlurView);
            TextView textView3 = this.mDateView;
            PadExclusiveABaseInfo padExclusiveABaseInfo4 = this.mClockInfo;
            ClockEffectUtils.setClockEffectsView(textView3, padExclusiveABaseInfo4, zUpdateTextDark, padExclusiveABaseInfo4.getBlendColor(), this.mClockInfo.getPrimaryColor(), i3, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()), textDarkAlpha);
        }
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        super.updateColor();
        this.mTimeView.setTextColor(this.mClockInfo.getPrimaryColor());
        this.mDateView.setTextColor(this.mClockInfo.getPrimaryColor());
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateGradientEffectColor(float[] fArr) {
        super.updateGradientEffectColor(fArr);
        PadExclusiveABaseInfo padExclusiveABaseInfo = this.mClockInfo;
        if (padExclusiveABaseInfo != null && ClockEffectUtils.isGradualType(padExclusiveABaseInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            MiuiBlurUtils.setGradientBlurMethod(this.mTimeView, fArr);
        }
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        this.mTimeView.setText(this.mContext.getString(R.string.format_hour_minute, ClassicClockTimeUtils.getTimeString(ClassicClockTimeUtils.getHourInt(this.m24HourFormat, this.mCalendar), true), ClassicClockTimeUtils.getTimeString(this.mCalendar.get(20), true)));
        this.mDateView.setText(this.mCalendar.format(this.mContext, getResources().getString(R.string.format_month_day_week)));
        this.mTimeView.setContentDescription(DateUtils.formatDateTime(getContext(), System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
        this.mDateView.setContentDescription(getResources().getString(R.string.format_month_day_week_desc, this.mCalendar.format(this.mContext, getResources().getString(R.string.format_full_month_of_year)), String.valueOf(ClassicClockTimeUtils.getDayOfMonthInt(this.mCalendar)), this.mCalendar.format(this.mContext, getResources().getString(R.string.format_full_day_of_week))));
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        int timeTextSize = getTimeTextSize();
        Log.d("PadExclusiveAClock", "current mOrientation： " + this.mOrientation + ", text size: " + timeTextSize);
        Configuration configuration = getContext().getResources().getConfiguration();
        StringBuilder sb = new StringBuilder();
        sb.append("configuration: ");
        sb.append(configuration);
        Log.d("PadExclusiveAClock", sb.toString());
        int i = R.dimen.miui_pad_exclusive_a_time_margin_top;
        int i2 = R.dimen.miui_pad_exclusive_a_date_margin_top;
        this.mDateView.setTextSize(0, getDimenPX(R.dimen.miui_pad_exclusive_a_date_size));
        this.mTimeView.setTextSize(0, timeTextSize);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mTimeView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimenPX(i);
        this.mTimeView.setLayoutParams(layoutParams);
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mDateView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimenPX(i2);
        this.mDateView.setLayoutParams(layoutParams2);
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mbackgroundBlurView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams3).bottomMargin = getDimenPX(R.dimen.miui_pad_exclusive_a_signature_margin_bottom);
        this.mbackgroundBlurView.setLayoutParams(layoutParams3);
    }
}
