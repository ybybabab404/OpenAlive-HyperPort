package com.miui.clock;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.avoid.DepthAvoidEvaluatorDebug;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes.dex */
public abstract class MiuiGalleryBaseClock extends MiuiBaseClock2 {
    public boolean isToFullAod;
    public int mChoosePaletteType;
    public boolean mInAnimationToFullAod;
    public ViewGroup mMagazineGroup;
    public boolean mMagazineInfoVisible;
    public Map mPalette;
    public boolean mTextDark;
    public int mTextDarkAlpha;
    public boolean mWallpaperSupportDepth;
    public float[] userAnimGradientParams;

    public MiuiGalleryBaseClock(Context context) {
        super(context);
        this.mInAnimationToFullAod = false;
        this.isToFullAod = false;
        this.mTextDarkAlpha = 255;
    }

    public MiuiGalleryBaseClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mInAnimationToFullAod = false;
        this.isToFullAod = false;
        this.mTextDarkAlpha = 255;
    }

    public MiuiGalleryBaseClock(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mInAnimationToFullAod = false;
        this.isToFullAod = false;
        this.mTextDarkAlpha = 255;
    }

    public float[] getCurrentGradientParams() {
        float[] fArr = this.userAnimGradientParams;
        return fArr == null ? getGradientParams() : fArr;
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public Rect getDepthAvoidRect() {
        Log.d("MiuiGalleryBaseClock", "getDepthAvoidRect: default empty");
        return new Rect();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public int getDimen(int i) {
        return super.getDimen(i);
    }

    public float getDimensionFloat(int i) {
        return getResources().getDimension(i) * DeviceConfig.calculateScale(getContext());
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        return clockViewType == ClockViewType.MAGAZINE_INFO ? this.mMagazineGroup : super.getIClockView(clockViewType);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getMagazineColor() {
        return super.getMagazineColor();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public abstract /* synthetic */ int getNotificationClockBottom();

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    public float getScaleByGradientDesign() {
        return (this.mSmallestScreenWidth / DeviceConfig.getDesignDp(this.mContext)) / 2.7551022f;
    }

    public int getTextDarkAlpha(boolean z) {
        boolean z2 = this.mInAnimationToFullAod;
        if (z2 && this.isToFullAod && z) {
            return this.mTextDarkAlpha;
        }
        if (z2 && !this.isToFullAod && z) {
            return this.mTextDarkAlpha;
        }
        return 255;
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    public int getmTextDarkAlpha() {
        return this.mTextDarkAlpha;
    }

    public boolean isToFullAod() {
        return this.isToFullAod;
    }

    @Override // com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mMagazineGroup = (ViewGroup) findViewById(R.id.magazine_info_container);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void onHighTextContrastEnabledChanged(boolean z) {
        super.onHighTextContrastEnabledChanged(z);
        if (!z) {
            setClockPalette(this.mChoosePaletteType, this.mTextDark, this.mPalette, this.mWallpaperSupportDepth);
        } else {
            clearColorEffect();
            updateHighTextColors(getClockStyleInfo());
        }
    }

    public void resetGradientParams() {
        this.userAnimGradientParams = null;
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        this.mChoosePaletteType = i;
        this.mTextDark = z;
        this.mPalette = map;
        this.mWallpaperSupportDepth = z2;
        ClockStyleInfo clockStyleInfo = getClockStyleInfo();
        if (clockStyleInfo != null) {
            clockStyleInfo.setCurrentTextDark(z);
            clockStyleInfo.setCurrentLabAlpha(255);
        }
        Log.d("ClockPalette", "setClockPalette: type = " + i + ", textDark = " + z + ", palette = " + (map != null ? "secondary60=" + map.get("secondary60") : "null"));
        if (!DepthAvoidEvaluatorDebug.DEBUG_LOG || map == null) {
            return;
        }
        Log.d("ClockPalette", "┌── Base Palette Dump (setClockPalette) ──");
        for (Map.Entry entry : new TreeMap(map).entrySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append("│ ");
            sb.append((String) entry.getKey());
            sb.append(" = ");
            sb.append(entry.getValue() != null ? String.format("#%08X", entry.getValue()) : "null");
            Log.d("ClockPalette", sb.toString());
        }
        Log.d("ClockPalette", "└── end (" + map.size() + " entries) ──");
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setGradientIndex(int i) {
        ClockStyleInfo clockStyleInfo = getClockStyleInfo();
        if (clockStyleInfo != null) {
            clockStyleInfo.setGradientIndex(i);
            float[] currentGradientParams = getCurrentGradientParams();
            Log.d("MiuiGalleryBaseClock", "setGradientIndex update " + Arrays.toString(currentGradientParams));
            updateGradientEffectColor(currentGradientParams);
        }
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setMagazineInfoVisible(boolean z) {
        this.mMagazineInfoVisible = z;
        ViewGroup viewGroup = this.mMagazineGroup;
        if (viewGroup != null) {
            viewGroup.setVisibility(z ? 0 : 8);
        }
        updateTime();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setSuperSaveOpen(boolean z) {
        if (getClockStyleInfo() != null) {
            getClockStyleInfo().setSuperSaveOpen(z);
        }
        if (z) {
            clearColorEffect();
        }
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    public void updateClockBlendColor() {
        ClockStyleInfo clockStyleInfo = getClockStyleInfo();
        if (clockStyleInfo == null) {
            return;
        }
        boolean zIsFullAODType = ClockStyleInfo.isFullAODType(clockStyleInfo.getDisplayType());
        updateClockBlendColor(zIsFullAODType ? clockStyleInfo.getFullAodBlendColor() : clockStyleInfo.getBlendColor(), zIsFullAODType ? clockStyleInfo.getFullAodSecondaryBlendColor() : clockStyleInfo.getSecondaryBlendColor());
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2) {
        super.updateClockBlendColor(i, i2);
        updateClockinfoForBlendColor(i, i2);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2, int i3, int i4) {
        updateClockinfoForBlendColor(i, i2);
    }

    public void updateClockColor(int i, int i2) {
        ClockStyleInfo clockStyleInfo = getClockStyleInfo();
        if (clockStyleInfo != null) {
            clockStyleInfo.setCurrentPrimaryColor(i);
            clockStyleInfo.setCurrentSecondaryColor(i2);
        }
    }

    public void updateClockEffectsContainer() {
    }

    public final void updateClockinfoForBlendColor(int i, int i2) {
        ClockStyleInfo clockStyleInfo = getClockStyleInfo();
        if (clockStyleInfo != null) {
            clockStyleInfo.updateClockBlendColor(i, i2);
        }
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateGradientEffectColor(float[] fArr) {
        super.updateGradientEffectColor(fArr);
        if (DateFormatUtils.isSystemUI(getContext())) {
            this.userAnimGradientParams = fArr;
        }
    }

    public void updateHighTextColors(ClockStyleInfo clockStyleInfo) {
        if (!DeviceConfig.isHighTextContrastEnabled(this.mContext) || clockStyleInfo == null) {
            return;
        }
        clockStyleInfo.updateAodHighTextColors();
        if (ClockEffectUtils.isBlurMixType(clockStyleInfo.getClockEffect())) {
            if (!clockStyleInfo.isAutoPrimaryColor()) {
                clockStyleInfo.convertPrimaryColorByBlendColor(true);
            }
            if (!clockStyleInfo.isAutoSecondaryColor()) {
                clockStyleInfo.convertSecondaryColorByBlendColor(true);
            }
        }
        updateColor();
    }

    public boolean updateTextDark(boolean z) {
        boolean z2 = this.mInAnimationToFullAod;
        if (z2 && this.isToFullAod && z) {
            return false;
        }
        if (z2 && !this.isToFullAod && z) {
            return true;
        }
        return z;
    }
}
