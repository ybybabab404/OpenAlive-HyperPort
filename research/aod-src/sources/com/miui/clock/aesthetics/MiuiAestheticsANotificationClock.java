package com.miui.clock.aesthetics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.Guideline;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.MutiDeviceDimenAdapter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class MiuiAestheticsANotificationClock extends MiuiAestheticsABase {
    public Guideline guidelineV50;
    public ConstraintLayout liClockContainer;
    public TextView tvData;
    public TextView tvTime;

    /* JADX INFO: renamed from: com.miui.clock.aesthetics.MiuiAestheticsANotificationClock$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.FULL_TIME.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_DATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public MiuiAestheticsANotificationClock(Context context) {
        super(context);
    }

    public MiuiAestheticsANotificationClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MiuiAestheticsANotificationClock(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private void initTextParams() {
        Typeface miSans = FontUtils.getMiSans(450);
        this.tvTime.setTypeface(FontUtils.getAestheticsTypeFace(this.clockIsWide));
        this.tvData.setTypeface(miSans);
        float multiDeviceDimen = this.clockIsWide ? MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.miui_aesthetics_a_data_wide_text_size, true) : getDimen(R.dimen.miui_aesthetics_a_data_narrow_text_size);
        this.tvTime.setTextSize(0, getDimen(this.clockIsWide ? R.dimen.miui_aesthetics_a_notification_text_size : R.dimen.miui_aesthetics_a_hour_narrow_text_size));
        this.tvData.setTextSize(0, multiDeviceDimen);
    }

    private void initViewParams() {
        Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(-2, -2);
        layoutParams.topToTop = 0;
        layoutParams.startToStart = 0;
        layoutParams.endToEnd = 0;
        Constraints.LayoutParams layoutParams2 = new Constraints.LayoutParams(-2, -2);
        layoutParams2.topToTop = 0;
        layoutParams2.leftToLeft = 0;
        layoutParams2.rightToRight = 0;
        Constraints.LayoutParams layoutParams3 = new Constraints.LayoutParams(-2, -2);
        Constraints.LayoutParams layoutParams4 = new Constraints.LayoutParams(-2, -2);
        layoutParams3.topToTop = this.liClockContainer.getId();
        layoutParams3.leftToLeft = this.liClockContainer.getId();
        layoutParams3.rightToRight = this.liClockContainer.getId();
        layoutParams4.topToTop = this.liClockContainer.getId();
        layoutParams4.leftToLeft = this.liClockContainer.getId();
        layoutParams4.rightToRight = this.liClockContainer.getId();
        layoutParams.setMargins(0, getDimen(R.dimen.miui_aesthetics_a_notification_magazine_margin_top), 0, 0);
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.miui_aesthetics_a_clock_notification_margin_top, false);
        if (this.clockIsWide) {
            layoutParams3.setMargins(0, getDimen(R.dimen.miui_aesthetics_a_notification_wide_time_margin_top), 0, 0);
        } else {
            layoutParams3.setMargins(0, getDimen(R.dimen.miui_aesthetics_a_notification_narrow_time_margin_top), 0, 0);
        }
        this.mMagazineGroup.setLayoutParams(layoutParams);
        this.liClockContainer.setLayoutParams(layoutParams2);
        this.tvTime.setLayoutParams(layoutParams3);
        this.tvData.setLayoutParams(layoutParams4);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo = this.mClockInfo;
        if (miuiAestheticsClockBaseInfo == null) {
            return;
        }
        ClockEffectUtils.clearClockEffectsContainer(this.liClockContainer, miuiAestheticsClockBaseInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.tvTime, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.tvData, this.mClockInfo, z);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i != 1) {
            return i != 2 ? super.getIClockView(clockViewType) : this.tvData;
        }
        return this.tvTime;
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public View getNotificationClockTopView() {
        return this.liClockContainer;
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase
    public void initView() {
        super.initView();
        this.liClockContainer = (ConstraintLayout) findViewById(R.id.li_clock_container);
        int i = R.id.guideline;
        Guideline guideline = (Guideline) findViewById(i);
        this.guidelineV50 = guideline;
        guideline.setGuidelinePercent(0.5f);
        this.guidelineV50 = (Guideline) findViewById(i);
        this.tvTime = (TextView) findViewById(R.id.tv_time);
        this.tvData = (TextView) findViewById(R.id.tv_data);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        super.setClockPalette(i, z, map, z2);
        updateClockBlendColor(this.mClockInfo.getBlendColor(), this.mClockInfo.getSecondaryBlendColor());
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase
    public void setClockStyle(int i) {
        super.setClockStyle(i);
        initViewParams();
        initTextParams();
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2, int i3, int i4) {
        boolean zUpdateTextDark = updateTextDark(this.mTextDark);
        int textDarkAlpha = getTextDarkAlpha(this.mTextDark);
        ConstraintLayout constraintLayout = this.liClockContainer;
        int dimen = getDimen(R.dimen.miui_aesthetics_a_clock_blur_radius);
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo = this.mClockInfo;
        ClockEffectUtils.setClockEffectsContainer(constraintLayout, dimen, miuiAestheticsClockBaseInfo, ClockStyleInfo.isAODType(miuiAestheticsClockBaseInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()));
        TextView textView = this.tvTime;
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo2 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView, miuiAestheticsClockBaseInfo2, zUpdateTextDark, i, miuiAestheticsClockBaseInfo2.getPrimaryColor(), i3, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()), textDarkAlpha);
        TextView textView2 = this.tvData;
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo3 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView2, miuiAestheticsClockBaseInfo3, zUpdateTextDark, i, miuiAestheticsClockBaseInfo3.getPrimaryColor(), i3, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()), textDarkAlpha);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockColor(int i, int i2) {
        this.tvTime.setTextColor(i);
        this.tvData.setTextColor(i);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        super.updateColor();
        updateClockColor(this.mClockInfo.getPrimaryColor(), this.mClockInfo.getSecondaryColor());
    }

    @Override // com.miui.clock.aesthetics.MiuiAestheticsABase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        this.tvTime.setText(LocalTime.now().format(DateTimeFormatter.ofPattern(getResources().getString(this.m24HourFormat ? R.string.miui_aesthetics_a_notification_format_24 : R.string.miui_aesthetics_a_notification_format_12), Locale.US)));
        String language = Locale.getDefault().getLanguage();
        String country = Locale.getDefault().getCountry();
        if (!language.equals("zh") || !country.equals("CN")) {
            this.tvData.setText(this.mCalendar.format(getContext(), getResources().getString(R.string.format_month_day_week)).toUpperCase());
            return;
        }
        TextView textView = this.tvData;
        Calendar calendar = this.mCalendar;
        Context context = getContext();
        Resources resources = getResources();
        int i = R.string.miui_aesthetics_a_data_format;
        textView.setText(calendar.format(context, resources.getString(i)));
        this.tvData.setContentDescription(this.mCalendar.format(getContext(), getContext().getString(i)));
    }
}
