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
import com.miui.clock.utils.FontUtils;

/* JADX INFO: loaded from: classes.dex */
public class EasternArtBVerticalAodClock extends EasternArtBBase {
    public TextView mDateLunar;
    public TextView mDateWeekView;
    public TextView mHour1;
    public TextView mHour2;
    public TextView mMinute1;
    public TextView mMinute2;

    /* JADX INFO: renamed from: com.miui.clock.eastern.b.EasternArtBVerticalAodClock$1, reason: invalid class name */
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
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_DATE_WEEK.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_DATE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public EasternArtBVerticalAodClock(Context context) {
        super(context);
    }

    public EasternArtBVerticalAodClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
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
        switch (AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()]) {
            case 1:
                return this.mHour1;
            case 2:
                return this.mHour2;
            case 3:
                return this.mMinute1;
            case 4:
                return this.mMinute2;
            case 5:
                return this.mDateWeekView;
            case 6:
                return this.mDateLunar;
            default:
                return super.getIClockView(clockViewType);
        }
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getMagazineColor() {
        return super.getMagazineColor();
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
        this.mDateWeekView = (TextView) findViewById(R.id.date_and_week);
        this.mDateLunar = (TextView) findViewById(R.id.vertical_data_upper_view_date_lunar);
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

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        this.mHour1.setTextColor(this.mClockInfo.getAodPrimaryColor());
        this.mHour2.setTextColor(this.mClockInfo.getAodPrimaryColor());
        this.mMinute1.setTextColor(this.mClockInfo.getAodPrimaryColor());
        this.mMinute2.setTextColor(this.mClockInfo.getAodPrimaryColor());
        this.mDateWeekView.setTextColor(this.mClockInfo.getAodSecondaryColor());
        this.mDateLunar.setTextColor(this.mClockInfo.getAodSecondaryColor());
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
        if (isChineseLanguageOrCountry()) {
            String str = this.mCalendar.format(getContext(), getResources().getString(R.string.miui_eastern_art_b_week_view_format));
            this.mDateWeekView.setText(getResources().getString(R.string.format_lunar_calendar_solar_terms, this.mCalendar.format(getContext(), getResources().getString(R.string.miui_eastern_art_b_data_view_format)), str));
        } else {
            this.mDateWeekView.setText(getResources().getString(R.string.format_lunar_calendar_solar_terms, this.mCalendar.format(getContext(), getResources().getString(R.string.miui_smart_frame_clock_date)), this.mCalendar.format(getContext(), getResources().getString(R.string.format_full_day_of_week))));
        }
        this.mDateLunar.setText(ClassicClockTimeUtils.getLunarCalendarString(getContext(), this.mCalendar));
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mHour1.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mHour2.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mMinute1.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.mMinute2.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams5 = (ConstraintLayout.LayoutParams) this.mDateWeekView.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams6 = (ConstraintLayout.LayoutParams) this.mDateLunar.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_eastern_art_b_vertical_below_hour1_margin_top);
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimen(R.dimen.miui_eastern_art_b_vertical_below_hour2_margin_top);
        ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin = getDimen(R.dimen.miui_eastern_art_b_vertical_below_minute1_margin_top);
        ((ViewGroup.MarginLayoutParams) layoutParams4).topMargin = getDimen(R.dimen.miui_eastern_art_b_vertical_below_minute2_margin_top);
        ((ViewGroup.MarginLayoutParams) layoutParams5).topMargin = getDimen(R.dimen.miui_eastern_art_b_vertical_aod_date_and_week_margin_top);
        ((ViewGroup.MarginLayoutParams) layoutParams6).topMargin = getDimen(R.dimen.miui_eastern_art_b_vertical_aod_date_lunar_margin_top);
        Typeface chronicleForEasternArtB = FontUtils.getChronicleForEasternArtB(this.mContext);
        Typeface miSerifForEasternArtB = FontUtils.getMiSerifForEasternArtB(this.mContext);
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
        this.mDateWeekView.setTypeface(miSerifForEasternArtB);
        TextView textView2 = this.mDateWeekView;
        int i2 = R.dimen.miui_eastern_art_b_vertical_notification_date_text_size;
        textView2.setTextSize(0, getDimen(i2));
        this.mDateLunar.setTextSize(0, getDimen(i2));
        this.mDateLunar.setTypeface(miSerifForEasternArtB);
        updateColor();
    }
}
