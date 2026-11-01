package com.miui.keyguard.editor;

import android.app.ActivityManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.Trace;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.core.content.ContextCompat;
import androidx.core.os.BundleKt;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleCoroutineScope;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import com.miui.clock.utils.FontUtils;
import com.miui.keyguard.editor.base.ActivityUIAdapter;
import com.miui.keyguard.editor.base.ExitAnimationPerformer;
import com.miui.keyguard.editor.base.TransitionLayer;
import com.miui.keyguard.editor.base.TransitionPerformer;
import com.miui.keyguard.editor.base.TransitionPerformerImpl;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.template.BitmapTempStore;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.data.template.TemplateApiImpl;
import com.miui.keyguard.editor.edit.EditFragment;
import com.miui.keyguard.editor.edit.base.CombineEditListener;
import com.miui.keyguard.editor.edit.base.EditModeChangedListener;
import com.miui.keyguard.editor.edit.base.TemplateApplier;
import com.miui.keyguard.editor.edit.color.picker.ColorPickerCache;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.guidance.UserGuidance;
import com.miui.keyguard.editor.homepage.MainFragment;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.homepage.util.TitleDescController;
import com.miui.keyguard.editor.homepage.view.CrossViewPager;
import com.miui.keyguard.editor.homepage.view.EditorLoadingView;
import com.miui.keyguard.editor.homepage.view.PageTransitionLayer;
import com.miui.keyguard.editor.track.TrackHelper;
import com.miui.keyguard.editor.track.TrackTimeUtils;
import com.miui.keyguard.editor.utils.AnimatorKt;
import com.miui.keyguard.editor.utils.ClickUtil;
import com.miui.keyguard.editor.utils.CommonUtils;
import com.miui.keyguard.editor.utils.ConstantsKt;
import com.miui.keyguard.editor.utils.DensityUtils;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.DrawablesKt;
import com.miui.keyguard.editor.utils.EditActivityTaskHelper;
import com.miui.keyguard.editor.utils.EditorObserver;
import com.miui.keyguard.editor.utils.GestureSlideUpCallback;
import com.miui.keyguard.editor.utils.GestureSlideUpHelper;
import com.miui.keyguard.editor.utils.KeyboardUtils;
import com.miui.keyguard.editor.utils.LargeFontLanguageHelper;
import com.miui.keyguard.editor.utils.MultiDeviceAdaptHelper;
import com.miui.keyguard.editor.utils.Process;
import com.miui.keyguard.editor.utils.ProcessManager;
import com.miui.keyguard.editor.utils.ScenarioRecognitionUtils;
import com.miui.keyguard.editor.utils.SystemBarController;
import com.miui.keyguard.editor.utils.ThemeUtil;
import com.miui.keyguard.editor.utils.ViewStylerKt;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.utils.WideColorGamutInterface;
import com.miui.keyguard.editor.utils.WideColorGamutManager;
import com.miui.keyguard.editor.video.VideoCheckForDepthInterceptor;
import com.miui.keyguard.editor.view.LockScreenTransformerAnimationListener;
import com.miui.keyguard.editor.view.LockScreenTransformerController;
import com.miui.keyguard.editor.view.LockScreenTransformerLayer;
import com.miui.keyguard.editor.view.TransformerInfo;
import com.miui.keyguard.editor.view.TransformerPositionInfo;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import effect_engine.utils.PublicParam;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import miuix.androidbasewidget.widget.ProgressBar;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.property.ViewProperty;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.app.AppCompatActivity;
import miuix.core.util.MiuixUIUtils;
import org.jetbrains.annotations.NotNull;

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
/* JADX INFO: compiled from: EditorActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public class EditorActivity extends AppCompatActivity implements View.OnClickListener, EditModeChangedListener, LockScreenTransformerController, TransitionLayer, LockScreenTransformerAnimationListener, ExitAnimationPerformer, DialogInterface.OnShowListener, NavigationController, GestureSlideUpCallback, CombineEditListener {
    public static boolean backgroundBlurEnable;
    public static boolean isAppliedFromCommonEditorActivity;
    public ActivityUIAdapter activityUIAdapter;
    public TextView applyButton;
    public Runnable applyCompleteRunnable;
    public View applyLoadingView;
    public boolean applySuccess;
    public ViewGroup buttonBar;
    public TextView cancelButton;
    public Bundle clockExtraArgs;
    public EditorObserver editorObserver;
    public Runnable extAnimationRunnable;
    public BroadcastReceiver finishReceiver;
    public Runnable finishRunnable;
    public boolean firstShowSystemBar;
    public FrameLayout fragmentContainer;
    public Bundle fullAodExtraArgs;
    public boolean hasInvokeFinish;
    public boolean hasStop;
    public Runnable initRunnable;
    public boolean interceptAllEventsOnApplying;
    public String isADB;
    public boolean isDirectFinishWhenBackPress;
    public boolean isEnterApplying;
    public boolean isExitAnimation;
    public boolean isFocused;
    public boolean isImmersive;
    public boolean isRelaunched;
    public boolean isShowingDialog;
    public String launchFromPackage;
    public Runnable loadCompleteRunnable;
    public LockScreenTransformerLayer lockScreenTransformerLayer;
    public MainFragment mainFragment;
    public MultiDeviceAdaptHelper multiDeviceAdaptHelper;
    public OnActivityConfigChangedListener onActivityConfigChangedListener;
    public boolean onConfigChanging;
    public int orientation;
    public boolean orientationLockedForApplying;
    public boolean orientationLockedForTransition;
    public boolean pickWallpaperSetButtonDisbable;
    public ViewGroup rootView;
    public boolean shouldFillDataToCrossViewPager;
    public AlertDialog showingDialog;
    public PageTransitionLayer transitionContainer;
    public boolean wallpaperPickerShowing;
    public static final Companion Companion = new Companion(null);
    public static String tackId = "";
    public long startInterceptTime = -1;
    public final EditActivityTaskHelper taskHelper = new EditActivityTaskHelper(new WeakReference(this));
    public GestureSlideUpHelper gestureSlideUpHelper = new GestureSlideUpHelper();
    public final Lazy wideColorGamutListener$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.EditorActivity$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return EditorActivity.wideColorGamutListener_delegate$lambda$0(this.f$0);
        }
    });
    public final EditorActivity$onBackCallback$1 onBackCallback = new OnBackPressedCallback() { // from class: com.miui.keyguard.editor.EditorActivity$onBackCallback$1
        {
            super(true);
        }

        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            boolean zIsForwardTransitionRunning = this.this$0.isForwardTransitionRunning();
            PageTransitionLayer pageTransitionLayer = this.this$0.transitionContainer;
            if (pageTransitionLayer == null) {
                Intrinsics.throwUninitializedPropertyAccessException("transitionContainer");
                pageTransitionLayer = null;
            }
            boolean z = pageTransitionLayer.getVisibility() == 0;
            boolean zIsShowApplyLoading = this.this$0.isShowApplyLoading();
            if (ClickUtil.INSTANCE.isFastDoubleClick() || this.this$0.isExitAnimation) {
                Log.i("Keyguard-Editor-EditorActivity", "onBackPressed intercepted by fast double click, isExitAnimation=" + this.this$0.isExitAnimation);
                return;
            }
            if (zIsForwardTransitionRunning || z || zIsShowApplyLoading) {
                Log.i("Keyguard-Editor-EditorActivity", "onBackPressed intercepted");
                return;
            }
            Log.i("Keyguard-Editor-EditorActivity", "onBackPressed");
            if (this.this$0.getWallpaperPickerShowing()) {
                this.this$0.setWallpaperPickerShowing(false);
            } else {
                setEnabled(false);
                this.this$0.getOnBackPressedDispatcher().onBackPressed();
            }
        }
    };
    public EditorOrientationEventListener orientationListener = new EditorOrientationEventListener(new WeakReference(this));
    public WeakReference weakRef = new WeakReference(this);
    public final Runnable hideSystemBarVisibleRunnable = new Runnable() { // from class: com.miui.keyguard.editor.EditorActivity$$ExternalSyntheticLambda3
        @Override // java.lang.Runnable
        public final void run() {
            EditorActivity.hideSystemBarVisibleRunnable$lambda$21(this.f$0);
        }
    };
    public int savedOrientationBeforeTransition = -1;
    public final Runnable orientationUnlockRunnable = new Runnable() { // from class: com.miui.keyguard.editor.EditorActivity$$ExternalSyntheticLambda4
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.restoreOrientationAfterTransition();
        }
    };
    public int savedOrientationBeforeApplying = -1;
    public final Runnable applyOrientationUnlockRunnable = new Runnable() { // from class: com.miui.keyguard.editor.EditorActivity$$ExternalSyntheticLambda5
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.restoreOrientationAfterApplying();
        }
    };
    public Lazy transitionPerformer = LazyKt.lazy(LazyThreadSafetyMode.NONE, new Function0() { // from class: com.miui.keyguard.editor.EditorActivity$$ExternalSyntheticLambda6
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return EditorActivity.transitionPerformer$lambda$36(this.f$0);
        }
    });

    /* JADX INFO: compiled from: EditorActivity.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String getTackId() {
            return EditorActivity.tackId;
        }
    }

    /* JADX INFO: compiled from: EditorActivity.kt */
    @Metadata
    public interface NavigationActionListener {

        /* JADX INFO: compiled from: EditorActivity.kt */
        @Metadata
        public static final class DefaultImpls {
            public static /* synthetic */ boolean onApply$default(NavigationActionListener navigationActionListener, Consumer consumer, LifecycleOwner lifecycleOwner, int i, Object obj) {
                if (obj != null) {
                    throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onApply");
                }
                if ((i & 1) != 0) {
                    consumer = null;
                }
                if ((i & 2) != 0) {
                    lifecycleOwner = null;
                }
                return navigationActionListener.onApply(consumer, lifecycleOwner);
            }

            public static /* synthetic */ boolean onCancel$default(NavigationActionListener navigationActionListener, Consumer consumer, int i, Object obj) {
                if (obj != null) {
                    throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onCancel");
                }
                if ((i & 1) != 0) {
                    consumer = null;
                }
                return navigationActionListener.onCancel(consumer);
            }
        }

        void beforeApply();

        boolean onApply(Consumer consumer, LifecycleOwner lifecycleOwner);

        boolean onCancel(Consumer consumer);
    }

    /* JADX INFO: compiled from: EditorActivity.kt */
    @Metadata
    public interface UserOperationListener {
        void onUserOperation();
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.EditorActivity$onResume$1, reason: invalid class name */
    /* JADX INFO: compiled from: EditorActivity.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.EditorActivity$onResume$1", f = "EditorActivity.kt", l = {697}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        /* JADX INFO: renamed from: com.miui.keyguard.editor.EditorActivity$onResume$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: EditorActivity.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.EditorActivity$onResume$1$1", f = "EditorActivity.kt", l = {698}, m = "invokeSuspend")
        public static final class C00211 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public int label;

            public C00211(Continuation continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00211(continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00211) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.label = 1;
                    if (DelayKt.delay(150L, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EditorActivity.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineDispatcher coroutineDispatcher = Dispatchers.getDefault();
                C00211 c00211 = new C00211(null);
                this.label = 1;
                if (BuildersKt.withContext(coroutineDispatcher, c00211, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            if (EditorActivity.this.hasWindowFocus()) {
                EditorActivity.this.setWallpaperPickerShowing(false);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.EditorActivity$onResume$2, reason: invalid class name */
    /* JADX INFO: compiled from: EditorActivity.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.EditorActivity$onResume$2", f = "EditorActivity.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EditorActivity.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ThemeUtil.INSTANCE.makeWallpaperDirExists();
            WallpaperController companion = WallpaperController.Companion.getInstance();
            if (companion != null) {
                companion.refreshMiuiWallpaperInfo(EditorActivity.this);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.EditorActivity$setCancelButtonAccessForMainFragment$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: EditorActivity.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.EditorActivity$setCancelButtonAccessForMainFragment$1", f = "EditorActivity.kt", l = {1016}, m = "invokeSuspend")
    public static final class C00871 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public C00871(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EditorActivity.this.new C00871(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C00871) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(2000L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            EditorActivity.this.setCancelButtonAccessibility();
            return Unit.INSTANCE;
        }
    }

    public static final void hideSystemBarVisibleRunnable$lambda$21(EditorActivity editorActivity) {
        EditorActivity editorActivity2;
        WeakReference weakReference = editorActivity.weakRef;
        if (weakReference == null || (editorActivity2 = (EditorActivity) weakReference.get()) == null) {
            return;
        }
        editorActivity2.setupSystemBar(false);
    }

    public static final void initFragmentManagerBackStack$lambda$5(EditorActivity editorActivity) {
        try {
            editorActivity.onTopFragmentChanged(editorActivity.getSupportFragmentManager().findFragmentById(R.id.kg_fragment_container));
        } catch (Exception e) {
            Log.e("Keyguard-Editor-EditorActivity", "onBackStackChanged", e);
        }
    }

    public static final Unit onClick$lambda$16(EditorActivity editorActivity) {
        Log.e("Keyguard-Editor-EditorActivity", "currentNavigationActionListener error when apply");
        editorActivity.finishActivity();
        return Unit.INSTANCE;
    }

    public static final Unit onClick$lambda$17(EditorActivity editorActivity) {
        Log.e("Keyguard-Editor-EditorActivity", "currentNavigationActionListener error when cancel");
        editorActivity.finishActivity();
        return Unit.INSTANCE;
    }

    public static final void onCreate$lambda$1(EditorActivity editorActivity) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        ProcessManager.INSTANCE.onEditorCreate();
        editorActivity.initFragmentManagerBackStack();
        editorActivity.trackEditorOpen();
        editorActivity.setupApplyLoadingView();
        editorActivity.printDeviceInfo();
        editorActivity.printBundle(editorActivity.clockExtraArgs);
        editorActivity.printBundle(editorActivity.fullAodExtraArgs);
        Log.i("Keyguard-Editor-EditorActivity", "rootView costTime =" + (System.currentTimeMillis() - jCurrentTimeMillis));
    }

    public static final void onDataLoadCompleted$lambda$28(final EditorActivity editorActivity) {
        ViewTreeObserver viewTreeObserver;
        ViewGroup viewGroup = editorActivity.rootView;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rootView");
            viewGroup = null;
        }
        viewGroup.setVisibility(0);
        LockScreenTransformerLayer lockScreenTransformerLayer = editorActivity.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer != null) {
            lockScreenTransformerLayer.setVisibility(0);
        }
        LockScreenTransformerLayer lockScreenTransformerLayer2 = editorActivity.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer2 == null || (viewTreeObserver = lockScreenTransformerLayer2.getViewTreeObserver()) == null) {
            return;
        }
        viewTreeObserver.registerFrameCommitCallback(new Runnable() { // from class: com.miui.keyguard.editor.EditorActivity$onDataLoadCompleted$1$1
            @Override // java.lang.Runnable
            public void run() {
                ViewTreeObserver viewTreeObserver2;
                this.this$0.shouldFillDataToCrossViewPager = true;
                LockScreenTransformerLayer lockScreenTransformerLayer3 = this.this$0.lockScreenTransformerLayer;
                if (lockScreenTransformerLayer3 != null && (viewTreeObserver2 = lockScreenTransformerLayer3.getViewTreeObserver()) != null) {
                    viewTreeObserver2.unregisterFrameCommitCallback(this);
                }
                this.this$0.waitWallpaperFirstFrameThenSendEditorReady();
            }
        });
    }

    public static final void requestExitEditorFromMainFragment$lambda$26(EditorActivity editorActivity, TransformerInfo transformerInfo, float f) {
        LockScreenTransformerLayer lockScreenTransformerLayer = editorActivity.lockScreenTransformerLayer;
        Pair pair = TuplesKt.to("finalScaleX", Float.valueOf(lockScreenTransformerLayer != null ? lockScreenTransformerLayer.getFinalScaleX() : 1.0f));
        LockScreenTransformerLayer lockScreenTransformerLayer2 = editorActivity.lockScreenTransformerLayer;
        Pair pair2 = TuplesKt.to("finalScaleY", Float.valueOf(lockScreenTransformerLayer2 != null ? lockScreenTransformerLayer2.getFinalScaleY() : 1.0f));
        LockScreenTransformerLayer lockScreenTransformerLayer3 = editorActivity.lockScreenTransformerLayer;
        editorActivity.sendRequestExitEditorCommandToKeyguard(BundleKt.bundleOf(pair, pair2, TuplesKt.to("finalTranslationY", Float.valueOf(lockScreenTransformerLayer3 != null ? lockScreenTransformerLayer3.getFinalTranslationY() : 0.0f)), TuplesKt.to("isRealClockTime", Boolean.valueOf(transformerInfo == null)), TuplesKt.to("finalCornerRadius", Float.valueOf(f))));
    }

    public static final void setupTransformerLayer$lambda$10$lambda$9(View view) {
    }

    public static final TransitionPerformer transitionPerformer$lambda$36(EditorActivity editorActivity) {
        return TransitionPerformer.Companion.getImpl(editorActivity);
    }

    public static final Unit waitWallpaperFirstFrameThenSendEditorReady$lambda$29(Ref.BooleanRef booleanRef, long j, String reason) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        if (!booleanRef.element) {
            booleanRef.element = true;
            Log.i("Keyguard-Editor-EditorActivity", "onDataLoadCompleted: sendEditorReadyCommand reason=" + reason + " cost=" + (SystemClock.elapsedRealtime() - j));
            EditorServiceManager.Companion.getInstance().sendEditorReadyCommand();
            ProcessManager.INSTANCE.setLaunchingEditorFromLockScreen(false);
        }
        return Unit.INSTANCE;
    }

    public static final Unit waitWallpaperFirstFrameThenSendEditorReady$lambda$31(EditorActivity editorActivity, Runnable runnable, Function1 function1) {
        LockScreenTransformerLayer lockScreenTransformerLayer = editorActivity.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer != null) {
            lockScreenTransformerLayer.removeCallbacks(runnable);
        }
        function1.invoke("wallpaper_first_frame");
        return Unit.INSTANCE;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.miui.keyguard.editor.EditorActivity$wideColorGamutListener$2$1] */
    public static final EditorActivity$wideColorGamutListener$2$1 wideColorGamutListener_delegate$lambda$0(final EditorActivity editorActivity) {
        return new WideColorGamutInterface() { // from class: com.miui.keyguard.editor.EditorActivity$wideColorGamutListener$2$1
            @Override // com.miui.keyguard.editor.utils.WideColorGamutInterface
            public void onColorModeChanged() {
                this.this$0.finish();
            }
        };
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    public void attachBaseContext(Context newBase) {
        Intrinsics.checkNotNullParameter(newBase, "newBase");
        TrackTimeUtils.INSTANCE.beginTimeCalculate("editor_start");
        long jCurrentTimeMillis = System.currentTimeMillis();
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Bitmap.setDefaultDensity(DeviceUtil.initialDensityDpi$default(deviceUtil, 0, 1, null));
        super.attachBaseContext(newBase.createConfigurationContext(deviceUtil.initialConfiguration(newBase)));
        Log.i("Keyguard-Editor-EditorActivity", "attachBaseContext costTime=" + (System.currentTimeMillis() - jCurrentTimeMillis));
    }

    public final boolean callingFromLockCross() {
        return Intrinsics.areEqual(getLaunchedFromCaller(), "lock_negative_one");
    }

    public final boolean callingFromSystemUI() {
        String str = this.launchFromPackage;
        return (str == null || str.length() == 0) ? Intrinsics.areEqual("com.android.systemui", getLaunchedFromPackage()) : Intrinsics.areEqual(this.launchFromPackage, "systemui");
    }

    public final boolean callingFromTheme() {
        String str = this.launchFromPackage;
        return (str == null || str.length() == 0) ? Intrinsics.areEqual("com.android.thememanager", getLaunchedFromPackage()) : Intrinsics.areEqual(this.launchFromPackage, "thememanager");
    }

    public final void changeCancelButtonDecorateBackground(boolean z, boolean z2) {
        if (ViewStylerKt.canUseBlur()) {
            return;
        }
        TextView textView = this.cancelButton;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cancelButton");
            textView = null;
        }
        TextView textView2 = textView;
        ViewUtil.setThrottledClickListener$default(ViewUtil.INSTANCE, textView2, this, 0L, 2, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getMain(), null, new EditorActivity$changeCancelButtonDecorateBackground$1$1(z, textView2, this, z2, null), 2, null);
    }

    public final void changeIsDismissKeyguardWhenPause(boolean z) {
        EditorObserver editorObserver = this.editorObserver;
        if (editorObserver != null) {
            editorObserver.setShouldTryDismissKeyguardWhenStop(z);
        }
    }

    public final void clearDelayedCallback() {
        try {
            Result.Companion companion = Result.Companion;
            Log.d("Keyguard-Editor-EditorActivity", "clearDelayedCallback");
            getWindow().getDecorView().removeCallbacks(this.hideSystemBarVisibleRunnable);
            ViewGroup viewGroup = this.rootView;
            if (viewGroup == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rootView");
                viewGroup = null;
            }
            viewGroup.removeCallbacks(this.initRunnable);
            ViewGroup viewGroup2 = this.rootView;
            if (viewGroup2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rootView");
                viewGroup2 = null;
            }
            viewGroup2.removeCallbacks(this.applyCompleteRunnable);
            ViewGroup viewGroup3 = this.rootView;
            if (viewGroup3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rootView");
                viewGroup3 = null;
            }
            viewGroup3.removeCallbacks(this.finishRunnable);
            LockScreenTransformerLayer lockScreenTransformerLayer = this.lockScreenTransformerLayer;
            if (lockScreenTransformerLayer != null) {
                lockScreenTransformerLayer.removeCallbacks(this.extAnimationRunnable);
            }
            LockScreenTransformerLayer lockScreenTransformerLayer2 = this.lockScreenTransformerLayer;
            Result.m1061constructorimpl(lockScreenTransformerLayer2 != null ? Boolean.valueOf(lockScreenTransformerLayer2.removeCallbacks(this.loadCompleteRunnable)) : null);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
    }

    public final void continueToFillCrossViewPagerData() {
        MainFragment mainFragment = this.mainFragment;
        if (mainFragment != null) {
            mainFragment.fillViewPagerData();
        }
    }

    public final BroadcastReceiver createAndRegisterFinishBroadCast() {
        if (this instanceof CommonEditorActivity) {
            return null;
        }
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.miui.keyguard.editor.EditorActivity$createAndRegisterFinishBroadCast$finishReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                this.this$0.finish();
            }
        };
        ContextCompat.registerReceiver(this, broadcastReceiver, new IntentFilter("com.miui.aod.action_finish_editor_activity"), 4);
        return broadcastReceiver;
    }

    public final void detachMainFragment() {
        MainFragment mainFragment;
        Log.d("Keyguard-Editor-EditorActivity", "detachMainFragment in apply from=" + callingFromSystemUI());
        if (!callingFromSystemUI() || (mainFragment = this.mainFragment) == null) {
            return;
        }
        getSupportFragmentManager().beginTransaction().remove(mainFragment).commit();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        if (motionEvent != null) {
            dispatchTouchEvent(motionEvent);
        }
        return super.dispatchGenericMotionEvent(motionEvent);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        UserOperationListener currentUserOperationListener = getCurrentUserOperationListener();
        if (currentUserOperationListener != null) {
            currentUserOperationListener.onUserOperation();
        }
        if (this.isEnterApplying) {
            Log.d("Keyguard-Editor-EditorActivity", "EditorActivity is enter applying");
            return true;
        }
        long jUptimeMillis = SystemClock.uptimeMillis() - this.startInterceptTime;
        if ((!this.interceptAllEventsOnApplying || jUptimeMillis > 4000) && !this.applySuccess) {
            return super.dispatchTouchEvent(motionEvent);
        }
        Log.w("Keyguard-Editor-EditorActivity", "dispatchTouchEvent intercepted: it is applying now.");
        return false;
    }

    public final void exitWithoutUnLockCommand() {
        LockScreenTransformerLayer lockScreenTransformerLayer = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer != null) {
            lockScreenTransformerLayer.startExitTransformerAnimationWithShowBouncer();
        }
    }

    @Override // miuix.appcompat.app.AppCompatActivity, android.app.Activity
    public void finish() {
        super.finish();
        Log.i("Keyguard-Editor-EditorActivity", "editor finish");
        if (CommonUtils.INSTANCE.isActivityPinned(getApplicationContext())) {
            Log.i("Keyguard-Editor-EditorActivity", "reset when pinned");
            this.applySuccess = false;
            MainFragment mainFragment = this.mainFragment;
            if (mainFragment != null) {
                mainFragment.resetStateWhenPinned();
            }
        }
    }

    public final void finishActivity() {
        KeyboardUtils.INSTANCE.hideSoftInput(this);
        if (isFinishing()) {
            return;
        }
        if (this.applySuccess || callingFromSystemUI()) {
            ProcessManager.INSTANCE.finishAllActivity("apply success");
        } else {
            finish();
        }
    }

    public final void finishEditorTask() {
        try {
            Object systemService = getSystemService("activity");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.ActivityManager");
            List<ActivityManager.AppTask> appTasks = ((ActivityManager) systemService).getAppTasks();
            String packageName = getPackageName();
            for (ActivityManager.AppTask appTask : appTasks) {
                if (!appTask.getTaskInfo().isRunning) {
                    if (appTask.getTaskInfo().baseActivity != null) {
                        ComponentName componentName = appTask.getTaskInfo().baseActivity;
                        if (Intrinsics.areEqual(componentName != null ? componentName.getPackageName() : null, packageName)) {
                        }
                    }
                    appTask.finishAndRemoveTask();
                }
            }
        } catch (Exception e) {
            Log.d("Keyguard-Editor-EditorActivity", "finishEditorTask " + e.getMessage());
        }
    }

    public final void finishWhenLocked() {
        LockScreenTransformerLayer lockScreenTransformerLayer = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer == null || !lockScreenTransformerLayer.isEnterAnimWaitUnlock()) {
            Object systemService = getSystemService("power");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.os.PowerManager");
            boolean zIsInteractive = ((PowerManager) systemService).isInteractive();
            Log.i("Keyguard-Editor-EditorActivity", "isInteractive " + zIsInteractive);
            if (zIsInteractive) {
                return;
            }
        }
        Log.i("Keyguard-Editor-EditorActivity", "finishWhenLocked");
        this.hasInvokeFinish = true;
        finishActivity();
    }

    public final NavigationActionListener getCurrentNavigationActionListener() {
        ActivityResultCaller activityResultCallerFindFragmentById = getSupportFragmentManager().findFragmentById(R.id.kg_fragment_container);
        if (activityResultCallerFindFragmentById instanceof NavigationActionListener) {
            return (NavigationActionListener) activityResultCallerFindFragmentById;
        }
        return null;
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public int getCurrentSelect() {
        return CombineEditListener.DefaultImpls.getCurrentSelect(this);
    }

    public final UserOperationListener getCurrentUserOperationListener() {
        ActivityResultCaller activityResultCallerFindFragmentById = getSupportFragmentManager().findFragmentById(R.id.kg_fragment_container);
        if (activityResultCallerFindFragmentById instanceof UserOperationListener) {
            return (UserOperationListener) activityResultCallerFindFragmentById;
        }
        return null;
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public int getDialogOffset() {
        return CombineEditListener.DefaultImpls.getDialogOffset(this);
    }

    public final FrameLayout getFragmentContainer() {
        return this.fragmentContainer;
    }

    public Bundle getFullAodExtraArgs() {
        return this.fullAodExtraArgs;
    }

    public final String getLaunchFromPackage() {
        return this.launchFromPackage;
    }

    public final String getLaunchedFromCaller() {
        Intent intent = getIntent();
        if (intent != null) {
            return intent.getStringExtra("caller");
        }
        return null;
    }

    public final LockScreenTransformerLayer getLockScreenTransformerLayer() {
        return this.lockScreenTransformerLayer;
    }

    public final MainFragment getMainFragment() {
        return this.mainFragment;
    }

    public final boolean getOnConfigChanging() {
        return this.onConfigChanging;
    }

    public int getTheme(boolean z) {
        Log.d("Keyguard-Editor-EditorActivity", "getTheme: isNight=" + z);
        return z ? R.style.KgEditorActivityThemeNight : R.style.KgEditorActivityThemeLight;
    }

    @Override // com.miui.keyguard.editor.base.TransitionLayer
    public PageTransitionLayer getTransitionContainer() {
        PageTransitionLayer pageTransitionLayer = this.transitionContainer;
        if (pageTransitionLayer != null) {
            return pageTransitionLayer;
        }
        Intrinsics.throwUninitializedPropertyAccessException("transitionContainer");
        return null;
    }

    @Override // com.miui.keyguard.editor.base.TransitionLayer
    public TransitionPerformer getTransitionPerformer() {
        return (TransitionPerformer) this.transitionPerformer.getValue();
    }

    public final boolean getWallpaperPickerShowing() {
        return this.wallpaperPickerShowing;
    }

    public final EditorActivity$wideColorGamutListener$2$1 getWideColorGamutListener() {
        return (EditorActivity$wideColorGamutListener$2$1) this.wideColorGamutListener$delegate.getValue();
    }

    public final void hideLoadingView() {
        if (isShowApplyLoading()) {
            showOrHideApplyLoading(false);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void initContentView() {
        ViewGroup viewGroup;
        FrameLayout frameLayout = new FrameLayout(this);
        this.rootView = frameLayout;
        int i = R.color.kg_editor_background;
        frameLayout.setBackgroundResource(i);
        ViewGroup viewGroup2 = this.rootView;
        AttributeSet attributeSet = null;
        Object[] objArr = 0;
        if (viewGroup2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rootView");
            viewGroup2 = null;
        }
        viewGroup2.setId(R.id.kg_editor_background);
        FrameLayout frameLayout2 = new FrameLayout(this);
        this.fragmentContainer = frameLayout2;
        Intrinsics.checkNotNull(frameLayout2);
        frameLayout2.setId(R.id.kg_fragment_container);
        ViewGroup viewGroup3 = this.rootView;
        if (viewGroup3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rootView");
            viewGroup3 = null;
        }
        viewGroup3.addView(this.fragmentContainer, new ViewGroup.LayoutParams(-1, -1));
        LockScreenTransformerLayer lockScreenTransformerLayer = new LockScreenTransformerLayer(this, attributeSet, 2, objArr == true ? 1 : 0);
        this.lockScreenTransformerLayer = lockScreenTransformerLayer;
        Intrinsics.checkNotNull(lockScreenTransformerLayer);
        int i2 = R.id.kg_transformer_layer;
        lockScreenTransformerLayer.setId(i2);
        LockScreenTransformerLayer lockScreenTransformerLayer2 = this.lockScreenTransformerLayer;
        Intrinsics.checkNotNull(lockScreenTransformerLayer2);
        lockScreenTransformerLayer2.setBackgroundResource(i);
        LockScreenTransformerLayer lockScreenTransformerLayer3 = this.lockScreenTransformerLayer;
        Intrinsics.checkNotNull(lockScreenTransformerLayer3);
        lockScreenTransformerLayer3.setVisibility(4);
        LockScreenTransformerLayer lockScreenTransformerLayer4 = this.lockScreenTransformerLayer;
        Intrinsics.checkNotNull(lockScreenTransformerLayer4);
        lockScreenTransformerLayer4.inflateAndInit(true);
        ViewGroup viewGroup4 = this.rootView;
        if (viewGroup4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rootView");
            viewGroup4 = null;
        }
        viewGroup4.addView(this.lockScreenTransformerLayer, new ViewGroup.LayoutParams(-1, -1));
        FrameLayout frameLayout3 = new FrameLayout(this);
        this.buttonBar = frameLayout3;
        frameLayout3.setId(R.id.kg_editor_button_bar);
        ViewGroup viewGroup5 = this.buttonBar;
        if (viewGroup5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("buttonBar");
            viewGroup5 = null;
        }
        Resources resources = getResources();
        int i3 = R.dimen.kg_top_button_bar_padding_horizontal;
        viewGroup5.setPadding(resources.getDimensionPixelSize(i3), DeviceUtil.INSTANCE.isQ18() ? getResources().getDimensionPixelSize(R.dimen.kg_top_button_bar_padding_top) : 0, getResources().getDimensionPixelSize(i3), getResources().getDimensionPixelSize(R.dimen.kg_top_button_bar_padding_bottom));
        TextView textView = new TextView(this);
        this.cancelButton = textView;
        int i4 = R.id.kg_btn_editor_cancel;
        textView.setId(i4);
        TextView textView2 = this.cancelButton;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cancelButton");
            textView2 = null;
        }
        textView2.setText(this instanceof CommonEditorActivity ? ((CommonEditorActivity) this).getResources().getString(R.string.kg_dialog_apply_super_wallpaper_negative_button) : getResources().getString(R.string.kg_exit));
        TextView textView3 = this.cancelButton;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cancelButton");
            textView3 = null;
        }
        textView3.setTextColor(getResources().getColor(android.R.color.white));
        TextView textView4 = this.cancelButton;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cancelButton");
            textView4 = null;
        }
        Resources resources2 = getResources();
        int i5 = R.dimen.kg_top_button_text_size;
        textView4.setTextSize(0, resources2.getDimension(i5));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        TextView textView5 = this.cancelButton;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cancelButton");
            textView5 = null;
        }
        Resources resources3 = getResources();
        int i6 = R.dimen.kg_top_button_padding_horizontal;
        int dimensionPixelSize = resources3.getDimensionPixelSize(i6);
        Resources resources4 = getResources();
        int i7 = R.dimen.kg_top_button_padding_vertical;
        textView5.setPadding(dimensionPixelSize, resources4.getDimensionPixelSize(i7), getResources().getDimensionPixelSize(i6), getResources().getDimensionPixelSize(i7));
        TextView textView6 = this.cancelButton;
        if (textView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cancelButton");
            textView6 = null;
        }
        setMiProMediumFontFamily(textView6);
        ViewUtil viewUtil = ViewUtil.INSTANCE;
        View view = this.cancelButton;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cancelButton");
            view = null;
        }
        viewUtil.applyPressedState(view);
        TextView textView7 = new TextView(this);
        this.applyButton = textView7;
        textView7.setId(R.id.kg_btn_editor_apply);
        TextView textView8 = this.applyButton;
        if (textView8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("applyButton");
            textView8 = null;
        }
        textView8.setText(getResources().getString(R.string.kg_apply));
        TextView textView9 = this.applyButton;
        if (textView9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("applyButton");
            textView9 = null;
        }
        textView9.setContentDescription(getResources().getString(R.string.kg_talkback_apply_wallpaper));
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-2, -2);
        layoutParams2.gravity = 8388613;
        TextView textView10 = this.applyButton;
        if (textView10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("applyButton");
            textView10 = null;
        }
        textView10.setTextColor(getResources().getColor(android.R.color.white));
        TextView textView11 = this.applyButton;
        if (textView11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("applyButton");
            textView11 = null;
        }
        textView11.setTextSize(0, getResources().getDimension(i5));
        TextView textView12 = this.applyButton;
        if (textView12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("applyButton");
            textView12 = null;
        }
        textView12.setPadding(getResources().getDimensionPixelSize(i6), getResources().getDimensionPixelSize(i7), getResources().getDimensionPixelSize(i6), getResources().getDimensionPixelSize(i7));
        TextView textView13 = this.applyButton;
        if (textView13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("applyButton");
            textView13 = null;
        }
        setMiProMediumFontFamily(textView13);
        View view2 = this.applyButton;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("applyButton");
            view2 = null;
        }
        viewUtil.applyPressedState(view2);
        TextView textView14 = this.applyButton;
        if (textView14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("applyButton");
            textView14 = null;
        }
        textView14.setAccessibilityTraversalAfter(i4);
        ViewGroup viewGroup6 = this.buttonBar;
        if (viewGroup6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("buttonBar");
            viewGroup6 = null;
        }
        TextView textView15 = this.cancelButton;
        if (textView15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cancelButton");
            textView15 = null;
        }
        viewGroup6.addView(textView15, layoutParams);
        ViewGroup viewGroup7 = this.buttonBar;
        if (viewGroup7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("buttonBar");
            viewGroup7 = null;
        }
        TextView textView16 = this.applyButton;
        if (textView16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("applyButton");
            textView16 = null;
        }
        viewGroup7.addView(textView16, layoutParams2);
        ViewGroup viewGroup8 = this.rootView;
        if (viewGroup8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rootView");
            viewGroup8 = null;
        }
        ViewGroup viewGroup9 = this.buttonBar;
        if (viewGroup9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("buttonBar");
            viewGroup9 = null;
        }
        viewGroup8.addView(viewGroup9, new ViewGroup.LayoutParams(-1, -2));
        PageTransitionLayer pageTransitionLayer = new PageTransitionLayer(this, null, 2, null);
        this.transitionContainer = pageTransitionLayer;
        pageTransitionLayer.setId(i2);
        PageTransitionLayer pageTransitionLayer2 = this.transitionContainer;
        if (pageTransitionLayer2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transitionContainer");
            pageTransitionLayer2 = null;
        }
        pageTransitionLayer2.setTranslationZ(DensityUtils.dp2Px(5));
        PageTransitionLayer pageTransitionLayer3 = this.transitionContainer;
        if (pageTransitionLayer3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transitionContainer");
            pageTransitionLayer3 = null;
        }
        pageTransitionLayer3.setVisibility(8);
        PageTransitionLayer pageTransitionLayer4 = this.transitionContainer;
        if (pageTransitionLayer4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transitionContainer");
            pageTransitionLayer4 = null;
        }
        pageTransitionLayer4.setImportantForAccessibility(2);
        ViewGroup viewGroup10 = this.rootView;
        if (viewGroup10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rootView");
            viewGroup10 = null;
        }
        PageTransitionLayer pageTransitionLayer5 = this.transitionContainer;
        if (pageTransitionLayer5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transitionContainer");
            pageTransitionLayer5 = null;
        }
        viewGroup10.addView(pageTransitionLayer5, new ViewGroup.LayoutParams(-1, -1));
        LargeFontLanguageHelper largeFontLanguageHelper = LargeFontLanguageHelper.INSTANCE;
        TextView textView17 = this.applyButton;
        if (textView17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("applyButton");
            textView17 = null;
        }
        TextView textView18 = this.cancelButton;
        if (textView18 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cancelButton");
            textView18 = null;
        }
        largeFontLanguageHelper.adaptTopButtonForLargeFontLanguage(textView17, textView18);
        ViewGroup viewGroup11 = this.rootView;
        if (viewGroup11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rootView");
            viewGroup = null;
        } else {
            viewGroup = viewGroup11;
        }
        setContentView(viewGroup, new ViewGroup.LayoutParams(-1, -1));
    }

    public final void initFragmentManagerBackStack() {
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() { // from class: com.miui.keyguard.editor.EditorActivity$$ExternalSyntheticLambda13
            @Override // androidx.fragment.app.FragmentManager.OnBackStackChangedListener
            public final void onBackStackChanged() {
                EditorActivity.initFragmentManagerBackStack$lambda$5(this.f$0);
            }
        });
    }

    public final void initMultiDeviceAdaptHelper() {
        MultiDeviceAdaptHelper multiDeviceAdaptHelper = new MultiDeviceAdaptHelper(this);
        this.multiDeviceAdaptHelper = multiDeviceAdaptHelper;
        multiDeviceAdaptHelper.onActivityCreate();
    }

    public final boolean isAppFunctionCall() {
        return Intrinsics.areEqual(this.launchFromPackage, "appfunction");
    }

    @Override // com.miui.keyguard.editor.utils.GestureSlideUpCallback
    public boolean isAvailable() {
        return !isFinishing();
    }

    public final boolean isDirectFinishWhenBackPress() {
        boolean z = this.isDirectFinishWhenBackPress;
        this.isDirectFinishWhenBackPress = false;
        return z;
    }

    public final boolean isForwardTransitionRunning() {
        AtomicInteger forwardStatus;
        TransitionPerformer transitionPerformer = getTransitionPerformer();
        TransitionPerformerImpl transitionPerformerImpl = transitionPerformer instanceof TransitionPerformerImpl ? (TransitionPerformerImpl) transitionPerformer : null;
        return (transitionPerformerImpl == null || (forwardStatus = transitionPerformerImpl.getForwardStatus()) == null || forwardStatus.get() == TransitionPerformer.Companion.getFORWARD_FREE()) ? false : true;
    }

    public final boolean isImmersiveModeEnabledV30(Window window) {
        WindowInsetsController insetsController = window.getInsetsController();
        return insetsController != null && insetsController.getSystemBarsBehavior() == 2;
    }

    public final boolean isMiuiCall() {
        String launchedFromPackage = getLaunchedFromPackage();
        Log.d("Keyguard-Editor-EditorActivity", "launchPackage=" + launchedFromPackage);
        if (Intrinsics.areEqual(launchedFromPackage, "com.android.shell") && Intrinsics.areEqual(this.isADB, "adb")) {
            ConstantsKt.getCALL_PACKAGE_ALLOW().add("com.android.shell");
        }
        return ConstantsKt.getCALL_PACKAGE_ALLOW().contains(launchedFromPackage);
    }

    public boolean isShowApplyLoading() {
        View view = this.applyLoadingView;
        if (view != null && view != null && view.getVisibility() == 0) {
            View view2 = this.applyLoadingView;
            Float fValueOf = view2 != null ? Float.valueOf(view2.getAlpha()) : null;
            Intrinsics.checkNotNull(fValueOf);
            if (fValueOf.floatValue() > 0.0f) {
                return true;
            }
        }
        return false;
    }

    public final void lockOrientationForApplying() {
        if (this.orientationLockedForApplying) {
            return;
        }
        this.savedOrientationBeforeApplying = this.orientationLockedForTransition ? this.savedOrientationBeforeTransition : getRequestedOrientation();
        this.orientationLockedForApplying = true;
        setRequestedOrientation(14);
        getWindow().getDecorView().removeCallbacks(this.applyOrientationUnlockRunnable);
        getWindow().getDecorView().postDelayed(this.applyOrientationUnlockRunnable, 30000L);
    }

    public final void lockOrientationForTransition() {
        if (this.orientationLockedForTransition) {
            return;
        }
        this.savedOrientationBeforeTransition = getRequestedOrientation();
        this.orientationLockedForTransition = true;
        setRequestedOrientation(14);
        getWindow().getDecorView().removeCallbacks(this.orientationUnlockRunnable);
        getWindow().getDecorView().postDelayed(this.orientationUnlockRunnable, 5000L);
    }

    public void navigateToFirstFragment() {
        MainFragment mainFragment = new MainFragment();
        mainFragment.setArguments(this.clockExtraArgs);
        this.mainFragment = mainFragment;
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        int i = R.id.kg_fragment_container;
        MainFragment mainFragment2 = this.mainFragment;
        Intrinsics.checkNotNull(mainFragment2);
        fragmentTransactionBeginTransaction.add(i, mainFragment2, "EDITOR_MAIN_FRAGMENT").addToBackStack("EDITOR_MAIN_FRAGMENT").commit();
        setButtonBarVisbility(true);
    }

    @Override // com.miui.keyguard.editor.view.LockScreenTransformerController
    public void onApplyCompleted(final boolean z, final TransformerInfo transformerInfo) {
        Log.i("Keyguard-Editor-EditorActivity", "onApplyCompleted: " + z + " showLoading:" + isShowApplyLoading());
        if (isShowApplyLoading()) {
            ViewGroup viewGroup = this.rootView;
            ViewGroup viewGroup2 = null;
            if (viewGroup == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rootView");
                viewGroup = null;
            }
            viewGroup.removeCallbacks(this.applyCompleteRunnable);
            this.applyCompleteRunnable = new Runnable() { // from class: com.miui.keyguard.editor.EditorActivity$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.onApplyCompletedAction(z, transformerInfo);
                }
            };
            ViewGroup viewGroup3 = this.rootView;
            if (viewGroup3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rootView");
            } else {
                viewGroup2 = viewGroup3;
            }
            viewGroup2.post(this.applyCompleteRunnable);
        } else {
            onApplyCompletedAction(z, transformerInfo);
        }
        if (!z) {
            changeIsDismissKeyguardWhenPause(true);
        }
        onApplyEndWhenPinned();
    }

    public final void onApplyCompletedAction(boolean z, TransformerInfo transformerInfo) {
        Log.d("Keyguard-Editor-EditorActivity", "onApplyCompletedAction: applySuccess=" + z + " callingFromSystemUI=" + callingFromSystemUI() + " transformerInfo:" + (transformerInfo != null));
        restoreOrientationAfterApplying();
        this.applySuccess = z;
        if (callingFromSystemUI()) {
            if (z) {
                requestExitEditorFromMainFragment(true, transformerInfo);
                return;
            } else {
                resetApplyingFlagForMainFragment("ApplyFailed");
                return;
            }
        }
        resetApplyingFlagForMainFragment("notCallingFromSystemUI");
        if (isShowApplyLoading()) {
            showOrHideApplyLoading(false);
        }
        finish();
    }

    public final void onApplyEndFromCommonEditorActivity() {
        Log.i("Keyguard-Editor-EditorActivity", "onApplyEndFromCommonEditorActivity");
        isAppliedFromCommonEditorActivity = true;
    }

    public final void onApplyEndWhenPinned() {
        MainFragment mainFragment;
        if (CommonUtils.INSTANCE.isActivityPinned(getApplicationContext()) && (mainFragment = this.mainFragment) != null) {
            mainFragment.reloadAllData();
        }
        isAppliedFromCommonEditorActivity = false;
    }

    public void onApplyMyTemplate(TemplateItemBean curTemItemBean, TransformerInfo transformerInfo, boolean z) {
        Intrinsics.checkNotNullParameter(curTemItemBean, "curTemItemBean");
        Log.i("Keyguard-Editor-EditorActivity", "onApplyMyTemplate -> transformerInfo: " + transformerInfo + ", applySuccess: " + z);
        if (getCurrentNavigationActionListener() instanceof MainFragment) {
            TrackHelper trackHelper = TrackHelper.INSTANCE;
            Context applicationContext = getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
            CommonConfig current = curTemItemBean.getCurrent();
            CommonConfig current2 = curTemItemBean.getCurrent();
            NavigationActionListener currentNavigationActionListener = getCurrentNavigationActionListener();
            Intrinsics.checkNotNull(currentNavigationActionListener, "null cannot be cast to non-null type com.miui.keyguard.editor.homepage.MainFragment");
            TrackHelper.trackQuitHomePage$default(trackHelper, applicationContext, "应用", current, current2, ((MainFragment) currentNavigationActionListener).getTrackShowTag(), MainFragment.Companion.isEnterEditor(), false, 64, null);
        }
        restoreOrientationAfterApplying();
        if (callingFromSystemUI()) {
            requestExitEditorFromMainFragment(z, transformerInfo);
        } else {
            finish();
        }
    }

    @Override // com.miui.keyguard.editor.base.TransitionLayer
    public void onBeforeTransitionGone(boolean z) {
        TextView textView = this.cancelButton;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cancelButton");
            textView = null;
        }
        textView.setText(z ? getString(R.string.kg_dialog_apply_super_wallpaper_negative_button) : getString(R.string.kg_exit));
        if (!z) {
            textView.setContentDescription(getResources().getString(R.string.kg_exit));
            return;
        }
        textView.setContentDescription(getResources().getString(R.string.kg_talkback_cancel_wallpaper_custom));
        LifecycleCoroutineScope lifecycleScope = LifecycleOwnerKt.getLifecycleScope(this);
        if (lifecycleScope != null) {
            BuildersKt__Builders_commonKt.launch$default(lifecycleScope, Dispatchers.getMain(), null, new EditorActivity$onBeforeTransitionGone$1$1(this, null), 2, null);
        }
    }

    public void onCancel(TransformerInfo transformerInfo) {
        restoreOrientationAfterApplying();
        if (!callingFromSystemUI()) {
            finish();
            return;
        }
        Log.i("Keyguard-Editor-EditorActivity", "onCancel: " + transformerInfo);
        requestExitEditorFromMainFragment(false, transformerInfo);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(@NotNull View v) {
        Intrinsics.checkNotNullParameter(v, "v");
        if (this.isEnterApplying) {
            Log.d("TAG", "enter apply process");
            return;
        }
        TextView textView = this.applyButton;
        PageTransitionLayer pageTransitionLayer = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("applyButton");
            textView = null;
        }
        if (!Intrinsics.areEqual(v, textView)) {
            TextView textView2 = this.cancelButton;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("cancelButton");
                textView2 = null;
            }
            if (Intrinsics.areEqual(v, textView2)) {
                PageTransitionLayer pageTransitionLayer2 = this.transitionContainer;
                if (pageTransitionLayer2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("transitionContainer");
                    pageTransitionLayer2 = null;
                }
                if (pageTransitionLayer2.getVisibility() == 0 || ClickUtil.INSTANCE.isFastDoubleClick()) {
                    return;
                }
                Log.i("Keyguard-Editor-EditorActivity", "cancel");
                NavigationActionListener currentNavigationActionListener = getCurrentNavigationActionListener();
                if (currentNavigationActionListener != null) {
                    NavigationActionListener.DefaultImpls.onCancel$default(currentNavigationActionListener, null, 1, null);
                    return;
                } else {
                    new Function0() { // from class: com.miui.keyguard.editor.EditorActivity$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return EditorActivity.onClick$lambda$17(this.f$0);
                        }
                    };
                    return;
                }
            }
            return;
        }
        PageTransitionLayer pageTransitionLayer3 = this.transitionContainer;
        if (pageTransitionLayer3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transitionContainer");
        } else {
            pageTransitionLayer = pageTransitionLayer3;
        }
        if (pageTransitionLayer.getVisibility() == 0 || ClickUtil.INSTANCE.isFastDoubleClick()) {
            return;
        }
        TemplateApiImpl.Companion companion = TemplateApiImpl.Companion;
        companion.setApplyClickTimestamp(System.currentTimeMillis());
        Log.i("Keyguard-Editor-EditorActivity", "applyButton onClick: setting clickTs=" + companion.getApplyClickTimestamp());
        setupSystemBar(false);
        Log.i("Keyguard-Editor-EditorActivity", "begin apply");
        NavigationActionListener currentNavigationActionListener2 = getCurrentNavigationActionListener();
        if (currentNavigationActionListener2 != null) {
            currentNavigationActionListener2.beforeApply();
        } else {
            new Function0() { // from class: com.miui.keyguard.editor.EditorActivity$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return EditorActivity.onClick$lambda$16(this.f$0);
                }
            };
        }
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        ActivityUIAdapter activityUIAdapter;
        CrossViewPager viewPaper;
        TitleDescController currentFloorTitleDescController;
        View titleModule;
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        this.onConfigChanging = true;
        Log.d("Keyguard-Editor-EditorActivity", "onConfigurationChanged: " + getApplicationContext().getResources().getConfiguration().uiMode + " " + getResources().getConfiguration().uiMode);
        this.orientation = DeviceUtil.INSTANCE.getScreenOrientation(this);
        super.onConfigurationChanged(newConfig);
        AlertDialog alertDialog = this.showingDialog;
        PageTransitionLayer pageTransitionLayer = null;
        if (alertDialog != null) {
            Intrinsics.checkNotNull(alertDialog);
            if (alertDialog.isShowing()) {
                AlertDialog alertDialog2 = this.showingDialog;
                if (alertDialog2 != null) {
                    alertDialog2.dismissWithoutAnimation();
                }
                Log.i("Keyguard-Editor-EditorActivity", "dismiss");
                this.showingDialog = null;
            }
        }
        ActivityUIAdapter activityUIAdapter2 = this.activityUIAdapter;
        if (activityUIAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("activityUIAdapter");
            activityUIAdapter2 = null;
        }
        activityUIAdapter2.updateNightMode(this);
        ActivityUIAdapter activityUIAdapter3 = this.activityUIAdapter;
        if (activityUIAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("activityUIAdapter");
            activityUIAdapter = null;
        } else {
            activityUIAdapter = activityUIAdapter3;
        }
        ActivityUIAdapter.onConfigurationChanged$default(activityUIAdapter, this, newConfig, false, 4, null);
        MultiDeviceAdaptHelper multiDeviceAdaptHelper = this.multiDeviceAdaptHelper;
        if (multiDeviceAdaptHelper != null) {
            multiDeviceAdaptHelper.onConfigurationChanged(newConfig);
        }
        OnActivityConfigChangedListener onActivityConfigChangedListener = this.onActivityConfigChangedListener;
        if (onActivityConfigChangedListener != null) {
            onActivityConfigChangedListener.onConfigChanged(newConfig);
        }
        if (getTransitionPerformer().onConfigChanged()) {
            MainFragment mainFragment = this.mainFragment;
            if (mainFragment != null && (currentFloorTitleDescController = mainFragment.getCurrentFloorTitleDescController()) != null && (titleModule = currentFloorTitleDescController.getTitleModule()) != null) {
                titleModule.setVisibility(8);
            }
            MainFragment mainFragment2 = this.mainFragment;
            if (mainFragment2 != null && (viewPaper = mainFragment2.getViewPaper()) != null) {
                viewPaper.setAlpha(0.0f);
            }
            PageTransitionLayer pageTransitionLayer2 = this.transitionContainer;
            if (pageTransitionLayer2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("transitionContainer");
            } else {
                pageTransitionLayer = pageTransitionLayer2;
            }
            pageTransitionLayer.setAlpha(0.0f);
        }
        this.onConfigChanging = false;
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        this.isADB = getIntent().getStringExtra("is_adb");
        Log.i("Keyguard-Editor-EditorActivity", "onCreate saveInstanceState is null: " + (bundle == null));
        tackId = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.CHINA).format(new Date());
        EditorApplicationProxy.Companion companion = EditorApplicationProxy.Companion;
        if (companion.getEDITOR_DEBUG()) {
            Trace.beginSection("EditorActivity:onCreate");
        }
        this.isRelaunched = bundle != null;
        backgroundBlurEnable = Settings.Secure.getInt(getContentResolver(), "background_blur_enable", 0) == 1;
        getWindow().addFlags(131072);
        this.taskHelper.avoidAutoDensityCausingCrash(false);
        setEmptyWallpaperOpenAnimation();
        initMultiDeviceAdaptHelper();
        ResourcesProvider.INSTANCE.clearDimenResources();
        LockScreenTransformerLayer lockScreenTransformerLayer = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer != null) {
            lockScreenTransformerLayer.resetTransformerLayer();
        }
        ActivityUIAdapter activityUIAdapter = new ActivityUIAdapter(this);
        this.activityUIAdapter = activityUIAdapter;
        activityUIAdapter.initNightMode(this);
        ActivityUIAdapter activityUIAdapter2 = this.activityUIAdapter;
        ViewGroup viewGroup = null;
        if (activityUIAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("activityUIAdapter");
            activityUIAdapter2 = null;
        }
        activityUIAdapter2.registerDisplayListener(this);
        super.onCreate(bundle);
        this.firstShowSystemBar = true;
        if (!Process.INSTANCE.isKeyguardeditorProcess()) {
            Log.i("Keyguard-Editor-EditorActivity", "onCreate process name: " + Application.getProcessName() + "， this=" + this);
            finish();
            return;
        }
        if (callingFromSystemUI()) {
            ProcessManager.INSTANCE.setLaunchingEditorFromLockScreen(true);
            EditorServiceManager.Companion.getInstance().registerWatchDog(this);
            TemplateApiImpl.Companion.setWaitFlushConfig(true);
        } else {
            TemplateApiImpl.Companion companion2 = TemplateApiImpl.Companion;
            companion2.clearOldConfig();
            companion2.setWaitFlushConfig(false);
        }
        saveCallingSource(getIntent());
        initContentView();
        Intent intent = getIntent();
        this.clockExtraArgs = intent != null ? intent.getBundleExtra("clock_extra") : null;
        Intent intent2 = getIntent();
        this.fullAodExtraArgs = intent2 != null ? intent2.getBundleExtra("fullaod_extra") : null;
        setupViews();
        ViewGroup viewGroup2 = this.rootView;
        if (viewGroup2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rootView");
            viewGroup2 = null;
        }
        viewGroup2.removeCallbacks(this.initRunnable);
        this.initRunnable = new Runnable() { // from class: com.miui.keyguard.editor.EditorActivity$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                EditorActivity.onCreate$lambda$1(this.f$0);
            }
        };
        ViewGroup viewGroup3 = this.rootView;
        if (viewGroup3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rootView");
        } else {
            viewGroup = viewGroup3;
        }
        viewGroup.post(this.initRunnable);
        tryClearFragmentBackStackEntries();
        navigateToFirstFragment();
        EditorObserver editorObserver = new EditorObserver(this);
        editorObserver.onCreate(callingFromSystemUI());
        this.editorObserver = editorObserver;
        getLifecycle().addObserver(ColorPickerCache.INSTANCE);
        getLifecycle().addObserver(this.taskHelper);
        this.finishReceiver = createAndRegisterFinishBroadCast();
        if (companion.getEDITOR_DEBUG()) {
            Trace.endSection();
        }
        this.orientationListener.registerListener();
        getOnBackPressedDispatcher().addCallback(this, this.onBackCallback);
    }

    @Override // com.miui.keyguard.editor.view.LockScreenTransformerController
    public void onDataLoadCompleted(TransformerInfo transformerInfo, boolean z) {
        if (this.isRelaunched) {
            Log.w("Keyguard-Editor-EditorActivity", "onDataLoadCompleted: activity is relaunch started, fill data immediately.");
            ViewGroup viewGroup = this.rootView;
            if (viewGroup == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rootView");
                viewGroup = null;
            }
            viewGroup.setVisibility(0);
            setButtonClickable(true);
            continueToFillCrossViewPagerData();
            return;
        }
        boolean zCallingFromSystemUI = callingFromSystemUI();
        Log.i("Keyguard-Editor-EditorActivity", "onViewPagerLoadCompleted -> transformerInfo: " + transformerInfo + ", isHotLoad: " + z + ", isCallingFromSystemUI: " + zCallingFromSystemUI);
        if (!zCallingFromSystemUI) {
            continueToFillCrossViewPagerData();
            return;
        }
        EditorActivityViewModel.Companion.getViewModel(this).setTransformFinished(false);
        LockScreenTransformerLayer lockScreenTransformerLayer = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer != null) {
            lockScreenTransformerLayer.inflateAndInit(false);
        }
        LockScreenTransformerLayer lockScreenTransformerLayer2 = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer2 != null) {
            LockScreenTransformerLayer.setTransformerInfo$default(lockScreenTransformerLayer2, transformerInfo, false, false, 4, null);
        }
        LockScreenTransformerLayer lockScreenTransformerLayer3 = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer3 != null) {
            lockScreenTransformerLayer3.setupTransformerInitialState();
        }
        LockScreenTransformerLayer lockScreenTransformerLayer4 = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer4 != null) {
            lockScreenTransformerLayer4.removeCallbacks(this.loadCompleteRunnable);
        }
        Runnable runnable = new Runnable() { // from class: com.miui.keyguard.editor.EditorActivity$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                EditorActivity.onDataLoadCompleted$lambda$28(this.f$0);
            }
        };
        this.loadCompleteRunnable = runnable;
        LockScreenTransformerLayer lockScreenTransformerLayer5 = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer5 != null) {
            lockScreenTransformerLayer5.post(runnable);
        }
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        Log.i("Keyguard-Editor-EditorActivity", "editor onDestroy");
        if (callingFromSystemUI()) {
            EditorServiceManager.Companion.getInstance().clearWatchDog();
        }
        this.orientationListener.unregisterListener();
        restoreOrientationAfterApplying();
        getLifecycle().removeObserver(this.taskHelper);
        ActivityUIAdapter activityUIAdapter = this.activityUIAdapter;
        if (activityUIAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("activityUIAdapter");
            activityUIAdapter = null;
        }
        activityUIAdapter.unregisterDisplayListener(this);
        WideColorGamutManager wideColorGamutManager = WideColorGamutManager.INSTANCE;
        wideColorGamutManager.unregisterObservers(this);
        wideColorGamutManager.removeColorModeChangeListener(getWideColorGamutListener());
        setSystemBarDisableFlags(true);
        BitmapTempStore.Companion.getInstance().clear();
        MultiDeviceAdaptHelper multiDeviceAdaptHelper = this.multiDeviceAdaptHelper;
        if (multiDeviceAdaptHelper != null) {
            multiDeviceAdaptHelper.onActivityDestroy();
        }
        LockScreenTransformerLayer lockScreenTransformerLayer = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer != null) {
            lockScreenTransformerLayer.onActivityDestroy();
        }
        this.showingDialog = null;
        FontUtils.clearClassicTypefaceCache();
        clearDelayedCallback();
        TemplateApi.Companion companion = TemplateApi.Companion;
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        companion.getInstance(applicationContext).enableGetCurrentLockWallpaperFromWM();
        EditorApplicationProxy.Companion companion2 = EditorApplicationProxy.Companion;
        if (companion2.getEDITOR_DEBUG()) {
            Trace.beginSection("miuixAppCompatActivity onDestroy");
        }
        EditorObserver editorObserver = this.editorObserver;
        if (editorObserver != null) {
            editorObserver.onEditorDestroy();
        }
        if (Intrinsics.areEqual(getClass().getSimpleName(), EditorActivity.class.getSimpleName())) {
            Log.d("Keyguard-Editor-EditorActivity", "finish task when destroy ,finishOtherActivity");
            finishEditorTask();
            EditorServiceManager.Companion.getInstance().finishOtherActivity();
        }
        super.onDestroy();
        if (companion2.getEDITOR_DEBUG()) {
            Trace.endSection();
        }
        BroadcastReceiver broadcastReceiver = this.finishReceiver;
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        ProcessManager.INSTANCE.onEditorDestroy();
        PublicParam.setContext(null);
    }

    @Override // com.miui.keyguard.editor.view.LockScreenTransformerController
    public void onDisMissApplyLoading() {
        restoreOrientationAfterApplying();
        if (isShowApplyLoading()) {
            showOrHideApplyLoading(false);
        }
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onDismissBottomSheet() {
        CombineEditListener.DefaultImpls.onDismissBottomSheet(this);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onDismissBottomSheetForScreenChange(Configuration configuration) {
        CombineEditListener.DefaultImpls.onDismissBottomSheetForScreenChange(this, configuration);
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onDismissKeyboard() {
        CombineEditListener.DefaultImpls.onDismissKeyboard(this);
    }

    @Override // com.miui.keyguard.editor.edit.base.EditModeChangedListener
    public void onEditModeChanged(int i, int i2) {
        ViewGroup viewGroup = this.rootView;
        ViewGroup viewGroup2 = null;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rootView");
            viewGroup = null;
        }
        float f = (-viewGroup.getHeight()) / 5.0f;
        ViewGroup viewGroup3 = this.buttonBar;
        if (viewGroup3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("buttonBar");
        } else {
            viewGroup2 = viewGroup3;
        }
        Folme.use((View) viewGroup2).state().to(new AnimState().add(ViewProperty.TRANSLATION_Y, f, new long[0]), new AnimConfig().setEase(-2, 0.95f, 0.3f));
    }

    @Override // com.miui.keyguard.editor.view.LockScreenTransformerAnimationListener
    public void onEnterLockScreenTransformerAnimationEnd() {
        Log.i("Keyguard-Editor-EditorActivity", "onEnterLockScreenTransformerAnimationEnd -> shouldFillDataToCrossViewPager: " + this.shouldFillDataToCrossViewPager);
        setButtonClickable(true);
        if (this.shouldFillDataToCrossViewPager) {
            continueToFillCrossViewPagerData();
            this.shouldFillDataToCrossViewPager = false;
        }
    }

    @Override // com.miui.keyguard.editor.utils.GestureSlideUpCallback
    public void onGestureSlideUp() {
        boolean zIsForwardTransitionRunning = isForwardTransitionRunning();
        boolean zIsShowApplyLoading = isShowApplyLoading();
        if (zIsForwardTransitionRunning || zIsShowApplyLoading) {
            Log.i("Keyguard-Editor-EditorActivity", "onGestureSlideUp intercepted");
            return;
        }
        if (this.wallpaperPickerShowing) {
            this.wallpaperPickerShowing = false;
            return;
        }
        this.isDirectFinishWhenBackPress = true;
        AlertDialog alertDialog = this.showingDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        this.showingDialog = null;
        getOnBackPressedDispatcher().onBackPressed();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        Log.i("Keyguard-Editor-EditorActivity", "onNewIntent");
        ResourcesProvider.INSTANCE.clearDimenResources();
        LockScreenTransformerLayer lockScreenTransformerLayer = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer != null) {
            lockScreenTransformerLayer.resetTransformerLayer();
        }
        super.onNewIntent(intent);
        setIntent(intent);
        ViewGroup viewGroup = this.buttonBar;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("buttonBar");
            viewGroup = null;
        }
        viewGroup.setVisibility(0);
        this.clockExtraArgs = intent != null ? intent.getBundleExtra("clock_extra") : null;
        this.fullAodExtraArgs = intent != null ? intent.getBundleExtra("fullaod_extra") : null;
        tryClearFragmentBackStackEntries();
        saveCallingSource(intent);
        navigateToFirstFragment();
        trackEditorOpen();
    }

    public final void onOrientationChange() {
        Fragment fragmentFindFragmentById = getSupportFragmentManager().findFragmentById(R.id.kg_fragment_container);
        int screenOrientation = DeviceUtil.INSTANCE.getScreenOrientation(this);
        int i = this.orientation;
        if (i == screenOrientation || !(fragmentFindFragmentById instanceof EditFragment)) {
            return;
        }
        if ((i == 1 && screenOrientation == 3) || (i == 3 && screenOrientation == 1)) {
            ((EditFragment) fragmentFindFragmentById).hidAllBottomSheet();
            if (EditorServiceManager.Companion.getInstance().getCurrentState() == EditorServiceManager.EditorState.SHOWBOUNCER) {
                LockScreenTransformerLayer lockScreenTransformerLayer = this.lockScreenTransformerLayer;
                if (lockScreenTransformerLayer != null) {
                    lockScreenTransformerLayer.onScreenConfigurationChanged(true);
                }
            } else {
                LockScreenTransformerLayer lockScreenTransformerLayer2 = this.lockScreenTransformerLayer;
                if (lockScreenTransformerLayer2 != null) {
                    lockScreenTransformerLayer2.onScreenConfigurationChanged(false);
                }
            }
        } else if ((i == 0 && screenOrientation == 2) || (i == 2 && screenOrientation == 0)) {
            ((EditFragment) fragmentFindFragmentById).hidAllBottomSheet();
            if (EditorServiceManager.Companion.getInstance().getCurrentState() == EditorServiceManager.EditorState.SHOWBOUNCER) {
                LockScreenTransformerLayer lockScreenTransformerLayer3 = this.lockScreenTransformerLayer;
                if (lockScreenTransformerLayer3 != null) {
                    lockScreenTransformerLayer3.onScreenConfigurationChanged(true);
                }
            } else {
                LockScreenTransformerLayer lockScreenTransformerLayer4 = this.lockScreenTransformerLayer;
                if (lockScreenTransformerLayer4 != null) {
                    lockScreenTransformerLayer4.onScreenConfigurationChanged(false);
                }
            }
        }
        this.orientation = screenOrientation;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        this.wallpaperPickerShowing = true;
        Log.i("Keyguard-Editor-EditorActivity", "editor onPause");
        if (this.applySuccess && (callingFromLockCross() || isMiuiCall())) {
            overridePendingTransition(0, 0);
        }
        super.onPause();
        if (callingFromSystemUI() || callingFromLockCross()) {
            this.gestureSlideUpHelper.unregister(this);
        }
        setSystemBarDisableFlags(true);
        finishWhenLocked();
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onPreviewChanged(boolean z, boolean z2) {
        updateButtonsOnPreviewChanged(z);
        changeCancelButtonDecorateBackground(z, z2);
    }

    @Override // android.app.Activity
    public void onRestart() {
        Log.i("Keyguard-Editor-EditorActivity", "editor onRestart");
        super.onRestart();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        Log.i("Keyguard-Editor-EditorActivity", "editor onResume running Instance： " + getClass().getSimpleName());
        super.onResume();
        setEnabled(true);
        WideColorGamutManager wideColorGamutManager = WideColorGamutManager.INSTANCE;
        wideColorGamutManager.updateSupportWideColor(this);
        wideColorGamutManager.addColorModeChangeListener(getWideColorGamutListener());
        int deviceMaxColorMode = wideColorGamutManager.getDeviceMaxColorMode(this);
        Log.i("Keyguard-Editor-EditorActivity", "onResume: getDeviceMaxColorMode=" + deviceMaxColorMode);
        getWindow().setColorMode(deviceMaxColorMode);
        if (this.pickWallpaperSetButtonDisbable) {
            setButtonClickable(true);
            this.pickWallpaperSetButtonDisbable = false;
        }
        if (this.wallpaperPickerShowing) {
            BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(this), Dispatchers.getMain(), null, new AnonymousClass1(null), 2, null);
        }
        backgroundBlurEnable = Settings.Secure.getInt(getContentResolver(), "background_blur_enable", 0) == 1;
        if (callingFromSystemUI() || callingFromLockCross()) {
            this.gestureSlideUpHelper.register(this, this);
        }
        BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(this), Dispatchers.getIO(), null, new AnonymousClass2(null), 2, null);
        if ((this instanceof CommonEditorActivity) || !isAppliedFromCommonEditorActivity) {
            return;
        }
        onApplyEndWhenPinned();
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onScrolledChanged(boolean z, boolean z2) {
        updateButtonsOnPreviewChanged(z);
        changeCancelButtonDecorateBackground(z, z2);
    }

    @Override // android.content.DialogInterface.OnShowListener
    public void onShow(DialogInterface dialogInterface) {
        this.showingDialog = dialogInterface instanceof AlertDialog ? (AlertDialog) dialogInterface : null;
        this.isShowingDialog = true;
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onShowBottomSheet() {
        CombineEditListener.DefaultImpls.onShowBottomSheet(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        this.hasStop = false;
        this.hasInvokeFinish = false;
        Log.i("Keyguard-Editor-EditorActivity", "editor onStart");
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        this.hasStop = true;
        if (!this.hasInvokeFinish) {
            finishWhenLocked();
        }
        SystemBarController.disable(this, 0);
        EditorObserver editorObserver = this.editorObserver;
        if (editorObserver != null) {
            editorObserver.onEditorStop();
        }
        Log.i("Keyguard-Editor-EditorActivity", "editor onStop");
    }

    @Override // com.miui.keyguard.editor.edit.base.CombineEditListener
    public void onTemplateReady(int i) {
        CombineEditListener.DefaultImpls.onTemplateReady(this, i);
    }

    public final void onTopFragmentChanged(Fragment fragment) {
    }

    @Override // com.miui.keyguard.editor.base.TransitionLayer
    public void onTransitionComplete(boolean z) {
        CrossViewPager viewPaper;
        TitleDescController currentFloorTitleDescController;
        View titleModule;
        PageTransitionLayer pageTransitionLayer = null;
        if (z) {
            PageTransitionLayer pageTransitionLayer2 = this.transitionContainer;
            if (pageTransitionLayer2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("transitionContainer");
                pageTransitionLayer2 = null;
            }
            pageTransitionLayer2.removeAllViews();
        }
        PageTransitionLayer pageTransitionLayer3 = this.transitionContainer;
        if (pageTransitionLayer3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transitionContainer");
            pageTransitionLayer3 = null;
        }
        pageTransitionLayer3.setVisibility(8);
        MainFragment mainFragment = this.mainFragment;
        if (mainFragment != null) {
            mainFragment.setViewPageUserInputEnabled(true);
        }
        MainFragment mainFragment2 = this.mainFragment;
        if (mainFragment2 != null && (currentFloorTitleDescController = mainFragment2.getCurrentFloorTitleDescController()) != null && (titleModule = currentFloorTitleDescController.getTitleModule()) != null) {
            titleModule.setVisibility(0);
        }
        MainFragment mainFragment3 = this.mainFragment;
        if (mainFragment3 != null && (viewPaper = mainFragment3.getViewPaper()) != null) {
            viewPaper.setAlpha(1.0f);
        }
        PageTransitionLayer pageTransitionLayer4 = this.transitionContainer;
        if (pageTransitionLayer4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transitionContainer");
        } else {
            pageTransitionLayer = pageTransitionLayer4;
        }
        pageTransitionLayer.setAlpha(1.0f);
        restoreOrientationAfterTransition();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        Log.i("Keyguard-Editor-EditorActivity", "onWindowFocusChanged " + z);
        this.isFocused = z;
        super.onWindowFocusChanged(z);
        if (z || !this.isShowingDialog) {
            setSystemBarDisableFlags(!z);
        } else {
            this.isShowingDialog = false;
        }
        setSystemBarVisible(z);
        if (z && this.showingDialog != null) {
            this.showingDialog = null;
        }
        if (z) {
            TrackTimeUtils trackTimeUtils = TrackTimeUtils.INSTANCE;
            long timeCalculate = trackTimeUtils.getTimeCalculate("editor_start");
            Log.i("Keyguard-Editor-EditorActivity", "editorOpenTime:=" + timeCalculate);
            TrackHelper.INSTANCE.trackEditorOpenTime(this, timeCalculate);
            trackTimeUtils.clearStartTimeCalculate();
            setCancelButtonAccessForMainFragment();
        }
    }

    public final void printBundle(Bundle bundle) {
        Log.d("Keyguard-Editor-EditorActivity", "---------------- EditorActivity clockExtras -----------------");
        Set<String> setKeySet = bundle != null ? bundle.keySet() : null;
        if (setKeySet == null || setKeySet.isEmpty()) {
            Log.i("Keyguard-Editor-EditorActivity", "printBundle: bundle is empty~");
            return;
        }
        for (String str : setKeySet) {
            Log.i("Keyguard-Editor-EditorActivity", "printBundle: key = " + str + ", value = " + bundle.get(str));
        }
    }

    public final void printDeviceInfo() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Log.d("Keyguard-Editor-EditorActivity", "widthPixels: " + displayMetrics + ", heightPixels: " + displayMetrics.heightPixels + ", densityDpi: " + displayMetrics.densityDpi);
    }

    @Override // android.app.Activity
    public void recreate() {
        Log.i("Keyguard-Editor-EditorActivity", "recreate");
        super.recreate();
    }

    public final void requestExitEditorFromMainFragment(boolean z, final TransformerInfo transformerInfo) {
        ViewTreeObserver viewTreeObserver;
        if (this.isExitAnimation) {
            Log.i("Keyguard-Editor-EditorActivity", "already in exit animation");
            return;
        }
        this.isExitAnimation = true;
        MainFragment mainFragment = this.mainFragment;
        if (mainFragment != null) {
            mainFragment.interceptItemLongClick(true);
        }
        EditorServiceManager.Companion.getInstance().setExitAnimationPerformer(this);
        LockScreenTransformerLayer lockScreenTransformerLayer = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer != null) {
            lockScreenTransformerLayer.setTransformerInfo(transformerInfo, z, false);
        }
        LockScreenTransformerLayer lockScreenTransformerLayer2 = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer2 != null) {
            lockScreenTransformerLayer2.setupExitTransformerInitialState(z);
        }
        LockScreenTransformerLayer lockScreenTransformerLayer3 = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer3 != null) {
            lockScreenTransformerLayer3.setAlpha(0.0f);
        }
        LockScreenTransformerLayer lockScreenTransformerLayer4 = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer4 != null) {
            lockScreenTransformerLayer4.showTransFormerLayer();
        }
        LockScreenTransformerLayer lockScreenTransformerLayer5 = this.lockScreenTransformerLayer;
        final float previewRadiusPixel = lockScreenTransformerLayer5 != null ? lockScreenTransformerLayer5.getPreviewRadiusPixel() : ResourcesProvider.INSTANCE.providePreviewRadius(this);
        LockScreenTransformerLayer lockScreenTransformerLayer6 = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer6 != null) {
            lockScreenTransformerLayer6.removeCallbacks(this.extAnimationRunnable);
        }
        this.extAnimationRunnable = new Runnable() { // from class: com.miui.keyguard.editor.EditorActivity$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                EditorActivity.requestExitEditorFromMainFragment$lambda$26(this.f$0, transformerInfo, previewRadiusPixel);
            }
        };
        Log.d("Keyguard-Editor-EditorActivity", "requestExitEditorFromMainFragment: lockScreenTransformerLayer:" + (this.lockScreenTransformerLayer != null));
        LockScreenTransformerLayer lockScreenTransformerLayer7 = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer7 == null || (viewTreeObserver = lockScreenTransformerLayer7.getViewTreeObserver()) == null) {
            return;
        }
        viewTreeObserver.registerFrameCommitCallback(new Runnable() { // from class: com.miui.keyguard.editor.EditorActivity.requestExitEditorFromMainFragment.2
            @Override // java.lang.Runnable
            public void run() {
                ViewTreeObserver viewTreeObserver2;
                LockScreenTransformerLayer lockScreenTransformerLayer8 = EditorActivity.this.lockScreenTransformerLayer;
                if (lockScreenTransformerLayer8 != null && (viewTreeObserver2 = lockScreenTransformerLayer8.getViewTreeObserver()) != null) {
                    viewTreeObserver2.unregisterFrameCommitCallback(this);
                }
                Log.d("Keyguard-Editor-EditorActivity", "exit frame committed, start exit anim");
                Runnable runnable = EditorActivity.this.extAnimationRunnable;
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
    }

    public final void resetApplyingFlagForMainFragment(String source) {
        Intrinsics.checkNotNullParameter(source, "source");
        try {
            Fragment fragmentFindFragmentById = getSupportFragmentManager().findFragmentById(R.id.kg_fragment_container);
            if (fragmentFindFragmentById instanceof MainFragment) {
                ((MainFragment) fragmentFindFragmentById).resetApplyingFlag(source);
            }
        } catch (Exception e) {
            Log.e("Keyguard-Editor-EditorActivity", "resetApplyingFlagForMainFragment: source = " + source, e);
        }
    }

    public final void resetLockTransLayerCurPositionInfo(TransformerPositionInfo positionInfo) {
        Intrinsics.checkNotNullParameter(positionInfo, "positionInfo");
        Log.i("Keyguard-Editor-EditorActivity", "resetLockTransLayerCurPositionInfo " + positionInfo);
        LockScreenTransformerLayer lockScreenTransformerLayer = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer != null) {
            lockScreenTransformerLayer.setEntranceCurrentPositionInfo(positionInfo);
        }
    }

    public final void restoreOrientationAfterApplying() {
        if (this.orientationLockedForApplying) {
            this.orientationLockedForApplying = false;
            getWindow().getDecorView().removeCallbacks(this.applyOrientationUnlockRunnable);
            if (this.orientationLockedForTransition) {
                return;
            }
            setRequestedOrientation(this.savedOrientationBeforeApplying);
        }
    }

    public final void restoreOrientationAfterTransition() {
        if (this.orientationLockedForTransition) {
            this.orientationLockedForTransition = false;
            getWindow().getDecorView().removeCallbacks(this.orientationUnlockRunnable);
            if (this.orientationLockedForApplying) {
                return;
            }
            setRequestedOrientation(this.savedOrientationBeforeTransition);
        }
    }

    public void saveCallingSource(Intent intent) {
        this.launchFromPackage = intent != null ? intent.getStringExtra("launched_from_package") : null;
        String launchedFromCaller = getLaunchedFromCaller();
        Log.i("Keyguard-Editor-EditorActivity", "saveCallingSource -> launchFromPackage: " + this.launchFromPackage + " caller=" + launchedFromCaller);
        if (!callingFromSystemUI() && !callingFromLockCross()) {
            if (!isMiuiCall() && !isAppFunctionCall()) {
                finish();
                return;
            }
            SettingsEditorServiceManager.INSTANCE.setActivity(this);
            if (intent != null) {
                intent.putExtra("openSource", 1);
            }
            Log.i("Keyguard-Editor-EditorActivity", "callingFromTheme");
            return;
        }
        EditorServiceManager.Companion companion = EditorServiceManager.Companion;
        companion.getInstance().setActivity(this);
        companion.getInstance().setExitAnimationPerformer(this);
        if (intent != null) {
            intent.putExtra("openSource", 0);
        }
        UserGuidance userGuidance = UserGuidance.INSTANCE;
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        userGuidance.saveCalledFromSystemUI(applicationContext);
        Log.i("Keyguard-Editor-EditorActivity", "callingFromSystemUI");
    }

    public final void sendRequestExitEditorCommandToKeyguard(Bundle bundle) {
        MainFragment mainFragment = this.mainFragment;
        if (mainFragment == null || mainFragment.getStartCancelTime() != 0) {
            ScenarioRecognitionUtils scenarioRecognitionUtils = ScenarioRecognitionUtils.INSTANCE;
            long currentTime = scenarioRecognitionUtils.getCurrentTime();
            MainFragment mainFragment2 = this.mainFragment;
            Intrinsics.checkNotNull(mainFragment2);
            scenarioRecognitionUtils.notifyScenarioChanged(1071L, currentTime - mainFragment2.getStartCancelTime());
            MainFragment mainFragment3 = this.mainFragment;
            Intrinsics.checkNotNull(mainFragment3);
            mainFragment3.setStartCancelTime(0L);
        }
        Log.d("Keyguard-Editor-EditorActivity", "sendRequestExitEditorCommandToKeyguard");
        if (callingFromSystemUI()) {
            float fProvideTemplatePreviewWidth = ResourcesProvider.INSTANCE.provideTemplatePreviewWidth(this);
            Log.i("Keyguard-Editor-EditorActivity", "preview width " + fProvideTemplatePreviewWidth);
            bundle.putFloat("templateReallyWidth", fProvideTemplatePreviewWidth);
            ViewUtil viewUtil = ViewUtil.INSTANCE;
            bundle.putParcelable("surfaceControl", viewUtil.getViewRootImplSurfaceControl(getWindow().getDecorView()));
            bundle.putParcelable("parentSurfaceControl", viewUtil.getViewRootImplParentSurfaceControl(getWindow().getDecorView()));
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            View decorView = getWindow().getDecorView();
            Intrinsics.checkNotNullExpressionValue(decorView, "getDecorView(...)");
            bundle.putFloatArray("finalCornerRadiusArr", deviceUtil.screenRadii(decorView));
            if (EditorServiceManager.Companion.getInstance().sendRequestExitEditorCommand(bundle)) {
                changeIsDismissKeyguardWhenPause(false);
            } else {
                finishActivity();
            }
        }
    }

    @Override // com.miui.keyguard.editor.NavigationController
    public void setApplyButtonEnabled(boolean z) {
        TextView textView;
        TextView textView2 = this.applyButton;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("applyButton");
            textView2 = null;
        }
        if (textView2.isEnabled() == z) {
            return;
        }
        TextView textView3 = this.applyButton;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("applyButton");
            textView3 = null;
        }
        textView3.setEnabled(z);
        TextView textView4 = this.applyButton;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("applyButton");
            textView = null;
        } else {
            textView = textView4;
        }
        AnimatorKt.alphaTo$default(textView, z ? 1.0f : 0.3f, 0.0f, 0.0f, null, 14, null);
    }

    public final void setApplyStatus(boolean z) {
        this.applySuccess = z;
    }

    public final void setButtonBarVisbility(boolean z) {
        ViewGroup viewGroup = this.buttonBar;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("buttonBar");
            viewGroup = null;
        }
        viewGroup.setVisibility(z ? 0 : 8);
    }

    public final void setButtonClickable(boolean z) {
        TextView textView = this.applyButton;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("applyButton");
            textView = null;
        }
        textView.setClickable(z);
        TextView textView3 = this.cancelButton;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cancelButton");
        } else {
            textView2 = textView3;
        }
        textView2.setClickable(z);
    }

    public final void setCancelButtonAccessForMainFragment() {
        LifecycleCoroutineScope lifecycleScope;
        try {
            if (!(getSupportFragmentManager().findFragmentById(R.id.kg_fragment_container) instanceof MainFragment) || (lifecycleScope = LifecycleOwnerKt.getLifecycleScope(this)) == null) {
                return;
            }
            BuildersKt__Builders_commonKt.launch$default(lifecycleScope, Dispatchers.getMain(), null, new C00871(null), 2, null);
        } catch (Exception unused) {
        }
    }

    public final void setCancelButtonAccessibility() {
        TextView textView = this.cancelButton;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cancelButton");
            textView = null;
        }
        textView.requestFocus();
        TextView textView3 = this.cancelButton;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cancelButton");
        } else {
            textView2 = textView3;
        }
        textView2.sendAccessibilityEvent(128);
    }

    public final void setEmptyWallpaperOpenAnimation() {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        if (attributes != null) {
            attributes.extraFlags |= Integer.MIN_VALUE;
        }
    }

    public final void setEnterApplying(boolean z) {
        this.isEnterApplying = z;
    }

    public final void setInterceptAllEventsOnApplying(boolean z) {
        this.interceptAllEventsOnApplying = z;
        Log.i("Keyguard-Editor-EditorActivity", "setInterceptAllEventsOnApplying: " + z);
        this.startInterceptTime = z ? SystemClock.uptimeMillis() : -1L;
    }

    public final void setLaunchFromPackage(String str) {
        this.launchFromPackage = str;
    }

    public final void setMiProMediumFontFamily(TextView textView) {
        try {
            textView.setTypeface(Typeface.create("mipro-medium", 0));
        } catch (Exception e) {
            Log.e("Keyguard-Editor-EditorActivity", "setMiProMediumFontFamily", e);
        }
    }

    public final void setOnActivityConfigChangedListener(OnActivityConfigChangedListener onActivityConfigChangedListener) {
        this.onActivityConfigChangedListener = onActivityConfigChangedListener;
    }

    public final void setOrientation(int i) {
        this.orientation = i;
    }

    public final void setPickWallpaperSetButtonDisbable(boolean z) {
        this.pickWallpaperSetButtonDisbable = z;
    }

    public final void setSystemBarDisableFlags(boolean z) {
        int i;
        if (callingFromSystemUI()) {
            Window window = getWindow();
            Intrinsics.checkNotNullExpressionValue(window, "getWindow(...)");
            boolean zIsImmersiveModeEnabledV30 = isImmersiveModeEnabledV30(window);
            this.isImmersive = zIsImmersiveModeEnabledV30;
            if (z) {
                i = 0;
            } else {
                if (zIsImmersiveModeEnabledV30 && SystemBarController.isFullScreenGestureEnabled(this)) {
                    return;
                }
                if (this.isImmersive) {
                    i = 18874368;
                } else {
                    i = !SystemBarController.isFullScreenGestureEnabled(this) ? android.R.attr.optional : 9895936;
                }
            }
            SystemBarController.disable(this, i);
        }
    }

    public final void setSystemBarVisible(boolean z) {
        getWindow().getDecorView().removeCallbacks(this.hideSystemBarVisibleRunnable);
        if (this.firstShowSystemBar && z) {
            setupSystemBar(z);
            this.firstShowSystemBar = false;
        } else {
            setupSystemBar(false);
        }
        if (z) {
            getWindow().getDecorView().postDelayed(this.hideSystemBarVisibleRunnable, 2000L);
        }
    }

    public final void setTitleBarBlurEnabled(boolean z) {
    }

    public final void setWallpaperPickerShowing(boolean z) {
        this.wallpaperPickerShowing = z;
    }

    public final void setupApplyLoadingView() {
        EditorLoadingView editorLoadingView = new EditorLoadingView(this);
        ((ProgressBar) editorLoadingView.findViewById(R.id.kg_hierarchy_loading)).setIndeterminateTintList(ColorStateList.valueOf(-1));
        ((TextView) editorLoadingView.findViewById(R.id.kg_hierarchy_loading_text)).setText(getString(R.string.kg_applying));
        this.applyLoadingView = editorLoadingView;
        editorLoadingView.setVisibility(8);
        View view = this.applyLoadingView;
        ViewGroup viewGroup = null;
        if ((view != null ? view.getParent() : null) == null) {
            ViewGroup viewGroup2 = this.rootView;
            if (viewGroup2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rootView");
            } else {
                viewGroup = viewGroup2;
            }
            viewGroup.addView(this.applyLoadingView);
        }
    }

    public final void setupButtonBar() {
        TextView textView;
        TextView textView2;
        ViewGroup viewGroup = this.buttonBar;
        TextView textView3 = null;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("buttonBar");
            viewGroup = null;
        }
        ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
        layoutParams2.topMargin = !DeviceUtil.INSTANCE.isQ18() ? MiuixUIUtils.getStatusBarHeight(getApplicationContext()) : getResources().getDimensionPixelSize(R.dimen.kg_editor_button_bar_margin_top_q18);
        ViewGroup viewGroup2 = this.buttonBar;
        if (viewGroup2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("buttonBar");
            viewGroup2 = null;
        }
        viewGroup2.setLayoutParams(layoutParams2);
        TextView textView4 = this.applyButton;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("applyButton");
            textView = null;
        } else {
            textView = textView4;
        }
        ViewUtil viewUtil = ViewUtil.INSTANCE;
        ViewUtil.setThrottledClickListener$default(viewUtil, textView, this, 0L, 2, null);
        Resources resources = textView.getResources();
        int i = R.dimen.kg_top_button_corner_radius;
        textView.setBackground(DrawablesKt.createRoundDrawable(resources.getDimension(i), getColor(R.color.kg_editor_apply_button_background_for_lite)));
        TextView textView5 = this.cancelButton;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cancelButton");
            textView2 = null;
        } else {
            textView2 = textView5;
        }
        ViewUtil.setThrottledClickListener$default(viewUtil, textView2, this, 0L, 2, null);
        ViewUtil.decorateBackgroundByDefault$default(viewUtil, textView2, i, 0, 0, Integer.valueOf(getColor(R.color.kg_editor_cancel_button_main_item_background_for_lite)), false, 22, null);
        if (callingFromSystemUI()) {
            setButtonClickable(false);
        }
        MultiDeviceAdaptHelper multiDeviceAdaptHelper = this.multiDeviceAdaptHelper;
        if (multiDeviceAdaptHelper != null) {
            ViewGroup viewGroup3 = this.buttonBar;
            if (viewGroup3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("buttonBar");
                viewGroup3 = null;
            }
            TextView textView6 = this.cancelButton;
            if (textView6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("cancelButton");
                textView6 = null;
            }
            TextView textView7 = this.applyButton;
            if (textView7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("applyButton");
            } else {
                textView3 = textView7;
            }
            multiDeviceAdaptHelper.attachButtonBar(viewGroup3, textView6, textView3);
        }
    }

    public final void setupSystemBar(boolean z) {
        Window window = getWindow();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.layoutInDisplayCutoutMode = 3;
        window.setAttributes(attributes);
        WindowInsetsControllerCompat windowInsetsControllerCompat = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        if (z) {
            windowInsetsControllerCompat.show(WindowInsetsCompat.Type.systemBars());
        } else {
            windowInsetsControllerCompat.hide(WindowInsetsCompat.Type.systemBars());
        }
        windowInsetsControllerCompat.setSystemBarsBehavior(2);
        if (!callingFromSystemUI() && !callingFromLockCross()) {
            SystemBarController.disable(this, 0);
            return;
        }
        boolean z2 = this.isImmersive;
        if (!z2 || (z2 && this.isFocused)) {
            SystemBarController.disable(this, SystemBarController.isFullScreenGestureEnabled(this) ? 65536 : 18939904);
        }
    }

    public final void setupTransformerLayer() {
        PageTransitionLayer pageTransitionLayer = this.transitionContainer;
        TextView textView = null;
        if (pageTransitionLayer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transitionContainer");
            pageTransitionLayer = null;
        }
        pageTransitionLayer.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.EditorActivity$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                EditorActivity.setupTransformerLayer$lambda$10$lambda$9(view);
            }
        });
        LockScreenTransformerLayer lockScreenTransformerLayer = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer != null) {
            TextView textView2 = this.cancelButton;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("cancelButton");
                textView2 = null;
            }
            WeakReference weakReference = new WeakReference(textView2);
            TextView textView3 = this.applyButton;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("applyButton");
            } else {
                textView = textView3;
            }
            lockScreenTransformerLayer.attachExternalViews(weakReference, new WeakReference(textView));
        }
        LockScreenTransformerLayer lockScreenTransformerLayer2 = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer2 != null) {
            lockScreenTransformerLayer2.setLockScreenTransformerAnimationListener(new WeakReference<>(this));
        }
        MultiDeviceAdaptHelper multiDeviceAdaptHelper = this.multiDeviceAdaptHelper;
        if (multiDeviceAdaptHelper != null) {
            multiDeviceAdaptHelper.attachTransformerLayer(this.lockScreenTransformerLayer);
        }
    }

    public final void setupViews() {
        setSystemBarDisableFlags(false);
        new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView()).setAppearanceLightStatusBars(false);
        if (callingFromSystemUI()) {
            ViewGroup viewGroup = this.rootView;
            if (viewGroup == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rootView");
                viewGroup = null;
            }
            viewGroup.setVisibility(4);
        }
        setupButtonBar();
        setupTransformerLayer();
    }

    public final void showOrHideApplyLoading(boolean z) {
        if (this.applyLoadingView != null) {
            if (!z) {
                restoreOrientationAfterApplying();
                View view = this.applyLoadingView;
                if (view != null) {
                    view.setVisibility(8);
                    return;
                }
                return;
            }
            lockOrientationForApplying();
            View view2 = this.applyLoadingView;
            if (view2 != null) {
                view2.setAlpha(0.0f);
            }
            View view3 = this.applyLoadingView;
            if (view3 != null) {
                view3.setVisibility(0);
            }
            View view4 = this.applyLoadingView;
            if (view4 != null) {
                AnimatorKt.alphaTo$default(view4, 1.0f, 0.0f, 0.0f, null, 14, null);
            }
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void startActivityForResult(Intent intent, int i, Bundle bundle) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        super.startActivityForResult(intent, i, bundle);
        MainFragment mainFragment = this.mainFragment;
        if (mainFragment != null) {
            Long lValueOf = Long.valueOf(mainFragment.getStartPickerTime());
            if (lValueOf.longValue() == 0) {
                lValueOf = null;
            }
            if (lValueOf != null) {
                long jLongValue = lValueOf.longValue();
                ScenarioRecognitionUtils scenarioRecognitionUtils = ScenarioRecognitionUtils.INSTANCE;
                scenarioRecognitionUtils.notifyScenarioChanged(441L, scenarioRecognitionUtils.getCurrentTime() - jLongValue);
                MainFragment mainFragment2 = this.mainFragment;
                Intrinsics.checkNotNull(mainFragment2);
                mainFragment2.setStartPickerTime(0L);
            }
        }
        EditorObserver editorObserver = this.editorObserver;
        if (editorObserver != null) {
            editorObserver.onStartActivity();
        }
    }

    @Override // com.miui.keyguard.editor.base.ExitAnimationPerformer
    public void startExitTransformerAnimation(boolean z, TemplateApplier.ApplyCallback applyCallback, ExitAnimationPerformer exitAnimationPerformer) {
        Log.i("Keyguard-Editor-EditorActivity", "startExitTransformerAnimation -> lockScreenTransformerLayer = " + this.lockScreenTransformerLayer + " fromLock=" + z);
        if (!z) {
            switchButton(false);
            return;
        }
        LockScreenTransformerLayer lockScreenTransformerLayer = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer != null) {
            lockScreenTransformerLayer.setVisibility(0);
        }
        FrameLayout frameLayout = this.fragmentContainer;
        if (frameLayout != null) {
            frameLayout.setVisibility(8);
        }
        LockScreenTransformerLayer lockScreenTransformerLayer2 = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer2 != null) {
            lockScreenTransformerLayer2.startExitTransformerAnimation();
        }
    }

    public final void startTransformerAnimation(boolean z, long j, long j2) {
        if (z) {
            LockScreenTransformerLayer lockScreenTransformerLayer = this.lockScreenTransformerLayer;
            if (lockScreenTransformerLayer != null) {
                lockScreenTransformerLayer.startTransformerAnimation(j, j2);
                return;
            }
            return;
        }
        LockScreenTransformerLayer lockScreenTransformerLayer2 = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer2 != null) {
            lockScreenTransformerLayer2.startTransformerAnimationWithShowBouncer(j);
        }
    }

    public final void startTransformerAnimationUnlock() {
        LockScreenTransformerLayer lockScreenTransformerLayer = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer != null) {
            lockScreenTransformerLayer.startTransformerAnimationUnlock();
        }
    }

    public final void switchButton(boolean z) {
        ViewGroup viewGroup = this.rootView;
        ViewGroup viewGroup2 = null;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rootView");
            viewGroup = null;
        }
        float f = (-viewGroup.getHeight()) / 5.0f;
        if (!z) {
            ViewGroup viewGroup3 = this.buttonBar;
            if (viewGroup3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("buttonBar");
            } else {
                viewGroup2 = viewGroup3;
            }
            Folme.use((View) viewGroup2).state().to(new AnimState().add(ViewProperty.TRANSLATION_Y, f, new long[0]), new AnimConfig().setEase(-2, 0.95f, 0.3f));
            return;
        }
        ViewGroup viewGroup4 = this.buttonBar;
        if (viewGroup4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("buttonBar");
            viewGroup4 = null;
        }
        viewGroup4.setTranslationY(f);
        ViewGroup viewGroup5 = this.buttonBar;
        if (viewGroup5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("buttonBar");
        } else {
            viewGroup2 = viewGroup5;
        }
        Folme.use((View) viewGroup2).state().to(new AnimState().add(ViewProperty.TRANSLATION_Y, 0, new long[0]), new AnimConfig().setEase(-2, 0.95f, 0.3f));
    }

    public final void trackEditorOpen() {
        if (this instanceof CommonEditorActivity) {
            return;
        }
        if (this instanceof EditorActivityForSettings) {
            TrackHelper trackHelper = TrackHelper.INSTANCE;
            Context applicationContext = ((EditorActivityForSettings) this).getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
            trackHelper.trackOpenHomePage(applicationContext, true, tackId);
            return;
        }
        TrackHelper trackHelper2 = TrackHelper.INSTANCE;
        Context applicationContext2 = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext2, "getApplicationContext(...)");
        trackHelper2.trackOpenHomePage(applicationContext2, callingFromTheme(), tackId);
    }

    public final void tryClearFragmentBackStackEntries() {
        FrameLayout frameLayout;
        FragmentManager.BackStackEntry backStackEntryAt;
        try {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0 && (backStackEntryAt = getSupportFragmentManager().getBackStackEntryAt(0)) != null) {
                getSupportFragmentManager().popBackStackImmediate(Integer.valueOf(backStackEntryAt.getId()).intValue(), 1);
            }
            FrameLayout frameLayout2 = this.fragmentContainer;
            if ((frameLayout2 != null ? frameLayout2.getChildCount() : 0) <= 0 || (frameLayout = this.fragmentContainer) == null) {
                return;
            }
            frameLayout.removeAllViews();
        } catch (Exception e) {
            Log.e("Keyguard-Editor-EditorActivity", "tryClearFragmentBackStackEntries", e);
        }
    }

    public final void updateButtonLayoutZ(float f) {
        ViewGroup viewGroup = this.buttonBar;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("buttonBar");
            viewGroup = null;
        }
        viewGroup.setZ(f);
    }

    public final void updateButtonsOnPreviewChanged(boolean z) {
        TextView textView;
        TextView textView2;
        TextView textView3 = null;
        if (!z) {
            TextView textView4 = this.applyButton;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("applyButton");
                textView4 = null;
            }
            textView4.setClickable(true);
            TextView textView5 = this.applyButton;
            if (textView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("applyButton");
                textView = null;
            } else {
                textView = textView5;
            }
            AnimatorKt.alphaTo$default(textView, 1.0f, 0.5f, 0.0f, null, 12, null);
            TextView textView6 = this.cancelButton;
            if (textView6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("cancelButton");
                textView6 = null;
            }
            textView6.setText(R.string.kg_dialog_apply_linkage_video_wallpaper_negative_button);
            TextView textView7 = this.cancelButton;
            if (textView7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("cancelButton");
            } else {
                textView3 = textView7;
            }
            textView3.setContentDescription(getResources().getString(R.string.kg_talkback_cancel_wallpaper_custom));
            return;
        }
        TextView textView8 = this.applyButton;
        if (textView8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("applyButton");
            textView2 = null;
        } else {
            textView2 = textView8;
        }
        AnimatorKt.alphaTo$default(textView2, 0.0f, 0.5f, 0.0f, null, 12, null);
        TextView textView9 = this.cancelButton;
        if (textView9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cancelButton");
            textView9 = null;
        }
        int i = R.string.kg_exit;
        textView9.setText(i);
        TextView textView10 = this.cancelButton;
        if (textView10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cancelButton");
            textView10 = null;
        }
        textView10.setContentDescription(getResources().getString(i));
        TextView textView11 = this.applyButton;
        if (textView11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("applyButton");
        } else {
            textView3 = textView11;
        }
        textView3.setClickable(false);
    }

    public final void videoCheck() {
        Intent intent = getIntent();
        if (Intrinsics.areEqual(intent != null ? Boolean.valueOf(intent.getBooleanExtra("video_resource_checked", false)) : null, Boolean.FALSE)) {
            Intent intent2 = getIntent();
            if (TextUtils.equals(intent2 != null ? intent2.getStringExtra("resourceType") : null, "video")) {
                VideoCheckForDepthInterceptor videoCheckForDepthInterceptor = new VideoCheckForDepthInterceptor(this, false, false, 6, null);
                Intent intent3 = getIntent();
                videoCheckForDepthInterceptor.accessCheck(intent3 != null ? intent3.getData() : null, null);
            }
        }
    }

    public final void waitWallpaperFirstFrameThenSendEditorReady() {
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        final long jElapsedRealtime = SystemClock.elapsedRealtime();
        final Function1 function1 = new Function1() { // from class: com.miui.keyguard.editor.EditorActivity$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return EditorActivity.waitWallpaperFirstFrameThenSendEditorReady$lambda$29(booleanRef, jElapsedRealtime, (String) obj);
            }
        };
        final Runnable runnable = new Runnable() { // from class: com.miui.keyguard.editor.EditorActivity$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                function1.invoke("wallpaper_first_frame_timeout");
            }
        };
        LockScreenTransformerLayer lockScreenTransformerLayer = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer != null) {
            lockScreenTransformerLayer.postDelayed(runnable, 500L);
        }
        LockScreenTransformerLayer lockScreenTransformerLayer2 = this.lockScreenTransformerLayer;
        if (lockScreenTransformerLayer2 == null || !lockScreenTransformerLayer2.waitCurrentWallpaperFirstFrameReady(new Function0() { // from class: com.miui.keyguard.editor.EditorActivity$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return EditorActivity.waitWallpaperFirstFrameThenSendEditorReady$lambda$31(this.f$0, runnable, function1);
            }
        })) {
            LockScreenTransformerLayer lockScreenTransformerLayer3 = this.lockScreenTransformerLayer;
            if (lockScreenTransformerLayer3 != null) {
                lockScreenTransformerLayer3.removeCallbacks(runnable);
            }
            function1.invoke("no_wallpaper_first_frame_listener");
        }
    }
}
