package com.miui.clock.classic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Group;
import com.google.gson.Gson;
import com.miui.clock.ConstraintLayoutAccessibilityHelper;
import com.miui.clock.MiuiTextGlassView;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClassicContentStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.HealthBean;
import com.miui.clock.module.MiuiClassicClockInfoBase;
import com.miui.clock.module.MiuiClassicPlusClockInfo;
import com.miui.clock.module.WeatherBean;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.HealthFetcherController;
import com.miui.clock.utils.MiuiBlurUtils;
import com.miui.clock.utils.MutiDeviceDimenAdapter;
import com.miui.clock.utils.WeatherFetcherController;
import java.util.HashSet;
import java.util.Map;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class ClassicPlusClockView extends ClassicClockBaseView {
    public final HashSet healthType;
    public Gson mGson;
    public boolean mHasPresetData;
    public HealthBean mHealthBean;
    public HealthFetcherController mHealthFetcherController;
    public boolean mNeedShowTimeView2;
    public int mScreenWidth;
    public Group mSecondLine;
    public TextPaint mStaticLayoutPaint;
    public ConstraintLayoutAccessibilityHelper mTimeDesc;
    public MiuiTextGlassView mTimeView;
    public MiuiTextGlassView mTimeView2;
    public WeatherBean mWeatherBean;
    public WeatherFetcherController mWeatherFetcherController;

    /* JADX INFO: renamed from: com.miui.clock.classic.ClassicPlusClockView$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.TEXT_AREA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.TIME_AREA.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.TIME_AREA2.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$hjx6qVMKQ-5c73duCOKfOxOqG8o, reason: not valid java name */
    public static /* synthetic */ void m651$r8$lambda$hjx6qVMKQ5c73duCOKfOxOqG8o(ClassicPlusClockView classicPlusClockView, WeatherBean weatherBean) {
        if (classicPlusClockView.isAttachedToWindow()) {
            classicPlusClockView.updateWeather(weatherBean);
        }
    }

    public ClassicPlusClockView(Context context) {
        super(context);
        this.healthType = new HashSet();
        this.mStaticLayoutPaint = new TextPaint();
    }

    public ClassicPlusClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.healthType = new HashSet();
        this.mStaticLayoutPaint = new TextPaint();
    }

    public ClassicPlusClockView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.healthType = new HashSet();
        this.mStaticLayoutPaint = new TextPaint();
    }

    private void addType(int i) {
        if (ClassicContentStyle.Health.isHealthType(i)) {
            if (i != 507) {
                this.healthType.add(Integer.valueOf(i));
                return;
            }
            this.healthType.add(502);
            this.healthType.add(500);
            this.healthType.add(508);
        }
    }

    private void checkGson() {
        if (this.mGson == null) {
            this.mGson = new Gson();
        }
    }

    private boolean hasWeatherType() {
        return ClassicContentStyle.Weather.isWeatherType(this.mClockStyleInfo.getClassicLine1()) || ClassicContentStyle.Weather.isWeatherType(this.mClockStyleInfo.getClassicLine3());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScreenSizeChange() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);
        constraintSet.setMargin(R.id.text_area, 3, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.classic_plus_text_area_margin_top, false));
        int i = R.id.area1;
        constraintSet.setMargin(i, 3, getDimen(R.dimen.classic_plus_area1_time1_margin_top));
        int i2 = R.id.area2;
        constraintSet.setMargin(i2, 3, getDimen(R.dimen.classic_plus_area2_time2_margin_top));
        constraintSet.constrainHeight(i, getDimen(R.dimen.classic_plus_area1_time1_height));
        constraintSet.constrainHeight(i2, getDimen(R.dimen.classic_plus_area2_time2_height));
        int i3 = R.id.signature_text_container;
        constraintSet.setMargin(i3, 3, getDimen(this.mNeedShowTimeView2 ? R.dimen.classic_plus_signature_margin_top_two_line : R.dimen.classic_plus_signature_margin_top_single_line));
        constraintSet.constrainWidth(i3, getDimen(R.dimen.classic_text_signature_max_width));
        constraintSet.applyTo(this);
        this.mTextArea.onScreenSizeChange();
        MiuiClassicClockInfoBase miuiClassicClockInfoBase = this.mClockStyleInfo;
        if (miuiClassicClockInfoBase != null) {
            ClassicClockTimeUtils.setClassicTimeStyle(this.mTimeView, miuiClassicClockInfoBase);
            ClassicClockTimeUtils.setClassicTimeStyle(this.mTimeView2, this.mClockStyleInfo);
        }
        this.mTimeView.setLetterSpacing(-0.02f);
        this.mTimeView2.setLetterSpacing(-0.02f);
        updateSignatureText();
    }

    private void presetData(String str, String str2) {
        WeatherBean weatherBean;
        boolean zIsEmpty = TextUtils.isEmpty(str);
        boolean zIsEmpty2 = TextUtils.isEmpty(str2);
        Log.i(this.TAG, "presetData this = " + this + ", weatherEmpty = " + zIsEmpty + ", healthEmpty = " + zIsEmpty2);
        if (zIsEmpty && zIsEmpty2) {
            return;
        }
        checkGson();
        HealthBean healthBean = null;
        if (zIsEmpty) {
            weatherBean = null;
        } else {
            try {
                weatherBean = (WeatherBean) this.mGson.fromJson(str, WeatherBean.class);
            } catch (Exception unused) {
                Log.i(this.TAG, "presetData weather convert fail");
                weatherBean = null;
            }
        }
        if (!zIsEmpty2) {
            try {
                healthBean = (HealthBean) this.mGson.fromJson(str2, HealthBean.class);
            } catch (Exception unused2) {
                Log.i(this.TAG, "presetData health convert fail");
            }
        }
        if (healthBean == null && weatherBean == null) {
            return;
        }
        this.mHasPresetData = true;
        this.mTextArea.presetData(healthBean, weatherBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateHealth(HealthBean healthBean) {
        HealthBean healthBean2 = this.mHealthBean;
        if (healthBean2 != null) {
            healthBean2.updateData(healthBean, this.healthType);
        } else {
            this.mHealthBean = healthBean;
        }
        Log.d(this.TAG, "Current Health Data:[" + this.mHealthBean.toString() + "]");
        if (ClassicContentStyle.Health.isHealthType(this.mClockStyleInfo.getClassicLine1())) {
            this.mTextArea.updateHealth(this.mHealthBean);
        }
    }

    private void updateHealthTypeList() {
        this.healthType.clear();
        addType(this.mClockStyleInfo.getClassicLine1());
        addType(this.mClockStyleInfo.getClassicLine3());
    }

    private void updateWeather(WeatherBean weatherBean) {
        this.mWeatherBean = weatherBean;
        if (ClassicContentStyle.Weather.isWeatherType(this.mClockStyleInfo.getClassicLine1())) {
            this.mTextArea.updateWeather(weatherBean);
        }
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        if (this.mClockStyleInfo == null || ClockEffectUtils.isLessThanOS2()) {
            return;
        }
        ClockEffectUtils.clearClockEffectsContainer(this, this.mClockStyleInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mTextArea, this.mClockStyleInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mTimeView, this.mClockStyleInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mTimeView2, this.mClockStyleInfo, z);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getGalleryGravity() {
        return 1;
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        int i = AnonymousClass2.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i == 1) {
            return this.mTextArea;
        }
        if (i != 2) {
            return i != 3 ? super.getIClockView(clockViewType) : this.mTimeView2;
        }
        return this.mTimeView;
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public float getTopMargin() {
        return getDimen(R.dimen.classic_plus_text_area_margin_top);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView
    public boolean isPlus() {
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // com.miui.clock.MiuiBaseClock2, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int iWidth = DeviceConfig.getScreenRect(this.mContext).width();
        if (this.mScreenWidth == iWidth || this.mEditMode) {
            return;
        }
        this.mScreenWidth = iWidth;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        WeatherFetcherController weatherFetcherController = this.mWeatherFetcherController;
        if (weatherFetcherController != null) {
            weatherFetcherController.reset();
            this.mWeatherFetcherController = null;
        }
        HealthFetcherController healthFetcherController = this.mHealthFetcherController;
        if (healthFetcherController != null) {
            healthFetcherController.reset();
            this.mHealthFetcherController = null;
        }
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mScreenWidth = DeviceConfig.getScreenRect(this.mContext).width();
        this.mTimeView = (MiuiTextGlassView) findViewById(R.id.time_view);
        MiuiTextGlassView miuiTextGlassView = (MiuiTextGlassView) findViewById(R.id.time_view2);
        this.mTimeView2 = miuiTextGlassView;
        miuiTextGlassView.setSameNumberWidth(true);
        this.mSecondLine = (Group) findViewById(R.id.second_line);
        this.mTimeDesc = (ConstraintLayoutAccessibilityHelper) findViewById(R.id.time_desc);
        updateSignatureText();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiBaseClock2
    public void onLanguageChanged() {
        super.onLanguageChanged();
        MiuiClassicClockInfoBase miuiClassicClockInfoBase = this.mClockStyleInfo;
        if (miuiClassicClockInfoBase != null) {
            ClassicClockTimeUtils.setClassicTimeStyle(this.mTimeView, miuiClassicClockInfoBase);
            ClassicClockTimeUtils.setClassicTimeStyle(this.mTimeView2, this.mClockStyleInfo);
        }
        updateTime();
        queryDataAsync();
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        post(new Runnable() { // from class: com.miui.clock.classic.ClassicPlusClockView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.onScreenSizeChange();
            }
        });
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void queryDataAsync() {
        Context specialContext = WeatherFetcherController.Companion.getSpecialContext(this.mContext, this.mCurrentUserId);
        if (hasWeatherType()) {
            if (this.mWeatherFetcherController == null) {
                this.mWeatherFetcherController = new WeatherFetcherController();
            }
            this.mWeatherFetcherController.fetchWeatherData(specialContext, this.mHasPresetData, new WeatherFetcherController.Callback() { // from class: com.miui.clock.classic.ClassicPlusClockView$$ExternalSyntheticLambda1
                @Override // com.miui.clock.utils.WeatherFetcherController.Callback
                public final void updateWeatherInfo(WeatherBean weatherBean) {
                    ClassicPlusClockView.m651$r8$lambda$hjx6qVMKQ5c73duCOKfOxOqG8o(this.f$0, weatherBean);
                }
            });
        }
        updateHealthTypeList();
        if (this.healthType.size() > 0) {
            if (this.mHealthFetcherController == null) {
                this.mHealthFetcherController = new HealthFetcherController();
            }
            this.mHealthFetcherController.fetchHealthData(specialContext, this.healthType, new HealthFetcherController.Callback() { // from class: com.miui.clock.classic.ClassicPlusClockView.1
                @Override // com.miui.clock.utils.HealthFetcherController.Callback
                public void updateHealth(HealthBean healthBean) {
                    if (ClassicPlusClockView.this.isAttachedToWindow()) {
                        ClassicPlusClockView.this.updateHealth(healthBean);
                    }
                }
            });
        }
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        super.setClockPalette(i, z, map, z2);
        if (this.mClockStyleInfo == null || DeviceConfig.isHighTextContrastEnabled(this.mContext) || ClockStyleInfo.isAODType(this.mClockStyleInfo.getDisplayType())) {
            return;
        }
        updateClockEffectsContainer();
        if (ClockEffectUtils.isGradualType(this.mClockStyleInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            return;
        }
        if (ClockEffectUtils.isDifferenceType(this.mClockStyleInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            int fullAodBlendColor = ClockStyleInfo.isFullAODType(this.mClockStyleInfo.getDisplayType()) ? this.mClockStyleInfo.getFullAodBlendColor() : this.mClockStyleInfo.getBlendColor();
            MiuiBlurUtils.setMemberDiffEffectColor(this.mTextArea, fullAodBlendColor);
            MiuiBlurUtils.setMemberDiffEffectColor(this.mTimeView, fullAodBlendColor);
            MiuiBlurUtils.setMemberDiffEffectColor(this.mTimeView2, fullAodBlendColor);
            return;
        }
        if (ClockEffectUtils.isBlurMixType(this.mClockStyleInfo.getClockEffect()) && DeviceConfig.supportBackgroundBlur(this.mContext)) {
            boolean zIsFullAODType = ClockStyleInfo.isFullAODType(this.mClockStyleInfo.getDisplayType());
            MiuiClassicClockInfoBase miuiClassicClockInfoBase = this.mClockStyleInfo;
            int fullAodBlendColor2 = zIsFullAODType ? miuiClassicClockInfoBase.getFullAodBlendColor() : miuiClassicClockInfoBase.getBlendColor();
            MiuiClassicClockInfoBase miuiClassicClockInfoBase2 = this.mClockStyleInfo;
            int fullAodPrimaryColor = zIsFullAODType ? miuiClassicClockInfoBase2.getFullAodPrimaryColor() : miuiClassicClockInfoBase2.getPrimaryColor();
            boolean z3 = false;
            MiuiBlurUtils.setMemberBlendColors(this.mTextArea, !zIsFullAODType && z, fullAodBlendColor2, fullAodPrimaryColor);
            MiuiBlurUtils.setMemberBlendColors(this.mTimeView, !zIsFullAODType && z, fullAodBlendColor2, fullAodPrimaryColor);
            MiuiTextGlassView miuiTextGlassView = this.mTimeView2;
            if (!zIsFullAODType && z) {
                z3 = true;
            }
            MiuiBlurUtils.setMemberBlendColors(miuiTextGlassView, z3, fullAodBlendColor2, fullAodPrimaryColor);
        }
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @SuppressLint({"UnclosedTrace"})
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        this.mNeedShowTimeView2 = this.mClockStyleInfo.getClassicLine2() == 72;
        if (!this.mHasPresetData && !this.mClockStyleInfo.isUnablePresetData()) {
            presetData(this.mClockStyleInfo.getWeatherJson(), this.mClockStyleInfo.getHealthJson());
        }
        if (isAODType()) {
            MiuiClassicClockInfoBase miuiClassicClockInfoBase = this.mClockStyleInfo;
            miuiClassicClockInfoBase.setClockWeight((int) (miuiClassicClockInfoBase.getClockWeight() * ClassicClockTimeUtils.getClassicAodFontWeightRadio()));
        }
        onScreenSizeChange();
        this.mClockStyleInfo.getOriginMagazineColor();
        queryDataAsync();
        clearColorEffect(false);
        this.mTimeView.setSameNumberWidth(this.mNeedShowTimeView2);
        if (this.mNeedShowTimeView2) {
            this.mSecondLine.setVisibility(0);
            setMarginTop(R.id.magazine_info_container, getDimen(R.dimen.classic_gallery_area_margin_top_double_line));
        } else {
            this.mSecondLine.setVisibility(8);
            setMarginTop(R.id.magazine_info_container, getDimen(R.dimen.classic_gallery_area_margin_top_single_line));
        }
        updateTime();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setEditMode(boolean z) {
        super.setEditMode(z);
        ViewGroup.LayoutParams layoutParams = this.mTextArea.getLayoutParams();
        layoutParams.height = z ? getDimen(R.dimen.classic_plus_text_area_height) : -2;
        this.mTextArea.setLayoutParams(layoutParams);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2, int i3, int i4) {
        super.updateClockBlendColor(i, i2, i3, i4);
        if (!DeviceConfig.supportBackgroundBlur(this.mContext)) {
            updateClockColor(i, i2);
            return;
        }
        boolean zIsAODType = ClockStyleInfo.isAODType(this.mClockStyleInfo.getDisplayType());
        boolean zIsFullAODType = ClockStyleInfo.isFullAODType(this.mClockStyleInfo.getDisplayType());
        boolean zUpdateTextDark = updateTextDark(this.mTextDark);
        int textDarkAlpha = getTextDarkAlpha(this.mTextDark);
        updateClockEffectsContainer();
        ClockEffectUtils.setClockEffectsView(this.mTextArea, this.mClockStyleInfo, zUpdateTextDark, i, 0, i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        MiuiTextGlassView miuiTextGlassView = this.mTimeView;
        MiuiClassicClockInfoBase miuiClassicClockInfoBase = this.mClockStyleInfo;
        ClockEffectUtils.setClockEffectsView(miuiTextGlassView, miuiClassicClockInfoBase, zUpdateTextDark, i, miuiClassicClockInfoBase.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        MiuiTextGlassView miuiTextGlassView2 = this.mTimeView2;
        MiuiClassicClockInfoBase miuiClassicClockInfoBase2 = this.mClockStyleInfo;
        ClockEffectUtils.setClockEffectsView(miuiTextGlassView2, miuiClassicClockInfoBase2, zUpdateTextDark, i, miuiClassicClockInfoBase2.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        if (!ClockEffectUtils.isBlurMixType(this.mClockStyleInfo.getClockEffect()) || isToFullAod()) {
            return;
        }
        MiuiBlurUtils.addMiBackgroundBlendColor(this.mTextArea, i3, 200);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockColor(int i, int i2) {
        super.updateClockColor(i, i2);
        this.mTextArea.setTextColor(i);
        this.mTimeView.setTextColor(i);
        this.mTimeView2.setTextColor(i);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockEffectsContainer() {
        super.updateClockEffectsContainer();
        updateViewBackgroundBlurContainer(this, getDimen(R.dimen.miui_eastern_art_c_clock_blur_radius), this.mTextArea, this.mTimeView, this.mTimeView2);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView
    public void updateColor(ClockStyleInfo clockStyleInfo) {
        if (clockStyleInfo instanceof MiuiClassicPlusClockInfo) {
            super.updateColor(clockStyleInfo);
            if (clockStyleInfo == null) {
                return;
            }
            ClassicClockTimeUtils.setClassicTimeColor(this.mTimeView, this.mClockStyleInfo);
            ClassicClockTimeUtils.setClassicTimeColor(this.mTimeView2, this.mClockStyleInfo);
        }
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        int hourInt = ClassicClockTimeUtils.getHourInt(this.m24HourFormat, this.mCalendar);
        int i = this.mCalendar.get(20);
        if (this.mNeedShowTimeView2) {
            this.mTimeView.setText(ClassicClockTimeUtils.getTimeString(hourInt, true));
            this.mTimeView2.setText(ClassicClockTimeUtils.getTimeString(i, true));
        } else {
            this.mTimeView.setText(this.mContext.getString(R.string.format_hour_minute, ClassicClockTimeUtils.getTimeString(hourInt, this.m24HourFormat), ClassicClockTimeUtils.getTimeString(i, true)));
            this.mTimeView2.setText("");
        }
        Log.d(this.TAG, "mNeedShowTimeView2=" + this.mNeedShowTimeView2 + " mTimeView2=[" + ((Object) this.mTimeView2.getText()) + "]");
        this.mTimeDesc.setContentDescription(DateUtils.formatDateTime(getContext(), System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
    }
}
