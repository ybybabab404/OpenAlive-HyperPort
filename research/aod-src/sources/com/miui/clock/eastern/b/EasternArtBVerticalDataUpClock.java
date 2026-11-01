package com.miui.clock.eastern.b;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
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
public class EasternArtBVerticalDataUpClock extends EasternArtBBase {
    public ViewGroup mClockContainer;
    public TextView mDate;
    public TextView mDateLunar;
    public TextView mWeek;
    public TextView mWeek2;
    public TextView mYear;

    /* JADX INFO: renamed from: com.miui.clock.eastern.b.EasternArtBVerticalDataUpClock$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.FULL_WEEK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_YEAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_DATE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public EasternArtBVerticalDataUpClock(Context context) {
        super(context);
    }

    public EasternArtBVerticalDataUpClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public EasternArtBVerticalDataUpClock(Context context, AttributeSet attributeSet, int i) {
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
        ClockEffectUtils.clearClockEffectsView(this.mWeek, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mYear, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mDate, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mWeek2, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mDateLunar, this.mClockInfo, z);
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
            return this.mWeek;
        }
        if (i != 2) {
            return i != 3 ? super.getIClockView(clockViewType) : this.mDate;
        }
        return this.mYear;
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
        this.mWeek = (TextView) findViewById(R.id.vertical_data_upper_view_week_zh);
        this.mWeek2 = (TextView) findViewById(R.id.vertical_data_upper_view_week_rotate);
        this.mDate = (TextView) findViewById(R.id.vertical_data_upper_view_date);
        this.mDateLunar = (TextView) findViewById(R.id.vertical_data_upper_view_date_lunar);
        this.mYear = (TextView) findViewById(R.id.vertical_data_upper_view_year);
        this.mClockContainer = (ViewGroup) findViewById(R.id.clock_container);
        updateViewsLayoutParams();
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
        updateViewsLayoutParams();
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
        boolean zIsAODType = ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType());
        boolean zIsFullAODType = ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType());
        updateViewBackgroundBlurContainer(this, getDimen(R.dimen.miui_eastern_art_b_horizontal_blur_radius), this.mWeek, this.mWeek2, this.mYear, this.mDate, this.mDateLunar);
        TextView textView = this.mWeek;
        EasternArtBBaseInfo easternArtBBaseInfo = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView, easternArtBBaseInfo, this.mTextDark, i, easternArtBBaseInfo.getPrimaryColor(), i3, zIsAODType, zIsFullAODType);
        TextView textView2 = this.mWeek2;
        EasternArtBBaseInfo easternArtBBaseInfo2 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView2, easternArtBBaseInfo2, this.mTextDark, i, easternArtBBaseInfo2.getPrimaryColor(), i3, zIsAODType, zIsFullAODType);
        TextView textView3 = this.mYear;
        EasternArtBBaseInfo easternArtBBaseInfo3 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView3, easternArtBBaseInfo3, this.mTextDark, i, easternArtBBaseInfo3.getPrimaryColor(), i3, zIsAODType, zIsFullAODType);
        TextView textView4 = this.mDate;
        EasternArtBBaseInfo easternArtBBaseInfo4 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView4, easternArtBBaseInfo4, this.mTextDark, i2, easternArtBBaseInfo4.getSecondaryColor(), i4, zIsAODType, zIsFullAODType);
        TextView textView5 = this.mDateLunar;
        EasternArtBBaseInfo easternArtBBaseInfo5 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView5, easternArtBBaseInfo5, this.mTextDark, i2, easternArtBBaseInfo5.getSecondaryColor(), i4, zIsAODType, zIsFullAODType);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockColor(int i, int i2) {
        updatePrimaryColor(i);
        updateSecondaryColor(i2);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        updateClockColor(this.mClockInfo.getPrimaryColor(), this.mClockInfo.getSecondaryColor());
    }

    public void updatePrimaryColor(int i) {
        this.mWeek.setTextColor(i);
        this.mYear.setTextColor(i);
        this.mDate.setTextColor(i);
        this.mWeek2.setTextColor(i);
        this.mDateLunar.setTextColor(i);
    }

    public void updateSecondaryColor(int i) {
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        this.mWeek.setText(this.mCalendar.format(getContext(), getResources().getString(R.string.miui_eastern_art_b_week_view_format)));
        this.mWeek2.setText(this.mCalendar.format(getContext(), getResources().getString(R.string.format_full_day_of_week)));
        this.mDate.setText(String.format("(%s)", this.mCalendar.format(getContext(), getResources().getString(R.string.miui_smart_frame_clock_date))));
        this.mDateLunar.setText(ClassicClockTimeUtils.getLunarCalendarString(getContext(), this.mCalendar));
        this.mYear.setText(this.mCalendar.format(getContext(), getResources().getString(R.string.miui_eastern_art_b_data2_view_format)));
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        ViewGroup viewGroup = this.mClockContainer;
        if (viewGroup != null) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) viewGroup.getLayoutParams();
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_eastern_art_b_vertical_upper_data_week_margin_top);
            this.mClockContainer.setLayoutParams(layoutParams);
            Typeface chronicleForEasternArtB = FontUtils.getChronicleForEasternArtB(this.mContext);
            Typeface miSerifForEasternArtB = FontUtils.getMiSerifForEasternArtB(this.mContext);
            if (isChineseLanguageOrCountry()) {
                this.mDateLunar.setVisibility(0);
                this.mYear.setVisibility(8);
            } else {
                if (isEnglishLanguage()) {
                    this.mWeek.setVisibility(8);
                    this.mWeek2.setVisibility(0);
                } else {
                    this.mWeek2.setVisibility(8);
                    this.mWeek.setVisibility(0);
                }
                this.mDateLunar.setVisibility(8);
                this.mYear.setVisibility(0);
            }
            TextView textView = this.mWeek;
            int i = R.dimen.miui_eastern_art_b_vertical_upper_text_size;
            textView.setTextSize(0, getDimen(i));
            TextView textView2 = this.mWeek;
            int i2 = R.dimen.miui_eastern_art_b_vertical_upper_text_line_height;
            textView2.setLineHeight(getDimen(i2));
            this.mWeek2.setTextSize(0, getDimen(i));
            this.mWeek2.setLineHeight(getDimen(i2));
            this.mDate.setTextSize(0, getDimen(R.dimen.miui_eastern_art_b_vertical_upper_date_text_size));
            this.mDate.setLineHeight(getDimen(R.dimen.miui_eastern_art_b_vertical_upper_date_text_line_height));
            this.mDateLunar.setTextSize(0, getDimen(i));
            this.mDateLunar.setLineHeight(getDimen(i2));
            this.mYear.setTextSize(0, getDimen(i));
            this.mYear.setLineHeight(getDimen(i2));
            this.mWeek.setTypeface(miSerifForEasternArtB);
            this.mWeek2.setTypeface(miSerifForEasternArtB);
            this.mYear.setTypeface(miSerifForEasternArtB);
            this.mDate.setTypeface(chronicleForEasternArtB);
            this.mDateLunar.setTypeface(miSerifForEasternArtB);
        }
    }
}
