package com.miui.clock.graffiti;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Space;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.Guideline;
import com.miui.clock.R;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.utils.ColorUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class MiuiGraffitiAodClockView extends ConstraintLayout {
    public boolean clockIsZH;
    public Space clockSpace;
    public ImageView dataDay;
    public ImageView dataImage;
    public ImageView dataMonth;
    public Guideline guidelineV50;
    public boolean hiddenDoodle;
    public boolean m24HourFormat;
    public Calendar mCalendar;
    public ConstraintLayout mDateDesc;
    public MiuiGraffitiClockInfoBase mInfo;
    public ConstraintLayout mTimeDesc;
    public boolean mWallpaperSupportDepth;
    public ConstraintLayout mWeekDesc;
    public ImageView timeDot;
    public ImageView timeHour;
    public ImageView timeMinute;
    public ImageView weekToday;
    public ImageView weekTodayis;

    public MiuiGraffitiAodClockView(Context context) {
        super(context);
    }

    public MiuiGraffitiAodClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MiuiGraffitiAodClockView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private int getDimen(int i) {
        return (int) (getResources().getDimensionPixelSize(i) * DeviceConfig.calculateScale(getContext()));
    }

    private void initDate() {
        Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(0, 0);
        Constraints.LayoutParams layoutParams2 = new Constraints.LayoutParams(0, 0);
        Constraints.LayoutParams layoutParams3 = new Constraints.LayoutParams(0, 0);
        this.dataImage.setVisibility(this.clockIsZH ? 0 : 8);
        if (this.clockIsZH) {
            layoutParams.leftToLeft = this.guidelineV50.getId();
            layoutParams.topToTop = this.clockSpace.getId();
            ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.miui_graffiti_aod_dateimg_width);
            ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.miui_graffiti_aod_dateimg_height);
            layoutParams.setMargins(getDimen(R.dimen.miui_graffiti_aod_dateimg_margin_left), getDimen(R.dimen.miui_graffiti_aod_dateimg_margin_top), 0, 0);
        }
        layoutParams2.topToTop = this.clockSpace.getId();
        layoutParams2.leftToLeft = this.guidelineV50.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = getDimen(this.clockIsZH ? R.dimen.miui_graffiti_aod_month_width_zh : R.dimen.miui_graffiti_aod_month_width_en);
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = getDimen(this.clockIsZH ? R.dimen.miui_graffiti_aod_month_height_zh : R.dimen.miui_graffiti_aod_month_height_en);
        layoutParams2.setMargins(getDimen(this.clockIsZH ? R.dimen.miui_graffiti_aod_month_margin_left_zh : R.dimen.miui_graffiti_aod_month_margin_left_en), getDimen(this.clockIsZH ? R.dimen.miui_graffiti_aod_month_margin_top_zh : R.dimen.miui_graffiti_aod_month_margin_top_en), 0, 0);
        layoutParams3.topToTop = this.clockSpace.getId();
        layoutParams3.leftToLeft = this.guidelineV50.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams3).width = getDimen(this.clockIsZH ? R.dimen.miui_graffiti_aod_day_width_zh : R.dimen.miui_graffiti_aod_day_width_en);
        ((ViewGroup.MarginLayoutParams) layoutParams3).height = getDimen(this.clockIsZH ? R.dimen.miui_graffiti_aod_day_height_zh : R.dimen.miui_graffiti_aod_day_height_en);
        layoutParams3.setMargins(getDimen(this.clockIsZH ? R.dimen.miui_graffiti_aod_day_margin_left_zh : R.dimen.miui_graffiti_aod_day_margin_left_en), getDimen(this.clockIsZH ? R.dimen.miui_graffiti_aod_day_margin_top_zh : R.dimen.miui_graffiti_aod_day_margin_top_en), 0, 0);
        this.dataImage.setLayoutParams(layoutParams);
        this.dataMonth.setLayoutParams(layoutParams2);
        this.dataDay.setLayoutParams(layoutParams3);
    }

    private void initTime() {
        Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(0, 0);
        Constraints.LayoutParams layoutParams2 = new Constraints.LayoutParams(0, 0);
        Constraints.LayoutParams layoutParams3 = new Constraints.LayoutParams(0, 0);
        layoutParams.leftToLeft = this.clockSpace.getId();
        layoutParams.rightToRight = this.clockSpace.getId();
        layoutParams.topToTop = this.clockSpace.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.miui_graffiti_aod_timedot_width);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.miui_graffiti_aod_timedot_height);
        layoutParams.setMargins(0, getDimen(R.dimen.miui_graffiti_aod_timedot_margin_top), 0, 0);
        layoutParams2.rightToRight = this.guidelineV50.getId();
        layoutParams2.topToTop = this.clockSpace.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = getDimen(R.dimen.miui_graffiti_aod_timehour_width);
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = getDimen(R.dimen.miui_graffiti_aod_timehour_height);
        layoutParams3.leftToLeft = this.timeDot.getId();
        layoutParams3.topToTop = this.clockSpace.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams3).width = getDimen(R.dimen.miui_graffiti_aod_timeminute_width);
        ((ViewGroup.MarginLayoutParams) layoutParams3).height = getDimen(R.dimen.miui_graffiti_aod_timeminute_height);
        layoutParams3.setMargins(getDimen(R.dimen.miui_graffiti_aod_timeminute_margin_left), getDimen(R.dimen.miui_graffiti_aod_timeminute_margin_top), 0, 0);
        this.timeDot.setLayoutParams(layoutParams);
        this.timeHour.setLayoutParams(layoutParams2);
        this.timeMinute.setLayoutParams(layoutParams3);
    }

    private void initWeek() {
        Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(0, 0);
        Constraints.LayoutParams layoutParams2 = new Constraints.LayoutParams(0, 0);
        layoutParams.topToTop = this.clockSpace.getId();
        layoutParams.rightToRight = this.guidelineV50.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(this.clockIsZH ? R.dimen.miui_graffiti_aod_weektodayis_width_zh : R.dimen.miui_graffiti_aod_weektodayis_width_en);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(this.clockIsZH ? R.dimen.miui_graffiti_aod_weektodayis_height_zh : R.dimen.miui_graffiti_aod_weektodayis_height_en);
        layoutParams.setMargins(0, getDimen(this.clockIsZH ? R.dimen.miui_graffiti_aod_weektodayis_margin_top_zh : R.dimen.miui_graffiti_aod_weektodayis_margin_top_en), getDimen(this.clockIsZH ? R.dimen.miui_graffiti_aod_weektodayis_margin_right_zh : R.dimen.miui_graffiti_aod_weektodayis_margin_right_en), 0);
        layoutParams2.topToTop = this.clockSpace.getId();
        layoutParams2.rightToRight = this.guidelineV50.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = getDimen(this.clockIsZH ? R.dimen.miui_graffiti_aod_weektoday_width_zh : R.dimen.miui_graffiti_aod_weektoday_width_en);
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = getDimen(this.clockIsZH ? R.dimen.miui_graffiti_aod_weektoday_height_zh : R.dimen.miui_graffiti_aod_weektoday_height_en);
        layoutParams2.setMargins(0, getDimen(this.clockIsZH ? R.dimen.miui_graffiti_aod_weektoday_margin_top_zh : R.dimen.miui_graffiti_aod_weektoday_margin_top_en), getDimen(this.clockIsZH ? R.dimen.miui_graffiti_aod_weektoday_margin_right_zh : R.dimen.miui_graffiti_aod_weektoday_margin_right_en), 0);
        this.weekTodayis.setLayoutParams(layoutParams);
        this.weekToday.setLayoutParams(layoutParams2);
    }

    private void setClockStyle(int i) {
        this.clockIsZH = i == 1;
        initWeek();
        initTime();
        initDate();
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        MiuiGraffitiClockInfoBase miuiGraffitiClockInfoBase = this.mInfo;
        if (miuiGraffitiClockInfoBase != null) {
            setClockStyleInfo(miuiGraffitiClockInfoBase);
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mCalendar = Calendar.getInstance();
        this.guidelineV50 = (Guideline) findViewById(R.id.guideline);
        this.clockSpace = (Space) findViewById(R.id.clock_space);
        this.weekTodayis = (ImageView) findViewById(R.id.week_todayis);
        this.weekToday = (ImageView) findViewById(R.id.week_today);
        this.timeDot = (ImageView) findViewById(R.id.time_dot);
        this.timeHour = (ImageView) findViewById(R.id.time_hour);
        this.timeMinute = (ImageView) findViewById(R.id.time_minute);
        this.dataDay = (ImageView) findViewById(R.id.data_day);
        this.dataMonth = (ImageView) findViewById(R.id.data_month);
        this.dataImage = (ImageView) findViewById(R.id.data_image);
        this.mDateDesc = (ConstraintLayout) findViewById(R.id.date_desc);
        this.mWeekDesc = (ConstraintLayout) findViewById(R.id.week_desc);
        this.mTimeDesc = (ConstraintLayout) findViewById(R.id.time_desc);
    }

    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        MiuiGraffitiClockInfoBase miuiGraffitiClockInfoBase = (MiuiGraffitiClockInfoBase) clockStyleInfo;
        this.mInfo = miuiGraffitiClockInfoBase;
        setClockStyle(miuiGraffitiClockInfoBase.getClockStyle());
        this.hiddenDoodle = this.mInfo.isHiddenDoodle();
        updateTime();
    }

    public void setWallpaperSupportDepth(boolean z) {
        this.mWallpaperSupportDepth = z;
    }

    public void updateTime() {
        this.m24HourFormat = DateFormatUtils.is24HourFormat(getContext());
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        int i = this.mCalendar.get(2) + 1;
        int i2 = this.mCalendar.get(5);
        int i3 = this.mCalendar.get(7);
        int i4 = 12;
        int i5 = this.mCalendar.get(12);
        if (this.m24HourFormat) {
            i4 = this.mCalendar.get(11);
        } else {
            int i6 = this.mCalendar.get(10);
            if (i6 != 0) {
                i4 = i6;
            }
        }
        boolean z = (this.mInfo.isHiddenDoodle() || this.mInfo.isSuperSaveOpen() || !this.mWallpaperSupportDepth) ? false : true;
        int aodPrimaryColor = this.mInfo.getAodPrimaryColor();
        int iBlendColor = ColorUtils.blendColor(aodPrimaryColor, 0.6f);
        Drawable drawable = getResources().getDrawable(this.clockIsZH ? GraffitiStyle.mWeekResource_ZH[i3] : GraffitiStyle.mWeekResource_EN[i3]);
        drawable.setTint(!z ? iBlendColor : aodPrimaryColor);
        this.weekToday.setImageDrawable(drawable);
        Drawable drawable2 = getResources().getDrawable(GraffitiStyle.mTimeHourResource[i4]);
        drawable2.setTint(aodPrimaryColor);
        this.timeHour.setImageDrawable(drawable2);
        Drawable drawable3 = getResources().getDrawable(GraffitiStyle.mTimeMinuteResource[i5]);
        drawable3.setTint(aodPrimaryColor);
        this.timeMinute.setImageDrawable(drawable3);
        Drawable drawable4 = getResources().getDrawable(this.clockIsZH ? GraffitiStyle.mMonthResource_ZH[i] : GraffitiStyle.mMonthResource_EN[i]);
        drawable4.setTint(!z ? iBlendColor : aodPrimaryColor);
        this.dataMonth.setImageDrawable(drawable4);
        Drawable drawable5 = getResources().getDrawable(this.clockIsZH ? GraffitiStyle.mDayResource_ZH[i2] : GraffitiStyle.mDayResource_EN[i2]);
        if (z) {
            iBlendColor = aodPrimaryColor;
        }
        drawable5.setTint(iBlendColor);
        this.dataDay.setImageDrawable(drawable5);
        Drawable drawable6 = getResources().getDrawable(R.drawable.graffiti_dot);
        drawable6.setTint(aodPrimaryColor);
        this.timeDot.setImageDrawable(drawable6);
        Drawable drawable7 = getResources().getDrawable(this.clockIsZH ? R.drawable.graffiti_week_todayis_zh : R.drawable.graffiti_week_todayis_en);
        drawable7.setTint(aodPrimaryColor);
        this.weekTodayis.setImageDrawable(drawable7);
        this.weekTodayis.setVisibility(!z ? 4 : 0);
        if (this.clockIsZH) {
            Drawable drawable8 = getResources().getDrawable(R.drawable.graffiti_dateimg);
            drawable8.setTint(aodPrimaryColor);
            this.dataImage.setImageDrawable(drawable8);
            this.dataImage.setVisibility(z ? 0 : 4);
        }
        this.mDateDesc.setContentDescription(new SimpleDateFormat(getResources().getString(R.string.miui_rhombus_clock_date), Locale.getDefault()).format(Long.valueOf(this.mCalendar.getTimeInMillis())));
        this.mTimeDesc.setContentDescription(DateUtils.formatDateTime(getContext(), System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
        this.mWeekDesc.setContentDescription(new SimpleDateFormat(getResources().getString(R.string.miui_rhombus_clock_week_full)).format(this.mCalendar.getTime()));
    }

    public void updateTimeZone(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mCalendar = Calendar.getInstance(TimeZone.getTimeZone(str));
        updateTime();
    }
}
