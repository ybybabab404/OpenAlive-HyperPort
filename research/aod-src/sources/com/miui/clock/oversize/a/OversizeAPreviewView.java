package com.miui.clock.oversize.a;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.miui.clock.MiuiClockPreviewBaseView;
import com.miui.clock.R;
import com.miui.clock.module.BaseFontStyle;
import com.miui.clock.module.FontNeumaticCompressedB;
import com.miui.clock.module.FontNeumaticCompressedBF;
import com.miui.clock.oversize.OversizeSvgView;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;

/* JADX INFO: loaded from: classes.dex */
public class OversizeAPreviewView extends MiuiClockPreviewBaseView {
    public OversizeSvgView mHour1View;
    public OversizeSvgView mHour2View;
    public int mHourColor;
    public LinearLayout mHourView;
    public int mMargin;
    public OversizeSvgView mMinute1View;
    public OversizeSvgView mMinute2View;
    public int mMinuteColor;
    public LinearLayout mMinuteView;
    public int mNumHeight;
    public int mNumWidth;

    public OversizeAPreviewView(@NonNull Context context) {
        super(context);
        this.mHourColor = Color.parseColor("#000000");
        this.mMinuteColor = Color.parseColor("#66000000");
    }

    public OversizeAPreviewView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHourColor = Color.parseColor("#000000");
        this.mMinuteColor = Color.parseColor("#66000000");
    }

    public OversizeAPreviewView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mHourColor = Color.parseColor("#000000");
        this.mMinuteColor = Color.parseColor("#66000000");
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public int getDimen(int i) {
        return (int) (getResources().getDimensionPixelSize(i) * DeviceConfig.calculateScale(getContext()));
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public int getDimenWithScale(int i) {
        return (int) (getResources().getDimensionPixelSize(i) * DeviceConfig.calculateScale(getContext()) * getScaleRadio());
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public float getScaleRadio() {
        return 0.2f;
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void init() {
        super.init();
        LinearLayout linearLayout = new LinearLayout(this.mContext);
        linearLayout.setOrientation(1);
        linearLayout.setLayoutDirection(0);
        this.mNumWidth = getDimenWithScale(R.dimen.miui_oversize_a_time_w);
        this.mNumHeight = getDimenWithScale(R.dimen.miui_oversize_a_time_h);
        this.mMargin = getDimenWithScale(R.dimen.miui_oversize_a_view_margin);
        LinearLayout linearLayout2 = new LinearLayout(this.mContext);
        this.mHourView = linearLayout2;
        linearLayout2.setOrientation(0);
        this.mHourView.setLayoutDirection(0);
        this.mHour1View = new OversizeSvgView(this.mContext);
        this.mHour2View = new OversizeSvgView(this.mContext);
        this.mHourView.addView(this.mHour1View, new LinearLayout.LayoutParams(this.mNumWidth, this.mNumHeight));
        this.mHourView.addView(this.mHour2View, new LinearLayout.LayoutParams(this.mNumWidth, this.mNumHeight));
        LinearLayout linearLayout3 = new LinearLayout(this.mContext);
        this.mMinuteView = linearLayout3;
        linearLayout3.setOrientation(0);
        this.mMinuteView.setLayoutDirection(0);
        this.mMinute1View = new OversizeSvgView(this.mContext);
        this.mMinute2View = new OversizeSvgView(this.mContext);
        this.mMinuteView.addView(this.mMinute1View, new LinearLayout.LayoutParams(this.mNumWidth, this.mNumHeight));
        this.mMinuteView.addView(this.mMinute2View, new LinearLayout.LayoutParams(this.mNumWidth, this.mNumHeight));
        linearLayout.addView(this.mHourView, new FrameLayout.LayoutParams(this.mNumWidth * 2, this.mNumHeight));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(this.mNumWidth * 2, this.mNumHeight);
        layoutParams.topMargin = this.mMargin;
        linearLayout.addView(this.mMinuteView, layoutParams);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-2, -2);
        layoutParams2.gravity = 17;
        addView(linearLayout, layoutParams2);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(getDimen(R.dimen.miui_clock_small_view_width), getDimen(R.dimen.miui_oversize_a_view_height));
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void setClockDarkMode(boolean z) {
        if (z) {
            updateColor(getResources().getColor(R.color.miui_clock_preview_dark_mode_color_90), getResources().getColor(R.color.miui_clock_preview_light_mode_color_40));
        } else {
            updateColor(getResources().getColor(R.color.miui_clock_preview_light_mode_color), getResources().getColor(R.color.miui_clock_preview_light_mode_color_39));
        }
    }

    public void updateColor(int i, int i2) {
        this.mHourColor = i;
        this.mMinuteColor = i2;
        updateTime();
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void updateTime() {
        super.updateTime();
        String time = DateFormatUtils.formatTime(this.m24HourFormat ? "HHmm" : "hhmm");
        for (int i = 0; i < time.length(); i++) {
            this.mCurrentTimeArray[i] = Integer.parseInt(String.valueOf(time.charAt(i)));
        }
        BaseFontStyle fontNeumaticCompressedBF = new FontNeumaticCompressedBF();
        BaseFontStyle fontNeumaticCompressedB = new FontNeumaticCompressedB();
        if (this.mStyle == 2) {
            fontNeumaticCompressedBF = new FontNeumaticCompressedB();
        }
        if (this.mStyle == 1) {
            fontNeumaticCompressedB = new FontNeumaticCompressedBF();
        }
        this.mHour1View.setFontStyle(fontNeumaticCompressedBF).setTextColor(this.mHourColor).setIndex(this.mCurrentTimeArray[0]).build();
        this.mHour2View.setFontStyle(fontNeumaticCompressedBF).setTextColor(this.mHourColor).setIndex(this.mCurrentTimeArray[1]).build();
        this.mMinute1View.setFontStyle(fontNeumaticCompressedB).setTextColor(this.mMinuteColor).setIndex(this.mCurrentTimeArray[2]).build();
        this.mMinute2View.setFontStyle(fontNeumaticCompressedB).setTextColor(this.mMinuteColor).setIndex(this.mCurrentTimeArray[3]).build();
    }
}
