package com.miui.clock.eastern.b;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
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
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.ColorUtils;
import com.miui.clock.utils.FontUtils;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class EasternArtBVerticalTimeBelowClock extends EasternArtBBase {
    public static String TAG = "EasternArtBVerticalTimeBelowClock";
    public TextView mBackgroundBlurContainer;
    public TextView mHour1;
    public TextView mHour2;
    public TextView mMinute1;
    public TextView mMinute2;

    /* JADX INFO: renamed from: com.miui.clock.eastern.b.EasternArtBVerticalTimeBelowClock$1, reason: invalid class name */
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
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.MIN1.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.MIN2.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public EasternArtBVerticalTimeBelowClock(Context context) {
        super(context);
    }

    public EasternArtBVerticalTimeBelowClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public EasternArtBVerticalTimeBelowClock(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        EasternArtBBaseInfo easternArtBBaseInfo = this.mClockInfo;
        if (easternArtBBaseInfo == null) {
            return;
        }
        ClockEffectUtils.clearClockEffectsContainer(this, easternArtBBaseInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mHour1, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mHour2, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mMinute1, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mMinute2, this.mClockInfo, z);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i == 1) {
            return this.mHour1;
        }
        if (i == 2) {
            return this.mHour2;
        }
        if (i != 3) {
            return i != 4 ? super.getIClockView(clockViewType) : this.mMinute2;
        }
        return this.mMinute1;
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getMagazineColor() {
        EasternArtBBaseInfo easternArtBBaseInfo = this.mClockInfo;
        if (easternArtBBaseInfo != null) {
            return ColorUtils.blendColor(easternArtBBaseInfo.getOriginMagazineColor(), 0.6f);
        }
        return -1;
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockBottom() {
        return this.mMagazineInfoVisible ? getDimen(R.dimen.miui_eastern_art_b_vertical_magazine_notification_margin_top) : getDimen(R.dimen.miui_eastern_art_b_vertical_notification_margin_top);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mHour1 = (TextView) findViewById(R.id.vertical_time_below_view_hour1);
        this.mHour2 = (TextView) findViewById(R.id.vertical_time_below_view_hour2);
        this.mMinute1 = (TextView) findViewById(R.id.vertical_time_below_view_minute1);
        this.mMinute2 = (TextView) findViewById(R.id.vertical_time_below_view_minute2);
        this.mBackgroundBlurContainer = (TextView) findViewById(R.id.eastern_art_b_vertical_blur_container);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiBaseClock2
    public void onLanguageChanged() {
        super.onLanguageChanged();
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        super.setClockPalette(i, z, map, z2);
        updateClockBlendColor();
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        clearColorEffect(false);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2, int i3, int i4) {
        if (this.mClockInfo == null) {
            return;
        }
        updateClockEffectsContainer();
        TextView textView = this.mHour1;
        EasternArtBBaseInfo easternArtBBaseInfo = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView, easternArtBBaseInfo, this.mTextDark, i, easternArtBBaseInfo.getPrimaryColor(), i3, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()));
        TextView textView2 = this.mHour2;
        EasternArtBBaseInfo easternArtBBaseInfo2 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView2, easternArtBBaseInfo2, this.mTextDark, i, easternArtBBaseInfo2.getPrimaryColor(), i3, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()));
        TextView textView3 = this.mMinute1;
        EasternArtBBaseInfo easternArtBBaseInfo3 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView3, easternArtBBaseInfo3, this.mTextDark, i, easternArtBBaseInfo3.getPrimaryColor(), i3, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()));
        TextView textView4 = this.mMinute2;
        EasternArtBBaseInfo easternArtBBaseInfo4 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView4, easternArtBBaseInfo4, this.mTextDark, i, easternArtBBaseInfo4.getPrimaryColor(), i3, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()));
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockColor(int i, int i2) {
        updatePrimaryColor(i);
        updateSecondaryColor(i2);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockEffectsContainer() {
        super.updateClockEffectsContainer();
        updateViewBackgroundBlurContainer(this.mBackgroundBlurContainer, getDimen(R.dimen.miui_eastern_art_c_clock_blur_radius), this.mHour1, this.mHour2, this.mMinute1, this.mMinute2);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        updateClockColor(this.mClockInfo.getPrimaryColor(), this.mClockInfo.getSecondaryColor());
    }

    public void updatePrimaryColor(int i) {
        Log.d(TAG, "primaryColor: " + i);
        this.mHour1.setTextColor(i);
        this.mHour2.setTextColor(i);
    }

    public void updateSecondaryColor(int i) {
        Log.d(TAG, "updateSecondaryColor: " + i);
        this.mMinute1.setTextColor(i);
        this.mMinute2.setTextColor(i);
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        String timeString = ClassicClockTimeUtils.getTimeString(getHourInt(this.m24HourFormat, this.mCalendar), true);
        this.mHour1.setText(Character.toString(timeString.charAt(0)));
        this.mHour2.setText(Character.toString(timeString.charAt(1)));
        String timeString2 = ClassicClockTimeUtils.getTimeString(getMinuteInt(this.m24HourFormat, this.mCalendar), true);
        this.mMinute1.setText(Character.toString(timeString2.charAt(0)));
        this.mMinute2.setText(Character.toString(timeString2.charAt(1)));
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mHour1.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mHour2.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mMinute1.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.mMinute2.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_eastern_art_b_vertical_below_hour1_margin_top);
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimen(R.dimen.miui_eastern_art_b_vertical_below_hour2_margin_top);
        ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin = getDimen(R.dimen.miui_eastern_art_b_vertical_below_minute1_margin_top);
        ((ViewGroup.MarginLayoutParams) layoutParams4).topMargin = getDimen(R.dimen.miui_eastern_art_b_vertical_below_minute2_margin_top);
        Typeface chronicleForEasternArtB = FontUtils.getChronicleForEasternArtB(this.mContext);
        TextView textView = this.mHour1;
        int i = R.dimen.miui_eastern_art_b_vertical_below_time_text_size;
        textView.setTextSize(0, getDimen(i));
        this.mHour1.setTypeface(chronicleForEasternArtB);
        this.mHour2.setTextSize(0, getDimen(i));
        this.mHour2.setTypeface(chronicleForEasternArtB);
        this.mMinute1.setTextSize(0, getDimen(i));
        this.mMinute1.setTypeface(chronicleForEasternArtB);
        this.mMinute2.setTextSize(0, getDimen(i));
        this.mMinute2.setTypeface(chronicleForEasternArtB);
        updateColor();
    }
}
