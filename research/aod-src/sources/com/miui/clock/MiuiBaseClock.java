package com.miui.clock;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.utils.DateFormatUtils;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class MiuiBaseClock extends LinearLayout implements MiuiClockController.IClockView {
    public boolean m24HourFormat;
    public Calendar mCalendar;
    public int mCalendarDayOfWeek;
    public Context mContext;
    public TextView mCurrentDate;
    public int mDensityDpi;
    public float mFontScale;
    public boolean mFontScaleChanged;
    public boolean mHasTopMargin;
    public String mLanguage;
    public TextView mLunarCalendarInfo;
    public TextView mOwnerInfo;
    public Resources mResources;
    public float mScaleRatio;
    public boolean mShowLunarCalendar;

    public MiuiBaseClock(Context context) {
        this(context, null);
    }

    public MiuiBaseClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mResources = null;
        this.mHasTopMargin = true;
        this.mScaleRatio = 1.0f;
        this.mContext = context;
        this.mResources = context.getResources();
        updateHourFormat();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ View getBackgroundBlurContainer() {
        return super.getBackgroundBlurContainer();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public int getClockHeight() {
        if (getHeight() > 0) {
            return getHeight();
        }
        return 0;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ ClockStyleInfo getClockStyleInfo() {
        return super.getClockStyleInfo();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public float getClockVisibleHeight() {
        if (getHeight() > 0) {
            return getHeight();
        }
        return 0.0f;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ Rect getDepthAvoidRect() {
        return super.getDepthAvoidRect();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        return this;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    public View getLunarCalendarView() {
        return this.mLunarCalendarInfo;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getMagazineColor() {
        return super.getMagazineColor();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockBottom() {
        return getBottom() + getResources().getDimensionPixelSize(R.dimen.notification_margin_top);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationClockTop() {
        return super.getNotificationClockTop();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ TextView getTimeView() {
        return super.getTimeView();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getTimeZone() {
        return super.getTimeZone();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public float getTopMargin() {
        return 0.0f;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public boolean isApplyFace() {
        return Settings.Secure.getInt(this.mContext.getContentResolver(), "face_unlcok_apply_for_lock", 0) != 0;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        updateViewsLayoutParams();
        updateViewsTextSize();
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        float f = configuration.fontScale;
        if (this.mFontScale != f) {
            this.mFontScaleChanged = true;
            updateViewsTextSize();
            this.mFontScale = f;
        }
        int i = configuration.densityDpi;
        if (this.mDensityDpi != i) {
            this.mFontScaleChanged = true;
            updateViewsTextSize();
            updateViewsLayoutParams();
            this.mDensityDpi = i;
        }
        String language = configuration.locale.getLanguage();
        if (TextUtils.isEmpty(language) || language.equals(this.mLanguage)) {
            return;
        }
        this.mLanguage = language;
        onLanguageChanged(language);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mCurrentDate = (TextView) findViewById(R.id.current_date);
        this.mLunarCalendarInfo = (TextView) findViewById(R.id.unlock_screen_lunar_calendar_info);
        this.mOwnerInfo = (TextView) findViewById(R.id.unlock_screen_owner_info);
        this.mCalendar = new Calendar();
        updateLunarCalendarInfo();
    }

    public void onLanguageChanged(String str) {
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setBackgroundBlurContainer(View view) {
        super.setBackgroundBlurContainer(view);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setClockAlpha(float f) {
        setAlpha(f);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setClockPalette(int i, boolean z, Map map, boolean z2) {
        super.setClockPalette(i, z, map, z2);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    public void setFontStyleTextView(boolean z, TextView textView) {
        Typeface typefaceCreate = Typeface.create("miclock-time-thin", 0);
        Typeface typefaceCreate2 = Typeface.create("miclock-time", 0);
        if (z) {
            typefaceCreate = typefaceCreate2;
        }
        textView.setTypeface(typefaceCreate);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGradientIndex(int i) {
        super.setGradientIndex(i);
    }

    public void setInfoDarkMode(int i) {
        this.mCurrentDate.setTextColor(i);
        this.mLunarCalendarInfo.setTextColor(i);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    public void setIs24HourFormat(boolean z) {
        this.m24HourFormat = z;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setMagazineInfoVisible(boolean z) {
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setOwnerInfo(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mOwnerInfo.setVisibility(8);
        } else {
            this.mOwnerInfo.setVisibility(0);
            this.mOwnerInfo.setText(str);
        }
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setScaleRatio(float f) {
        this.mScaleRatio = f;
        updateViewsTextSize();
        try {
            updateViewsLayoutParams();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setShowLunarCalendar(boolean z) {
        this.mShowLunarCalendar = z;
        updateLunarCalendarInfo();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setSuperSaveOpen(boolean z) {
        super.setSuperSaveOpen(z);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setTextColorDark(boolean z) {
        this.mOwnerInfo.setTextColor(z ? getContext().getResources().getColor(R.color.miui_owner_info_dark_text_color) : getContext().getResources().getColor(R.color.miui_owner_info_light_text_color));
        setTextFontDark(z);
    }

    public void setTextFontDark(boolean z) {
        Typeface typefaceCreate = Typeface.create("mipro-regular", 0);
        Typeface typefaceCreate2 = Typeface.create("mipro-medium", 0);
        if (z) {
            typefaceCreate = typefaceCreate2;
        }
        this.mCurrentDate.setTypeface(typefaceCreate);
        this.mLunarCalendarInfo.setTypeface(typefaceCreate);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    public void updateHourFormat() {
        this.m24HourFormat = DateFormatUtils.is24HourFormat(this.mContext);
    }

    public void updateLunarCalendarInfo() {
        if (!Locale.CHINESE.getLanguage().equals(Locale.getDefault().getLanguage()) || !this.mShowLunarCalendar) {
            this.mLunarCalendarInfo.setVisibility(8);
            return;
        }
        Calendar calendar = new Calendar();
        this.mLunarCalendarInfo.setVisibility(0);
        this.mLunarCalendarInfo.setText(calendar.format(this.mContext, "YY年 N月e"));
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateResidentTimeZone(String str) {
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        int i = this.m24HourFormat ? R.string.miui_lock_screen_date : R.string.miui_lock_screen_date_12;
        TextView textView = this.mCurrentDate;
        Calendar calendar = this.mCalendar;
        Context context = this.mContext;
        textView.setText(calendar.format(context, context.getString(i)));
        int i2 = this.mCalendar.get(14);
        if (i2 != this.mCalendarDayOfWeek) {
            updateLunarCalendarInfo();
            this.mCalendarDayOfWeek = i2;
        }
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateTimeZone(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mCalendar = new Calendar(TimeZone.getTimeZone(str));
        updateTime();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateViewTopMargin(boolean z) {
        this.mHasTopMargin = z;
        try {
            updateViewsLayoutParams();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateViewsLayoutParams() {
    }

    public void updateViewsTextSize() {
        Resources resources = this.mContext.getResources();
        float f = this.mScaleRatio;
        int i = R.dimen.miui_clock_date_text_size;
        float dimensionPixelSize = (int) (f * resources.getDimensionPixelSize(i));
        this.mCurrentDate.setTextSize(0, dimensionPixelSize);
        this.mLunarCalendarInfo.setTextSize(0, dimensionPixelSize);
        this.mOwnerInfo.setTextSize(0, (int) (this.mScaleRatio * resources.getDimensionPixelSize(i)));
    }
}
