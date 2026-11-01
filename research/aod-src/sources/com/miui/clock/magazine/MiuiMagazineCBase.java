package com.miui.clock.magazine;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import com.miui.clock.MiuiGalleryBaseClock;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.Effect;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.ColorUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.GlobalColorUtils;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class MiuiMagazineCBase extends MiuiGalleryBaseClock {
    public MiuiMagazineCBaseInfo mClockInfo;
    public int[] mCurrentTimeArray;
    public int mSmallestScreenWidth;
    public final String[] ssTags;

    public MiuiMagazineCBase(Context context) {
        super(context);
        this.mCurrentTimeArray = new int[4];
        this.ssTags = new String[]{"ss01", "ss02", "ss03", "ss04", "ss05", "ss06", "ss07", "ss08", "ss09", "ss10"};
    }

    public MiuiMagazineCBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurrentTimeArray = new int[4];
        this.ssTags = new String[]{"ss01", "ss02", "ss03", "ss04", "ss05", "ss06", "ss07", "ss08", "ss09", "ss10"};
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        MiuiMagazineCBaseInfo miuiMagazineCBaseInfo = this.mClockInfo;
        if (miuiMagazineCBaseInfo == null) {
            return;
        }
        int clockEffect = miuiMagazineCBaseInfo.getClockEffect();
        if (z || !ClockEffectUtils.isGradualType(clockEffect)) {
            resetGradientParams();
        }
    }

    public boolean diffColor(MiuiMagazineCBaseInfo miuiMagazineCBaseInfo) {
        return (miuiMagazineCBaseInfo == null || miuiMagazineCBaseInfo.getClockStyle() == 6 || miuiMagazineCBaseInfo.getClockStyle() == 9) ? false : true;
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public ClockStyleInfo getClockStyleInfo() {
        return this.mClockInfo;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public float[] getGradientAnimationParams() {
        MiuiMagazineCBaseInfo miuiMagazineCBaseInfo = this.mClockInfo;
        return miuiMagazineCBaseInfo == null ? Effect.EMPTY_GRADIENT : miuiMagazineCBaseInfo.getAodGradientParams();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public float[] getGradientParams() {
        MiuiMagazineCBaseInfo miuiMagazineCBaseInfo = this.mClockInfo;
        if (miuiMagazineCBaseInfo == null) {
            return Effect.EMPTY_GRADIENT;
        }
        boolean zIsAODType = ClockStyleInfo.isAODType(miuiMagazineCBaseInfo.getDisplayType());
        MiuiMagazineCBaseInfo miuiMagazineCBaseInfo2 = this.mClockInfo;
        return zIsAODType ? miuiMagazineCBaseInfo2.getAodGradientParams() : miuiMagazineCBaseInfo2.getLockGradientParams();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    public int getMagazineCNotificationMagazineY() {
        int dimen = getDimen(R.dimen.miui_magazine_c_clock_notification_margin_top);
        int dimen2 = getDimen(R.dimen.miui_magazine_c_clock_notification_height);
        return dimen + dimen2 + getDimen(R.dimen.miui_magazine_c_clock_notification_gallery_margin_top) + getDimen(R.dimen.miui_magazine_c_clock_notification_date_height);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getMagazineColor() {
        MiuiMagazineCBaseInfo miuiMagazineCBaseInfo = this.mClockInfo;
        if (miuiMagazineCBaseInfo != null) {
            return ColorUtils.blendColor(miuiMagazineCBaseInfo.getOriginMagazineColor(), 0.6f);
        }
        return 0;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockBottom() {
        return getDimen(R.dimen.notification_bottom_magazine_c);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockTop() {
        return getDimen(R.dimen.miui_magazine_c_clock_notification_margin_top);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationRelativePosition() {
        return (DeviceConfig.PAD_DEVICE && getResources().getConfiguration().orientation == 2) ? 2 : 1;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiBaseClock2, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int i = configuration.smallestScreenWidthDp;
        if ((DeviceConfig.isFlip() || DeviceConfig.FOLD_DEVICE) && i != this.mSmallestScreenWidth) {
            this.mSmallestScreenWidth = i;
            setClockStyleInfo();
        }
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
        int iIntValue;
        int iIntValue2;
        super.setClockPalette(i, z, map, z2);
        if (map != null) {
            str = "secondary98=" + map.get("secondary98") + ",secondary20=" + map.get("secondary20") + ",secondary85=" + map.get("secondary85") + ",secondary40=" + map.get("secondary40");
        } else {
            str = "null";
        }
        Log.d("ClockPalette", "setClockPalette: type = " + i + ", textDark = " + z + ", palette = " + str);
        if (this.mClockInfo == null || DeviceConfig.isHighTextContrastEnabled(this.mContext)) {
            return;
        }
        if (this.mClockInfo.isSuperSaveOpen()) {
            Log.d("MiuiMagazineCBase", "isSuperSaveOpen not setClockPalette");
            return;
        }
        if (map != null) {
            int clockEffect = this.mClockInfo.getClockEffect();
            boolean zSupportBackgroundBlur = DeviceConfig.supportBackgroundBlur(this.mContext);
            if (ClockEffectUtils.isDifferenceType(this.mClockInfo.getClockEffect())) {
                if (this.mClockInfo.isAutoPrimaryColor()) {
                    if (z) {
                        if (map.get("secondary15") != null) {
                            int iIntValue3 = map.get("secondary15").intValue();
                            this.mClockInfo.setPrimaryColor(iIntValue3);
                            this.mClockInfo.setBlendColor(iIntValue3);
                        }
                    } else if (map.get("secondary95") != null) {
                        int iIntValue4 = map.get("secondary95").intValue();
                        this.mClockInfo.setPrimaryColor(iIntValue4);
                        this.mClockInfo.setBlendColor(iIntValue4);
                    }
                }
                if (this.mClockInfo.isAutoSecondaryColor()) {
                    if (z) {
                        if (map.get("secondary30") != null) {
                            int iIntValue5 = map.get("secondary30").intValue();
                            this.mClockInfo.setSecondaryColor(iIntValue5);
                            this.mClockInfo.setSecondaryBlendColor(iIntValue5);
                        }
                    } else if (map.get("secondary85") != null) {
                        int iIntValue6 = map.get("secondary85").intValue();
                        this.mClockInfo.setSecondaryColor(iIntValue6);
                        this.mClockInfo.setSecondaryBlendColor(iIntValue6);
                    }
                }
                this.mClockInfo.convertAnimationColor();
                MiuiMagazineCBaseInfo miuiMagazineCBaseInfo = this.mClockInfo;
                miuiMagazineCBaseInfo.setFullAodPrimaryColor(miuiMagazineCBaseInfo.getPrimaryColor());
                MiuiMagazineCBaseInfo miuiMagazineCBaseInfo2 = this.mClockInfo;
                miuiMagazineCBaseInfo2.setFullAodSecondaryColor(miuiMagazineCBaseInfo2.getSecondaryColor());
                return;
            }
            if (ClockEffectUtils.isGlowType(this.mClockInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                return;
            }
            if (ClockEffectUtils.needPickSolidColor(this.mClockInfo.getClockEffect(), zSupportBackgroundBlur)) {
                if (ClockEffectUtils.isBlurMixType(clockEffect)) {
                    if (!this.mClockInfo.isAutoPrimaryColor()) {
                        this.mClockInfo.convertPrimaryColorByBlendColor(true);
                    }
                    if (!this.mClockInfo.isAutoSecondaryColor()) {
                        this.mClockInfo.convertSecondaryColorByBlendColor(true);
                    }
                }
                int primaryColor = this.mClockInfo.getPrimaryColor();
                if (this.mClockInfo.isAutoPrimaryColor()) {
                    if (z) {
                        primaryColor = map.get("secondary20") != null ? map.get("secondary20").intValue() : this.mClockInfo.getPrimaryColor();
                    } else {
                        primaryColor = map.get("secondary98") != null ? map.get("secondary98").intValue() : this.mClockInfo.getPrimaryColor();
                    }
                    this.mClockInfo.setOriginMagazineColor(primaryColor);
                }
                this.mClockInfo.setPrimaryColor(primaryColor);
                this.mClockInfo.setAnimationPrimaryColor(primaryColor);
                int secondaryColor = this.mClockInfo.getSecondaryColor();
                if (this.mClockInfo.isAutoSecondaryColor()) {
                    if (z) {
                        iIntValue2 = map.get("secondary40") != null ? map.get("secondary40").intValue() : this.mClockInfo.getSecondaryColor();
                    } else {
                        iIntValue2 = map.get("secondary85") != null ? map.get("secondary85").intValue() : this.mClockInfo.getSecondaryColor();
                    }
                    secondaryColor = iIntValue2;
                }
                this.mClockInfo.setSecondaryColor(secondaryColor);
                MiuiMagazineCBaseInfo miuiMagazineCBaseInfo3 = this.mClockInfo;
                miuiMagazineCBaseInfo3.setAnimationSecondaryColor(miuiMagazineCBaseInfo3.getSecondaryColor());
                this.mClockInfo.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(primaryColor));
                this.mClockInfo.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(secondaryColor));
                return;
            }
            if (this.mClockInfo.isAutoPrimaryColor()) {
                if (z) {
                    color3 = map.get("neutral-variant30") != null ? map.get("neutral-variant30").intValue() : this.mClockInfo.getPrimaryColor();
                } else {
                    color3 = Color.parseColor("#FFABABAB");
                }
                if (z) {
                    color4 = map.get("secondary80") != null ? map.get("secondary80").intValue() : color3;
                } else {
                    color4 = Color.parseColor("#FFBEBEBE");
                }
                this.mClockInfo.setPrimaryColor(-1);
                this.mClockInfo.setBlendColor(color3);
                this.mClockInfo.setAodBlendColor(color4);
                if (z) {
                    iIntValue = map.get("secondary20") != null ? map.get("secondary20").intValue() : this.mClockInfo.getPrimaryColor();
                } else {
                    iIntValue = map.get("secondary98") != null ? map.get("secondary98").intValue() : this.mClockInfo.getPrimaryColor();
                }
                this.mClockInfo.setOriginMagazineColor(iIntValue);
                this.mClockInfo.setFullAodBlendColor(Color.parseColor("#FFABABAB"));
            } else {
                this.mClockInfo.setPrimaryColor(-1);
                MiuiMagazineCBaseInfo miuiMagazineCBaseInfo4 = this.mClockInfo;
                miuiMagazineCBaseInfo4.setAodBlendColor(GlobalColorUtils.transformAodColor(miuiMagazineCBaseInfo4.getBlendColor()));
                MiuiMagazineCBaseInfo miuiMagazineCBaseInfo5 = this.mClockInfo;
                miuiMagazineCBaseInfo5.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(miuiMagazineCBaseInfo5.getBlendColor()));
                MiuiMagazineCBaseInfo miuiMagazineCBaseInfo6 = this.mClockInfo;
                miuiMagazineCBaseInfo6.setOriginMagazineColor(miuiMagazineCBaseInfo6.getBlendColor());
            }
            if (this.mClockInfo.isAutoSecondaryColor()) {
                this.mClockInfo.setSecondaryColor(-8355712);
                if (z) {
                    color = map.get("neutral-variant30") != null ? map.get("neutral-variant30").intValue() : this.mClockInfo.getPrimaryColor();
                } else {
                    color = Color.parseColor("#FFABABAB");
                }
                this.mClockInfo.setSecondaryBlendColor(color);
                if (z) {
                    color2 = map.get("secondary80") != null ? map.get("secondary80").intValue() : color;
                } else {
                    color2 = Color.parseColor("#FFBEBEBE");
                }
                this.mClockInfo.setAodSecondaryBlendColor(color2);
                if (diffColor(this.mClockInfo)) {
                    if (!z) {
                        color = Color.parseColor("#FF808080");
                    } else if (map.get("secondary60") != null) {
                        color = map.get("secondary60").intValue();
                    }
                    this.mClockInfo.setAodSecondaryBlendColor(color);
                }
                this.mClockInfo.setFullAodSecondaryBlendColor(Color.parseColor("#FFABABAB"));
            } else {
                this.mClockInfo.setSecondaryColor(-8355712);
                MiuiMagazineCBaseInfo miuiMagazineCBaseInfo7 = this.mClockInfo;
                miuiMagazineCBaseInfo7.setAodSecondaryBlendColor(GlobalColorUtils.transformAodColor(miuiMagazineCBaseInfo7.getSecondaryBlendColor()));
                MiuiMagazineCBaseInfo miuiMagazineCBaseInfo8 = this.mClockInfo;
                miuiMagazineCBaseInfo8.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(miuiMagazineCBaseInfo8.getSecondaryBlendColor()));
            }
            this.mClockInfo.convertAnimationColor();
            MiuiMagazineCBaseInfo miuiMagazineCBaseInfo9 = this.mClockInfo;
            miuiMagazineCBaseInfo9.setFullAodPrimaryColor(miuiMagazineCBaseInfo9.getPrimaryColor());
            MiuiMagazineCBaseInfo miuiMagazineCBaseInfo10 = this.mClockInfo;
            miuiMagazineCBaseInfo10.setFullAodSecondaryColor(miuiMagazineCBaseInfo10.getSecondaryColor());
        }
    }

    public void setClockStyleInfo() {
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        this.mClockInfo = (MiuiMagazineCBaseInfo) clockStyleInfo;
        updateTime();
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

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        String time = DateFormatUtils.formatTime(this.m24HourFormat ? "HHmm" : "hhmm");
        for (int i = 0; i < time.length(); i++) {
            this.mCurrentTimeArray[i] = Integer.parseInt(String.valueOf(time.charAt(i)));
        }
    }
}
