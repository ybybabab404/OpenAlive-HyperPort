package com.miui.clock.eastern.b;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.VectorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.MiuiClockPreviewBaseView;
import com.miui.clock.R;
import com.miui.clock.module.WeatherBean;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.WeatherFetcherController;
import java.util.Locale;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class EasternArtBClockPreviewView extends MiuiClockPreviewBaseView {
    public ImageView mColon;
    public VectorDrawable mColonBg;
    public String mCountry;
    public int mCurrentUserId;
    public TextView mData;
    public TextView mData2;
    public boolean mHasPresetData;
    public String mLanguage;
    public TextView mLocal;
    public TextView mTimeHour;
    public TextView mTimeMinute;
    public ViewGroup mTimeParent;
    public TextView mWeather;
    public TextView mWeather2;
    public WeatherBean mWeatherBean;
    public WeatherFetcherController mWeatherFetcherController;
    public TextView mWeek;
    public TextView mWeek2;
    public TextView mYear;

    public static /* synthetic */ void $r8$lambda$aRIT1HcAlA0DWm9FbuC5d8zLyPk(EasternArtBClockPreviewView easternArtBClockPreviewView, WeatherBean weatherBean) {
        if (easternArtBClockPreviewView.isAttachedToWindow()) {
            if (weatherBean != null) {
                easternArtBClockPreviewView.setWeatherBean(weatherBean);
                easternArtBClockPreviewView.updateWeatherInfo(weatherBean);
            } else {
                easternArtBClockPreviewView.setWeatherBean(null);
                easternArtBClockPreviewView.clearWeatherInfo();
            }
        }
    }

    public EasternArtBClockPreviewView(@NonNull Context context) {
        super(context);
    }

    public EasternArtBClockPreviewView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private boolean hasWeatherType() {
        return true;
    }

    public final void clearWeatherInfo() {
        this.mWeather.setText("--°");
        this.mWeather2.setText("--°");
        this.mLocal.setText("--");
        Log.d("EasternArtBClockPreviewView", "Clear Weather Info!");
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public int getDimen(int i) {
        return (int) (this.mContext.getResources().getDimensionPixelSize(i) * getScaleRadio());
    }

    public int getHourInt(boolean z, Calendar calendar) {
        if (z) {
            return calendar.get(18);
        }
        int i = calendar.get(18);
        if (i == 0) {
            return 12;
        }
        return i < 13 ? i : i - 12;
    }

    public int getMinuteInt(boolean z, Calendar calendar) {
        return calendar.get(20);
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public float getScaleRadio() {
        return 0.5f;
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void init() {
        super.init();
        View viewInflate = View.inflate(this.mContext, R.layout.miui_clock_layout_eastern_art_b_preview_view, this);
        this.mTimeParent = (ViewGroup) viewInflate.findViewById(R.id.eastern_art_b_preview_time_parent);
        this.mTimeHour = (TextView) viewInflate.findViewById(R.id.eastern_art_b_preview_time_hour);
        this.mTimeMinute = (TextView) viewInflate.findViewById(R.id.eastern_art_b_preview_time_minute);
        this.mWeek = (TextView) viewInflate.findViewById(R.id.eastern_art_b_preview_week_zh);
        this.mWeek2 = (TextView) viewInflate.findViewById(R.id.eastern_art_b_preview_week2);
        this.mYear = (TextView) viewInflate.findViewById(R.id.eastern_art_b_preview_year_zh);
        this.mData = (TextView) viewInflate.findViewById(R.id.eastern_art_b_preview_data_zh);
        this.mWeather = (TextView) viewInflate.findViewById(R.id.eastern_art_b_preview_weather_zh);
        this.mData2 = (TextView) viewInflate.findViewById(R.id.eastern_art_b_preview_data2);
        this.mLocal = (TextView) viewInflate.findViewById(R.id.eastern_art_b_preview_local);
        this.mWeather2 = (TextView) viewInflate.findViewById(R.id.eastern_art_b_preview_weather2);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.eastern_art_b_preview_colon);
        this.mColon = imageView;
        this.mColonBg = (VectorDrawable) imageView.getBackground();
        this.mLanguage = Locale.getDefault().getLanguage();
        this.mCountry = Locale.getDefault().getCountry();
    }

    public final boolean isChineseLanguageOrCountry() {
        if ("zh".equals(this.mLanguage)) {
            return "CN".equals(this.mCountry) || "HK".equals(this.mCountry) || "TW".equals(this.mCountry);
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        WeatherFetcherController weatherFetcherController = this.mWeatherFetcherController;
        if (weatherFetcherController != null) {
            weatherFetcherController.reset();
            this.mWeatherFetcherController = null;
        }
    }

    public final void queryDataAsync() {
        Context specialContext = WeatherFetcherController.Companion.getSpecialContext(this.mContext, this.mCurrentUserId);
        if (hasWeatherType()) {
            if (this.mWeatherFetcherController == null) {
                this.mWeatherFetcherController = new WeatherFetcherController();
            }
            this.mWeatherFetcherController.fetchWeatherData(specialContext, this.mHasPresetData, new WeatherFetcherController.Callback() { // from class: com.miui.clock.eastern.b.EasternArtBClockPreviewView$$ExternalSyntheticLambda0
                @Override // com.miui.clock.utils.WeatherFetcherController.Callback
                public final void updateWeatherInfo(WeatherBean weatherBean) {
                    EasternArtBClockPreviewView.$r8$lambda$aRIT1HcAlA0DWm9FbuC5d8zLyPk(this.f$0, weatherBean);
                }
            });
        }
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

    public void setWeatherBean(WeatherBean weatherBean) {
        this.mWeatherBean = weatherBean;
    }

    public void updateColor(int i) {
        this.mTimeHour.setTextColor(i);
        this.mTimeMinute.setTextColor(i);
        this.mWeek.setTextColor(i);
        this.mWeek2.setTextColor(i);
        this.mYear.setTextColor(i);
        this.mData.setTextColor(i);
        this.mWeather.setTextColor(i);
        this.mData2.setTextColor(i);
        this.mLocal.setTextColor(i);
        this.mWeather2.setTextColor(i);
        this.mColonBg.setColorFilter(new PorterDuffColorFilter(i, PorterDuff.Mode.SRC_IN));
    }

    public void updateLayoutParams() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mTimeParent.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mWeek.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mWeek2.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.mYear.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams5 = (ConstraintLayout.LayoutParams) this.mData.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams6 = (ConstraintLayout.LayoutParams) this.mWeather.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams7 = (ConstraintLayout.LayoutParams) this.mData2.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams8 = (ConstraintLayout.LayoutParams) this.mLocal.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams9 = (ConstraintLayout.LayoutParams) this.mWeather2.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams10 = (ConstraintLayout.LayoutParams) this.mColon.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_eastern_art_b_horizontal_below_time_margin_top);
        int i = R.dimen.miui_eastern_art_b_horizontal_upper_data_week_margin_top;
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimen(i);
        ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin = getDimen(i);
        int i2 = R.dimen.miui_eastern_art_b_horizontal_upper_data_year_margin_week_top;
        ((ViewGroup.MarginLayoutParams) layoutParams4).topMargin = getDimen(i2);
        int i3 = R.dimen.miui_eastern_art_b_horizontal_upper_data_data_margin_week_top;
        ((ViewGroup.MarginLayoutParams) layoutParams5).topMargin = getDimen(i3);
        int i4 = R.dimen.miui_eastern_art_b_horizontal_upper_data_weather_margin_week_top;
        ((ViewGroup.MarginLayoutParams) layoutParams6).topMargin = getDimen(i4);
        ((ViewGroup.MarginLayoutParams) layoutParams7).topMargin = getDimen(i2);
        ((ViewGroup.MarginLayoutParams) layoutParams8).topMargin = getDimen(i3);
        ((ViewGroup.MarginLayoutParams) layoutParams9).topMargin = getDimen(i4);
        int i5 = R.dimen.miui_eastern_art_b_horizontal_upper_text_height;
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = getDimen(i5);
        ((ViewGroup.MarginLayoutParams) layoutParams3).height = getDimen(i5);
        ((ViewGroup.MarginLayoutParams) layoutParams4).height = getDimen(i5);
        ((ViewGroup.MarginLayoutParams) layoutParams5).height = getDimen(i5);
        ((ViewGroup.MarginLayoutParams) layoutParams6).height = getDimen(i5);
        ((ViewGroup.MarginLayoutParams) layoutParams7).height = getDimen(i5);
        ((ViewGroup.MarginLayoutParams) layoutParams8).height = getDimen(i5);
        ((ViewGroup.MarginLayoutParams) layoutParams9).height = getDimen(i5);
        ((ViewGroup.MarginLayoutParams) layoutParams10).height = getDimen(R.dimen.miui_eastern_art_b_horizontal_below_colon_height);
        TextView textView = this.mTimeHour;
        int i6 = R.dimen.miui_eastern_art_b_horizontal_below_time_text_size;
        textView.setTextSize(0, getDimen(i6));
        this.mTimeMinute.setTextSize(0, getDimen(i6));
        TextView textView2 = this.mWeek;
        int i7 = R.dimen.miui_eastern_art_b_horizontal_upper_text_size;
        textView2.setTextSize(0, getDimen(i7));
        this.mWeek2.setTextSize(0, getDimen(i7));
        this.mYear.setTextSize(0, getDimen(i7));
        this.mData.setTextSize(0, getDimen(i7));
        this.mWeather.setTextSize(0, getDimen(i7));
        this.mData2.setTextSize(0, getDimen(i7));
        this.mLocal.setTextSize(0, getDimen(i7));
        this.mWeather2.setTextSize(0, getDimen(i7));
        Typeface miSerifForEasternArtB = FontUtils.getMiSerifForEasternArtB(this.mContext);
        this.mTimeHour.setTypeface(miSerifForEasternArtB);
        this.mTimeMinute.setTypeface(miSerifForEasternArtB);
        this.mTimeHour.setLetterSpacing(getScaleRadio() * 0.96f);
        this.mTimeMinute.setLetterSpacing(getScaleRadio() * 0.96f);
        this.mWeek.setTypeface(miSerifForEasternArtB);
        this.mWeek2.setTypeface(miSerifForEasternArtB);
        this.mYear.setTypeface(miSerifForEasternArtB);
        this.mData2.setTypeface(miSerifForEasternArtB);
        this.mData.setTypeface(miSerifForEasternArtB);
        this.mLocal.setTypeface(miSerifForEasternArtB);
        this.mWeather.setTypeface(miSerifForEasternArtB);
        this.mWeather2.setTypeface(miSerifForEasternArtB);
        setClockDarkMode(isDarkMode());
    }

    @Override // com.miui.clock.MiuiClockPreviewBaseView
    public void updateTime() {
        super.updateTime();
        queryDataAsync();
        this.mTimeHour.setText(ClassicClockTimeUtils.getTimeString(getHourInt(this.m24HourFormat, this.mCalendar), true));
        this.mTimeMinute.setText(ClassicClockTimeUtils.getTimeString(getMinuteInt(this.m24HourFormat, this.mCalendar), true));
        this.mWeek.setText(this.mCalendar.format(getContext(), getResources().getString(R.string.miui_eastern_art_b_week_view_format)));
        this.mYear.setText(ClassicClockTimeUtils.convertYearToChinese(this.mCalendar.format(getContext(), getResources().getString(R.string.miui_eastern_art_b_year_view_format))));
        this.mData.setText(getResources().getString(R.string.format_lunar_calendar_solar_terms, this.mCalendar.format(getContext(), getResources().getString(R.string.miui_eastern_art_b_data_view_format)), getResources().getString(R.string.format_lunar_calendar_solar_terms_continuous, ClassicClockTimeUtils.getSexagenaryCycleString(getContext(), this.mCalendar), ClassicClockTimeUtils.getLunarCalendarString(getContext(), this.mCalendar))));
        this.mWeek2.setText(this.mCalendar.format(getContext(), getResources().getString(R.string.format_full_day_of_week)));
        this.mData2.setText(this.mCalendar.format(getContext(), getResources().getString(R.string.miui_smart_frame_clock_date)));
        if (isChineseLanguageOrCountry()) {
            this.mWeek.setVisibility(0);
            this.mYear.setVisibility(0);
            this.mData.setVisibility(0);
            this.mWeather.setVisibility(0);
            this.mWeek2.setVisibility(8);
            this.mLocal.setVisibility(8);
            this.mData2.setVisibility(8);
            this.mWeather2.setVisibility(8);
        } else {
            this.mWeek.setVisibility(4);
            this.mYear.setVisibility(8);
            this.mData.setVisibility(8);
            this.mWeather.setVisibility(8);
            this.mWeek2.setVisibility(0);
            this.mData2.setVisibility(0);
            this.mWeather2.setVisibility(0);
            this.mLocal.setVisibility(0);
        }
        updateLayoutParams();
    }

    public void updateWeatherInfo(WeatherBean weatherBean) {
        if (weatherBean == null) {
            Log.d("EasternArtBClockPreviewView", "updateWeatherInfo  bean == null");
            return;
        }
        TextView textView = this.mWeather;
        Resources resources = getResources();
        int i = R.string.format_lunar_calendar_solar_terms;
        textView.setText(resources.getString(i, getResources().getString(R.string.format_lunar_calendar_solar_terms_continuous, weatherBean.getDescription(), "-"), Integer.valueOf(weatherBean.getTemperature())));
        this.mWeather2.setText(getResources().getString(i, weatherBean.getDescription(), Integer.valueOf(weatherBean.getTemperature())));
        this.mLocal.setText(weatherBean.getCityName());
    }
}
