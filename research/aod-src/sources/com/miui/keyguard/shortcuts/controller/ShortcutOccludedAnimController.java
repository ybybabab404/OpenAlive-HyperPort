package com.miui.keyguard.shortcuts.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Trace;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.RemoteAnimationTarget;
import android.view.ViewGroup;
import com.miui.keyguard.shortcuts.R;
import com.miui.keyguard.shortcuts.manager.MiuiShortcutManager;
import com.miui.keyguard.shortcuts.manager.OccludedAnimTrace;
import com.miui.keyguard.shortcuts.manager.ShortcutWindowManager;
import com.miui.keyguard.shortcuts.utils.AnimationUtils;
import com.miui.keyguard.shortcuts.utils.HapticFeedbackUtils;
import com.miui.keyguard.shortcuts.utils.LogUtils;
import com.miui.keyguard.shortcuts.utils.OccludedAnimationUtils;
import com.miui.keyguard.shortcuts.utils.ToastUtils;
import com.miui.keyguard.shortcuts.view.ShortcutOccludedAnimView;
import java.io.IOException;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.MainCoroutineDispatcher;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.IFolme;
import miuix.animation.IStateStyle;
import miuix.animation.IVisibleStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.base.AnimSpecialConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.ValueProperty;
import miuix.animation.property.ViewProperty;
import miuix.view.HapticFeedbackConstants;

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
/* JADX INFO: compiled from: ShortcutOccludedAnimController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ShortcutOccludedAnimController extends BaseShortcutAnimController<ShortcutOccludedAnimView> {
    public static final Companion Companion = new Companion(null);
    public final IStateStyle activeAnimFolme;
    public final IStateStyle backAnimFolme;
    public final Callback callback;
    public final Runnable dismissRunnable;
    public FakeImageAnimState fakeImageAnimState;
    public final IStateStyle fullAnimFolme;
    public boolean isActive;
    public boolean isActiveAnimPlaying;
    public boolean isBackAnimPlaying;
    public boolean isDismissAnimPlaying;
    public boolean isLeashAnimPlaying;
    public boolean isLeftShortcutTouched;
    public boolean isOccludedAnimStarted;
    public boolean isRightShortcutTouched;
    public boolean lastIsActive;
    public MainThreadCallback mainThreadCallback;
    public final IStateStyle occludedAnimForDefaultFolme;
    public final IStateStyle occludedAnimForShortcutFolme;
    public ShortcutOccludedAnimView shortcutAnimView;
    public boolean shortcutAnimViewShowing;
    public final IFolme shortcutViewAnimFolme;

    /* JADX INFO: compiled from: ShortcutOccludedAnimController.kt */
    @Metadata
    public interface Callback {

        /* JADX INFO: compiled from: ShortcutOccludedAnimController.kt */
        @Metadata
        public static final class DefaultImpls {
            public static void onFullscreenAnimationEnd(Callback callback) {
            }

            public static void onFullscreenAnimationStart(Callback callback) {
            }

            public static void reset(Callback callback, String reason) {
                Intrinsics.checkNotNullParameter(reason, "reason");
            }
        }

        void onBackAnimationEnd();

        void onBackAnimationUpdate(float f);

        void onDismissAnimationEnd();

        void onFullscreenAnimationEnd();

        void onFullscreenAnimationStart();

        void onOccludedAnimationEnd();

        void onOccludedAnimationStart();

        void reset(String str);
    }

    /* JADX INFO: compiled from: ShortcutOccludedAnimController.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: ShortcutOccludedAnimController.kt */
    @Metadata
    public enum FakeImageAnimState {
        IDLE,
        PLAYING,
        FINISHED;

        public static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    }

    /* JADX INFO: compiled from: ShortcutOccludedAnimController.kt */
    @Metadata
    public static final class MainThreadCallback implements Callback {
        public final Callback callback;
        public final Handler mainHandler;

        public MainThreadCallback(Callback callback, Handler mainHandler) {
            Intrinsics.checkNotNullParameter(callback, "callback");
            Intrinsics.checkNotNullParameter(mainHandler, "mainHandler");
            this.callback = callback;
            this.mainHandler = mainHandler;
        }

        public static final void onBackAnimationEnd$lambda$3(MainThreadCallback mainThreadCallback) {
            mainThreadCallback.callback.onBackAnimationEnd();
        }

        public static final void onBackAnimationUpdate$lambda$2(MainThreadCallback mainThreadCallback, float f) {
            mainThreadCallback.callback.onBackAnimationUpdate(f);
        }

        public static final void onDismissAnimationEnd$lambda$4(MainThreadCallback mainThreadCallback) {
            mainThreadCallback.callback.onDismissAnimationEnd();
        }

        public static final void onOccludedAnimationEnd$lambda$1(MainThreadCallback mainThreadCallback) {
            mainThreadCallback.callback.onOccludedAnimationEnd();
        }

        public static final void onOccludedAnimationStart$lambda$0(MainThreadCallback mainThreadCallback) {
            mainThreadCallback.callback.onOccludedAnimationStart();
        }

        public static final void reset$lambda$5(MainThreadCallback mainThreadCallback, String str) {
            mainThreadCallback.callback.reset(str);
        }

        @Override // com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController.Callback
        public void onBackAnimationEnd() {
            this.mainHandler.post(new Runnable() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$MainThreadCallback$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    ShortcutOccludedAnimController.MainThreadCallback.onBackAnimationEnd$lambda$3(this.f$0);
                }
            });
        }

        @Override // com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController.Callback
        public void onBackAnimationUpdate(final float f) {
            this.mainHandler.post(new Runnable() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$MainThreadCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ShortcutOccludedAnimController.MainThreadCallback.onBackAnimationUpdate$lambda$2(this.f$0, f);
                }
            });
        }

        @Override // com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController.Callback
        public void onDismissAnimationEnd() {
            this.mainHandler.post(new Runnable() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$MainThreadCallback$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ShortcutOccludedAnimController.MainThreadCallback.onDismissAnimationEnd$lambda$4(this.f$0);
                }
            });
        }

        @Override // com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController.Callback
        public void onFullscreenAnimationEnd() {
            Callback.DefaultImpls.onFullscreenAnimationEnd(this);
        }

        @Override // com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController.Callback
        public void onFullscreenAnimationStart() {
            Callback.DefaultImpls.onFullscreenAnimationStart(this);
        }

        @Override // com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController.Callback
        public void onOccludedAnimationEnd() {
            this.mainHandler.post(new Runnable() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$MainThreadCallback$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    ShortcutOccludedAnimController.MainThreadCallback.onOccludedAnimationEnd$lambda$1(this.f$0);
                }
            });
        }

        @Override // com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController.Callback
        public void onOccludedAnimationStart() {
            this.mainHandler.post(new Runnable() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$MainThreadCallback$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    ShortcutOccludedAnimController.MainThreadCallback.onOccludedAnimationStart$lambda$0(this.f$0);
                }
            });
        }

        @Override // com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController.Callback
        public void reset(final String reason) {
            Intrinsics.checkNotNullParameter(reason, "reason");
            Callback.DefaultImpls.reset(this, reason);
            this.mainHandler.post(new Runnable() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$MainThreadCallback$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    ShortcutOccludedAnimController.MainThreadCallback.reset$lambda$5(this.f$0, reason);
                }
            });
        }
    }

    /* JADX INFO: compiled from: ShortcutOccludedAnimController.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FakeImageAnimState.values().length];
            try {
                iArr[FakeImageAnimState.IDLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FakeImageAnimState.FINISHED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FakeImageAnimState.PLAYING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$doOccludedAnimationForDefault$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ShortcutOccludedAnimController.kt */
    @Metadata
    public static final class C01531 extends TransitionListener {
        public C01531() {
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onCancel(Object obj) {
            ShortcutOccludedAnimController.this.isLeashAnimPlaying = false;
            ShortcutOccludedAnimController.this.getShortcutAnimView().doOccludedAnimationEnd();
            Handler mainHandler = ShortcutOccludedAnimController.this.getMainHandler();
            final ShortcutOccludedAnimController shortcutOccludedAnimController = ShortcutOccludedAnimController.this;
            mainHandler.post(new Runnable() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$doOccludedAnimationForDefault$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() throws IOException {
                    shortcutOccludedAnimController.reset("doOccludedAnimationForDefault#onCancel");
                }
            });
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            ShortcutOccludedAnimController.this.isLeashAnimPlaying = false;
            ShortcutOccludedAnimController.this.getShortcutAnimView().doOccludedAnimationEnd();
            Handler mainHandler = ShortcutOccludedAnimController.this.getMainHandler();
            final ShortcutOccludedAnimController shortcutOccludedAnimController = ShortcutOccludedAnimController.this;
            mainHandler.post(new Runnable() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$doOccludedAnimationForDefault$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws IOException {
                    shortcutOccludedAnimController.reset("doOccludedAnimationForDefault#onComplete");
                }
            });
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onUpdate(Object obj, Collection collection) {
            OccludedAnimationUtils occludedAnimationUtils = OccludedAnimationUtils.INSTANCE;
            UpdateInfo updateInfoFindBy = UpdateInfo.findBy(collection, occludedAnimationUtils.getTAG_DEFAULT_LEASH_SCALE());
            UpdateInfo updateInfoFindBy2 = UpdateInfo.findBy(collection, occludedAnimationUtils.getTAG_DEFAULT_LEASH_ALPHA());
            ShortcutOccludedAnimController.this.getShortcutAnimView().doOccludedAnimationForDefault(updateInfoFindBy != null ? updateInfoFindBy.getFloatValue() : 0.9f, updateInfoFindBy2 != null ? updateInfoFindBy2.getFloatValue() : 0.0f);
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$doOccludedAnimationForShortcut$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ShortcutOccludedAnimController.kt */
    @Metadata
    public static final class C01541 extends TransitionListener {
        public final /* synthetic */ boolean $needHideBlurWhenOccludedAnim;

        public C01541(boolean z) {
            this.$needHideBlurWhenOccludedAnim = z;
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onBegin(Object obj) {
            OccludedAnimTrace.INSTANCE.startForLeashShow();
            ShortcutOccludedAnimController.this.getShortcutAnimView().initSynSurfaceTransactionApplier();
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onCancel(Object obj) {
            ShortcutOccludedAnimController.this.isLeashAnimPlaying = false;
            ShortcutOccludedAnimController.this.getShortcutAnimView().doOccludedAnimationEnd();
            Handler mainHandler = ShortcutOccludedAnimController.this.getMainHandler();
            final ShortcutOccludedAnimController shortcutOccludedAnimController = ShortcutOccludedAnimController.this;
            mainHandler.post(new Runnable() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$doOccludedAnimationForShortcut$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() throws IOException {
                    shortcutOccludedAnimController.reset("doOccludedAnimationForShortcut#onCancel");
                }
            });
            OccludedAnimTrace.INSTANCE.endTraceForLeashShow();
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) throws IOException {
            ShortcutOccludedAnimController.this.isLeashAnimPlaying = false;
            if (!this.$needHideBlurWhenOccludedAnim) {
                ShortcutOccludedAnimController.this.updateBlurRatio(0.0f);
            }
            ShortcutWindowManager.INSTANCE.hideWindowSurface();
            ShortcutOccludedAnimController.this.getShortcutAnimView().doOccludedAnimationEnd();
            Handler mainHandler = ShortcutOccludedAnimController.this.getMainHandler();
            final ShortcutOccludedAnimController shortcutOccludedAnimController = ShortcutOccludedAnimController.this;
            mainHandler.post(new Runnable() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$doOccludedAnimationForShortcut$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws IOException {
                    shortcutOccludedAnimController.reset("doOccludedAnimationForShortcut#onComplete");
                }
            });
            OccludedAnimTrace.INSTANCE.endTraceForLeashShow();
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onUpdate(Object obj, Collection collection) throws IOException {
            OccludedAnimationUtils occludedAnimationUtils = OccludedAnimationUtils.INSTANCE;
            UpdateInfo updateInfoFindBy = UpdateInfo.findBy(collection, occludedAnimationUtils.getTAG_SHORTCUT_LEASH_ALPHA());
            UpdateInfo updateInfoFindBy2 = UpdateInfo.findBy(collection, occludedAnimationUtils.getTAG_SHORTCUT_ICON_ALPHA());
            float floatValue = updateInfoFindBy != null ? updateInfoFindBy.getFloatValue() : 0.0f;
            float floatValue2 = updateInfoFindBy2 != null ? updateInfoFindBy2.getFloatValue() : 1.0f;
            UpdateInfo updateInfoFindBy3 = UpdateInfo.findBy(collection, occludedAnimationUtils.getTAG_FAKE_ALPHA_ANIM());
            ShortcutOccludedAnimController.this.getShortcutAnimView().doOccludedAnimationForShortcut(floatValue, floatValue2, updateInfoFindBy3 != null ? updateInfoFindBy3.getFloatValue() : 0.4f);
            if (this.$needHideBlurWhenOccludedAnim) {
                UpdateInfo updateInfoFindBy4 = UpdateInfo.findBy(collection, occludedAnimationUtils.getTAG_BLUR_RATIO_ANIM());
                ShortcutOccludedAnimController.this.updateBlurRatio(updateInfoFindBy4 != null ? updateInfoFindBy4.getFloatValue() : 1.0f);
            }
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$startShortcutActivity$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ShortcutOccludedAnimController.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$startShortcutActivity$1", f = "ShortcutOccludedAnimController.kt", l = {539}, m = "invokeSuspend")
    public static final class C01571 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$startShortcutActivity$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ShortcutOccludedAnimController.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$startShortcutActivity$1$1", f = "ShortcutOccludedAnimController.kt", l = {}, m = "invokeSuspend")
        public static final class C00391 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public int label;
            public final /* synthetic */ ShortcutOccludedAnimController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00391(ShortcutOccludedAnimController shortcutOccludedAnimController, Continuation continuation) {
                super(2, continuation);
                this.this$0 = shortcutOccludedAnimController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00391(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00391) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                ShortcutOccludedAnimController.dismissShortcutAnimView$default(this.this$0, "startShortcutActivity", false, 2, null);
                return Unit.INSTANCE;
            }
        }

        public C01571(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShortcutOccludedAnimController.this.new C01571(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01571) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                MiuiShortcutManager miuiShortcutManager = MiuiShortcutManager.INSTANCE;
                if (miuiShortcutManager.isKeyguardGoingAway()) {
                    return Unit.INSTANCE;
                }
                Trace.beginSection("startShortcutActivity");
                long j = miuiShortcutManager.startShortcutActivity() ? 5000L : 0L;
                Trace.endSection();
                if (miuiShortcutManager.isOccluded()) {
                    MainCoroutineDispatcher main = Dispatchers.getMain();
                    C00391 c00391 = new C00391(ShortcutOccludedAnimController.this, null);
                    this.label = 1;
                    if (BuildersKt.withContext(main, c00391, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    Boxing.boxBoolean(ShortcutOccludedAnimController.this.getMainHandler().postDelayed(ShortcutOccludedAnimController.this.dismissRunnable, j));
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShortcutOccludedAnimController(final Context context, ViewGroup viewGroup, Callback callback) {
        super(context, viewGroup);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(viewGroup, "viewGroup");
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.callback = callback;
        this.shortcutAnimView = new ShortcutOccludedAnimView(context);
        this.occludedAnimForShortcutFolme = Folme.useValue("TAG_OCCLUDED_ANIM_FOR_SHORTCUT");
        this.occludedAnimForDefaultFolme = Folme.useValue("TAG_OCCLUDED_ANIM_FOR_DEFAULT");
        this.fullAnimFolme = Folme.useValue(OccludedAnimationUtils.INSTANCE.getTAG_FULL_ANIM());
        this.activeAnimFolme = Folme.useValue("TAG_ACTIVE_ANIM");
        this.backAnimFolme = Folme.useValue("TAG_BACK_ANIM");
        this.shortcutViewAnimFolme = Folme.useAt(getShortcutAnimView());
        this.fakeImageAnimState = FakeImageAnimState.IDLE;
        this.mainThreadCallback = new MainThreadCallback(callback, getMainHandler());
        this.dismissRunnable = new Runnable() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() throws IOException {
                ShortcutOccludedAnimController.dismissRunnable$lambda$1(context, this);
            }
        };
    }

    public static final void checkOccludedRemoteAnimation$lambda$4(final ShortcutOccludedAnimController shortcutOccludedAnimController) {
        if (shortcutOccludedAnimController.shortcutAnimViewShowing) {
            shortcutOccludedAnimController.doOccludedAnimationInternal();
        } else {
            shortcutOccludedAnimController.shortcutAnimViewShowing = true;
            shortcutOccludedAnimController.addAnimShortcutView(new Function0() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$$ExternalSyntheticLambda6
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return ShortcutOccludedAnimController.checkOccludedRemoteAnimation$lambda$4$lambda$3(this.f$0);
                }
            });
        }
    }

    public static final Unit checkOccludedRemoteAnimation$lambda$4$lambda$3(ShortcutOccludedAnimController shortcutOccludedAnimController) {
        shortcutOccludedAnimController.doOccludedAnimationInternal();
        return Unit.INSTANCE;
    }

    public static final void dismissRunnable$lambda$1(Context context, ShortcutOccludedAnimController shortcutOccludedAnimController) throws IOException {
        ToastUtils.INSTANCE.show(context, R.string.keyguard_shortcut_open_failed);
        shortcutOccludedAnimController.mainThreadCallback.onBackAnimationEnd();
        shortcutOccludedAnimController.reset("dismissRunnable");
    }

    public static /* synthetic */ void dismissShortcutAnimView$default(ShortcutOccludedAnimController shortcutOccludedAnimController, String str, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        shortcutOccludedAnimController.dismissShortcutAnimView(str, z);
    }

    public static final Unit dismissShortcutAnimView$lambda$5(ShortcutOccludedAnimController shortcutOccludedAnimController, String str) {
        shortcutOccludedAnimController.mainThreadCallback.reset(str);
        shortcutOccludedAnimController.isDismissAnimPlaying = false;
        shortcutOccludedAnimController.mainThreadCallback.onDismissAnimationEnd();
        return Unit.INSTANCE;
    }

    public static final Unit startFullScreenAnim$lambda$6(ShortcutOccludedAnimController shortcutOccludedAnimController) {
        OccludedAnimTrace.INSTANCE.startForFullAnim();
        if (shortcutOccludedAnimController.shortcutAnimViewShowing && shortcutOccludedAnimController.fakeImageAnimState == FakeImageAnimState.IDLE) {
            shortcutOccludedAnimController.fakeImageAnimState = FakeImageAnimState.PLAYING;
        }
        return Unit.INSTANCE;
    }

    public static final Unit startFullScreenAnim$lambda$7(ShortcutOccludedAnimController shortcutOccludedAnimController) {
        shortcutOccludedAnimController.fakeImageAnimState = (shortcutOccludedAnimController.shortcutAnimViewShowing && shortcutOccludedAnimController.fakeImageAnimState == FakeImageAnimState.PLAYING) ? FakeImageAnimState.FINISHED : FakeImageAnimState.IDLE;
        MiuiShortcutManager miuiShortcutManager = MiuiShortcutManager.INSTANCE;
        if (!miuiShortcutManager.getInteractive() && !shortcutOccludedAnimController.isDismissAnimPlaying && miuiShortcutManager.fullAodEnable(shortcutOccludedAnimController.getContext())) {
            dismissShortcutAnimView$default(shortcutOccludedAnimController, "startFullScreenAnim end", false, 2, null);
        }
        shortcutOccludedAnimController.callback.onFullscreenAnimationEnd();
        OccludedAnimTrace.INSTANCE.endTraceForFullAnim();
        return Unit.INSTANCE;
    }

    public final boolean checkOccludedRemoteAnimation(RemoteAnimationTarget[] targetApps, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        Intrinsics.checkNotNullParameter(targetApps, "targetApps");
        setOccludedAnimStarted(getShortcutAnimView().checkOccludedRemoteAnimation(targetApps, iRemoteAnimationFinishedCallback));
        if (this.isOccludedAnimStarted) {
            MiuiShortcutManager.INSTANCE.setInAppOpening(true);
            LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutOccludedAnimController", "checkOccludedRemoteAnimation, fakeImageAnimState=" + this.fakeImageAnimState, null, 4, null);
            getMainHandler().removeCallbacks(this.dismissRunnable);
            getMainHandler().post(new Runnable() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    ShortcutOccludedAnimController.checkOccludedRemoteAnimation$lambda$4(this.f$0);
                }
            });
        }
        LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutOccludedAnimController", "checkOccludedRemoteAnimation, isOccludedAnimStarted=" + this.isOccludedAnimStarted, null, 4, null);
        return this.isOccludedAnimStarted;
    }

    @Override // com.miui.keyguard.shortcuts.controller.BaseShortcutAnimController
    public void destroy() {
        super.destroy();
        getMainHandler().removeCallbacks(this.dismissRunnable);
        this.occludedAnimForShortcutFolme.cancel();
        this.occludedAnimForDefaultFolme.cancel();
    }

    public final void dismissShortcutAnimView(final String str, boolean z) {
        LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutOccludedAnimController", "dismissShortcutAnimView reason:" + str + ", isDismissAnimPlaying:" + this.isDismissAnimPlaying + ", shortcutAnimViewShowing:" + this.shortcutAnimViewShowing + "， withAnim:" + z, null, 4, null);
        if (!(this.isDismissAnimPlaying && z) && this.shortcutAnimViewShowing) {
            final Function0 function0 = new Function0() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return ShortcutOccludedAnimController.dismissShortcutAnimView$lambda$5(this.f$0, str);
                }
            };
            IVisibleStyle iVisibleStyleVisible = this.shortcutViewAnimFolme.visible();
            if (z) {
                this.isDismissAnimPlaying = true;
                iVisibleStyleVisible.hide(new AnimConfig().setEase(FolmeEase.spring(0.9f, 0.3f)).addListeners(new TransitionListener() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController.dismissShortcutAnimView.1
                    @Override // miuix.animation.listener.TransitionListener
                    public void onBegin(Object obj) {
                        super.onBegin(obj);
                        OccludedAnimTrace.INSTANCE.startForDismissAnim();
                    }

                    @Override // miuix.animation.listener.TransitionListener
                    public void onCancel(Object obj) {
                        super.onCancel(obj);
                        ShortcutOccludedAnimController.this.isDismissAnimPlaying = false;
                        ShortcutOccludedAnimController.this.mainThreadCallback.onDismissAnimationEnd();
                        OccludedAnimTrace.INSTANCE.endTraceForDismissAnim();
                    }

                    @Override // miuix.animation.listener.TransitionListener
                    public void onComplete(Object obj) {
                        super.onComplete(obj);
                        function0.invoke();
                        OccludedAnimTrace.INSTANCE.endTraceForDismissAnim();
                    }

                    @Override // miuix.animation.listener.TransitionListener
                    public void onUpdate(Object obj, Collection collection) throws IOException {
                        super.onUpdate(obj, collection);
                        ShortcutOccludedAnimController shortcutOccludedAnimController = ShortcutOccludedAnimController.this;
                        UpdateInfo updateInfoFindBy = UpdateInfo.findBy(collection, ViewProperty.AUTO_ALPHA);
                        shortcutOccludedAnimController.updateBlurRatio(updateInfoFindBy != null ? updateInfoFindBy.getFloatValue() : 0.0f);
                    }
                }));
                return;
            }
            iVisibleStyleVisible.setHide();
            if (this.isDismissAnimPlaying) {
                iVisibleStyleVisible.end(new Object[0]);
            } else {
                function0.invoke();
            }
        }
    }

    public final void doOccludedAnimationForDefault() {
        this.isLeashAnimPlaying = true;
        AnimConfig animConfig = new AnimConfig();
        OccludedAnimationUtils occludedAnimationUtils = OccludedAnimationUtils.INSTANCE;
        ValueProperty tag_default_leash_scale = occludedAnimationUtils.getTAG_DEFAULT_LEASH_SCALE();
        AnimConfig animConfigDefaultLeashScale = occludedAnimationUtils.getAnimConfigDefaultLeashScale();
        Intrinsics.checkNotNull(animConfigDefaultLeashScale, "null cannot be cast to non-null type miuix.animation.base.AnimSpecialConfig");
        AnimConfig special = animConfig.setSpecial(tag_default_leash_scale, (AnimSpecialConfig) animConfigDefaultLeashScale);
        ValueProperty tag_default_leash_alpha = occludedAnimationUtils.getTAG_DEFAULT_LEASH_ALPHA();
        AnimConfig animConfigDefaultLeashAlpha = occludedAnimationUtils.getAnimConfigDefaultLeashAlpha();
        Intrinsics.checkNotNull(animConfigDefaultLeashAlpha, "null cannot be cast to non-null type miuix.animation.base.AnimSpecialConfig");
        this.occludedAnimForDefaultFolme.setTo(getShortcutAnimView().buildOccludedAnimForDefaultStateStart()).to(getShortcutAnimView().buildOccludedAnimForDefaultStateEnd(), special.setSpecial(tag_default_leash_alpha, (AnimSpecialConfig) animConfigDefaultLeashAlpha).addListeners(new C01531()));
    }

    public final void doOccludedAnimationForShortcut() {
        this.isLeashAnimPlaying = true;
        boolean zIsNeedHideBlurWhenOccludedAnim = getShortcutAnimView().isNeedHideBlurWhenOccludedAnim(getContext());
        AnimConfig animConfig = new AnimConfig();
        OccludedAnimationUtils occludedAnimationUtils = OccludedAnimationUtils.INSTANCE;
        ValueProperty tag_shortcut_leash_alpha = occludedAnimationUtils.getTAG_SHORTCUT_LEASH_ALPHA();
        AnimConfig animConfigShortcutLeashAlpha = occludedAnimationUtils.getAnimConfigShortcutLeashAlpha();
        Intrinsics.checkNotNull(animConfigShortcutLeashAlpha, "null cannot be cast to non-null type miuix.animation.base.AnimSpecialConfig");
        AnimConfig special = animConfig.setSpecial(tag_shortcut_leash_alpha, (AnimSpecialConfig) animConfigShortcutLeashAlpha);
        ValueProperty tag_shortcut_icon_alpha = occludedAnimationUtils.getTAG_SHORTCUT_ICON_ALPHA();
        AnimConfig animConfigShortcutIconAlpha = occludedAnimationUtils.getAnimConfigShortcutIconAlpha();
        Intrinsics.checkNotNull(animConfigShortcutIconAlpha, "null cannot be cast to non-null type miuix.animation.base.AnimSpecialConfig");
        AnimConfig special2 = special.setSpecial(tag_shortcut_icon_alpha, (AnimSpecialConfig) animConfigShortcutIconAlpha);
        ValueProperty tag_blur_ratio_anim = occludedAnimationUtils.getTAG_BLUR_RATIO_ANIM();
        AnimConfig animConfigBlurRatio = occludedAnimationUtils.getAnimConfigBlurRatio();
        Intrinsics.checkNotNull(animConfigBlurRatio, "null cannot be cast to non-null type miuix.animation.base.AnimSpecialConfig");
        AnimConfig special3 = special2.setSpecial(tag_blur_ratio_anim, (AnimSpecialConfig) animConfigBlurRatio);
        ValueProperty tag_fake_alpha_anim = occludedAnimationUtils.getTAG_FAKE_ALPHA_ANIM();
        AnimConfig animConfigFakeAlpha = occludedAnimationUtils.getAnimConfigFakeAlpha();
        Intrinsics.checkNotNull(animConfigFakeAlpha, "null cannot be cast to non-null type miuix.animation.base.AnimSpecialConfig");
        this.occludedAnimForShortcutFolme.setTo(getShortcutAnimView().buildOccludedAnimForShortcutStateStart()).to(getShortcutAnimView().buildOccludedAnimForShortcutStateEnd(), special3.setSpecial(tag_fake_alpha_anim, (AnimSpecialConfig) animConfigFakeAlpha).addListeners(new C01541(zIsNeedHideBlurWhenOccludedAnim)));
    }

    public final void doOccludedAnimationInternal() {
        int i = WhenMappings.$EnumSwitchMapping$0[this.fakeImageAnimState.ordinal()];
        if (i == 1) {
            doOccludedAnimationForDefault();
        } else {
            if (i != 2 && i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            doOccludedAnimationForShortcut();
        }
    }

    @Override // com.miui.keyguard.shortcuts.controller.BaseShortcutAnimController
    public ShortcutOccludedAnimView getShortcutAnimView() {
        return this.shortcutAnimView;
    }

    public final void handleMoveDistanceChanged() throws IOException {
        if (this.fakeImageAnimState == FakeImageAnimState.PLAYING) {
            return;
        }
        setMovePer(AnimationUtils.INSTANCE.perFromVal(getMoveDistance(), 0.0f, getShortcutAnimView().getCurrentScreenWidth() / 3));
        if (!this.isDismissAnimPlaying) {
            updateBlurRatio(RangesKt.coerceIn(getMoveDistance() / 270.0f, 0.0f, 1.0f));
        }
        updateActiveAnim();
        setMovePer(getMovePer() + (getActiveAnimPer() * 0.4f));
        setMovePer(Math.max(getMovePer(), 0.0f));
        setMoveYPer(Math.min(0.0f, getMoveDistanceY()) / getShortcutAnimView().getCurrentScreenHeight());
        getShortcutAnimView().setAlpha(1.0f);
        getShortcutAnimView().updateViews(getMovePer(), getMoveYPer(), getActiveAnimPer());
    }

    public final boolean isActive() {
        return this.isActive;
    }

    public boolean isAnyAnimPlaying() {
        return this.isActiveAnimPlaying || this.isBackAnimPlaying || this.isLeashAnimPlaying || this.fakeImageAnimState != FakeImageAnimState.IDLE;
    }

    public final boolean isOccludedAnimStartOrPlaying() {
        return this.isOccludedAnimStarted;
    }

    @Override // com.miui.keyguard.shortcuts.controller.BaseShortcutAnimController
    public void keyguardGoingAway() {
        super.keyguardGoingAway();
        getMainHandler().removeCallbacks(this.dismissRunnable);
        dismissShortcutAnimView$default(this, "keyguardGoingAway", false, 2, null);
    }

    public void onBouncerShownChanged(boolean z) {
        getBlurHelper().isBouncerShowing(z);
        if (z) {
            getMainHandler().removeCallbacks(this.dismissRunnable);
            dismissShortcutAnimView$default(this, "onBouncerShow", false, 2, null);
        }
    }

    @Override // com.miui.keyguard.shortcuts.controller.BaseShortcutAnimController
    public void onChargeAnimationStart() {
        super.onChargeAnimationStart();
        if (this.isRightShortcutTouched || this.isLeftShortcutTouched) {
            dismissShortcutAnimView$default(this, "onChargeAnimationStart", false, 2, null);
        }
    }

    @Override // com.miui.keyguard.shortcuts.controller.BaseShortcutAnimController
    public void onFinishedGoingToSleep() {
        super.onFinishedGoingToSleep();
        getMainHandler().removeCallbacks(this.dismissRunnable);
        FakeImageAnimState fakeImageAnimState = this.fakeImageAnimState;
        if (fakeImageAnimState != FakeImageAnimState.IDLE) {
            if (fakeImageAnimState == FakeImageAnimState.FINISHED || fakeImageAnimState == FakeImageAnimState.PLAYING) {
                if (fakeImageAnimState == FakeImageAnimState.PLAYING) {
                    this.fullAnimFolme.cancel();
                }
                dismissShortcutAnimView("onFinishedGoingToSleep", MiuiShortcutManager.INSTANCE.fullAodEnable(getContext()));
                return;
            }
            return;
        }
        if (MiuiShortcutManager.INSTANCE.fullAodEnable(getContext())) {
            startBackAnim();
        } else if (this.isLeftShortcutTouched || this.isRightShortcutTouched) {
            this.mainThreadCallback.onBackAnimationEnd();
        }
    }

    public void onFoldStateChanged(boolean z) {
        if (z) {
            getMainHandler().removeCallbacks(this.dismissRunnable);
            dismissShortcutAnimView$default(this, "onFoldStateChanged->True", false, 2, null);
        }
    }

    @Override // com.miui.keyguard.shortcuts.controller.BaseShortcutAnimController
    public void onKeyguardOccludedChanged(boolean z) {
        super.onKeyguardOccludedChanged(z);
        if (z && getMainHandler().hasCallbacks(this.dismissRunnable)) {
            LogUtils.logI$default(LogUtils.INSTANCE, "ShortcutOccludedAnimController", "onKeyguardOccludedChanged true has dismissRunnable, isDismissAnimPlaying: " + this.isDismissAnimPlaying, null, 4, null);
            getMainHandler().removeCallbacks(this.dismissRunnable);
            dismissShortcutAnimView$default(this, "onKeyguardOccludedChanged#true", false, 2, null);
        }
    }

    @Override // com.miui.keyguard.shortcuts.controller.BaseShortcutAnimController
    public void onKeyguardShowingChanged(boolean z) throws IOException {
        if (z) {
            return;
        }
        getMainHandler().removeCallbacks(this.dismissRunnable);
        dismissShortcutAnimView$default(this, "keyguardShowing.not", false, 2, null);
        if (this.isOccludedAnimStarted) {
            reset("keyguardShowing");
        }
    }

    public final void onMoveDistanceUpdate(float f, float f2) throws IOException {
        setMoveDistance(f);
        setMoveDistanceY(f2);
        handleMoveDistanceChanged();
    }

    @Override // com.miui.keyguard.shortcuts.controller.BaseShortcutAnimController
    public void onStartedWakingUp() {
        super.onStartedWakingUp();
        if (this.isBackAnimPlaying) {
            Folme.end(getShortcutAnimView());
            Folme.end("TAG_BACK_ANIM");
        }
    }

    public final void onTouchDown(float f, float f2, boolean z) {
        if (this.shortcutAnimViewShowing || this.fakeImageAnimState == FakeImageAnimState.PLAYING) {
            return;
        }
        setMoveYPer(0.0f);
        this.isActive = false;
        this.lastIsActive = false;
        setActiveAnimPer(0.0f);
        if (this.shortcutAnimViewShowing) {
            return;
        }
        this.shortcutAnimViewShowing = true;
        BaseShortcutAnimController.addAnimShortcutView$default(this, null, 1, null);
        getShortcutAnimView().setAlpha(0.0f);
        if (z) {
            this.isLeftShortcutTouched = true;
        } else {
            this.isRightShortcutTouched = true;
        }
        getShortcutAnimView().onTouchDown(z);
        updateShortcutView(z ? R.id.shortcut_view_left : R.id.shortcut_view_right);
        MiuiShortcutManager miuiShortcutManager = MiuiShortcutManager.INSTANCE;
        miuiShortcutManager.setOccludedMovedShortcut(z);
        miuiShortcutManager.setInAppOpening(true);
    }

    public final void onTouchUp(float f, float f2) {
        this.isLeftShortcutTouched = false;
        this.isRightShortcutTouched = false;
        if (this.isActive) {
            startFullScreenAnim();
        } else {
            startBackAnim();
            MiuiShortcutManager.INSTANCE.resetOccludedMovedShortcut();
        }
    }

    @Override // com.miui.keyguard.shortcuts.controller.BaseShortcutAnimController
    public void reset(String reason) throws IOException {
        Intrinsics.checkNotNullParameter(reason, "reason");
        if (this.shortcutAnimViewShowing) {
            super.reset(reason);
            ShortcutOccludedAnimView shortcutAnimView = getShortcutAnimView();
            shortcutAnimView.setScaleX(1.0f);
            shortcutAnimView.setScaleY(1.0f);
            this.shortcutAnimViewShowing = false;
            setMoveDistance(0.0f);
            handleMoveDistanceChanged();
            getShortcutAnimView().cancelFullAnim();
            this.fakeImageAnimState = FakeImageAnimState.IDLE;
            this.isLeftShortcutTouched = false;
            this.isRightShortcutTouched = false;
            setOccludedAnimStarted(false);
            this.isActiveAnimPlaying = false;
            this.isBackAnimPlaying = false;
            this.isLeashAnimPlaying = false;
            MiuiShortcutManager.INSTANCE.setInAppOpening(false);
        }
    }

    public final void setOccludedAnimStarted(boolean z) {
        if (this.isOccludedAnimStarted != z) {
            this.isOccludedAnimStarted = z;
            if (!z) {
                this.mainThreadCallback.onOccludedAnimationEnd();
                return;
            }
            if (this.isRightShortcutTouched || this.isLeftShortcutTouched) {
                getMainHandler().post(new Runnable() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        ShortcutOccludedAnimController.dismissShortcutAnimView$default(this.f$0, "OccludedAnimStarted", false, 2, null);
                    }
                });
            }
            this.mainThreadCallback.onOccludedAnimationStart();
        }
    }

    public final void startActiveAnim(float f, float f2) {
        if (f == f2) {
            return;
        }
        this.isActiveAnimPlaying = true;
        this.activeAnimFolme.setTo("ANIM_STEP_VALUE", Float.valueOf(f)).to("ANIM_STEP_VALUE", Float.valueOf(f2), new AnimConfig().setEase(FolmeEase.spring(0.9f, 0.2f)).addListeners(new TransitionListener() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController.startActiveAnim.1
            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                ShortcutOccludedAnimController.this.isActiveAnimPlaying = false;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                ShortcutOccludedAnimController.this.isActiveAnimPlaying = false;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) throws IOException {
                UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, "ANIM_STEP_VALUE");
                if (updateInfoFindByName == null) {
                    return;
                }
                ShortcutOccludedAnimController.this.setActiveAnimPer(RangesKt.coerceAtLeast(RangesKt.coerceAtMost(updateInfoFindByName.getFloatValue(), 1.0f), 0.0f));
                ShortcutOccludedAnimController.this.handleMoveDistanceChanged();
            }
        }));
    }

    public final void startBackAnim() {
        if (getMoveDistance() <= 0.0f) {
            this.mainThreadCallback.onBackAnimationEnd();
            return;
        }
        this.isBackAnimPlaying = true;
        AnimConfig animConfig = new AnimConfig();
        OccludedAnimationUtils occludedAnimationUtils = OccludedAnimationUtils.INSTANCE;
        ValueProperty tag_back_anim_inactive = occludedAnimationUtils.getTAG_BACK_ANIM_INACTIVE();
        AnimConfig animConfigBackAnimInactive = occludedAnimationUtils.getAnimConfigBackAnimInactive();
        Intrinsics.checkNotNull(animConfigBackAnimInactive, "null cannot be cast to non-null type miuix.animation.base.AnimSpecialConfig");
        AnimConfig special = animConfig.setSpecial(tag_back_anim_inactive, (AnimSpecialConfig) animConfigBackAnimInactive);
        ValueProperty tag_back_anim_distance = occludedAnimationUtils.getTAG_BACK_ANIM_DISTANCE();
        AnimConfig animConfigBackAnimDistance = occludedAnimationUtils.getAnimConfigBackAnimDistance();
        Intrinsics.checkNotNull(animConfigBackAnimDistance, "null cannot be cast to non-null type miuix.animation.base.AnimSpecialConfig");
        this.backAnimFolme.setTo(getShortcutAnimView().buildBackAnimStateStart(getMoveDistance())).to(getShortcutAnimView().buildBackAnimStateEnd(), special.setSpecial(tag_back_anim_distance, (AnimSpecialConfig) animConfigBackAnimDistance).addListeners(new TransitionListener() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController.startBackAnim.1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                super.onBegin(obj);
                OccludedAnimTrace.INSTANCE.startForBackAnim();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                ShortcutOccludedAnimController.this.isBackAnimPlaying = false;
                ShortcutOccludedAnimController.this.mainThreadCallback.onBackAnimationEnd();
                OccludedAnimTrace.INSTANCE.endTraceForBackAnim();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                ShortcutOccludedAnimController.this.isBackAnimPlaying = false;
                ShortcutOccludedAnimController.this.mainThreadCallback.onBackAnimationEnd();
                OccludedAnimTrace.INSTANCE.endTraceForBackAnim();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) throws IOException {
                OccludedAnimationUtils occludedAnimationUtils2 = OccludedAnimationUtils.INSTANCE;
                UpdateInfo updateInfoFindBy = UpdateInfo.findBy(collection, occludedAnimationUtils2.getTAG_BACK_ANIM_INACTIVE());
                UpdateInfo updateInfoFindBy2 = UpdateInfo.findBy(collection, occludedAnimationUtils2.getTAG_BACK_ANIM_DISTANCE());
                float floatValue = updateInfoFindBy != null ? updateInfoFindBy.getFloatValue() : 0.0f;
                float floatValue2 = updateInfoFindBy2 != null ? updateInfoFindBy2.getFloatValue() : 0.0f;
                ShortcutOccludedAnimController shortcutOccludedAnimController = ShortcutOccludedAnimController.this;
                float f = 1 - floatValue;
                shortcutOccludedAnimController.setActiveAnimPer(shortcutOccludedAnimController.getActiveAnimPer() * f);
                ShortcutOccludedAnimController shortcutOccludedAnimController2 = ShortcutOccludedAnimController.this;
                shortcutOccludedAnimController2.setActiveAnimPer(shortcutOccludedAnimController2.getActiveAnimPer() >= 0.0f ? ShortcutOccludedAnimController.this.getActiveAnimPer() : 0.0f);
                ShortcutOccludedAnimController shortcutOccludedAnimController3 = ShortcutOccludedAnimController.this;
                shortcutOccludedAnimController3.setMoveYPer(shortcutOccludedAnimController3.getMoveYPer() * f);
                ShortcutOccludedAnimController.this.setMoveDistance(floatValue2);
                ShortcutOccludedAnimController.this.handleMoveDistanceChanged();
                ShortcutOccludedAnimController.this.mainThreadCallback.onBackAnimationUpdate(ShortcutOccludedAnimController.this.getMoveDistance());
            }
        }));
        MiuiShortcutManager miuiShortcutManager = MiuiShortcutManager.INSTANCE;
        if (!miuiShortcutManager.getInteractive() && this.shortcutAnimViewShowing && miuiShortcutManager.fullAodEnable(getContext())) {
            this.shortcutViewAnimFolme.state().to(occludedAnimationUtils.getAnimStateScaleByGoToSleep(), new AnimConfig().setEase(FolmeEase.spring(0.93f, 0.3f)).addListeners(new TransitionListener() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController.startBackAnim.2
                @Override // miuix.animation.listener.TransitionListener
                public void onBegin(Object obj) {
                    super.onBegin(obj);
                    ShortcutOccludedAnimController.this.setBlurContainerScale(1.0526316f, 1.0526316f);
                }
            }));
        }
    }

    public final void startFullScreenAnim() {
        LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutOccludedAnimController", "startFullScreenAnim", null, 4, null);
        this.callback.onFullscreenAnimationStart();
        getShortcutAnimView().getFullScreenAnim(new Function0() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return ShortcutOccludedAnimController.startFullScreenAnim$lambda$6(this.f$0);
            }
        }, new Function0() { // from class: com.miui.keyguard.shortcuts.controller.ShortcutOccludedAnimController$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return ShortcutOccludedAnimController.startFullScreenAnim$lambda$7(this.f$0);
            }
        });
        startShortcutActivity();
    }

    public final void startShortcutActivity() {
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault()), null, null, new C01571(null), 3, null);
    }

    public final void updateActiveAnim() {
        this.lastIsActive = this.isActive;
        this.isActive = getMovePer() > 0.6f;
        getShortcutAnimView().setIconAlpha(1 - Math.min(getMovePer(), 0.6f));
        boolean z = this.isActive;
        if (!z && this.lastIsActive) {
            startActiveAnim(getActiveAnimPer(), 0.0f);
        } else {
            if (!z || this.lastIsActive) {
                return;
            }
            startActiveAnim(getActiveAnimPer(), 1.0f);
            HapticFeedbackUtils.INSTANCE.performHapticFeedback(getShortcutAnimView(), HapticFeedbackConstants.MIUI_ALERT, HapticFeedbackConstants.MIUI_MESH_HEAVY, 150L);
        }
    }
}
