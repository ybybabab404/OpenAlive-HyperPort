package com.miui.clock.tiny.model;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.tiny.TinyMiuiClockController;
import com.miui.clock.tiny.utils.DateFormatUtils;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public abstract class TinyMiuiBaseClock extends ConstraintLayout implements TinyMiuiClockController.IClockView {
    public static final int DEFAULT_BLEND_DARK_COLOR = Color.parseColor("#FFFFFF");
    public boolean m24HourFormat;
    public Calendar mCalendar;
    public TinyClockInfo mClockStyleInfo;
    public Configuration mConfiguration;
    public Context mContext;
    public int[] mCurrentTimeArray;
    public boolean resourceStatus;

    public TinyMiuiBaseClock(Context context) {
        super(context);
        this.mCurrentTimeArray = new int[4];
        this.resourceStatus = true;
    }

    public TinyMiuiBaseClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurrentTimeArray = new int[4];
        this.resourceStatus = true;
    }

    public TinyMiuiBaseClock(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCurrentTimeArray = new int[4];
        this.resourceStatus = true;
        init(context);
    }

    private void init(Context context) {
        this.mCalendar = new Calendar();
        this.mContext = getContext();
        this.mConfiguration = new Configuration(context.getResources().getConfiguration());
        this.m24HourFormat = DateFormatUtils.is24HourFormat(this.mContext);
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
    }

    public static int isBackgroundBlurEnabled(Context context) {
        if (!DateFormatUtils.isSystemUI(context)) {
            return Settings.Secure.getInt(context.getContentResolver(), "background_blur_enable", 0);
        }
        try {
            int iUpdateCurrentUserId = DateFormatUtils.updateCurrentUserId();
            Class cls = Integer.TYPE;
            return ((Integer) Settings.Secure.class.getMethod("getIntForUser", ContentResolver.class, String.class, cls, cls).invoke(null, context.getContentResolver(), "background_blur_enable", 0, Integer.valueOf(iUpdateCurrentUserId))).intValue();
        } catch (Exception e) {
            Log.e("TinyMiuiBaseClock", "getIntForUser fail", e);
            return 0;
        }
    }

    public Calendar getClockCalendar() {
        return this.mCalendar;
    }

    public TinyClockInfo getClockStyleInfo() {
        return this.mClockStyleInfo;
    }

    public int getDimen(int i) {
        return getContext().getResources().getDimensionPixelSize(i);
    }

    public View getIClockView(TinyClockViewType tinyClockViewType) {
        return this;
    }

    public abstract /* synthetic */ int getMarginAOD();

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if ((this.mConfiguration.updateFrom(configuration) & 4) != 0) {
            Log.d("TinyMiuiBaseClock", "language changed!");
            onLanguageChanged();
        }
    }

    public void onLanguageChanged() {
        updateTime();
    }

    public void setBatteryColor(int i) {
    }

    public void setBatteryLevel(int i) {
    }

    public void setClockStyleInfo(TinyClockInfo tinyClockInfo) {
        this.mClockStyleInfo = tinyClockInfo;
    }

    public void updateTime() {
        this.m24HourFormat = DateFormatUtils.is24HourFormat(getContext());
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        String time = DateFormatUtils.formatTime(this.m24HourFormat ? "HHmm" : "hhmm");
        for (int i = 0; i < time.length(); i++) {
            this.mCurrentTimeArray[i] = Integer.parseInt(String.valueOf(time.charAt(i)));
        }
    }

    public void updateTimeZone(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mCalendar.setTimeZone(TimeZone.getTimeZone(str));
    }
}
