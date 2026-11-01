package com.miui.clock.classic.max;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.ColorUtils;
import com.google.gson.Gson;
import com.miui.clock.MiuiTextGlassView;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.classic.ClassicClockBaseView;
import com.miui.clock.module.ClassicContentStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.HealthBean;
import com.miui.clock.module.MiuiClassicClockInfoBase;
import com.miui.clock.module.WeatherBean;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.GlobalColorUtils;
import com.miui.clock.utils.HealthFetcherController;
import com.miui.clock.utils.MiuiBlurUtils;
import com.miui.clock.utils.MutiDeviceDimenAdapter;
import com.miui.clock.utils.WeatherFetcherController;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.util.HashSet;
import java.util.Map;
import miuix.androidbasewidget.widget.ClearableEditText;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class ClassicMaxClockBaseView extends ClassicClockBaseView {
    public String TAG;
    public ConstraintLayout anchor;
    public final HashSet healthType;
    public ConstraintLayout mClockContainer;
    public Gson mGson;
    public boolean mHasPresetData;
    public HealthBean mHealthBean;
    public HealthFetcherController mHealthFetcherController;
    public TextView mSignatureText;
    public ClearableEditText mSignatureTextInEditor;
    public TextPaint mStaticLayoutPaint;
    public MiuiTextGlassView mTimeView;
    public WeatherBean mWeatherBean;
    public WeatherFetcherController mWeatherFetcherController;

    /* JADX INFO: renamed from: com.miui.clock.classic.max.ClassicMaxClockBaseView$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.CLOCK_CONTAINER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.TEXT_AREA.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.TIME_AREA.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.SIGNATURE_AREA.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.SIGNATURE_EDIT_AREA.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$73ZPNCljZfcba02LjbGoPSUVpLU(ClassicMaxClockBaseView classicMaxClockBaseView, WeatherBean weatherBean) {
        if (classicMaxClockBaseView.isAttachedToWindow()) {
            classicMaxClockBaseView.updateWeather(weatherBean);
        }
    }

    public ClassicMaxClockBaseView(Context context) {
        super(context);
        this.TAG = "ClassicMaxClockBaseView";
        this.healthType = new HashSet();
        this.mStaticLayoutPaint = new TextPaint();
    }

    public ClassicMaxClockBaseView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "ClassicMaxClockBaseView";
        this.healthType = new HashSet();
        this.mStaticLayoutPaint = new TextPaint();
    }

    public ClassicMaxClockBaseView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = "ClassicMaxClockBaseView";
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

    private int getContainerTop() {
        Rect screenRect = DeviceConfig.getScreenRect(getContext());
        int iHeight = screenRect.height();
        int iWidth = screenRect.width();
        boolean z = false;
        boolean z2 = this.mConfiguration.orientation == 2;
        if (!DeviceConfig.isPhoneDevice() && (!DeviceConfig.isFoldDevice() || DeviceConfig.inFoldLargeScreen(getContext()))) {
            z = z2;
        }
        Configuration configuration = getContext().getResources().getConfiguration();
        Log.w(this.TAG, "debug mConfiguration orientation" + this.mConfiguration.orientation + " view config" + configuration.orientation + " smallestScreenWidthDp: " + configuration.smallestScreenWidthDp + " height" + iHeight + "width " + iWidth + this);
        int iMin = z ? Math.min(iHeight, iWidth) : Math.max(iHeight, iWidth);
        return ((iMin - (MutiDeviceDimenAdapter.getMultiDeviceDimen(this.mContext, R.dimen.miui_classic_max_time_area_text_height, true) + getDimen(R.dimen.miui_classic_max_time_area_margin_top))) / 2) - ((int) (iMin * ClassicClockTimeUtils.getClassicMaxOffsetRadio()));
    }

    private boolean hasWeatherType() {
        return ClassicContentStyle.Weather.isWeatherType(this.mClockStyleInfo.getClassicLine1());
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
            return this.mClockContainer;
        }
        if (i == 2) {
            return this.mTextArea;
        }
        if (i == 3) {
            return this.mTimeView;
        }
        if (i != 4) {
            return i != 5 ? super.getIClockView(clockViewType) : this.mSignatureTextInEditor;
        }
        return this.mSignatureText;
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockBottom() {
        return super.getNotificationClockBottom();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiBaseClock2
    public View getNotificationClockTopView() {
        return this.mClockContainer;
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView
    public boolean isPlus() {
        return true;
    }

    @Override // com.miui.clock.MiuiBaseClock2, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int iUpdateFrom = this.mConfiguration.updateFrom(configuration);
        if ((iUpdateFrom & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0 || (iUpdateFrom & 128) != 0 || (iUpdateFrom & 1024) != 0) {
            onScreenSizeChange();
            Log.d(this.TAG, "configuration changed, changes=" + iUpdateFrom);
        }
        if (!this.mEditMode) {
            checkAndCorrectSignatureText(this.mSignatureText, this.mClockStyleInfo.getSignature(), 2);
        } else {
            ClearableEditText clearableEditText = this.mSignatureTextInEditor;
            checkAndCorrectSignatureText(clearableEditText, clearableEditText.getText().toString(), 2);
        }
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mClockContainer = (ConstraintLayout) findViewById(R.id.clock_container);
        this.mTimeView = (MiuiTextGlassView) findViewById(R.id.time_view);
        TextView textView = (TextView) findViewById(R.id.signature_text);
        this.mSignatureText = textView;
        textView.setTypeface(FontUtils.getMiSans(380));
        ClearableEditText clearableEditText = (ClearableEditText) findViewById(R.id.signature_text_edit);
        this.mSignatureTextInEditor = clearableEditText;
        if (clearableEditText != null) {
            clearableEditText.setBackground(null);
            this.mSignatureTextInEditor.setPadding(0, 0, 0, 0);
        }
        ClassicClockBaseView.setLocalePreferredLineHeightForMinimumUsed(this.mSignatureTextInEditor, false);
        Configuration configuration = new Configuration();
        this.mConfiguration = configuration;
        configuration.setTo(getResources().getConfiguration());
        this.anchor = (ConstraintLayout) findViewById(R.id.clock_container_anchor);
        updateSignatureText();
    }

    public void onScreenSizeChange() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mTimeView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_classic_max_time_area_margin_top);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = MutiDeviceDimenAdapter.getMultiDeviceDimen(this.mContext, R.dimen.miui_classic_max_time_area_text_height, true);
        this.mTimeView.setLayoutParams(layoutParams);
        if (!isAODType()) {
            updateClockContainerLayoutParams();
        }
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);
        constraintSet.constrainWidth(R.id.signature_text_container, getDimen(R.dimen.classic_text_signature_max_width));
        constraintSet.applyTo(this);
        this.mTextArea.onScreenSizeChange();
        this.mTimeView.setTextSize(0, MutiDeviceDimenAdapter.getMultiDeviceDimen(this.mContext, R.dimen.miui_classic_max_time_area_text_size, true));
        updateSignatureText();
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.mViewHeight == i2 && this.mViewWidth == i) {
            return;
        }
        this.mViewWidth = i;
        this.mViewHeight = i2;
        Log.d(this.TAG, "onScreenSizeChange w=" + i + " h=" + i2 + "this : " + this);
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void queryDataAsync() {
        Context specialContext = WeatherFetcherController.Companion.getSpecialContext(this.mContext, this.mCurrentUserId);
        if (hasWeatherType()) {
            if (this.mWeatherFetcherController == null) {
                this.mWeatherFetcherController = new WeatherFetcherController();
            }
            this.mWeatherFetcherController.fetchWeatherData(specialContext, this.mHasPresetData, new WeatherFetcherController.Callback() { // from class: com.miui.clock.classic.max.ClassicMaxClockBaseView$$ExternalSyntheticLambda0
                @Override // com.miui.clock.utils.WeatherFetcherController.Callback
                public final void updateWeatherInfo(WeatherBean weatherBean) {
                    ClassicMaxClockBaseView.$r8$lambda$73ZPNCljZfcba02LjbGoPSUVpLU(this.f$0, weatherBean);
                }
            });
        }
        updateHealthTypeList();
        if (this.healthType.isEmpty()) {
            return;
        }
        if (this.mHealthFetcherController == null) {
            this.mHealthFetcherController = new HealthFetcherController();
        }
        this.mHealthFetcherController.fetchHealthData(specialContext, this.healthType, new HealthFetcherController.Callback() { // from class: com.miui.clock.classic.max.ClassicMaxClockBaseView.1
            @Override // com.miui.clock.utils.HealthFetcherController.Callback
            public void updateHealth(HealthBean healthBean) {
                if (ClassicMaxClockBaseView.this.isAttachedToWindow()) {
                    ClassicMaxClockBaseView.this.updateHealth(healthBean);
                }
            }
        });
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
        String str;
        boolean z3;
        int aodSecondaryColor;
        int secondaryColor;
        int color;
        int color2;
        int primaryColor;
        int color3;
        int color4;
        super.setClockPalette(i, z, map, z2);
        if (map != null) {
            str = "secondary30=" + map.get("secondary30");
        } else {
            str = "null";
        }
        Log.d(this.TAG, "setClockPalette: type = " + i + ", textDark = " + z + ", palette = " + str);
        if (this.mClockStyleInfo == null || DeviceConfig.isHighTextContrastEnabled(this.mContext)) {
            return;
        }
        int clockEffect = this.mClockStyleInfo.getClockEffect();
        boolean zIsFullAODType = ClockStyleInfo.isFullAODType(this.mClockStyleInfo.getDisplayType());
        boolean zSupportBackgroundBlur = DeviceConfig.supportBackgroundBlur(this.mContext);
        int primaryColor2 = -1;
        if (ClockEffectUtils.isGradualType(clockEffect)) {
            z3 = true;
        } else {
            if (ClockEffectUtils.isDifferenceType(clockEffect)) {
                if (this.mClockStyleInfo.isAutoPrimaryColor()) {
                    Object obj = z ? "secondary15" : "secondary85";
                    if (map != null && map.get(obj) != null) {
                        int iIntValue = map.get(obj).intValue();
                        MiuiClassicClockInfoBase miuiClassicClockInfoBase = this.mClockStyleInfo;
                        miuiClassicClockInfoBase.setPrimaryColor(ClockStyleInfo.isAODType(miuiClassicClockInfoBase.getDisplayType()) ? GlobalColorUtils.transformAodColor(iIntValue) : iIntValue);
                        this.mClockStyleInfo.setBlendColor(iIntValue);
                        this.mClockStyleInfo.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(iIntValue));
                        this.mClockStyleInfo.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(iIntValue));
                    }
                } else {
                    MiuiClassicClockInfoBase miuiClassicClockInfoBase2 = this.mClockStyleInfo;
                    miuiClassicClockInfoBase2.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(miuiClassicClockInfoBase2.getPrimaryColor()));
                    MiuiClassicClockInfoBase miuiClassicClockInfoBase3 = this.mClockStyleInfo;
                    miuiClassicClockInfoBase3.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(miuiClassicClockInfoBase3.getBlendColor()));
                }
                if (this.mClockStyleInfo.isAutoSecondaryColor()) {
                    String str2 = z ? "secondary15" : "secondary85";
                    if (map != null && map.get(str2) != null) {
                        int iIntValue2 = map.get(str2).intValue();
                        MiuiClassicClockInfoBase miuiClassicClockInfoBase4 = this.mClockStyleInfo;
                        miuiClassicClockInfoBase4.setSecondaryColor(ClockStyleInfo.isAODType(miuiClassicClockInfoBase4.getDisplayType()) ? GlobalColorUtils.transformAodColor(iIntValue2) : iIntValue2);
                        this.mClockStyleInfo.setSecondaryBlendColor(iIntValue2);
                        this.mClockStyleInfo.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(iIntValue2));
                        this.mClockStyleInfo.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(iIntValue2));
                    }
                } else {
                    MiuiClassicClockInfoBase miuiClassicClockInfoBase5 = this.mClockStyleInfo;
                    miuiClassicClockInfoBase5.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(miuiClassicClockInfoBase5.getClassicPlusSecondaryColor()));
                    MiuiClassicClockInfoBase miuiClassicClockInfoBase6 = this.mClockStyleInfo;
                    miuiClassicClockInfoBase6.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(miuiClassicClockInfoBase6.getClassicPlusSecondaryBlendColor()));
                }
                this.mClockStyleInfo.convertAnimationColor();
            } else if (!ClockEffectUtils.isGlowType(this.mClockStyleInfo.getClockEffect()) || !DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                if (this.mClockStyleInfo.isAutoPrimaryColor()) {
                    if (ClockEffectUtils.isBlurMixType(clockEffect) && DeviceConfig.supportBackgroundBlur(this.mContext)) {
                        if (z) {
                            color3 = (map == null || map.get("neutral-variant30") == null) ? this.mClockStyleInfo.getPrimaryColor() : map.get("neutral-variant30").intValue();
                        } else {
                            color3 = Color.parseColor("#FFABABAB");
                        }
                        if (z) {
                            color4 = (map == null || map.get("secondary70") == null) ? color3 : map.get("secondary70").intValue();
                        } else {
                            color4 = Color.parseColor("#FFBEBEBE");
                        }
                        this.mClockStyleInfo.setPrimaryColor(-1);
                        this.mClockStyleInfo.setFullAodPrimaryColor(-1);
                        this.mClockStyleInfo.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(color3));
                        this.mClockStyleInfo.setBlendColor(color3);
                        this.mClockStyleInfo.setAodPrimaryColor(color4);
                        this.mClockStyleInfo.setAodBlendColor(color4);
                    } else {
                        if (z) {
                            primaryColor = (map == null || map.get("secondary30") == null) ? this.mClockStyleInfo.getPrimaryColor() : map.get("secondary30").intValue();
                        } else {
                            primaryColor = -1;
                        }
                        this.mClockStyleInfo.setPrimaryColor(primaryColor);
                        this.mClockStyleInfo.setAodPrimaryColor(GlobalColorUtils.transformAodColor(primaryColor));
                        this.mClockStyleInfo.setAodBlendColor(GlobalColorUtils.transformAodColor(primaryColor));
                        this.mClockStyleInfo.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(primaryColor));
                    }
                } else if (ClockEffectUtils.isBlurMixType(clockEffect) && DeviceConfig.supportBackgroundBlur(this.mContext)) {
                    int iTransformAodColor = GlobalColorUtils.transformAodColor(this.mClockStyleInfo.getBlendColor());
                    int iTransformFullAodColor = GlobalColorUtils.transformFullAodColor(this.mClockStyleInfo.getBlendColor());
                    this.mClockStyleInfo.setPrimaryColor(-1);
                    this.mClockStyleInfo.setAodPrimaryColor(iTransformAodColor);
                    this.mClockStyleInfo.setAodBlendColor(iTransformAodColor);
                    this.mClockStyleInfo.setFullAodPrimaryColor(-1);
                    this.mClockStyleInfo.setFullAodBlendColor(iTransformFullAodColor);
                } else {
                    if (ClockEffectUtils.isBlurMixType(this.mClockStyleInfo.getClockEffect()) && !DeviceConfig.supportBackgroundBlur(this.mContext)) {
                        this.mClockStyleInfo.convertPrimaryColorByBlendColor(true);
                    }
                    this.mClockStyleInfo.setAodBlendColor(this.mClockStyleInfo.getAodPrimaryColor());
                    MiuiClassicClockInfoBase miuiClassicClockInfoBase7 = this.mClockStyleInfo;
                    miuiClassicClockInfoBase7.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(miuiClassicClockInfoBase7.getPrimaryColor()));
                }
                int color5 = Color.parseColor("#FFFFFF");
                if (!this.mClockStyleInfo.isAutoSecondaryColor()) {
                    if (ClockEffectUtils.isBlurMixType(clockEffect) && DeviceConfig.supportBackgroundBlur(this.mContext)) {
                        int iTransformAodColor2 = GlobalColorUtils.transformAodColor(this.mClockStyleInfo.getSecondaryBlendColor());
                        this.mClockStyleInfo.setSecondaryColor(color5);
                        this.mClockStyleInfo.setFullAodSecondaryColor(color5);
                        MiuiClassicClockInfoBase miuiClassicClockInfoBase8 = this.mClockStyleInfo;
                        miuiClassicClockInfoBase8.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(miuiClassicClockInfoBase8.getSecondaryBlendColor()));
                        aodSecondaryColor = iTransformAodColor2;
                        z3 = true;
                    } else {
                        if (!ClockEffectUtils.isBlurMixType(this.mClockStyleInfo.getClockEffect()) || DeviceConfig.supportBackgroundBlur(this.mContext)) {
                            z3 = true;
                        } else {
                            z3 = true;
                            this.mClockStyleInfo.convertSecondaryColorByBlendColor(true);
                        }
                        aodSecondaryColor = this.mClockStyleInfo.getAodSecondaryColor();
                        MiuiClassicClockInfoBase miuiClassicClockInfoBase9 = this.mClockStyleInfo;
                        miuiClassicClockInfoBase9.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(miuiClassicClockInfoBase9.getSecondaryColor()));
                    }
                    this.mClockStyleInfo.setAodSecondaryColor(aodSecondaryColor);
                    this.mClockStyleInfo.setAodSecondaryBlendColor(aodSecondaryColor);
                } else if (ClockEffectUtils.isBlurMixType(clockEffect) && DeviceConfig.supportBackgroundBlur(this.mContext)) {
                    if (z) {
                        color = (map == null || map.get("neutral-variant30") == null) ? this.mClockStyleInfo.getSecondaryColor() : map.get("neutral-variant30").intValue();
                    } else {
                        color = Color.parseColor("#FFABABAB");
                    }
                    if (z) {
                        color2 = (map == null || map.get("secondary70") == null) ? color : map.get("secondary70").intValue();
                    } else {
                        color2 = Color.parseColor("#FFBEBEBE");
                    }
                    this.mClockStyleInfo.setSecondaryColor(color5);
                    this.mClockStyleInfo.setFullAodSecondaryColor(color5);
                    this.mClockStyleInfo.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(color));
                    this.mClockStyleInfo.setSecondaryBlendColor(color);
                    this.mClockStyleInfo.setAodSecondaryColor(color2);
                    this.mClockStyleInfo.setAodSecondaryBlendColor(color2);
                } else {
                    if (z) {
                        secondaryColor = (map == null || map.get("secondary30") == null) ? this.mClockStyleInfo.getSecondaryColor() : map.get("secondary30").intValue();
                    } else {
                        secondaryColor = -1;
                    }
                    this.mClockStyleInfo.setSecondaryColor(secondaryColor);
                    this.mClockStyleInfo.setAodSecondaryColor(GlobalColorUtils.transformAodColor(secondaryColor));
                    this.mClockStyleInfo.setAodSecondaryBlendColor(GlobalColorUtils.transformAodColor(secondaryColor));
                    this.mClockStyleInfo.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(secondaryColor));
                }
            }
            z3 = true;
        }
        if (this.mClockStyleInfo.isAutoPrimaryColor()) {
            MiuiClassicClockInfoBase miuiClassicClockInfoBase10 = this.mClockStyleInfo;
            if (z) {
                primaryColor2 = (map == null || map.get("secondary30") == null) ? this.mClockStyleInfo.getPrimaryColor() : map.get("secondary30").intValue();
            }
            miuiClassicClockInfoBase10.setOriginMagazineColor(primaryColor2);
        } else if (ClockEffectUtils.isBlurMixType(clockEffect)) {
            MiuiClassicClockInfoBase miuiClassicClockInfoBase11 = this.mClockStyleInfo;
            miuiClassicClockInfoBase11.setOriginMagazineColor(miuiClassicClockInfoBase11.getBlendColor());
        }
        MiuiClassicClockInfoBase miuiClassicClockInfoBase12 = this.mClockStyleInfo;
        miuiClassicClockInfoBase12.setAnimationPrimaryColor(miuiClassicClockInfoBase12.getPrimaryColor());
        MiuiClassicClockInfoBase miuiClassicClockInfoBase13 = this.mClockStyleInfo;
        miuiClassicClockInfoBase13.setAnimationSecondaryColor(miuiClassicClockInfoBase13.getSecondaryColor());
        updateColor(this.mClockStyleInfo);
        if (DeviceConfig.isHighTextContrastEnabled(this.mContext) || ClockStyleInfo.isAODType(this.mClockStyleInfo.getDisplayType())) {
            return;
        }
        updateClockEffectsContainer();
        this.mSignatureTextInEditor.setHintTextColor(this.mContext.getResources().getColor(z ? R.color.classic_color_black_40 : R.color.classic_color_white_60));
        if (ClockEffectUtils.isDifferenceType(clockEffect)) {
            MiuiBlurUtils.setDiffEffectContainer(this, this.mClockStyleInfo.isMultiWindowBlur());
            MiuiClassicClockInfoBase miuiClassicClockInfoBase14 = this.mClockStyleInfo;
            int fullAodBlendColor = zIsFullAODType ? miuiClassicClockInfoBase14.getFullAodBlendColor() : miuiClassicClockInfoBase14.getBlendColor();
            MiuiBlurUtils.setMemberDiffEffectColor(this.mTextArea, fullAodBlendColor);
            MiuiBlurUtils.setMemberDiffEffectColor(this.mTimeView, fullAodBlendColor);
            return;
        }
        if (!(ClockEffectUtils.isGlowType(clockEffect) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) && ClockEffectUtils.isBlurMixType(this.mClockStyleInfo.getClockEffect()) && zSupportBackgroundBlur) {
            MiuiClassicClockInfoBase miuiClassicClockInfoBase15 = this.mClockStyleInfo;
            int fullAodBlendColor2 = zIsFullAODType ? miuiClassicClockInfoBase15.getFullAodBlendColor() : miuiClassicClockInfoBase15.getBlendColor();
            MiuiClassicClockInfoBase miuiClassicClockInfoBase16 = this.mClockStyleInfo;
            int fullAodPrimaryColor = zIsFullAODType ? miuiClassicClockInfoBase16.getFullAodPrimaryColor() : miuiClassicClockInfoBase16.getPrimaryColor();
            if (!this.mClockStyleInfo.isDisableContainerPassBlur()) {
                MiuiBlurUtils.setContainerPassBlur(this, getDimen(R.dimen.miui_classic_plus_clock_blur_radius), this.mClockStyleInfo.isMultiWindowBlur());
            }
            MiuiBlurUtils.setMemberBlendColors(this.mTextArea, (zIsFullAODType || !z) ? false : z3, fullAodBlendColor2, fullAodPrimaryColor);
            MiuiTextGlassView miuiTextGlassView = this.mTimeView;
            if (zIsFullAODType || !z) {
                z3 = false;
            }
            MiuiBlurUtils.setMemberBlendColors(miuiTextGlassView, z3, fullAodBlendColor2, fullAodPrimaryColor);
        }
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        float classicAodFontWeightRadio;
        super.setClockStyleInfo(clockStyleInfo);
        if (!this.mHasPresetData && !this.mClockStyleInfo.isUnablePresetData()) {
            presetData(this.mClockStyleInfo.getWeatherJson(), this.mClockStyleInfo.getHealthJson());
        }
        queryDataAsync();
        clearColorEffect(false);
        if (isAODType()) {
            MiuiClassicClockInfoBase miuiClassicClockInfoBase = this.mClockStyleInfo;
            if (miuiClassicClockInfoBase.getClockWeight() <= 0) {
                classicAodFontWeightRadio = ClassicClockTimeUtils.getClassicAodFontWeightRadio() * 420.0f;
            } else {
                classicAodFontWeightRadio = ClassicClockTimeUtils.getClassicAodFontWeightRadio() * this.mClockStyleInfo.getClockWeight();
            }
            miuiClassicClockInfoBase.setClockWeight((int) classicAodFontWeightRadio);
        }
        ClassicClockTimeUtils.setClassicTimeStyle(this.mTimeView, this.mClockStyleInfo);
        if (this.mEditMode) {
            this.mSignatureText.setVisibility(8);
        } else {
            this.mSignatureText.setVisibility(0);
            this.mSignatureTextInEditor.setVisibility(8);
            onScreenSizeChange();
        }
        if (this.mClockStyleInfo.getClassicLine5() == 0 || isAODType()) {
            this.mSignatureText.setVisibility(8);
            this.mSignatureText.setText("");
        } else {
            String signature = this.mClockStyleInfo.getSignature();
            if (TextUtils.isEmpty(signature)) {
                this.mSignatureText.setVisibility(8);
            } else if (this.mEditMode) {
                this.mSignatureTextInEditor.setText(signature);
                checkAndCorrectSignatureText(this.mSignatureTextInEditor, this.mClockStyleInfo.getSignature(), 2);
                ClearableEditText clearableEditText = this.mSignatureTextInEditor;
                clearableEditText.setSelection(clearableEditText.getText().length());
            } else {
                this.mSignatureText.setVisibility(0);
                checkAndCorrectSignatureText(this.mSignatureText, signature, 2);
            }
            Log.d(this.TAG, "set signature=[" + signature + "]");
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
        if (isEditMode()) {
            this.mSignatureText.setVisibility(8);
            this.mSignatureTextInEditor.setVisibility(0);
            String signature = this.mClockStyleInfo.getSignature();
            initEditTextView(this.mSignatureTextInEditor, signature, 2);
            checkAndCorrectSignatureText(this.mSignatureTextInEditor, signature, 2);
        }
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

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setMagazineInfoVisible(boolean z) {
        super.setMagazineInfoVisible(z);
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
        MiuiTextGlassView miuiTextGlassView = this.mTimeView;
        MiuiClassicClockInfoBase miuiClassicClockInfoBase = this.mClockStyleInfo;
        ClockEffectUtils.setClockEffectsView(miuiTextGlassView, miuiClassicClockInfoBase, zUpdateTextDark, i, miuiClassicClockInfoBase.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        ClockEffectUtils.setClockEffectsView(this.mTextArea, this.mClockStyleInfo, zUpdateTextDark, i, 0, i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        if (!ClockEffectUtils.isBlurMixType(this.mClockStyleInfo.getClockEffect()) || isToFullAod()) {
            return;
        }
        MiuiBlurUtils.addMiBackgroundBlendColor(this.mTextArea, i3, 200);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockColor(int i, int i2) {
        super.updateClockColor(i, i2);
        this.mTimeView.setTextColor(i);
        this.mTextArea.setTextColor(i);
    }

    public final void updateClockContainerLayoutParams() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.anchor.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).height = 0;
        layoutParams.matchConstraintPercentHeight = ClassicClockTimeUtils.getClassicMaxOffsetRadio() * 2.0f;
        this.anchor.setLayoutParams(layoutParams);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockEffectsContainer() {
        super.updateClockEffectsContainer();
        updateViewBackgroundBlurContainer(this, getDimen(R.dimen.miui_classic_plus_clock_blur_radius), this.mTextArea, this.mTimeView);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView
    public void updateColor(ClockStyleInfo clockStyleInfo) {
        super.updateColor(clockStyleInfo);
        if (clockStyleInfo == null) {
            return;
        }
        int originMagazineColor = this.mClockStyleInfo.getOriginMagazineColor();
        if (originMagazineColor != 0) {
            int alphaComponent = ColorUtils.setAlphaComponent(originMagazineColor, 153);
            this.mSignatureText.setTextColor(alphaComponent);
            this.mSignatureTextInEditor.setTextColor(alphaComponent);
        }
        ClassicClockTimeUtils.setClassicTimeColor(this.mTimeView, this.mClockStyleInfo);
        updateTime();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView
    public void updateSignatureText() {
        super.updateSignatureText();
        this.mSignatureTextInEditor.setTextSize(0, getDimensionFloat(R.dimen.classic_signature_text_size));
        this.mSignatureTextInEditor.setTypeface(FontUtils.getMiSans(380));
        this.mSignatureTextInEditor.setMaxWidth(getDimen(R.dimen.classic_text_signature_max_width));
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        this.mTextArea.updateTime();
        onScreenSizeChange();
        this.mTimeView.setContentDescription(DateUtils.formatDateTime(getContext(), System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
    }
}
