package com.miui.clock;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.MiuiBlurUtils;
import com.miui.clock.utils.MutiDeviceDimenAdapter;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public abstract class MiuiBaseClock2 extends ConstraintLayout implements MiuiClockController.IClockView {
    public ClockStyleInfo clockStyleInfo;
    public boolean isSystemUI;
    public boolean m24HourFormat;
    public Calendar mCalendar;
    public Configuration mConfiguration;
    public Context mContext;
    public boolean mHandlingRelayoutAfterShown;
    public String mLanguage;
    public int mLastFrameworkOrientation;
    public int mOrientation;
    public View mOutBackgroundBlurContainer;
    public int mScreenHeight;
    public int mScreenWidth;
    public int mSmallestScreenWidth;
    public int mViewHeight;
    public int mViewWidth;
    public boolean needRelayoutAfterShown;

    public MiuiBaseClock2(Context context) {
        super(context);
        this.needRelayoutAfterShown = false;
        this.mLastFrameworkOrientation = 0;
        init(context);
    }

    public MiuiBaseClock2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.needRelayoutAfterShown = false;
        this.mLastFrameworkOrientation = 0;
        init(context);
    }

    public MiuiBaseClock2(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.needRelayoutAfterShown = false;
        this.mLastFrameworkOrientation = 0;
        init(context);
    }

    private int getDisplayOrientation() {
        if (DeviceConfig.isPhoneDevice()) {
            return 1;
        }
        if (getDisplay() == null) {
            return 0;
        }
        int rotation = getDisplay().getRotation();
        return (rotation == 1 || rotation == 3) ? 2 : 1;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void checkNeedRelayoutAfterShown() {
        if (this.needRelayoutAfterShown && isParentShown()) {
            Configuration configuration = getResources().getConfiguration();
            int i = configuration.orientation;
            int i2 = this.mLastFrameworkOrientation;
            if (i2 == 0) {
                return;
            }
            if (i != i2) {
                Log.d("MiuiBaseClock2", "checkNeedRelayoutAfterShown skip transient orientation, config:" + configuration + " target:" + this.mLastFrameworkOrientation);
                return;
            }
            this.needRelayoutAfterShown = false;
            Log.d("MiuiBaseClock2", "checkNeedRelayoutAfterShown orientation change, config:" + configuration + " change:" + this.mOrientation + " --> " + i);
            this.mHandlingRelayoutAfterShown = true;
            try {
                onConfigurationChanged(configuration);
            } finally {
                this.mHandlingRelayoutAfterShown = false;
            }
        }
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public View getBackgroundBlurContainer() {
        return this.mOutBackgroundBlurContainer;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public int getClockHeight() {
        return Math.max(getHeight(), 0);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public ClockStyleInfo getClockStyleInfo() {
        return this.clockStyleInfo;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public float getClockVisibleHeight() {
        return Math.max(getHeight(), 0);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ Rect getDepthAvoidRect() {
        return super.getDepthAvoidRect();
    }

    public int getDimen(int i) {
        return (int) (getResources().getDimensionPixelSize(i) * DeviceConfig.calculateScale(getContext()));
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        if (clockViewType == ClockViewType.BLUR_CONTAINER) {
            return getBackgroundBlurContainer();
        }
        if (clockViewType == ClockViewType.ALL_VIEW) {
            return this;
        }
        return null;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getMagazineColor() {
        return super.getMagazineColor();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public abstract /* synthetic */ int getNotificationClockBottom();

    @Override // com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockTop() {
        return 0;
    }

    public View getNotificationClockTopView() {
        return null;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public TextView getTimeView() {
        return null;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public String getTimeZone() {
        return this.mCalendar.getTimeZone().getID();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public float getTopMargin() {
        return 0.0f;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public void init(Context context) {
        int displayOrientation;
        this.mContext = context;
        this.isSystemUI = DateFormatUtils.isSystemUI(context);
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        this.mConfiguration = configuration;
        this.mOrientation = configuration.orientation;
        Log.d("MiuiBaseClock2", "init mOrientation: " + this.mOrientation);
        if (isParentShown() && (displayOrientation = getDisplayOrientation()) != 0) {
            updateLastFrameworkOrientation("init", displayOrientation);
        }
        this.mCalendar = new Calendar();
        updateHourFormat();
        this.mLanguage = Locale.getDefault().getLanguage();
    }

    public final void initFrameworkOrientationIfShown(String str) {
        int displayOrientation;
        if (this.mLastFrameworkOrientation == 0 && isParentShown() && (displayOrientation = getDisplayOrientation()) != 0) {
            updateLastFrameworkOrientation(str, displayOrientation);
        }
    }

    public final boolean isParentShown() {
        if (getParent() instanceof ViewGroup) {
            return ((ViewGroup) getParent()).isShown();
        }
        return false;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        MutiDeviceDimenAdapter.clearIdentifierCache();
        String language = configuration.locale.getLanguage();
        int iUpdateFrom = this.mConfiguration.updateFrom(configuration);
        if ((iUpdateFrom & 4) != 0) {
            Log.d("MiuiBaseClock2", "language changed!");
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
            this.mLanguage = language;
            onLanguageChanged();
        }
        int i = configuration.orientation;
        if (!this.mHandlingRelayoutAfterShown) {
            updateLastFrameworkOrientation("onConfigurationChanged", i);
        }
        boolean z = i != this.mOrientation;
        if (z) {
            Log.d("MiuiBaseClock2", "orientation changed: " + this.mOrientation + "--> " + i);
            this.mOrientation = i;
            onOrientationChange(i);
        }
        boolean z2 = (iUpdateFrom & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0;
        if (z2) {
            onScreenLayoutChanged();
        }
        boolean z3 = (iUpdateFrom & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0;
        if (z || z2 || z3 || this.mHandlingRelayoutAfterShown) {
            updateViewsLayoutParams();
        }
        updateTime();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        updateSmallestScreenWidth(this.mContext);
    }

    public void onLanguageChanged() {
    }

    public void onOrientationChange(int i) {
        updateSmallestScreenWidth(this.mContext);
    }

    public void onScreenLayoutChanged() {
        updateSmallestScreenWidth(this.mContext);
    }

    public void onTimeZoneUpdate(TimeZone timeZone) {
    }

    @Override // android.view.View
    public void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        if (z) {
            initFrameworkOrientationIfShown("onVisibilityAggregated");
            if (getResources().getConfiguration().orientation == this.mOrientation || this.mLastFrameworkOrientation == 0) {
                return;
            }
            this.needRelayoutAfterShown = true;
        }
    }

    @Override // android.view.View
    public void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (i == 0) {
            boolean zIs24HourFormat = DateFormatUtils.is24HourFormat(this.mContext);
            if (zIs24HourFormat != this.m24HourFormat) {
                this.m24HourFormat = zIs24HourFormat;
                updateTime();
            }
            if (this.clockStyleInfo != null) {
                queryDataAsync();
            }
        }
    }

    public void queryDataAsync() {
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setBackgroundBlurContainer(View view) {
        this.mOutBackgroundBlurContainer = view;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setClockAlpha(float f) {
        setAlpha(f);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setClockPalette(int i, boolean z, Map map, boolean z2) {
        super.setClockPalette(i, z, map, z2);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        this.clockStyleInfo = clockStyleInfo;
        updateHourFormat();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGradientIndex(int i) {
        super.setGradientIndex(i);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    public void setIs24HourFormat(boolean z) {
        this.m24HourFormat = z;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setMagazineInfoVisible(boolean z) {
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setOwnerInfo(String str) {
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setScaleRatio(float f) {
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setShowLunarCalendar(boolean z) {
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setSuperSaveOpen(boolean z) {
        super.setSuperSaveOpen(z);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setTextColorDark(boolean z) {
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    public void updateHourFormat() {
        this.m24HourFormat = DateFormatUtils.is24HourFormat(this.mContext);
    }

    public final void updateLastFrameworkOrientation(String str, int i) {
        int i2 = DeviceConfig.isPhoneDevice() ? 1 : i;
        int i3 = this.mLastFrameworkOrientation;
        this.mLastFrameworkOrientation = i2;
        Log.d("MiuiBaseClock2", "updateLastFrameworkOrientation source=" + str + " orientation=" + i3 + " --> " + i2 + " rawOrientation=" + i + " mOrientation=" + this.mOrientation + " config=" + getResources().getConfiguration());
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateResidentTimeZone(String str) {
    }

    public void updateSmallestScreenWidth(Context context) {
        if (context == null) {
            Log.d("MiuiBaseClock2", "getSmallestScreenWidth context is null");
            context = getContext();
        }
        Configuration configuration = context.getResources().getConfiguration();
        float f = (configuration.densityDpi * 1.0f) / 160.0f;
        int i = (int) (configuration.screenHeightDp * f);
        int i2 = (int) (configuration.screenWidthDp * f);
        int i3 = (int) (f * configuration.smallestScreenWidthDp);
        this.mScreenWidth = i2;
        this.mScreenHeight = i;
        if (DateFormatUtils.isSystemUI(getContext())) {
            this.mViewHeight = i;
            this.mViewWidth = i2;
        }
        this.mSmallestScreenWidth = i3;
        Log.d("MiuiBaseClock2", "updateSmallestScreenWidth mScreenWidth=" + this.mScreenWidth + " mScreenHeight=" + this.mScreenHeight + " mSmallestScreenWidth=" + this.mSmallestScreenWidth);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateTimeZone(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        DateFormatUtils.clearCache();
        Log.d("MiuiClock", "update clock time zone: " + str);
        TimeZone timeZone = TimeZone.getTimeZone(str);
        this.mCalendar.setTimeZone(timeZone);
        onTimeZoneUpdate(timeZone);
        updateTime();
    }

    public void updateViewBackgroundBlurContainer(View view, int i, View... viewArr) {
        ClockStyleInfo clockStyleInfo = getClockStyleInfo();
        if (clockStyleInfo == null) {
            return;
        }
        boolean zIsAODType = ClockStyleInfo.isAODType(clockStyleInfo.getDisplayType());
        boolean zIsFullAODType = ClockStyleInfo.isFullAODType(clockStyleInfo.getDisplayType());
        if (this.mOutBackgroundBlurContainer == null) {
            Log.w("MiuiBaseClock2", "not blend background view container");
            ClockEffectUtils.setClockEffectsContainer(view, i, clockStyleInfo, zIsAODType, zIsFullAODType);
        } else {
            MiuiBlurUtils.clearContainerPassBlur(view);
            ClockEffectUtils.setClockEffectsContainer(this.mOutBackgroundBlurContainer, i, clockStyleInfo, zIsAODType, zIsFullAODType);
            view = this.mOutBackgroundBlurContainer;
        }
        for (View view2 : viewArr) {
            if (view2 != null) {
                MiuiBlurUtils.chooseBackgroundBlurContainer(view2, view);
            }
        }
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateViewTopMargin(boolean z) {
    }

    public void updateViewsLayoutParams() {
        if (this.mOrientation == getResources().getConfiguration().orientation || getParent() == null || isParentShown()) {
            this.needRelayoutAfterShown = false;
            return;
        }
        Log.w("MiuiBaseClock2", "updateViewsLayoutParams error mOrientation: " + this.mOrientation + "， config : " + getResources().getConfiguration());
        if (this.mLastFrameworkOrientation != 0) {
            this.needRelayoutAfterShown = true;
            return;
        }
        Log.w("MiuiBaseClock2", "updateViewsLayoutParams skip compensation: mLastFrameworkOrientation UNDEFINED displayOrientation=" + getDisplayOrientation() + " configOrientation=" + getResources().getConfiguration().orientation + " mOrientation=" + this.mOrientation);
    }
}
