package com.miui.clock.tiny.pets.interactive;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import com.miui.clock.tiny.R;
import com.miui.clock.tiny.model.TinyClockInfo;
import com.miui.clock.tiny.model.TinyClockViewType;
import com.miui.clock.tiny.utils.DateFormatUtils;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class InteractivePetsClockTimeBase extends ConstraintLayout {
    public Space clockSpace;
    public Guideline guidelineV50;
    public boolean m24HourFormat;
    public Calendar mCalendar;
    public InteractivePetClockInfoBase mInfo;
    public TextView mMonth;
    public ConstraintLayout mTimeDesc;
    public ImageView timeDot;
    public ImageView timeHourLeft;
    public ImageView timeHourRight;
    public ImageView timeMinuteLeft;
    public ImageView timeMinuteRight;
    public ImageView weekBottom;
    public TextView weekToday;

    public InteractivePetsClockTimeBase(Context context) {
        super(context);
    }

    public InteractivePetsClockTimeBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public InteractivePetsClockTimeBase(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private float getDesignScale() {
        return this.mInfo.getDesignWidthDp() / 372.0f;
    }

    public Calendar getClockCalendar() {
        return this.mCalendar;
    }

    public int getDimen(int i) {
        return (int) (((double) (getResources().getDimensionPixelSize(i) * getDesignScale())) + 0.5d);
    }

    public View getIClockView(TinyClockViewType tinyClockViewType) {
        return this;
    }

    public void initColor() {
    }

    public void initDate() {
    }

    public void initTime() {
    }

    public void initWeek() {
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mCalendar = new Calendar();
        this.guidelineV50 = (Guideline) findViewById(R.id.guideline);
        this.clockSpace = (Space) findViewById(R.id.clock_space);
        this.weekBottom = (ImageView) findViewById(R.id.week_todayis);
        this.weekToday = (TextView) findViewById(R.id.week);
        this.timeDot = (ImageView) findViewById(R.id.time_dot);
        this.timeHourLeft = (ImageView) findViewById(R.id.time_hour);
        this.timeHourRight = (ImageView) findViewById(R.id.time_hour_right);
        this.timeMinuteLeft = (ImageView) findViewById(R.id.time_minute);
        this.timeMinuteRight = (ImageView) findViewById(R.id.time_minute_right);
        this.mMonth = (TextView) findViewById(R.id.month);
        this.mTimeDesc = (ConstraintLayout) findViewById(R.id.time_desc);
        Drawable drawable = getResources().getDrawable(R.drawable.interactive_pets_week_bottom);
        drawable.setTint(-1);
        this.weekBottom.setImageDrawable(drawable);
    }

    public void setClockStyle() {
        initColor();
        initWeek();
        initTime();
        initDate();
    }

    public void setClockStyleInfo(TinyClockInfo tinyClockInfo) {
        this.mInfo = (InteractivePetClockInfoBase) tinyClockInfo;
        Log.d("PetHourClockAodView", "change doodle clock base view");
        setClockStyle();
        updateTime();
    }

    public void updateTime() {
        this.m24HourFormat = DateFormatUtils.is24HourFormat(getContext());
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        this.mCalendar.get(5);
        this.mCalendar.get(9);
        this.mCalendar.get(14);
        int i = this.mCalendar.get(20);
        int i2 = this.mCalendar.get(18);
        if (!this.m24HourFormat && (i2 = i2 % 12) == 0) {
            i2 = 12;
        }
        int primaryColor = this.mInfo.getPrimaryColor();
        this.weekToday.setText(this.mCalendar.format(getContext(), getContext().getString(R.string.tiny_pets_week_format)));
        Drawable drawable = getResources().getDrawable(InteractivePetsStyle.mTimeHourLeftResource[i2 / 10]);
        Drawable drawable2 = getResources().getDrawable(InteractivePetsStyle.mTimeHourRightResource[i2 % 10]);
        drawable.setTint(-1);
        drawable2.setTint(-1);
        this.timeHourLeft.setImageDrawable(drawable);
        this.timeHourRight.setImageDrawable(drawable2);
        Drawable drawable3 = getResources().getDrawable(InteractivePetsStyle.mTimeMinuteLeftResource[i / 10]);
        drawable3.setTint(-1);
        this.timeMinuteLeft.setImageDrawable(drawable3);
        Drawable drawable4 = getResources().getDrawable(InteractivePetsStyle.mTimeMinuteRightResource[i % 10]);
        drawable3.setTint(-1);
        this.timeMinuteRight.setImageDrawable(drawable4);
        this.mMonth.setText(DateFormatUtils.formatTime(getResources().getString(R.string.format_month_day_style_interactive_pets)).toUpperCase());
        Drawable drawable5 = getResources().getDrawable(R.drawable.interactive_pets_dot);
        drawable5.setTint(primaryColor);
        this.timeDot.setImageDrawable(drawable5);
        this.mMonth.setContentDescription(this.mCalendar.format(getContext(), getContext().getString(R.string.tiny_pets_date_format)));
        this.mTimeDesc.setContentDescription(DateUtils.formatDateTime(getContext(), System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
    }

    public void updateTimeZone(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mCalendar.setTimeZone(TimeZone.getTimeZone(str));
        updateTime();
    }
}
