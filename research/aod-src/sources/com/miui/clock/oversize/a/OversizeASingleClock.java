package com.miui.clock.oversize.a;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.BaseFontStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.FontNeumaticCompressedB;
import com.miui.clock.module.FontNeumaticCompressedBF;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class OversizeASingleClock extends OversizeABase {
    public ViewGroup mClockContainer;
    public OversizeASingleClockInfo mClockInfo;
    public TextView mDateTextView;
    public FrameLayout mFullWeek;
    public LinearLayout mHourContainer;
    public LinearLayout mMinuteContainer;
    public TextView mWeekRoteTextView;
    public TextView mWeekTextView;

    public OversizeASingleClock(Context context) {
        super(context);
    }

    public OversizeASingleClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void setTypeFace() {
        this.mDateTextView.setTypeface(getMiSansDemiBold());
        this.mWeekTextView.setTypeface(getMiSansDemiBold());
        this.mWeekRoteTextView.setTypeface(getMiSansDemiBold());
    }

    private void switchWeekView() {
        if (isEastAsiaLanguage()) {
            this.mWeekRoteTextView.setVisibility(8);
            this.mWeekTextView.setVisibility(0);
        } else {
            this.mWeekRoteTextView.setVisibility(0);
            this.mWeekTextView.setVisibility(8);
        }
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        ClockEffectUtils.clearClockGradualEffect(this.mHourContainer, this.mClockInfo, z);
        ClockEffectUtils.clearClockGradualEffect(this.mMinuteContainer, this.mClockInfo, z);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        if (clockViewType != ClockViewType.FULL_TIME && clockViewType != ClockViewType.CLOCK_CONTAINER) {
            if (clockViewType == ClockViewType.HOUR1) {
                return this.mHour1View;
            }
            if (clockViewType == ClockViewType.HOUR2) {
                return this.mHour2View;
            }
            if (clockViewType == ClockViewType.MIN1) {
                return this.mMinute1View;
            }
            if (clockViewType == ClockViewType.MIN2) {
                return this.mMinute2View;
            }
            if (clockViewType == ClockViewType.FULL_WEEK) {
                return this.mFullWeek;
            }
            return clockViewType == ClockViewType.FULL_DATE ? this.mDateTextView : super.getIClockView(clockViewType);
        }
        return this.mClockContainer;
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public float getTopMargin() {
        return getDimen(R.dimen.miui_oversize_a_single_margin_top);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mClockContainer = (ViewGroup) findViewById(R.id.clock_container);
        this.mFullWeek = (FrameLayout) findViewById(R.id.full_week);
        this.mHourContainer = (LinearLayout) findViewById(R.id.hour_view);
        this.mMinuteContainer = (LinearLayout) findViewById(R.id.minute_view);
        this.mDateTextView = (TextView) findViewById(R.id.current_date);
        this.mWeekTextView = (TextView) findViewById(R.id.current_week);
        this.mWeekRoteTextView = (TextView) findViewById(R.id.current_week_rote);
        this.mDateTextView.setTypeface(getMiSansDemiBold());
        this.mWeekTextView.setTypeface(getMiSansDemiBold());
        this.mWeekRoteTextView.setTypeface(getMiSansDemiBold());
        setRoundRect(this.mFullWeek);
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiBaseClock2
    public void onLanguageChanged() {
        super.onLanguageChanged();
        setTypeFace();
        switchWeekView();
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        this.mClockInfo = (OversizeASingleClockInfo) clockStyleInfo;
        super.setClockStyleInfo(clockStyleInfo);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2) {
        super.updateClockBlendColor(i, i2);
        updateClockColor(this.mClockInfo.getAodBlendColor(), this.mClockInfo.getAodSecondaryBlendColor());
        updateGradientEffectColor(getCurrentGradientParams());
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        OversizeASingleClockInfo oversizeASingleClockInfo = this.mClockInfo;
        if (oversizeASingleClockInfo == null) {
            return;
        }
        this.mHour1View.setTextColor(oversizeASingleClockInfo.getAodPrimaryColor());
        this.mHour2View.setTextColor(this.mClockInfo.getAodPrimaryColor());
        this.mMinute1View.setTextColor(this.mClockInfo.getAodSecondaryColor());
        this.mMinute2View.setTextColor(this.mClockInfo.getAodSecondaryColor());
        this.mDateTextView.setTextColor(this.mClockInfo.getAodSecondaryColor());
        this.mFullWeek.setBackgroundColor(this.mClockInfo.getAodPrimaryColor());
        this.mWeekTextView.setTextColor(this.mClockInfo.getAodInfoColor());
        this.mWeekRoteTextView.setTextColor(this.mClockInfo.getAodInfoColor());
        Log.d("OversizeASingleClock", "aodPrimaryColor: " + this.mClockInfo.getAodPrimaryColor() + "    aodSecondaryColor:" + this.mClockInfo.getAodSecondaryColor());
        updateTime();
    }

    @Override // com.miui.clock.oversize.a.OversizeABase
    public void updateFontType() {
        BaseFontStyle fontNeumaticCompressedBF;
        BaseFontStyle fontNeumaticCompressedBF2;
        if (this.mClockInfo == null) {
            return;
        }
        if (DeviceConfig.isCupAndGupHighLevel()) {
            this.mHour1View.setFontStyle(new FontNeumaticCompressedB());
            this.mHour2View.setFontStyle(new FontNeumaticCompressedB());
            this.mMinute1View.setFontStyle(new FontNeumaticCompressedB());
            this.mMinute2View.setFontStyle(new FontNeumaticCompressedB());
            return;
        }
        if (this.mClockInfo.getClockStyle() == 2) {
            fontNeumaticCompressedBF = new FontNeumaticCompressedB();
            fontNeumaticCompressedBF2 = new FontNeumaticCompressedB();
        } else if (this.mClockInfo.getClockStyle() == 3) {
            fontNeumaticCompressedBF = new FontNeumaticCompressedBF();
            fontNeumaticCompressedBF2 = new FontNeumaticCompressedB();
        } else {
            fontNeumaticCompressedBF = new FontNeumaticCompressedBF();
            fontNeumaticCompressedBF2 = new FontNeumaticCompressedBF();
        }
        this.mHour1View.setFontStyle(fontNeumaticCompressedBF);
        this.mHour2View.setFontStyle(fontNeumaticCompressedBF);
        this.mMinute1View.setFontStyle(fontNeumaticCompressedBF2);
        this.mMinute2View.setFontStyle(fontNeumaticCompressedBF2);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateGradientEffectColor(float[] fArr) {
        super.updateGradientEffectColor(fArr);
        OversizeASingleClockInfo oversizeASingleClockInfo = this.mClockInfo;
        if (oversizeASingleClockInfo != null && ClockEffectUtils.isGradualType(oversizeASingleClockInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            float[] fArrScaleGradientParams = ClockEffectUtils.scaleGradientParams(this.mClockInfo.getAodHourGradientParams(), getScaleByGradientDesign());
            float[] fArrScaleGradientParams2 = ClockEffectUtils.scaleGradientParams(this.mClockInfo.getAodMinuteGradientParams(), getScaleByGradientDesign());
            ClockEffectUtils.setClockGradualEffect(fArrScaleGradientParams, this.mHourContainer, this.mClockInfo, true, false, 1.0f);
            ClockEffectUtils.setClockGradualEffect(fArrScaleGradientParams2, this.mMinuteContainer, this.mClockInfo, true, false, 1.0f);
        }
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        if (this.mClockInfo == null) {
            return;
        }
        this.mHour1View.setIndex(this.mCurrentTimeArray[0]).build();
        this.mHour2View.setIndex(this.mCurrentTimeArray[1]).build();
        this.mMinute1View.setIndex(this.mCurrentTimeArray[2]).build();
        this.mMinute2View.setIndex(this.mCurrentTimeArray[3]).build();
        this.mClockContainer.setContentDescription(DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
        this.mDateTextView.setText(DateFormatUtils.formatTime(this.mContext.getString(R.string.miui_oversize_a_date)).toUpperCase());
        TextView textView = this.mDateTextView;
        Calendar calendar = this.mCalendar;
        Context context = this.mContext;
        textView.setContentDescription(calendar.format(context, context.getString(R.string.miui_magazine_clock_date_content_description)));
        Calendar calendar2 = this.mCalendar;
        Context context2 = this.mContext;
        String str = calendar2.format(context2, context2.getString(R.string.miui_smart_frame_clock_week));
        this.mWeekTextView.setContentDescription(str);
        this.mWeekRoteTextView.setContentDescription(str);
        this.mWeekTextView.setText(str.toUpperCase());
        this.mWeekRoteTextView.setText(str.toUpperCase());
        switchWeekView();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mClockContainer.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_oversize_a_single_margin_top);
        this.mClockContainer.setLayoutParams(layoutParams);
        int dimen = (int) (((double) getDimen(R.dimen.miui_oversize_a_time_w)) * 0.4d);
        int dimen2 = (int) (((double) getDimen(R.dimen.miui_oversize_a_time_h)) * 0.4d);
        this.mHour1View.setLayoutParams(new LinearLayout.LayoutParams(dimen, dimen2));
        this.mHour2View.setLayoutParams(new LinearLayout.LayoutParams(dimen, dimen2));
        this.mMinute1View.setLayoutParams(new LinearLayout.LayoutParams(dimen, dimen2));
        this.mMinute2View.setLayoutParams(new LinearLayout.LayoutParams(dimen, dimen2));
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mMinuteContainer.getLayoutParams();
        layoutParams2.topMargin = getDimen(R.dimen.miui_oversize_a_single_minute_margin_top);
        this.mMinuteContainer.setLayoutParams(layoutParams2);
        int dimen3 = (int) (getDimen(R.dimen.miui_oversize_a_date_width) * 0.8f);
        float dimen4 = getDimen(R.dimen.miui_oversize_a_date_size) * 0.8f;
        this.mDateTextView.setTextSize(0, dimen4);
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mDateTextView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams3).width = dimen3;
        layoutParams3.setMarginStart(getDimen(R.dimen.miui_oversize_a_single_date_margin_left));
        this.mDateTextView.setLayoutParams(layoutParams3);
        int dimen5 = (int) (getDimen(R.dimen.miui_oversize_a_week_padding_left) * 0.8f);
        this.mWeekTextView.setPadding(dimen5, 0, dimen5, 0);
        this.mWeekTextView.setTextSize(0, dimen4);
        FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) this.mWeekTextView.getLayoutParams();
        layoutParams4.width = dimen3;
        this.mWeekTextView.setLayoutParams(layoutParams4);
        this.mWeekRoteTextView.setTextSize(0, dimen4);
        int dimen6 = (int) (getDimen(R.dimen.miui_oversize_a_week_padding_top) * 0.8f);
        this.mFullWeek.setPadding(0, dimen6, 0, dimen6);
        ConstraintLayout.LayoutParams layoutParams5 = (ConstraintLayout.LayoutParams) this.mFullWeek.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams5).width = dimen3;
        ((ViewGroup.MarginLayoutParams) layoutParams5).topMargin = getDimen(R.dimen.miui_oversize_a_single_week_margin_top);
        this.mFullWeek.setLayoutParams(layoutParams5);
    }
}
