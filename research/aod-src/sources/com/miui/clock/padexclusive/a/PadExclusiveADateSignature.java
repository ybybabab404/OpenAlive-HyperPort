package com.miui.clock.padexclusive.a;

import android.content.Context;
import android.content.res.Configuration;
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
import com.miui.clock.padexclusive.a.clockinfo.PadExclusiveASignatureClockInfo;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class PadExclusiveADateSignature extends PadExclusiveABase {
    public PadExclusiveASignatureClockInfo mClockInfo;
    public TextView mDateView;
    public TextView mSignatureLine1;
    public TextView mSignatureLine2;
    public TextView mSignatureLine3;
    public ViewGroup mSignatureTextContainer;

    /* JADX INFO: renamed from: com.miui.clock.padexclusive.a.PadExclusiveADateSignature$1, reason: invalid class name */
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
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.SIGNATURE_AREA.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.TEXT_AREA.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.TEXT_AREA2.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.TEXT_AREA3.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public PadExclusiveADateSignature(Context context) {
        super(context);
    }

    public PadExclusiveADateSignature(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        PadExclusiveASignatureClockInfo padExclusiveASignatureClockInfo = this.mClockInfo;
        if (padExclusiveASignatureClockInfo == null) {
            return;
        }
        ClockEffectUtils.clearClockEffectsContainer(this, padExclusiveASignatureClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mDateView, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mSignatureLine1, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mSignatureLine3, this.mClockInfo, z);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase
    public int getDimenPX(int i) {
        return getDimen(i);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i == 1) {
            return this.mDateView;
        }
        if (i == 2) {
            return this.mSignatureTextContainer;
        }
        if (i == 3) {
            return this.mSignatureLine1;
        }
        if (i != 4) {
            return i != 5 ? super.getIClockView(clockViewType) : this.mSignatureLine3;
        }
        return this.mSignatureLine2;
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getMagazineColor() {
        return super.getMagazineColor();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockTop() {
        return getDimen(R.dimen.miui_pad_exclusive_a_date_margin_top);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiBaseClock2
    public View getNotificationClockTopView() {
        return this.mDateView;
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiBaseClock2, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mSignatureLine1 = (TextView) findViewById(R.id.signature_text_line1);
        this.mSignatureLine2 = (TextView) findViewById(R.id.signature_text_line2);
        this.mSignatureLine3 = (TextView) findViewById(R.id.signature_text_line3);
        this.mSignatureTextContainer = (ViewGroup) findViewById(R.id.signature_text_container);
        this.mDateView = (TextView) findViewById(R.id.date);
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        super.setClockPalette(i, z, map, z2);
        updateClockBlendColor(this.mClockInfo.getBlendColor(), this.mClockInfo.getSecondaryBlendColor());
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        this.mClockInfo = (PadExclusiveASignatureClockInfo) clockStyleInfo;
        clearColorEffect(false);
        setTextTypeFace(this.mDateView, 3);
        setTextTypeFace(this.mSignatureLine1, 2);
        setTextTypeFace(this.mSignatureLine2, 3);
        setTextTypeFace(this.mSignatureLine3, 3);
        this.mSignatureLine1.setText(this.mClockInfo.getSignatureLine1());
        this.mSignatureLine2.setText(this.mClockInfo.getSignatureLine2());
        this.mSignatureLine3.setText(this.mClockInfo.getSignatureLine3());
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.padexclusive.a.PadExclusiveABase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2, int i3, int i4) {
        PadExclusiveASignatureClockInfo padExclusiveASignatureClockInfo;
        super.updateClockBlendColor(i, i2, i3, i4);
        if (DeviceConfig.isHighTextContrastEnabled(this.mContext) || (padExclusiveASignatureClockInfo = this.mClockInfo) == null) {
            return;
        }
        if (ClockEffectUtils.isGlowType(padExclusiveASignatureClockInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            return;
        }
        if (ClockEffectUtils.isGradualType(this.mClockInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            return;
        }
        updateViewBackgroundBlurContainer(this, getDimen(R.dimen.miui_pad_exclusive_a_clock_blur_radius), this.mDateView, this.mSignatureLine1, this.mSignatureLine3);
        boolean zUpdateTextDark = updateTextDark(this.mTextDark);
        int textDarkAlpha = getTextDarkAlpha(this.mTextDark);
        TextView textView = this.mDateView;
        PadExclusiveASignatureClockInfo padExclusiveASignatureClockInfo2 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView, padExclusiveASignatureClockInfo2, zUpdateTextDark, padExclusiveASignatureClockInfo2.getPlainBlendColor(), this.mClockInfo.getPlainColor(), 0, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()), textDarkAlpha);
        TextView textView2 = this.mSignatureLine1;
        PadExclusiveASignatureClockInfo padExclusiveASignatureClockInfo3 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView2, padExclusiveASignatureClockInfo3, zUpdateTextDark, padExclusiveASignatureClockInfo3.getPlainBlendColor(), this.mClockInfo.getPlainColor(), 0, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()), textDarkAlpha);
        TextView textView3 = this.mSignatureLine3;
        PadExclusiveASignatureClockInfo padExclusiveASignatureClockInfo4 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView3, padExclusiveASignatureClockInfo4, zUpdateTextDark, padExclusiveASignatureClockInfo4.getPlainBlendColor(), this.mClockInfo.getPlainColor(), 0, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()), textDarkAlpha);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        super.updateColor();
        this.mSignatureLine1.setTextColor(this.mClockInfo.getPlainColor());
        this.mSignatureLine3.setTextColor(this.mClockInfo.getPlainColor());
        this.mSignatureLine2.setTextColor(this.mClockInfo.getSecondaryColor());
        this.mDateView.setTextColor(this.mClockInfo.getPlainColor());
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        this.mDateView.setText(this.mCalendar.format(this.mContext, getResources().getString(R.string.miui_clock_pad_exclusive_format_week_full_date)));
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        boolean zIsOrientation90 = isOrientation90();
        this.mDateView.setTextSize(0, getDimenPX(R.dimen.miui_pad_exclusive_a_full_date_size));
        this.mSignatureLine1.setTextSize(0, getDimenPX(R.dimen.miui_pad_exclusive_a_signature1_size));
        this.mSignatureLine2.setTextSize(0, getDimenPX(R.dimen.miui_pad_exclusive_a_signature2_size));
        this.mSignatureLine3.setTextSize(0, getDimenPX(R.dimen.miui_pad_exclusive_a_signature3_size));
        int i = R.dimen.miui_pad_exclusive_a_signature_margin_bottom;
        int i2 = R.dimen.miui_pad_exclusive_a_full_date_margin_bottom;
        int i3 = R.dimen.miui_pad_exclusive_a_signature_margin_end;
        int i4 = R.dimen.miui_pad_exclusive_a_signature_margin_start;
        int dimPXByPadDesignWidth = zIsOrientation90 ? getDimPXByPadDesignWidth(i3) : getDimenPX(i3);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mSignatureTextContainer.getLayoutParams();
        layoutParams.setMarginStart(getDimenPX(i4));
        ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = getDimenPX(i);
        int i5 = R.dimen.miui_pad_exclusive_a_signature_width;
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimenPX(i5);
        layoutParams.setMarginEnd(dimPXByPadDesignWidth);
        this.mSignatureTextContainer.setLayoutParams(layoutParams);
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mDateView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin = getDimenPX(i2);
        layoutParams2.setMarginEnd(dimPXByPadDesignWidth);
        this.mDateView.setLayoutParams(layoutParams2);
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mMagazineGroup.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams3).bottomMargin = getDimen(R.dimen.miui_pad_exclusive_a_magazine_info_margin_bottom);
        layoutParams3.setMarginEnd(dimPXByPadDesignWidth);
        this.mMagazineGroup.setLayoutParams(layoutParams3);
        LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) this.mSignatureLine2.getLayoutParams();
        layoutParams4.bottomMargin = getDimenPX(R.dimen.miui_pad_exclusive_a_signature2_bottom);
        this.mSignatureLine2.setLayoutParams(layoutParams4);
        LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) this.mSignatureLine3.getLayoutParams();
        layoutParams5.bottomMargin = getDimenPX(R.dimen.miui_pad_exclusive_a_signature3_bottom);
        this.mSignatureLine3.setLayoutParams(layoutParams5);
        this.mSignatureLine1.setMaxWidth(getDimenPX(i5));
        this.mSignatureLine2.setMaxWidth(getDimenPX(i5));
        this.mSignatureLine3.setMaxWidth(getDimenPX(R.dimen.miui_pad_exclusive_a_signature3_width));
    }
}
