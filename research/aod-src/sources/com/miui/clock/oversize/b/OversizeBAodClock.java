package com.miui.clock.oversize.b;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.FontHumaneMono;
import com.miui.clock.module.FontHumaneRegularMono;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class OversizeBAodClock extends OversizeBBaseClock {
    public OversizeBAodInfo mClockInfo;
    public TextView mDate;
    public LinearLayout mFullDateWeek;
    public LinearLayout mTimeHour;
    public LinearLayout mTimeMinute;
    public LinearLayout mTimeView;
    public TextView mWeek;

    /* JADX INFO: renamed from: com.miui.clock.oversize.b.OversizeBAodClock$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.FULL_HOUR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_MINUTE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.HOUR1.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.HOUR2.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.MIN1.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.MIN2.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_WEEK.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_DATE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_DATE_WEEK.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    public OversizeBAodClock(Context context) {
        super(context);
    }

    public OversizeBAodClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public OversizeBAodClock(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        ClockEffectUtils.clearClockGradualEffect(this.mTimeHour, this.mClockInfo, z);
        ClockEffectUtils.clearClockGradualEffect(this.mTimeMinute, this.mClockInfo, z);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock
    public int getBatteryMarginStart() {
        if (DeviceConfig.inFoldLargeScreen(this.mContext)) {
            return !DeviceConfig.isRTL() ? getDimen(R.dimen.oversize_b_aod_margin_start) + getDimen(R.dimen.miui_oversize_b_aod_date_margin1) : ((getScreenWidth() - (getDimenByRatio(R.dimen.miui_oversize_b_time_w) * 4)) - getDimen(R.dimen.oversize_b_aod_margin_start)) + getDimen(R.dimen.miui_oversize_b_aod_date_margin1);
        }
        int screenWidth = (getScreenWidth() / 2) - (getDimenByRatio(R.dimen.miui_oversize_b_time_w) * 2);
        int i = R.dimen.miui_oversize_b_aod_date_margin1;
        int dimen = screenWidth + getDimen(i);
        if (DeviceConfig.isCupAndGupHighLevel()) {
            return dimen;
        }
        int i2 = R.dimen.miui_oversize_b_time_overlapping;
        return dimen - (((getDimenByRatio(i2) + (getDimenByRatio(i2) / 2)) + getDimen(i)) - getDimen(R.dimen.miui_oversize_b_aod_date_margin2));
    }

    public final int getDimenByRatio(int i) {
        return (int) (((double) (getResources().getDimensionPixelSize(i) * DeviceConfig.calculateScale(getContext()))) * 0.35d);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        switch (AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()]) {
            case 1:
                return this.mTimeHour;
            case 2:
                return this.mTimeMinute;
            case 3:
                return this.mHour1View;
            case 4:
                return this.mHour2View;
            case 5:
                return this.mMinute1View;
            case 6:
                return this.mMinute2View;
            case 7:
                return this.mWeek;
            case 8:
                return this.mDate;
            case 9:
                return this.mFullDateWeek;
            default:
                return super.getIClockView(clockViewType);
        }
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public float getTopMargin() {
        return getDimen(R.dimen.oversize_b_aod_margin_top);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTimeView = (LinearLayout) findViewById(R.id.time_view);
        this.mTimeHour = (LinearLayout) findViewById(R.id.oversize_b_hour);
        this.mTimeMinute = (LinearLayout) findViewById(R.id.oversize_b_minute);
        this.mDate = (TextView) findViewById(R.id.oversize_b_date);
        this.mWeek = (TextView) findViewById(R.id.oversize_b_week);
        this.mDate.setTypeface(FontUtils.getMiSans(330));
        this.mWeek.setTypeface(FontUtils.getMiSans(330));
        this.mFullDateWeek = (LinearLayout) findViewById(R.id.oversize_b_date_con);
        this.mTimeFontStyle = new FontHumaneMono();
        if (!DeviceConfig.isCupAndGupHighLevel() && !DeviceConfig.inFoldLargeScreen(this.mContext)) {
            this.mTimeFontStyle = new FontHumaneRegularMono();
        }
        this.mHour1View.setFontStyle(this.mTimeFontStyle);
        this.mHour2View.setFontStyle(this.mTimeFontStyle);
        this.mMinute1View.setFontStyle(this.mTimeFontStyle);
        this.mMinute2View.setFontStyle(this.mTimeFontStyle);
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void onLanguageChanged() {
        updateDateLayout();
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        this.mClockInfo = (OversizeBAodInfo) clockStyleInfo;
        super.setClockStyleInfo(clockStyleInfo);
        updateColor();
        updateViewsLayoutParams();
        updateDateLayout();
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockBlendColor() {
        updateClockBlendColor(this.mClockInfo.getAodBlendColor(), this.mClockInfo.getAodSecondaryBlendColor());
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2) {
        updateClockColor(i, i2);
        updateClockColor(this.mClockInfo.getAodBlendColor(), this.mClockInfo.getAodSecondaryBlendColor());
        updateGradientEffectColor(getCurrentGradientParams());
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        super.updateColor();
        OversizeBAodInfo oversizeBAodInfo = this.mClockInfo;
        if (oversizeBAodInfo != null) {
            this.mHour1View.updateColor(oversizeBAodInfo.getAodPrimaryColor());
            this.mHour2View.updateColor(this.mClockInfo.getAodPrimaryColor());
            this.mMinute1View.updateColor(this.mClockInfo.getAodSecondaryColor());
            this.mMinute2View.updateColor(this.mClockInfo.getAodSecondaryColor());
            if (this.mMagazineInfoVisible) {
                this.mDate.setTextColor(this.mClockInfo.getOriginMagazineColor());
                this.mWeek.setTextColor(this.mClockInfo.getOriginMagazineColor());
            } else {
                this.mDate.setTextColor(this.mClockInfo.getAodPrimaryColor());
                this.mWeek.setTextColor(this.mClockInfo.getAodPrimaryColor());
            }
        }
    }

    public void updateDateLayout() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mWeek.getLayoutParams();
        if (FontUtils.isChinese(this.mLanguage) || isFoldLandscape()) {
            this.mFullDateWeek.setOrientation(0);
            layoutParams.setMarginStart(getDimen(R.dimen.miui_oversize_b_date_margin));
        } else {
            this.mFullDateWeek.setOrientation(1);
            layoutParams.setMarginStart(0);
        }
        this.mWeek.setLayoutParams(layoutParams);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateGradientEffectColor(float[] fArr) {
        super.updateGradientEffectColor(fArr);
        OversizeBAodInfo oversizeBAodInfo = this.mClockInfo;
        if (oversizeBAodInfo != null && ClockEffectUtils.isGradualType(oversizeBAodInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            float[] fArrScaleGradientParams = ClockEffectUtils.scaleGradientParams(this.mClockInfo.getAodHourGradientParams(), getScaleByGradientDesign());
            float[] fArrScaleGradientParams2 = ClockEffectUtils.scaleGradientParams(this.mClockInfo.getAodMinuteGradientParams(), getScaleByGradientDesign());
            ClockEffectUtils.setClockGradualEffect(fArrScaleGradientParams, this.mTimeHour, this.mClockInfo, true, false, 1.0f);
            ClockEffectUtils.setClockGradualEffect(fArrScaleGradientParams2, this.mTimeMinute, this.mClockInfo, true, false, 1.0f);
        }
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        this.mHour1View.setIndex(this.mCurrentTimeArray[0]).build();
        this.mHour2View.setIndex(this.mCurrentTimeArray[1]).build();
        this.mMinute1View.setIndex(this.mCurrentTimeArray[2]).build();
        this.mMinute2View.setIndex(this.mCurrentTimeArray[3]).build();
        this.mTimeView.setContentDescription(DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
        this.mDate.setText(DateFormatUtils.formatTime(getResources().getString(R.string.format_month_day_style2)).toUpperCase());
        TextView textView = this.mDate;
        Calendar calendar = this.mCalendar;
        Context context = this.mContext;
        textView.setContentDescription(calendar.format(context, context.getString(R.string.miui_magazine_clock_date_content_description)));
        this.mWeek.setText(ClassicClockTimeUtils.getDayOfWeekString(this.mContext, this.mCalendar));
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        if (this.mClockInfo != null) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mTimeView.getLayoutParams();
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.oversize_b_aod_margin_top);
            if (DeviceConfig.FOLD_DEVICE && DeviceConfig.inFoldLargeScreen(getContext())) {
                layoutParams.rightToRight = -1;
                ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = getDimen(R.dimen.oversize_b_aod_margin_start);
            } else {
                ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = 0;
                layoutParams.rightToRight = 0;
            }
            this.mTimeView.setLayoutParams(layoutParams);
            int dimenByRatio = getDimenByRatio(R.dimen.miui_oversize_b_time_w);
            int dimenByRatio2 = getDimenByRatio(R.dimen.miui_oversize_b_time_h);
            this.mHour1View.setLayoutParams(new LinearLayout.LayoutParams(dimenByRatio, dimenByRatio2));
            this.mHour2View.setLayoutParams(new LinearLayout.LayoutParams(dimenByRatio, dimenByRatio2));
            this.mMinute1View.setLayoutParams(new LinearLayout.LayoutParams(dimenByRatio, dimenByRatio2));
            this.mMinute2View.setLayoutParams(new LinearLayout.LayoutParams(dimenByRatio, dimenByRatio2));
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mTimeHour.getLayoutParams();
            layoutParams2.setMarginEnd(0);
            this.mTimeHour.setLayoutParams(layoutParams2);
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mTimeMinute.getLayoutParams();
            layoutParams3.setMarginStart(0);
            this.mTimeMinute.setLayoutParams(layoutParams3);
            ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.mFullDateWeek.getLayoutParams();
            layoutParams4.setMarginStart(getDimen(R.dimen.miui_oversize_b_aod_date_margin1));
            this.mFullDateWeek.setLayoutParams(layoutParams4);
            if (!DeviceConfig.isCupAndGupHighLevel() && !DeviceConfig.inFoldLargeScreen(getContext())) {
                LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) this.mHour2View.getLayoutParams();
                int i = R.dimen.miui_oversize_b_time_overlapping;
                layoutParams5.setMarginStart(getDimenByRatio(i));
                this.mHour2View.setLayoutParams(layoutParams5);
                LinearLayout.LayoutParams layoutParams6 = (LinearLayout.LayoutParams) this.mMinute2View.getLayoutParams();
                layoutParams6.setMarginStart(getDimenByRatio(i));
                this.mMinute2View.setLayoutParams(layoutParams6);
                layoutParams2.setMarginEnd(getDimenByRatio(i) / 2);
                this.mTimeHour.setLayoutParams(layoutParams2);
                layoutParams3.setMarginStart(getDimenByRatio(i) / 2);
                this.mTimeMinute.setLayoutParams(layoutParams3);
                layoutParams4.setMarginStart(getDimen(R.dimen.miui_oversize_b_aod_date_margin2));
                this.mFullDateWeek.setLayoutParams(layoutParams4);
            }
            TextView textView = this.mDate;
            int i2 = R.dimen.oversize_b_aod_date_size;
            textView.setTextSize(0, getDimen(i2));
            this.mWeek.setTextSize(0, getDimen(i2));
        }
    }
}
