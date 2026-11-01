package com.miui.clock.eastern.b;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.MiuiClockPreviewBaseView;
import com.miui.clock.R;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.FontUtils;
import java.util.Locale;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class EasternArtBVerticalPreviewView extends MiuiClockPreviewBaseView {
    public ViewGroup mClockContainer;
    public String mCountry;
    public TextView mDate;
    public TextView mDateLunar;
    public TextView mHour1;
    public TextView mHour2;
    public String mLanguage;
    public TextView mMinute1;
    public TextView mMinute2;
    public TextView mWeek;
    public TextView mWeek2;
    public TextView mYear;

    public EasternArtBVerticalPreviewView(@NonNull Context context) {
        super(context);
    }

    public EasternArtBVerticalPreviewView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public EasternArtBVerticalPreviewView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private int getHourInt(boolean z, Calendar calendar) {
        if (z) {
            return calendar.get(18);
        }
        int i = calendar.get(18);
        if (i == 0) {
            return 12;
        }
        return i < 13 ? i : i - 12;
    }

    private int getMinuteInt(boolean z, Calendar calendar) {
        return calendar.get(20);
    }

    private boolean isChineseLanguageOrCountry() {
        if ("zh".equals(this.mLanguage)) {
            return "CN".equals(this.mCountry) || "HK".equals(this.mCountry) || "TW".equals(this.mCountry);
        }
        return false;
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public int getDimen(int i) {
        return (int) (this.mContext.getResources().getDimensionPixelSize(i) * getScaleRadio());
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public float getScaleRadio() {
        return 0.5f;
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void init() {
        super.init();
        View viewInflate = View.inflate(this.mContext, R.layout.miui_clock_layout_eastern_art_b_vertical_preview_view, this);
        this.mHour1 = (TextView) viewInflate.findViewById(R.id.vertical_time_below_view_hour1);
        this.mHour2 = (TextView) viewInflate.findViewById(R.id.vertical_time_below_view_hour2);
        this.mMinute1 = (TextView) viewInflate.findViewById(R.id.vertical_time_below_view_minute1);
        this.mMinute2 = (TextView) viewInflate.findViewById(R.id.vertical_time_below_view_minute2);
        this.mWeek = (TextView) viewInflate.findViewById(R.id.vertical_data_upper_view_week_zh);
        this.mWeek2 = (TextView) viewInflate.findViewById(R.id.vertical_data_upper_view_week_rotate);
        this.mDate = (TextView) viewInflate.findViewById(R.id.vertical_data_upper_view_date);
        this.mDateLunar = (TextView) viewInflate.findViewById(R.id.vertical_data_upper_view_date_lunar);
        this.mYear = (TextView) viewInflate.findViewById(R.id.vertical_data_upper_view_year);
        this.mClockContainer = (ViewGroup) viewInflate.findViewById(R.id.clock_container);
        this.mLanguage = Locale.getDefault().getLanguage();
        this.mCountry = Locale.getDefault().getCountry();
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void setClockDarkMode(boolean z) {
        Resources resources;
        int i;
        int color = z ? getResources().getColor(R.color.miui_clock_preview_dark_mode_color_90) : -16777216;
        if (z) {
            resources = getResources();
            i = R.color.miui_clock_preview_light_mode_color_40;
        } else {
            resources = getResources();
            i = R.color.miui_clock_preview_light_mode_color_60;
        }
        updateColor(color, resources.getColor(i));
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void setStyle(int i) {
        super.setStyle(i);
        this.mStyle = i;
        updateViewLayoutParams();
    }

    public void updateColor(int i, int i2) {
        this.mHour1.setTextColor(i);
        this.mHour2.setTextColor(i);
        this.mMinute1.setTextColor(i2);
        this.mMinute2.setTextColor(i2);
        this.mWeek.setTextColor(i);
        this.mWeek2.setTextColor(i);
        this.mDate.setTextColor(i);
        this.mDateLunar.setTextColor(i);
        this.mYear.setTextColor(i);
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void updateTime() {
        super.updateTime();
        String timeString = ClassicClockTimeUtils.getTimeString(getHourInt(this.m24HourFormat, this.mCalendar), true);
        this.mHour1.setText(Character.toString(timeString.charAt(0)));
        this.mHour2.setText(Character.toString(timeString.charAt(1)));
        String timeString2 = ClassicClockTimeUtils.getTimeString(getMinuteInt(this.m24HourFormat, this.mCalendar), true);
        this.mMinute1.setText(Character.toString(timeString2.charAt(0)));
        this.mMinute2.setText(Character.toString(timeString2.charAt(1)));
        this.mWeek.setText(this.mCalendar.format(getContext(), getResources().getString(R.string.miui_eastern_art_b_week_view_format)));
        this.mWeek2.setText(this.mCalendar.format(getContext(), getResources().getString(R.string.format_full_day_of_week)));
        this.mDate.setText(String.format("(%s)", this.mCalendar.format(getContext(), getResources().getString(R.string.miui_smart_frame_clock_date))));
        this.mDateLunar.setText(ClassicClockTimeUtils.getLunarCalendarString(getContext(), this.mCalendar));
        this.mYear.setText(this.mCalendar.format(getContext(), getResources().getString(R.string.miui_eastern_art_b_data2_view_format)));
        updateViewLayoutParams();
    }

    public void updateViewLayoutParams() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mHour1.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mHour2.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mMinute1.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.mMinute2.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_eastern_art_b_vertical_below_hour1_margin_top);
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimen(R.dimen.miui_eastern_art_b_vertical_below_hour2_margin_top);
        ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin = getDimen(R.dimen.miui_eastern_art_b_vertical_below_minute1_margin_top);
        ((ViewGroup.MarginLayoutParams) layoutParams4).topMargin = getDimen(R.dimen.miui_eastern_art_b_vertical_below_minute2_margin_top);
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
        ViewGroup viewGroup = this.mClockContainer;
        if (viewGroup != null) {
            ConstraintLayout.LayoutParams layoutParams5 = (ConstraintLayout.LayoutParams) viewGroup.getLayoutParams();
            LinearLayout.LayoutParams layoutParams6 = (LinearLayout.LayoutParams) this.mWeek.getLayoutParams();
            LinearLayout.LayoutParams layoutParams7 = (LinearLayout.LayoutParams) this.mWeek2.getLayoutParams();
            LinearLayout.LayoutParams layoutParams8 = (LinearLayout.LayoutParams) this.mDateLunar.getLayoutParams();
            LinearLayout.LayoutParams layoutParams9 = (LinearLayout.LayoutParams) this.mYear.getLayoutParams();
            ((ViewGroup.MarginLayoutParams) layoutParams5).topMargin = getDimen(R.dimen.miui_eastern_art_b_vertical_upper_data_week_margin_top);
            int i2 = R.dimen.miui_eastern_art_b_vertical_upper_week_width;
            layoutParams6.width = getDimen(i2);
            layoutParams7.width = getDimen(i2);
            layoutParams8.width = getDimen(R.dimen.miui_eastern_art_b_vertical_upper_date_lunar_width);
            layoutParams9.width = getDimen(R.dimen.miui_eastern_art_b_vertical_upper_year_width);
            this.mClockContainer.setLayoutParams(layoutParams5);
            if (isChineseLanguageOrCountry()) {
                this.mWeek.setVisibility(0);
                this.mDateLunar.setVisibility(0);
                this.mWeek2.setVisibility(8);
                this.mYear.setVisibility(8);
            } else {
                this.mWeek.setVisibility(4);
                this.mWeek2.setVisibility(0);
                this.mDateLunar.setVisibility(8);
                this.mYear.setVisibility(0);
            }
            TextView textView2 = this.mWeek;
            int i3 = R.dimen.miui_eastern_art_b_vertical_upper_text_size;
            textView2.setTextSize(0, getDimen(i3));
            TextView textView3 = this.mWeek;
            int i4 = R.dimen.miui_eastern_art_b_vertical_upper_text_line_height;
            textView3.setLineHeight(getDimen(i4));
            this.mWeek2.setTextSize(0, getDimen(i3));
            this.mWeek2.setLineHeight(getDimen(i4));
            this.mDate.setTextSize(0, getDimen(R.dimen.miui_eastern_art_b_vertical_upper_date_text_size));
            this.mDate.setLineHeight(getDimen(R.dimen.miui_eastern_art_b_vertical_upper_date_text_line_height));
            this.mDateLunar.setTextSize(0, getDimen(i3));
            this.mDateLunar.setLineHeight(getDimen(i4));
            this.mYear.setTextSize(0, getDimen(i3));
            this.mYear.setLineHeight(getDimen(i4));
            this.mWeek.setTypeface(miSerifForEasternArtB);
            this.mWeek2.setTypeface(miSerifForEasternArtB);
            this.mYear.setTypeface(miSerifForEasternArtB);
            this.mDate.setTypeface(chronicleForEasternArtB);
            this.mDateLunar.setTypeface(miSerifForEasternArtB);
        }
    }
}
