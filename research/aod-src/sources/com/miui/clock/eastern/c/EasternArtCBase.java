package com.miui.clock.eastern.c;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;
import com.miui.clock.MiuiGalleryBaseClock;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.eastern.c.clokcinfo.EasternArtCBaseInfo;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.GlobalColorUtils;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class EasternArtCBase extends MiuiGalleryBaseClock {
    public EasternArtCBaseInfo mClockInfo;
    public String mCountry;
    public int mFontStyle;
    public boolean mIsOnHour;

    public EasternArtCBase(Context context) {
        super(context);
        this.mIsOnHour = true;
        this.mFontStyle = -1;
    }

    public EasternArtCBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsOnHour = true;
        this.mFontStyle = -1;
        this.mCountry = Locale.getDefault().getCountry();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public ClockStyleInfo getClockStyleInfo() {
        return this.mClockInfo;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getGalleryGravity() {
        return 1;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
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
    public /* bridge */ /* synthetic */ int getMagazineColor() {
        return super.getMagazineColor();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public abstract /* synthetic */ int getNotificationClockBottom();

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockTop() {
        return getDimen(R.dimen.miui_eastern_art_single_margin_top);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    public int getTimeTextSize(int i) {
        if (i != 2) {
            return getDimen(this.mIsOnHour ? R.dimen.miui_eastern_art_time_big_text_size : R.dimen.miui_eastern_art_time_small_text_size);
        }
        return getDimen(this.mIsOnHour ? R.dimen.miui_eastern_art_cursive_time_big_text_size : R.dimen.miui_eastern_art_cursive_time_small_text_size);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    public boolean isOnHour() {
        return this.mIsOnHour;
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
        int color;
        int color2;
        int color3;
        int color4;
        int secondaryColor;
        int primaryColor;
        super.setClockPalette(i, z, map, z2);
        if (map != null) {
            str = "secondary30=" + map.get("secondary30");
        } else {
            str = "null";
        }
        Log.d("EasternArtCBase ClockPalette", "setClockPalette: type = " + i + ", textDark = " + z + ", palette = " + str);
        updateHighTextColors(this.mClockInfo);
        if (this.mClockInfo == null || DeviceConfig.isHighTextContrastEnabled(this.mContext) || this.mClockInfo.isSuperSaveOpen()) {
            return;
        }
        if (this.mClockInfo != null) {
            boolean zSupportBackgroundBlur = DeviceConfig.supportBackgroundBlur(this.mContext);
            int clockEffect = this.mClockInfo.getClockEffect();
            if (!ClockEffectUtils.isGradualType(this.mClockInfo.getClockEffect()) || !DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                if (ClockEffectUtils.isDifferenceType(this.mClockInfo.getClockEffect())) {
                    if (!this.mClockInfo.isAutoPrimaryColor()) {
                        EasternArtCBaseInfo easternArtCBaseInfo = this.mClockInfo;
                        easternArtCBaseInfo.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(easternArtCBaseInfo.getBlendColor()));
                    } else if (z) {
                        if (map != null && map.get("secondary15") != null) {
                            int iIntValue = map.get("secondary15").intValue();
                            this.mClockInfo.setPrimaryColor(iIntValue);
                            this.mClockInfo.setBlendColor(iIntValue);
                        }
                    } else if (map != null && map.get("secondary85") != null) {
                        int iIntValue2 = map.get("secondary85").intValue();
                        this.mClockInfo.setPrimaryColor(iIntValue2);
                        this.mClockInfo.setBlendColor(iIntValue2);
                    }
                    EasternArtCBaseInfo easternArtCBaseInfo2 = this.mClockInfo;
                    easternArtCBaseInfo2.setAodBlendColor(GlobalColorUtils.transformAodColor(easternArtCBaseInfo2.getBlendColor()));
                    if (!this.mClockInfo.isAutoSecondaryColor()) {
                        EasternArtCBaseInfo easternArtCBaseInfo3 = this.mClockInfo;
                        easternArtCBaseInfo3.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(easternArtCBaseInfo3.getSecondaryBlendColor()));
                    } else if (z) {
                        if (map != null && map.get("secondary15") != null) {
                            int iIntValue3 = map.get("secondary15").intValue();
                            this.mClockInfo.setSecondaryColor(iIntValue3);
                            this.mClockInfo.setSecondaryBlendColor(iIntValue3);
                        }
                    } else if (map != null && map.get("secondary85") != null) {
                        int iIntValue4 = map.get("secondary85").intValue();
                        this.mClockInfo.setSecondaryColor(iIntValue4);
                        this.mClockInfo.setSecondaryBlendColor(iIntValue4);
                    }
                    EasternArtCBaseInfo easternArtCBaseInfo4 = this.mClockInfo;
                    easternArtCBaseInfo4.setAodSecondaryBlendColor(GlobalColorUtils.transformAodColor(easternArtCBaseInfo4.getSecondaryBlendColor()));
                } else if (!ClockEffectUtils.isGlowType(this.mClockInfo.getClockEffect()) || !DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                    if (ClockEffectUtils.needPickSolidColor(this.mClockInfo.getClockEffect(), zSupportBackgroundBlur)) {
                        if (ClockEffectUtils.isBlurMixType(clockEffect)) {
                            if (!this.mClockInfo.isAutoPrimaryColor()) {
                                this.mClockInfo.convertPrimaryColorByBlendColor(true);
                            }
                            if (!this.mClockInfo.isAutoSecondaryColor()) {
                                this.mClockInfo.convertSecondaryColorByBlendColor(true);
                            }
                        }
                        if (this.mClockInfo.isAutoPrimaryColor()) {
                            if (z) {
                                primaryColor = (map == null || map.get("secondary30") == null) ? this.mClockInfo.getPrimaryColor() : map.get("secondary30").intValue();
                            } else {
                                primaryColor = -1;
                            }
                            this.mClockInfo.setPrimaryColor(primaryColor);
                        }
                        if (this.mClockInfo.isAutoSecondaryColor()) {
                            if (z) {
                                secondaryColor = (map == null || map.get("secondary30") == null) ? this.mClockInfo.getSecondaryColor() : map.get("secondary30").intValue();
                            } else {
                                secondaryColor = -1;
                            }
                            this.mClockInfo.setSecondaryColor(secondaryColor);
                        }
                        EasternArtCBaseInfo easternArtCBaseInfo5 = this.mClockInfo;
                        easternArtCBaseInfo5.setAodPrimaryColor(GlobalColorUtils.transformAodColor(easternArtCBaseInfo5.getPrimaryColor()));
                        EasternArtCBaseInfo easternArtCBaseInfo6 = this.mClockInfo;
                        easternArtCBaseInfo6.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(easternArtCBaseInfo6.getPrimaryColor()));
                        EasternArtCBaseInfo easternArtCBaseInfo7 = this.mClockInfo;
                        easternArtCBaseInfo7.setAodSecondaryColor(GlobalColorUtils.transformAodColor(easternArtCBaseInfo7.getSecondaryColor()));
                        EasternArtCBaseInfo easternArtCBaseInfo8 = this.mClockInfo;
                        easternArtCBaseInfo8.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(easternArtCBaseInfo8.getSecondaryColor()));
                        EasternArtCBaseInfo easternArtCBaseInfo9 = this.mClockInfo;
                        easternArtCBaseInfo9.setFullAodInfoColor(easternArtCBaseInfo9.getFullAodPrimaryColor());
                    } else {
                        this.mClockInfo.setPrimaryColor(-1);
                        this.mClockInfo.setSecondaryColor(-1);
                        if (this.mClockInfo.isAutoPrimaryColor()) {
                            if (z) {
                                color3 = (map == null || map.get("neutral-variant30") == null) ? this.mClockInfo.getPrimaryColor() : map.get("neutral-variant30").intValue();
                            } else {
                                color3 = Color.parseColor("#FFABABAB");
                            }
                            if (z) {
                                color4 = (map == null || map.get("secondary70") == null) ? color3 : map.get("secondary70").intValue();
                            } else {
                                color4 = Color.parseColor("#FFBEBEBE");
                            }
                            this.mClockInfo.setBlendColor(color3);
                            this.mClockInfo.setAodBlendColor(color4);
                            this.mClockInfo.setAodPrimaryColor(color4);
                            this.mClockInfo.setFullAodBlendColor(Color.parseColor("#FFABABAB"));
                        } else {
                            int blendColor = this.mClockInfo.getBlendColor();
                            int iTransformAodColor = GlobalColorUtils.transformAodColor(blendColor);
                            this.mClockInfo.setAodBlendColor(iTransformAodColor);
                            this.mClockInfo.setAodPrimaryColor(iTransformAodColor);
                            this.mClockInfo.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(blendColor));
                        }
                        if (this.mClockInfo.isAutoSecondaryColor()) {
                            if (z) {
                                color = (map == null || map.get("neutral-variant30") == null) ? this.mClockInfo.getSecondaryColor() : map.get("neutral-variant30").intValue();
                            } else {
                                color = Color.parseColor("#FFABABAB");
                            }
                            if (z) {
                                color2 = (map == null || map.get("secondary70") == null) ? color : map.get("secondary70").intValue();
                            } else {
                                color2 = Color.parseColor("#FFBEBEBE");
                            }
                            this.mClockInfo.setSecondaryBlendColor(color);
                            this.mClockInfo.setAodSecondaryBlendColor(color2);
                            this.mClockInfo.setAodSecondaryColor(color2);
                            this.mClockInfo.setFullAodSecondaryBlendColor(Color.parseColor("#FFABABAB"));
                        } else {
                            int secondaryBlendColor = this.mClockInfo.getSecondaryBlendColor();
                            int iTransformAodColor2 = GlobalColorUtils.transformAodColor(secondaryBlendColor);
                            this.mClockInfo.setAodSecondaryBlendColor(iTransformAodColor2);
                            this.mClockInfo.setAodSecondaryColor(iTransformAodColor2);
                            this.mClockInfo.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(secondaryBlendColor));
                        }
                    }
                }
            }
            if (z) {
                this.mClockInfo.setOriginMagazineColor(-16777216);
            } else {
                this.mClockInfo.setOriginMagazineColor(-1);
            }
        }
        updateColor();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        this.mClockInfo = (EasternArtCBaseInfo) clockStyleInfo;
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

    public void setTextTypeFace(TextView textView, int i) {
        int i2;
        Typeface miSerifTcVfByWeight;
        this.mFontStyle = i;
        if (i != 1) {
            i2 = 0;
            if (i == 2) {
                miSerifTcVfByWeight = FontUtils.getCursiveRegular(getContext());
            } else if (i != 3) {
                miSerifTcVfByWeight = FontUtils.getOtherFont("miclock-miserif-tc-vf");
            } else {
                i2 = 330;
                miSerifTcVfByWeight = FontUtils.getMiSans(330);
            }
        } else {
            i2 = 500;
            miSerifTcVfByWeight = FontUtils.getMiSerifTcVfByWeight(500);
        }
        textView.setTypeface(miSerifTcVfByWeight);
        if (i2 > 0) {
            textView.setFontVariationSettings(null);
            textView.setFontVariationSettings(FontVariationAxis.toFontVariationSettings(new FontVariationAxis[]{new FontVariationAxis("wght", i2)}));
        }
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        this.mIsOnHour = this.mCalendar.get(20) == 0;
    }
}
