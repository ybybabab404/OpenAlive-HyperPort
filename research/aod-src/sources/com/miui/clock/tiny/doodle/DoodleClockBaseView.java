package com.miui.clock.tiny.doodle;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Space;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import com.miui.clock.tiny.R;
import com.miui.clock.tiny.model.TinyClockInfo;
import com.miui.clock.tiny.model.TinyClockViewType;
import com.miui.clock.tiny.utils.DateFormatUtils;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class DoodleClockBaseView extends ConstraintLayout {
    public boolean clockIsZH;
    public Space clockSpace;
    public ImageView dataDay;
    public ImageView dataMonth;
    public ImageView dayImage;
    public Guideline guidelineV50;
    public boolean m24HourFormat;
    public Calendar mCalendar;
    public DoodleClockInfoBase mInfo;
    public ConstraintLayout mMonthDesc;
    public ConstraintLayout mTimeDesc;
    public ConstraintLayout mWeekDesc;
    public boolean resourceStatus;
    public ImageView timeDot;
    public ImageView timeHour;
    public ImageView timeMinute;
    public ImageView weekToday;
    public ImageView weekTodayis;

    public DoodleClockBaseView(Context context) {
        super(context);
        this.resourceStatus = true;
    }

    public DoodleClockBaseView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.resourceStatus = true;
    }

    public DoodleClockBaseView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.resourceStatus = true;
    }

    private float getDesignScale() {
        return this.mInfo.getDesignWidthDp() / 372.0f;
    }

    private int getDimen(int i) {
        return (int) (((double) (getResources().getDimensionPixelSize(i) * getDesignScale())) + 0.5d);
    }

    private void initColor() {
        DoodleClockInfoBase doodleClockInfoBase = this.mInfo;
        DoodleStyle.DoodleClockViewColorStyle clockColorStyleInfo = doodleClockInfoBase.getClockColorStyleInfo(doodleClockInfoBase.getRotation());
        this.mInfo.setAodPrimaryColor(clockColorStyleInfo.getAodPrimaryColor());
        this.mInfo.setPrimaryColor(clockColorStyleInfo.getPrimaryColor());
        this.mInfo.setDayColor(clockColorStyleInfo.getDayColor());
        this.mInfo.setDayBottomColor(clockColorStyleInfo.getDayBottomColor());
        DoodleClockInfoBase doodleClockInfoBase2 = this.mInfo;
        doodleClockInfoBase2.setTodayColor(doodleClockInfoBase2.isAODType() ? clockColorStyleInfo.getAodTodayDay() : clockColorStyleInfo.getTodayColor());
        DoodleClockInfoBase doodleClockInfoBase3 = this.mInfo;
        doodleClockInfoBase3.setTodayDayBottom(doodleClockInfoBase3.isAODType() ? clockColorStyleInfo.getAodTodayDayBottom() : clockColorStyleInfo.getTodayDayBottom());
        setCurLanguage(clockColorStyleInfo.getLanguage());
    }

    private void initDate() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.dayImage.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.dataMonth.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.dataDay.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = 0;
        ((ViewGroup.MarginLayoutParams) layoutParams).height = 0;
        this.dayImage.setVisibility(this.clockIsZH ? 0 : 8);
        if (this.clockIsZH) {
            layoutParams.leftToLeft = this.dataDay.getId();
            layoutParams.topToTop = this.clockSpace.getId();
            ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.tiny_doodle_dayimg_width);
            ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.tiny_doodle_dayimg_height);
            layoutParams.setMargins(getDimen(R.dimen.tiny_doodle_dayimg_margin_left), getDimen(R.dimen.tiny_doodle_dayimg_margin_top), 0, 0);
            Drawable drawable = getResources().getDrawable(R.drawable.tiny_doodle_day_image_zh);
            drawable.setTint(this.mInfo.getPrimaryColor());
            this.dayImage.setImageDrawable(drawable);
        }
        layoutParams2.topToTop = this.clockSpace.getId();
        layoutParams2.leftToLeft = this.timeDot.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = getDimen(this.clockIsZH ? R.dimen.tiny_doodle_month_width_zh : R.dimen.tiny_doodle_month_width_en);
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = getDimen(this.clockIsZH ? R.dimen.tiny_doodle_month_height_zh : R.dimen.tiny_doodle_month_height_en);
        layoutParams2.setMargins(getDimen(this.clockIsZH ? R.dimen.tiny_doodle_month_margin_left_zh : R.dimen.tiny_doodle_month_margin_left_en), getDimen(this.clockIsZH ? R.dimen.tiny_doodle_month_margin_top_zh : R.dimen.tiny_doodle_month_margin_top_en), 0, 0);
        layoutParams3.topToTop = this.clockSpace.getId();
        layoutParams3.leftToLeft = this.dataMonth.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams3).width = getDimen(this.clockIsZH ? R.dimen.tiny_doodle_day_width_zh : R.dimen.tiny_doodle_day_width_en);
        ((ViewGroup.MarginLayoutParams) layoutParams3).height = getDimen(this.clockIsZH ? R.dimen.tiny_doodle_day_height_zh : R.dimen.tiny_doodle_day_height_en);
        layoutParams3.setMargins(getDimen(this.clockIsZH ? R.dimen.tiny_doodle_day_margin_left_zh : R.dimen.tiny_doodle_day_margin_left_en), getDimen(this.clockIsZH ? R.dimen.tiny_doodle_day_margin_top_zh : R.dimen.tiny_doodle_day_margin_top_en), 0, 0);
        this.dayImage.setLayoutParams(layoutParams);
        this.dataMonth.setLayoutParams(layoutParams2);
        this.dataDay.setLayoutParams(layoutParams3);
    }

    private void initTime() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.timeDot.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.timeHour.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.timeMinute.getLayoutParams();
        layoutParams.leftToLeft = this.clockSpace.getId();
        layoutParams.rightToRight = this.clockSpace.getId();
        layoutParams.topToTop = this.clockSpace.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.tiny_doodle_timedot_width);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.tiny_doodle_timedot_height);
        layoutParams.setMargins(0, getDimen(R.dimen.tiny_doodle_timedot_margin_top), 0, 0);
        layoutParams2.rightToRight = this.timeDot.getId();
        layoutParams2.topToTop = this.clockSpace.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = getDimen(R.dimen.tiny_doodle_timehour_width);
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = getDimen(R.dimen.tiny_doodle_timehour_height);
        layoutParams2.setMargins(0, getDimen(R.dimen.tiny_doodle_timehour_margin_top), getDimen(R.dimen.tiny_doodle_timehour_margin_right), 0);
        layoutParams3.leftToLeft = this.timeDot.getId();
        layoutParams3.topToTop = this.clockSpace.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams3).width = getDimen(R.dimen.tiny_doodle_timeminute_width);
        ((ViewGroup.MarginLayoutParams) layoutParams3).height = getDimen(R.dimen.tiny_doodle_timeminute_height);
        layoutParams3.setMargins(getDimen(R.dimen.tiny_doodle_timeminute_margin_left), getDimen(R.dimen.tiny_doodle_timeminute_margin_top), 0, 0);
        this.timeDot.setLayoutParams(layoutParams);
        this.timeHour.setLayoutParams(layoutParams2);
        this.timeMinute.setLayoutParams(layoutParams3);
    }

    private void initWeek() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.weekTodayis.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.weekToday.getLayoutParams();
        layoutParams.topToTop = this.clockSpace.getId();
        layoutParams.rightToRight = this.timeDot.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(this.clockIsZH ? R.dimen.tiny_doodle_weektodayis_width_zh : R.dimen.tiny_doodle_weektodayis_width_en);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(this.clockIsZH ? R.dimen.tiny_doodle_weektodayis_height_zh : R.dimen.tiny_doodle_weektodayis_height_en);
        layoutParams.setMargins(0, getDimen(this.clockIsZH ? R.dimen.tiny_doodle_weektodayis_margin_top_zh : R.dimen.tiny_doodle_weektodayis_margin_top_en), getDimen(this.clockIsZH ? R.dimen.tiny_doodle_weektodayis_margin_right_zh : R.dimen.tiny_doodle_weektodayis_margin_right_en), 0);
        layoutParams2.bottomToBottom = this.weekTodayis.getId();
        layoutParams2.rightToRight = this.timeDot.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = getDimen(this.clockIsZH ? R.dimen.tiny_doodle_weektoday_width_zh : R.dimen.tiny_doodle_weektoday_width_en);
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = getDimen(this.clockIsZH ? R.dimen.tiny_doodle_weektoday_height_zh : R.dimen.tiny_doodle_weektoday_height_en);
        layoutParams2.setMargins(0, getDimen(this.clockIsZH ? R.dimen.tiny_doodle_weektoday_margin_top_zh : R.dimen.tiny_doodle_weektoday_margin_top_en), getDimen(this.clockIsZH ? R.dimen.tiny_doodle_weektoday_margin_right_zh : R.dimen.tiny_doodle_weektoday_margin_right_en), 0);
        this.weekTodayis.setLayoutParams(layoutParams);
        this.weekToday.setLayoutParams(layoutParams2);
    }

    private void setClockStyle() {
        initColor();
        initWeek();
        initTime();
        initDate();
    }

    public Calendar getClockCalendar() {
        return this.mCalendar;
    }

    public View getIClockView(TinyClockViewType tinyClockViewType) {
        return this;
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mCalendar = new Calendar();
        this.guidelineV50 = (Guideline) findViewById(R.id.guideline);
        this.clockSpace = (Space) findViewById(R.id.clock_space);
        this.weekTodayis = (ImageView) findViewById(R.id.week_todayis);
        this.weekToday = (ImageView) findViewById(R.id.week_today);
        this.timeDot = (ImageView) findViewById(R.id.time_dot);
        this.timeHour = (ImageView) findViewById(R.id.time_hour);
        this.timeMinute = (ImageView) findViewById(R.id.time_minute);
        this.dataDay = (ImageView) findViewById(R.id.data_day);
        this.dataMonth = (ImageView) findViewById(R.id.data_month);
        this.dayImage = (ImageView) findViewById(R.id.data_image);
        this.mMonthDesc = (ConstraintLayout) findViewById(R.id.date_desc);
        this.mWeekDesc = (ConstraintLayout) findViewById(R.id.week_desc);
        this.mTimeDesc = (ConstraintLayout) findViewById(R.id.time_desc);
    }

    public void setClockStyleInfo(TinyClockInfo tinyClockInfo) {
        this.mInfo = (DoodleClockInfoBase) tinyClockInfo;
        Log.d("DoodleClockBaseView", "change doodle clock base view");
        setClockStyle();
        updateTime();
    }

    public void setCurLanguage(int i) {
        this.clockIsZH = i == 1;
    }

    public void updateTime() {
        this.m24HourFormat = DateFormatUtils.is24HourFormat(getContext());
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        if (!this.resourceStatus) {
            this.timeHour.setImageDrawable(null);
            this.timeMinute.setImageDrawable(null);
            return;
        }
        int i = this.mCalendar.get(5) + 1;
        int i2 = this.mCalendar.get(9);
        int i3 = this.mCalendar.get(14);
        int i4 = this.mCalendar.get(20);
        int i5 = this.mCalendar.get(18);
        if (!this.m24HourFormat && (i5 = i5 % 12) == 0) {
            i5 = 12;
        }
        int aodPrimaryColor = this.mInfo.isAODType() ? this.mInfo.getAodPrimaryColor() : this.mInfo.getPrimaryColor();
        int dayBottomColor = this.mInfo.getDayBottomColor();
        int todayDayBottom = this.mInfo.getTodayDayBottom();
        int dayColor = this.mInfo.getDayColor();
        int todayColor = this.mInfo.getTodayColor();
        Drawable drawable = getResources().getDrawable(this.clockIsZH ? DoodleStyle.mWeekResource_ZH[i3] : DoodleStyle.mWeekResource_EN[i3]);
        drawable.setTint(todayColor);
        this.weekToday.setImageDrawable(drawable);
        Drawable drawable2 = getResources().getDrawable(getDesignScale() < 0.5f ? DoodleStyle.mTimeHourResourceSmall[i5] : DoodleStyle.mTimeHourResource[i5]);
        drawable2.setTint(aodPrimaryColor);
        this.timeHour.setImageDrawable(drawable2);
        Drawable drawable3 = getResources().getDrawable(getDesignScale() < 0.5f ? DoodleStyle.mTimeMinuteResourceSmall[i4] : DoodleStyle.mTimeMinuteResource[i4]);
        drawable3.setTint(aodPrimaryColor);
        this.timeMinute.setImageDrawable(drawable3);
        Drawable drawable4 = getResources().getDrawable(this.clockIsZH ? DoodleStyle.mMonthResource_ZH[i] : DoodleStyle.mMonthResource_EN[i]);
        drawable4.setTint(aodPrimaryColor);
        this.dataMonth.setImageDrawable(drawable4);
        Drawable drawable5 = getResources().getDrawable(this.clockIsZH ? DoodleStyle.mDayResource_ZH[i2] : DoodleStyle.mDayResource_EN[i2]);
        Drawable drawable6 = getResources().getDrawable(R.drawable.tiny_doodle_day_bottom);
        drawable5.setTint(dayColor);
        drawable6.setTint(dayBottomColor);
        this.dataDay.setImageDrawable(new LayerDrawable(new Drawable[]{drawable6, drawable5}));
        Drawable drawable7 = getResources().getDrawable(R.drawable.tiny_doodle_dot);
        drawable7.setTint(aodPrimaryColor);
        this.timeDot.setImageDrawable(drawable7);
        Drawable drawable8 = getResources().getDrawable(this.clockIsZH ? R.drawable.tiny_doodle_week_bottom_zh : R.drawable.tiny_doodle_week_bottom_en);
        drawable8.setTint(todayDayBottom);
        this.weekTodayis.setImageDrawable(drawable8);
        if (this.clockIsZH) {
            Drawable drawable9 = getResources().getDrawable(R.drawable.tiny_doodle_day_image_zh);
            drawable9.setTint(aodPrimaryColor);
            this.dayImage.setImageDrawable(drawable9);
        }
        this.mMonthDesc.setContentDescription(new SimpleDateFormat(getResources().getString(R.string.tiny_doodle_clock_date), Locale.getDefault()).format(Long.valueOf(this.mCalendar.getTimeInMillis())));
        this.mTimeDesc.setContentDescription(DateUtils.formatDateTime(getContext(), System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
        this.mWeekDesc.setContentDescription(new SimpleDateFormat(getResources().getString(R.string.tiny_splicing_week_format)).format(Long.valueOf(this.mCalendar.getTimeInMillis())));
    }

    public void updateTimeZone(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mCalendar.setTimeZone(TimeZone.getTimeZone(str));
        updateTime();
    }
}
