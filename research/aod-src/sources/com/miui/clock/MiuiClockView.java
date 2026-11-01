package com.miui.clock;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.FrameLayout;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.utils.DeviceConfig;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class MiuiClockView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Context mContext;
    public boolean mIgnoreAccessibilityScale;
    public boolean mIsAttachedToWindow;
    public MiuiClockController mMiuiClockController;

    public MiuiClockView(Context context) {
        this(context, null);
    }

    public MiuiClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIgnoreAccessibilityScale = false;
        if (context.getApplicationContext() != null) {
            this.mContext = context.getApplicationContext();
        } else {
            this.mContext = context;
        }
        if (context instanceof ContextThemeWrapper) {
            return;
        }
        this.mContext = context;
    }

    public Rect calculateColorPickupRect(Boolean bool) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        return miuiClockController != null ? miuiClockController.calculateColorPickupRect(bool, new Rect()) : new Rect();
    }

    public final MiuiClockView createMiuiClockController(ClockBean clockBean, int i, boolean z) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null && this.mIsAttachedToWindow) {
            miuiClockController.onDetachedFromWindow();
            this.mMiuiClockController = null;
        }
        MiuiClockController miuiClockController2 = new MiuiClockController(this.mContext, this, clockBean, i, z);
        this.mMiuiClockController = miuiClockController2;
        miuiClockController2.ignoreAccessibilityScale(this.mIgnoreAccessibilityScale);
        if (z) {
            this.mMiuiClockController.setIsAttach(this.mIsAttachedToWindow);
            this.mMiuiClockController.buildAsync();
            return this;
        }
        this.mMiuiClockController.build();
        if (this.mIsAttachedToWindow) {
            this.mMiuiClockController.onAttachedToWindow();
        }
        return this;
    }

    public View getBackgroundBlurContainer() {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            return miuiClockController.getBackgroundBlurContainer();
        }
        return null;
    }

    public ClockBean getClockBean() {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            return miuiClockController.getClockBean();
        }
        return null;
    }

    public int getClockHeight() {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            return miuiClockController.getClockHeight();
        }
        return 0;
    }

    public Map<String, Integer> getClockPalette() {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            return miuiClockController.getClockPalette();
        }
        return null;
    }

    public int getClockPaletteType() {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            return miuiClockController.getClockPaletteType();
        }
        return 0;
    }

    public ClockStyleInfo getClockStyleInfo() {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            return miuiClockController.getClockStyleInfo();
        }
        return null;
    }

    public float getClockVisibleHeight() {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            return miuiClockController.getClockVisibleHeight();
        }
        return 0.0f;
    }

    public Rect getDepthAvoidRect() {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        return miuiClockController != null ? miuiClockController.getDepthAvoidRect() : new Rect();
    }

    public int getGalleryGravity() {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            return miuiClockController.getGalleryGravity();
        }
        return 8388611;
    }

    public String getHealthJson() {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        return miuiClockController != null ? miuiClockController.getHealthJson() : "";
    }

    public View getIClockView(ClockViewType clockViewType) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            return miuiClockController.getIClockView(clockViewType);
        }
        return null;
    }

    public String getLocalCity() {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            return miuiClockController.getLocalCity();
        }
        return null;
    }

    public int getMagazineColor() {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            return miuiClockController.getMagazineColor();
        }
        return 0;
    }

    public int getNotificationClockBottom() {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            return miuiClockController.getNotificationClockBottom();
        }
        return 0;
    }

    public int getNotificationRelativePosition() {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            return miuiClockController.getNotificationRelativePosition();
        }
        return 1;
    }

    public float getTopMargin() {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            return miuiClockController.getTopMargin();
        }
        return 0.0f;
    }

    public String getWeatherJson() {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        return miuiClockController != null ? miuiClockController.getWeatherJson() : "";
    }

    public void ignoreAccessibilityScale(boolean z) {
        this.mIgnoreAccessibilityScale = z;
    }

    public MiuiClockView init(int i, boolean z) {
        return createMiuiClockController(null, i, z);
    }

    public MiuiClockView init(ClockBean clockBean, int i, boolean z) {
        if (clockBean == null) {
            clockBean = ClockBean.getClassicDefaultBean(this.mContext);
        }
        return createMiuiClockController(clockBean, i, z);
    }

    public MiuiClockView init(ClockBean clockBean, boolean z) {
        return init(clockBean, 0, z);
    }

    public boolean isDualClock() {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            return miuiClockController.isDualClock();
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIsAttachedToWindow = true;
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.onAttachedToWindow();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mIsAttachedToWindow = false;
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.onDetachedFromWindow();
        }
    }

    public void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
        }
    }

    public void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
        }
    }

    public void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
        }
    }

    public void setAutoDualClock(boolean z) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setAutoDualClock(z);
        }
    }

    public void setAutoUpdateTime(boolean z) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setAutoUpdateTime(z);
        }
    }

    public void setBackgroundBlurContainer(View view) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setOutBackgroundBlurContainer(view);
        }
    }

    public void setClockAlpha(float f) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setClockAlpha(f);
        }
    }

    public void setClockBean(ClockBean clockBean) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setClockBean(clockBean);
        }
    }

    public void setClockContext(Context context) {
        this.mContext = context;
    }

    public void setClockPalette(int i, boolean z, Map<String, Integer> map) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController == null || map == null) {
            return;
        }
        miuiClockController.setClockPalette(i, z, map);
    }

    public void setClockPalette(int i, boolean z, Map<String, Integer> map, View view) {
        setBackgroundBlurContainer(view);
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController == null || map == null) {
            return;
        }
        miuiClockController.setClockPalette(i, z, map);
    }

    public void setClockPaletteChangeListener(MiuiClockController.ClockPaletteChangeListener clockPaletteChangeListener) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setClockPaletteChangeListener(clockPaletteChangeListener);
        }
    }

    public void setClockStyle(int i) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setClockStyle(i);
        }
    }

    public void setContext(Context context) {
        if (DeviceConfig.FOLD_DEVICE) {
            this.mContext = context;
        }
    }

    public void setEditMode(boolean z) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setEditMode(z);
        }
    }

    public void setEditorColonShow(boolean z) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setEditorColonShow(z);
        }
    }

    public void setEditorColonViewVisible(boolean z) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setEditorColonViewVisible(z);
        }
    }

    public void setGradientIndex(int i) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setGradientIndex(i);
        }
    }

    public void setHasTopMargin(boolean z) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setHasTopMargin(z);
        }
    }

    public void setInfoTextColorDark(boolean z) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setInfoTextColorDark(z);
        }
    }

    public void setLoadListener(MiuiClockController.LoadListener loadListener) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setLoadListener(loadListener);
        }
    }

    public void setLocalCity(String str) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setLocalCity(str);
        }
    }

    public void setMagazineInfoVisible(boolean z) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setMagazineInfoVisible(z);
        }
    }

    public void setMinuteColonAlpha(float f) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setMinuteColonAlpha(f);
        }
    }

    @Deprecated
    public void setMinuteColor(int i, int i2) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setMinuteColor(i, i2);
        }
    }

    public void setMiuiClockInfoListener(MiuiClockController.MiuiClockInfoListener miuiClockInfoListener) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setMiuiClockInfoListener(miuiClockInfoListener);
        }
    }

    public void setOwnerInfo(String str) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setOwnerInfo(str);
        }
    }

    public void setScaleRatio(float f) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setScaleRatio(f);
        }
    }

    public void setShowLunarCalendar(int i) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setShowLunarCalendar(i);
        }
    }

    public void setTextColorDark(boolean z) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setTextColorDark(z);
        }
    }

    public void setTimeUpdateListener(MiuiClockController.TimeUpdateListener timeUpdateListener) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setTimeUpdateListener(timeUpdateListener);
        }
    }

    public void setWallpaperSupportDepth(boolean z) {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.setWallpaperSupportDepth(z);
        }
    }

    public void updateTime() {
        MiuiClockController miuiClockController = this.mMiuiClockController;
        if (miuiClockController != null) {
            miuiClockController.updateTime();
        }
    }
}
