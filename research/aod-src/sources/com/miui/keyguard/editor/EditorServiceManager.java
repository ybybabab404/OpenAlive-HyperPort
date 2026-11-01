package com.miui.keyguard.editor;

import android.app.Application;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.Window;
import androidx.core.os.BundleKt;
import com.miui.keyguard.editor.base.ExitAnimationPerformer;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.edit.base.TemplateApplier;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.GsonSingletonKt;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.view.LockScreenTransformerLayer;
import com.miui.keyguardeditor.IMiuiKeyguardEditorCallback;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
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
/* JADX INFO: compiled from: EditorServiceManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class EditorServiceManager {
    public static final Companion Companion = new Companion(null);
    public final String TAG;
    public WeakReference activityReference;
    public EditorState currentState;
    public WeakReference exitAnimationPerformerReference;
    public volatile IMiuiKeyguardEditorCallback iMiuiKeyguardEditorCallback;
    public volatile boolean isEditorReadyRequestSend;
    public KeyguardManager keyguardManager;
    public final Handler mainHandle;
    public List otherActivities;
    public long screenChangeTime;
    public EditorServiceWatchDog serviceWatchDog;

    /* JADX INFO: compiled from: EditorServiceManager.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final EditorServiceManager getInstance() {
            return Helper.INSTANCE.getInstance();
        }
    }

    /* JADX INFO: compiled from: EditorServiceManager.kt */
    @Metadata
    public enum EditorState {
        READY,
        SHOWING,
        SHOWBOUNCER,
        EXITING,
        NULL;

        public static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    }

    /* JADX INFO: compiled from: EditorServiceManager.kt */
    @Metadata
    public static final class Helper {
        public static final Helper INSTANCE = new Helper();
        public static final EditorServiceManager instance = new EditorServiceManager(null);

        public final EditorServiceManager getInstance() {
            return instance;
        }
    }

    public EditorServiceManager() {
        this.TAG = "KeyguardEditorServiceManager";
        this.currentState = EditorState.NULL;
        this.otherActivities = new ArrayList();
        this.mainHandle = new Handler(Looper.getMainLooper());
        this.screenChangeTime = System.currentTimeMillis();
    }

    public /* synthetic */ EditorServiceManager(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public static final void delyStartTransformerAnimation$lambda$6(EditorServiceManager editorServiceManager, boolean z, long j, long j2) {
        EditorActivity editorActivity;
        EditorActivity editorActivity2;
        WeakReference weakReference = editorServiceManager.activityReference;
        if (weakReference != null && (editorActivity2 = (EditorActivity) weakReference.get()) != null && editorActivity2.getOnConfigChanging() && System.currentTimeMillis() - editorServiceManager.screenChangeTime < 1000) {
            editorServiceManager.delyStartTransformerAnimation(z, j, j2);
            return;
        }
        editorServiceManager.currentState = z ? EditorState.SHOWING : EditorState.SHOWBOUNCER;
        WeakReference weakReference2 = editorServiceManager.activityReference;
        if (weakReference2 == null || (editorActivity = (EditorActivity) weakReference2.get()) == null) {
            return;
        }
        editorActivity.startTransformerAnimation(z, j, j2);
    }

    public static final void onReceiveExitWithoutUnLockCommand$lambda$4(EditorServiceManager editorServiceManager) {
        EditorActivity editorActivity;
        editorServiceManager.currentState = EditorState.EXITING;
        WeakReference weakReference = editorServiceManager.activityReference;
        if (weakReference == null || (editorActivity = (EditorActivity) weakReference.get()) == null) {
            return;
        }
        editorActivity.exitWithoutUnLockCommand();
    }

    public static final void onReceiveFinishCommand$lambda$3(EditorServiceManager editorServiceManager) {
        WeakReference weakReference = editorServiceManager.activityReference;
        EditorActivity editorActivity = weakReference != null ? (EditorActivity) weakReference.get() : null;
        Log.d(editorServiceManager.TAG, "onReceiveFinishCommand: activity=" + editorActivity);
        if (editorActivity != null) {
            editorActivity.resetApplyingFlagForMainFragment("onReceiveFinishCommand");
        }
        if (editorActivity != null) {
            editorActivity.finishActivity();
        }
        editorServiceManager.finishOtherActivity();
    }

    public static final void onReceiveHideLoadingCommand$lambda$2(EditorServiceManager editorServiceManager) {
        WeakReference weakReference = editorServiceManager.activityReference;
        EditorActivity editorActivity = weakReference != null ? (EditorActivity) weakReference.get() : null;
        Log.d(editorServiceManager.TAG, "onReceiveHideLoadingCommand: activity=" + editorActivity);
        if (editorActivity != null) {
            editorActivity.hideLoadingView();
        }
    }

    public static final void onReceiveKeyguardUnlocked$lambda$5(EditorServiceManager editorServiceManager) {
        editorServiceManager.currentState = EditorState.SHOWING;
        WeakReference weakReference = editorServiceManager.activityReference;
        EditorActivity editorActivity = weakReference != null ? (EditorActivity) weakReference.get() : null;
        Log.d(editorServiceManager.TAG, "onReceiveKeyguardUnlocked: activity=" + editorActivity);
        if (editorActivity != null) {
            editorActivity.startTransformerAnimationUnlock();
        }
    }

    public static final void onReceivedStartExitTransformerAnimation$lambda$8(boolean z, EditorServiceManager editorServiceManager) {
        if (z) {
            WeakReference weakReference = editorServiceManager.exitAnimationPerformerReference;
            ExitAnimationPerformer exitAnimationPerformer = weakReference != null ? (ExitAnimationPerformer) weakReference.get() : null;
            if (exitAnimationPerformer == null) {
                Log.w(editorServiceManager.TAG, "onReceivedStartExitTransformerAnimation: performer = null.");
                return;
            } else {
                ExitAnimationPerformer.DefaultImpls.startExitTransformerAnimation$default(exitAnimationPerformer, true, null, null, 6, null);
                return;
            }
        }
        WeakReference weakReference2 = editorServiceManager.activityReference;
        EditorActivity editorActivity = weakReference2 != null ? (EditorActivity) weakReference2.get() : null;
        if (editorActivity == null) {
            Log.w(editorServiceManager.TAG, "onReceivedStartExitTransformerAnimation: activity = null.");
        } else {
            editorActivity.finishActivity();
        }
    }

    public static final void onReceivedStartTransformerAnimation$lambda$7(EditorServiceManager editorServiceManager, boolean z, long j, long j2) {
        EditorActivity editorActivity;
        EditorActivity editorActivity2;
        WeakReference weakReference = editorServiceManager.activityReference;
        if (weakReference != null && (editorActivity2 = (EditorActivity) weakReference.get()) != null && editorActivity2.getOnConfigChanging()) {
            editorServiceManager.delyStartTransformerAnimation(z, j, j2);
            return;
        }
        editorServiceManager.currentState = z ? EditorState.SHOWING : EditorState.SHOWBOUNCER;
        WeakReference weakReference2 = editorServiceManager.activityReference;
        if (weakReference2 == null || (editorActivity = (EditorActivity) weakReference2.get()) == null) {
            return;
        }
        editorActivity.startTransformerAnimation(z, j, j2);
    }

    public static final void sendStartAnimationRequest$lambda$9(TemplateApplier.ApplyCallback applyCallback) {
        if (applyCallback != null) {
            applyCallback.finish();
        }
    }

    public static final void setIMiuiKeyguardEditorCallback$lambda$1(IMiuiKeyguardEditorCallback iMiuiKeyguardEditorCallback, EditorServiceManager editorServiceManager) {
        if (iMiuiKeyguardEditorCallback != null) {
            editorServiceManager.clearWatchDog();
        }
        editorServiceManager.iMiuiKeyguardEditorCallback = iMiuiKeyguardEditorCallback;
        if (editorServiceManager.isEditorReadyRequestSend) {
            editorServiceManager.sendEditorReadyCommand();
        }
    }

    public final void clearWatchDog() {
        EditorServiceWatchDog editorServiceWatchDog = this.serviceWatchDog;
        if (editorServiceWatchDog != null) {
            editorServiceWatchDog.cancel();
        }
        this.serviceWatchDog = null;
    }

    public final Bundle createReadyBundle() {
        EditorActivity editorActivity;
        WeakReference weakReference = this.activityReference;
        LockScreenTransformerLayer lockScreenTransformerLayer = (weakReference == null || (editorActivity = (EditorActivity) weakReference.get()) == null) ? null : editorActivity.getLockScreenTransformerLayer();
        Bundle bundle = new Bundle();
        if (lockScreenTransformerLayer != null) {
            bundle.putFloat("finalScaleX", lockScreenTransformerLayer.getFinalScaleX());
            bundle.putFloat("finalScaleY", lockScreenTransformerLayer.getFinalScaleY());
            bundle.putFloat("finalTranslationY", lockScreenTransformerLayer.getFinalTranslationY());
            bundle.putFloat("finalScaleXWithShowBouncer", lockScreenTransformerLayer.getFinalScaleXWithShowBouncer());
            bundle.putFloat("finalScaleYWithShowBouncer", lockScreenTransformerLayer.getFinalScaleYWithShowBouncer());
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            bundle.putFloat("finalTranslationYWithShowBouncer", deviceUtil.isPhone() ? lockScreenTransformerLayer.getFinalTranslationYWithShowBouncer() : 0.0f);
            bundle.putFloat("finalCornerRadiusWithShowBouncer", lockScreenTransformerLayer.getPreviewRadiusPixel());
            ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
            Context context = lockScreenTransformerLayer.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            bundle.putFloat("finalCornerRadius", resourcesProvider.providePreviewRadius(context));
            bundle.putFloat("templateReallyWidth", lockScreenTransformerLayer.getReallyPreviewWidth());
            WeakReference weakReference2 = this.activityReference;
            EditorActivity editorActivity2 = weakReference2 != null ? (EditorActivity) weakReference2.get() : null;
            if (editorActivity2 != null) {
                View decorView = editorActivity2.getWindow().getDecorView();
                Intrinsics.checkNotNullExpressionValue(decorView, "getDecorView(...)");
                float[] fArrScreenRadii = deviceUtil.screenRadii(decorView);
                bundle.putFloatArray("finalCornerRadiusArr", fArrScreenRadii);
                bundle.putFloatArray("finalCornerRadiusWithShowBouncerArr", fArrScreenRadii);
            }
        }
        return bundle;
    }

    public final void delyStartTransformerAnimation(final boolean z, final long j, final long j2) {
        this.mainHandle.postDelayed(new Runnable() { // from class: com.miui.keyguard.editor.EditorServiceManager$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                EditorServiceManager.delyStartTransformerAnimation$lambda$6(this.f$0, z, j, j2);
            }
        }, 100L);
    }

    public final void finishOtherActivity() {
        EditorActivity editorActivity;
        EditorActivity editorActivity2;
        Log.d(this.TAG, "clearActivity");
        for (WeakReference weakReference : this.otherActivities) {
            if (weakReference != null && (editorActivity = (EditorActivity) weakReference.get()) != null && !editorActivity.isFinishing() && (editorActivity2 = (EditorActivity) weakReference.get()) != null) {
                editorActivity2.finish();
            }
        }
        this.otherActivities.clear();
    }

    public final EditorState getCurrentState() {
        return this.currentState;
    }

    public final boolean isKeyguardEditorCallbackReady() {
        return this.iMiuiKeyguardEditorCallback != null;
    }

    public final void onReceiveExitWithoutUnLockCommand() {
        Log.d(this.TAG, "onReceiveBackBecauseUnLockCommand ");
        this.mainHandle.post(new Runnable() { // from class: com.miui.keyguard.editor.EditorServiceManager$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                EditorServiceManager.onReceiveExitWithoutUnLockCommand$lambda$4(this.f$0);
            }
        });
    }

    public final void onReceiveFinishCommand() {
        Log.d(this.TAG, "onReceiveFinishCommand ");
        this.mainHandle.post(new Runnable() { // from class: com.miui.keyguard.editor.EditorServiceManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                EditorServiceManager.onReceiveFinishCommand$lambda$3(this.f$0);
            }
        });
    }

    public final void onReceiveHideLoadingCommand() {
        Log.d(this.TAG, "onReceiveHideLoadingCommand ");
        this.mainHandle.post(new Runnable() { // from class: com.miui.keyguard.editor.EditorServiceManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                EditorServiceManager.onReceiveHideLoadingCommand$lambda$2(this.f$0);
            }
        });
    }

    public final void onReceiveKeyguardUnlocked() {
        Log.d(this.TAG, "onReceiveKeyguardUnlocked ");
        this.mainHandle.post(new Runnable() { // from class: com.miui.keyguard.editor.EditorServiceManager$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                EditorServiceManager.onReceiveKeyguardUnlocked$lambda$5(this.f$0);
            }
        });
    }

    public final void onReceivedStartExitTransformerAnimation() {
        KeyguardManager keyguardManager = this.keyguardManager;
        final boolean zIsKeyguardLocked = keyguardManager != null ? keyguardManager.isKeyguardLocked() : false;
        String str = this.TAG;
        KeyguardManager keyguardManager2 = this.keyguardManager;
        Log.i(str, "onReceivedStartExitTransformerAnimation: " + (keyguardManager2 == null) + " " + (keyguardManager2 != null ? Boolean.valueOf(keyguardManager2.isKeyguardLocked()) : null) + " isKeyguardLocked = " + zIsKeyguardLocked);
        this.mainHandle.post(new Runnable() { // from class: com.miui.keyguard.editor.EditorServiceManager$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                EditorServiceManager.onReceivedStartExitTransformerAnimation$lambda$8(zIsKeyguardLocked, this);
            }
        });
    }

    public final void onReceivedStartTransformerAnimation(final boolean z, final long j, final long j2) {
        Log.d(this.TAG, "onReceivedStartTransformerAnimation， unlock=" + z);
        this.mainHandle.post(new Runnable() { // from class: com.miui.keyguard.editor.EditorServiceManager$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                EditorServiceManager.onReceivedStartTransformerAnimation$lambda$7(this.f$0, z, j, j2);
            }
        });
    }

    public final void onScreenConfigurationChange(boolean z) {
        sendConfigChangeWhenEnterAnimWaitUnlockCommand(z);
    }

    public final void registerWatchDog(EditorActivity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        clearWatchDog();
        this.serviceWatchDog = new EditorServiceWatchDog(activity);
    }

    public final boolean sendCommandToKeyguard(String str, Bundle bundle) {
        Log.d(this.TAG, "sendCommand " + str + ", iMiuiKeyguardEditorCallback=" + this.iMiuiKeyguardEditorCallback + " " + bundle);
        try {
            if (this.iMiuiKeyguardEditorCallback == null) {
                return false;
            }
            IMiuiKeyguardEditorCallback iMiuiKeyguardEditorCallback = this.iMiuiKeyguardEditorCallback;
            Intrinsics.checkNotNull(iMiuiKeyguardEditorCallback);
            iMiuiKeyguardEditorCallback.sendCommand(str, bundle);
            return true;
        } catch (RemoteException e) {
            Log.d(this.TAG, "sendCommandToKeyguard error: " + e);
            return false;
        }
    }

    public final void sendConfigChangeWhenEnterAnimWaitUnlockCommand(boolean z) {
        WeakReference weakReference;
        EditorActivity editorActivity;
        LockScreenTransformerLayer lockScreenTransformerLayer;
        EditorActivity editorActivity2;
        Window window;
        View decorView;
        Log.d(this.TAG, "sendConfigChangeWhenEnterAnimCommand isScreenChange=" + z + " activityReference=" + (this.activityReference != null));
        if (!z || (weakReference = this.activityReference) == null || (editorActivity = (EditorActivity) weakReference.get()) == null || (lockScreenTransformerLayer = editorActivity.getLockScreenTransformerLayer()) == null || !lockScreenTransformerLayer.isEnterAnimationStarted()) {
            return;
        }
        Bundle bundleCreateReadyBundle = createReadyBundle();
        WeakReference weakReference2 = this.activityReference;
        if (weakReference2 != null && (editorActivity2 = (EditorActivity) weakReference2.get()) != null && (window = editorActivity2.getWindow()) != null && (decorView = window.getDecorView()) != null) {
            ViewUtil viewUtil = ViewUtil.INSTANCE;
            bundleCreateReadyBundle.putParcelable("surfaceControl", viewUtil.getViewRootImplSurfaceControl(decorView));
            bundleCreateReadyBundle.putParcelable("parentSurfaceControl", viewUtil.getViewRootImplParentSurfaceControl(decorView));
        }
        sendCommandToKeyguard("action_config_change_when_enter_anim_wait_unlock", bundleCreateReadyBundle);
    }

    public final void sendEditorReadyCommand() {
        Log.d(this.TAG, "sendEditorReadyCommand");
        this.currentState = EditorState.READY;
        this.isEditorReadyRequestSend = !sendCommandToKeyguard("action_editor_ready", createReadyBundle());
    }

    public final boolean sendRequestExitEditorCommand(Bundle bundle) {
        Intrinsics.checkNotNullParameter(bundle, "bundle");
        Log.d(this.TAG, "sendRequestExitEditorCommand");
        return sendCommandToKeyguard("action_request_exit_editor", bundle);
    }

    public final void sendStartAnimationRequest(BaseTemplateView templateView, boolean z, final TemplateApplier.ApplyCallback applyCallback, ExitAnimationPerformer exitAnimationPerformer) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        EditorServiceManager companion = Companion.getInstance();
        companion.setExitAnimationPerformer(exitAnimationPerformer);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        View rootView = templateView.getRootView();
        Intrinsics.checkNotNullExpressionValue(rootView, "getRootView(...)");
        float[] fArrScreenRadii = deviceUtil.screenRadii(rootView);
        Pair pair = TuplesKt.to("finalScaleX", Float.valueOf(templateView.getScaleX()));
        Pair pair2 = TuplesKt.to("finalScaleY", Float.valueOf(templateView.getScaleY()));
        Pair pair3 = TuplesKt.to("finalTranslationY", Float.valueOf((templateView.getPivotY() - ((templateView.getHeight() * 1.0f) / 2)) * (1.0f - templateView.getScaleY())));
        Pair pair4 = TuplesKt.to("finalCornerRadius", Float.valueOf(templateView.getContext().getResources().getDimension(R.dimen.kg_template_view_radius)));
        Pair pair5 = TuplesKt.to("finalCornerRadiusArr", fArrScreenRadii);
        Pair pair6 = TuplesKt.to("isRealClockTime", Boolean.valueOf(!z));
        ViewUtil viewUtil = ViewUtil.INSTANCE;
        if (companion.sendRequestExitEditorCommand(BundleKt.bundleOf(pair, pair2, pair3, pair4, pair5, pair6, TuplesKt.to("surfaceControl", viewUtil.getViewRootImplSurfaceControl(templateView.getRootView())), TuplesKt.to("parentSurfaceControl", viewUtil.getViewRootImplParentSurfaceControl(templateView.getRootView())), TuplesKt.to("templateReallyWidth", Float.valueOf(templateView.getScaleX() * templateView.getWidth()))))) {
            return;
        }
        templateView.post(new Runnable() { // from class: com.miui.keyguard.editor.EditorServiceManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                EditorServiceManager.sendStartAnimationRequest$lambda$9(applyCallback);
            }
        });
    }

    public final boolean sendTemplateData(TemplateConfig templateConfig) {
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        Log.d(this.TAG, "sendTemplateData");
        templateConfig.setLastUpdate(Application.getProcessName());
        return sendCommandToKeyguard("action_send_template_data", BundleKt.bundleOf(TuplesKt.to("templateData", GsonSingletonKt.getGSON_SINGLETON().toJson(templateConfig))));
    }

    public final void setActivity(EditorActivity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Log.d(this.TAG, "setActivity: " + activity);
        WeakReference weakReference = this.activityReference;
        if (weakReference == null) {
            this.activityReference = new WeakReference(activity);
        } else {
            Log.d(this.TAG, "already have activity=" + (weakReference != null ? (EditorActivity) weakReference.get() : null));
            this.otherActivities.add(this.activityReference);
            this.activityReference = new WeakReference(activity);
        }
        this.keyguardManager = (KeyguardManager) activity.getApplicationContext().getSystemService("keyguard");
    }

    public final void setExitAnimationPerformer(ExitAnimationPerformer exitAnimationPerformer) {
        this.exitAnimationPerformerReference = new WeakReference(exitAnimationPerformer);
    }

    public final void setIMiuiKeyguardEditorCallback(final IMiuiKeyguardEditorCallback iMiuiKeyguardEditorCallback) {
        Log.d(this.TAG, "setIMiuiKeyguardEditorCallback ");
        this.mainHandle.post(new Runnable() { // from class: com.miui.keyguard.editor.EditorServiceManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                EditorServiceManager.setIMiuiKeyguardEditorCallback$lambda$1(iMiuiKeyguardEditorCallback, this);
            }
        });
    }
}
