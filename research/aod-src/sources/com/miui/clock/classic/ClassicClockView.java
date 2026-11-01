package com.miui.clock.classic;

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
import com.miui.clock.MiuiTextGlassView;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClassicContentStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.HealthBean;
import com.miui.clock.module.MiuiClassicClockInfo;
import com.miui.clock.module.MiuiClassicClockInfoBase;
import com.miui.clock.module.WeatherBean;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DataUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.HealthFetcherController;
import com.miui.clock.utils.MiuiBlurUtils;
import com.miui.clock.utils.MutiDeviceDimenAdapter;
import com.miui.clock.utils.WeatherFetcherController;
import java.util.HashSet;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ClassicClockView extends ClassicClockBaseView {
    public final HashSet healthType;
    public boolean isHealthAppInstall;
    public boolean isWeatherAppInstall;
    public MiuiClassicClockInfo mClassicClockInfo;
    public ClassicContentAreaView mContentArea1;
    public ClassicContentAreaView mContentArea2;
    public boolean mFirstInit;
    public Gson mGson;
    public boolean mHasPresetData;
    public HealthBean mHealthBean;
    public HealthFetcherController mHealthFetcherController;
    public Group mLine3;
    public Group mLine4;
    public int mScreenWidth;
    public boolean mShowFullTime;
    public TextPaint mStaticLayoutPaint;
    public MiuiTextGlassView mTimeView;
    public boolean mUserDefineShowLine3;
    public boolean mUserDefineShowLine4;
    public WeatherBean mWeatherBean;
    public WeatherFetcherController mWeatherFetcherController;

    /* JADX INFO: renamed from: com.miui.clock.classic.ClassicClockView$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
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
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.CONTENT_AREA1.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.CONTENT_AREA2.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$N0Q4fMWNku2aqWmdKifW2mVXs1g(ClassicClockView classicClockView, WeatherBean weatherBean) {
        if (classicClockView.isAttachedToWindow()) {
            classicClockView.updateWeather(weatherBean);
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$fYFAENWig-BBQNxInxSMFIr9ipo, reason: not valid java name */
    public static /* synthetic */ void m650$r8$lambda$fYFAENWigBBQNxInxSMFIr9ipo(ClassicClockView classicClockView, HealthBean healthBean) {
        if (classicClockView.isAttachedToWindow()) {
            classicClockView.updateHealth(healthBean);
        }
    }

    public ClassicClockView(Context context) {
        super(context);
        this.mFirstInit = true;
        this.healthType = new HashSet();
        this.mStaticLayoutPaint = new TextPaint();
    }

    public ClassicClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFirstInit = true;
        this.healthType = new HashSet();
        this.mStaticLayoutPaint = new TextPaint();
    }

    public ClassicClockView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mFirstInit = true;
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

    private int getDimenPX(int i) {
        return getDimen(i);
    }

    private boolean hasWeatherType() {
        return ClassicContentStyle.Weather.isWeatherType(this.mClassicClockInfo.getClassicLine1()) || ClassicContentStyle.Weather.isWeatherType(this.mClassicClockInfo.getClassicLine3()) || ClassicContentStyle.Weather.isWeatherType(this.mClassicClockInfo.getClassicLine4());
    }

    private void presetData(String str, String str2) {
        WeatherBean weatherBean;
        boolean zIsEmpty = TextUtils.isEmpty(str);
        boolean zIsEmpty2 = TextUtils.isEmpty(str2);
        Log.i("ClassicClockView", "presetData this = " + this + ", weatherEmpty = " + zIsEmpty + ", healthEmpty = " + zIsEmpty2);
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
                Log.i("ClassicClockView", "presetData weather convert fail");
                weatherBean = null;
            }
        }
        if (!zIsEmpty2) {
            try {
                healthBean = (HealthBean) this.mGson.fromJson(str2, HealthBean.class);
            } catch (Exception unused2) {
                Log.i("ClassicClockView", "presetData health convert fail");
            }
        }
        if (healthBean == null && weatherBean == null) {
            return;
        }
        this.mHasPresetData = true;
        this.mTextArea.presetData(healthBean, weatherBean);
        this.mContentArea1.presetData(healthBean, weatherBean);
        this.mContentArea2.presetData(healthBean, weatherBean);
    }

    private void updateHealth(HealthBean healthBean) {
        HealthBean healthBean2 = this.mHealthBean;
        if (healthBean2 != null) {
            healthBean2.updateData(healthBean, this.healthType);
        } else {
            this.mHealthBean = healthBean;
        }
        Log.d("ClassicClockView", "Current Health Data:[" + this.mHealthBean.toString() + "]");
        if (ClassicContentStyle.Health.isHealthType(this.mClassicClockInfo.getClassicLine1())) {
            this.mTextArea.updateHealth(this.mHealthBean);
        }
        if (ClassicContentStyle.Health.isHealthType(this.mClassicClockInfo.getClassicLine3())) {
            this.mContentArea1.updateHealth(this.mHealthBean);
            this.mContentArea1.updateColor(this.mClassicClockInfo.getClassicPlusSecondaryColor());
        }
        if (ClassicContentStyle.Health.isHealthType(this.mClassicClockInfo.getClassicLine4())) {
            this.mContentArea2.updateHealth(this.mHealthBean);
            this.mContentArea2.updateColor(this.mClassicClockInfo.getClassicPlusSecondaryColor());
        }
    }

    private void updateHealthTypeList() {
        this.healthType.clear();
        addType(this.mClassicClockInfo.getClassicLine1());
        addType(this.mClassicClockInfo.getClassicLine3());
        addType(this.mClassicClockInfo.getClassicLine4());
    }

    private void updateWeather(WeatherBean weatherBean) {
        this.mWeatherBean = weatherBean;
        if (ClassicContentStyle.Weather.isWeatherType(this.mClassicClockInfo.getClassicLine1())) {
            this.mTextArea.updateWeather(weatherBean);
        }
        if (ClassicContentStyle.Weather.isWeatherType(this.mClassicClockInfo.getClassicLine3())) {
            this.mContentArea1.updateWeather(weatherBean);
            this.mContentArea1.updateColor(this.mClassicClockInfo.getClassicPlusSecondaryColor());
        }
        if (ClassicContentStyle.Weather.isWeatherType(this.mClassicClockInfo.getClassicLine4())) {
            this.mContentArea2.updateWeather(weatherBean);
            this.mContentArea2.updateColor(this.mClassicClockInfo.getClassicPlusSecondaryColor());
        }
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        if (this.mClockStyleInfo == null) {
            return;
        }
        if (!ClockEffectUtils.isLessThanOS2()) {
            ClockEffectUtils.clearClockEffectsContainer(this, this.mClockStyleInfo, z);
            ClockEffectUtils.clearClockEffectsView(this.mTextArea, this.mClockStyleInfo, z);
            ClockEffectUtils.clearClockEffectsView(this.mTimeView, this.mClockStyleInfo, z);
            ClockEffectUtils.clearClockEffectsView(this.mContentArea1, this.mClockStyleInfo, z);
            ClockEffectUtils.clearClockEffectsView(this.mContentArea2, this.mClockStyleInfo, z);
            return;
        }
        if ((DeviceConfig.supportBackgroundBlur(this.mContext) || DeviceConfig.isHighTextContrastEnabled(this.mContext)) && DeviceConfig.BACKGROUND_BLUR_SUPPORTED_2) {
            MiuiBlurUtils.clearContainerPassBlur(this);
            MiuiBlurUtils.clearMemberBlendColor(this.mTextArea);
            MiuiBlurUtils.clearMemberBlendColor(this.mTimeView);
            MiuiBlurUtils.clearMemberBlendColor(this.mContentArea1);
            MiuiBlurUtils.clearMemberBlendColor(this.mContentArea2);
        }
        MiuiBlurUtils.clearMemberBlendColor(this.mContentArea1);
        MiuiBlurUtils.clearMemberBlendColor(this.mContentArea2);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
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
    public String getHealthJson() {
        HealthBean healthBean = this.mHealthBean;
        return healthBean == null ? super.getHealthJson() : healthBean.toJsonString();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i == 1) {
            return this.mTextArea;
        }
        if (i == 2) {
            return this.mTimeView;
        }
        if (i != 3) {
            return i != 4 ? super.getIClockView(clockViewType) : this.mContentArea2;
        }
        return this.mContentArea1;
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationRelativePosition() {
        return (DeviceConfig.PAD_DEVICE && getResources().getConfiguration().orientation == 2) ? 2 : 1;
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public float getTopMargin() {
        return MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.classic_text_area_margin_top_has_face, true);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public String getWeatherJson() {
        WeatherBean weatherBean = this.mWeatherBean;
        return weatherBean == null ? super.getWeatherJson() : weatherBean.toJsonString();
    }

    public final boolean hasNumberHourMin() {
        return isNumberHourMinType(this.mClassicClockInfo.getClassicLine2()) || isNumberHourMinType(this.mClassicClockInfo.getClassicLine3()) || isNumberHourMinType(this.mClassicClockInfo.getClassicLine4());
    }

    public final boolean isNumberHourMinType(int i) {
        return i == 302 || i == 303;
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView
    public boolean isPlus() {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.isHealthAppInstall = DataUtils.isHealthAppInstalled(this.mContext);
        this.isWeatherAppInstall = DataUtils.isWeatherAppInstalled(this.mContext);
    }

    @Override // com.miui.clock.MiuiBaseClock2, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int iWidth = DeviceConfig.getScreenRect(this.mContext).width();
        if (this.mScreenWidth != iWidth) {
            this.mScreenWidth = iWidth;
            onScreenSizeChange();
        }
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
        this.mContentArea1 = (ClassicContentAreaView) findViewById(R.id.content_area1);
        this.mContentArea2 = (ClassicContentAreaView) findViewById(R.id.content_area2);
        this.mLine3 = (Group) findViewById(R.id.line3);
        this.mLine4 = (Group) findViewById(R.id.line4);
        this.mContentArea1.setCalendar(this.mCalendar);
        this.mContentArea2.setCalendar(this.mCalendar);
        updateSignatureText();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiBaseClock2
    public void onLanguageChanged() {
        super.onLanguageChanged();
        MiuiClassicClockInfoBase miuiClassicClockInfoBase = this.mClockStyleInfo;
        if (miuiClassicClockInfoBase == null) {
            return;
        }
        ClassicClockTimeUtils.setClassicTimeStyle(this.mTimeView, miuiClassicClockInfoBase);
        this.mContentArea1.initTextViewStyle(true);
        this.mContentArea2.initTextViewStyle(true);
        if (ClassicContentStyle.LargeDate.needSwitchStyle(this.mClassicClockInfo.getClassicLine3())) {
            this.mContentArea1.initViewVisible();
            this.mContentArea1.updateColor(this.mClassicClockInfo.getClassicPlusSecondaryColor());
        }
        if (ClassicContentStyle.LargeDate.needSwitchStyle(this.mClassicClockInfo.getClassicLine4())) {
            this.mContentArea2.initViewVisible();
            this.mContentArea2.updateColor(this.mClassicClockInfo.getClassicPlusSecondaryColor());
        }
        updateTime();
        queryDataAsync();
    }

    public void onScreenSizeChange() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);
        int i = R.id.text_area;
        constraintSet.setMargin(i, 3, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.classic_text_area_margin_top_has_face, true));
        Context context = getContext();
        int i2 = R.dimen.classic_text_area_margin_start_end;
        constraintSet.setMargin(i, 6, MutiDeviceDimenAdapter.getMultiDeviceDimen(context, i2, true));
        constraintSet.setMargin(i, 7, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), i2, true));
        int i3 = R.id.area1;
        constraintSet.setMargin(i3, 3, getDimenPX(isEditMode() ? R.dimen.classic_area2_margin_top_edit : R.dimen.classic_area2_margin_top));
        int i4 = R.id.time_view;
        Context context2 = getContext();
        int i5 = R.dimen.classic_area_2_3_4_margin_start_end;
        constraintSet.setMargin(i4, 6, MutiDeviceDimenAdapter.getMultiDeviceDimen(context2, i5, true));
        constraintSet.setMargin(i4, 7, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), i5, true));
        int i6 = R.dimen.classic_area_2_3_4_content_margin_bottom;
        constraintSet.setMargin(i4, 4, getDimenPX(i6));
        int i7 = R.id.content_area1;
        constraintSet.setMargin(i7, 6, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), i5, true));
        constraintSet.setMargin(i7, 7, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), i5, true));
        constraintSet.setMargin(i7, 4, getDimenPX(i6));
        int i8 = R.id.content_area2;
        constraintSet.setMargin(i8, 6, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), i5, true));
        constraintSet.setMargin(i8, 7, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), i5, true));
        constraintSet.setMargin(i8, 4, getDimenPX(i6));
        int i9 = R.dimen.classic_area_location_height;
        constraintSet.constrainHeight(i3, getDimenPX(i9));
        constraintSet.constrainHeight(R.id.area2, getDimenPX(i9));
        constraintSet.constrainHeight(R.id.area3, getDimenPX(i9));
        int i10 = R.id.signature_text_container;
        constraintSet.setMargin(i10, 3, getDimenPX(isEditMode() ? R.dimen.classic_signature_area_margin_top_edit : R.dimen.classic_signature_area_margin_top));
        Context context3 = getContext();
        int i11 = R.dimen.classic_text_signature_margin_start_end;
        constraintSet.setMargin(i10, 6, MutiDeviceDimenAdapter.getMultiDeviceDimen(context3, i11, true));
        constraintSet.setMargin(i10, 7, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), i11, true));
        constraintSet.constrainWidth(i10, getDimenPX(R.dimen.classic_text_signature_max_width));
        constraintSet.applyTo(this);
        this.mTextArea.onScreenSizeChange();
        MiuiClassicClockInfoBase miuiClassicClockInfoBase = this.mClockStyleInfo;
        if (miuiClassicClockInfoBase != null) {
            ClassicClockTimeUtils.setClassicTimeStyle(this.mTimeView, miuiClassicClockInfoBase);
        }
        updateSignatureText();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void queryDataAsync() {
        Context specialContext = WeatherFetcherController.Companion.getSpecialContext(this.mContext, this.mCurrentUserId);
        if (hasWeatherType()) {
            if (this.mWeatherFetcherController == null) {
                this.mWeatherFetcherController = new WeatherFetcherController();
            }
            this.mWeatherFetcherController.fetchWeatherData(specialContext, this.mHasPresetData, new WeatherFetcherController.Callback() { // from class: com.miui.clock.classic.ClassicClockView$$ExternalSyntheticLambda0
                @Override // com.miui.clock.utils.WeatherFetcherController.Callback
                public final void updateWeatherInfo(WeatherBean weatherBean) {
                    ClassicClockView.$r8$lambda$N0Q4fMWNku2aqWmdKifW2mVXs1g(this.f$0, weatherBean);
                }
            });
        }
        updateHealthTypeList();
        if (this.healthType.size() > 0) {
            if (this.mHealthFetcherController == null) {
                this.mHealthFetcherController = new HealthFetcherController();
            }
            this.mHealthFetcherController.fetchHealthData(specialContext, this.healthType, new HealthFetcherController.Callback() { // from class: com.miui.clock.classic.ClassicClockView$$ExternalSyntheticLambda1
                @Override // com.miui.clock.utils.HealthFetcherController.Callback
                public final void updateHealth(HealthBean healthBean) {
                    ClassicClockView.m650$r8$lambda$fYFAENWigBBQNxInxSMFIr9ipo(this.f$0, healthBean);
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
        if (!ClockEffectUtils.isBlurMixType(this.mClassicClockInfo.getClockEffect())) {
            if (ClockEffectUtils.isDifferenceType(this.mClockStyleInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                boolean zIsFullAODType = ClockStyleInfo.isFullAODType(this.mClockStyleInfo.getDisplayType());
                MiuiClassicClockInfoBase miuiClassicClockInfoBase = this.mClockStyleInfo;
                int fullAodBlendColor = zIsFullAODType ? miuiClassicClockInfoBase.getFullAodBlendColor() : miuiClassicClockInfoBase.getBlendColor();
                int fullAodSecondaryBlendColor = zIsFullAODType ? this.mClockStyleInfo.getFullAodSecondaryBlendColor() : this.mClockStyleInfo.getClassicPlusSecondaryBlendColor();
                MiuiBlurUtils.setMemberDiffEffectColor(this.mTextArea, fullAodBlendColor);
                MiuiBlurUtils.setMemberDiffEffectColor(this.mTimeView, fullAodBlendColor);
                MiuiBlurUtils.setMemberDiffEffectColor(this.mContentArea1, fullAodSecondaryBlendColor);
                MiuiBlurUtils.setMemberDiffEffectColor(this.mContentArea2, fullAodSecondaryBlendColor);
                return;
            }
            return;
        }
        if (this.mClockStyleInfo == null || !DeviceConfig.supportBackgroundBlur(this.mContext)) {
            return;
        }
        boolean zIsFullAODType2 = ClockStyleInfo.isFullAODType(this.mClockStyleInfo.getDisplayType());
        MiuiClassicClockInfoBase miuiClassicClockInfoBase2 = this.mClockStyleInfo;
        int fullAodBlendColor2 = zIsFullAODType2 ? miuiClassicClockInfoBase2.getFullAodBlendColor() : miuiClassicClockInfoBase2.getBlendColor();
        MiuiClassicClockInfoBase miuiClassicClockInfoBase3 = this.mClockStyleInfo;
        int fullAodPrimaryColor = zIsFullAODType2 ? miuiClassicClockInfoBase3.getFullAodPrimaryColor() : miuiClassicClockInfoBase3.getPrimaryColor();
        MiuiClassicClockInfoBase miuiClassicClockInfoBase4 = this.mClockStyleInfo;
        int fullAodSecondaryBlendColor2 = zIsFullAODType2 ? miuiClassicClockInfoBase4.getFullAodSecondaryBlendColor() : miuiClassicClockInfoBase4.getSecondaryBlendColor();
        boolean z3 = false;
        MiuiBlurUtils.setMemberBlendColors(this.mTextArea, !zIsFullAODType2 && z, fullAodBlendColor2, fullAodPrimaryColor);
        MiuiBlurUtils.setMemberBlendColors(this.mTimeView, !zIsFullAODType2 && z, fullAodBlendColor2, fullAodPrimaryColor);
        MiuiBlurUtils.setMemberBlendColor(this.mContentArea1, !zIsFullAODType2 && z, fullAodSecondaryBlendColor2);
        ClassicContentAreaView classicContentAreaView = this.mContentArea2;
        if (!zIsFullAODType2 && z) {
            z3 = true;
        }
        MiuiBlurUtils.setMemberBlendColor(classicContentAreaView, z3, fullAodSecondaryBlendColor2);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        this.mClassicClockInfo = (MiuiClassicClockInfo) clockStyleInfo;
        if (this.mFirstInit) {
            this.mFirstInit = false;
            onScreenSizeChange();
        }
        boolean zHasNumberHourMin = hasNumberHourMin();
        if (!this.mHasPresetData && !this.mClassicClockInfo.isUnablePresetData()) {
            presetData(this.mClassicClockInfo.getWeatherJson(), this.mClassicClockInfo.getHealthJson());
        }
        super.setClockStyleInfo(clockStyleInfo);
        clearColorEffect(false);
        this.mTimeView.setSameNumberWidth(ClassicContentStyle.needOpenTextSameWidth(this.mClassicClockInfo.getClassicLine2(), zHasNumberHourMin));
        ClassicClockTimeUtils.setClassicTimeStyle(this.mTimeView, this.mClockStyleInfo);
        int classicLine3 = this.mClassicClockInfo.getClassicLine3();
        int classicLine4 = this.mClassicClockInfo.getClassicLine4();
        if (!this.mUserDefineShowLine3) {
            this.mLine3.setVisibility(classicLine3 == 0 ? 8 : 0);
        }
        if (!this.mUserDefineShowLine4) {
            this.mLine4.setVisibility(classicLine4 == 0 ? 8 : 0);
        }
        this.mContentArea1.initData(this.mClassicClockInfo, classicLine3, zHasNumberHourMin);
        this.mContentArea2.initData(this.mClassicClockInfo, classicLine4, zHasNumberHourMin);
        queryDataAsync();
        if (this.mClassicClockInfo.getClassicLine5() == 0 || isAODType()) {
            this.mSignatureText.setVisibility(8);
            this.mSignatureText.setText("");
        } else {
            String signature = this.mClassicClockInfo.getSignature();
            this.mSignatureText.setVisibility(4);
            this.mSignatureText.setText(signature);
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
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);
        constraintSet.setMargin(R.id.area1, 3, getDimenPX(isEditMode() ? R.dimen.classic_area2_margin_top_edit : R.dimen.classic_area2_margin_top));
        constraintSet.applyTo(this);
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

    public void setLine2ExtraMarginTop(int i) {
        setMarginTop(R.id.area1, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), this.showFace ? R.dimen.classic_text_area_margin_top_has_face : R.dimen.classic_area2_margin_top, true) + getDimenPX(i));
    }

    public void setLine3ExtraMarginTop(int i) {
        setMarginTop(R.id.area2, getDimenPX(i));
    }

    public void setLine4ExtraMarginTop(int i) {
        setMarginTop(R.id.area3, getDimenPX(i));
    }

    public void setLine5ExtraMarginTop(int i) {
        setMarginTop(R.id.signature_text_container, getDimenPX(R.dimen.classic_signature_area_margin_top) + getDimenPX(i));
    }

    public void setLineExtraMarginTop(int i, int i2, int i3, int i4) {
        ((ClassicClockBaseView) this).mConstraintSet.clone(this);
        ((ClassicClockBaseView) this).mConstraintSet.setMargin(R.id.area1, 3, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), this.showFace ? R.dimen.classic_text_area_margin_top_has_face : R.dimen.classic_area2_margin_top, true) + getDimenPX(i));
        ((ClassicClockBaseView) this).mConstraintSet.setMargin(R.id.area2, 3, getDimenPX(i2));
        ((ClassicClockBaseView) this).mConstraintSet.setMargin(R.id.area3, 3, getDimenPX(i3));
        ((ClassicClockBaseView) this).mConstraintSet.setMargin(R.id.signature_text_container, 3, getDimenPX(R.dimen.classic_signature_area_margin_top) + getDimenPX(i4));
        ((ClassicClockBaseView) this).mConstraintSet.applyTo(this);
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

    public void setShowFullTime(boolean z) {
        Log.i("ClassicClockView", "setShowFullTime = " + z);
        this.mShowFullTime = z;
        this.mTimeView.setText(ClassicClockTimeUtils.getClassicTime(this.mContext, this.mCalendar, this.m24HourFormat, this.mClockStyleInfo.getClassicLine2(), this.mShowFullTime));
    }

    public void setUserDefineShowLine3(boolean z) {
        this.mUserDefineShowLine3 = z;
    }

    public void setUserDefineShowLine4(boolean z) {
        this.mUserDefineShowLine4 = z;
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
        MiuiTextGlassView miuiTextGlassView = this.mTimeView;
        MiuiClassicClockInfoBase miuiClassicClockInfoBase = this.mClockStyleInfo;
        int i5 = i;
        ClockEffectUtils.setClockEffectsView(miuiTextGlassView, miuiClassicClockInfoBase, zUpdateTextDark, i5, miuiClassicClockInfoBase.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        ClockEffectUtils.setClockEffectsView(this.mTextArea, this.mClockStyleInfo, zUpdateTextDark, i5, 0, i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        boolean zIsAutoSecondaryColor = this.mClockStyleInfo.isAutoSecondaryColor();
        if (!zIsAutoSecondaryColor) {
            i5 = i2;
        }
        int i6 = zIsAutoSecondaryColor ? i3 : i4;
        ClockEffectUtils.setClockEffectsView(this.mContentArea1, this.mClockStyleInfo, zUpdateTextDark, i5, 0, i6, zIsAODType, zIsFullAODType, textDarkAlpha);
        ClockEffectUtils.setClockEffectsView(this.mContentArea2, this.mClockStyleInfo, zUpdateTextDark, i5, 0, i6, zIsAODType, zIsFullAODType, textDarkAlpha);
        if (!ClockEffectUtils.isBlurMixType(this.mClockStyleInfo.getClockEffect()) || isToFullAod()) {
            return;
        }
        MiuiBlurUtils.addMiBackgroundBlendColor(this.mContentArea1, i6, 200);
        MiuiBlurUtils.addMiBackgroundBlendColor(this.mContentArea2, i6, 200);
        MiuiBlurUtils.addMiBackgroundBlendColor(this.mTextArea, i3, 200);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockColor(int i, int i2) {
        super.updateClockColor(i, i2);
        this.mTextArea.setTextColor(i);
        this.mTimeView.setTextColor(i);
        this.mContentArea1.updateColor(this.mClassicClockInfo.getCorrectSecondaryColor(i, i2));
        this.mContentArea2.updateColor(this.mClassicClockInfo.getCorrectSecondaryColor(i, i2));
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockEffectsContainer() {
        super.updateClockEffectsContainer();
        updateViewBackgroundBlurContainer(this, getDimen(R.dimen.miui_eastern_art_c_clock_blur_radius), this.mTimeView, this.mTextArea, this.mContentArea1, this.mContentArea2);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView
    public void updateColor(ClockStyleInfo clockStyleInfo) {
        super.updateColor(clockStyleInfo);
        if (clockStyleInfo == null) {
            return;
        }
        this.mClassicClockInfo = (MiuiClassicClockInfo) clockStyleInfo;
        ClassicClockTimeUtils.setClassicTimeColor(this.mTimeView, this.mClockStyleInfo);
        this.mContentArea1.updateColor(this.mClassicClockInfo.getClassicPlusSecondaryColor());
        this.mContentArea2.updateColor(this.mClassicClockInfo.getClassicPlusSecondaryColor());
        updateTime();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        String classicTime = ClassicClockTimeUtils.getClassicTime(this.mContext, this.mCalendar, this.m24HourFormat, this.mClockStyleInfo.getClassicLine2(), this.mShowFullTime);
        if (isAODType()) {
            Log.d("ClassicClockView", "classic clock time update: " + classicTime);
        }
        this.mTimeView.setText(classicTime);
        this.mContentArea1.updateTime(this.m24HourFormat);
        this.mContentArea2.updateTime(this.m24HourFormat);
        if (!this.mUserDefineShowLine3) {
            this.mLine3.setVisibility(this.mClassicClockInfo.getClassicLine3() == 0 ? 8 : 0);
        }
        if (this.mUserDefineShowLine4) {
            return;
        }
        this.mLine4.setVisibility(this.mClassicClockInfo.getClassicLine4() == 0 ? 8 : 0);
    }
}
