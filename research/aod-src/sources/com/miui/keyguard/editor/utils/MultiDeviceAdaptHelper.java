package com.miui.keyguard.editor.utils;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import com.miui.keyguard.editor.EditorActivity;
import com.miui.keyguard.editor.EditorServiceManager;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.BaseFragment;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.view.LockScreenTransformerLayer;
import com.miui.keyguard.editor.view.TransformerEventListener;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import java.lang.ref.WeakReference;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

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
/* JADX INFO: compiled from: MultiDeviceAdaptHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class MultiDeviceAdaptHelper implements TransformerEventListener {
    public final String TAG;
    public final Context applicationContext;
    public View applyButton;
    public View buttonBar;
    public View cancelButton;
    public ComponentCallbacks componentCallback;
    public boolean configurationChangedOnAnimRunning;
    public Configuration currentConfiguration;
    public final EditorActivity editor;
    public final Resources resources;
    public AccelerometerRotationObserver rotationChangeObserver;
    public LockScreenTransformerLayer transformerLayer;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.MultiDeviceAdaptHelper$processConfigurationChangedWhenEntering$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: MultiDeviceAdaptHelper.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.utils.MultiDeviceAdaptHelper$processConfigurationChangedWhenEntering$1", f = "MultiDeviceAdaptHelper.kt", l = {230}, m = "invokeSuspend")
    public static final class C01431 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.MultiDeviceAdaptHelper$processConfigurationChangedWhenEntering$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: MultiDeviceAdaptHelper.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.utils.MultiDeviceAdaptHelper$processConfigurationChangedWhenEntering$1$1", f = "MultiDeviceAdaptHelper.kt", l = {}, m = "invokeSuspend")
        public static final class C00341 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public int label;
            public final /* synthetic */ MultiDeviceAdaptHelper this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00341(MultiDeviceAdaptHelper multiDeviceAdaptHelper, Continuation continuation) {
                super(2, continuation);
                this.this$0 = multiDeviceAdaptHelper;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00341(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00341) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                LockScreenTransformerLayer lockScreenTransformerLayer = this.this$0.transformerLayer;
                if (lockScreenTransformerLayer == null) {
                    return null;
                }
                lockScreenTransformerLayer.reloadCurrentTemplateWallpaper();
                return Unit.INSTANCE;
            }
        }

        public C01431(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MultiDeviceAdaptHelper.this.new C01431(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01431) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineDispatcher io2 = Dispatchers.getIO();
                C00341 c00341 = new C00341(MultiDeviceAdaptHelper.this, null);
                this.label = 1;
                if (BuildersKt.withContext(io2, c00341, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            LockScreenTransformerLayer lockScreenTransformerLayer = MultiDeviceAdaptHelper.this.transformerLayer;
            if (lockScreenTransformerLayer != null) {
                lockScreenTransformerLayer.onScreenConfigurationChanged(true);
            }
            return Unit.INSTANCE;
        }
    }

    public MultiDeviceAdaptHelper(EditorActivity editor) {
        Intrinsics.checkNotNullParameter(editor, "editor");
        this.editor = editor;
        this.TAG = "Keyguard-Editor-MultiDeviceAdaptHelper";
        this.applicationContext = editor.getApplicationContext();
        this.resources = editor.getResources();
    }

    public static /* synthetic */ void checkDeviceOrSetScreenRotatable$default(MultiDeviceAdaptHelper multiDeviceAdaptHelper, Context context, boolean z, Boolean bool, int i, Object obj) {
        if ((i & 4) != 0) {
            bool = null;
        }
        multiDeviceAdaptHelper.checkDeviceOrSetScreenRotatable(context, z, bool);
    }

    public static final Unit initAccelerometerRotationObserver$lambda$0(MultiDeviceAdaptHelper multiDeviceAdaptHelper, boolean z) {
        Log.i(multiDeviceAdaptHelper.TAG, "AccelerometerRotationObserver@onChange: isScreenOrientationLocked = " + z);
        Context applicationContext = multiDeviceAdaptHelper.applicationContext;
        Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
        multiDeviceAdaptHelper.checkDeviceOrSetScreenRotatable(applicationContext, z ^ true, Boolean.valueOf(z));
        return Unit.INSTANCE;
    }

    public final void attachButtonBar(View buttonBar, View cancelButton, View applyButton) {
        Intrinsics.checkNotNullParameter(buttonBar, "buttonBar");
        Intrinsics.checkNotNullParameter(cancelButton, "cancelButton");
        Intrinsics.checkNotNullParameter(applyButton, "applyButton");
        this.buttonBar = buttonBar;
        this.cancelButton = cancelButton;
        this.applyButton = applyButton;
    }

    public final void attachTransformerLayer(LockScreenTransformerLayer lockScreenTransformerLayer) {
        this.transformerLayer = lockScreenTransformerLayer;
        if (lockScreenTransformerLayer != null) {
            lockScreenTransformerLayer.addEventListener(this);
        }
    }

    public final void checkDeviceOrSetScreenRotatable(Context context, boolean z, Boolean bool) {
        if (this.editor.isFinishing() || this.editor.isDestroyed()) {
            Log.w(this.TAG, "checkDeviceOrSetScreenRotatable failed: editor activity is destroyed.");
            return;
        }
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPhone() || deviceUtil.isJ18() || (deviceUtil.isFold() && !deviceUtil.isLargeDisplay(context))) {
            this.editor.setRequestedOrientation(1);
            return;
        }
        if (!(bool != null ? bool.booleanValue() : deviceUtil.isScreenOrientationLocked(context))) {
            this.editor.setRequestedOrientation(z ? 4 : 14);
        } else {
            Log.i(this.TAG, "checkDeviceOrSetScreenRotatable ignored: isScreenOrientationLocked = true");
            this.editor.setRequestedOrientation(14);
        }
    }

    public final void dispatchFragmentConfigurationChanged(boolean z, boolean z2) {
        List<Fragment> fragments = this.editor.getSupportFragmentManager().getFragments();
        Intrinsics.checkNotNullExpressionValue(fragments, "getFragments(...)");
        for (Fragment fragment : fragments) {
            if (fragment instanceof BaseFragment) {
                ((BaseFragment) fragment).onScreenConfigurationChanged(z, z2);
            }
        }
    }

    public final void ensureComponentCallbacks() {
        if (this.componentCallback != null) {
            return;
        }
        this.componentCallback = new ComponentCallbacks() { // from class: com.miui.keyguard.editor.utils.MultiDeviceAdaptHelper.ensureComponentCallbacks.1
            @Override // android.content.ComponentCallbacks
            public void onConfigurationChanged(Configuration newConfig) {
                Intrinsics.checkNotNullParameter(newConfig, "newConfig");
                Lifecycle.State currentState = MultiDeviceAdaptHelper.this.editor.getLifecycle().getCurrentState();
                if (!currentState.isAtLeast(Lifecycle.State.STARTED)) {
                    MultiDeviceAdaptHelper.this.onConfigurationChangedOnBackground(newConfig);
                    return;
                }
                Log.w(MultiDeviceAdaptHelper.this.TAG, "Component.ConfigurationChanged: is active, not dispatched! currentState = " + currentState);
            }

            @Override // android.content.ComponentCallbacks
            public void onLowMemory() {
            }
        };
    }

    public final void initAccelerometerRotationObserver() {
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPhone() || deviceUtil.isJ18()) {
            return;
        }
        AccelerometerRotationObserver accelerometerRotationObserver = new AccelerometerRotationObserver(new WeakReference(this.editor.getApplicationContext()), new Function1() { // from class: com.miui.keyguard.editor.utils.MultiDeviceAdaptHelper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MultiDeviceAdaptHelper.initAccelerometerRotationObserver$lambda$0(this.f$0, ((Boolean) obj).booleanValue());
            }
        });
        this.rotationChangeObserver = accelerometerRotationObserver;
        accelerometerRotationObserver.register();
    }

    public final void initComponentCallbacks() {
        ensureComponentCallbacks();
        this.applicationContext.registerComponentCallbacks(this.componentCallback);
    }

    public final void onActivityCreate() {
        this.currentConfiguration = new Configuration(this.resources.getConfiguration());
        initAccelerometerRotationObserver();
        initComponentCallbacks();
        Context applicationContext = this.applicationContext;
        Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
        checkDeviceOrSetScreenRotatable$default(this, applicationContext, true, null, 4, null);
    }

    public final void onActivityDestroy() {
        Context context;
        AccelerometerRotationObserver accelerometerRotationObserver = this.rotationChangeObserver;
        if (accelerometerRotationObserver != null) {
            accelerometerRotationObserver.unregister();
        }
        ComponentCallbacks componentCallbacks = this.componentCallback;
        if (componentCallbacks == null || (context = this.applicationContext) == null) {
            return;
        }
        context.unregisterComponentCallbacks(componentCallbacks);
    }

    public final void onConfigurationChanged(Configuration newConfig) {
        MultiDeviceAdaptHelper multiDeviceAdaptHelper;
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        Configuration configuration = this.currentConfiguration;
        if (configuration == null) {
            Log.w(this.TAG, "onConfigurationChanged: currentConfiguration = null");
            return;
        }
        Intrinsics.checkNotNull(configuration);
        int iUpdateFrom = configuration.updateFrom(newConfig);
        boolean z = (iUpdateFrom & 128) != 0;
        boolean z2 = (iUpdateFrom & 1024) != 0;
        Log.i(this.TAG, "onConfigurationChanged: orientationChanged = " + z + ", screenSizeChanged = " + z2 + ", newConfig = " + newConfig);
        if (z2) {
            Context applicationContext = this.applicationContext;
            Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
            multiDeviceAdaptHelper = this;
            checkDeviceOrSetScreenRotatable$default(multiDeviceAdaptHelper, applicationContext, true, null, 4, null);
        } else {
            multiDeviceAdaptHelper = this;
        }
        if (z | z2) {
            ResourcesProvider.INSTANCE.clearDimenResources();
            multiDeviceAdaptHelper.dispatchFragmentConfigurationChanged(z, z2);
            multiDeviceAdaptHelper.onConfigurationChangedInternal(z, z2);
        }
        multiDeviceAdaptHelper.resetTopButtonBarDimens();
    }

    public final void onConfigurationChangedInternal(boolean z, boolean z2) {
        if (this.transformerLayer == null) {
            return;
        }
        Log.i(this.TAG, "onConfigurationChangedInternal -> orientationChanged: " + z + ", screenSizeChanged: " + z2);
        LockScreenTransformerLayer lockScreenTransformerLayer = this.transformerLayer;
        Intrinsics.checkNotNull(lockScreenTransformerLayer);
        boolean zIsEnterAnimationStarted = lockScreenTransformerLayer.isEnterAnimationStarted();
        LockScreenTransformerLayer lockScreenTransformerLayer2 = this.transformerLayer;
        Intrinsics.checkNotNull(lockScreenTransformerLayer2);
        boolean zIsExitAnimationStarted = lockScreenTransformerLayer2.isExitAnimationStarted();
        Log.i(this.TAG, "isEnterAnimationStarted: " + zIsEnterAnimationStarted + ", isExitAnimationStarted: " + zIsExitAnimationStarted);
        if (zIsEnterAnimationStarted) {
            processConfigurationChangedWhenEntering(z, z2);
        } else {
            if (zIsExitAnimationStarted) {
                return;
            }
            processConfigurationChangedWhenAnimationFinished();
        }
    }

    public final void onConfigurationChangedOnBackground(Configuration configuration) {
        Configuration configuration2 = this.currentConfiguration;
        if (configuration2 == null) {
            Log.w(this.TAG, "onConfigurationChangedOnBackground: currentConfiguration = null");
            return;
        }
        Intrinsics.checkNotNull(configuration2);
        int iDiff = configuration2.diff(configuration);
        boolean z = (iDiff & 128) != 0;
        boolean z2 = (iDiff & 1024) != 0;
        Log.i(this.TAG, "onConfigurationChangedOnBackground: newConfig = " + configuration + ", orientationChanged = " + z + ", screenSizeChanged = " + z2);
        if (z || z2) {
            Context applicationContext = this.applicationContext;
            Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
            checkDeviceOrSetScreenRotatable$default(this, applicationContext, true, null, 4, null);
        }
    }

    @Override // com.miui.keyguard.editor.view.TransformerEventListener
    public void onTransformerBegin(int i) {
        Log.i(this.TAG, "onTransformerBegin -> transformerType: " + i);
        Context applicationContext = this.applicationContext;
        Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
        checkDeviceOrSetScreenRotatable$default(this, applicationContext, false, null, 4, null);
    }

    @Override // com.miui.keyguard.editor.view.TransformerEventListener
    public void onTransformerFinished(int i, boolean z) {
        Log.i(this.TAG, "onTransformerFinished -> transformerType: " + i);
        Context applicationContext = this.applicationContext;
        Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
        checkDeviceOrSetScreenRotatable$default(this, applicationContext, true, null, 4, null);
        tryResetLayerDimensAfterAnimFinished(i);
    }

    public final void processConfigurationChangedWhenAnimationFinished() {
        Log.i(this.TAG, "animation finished.");
        EditorServiceManager.Companion companion = EditorServiceManager.Companion;
        if (companion.getInstance().getCurrentState() == EditorServiceManager.EditorState.SHOWBOUNCER || companion.getInstance().getCurrentState() == EditorServiceManager.EditorState.READY) {
            LockScreenTransformerLayer lockScreenTransformerLayer = this.transformerLayer;
            if (lockScreenTransformerLayer != null) {
                lockScreenTransformerLayer.onScreenConfigurationChanged(true);
                return;
            }
            return;
        }
        LockScreenTransformerLayer lockScreenTransformerLayer2 = this.transformerLayer;
        if (lockScreenTransformerLayer2 != null) {
            lockScreenTransformerLayer2.onScreenConfigurationChanged(false);
        }
    }

    public final void processConfigurationChangedWhenAnimationRunning(boolean z, boolean z2) {
        Log.i(this.TAG, "animation is running.");
        if (z2 || z) {
            this.configurationChangedOnAnimRunning = true;
        }
    }

    public final void processConfigurationChangedWhenEntering(boolean z, boolean z2) {
        LockScreenTransformerLayer lockScreenTransformerLayer = this.transformerLayer;
        Intrinsics.checkNotNull(lockScreenTransformerLayer);
        boolean zIsUnlockAnimationStarted = lockScreenTransformerLayer.isUnlockAnimationStarted();
        LockScreenTransformerLayer lockScreenTransformerLayer2 = this.transformerLayer;
        Intrinsics.checkNotNull(lockScreenTransformerLayer2);
        boolean zIsUnlockEnterAnimationStarted = lockScreenTransformerLayer2.isUnlockEnterAnimationStarted();
        LockScreenTransformerLayer lockScreenTransformerLayer3 = this.transformerLayer;
        Intrinsics.checkNotNull(lockScreenTransformerLayer3);
        boolean zIsLockedEnterAnimationStarted = lockScreenTransformerLayer3.isLockedEnterAnimationStarted();
        Log.i(this.TAG, "isUnlockAnimationStarted: " + zIsUnlockAnimationStarted + ", isUnlockEnterAnimationStarted: " + zIsUnlockEnterAnimationStarted + ", isLockedEnterAnimationStarted: " + zIsLockedEnterAnimationStarted);
        if (zIsUnlockAnimationStarted || zIsLockedEnterAnimationStarted) {
            processConfigurationChangedWhenAnimationRunning(z, z2);
            return;
        }
        if (zIsUnlockEnterAnimationStarted) {
            LockScreenTransformerLayer lockScreenTransformerLayer4 = this.transformerLayer;
            if (lockScreenTransformerLayer4 != null) {
                lockScreenTransformerLayer4.onScreenConfigurationChanged(false);
            }
            LockScreenTransformerLayer lockScreenTransformerLayer5 = this.transformerLayer;
            if (lockScreenTransformerLayer5 != null) {
                lockScreenTransformerLayer5.reStartTransformerAnimation();
                return;
            }
            return;
        }
        Log.i(this.TAG, "unlock finished, stage in lock page.");
        LockScreenTransformerLayer lockScreenTransformerLayer6 = this.transformerLayer;
        if (lockScreenTransformerLayer6 != null) {
            lockScreenTransformerLayer6.onScreenConfigurationChanged(true);
        }
        if (DeviceUtil.INSTANCE.isFold()) {
            BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(this.editor), null, null, new C01431(null), 3, null);
        }
    }

    public final void resetTopButtonBarDimens() {
        int dimensionPixelSize = this.resources.getDimensionPixelSize(R.dimen.kg_top_button_bar_padding_horizontal);
        int dimensionPixelSize2 = this.resources.getDimensionPixelSize(R.dimen.kg_top_button_bar_padding_bottom);
        View view = this.buttonBar;
        if (view != null) {
            view.setPadding(dimensionPixelSize, 0, dimensionPixelSize, dimensionPixelSize2);
        }
        int dimensionPixelSize3 = this.resources.getDimensionPixelSize(R.dimen.kg_top_button_padding_horizontal);
        int dimensionPixelSize4 = this.resources.getDimensionPixelSize(R.dimen.kg_top_button_padding_vertical);
        float dimension = this.resources.getDimension(R.dimen.kg_top_button_text_size);
        View view2 = this.applyButton;
        if (view2 != null) {
            view2.setPadding(dimensionPixelSize3, dimensionPixelSize4, dimensionPixelSize3, dimensionPixelSize4);
        }
        View view3 = this.cancelButton;
        if (view3 != null) {
            view3.setPadding(dimensionPixelSize3, dimensionPixelSize4, dimensionPixelSize3, dimensionPixelSize4);
        }
        View view4 = this.applyButton;
        if (view4 instanceof Button) {
            ((Button) view4).setTextSize(0, dimension);
        }
        View view5 = this.cancelButton;
        if (view5 instanceof Button) {
            ((Button) view5).setTextSize(0, dimension);
        }
    }

    public final void tryResetLayerDimensAfterAnimFinished(int i) {
        if ((i == 2 || i == 1 || i == 3) && this.configurationChangedOnAnimRunning) {
            boolean z = i == 2;
            this.configurationChangedOnAnimRunning = false;
            LockScreenTransformerLayer lockScreenTransformerLayer = this.transformerLayer;
            if (lockScreenTransformerLayer != null) {
                lockScreenTransformerLayer.onScreenConfigurationChanged(z);
            }
        }
    }
}
