package com.miui.keyguard.shortcuts;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.RemoteAnimationTarget;
import android.view.ViewGroup;
import com.android.systemui.plugins.annotations.Requires;
import com.android.systemui.plugins.miui.keyguard.MiuiShortcutPlugin;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import com.miui.keyguard.shortcuts.analy.ShortcutAnalyticManager;
import com.miui.keyguard.shortcuts.controller.ShortcutMoveController;
import com.miui.keyguard.shortcuts.controller.ShortcutUnoccludedAnimController;
import com.miui.keyguard.shortcuts.controller.ShortcutViewLayoutController;
import com.miui.keyguard.shortcuts.data.ShortcutEntity;
import com.miui.keyguard.shortcuts.manager.DeviceFoldStateManager;
import com.miui.keyguard.shortcuts.manager.MiuiShortcutManager;
import com.miui.keyguard.shortcuts.manager.ShortcutManager;
import com.miui.keyguard.shortcuts.manager.ShortcutWindowManager;
import com.miui.keyguard.shortcuts.manager.UserSwitchManager;
import com.miui.keyguard.shortcuts.utils.DeviceHelper;
import com.miui.keyguard.shortcuts.utils.HapticFeedbackUtils;
import com.miui.keyguard.shortcuts.utils.LogUtils;
import com.miui.keyguard.shortcuts.utils.ViewUtils;
import com.xiaomi.onetrack.OneTrack;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: ShortcutPluginImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Requires(target = MiuiShortcutPlugin.class, version = 1)
@Metadata
@SourceDebugExtension
public final class ShortcutPluginImpl implements MiuiShortcutPlugin {
    public static final Companion Companion = new Companion(null);
    public Context pluginContext;
    public PowerManager powerManager;
    public ViewGroup shortcutContentView;
    public ViewGroup shortcutContentViewForNormal;
    public ShortcutMoveController shortcutMoveController;
    public MiuiShortcutPlugin.ShortcutPluginCallback shortcutPluginCallback;
    public ShortcutViewLayoutController shortcutViewLayoutController;
    public Context sysuiContext;
    public ShortcutUnoccludedAnimController unoccludedAnimController;
    public final Object syncUnoccludedAnimObject = new Object();
    public final Configuration configuration = new Configuration();
    public final ShortcutPluginImpl$shortcutMoveControllerCallback$1 shortcutMoveControllerCallback = new ShortcutPluginImpl$shortcutMoveControllerCallback$1(this);
    public final ShortcutPluginImpl$unoccludedAnimControllerCallback$1 unoccludedAnimControllerCallback = new ShortcutUnoccludedAnimController.Callback() { // from class: com.miui.keyguard.shortcuts.ShortcutPluginImpl$unoccludedAnimControllerCallback$1
        @Override // com.miui.keyguard.shortcuts.controller.ShortcutUnoccludedAnimController.Callback
        public float getBouncerWallpaperPreviewAlpha() {
            MiuiShortcutPlugin.ShortcutPluginCallback shortcutPluginCallback = this.this$0.shortcutPluginCallback;
            Object objOnShortcutPluginCallback$default = shortcutPluginCallback != null ? MiuiShortcutPlugin.ShortcutPluginCallback.onShortcutPluginCallback$default(shortcutPluginCallback, "getBouncerPreviewAlpha", (Bundle) null, 2, (Object) null) : null;
            if (objOnShortcutPluginCallback$default instanceof Float) {
                return ((Number) objOnShortcutPluginCallback$default).floatValue();
            }
            return 0.0f;
        }

        @Override // com.miui.keyguard.shortcuts.controller.ShortcutUnoccludedAnimController.Callback
        public void onUnoccludedAnimationEnd() {
            this.this$0.resetShortcutViewVisibility();
            MiuiShortcutManager miuiShortcutManager = MiuiShortcutManager.INSTANCE;
            miuiShortcutManager.resetOccludedMovedShortcut();
            MiuiShortcutPlugin.ShortcutPluginCallback shortcutPluginCallback = this.this$0.shortcutPluginCallback;
            if (shortcutPluginCallback != null) {
                MiuiShortcutPlugin.ShortcutPluginCallback.onShortcutPluginCallback$default(shortcutPluginCallback, "onUnoccludedAnimationEnd", (Bundle) null, 2, (Object) null);
            }
            this.this$0.shortcutMoveControllerCallback.updateCanShowBiometricAuthentication(miuiShortcutManager.shouldShowFingerprintOrFaceIcon(), miuiShortcutManager.shouldRequestFaceAuth());
        }

        @Override // com.miui.keyguard.shortcuts.controller.ShortcutUnoccludedAnimController.Callback
        public void onUnoccludedAnimationStart() {
            String shortcutTrackName;
            MiuiShortcutManager miuiShortcutManager = MiuiShortcutManager.INSTANCE;
            if (miuiShortcutManager.isOccludedMovedShortcutLeft()) {
                shortcutTrackName = miuiShortcutManager.getShortcutDataLeft().getShortcutTrackName();
            } else {
                shortcutTrackName = miuiShortcutManager.isOccludedMovedShortcutRight() ? miuiShortcutManager.getShortcutDataRight().getShortcutTrackName() : "";
            }
            Bundle bundle = new Bundle();
            bundle.putString("occludedShortcutTrackName", shortcutTrackName);
            MiuiShortcutPlugin.ShortcutPluginCallback shortcutPluginCallback = this.this$0.shortcutPluginCallback;
            if (shortcutPluginCallback != null) {
                shortcutPluginCallback.onShortcutPluginCallback("onUnoccludedAnimationStart", bundle);
            }
        }
    };
    public final DeviceFoldStateManager.FoldCallback foldStateListener = new DeviceFoldStateManager.FoldCallback() { // from class: com.miui.keyguard.shortcuts.ShortcutPluginImpl$$ExternalSyntheticLambda0
        @Override // com.miui.keyguard.shortcuts.manager.DeviceFoldStateManager.FoldCallback
        public final void onFoldUpdated(boolean z) throws IOException {
            this.f$0.onFoldStateChanged(z);
        }
    };

