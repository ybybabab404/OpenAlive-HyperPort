package com.miui.clock.eastern.c;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.eastern.c.clokcinfo.EasternArtCBaseInfo;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.ColorUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.MutiDeviceDimenAdapter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class EasternArtCHourClock extends EasternArtCBase {
    public TextView mBackgroundBlurView;
    public TextView mTimeHour1;
    public TextView mTimeHour2;

    /* JADX INFO: renamed from: com.miui.clock.eastern.c.EasternArtCHourClock$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.HOUR1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.HOUR2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public EasternArtCHourClock(Context context) {
        super(context);
    }

    public EasternArtCHourClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void getUnUseDimens() {
        Resources resources = getResources();
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_hour1_margin_top_fold);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_hour1_margin_top_fold_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_hour1_margin_top_pad);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_hour1_margin_top_pad_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_hour2_small_margin_top_fold);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_hour2_small_margin_top_fold_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_hour2_small_margin_top_pad);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_hour2_small_margin_top_pad_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_hour2_big_margin_top_fold);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_hour2_big_margin_top_fold_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_hour2_big_margin_top_pad);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_hour2_big_margin_top_pad_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_hour2_big_margin_top_fold);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_hour2_big_margin_top_fold_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_hour2_big_margin_top_pad);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_hour2_big_margin_top_pad_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_hour1_margin_top_fold);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_hour1_margin_top_fold_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_hour1_margin_top_pad);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_hour1_margin_top_pad_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_hour2_small_margin_top_fold);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_hour2_small_margin_top_fold_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_hour2_small_margin_top_pad);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_hour2_small_margin_top_pad_land);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        EasternArtCBaseInfo easternArtCBaseInfo = this.mClockInfo;
        if (easternArtCBaseInfo == null) {
            return;
        }
        ClockEffectUtils.clearClockEffectsContainer(this.mBackgroundBlurView, easternArtCBaseInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mTimeHour1, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mTimeHour2, this.mClockInfo, z);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getBackgroundBlurContainer() {
        return this.mBackgroundBlurView;
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

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i != 1) {
            return i != 2 ? super.getIClockView(clockViewType) : this.mTimeHour2;
        }
        return this.mTimeHour1;
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

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getTextViewSize() {
        return getTimeTextSize(this.mClockInfo.getClockStyle());
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mBackgroundBlurView = (TextView) findViewById(R.id.blur_view);
        this.mTimeHour1 = (TextView) findViewById(R.id.time_hour_1);
        this.mTimeHour2 = (TextView) findViewById(R.id.time_hour_2);
        getUnUseDimens();
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
            updateClockBlendColor();
        } else {
            clearPassBlur();
        }
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        clearColorEffect(false);
        if (this.mFontStyle != this.mClockInfo.getClockStyle()) {
            setTextTypeFace(this.mTimeHour1, this.mClockInfo.getClockStyle());
            setTextTypeFace(this.mTimeHour2, this.mClockInfo.getClockStyle());
        }
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
        if (this.mClockInfo != null) {
            updateClockEffectsContainer();
            TextView textView = this.mTimeHour1;
            EasternArtCBaseInfo easternArtCBaseInfo = this.mClockInfo;
            ClockEffectUtils.setClockEffectsView(textView, easternArtCBaseInfo, zUpdateTextDark, i, easternArtCBaseInfo.getPrimaryColor(), i3, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()), textDarkAlpha);
            TextView textView2 = this.mTimeHour2;
            EasternArtCBaseInfo easternArtCBaseInfo2 = this.mClockInfo;
            ClockEffectUtils.setClockEffectsView(textView2, easternArtCBaseInfo2, zUpdateTextDark, i, easternArtCBaseInfo2.getPrimaryColor(), i3, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()), textDarkAlpha);
        }
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockColor(int i, int i2) {
        updatePrimaryColor(i);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockEffectsContainer() {
        super.updateClockEffectsContainer();
        updateViewBackgroundBlurContainer(this.mBackgroundBlurView, getDimen(R.dimen.miui_eastern_art_c_clock_blur_radius), this.mTimeHour1, this.mTimeHour2);
    }

    public final void updateClockStyle(boolean z, int i, int i2, int i3) {
        int timeTextSize = getTimeTextSize(this.mClockInfo.getClockStyle());
        if (z) {
            this.mTimeHour1.setVisibility(8);
            this.mTimeHour2.setTextSize(0, timeTextSize);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mTimeHour2.getLayoutParams();
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), i2, false);
            this.mTimeHour2.setLayoutParams(layoutParams);
            return;
        }
        this.mTimeHour1.setVisibility(0);
        float f = timeTextSize;
        this.mTimeHour1.setTextSize(0, f);
        this.mTimeHour2.setTextSize(0, f);
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mTimeHour1.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), i, false);
        this.mTimeHour1.setLayoutParams(layoutParams2);
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mTimeHour2.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin = MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), i3, false);
        this.mTimeHour2.setLayoutParams(layoutParams3);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        updateClockColor(this.mClockInfo.getPrimaryColor(), this.mClockInfo.getSecondaryColor());
    }

    public void updatePrimaryColor(int i) {
        this.mTimeHour1.setTextColor(i);
        this.mTimeHour2.setTextColor(i);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        this.mTimeHour1.setText(ClassicClockTimeUtils.getChsTimeHour(this.m24HourFormat, this.mIsOnHour, true, this.mCalendar));
        this.mTimeHour2.setText(ClassicClockTimeUtils.getChsTimeHour(this.m24HourFormat, this.mIsOnHour, false, this.mCalendar));
        String str = this.mCalendar.format(getContext(), getResources().getString(this.m24HourFormat ? R.string.miui_aesthetics_a_notification_format_24 : R.string.miui_aesthetics_a_notification_format_12));
        this.mTimeHour1.setContentDescription(str);
        this.mTimeHour2.setContentDescription(str);
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        if (this.mClockInfo.getClockStyle() != 2) {
            updateClockStyle(this.mIsOnHour, R.dimen.miui_eastern_art_time_hour1_margin_top, R.dimen.miui_eastern_art_time_hour2_big_margin_top, R.dimen.miui_eastern_art_time_hour2_small_margin_top);
        } else {
            updateClockStyle(this.mIsOnHour, R.dimen.miui_eastern_art_cursive_time_hour1_margin_top, R.dimen.miui_eastern_art_cursive_time_hour2_big_margin_top, R.dimen.miui_eastern_art_cursive_time_hour2_small_margin_top);
        }
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mBackgroundBlurView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.miui_eastern_art_c_clock_blur_width);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.miui_eastern_art_c_clock_blur_height);
        layoutParams.topToTop = this.mIsOnHour ? R.id.time_hour_2 : R.id.time_hour_1;
        this.mBackgroundBlurView.setLayoutParams(layoutParams);
    }
}
