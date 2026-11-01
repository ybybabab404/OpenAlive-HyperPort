package com.miui.clock.classic;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import com.miui.clock.MiuiClockPreviewBaseView;
import com.miui.clock.MiuiTextGlassView;
import com.miui.clock.R;
import com.miui.clock.utils.ClassicClockTimeUtils;

/* JADX INFO: loaded from: classes.dex */
public class ClassicPlusClockPreviewView extends MiuiClockPreviewBaseView {
    public View mArea1;
    public View mArea2;
    public ViewGroup mClockContainer;
    public int mFontStyle;
    public Group mSecondLine;
    public MiuiTextGlassView mTimeView;
    public MiuiTextGlassView mTimeView2;

    public ClassicPlusClockPreviewView(@NonNull Context context) {
        super(context);
        this.mFontStyle = 23;
    }

    public ClassicPlusClockPreviewView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFontStyle = 23;
    }

    public ClassicPlusClockPreviewView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mFontStyle = 23;
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public float getScaleRadio() {
        return 0.42f;
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void init() {
        super.init();
        View viewInflate = View.inflate(this.mContext, R.layout.layout_classic_plus_preview_view, this);
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
        setMeasuredDimension(getDimen(R.dimen.miui_classic_plus_clock_small_view_width), getDimen(R.dimen.miui_classic_plus_clock_small_view_height));
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void setClockDarkMode(boolean z) {
        Resources resources;
        int i;
        setDarkMode(z);
        if (z) {
            resources = getResources();
            i = R.color.miui_clock_preview_dark_mode_color_20;
        } else {
            resources = getResources();
            i = R.color.miui_clock_preview_light_mode_color;
        }
        updateColor(resources.getColor(i));
    }

    public void setFontStyle(int i) {
        this.mFontStyle = i;
    }

    public void updateColor(int i) {
        this.mTimeView.setTextColor(i);
        this.mTimeView2.setTextColor(i);
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void updateTime() {
        super.updateTime();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mClockContainer.getLayoutParams();
        layoutParams.width = getResources().getDimensionPixelSize(R.dimen.miui_classic_plus_clock_small_view_width);
        layoutParams.height = getDimen(R.dimen.miui_classic_plus_clock_small_view_height);
        ViewGroup.LayoutParams layoutParams2 = this.mArea1.getLayoutParams();
        ViewGroup.LayoutParams layoutParams3 = this.mArea2.getLayoutParams();
        int i = R.dimen.classic_plus_area_time_height;
        layoutParams2.height = getDimenWithScale(i);
        layoutParams3.height = getDimenWithScale(i);
        boolean z = this.mStyle == 72;
        this.mTimeView.setSameNumberWidth(z);
        if (z) {
            this.mSecondLine.setVisibility(0);
        } else {
            this.mSecondLine.setVisibility(8);
        }
        MiuiTextGlassView miuiTextGlassView = this.mTimeView;
        Resources resources = getResources();
        int i2 = R.color.preview_text_color;
        ClassicClockTimeUtils.setClassicTimeStyle(miuiTextGlassView, "classic_plus", resources.getColor(i2), this.mFontStyle, 420, this.mStyle, false, true);
        ClassicClockTimeUtils.setClassicTimeStyle(this.mTimeView2, "classic_plus", getResources().getColor(i2), this.mFontStyle, 420, this.mStyle, false, true);
        int hourInt = ClassicClockTimeUtils.getHourInt(this.m24HourFormat, this.mCalendar);
        int i3 = this.mCalendar.get(20);
        if (z) {
            this.mTimeView.setText(ClassicClockTimeUtils.getTimeString(hourInt, true));
            this.mTimeView2.setText(ClassicClockTimeUtils.getTimeString(i3, true));
        } else {
            this.mTimeView.setText(this.mContext.getString(R.string.format_hour_minute, ClassicClockTimeUtils.getTimeString(hourInt, true), ClassicClockTimeUtils.getTimeString(i3, true)));
            this.mTimeView2.setText("");
        }
        MiuiTextGlassView miuiTextGlassView2 = this.mTimeView;
        int i4 = R.dimen.classic_time_area_plus_text_size;
        miuiTextGlassView2.setTextSize(0, getDimenWithScale(i4));
        this.mTimeView2.setTextSize(0, getDimenWithScale(i4));
        setClockDarkMode(isDarkMode());
    }
}
