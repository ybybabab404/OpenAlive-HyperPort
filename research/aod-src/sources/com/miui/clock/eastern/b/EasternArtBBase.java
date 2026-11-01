package com.miui.clock.eastern.b;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.os.Trace;
import android.util.AttributeSet;
import android.widget.TextView;
import com.miui.clock.MiuiGalleryBaseClock;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.GlobalColorUtils;
import com.xiaomi.onetrack.h.d.a;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public abstract class EasternArtBBase extends MiuiGalleryBaseClock {
    public EasternArtBBaseInfo mClockInfo;
    public String mCountry;
    public boolean mIsOnHour;
    public String mLanguage;
    public int mSecondaryColor;

    public EasternArtBBase(Context context) {
        super(context);
        this.mIsOnHour = true;
        this.mSecondaryColor = Color.parseColor("#FA2A2A");
    }

    public EasternArtBBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsOnHour = true;
        this.mSecondaryColor = Color.parseColor("#FA2A2A");
        this.mLanguage = Locale.getDefault().getLanguage();
        this.mCountry = Locale.getDefault().getCountry();
    }

    public EasternArtBBase(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsOnHour = true;
        this.mSecondaryColor = Color.parseColor("#FA2A2A");
    }

    private void setPrimaryColor(int i) {
        this.mClockInfo.setPrimaryColor(i);
        this.mClockInfo.setBlendColor(i);
        this.mClockInfo.setAodBlendColor(GlobalColorUtils.transformAodColor(i));
        this.mClockInfo.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(i));
    }

    private void setSecondaryColor(int i) {
        this.mClockInfo.setSecondaryColor(i);
        this.mClockInfo.setSecondaryBlendColor(i);
        this.mClockInfo.setAodSecondaryBlendColor(GlobalColorUtils.transformAodColor(i));
        this.mClockInfo.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(i));
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

    public int getHourInt(boolean z, Calendar calendar) {
        if (z) {
            return calendar.get(18);
        }
        int i = calendar.get(18);
        if (i == 0) {
            return 12;
        }
        return i < 13 ? i : i - 12;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getMagazineColor() {
        return super.getMagazineColor();
    }

    public int getMinuteInt(boolean z, Calendar calendar) {
        return calendar.get(20);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public abstract /* synthetic */ int getNotificationClockBottom();

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    public int getTimeTextSize() {
        return getDimen(this.mIsOnHour ? R.dimen.miui_eastern_art_time_big_text_size : R.dimen.miui_eastern_art_time_small_text_size);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    public boolean isChineseLanguageOrCountry() {
        if ("zh".equals(this.mLanguage)) {
            return "CN".equals(this.mCountry) || "HK".equals(this.mCountry) || "TW".equals(this.mCountry);
        }
        return false;
    }

    public boolean isEnglishLanguage() {
        return a.r.equals(this.mLanguage);
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void onLanguageChanged() {
        this.mLanguage = Locale.getDefault().getLanguage();
        this.mCountry = Locale.getDefault().getCountry();
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
        int color;
        int color2;
        super.setClockPalette(i, z, map, z2);
        updateHighTextColors(this.mClockInfo);
        if (this.mClockInfo == null || DeviceConfig.isHighTextContrastEnabled(this.mContext) || this.mClockInfo.isSuperSaveOpen()) {
            return;
        }
        if (this.mClockInfo != null) {
            boolean zSupportBackgroundBlur = DeviceConfig.supportBackgroundBlur(this.mContext);
            int clockEffect = this.mClockInfo.getClockEffect();
            if ((!ClockEffectUtils.isGradualType(this.mClockInfo.getClockEffect()) || !DeviceConfig.supportAdvanceVisualEffect(this.mContext)) && (!ClockEffectUtils.isGlowType(this.mClockInfo.getClockEffect()) || !DeviceConfig.supportAdvanceVisualEffect(this.mContext))) {
                if (ClockEffectUtils.isDifferenceType(this.mClockInfo.getClockEffect())) {
                    if (!this.mClockInfo.isAutoPrimaryColor()) {
                        EasternArtBBaseInfo easternArtBBaseInfo = this.mClockInfo;
                        easternArtBBaseInfo.setAodBlendColor(GlobalColorUtils.transformAodColor(easternArtBBaseInfo.getBlendColor()));
                        EasternArtBBaseInfo easternArtBBaseInfo2 = this.mClockInfo;
                        easternArtBBaseInfo2.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(easternArtBBaseInfo2.getBlendColor()));
                    } else if (z) {
                        if (map != null && map.get("secondary15") != null) {
                            setPrimaryColor(map.get("secondary15").intValue());
                        }
                    } else if (map != null && map.get("secondary85") != null) {
                        setPrimaryColor(map.get("secondary85").intValue());
                    }
                    if (this.mClockInfo.isAutoSecondaryColor()) {
                        setSecondaryColor(this.mSecondaryColor);
                    }
                    EasternArtBBaseInfo easternArtBBaseInfo3 = this.mClockInfo;
                    easternArtBBaseInfo3.setAodSecondaryColor(GlobalColorUtils.transformAodColor(easternArtBBaseInfo3.getSecondaryColor()));
                    EasternArtBBaseInfo easternArtBBaseInfo4 = this.mClockInfo;
                    easternArtBBaseInfo4.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(easternArtBBaseInfo4.getSecondaryColor()));
                    EasternArtBBaseInfo easternArtBBaseInfo5 = this.mClockInfo;
                    easternArtBBaseInfo5.setOriginMagazineColor(easternArtBBaseInfo5.getPrimaryColor());
                } else {
                    int primaryColor = -1;
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
                            }
                            this.mClockInfo.setPrimaryColor(primaryColor);
                        }
                        if (this.mClockInfo.isAutoSecondaryColor()) {
                            this.mClockInfo.setSecondaryColor(this.mSecondaryColor);
                        }
                        EasternArtBBaseInfo easternArtBBaseInfo6 = this.mClockInfo;
                        easternArtBBaseInfo6.setSecondaryBlendColor(easternArtBBaseInfo6.getSecondaryColor());
                        EasternArtBBaseInfo easternArtBBaseInfo7 = this.mClockInfo;
                        easternArtBBaseInfo7.setAodPrimaryColor(GlobalColorUtils.transformAodColor(easternArtBBaseInfo7.getPrimaryColor()));
                        EasternArtBBaseInfo easternArtBBaseInfo8 = this.mClockInfo;
                        easternArtBBaseInfo8.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(easternArtBBaseInfo8.getPrimaryColor()));
                        EasternArtBBaseInfo easternArtBBaseInfo9 = this.mClockInfo;
                        easternArtBBaseInfo9.setFullAodInfoColor(easternArtBBaseInfo9.getFullAodPrimaryColor());
                        EasternArtBBaseInfo easternArtBBaseInfo10 = this.mClockInfo;
                        easternArtBBaseInfo10.setAodSecondaryColor(GlobalColorUtils.transformAodColor(easternArtBBaseInfo10.getSecondaryColor()));
                        EasternArtBBaseInfo easternArtBBaseInfo11 = this.mClockInfo;
                        easternArtBBaseInfo11.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(easternArtBBaseInfo11.getSecondaryColor()));
                        EasternArtBBaseInfo easternArtBBaseInfo12 = this.mClockInfo;
                        easternArtBBaseInfo12.setOriginMagazineColor(easternArtBBaseInfo12.getPrimaryColor());
                    } else {
                        this.mClockInfo.setPrimaryColor(-1);
                        if (this.mClockInfo.isAutoPrimaryColor()) {
                            if (z) {
                                color = (map == null || map.get("neutral-variant30") == null) ? this.mClockInfo.getPrimaryColor() : map.get("neutral-variant30").intValue();
                            } else {
                                color = Color.parseColor("#FFABABAB");
                            }
                            if (z) {
                                color2 = (map == null || map.get("secondary70") == null) ? color : map.get("secondary70").intValue();
                            } else {
                                color2 = Color.parseColor("#FFBEBEBE");
                            }
                            this.mClockInfo.setBlendColor(color);
                            this.mClockInfo.setAodPrimaryColor(color2);
                            this.mClockInfo.setAodBlendColor(color2);
                            this.mClockInfo.setFullAodBlendColor(Color.parseColor("#FFABABAB"));
                        } else {
                            int blendColor = this.mClockInfo.getBlendColor();
                            this.mClockInfo.setAodBlendColor(GlobalColorUtils.transformAodColor(blendColor));
                            this.mClockInfo.setAodPrimaryColor(GlobalColorUtils.transformAodColor(blendColor));
                            this.mClockInfo.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(blendColor));
                        }
                        if (this.mClockInfo.isAutoSecondaryColor()) {
                            this.mClockInfo.setSecondaryColor(this.mSecondaryColor);
                            this.mClockInfo.setSecondaryBlendColor(this.mSecondaryColor);
                        }
                        EasternArtBBaseInfo easternArtBBaseInfo13 = this.mClockInfo;
                        easternArtBBaseInfo13.setAodSecondaryColor(GlobalColorUtils.transformAodColor(easternArtBBaseInfo13.getSecondaryColor()));
                        EasternArtBBaseInfo easternArtBBaseInfo14 = this.mClockInfo;
                        easternArtBBaseInfo14.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(easternArtBBaseInfo14.getSecondaryColor()));
                        EasternArtBBaseInfo easternArtBBaseInfo15 = this.mClockInfo;
                        easternArtBBaseInfo15.setAodSecondaryBlendColor(GlobalColorUtils.transformAodColor(easternArtBBaseInfo15.getSecondaryBlendColor()));
                        EasternArtBBaseInfo easternArtBBaseInfo16 = this.mClockInfo;
                        easternArtBBaseInfo16.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(easternArtBBaseInfo16.getSecondaryBlendColor()));
                        if (this.mClockInfo.isAutoPrimaryColor()) {
                            EasternArtBBaseInfo easternArtBBaseInfo17 = this.mClockInfo;
                            if (z) {
                                primaryColor = (map == null || map.get("secondary30") == null) ? this.mClockInfo.getPrimaryColor() : map.get("secondary30").intValue();
                            }
                            easternArtBBaseInfo17.setOriginMagazineColor(primaryColor);
                        } else {
                            EasternArtBBaseInfo easternArtBBaseInfo18 = this.mClockInfo;
                            easternArtBBaseInfo18.setOriginMagazineColor(easternArtBBaseInfo18.getBlendColor());
                        }
                    }
                }
            }
        }
        updateColor();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        this.mClockInfo = (EasternArtBBaseInfo) clockStyleInfo;
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

    public void setTextTypeFace(TextView textView, int i, int i2, int i3, boolean z) {
        Typeface otherFont = (i == 2 && !FontUtils.isTraditionalChinese()) ? FontUtils.getOtherFont("miclock-miserif-sc-vf") : FontUtils.getOtherFont("miclock-miserif-tc-vf");
        String language = Locale.getDefault().getLanguage();
        if (!language.equals("zh") && !language.equals(a.r) && z) {
            otherFont = FontUtils.getMiSans(i2);
        }
        textView.setTypeface(otherFont);
        ArrayList arrayList = new ArrayList();
        if (i2 > 0) {
            arrayList.add(new FontVariationAxis("wght", i2));
        }
        if (i3 > 0) {
            arrayList.add(new FontVariationAxis("opsz", i3));
        }
        Trace.beginSection("fontVariationAxisArrayList");
        if (!arrayList.isEmpty()) {
            textView.setFontVariationSettings(null);
            textView.setFontVariationSettings(FontVariationAxis.toFontVariationSettings((FontVariationAxis[]) arrayList.toArray(new FontVariationAxis[0])));
        }
        Trace.endSection();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateHighTextColors(ClockStyleInfo clockStyleInfo) {
        if (!DeviceConfig.isHighTextContrastEnabled(this.mContext) || clockStyleInfo == null) {
            return;
        }
        if (clockStyleInfo.isAutoSecondaryColor()) {
            clockStyleInfo.setSecondaryColor(this.mSecondaryColor);
        }
        clockStyleInfo.setSecondaryBlendColor(clockStyleInfo.getSecondaryColor());
        clockStyleInfo.setAodSecondaryColor(GlobalColorUtils.transformAodColor(clockStyleInfo.getSecondaryColor()));
        clockStyleInfo.setAodSecondaryBlendColor(GlobalColorUtils.transformAodColor(clockStyleInfo.getSecondaryColor()));
        clockStyleInfo.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(clockStyleInfo.getSecondaryColor()));
        clockStyleInfo.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(clockStyleInfo.getSecondaryColor()));
        if (clockStyleInfo.getAodPrimaryColor() == 0) {
            clockStyleInfo.setAodPrimaryColor(-1);
        }
        updateColor();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        this.mIsOnHour = this.mCalendar.get(20) == 0;
    }
}
