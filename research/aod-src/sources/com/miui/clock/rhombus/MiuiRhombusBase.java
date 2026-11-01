package com.miui.clock.rhombus;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.miui.clock.MiuiClockController;
import com.miui.clock.MiuiClockNumberView;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.BaseFontStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.Effect;
import com.miui.clock.module.FontBeiHaiBei;
import com.miui.clock.module.FontCopperplate;
import com.miui.clock.module.FontDryBrush9;
import com.miui.clock.module.FontFoundryGridnik;
import com.miui.clock.module.FontMiSansRoundLock;
import com.miui.clock.module.FontMiSerif;
import com.miui.clock.module.FontNeumaticCompressed;
import com.miui.clock.module.FontSKPupok;
import com.miui.clock.module.FontSalome;
import com.miui.clock.module.FontSilkSerif;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.GlobalColorUtils;
import com.miui.clock.utils.MiuiBlurUtils;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.util.Arrays;
import java.util.Map;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class MiuiRhombusBase extends RelativeLayout implements MiuiClockController.IClockView {
    public DisplayMetrics dm;
    public boolean m24HourFormat;
    public float mCalculateScale;
    public Calendar mCalendar;
    public int mChoosePaletteType;
    public Configuration mConfiguration;
    public Context mContext;
    public int[] mCurrentTimeArray;
    public float mDensity;
    public int mDensityDpi;
    public MiuiClockNumberView mHour1View;
    public MiuiClockNumberView mHour2View;
    public MiuiRhombusClockBaseInfo mInfo;
    public String mLanguage;
    public boolean mMagazineInfoVisible;
    public MiuiClockNumberView mMinute1View;
    public MiuiClockNumberView mMinute2View;
    public int mOrientation;
    public View mOutBackgroundBlurContainer;
    public Map mPalette;
    public Resources mResources;
    public int mScreenHeight;
    public int mScreenWidth;
    public int mSmallestScreenWidth;
    public boolean mTextDark;
    public boolean mWallpaperSupportDepth;
    public BaseFontStyle style;
    public float[] userAnimGradientParams;

    /* JADX INFO: renamed from: com.miui.clock.rhombus.MiuiRhombusBase$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.HOUR1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.HOUR2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.MIN1.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.MIN2.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.ALL_VIEW.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public MiuiRhombusBase(Context context) {
        this(context, null);
    }

    public MiuiRhombusBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mResources = null;
        this.mCurrentTimeArray = new int[4];
        this.mContext = context;
        this.mResources = context.getResources();
        updateHourFormat();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        MiuiRhombusClockBaseInfo miuiRhombusClockBaseInfo = this.mInfo;
        if (miuiRhombusClockBaseInfo == null) {
            return;
        }
        int clockEffect = miuiRhombusClockBaseInfo.getClockEffect();
        if (z || !ClockEffectUtils.isGradualType(clockEffect)) {
            resetGradientParams();
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

    public float getClockSizeScale() {
        if (DeviceConfig.PAD_DEVICE && this.mOrientation == 1) {
            return 1.2f;
        }
        return (!DeviceConfig.isJ18() || DeviceConfig.inLargeScreen(this.mContext)) ? 1.0f : 0.8f;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public ClockStyleInfo getClockStyleInfo() {
        return this.mInfo;
    }

    public void getClockViewPosition(ClockViewType clockViewType, int[] iArr) {
        if (iArr == null || iArr.length < 2) {
            return;
        }
        this.mDensity = this.dm.density * DeviceConfig.calculateScale(getContext());
        int[] iArr2 = this.mCurrentTimeArray;
        int i = iArr2[0];
        int i2 = iArr2[1];
        int i3 = iArr2[2];
        int i4 = iArr2[3];
        int[] iArr3 = this.style.getHourOffset()[i][i2];
        int[] iArr4 = this.style.getMinuteOffset()[i3][i4];
        if (this.style.getFontStyle() == BaseFontStyle.Style.Copperplate) {
            iArr[0] = 0;
            iArr[1] = 0;
            return;
        }
        int i5 = AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i5 == 1) {
            float f = iArr3[0];
            float f2 = this.mDensity;
            iArr[0] = (int) (f * f2);
            iArr[1] = (int) (iArr3[1] * f2);
            return;
        }
        if (i5 == 2) {
            float f3 = iArr3[2];
            float f4 = this.mDensity;
            iArr[0] = (int) (f3 * f4);
            iArr[1] = (int) (iArr3[3] * f4);
            return;
        }
        if (i5 == 3) {
            float f5 = iArr4[0];
            float f6 = this.mDensity;
            iArr[0] = (int) (f5 * f6);
            iArr[1] = (int) (iArr4[1] * f6);
            return;
        }
        if (i5 != 4) {
            iArr[0] = 0;
            iArr[1] = 0;
        } else {
            float f7 = iArr4[2];
            float f8 = this.mDensity;
            iArr[0] = (int) (f7 * f8);
            iArr[1] = (int) (iArr4[3] * f8);
        }
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public float getClockVisibleHeight() {
        return Math.max(getHeight(), 0);
    }

    public float[] getCurrentGradientParams() {
        float[] fArr = this.userAnimGradientParams;
        return fArr == null ? getGradientParams() : fArr;
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
    public float[] getGradientAnimationParams() {
        MiuiRhombusClockBaseInfo miuiRhombusClockBaseInfo = this.mInfo;
        return miuiRhombusClockBaseInfo == null ? Effect.EMPTY_GRADIENT : ClockEffectUtils.scaleGradientParams(miuiRhombusClockBaseInfo.getAodGradientParams(), getClockSizeScale() * getScaleByGradientDesign());
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public float[] getGradientParams() {
        MiuiRhombusClockBaseInfo miuiRhombusClockBaseInfo = this.mInfo;
        if (miuiRhombusClockBaseInfo == null) {
            return Effect.EMPTY_GRADIENT;
        }
        return ClockEffectUtils.scaleGradientParams(ClockStyleInfo.isAODType(miuiRhombusClockBaseInfo.getDisplayType()) ? this.mInfo.getAodGradientParams() : this.mInfo.getLockGradientParams(), getClockSizeScale() * getScaleByGradientDesign());
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i == 1) {
            return this.mHour1View;
        }
        if (i == 2) {
            return this.mHour2View;
        }
        if (i == 3) {
            return this.mMinute1View;
        }
        if (i == 4) {
            return this.mMinute2View;
        }
        if (i != 5) {
            return null;
        }
        return this;
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
    public int getNotificationClockBottom() {
        int dimen = getDimen(R.dimen.miui_rhombus_small_clock_margin_top) + getDimen(R.dimen.miui_rhombus_clock_notification_date_height) + getDimen(R.dimen.miui_rhombus_small_clock_time_container_margin_top) + ((int) (this.style.getSmallClockInnerHeight() * this.mDensity * ((!DeviceConfig.isJ18() || DeviceConfig.inLargeScreen(this.mContext)) ? 1.0f : 0.8f))) + getDimen(R.dimen.notification_margin_top);
        return this.mMagazineInfoVisible ? dimen + getDimen(R.dimen.miui_rhombus_clock_notification_gallery_margin_top) + getDimen(R.dimen.miui_rhombus_clock_notification_gallery_height) : dimen;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockTop() {
        return getDimen(R.dimen.miui_rhombus_small_clock_margin_top);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    public int getRhombusClockHeight(ClockViewType clockViewType) {
        int[] iArr = this.mCurrentTimeArray;
        int i = iArr[0];
        int i2 = iArr[1];
        int i3 = iArr[2];
        int i4 = iArr[3];
        int i5 = AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i5 == 1) {
            return this.style.getLargeClockHeights()[i];
        }
        if (i5 == 2) {
            return this.style.getLargeClockHeights()[i2];
        }
        if (i5 == 3) {
            return this.style.getLargeClockHeights()[i3];
        }
        if (i5 != 4) {
            return 0;
        }
        return this.style.getLargeClockHeights()[i4];
    }

    public MiuiRotateRhombusInfo getRotateRhombusClockInfo(ClockViewType clockViewType) {
        int i;
        if (!this.style.enableRotate()) {
            return null;
        }
        int[] iArr = this.mCurrentTimeArray;
        int i2 = 0;
        int i3 = iArr[0];
        int i4 = iArr[1];
        int i5 = iArr[2];
        int i6 = iArr[3];
        int i7 = AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i7 == 1) {
            i2 = this.style.getLargeClockWidth()[i3];
            i = this.style.getLargeClockHeights()[i3];
        } else if (i7 == 2) {
            i2 = this.style.getLargeClockWidth()[i4];
            i = this.style.getLargeClockHeights()[i4];
        } else if (i7 == 3) {
            i2 = this.style.getLargeClockWidth()[i5];
            i = this.style.getLargeClockHeights()[i5];
        } else if (i7 != 4) {
            i = 0;
        } else {
            i2 = this.style.getLargeClockWidth()[i6];
            i = this.style.getLargeClockHeights()[i6];
        }
        double radians = Math.toRadians(this.style.getMaxRotateDegree());
        double d = i2;
        double d2 = i;
        double dCos = (Math.cos(radians) * d) + (Math.sin(radians) * d2);
        double dCos2 = (Math.cos(radians) * d2) + (Math.sin(radians) * d);
        MiuiRotateRhombusInfo miuiRotateRhombusInfo = new MiuiRotateRhombusInfo();
        miuiRotateRhombusInfo.originWidth = i2;
        miuiRotateRhombusInfo.originHeight = i;
        miuiRotateRhombusInfo.maxWidth = (int) dCos;
        miuiRotateRhombusInfo.maxHeight = (int) dCos2;
        miuiRotateRhombusInfo.leftRightSpace = (int) ((dCos - d) * 0.5d);
        miuiRotateRhombusInfo.topBottomSpace = (int) ((dCos2 - d2) * 0.5d);
        return miuiRotateRhombusInfo;
    }

    public float getScaleByGradientDesign() {
        return (this.mSmallestScreenWidth / DeviceConfig.getDesignDp(this.mContext)) / 2.7551022f;
    }

    public BaseFontStyle getStyle() {
        return this.style;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ TextView getTimeView() {
        return super.getTimeView();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getTimeZone() {
        return super.getTimeZone();
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

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int i = configuration.densityDpi;
        float fCalculateScale = DeviceConfig.calculateScale(getContext());
        if (this.mDensityDpi != i || Math.abs(fCalculateScale - this.mCalculateScale) >= 1.0E-6d) {
            this.mDensity = this.dm.density * DeviceConfig.calculateScale(getContext());
            this.mDensityDpi = i;
            this.mCalculateScale = fCalculateScale;
        }
        String language = configuration.locale.getLanguage();
        if (!TextUtils.isEmpty(language) && !language.equals(this.mLanguage)) {
            this.mLanguage = language;
            onLanguageChanged(language);
        }
        int i2 = configuration.orientation;
        Log.d("MiuiRhombusBase", "new orientation is " + i2);
        if (i2 != this.mOrientation) {
            this.mOrientation = i2;
            onOrientationChange(i2);
        }
        if ((this.mConfiguration.updateFrom(configuration) & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0) {
            onScreenLayoutChanged();
        }
        updateTime();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        Configuration configuration = new Configuration(getContext().getResources().getConfiguration());
        this.mConfiguration = configuration;
        this.mOrientation = configuration.orientation;
        this.dm = getResources().getDisplayMetrics();
        updateSmallestScreenWidth(this.mContext);
        this.mDensity = this.dm.density * DeviceConfig.calculateScale(getContext());
        this.mCalendar = new Calendar();
        this.mHour1View = (MiuiClockNumberView) findViewById(R.id.hour1);
        this.mHour2View = (MiuiClockNumberView) findViewById(R.id.hour2);
        this.mMinute1View = (MiuiClockNumberView) findViewById(R.id.minute1);
        this.mMinute2View = (MiuiClockNumberView) findViewById(R.id.minute2);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void onHighTextContrastEnabledChanged(boolean z) {
        if (z) {
            clearColorEffect();
        } else {
            setClockPalette(this.mChoosePaletteType, this.mTextDark, this.mPalette, this.mWallpaperSupportDepth);
        }
    }

    public void onLanguageChanged(String str) {
    }

    public void onOrientationChange(int i) {
        updateSmallestScreenWidth(this.mContext);
        MiuiRhombusClockBaseInfo miuiRhombusClockBaseInfo = this.mInfo;
        if (miuiRhombusClockBaseInfo == null) {
            return;
        }
        miuiRhombusClockBaseInfo.updateGradientTypeByOrientation(i);
        if (Effect.needHiddenGradient(getCurrentGradientParams())) {
            return;
        }
        updateGradientEffectColor(getGradientParams());
    }

    public void onScreenLayoutChanged() {
        updateSmallestScreenWidth(this.mContext);
    }

    @Override // android.view.View
    public void onWindowVisibilityChanged(int i) {
        boolean zIs24HourFormat;
        super.onWindowVisibilityChanged(i);
        if (i != 0 || (zIs24HourFormat = DateFormatUtils.is24HourFormat(this.mContext)) == this.m24HourFormat) {
            return;
        }
        this.m24HourFormat = zIs24HourFormat;
        updateTime();
    }

    public void resetGradientParams() {
        this.userAnimGradientParams = null;
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
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        String str;
        this.mChoosePaletteType = i;
        this.mTextDark = z;
        this.mPalette = map;
        this.mWallpaperSupportDepth = z2;
        if (map != null) {
            str = "secondary20=" + map.get("secondary20") + ",secondary90=" + map.get("secondary90") + ",secondary15=" + map.get("secondary15");
        } else {
            str = "null";
        }
        Log.d("ClockPalette", "setClockPalette: type = " + i + ", textDark = " + z + ", palette = " + str);
        MiuiRhombusClockBaseInfo miuiRhombusClockBaseInfo = this.mInfo;
        if (miuiRhombusClockBaseInfo != null && miuiRhombusClockBaseInfo.isAutoSecondaryColor()) {
            this.mInfo.setSecondaryColor(Color.parseColor("#EE3434"));
            this.mInfo.setFullAodSecondaryColor(Color.parseColor("#FF4242"));
            MiuiRhombusClockBaseInfo miuiRhombusClockBaseInfo2 = this.mInfo;
            miuiRhombusClockBaseInfo2.setAnimationSecondaryColor(miuiRhombusClockBaseInfo2.getSecondaryColor());
        }
        if (DeviceConfig.isHighTextContrastEnabled(this.mContext)) {
            updateHighTextColors(this.mInfo);
            return;
        }
        MiuiRhombusClockBaseInfo miuiRhombusClockBaseInfo3 = this.mInfo;
        if (miuiRhombusClockBaseInfo3 != null) {
            miuiRhombusClockBaseInfo3.setPalette(map);
            this.mInfo.setInfoTextDark(z);
            if (ClockEffectUtils.isDifferenceType(this.mInfo.getClockEffect())) {
                if (this.mInfo.isAutoPrimaryColor()) {
                    if (z) {
                        if (map != null && map.get("secondary15") != null) {
                            int iIntValue = map.get("secondary15").intValue();
                            if (ClockStyleInfo.isAODType(this.mInfo.getDisplayType())) {
                                this.mInfo.setPrimaryColor(GlobalColorUtils.transformAodColor(iIntValue));
                            } else {
                                this.mInfo.setPrimaryColor(iIntValue);
                            }
                            this.mInfo.setBlendColor(iIntValue);
                        }
                    } else if (map != null && map.get("secondary85") != null) {
                        int iIntValue2 = map.get("secondary85").intValue();
                        if (ClockStyleInfo.isAODType(this.mInfo.getDisplayType())) {
                            this.mInfo.setPrimaryColor(GlobalColorUtils.transformAodColor(iIntValue2));
                        } else {
                            this.mInfo.setPrimaryColor(iIntValue2);
                        }
                        this.mInfo.setBlendColor(iIntValue2);
                    }
                }
                if (map != null && map.get("secondary90") != null) {
                    this.mInfo.setLightMinuteColor(map.get("secondary90").intValue());
                }
                if (map == null || map.get("secondary15") == null) {
                    return;
                }
                this.mInfo.setDarkMinuteColor(map.get("secondary15").intValue());
                return;
            }
            if (ClockEffectUtils.isGlowType(this.mInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                return;
            }
            boolean zSupportBackgroundBlur = DeviceConfig.supportBackgroundBlur(this.mContext);
            boolean zIsAODType = ClockStyleInfo.isAODType(this.mInfo.getDisplayType());
            int primaryColor = -1;
            if (zSupportBackgroundBlur && !zIsAODType && ClockEffectUtils.isBlurMixType(this.mInfo.getClockEffect())) {
                this.mInfo.setPrimaryColor(-1);
                if (this.mInfo.isAutoPrimaryColor()) {
                    if (z && map != null && map.get("neutral-variant30") != null) {
                        this.mInfo.setBlendColor(map.get("neutral-variant30").intValue());
                    } else if (!z) {
                        this.mInfo.setBlendColor(-4605511);
                    }
                    this.mInfo.setFullAodBlendColor(-4605511);
                }
            }
            if (z) {
                primaryColor = (map == null || map.get("secondary20") == null) ? this.mInfo.getPrimaryColor() : map.get("secondary20").intValue();
            }
            if (this.mInfo.isAutoPrimaryColor()) {
                if (ClockEffectUtils.needPickSolidColor(this.mInfo.getClockEffect(), zSupportBackgroundBlur) || zIsAODType) {
                    this.mInfo.setPrimaryColor(primaryColor);
                }
                this.mInfo.setOriginMagazineColor(primaryColor);
                MiuiRhombusClockBaseInfo miuiRhombusClockBaseInfo4 = this.mInfo;
                miuiRhombusClockBaseInfo4.setAnimationPrimaryColor(miuiRhombusClockBaseInfo4.getPrimaryColor());
            }
            if (map != null && map.get("secondary90") != null) {
                this.mInfo.setLightMinuteColor(map.get("secondary90").intValue());
            }
            if (map != null && map.get("secondary15") != null) {
                this.mInfo.setDarkMinuteColor(map.get("secondary15").intValue());
            }
            if (!ClockEffectUtils.isBlurMixType(this.mInfo.getClockEffect()) || DeviceConfig.supportBackgroundBlur(this.mContext)) {
                return;
            }
            if (!this.mInfo.isAutoPrimaryColor()) {
                this.mInfo.convertPrimaryColorByBlendColor(true);
            }
            if (this.mInfo.isAutoSecondaryColor()) {
                return;
            }
            this.mInfo.convertSecondaryColorByBlendColor(true);
        }
    }

    public void setClockStyle(int i) {
        switch (i) {
            case 1:
                this.style = new FontSilkSerif();
                break;
            case 2:
                this.style = new FontSKPupok();
                break;
            case 3:
                this.style = new FontMiSansRoundLock();
                break;
            case 4:
                this.style = new FontSalome();
                break;
            case 5:
                this.style = new FontBeiHaiBei();
                break;
            case 6:
                this.style = new FontFoundryGridnik();
                break;
            case 7:
                this.style = new FontMiSerif();
                break;
            case 8:
                this.style = new FontNeumaticCompressed();
                break;
            case 9:
                this.style = new FontDryBrush9();
                break;
            case 10:
                this.style = new FontCopperplate();
                break;
            default:
                this.style = new FontSilkSerif();
                break;
        }
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        this.mInfo = (MiuiRhombusClockBaseInfo) clockStyleInfo;
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
    public void setGradientIndex(int i) {
        ClockStyleInfo clockStyleInfo = getClockStyleInfo();
        if (clockStyleInfo != null) {
            clockStyleInfo.setGradientIndex(i);
            float[] currentGradientParams = getCurrentGradientParams();
            Log.d("MiuiRhombusBase", "setGradientIndex update " + Arrays.toString(currentGradientParams));
            updateGradientEffectColor(currentGradientParams);
        }
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
        this.mMagazineInfoVisible = z;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setMinuteColor(int i, int i2) {
        MiuiRhombusClockBaseInfo miuiRhombusClockBaseInfo = this.mInfo;
        if (miuiRhombusClockBaseInfo != null) {
            miuiRhombusClockBaseInfo.setLightMinuteColor(i);
            this.mInfo.setDarkMinuteColor(i2);
            updateColor();
        }
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

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateGradientEffectColor(float[] fArr) {
        if (DateFormatUtils.isSystemUI(getContext())) {
            this.userAnimGradientParams = fArr;
        }
    }

    public void updateHighTextColors(ClockStyleInfo clockStyleInfo) {
        if (!DeviceConfig.isHighTextContrastEnabled(this.mContext) || clockStyleInfo == null) {
            return;
        }
        clockStyleInfo.updateAodHighTextColors();
        updateColor();
    }

    public void updateHourFormat() {
        this.m24HourFormat = DateFormatUtils.is24HourFormat(this.mContext);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateResidentTimeZone(String str) {
    }

    public void updateSmallestScreenWidth(Context context) {
        if (context == null) {
            Log.d("MiuiRhombusBase", "getSmallestScreenWidth context is null");
            context = getContext();
        }
        Configuration configuration = context.getResources().getConfiguration();
        float f = (configuration.densityDpi * 1.0f) / 160.0f;
        int i = (int) (configuration.screenHeightDp * f);
        int i2 = (int) (configuration.screenWidthDp * f);
        int i3 = (int) (f * configuration.smallestScreenWidthDp);
        this.mScreenHeight = i;
        this.mScreenWidth = i2;
        this.mSmallestScreenWidth = i3;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        updateHourFormat();
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        String time = DateFormatUtils.formatTime(this.m24HourFormat ? "HHmm" : "hhmm");
        for (int i = 0; i < time.length(); i++) {
            this.mCurrentTimeArray[i] = Integer.parseInt(String.valueOf(time.charAt(i)));
        }
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateTimeZone(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        DateFormatUtils.clearCache();
        this.mCalendar = new Calendar(TimeZone.getTimeZone(str));
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
            Log.w("MiuiRhombusBase", "not blend background view container");
            ClockEffectUtils.setClockEffectsContainer(view, i, clockStyleInfo, zIsAODType, zIsFullAODType);
        } else {
            MiuiBlurUtils.clearContainerPassBlur(view);
            view = this.mOutBackgroundBlurContainer;
            ClockEffectUtils.setClockEffectsContainer(view, i, clockStyleInfo, zIsAODType, zIsFullAODType);
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
}
