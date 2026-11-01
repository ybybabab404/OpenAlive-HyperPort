package com.miui.clock.oversize.a;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import com.miui.clock.MiuiGalleryBaseClock;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.BaseFontStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.Effect;
import com.miui.clock.module.FontNeumaticCompressedB;
import com.miui.clock.oversize.OversizeSvgView;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.ColorUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.GlobalColorUtils;
import java.util.Map;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public abstract class OversizeABase extends MiuiGalleryBaseClock {
    public BaseFontStyle fontStyle;
    public OversizeABaseInfo mClockInfo;
    public int[] mCurrentTimeArray;
    public OversizeSvgView mHour1View;
    public OversizeSvgView mHour2View;
    public OversizeSvgView mMinute1View;
    public OversizeSvgView mMinute2View;
    public Paint mWeekMeasurePaint;
    public String mWeekStr;

    public OversizeABase(Context context) {
        super(context);
        this.mCurrentTimeArray = new int[4];
        this.fontStyle = new FontNeumaticCompressedB();
    }

    public OversizeABase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurrentTimeArray = new int[4];
        this.fontStyle = new FontNeumaticCompressedB();
    }

    private void setBlurMixColor(boolean z, Map map) {
        int color;
        int color2;
        int paletteColor;
        int paletteColor2;
        int paletteColor3;
        int paletteColor4;
        int color3 = Color.parseColor("#666666");
        int color4 = Color.parseColor("#B3B3B3");
        int color5 = Color.parseColor("#ABABAB");
        if (z) {
            color = Color.parseColor("#808080");
            color2 = Color.parseColor("#FFFFFF");
            paletteColor = getPaletteColor(map, "neutral-variant30", this.mClockInfo.getPrimaryColor());
            int paletteColor5 = getPaletteColor(map, "secondary60", this.mClockInfo.getPrimaryColor());
            int paletteColor6 = getPaletteColor(map, "secondary70", this.mClockInfo.getSecondaryColor());
            paletteColor2 = paletteColor5;
            paletteColor3 = getPaletteColor(map, "neutral-variant30", this.mClockInfo.getBlendColor());
            paletteColor4 = paletteColor6;
        } else {
            color = Color.parseColor("#666666");
            color2 = Color.parseColor("#B3B3B3");
            paletteColor = Color.parseColor("#ABABAB");
            paletteColor2 = getPaletteColor(map, "primary85", this.mClockInfo.getPrimaryColor());
            paletteColor4 = getPaletteColor(map, "primary93", this.mClockInfo.getSecondaryColor());
            paletteColor3 = Color.parseColor("#FFFFFF");
        }
        this.mClockInfo.setPrimaryColor(color);
        this.mClockInfo.setSecondaryColor(color2);
        OversizeABaseInfo oversizeABaseInfo = this.mClockInfo;
        if (!oversizeABaseInfo.isAutoPrimaryColor()) {
            paletteColor3 = this.mClockInfo.getBlendColor();
        }
        oversizeABaseInfo.setOriginMagazineColor(paletteColor3);
        this.mClockInfo.setFullAodPrimaryColor(color3);
        this.mClockInfo.setFullAodSecondaryColor(color4);
        if (this.mClockInfo.isAutoPrimaryColor() && this.mClockInfo.isAutoSecondaryColor()) {
            this.mClockInfo.setBlendColor(paletteColor);
            this.mClockInfo.setSecondaryBlendColor(paletteColor);
            this.mClockInfo.setAodPrimaryColor(paletteColor2);
            this.mClockInfo.setAodBlendColor(paletteColor2);
            this.mClockInfo.setAodSecondaryColor(paletteColor4);
            this.mClockInfo.setAodSecondaryBlendColor(paletteColor4);
            this.mClockInfo.setFullAodBlendColor(color5);
            this.mClockInfo.setFullAodSecondaryBlendColor(color5);
        } else if (this.mClockInfo.isAutoPrimaryColor()) {
            int secondaryBlendColor = this.mClockInfo.getSecondaryBlendColor();
            this.mClockInfo.setBlendColor(secondaryBlendColor);
            int iTransformAodColor = GlobalColorUtils.transformAodColor(secondaryBlendColor);
            int iTransformFullAodColor = GlobalColorUtils.transformFullAodColor(secondaryBlendColor);
            this.mClockInfo.setAodPrimaryColor(iTransformAodColor);
            this.mClockInfo.setAodBlendColor(iTransformAodColor);
            this.mClockInfo.setAodSecondaryColor(iTransformAodColor);
            this.mClockInfo.setAodSecondaryBlendColor(iTransformAodColor);
            this.mClockInfo.setFullAodBlendColor(iTransformFullAodColor);
            this.mClockInfo.setFullAodSecondaryBlendColor(iTransformFullAodColor);
        } else if (this.mClockInfo.isAutoSecondaryColor()) {
            int blendColor = this.mClockInfo.getBlendColor();
            this.mClockInfo.setSecondaryBlendColor(blendColor);
            int iTransformAodColor2 = GlobalColorUtils.transformAodColor(blendColor);
            int iTransformFullAodColor2 = GlobalColorUtils.transformFullAodColor(blendColor);
            this.mClockInfo.setAodPrimaryColor(iTransformAodColor2);
            this.mClockInfo.setAodBlendColor(iTransformAodColor2);
            this.mClockInfo.setAodSecondaryColor(iTransformAodColor2);
            this.mClockInfo.setAodSecondaryBlendColor(iTransformAodColor2);
            this.mClockInfo.setFullAodBlendColor(iTransformFullAodColor2);
            this.mClockInfo.setFullAodSecondaryBlendColor(iTransformFullAodColor2);
        } else {
            int iTransformAodColor3 = GlobalColorUtils.transformAodColor(this.mClockInfo.getBlendColor());
            this.mClockInfo.setAodPrimaryColor(iTransformAodColor3);
            this.mClockInfo.setAodBlendColor(iTransformAodColor3);
            OversizeABaseInfo oversizeABaseInfo2 = this.mClockInfo;
            oversizeABaseInfo2.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(oversizeABaseInfo2.getBlendColor()));
            int iTransformAodColor4 = GlobalColorUtils.transformAodColor(this.mClockInfo.getSecondaryBlendColor());
            this.mClockInfo.setAodSecondaryColor(iTransformAodColor4);
            this.mClockInfo.setAodSecondaryBlendColor(iTransformAodColor4);
            OversizeABaseInfo oversizeABaseInfo3 = this.mClockInfo;
            oversizeABaseInfo3.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(oversizeABaseInfo3.getSecondaryBlendColor()));
        }
        this.mClockInfo.updateInfoColors();
    }

    private void setSolidColor(boolean z, Map map) {
        if (z) {
            if (this.mClockInfo.isAutoPrimaryColor() && this.mClockInfo.isAutoSecondaryColor()) {
                int paletteColor = getPaletteColor(map, "secondary60", this.mClockInfo.getPrimaryColor());
                int paletteColor2 = getPaletteColor(map, "secondary70", this.mClockInfo.getSecondaryColor());
                this.mClockInfo.setPrimaryColor(paletteColor);
                this.mClockInfo.setSecondaryColor(paletteColor2);
            } else if (this.mClockInfo.isAutoPrimaryColor()) {
                this.mClockInfo.setPrimaryColor(GlobalColorUtils.addColorTone(this.mClockInfo.getSecondaryColor(), -10.0d));
            } else if (this.mClockInfo.isAutoSecondaryColor()) {
                this.mClockInfo.setSecondaryColor(GlobalColorUtils.addColorTone(this.mClockInfo.getPrimaryColor(), 10.0d));
            }
        } else if (this.mClockInfo.isAutoPrimaryColor() && this.mClockInfo.isAutoSecondaryColor()) {
            int paletteColor3 = getPaletteColor(map, "primary85", this.mClockInfo.getPrimaryColor());
            int paletteColor4 = getPaletteColor(map, "primary93", this.mClockInfo.getSecondaryColor());
            this.mClockInfo.setPrimaryColor(paletteColor3);
            this.mClockInfo.setSecondaryColor(paletteColor4);
        } else if (this.mClockInfo.isAutoPrimaryColor()) {
            this.mClockInfo.setPrimaryColor(GlobalColorUtils.addColorTone(this.mClockInfo.getSecondaryColor(), -8.0d));
        } else if (this.mClockInfo.isAutoSecondaryColor()) {
            this.mClockInfo.setSecondaryColor(GlobalColorUtils.addColorTone(this.mClockInfo.getPrimaryColor(), 8.0d));
        }
        OversizeABaseInfo oversizeABaseInfo = this.mClockInfo;
        oversizeABaseInfo.setOriginMagazineColor(oversizeABaseInfo.getPrimaryColor());
        OversizeABaseInfo oversizeABaseInfo2 = this.mClockInfo;
        oversizeABaseInfo2.setAodPrimaryColor(GlobalColorUtils.transformAodColor(oversizeABaseInfo2.getPrimaryColor()));
        OversizeABaseInfo oversizeABaseInfo3 = this.mClockInfo;
        oversizeABaseInfo3.setAodSecondaryColor(GlobalColorUtils.transformAodColor(oversizeABaseInfo3.getSecondaryColor()));
        OversizeABaseInfo oversizeABaseInfo4 = this.mClockInfo;
        oversizeABaseInfo4.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(oversizeABaseInfo4.getPrimaryColor()));
        OversizeABaseInfo oversizeABaseInfo5 = this.mClockInfo;
        oversizeABaseInfo5.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(oversizeABaseInfo5.getSecondaryColor()));
        this.mClockInfo.updateInfoColors();
    }

    public final void adaptMixToSolid() {
        if (this.mClockInfo.isAutoPrimaryColor() && this.mClockInfo.isAutoSecondaryColor()) {
            return;
        }
        if (this.mClockInfo.isAutoPrimaryColor()) {
            OversizeABaseInfo oversizeABaseInfo = this.mClockInfo;
            oversizeABaseInfo.setSecondaryColor(oversizeABaseInfo.getSecondaryBlendColor());
        } else if (this.mClockInfo.isAutoSecondaryColor()) {
            OversizeABaseInfo oversizeABaseInfo2 = this.mClockInfo;
            oversizeABaseInfo2.setPrimaryColor(oversizeABaseInfo2.getBlendColor());
        } else {
            OversizeABaseInfo oversizeABaseInfo3 = this.mClockInfo;
            oversizeABaseInfo3.setPrimaryColor(oversizeABaseInfo3.getBlendColor());
            OversizeABaseInfo oversizeABaseInfo4 = this.mClockInfo;
            oversizeABaseInfo4.setSecondaryColor(oversizeABaseInfo4.getSecondaryBlendColor());
        }
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        OversizeABaseInfo oversizeABaseInfo = this.mClockInfo;
        if (oversizeABaseInfo == null) {
            return;
        }
        int clockEffect = oversizeABaseInfo.getClockEffect();
        if (z || !ClockEffectUtils.isGradualType(clockEffect)) {
            resetGradientParams();
        }
    }

    public int getAdaptMargin() {
        int dimen;
        OversizeABaseInfo oversizeABaseInfo;
        if (DeviceConfig.inFoldLargeScreen(this.mContext)) {
            dimen = getResources().getConfiguration().orientation == 2 ? getDimen(R.dimen.miui_oversize_a_fold_left_landscape) : getDimen(R.dimen.miui_oversize_a_fold_left);
        } else {
            dimen = 0;
        }
        if (DeviceConfig.inFoldLargeScreen(this.mContext)) {
            return ((DeviceConfig.isRTL() || ((oversizeABaseInfo = this.mClockInfo) != null && oversizeABaseInfo.isSuperSaveOpen())) && this.mWeekMeasurePaint.measureText(this.mWeekStr.toUpperCase()) > ((float) (getDimen(R.dimen.miui_oversize_a_week_max_height) - (getDimen(R.dimen.miui_oversize_a_week_padding_top) * 2)))) ? getDimen(R.dimen.miui_oversize_a_date_width) + getDimen(R.dimen.miui_oversize_a_date_margin_right) : dimen;
        }
        return dimen;
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public ClockStyleInfo getClockStyleInfo() {
        return this.mClockInfo;
    }

    public BaseFontStyle getFontStyle() {
        return this.fontStyle;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public float[] getGradientAnimationParams() {
        OversizeABaseInfo oversizeABaseInfo = this.mClockInfo;
        return oversizeABaseInfo == null ? Effect.EMPTY_GRADIENT : oversizeABaseInfo.getAodGradientParams();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public float[] getGradientParams() {
        OversizeABaseInfo oversizeABaseInfo = this.mClockInfo;
        if (oversizeABaseInfo == null) {
            return Effect.EMPTY_GRADIENT;
        }
        boolean zIsAODType = ClockStyleInfo.isAODType(oversizeABaseInfo.getDisplayType());
        OversizeABaseInfo oversizeABaseInfo2 = this.mClockInfo;
        return zIsAODType ? oversizeABaseInfo2.getAodGradientParams() : oversizeABaseInfo2.getLockGradientParams();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getMagazineColor() {
        OversizeABaseInfo oversizeABaseInfo = this.mClockInfo;
        return oversizeABaseInfo != null ? ColorUtils.blendColor(oversizeABaseInfo.getOriginMagazineColor(), 0.6f) : super.getMagazineColor();
    }

    public Typeface getMiSansDemiBold() {
        return FontUtils.getMiSans(520);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockBottom() {
        return this.mMagazineInfoVisible ? getDimen(R.dimen.miui_oversize_a_notification_magazine_margin_top) : getDimen(R.dimen.miui_oversize_a_notification_margin_top);
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public View getNotificationClockTopView() {
        return this.mHour1View;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    public final int getPaletteColor(Map map, String str, int i) {
        return (map == null || str == null || map.get(str) == null) ? i : ((Integer) map.get(str)).intValue();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void init(Context context) {
        super.init(context);
        Paint paint = new Paint();
        this.mWeekMeasurePaint = paint;
        paint.setTypeface(getMiSansDemiBold());
        this.mWeekMeasurePaint.setTextSize(getDimen(R.dimen.miui_oversize_a_date_size));
    }

    public boolean isCnLanguage() {
        return "zh".equals(this.mLanguage);
    }

    public boolean isEastAsiaLanguage() {
        return "zh".equals(this.mLanguage) || "ko".equals(this.mLanguage) || "ja".equals(this.mLanguage);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mHour1View = (OversizeSvgView) findViewById(R.id.hour1);
        this.mHour2View = (OversizeSvgView) findViewById(R.id.hour2);
        this.mMinute1View = (OversizeSvgView) findViewById(R.id.minute1);
        this.mMinute2View = (OversizeSvgView) findViewById(R.id.minute2);
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void onLanguageChanged() {
        super.onLanguageChanged();
        this.mWeekMeasurePaint.setTypeface(getMiSansDemiBold());
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        String str;
        super.setClockPalette(i, z, map, z2);
        if (map != null) {
            str = "secondary60=" + map.get("secondary60") + ",secondary70=" + map.get("secondary70") + ",primary85=" + map.get("primary85") + ",primary93=" + map.get("primary93");
        } else {
            str = "null";
        }
        Log.d("ClockPalette", "setClockPalette: type = " + i + ", textDark = " + z + ", palette = " + str);
        updateHighTextColors(this.mClockInfo);
        if (this.mClockInfo == null || DeviceConfig.isHighTextContrastEnabled(this.mContext) || this.mClockInfo.isSuperSaveOpen()) {
            return;
        }
        if (ClockEffectUtils.isGradualType(this.mClockInfo.getClockEffect())) {
            setSolidColor(z, map);
            updateColor();
            if (DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                updateClockBlendColor();
                return;
            }
            return;
        }
        if (ClockEffectUtils.isSolidType(this.mClockInfo.getClockEffect())) {
            setSolidColor(z, map);
            updateColor();
            return;
        }
        if (!ClockEffectUtils.isBlurMixType(this.mClockInfo.getClockEffect())) {
            if (ClockEffectUtils.isDifferenceType(this.mClockInfo.getClockEffect())) {
                setDifferenceColor(z, map);
                updateColor();
                if (DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                    updateClockBlendColor();
                    return;
                }
                return;
            }
            return;
        }
        setBlurMixColor(z, map);
        if (DeviceConfig.supportBackgroundBlur(this.mContext)) {
            updateColor();
            updateClockBlendColor();
        } else {
            adaptMixToSolid();
            setSolidColor(z, map);
            updateColor();
        }
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        this.mClockInfo = (OversizeABaseInfo) clockStyleInfo;
        clearColorEffect(false);
        updateFontType();
        updateColor();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    public final void setDifferenceColor(boolean z, Map map) {
        if (this.mClockInfo.isAutoPrimaryColor() && this.mClockInfo.isAutoSecondaryColor()) {
            if (z) {
                if (map != null && map.get("secondary40") != null) {
                    int iIntValue = ((Integer) map.get("secondary40")).intValue();
                    this.mClockInfo.setPrimaryColor(iIntValue);
                    this.mClockInfo.setBlendColor(iIntValue);
                    this.mClockInfo.setAodBlendColor(GlobalColorUtils.transformAodColor(iIntValue));
                    this.mClockInfo.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(iIntValue));
                    this.mClockInfo.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(iIntValue));
                }
                if (map != null && map.get("secondary50") != null) {
                    int iIntValue2 = ((Integer) map.get("secondary50")).intValue();
                    this.mClockInfo.setSecondaryColor(iIntValue2);
                    this.mClockInfo.setSecondaryBlendColor(iIntValue2);
                    this.mClockInfo.setAodSecondaryBlendColor(GlobalColorUtils.transformAodColor(iIntValue2));
                    this.mClockInfo.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(iIntValue2));
                    this.mClockInfo.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(iIntValue2));
                }
            } else {
                if (map != null && map.get("primary85") != null) {
                    int iIntValue3 = ((Integer) map.get("primary85")).intValue();
                    this.mClockInfo.setPrimaryColor(iIntValue3);
                    this.mClockInfo.setBlendColor(iIntValue3);
                    this.mClockInfo.setAodBlendColor(GlobalColorUtils.transformAodColor(iIntValue3));
                    this.mClockInfo.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(iIntValue3));
                    this.mClockInfo.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(iIntValue3));
                }
                if (map != null && map.get("primary93") != null) {
                    int iIntValue4 = ((Integer) map.get("primary93")).intValue();
                    this.mClockInfo.setSecondaryColor(iIntValue4);
                    this.mClockInfo.setSecondaryBlendColor(iIntValue4);
                    this.mClockInfo.setAodSecondaryBlendColor(GlobalColorUtils.transformAodColor(iIntValue4));
                    this.mClockInfo.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(iIntValue4));
                    this.mClockInfo.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(iIntValue4));
                }
            }
        } else if (this.mClockInfo.isAutoPrimaryColor()) {
            int secondaryBlendColor = this.mClockInfo.getSecondaryBlendColor();
            int iAddColorTone = z ? GlobalColorUtils.addColorTone(secondaryBlendColor, -10.0d) : GlobalColorUtils.addColorTone(secondaryBlendColor, -8.0d);
            this.mClockInfo.setPrimaryColor(iAddColorTone);
            this.mClockInfo.setBlendColor(iAddColorTone);
            this.mClockInfo.setAodBlendColor(GlobalColorUtils.transformAodColor(iAddColorTone));
            this.mClockInfo.setAodSecondaryBlendColor(GlobalColorUtils.transformAodColor(secondaryBlendColor));
            this.mClockInfo.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(iAddColorTone));
            this.mClockInfo.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(secondaryBlendColor));
            this.mClockInfo.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(iAddColorTone));
            this.mClockInfo.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(secondaryBlendColor));
        } else if (this.mClockInfo.isAutoSecondaryColor()) {
            int blendColor = this.mClockInfo.getBlendColor();
            int iAddColorTone2 = z ? GlobalColorUtils.addColorTone(blendColor, 10.0d) : GlobalColorUtils.addColorTone(blendColor, 8.0d);
            this.mClockInfo.setSecondaryColor(iAddColorTone2);
            this.mClockInfo.setSecondaryBlendColor(iAddColorTone2);
            this.mClockInfo.setAodBlendColor(GlobalColorUtils.transformAodColor(blendColor));
            this.mClockInfo.setAodSecondaryBlendColor(GlobalColorUtils.transformAodColor(iAddColorTone2));
            this.mClockInfo.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(blendColor));
            this.mClockInfo.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(iAddColorTone2));
            this.mClockInfo.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(blendColor));
            this.mClockInfo.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(iAddColorTone2));
        } else {
            OversizeABaseInfo oversizeABaseInfo = this.mClockInfo;
            oversizeABaseInfo.setAodBlendColor(GlobalColorUtils.transformAodColor(oversizeABaseInfo.getBlendColor()));
            OversizeABaseInfo oversizeABaseInfo2 = this.mClockInfo;
            oversizeABaseInfo2.setAodSecondaryBlendColor(GlobalColorUtils.transformAodColor(oversizeABaseInfo2.getSecondaryBlendColor()));
            OversizeABaseInfo oversizeABaseInfo3 = this.mClockInfo;
            oversizeABaseInfo3.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(oversizeABaseInfo3.getBlendColor()));
            OversizeABaseInfo oversizeABaseInfo4 = this.mClockInfo;
            oversizeABaseInfo4.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(oversizeABaseInfo4.getSecondaryBlendColor()));
            OversizeABaseInfo oversizeABaseInfo5 = this.mClockInfo;
            oversizeABaseInfo5.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(oversizeABaseInfo5.getBlendColor()));
            OversizeABaseInfo oversizeABaseInfo6 = this.mClockInfo;
            oversizeABaseInfo6.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(oversizeABaseInfo6.getSecondaryBlendColor()));
        }
        this.mClockInfo.updateInfoColors();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    public void setRoundRect(View view) {
        view.setClipToOutline(true);
        view.setOutlineProvider(new ViewOutlineProvider() { // from class: com.miui.clock.oversize.a.OversizeABase.1
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view2, Outline outline) {
                int width = view2.getWidth();
                outline.setRoundRect(0, 0, width, view2.getHeight(), width);
            }
        });
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    public void updateFontType() {
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        String time = DateFormatUtils.formatTime(this.m24HourFormat ? "HHmm" : "hhmm");
        for (int i = 0; i < time.length(); i++) {
            this.mCurrentTimeArray[i] = Integer.parseInt(String.valueOf(time.charAt(i)));
        }
        Calendar calendar = this.mCalendar;
        Context context = this.mContext;
        this.mWeekStr = calendar.format(context, context.getString(R.string.miui_smart_frame_clock_week));
    }
}
