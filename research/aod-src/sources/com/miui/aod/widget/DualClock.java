package com.miui.aod.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.icu.ICUManager;
import com.miui.aod.resource.AodDrawables;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class DualClock implements IAodClock {
    public Calendar mCal;
    public TextView mClockHorizontal;
    public TextView mClockHourView;
    public TextView mClockMinuteView;
    public Context mContext;
    public TextView mDateView;
    public GradientLinearLayout mGradientLayout;
    public final int mSize;
    public TimeZone mTimeZone;

    @Override // com.miui.aod.widget.IAodClock
    public void bindView(View view) {
        Context context = view.getContext();
        this.mContext = context;
        if (this.mSize == 1) {
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.aod_clock_padding_large);
            view.setPaddingRelative(dimensionPixelSize, view.getPaddingTop(), dimensionPixelSize, view.getPaddingBottom());
        }
        Typeface typefaceCreateFromAsset = Typeface.createFromAsset(this.mContext.getAssets(), "fonts/MitypeRoundedNormal.otf");
        this.mClockHourView = (TextView) view.findViewById(R.id.clock_hour);
        this.mClockMinuteView = (TextView) view.findViewById(R.id.clock_minute);
        TextView textView = this.mClockHourView;
        if (textView != null) {
            textView.setTypeface(typefaceCreateFromAsset);
        }
        TextView textView2 = this.mClockMinuteView;
        if (textView2 != null) {
            textView2.setTypeface(typefaceCreateFromAsset);
        }
        this.mDateView = (TextView) view.findViewById(R.id.date);
        TextView textView3 = (TextView) view.findViewById(R.id.clock_horizontal);
        this.mClockHorizontal = textView3;
        if (textView3 != null) {
            textView3.setTypeface(typefaceCreateFromAsset);
        }
        TextView textView4 = (TextView) view.findViewById(R.id.city);
        TimeZone timeZone = this.mTimeZone;
        if (timeZone != null) {
            ICUManager.INSTANCE.loadCity(textView4, this.mContext, timeZone);
        }
        this.mGradientLayout = (GradientLinearLayout) view.findViewById(R.id.gradient_layout);
    }

    @Override // com.miui.aod.widget.IAodClock
    public int getLayoutResource(StyleInfo styleInfo) {
        int i = this.mSize;
        return (i == 1 || i == 3 || i == 5) ? R.layout.aod_content_horizontal_dual : R.layout.aod_content_horizontal_dual_small;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setClockMask(int i, int i2) {
        if (i2 > 0) {
            this.mGradientLayout.setGradientOverlayDrawable(AodDrawables.getDrawableForMask(this.mContext, i2));
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setTimeZone(TimeZone timeZone) {
        this.mTimeZone = timeZone;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setTimeZone2(TimeZone timeZone) {
    }

    @Override // com.miui.aod.widget.IAodClock
    public void updateTime(boolean z) {
        TimeZone timeZone = this.mTimeZone;
        if (timeZone == null) {
            timeZone = TimeZone.getDefault();
        }
        this.mCal = Utils.refreshCalendar(this.mCal, timeZone, System.currentTimeMillis());
        new SimpleDateFormat(Utils.getHourMinformat(this.mContext)).setTimeZone(timeZone);
        int i = this.mCal.get(18);
        if (!z && i > 12) {
            i -= 12;
        }
        int i2 = (z || i != 0) ? i : 12;
        TextView textView = this.mClockHourView;
        if (textView != null) {
            textView.setText(String.valueOf(i2));
        }
        TextView textView2 = this.mClockMinuteView;
        if (textView2 != null) {
            textView2.setText(String.format("%02d", Integer.valueOf(this.mCal.get(20))));
        }
        TextView textView3 = this.mClockHorizontal;
        if (textView3 != null) {
            textView3.setText(String.format("%d:%02d", Integer.valueOf(i2), Integer.valueOf(this.mCal.get(20))));
        }
        this.mDateView.setText(this.mCal.format(this.mContext, this.mContext.getResources().getString(z ? R.string.aod_lock_screen_date : R.string.aod_lock_screen_date_12)));
    }
}
