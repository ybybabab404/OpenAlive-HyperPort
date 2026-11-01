package com.miui.clock;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public abstract class MiuiClockPreviewBaseView extends FrameLayout {
    public boolean m24HourFormat;
    public Calendar mCalendar;
    public Context mContext;
    public int[] mCurrentTimeArray;
    public boolean mDarkMode;
    public int mStyle;
    public final String[] ssTags;

    public MiuiClockPreviewBaseView(@NonNull Context context) {
        super(context);
        this.mStyle = -1;
        this.mCurrentTimeArray = new int[4];
        this.ssTags = new String[]{"ss01", "ss02", "ss03", "ss04", "ss05", "ss06", "ss07", "ss08", "ss09", "ss10"};
        init();
    }

    public MiuiClockPreviewBaseView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mStyle = -1;
        this.mCurrentTimeArray = new int[4];
        this.ssTags = new String[]{"ss01", "ss02", "ss03", "ss04", "ss05", "ss06", "ss07", "ss08", "ss09", "ss10"};
        init();
    }

    public MiuiClockPreviewBaseView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mStyle = -1;
        this.mCurrentTimeArray = new int[4];
        this.ssTags = new String[]{"ss01", "ss02", "ss03", "ss04", "ss05", "ss06", "ss07", "ss08", "ss09", "ss10"};
        init();
    }

    public int getDimen(int i) {
        return (int) (getResources().getDimensionPixelSize(i) * DeviceConfig.calculateScale(getContext()) * ((!DeviceConfig.isJ18() || DeviceConfig.inLargeScreen(getContext())) ? 1.0f : 0.8f));
    }

    public int getDimenWithScale(int i) {
        return (int) (getResources().getDimensionPixelSize(i) * DeviceConfig.calculateScale(getContext()) * getScaleRadio() * ((!DeviceConfig.isJ18() || DeviceConfig.inLargeScreen(getContext())) ? 1.0f : 0.8f));
    }

    public abstract float getScaleRadio();

    public void init() {
        this.mContext = getContext();
    }

    public boolean isDarkMode() {
        return this.mDarkMode;
    }

    public void setCalendar(Calendar calendar) {
        this.mCalendar = calendar;
    }

    public abstract void setClockDarkMode(boolean z);

    public void setDarkMode(boolean z) {
        this.mDarkMode = z;
    }

    public void setStyle(int i) {
        this.mStyle = i;
    }

    public void updateTime() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (this.mCalendar == null) {
            this.mCalendar = new Calendar(TimeZone.getDefault());
        }
        this.mCalendar.setTimeInMillis(jCurrentTimeMillis);
        this.m24HourFormat = DateFormatUtils.is24HourFormat(this.mContext);
    }

    public void updateTimeView() {
        if (this.mStyle == -1) {
            return;
        }
        updateTime();
    }
}
