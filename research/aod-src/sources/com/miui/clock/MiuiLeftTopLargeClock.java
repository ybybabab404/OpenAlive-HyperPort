package com.miui.clock;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import java.util.Map;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class MiuiLeftTopLargeClock extends MiuiBaseClock {
    public TextView mCurrentDateLarge;
    public FrameLayout mDateContainer;
    public String mLanguage;
    public boolean mTextDark;
    public TextView mTimeText;

    public MiuiLeftTopLargeClock(Context context) {
        this(context, null);
    }

    public MiuiLeftTopLargeClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTextDark = false;
        this.mLanguage = null;
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ View getBackgroundBlurContainer() {
        return super.getBackgroundBlurContainer();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ ClockStyleInfo getClockStyleInfo() {
        return super.getClockStyleInfo();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ Rect getDepthAvoidRect() {
        return super.getDepthAvoidRect();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getMagazineColor() {
        return super.getMagazineColor();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationClockTop() {
        return super.getNotificationClockTop();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ TextView getTimeView() {
        return super.getTimeView();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getTimeZone() {
        return super.getTimeZone();
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public float getTopMargin() {
        return this.mContext.getResources().getDimensionPixelSize(R.dimen.miui_left_top_clock_margin_top);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiBaseClock, android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public boolean isBoOrUgLanguage() {
        return "bo".equals(this.mLanguage) || "ug".equals(this.mLanguage);
    }

    @Override // com.miui.clock.MiuiBaseClock, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTimeText = (TextView) findViewById(R.id.current_time);
        this.mDateContainer = (FrameLayout) findViewById(R.id.left_top_date_container);
        this.mCurrentDateLarge = (TextView) findViewById(R.id.current_date_large);
        updateTime();
    }

    @Override // com.miui.clock.MiuiBaseClock
    public void onLanguageChanged(String str) {
        Typeface typefaceCreate;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mDateContainer.getLayoutParams();
        this.mLanguage = str;
        if (isBoOrUgLanguage()) {
            if ("bo".equals(str)) {
                layoutParams.topMargin = (int) (this.mScaleRatio * this.mResources.getDimensionPixelSize(R.dimen.miui_left_top_large_clock_date_info_top_margin_bo));
                layoutParams.bottomMargin = (int) (this.mScaleRatio * this.mResources.getDimensionPixelSize(R.dimen.miui_left_top_large_clock_date_info_bottom_margin));
                this.mCurrentDateLarge.setAlpha(0.65f);
                typefaceCreate = Typeface.create("miclock-thin-tibetan", 0);
            } else {
                layoutParams.topMargin = (int) (this.mScaleRatio * this.mResources.getDimensionPixelSize(R.dimen.miui_left_top_large_clock_date_info_top_margin_ug));
                layoutParams.bottomMargin = 0;
                this.mCurrentDateLarge.setAlpha(1.0f);
                typefaceCreate = Typeface.create("miclock-thin-ug", 0);
            }
            this.mCurrentDateLarge.setLineSpacing(0.0f, 1.0f);
            this.mCurrentDateLarge.setTypeface(typefaceCreate);
            this.mCurrentDateLarge.setIncludeFontPadding(false);
        } else {
            this.mCurrentDateLarge.setTypeface(Typeface.create(this.mTextDark ? "miclock-date" : "miclock-date-thin", 0));
            layoutParams.bottomMargin = 0;
            layoutParams.topMargin = (int) (this.mScaleRatio * this.mResources.getDimensionPixelSize(R.dimen.miui_left_top_large_clock_date_info_top_margin));
            this.mCurrentDateLarge.setLineSpacing(0.0f, 0.75f);
            this.mCurrentDateLarge.setAlpha(0.7f);
            this.mCurrentDateLarge.setIncludeFontPadding(true);
        }
        this.mDateContainer.setLayoutParams(layoutParams);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setBackgroundBlurContainer(View view) {
        super.setBackgroundBlurContainer(view);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setClockPalette(int i, boolean z, Map map, boolean z2) {
        super.setClockPalette(i, z, map, z2);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGradientIndex(int i) {
        super.setGradientIndex(i);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setSuperSaveOpen(boolean z) {
        super.setSuperSaveOpen(z);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void setTextColorDark(boolean z) {
        this.mTextDark = z;
        int color = z ? getContext().getResources().getColor(R.color.miui_common_time_dark_text_color) : -1;
        this.mTimeText.setTextColor(color);
        this.mCurrentDateLarge.setTextColor(color);
        setInfoDarkMode(color);
        this.mOwnerInfo.setTextColor(getContext().getResources().getColor(z ? R.color.miui_owner_info_dark_text_color : R.color.miui_owner_info_light_text_color));
        setTextFontDark(z);
    }

    @Override // com.miui.clock.MiuiBaseClock
    public void setTextFontDark(boolean z) {
        super.setTextFontDark(z);
        setFontStyleTextView(z, this.mTimeText);
        if (isBoOrUgLanguage()) {
            return;
        }
        Typeface typefaceCreate = Typeface.create("miclock-date-thin", 0);
        Typeface typefaceCreate2 = Typeface.create("miclock-date", 0);
        if (z) {
            typefaceCreate = typefaceCreate2;
        }
        this.mCurrentDateLarge.setTypeface(typefaceCreate);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.MiuiBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        this.mTimeText.setText(DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
        int i = this.m24HourFormat ? R.string.miui_lock_screen_large_date : R.string.miui_lock_screen_large_date_12;
        this.mCurrentDateLarge.setContentDescription(this.mCurrentDate.getText());
        TextView textView = this.mCurrentDateLarge;
        Calendar calendar = this.mCalendar;
        Context context = this.mContext;
        textView.setText(calendar.format(context, context.getString(i)).toUpperCase());
    }

    @Override // com.miui.clock.MiuiBaseClock
    public void updateViewsLayoutParams() {
        int i;
        float f;
        int dimensionPixelSize;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        if (this.mHasTopMargin) {
            if (isApplyFace()) {
                f = this.mScaleRatio;
                dimensionPixelSize = this.mResources.getDimensionPixelSize(R.dimen.miui_left_top_clock_margin_top_use_face);
            } else {
                f = this.mScaleRatio;
                dimensionPixelSize = this.mResources.getDimensionPixelSize(R.dimen.miui_left_top_clock_margin_top);
            }
            i = (int) (f * dimensionPixelSize);
        } else {
            i = 0;
        }
        layoutParams.topMargin = i;
        layoutParams.setMarginStart((int) (this.mScaleRatio * this.mResources.getDimensionPixelSize(R.dimen.miui_left_top_clock_margin_left)));
        setLayoutParams(layoutParams);
        String language = this.mContext.getResources().getConfiguration().locale.getLanguage();
        if (language.equals(this.mLanguage)) {
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mDateContainer.getLayoutParams();
            layoutParams2.topMargin = (int) (this.mScaleRatio * this.mResources.getDimensionPixelSize(R.dimen.miui_left_top_large_clock_date_info_top_margin));
            this.mDateContainer.setLayoutParams(layoutParams2);
        } else {
            onLanguageChanged(language);
        }
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mLunarCalendarInfo.getLayoutParams();
        layoutParams3.topMargin = (int) (this.mScaleRatio * this.mResources.getDimensionPixelSize(R.dimen.miui_clock_lunar_calendar_top_margin));
        float f2 = this.mScaleRatio;
        Resources resources = this.mResources;
        int i2 = R.dimen.left_top_clock_date_margin_extra;
        layoutParams3.setMarginStart((int) (f2 * resources.getDimensionPixelSize(i2)));
        this.mLunarCalendarInfo.setLayoutParams(layoutParams3);
        LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) this.mOwnerInfo.getLayoutParams();
        layoutParams4.topMargin = (int) (this.mScaleRatio * this.mResources.getDimensionPixelSize(R.dimen.miui_clock_owner_info_top_margin));
        layoutParams4.setMarginStart((int) (this.mScaleRatio * this.mResources.getDimensionPixelSize(i2)));
        this.mOwnerInfo.setLayoutParams(layoutParams4);
    }

    @Override // com.miui.clock.MiuiBaseClock
    public void updateViewsTextSize() {
        super.updateViewsTextSize();
        Resources resources = this.mContext.getResources();
        this.mTimeText.setTextSize(0, (int) (this.mScaleRatio * resources.getDimensionPixelSize(R.dimen.miui_left_top_clock_time_text_size)));
        this.mCurrentDateLarge.setTextSize(0, (int) (this.mScaleRatio * resources.getDimensionPixelSize(R.dimen.miui_left_top_large_clock_date_text_size)));
    }
}
