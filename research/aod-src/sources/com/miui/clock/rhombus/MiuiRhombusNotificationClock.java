package com.miui.clock.rhombus;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.BaseFontStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.Effect;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.ColorUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.MiuiBlurUtils;
import com.miui.clock.utils.SvgUtils;
import java.util.Map;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class MiuiRhombusNotificationClock extends MiuiRhombusBase {
    public ImageView mColonView;
    public ViewGroup mDateContainer;
    public TextView mDateText;
    public ViewGroup mMagazineGroup;
    public ViewGroup mTimeContainer;
    public boolean supportAccessibilityScale;

    /* JADX INFO: renamed from: com.miui.clock.rhombus.MiuiRhombusNotificationClock$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.FULL_COLON.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.MAGAZINE_INFO.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_DATE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public MiuiRhombusNotificationClock(Context context) {
        super(context);
        this.supportAccessibilityScale = true;
    }

    public MiuiRhombusNotificationClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.supportAccessibilityScale = true;
    }

    private void clearBlur() {
        MiuiBlurUtils.clearContainerPassBlur(this);
        MiuiBlurUtils.clearMemberBlendColor(this.mHour1View);
        MiuiBlurUtils.clearMemberBlendColor(this.mHour2View);
        MiuiBlurUtils.clearMemberBlendColor(this.mMinute1View);
        MiuiBlurUtils.clearMemberBlendColor(this.mMinute2View);
        MiuiBlurUtils.clearMemberBlendColor(this.mColonView);
        MiuiBlurUtils.clearMemberBlendColor(this.mDateText);
    }

    private void updateTimeColor() {
        this.mHour1View.updateColor();
        this.mHour2View.updateColor();
        this.mMinute1View.updateColor();
        this.mMinute2View.updateColor();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        if (this.mInfo == null) {
            return;
        }
        if (ClockEffectUtils.isLessThanOS2()) {
            if (this.mInfo.isAutoPrimaryColor() && DeviceConfig.supportBackgroundBlur(this.mContext) && !DeviceConfig.isHighTextContrastEnabled(this.mContext)) {
                return;
            }
            clearBlur();
            return;
        }
        ClockEffectUtils.clearClockEffectsContainer(this, this.mInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mHour1View, this.mInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mHour2View, this.mInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mMinute1View, this.mInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mMinute2View, this.mInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mColonView, this.mInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mDateText, this.mInfo, z);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ Rect getDepthAvoidRect() {
        return super.getDepthAvoidRect();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i == 1) {
            return this.mColonView;
        }
        if (i != 2) {
            return i != 3 ? super.getIClockView(clockViewType) : this.mDateText;
        }
        return this.mMagazineGroup;
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public int getMagazineColor() {
        MiuiRhombusClockBaseInfo miuiRhombusClockBaseInfo = this.mInfo;
        if (miuiRhombusClockBaseInfo != null) {
            return ColorUtils.blendColor(miuiRhombusClockBaseInfo.getPrimaryColor(), 0.6f);
        }
        return 0;
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ TextView getTimeView() {
        return super.getTimeView();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getTimeZone() {
        return super.getTimeZone();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void ignoreAccessibilityScale(boolean z) {
        this.supportAccessibilityScale = !z;
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mDateText = (TextView) findViewById(R.id.current_date);
        this.mDateContainer = (ViewGroup) findViewById(R.id.date_container);
        this.mTimeContainer = (ViewGroup) findViewById(R.id.time_container);
        this.mColonView = (ImageView) findViewById(R.id.colon);
        this.mMagazineGroup = (ViewGroup) findViewById(R.id.magazine_info_container);
        updateTime();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        super.setClockPalette(i, z, map, z2);
        if (this.mInfo == null || DeviceConfig.isHighTextContrastEnabled(this.mContext)) {
            return;
        }
        updateColor();
        if (!ClockEffectUtils.isGradualType(this.mInfo.getClockEffect()) || !DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            if (ClockEffectUtils.isDifferenceType(this.mInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                MiuiBlurUtils.setDiffEffectContainer(this, this.mInfo.isMultiWindowBlur());
                MiuiBlurUtils.setMemberDiffEffectColor(this.mHour1View, this.mInfo.getBlendColor());
                MiuiBlurUtils.setMemberDiffEffectColor(this.mHour2View, this.mInfo.getBlendColor());
                MiuiBlurUtils.setMemberDiffEffectColor(this.mMinute1View, this.mInfo.getBlendColor());
                MiuiBlurUtils.setMemberDiffEffectColor(this.mMinute2View, this.mInfo.getBlendColor());
                MiuiBlurUtils.setMemberDiffEffectColor(this.mColonView, this.mInfo.getBlendColor());
                MiuiBlurUtils.setMemberDiffEffectColor(this.mDateText, this.mInfo.getBlendColor());
            } else if (ClockEffectUtils.isGlowType(this.mInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                Effect.Glow glowParam = Effect.GlowEffect.getGlowParam(this.mInfo.getBlendColor());
                MiuiBlurUtils.setGlowEffectContainer(this);
                boolean zIsFullAODType = ClockStyleInfo.isFullAODType(this.mInfo.getDisplayType());
                MiuiBlurUtils.setGlowEffectMethod(this.mHour1View, glowParam, false, zIsFullAODType);
                MiuiBlurUtils.setGlowEffectMethod(this.mHour2View, glowParam, false, zIsFullAODType);
                MiuiBlurUtils.setGlowEffectMethod(this.mMinute1View, glowParam, false, zIsFullAODType);
                MiuiBlurUtils.setGlowEffectMethod(this.mMinute2View, glowParam, false, zIsFullAODType);
                MiuiBlurUtils.setGlowEffectMethod(this.mDateText, glowParam, false, zIsFullAODType);
            } else if (ClockEffectUtils.isBlurMixType(this.mInfo.getClockEffect()) && DeviceConfig.supportBackgroundBlur(this.mContext)) {
                int blendColor = this.mInfo.getBlendColor();
                if (!this.mInfo.isDisableContainerPassBlur()) {
                    MiuiBlurUtils.setContainerPassBlur(this, getDimen(R.dimen.miui_rhombus_clock_blur_radius), this.mInfo.isMultiWindowBlur());
                }
                MiuiBlurUtils.setMemberBlendColors(this.mHour1View, z, blendColor, this.mInfo.getPrimaryColor());
                MiuiBlurUtils.setMemberBlendColors(this.mHour2View, z, blendColor, this.mInfo.getPrimaryColor());
                MiuiBlurUtils.setMemberBlendColors(this.mMinute1View, z, blendColor, this.mInfo.getPrimaryColor());
                MiuiBlurUtils.setMemberBlendColors(this.mMinute2View, z, blendColor, this.mInfo.getPrimaryColor());
                MiuiBlurUtils.setMemberBlendColors(this.mColonView, z, blendColor, this.mInfo.getPrimaryColor());
                MiuiBlurUtils.setMemberBlendColors(this.mDateText, z, blendColor, this.mInfo.getPrimaryColor());
            }
        }
        updateTimeColor();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        setClockStyle(this.mInfo.getClockStyle());
        clearColorEffect(false);
        updateColor();
        updateTime();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public void setMagazineInfoVisible(boolean z) {
        super.setMagazineInfoVisible(z);
        this.mMagazineInfoVisible = z;
        this.mMagazineGroup.setVisibility(z ? 0 : 8);
        updateTime();
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setSuperSaveOpen(boolean z) {
        super.setSuperSaveOpen(z);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        super.updateColor();
        MiuiRhombusClockBaseInfo miuiRhombusClockBaseInfo = this.mInfo;
        if (miuiRhombusClockBaseInfo == null) {
            return;
        }
        int i = miuiRhombusClockBaseInfo.getHourAndMinuteColor(this.mContext)[0];
        if (i == 0) {
            i = -1;
        }
        this.mDateText.setTextColor(i);
        this.mColonView.setImageDrawable(SvgUtils.initSvgColor(ContextCompat.getDrawable(getContext(), this.style.getSmallColonResource()), i));
        this.mHour1View.setTextColor(i);
        this.mHour2View.setTextColor(i);
        this.mMinute1View.setTextColor(i);
        this.mMinute2View.setTextColor(i);
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public void updateGradientEffectColor(float[] fArr) {
        super.updateGradientEffectColor(fArr);
        MiuiRhombusClockBaseInfo miuiRhombusClockBaseInfo = this.mInfo;
        if (miuiRhombusClockBaseInfo != null && ClockEffectUtils.isGradualType(miuiRhombusClockBaseInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            MiuiBlurUtils.setGradientBlurMethod(this, fArr);
        }
    }

    @Override // com.miui.clock.rhombus.MiuiRhombusBase, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        if (this.mInfo == null) {
            return;
        }
        float f = (!DeviceConfig.isJ18() || DeviceConfig.inLargeScreen(this.mContext)) ? 1.0f : 0.8f;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mHour1View.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mHour2View.getLayoutParams();
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mMinute1View.getLayoutParams();
        LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) this.mMinute2View.getLayoutParams();
        LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) this.mColonView.getLayoutParams();
        if (this.style.getFontStyle() == BaseFontStyle.Style.SKPupok) {
            layoutParams.height = (int) (this.style.getLargeClockHeights()[this.mCurrentTimeArray[0]] * this.mDensity * f * 0.41f);
            layoutParams2.height = (int) (this.style.getLargeClockHeights()[this.mCurrentTimeArray[1]] * this.mDensity * f * 0.41f);
            layoutParams3.height = (int) (this.style.getLargeClockHeights()[this.mCurrentTimeArray[2]] * this.mDensity * f * 0.41f);
            layoutParams4.height = (int) (this.style.getLargeClockHeights()[this.mCurrentTimeArray[3]] * this.mDensity * f * 0.41f);
            layoutParams5.height = (int) (this.style.getSmallColonResourceHeight() * this.mDensity * f * 0.41f);
        } else {
            int smallClockInnerHeight = (int) (this.style.getSmallClockInnerHeight() * this.mDensity * f);
            layoutParams.height = smallClockInnerHeight;
            layoutParams2.height = smallClockInnerHeight;
            layoutParams3.height = smallClockInnerHeight;
            layoutParams4.height = smallClockInnerHeight;
            layoutParams5.height = smallClockInnerHeight;
        }
        layoutParams2.setMarginEnd((int) (this.style.getNotificationCenterXOffset()[this.mCurrentTimeArray[1]] * this.mDensity * f));
        layoutParams3.setMarginEnd((int) (this.style.getNotificationCenterXOffset()[this.mCurrentTimeArray[2]] * this.mDensity * f));
        layoutParams2.setMarginStart((int) (this.style.getSmallClockInnerMarginOffset() * this.mDensity * f));
        layoutParams3.setMarginStart((int) (this.style.getSmallClockInnerMarginOffset() * this.mDensity * f));
        layoutParams4.setMarginStart((int) (this.style.getSmallClockInnerMarginOffset() * this.mDensity * f));
        layoutParams5.setMarginStart((int) (this.style.getSmallClockInnerMarginOffset() * this.mDensity * f));
        this.mHour1View.setLayoutParams(layoutParams);
        this.mHour2View.setLayoutParams(layoutParams2);
        this.mMinute1View.setLayoutParams(layoutParams3);
        this.mMinute2View.setLayoutParams(layoutParams4);
        this.mColonView.setLayoutParams(layoutParams5);
        TextView textView = this.mDateText;
        Calendar calendar = this.mCalendar;
        Context context = this.mContext;
        textView.setText(calendar.format(context, context.getString(R.string.miui_lock_screen_date)));
        this.mDateText.setTextSize(0, getDimen(R.dimen.miui_rhombus_small_date_text_size));
        this.mTimeContainer.setContentDescription(DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
        if (this.m24HourFormat) {
            this.mHour1View.setVisibility(0);
        } else {
            this.mHour1View.setVisibility(this.mCurrentTimeArray[0] > 0 ? 0 : 8);
        }
        ((RelativeLayout.LayoutParams) this.mDateContainer.getLayoutParams()).topMargin = getDimen(R.dimen.miui_rhombus_small_clock_margin_top);
        ((RelativeLayout.LayoutParams) this.mTimeContainer.getLayoutParams()).topMargin = getDimen(R.dimen.miui_rhombus_small_clock_time_container_margin_top);
        this.mHour1View.setFontStyle(this.style).setAngel(0.0f).setCenterPoint(0, 0).setCenterOffset(0, 0).setIndex(this.mCurrentTimeArray[0]).build();
        this.mHour2View.setFontStyle(this.style).setAngel(0.0f).setCenterPoint(0, 0).setCenterOffset(0, 0).setIndex(this.mCurrentTimeArray[1]).build();
        this.mMinute1View.setFontStyle(this.style).setAngel(0.0f).setCenterPoint(0, 0).setCenterOffset(0, 0).setIndex(this.mCurrentTimeArray[2]).build();
        this.mMinute2View.setFontStyle(this.style).setAngel(0.0f).setCenterPoint(0, 0).setCenterOffset(0, 0).setIndex(this.mCurrentTimeArray[3]).build();
        ((RelativeLayout.LayoutParams) this.mMagazineGroup.getLayoutParams()).topMargin = getDimen(R.dimen.miui_rhombus_clock_notification_gallery_margin_top);
    }
}
