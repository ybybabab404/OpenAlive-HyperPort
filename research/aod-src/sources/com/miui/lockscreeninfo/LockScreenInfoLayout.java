package com.miui.lockscreeninfo;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.Keep;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.lockscreeninfo.compat.ActivityManagerCompat;
import com.miui.lockscreeninfo.compat.ContentResolverCompat;
import com.miui.lockscreeninfo.compat.ContextCompat;
import com.miui.lockscreeninfo.compat.SettingsCompat;
import com.miui.lockscreeninfo.compat.UserHandleCompat;
import com.miui.lockscreeninfo.compat.ViewCompat;
import com.miui.lockscreeninfo.model.LockScreenInfoType;
import com.miui.lockscreeninfo.model.MagazineA;
import com.miui.lockscreeninfo.model.MagazineB;
import com.miui.lockscreeninfo.model.ModelFactory;
import com.miui.lockscreeninfo.model.SignatureInfo;
import com.miui.lockscreeninfo.utils.DeviceConfig;
import com.miui.lockscreeninfo.utils.GlobalColorUtils;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class LockScreenInfoLayout extends RelativeLayout {
    public boolean isSuperSaveOpen;
    public ContentObserver mAccessibilityHighTextContrastObserver;
    public boolean mAccessibilityHighTextEnabled;
    public View mBackgroundBlurContainer;
    public ContentObserver mBackgroundBlurEnableObserver;
    public boolean mBackgroundBlurEnabled;
    public ConstraintLayout mContainer;
    public Context mContext;
    public int mCurrentUserId;
    public int mEditorVersion;
    public final Handler mHandler;
    public final ContentObserver mListener;
    public SignatureInfo mModel;
    public boolean mNeedBuildFromSetting;
    public Map mPalette;
    public ContentObserver mSuperSaveOpenObserver;
    public boolean mTextDark;
    public UserHandle mUserAllHandle;
    public BroadcastReceiver mUserSwitchBroadcastReceiver;
    public BaseTextView signatureView;
    public SignatureView signatureViewMagazineA;
    public BaseTextView signatureViewMagazineB;

    /* JADX INFO: renamed from: com.miui.lockscreeninfo.LockScreenInfoLayout$4, reason: invalid class name */
    public class AnonymousClass4 extends ContentObserver {
        public static /* synthetic */ void $r8$lambda$fwFbsFuPqgvg_9kJBRDFxCXuBGg(AnonymousClass4 anonymousClass4) {
            anonymousClass4.getClass();
            Log.d("LockScreenInfoLayout", "Background Blur Enable Listener change");
            boolean zSupportBlur = LockScreenInfoLayout.this.supportBlur();
            if (LockScreenInfoLayout.this.mBackgroundBlurEnabled != zSupportBlur) {
                LockScreenInfoLayout.this.mBackgroundBlurEnabled = zSupportBlur;
                if (!LockScreenInfoLayout.this.mBackgroundBlurEnabled) {
                    LockScreenInfoLayout.this.clearEffect();
                }
                LockScreenInfoLayout lockScreenInfoLayout = LockScreenInfoLayout.this;
                lockScreenInfoLayout.setClockPalette(lockScreenInfoLayout.mTextDark, LockScreenInfoLayout.this.mPalette);
            }
        }

        public AnonymousClass4(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            LockScreenInfoLayout.this.mHandler.post(new Runnable() { // from class: com.miui.lockscreeninfo.LockScreenInfoLayout$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    LockScreenInfoLayout.AnonymousClass4.$r8$lambda$fwFbsFuPqgvg_9kJBRDFxCXuBGg(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.miui.lockscreeninfo.LockScreenInfoLayout$5, reason: invalid class name */
    public class AnonymousClass5 extends ContentObserver {
        /* JADX INFO: renamed from: $r8$lambda$3Bsafvk4-ykoB66I7lSFH9SGu2g, reason: not valid java name */
        public static /* synthetic */ void m953$r8$lambda$3Bsafvk4ykoB66I7lSFH9SGu2g(AnonymousClass5 anonymousClass5) {
            boolean highTextContrastEnabled = LockScreenInfoLayout.this.getHighTextContrastEnabled();
            if (LockScreenInfoLayout.this.mAccessibilityHighTextEnabled != highTextContrastEnabled) {
                LockScreenInfoLayout.this.mAccessibilityHighTextEnabled = highTextContrastEnabled;
                LockScreenInfoLayout lockScreenInfoLayout = LockScreenInfoLayout.this;
                lockScreenInfoLayout.onHighTextContrastEnabledChanged(lockScreenInfoLayout.mAccessibilityHighTextEnabled);
            }
        }

        public AnonymousClass5(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            LockScreenInfoLayout.this.mHandler.post(new Runnable() { // from class: com.miui.lockscreeninfo.LockScreenInfoLayout$5$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    LockScreenInfoLayout.AnonymousClass5.m953$r8$lambda$3Bsafvk4ykoB66I7lSFH9SGu2g(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.miui.lockscreeninfo.LockScreenInfoLayout$6, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass6 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$lockscreeninfo$model$LockScreenInfoType;

        static {
            int[] iArr = new int[LockScreenInfoType.values().length];
            $SwitchMap$com$miui$lockscreeninfo$model$LockScreenInfoType = iArr;
            try {
                iArr[LockScreenInfoType.BLUR_CONTAINER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$lockscreeninfo$model$LockScreenInfoType[LockScreenInfoType.SIGNATURE_TEXT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public LockScreenInfoLayout(Context context) {
        this(context, null);
    }

    public LockScreenInfoLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LockScreenInfoLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Handler handler = new Handler();
        this.mHandler = handler;
        this.mAccessibilityHighTextEnabled = false;
        this.mBackgroundBlurEnabled = false;
        this.isSuperSaveOpen = false;
        this.mTextDark = false;
        this.mNeedBuildFromSetting = false;
        this.mEditorVersion = -1;
        this.mListener = new ContentObserver(handler) { // from class: com.miui.lockscreeninfo.LockScreenInfoLayout.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                Log.i("LockScreenInfoLayout", "parentView is " + LockScreenInfoLayout.this.getParent());
                LockScreenInfoLayout.this.updateFromJson();
            }
        };
        initView(context);
    }

    private boolean getBackgroundBlurEnabled() {
        return (isSystemUI() ? SettingsCompat.Secure.getIntForUser(this.mContext.getContentResolver(), "background_blur_enable", 0, this.mCurrentUserId) : Settings.Secure.getInt(this.mContext.getContentResolver(), "background_blur_enable", 0)) == 1;
    }

    private String getConstantTemplateEditorInfo() {
        String stringForUser = SettingsCompat.Secure.getStringForUser(this.mContext.getContentResolver(), "constant_template_editor_info", this.mCurrentUserId);
        Log.d("LockScreenInfoLayout", "templateEditorInfo=" + stringForUser + ", mCurrentUserId = " + this.mCurrentUserId);
        return stringForUser;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getHighTextContrastEnabled() {
        return (isSystemUI() ? SettingsCompat.Secure.getIntForUser(this.mContext.getContentResolver(), "high_text_contrast_enabled", 0, this.mCurrentUserId) : Settings.Secure.getInt(this.mContext.getContentResolver(), "high_text_contrast_enabled", 0)) == 1;
    }

    private String getJson() {
        if (!isSystemUI()) {
            String string = Settings.Secure.getString(this.mContext.getContentResolver(), "constant_template_editor_info");
            if (string == null || string.isEmpty()) {
                return Settings.Secure.getString(this.mContext.getContentResolver(), "constant_template_editor_info");
            }
            Pair lockscreenInfo = getLockscreenInfo(string);
            this.mEditorVersion = ((Integer) lockscreenInfo.first).intValue();
            return (String) lockscreenInfo.second;
        }
        String constantTemplateEditorInfo = getConstantTemplateEditorInfo();
        if (constantTemplateEditorInfo != null && !constantTemplateEditorInfo.isEmpty()) {
            Pair lockscreenInfo2 = getLockscreenInfo(constantTemplateEditorInfo);
            this.mEditorVersion = ((Integer) lockscreenInfo2.first).intValue();
            return (String) lockscreenInfo2.second;
        }
        String stringForUser = SettingsCompat.Secure.getStringForUser(this.mContext.getContentResolver(), "constant_lockscreen_info", this.mCurrentUserId);
        Log.i("LockScreenInfoLayout", "getStringForUser, mCurrentUserId = " + this.mCurrentUserId);
        return stringForUser;
    }

    private void setModelFromJson(String str) throws JSONException {
        String str2;
        int i;
        float f;
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject jSONObject2 = new JSONObject(jSONObject.getString("signatureInfo"));
            String string = jSONObject2.getString("templateId");
            String string2 = jSONObject2.getString("content");
            int i2 = jSONObject2.getInt("primaryColor");
            int i3 = jSONObject2.getInt("alignment");
            boolean z = jSONObject2.getBoolean("isAutoPrimaryColor");
            Log.d("LockScreenInfoLayout", "getClockBeanFromSetting: version is= " + (jSONObject.has("version") ? jSONObject.getInt("version") : -1));
            if (jSONObject2.has("clockEffect")) {
                i = jSONObject2.getInt("clockEffect");
            } else {
                i = (z && getBackgroundBlurEnabled()) ? 1 : 0;
            }
            int i4 = jSONObject2.has("templateFontType") ? jSONObject2.getInt("templateFontType") : 0;
            float f2 = jSONObject2.has("textSize") ? (float) jSONObject2.getDouble("textSize") : 230.0f;
            if (jSONObject2.has("letterSpaceValue")) {
                str2 = "LockScreenInfoLayout";
                try {
                    f = (float) jSONObject2.getDouble("letterSpaceValue");
                } catch (Exception e) {
                    e = e;
                    Log.e(str2, "setModelFromJson: error " + e.getMessage());
                    return;
                }
            } else {
                str2 = "LockScreenInfoLayout";
                f = 0.001f;
            }
            float f3 = jSONObject2.has("lineSpaceValue") ? (float) jSONObject2.getDouble("lineSpaceValue") : 0.0f;
            int i5 = jSONObject2.has("blendColor") ? jSONObject2.getInt("blendColor") : -1;
            SignatureInfo signatureInfoCreateModel = ModelFactory.createModel(string);
            signatureInfoCreateModel.setContent(string2);
            signatureInfoCreateModel.setAlignment(i3);
            signatureInfoCreateModel.setPrimaryColor(i2);
            signatureInfoCreateModel.setAutoPrimaryColor(z);
            signatureInfoCreateModel.setClockEffect(i);
            signatureInfoCreateModel.setBlendColor(i5);
            signatureInfoCreateModel.setTemplateFontType(i4);
            signatureInfoCreateModel.setTextsize(f2);
            signatureInfoCreateModel.setLetterSpaceValue(f);
            signatureInfoCreateModel.setLineSpaceValue(f3);
            setModel(signatureInfoCreateModel);
            Log.i(str2, "setModelFromJson: " + str);
        } catch (Exception e2) {
            e = e2;
            str2 = "LockScreenInfoLayout";
        }
    }

    @Keep
    public void buildFromSetting() {
        this.mNeedBuildFromSetting = true;
        registerModelListener();
        updateFromJson();
    }

    public final void clear() {
        this.mModel = null;
        this.signatureView.setText("");
    }

    public void clearEffect() {
        ClockEffectUtils.clearClockEffectsContainer(this, this.mModel, true);
        ClockEffectUtils.clearClockEffectsContainer(this.mContainer, this.mModel, true);
        ClockEffectUtils.clearClockEffectsView(this.mContainer, this.mModel, true);
    }

    public final void fullAodPrimaryColorToFullAod() {
        SignatureInfo signatureInfo = this.mModel;
        signatureInfo.setAodPrimaryColor(signatureInfo.getFullAodPrimaryColor());
        SignatureInfo signatureInfo2 = this.mModel;
        signatureInfo2.setAodBlendColor(signatureInfo2.getFullAodBlendColor());
    }

    public View getBackgroundBlurContainer() {
        View view = this.mBackgroundBlurContainer;
        return view == null ? this : view;
    }

    public final int getDimen(int i) {
        Context applicationContext = this.mContext.getApplicationContext() == null ? this.mContext : this.mContext.getApplicationContext();
        return Math.round(applicationContext.getResources().getDimensionPixelSize(i) * VerticalTextViewUtils.calculateScale(applicationContext));
    }

    public View getIView(LockScreenInfoType lockScreenInfoType) {
        int i = AnonymousClass6.$SwitchMap$com$miui$lockscreeninfo$model$LockScreenInfoType[lockScreenInfoType.ordinal()];
        if (i != 1) {
            return i != 2 ? this : this.signatureView;
        }
        return this.mContainer;
    }

    public float getInitTextSize() {
        return this.signatureView.getInitTextSize();
    }

    public float getLetterSpaceValue() {
        return this.mModel.isVerticalMode() ? ((SignatureView) getSignatureView()).getCJKVerticalSpacingMultiplier() : ((BaseTextView) getSignatureView()).getLetterSpacing();
    }

    public float getLineSpaceValue() {
        return ((BaseTextView) getSignatureView()).getLineSpacingExtra();
    }

    public final Pair getLockscreenInfo(String str) {
        int i;
        String string = "";
        try {
            JSONObject jSONObject = new JSONObject(str);
            string = jSONObject.getString("lockscreenInfo");
            i = jSONObject.getInt("version");
        } catch (Exception e) {
            Log.d("LockScreenInfoLayout", "getClockInfo error, return!", e);
            i = -1;
        }
        return Pair.create(Integer.valueOf(i), string);
    }

    public int getMaxTextSize() {
        return this.signatureView.getMaxTextSize();
    }

    public SignatureInfo getModel() {
        return this.mModel;
    }

    public View getSignatureContainer() {
        return getIView(LockScreenInfoType.BLUR_CONTAINER);
    }

    public View getSignatureView() {
        return this.signatureView;
    }

    public float getTextsize() {
        return ((BaseTextView) getSignatureView()).getTextSize();
    }

    public final void initView(Context context) {
        this.mContext = context;
        this.signatureViewMagazineA = new SignatureView(context);
        this.signatureViewMagazineB = new BaseTextView(context);
        this.signatureViewMagazineA.setIsSystemUI(isSystemUI());
        this.signatureViewMagazineB.setIsSystemUI(isSystemUI());
        this.mContainer = new ConstraintLayout(context);
        setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.signatureView = this.signatureViewMagazineA;
        this.mContainer.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        this.mContainer.addView(this.signatureView);
        addView(this.mContainer);
        if (isSystemUI()) {
            updateCurrentUserId();
        }
        updateSuperSaveOpen();
    }

    public final boolean isAOD() {
        return "com.miui.aod".equals(this.mContext.getPackageName());
    }

    public final boolean isCrossUser() {
        return isSystemUI() || isAOD();
    }

    public final boolean isSystemUI() {
        return "com.android.systemui".equals(this.mContext.getPackageName());
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isSystemUI()) {
            updateCurrentUserId();
        }
        updateSuperSaveOpen();
        registerContentObserverListener();
        if (this.mNeedBuildFromSetting) {
            registerModelListener();
        }
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mModel != null) {
            Log.i("LockScreenInfoLayout", "onConfigurationChanged");
            updateLayoutParams();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeContentObserverListener();
        if (this.mNeedBuildFromSetting) {
            unregisterModelListener();
        }
    }

    public void onHighTextContrastEnabledChanged(boolean z) {
        if (!z) {
            setClockPalette(this.mTextDark, this.mPalette);
            return;
        }
        clearEffect();
        SignatureInfo signatureInfo = this.mModel;
        if (signatureInfo != null) {
            if (ClockEffectUtils.isBlurMixType(signatureInfo.getClockEffect()) && !this.mModel.isAutoPrimaryColor()) {
                SignatureInfo signatureInfo2 = this.mModel;
                signatureInfo2.setPrimaryColor(signatureInfo2.getBlendColor());
            }
            updateColor();
        }
    }

    public final void registerAccessibilityHighTextContrastListener() {
        this.mAccessibilityHighTextEnabled = getHighTextContrastEnabled();
        if (this.mAccessibilityHighTextContrastObserver == null) {
            this.mAccessibilityHighTextContrastObserver = new AnonymousClass5(this.mHandler);
        }
        updateCurrentUserId();
        UserHandle userHandle = null;
        try {
            userHandle = (UserHandle) UserHandle.class.getConstructor(Integer.TYPE).newInstance(UserHandle.class.getField("USER_ALL").get(null));
        } catch (Exception e) {
            Log.e("LockScreenInfoLayout", "constructor user handler fail", e);
        }
        if (!isCrossUser() || userHandle == null) {
            this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("high_text_contrast_enabled"), false, this.mAccessibilityHighTextContrastObserver);
        } else {
            try {
                ContentResolver.class.getMethod("registerContentObserverAsUser", Uri.class, Boolean.TYPE, ContentObserver.class, UserHandle.class).invoke(this.mContext.getContentResolver(), Settings.Secure.getUriFor("high_text_contrast_enabled"), Boolean.FALSE, this.mAccessibilityHighTextContrastObserver, userHandle);
            } catch (Exception e2) {
                Log.e("LockScreenInfoLayout", "register content observer as user fail", e2);
            }
        }
        this.mHandler.post(new Runnable() { // from class: com.miui.lockscreeninfo.LockScreenInfoLayout$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mAccessibilityHighTextContrastObserver.onChange(false);
            }
        });
    }

    public final void registerBackgroundBlurEnableListener() {
        if (this.mBackgroundBlurEnableObserver == null) {
            this.mBackgroundBlurEnableObserver = new AnonymousClass4(this.mHandler);
        }
        updateCurrentUserId();
        UserHandle userHandle = null;
        try {
            userHandle = (UserHandle) UserHandle.class.getConstructor(Integer.TYPE).newInstance(UserHandle.class.getField("USER_ALL").get(null));
        } catch (Exception e) {
            Log.e("LockScreenInfoLayout", "constructor user handler fail", e);
        }
        if (!isCrossUser() || userHandle == null) {
            this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("background_blur_enable"), false, this.mBackgroundBlurEnableObserver);
        } else {
            try {
                ContentResolver.class.getMethod("registerContentObserverAsUser", Uri.class, Boolean.TYPE, ContentObserver.class, UserHandle.class).invoke(this.mContext.getContentResolver(), Settings.Secure.getUriFor("background_blur_enable"), Boolean.FALSE, this.mBackgroundBlurEnableObserver, userHandle);
            } catch (Exception e2) {
                Log.e("LockScreenInfoLayout", "register content observer as user fail", e2);
            }
        }
        this.mBackgroundBlurEnabled = supportBlur();
    }

    public final void registerContentObserverListener() {
        if (this.mSuperSaveOpenObserver == null) {
            this.mSuperSaveOpenObserver = new ContentObserver(new Handler()) { // from class: com.miui.lockscreeninfo.LockScreenInfoLayout.3
                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                    LockScreenInfoLayout.this.updateSuperSaveOpen();
                    Log.i("LockScreenInfoLayout", "isSuperSaveOpen on change " + LockScreenInfoLayout.this.isSuperSaveOpen);
                    if (LockScreenInfoLayout.this.signatureView == null || LockScreenInfoLayout.this.mModel == null) {
                        return;
                    }
                    LockScreenInfoLayout.this.signatureView.setSuperSaveOpen(LockScreenInfoLayout.this.isSuperSaveOpen);
                    LockScreenInfoLayout.this.signatureView.setIsSystemUI(LockScreenInfoLayout.this.isSystemUI());
                    LockScreenInfoLayout.this.signatureView.init(LockScreenInfoLayout.this.mModel);
                }
            };
        }
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("power_supersave_mode_open"), false, this.mSuperSaveOpenObserver);
        this.mSuperSaveOpenObserver.onChange(false);
        registerAccessibilityHighTextContrastListener();
        registerBackgroundBlurEnableListener();
    }

    @Keep
    public void registerModelListener() {
        registerModelListener(isSystemUI());
    }

    public void registerModelListener(boolean z) {
        Log.i("LockScreenInfoLayout", "registerClockBeanListener isSystemUI = " + z);
        if (!z) {
            this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("constant_template_editor_info"), false, this.mListener);
            return;
        }
        this.mUserSwitchBroadcastReceiver = new BroadcastReceiver() { // from class: com.miui.lockscreeninfo.LockScreenInfoLayout.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent != null && "android.intent.action.USER_SWITCHED".equals(intent.getAction())) {
                    LockScreenInfoLayout.this.mCurrentUserId = intent.getIntExtra("android.intent.extra.user_handle", 0);
                    Log.i("LockScreenInfoLayout", "onReceive User Switch Broadcast mCurrentUserId = " + LockScreenInfoLayout.this.mCurrentUserId);
                    LockScreenInfoLayout.this.updateFromJson();
                }
            }
        };
        updateCurrentUserId();
        UserHandle userHandleNewInstance = UserHandleCompat.newInstance(-1);
        this.mUserAllHandle = userHandleNewInstance;
        ContextCompat.registerReceiverAsUser(this.mContext, this.mUserSwitchBroadcastReceiver, userHandleNewInstance, new IntentFilter("android.intent.action.USER_SWITCHED"), null, null);
        ContentResolverCompat.registerContentObserver(this.mContext.getContentResolver(), Settings.Secure.getUriFor("constant_template_editor_info"), false, this.mListener, this.mUserAllHandle);
    }

    public final void removeAccessibilityHighTextContrastListener() {
        if (this.mAccessibilityHighTextContrastObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mAccessibilityHighTextContrastObserver);
        }
    }

    public final void removeBackgroundBlurEnableListener() {
        if (this.mBackgroundBlurEnableObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mBackgroundBlurEnableObserver);
        }
    }

    public final void removeContentObserverListener() {
        if (this.mSuperSaveOpenObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mSuperSaveOpenObserver);
        }
        removeAccessibilityHighTextContrastListener();
        removeBackgroundBlurEnableListener();
    }

    public void setBackgroundBlurContainer(View view) {
        this.mBackgroundBlurContainer = view;
    }

    @Keep
    public void setClockPalette(boolean z, Map<String, Integer> map) {
        Integer num;
        this.mTextDark = z;
        this.mPalette = map;
        SignatureInfo signatureInfo = this.mModel;
        if (signatureInfo == null) {
            Log.e("LockScreenInfoLayout", "mModel is " + this.mModel);
            return;
        }
        signatureInfo.setTextDark(z);
        clearEffect();
        boolean zSupportBlur = supportBlur();
        if (map == null) {
            updateColor();
            if (!zSupportBlur || ClockEffectUtils.isSolidType(this.mModel.getClockEffect())) {
                return;
            }
            updateClockBlendColor(this.mModel.getBlendColor());
            return;
        }
        if (ClockEffectUtils.isSolidType(this.mModel.getClockEffect()) || getHighTextContrastEnabled()) {
            Integer numValueOf = Integer.valueOf(this.mModel.getPrimaryColor());
            if (this.mModel.isAutoPrimaryColor()) {
                Integer num2 = z ? map.get("secondary30") : -1;
                int iIntValue = num2 != null ? num2.intValue() : -1;
                numValueOf = Integer.valueOf(iIntValue);
                this.mModel.setPrimaryColor(iIntValue);
            }
            this.mModel.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(numValueOf.intValue()));
            fullAodPrimaryColorToFullAod();
            updateColor();
            Log.e("LockScreenInfoLayout", "isAutoPrimaryColor =" + this.mModel.isAutoPrimaryColor() + " getHighTextContrastEnabled = " + getHighTextContrastEnabled() + " PrimaryColor = " + String.format("#%08X", Integer.valueOf(this.mModel.getPrimaryColor())));
            return;
        }
        if (!zSupportBlur) {
            Integer numValueOf2 = Integer.valueOf(this.mModel.getBlendColor());
            if (this.mModel.isAutoPrimaryColor()) {
                if (ClockEffectUtils.isDifferenceType(this.mModel.getClockEffect())) {
                    num = z ? map.get("secondary15") : map.get("secondary85");
                } else {
                    num = z ? map.get("secondary30") : -1;
                }
                numValueOf2 = Integer.valueOf(num != null ? num.intValue() : -1);
            }
            this.mModel.setPrimaryColor(numValueOf2.intValue());
            this.mModel.setBlendColor(numValueOf2.intValue());
            this.mModel.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(numValueOf2.intValue()));
            SignatureInfo signatureInfo2 = this.mModel;
            signatureInfo2.setFullAodBlendColor(signatureInfo2.getFullAodPrimaryColor());
        } else if (ClockEffectUtils.isDifferenceType(this.mModel.getClockEffect())) {
            if (this.mModel.isAutoPrimaryColor()) {
                Integer num3 = z ? map.get("secondary15") : map.get("secondary85");
                this.mModel.setPrimaryColor(num3 != null ? num3.intValue() : -1);
                SignatureInfo signatureInfo3 = this.mModel;
                signatureInfo3.setBlendColor(signatureInfo3.getPrimaryColor());
            }
            SignatureInfo signatureInfo4 = this.mModel;
            signatureInfo4.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(signatureInfo4.getPrimaryColor()));
            SignatureInfo signatureInfo5 = this.mModel;
            signatureInfo5.setFullAodBlendColor(signatureInfo5.getFullAodPrimaryColor());
        } else if (ClockEffectUtils.isBlurMixType(this.mModel.getClockEffect())) {
            this.mModel.setPrimaryColor(-1);
            this.mModel.setFullAodPrimaryColor(Color.parseColor("#808080"));
            if (this.mModel.isAutoPrimaryColor()) {
                Integer numValueOf3 = z ? map.get("neutral-variant30") : Integer.valueOf(Color.parseColor("#FFABABAB"));
                this.mModel.setBlendColor(numValueOf3 != null ? numValueOf3.intValue() : -1);
                this.mModel.setFullAodBlendColor(Color.parseColor("#ABABAB"));
            } else {
                SignatureInfo signatureInfo6 = this.mModel;
                signatureInfo6.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(signatureInfo6.getBlendColor()));
            }
        }
        fullAodPrimaryColorToFullAod();
        updateColor();
        if (zSupportBlur) {
            updateClockBlendColor(SignatureInfo.isFullAODType(this.mModel.getDisplayType()) ? this.mModel.getFullAodBlendColor() : this.mModel.getBlendColor());
        }
        Log.i("LockScreenInfoLayout", "setClockPalette: blendColor = " + String.format("#%08X", Integer.valueOf(this.mModel.getBlendColor())) + ",aodBlendColor = " + String.format("#%08X", Integer.valueOf(this.mModel.getAodBlendColor())) + ",textDark = " + z + ",PrimaryColor = " + String.format("#%08X", Integer.valueOf(this.mModel.getPrimaryColor())) + ",Properties = " + VerticalTextViewUtils.BACKGROUND_BLUR_SUPPORTED + ",Settings = " + getBackgroundBlurEnabled() + ",DeviceBlurEnabled= " + VerticalTextViewUtils.deviceBlurEnabled() + ",clockEffect = " + this.mModel.getClockEffect());
    }

    public final RelativeLayout.LayoutParams setLayout(MagazineA magazineA) {
        if (!magazineA.isVerticalMode()) {
            setPadding(0, getDimen(R.dimen.layout_padding_top_a_h), 0, 0);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(getDimen(R.dimen.width_a_h), getDimen(R.dimen.height_a));
            this.signatureView.setPadding(getDimen(R.dimen.layout_padding_left_a_h), 0, getDimen(R.dimen.layout_padding_right_a_h), 0);
            return layoutParams;
        }
        int i = R.dimen.layout_padding_right_a;
        int dimen = getDimen(i);
        setPadding(0, getDimen(R.dimen.layout_padding_top_a) - dimen, 0, 0);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(getDimen(R.dimen.width_a), getDimen(R.dimen.height_a) + dimen);
        if (magazineA.getAlignment() == 101) {
            this.signatureView.setPadding(getDimen(i), dimen, 0, 0);
            return layoutParams2;
        }
        if (magazineA.getAlignment() == 102) {
            this.signatureView.setPadding(0, dimen, getDimen(i), 0);
        }
        return layoutParams2;
    }

    public final RelativeLayout.LayoutParams setLayout(MagazineB magazineB) {
        setGravity(1);
        if (VerticalTextViewUtils.isL18Externalscreen(this.mContext)) {
            setPadding(0, getDimen(R.dimen.layout_padding_top_b_l18), 0, 0);
        } else if (DeviceConfig.isLongScreenDevice()) {
            setPadding(0, getDimen(R.dimen.layout_padding_top_b_long_screen), 0, 0);
        } else {
            setPadding(0, getDimen(R.dimen.layout_padding_top_b), 0, 0);
        }
        this.signatureView.setPadding(0, 0, 0, magazineB.getTemplateFontType() == 0 ? getDimen(R.dimen.padding_bottom_b) : 0);
        return new RelativeLayout.LayoutParams(getDimen(R.dimen.width_b), getDimen(R.dimen.height_b));
    }

    @Keep
    @RequiresApi
    public void setModel(SignatureInfo signatureInfo) {
        if (signatureInfo == null) {
            clear();
            Log.e("LockScreenInfoLayout", "SignatureInfo is null");
        } else {
            this.mModel = signatureInfo;
            updateLayoutParams();
            this.signatureView.setSuperSaveOpen(this.isSuperSaveOpen);
            setClockPalette(this.mTextDark, this.mPalette);
        }
    }

    public boolean supportBlur() {
        return VerticalTextViewUtils.BACKGROUND_BLUR_SUPPORTED && getBackgroundBlurEnabled() && VerticalTextViewUtils.deviceBlurEnabled() && !this.isSuperSaveOpen;
    }

    @Keep
    public void unregisterModelListener() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mListener);
        BroadcastReceiver broadcastReceiver = this.mUserSwitchBroadcastReceiver;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
        }
    }

    public final void updateClockBlendColor(int i) {
        if (this.mModel == null) {
            Log.e("LockScreenInfoLayout", "updateClockBlendColor mModel is " + ((Object) null));
            return;
        }
        if (supportBlur()) {
            int clockEffect = this.mModel.getClockEffect();
            if (this.mBackgroundBlurContainer == null) {
                Log.w("LockScreenInfoLayout", "not blend background view container");
                ClockEffectUtils.setClockEffectsContainer(this, getDimen(R.dimen.blur_radius), this.mModel, false);
            } else {
                ClockEffectUtils.clearClockEffectsContainer(this, this.mModel, true);
                ViewCompat.chooseBackgroundBlurContainer(this.mContainer, this.mBackgroundBlurContainer);
            }
            if (ClockEffectUtils.isDifferenceType(this.mModel.getClockEffect())) {
                updateDiffEffectColor(i, 0);
            } else if (ClockEffectUtils.isBlurMixType(clockEffect)) {
                updateClockBlendColor(i, SignatureInfo.isFullAODType(this.mModel.getDisplayType()) ? this.mModel.getFullAodPrimaryColor() : this.mModel.getPrimaryColor(), false, 255);
            }
        }
    }

    public void updateClockBlendColor(int i, int i2, boolean z, int i3) {
        if (this.mModel == null || !supportBlur() || getHighTextContrastEnabled()) {
            Log.d("LockScreenInfoLayout", "updateClockBlendColor supportBlur？ ");
            return;
        }
        if (!this.mModel.isTextDark()) {
            i3 = 255;
        }
        VerticalTextViewUtils.setMemberBlendColor(this.mContainer, (SignatureInfo.isFullAODType(this.mModel.getDisplayType()) || z || !this.mModel.isTextDark()) ? false : true, i, 255, i2, i3);
    }

    public final void updateColor() {
        SignatureInfo signatureInfo;
        if (this.signatureView == null || (signatureInfo = this.mModel) == null) {
            return;
        }
        this.signatureView.setTextColor(SignatureInfo.isFullAODType(signatureInfo.getDisplayType()) ? this.mModel.getFullAodPrimaryColor() : this.mModel.getPrimaryColor());
    }

    public final void updateCurrentUserId() {
        this.mCurrentUserId = ActivityManagerCompat.getCurrentUser();
    }

    public void updateDiffEffectColor(int i, int i2) {
        if (this.mModel == null || !supportBlur() || getHighTextContrastEnabled()) {
            Log.d("LockScreenInfoLayout", "updateDiffEffectColor supportBlur?");
        } else {
            this.signatureView.setTextColor(i);
            VerticalTextViewUtils.setMemberDiffEffectColor(this.mContainer, i, i2);
        }
    }

    public final void updateFromJson() {
        try {
            String json = getJson();
            if (!TextUtils.isEmpty(json)) {
                setModelFromJson(json);
            } else {
                clear();
                Log.e("LockScreenInfoLayout", "ContentObserver fail, constant_template_editor_info value is Empty");
            }
        } catch (Exception e) {
            clear();
            Log.e("LockScreenInfoLayout", "ContentObserver fail, msg = " + e);
        }
    }

    public final void updateLayoutParams() {
        SignatureInfo signatureInfo = this.mModel;
        if (signatureInfo == null) {
            return;
        }
        switch (signatureInfo.getAlignment()) {
            case 101:
            case 103:
                setGravity(3);
                break;
            case 102:
            case com.miui.maml.R.styleable.AppCompatTheme_textAppearanceListItemSmall /* 105 */:
                setGravity(5);
                break;
            case 104:
            case com.miui.maml.R.styleable.AppCompatTheme_textAppearancePopupMenuHeader /* 106 */:
                setGravity(1);
                break;
        }
        if (this.mModel instanceof MagazineA) {
            this.mContainer.removeView(this.signatureView);
            this.signatureView = this.signatureViewMagazineA;
            this.signatureView.setLayoutParams(setLayout((MagazineA) this.mModel));
            this.mContainer.addView(this.signatureView);
        }
        if (this.mModel instanceof MagazineB) {
            this.mContainer.removeView(this.signatureView);
            this.signatureView = this.signatureViewMagazineB;
            this.signatureView.setLayoutParams(setLayout((MagazineB) this.mModel));
            this.mContainer.addView(this.signatureView);
        }
        this.signatureView.init(this.mModel);
    }

    public final void updateSuperSaveOpen() {
        boolean z = Settings.System.getInt(this.mContext.getContentResolver(), "power_supersave_mode_open", 0) != 0;
        this.signatureView.setSuperSaveOpen(z);
        if (z && !this.isSuperSaveOpen && this.signatureView != null) {
            Log.i("LockScreenInfoLayout", "updateSuperSaveOpen on change, update Color ");
            clearEffect();
            this.signatureView.setTextColor(-1);
        }
        this.isSuperSaveOpen = z;
    }
}
