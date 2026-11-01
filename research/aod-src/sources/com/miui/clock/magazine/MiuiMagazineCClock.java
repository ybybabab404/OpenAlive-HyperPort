package com.miui.clock.magazine;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import com.miui.clock.MiuiTextGlassView;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.FontNeueMatic;
import com.miui.clock.utils.BaseLineSpaceView;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.MiuiBlurUtils;
import com.xiaomi.onetrack.util.ac;
import java.util.Locale;
import java.util.Map;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class MiuiMagazineCClock extends MiuiMagazineCBase {
    public ViewGroup mClockContainerStyle1;
    public ViewGroup mClockContainerStyle2;
    public TextView mDateTextStyle1;
    public TextView mDateTextStyle2;
    public float mDeviceScale;
    public MiuiTextGlassView mHourTextStyle1;
    public MiuiTextGlassView mHourTextStyle2;
    public MiuiTextGlassView mMinuteTextStyle1;
    public MiuiTextGlassView mMinuteTextStyle2;
    public TextView mNotificationDateTextStyle1;
    public BaseLineSpaceView mSpaceDateStyle1;
    public Space mSpaceTimeEndStyle1;
    public Space mSpaceTimeStyle1;
    public BaseLineSpaceView mSpaceTimeStyle2;
    public BaseLineSpaceView mSpaceWeekStyle1;
    public Guideline mStyle1MagazineGuideline;
    public ViewGroup mTimeContainerStyle1;
    public Typeface mTypefaceMisans;
    public Typeface mTypefaceNeuRounded;
    public Typeface mTypefaceNeueMatic;
    public TextView mWeekTextStyle1;
    public boolean supportAccessibilityScale;

    /* JADX INFO: renamed from: com.miui.clock.magazine.MiuiMagazineCClock$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.FULL_DATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_WEEK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.CLOCK_CONTAINER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_HOUR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_MINUTE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.NOTIFICATION_DATE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public MiuiMagazineCClock(Context context) {
        super(context);
        this.mDeviceScale = 1.0f;
        this.supportAccessibilityScale = true;
    }

    public MiuiMagazineCClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDeviceScale = 1.0f;
        this.supportAccessibilityScale = true;
    }

    private boolean isPreviewType() {
        MiuiMagazineCBaseInfo miuiMagazineCBaseInfo = this.mClockInfo;
        return miuiMagazineCBaseInfo != null && 16 == miuiMagazineCBaseInfo.displayType;
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        if (this.mClockInfo == null) {
            return;
        }
        if (!ClockEffectUtils.isLessThanOS2()) {
            ClockEffectUtils.clearClockEffectsContainer(this.mClockContainerStyle1, this.mClockInfo, z);
            ClockEffectUtils.clearClockEffectsContainer(this.mClockContainerStyle2, this.mClockInfo, z);
            ClockEffectUtils.clearClockEffectsView(this.mDateTextStyle1, this.mClockInfo, z);
            ClockEffectUtils.clearClockEffectsView(this.mWeekTextStyle1, this.mClockInfo, z);
            ClockEffectUtils.clearClockEffectsView(this.mHourTextStyle1, this.mClockInfo, z);
            ClockEffectUtils.clearClockEffectsView(this.mNotificationDateTextStyle1, this.mClockInfo, z);
            ClockEffectUtils.clearClockEffectsView(this.mMinuteTextStyle1, this.mClockInfo, z);
            ClockEffectUtils.clearClockEffectsView(this.mHourTextStyle2, this.mClockInfo, z);
            ClockEffectUtils.clearClockEffectsView(this.mMinuteTextStyle2, this.mClockInfo, z);
            ClockEffectUtils.clearClockGradualEffect(this.mClockContainerStyle1, this.mClockInfo, z);
            ClockEffectUtils.clearClockGradualEffect(this.mClockContainerStyle2, this.mClockInfo, z);
            return;
        }
        if (!DeviceConfig.supportBackgroundBlur(this.mContext) || DeviceConfig.isHighTextContrastEnabled(this.mContext)) {
            MiuiBlurUtils.clearContainerPassBlur(this.mClockContainerStyle1);
            MiuiBlurUtils.clearMemberBlendColor(this.mDateTextStyle1);
            MiuiBlurUtils.clearMemberBlendColor(this.mWeekTextStyle1);
            MiuiBlurUtils.clearMemberBlendColor(this.mNotificationDateTextStyle1);
            MiuiBlurUtils.clearMemberBlendColor(this.mHourTextStyle1);
            MiuiBlurUtils.clearMemberBlendColor(this.mMinuteTextStyle1);
            MiuiBlurUtils.clearContainerPassBlur(this.mClockContainerStyle2);
            MiuiBlurUtils.clearMemberBlendColor(this.mHourTextStyle2);
            MiuiBlurUtils.clearMemberBlendColor(this.mMinuteTextStyle2);
            return;
        }
        if (this.mClockInfo.getClockStyle() == 5) {
            if (!this.mClockInfo.isAutoPrimaryColor() && !this.mClockInfo.isAutoSecondaryColor()) {
                MiuiBlurUtils.clearContainerPassBlur(this.mClockContainerStyle1);
            }
            if (!this.mClockInfo.isAutoPrimaryColor()) {
                MiuiBlurUtils.clearMemberBlendColor(this.mDateTextStyle1);
                MiuiBlurUtils.clearMemberBlendColor(this.mWeekTextStyle1);
                MiuiBlurUtils.clearMemberBlendColor(this.mNotificationDateTextStyle1);
                MiuiBlurUtils.clearMemberBlendColor(this.mHourTextStyle1);
            }
            if (!this.mClockInfo.isAutoSecondaryColor()) {
                MiuiBlurUtils.clearMemberBlendColor(this.mMinuteTextStyle1);
            }
            MiuiBlurUtils.clearContainerPassBlur(this.mClockContainerStyle2);
            MiuiBlurUtils.clearMemberBlendColor(this.mHourTextStyle2);
            MiuiBlurUtils.clearMemberBlendColor(this.mMinuteTextStyle2);
            return;
        }
        if (this.mClockInfo.getClockStyle() == 6 || this.mClockInfo.getClockStyle() == 9) {
            if (!this.mClockInfo.isAutoPrimaryColor()) {
                MiuiBlurUtils.clearContainerPassBlur(this.mClockContainerStyle2);
                MiuiBlurUtils.clearMemberBlendColor(this.mHourTextStyle2);
                MiuiBlurUtils.clearMemberBlendColor(this.mMinuteTextStyle2);
            }
            MiuiBlurUtils.clearContainerPassBlur(this.mClockContainerStyle1);
            MiuiBlurUtils.clearMemberBlendColor(this.mDateTextStyle1);
            MiuiBlurUtils.clearMemberBlendColor(this.mWeekTextStyle1);
            MiuiBlurUtils.clearMemberBlendColor(this.mNotificationDateTextStyle1);
            MiuiBlurUtils.clearMemberBlendColor(this.mHourTextStyle1);
            MiuiBlurUtils.clearMemberBlendColor(this.mMinuteTextStyle1);
            return;
        }
        if (!this.mClockInfo.isAutoPrimaryColor() && !this.mClockInfo.isAutoSecondaryColor()) {
            MiuiBlurUtils.clearContainerPassBlur(this.mClockContainerStyle2);
        }
        if (!this.mClockInfo.isAutoPrimaryColor()) {
            MiuiBlurUtils.clearMemberBlendColor(this.mHourTextStyle2);
        }
        if (!this.mClockInfo.isAutoSecondaryColor()) {
            MiuiBlurUtils.clearMemberBlendColor(this.mMinuteTextStyle2);
        }
        MiuiBlurUtils.clearContainerPassBlur(this.mClockContainerStyle1);
        MiuiBlurUtils.clearMemberBlendColor(this.mDateTextStyle1);
        MiuiBlurUtils.clearMemberBlendColor(this.mWeekTextStyle1);
        MiuiBlurUtils.clearMemberBlendColor(this.mNotificationDateTextStyle1);
        MiuiBlurUtils.clearMemberBlendColor(this.mHourTextStyle1);
        MiuiBlurUtils.clearMemberBlendColor(this.mMinuteTextStyle1);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        switch (AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()]) {
            case 1:
                if (this.mClockInfo.getClockStyle() == 5) {
                    return this.mDateTextStyle1;
                }
                return null;
            case 2:
                if (this.mClockInfo.getClockStyle() == 5) {
                    return this.mWeekTextStyle1;
                }
                return null;
            case 3:
                return this.mClockInfo.getClockStyle() == 5 ? this.mClockContainerStyle1 : this.mClockContainerStyle2;
            case 4:
                return this.mClockInfo.getClockStyle() == 5 ? this.mHourTextStyle1 : this.mHourTextStyle2;
            case 5:
                return this.mClockInfo.getClockStyle() == 5 ? this.mMinuteTextStyle1 : this.mMinuteTextStyle2;
            case 6:
                if (this.mClockInfo.getClockStyle() == 5) {
                    return this.mNotificationDateTextStyle1;
                }
                break;
        }
        return super.getIClockView(clockViewType);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void ignoreAccessibilityScale(boolean z) {
        this.supportAccessibilityScale = !z;
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTypefaceNeueMatic = FontUtils.getNeueMatic();
        this.mTypefaceMisans = FontUtils.getMiSansGlobal();
        this.mTypefaceNeuRounded = FontUtils.getNeuRounded(this.mContext);
        this.mClockContainerStyle1 = (ViewGroup) findViewById(R.id.clock_container_style1);
        this.mTimeContainerStyle1 = (ViewGroup) findViewById(R.id.time_container_style1);
        this.mStyle1MagazineGuideline = (Guideline) findViewById(R.id.style1_magazine_top_line);
        this.mDateTextStyle1 = (TextView) findViewById(R.id.current_date_style1);
        this.mWeekTextStyle1 = (TextView) findViewById(R.id.current_week_style1);
        this.mHourTextStyle1 = (MiuiTextGlassView) findViewById(R.id.current_time_hour_style1);
        this.mSpaceTimeStyle1 = (Space) findViewById(R.id.time_space_style1);
        this.mSpaceTimeEndStyle1 = (Space) findViewById(R.id.time_end_space_style1);
        this.mSpaceWeekStyle1 = (BaseLineSpaceView) findViewById(R.id.week_space_style1);
        this.mSpaceDateStyle1 = (BaseLineSpaceView) findViewById(R.id.date_space_style1);
        this.mMinuteTextStyle1 = (MiuiTextGlassView) findViewById(R.id.current_time_minute_style1);
        this.mNotificationDateTextStyle1 = (TextView) findViewById(R.id.current_notification_date_style1);
        this.mClockContainerStyle2 = (ViewGroup) findViewById(R.id.clock_container_style2);
        this.mHourTextStyle2 = (MiuiTextGlassView) findViewById(R.id.current_time_hour_style2);
        this.mMinuteTextStyle2 = (MiuiTextGlassView) findViewById(R.id.current_time_minute_style2);
        this.mSpaceTimeStyle2 = (BaseLineSpaceView) findViewById(R.id.time_space_style2);
        this.mDateTextStyle2 = (TextView) findViewById(R.id.current_date_style2);
        FontNeueMatic fontNeueMatic = new FontNeueMatic();
        this.mHourTextStyle2.setFontStyle(fontNeueMatic);
        this.mMinuteTextStyle2.setFontStyle(fontNeueMatic);
        this.mMinuteTextStyle1.setFontStyle(fontNeueMatic);
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void onHighTextContrastEnabledChanged(boolean z) {
        super.onHighTextContrastEnabledChanged(z);
        if (z) {
            return;
        }
        setClockStyleInfo();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void onLanguageChanged() {
        super.onLanguageChanged();
        this.mTypefaceMisans = FontUtils.getMiSansGlobal();
        setClockStyleInfo();
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        MiuiMagazineCBaseInfo miuiMagazineCBaseInfo;
        super.setClockPalette(i, z, map, z2);
        updateHighTextColors(this.mClockInfo);
        if (DeviceConfig.isHighTextContrastEnabled(this.mContext) || (miuiMagazineCBaseInfo = this.mClockInfo) == null || ClockStyleInfo.isAODType(miuiMagazineCBaseInfo.getDisplayType())) {
            return;
        }
        updateClockEffectsContainer();
        boolean zIsFullAODType = ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType());
        MiuiMagazineCBaseInfo miuiMagazineCBaseInfo2 = this.mClockInfo;
        int fullAodBlendColor = zIsFullAODType ? miuiMagazineCBaseInfo2.getFullAodBlendColor() : miuiMagazineCBaseInfo2.getBlendColor();
        MiuiMagazineCBaseInfo miuiMagazineCBaseInfo3 = this.mClockInfo;
        int fullAodSecondaryBlendColor = zIsFullAODType ? miuiMagazineCBaseInfo3.getFullAodSecondaryBlendColor() : miuiMagazineCBaseInfo3.getSecondaryBlendColor();
        boolean z3 = !zIsFullAODType && z;
        if (ClockEffectUtils.isGradualType(this.mClockInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            if (this.mClockInfo.getClockStyle() == 5) {
                ClockEffectUtils.setClockGradualEffect(getCurrentGradientParams(), this.mClockContainerStyle1, this.mClockInfo, false, zIsFullAODType, getScaleByGradientDesign());
            } else {
                ClockEffectUtils.setClockGradualEffect(getCurrentGradientParams(), this.mClockContainerStyle2, this.mClockInfo, false, zIsFullAODType, getScaleByGradientDesign());
            }
        } else if (ClockEffectUtils.isDifferenceType(this.mClockInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            if (this.mClockInfo.getClockStyle() == 5) {
                MiuiBlurUtils.setMemberDiffEffectColor(this.mDateTextStyle1, fullAodBlendColor);
                MiuiBlurUtils.setMemberDiffEffectColor(this.mWeekTextStyle1, fullAodBlendColor);
                MiuiBlurUtils.setMemberDiffEffectColor(this.mHourTextStyle1, fullAodBlendColor);
                MiuiBlurUtils.setMemberDiffEffectColor(this.mNotificationDateTextStyle1, fullAodBlendColor);
                MiuiBlurUtils.setMemberDiffEffectColor(this.mMinuteTextStyle1, fullAodSecondaryBlendColor);
            } else if (this.mClockInfo.getClockStyle() == 6 || this.mClockInfo.getClockStyle() == 9) {
                MiuiBlurUtils.setMemberDiffEffectColor(this.mHourTextStyle2, fullAodBlendColor);
                MiuiBlurUtils.setMemberDiffEffectColor(this.mMinuteTextStyle2, fullAodBlendColor);
            } else {
                MiuiBlurUtils.setMemberDiffEffectColor(this.mHourTextStyle2, fullAodBlendColor);
                MiuiBlurUtils.setMemberDiffEffectColor(this.mMinuteTextStyle2, fullAodSecondaryBlendColor);
            }
        } else if (DeviceConfig.supportBackgroundBlur(this.mContext) && ClockEffectUtils.isBlurMixType(this.mClockInfo.getClockEffect())) {
            if (this.mClockInfo.getClockStyle() == 5) {
                MiuiBlurUtils.setMemberBlendColors(this.mDateTextStyle1, z3, fullAodBlendColor, this.mClockInfo.getPrimaryColor());
                MiuiBlurUtils.setMemberBlendColors(this.mWeekTextStyle1, z3, fullAodBlendColor, this.mClockInfo.getPrimaryColor());
                MiuiBlurUtils.setMemberBlendColors(this.mHourTextStyle1, z3, fullAodBlendColor, this.mClockInfo.getPrimaryColor());
                MiuiBlurUtils.setMemberBlendColors(this.mNotificationDateTextStyle1, z3, fullAodBlendColor, this.mClockInfo.getPrimaryColor());
                MiuiBlurUtils.setMemberBlendColors(this.mMinuteTextStyle1, z3, fullAodSecondaryBlendColor, this.mClockInfo.getSecondaryColor());
            } else if (this.mClockInfo.getClockStyle() == 6 || this.mClockInfo.getClockStyle() == 9) {
                MiuiBlurUtils.setMemberBlendColors(this.mHourTextStyle2, z3, fullAodBlendColor, this.mClockInfo.getPrimaryColor());
                MiuiBlurUtils.setMemberBlendColors(this.mMinuteTextStyle2, z3, fullAodBlendColor, this.mClockInfo.getPrimaryColor());
            } else {
                MiuiBlurUtils.setMemberBlendColors(this.mHourTextStyle2, z3, fullAodBlendColor, this.mClockInfo.getPrimaryColor());
                MiuiBlurUtils.setMemberBlendColors(this.mMinuteTextStyle2, z3, fullAodSecondaryBlendColor, this.mClockInfo.getSecondaryColor());
            }
        }
        updateColor();
        updateTime();
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase
    public void setClockStyleInfo() {
        MiuiMagazineCBaseInfo miuiMagazineCBaseInfo = this.mClockInfo;
        if (miuiMagazineCBaseInfo == null) {
            return;
        }
        if (miuiMagazineCBaseInfo.getClockStyle() == 5) {
            this.mClockContainerStyle1.setVisibility(0);
            this.mClockContainerStyle2.setVisibility(8);
            this.mDateTextStyle1.setTextColor(this.mClockInfo.getPrimaryColor());
            this.mWeekTextStyle1.setTextColor(this.mClockInfo.getPrimaryColor());
            this.mNotificationDateTextStyle1.setTextColor(this.mClockInfo.getPrimaryColor());
            this.mHourTextStyle1.setTextColor(this.mClockInfo.getPrimaryColor());
            this.mMinuteTextStyle1.setTextColor(this.mClockInfo.getSecondaryColor());
            this.mDateTextStyle1.setText(DateFormatUtils.formatTime(this.mContext.getString(R.string.miui_magazine_clock_date)).toUpperCase());
            TextView textView = this.mDateTextStyle1;
            Calendar calendar = this.mCalendar;
            Context context = this.mContext;
            textView.setContentDescription(calendar.format(context, context.getString(R.string.miui_magazine_clock_date_content_description)));
            this.mTimeContainerStyle1.setContentDescription(DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
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
            ((ViewGroup.MarginLayoutParams) ((ConstraintLayout.LayoutParams) this.mNotificationDateTextStyle1.getLayoutParams())).topMargin = (int) (getDimen(R.dimen.miui_magazine_c_clock_style1_notification_date_week_margin_top) - (getDimen(R.dimen.miui_magazine_c_clock_style1_margin_top) * 1.0f));
            if (FontUtils.isTypefaceSupport(str)) {
                this.mWeekTextStyle1.setTextSize(0, getDimen(R.dimen.miui_magazine_c_clock_style1_text_size) * 1.0f);
                this.mWeekTextStyle1.setTypeface(this.mTypefaceNeueMatic);
                if (FontUtils.isCharHasChinese(str) || !FontUtils.isCharHasSoundmark(str)) {
                    ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = (int) (getDimen(R.dimen.miui_magazine_c_clock_style1_week_margin_top) * 1.0f);
                    ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = (int) (getDimen(R.dimen.miui_magazine_c_clock_style1_date_margin_top) * 1.0f);
                } else {
                    ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = (int) (getDimen(R.dimen.miui_magazine_c_clock_style1_week_soundmark_margin_top) * 1.0f);
                    ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = (int) (getDimen(R.dimen.miui_magazine_c_clock_style1_date_soundmark_margin_top) * 1.0f);
                }
                layoutParams3.baselineToBaseline = R.id.week_space_style1;
                layoutParams3.topToTop = -1;
            } else {
                if ("bo".equals(Locale.getDefault().getLanguage())) {
                    this.mWeekTextStyle1.setTextSize(0, getDimen(R.dimen.miui_magazine_c_clock_style1_bo_text_size) * 1.0f);
                    ((ViewGroup.MarginLayoutParams) layoutParams5).topMargin = (int) (getDimen(R.dimen.miui_magazine_c_clock_style1_week_bo_margin_top) * 1.0f);
                } else {
                    this.mWeekTextStyle1.setTextSize(0, getDimen(R.dimen.miui_magazine_c_clock_style1_notsupport_text_size) * 1.0f);
                    ((ViewGroup.MarginLayoutParams) layoutParams5).topMargin = (int) (getDimen(R.dimen.miui_magazine_c_clock_style1_week_notsupport_margin_top) * 1.0f);
                }
                layoutParams3.baselineToBaseline = -1;
                layoutParams3.topToTop = R.id.time_end_space_style1;
                this.mWeekTextStyle1.setTypeface(this.mTypefaceMisans);
                ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = (int) (getDimen(R.dimen.miui_magazine_c_clock_style1_date_notsupport_margin_top) * 1.0f);
            }
            ((ViewGroup.MarginLayoutParams) layoutParams4).bottomMargin = getDimen(R.dimen.miui_magazine_c_clock_style1_time_margin_bottom);
            this.mStyle1MagazineGuideline.setGuidelineBegin(((ViewGroup.MarginLayoutParams) layoutParams2).topMargin + ((ViewGroup.MarginLayoutParams) ((ConstraintLayout.LayoutParams) this.mClockContainerStyle1.getLayoutParams())).topMargin);
            MiuiTextGlassView miuiTextGlassView = this.mHourTextStyle1;
            Locale locale = Locale.ENGLISH;
            miuiTextGlassView.setText(String.format(locale, "%d%d", Integer.valueOf(this.mCurrentTimeArray[0]), Integer.valueOf(this.mCurrentTimeArray[1])));
            this.mMinuteTextStyle1.setText(String.format(locale, "%d%d", Integer.valueOf(this.mCurrentTimeArray[2]), Integer.valueOf(this.mCurrentTimeArray[3])));
            this.mMinuteTextStyle1.setEnableDiffusion(this.mClockInfo.isEnableDiffusion());
            ConstraintLayout.LayoutParams layoutParams6 = (ConstraintLayout.LayoutParams) this.mMagazineGroup.getLayoutParams();
            layoutParams6.topToBottom = R.id.style1_magazine_top_line;
            layoutParams6.startToStart = 0;
            layoutParams6.endToEnd = -1;
            ((ViewGroup.MarginLayoutParams) layoutParams6).topMargin = getDimen(R.dimen.miui_magazine_c_clock_style1_gallery_margin_top);
            layoutParams6.setMarginStart((int) (getDimen(R.dimen.miui_magazine_c_clock_style1_margin_start) * 1.0f));
            return;
        }
        this.mDateTextStyle2.setVisibility(isPreviewType() ? 0 : 8);
        this.mClockContainerStyle1.setVisibility(8);
        this.mClockContainerStyle2.setVisibility(0);
        this.mHourTextStyle2.setTextColor(this.mClockInfo.getPrimaryColor());
        if (this.mClockInfo.getClockStyle() == 6) {
            this.mMinuteTextStyle2.setTextColor(this.mClockInfo.getPrimaryColor());
        } else if (this.mClockInfo.getClockStyle() == 7 || this.mClockInfo.getClockStyle() == 8) {
            this.mMinuteTextStyle2.setTextColor(this.mClockInfo.getSecondaryColor());
        } else if (this.mClockInfo.getClockStyle() == 9) {
            this.mMinuteTextStyle2.setTextColor(this.mClockInfo.getPrimaryColor());
        } else {
            this.mMinuteTextStyle2.setTextColor(getResources().getColor(R.color.magazine_c_preview_minute_color_40));
        }
        if (this.mClockInfo.getClockStyle() == 9) {
            this.mHourTextStyle2.setTypeface(this.mTypefaceNeuRounded);
            this.mMinuteTextStyle2.setTypeface(this.mTypefaceNeuRounded);
        } else {
            this.mHourTextStyle2.setTypeface(this.mTypefaceNeueMatic);
            this.mMinuteTextStyle2.setTypeface(this.mTypefaceNeueMatic);
        }
        float dimen = getDimen(R.dimen.miui_magazine_c_clock_style2_time_text_size) * 1.0f;
        this.mHourTextStyle2.setTextSize(0, dimen);
        this.mMinuteTextStyle2.setTextSize(0, dimen);
        ((ViewGroup.MarginLayoutParams) ((ConstraintLayout.LayoutParams) this.mSpaceTimeStyle2.getLayoutParams())).topMargin = (int) (getDimen(R.dimen.miui_magazine_c_clock_style2_time_margin_top) * 1.0f);
        if (this.mClockInfo.getClockStyle() == 6) {
            this.mHourTextStyle2.setFontFeatureSettings("");
            this.mMinuteTextStyle2.setFontFeatureSettings(this.ssTags[this.mCurrentTimeArray[2]] + ac.b + this.ssTags[this.mCurrentTimeArray[3]]);
        } else if (this.mClockInfo.getClockStyle() == 7) {
            this.mHourTextStyle2.setFontFeatureSettings("");
            this.mMinuteTextStyle2.setFontFeatureSettings("");
        } else if (this.mClockInfo.getClockStyle() == 8) {
            this.mHourTextStyle2.setFontFeatureSettings(this.ssTags[this.mCurrentTimeArray[0]] + ac.b + this.ssTags[this.mCurrentTimeArray[1]]);
            this.mMinuteTextStyle2.setFontFeatureSettings(this.ssTags[this.mCurrentTimeArray[2]] + ac.b + this.ssTags[this.mCurrentTimeArray[3]]);
        } else if (this.mClockInfo.getClockStyle() == 9) {
            this.mHourTextStyle2.setFontFeatureSettings("");
            this.mMinuteTextStyle2.setFontFeatureSettings("");
        }
        MiuiTextGlassView miuiTextGlassView2 = this.mHourTextStyle2;
        Locale locale2 = Locale.ENGLISH;
        miuiTextGlassView2.setText(String.format(locale2, "%d%d", Integer.valueOf(this.mCurrentTimeArray[0]), Integer.valueOf(this.mCurrentTimeArray[1])));
        this.mMinuteTextStyle2.setText(String.format(locale2, "%d%d", Integer.valueOf(this.mCurrentTimeArray[2]), Integer.valueOf(this.mCurrentTimeArray[3])));
        this.mClockContainerStyle2.setContentDescription(DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
        this.mHourTextStyle2.setEnableDiffusion(this.mClockInfo.isEnableDiffusion());
        this.mMinuteTextStyle2.setEnableDiffusion(this.mClockInfo.isEnableDiffusion());
        this.mDateTextStyle2.setTextColor(this.mClockInfo.getPrimaryColor());
        TextView textView2 = this.mDateTextStyle2;
        Calendar calendar3 = this.mCalendar;
        Context context3 = this.mContext;
        textView2.setText(calendar3.format(context3, context3.getString(R.string.miui_magazine_c_clock_style2_date)));
        TextView textView3 = this.mDateTextStyle2;
        Calendar calendar4 = this.mCalendar;
        Context context4 = this.mContext;
        textView3.setContentDescription(calendar4.format(context4, context4.getString(R.string.miui_magazine_c_clock_style2_date_content_description)));
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        clearColorEffect(false);
        setClockStyleInfo();
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setMagazineInfoVisible(boolean z) {
        MiuiMagazineCBaseInfo miuiMagazineCBaseInfo = this.mClockInfo;
        if (miuiMagazineCBaseInfo == null || miuiMagazineCBaseInfo.getClockStyle() != 5) {
            return;
        }
        super.setMagazineInfoVisible(z);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockEffectsContainer() {
        super.updateClockEffectsContainer();
        int dimen = getDimen(R.dimen.miui_magazine_c_clock_blur_radius);
        if (this.mClockInfo.getClockStyle() == 5) {
            updateViewBackgroundBlurContainer(this.mClockContainerStyle1, dimen, this.mDateTextStyle1, this.mWeekTextStyle1, this.mHourTextStyle1, this.mNotificationDateTextStyle1, this.mMinuteTextStyle1);
        } else {
            updateViewBackgroundBlurContainer(this.mClockContainerStyle2, dimen, this.mHourTextStyle2, this.mMinuteTextStyle2);
        }
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        MiuiMagazineCBaseInfo miuiMagazineCBaseInfo = this.mClockInfo;
        if (miuiMagazineCBaseInfo == null) {
            return;
        }
        boolean zIsFullAODType = ClockStyleInfo.isFullAODType(miuiMagazineCBaseInfo.getDisplayType());
        MiuiMagazineCBaseInfo miuiMagazineCBaseInfo2 = this.mClockInfo;
        int fullAodPrimaryColor = zIsFullAODType ? miuiMagazineCBaseInfo2.getFullAodPrimaryColor() : miuiMagazineCBaseInfo2.getPrimaryColor();
        int fullAodSecondaryColor = zIsFullAODType ? this.mClockInfo.getFullAodSecondaryColor() : this.mClockInfo.getSecondaryColor();
        if (this.mClockInfo.getClockStyle() == 5) {
            this.mClockContainerStyle1.setVisibility(0);
            this.mClockContainerStyle2.setVisibility(8);
            this.mDateTextStyle1.setTextColor(fullAodPrimaryColor);
            this.mWeekTextStyle1.setTextColor(fullAodPrimaryColor);
            this.mNotificationDateTextStyle1.setTextColor(fullAodPrimaryColor);
            this.mHourTextStyle1.setTextColor(fullAodPrimaryColor);
            this.mMinuteTextStyle1.setTextColor(fullAodSecondaryColor);
            this.mMinuteTextStyle1.setEnableDiffusion(this.mClockInfo.isEnableDiffusion());
            return;
        }
        this.mDateTextStyle2.setVisibility(isPreviewType() ? 0 : 8);
        this.mClockContainerStyle1.setVisibility(8);
        this.mClockContainerStyle2.setVisibility(0);
        this.mHourTextStyle2.setTextColor(fullAodPrimaryColor);
        if (this.mClockInfo.getClockStyle() == 6 || this.mClockInfo.getClockStyle() == 9) {
            this.mMinuteTextStyle2.setTextColor(fullAodPrimaryColor);
        } else {
            this.mMinuteTextStyle2.setTextColor(fullAodSecondaryColor);
        }
        this.mHourTextStyle2.setEnableDiffusion(this.mClockInfo.isEnableDiffusion());
        this.mMinuteTextStyle2.setEnableDiffusion(this.mClockInfo.isEnableDiffusion());
        this.mDateTextStyle2.setTextColor(fullAodPrimaryColor);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateGradientEffectColor(float[] fArr) {
        super.updateGradientEffectColor(fArr);
        MiuiMagazineCBaseInfo miuiMagazineCBaseInfo = this.mClockInfo;
        if (miuiMagazineCBaseInfo != null && ClockEffectUtils.isGradualType(miuiMagazineCBaseInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            if (this.mClockInfo.getClockStyle() == 5) {
                MiuiBlurUtils.setGradientBlurMethod(this.mClockContainerStyle1, fArr, getScaleByGradientDesign());
            } else {
                MiuiBlurUtils.setGradientBlurMethod(this.mClockContainerStyle2, fArr, getScaleByGradientDesign());
            }
        }
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        if (this.mClockInfo == null) {
            return;
        }
        updateColor();
        if (this.mClockInfo.getClockStyle() == 5) {
            this.mDateTextStyle1.setText(DateFormatUtils.formatTime(this.mContext.getString(R.string.miui_magazine_clock_date)).toUpperCase());
            TextView textView = this.mDateTextStyle1;
            Calendar calendar = this.mCalendar;
            Context context = this.mContext;
            textView.setContentDescription(calendar.format(context, context.getString(R.string.miui_magazine_clock_date_content_description)));
            this.mTimeContainerStyle1.setContentDescription(DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
            Calendar calendar2 = this.mCalendar;
            Context context2 = this.mContext;
            String str = calendar2.format(context2, context2.getString(R.string.miui_magazine_c_clock_style1_week));
            this.mWeekTextStyle1.setContentDescription(str);
            this.mWeekTextStyle1.setText(str.toUpperCase());
            this.mNotificationDateTextStyle1.setText(this.mCalendar.format(this.mContext, getResources().getString(R.string.miui_magazine_c_clock_style2_date)));
            TextView textView2 = this.mNotificationDateTextStyle1;
            Calendar calendar3 = this.mCalendar;
            Context context3 = this.mContext;
            textView2.setContentDescription(calendar3.format(context3, context3.getString(R.string.miui_magazine_c_clock_style2_date_content_description)));
            MiuiTextGlassView miuiTextGlassView = this.mHourTextStyle1;
            Locale locale = Locale.ENGLISH;
            miuiTextGlassView.setText(String.format(locale, "%d%d", Integer.valueOf(this.mCurrentTimeArray[0]), Integer.valueOf(this.mCurrentTimeArray[1])));
            this.mMinuteTextStyle1.setText(String.format(locale, "%d%d", Integer.valueOf(this.mCurrentTimeArray[2]), Integer.valueOf(this.mCurrentTimeArray[3])));
            return;
        }
        if (this.mClockInfo.getClockStyle() == 6) {
            this.mHourTextStyle2.setFontFeatureSettings("");
            this.mMinuteTextStyle2.setFontFeatureSettings(this.ssTags[this.mCurrentTimeArray[2]] + ac.b + this.ssTags[this.mCurrentTimeArray[3]]);
        } else if (this.mClockInfo.getClockStyle() == 7) {
            this.mHourTextStyle2.setFontFeatureSettings("");
            this.mMinuteTextStyle2.setFontFeatureSettings("");
        } else if (this.mClockInfo.getClockStyle() == 8) {
            this.mHourTextStyle2.setFontFeatureSettings(this.ssTags[this.mCurrentTimeArray[0]] + ac.b + this.ssTags[this.mCurrentTimeArray[1]]);
            this.mMinuteTextStyle2.setFontFeatureSettings(this.ssTags[this.mCurrentTimeArray[2]] + ac.b + this.ssTags[this.mCurrentTimeArray[3]]);
        }
        MiuiTextGlassView miuiTextGlassView2 = this.mHourTextStyle2;
        Locale locale2 = Locale.ENGLISH;
        miuiTextGlassView2.setText(String.format(locale2, "%d%d", Integer.valueOf(this.mCurrentTimeArray[0]), Integer.valueOf(this.mCurrentTimeArray[1])));
        this.mMinuteTextStyle2.setText(String.format(locale2, "%d%d", Integer.valueOf(this.mCurrentTimeArray[2]), Integer.valueOf(this.mCurrentTimeArray[3])));
        this.mClockContainerStyle2.setContentDescription(DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
        TextView textView3 = this.mDateTextStyle2;
        Calendar calendar4 = this.mCalendar;
        Context context4 = this.mContext;
        textView3.setText(calendar4.format(context4, context4.getString(R.string.miui_magazine_c_clock_style2_date)));
        TextView textView4 = this.mDateTextStyle2;
        Calendar calendar5 = this.mCalendar;
        Context context5 = this.mContext;
        textView4.setContentDescription(calendar5.format(context5, context5.getString(R.string.miui_magazine_c_clock_style2_date_content_description)));
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        if (this.mClockContainerStyle1 != null) {
            this.mClockContainerStyle1.getLayoutParams().width = (int) (getDimen(R.dimen.miui_magazine_c_clock_style1_width) * 1.0f);
            float dimen = getDimen(R.dimen.miui_magazine_c_clock_style1_text_size) * 1.0f;
            this.mDateTextStyle1.setTextSize(0, dimen);
            this.mHourTextStyle1.setTextSize(0, dimen);
            this.mNotificationDateTextStyle1.setTextSize(0, getDimen(R.dimen.miui_magazine_c_clock_style2_date_text_size));
            this.mMinuteTextStyle1.setTextSize(0, dimen);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mClockContainerStyle1.getLayoutParams();
            if (!DeviceConfig.isJ18() || DeviceConfig.inLargeScreen(this.mContext)) {
                layoutParams.setMarginStart(getDimen(R.dimen.miui_magazine_c_clock_style1_margin_start));
                ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = (int) (getDimen(R.dimen.miui_magazine_c_clock_style1_margin_top) * 1.0f);
            } else {
                layoutParams.setMarginStart(getDimen(R.dimen.miui_magazine_c_clock_style1_margin_start_j18));
                ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_magazine_c_clock_style1_margin_top_j18);
            }
            this.mClockContainerStyle1.setLayoutParams(layoutParams);
        }
        if (this.mClockContainerStyle2 != null) {
            this.mDateTextStyle2.setTextSize(0, getDimen(R.dimen.miui_magazine_c_clock_style2_date_text_size));
            this.mClockContainerStyle2.getLayoutParams().width = (int) (getDimen(R.dimen.miui_magazine_c_clock_style2_width) * 1.0f);
            ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mClockContainerStyle2.getLayoutParams();
            if (!DeviceConfig.isJ18() || DeviceConfig.inLargeScreen(this.mContext)) {
                ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = (int) (getDimen(R.dimen.miui_magazine_c_clock_style2_margin_top) * 1.0f);
            } else {
                ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimen(R.dimen.miui_magazine_c_clock_style2_margin_top_j18);
            }
            ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mDateTextStyle2.getLayoutParams();
            if (!DeviceConfig.isJ18() || DeviceConfig.inLargeScreen(this.mContext)) {
                ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin = getDimen(R.dimen.miui_magazine_c_clock_style2_date_margin_top);
            } else {
                ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin = getDimen(R.dimen.miui_magazine_c_clock_style2_date_margin_top_j18);
            }
        }
    }
}
