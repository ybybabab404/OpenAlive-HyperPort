package com.miui.clock.eastern.c;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
public class MiuiEasternArtCPreviewView extends MiuiClockPreviewBaseView {
    public ViewGroup mClockPreview;
    public TextView mDateTextStyle;
    public boolean mIsOnHour;
    public int mStyle;
    public TextView mTimeHour1;
    public TextView mTimeHour2;
    public TextView mTimeMinute1;
    public TextView mTimeMinute2;
    public TextView mWeekTextStyle;

    public MiuiEasternArtCPreviewView(@NonNull Context context) {
        super(context);
        this.mIsOnHour = true;
    }

    public MiuiEasternArtCPreviewView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsOnHour = true;
    }

    public MiuiEasternArtCPreviewView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsOnHour = true;
    }

    private int getDateTextSize() {
        return getDimenWithScale(R.dimen.miui_eastern_art_preview_time_week_text_size);
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public float getScaleRadio() {
        return 1.0f;
    }

    public int getTimeTextSize(int i) {
        if (i != 2) {
            return getDimenWithScale(this.mIsOnHour ? R.dimen.miui_eastern_art_preview_time_big_text_size : R.dimen.miui_eastern_art_preview_time_small_text_size);
        }
        return getDimenWithScale(this.mIsOnHour ? R.dimen.miui_eastern_art_preview_cursive_time_big_text_size : R.dimen.miui_eastern_art_preview_cursive_time_small_text_size);
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void init() {
        super.init();
        View viewInflate = View.inflate(this.mContext, R.layout.miui_clock_layout_eastern_art_c_preview, this);
        this.mTimeHour1 = (TextView) viewInflate.findViewById(R.id.time_hour_1);
        this.mTimeHour2 = (TextView) viewInflate.findViewById(R.id.time_hour_2);
        this.mWeekTextStyle = (TextView) viewInflate.findViewById(R.id.current_week);
        this.mDateTextStyle = (TextView) viewInflate.findViewById(R.id.date_and_time);
        this.mTimeMinute1 = (TextView) viewInflate.findViewById(R.id.time_minute_1);
        this.mTimeMinute2 = (TextView) viewInflate.findViewById(R.id.time_minute_2);
        this.mClockPreview = (ViewGroup) viewInflate.findViewById(R.id.eastern_art_c_preview);
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void setClockDarkMode(boolean z) {
        if (z) {
            TextView textView = this.mTimeHour1;
            Resources resources = getResources();
            int i = R.color.miui_clock_preview_dark_mode_color_90;
            textView.setTextColor(resources.getColor(i));
            this.mTimeHour2.setTextColor(getResources().getColor(i));
            TextView textView2 = this.mWeekTextStyle;
            Resources resources2 = getResources();
            int i2 = R.color.miui_clock_preview_light_mode_color_40;
            textView2.setTextColor(resources2.getColor(i2));
            this.mDateTextStyle.setTextColor(getResources().getColor(i2));
            this.mTimeMinute1.setTextColor(getResources().getColor(i));
            this.mTimeMinute2.setTextColor(getResources().getColor(i));
            return;
        }
        TextView textView3 = this.mTimeHour1;
        Resources resources3 = getResources();
        int i3 = R.color.miui_clock_preview_light_mode_color;
        textView3.setTextColor(resources3.getColor(i3));
        this.mTimeHour2.setTextColor(getResources().getColor(i3));
        TextView textView4 = this.mWeekTextStyle;
        Resources resources4 = getResources();
        int i4 = R.color.miui_clock_preview_light_mode_color_60;
        textView4.setTextColor(resources4.getColor(i4));
        this.mDateTextStyle.setTextColor(getResources().getColor(i4));
        this.mTimeMinute1.setTextColor(getResources().getColor(i3));
        this.mTimeMinute2.setTextColor(getResources().getColor(i3));
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void setStyle(int i) {
        super.setStyle(i);
        this.mStyle = i;
        setTextTypeFace(this.mTimeHour1, i);
        setTextTypeFace(this.mTimeHour2, this.mStyle);
        setTextTypeFace(this.mTimeMinute1, this.mStyle);
        setTextTypeFace(this.mTimeMinute2, this.mStyle);
        updateViewLayoutParams();
    }

    public void setTextTypeFace(TextView textView, int i) {
        Typeface miSerifTcVfByWeight;
        if (i == 1) {
            miSerifTcVfByWeight = FontUtils.getMiSerifTcVfByWeight(400);
        } else if (i != 2) {
            miSerifTcVfByWeight = i != 3 ? FontUtils.getOtherFont("miclock-miserif-tc-vf") : FontUtils.getMiSans(330);
        } else {
            miSerifTcVfByWeight = FontUtils.getCursiveRegular(getContext());
        }
        textView.setTypeface(miSerifTcVfByWeight);
    }

    public final void updateHourClockStyle(boolean z, int i, int i2) {
        int timeTextSize = getTimeTextSize(this.mStyle);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mTimeHour2.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimenWithScale(i2);
        this.mTimeHour2.setLayoutParams(layoutParams);
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mTimeHour1.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimenWithScale(i);
        this.mTimeHour1.setLayoutParams(layoutParams2);
        float f = timeTextSize;
        this.mTimeHour2.setTextSize(0, f);
        this.mTimeHour1.setTextSize(0, f);
        this.mTimeHour1.setVisibility(z ? 8 : 0);
    }

    public final void updateMinuteClockStyle(boolean z, int i, int i2) {
        float timeTextSize = getTimeTextSize(this.mStyle);
        this.mTimeMinute1.setTextSize(0, timeTextSize);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mTimeMinute1.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimenWithScale(i);
        this.mTimeMinute1.setLayoutParams(layoutParams);
        this.mTimeMinute2.setVisibility(z ? 8 : 0);
        this.mTimeMinute2.setTextSize(0, timeTextSize);
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mTimeMinute2.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimenWithScale(i2);
        this.mTimeMinute2.setLayoutParams(layoutParams2);
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void updateTime() {
        String str;
        String string;
        super.updateTime();
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        boolean z = this.mCalendar.get(20) == 0;
        this.mIsOnHour = z;
        String chsTimeHour = ClassicClockTimeUtils.getChsTimeHour(this.m24HourFormat, z, true, this.mCalendar);
        this.mTimeHour1.setText(chsTimeHour);
        String chsTimeHour2 = ClassicClockTimeUtils.getChsTimeHour(this.m24HourFormat, this.mIsOnHour, false, this.mCalendar);
        this.mTimeHour2.setText(chsTimeHour2);
        String chsTimeMinute = ClassicClockTimeUtils.getChsTimeMinute(this.m24HourFormat, true, this.mCalendar);
        String chsTimeMinute2 = ClassicClockTimeUtils.getChsTimeMinute(this.m24HourFormat, false, this.mCalendar);
        this.mTimeMinute1.setText(chsTimeMinute);
        this.mTimeMinute2.setText(chsTimeMinute2);
        String country = Locale.getDefault().getCountry();
        if (Locale.getDefault().getLanguage().equals("zh") && (country.equals("CN") || country.equals("HK") || country.equals("TW"))) {
            Calendar calendar = this.mCalendar;
            Context context = this.mContext;
            str = calendar.format(context, context.getString(R.string.miui_lock_eastern_art_week_am));
            string = this.mContext.getString(this.m24HourFormat ? R.string.miui_lock_eastern_art_date_time_24 : R.string.miui_lock_eastern_art_date_time_12);
        } else {
            Calendar calendar2 = this.mCalendar;
            Context context2 = this.mContext;
            str = calendar2.format(context2, context2.getString(R.string.miui_rhombus_clock_week_full));
            string = this.mContext.getString(this.m24HourFormat ? R.string.miui_lock_eastern_art_date_curisive_24 : R.string.miui_lock_eastern_art_date_curisive_12);
        }
        String str2 = this.mCalendar.format(this.mContext, string);
        this.mWeekTextStyle.setText(str);
        this.mDateTextStyle.setText(str2);
        this.mClockPreview.setContentDescription(chsTimeHour + " " + chsTimeHour2 + " " + str + " " + str2 + " " + chsTimeMinute + " " + chsTimeMinute2);
        updateViewLayoutParams();
    }

    public void updateViewLayoutParams() {
        int dateTextSize = getDateTextSize();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mClockPreview.getLayoutParams();
        layoutParams.height = getDimen(R.dimen.miui_eastern_art_preview_height);
        this.mClockPreview.setLayoutParams(layoutParams);
        float f = dateTextSize;
        this.mWeekTextStyle.setTextSize(0, f);
        this.mDateTextStyle.setTextSize(0, f);
        updateWeekAndDateClockStyle(this.mIsOnHour);
        if (this.mStyle != 2) {
            boolean z = this.mIsOnHour;
            updateHourClockStyle(z, R.dimen.miui_eastern_art_preview_time_hour1_margin_top, z ? R.dimen.miui_eastern_art_preview_time_hour2_big_margin_top : R.dimen.miui_eastern_art_preview_time_hour2_small_margin_top);
            boolean z2 = this.mIsOnHour;
            updateMinuteClockStyle(z2, z2 ? R.dimen.miui_eastern_art_preview_time_minute1_big_margin_top : R.dimen.miui_eastern_art_preview_time_minute1_small_margin_top, R.dimen.miui_eastern_art_preview_time_minute2_margin_top);
            updateWeekAndDateClockStyle(this.mIsOnHour);
            return;
        }
        boolean z3 = this.mIsOnHour;
        updateHourClockStyle(z3, R.dimen.miui_eastern_art_preview_cursive_time_hour1_margin_top, z3 ? R.dimen.miui_eastern_art_preview_cursive_time_hour2_big_margin_top : R.dimen.miui_eastern_art_preview_cursive_time_hour2_small_margin_top);
        boolean z4 = this.mIsOnHour;
        updateMinuteClockStyle(z4, z4 ? R.dimen.miui_eastern_art_preview_cursive_time_minute1_big_margin_top : R.dimen.miui_eastern_art_preview_cursive_time_minute1_small_margin_top, R.dimen.miui_eastern_art_preview_cursive_time_minute2_margin_top);
        updateWeekAndDateClockStyle(this.mIsOnHour);
    }

    public final void updateWeekAndDateClockStyle(boolean z) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mWeekTextStyle.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimenWithScale(z ? R.dimen.miui_eastern_art_preview_time_week_big_margin_top : R.dimen.miui_eastern_art_preview_time_week_margin_top);
        this.mWeekTextStyle.setLayoutParams(layoutParams);
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mDateTextStyle.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimenWithScale(z ? R.dimen.miui_eastern_art_preview_time_date_big_margin_top : R.dimen.miui_eastern_art_preview_time_date_margin_top);
        this.mDateTextStyle.setLayoutParams(layoutParams2);
    }
}
