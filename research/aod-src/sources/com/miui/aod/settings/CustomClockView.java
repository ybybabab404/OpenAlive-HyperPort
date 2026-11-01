package com.miui.aod.settings;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.aod.R;
import com.miui.aod.Utils;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class CustomClockView extends LinearLayout {
    public static final int DEFAULT_BLEND_DARK_COLOR = Color.parseColor("#FFFFFF");
    public final Typeface demibold;
    public boolean m24HourFormat;
    public ImageView mAodNotification;
    public TextView mBigMsgView;
    public Calendar mCalendar;
    public Context mContext;
    public Handler mHandler;
    public TextView mTimeView;
    public final Runnable mUpdateRunnable;
    public final Typeface medium;

    public CustomClockView(Context context) {
        this(context, null);
    }

    public CustomClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.demibold = Typeface.create("mipro-demibold", 0);
        this.medium = Typeface.create("mipro-medium", 0);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mUpdateRunnable = new Runnable() { // from class: com.miui.aod.settings.CustomClockView.1
            @Override // java.lang.Runnable
            public void run() {
                CustomClockView.this.updateTime();
                CustomClockView.this.mHandler.postDelayed(CustomClockView.this.mUpdateRunnable, 60000L);
            }
        };
        init(context);
    }

    public static int getHourInt(boolean z, Calendar calendar) {
        if (z) {
            return calendar.get(18);
        }
        int i = calendar.get(18);
        if (i == 0) {
            return 12;
        }
        return i < 13 ? i : i - 12;
    }

    public static String getTimeString(int i, boolean z) {
        if (i >= 10 || !z) {
            return String.valueOf(i);
        }
        return "0" + i;
    }

    public final void applyNotificationBlur(View view) {
        if (view == null) {
            return;
        }
        try {
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.custom_clock_notification_blur_radius);
            Class<?> cls = view.getClass();
            Class cls2 = Integer.TYPE;
            cls.getMethod("setMiBackgroundBlurMode", cls2).invoke(view, 1);
            view.getClass().getMethod("setMiBackgroundBlurRadius", cls2).invoke(view, Integer.valueOf(dimensionPixelSize));
            view.getClass().getMethod("setMiViewBlurMode", cls2).invoke(view, 1);
        } catch (Exception e) {
            Log.e("CustomClockView", "applyNotificationBlur failed", e);
        }
    }

    public void changeLayout() {
        this.mAodNotification.setImageResource(R.drawable.aod_notification_inner_1);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mAodNotification.getLayoutParams();
        layoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.custom_clock_notification_margin_bottom);
        if (Utils.isFoldDevice() && Utils.inLargeScreen(getContext())) {
            Configuration configuration = getResources().getConfiguration();
            if (configuration != null && configuration.orientation == 2) {
                this.mAodNotification.setImageResource(R.drawable.aod_notification_inner_2);
                layoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.custom_clock_notification_margin_bottom_large_land);
            } else if (configuration != null) {
                this.mAodNotification.setImageResource(R.drawable.aod_notification_inner_3);
                layoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.custom_clock_notification_margin_bottom_large_port);
            }
        }
        this.mAodNotification.setLayoutParams(layoutParams);
        applyNotificationBlur(this.mAodNotification);
    }

    public void init(Context context) {
        this.mContext = context;
        this.mCalendar = new Calendar();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTimeView = (TextView) findViewById(R.id.time_view);
        this.mBigMsgView = (TextView) findViewById(R.id.big_msg_view);
        this.mAodNotification = (ImageView) findViewById(R.id.iv_aod_notification);
        TextView textView = this.mTimeView;
        int i = DEFAULT_BLEND_DARK_COLOR;
        textView.setTextColor(i);
        this.mBigMsgView.setTextColor(i);
        this.mTimeView.setTypeface(this.demibold);
        this.mBigMsgView.setTypeface(this.medium);
        this.mTimeView.setIncludeFontPadding(false);
        this.mBigMsgView.setIncludeFontPadding(false);
        changeLayout();
        startUpdateTime();
    }

    public final void startUpdateTime() {
        updateTime();
        this.mHandler.postDelayed(this.mUpdateRunnable, ((60 - this.mCalendar.get(21)) * 1000) + (1000 - this.mCalendar.get(22)));
    }

    public void stopUpdateTime() {
        this.mHandler.removeCallbacks(this.mUpdateRunnable);
    }

    public void updateTime() {
        this.m24HourFormat = DateFormat.is24HourFormat(getContext());
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        this.mTimeView.setText(getContext().getResources().getString(R.string.format_hour_minute, getTimeString(getHourInt(this.m24HourFormat, this.mCalendar), this.m24HourFormat), getTimeString(this.mCalendar.get(20), true)));
        TextView textView = this.mBigMsgView;
        Calendar calendar = this.mCalendar;
        Context context = this.mContext;
        textView.setText(calendar.format(context, context.getString(R.string.format_month_day_week)));
    }
}
