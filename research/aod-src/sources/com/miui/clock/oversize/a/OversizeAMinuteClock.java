package com.miui.clock.oversize.a;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.FontNeumaticCompressedB;
import com.miui.clock.module.FontNeumaticCompressedBF;
import com.miui.clock.oversize.OversizeSvgView;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.MiuiBlurUtils;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class OversizeAMinuteClock extends OversizeABase {
    public TextView mDateTextView;
    public View mFullWeek;
    public View mMagazineInfoContainer;
    public TextView mNotificationDateTextView;
    public LinearLayout mTimeView;
    public View mWeek;
    public View mWeekBg;
    public TextView mWeekRoteTextView;
    public TextView mWeekTextView;

    /* JADX INFO: renamed from: com.miui.clock.oversize.a.OversizeAMinuteClock$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.FULL_MINUTE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.MIN1.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.MIN2.ordinal()] = 3;
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
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.NOTIFICATION_DATE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public OversizeAMinuteClock(Context context) {
        super(context);
    }

    public OversizeAMinuteClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void adaptFoldWeekView() {
        if (DeviceConfig.FOLD_DEVICE && !isCnLanguage()) {
            int adaptMargin = getAdaptMargin();
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mTimeView.getLayoutParams();
            if (layoutParams.getMarginEnd() != adaptMargin) {
                layoutParams.setMarginEnd(adaptMargin);
                this.mTimeView.setLayoutParams(layoutParams);
            }
        }
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        OversizeABaseInfo oversizeABaseInfo = this.mClockInfo;
        if (oversizeABaseInfo == null) {
            return;
        }
        ClockEffectUtils.clearClockEffectsContainer(this, oversizeABaseInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mMinute1View, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mMinute2View, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mDateTextView, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mWeekBg, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mNotificationDateTextView, this.mClockInfo, z);
        ClockEffectUtils.clearClockGradualEffect(this.mTimeView, this.mClockInfo, z);
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
        switch (AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()]) {
            case 1:
                return this.mTimeView;
            case 2:
                return this.mMinute1View;
            case 3:
                return this.mMinute2View;
            case 4:
                return this.mFullWeek;
            case 5:
                return this.mDateTextView;
            case 6:
                return this.mNotificationDateTextView;
            default:
                return super.getIClockView(clockViewType);
        }
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiBaseClock2
    public View getNotificationClockTopView() {
        return this.mTimeView;
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTimeView = (LinearLayout) findViewById(R.id.time_view);
        this.mDateTextView = (TextView) findViewById(R.id.current_date);
        this.mWeekTextView = (TextView) findViewById(R.id.current_week);
        this.mWeekRoteTextView = (TextView) findViewById(R.id.current_week_rote);
        this.mFullWeek = findViewById(R.id.full_week);
        this.mWeekBg = findViewById(R.id.week_bg);
        this.mWeek = findViewById(R.id.week);
        this.mNotificationDateTextView = (TextView) findViewById(R.id.notification_date);
        this.mMagazineInfoContainer = findViewById(R.id.magazine_info_container);
        this.mDateTextView.setTypeface(getMiSansDemiBold());
        this.mWeekTextView.setTypeface(getMiSansDemiBold());
        this.mWeekRoteTextView.setTypeface(getMiSansDemiBold());
        this.mNotificationDateTextView.setTypeface(FontUtils.getMiSans(380));
        setRoundRect(this.mWeekBg);
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiBaseClock2
    public void onLanguageChanged() {
        super.onLanguageChanged();
        setTypeFace();
        switchWeekView();
        adaptFoldWeekView();
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

    public final void setTypeFace() {
        this.mDateTextView.setTypeface(getMiSansDemiBold());
        this.mWeekTextView.setTypeface(getMiSansDemiBold());
        this.mWeekRoteTextView.setTypeface(getMiSansDemiBold());
        this.mNotificationDateTextView.setTypeface(FontUtils.getMiSans(380));
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    public final void switchWeekView() {
        if (isEastAsiaLanguage()) {
            this.mWeekRoteTextView.setVisibility(8);
            this.mWeekTextView.setVisibility(0);
        } else {
            this.mWeekRoteTextView.setVisibility(0);
            this.mWeekTextView.setVisibility(8);
        }
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2, int i3, int i4) {
        super.updateClockBlendColor(i, i2, i3, i4);
        boolean zIsAODType = ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType());
        boolean zIsFullAODType = ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType());
        boolean zUpdateTextDark = updateTextDark(this.mTextDark);
        int textDarkAlpha = getTextDarkAlpha(this.mTextDark);
        updateViewBackgroundBlurContainer(this, getDimen(R.dimen.miui_oversize_a_time_blur_radius), this.mMinute1View, this.mMinute2View, this.mWeekBg, this.mNotificationDateTextView, this.mDateTextView);
        if (ClockEffectUtils.isBlurMixType(this.mClockInfo.getClockEffect()) || ClockEffectUtils.isDifferenceType(this.mClockInfo.getClockEffect())) {
            View view = this.mWeekBg;
            OversizeABaseInfo oversizeABaseInfo = this.mClockInfo;
            ClockEffectUtils.setClockEffectsView(view, oversizeABaseInfo, !zIsFullAODType && zUpdateTextDark, i, oversizeABaseInfo.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
            TextView textView = this.mNotificationDateTextView;
            OversizeABaseInfo oversizeABaseInfo2 = this.mClockInfo;
            ClockEffectUtils.setClockEffectsView(textView, oversizeABaseInfo2, !zIsFullAODType && zUpdateTextDark, i2, oversizeABaseInfo2.getSecondaryColor(), i4, zIsAODType, zIsFullAODType, textDarkAlpha);
            TextView textView2 = this.mDateTextView;
            OversizeABaseInfo oversizeABaseInfo3 = this.mClockInfo;
            ClockEffectUtils.setClockEffectsView(textView2, oversizeABaseInfo3, !zIsFullAODType && zUpdateTextDark, i2, oversizeABaseInfo3.getSecondaryColor(), i4, zIsAODType, zIsFullAODType, textDarkAlpha);
        }
        OversizeSvgView oversizeSvgView = this.mMinute1View;
        OversizeABaseInfo oversizeABaseInfo4 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(oversizeSvgView, oversizeABaseInfo4, !zIsFullAODType && zUpdateTextDark, i2, oversizeABaseInfo4.getSecondaryColor(), i4, zIsAODType, zIsFullAODType, textDarkAlpha);
        OversizeSvgView oversizeSvgView2 = this.mMinute2View;
        OversizeABaseInfo oversizeABaseInfo5 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(oversizeSvgView2, oversizeABaseInfo5, !zIsFullAODType && zUpdateTextDark, i2, oversizeABaseInfo5.getSecondaryColor(), i4, zIsAODType, zIsFullAODType, textDarkAlpha);
        ClockEffectUtils.setClockGradualEffect(getCurrentGradientParams(), this.mTimeView, this.mClockInfo, zIsAODType, zIsFullAODType, getScaleByGradientDesign());
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        OversizeABaseInfo oversizeABaseInfo = this.mClockInfo;
        if (oversizeABaseInfo == null) {
            return;
        }
        if (ClockStyleInfo.isFullAODType(oversizeABaseInfo.getDisplayType())) {
            this.mMinute1View.setTextColor(this.mClockInfo.getFullAodSecondaryColor());
            this.mMinute2View.setTextColor(this.mClockInfo.getFullAodSecondaryColor());
            this.mDateTextView.setTextColor(this.mClockInfo.getFullAodSecondaryColor());
            this.mNotificationDateTextView.setTextColor(this.mClockInfo.getFullAodSecondaryColor());
            this.mWeekBg.setBackgroundColor(this.mClockInfo.getFullAodPrimaryColor());
            this.mWeekTextView.setTextColor(this.mClockInfo.getFullAodInfoColor());
        } else {
            this.mMinute1View.setTextColor(this.mClockInfo.getSecondaryColor());
            this.mMinute2View.setTextColor(this.mClockInfo.getSecondaryColor());
            this.mDateTextView.setTextColor(this.mClockInfo.getSecondaryColor());
            this.mNotificationDateTextView.setTextColor(this.mClockInfo.getSecondaryColor());
            this.mWeekBg.setBackgroundColor(this.mClockInfo.getPrimaryColor());
            this.mWeekTextView.setTextColor(this.mClockInfo.getInfoAreaColor());
        }
        this.mWeekRoteTextView.setTextColor(this.mClockInfo.getInfoAreaColor());
        updateTime();
    }

    @Override // com.miui.clock.oversize.a.OversizeABase
    public void updateFontType() {
        OversizeABaseInfo oversizeABaseInfo = this.mClockInfo;
        if (oversizeABaseInfo == null) {
            return;
        }
        if (oversizeABaseInfo.getClockStyle() == 2 || this.mClockInfo.getClockStyle() == 3) {
            this.fontStyle = new FontNeumaticCompressedB();
        } else {
            this.fontStyle = new FontNeumaticCompressedBF();
        }
        this.mMinute1View.setFontStyle(this.fontStyle);
        this.mMinute2View.setFontStyle(this.fontStyle);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateGradientEffectColor(float[] fArr) {
        super.updateGradientEffectColor(fArr);
        OversizeABaseInfo oversizeABaseInfo = this.mClockInfo;
        if (oversizeABaseInfo != null && ClockEffectUtils.isGradualType(oversizeABaseInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            MiuiBlurUtils.setGradientBlurMethod(this.mTimeView, fArr, getScaleByGradientDesign());
        }
    }

    @Override // com.miui.clock.oversize.a.OversizeABase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        if (this.mClockInfo == null) {
            return;
        }
        this.mMinute1View.setIndex(this.mCurrentTimeArray[2]).build();
        this.mMinute2View.setIndex(this.mCurrentTimeArray[3]).build();
        this.mTimeView.setContentDescription(DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
        TextView textView = this.mNotificationDateTextView;
        Calendar calendar = this.mCalendar;
        Context context = this.mContext;
        textView.setText(calendar.format(context, context.getString(R.string.miui_lock_screen_date)));
        this.mDateTextView.setText(DateFormatUtils.formatTime(this.mContext.getString(R.string.miui_oversize_a_date)).toUpperCase());
        TextView textView2 = this.mDateTextView;
        Calendar calendar2 = this.mCalendar;
        Context context2 = this.mContext;
        textView2.setContentDescription(calendar2.format(context2, context2.getString(R.string.miui_magazine_clock_date_content_description)));
        this.mWeekTextView.setContentDescription(this.mWeekStr);
        this.mWeekRoteTextView.setContentDescription(this.mWeekStr);
        this.mWeekTextView.setText(this.mWeekStr.toUpperCase());
        this.mWeekRoteTextView.setText(this.mWeekStr.toUpperCase());
        switchWeekView();
        adaptFoldWeekView();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        int dimen;
        int dimen2;
        super.updateViewsLayoutParams();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mTimeView.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mMinute1View.getLayoutParams();
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mMinute2View.getLayoutParams();
        int dimen3 = getDimen(R.dimen.miui_oversize_a_date_margin_top);
        if (DeviceConfig.FOLD_DEVICE && DeviceConfig.inFoldLargeScreen(this.mContext)) {
            if (getResources().getConfiguration().orientation == 2) {
                dimen2 = getDimen(R.dimen.miui_oversize_a_fold_left_landscape);
                dimen = getDimen(R.dimen.miui_oversize_a_date_margin_top_fold);
            } else {
                dimen = dimen3;
                dimen2 = getDimen(R.dimen.miui_oversize_a_fold_left);
            }
            layoutParams.startToStart = -1;
            layoutParams.endToEnd = 0;
            layoutParams.topToTop = 0;
            layoutParams.bottomToBottom = 0;
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = 0;
            layoutParams.setMarginEnd(dimen2);
            int i = R.dimen.miui_oversize_a_time_h;
            layoutParams2.height = (int) (((double) getDimen(i)) * 0.9d);
            int i2 = R.dimen.miui_oversize_a_time_w;
            layoutParams2.width = (int) (((double) getDimen(i2)) * 0.9d);
            layoutParams3.height = (int) (((double) getDimen(i)) * 0.9d);
            layoutParams3.width = (int) (((double) getDimen(i2)) * 0.9d);
            dimen3 = dimen;
        } else {
            layoutParams.startToStart = 0;
            layoutParams.endToEnd = 0;
            layoutParams.topToTop = 0;
            layoutParams.bottomToBottom = -1;
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_oversize_a_minute_margin_top);
            layoutParams.setMarginEnd(0);
            int i3 = R.dimen.miui_oversize_a_time_h;
            layoutParams2.height = getDimen(i3);
            int i4 = R.dimen.miui_oversize_a_time_w;
            layoutParams2.width = getDimen(i4);
            layoutParams3.height = getDimen(i3);
            layoutParams3.width = getDimen(i4);
        }
        this.mTimeView.setLayoutParams(layoutParams);
        this.mMinute1View.setLayoutParams(layoutParams2);
        this.mMinute2View.setLayoutParams(layoutParams3);
        TextView textView = this.mDateTextView;
        int i5 = R.dimen.miui_oversize_a_date_size;
        textView.setTextSize(0, getDimen(i5));
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.mDateTextView.getLayoutParams();
        int i6 = R.dimen.miui_oversize_a_date_width;
        ((ViewGroup.MarginLayoutParams) layoutParams4).width = getDimen(i6);
        ((ViewGroup.MarginLayoutParams) layoutParams4).topMargin = dimen3;
        int i7 = R.dimen.miui_oversize_a_date_margin_right;
        layoutParams4.setMarginEnd(getDimen(i7));
        this.mDateTextView.setLayoutParams(layoutParams4);
        int dimen4 = getDimen(R.dimen.miui_oversize_a_week_padding_top);
        int dimen5 = getDimen(i6);
        int dimen6 = getDimen(i5);
        this.mWeek.setPadding(0, dimen4, 0, dimen4);
        ConstraintLayout.LayoutParams layoutParams5 = (ConstraintLayout.LayoutParams) this.mFullWeek.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams5).width = dimen5;
        ((ViewGroup.MarginLayoutParams) layoutParams5).topMargin = getDimen(R.dimen.miui_oversize_a_week_margin_top);
        layoutParams5.setMarginEnd(getDimen(i7));
        this.mFullWeek.setLayoutParams(layoutParams5);
        int dimen7 = getDimen(R.dimen.miui_oversize_a_week_padding_left);
        this.mWeekTextView.setPadding(dimen7, 0, dimen7, 0);
        float f = dimen6;
        this.mWeekTextView.setTextSize(0, f);
        FrameLayout.LayoutParams layoutParams6 = (FrameLayout.LayoutParams) this.mWeekTextView.getLayoutParams();
        layoutParams6.width = dimen5;
        this.mWeekTextView.setLayoutParams(layoutParams6);
        this.mWeekRoteTextView.setTextSize(0, f);
        this.mNotificationDateTextView.setTextSize(0, getDimen(R.dimen.miui_rhombus_small_date_text_size));
        ConstraintLayout.LayoutParams layoutParams7 = (ConstraintLayout.LayoutParams) this.mNotificationDateTextView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams7).topMargin = getDimen(R.dimen.miui_rhombus_small_clock_margin_top);
        this.mNotificationDateTextView.setLayoutParams(layoutParams7);
        ConstraintLayout.LayoutParams layoutParams8 = (ConstraintLayout.LayoutParams) this.mMagazineInfoContainer.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams8).topMargin = getDimen(R.dimen.miui_oversize_a_hour_margin_top);
        layoutParams8.setMarginStart(getDimen(i7));
        this.mMagazineInfoContainer.setLayoutParams(layoutParams8);
    }
}
