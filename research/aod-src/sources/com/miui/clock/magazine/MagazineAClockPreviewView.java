package com.miui.clock.magazine;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Space;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.MiuiClockPreviewBaseView;
import com.miui.clock.R;
import com.miui.clock.utils.DateFormatUtils;
import java.util.Locale;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class MagazineAClockPreviewView extends MiuiClockPreviewBaseView {
    public ViewGroup mClockContainer;
    public ViewGroup mClockContainerInner;
    public Space mDateCenterSpace;
    public View mDateSpace;
    public TextView mDateText;
    public String mLanguage;
    public Space mTimeCenterSpace;
    public View mTimeSpace;
    public TextView mTimeText;
    public View mWeekSpace;
    public TextView mWeekText;

    public MagazineAClockPreviewView(@NonNull Context context) {
        super(context);
    }

    public MagazineAClockPreviewView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MagazineAClockPreviewView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public final int extraTopMarginForWeek() {
        if ("bo".equals(this.mLanguage)) {
            return getDimen(R.dimen.miui_magazine_a_clock_week_extra_margin_top_for_bo);
        }
        return 0;
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public float getScaleRadio() {
        return 0.486f;
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void init() {
        super.init();
        View viewInflate = View.inflate(this.mContext, R.layout.layout_magazine_a_preview_view, this);
        this.mDateText = (TextView) viewInflate.findViewById(R.id.current_date);
        this.mWeekText = (TextView) viewInflate.findViewById(R.id.current_week);
        this.mTimeText = (TextView) viewInflate.findViewById(R.id.current_time);
        this.mDateSpace = viewInflate.findViewById(R.id.date_space);
        this.mTimeSpace = viewInflate.findViewById(R.id.time_space);
        this.mWeekSpace = viewInflate.findViewById(R.id.week_space);
        this.mDateCenterSpace = (Space) viewInflate.findViewById(R.id.date_center_space);
        this.mTimeCenterSpace = (Space) viewInflate.findViewById(R.id.time_center_space);
        this.mClockContainerInner = (ViewGroup) viewInflate.findViewById(R.id.clock_container_inner);
        this.mClockContainer = (ViewGroup) viewInflate.findViewById(R.id.clock_container);
        this.mLanguage = Locale.getDefault().getLanguage();
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(getDimen(R.dimen.miui_clock_small_view_width), getDimen("bo".equals(this.mLanguage) ? R.dimen.miui_clock_small_view_bo_height : R.dimen.miui_clock_small_view_height));
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void setClockDarkMode(boolean z) {
        setDarkMode(z);
        if (z) {
            TextView textView = this.mTimeText;
            Resources resources = getResources();
            int i = R.color.miui_clock_preview_dark_mode_color_90;
            textView.setTextColor(resources.getColor(i));
            this.mDateText.setTextColor(getResources().getColor(i));
            this.mWeekText.setTextColor(getResources().getColor(i));
            return;
        }
        TextView textView2 = this.mTimeText;
        Resources resources2 = getResources();
        int i2 = R.color.miui_clock_preview_light_mode_color;
        textView2.setTextColor(resources2.getColor(i2));
        this.mDateText.setTextColor(getResources().getColor(i2));
        this.mWeekText.setTextColor(getResources().getColor(i2));
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void updateTime() {
        super.updateTime();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mClockContainer.getLayoutParams();
        layoutParams.width = getDimen(R.dimen.miui_clock_small_view_width);
        layoutParams.height = getDimen("bo".equals(this.mLanguage) ? R.dimen.miui_clock_small_view_bo_height : R.dimen.miui_clock_small_view_height);
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mClockContainerInner.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mTimeText.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.mDateText.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams5 = (ConstraintLayout.LayoutParams) this.mWeekText.getLayoutParams();
        Typeface typefaceCreate = Typeface.create("miclock-chamberi-display-semibold", 0);
        Typeface typefaceCreate2 = Typeface.create("miclock-chamberi-display-semibold-italic", 0);
        Typeface typefaceCreate3 = Typeface.create("miclock-qinghe-75w", 0);
        ConstraintLayout.LayoutParams layoutParams6 = (ConstraintLayout.LayoutParams) this.mDateSpace.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams7 = (ConstraintLayout.LayoutParams) this.mTimeSpace.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams8 = (ConstraintLayout.LayoutParams) this.mWeekSpace.getLayoutParams();
        if (this.mStyle == 2) {
            int i = R.dimen.miui_magazine_a_clock_date_style2_margin_top;
            ((ViewGroup.MarginLayoutParams) layoutParams6).topMargin = getDimenWithScale(i);
            int i2 = R.dimen.miui_magazine_a_clock_time_style2_margin_top;
            ((ViewGroup.MarginLayoutParams) layoutParams7).topMargin = getDimenWithScale(i2);
            ((ViewGroup.MarginLayoutParams) layoutParams8).topMargin = getDimenWithScale(R.dimen.miui_magazine_a_clock_week_style2_margin_top) + extraTopMarginForWeek();
            ConstraintLayout.LayoutParams layoutParams9 = (ConstraintLayout.LayoutParams) this.mDateCenterSpace.getLayoutParams();
            ((ViewGroup.MarginLayoutParams) layoutParams9).topMargin = getDimenWithScale(i);
            layoutParams9.setMarginStart(getDimenWithScale(R.dimen.miui_magazine_a_clock_date_style2_center_x));
            this.mDateCenterSpace.setLayoutParams(layoutParams9);
            ConstraintLayout.LayoutParams layoutParams10 = (ConstraintLayout.LayoutParams) this.mTimeCenterSpace.getLayoutParams();
            ((ViewGroup.MarginLayoutParams) layoutParams10).topMargin = getDimenWithScale(i2);
            layoutParams10.setMarginStart(getDimenWithScale(R.dimen.miui_magazine_a_clock_time_style2_center_x));
            this.mTimeCenterSpace.setLayoutParams(layoutParams10);
            int i3 = R.dimen.miui_magazine_a_clock_date_style2_minwidth;
            ((ViewGroup.MarginLayoutParams) layoutParams4).width = getDimenWithScale(i3);
            ((ViewGroup.MarginLayoutParams) layoutParams4).height = getDimenWithScale(R.dimen.miui_magazine_a_clock_date_style2_height);
            ((ViewGroup.MarginLayoutParams) layoutParams3).width = getDimenWithScale(i3);
            ((ViewGroup.MarginLayoutParams) layoutParams3).height = getDimenWithScale(R.dimen.miui_magazine_a_clock_time_style2_height);
            ((ViewGroup.MarginLayoutParams) layoutParams5).width = -1;
            ((ViewGroup.MarginLayoutParams) layoutParams5).height = getDimenWithScale(R.dimen.miui_magazine_a_clock_week_style2_height);
            layoutParams4.baselineToBaseline = R.id.date_space;
            layoutParams3.baselineToBaseline = R.id.time_space;
            layoutParams5.baselineToBaseline = R.id.week_space;
            int i4 = R.id.date_center_space;
            layoutParams4.startToStart = i4;
            int i5 = R.id.time_center_space;
            layoutParams3.startToStart = i5;
            layoutParams5.startToStart = 0;
            layoutParams4.endToEnd = i4;
            layoutParams3.endToEnd = i5;
            layoutParams5.endToEnd = 0;
            layoutParams4.setMarginStart(0);
            layoutParams3.setMarginStart(0);
            layoutParams5.setMarginStart(0);
            layoutParams4.setMarginEnd(0);
            layoutParams3.setMarginEnd(0);
            layoutParams5.setMarginEnd(0);
            this.mDateText.setLayoutParams(layoutParams4);
            this.mTimeText.setLayoutParams(layoutParams3);
            this.mWeekText.setLayoutParams(layoutParams5);
            this.mDateText.setTextSize(0, getDimenWithScale(R.dimen.miui_magazine_a_clock_date_style2_text_size));
            this.mTimeText.setTextSize(0, getDimenWithScale(R.dimen.miui_magazine_a_clock_time_style2_text_size));
            this.mWeekText.setTextSize(0, getDimenWithScale(R.dimen.miui_magazine_a_clock_week_style2_text_size));
            this.mDateText.setTypeface(typefaceCreate2);
            this.mTimeText.setTypeface(typefaceCreate2);
            this.mWeekText.setTypeface(typefaceCreate3);
            this.mDateText.setLetterSpacing(0.0f);
            this.mTimeText.setLetterSpacing(0.0f);
            this.mDateText.setGravity(1);
            this.mTimeText.setGravity(1);
            this.mWeekText.setGravity(1);
            layoutParams2.startToStart = 0;
            layoutParams2.endToEnd = 0;
            layoutParams2.topToTop = 0;
            layoutParams2.setMarginStart(0);
            layoutParams2.setMarginEnd(0);
            ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimen(R.dimen.miui_magazine_a_small_view_style_2_margin_inner_top);
        } else {
            ((ViewGroup.MarginLayoutParams) layoutParams6).topMargin = getDimenWithScale(R.dimen.miui_magazine_a_clock_date_style1_margin_top);
            ((ViewGroup.MarginLayoutParams) layoutParams7).topMargin = getDimenWithScale(R.dimen.miui_magazine_a_clock_time_style1_margin_top);
            ((ViewGroup.MarginLayoutParams) layoutParams8).topMargin = getDimenWithScale(R.dimen.miui_magazine_a_clock_week_style1_margin_top) + extraTopMarginForWeek();
            ((ViewGroup.MarginLayoutParams) layoutParams4).width = -2;
            ((ViewGroup.MarginLayoutParams) layoutParams4).height = getDimenWithScale(R.dimen.miui_magazine_a_clock_date_style1_height);
            ((ViewGroup.MarginLayoutParams) layoutParams3).width = -2;
            ((ViewGroup.MarginLayoutParams) layoutParams3).height = getDimenWithScale(R.dimen.miui_magazine_a_clock_time_style1_height);
            ((ViewGroup.MarginLayoutParams) layoutParams5).width = -2;
            ((ViewGroup.MarginLayoutParams) layoutParams5).height = getDimenWithScale(R.dimen.miui_magazine_a_clock_week_style1_height);
            layoutParams4.baselineToBaseline = R.id.date_space;
            layoutParams3.baselineToBaseline = R.id.time_space;
            layoutParams5.baselineToBaseline = R.id.week_space;
            if (this.mStyle == 1) {
                layoutParams4.startToStart = 0;
                layoutParams3.startToStart = 0;
                layoutParams5.startToStart = 0;
                layoutParams4.endToEnd = -1;
                layoutParams3.endToEnd = -1;
                layoutParams5.endToEnd = -1;
                layoutParams4.setMarginStart(getDimenWithScale(R.dimen.miui_magazine_a_clock_date_style1_margin_start));
                layoutParams3.setMarginStart(getDimenWithScale(R.dimen.miui_magazine_a_clock_time_style1_margin_start));
                layoutParams5.setMarginStart(getDimenWithScale(R.dimen.miui_magazine_a_clock_week_style1_margin_start));
                layoutParams4.setMarginEnd(0);
                layoutParams3.setMarginEnd(0);
                layoutParams5.setMarginEnd(0);
                layoutParams2.startToStart = 0;
                layoutParams2.endToEnd = -1;
                layoutParams2.topToTop = 0;
                layoutParams2.setMarginStart(getDimen(R.dimen.miui_magazine_a_small_view_style_1_margin_inner_margin_start));
                ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimen(R.dimen.miui_magazine_a_small_view_style_1_margin_inner_margin_top);
                layoutParams2.setMarginEnd(0);
            } else {
                layoutParams4.endToEnd = 0;
                layoutParams3.endToEnd = 0;
                layoutParams5.endToEnd = 0;
                layoutParams4.startToStart = -1;
                layoutParams3.startToStart = -1;
                layoutParams5.startToStart = -1;
                layoutParams4.setMarginStart(0);
                layoutParams3.setMarginStart(0);
                layoutParams5.setMarginStart(0);
                layoutParams4.setMarginEnd(getDimenWithScale(R.dimen.miui_magazine_a_clock_date_style1_margin_start));
                layoutParams3.setMarginEnd(getDimenWithScale(R.dimen.miui_magazine_a_clock_time_style1_margin_start));
                layoutParams5.setMarginEnd(getDimenWithScale(R.dimen.miui_magazine_a_clock_week_style1_margin_end));
                layoutParams2.endToEnd = 0;
                layoutParams2.startToStart = -1;
                layoutParams2.topToTop = 0;
                layoutParams2.setMarginStart(0);
                ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimen(R.dimen.miui_magazine_a_small_view_style_1_margin_inner_margin_top);
                layoutParams2.setMarginEnd(getDimen(R.dimen.miui_magazine_a_small_view_style_1_margin_inner_margin_end));
            }
            this.mDateText.setLayoutParams(layoutParams4);
            this.mTimeText.setLayoutParams(layoutParams3);
            this.mWeekText.setLayoutParams(layoutParams5);
            this.mDateText.setTextSize(0, getDimenWithScale(R.dimen.miui_magazine_a_clock_date_style1_text_size));
            this.mTimeText.setTextSize(0, getDimenWithScale(R.dimen.miui_magazine_a_clock_time_style1_text_size));
            this.mWeekText.setTextSize(0, getDimenWithScale(R.dimen.miui_magazine_a_clock_week_style1_text_size));
            this.mDateText.setTypeface(typefaceCreate);
            this.mTimeText.setTypeface(typefaceCreate);
            this.mWeekText.setTypeface(typefaceCreate3);
            this.mDateText.setLetterSpacing(0.03f);
            this.mTimeText.setLetterSpacing(0.03f);
        }
        this.mClockContainerInner.setLayoutParams(layoutParams2);
        TextView textView = this.mTimeText;
        Resources resources = getResources();
        int i6 = R.color.preview_text_color;
        textView.setTextColor(resources.getColor(i6));
        this.mDateText.setTextColor(getResources().getColor(i6));
        this.mWeekText.setTextColor(getResources().getColor(R.color.magazine_a_preview_week_color));
        this.mTimeText.setText(DateFormatUtils.formatTime(this.mContext.getString(this.m24HourFormat ? R.string.miui_clock_time_format_24 : R.string.miui_clock_time_format_12)));
        this.mDateText.setText(DateFormatUtils.formatTime(this.mContext.getString(R.string.miui_magazine_clock_date)).toUpperCase());
        TextView textView2 = this.mDateText;
        Calendar calendar = this.mCalendar;
        Context context = this.mContext;
        textView2.setContentDescription(calendar.format(context, context.getString(R.string.miui_magazine_clock_date_content_description)));
        TextView textView3 = this.mWeekText;
        Calendar calendar2 = this.mCalendar;
        Context context2 = this.mContext;
        textView3.setText(calendar2.format(context2, context2.getString(R.string.miui_magazine_clock_week)).toUpperCase());
        setClockDarkMode(isDarkMode());
    }
}
