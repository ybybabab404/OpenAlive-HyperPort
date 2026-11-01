package com.miui.clock.aesthetics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.MutiDeviceDimenAdapter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class MiuiAestheticsAHourClock extends MiuiAestheticsABase {
    public Guideline guidelineV50;
    public ConstraintLayout liClockContainer;
    public TextView tvHour;

    /* JADX INFO: renamed from: com.miui.clock.aesthetics.MiuiAestheticsAHourClock$1, reason: invalid class name */
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
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.CLOCK_CONTAINER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public MiuiAestheticsAHourClock(Context context) {
        super(context);
    }

    public MiuiAestheticsAHourClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MiuiAestheticsAHourClock(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private void getUnUseDimens() {
        Resources resources = getResources();
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_hour_wide_text_size_fold);
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_hour_wide_text_size_pad);
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_data_wide_text_size_fold);
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_data_wide_text_size_pad);
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_clock_wide_margin_top_fold);
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_clock_wide_margin_top_fold_land);
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_clock_wide_margin_top_pad);
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_clock_wide_margin_top_pad_land);
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_clock_wide_single_margin_top_fold);
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_clock_wide_single_margin_top_fold_land);
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_clock_wide_single_margin_top_pad);
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_clock_wide_single_margin_top_pad_land);
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_clock_wide_min_margin_top_pad);
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_clock_wide_data_margin_top_pad);
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_clock_wide_week_margin_top_pad);
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_clock_narrow_margin_top_fold);
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_clock_narrow_margin_top_fold_land);
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_clock_narrow_margin_top_pad);
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_clock_narrow_margin_top_pad_land);
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_clock_narrow_single_margin_top_fold);
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_clock_notification_margin_top_fold);
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_clock_notification_margin_top_fold_land);
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_clock_notification_margin_top_pad);
        resources.getDimensionPixelSize(R.dimen.miui_aesthetics_a_clock_notification_margin_top_pad_land);
    }

    private void initTextParams() {
        this.tvHour.setTypeface(FontUtils.getAestheticsTypeFace(this.clockIsWide));
        this.tvHour.setTextSize(0, this.clockIsWide ? MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.miui_aesthetics_a_hour_wide_text_size, true) : getDimen(R.dimen.miui_aesthetics_a_hour_narrow_text_size));
    }

    private void initViewParams() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.liClockContainer.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.miui_aesthetics_a_clock_blur_width);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.miui_aesthetics_a_clock_blur_height);
        layoutParams.topToTop = 0;
        layoutParams.leftToLeft = 0;
        layoutParams.rightToRight = 0;
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), this.clockIsWide ? R.dimen.miui_aesthetics_a_clock_wide_margin_top : R.dimen.miui_aesthetics_a_clock_narrow_margin_top, false);
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.tvHour.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = -2;
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = -2;
        layoutParams2.topToTop = this.liClockContainer.getId();
        layoutParams2.leftToLeft = this.liClockContainer.getId();
        layoutParams2.rightToRight = this.liClockContainer.getId();
        this.liClockContainer.setLayoutParams(layoutParams);
        this.tvHour.setLayoutParams(layoutParams2);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo = this.mClockInfo;
        if (miuiAestheticsClockBaseInfo == null) {
            return;
        }
        ClockEffectUtils.clearClockEffectsContainer(this.liClockContainer, miuiAestheticsClockBaseInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.tvHour, this.mClockInfo, z);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getBackgroundBlurContainer() {
        return this.liClockContainer;
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i != 1) {
            return i != 2 ? super.getIClockView(clockViewType) : this.liClockContainer;
        }
        return this.tvHour;
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase
    public void initView() {
        super.initView();
        this.liClockContainer = (ConstraintLayout) findViewById(R.id.li_clock_container);
        int i = R.id.guideline;
        Guideline guideline = (Guideline) findViewById(i);
        this.guidelineV50 = guideline;
        guideline.setGuidelinePercent(0.5f);
        this.guidelineV50 = (Guideline) findViewById(i);
        this.tvHour = (TextView) findViewById(R.id.tv_hour);
        getUnUseDimens();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d("MiuiAestheticsAHourClock", "onAttachedToWindow, mOrientation = " + this.mOrientation);
        if (this.mOrientation != getResources().getConfiguration().orientation) {
            initViewParams();
            initTextParams();
        }
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        super.setClockPalette(i, z, map, z2);
        updateClockBlendColor();
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase
    public void setClockStyle(int i) {
        super.setClockStyle(i);
        initViewParams();
        initTextParams();
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2, int i3, int i4) {
        boolean zUpdateTextDark = updateTextDark(this.mTextDark);
        int textDarkAlpha = getTextDarkAlpha(this.mTextDark);
        updateClockEffectsContainer();
        TextView textView = this.tvHour;
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView, miuiAestheticsClockBaseInfo, zUpdateTextDark, i, miuiAestheticsClockBaseInfo.getPrimaryColor(), i3, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()), textDarkAlpha);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockColor(int i, int i2) {
        this.tvHour.setTextColor(i);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockEffectsContainer() {
        super.updateClockEffectsContainer();
        updateViewBackgroundBlurContainer(this.liClockContainer, getDimen(R.dimen.miui_aesthetics_a_clock_blur_radius), this.tvHour);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        super.updateColor();
        updateClockColor(this.mClockInfo.getPrimaryColor(), this.mClockInfo.getSecondaryColor());
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mMagazineGroup.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = -2;
        ((ViewGroup.MarginLayoutParams) layoutParams).height = -2;
        layoutParams.topToTop = 0;
        layoutParams.startToStart = 0;
        layoutParams.endToEnd = 0;
        layoutParams.setMargins(0, getDimen(this.clockIsWide ? R.dimen.miui_aesthetics_a_wide_magazine_margin_top : R.dimen.miui_aesthetics_a_narrow_magazine_margin_top), 0, 0);
        this.mMagazineGroup.setLayoutParams(layoutParams);
        this.tvHour.setText(ClassicClockTimeUtils.getTimeString(getHourInt(this.m24HourFormat, this.mCalendar), true));
        this.tvHour.setContentDescription(this.mCalendar.format(getContext(), getResources().getString(this.m24HourFormat ? R.string.miui_aesthetics_a_notification_format_24 : R.string.miui_aesthetics_a_notification_format_12)));
    }
}
