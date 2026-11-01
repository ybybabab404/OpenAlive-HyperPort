package com.miui.clock.oversize.b;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.Effect;
import com.miui.clock.oversize.OversizeSvgView;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.MiuiBlurUtils;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class OversizeBHourClock extends OversizeBBaseClock {
    public TextView mDate;
    public LinearLayout mFullDateWeek;
    public TextView mNotificationDateTextView;
    public Space mSpace;
    public LinearLayout mTimeHour;
    public TextView mWeek;

    /* JADX INFO: renamed from: com.miui.clock.oversize.b.OversizeBHourClock$1, reason: invalid class name */
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
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.HOUR1.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.HOUR2.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_WEEK.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_DATE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_DATE_WEEK.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.NOTIFICATION_DATE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public OversizeBHourClock(Context context) {
        super(context);
    }

    public OversizeBHourClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public OversizeBHourClock(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        OversizeBBaseInfo oversizeBBaseInfo = this.mClockInfo;
        if (oversizeBBaseInfo == null) {
            return;
        }
        ClockEffectUtils.clearClockEffectsContainer(this, oversizeBBaseInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mHour1View, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mHour2View, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mNotificationDateTextView, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mDate, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mWeek, this.mClockInfo, z);
        ClockEffectUtils.clearClockGradualEffect(this.mTimeHour, this.mClockInfo, z);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public float[] getGradientAnimationParams() {
        if (this.mClockInfo == null) {
            return Effect.EMPTY_GRADIENT;
        }
        getDimen(R.dimen.oversize_b_time_margin_start);
        getDimen(R.dimen.miui_oversize_b_time_w);
        if (DeviceConfig.FOLD_DEVICE && DeviceConfig.inFoldLargeScreen(getContext())) {
            getDimen(R.dimen.oversize_b_aod_margin_start);
        }
        if (!this.mInFaceUnlockStatus || DeviceConfig.inFoldLargeScreen(this.mContext)) {
            getDimen(R.dimen.oversize_b_time_margin_top);
        } else {
            getDimen(R.dimen.oversize_b_time_face_margin_top);
        }
        getDimen(R.dimen.oversize_b_aod_margin_top);
        return ClockEffectUtils.scaleGradientParams(this.mClockInfo.getAodGradientParams(), getScaleByGradientDesign(), 0.0f, 0.0f);
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
                return this.mHour1View;
            case 3:
                return this.mHour2View;
            case 4:
                return this.mWeek;
            case 5:
                return this.mDate;
            case 6:
                return this.mFullDateWeek;
            case 7:
                return this.mNotificationDateTextView;
            default:
                return super.getIClockView(clockViewType);
        }
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiBaseClock2
    public View getNotificationClockTopView() {
        TextView textView = this.mNotificationDateTextView;
        return (textView == null || textView.getVisibility() != 0 || this.mNotificationDateTextView.getAlpha() <= 0.0f) ? this.mTimeHour : this.mNotificationDateTextView;
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mSpace = (Space) findViewById(R.id.space);
        this.mTimeHour = (LinearLayout) findViewById(R.id.oversize_b_hour);
        this.mDate = (TextView) findViewById(R.id.oversize_b_date);
        this.mWeek = (TextView) findViewById(R.id.oversize_b_week);
        this.mDate.setTypeface(FontUtils.getMiSans(330));
        this.mWeek.setTypeface(FontUtils.getMiSans(330));
        TextView textView = (TextView) findViewById(R.id.oversize_b_date_notify);
        this.mNotificationDateTextView = textView;
        textView.setTypeface(FontUtils.getMiSans(500));
        this.mFullDateWeek = (LinearLayout) findViewById(R.id.oversize_b_date_con);
        updateViewsLayoutParams();
        setClipChildren(false);
        setClipToPadding(false);
        setClipToOutline(false);
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void onLanguageChanged() {
        updateViewsLayoutParams();
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
        super.setClockStyleInfo(clockStyleInfo);
        updateColor();
        updateTimeFontType();
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

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2, int i3, int i4) {
        boolean zIsAODType = ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType());
        boolean zIsFullAODType = ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType());
        boolean zUpdateTextDark = updateTextDark(this.mTextDark);
        int textDarkAlpha = getTextDarkAlpha(this.mTextDark);
        updateClockEffectsContainer();
        TextView textView = this.mNotificationDateTextView;
        OversizeBBaseInfo oversizeBBaseInfo = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView, oversizeBBaseInfo, !zIsFullAODType && zUpdateTextDark, i, oversizeBBaseInfo.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        TextView textView2 = this.mDate;
        OversizeBBaseInfo oversizeBBaseInfo2 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView2, oversizeBBaseInfo2, !zIsFullAODType && zUpdateTextDark, i, oversizeBBaseInfo2.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        TextView textView3 = this.mWeek;
        OversizeBBaseInfo oversizeBBaseInfo3 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView3, oversizeBBaseInfo3, !zIsFullAODType && zUpdateTextDark, i, oversizeBBaseInfo3.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        OversizeSvgView oversizeSvgView = this.mHour1View;
        OversizeBBaseInfo oversizeBBaseInfo4 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(oversizeSvgView, oversizeBBaseInfo4, !zIsFullAODType && zUpdateTextDark, i, oversizeBBaseInfo4.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        OversizeSvgView oversizeSvgView2 = this.mHour2View;
        OversizeBBaseInfo oversizeBBaseInfo5 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(oversizeSvgView2, oversizeBBaseInfo5, !zIsFullAODType && zUpdateTextDark, i, oversizeBBaseInfo5.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        ClockEffectUtils.setClockGradualEffect(getCurrentGradientParams(), this.mTimeHour, this.mClockInfo, zIsAODType, zIsFullAODType);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockColor(int i, int i2) {
        updatePrimaryColor(i);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockEffectsContainer() {
        super.updateClockEffectsContainer();
        updateViewBackgroundBlurContainer(this, getDimen(R.dimen.oversize_b_time_blur_radius), this.mHour1View, this.mHour2View, this.mNotificationDateTextView, this.mDate, this.mWeek);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        super.updateColor();
        updateClockColor(this.mClockInfo.getPrimaryColor(), this.mClockInfo.getSecondaryColor());
    }

    public void updateDateLayout() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mFullDateWeek.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.oversize_b_date_margin_top);
        layoutParams.setMarginStart((getDimen(R.dimen.oversize_b_date_margin_start) + getDimen(R.dimen.oversize_b_time_margin_start)) - getDimen(R.dimen.miui_oversize_b_space_margin_start));
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mWeek.getLayoutParams();
        if (FontUtils.isChinese(this.mLanguage) || isFoldLandscape()) {
            this.mFullDateWeek.setOrientation(0);
            layoutParams2.setMarginStart(getDimen(R.dimen.miui_oversize_b_date_margin));
        } else {
            this.mFullDateWeek.setOrientation(1);
            layoutParams2.setMarginStart(0);
        }
        this.mWeek.setLayoutParams(layoutParams2);
        this.mFullDateWeek.setLayoutParams(layoutParams);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateGradientEffectColor(float[] fArr) {
        super.updateGradientEffectColor(fArr);
        OversizeBBaseInfo oversizeBBaseInfo = this.mClockInfo;
        if (oversizeBBaseInfo != null && ClockEffectUtils.isGradualType(oversizeBBaseInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            MiuiBlurUtils.setGradientBlurMethod(this.mTimeHour, fArr);
        }
    }

    public void updatePrimaryColor(int i) {
        this.mHour1View.updateColor(i);
        this.mHour2View.updateColor(i);
        this.mDate.setTextColor(i);
        this.mWeek.setTextColor(i);
        this.mNotificationDateTextView.setTextColor(i);
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        this.mHour1View.setIndex(this.mCurrentTimeArray[0]).build();
        this.mHour2View.setIndex(this.mCurrentTimeArray[1]).build();
        this.mTimeHour.setContentDescription(DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
        this.mDate.setText(DateFormatUtils.formatTime(getResources().getString(R.string.format_month_day_style2)).toUpperCase());
        TextView textView = this.mDate;
        Calendar calendar = this.mCalendar;
        Context context = this.mContext;
        textView.setContentDescription(calendar.format(context, context.getString(R.string.miui_magazine_clock_date_content_description)));
        this.mWeek.setText(ClassicClockTimeUtils.getDayOfFullWeekString(this.mContext, this.mCalendar));
        this.mNotificationDateTextView.setText(ClassicClockTimeUtils.getDateWithWeekString(this.mContext, this.mCalendar));
    }

    @Override // com.miui.clock.oversize.b.OversizeBBaseClock
    public void updateTimeFontType() {
        super.updateTimeFontType();
        this.mHour1View.setFontStyle(this.mTimeFontStyle);
        this.mHour2View.setFontStyle(this.mTimeFontStyle);
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mSpace.getLayoutParams();
        layoutParams.setMarginEnd(getDimen(R.dimen.miui_oversize_b_space_margin_start));
        this.mSpace.setLayoutParams(layoutParams);
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mTimeHour.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = (!this.mInFaceUnlockStatus || DeviceConfig.inFoldLargeScreen(this.mContext)) ? getDimen(R.dimen.oversize_b_time_margin_top) : getDimen(R.dimen.oversize_b_time_face_margin_top);
        ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin = getDimen(R.dimen.oversize_b_time_margin_start);
        this.mTimeHour.setLayoutParams(layoutParams2);
        int dimen = getDimen(R.dimen.miui_oversize_b_time_w);
        int dimen2 = getDimen(R.dimen.miui_oversize_b_time_h);
        this.mHour1View.setLayoutParams(new LinearLayout.LayoutParams(dimen, dimen2));
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(dimen, dimen2);
        layoutParams3.setMarginStart(getDimen(R.dimen.miui_oversize_b_time_overlapping));
        this.mHour2View.setLayoutParams(layoutParams3);
        TextView textView = this.mDate;
        int i = R.dimen.oversize_b_date_text_size;
        textView.setTextSize(0, getDimen(i));
        this.mWeek.setTextSize(0, getDimen(i));
        this.mNotificationDateTextView.setTextSize(0, getDimen(R.dimen.oversize_b_notify_date_size));
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.mNotificationDateTextView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams4).topMargin = this.mInFaceUnlockStatus ? getDimen(R.dimen.miui_rhombus_small_clock_margin_top) : getDimen(R.dimen.oversize_b_date_notify_top_margin);
        this.mNotificationDateTextView.setLayoutParams(layoutParams4);
        updateDateLayout();
        updateTimeFontType();
    }
}