    /* JADX INFO: compiled from: ShortcutPluginImpl.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public boolean checkOccludedRemoteAnimation(RemoteAnimationTarget[] targetApps, IRemoteAnimationFinishedCallback finishedCallback) {
        ShortcutMoveController shortcutMoveController;
        Intrinsics.checkNotNullParameter(targetApps, "targetApps");
        Intrinsics.checkNotNullParameter(finishedCallback, "finishedCallback");
        if (!needPlayOccludedAnimation() || (shortcutMoveController = this.shortcutMoveController) == null) {
            return false;
        }
        return shortcutMoveController.checkOccludedRemoteAnimation(targetApps, finishedCallback);
    }

    public boolean checkUnoccludedRemoteAnimation(RemoteAnimationTarget[] targetApps, IRemoteAnimationFinishedCallback finishedCallback) {
        ShortcutUnoccludedAnimController shortcutUnoccludedAnimController;
        Intrinsics.checkNotNullParameter(targetApps, "targetApps");
        Intrinsics.checkNotNullParameter(finishedCallback, "finishedCallback");
        if (!needPlayUnoccludedAnimation() || (shortcutUnoccludedAnimController = this.unoccludedAnimController) == null) {
            return false;
        }
        return shortcutUnoccludedAnimController.checkUnoccludedRemoteAnimation(targetApps, finishedCallback);
    }

    public final Context getPluginContext() {
        Context context = this.pluginContext;
        if (context != null) {
            return context;
        }
        Intrinsics.throwUninitializedPropertyAccessException("pluginContext");
        return null;
    }

    public ViewGroup getShortcutContentView() {
        return this.shortcutContentView;
    }

    public final Context getSysuiContext() {
        Context context = this.sysuiContext;
        if (context != null) {
            return context;
        }
        Intrinsics.throwUninitializedPropertyAccessException("sysuiContext");
        return null;
    }

    public final void inflateShortcutViews() {
        ViewGroup shortcutContentView = getShortcutContentView();
        if (shortcutContentView != null) {
            ShortcutViewLayoutController shortcutViewLayoutController = this.shortcutViewLayoutController;
            if (shortcutViewLayoutController != null) {
                shortcutViewLayoutController.release();
            }
            ShortcutMoveController shortcutMoveController = this.shortcutMoveController;
            if (shortcutMoveController != null) {
                shortcutMoveController.destroy();
            }
            ShortcutUnoccludedAnimController shortcutUnoccludedAnimController = this.unoccludedAnimController;
            if (shortcutUnoccludedAnimController != null) {
                shortcutUnoccludedAnimController.destroy();
            }
            shortcutContentView.removeAllViews();
            boolean bottomIconDeep = ViewUtils.INSTANCE.getBottomIconDeep(getSysuiContext());
            LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "inflateShortcutViews, isBottomIconRectIsDeep=" + bottomIconDeep, null, 4, null);
            ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getPluginContext()).inflate(R.layout.systemui_shortcut_normal_layout, (ViewGroup) null);
            this.shortcutContentViewForNormal = viewGroup;
            if (viewGroup != null) {
                shortcutContentView.addView(viewGroup);
                this.shortcutMoveController = new ShortcutMoveController(getPluginContext(), viewGroup, this.shortcutMoveControllerCallback);
                this.unoccludedAnimController = new ShortcutUnoccludedAnimController(getSysuiContext(), viewGroup, this.unoccludedAnimControllerCallback);
                ShortcutViewLayoutController shortcutViewLayoutController2 = new ShortcutViewLayoutController(getPluginContext(), getSysuiContext(), viewGroup, bottomIconDeep, MiuiShortcutManager.INSTANCE.isGxzwLowPosition(), new ShortcutViewLayoutController.ShortcutTipsAnimCallback() { // from class: com.miui.keyguard.shortcuts.ShortcutPluginImpl$inflateShortcutViews$1$1$1
                    @Override // com.miui.keyguard.shortcuts.controller.ShortcutViewLayoutController.ShortcutTipsAnimCallback
                    public void onAnimationEnd() {
                        MiuiShortcutPlugin.ShortcutPluginCallback shortcutPluginCallback = this.this$0.shortcutPluginCallback;
                        if (shortcutPluginCallback != null) {
                            MiuiShortcutPlugin.ShortcutPluginCallback.onShortcutPluginCallback$default(shortcutPluginCallback, "onShortcutTipsAnimationEnd", (Bundle) null, 2, (Object) null);
                        }
                    }

                    @Override // com.miui.keyguard.shortcuts.controller.ShortcutViewLayoutController.ShortcutTipsAnimCallback
                    public void onAnimationStart() {
                        MiuiShortcutPlugin.ShortcutPluginCallback shortcutPluginCallback = this.this$0.shortcutPluginCallback;
                        if (shortcutPluginCallback != null) {
                            MiuiShortcutPlugin.ShortcutPluginCallback.onShortcutPluginCallback$default(shortcutPluginCallback, "onShortcutTipsAnimationStart", (Bundle) null, 2, (Object) null);
                        }
                    }
                });
                this.shortcutViewLayoutController = shortcutViewLayoutController2;
                shortcutViewLayoutController2.updateHorizontalMargin();
            }
        }
    }

    public final boolean interceptTouchEvent() {
        ShortcutMoveController shortcutMoveController = this.shortcutMoveController;
        if (shortcutMoveController != null) {
            return shortcutMoveController.interceptTouchEvent();
        }
        return false;
    }

    public final boolean needPlayOccludedAnimation() {
        PowerManager powerManager = this.powerManager;
        return powerManager != null && powerManager.isInteractive() && MiuiShortcutManager.INSTANCE.isDefaultTheme() && !DeviceHelper.INSTANCE.isTinyScreen(getSysuiContext());
    }

    public final boolean needPlayUnoccludedAnimation() {
        PowerManager powerManager = this.powerManager;
        if (powerManager == null || !powerManager.isInteractive()) {
            return false;
        }
        MiuiShortcutManager miuiShortcutManager = MiuiShortcutManager.INSTANCE;
        return miuiShortcutManager.isDefaultTheme() && miuiShortcutManager.isKeyguardShowing() && !miuiShortcutManager.isKeyguardGoingAway() && !DeviceHelper.INSTANCE.isTinyScreen(getSysuiContext());
    }

    public final void onBottomAreaAlphaChange(float f) {
        ShortcutWindowManager.INSTANCE.setAlpha(f);
        updateContentViewForNormalVisibility(f);
    }

    public final void onBottomIconDarkModeChanged(boolean z) {
        LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "onBottomIconDarkModeChanged: " + z, null, 4, null);
        ShortcutMoveController shortcutMoveController = this.shortcutMoveController;
        if (shortcutMoveController != null) {
            shortcutMoveController.onBottomIconDarkModeChanged(z);
        }
        ShortcutUnoccludedAnimController shortcutUnoccludedAnimController = this.unoccludedAnimController;
        if (shortcutUnoccludedAnimController != null) {
            shortcutUnoccludedAnimController.onBottomIconDarkModeChanged(z);
        }
        ShortcutViewLayoutController shortcutViewLayoutController = this.shortcutViewLayoutController;
        if (shortcutViewLayoutController != null) {
            shortcutViewLayoutController.onBottomIconDarkModeChanged(z);
        }
    }

    public final void onBouncerShownChanged(boolean z) {
        MiuiShortcutManager miuiShortcutManager = MiuiShortcutManager.INSTANCE;
        if (miuiShortcutManager.isBouncerShowing() != z) {
            miuiShortcutManager.setBouncerShowing(z);
            ShortcutMoveController shortcutMoveController = this.shortcutMoveController;
            if (shortcutMoveController != null) {
                shortcutMoveController.onBouncerShownChanged(z);
            }
            ShortcutUnoccludedAnimController shortcutUnoccludedAnimController = this.unoccludedAnimController;
            if (shortcutUnoccludedAnimController != null) {
                shortcutUnoccludedAnimController.onBouncerShownChanged(z);
            }
            if (z) {
                resetShortcutViewVisibility();
            }
        }
    }

    public final void onChargeAnimationStart() {
        ShortcutMoveController shortcutMoveController = this.shortcutMoveController;
        if (shortcutMoveController != null) {
            shortcutMoveController.onChargeAnimationStart();
        }
    }

    public final void onConfigChanged(Configuration configuration) throws IOException {
        MiuiShortcutManager.INSTANCE.onConfigurationChanged(configuration);
        ShortcutMoveController shortcutMoveController = this.shortcutMoveController;
        if (shortcutMoveController != null) {
            shortcutMoveController.onConfigurationChanged(configuration);
        }
        int iUpdateFrom = this.configuration.updateFrom(configuration);
        boolean z = (iUpdateFrom & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0;
        boolean z2 = (1073741824 & iUpdateFrom) != 0;
        if (z || z2) {
            inflateShortcutViews();
        }
        if ((iUpdateFrom & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0) {
            ShortcutViewLayoutController shortcutViewLayoutController = this.shortcutViewLayoutController;
            if (shortcutViewLayoutController != null) {
                shortcutViewLayoutController.updateShortcutFixedSize();
            }
            ShortcutViewLayoutController shortcutViewLayoutController2 = this.shortcutViewLayoutController;
            if (shortcutViewLayoutController2 != null) {
                shortcutViewLayoutController2.updateHorizontalMargin();
            }
        }
    }

    public void onCreate(Context sysuiContext, Context pluginContext) {
        Intrinsics.checkNotNullParameter(sysuiContext, "sysuiContext");
        Intrinsics.checkNotNullParameter(pluginContext, "pluginContext");
        super.onCreate(sysuiContext, pluginContext);
        LogUtils.logI$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "plugin onCreate", null, 4, null);
        setSysuiContext(sysuiContext);
        setPluginContext(pluginContext);
        if (miuix.os.DeviceHelper.isTablet()) {
            return;
        }
        this.configuration.updateFrom(sysuiContext.getResources().getConfiguration());
        DeviceHelper deviceHelper = DeviceHelper.INSTANCE;
        deviceHelper.updateForceMiddle(sysuiContext);
        UserSwitchManager.INSTANCE.init(sysuiContext);
        ShortcutManager.init$default(MiuiShortcutManager.INSTANCE, sysuiContext, false, 2, null);
        ShortcutWindowManager.INSTANCE.init(pluginContext);
        ShortcutAnalyticManager.init(sysuiContext, false, OneTrack.Mode.PLUGIN);
        if (deviceHelper.isFlipDevice(sysuiContext)) {
            DeviceFoldStateManager deviceFoldStateManager = DeviceFoldStateManager.INSTANCE;
            deviceFoldStateManager.init(sysuiContext);
            deviceFoldStateManager.registerCallback(this.foldStateListener);
        }
        HapticFeedbackUtils.INSTANCE.init(sysuiContext);
        setShortcutContentView((ViewGroup) LayoutInflater.from(pluginContext).inflate(R.layout.systemui_shortcut_root_layout, (ViewGroup) null));
        this.powerManager = (PowerManager) sysuiContext.getSystemService("power");
        inflateShortcutViews();
    }

    public void onDestroy() {
        super.onDestroy();
        LogUtils.logI$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "plugin onDestroy", null, 4, null);
        ShortcutViewLayoutController shortcutViewLayoutController = this.shortcutViewLayoutController;
        if (shortcutViewLayoutController != null) {
            shortcutViewLayoutController.release();
        }
        ShortcutMoveController shortcutMoveController = this.shortcutMoveController;
        if (shortcutMoveController != null) {
            shortcutMoveController.destroy();
        }
        ShortcutUnoccludedAnimController shortcutUnoccludedAnimController = this.unoccludedAnimController;
        if (shortcutUnoccludedAnimController != null) {
            shortcutUnoccludedAnimController.destroy();
        }
        UserSwitchManager.INSTANCE.release();
        MiuiShortcutManager.INSTANCE.release();
        ShortcutWindowManager.INSTANCE.release();
        if (DeviceHelper.INSTANCE.isFlipDevice(getSysuiContext())) {
            DeviceFoldStateManager deviceFoldStateManager = DeviceFoldStateManager.INSTANCE;
            deviceFoldStateManager.unregisterCallback(this.foldStateListener);
            deviceFoldStateManager.release();
        }
        HapticFeedbackUtils.INSTANCE.cleanup();
    }

    public final void onFashionGalleryConfigUpdate(int i, boolean z, String str) {
        MiuiShortcutManager.INSTANCE.onFashionGalleryConfigUpdate(i, z, str);
    }

    public final void onFinishedGoingToSleep() {
        ShortcutMoveController shortcutMoveController = this.shortcutMoveController;
        if (shortcutMoveController != null) {
            shortcutMoveController.onFinishedGoingToSleep();
        }
        ShortcutUnoccludedAnimController shortcutUnoccludedAnimController = this.unoccludedAnimController;
        if (shortcutUnoccludedAnimController != null) {
            shortcutUnoccludedAnimController.onFinishedGoingToSleep();
        }
    }

    public final void onFoldStateChanged(boolean z) throws IOException {
        ShortcutMoveController shortcutMoveController = this.shortcutMoveController;
        if (shortcutMoveController != null) {
            shortcutMoveController.onFoldStateChanged(z);
        }
        ShortcutUnoccludedAnimController shortcutUnoccludedAnimController = this.unoccludedAnimController;
        if (shortcutUnoccludedAnimController != null) {
            shortcutUnoccludedAnimController.onFoldStateChanged(z);
        }
    }

    public final void onKeyguardGoingAwayChanged(boolean z) {
        ShortcutMoveController shortcutMoveController;
        MiuiShortcutManager.INSTANCE.setKeyguardGoingAway(z);
        if (!z || (shortcutMoveController = this.shortcutMoveController) == null) {
            return;
        }
        shortcutMoveController.keyguardGoingAway();
    }

    public final void onKeyguardOccludedChanged(boolean z) {
        MiuiShortcutManager miuiShortcutManager = MiuiShortcutManager.INSTANCE;
        miuiShortcutManager.setOccluded(z);
        if (!z) {
            miuiShortcutManager.resetOccludedMovedShortcut();
        }
        ShortcutMoveController shortcutMoveController = this.shortcutMoveController;
        if (shortcutMoveController != null) {
            shortcutMoveController.onKeyguardOccludedChanged(z);
        }
        synchronized (this.syncUnoccludedAnimObject) {
            ShortcutUnoccludedAnimController shortcutUnoccludedAnimController = this.unoccludedAnimController;
            if (shortcutUnoccludedAnimController != null) {
                shortcutUnoccludedAnimController.onKeyguardOccludedChanged(z);
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    public final void onKeyguardShowingChanged(boolean z) throws IOException {
        MiuiShortcutManager.INSTANCE.setKeyguardShowing(z);
        ShortcutMoveController shortcutMoveController = this.shortcutMoveController;
        if (shortcutMoveController != null) {
            shortcutMoveController.onKeyguardShowingChanged(z);
        }
        ShortcutUnoccludedAnimController shortcutUnoccludedAnimController = this.unoccludedAnimController;
        if (shortcutUnoccludedAnimController != null) {
            shortcutUnoccludedAnimController.onKeyguardShowingChanged(z);
        }
    }

    public final void onStartedGoingToSleep() {
        MiuiShortcutManager.INSTANCE.setInteractive(false);
        ShortcutMoveController.Callback.DefaultImpls.updateCanShowBiometricAuthentication$default(this.shortcutMoveControllerCallback, true, false, 2, null);
    }

    public final void onStartedWakingUp() {
        MiuiShortcutManager.INSTANCE.setInteractive(true);
        ShortcutMoveController shortcutMoveController = this.shortcutMoveController;
        if (shortcutMoveController != null) {
            shortcutMoveController.onStartedWakingUp();
        }
    }

    public final void onSuperSaveModeChanged(boolean z) {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:147:0x01f0  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Instruction removed from duplicated block: B:147:0x01f0, please report this as an issue */
    public Object onSystemUIAction(String action, Bundle bundle) throws IOException {
        String string;
        Intrinsics.checkNotNullParameter(action, "action");
        boolean z = false;
        switch (action.hashCode()) {
            case -1707786362:
                if (action.equals("getShortcutData")) {
                    ShortcutEntity shortcutDataLeft = bundle != null ? bundle.getBoolean("isLeftShortcutData") : false ? MiuiShortcutManager.INSTANCE.getShortcutDataLeft() : MiuiShortcutManager.INSTANCE.getShortcutDataRight();
                    String shortcutTrackName = shortcutDataLeft.getShortcutTrackName();
                    Drawable drawableWithAvailable = shortcutDataLeft.getDrawableWithAvailable();
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("getShortcutTrackName", shortcutTrackName);
                    bundle2.putBoolean("isDrawableAvailable", drawableWithAvailable != null);
                    return bundle2;
                }
                LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "onSystemUIAction, action: " + action, null, 4, null);
                return null;
            case -1707242879:
                if (action.equals("getShortcutView")) {
                    if (bundle != null ? bundle.getBoolean("isLeftShortcutView") : true) {
                        ShortcutViewLayoutController shortcutViewLayoutController = this.shortcutViewLayoutController;
                        if (shortcutViewLayoutController != null) {
                            return shortcutViewLayoutController.getShortcutViewLeft();
                        }
                        return null;
                    }
                    ShortcutViewLayoutController shortcutViewLayoutController2 = this.shortcutViewLayoutController;
                    if (shortcutViewLayoutController2 != null) {
                        return shortcutViewLayoutController2.getShortcutViewRight();
                    }
                    return null;
                }
                LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "onSystemUIAction, action: " + action, null, 4, null);
                return null;
            case -1697356845:
                if (action.equals("onFinishedGoingToSleep")) {
                    onFinishedGoingToSleep();
                } else {
                    LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "onSystemUIAction, action: " + action, null, 4, null);
                }
                return null;
            case -1524649676:
                if (action.equals("onKeyguardShowingChanged")) {
                    onKeyguardShowingChanged(bundle != null ? bundle.getBoolean("keyguardShowing") : false);
                } else {
                    LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "onSystemUIAction, action: " + action, null, 4, null);
                }
                return null;
            case -1424756201:
                if (action.equals("onThemeStateChange")) {
                    onThemeStateChange(bundle != null ? bundle.getBoolean("isDefaultTheme") : true);
                } else {
                    LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "onSystemUIAction, action: " + action, null, 4, null);
                }
                return null;
            case -1164202108:
                if (action.equals("onFashionGalleryConfigUpdate")) {
                    int i = bundle != null ? bundle.getInt("userId") : 0;
                    if (bundle != null && !bundle.getBoolean("isAvailable")) {
                        z = true;
                    }
                    boolean z2 = !z;
                    if (bundle == null || (string = bundle.getString("packageName")) == null) {
                        string = "";
                    }
                    onFashionGalleryConfigUpdate(i, z2, string);
                } else {
                    LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "onSystemUIAction, action: " + action, null, 4, null);
                }
                return null;
            case -1130580828:
                if (action.equals("onKeyguardOccludedChanged")) {
                    onKeyguardOccludedChanged(bundle != null ? bundle.getBoolean("occluded") : false);
                } else {
                    LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "onSystemUIAction, action: " + action, null, 4, null);
                }
                return null;
            case -1093745411:
                if (action.equals("interceptTouchEvent")) {
                    return Boolean.valueOf(interceptTouchEvent());
                }
                LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "onSystemUIAction, action: " + action, null, 4, null);
                return null;
            case -888162921:
                if (action.equals("onBottomAreaAlphaChange")) {
                    onBottomAreaAlphaChange(bundle != null ? bundle.getFloat("alphaValue") : 1.0f);
                } else {
                    LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "onSystemUIAction, action: " + action, null, 4, null);
                }
                return null;
            case -204403823:
                if (action.equals("onChargeAnimationStart")) {
                    onChargeAnimationStart();
                } else {
                    LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "onSystemUIAction, action: " + action, null, 4, null);
                }
                return null;
            case -151463592:
                if (action.equals("onSuperSaveModeChanged")) {
                    onSuperSaveModeChanged(bundle != null ? bundle.getBoolean("isSuperSaveMode") : false);
                } else {
                    LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "onSystemUIAction, action: " + action, null, 4, null);
                }
                return null;
            case -73764207:
                if (action.equals("getBottomShortcutLayout")) {
                    ShortcutViewLayoutController shortcutViewLayoutController3 = this.shortcutViewLayoutController;
                    if (shortcutViewLayoutController3 != null) {
                        return shortcutViewLayoutController3.getShortcutViewLayout();
                    }
                    return null;
                }
                LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "onSystemUIAction, action: " + action, null, 4, null);
                return null;
            case 107147166:
                if (action.equals("onStartedWakingUp")) {
                    onStartedWakingUp();
                } else {
                    LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "onSystemUIAction, action: " + action, null, 4, null);
                }
                return null;
            case 227095455:
                if (action.equals("isOccludedAnimationPlaying")) {
                    ShortcutMoveController shortcutMoveController = this.shortcutMoveController;
                    if (shortcutMoveController != null) {
                        return Boolean.valueOf(shortcutMoveController.isOccludedAnimStartOrPlaying());
                    }
                    return null;
                }
                LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "onSystemUIAction, action: " + action, null, 4, null);
                return null;
            case 319380489:
                if (action.equals("onGxzwIconShownChanged")) {
                    updateMiuiShortcutTipsForGxzwLowPosition(bundle != null ? bundle.getBoolean("gxzwIconShowing") : false);
                } else {
                    LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "onSystemUIAction, action: " + action, null, 4, null);
                }
                return null;
            case 557598734:
                if (action.equals("onBouncerShownChanged")) {
                    onBouncerShownChanged(bundle != null ? bundle.getBoolean("bouncerShow") : false);
                } else {
                    LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "onSystemUIAction, action: " + action, null, 4, null);
                }
                return null;
            case 566539857:
                if (action.equals("onKeyguardGoingAwayChanged")) {
                    onKeyguardGoingAwayChanged(bundle != null ? bundle.getBoolean("isKeyguardGoingAway") : false);
                } else {
                    LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "onSystemUIAction, action: " + action, null, 4, null);
                }
                return null;
            case 1077382515:
                if (action.equals("onConfigChanged")) {
                    Configuration configuration = bundle != null ? (Configuration) bundle.getParcelable("newConfig") : null;
                    if (configuration != null) {
                        onConfigChanged(configuration);
                    }
                } else {
                    LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "onSystemUIAction, action: " + action, null, 4, null);
                }
                return null;
            case 1122631812:
                if (action.equals("onStartedGoingToSleep")) {
                    onStartedGoingToSleep();
                } else {
                    LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "onSystemUIAction, action: " + action, null, 4, null);
                }
                return null;
            case 1135405880:
                if (action.equals("onBottomIconDarkModeChanged")) {
                    onBottomIconDarkModeChanged(bundle != null ? bundle.getBoolean("isIconDarkMode") : false);
                } else {
                    LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "onSystemUIAction, action: " + action, null, 4, null);
                }
                return null;
            case 1492491448:
                if (action.equals("isUnoccludedAnimationPlaying")) {
                    ShortcutUnoccludedAnimController shortcutUnoccludedAnimController = this.unoccludedAnimController;
                    if (shortcutUnoccludedAnimController != null) {
                        return Boolean.valueOf(shortcutUnoccludedAnimController.isUnoccludedAnimStartOrPlaying());
                    }
                    return null;
                }
                LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "onSystemUIAction, action: " + action, null, 4, null);
                return null;
            default:
                LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "onSystemUIAction, action: " + action, null, 4, null);
                return null;
        }
    }

    public final void onThemeStateChange(boolean z) {
        MiuiShortcutManager.INSTANCE.setDefaultTheme(z);
    }

    public boolean onTouchEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        ShortcutMoveController shortcutMoveController = this.shortcutMoveController;
        if (shortcutMoveController != null) {
            return shortcutMoveController.onTouchEvent(event);
        }
        return false;
    }

    public void registerPluginCallback(MiuiShortcutPlugin.ShortcutPluginCallback shortcutPluginCallback) {
        this.shortcutPluginCallback = shortcutPluginCallback;
    }

    public final void resetShortcutViewVisibility() {
        ShortcutViewLayoutController shortcutViewLayoutController = this.shortcutViewLayoutController;
        if (shortcutViewLayoutController != null) {
            ViewUtils viewUtils = ViewUtils.INSTANCE;
            viewUtils.showIfDrawablePresent(shortcutViewLayoutController.getShortcutViewLeft());
            viewUtils.showIfDrawablePresent(shortcutViewLayoutController.getShortcutViewRight());
        }
    }

    public final void setPluginContext(Context context) {
        Intrinsics.checkNotNullParameter(context, "<set-?>");
        this.pluginContext = context;
    }

    public void setShortcutContentView(ViewGroup viewGroup) {
        this.shortcutContentView = viewGroup;
    }

    public final void setSysuiContext(Context context) {
        Intrinsics.checkNotNullParameter(context, "<set-?>");
        this.sysuiContext = context;
    }

    public void unregisterPluginCallback(MiuiShortcutPlugin.ShortcutPluginCallback shortcutPluginCallback) {
        this.shortcutPluginCallback = null;
    }

    public final void updateContentViewForNormalVisibility(float f) {
        ViewGroup viewGroup = this.shortcutContentViewForNormal;
        if (viewGroup != null) {
            int i = f == 0.0f ? 4 : 0;
            if (viewGroup.getVisibility() != i) {
                LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutPluginImpl", "shortcutContentViewForNormal set visibility " + i + " according to alpha " + f, null, 4, null);
                viewGroup.setVisibility(i);
            }
        }
    }

    public final void updateMiuiShortcutTipsForGxzwLowPosition(boolean z) {
        ShortcutViewLayoutController shortcutViewLayoutController = this.shortcutViewLayoutController;
        if (shortcutViewLayoutController != null) {
            shortcutViewLayoutController.updateMiuiShortcutTipsForGxzwLowPosition(z);
        }
    }
}
