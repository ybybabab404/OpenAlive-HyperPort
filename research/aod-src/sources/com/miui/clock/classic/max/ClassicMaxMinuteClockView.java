package com.miui.clock.classic.max;

import android.content.Context;
import android.graphics.Rect;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.ColorUtils;
import com.miui.clock.MiuiTextGlassView;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.MiuiClassicClockInfoBase;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.MiuiBlurUtils;
import com.miui.clock.utils.MutiDeviceDimenAdapter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ClassicMaxMinuteClockView extends ClassicMaxClockBaseView {
    public MiuiTextGlassView mColonView;

    /* JADX INFO: renamed from: com.miui.clock.classic.max.ClassicMaxMinuteClockView$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.FULL_COLON.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public ClassicMaxMinuteClockView(Context context) {
        super(context);
    }

    public ClassicMaxMinuteClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ClassicMaxMinuteClockView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        if (ClockEffectUtils.isLessThanOS2()) {
            return;
        }
        ClockEffectUtils.clearClockEffectsView(this.mColonView, this.mClockStyleInfo, z);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        return AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()] != 1 ? super.getIClockView(clockViewType) : this.mColonView;
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mColonView = (MiuiTextGlassView) findViewById(R.id.colon_view);
        updateSignatureText();
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView
    public void onScreenSizeChange() {
        super.onScreenSizeChange();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mColonView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_classic_max_time_area_margin_top);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = MutiDeviceDimenAdapter.getMultiDeviceDimen(this.mContext, R.dimen.miui_classic_max_time_area_text_height, true);
        this.mColonView.setLayoutParams(layoutParams);
        this.mColonView.setTextSize(0, MutiDeviceDimenAdapter.getMultiDeviceDimen(this.mContext, R.dimen.miui_classic_max_time_area_text_size, true));
        updateSignatureText();
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        MiuiClassicClockInfoBase miuiClassicClockInfoBase;
        super.setClockPalette(i, z, map, z2);
        if (DeviceConfig.isHighTextContrastEnabled(this.mContext) || (miuiClassicClockInfoBase = this.mClockStyleInfo) == null || ClockStyleInfo.isAODType(miuiClassicClockInfoBase.getDisplayType())) {
            return;
        }
        updateClockEffectsContainer();
        if (!ClockEffectUtils.isBlurMixType(this.mClockStyleInfo.getClockEffect())) {
            if (ClockEffectUtils.isDifferenceType(this.mClockStyleInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                MiuiBlurUtils.setMemberDiffEffectColor(this.mColonView, ClockStyleInfo.isFullAODType(this.mClockStyleInfo.getDisplayType()) ? this.mClockStyleInfo.getFullAodSecondaryBlendColor() : this.mClockStyleInfo.getSecondaryBlendColor());
                return;
            }
            return;
        }
        if (DeviceConfig.supportBackgroundBlur(this.mContext)) {
            boolean zIsFullAODType = ClockStyleInfo.isFullAODType(this.mClockStyleInfo.getDisplayType());
            MiuiClassicClockInfoBase miuiClassicClockInfoBase2 = this.mClockStyleInfo;
            MiuiBlurUtils.setMemberBlendColor(this.mColonView, !zIsFullAODType && z, zIsFullAODType ? miuiClassicClockInfoBase2.getFullAodSecondaryBlendColor() : miuiClassicClockInfoBase2.getSecondaryBlendColor());
        }
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        ClassicClockTimeUtils.setClassicTimeStyle(this.mColonView, this.mClockStyleInfo);
        updateTime();
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2, int i3, int i4) {
        super.updateClockBlendColor(i, i2, i3, i4);
        if (!DeviceConfig.supportBackgroundBlur(this.mContext)) {
            updateClockColor(i, i2);
            return;
        }
        boolean zIsAODType = ClockStyleInfo.isAODType(this.mClockStyleInfo.getDisplayType());
        boolean zIsFullAODType = ClockStyleInfo.isFullAODType(this.mClockStyleInfo.getDisplayType());
        boolean zUpdateTextDark = updateTextDark(this.mTextDark);
        int textDarkAlpha = getTextDarkAlpha(this.mTextDark);
        updateClockEffectsContainer();
        MiuiTextGlassView miuiTextGlassView = this.mColonView;
        MiuiClassicClockInfoBase miuiClassicClockInfoBase = this.mClockStyleInfo;
        ClockEffectUtils.setClockEffectsView(miuiTextGlassView, miuiClassicClockInfoBase, zUpdateTextDark, i2, miuiClassicClockInfoBase.getSecondaryColor(), i4, zIsAODType, zIsFullAODType, textDarkAlpha);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.MiuiGalleryBaseClock
    public void updateClockColor(int i, int i2) {
        super.updateClockColor(i, i2);
        this.mColonView.setTextColor(i2);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.MiuiGalleryBaseClock
    public void updateClockEffectsContainer() {
        super.updateClockEffectsContainer();
        updateViewBackgroundBlurContainer(this, getDimen(R.dimen.miui_classic_clock_blur_radius), this.mColonView);
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView
    public void updateColor(ClockStyleInfo clockStyleInfo) {
        super.updateColor(clockStyleInfo);
        updateClockColor(this.mClockStyleInfo.getPrimaryColor(), this.mClockStyleInfo.getSecondaryColor());
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView
    public void updateSignatureText() {
        super.updateSignatureText();
        this.mSignatureTextInEditor.setTextSize(0, getDimensionFloat(R.dimen.classic_signature_text_size));
        this.mSignatureTextInEditor.setTypeface(FontUtils.getMiSans(380));
        this.mSignatureTextInEditor.setMaxWidth(getDimen(R.dimen.classic_text_signature_max_width));
    }

    @Override // com.miui.clock.classic.max.ClassicMaxClockBaseView, com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        int hourInt = ClassicClockTimeUtils.getHourInt(this.m24HourFormat, this.mCalendar);
        int i = this.mCalendar.get(20);
        Context context = this.mContext;
        int i2 = R.string.format_hour_minute;
        SpannableString spannableString = new SpannableString(context.getString(i2, ClassicClockTimeUtils.getTimeString(hourInt, this.m24HourFormat), ClassicClockTimeUtils.getTimeString(i, true)));
        SpannableString spannableString2 = new SpannableString(this.mContext.getString(i2, ClassicClockTimeUtils.getTimeString(hourInt, this.m24HourFormat), ClassicClockTimeUtils.getTimeString(i, true)));
        int alphaComponent = ColorUtils.setAlphaComponent(this.mTimeView.getCurrentTextColor(), 0);
        int alphaComponent2 = ColorUtils.setAlphaComponent(this.mColonView.getCurrentTextColor(), 0);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(alphaComponent);
        ForegroundColorSpan foregroundColorSpan2 = new ForegroundColorSpan(alphaComponent2);
        ForegroundColorSpan foregroundColorSpan3 = new ForegroundColorSpan(alphaComponent2);
        int i3 = (hourInt >= 10 || this.m24HourFormat) ? 2 : 1;
        int i4 = i3 + 1;
        spannableString.setSpan(foregroundColorSpan, 0, i4, 17);
        this.mTimeView.setText(spannableString);
        spannableString2.setSpan(foregroundColorSpan2, 0, i3, 17);
        spannableString2.setSpan(foregroundColorSpan3, i4, spannableString.length(), 17);
        this.mColonView.setText(spannableString2);
    }
}
