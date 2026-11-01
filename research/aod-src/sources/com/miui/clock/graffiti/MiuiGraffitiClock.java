package com.miui.clock.graffiti;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import com.miui.clock.MiuiGalleryBaseClock;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.utils.ColorUtils;
import com.miui.clock.utils.DeviceConfig;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class MiuiGraffitiClock extends MiuiGalleryBaseClock {
    public MiuiGraffitiClockView clockView;
    public MiuiGraffitiClockInfoBase mClockInfo;

    public MiuiGraffitiClock(Context context) {
        super(context);
    }

    public MiuiGraffitiClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MiuiGraffitiClock(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private void initView() {
        ((Guideline) this.clockView.findViewById(R.id.guideline)).setGuidelinePercent(0.5f);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.clockView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = -1;
        ((ViewGroup.MarginLayoutParams) layoutParams).height = -2;
        layoutParams.setMargins(0, (int) (getResources().getDimensionPixelSize(R.dimen.miui_graffiti_clock_margin_top) * DeviceConfig.calculateScale(getContext())), 0, 0);
        layoutParams.topToTop = 0;
        this.clockView.setLayoutParams(layoutParams);
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
    public View getIClockView(ClockViewType clockViewType) {
        if (clockViewType != ClockViewType.ALL_VIEW && clockViewType != ClockViewType.CLOCK_CONTAINER) {
            return super.getIClockView(clockViewType);
        }
        return this.clockView;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getMagazineColor() {
        MiuiGraffitiClockInfoBase miuiGraffitiClockInfoBase = this.mClockInfo;
        if (miuiGraffitiClockInfoBase != null) {
            return ColorUtils.blendColor(miuiGraffitiClockInfoBase.getPrimaryColor(), 0.6f);
        }
        return 0;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockBottom() {
        return this.mMagazineInfoVisible ? getDimen(R.dimen.notification_bottom_graffiti_with_gallery) : getDimen(R.dimen.notification_bottom_graffiti);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockTop() {
        return getDimen(R.dimen.miui_graffiti_clock_margin_top);
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public View getNotificationClockTopView() {
        return this.clockView;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
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
        initView();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.clockView = (MiuiGraffitiClockView) findViewById(R.id.content_miui_graffiti);
        initView();
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
        int primaryColor;
        super.setClockPalette(i, z, map, z2);
        if (map != null) {
            str = "primary7=" + map.get("primary7") + ",primary18=" + map.get("primary18") + ",secondary60=" + map.get("secondary60");
        } else {
            str = "null";
        }
        Log.d("MiuiGraffitiClock ClockPalette", "setClockPalette: type = " + i + ", textDark = " + z + ", palette = " + str + ", mWallpaperSupportDepth = " + this.mWallpaperSupportDepth);
        MiuiGraffitiClockInfoBase miuiGraffitiClockInfoBase = this.mClockInfo;
        if (miuiGraffitiClockInfoBase != null) {
            if (miuiGraffitiClockInfoBase.isAutoPrimaryColor()) {
                if (this.mWallpaperSupportDepth) {
                    if (i == 1) {
                        primaryColor = (map == null || map.get("primary7") == null) ? this.mClockInfo.getPrimaryColor() : map.get("primary7").intValue();
                    } else if (i == 2) {
                        primaryColor = (map == null || map.get("primary18") == null) ? this.mClockInfo.getPrimaryColor() : map.get("primary18").intValue();
                    } else {
                        primaryColor = (map == null || map.get("secondary60") == null) ? this.mClockInfo.getPrimaryColor() : map.get("secondary60").intValue();
                    }
                    this.mClockInfo.setOriginMagazineColor(primaryColor);
                } else if (z) {
                    primaryColor = (map == null || map.get("primary20") == null) ? this.mClockInfo.getPrimaryColor() : map.get("primary20").intValue();
                } else {
                    primaryColor = (map == null || map.get("primary98") == null) ? this.mClockInfo.getPrimaryColor() : map.get("primary98").intValue();
                }
                this.mClockInfo.setOriginMagazineColor(primaryColor);
                this.mClockInfo.setPrimaryColor(primaryColor);
                MiuiGraffitiClockInfoBase miuiGraffitiClockInfoBase2 = this.mClockInfo;
                miuiGraffitiClockInfoBase2.setAnimationPrimaryColor(miuiGraffitiClockInfoBase2.getPrimaryColor());
            }
            this.clockView.setWallpaperSupportDepth(z2);
            this.clockView.setClockStyleInfo(this.mClockInfo);
        }
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        MiuiGraffitiClockInfoBase miuiGraffitiClockInfoBase = (MiuiGraffitiClockInfoBase) clockStyleInfo;
        this.mClockInfo = miuiGraffitiClockInfoBase;
        this.clockView.setClockStyleInfo(miuiGraffitiClockInfoBase);
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
    public void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
        setClockPalette(this.mChoosePaletteType, this.mTextDark, this.mPalette, z);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        this.clockView.updateTime();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTimeZone(String str) {
        this.clockView.updateTimeZone(str);
    }
}
