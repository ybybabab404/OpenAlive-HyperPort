package com.miui.clock.magazine;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Space;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.MiuiClockPreviewBaseView;
import com.miui.clock.MiuiTextGlassView;
import com.miui.clock.R;
import com.miui.clock.utils.BaseLineSpaceView;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.FontUtils;
import com.xiaomi.onetrack.util.ac;
import java.util.Locale;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class MagazineCClockPreviewView extends MiuiClockPreviewBaseView {
    public ViewGroup mClockContainerStyle1;
    public ViewGroup mClockContainerStyle2;
    public TextView mDateTextStyle1;
    public MiuiTextGlassView mHourTextStyle1;
    public MiuiTextGlassView mHourTextStyle2;
    public MiuiTextGlassView mMinuteTextStyle1;
    public MiuiTextGlassView mMinuteTextStyle2;
    public BaseLineSpaceView mSpaceDateStyle1;
    public Space mSpaceTimeEndStyle1;
    public Space mSpaceTimeStyle1;
    public BaseLineSpaceView mSpaceTimeStyle2;
    public BaseLineSpaceView mSpaceWeekStyle1;
    public ViewGroup mTimeContainerStyle1;
    public Typeface mTypefaceMisans;
    public Typeface mTypefaceNeueMatic;
    public TextView mWeekTextStyle1;
    public int resetColorHour;
    public int resetColorMinute;

    public MagazineCClockPreviewView(@NonNull Context context) {
        super(context);
        this.resetColorHour = 0;
        this.resetColorMinute = 0;
    }

    public MagazineCClockPreviewView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.resetColorHour = 0;
        this.resetColorMinute = 0;
    }

    public MagazineCClockPreviewView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.resetColorHour = 0;
        this.resetColorMinute = 0;
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public float getScaleRadio() {
        return this.mStyle == 5 ? 0.185f : 0.174f;
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void init() {
        super.init();
        this.mTypefaceNeueMatic = Typeface.create("miclock-neue-matic-compressed-black", 0);
        this.mTypefaceMisans = FontUtils.getMiSansGlobal();
    }

    public final void initStyle1() {
        View viewInflate = View.inflate(this.mContext, R.layout.layout_miui_clock_magazine_c_type1, this);
        this.mClockContainerStyle1 = (ViewGroup) viewInflate.findViewById(R.id.clock_container_style1);
        this.mTimeContainerStyle1 = (ViewGroup) viewInflate.findViewById(R.id.time_container_style1);
        this.mDateTextStyle1 = (TextView) viewInflate.findViewById(R.id.current_date_style1);
        this.mWeekTextStyle1 = (TextView) viewInflate.findViewById(R.id.current_week_style1);
        this.mHourTextStyle1 = (MiuiTextGlassView) viewInflate.findViewById(R.id.current_time_hour_style1);
        this.mSpaceTimeStyle1 = (Space) findViewById(R.id.time_space_style1);
        this.mSpaceTimeEndStyle1 = (Space) findViewById(R.id.time_end_space_style1);
        this.mSpaceWeekStyle1 = (BaseLineSpaceView) viewInflate.findViewById(R.id.week_space_style1);
        this.mSpaceDateStyle1 = (BaseLineSpaceView) viewInflate.findViewById(R.id.date_space_style1);
        this.mMinuteTextStyle1 = (MiuiTextGlassView) viewInflate.findViewById(R.id.current_time_minute_style1);
    }

    public final void initStyle2() {
        View viewInflate = View.inflate(this.mContext, R.layout.layout_miui_clock_magazine_c_type2, this);
        this.mClockContainerStyle2 = (ViewGroup) viewInflate.findViewById(R.id.clock_container_style2);
        this.mHourTextStyle2 = (MiuiTextGlassView) viewInflate.findViewById(R.id.current_time_hour_style2);
        this.mMinuteTextStyle2 = (MiuiTextGlassView) viewInflate.findViewById(R.id.current_time_minute_style2);
        this.mSpaceTimeStyle2 = (BaseLineSpaceView) viewInflate.findViewById(R.id.time_space_style2);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(getDimen(R.dimen.miui_clock_small_view_width), getDimen(R.dimen.miui_clock_small_view_height));
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void setClockDarkMode(boolean z) {
        setDarkMode(z);
        if (z) {
            if (this.mStyle == 5) {
                TextView textView = this.mDateTextStyle1;
                int color = this.resetColorHour;
                if (color == 0) {
                    color = getResources().getColor(R.color.miui_clock_preview_dark_mode_color_90);
                }
                textView.setTextColor(color);
                TextView textView2 = this.mWeekTextStyle1;
                int color2 = this.resetColorHour;
                if (color2 == 0) {
                    color2 = getResources().getColor(R.color.miui_clock_preview_dark_mode_color_90);
                }
                textView2.setTextColor(color2);
                MiuiTextGlassView miuiTextGlassView = this.mHourTextStyle1;
                int color3 = this.resetColorHour;
                if (color3 == 0) {
                    color3 = getResources().getColor(R.color.miui_clock_preview_dark_mode_color_90);
                }
                miuiTextGlassView.setTextColor(color3);
                MiuiTextGlassView miuiTextGlassView2 = this.mMinuteTextStyle1;
                int color4 = this.resetColorMinute;
                if (color4 == 0) {
                    color4 = getResources().getColor(R.color.miui_clock_preview_light_mode_color_40);
                }
                miuiTextGlassView2.setTextColor(color4);
                return;
            }
            MiuiTextGlassView miuiTextGlassView3 = this.mHourTextStyle2;
            int color5 = this.resetColorHour;
            if (color5 == 0) {
                color5 = getResources().getColor(R.color.miui_clock_preview_dark_mode_color_90);
            }
            miuiTextGlassView3.setTextColor(color5);
            int i = this.mStyle;
            if (i == 6 || i == 9) {
                MiuiTextGlassView miuiTextGlassView4 = this.mMinuteTextStyle2;
                int color6 = this.resetColorMinute;
                if (color6 == 0) {
                    color6 = getResources().getColor(R.color.miui_clock_preview_dark_mode_color_90);
                }
                miuiTextGlassView4.setTextColor(color6);
                return;
            }
            if (i == 7) {
                MiuiTextGlassView miuiTextGlassView5 = this.mMinuteTextStyle2;
                int color7 = this.resetColorMinute;
                if (color7 == 0) {
                    color7 = getResources().getColor(R.color.miui_clock_preview_light_mode_color_40);
                }
                miuiTextGlassView5.setTextColor(color7);
                return;
            }
            MiuiTextGlassView miuiTextGlassView6 = this.mMinuteTextStyle2;
            int color8 = this.resetColorMinute;
            if (color8 == 0) {
                color8 = getResources().getColor(R.color.miui_clock_preview_light_mode_color_40);
            }
            miuiTextGlassView6.setTextColor(color8);
            return;
        }
        if (this.mStyle == 5) {
            TextView textView3 = this.mDateTextStyle1;
            int color9 = this.resetColorHour;
            if (color9 == 0) {
                color9 = getResources().getColor(R.color.magazine_c_preview_minute_color_00);
            }
            textView3.setTextColor(color9);
            TextView textView4 = this.mWeekTextStyle1;
            int color10 = this.resetColorHour;
            if (color10 == 0) {
                color10 = getResources().getColor(R.color.magazine_c_preview_minute_color_00);
            }
            textView4.setTextColor(color10);
            MiuiTextGlassView miuiTextGlassView7 = this.mHourTextStyle1;
            int color11 = this.resetColorHour;
            if (color11 == 0) {
                color11 = getResources().getColor(R.color.magazine_c_preview_minute_color_00);
            }
            miuiTextGlassView7.setTextColor(color11);
            MiuiTextGlassView miuiTextGlassView8 = this.mMinuteTextStyle1;
            int color12 = this.resetColorMinute;
            if (color12 == 0) {
                color12 = getResources().getColor(R.color.magazine_c_preview_minute_color_40);
            }
            miuiTextGlassView8.setTextColor(color12);
            return;
        }
        MiuiTextGlassView miuiTextGlassView9 = this.mHourTextStyle2;
        int color13 = this.resetColorHour;
        if (color13 == 0) {
            color13 = getResources().getColor(R.color.magazine_c_preview_minute_color_00);
        }
        miuiTextGlassView9.setTextColor(color13);
        int i2 = this.mStyle;
        if (i2 == 6 || i2 == 9) {
            MiuiTextGlassView miuiTextGlassView10 = this.mMinuteTextStyle2;
            int color14 = this.resetColorMinute;
            if (color14 == 0) {
                color14 = getResources().getColor(R.color.magazine_c_preview_minute_color_00);
            }
            miuiTextGlassView10.setTextColor(color14);
            return;
        }
        if (i2 == 7) {
            MiuiTextGlassView miuiTextGlassView11 = this.mMinuteTextStyle2;
            int color15 = this.resetColorMinute;
            if (color15 == 0) {
                color15 = getResources().getColor(R.color.magazine_c_preview_minute_color_40);
            }
            miuiTextGlassView11.setTextColor(color15);
            return;
        }
        MiuiTextGlassView miuiTextGlassView12 = this.mMinuteTextStyle2;
        int color16 = this.resetColorMinute;
        if (color16 == 0) {
            color16 = getResources().getColor(R.color.magazine_c_preview_minute_color_40);
        }
        miuiTextGlassView12.setTextColor(color16);
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void setStyle(int i) {
        super.setStyle(i);
        if (this.mStyle == 5) {
            initStyle1();
        } else {
            initStyle2();
        }
    }

    public void setTextColorHour(@ColorInt int i) {
        this.resetColorHour = i;
    }

    public void setTextColorMinute(@ColorInt int i) {
        this.resetColorMinute = i;
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void updateTime() {
        super.updateTime();
        String time = DateFormatUtils.formatTime(this.m24HourFormat ? "HHmm" : "hhmm");
        for (int i = 0; i < time.length(); i++) {
            this.mCurrentTimeArray[i] = Integer.parseInt(String.valueOf(time.charAt(i)));
        }
        if (this.mStyle == 5) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mClockContainerStyle1.getLayoutParams();
            marginLayoutParams.setMarginStart(getDimen(R.dimen.miui_clock_magazine_c_small_view_margin_start));
            marginLayoutParams.topMargin = getDimen(R.dimen.miui_clock_magazine_c_small_view_margin_top);
            this.mClockContainerStyle1.setLayoutParams(marginLayoutParams);
            this.mTimeContainerStyle1.getLayoutParams().width = getDimenWithScale(R.dimen.miui_magazine_c_clock_style1_width);
            int i2 = R.dimen.miui_magazine_c_clock_style1_text_size;
            float dimenWithScale = getDimenWithScale(i2);
            this.mDateTextStyle1.setTextSize(0, dimenWithScale);
            this.mHourTextStyle1.setTextSize(0, dimenWithScale);
            this.mMinuteTextStyle1.setTextSize(0, dimenWithScale);
            this.mTimeContainerStyle1.setContentDescription(DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
            TextView textView = this.mDateTextStyle1;
            int color = this.resetColorHour;
            if (color == 0) {
                color = getResources().getColor(R.color.magazine_c_preview_minute_color_00);
            }
            textView.setTextColor(color);
            TextView textView2 = this.mWeekTextStyle1;
            int color2 = this.resetColorHour;
            if (color2 == 0) {
                color2 = getResources().getColor(R.color.magazine_c_preview_minute_color_00);
            }
            textView2.setTextColor(color2);
            MiuiTextGlassView miuiTextGlassView = this.mHourTextStyle1;
            int color3 = this.resetColorHour;
            if (color3 == 0) {
                color3 = getResources().getColor(R.color.magazine_c_preview_minute_color_00);
            }
            miuiTextGlassView.setTextColor(color3);
            MiuiTextGlassView miuiTextGlassView2 = this.mMinuteTextStyle1;
            int color4 = this.resetColorMinute;
            if (color4 == 0) {
                color4 = getResources().getColor(R.color.magazine_c_preview_minute_color_40);
            }
            miuiTextGlassView2.setTextColor(color4);
            this.mDateTextStyle1.setText(DateFormatUtils.formatTime(this.mContext.getString(R.string.miui_magazine_clock_date)).toUpperCase());
            TextView textView3 = this.mDateTextStyle1;
            Calendar calendar = this.mCalendar;
            Context context = this.mContext;
            textView3.setContentDescription(calendar.format(context, context.getString(R.string.miui_magazine_clock_date_content_description)));
            Calendar calendar2 = this.mCalendar;
            Context context2 = this.mContext;
            String str = calendar2.format(context2, context2.getString(R.string.miui_magazine_c_clock_style1_week));
            this.mWeekTextStyle1.setContentDescription(str);
            this.mWeekTextStyle1.setText(str.toUpperCase());
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mSpaceWeekStyle1.getLayoutParams();
            ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mSpaceDateStyle1.getLayoutParams();
            ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mWeekTextStyle1.getLayoutParams();
            ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.mSpaceTimeStyle1.getLayoutParams();
            ConstraintLayout.LayoutParams layoutParams5 = (ConstraintLayout.LayoutParams) this.mSpaceTimeEndStyle1.getLayoutParams();
            if (FontUtils.isTypefaceSupport(str)) {
                this.mWeekTextStyle1.setTextSize(0, getDimenWithScale(i2));
                this.mWeekTextStyle1.setTypeface(this.mTypefaceNeueMatic);
                if (FontUtils.isCharHasChinese(str) || !FontUtils.isCharHasSoundmark(str)) {
                    ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimenWithScale(R.dimen.miui_magazine_c_clock_style1_week_margin_top);
                    ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimenWithScale(R.dimen.miui_magazine_c_clock_style1_date_margin_top);
                } else {
                    ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimenWithScale(R.dimen.miui_magazine_c_clock_style1_week_soundmark_margin_top);
                    ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimenWithScale(R.dimen.miui_magazine_c_clock_style1_date_soundmark_margin_top);
                }
                layoutParams3.baselineToBaseline = R.id.week_space_style1;
                layoutParams3.topToTop = -1;
            } else {
                if ("bo".equals(Locale.getDefault().getLanguage())) {
                    this.mWeekTextStyle1.setTextSize(0, getDimenWithScale(R.dimen.miui_magazine_c_clock_style1_bo_text_size));
                    ((ViewGroup.MarginLayoutParams) layoutParams5).topMargin = getDimenWithScale(R.dimen.miui_magazine_c_clock_style1_week_bo_margin_top);
                } else {
                    this.mWeekTextStyle1.setTextSize(0, getDimenWithScale(R.dimen.miui_magazine_c_clock_style1_notsupport_text_size));
                    ((ViewGroup.MarginLayoutParams) layoutParams5).topMargin = getDimenWithScale(R.dimen.miui_magazine_c_clock_style1_week_notsupport_margin_top);
                }
                layoutParams3.baselineToBaseline = -1;
                layoutParams3.topToTop = R.id.time_end_space_style1;
                this.mWeekTextStyle1.setTypeface(this.mTypefaceMisans);
                ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimenWithScale(R.dimen.miui_magazine_c_clock_style1_date_notsupport_margin_top);
            }
            ((ViewGroup.MarginLayoutParams) layoutParams4).bottomMargin = getDimenWithScale(R.dimen.miui_magazine_c_clock_style1_time_margin_bottom);
            MiuiTextGlassView miuiTextGlassView3 = this.mHourTextStyle1;
            Locale locale = Locale.ENGLISH;
            miuiTextGlassView3.setText(String.format(locale, "%d%d", Integer.valueOf(this.mCurrentTimeArray[0]), Integer.valueOf(this.mCurrentTimeArray[1])));
            this.mMinuteTextStyle1.setText(String.format(locale, "%d%d", Integer.valueOf(this.mCurrentTimeArray[2]), Integer.valueOf(this.mCurrentTimeArray[3])));
        } else {
            FrameLayout.LayoutParams layoutParams6 = (FrameLayout.LayoutParams) this.mClockContainerStyle2.getLayoutParams();
            layoutParams6.topMargin = getDimen(R.dimen.miui_clock_magazine_c_style2_small_view_margin_top);
            layoutParams6.gravity = 1;
            this.mClockContainerStyle2.setLayoutParams(layoutParams6);
            MiuiTextGlassView miuiTextGlassView4 = this.mHourTextStyle2;
            int color5 = this.resetColorHour;
            if (color5 == 0) {
                color5 = getResources().getColor(R.color.magazine_c_preview_minute_color_00);
            }
            miuiTextGlassView4.setTextColor(color5);
            int i3 = this.mStyle;
            if (i3 == 6) {
                MiuiTextGlassView miuiTextGlassView5 = this.mMinuteTextStyle2;
                int color6 = this.resetColorMinute;
                if (color6 == 0) {
                    color6 = getResources().getColor(R.color.magazine_c_preview_minute_color_00);
                }
                miuiTextGlassView5.setTextColor(color6);
            } else if (i3 == 7) {
                MiuiTextGlassView miuiTextGlassView6 = this.mMinuteTextStyle2;
                int color7 = this.resetColorMinute;
                if (color7 == 0) {
                    color7 = getResources().getColor(R.color.magazine_c_preview_minute_color_40);
                }
                miuiTextGlassView6.setTextColor(color7);
            } else if (i3 == 9) {
                MiuiTextGlassView miuiTextGlassView7 = this.mMinuteTextStyle2;
                int color8 = this.resetColorMinute;
                if (color8 == 0) {
                    color8 = getResources().getColor(R.color.magazine_c_preview_minute_color_00);
                }
                miuiTextGlassView7.setTextColor(color8);
            } else {
                MiuiTextGlassView miuiTextGlassView8 = this.mMinuteTextStyle2;
                int color9 = this.resetColorMinute;
                if (color9 == 0) {
                    color9 = getResources().getColor(R.color.magazine_c_preview_minute_color_40);
                }
                miuiTextGlassView8.setTextColor(color9);
            }
            if (this.mStyle == 9) {
                this.mHourTextStyle2.setTypeface(FontUtils.getNeuRounded(this.mContext));
                this.mMinuteTextStyle2.setTypeface(FontUtils.getNeuRounded(this.mContext));
            } else {
                this.mHourTextStyle2.setTypeface(this.mTypefaceNeueMatic);
                this.mMinuteTextStyle2.setTypeface(this.mTypefaceNeueMatic);
            }
            this.resetColorHour = 0;
            this.resetColorMinute = 0;
            float dimenWithScale2 = getDimenWithScale(R.dimen.miui_magazine_c_clock_style2_time_text_size) * 1.0f;
            this.mHourTextStyle2.setTextSize(0, dimenWithScale2);
            this.mMinuteTextStyle2.setTextSize(0, dimenWithScale2);
            ((ViewGroup.MarginLayoutParams) ((ConstraintLayout.LayoutParams) this.mSpaceTimeStyle2.getLayoutParams())).topMargin = (int) (getDimenWithScale(R.dimen.miui_magazine_c_clock_style2_time_margin_top) * 1.0f);
            int i4 = this.mStyle;
            if (i4 == 6) {
                this.mMinuteTextStyle2.setFontFeatureSettings(this.ssTags[this.mCurrentTimeArray[2]] + ac.b + this.ssTags[this.mCurrentTimeArray[3]]);
            } else if (i4 == 7) {
                this.mMinuteTextStyle2.setFontFeatureSettings("");
            } else if (i4 == 8) {
                this.mHourTextStyle2.setFontFeatureSettings(this.ssTags[this.mCurrentTimeArray[0]] + ac.b + this.ssTags[this.mCurrentTimeArray[1]]);
                this.mMinuteTextStyle2.setFontFeatureSettings(this.ssTags[this.mCurrentTimeArray[2]] + ac.b + this.ssTags[this.mCurrentTimeArray[3]]);
            } else if (i4 == 9) {
                this.mHourTextStyle2.setFontFeatureSettings("");
                this.mMinuteTextStyle2.setFontFeatureSettings("");
            }
            MiuiTextGlassView miuiTextGlassView9 = this.mHourTextStyle2;
            Locale locale2 = Locale.ENGLISH;
            miuiTextGlassView9.setText(String.format(locale2, "%d%d", Integer.valueOf(this.mCurrentTimeArray[0]), Integer.valueOf(this.mCurrentTimeArray[1])));
            this.mMinuteTextStyle2.setText(String.format(locale2, "%d%d", Integer.valueOf(this.mCurrentTimeArray[2]), Integer.valueOf(this.mCurrentTimeArray[3])));
            this.mClockContainerStyle2.setContentDescription(DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
        }
        setClockDarkMode(isDarkMode());
    }
}
