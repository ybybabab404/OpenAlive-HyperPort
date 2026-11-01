package com.miui.clock.eastern.b;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.VectorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.R;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.ColorUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class EasternArtBTimeBelowClock extends EasternArtBBase {
    public static String TAG = "EasternArtBBase";
    public View mBackgroundBlurContainer;
    public ImageView mColon;
    public VectorDrawable mColonBg;
    public TextView mHour1;
    public TextView mHour2;
    public TextView mMinute1;
    public TextView mMinute2;
    public TextView mNotifyData;
    public ConstraintLayout mParent;

    /* JADX INFO: renamed from: com.miui.clock.eastern.b.EasternArtBTimeBelowClock$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.HOUR1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.HOUR2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.MIN1.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.MIN2.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_COLON.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.NOTIFICATION_DATE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public EasternArtBTimeBelowClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public EasternArtBTimeBelowClock(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private void updateTypeface() {
        setTextTypeFace(this.mHour1, 2, 430, 20, false);
        setTextTypeFace(this.mHour2, 2, 430, 20, false);
        setTextTypeFace(this.mMinute1, 2, 430, 20, false);
        setTextTypeFace(this.mMinute2, 2, 430, 20, false);
        this.mNotifyData.setTypeface(FontUtils.getMiSerifForEasternArtB(this.mContext));
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        EasternArtBBaseInfo easternArtBBaseInfo = this.mClockInfo;
        if (easternArtBBaseInfo == null) {
            return;
        }
        ClockEffectUtils.clearClockEffectsContainer(this.mBackgroundBlurContainer, easternArtBBaseInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mHour1, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mMinute1, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mHour2, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mMinute2, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mNotifyData, this.mClockInfo, z);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getBackgroundBlurContainer() {
        return this.mBackgroundBlurContainer;
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getGalleryGravity() {
        return 1;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        switch (AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()]) {
            case 1:
                return this.mHour1;
            case 2:
                return this.mHour2;
            case 3:
                return this.mMinute1;
            case 4:
                return this.mMinute2;
            case 5:
                return this.mColon;
            case 6:
                return this.mNotifyData;
            default:
                return super.getIClockView(clockViewType);
        }
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getMagazineColor() {
        EasternArtBBaseInfo easternArtBBaseInfo = this.mClockInfo;
        if (easternArtBBaseInfo != null) {
            return ColorUtils.blendColor(easternArtBBaseInfo.getOriginMagazineColor(), 0.6f);
        }
        return -1;
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockBottom() {
        return getDimen(R.dimen.miui_eastern_art_b_horizontal_upper_notification);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockTop() {
        return getDimen(R.dimen.miui_eastern_art_b_horizontal_below_notification_data_margin_top);
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public View getNotificationClockTopView() {
        return this.mNotifyData;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mParent = (ConstraintLayout) findViewById(R.id.time_below_view_parent);
        this.mHour1 = (TextView) findViewById(R.id.time_below_view_hour1);
        this.mHour2 = (TextView) findViewById(R.id.time_below_view_hour2);
        ImageView imageView = (ImageView) findViewById(R.id.time_below_view_colon);
        this.mColon = imageView;
        this.mColonBg = (VectorDrawable) imageView.getBackground();
        this.mMinute1 = (TextView) findViewById(R.id.time_below_view_minute1);
        this.mMinute2 = (TextView) findViewById(R.id.time_below_view_minute2);
        this.mNotifyData = (TextView) findViewById(R.id.time_below_view_data_notification);
        this.mBackgroundBlurContainer = findViewById(R.id.eastern_art_b_blur_container);
        updateTypeface();
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiBaseClock2
    public void onLanguageChanged() {
        super.onLanguageChanged();
        updateTypeface();
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map map, boolean z2) {
        super.setClockPalette(i, z, map, z2);
        if (this.mClockInfo == null || DeviceConfig.isHighTextContrastEnabled(this.mContext) || this.mClockInfo.isSuperSaveOpen()) {
            return;
        }
        updateClockBlendColor();
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        clearColorEffect(false);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2, int i3, int i4) {
        boolean z;
        boolean zUpdateTextDark = updateTextDark(this.mTextDark);
        int textDarkAlpha = getTextDarkAlpha(this.mTextDark);
        Log.d(TAG, "updateClockBlendColor mTextDark:" + this.mTextDark + "  updatedTextDark:" + zUpdateTextDark);
        EasternArtBBaseInfo easternArtBBaseInfo = this.mClockInfo;
        if (easternArtBBaseInfo == null || easternArtBBaseInfo.getDisplayType() != 32) {
            z = zUpdateTextDark;
        } else {
            textDarkAlpha = 255;
            z = false;
        }
        int i5 = textDarkAlpha;
        if (this.mClockInfo != null) {
            updateViewBackgroundBlurContainer(this.mBackgroundBlurContainer, getDimen(R.dimen.miui_eastern_art_c_clock_blur_radius), this.mHour1, this.mHour2, this.mMinute1, this.mMinute2, this.mNotifyData);
            TextView textView = this.mHour1;
            EasternArtBBaseInfo easternArtBBaseInfo2 = this.mClockInfo;
            ClockEffectUtils.setClockEffectsView(textView, easternArtBBaseInfo2, z, i, easternArtBBaseInfo2.getPrimaryColor(), i3, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()), i5);
            TextView textView2 = this.mMinute1;
            EasternArtBBaseInfo easternArtBBaseInfo3 = this.mClockInfo;
            ClockEffectUtils.setClockEffectsView(textView2, easternArtBBaseInfo3, z, i, easternArtBBaseInfo3.getPrimaryColor(), i3, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()), i5);
            TextView textView3 = this.mHour2;
            EasternArtBBaseInfo easternArtBBaseInfo4 = this.mClockInfo;
            ClockEffectUtils.setClockEffectsView(textView3, easternArtBBaseInfo4, z, i, easternArtBBaseInfo4.getPrimaryColor(), i3, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()), i5);
            TextView textView4 = this.mMinute2;
            EasternArtBBaseInfo easternArtBBaseInfo5 = this.mClockInfo;
            ClockEffectUtils.setClockEffectsView(textView4, easternArtBBaseInfo5, z, i, easternArtBBaseInfo5.getPrimaryColor(), i3, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()), i5);
            TextView textView5 = this.mNotifyData;
            EasternArtBBaseInfo easternArtBBaseInfo6 = this.mClockInfo;
            ClockEffectUtils.setClockEffectsView(textView5, easternArtBBaseInfo6, z, i, easternArtBBaseInfo6.getPrimaryColor(), i3, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()), i5);
            if (i2 != 0) {
                this.mColonBg.setColorFilter(new PorterDuffColorFilter(i2, PorterDuff.Mode.SRC_IN));
            }
        }
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockColor(int i, int i2) {
        updatePrimaryColor(i);
        updateSecondaryColor(i2);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        updateClockColor(this.mClockInfo.getPrimaryColor(), this.mClockInfo.getSecondaryColor());
    }

    public void updatePrimaryColor(int i) {
        this.mHour1.setTextColor(i);
        this.mMinute1.setTextColor(i);
        this.mHour2.setTextColor(i);
        this.mMinute2.setTextColor(i);
        this.mNotifyData.setTextColor(i);
    }

    public void updateSecondaryColor(int i) {
        Log.d(TAG, "updateSecondaryColor: " + i);
        if (i != 0) {
            this.mColonBg.setColorFilter(new PorterDuffColorFilter(i, PorterDuff.Mode.SRC_IN));
        }
    }

    @Override // com.miui.clock.eastern.b.EasternArtBBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        String timeString = ClassicClockTimeUtils.getTimeString(getHourInt(this.m24HourFormat, this.mCalendar), true);
        this.mHour1.setText("" + timeString.toCharArray()[0]);
        this.mHour2.setText("" + timeString.toCharArray()[1]);
        String timeString2 = ClassicClockTimeUtils.getTimeString(getMinuteInt(this.m24HourFormat, this.mCalendar), true);
        this.mMinute1.setText("" + timeString2.toCharArray()[0]);
        this.mMinute2.setText("" + timeString2.toCharArray()[1]);
        if (isChineseLanguageOrCountry()) {
            this.mNotifyData.setText(getResources().getString(R.string.format_lunar_calendar_solar_terms, this.mCalendar.format(getContext(), getResources().getString(R.string.miui_eastern_art_b_data_view_format)), this.mCalendar.format(getContext(), getResources().getString(R.string.miui_eastern_art_b_week_view_format))));
        } else {
            this.mNotifyData.setText(this.mCalendar.format(getContext(), getResources().getString(R.string.format_full_day_of_week)));
        }
        updateViewsLayoutParams();
        this.mParent.setContentDescription(this.mHour1.getText().toString() + this.mHour2.getText().toString() + ":" + this.mMinute1.getText().toString() + this.mMinute2.getText().toString());
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mParent.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mNotifyData.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_eastern_art_b_horizontal_below_time_margin_top);
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimen(R.dimen.miui_eastern_art_b_horizontal_below_notification_data_margin_top);
        float dimen = getDimen(R.dimen.miui_eastern_art_b_horizontal_below_time_text_size);
        this.mHour1.setTextSize(0, dimen);
        this.mMinute1.setTextSize(0, dimen);
        this.mHour2.setTextSize(0, dimen);
        this.mMinute2.setTextSize(0, dimen);
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mColon.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams3).width = getDimen(R.dimen.miui_eastern_art_b_horizontal_below_colon_width);
        ((ViewGroup.MarginLayoutParams) layoutParams3).height = getDimen(R.dimen.miui_eastern_art_b_horizontal_below_colon_height);
        this.mColon.setLayoutParams(layoutParams3);
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.mHour1.getLayoutParams();
        layoutParams4.setMarginEnd(getDimen(R.dimen.miui_eastern_art_b_horizontal_below_time_margin_right_value1));
        this.mHour1.setLayoutParams(layoutParams4);
        ConstraintLayout.LayoutParams layoutParams5 = (ConstraintLayout.LayoutParams) this.mHour2.getLayoutParams();
        int i = R.dimen.miui_eastern_art_b_horizontal_below_time_margin_right_value2;
        layoutParams5.setMarginEnd(getDimen(i));
        this.mHour2.setLayoutParams(layoutParams5);
        ConstraintLayout.LayoutParams layoutParams6 = (ConstraintLayout.LayoutParams) this.mMinute1.getLayoutParams();
        layoutParams6.setMarginStart(getDimen(i));
        this.mMinute1.setLayoutParams(layoutParams6);
        ConstraintLayout.LayoutParams layoutParams7 = (ConstraintLayout.LayoutParams) this.mMinute2.getLayoutParams();
        layoutParams7.setMarginStart(getDimen(R.dimen.miui_eastern_art_b_horizontal_below_time_margin_left_value1));
        this.mMinute2.setLayoutParams(layoutParams7);
        ConstraintLayout.LayoutParams layoutParams8 = (ConstraintLayout.LayoutParams) this.mBackgroundBlurContainer.getLayoutParams();
        layoutParams8.startToStart = this.mParent.getId();
        layoutParams8.endToEnd = this.mParent.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams8).height = getDimen(R.dimen.miui_eastern_art_b_horizontal_blur_container_height);
        this.mBackgroundBlurContainer.setLayoutParams(layoutParams8);
    }
}
