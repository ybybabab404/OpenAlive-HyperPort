package com.miui.clock.magazine;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.Effect;
import com.miui.clock.utils.BaseLineSpaceView;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.GlobalColorUtils;
import com.miui.clock.utils.MiuiBlurUtils;
import com.xiaomi.onetrack.util.ac;
import java.util.Locale;
import java.util.Map;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class MiuiMagazineCSingleClock extends MiuiMagazineCBase {
    public ViewGroup mClockContainer;
    public float mDeviceScale;
    public TextView mHourText;
    public TextView mMinuteText;
    public Typeface mNeuRounded;
    public BaseLineSpaceView mSpaceTime;
    public LinearLayout mTimeContainer;
    public ViewGroup mTimeRegion;
    public Typeface mTypefaceNeueMatic;
    public boolean supportAccessibilityScale;

    public MiuiMagazineCSingleClock(Context context) {
        super(context);
        this.mDeviceScale = 1.0f;
        this.supportAccessibilityScale = true;
    }

    public MiuiMagazineCSingleClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDeviceScale = 1.0f;
        this.supportAccessibilityScale = true;
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        if (ClockEffectUtils.isLessThanOS2()) {
            return;
        }
        ClockEffectUtils.clearClockEffectsContainer(this.mClockContainer, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mHourText, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mMinuteText, this.mClockInfo, z);
        ClockEffectUtils.clearClockGradualEffect(this.mClockContainer, this.mClockInfo, z);
    }

    public final void convertAodColor() {
        int primaryColor = this.mClockInfo.getPrimaryColor();
        int secondaryColor = this.mClockInfo.getSecondaryColor();
        this.mClockInfo.setAodPrimaryColor(GlobalColorUtils.transformAodColor(primaryColor));
        this.mClockInfo.setAodSecondaryColor(GlobalColorUtils.transformAodColor(secondaryColor));
    }

    public final void convertAodColorByBlendColor() {
        MiuiMagazineCBaseInfo miuiMagazineCBaseInfo = this.mClockInfo;
        miuiMagazineCBaseInfo.setAodPrimaryColor(miuiMagazineCBaseInfo.getAodBlendColor());
        MiuiMagazineCBaseInfo miuiMagazineCBaseInfo2 = this.mClockInfo;
        miuiMagazineCBaseInfo2.setAodSecondaryColor(miuiMagazineCBaseInfo2.getAodSecondaryBlendColor());
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
        if (clockViewType != ClockViewType.FULL_TIME && clockViewType != ClockViewType.CLOCK_CONTAINER) {
            return super.getIClockView(clockViewType);
        }
        return this.mClockContainer;
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    public int getNormalMarginOffset() {
        MiuiMagazineCBaseInfo miuiMagazineCBaseInfo = this.mClockInfo;
        return (miuiMagazineCBaseInfo == null || miuiMagazineCBaseInfo.getClockStyle() != 5) ? getDimen(R.dimen.miui_magazine_c_clock_style2_margin_top) : getDimen(R.dimen.miui_magazine_c_clock_style1_margin_top);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public float getTopMargin() {
        return getDimen(R.dimen.miui_magazine_c_clock_style1_margin_top);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void ignoreAccessibilityScale(boolean z) {
        this.supportAccessibilityScale = !z;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mHourText = (TextView) findViewById(R.id.current_time_hour_style1);
        this.mMinuteText = (TextView) findViewById(R.id.current_time_minute);
        this.mClockContainer = (ViewGroup) findViewById(R.id.clock_container);
        this.mTimeRegion = (ViewGroup) findViewById(R.id.time_region);
        this.mTimeContainer = (LinearLayout) findViewById(R.id.time_container);
        this.mSpaceTime = (BaseLineSpaceView) findViewById(R.id.time_space);
        this.mTypefaceNeueMatic = FontUtils.getNeueMatic();
        this.mNeuRounded = FontUtils.getNeuRounded(this.mContext);
        updateViewsLayoutParams();
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
        super.setClockPalette(i, z, map, z2);
        MiuiMagazineCBaseInfo miuiMagazineCBaseInfo = this.mClockInfo;
        if (miuiMagazineCBaseInfo == null || map == null) {
            updateColor();
            return;
        }
        int clockEffect = miuiMagazineCBaseInfo.getClockEffect();
        if (ClockEffectUtils.needPickSolidColor(clockEffect, DeviceConfig.supportBackgroundBlur(this.mContext))) {
            convertAodColor();
        } else if (ClockEffectUtils.isGradualType(clockEffect)) {
            convertAodColor();
            if (DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                updateGradientEffectColor(getCurrentGradientParams());
            }
        } else if (ClockEffectUtils.isDifferenceType(clockEffect)) {
            convertAodColor();
        } else if (ClockEffectUtils.isGlowType(clockEffect)) {
            Effect.Glow glowParam = Effect.GlowEffect.getGlowParam(this.mClockInfo.getBlendColor());
            Effect.Glow glowParam2 = Effect.GlowEffect.getGlowParam(this.mClockInfo.getSecondaryBlendColor());
            MiuiBlurUtils.setGlowEffectContainer(this.mClockContainer);
            MiuiBlurUtils.setGlowEffectMethod(this.mHourText, glowParam, true, false);
            if (this.mClockInfo.getClockStyle() == 6 || this.mClockInfo.getClockStyle() == 9) {
                MiuiBlurUtils.setGlowEffectMethod(this.mMinuteText, glowParam, true, false);
            } else {
                MiuiBlurUtils.setGlowEffectMethod(this.mMinuteText, glowParam2, true, false);
            }
        } else {
            convertAodColorByBlendColor();
        }
        updateColor();
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

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        super.updateColor();
        MiuiMagazineCBaseInfo miuiMagazineCBaseInfo = this.mClockInfo;
        if (miuiMagazineCBaseInfo == null) {
            return;
        }
        this.mHourText.setTextColor(miuiMagazineCBaseInfo.getAodPrimaryColor());
        if (this.mClockInfo.getClockStyle() == 6 || this.mClockInfo.getClockStyle() == 9) {
            this.mMinuteText.setTextColor(this.mClockInfo.getAodPrimaryColor());
        } else {
            this.mMinuteText.setTextColor(this.mClockInfo.getAodSecondaryColor());
        }
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateGradientEffectColor(float[] fArr) {
        super.updateGradientEffectColor(fArr);
        MiuiMagazineCBaseInfo miuiMagazineCBaseInfo = this.mClockInfo;
        if (miuiMagazineCBaseInfo != null && ClockEffectUtils.isGradualType(miuiMagazineCBaseInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            ClockEffectUtils.setClockGradualEffect(fArr, this.mTimeRegion, this.mClockInfo, true, false, getScaleByGradientDesign());
        }
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        MiuiMagazineCBaseInfo miuiMagazineCBaseInfo = this.mClockInfo;
        if (miuiMagazineCBaseInfo == null) {
            return;
        }
        int clockStyle = miuiMagazineCBaseInfo.getClockStyle();
        if (clockStyle == 5) {
            this.mTimeContainer.setGravity(8388611);
        } else {
            this.mTimeContainer.setGravity(1);
        }
        if (clockStyle == 6) {
            this.mMinuteText.setFontFeatureSettings(this.ssTags[this.mCurrentTimeArray[2]] + ac.b + this.ssTags[this.mCurrentTimeArray[3]]);
        } else if (clockStyle == 8) {
            this.mHourText.setFontFeatureSettings(this.ssTags[this.mCurrentTimeArray[0]] + ac.b + this.ssTags[this.mCurrentTimeArray[1]]);
            this.mMinuteText.setFontFeatureSettings(this.ssTags[this.mCurrentTimeArray[2]] + ac.b + this.ssTags[this.mCurrentTimeArray[3]]);
        } else if (this.mClockInfo.getClockStyle() == 9) {
            this.mHourText.setFontFeatureSettings("");
            this.mMinuteText.setFontFeatureSettings("");
        } else {
            this.mMinuteText.setFontFeatureSettings("");
        }
        if (this.mClockInfo.getClockStyle() == 9) {
            this.mHourText.setTypeface(this.mNeuRounded);
            this.mMinuteText.setTypeface(this.mNeuRounded);
        } else {
            this.mHourText.setTypeface(this.mTypefaceNeueMatic);
            this.mMinuteText.setTypeface(this.mTypefaceNeueMatic);
        }
        TextView textView = this.mHourText;
        Locale locale = Locale.ENGLISH;
        textView.setText(String.format(locale, "%d%d", Integer.valueOf(this.mCurrentTimeArray[0]), Integer.valueOf(this.mCurrentTimeArray[1])));
        this.mMinuteText.setText(String.format(locale, "%d%d", Integer.valueOf(this.mCurrentTimeArray[2]), Integer.valueOf(this.mCurrentTimeArray[3])));
        this.mClockContainer.setContentDescription(DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        if (this.mClockContainer != null) {
            float dimen = getDimen(R.dimen.miui_magazine_c_clock_style1_text_size) * 1.0f;
            this.mHourText.setTextSize(0, dimen);
            this.mMinuteText.setTextSize(0, dimen);
            ((ViewGroup.MarginLayoutParams) ((ConstraintLayout.LayoutParams) this.mSpaceTime.getLayoutParams())).topMargin = (int) (getDimen(R.dimen.miui_magazine_c_clock_style1_time_margin_top) * 1.0f);
            int dimen2 = getDimen(R.dimen.miui_magazine_c_clock_notification_height);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mClockContainer.getLayoutParams();
            ((ViewGroup.MarginLayoutParams) layoutParams).width = -1;
            ((ViewGroup.MarginLayoutParams) layoutParams).height = (int) (dimen2 * 1.0f);
            layoutParams.setMarginStart(getDimen(R.dimen.miui_magazine_c_clock_aod_margin_start));
            if (!DeviceConfig.isJ18() || DeviceConfig.inLargeScreen(this.mContext)) {
                ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_magazine_c_clock_notification_margin_top);
            } else {
                ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_magazine_c_clock_notification_margin_top_j18);
            }
            this.mClockContainer.setLayoutParams(layoutParams);
        }
    }
}
