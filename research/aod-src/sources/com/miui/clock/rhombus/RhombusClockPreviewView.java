package com.miui.clock.rhombus;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import com.miui.clock.MiuiTextGlassView;
import com.miui.clock.R;
import com.miui.clock.utils.DeviceConfig;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class RhombusClockPreviewView extends FrameLayout {
    public View mArea1;
    public View mArea2;
    public Calendar mCalendar;
    public ViewGroup mClockContainer;
    public Context mContext;
    public int mFontStyle;
    public Group mSecondLine;
    public int mStyle;
    public MiuiTextGlassView mTimeView;
    public MiuiTextGlassView mTimeView2;

    public RhombusClockPreviewView(@NonNull Context context) {
        super(context);
        this.mStyle = 71;
        this.mFontStyle = 23;
        init();
    }

    public RhombusClockPreviewView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mStyle = 71;
        this.mFontStyle = 23;
        init();
    }

    public RhombusClockPreviewView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mStyle = 71;
        this.mFontStyle = 23;
        init();
    }

    public int getDimen2(int i) {
        return (int) (getResources().getDimensionPixelSize(i) * DeviceConfig.calculateScale(getContext()));
    }

    public final void init() {
        Context context = getContext();
        this.mContext = context;
        View viewInflate = View.inflate(context, R.layout.layout_classic_plus_preview_view, this);
        this.mTimeView = (MiuiTextGlassView) viewInflate.findViewById(R.id.time_view);
        this.mTimeView2 = (MiuiTextGlassView) viewInflate.findViewById(R.id.time_view2);
        this.mArea1 = viewInflate.findViewById(R.id.area1);
        this.mArea2 = viewInflate.findViewById(R.id.area2);
        this.mTimeView2.setSameNumberWidth(true);
        this.mSecondLine = (Group) viewInflate.findViewById(R.id.second_line);
        this.mClockContainer = (ViewGroup) viewInflate.findViewById(R.id.clock_container);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(getDimen2(R.dimen.miui_classic_plus_clock_small_view_width), getDimen2(R.dimen.miui_classic_plus_clock_small_view_height));
    }

    public void setCalendar(Calendar calendar) {
        this.mCalendar = calendar;
    }

    public void setFontStyle(int i) {
        this.mFontStyle = i;
    }

    public void setStyle(int i) {
        this.mStyle = i;
    }
}
