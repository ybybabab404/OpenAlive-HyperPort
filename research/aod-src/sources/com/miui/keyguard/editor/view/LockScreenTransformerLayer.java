package com.miui.keyguard.editor.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.miui.keyguard.editor.EditorServiceManager;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.TemplateViewFactory;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.MagicType;
import com.miui.keyguard.editor.data.bean.PresetTemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.data.template.TemplateApiImpl;
import com.miui.keyguard.editor.data.template.TemplateFilePathGenerator;
import com.miui.keyguard.editor.data.template.WallpaperSource;
import com.miui.keyguard.editor.data.util.DataUtil;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.util.CrossListViewUtil;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.homepage.view.CustomButtonView;
import com.miui.keyguard.editor.homepage.view.generator.FactoryParams;
import com.miui.keyguard.editor.utils.CommonUtils;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.LargeFontLanguageHelper;
import com.miui.keyguard.editor.utils.ScenarioRecognitionUtils;
import com.miui.keyguard.editor.utils.TemplateRadiusAnimationListener;
import com.miui.keyguard.editor.utils.ViewStylerKt;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.utils.Wallpaper;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import com.miui.maml.folme.AnimatedPropertyType;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
/* JADX INFO: compiled from: LockScreenTransformerLayer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class LockScreenTransformerLayer extends LinearLayout {
    public final long _backupTimeout;
    public float _neighboringInitialScale;
    public float actualTopForCurrentTemplate;
    public WeakReference animationListener;
    public WeakReference applyButton;
    public boolean applySuccess;
    public View bottomTemplatePreviewView;
    public FrameLayout bottomTemplateViewContainer;
    public WeakReference cancelButton;
    public float containerHeight;
    public float containerPadding;
    public float containerWidth;
    public View currentHomeTemplatePreviewView;
    public View currentLockTemplatePreviewView;
    public float currentTemplateFromScaleX;
    public float currentTemplateFromScaleY;
    public FrameLayout currentTemplateViewContainer;
    public TransformerInfo currentTransformerInfo;
    public Job delayJob;
    public Runnable enterAnimationCompleteListener;
    public TransformerPositionInfo entranceCurrentPositionInfo;
    public final TemplateFilePathGenerator filePathGenerator;
    public float finalScaleX;
    public float finalScaleXWithShowBouncer;
    public float finalScaleY;
    public float finalScaleYWithShowBouncer;
    public float finalTranslateXForLeft;
    public float finalTranslateXForRight;
    public float finalTranslateYForBottom;
    public float finalTranslationY;
    public float finalTranslationYWithShowBouncer;
    public final Runnable hideTransformLayerRunnable;
    public int homeExtraHeight;
    public float homeScale;
    public float homeTemplateScale;
    public boolean isBackupInvalid;
    public boolean isEnter;
    public boolean isEnterAnimationStarted;
    public boolean isExitAnimationStarted;
    public boolean isInflatedContentView;
    public boolean isLockedEnterAnimationStarted;
    public boolean isRtl;
    public boolean isUnlockAnimationStarted;
    public boolean isUnlockEnterAnimationStarted;
    public LinearLayout layerRoot;
    public FrameLayout.LayoutParams layoutParamsForRealTemplateView;
    public View leftTemplatePreviewView;
    public FrameLayout leftTemplateViewContainer;
    public View myHomeTemplatePreviewView;
    public View myLockTemplatePreviewView;
    public FrameLayout myTemplateViewContainer;
    public Point outRealSize;
    public float pagerOffscreenOffset;
    public float previewHeight;
    public float previewRadiusPixel;
    public float previewWidth;
    public int realScreenHeight;
    public int realScreenWidth;
    public float realTemplateScaleX;
    public float realTemplateScaleY;
    public View rightTemplatePreviewView;
    public FrameLayout rightTemplateViewContainer;
    public boolean startExit;
    public RelativeLayout titleContainer;
    public LinearLayout topContainer;
    public List transformerEventListener;
    public CustomButtonView tvCustom;
    public TextView tvDescription;
    public TextView tvTitle;
    public final float unlockFactor;
    public float verticalOffscreenOffset;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.view.LockScreenTransformerLayer$inflateAndInit$1, reason: invalid class name */
    /* JADX INFO: compiled from: LockScreenTransformerLayer.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.view.LockScreenTransformerLayer$inflateAndInit$1", f = "LockScreenTransformerLayer.kt", l = {226}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LockScreenTransformerLayer.this.new AnonymousClass1(continuation);
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
                CoroutineDispatcher io2 = Dispatchers.getIO();
                LockScreenTransformerLayer$inflateAndInit$1$asyncView$1 lockScreenTransformerLayer$inflateAndInit$1$asyncView$1 = new LockScreenTransformerLayer$inflateAndInit$1$asyncView$1(LockScreenTransformerLayer.this, null);
                this.label = 1;
                obj = BuildersKt.withContext(io2, lockScreenTransformerLayer$inflateAndInit$1$asyncView$1, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            View view = (View) obj;
            if (!LockScreenTransformerLayer.this.isInflatedContentView()) {
                Log.i("Keyguard-Editor-LockScreenTransformerLayer", "inflate content async");
                LockScreenTransformerLayer.this.setInflatedContentView(true);
                LockScreenTransformerLayer.this.addView(view);
                LockScreenTransformerLayer.this.onInit();
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.view.LockScreenTransformerLayer$onAllTransformerCompleted$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: LockScreenTransformerLayer.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.view.LockScreenTransformerLayer$onAllTransformerCompleted$1", f = "LockScreenTransformerLayer.kt", l = {1405}, m = "invokeSuspend")
    public static final class C01501 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public /* synthetic */ Object L$0;
        public int label;

        /* JADX INFO: renamed from: com.miui.keyguard.editor.view.LockScreenTransformerLayer$onAllTransformerCompleted$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: LockScreenTransformerLayer.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.view.LockScreenTransformerLayer$onAllTransformerCompleted$1$1", f = "LockScreenTransformerLayer.kt", l = {}, m = "invokeSuspend")
        public static final class C00371 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public int label;
            public final /* synthetic */ LockScreenTransformerLayer this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00371(LockScreenTransformerLayer lockScreenTransformerLayer, Continuation continuation) {
                super(2, continuation);
                this.this$0 = lockScreenTransformerLayer;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00371(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00371) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                TemplateApiImpl.Companion companion = TemplateApiImpl.Companion;
                Context context = this.this$0.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                companion.flushConfigJson(context);
                return Unit.INSTANCE;
            }
        }

        public C01501(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C01501 c01501 = LockScreenTransformerLayer.this.new C01501(continuation);
            c01501.L$0 = obj;
            return c01501;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01501) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                BuildersKt__Builders_commonKt.launch$default((CoroutineScope) this.L$0, Dispatchers.getIO(), null, new C00371(LockScreenTransformerLayer.this, null), 2, null);
                this.label = 1;
                if (DelayKt.delay(300L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            LockScreenTransformerLayer.this.hideTransformLayerRunnable.run();
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.view.LockScreenTransformerLayer$startBackupHandler$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: LockScreenTransformerLayer.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.view.LockScreenTransformerLayer$startBackupHandler$1", f = "LockScreenTransformerLayer.kt", l = {AnimatedPropertyType.PIVOT_Y}, m = "invokeSuspend")
    public static final class C01511 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public C01511(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LockScreenTransformerLayer.this.new C01511(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01511) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                long j = LockScreenTransformerLayer.this._backupTimeout;
                this.label = 1;
                if (DelayKt.delay(j, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            LockScreenTransformerLayerKt.getBackupRunnable().invoke(LockScreenTransformerLayer.this);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    @JvmOverloads
    public LockScreenTransformerLayer(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public LockScreenTransformerLayer(@NotNull final Context context, @Nullable AttributeSet attributeSet) {
        float f;
        float f2;
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        setOrientation(1);
        setClipChildren(false);
        setClipToPadding(true);
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        this._neighboringInitialScale = resourcesProvider.provideMinScaleForNeighboringItem(context);
        this._backupTimeout = 1200L;
        this.filePathGenerator = new TemplateFilePathGenerator(context);
        this.previewRadiusPixel = resourcesProvider.providePreviewRadius(context);
        Point pointProvideRealSize = resourcesProvider.provideRealSize(context);
        this.outRealSize = pointProvideRealSize;
        this.realScreenWidth = pointProvideRealSize.x;
        this.realScreenHeight = pointProvideRealSize.y;
        this.containerWidth = resourcesProvider.provideTemplateContainerWidth(context);
        this.containerPadding = resourcesProvider.provideTemplateContainerPadding(context);
        this.containerHeight = resourcesProvider.provideTemplateContainerHeight(context, new FactoryParams(false, false, 2, null));
        this.isRtl = DeviceUtil.INSTANCE.isRtl(context);
        this.previewWidth = resourcesProvider.provideTemplatePreviewWidth(context);
        this.previewHeight = resourcesProvider.provideTemplatePreviewHeight(context);
        this.homeScale = resourcesProvider.provideHomeScale(context);
        this.homeExtraHeight = resourcesProvider.provideHomeExtraHeight(context);
        this.finalScaleX = this.previewWidth / this.realScreenWidth;
        this.finalScaleY = this.previewHeight / this.realScreenHeight;
        this.layoutParamsForRealTemplateView = new FrameLayout.LayoutParams(this.realScreenWidth, this.realScreenHeight);
        float f3 = this.previewWidth / this.realScreenWidth;
        this.realTemplateScaleX = f3;
        this.realTemplateScaleY = this.previewHeight / this.realScreenHeight;
        this.homeTemplateScale = f3 * this.homeScale;
        this.actualTopForCurrentTemplate = getResources().getDimensionPixelSize(R.dimen.kg_main_pager_margin_top) + getResources().getDimensionPixelSize(R.dimen.kg_main_item_floor_title_bar_height) + getResources().getDimensionPixelSize(R.dimen.kg_main_item_floor_pager_margin_top) + getResources().getDimensionPixelSize(R.dimen.kg_main_pager_margin_top_lock) + this.containerPadding;
        float fProvideHorizontalPagerOffscreenOffset = resourcesProvider.provideHorizontalPagerOffscreenOffset(context);
        this.pagerOffscreenOffset = fProvideHorizontalPagerOffscreenOffset;
        if (this.isRtl) {
            float f4 = this._neighboringInitialScale;
            f = fProvideHorizontalPagerOffscreenOffset + (((1 - f4) * this.containerWidth) / 2) + (this.containerPadding * f4);
        } else {
            float f5 = this._neighboringInitialScale;
            f = ((-fProvideHorizontalPagerOffscreenOffset) - (((1 - f5) * this.containerWidth) / 2)) - (this.containerPadding * f5);
        }
        this.finalTranslateXForRight = f;
        float fProvideBottomPagerOffscreenOffset = resourcesProvider.provideBottomPagerOffscreenOffset(context);
        this.verticalOffscreenOffset = fProvideBottomPagerOffscreenOffset;
        float f6 = this.containerPadding;
        this.finalTranslateYForBottom = (-fProvideBottomPagerOffscreenOffset) - f6;
        if (this.isRtl) {
            float f7 = -this.pagerOffscreenOffset;
            float f8 = this._neighboringInitialScale;
            f2 = (f7 - (((1 - f8) * this.containerWidth) / 2)) - (f6 * f8);
        } else {
            float f9 = this.pagerOffscreenOffset;
            float f10 = this._neighboringInitialScale;
            f2 = f9 + (((1 - f10) * this.containerWidth) / 2) + (f6 * f10);
        }
        this.finalTranslateXForLeft = f2;
        this.finalTranslationY = this.actualTopForCurrentTemplate - ((this.realScreenHeight - this.previewHeight) / 2);
        this.hideTransformLayerRunnable = new Runnable() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                LockScreenTransformerLayer.hideTransformLayerRunnable$lambda$1(this.f$0, context);
            }
        };
        this.currentTemplateFromScaleX = this.realScreenWidth / this.previewWidth;
        this.currentTemplateFromScaleY = this.realScreenHeight / this.previewHeight;
        this.unlockFactor = 1.38f;
        this.finalScaleXWithShowBouncer = this.finalScaleX * 1.38f;
        this.finalScaleYWithShowBouncer = this.finalScaleY * 1.38f;
        this.finalTranslationYWithShowBouncer = this.finalTranslationY * 1.38f;
        this.isEnter = true;
        this.transformerEventListener = new ArrayList();
    }

    public /* synthetic */ LockScreenTransformerLayer(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public static final void createHideAboveWallpaperLayersAlphaAnimator$lambda$34(List list, ValueAnimator valueAnimator) {
        Object animatedValue = valueAnimator.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        float fFloatValue = ((Float) animatedValue).floatValue();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((View) it.next()).setAlpha(fFloatValue);
        }
    }

    public static /* synthetic */ View createOrInitTemplatePreview$default(LockScreenTransformerLayer lockScreenTransformerLayer, TransformerPositionInfo transformerPositionInfo, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        return lockScreenTransformerLayer.createOrInitTemplatePreview(transformerPositionInfo, z, z2);
    }

    public static final void createShowAboveWallpaperLayersAnimator$lambda$36(LockScreenTransformerLayer lockScreenTransformerLayer, View view, ValueAnimator valueAnimator) {
        Object animatedValue = valueAnimator.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        lockScreenTransformerLayer.setViewAlpha(view, ((Float) animatedValue).floatValue());
    }

    public static /* synthetic */ TemplateRadiusAnimationListener createTemplateRadiusAnimationListener$default(LockScreenTransformerLayer lockScreenTransformerLayer, View view, float f, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return lockScreenTransformerLayer.createTemplateRadiusAnimationListener(view, f, z);
    }

    public static /* synthetic */ BaseTemplateView createTemplateView$default(LockScreenTransformerLayer lockScreenTransformerLayer, CommonConfig commonConfig, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        return lockScreenTransformerLayer.createTemplateView(commonConfig, z, z2);
    }

    public static final void hideTransformLayerRunnable$lambda$1(LockScreenTransformerLayer lockScreenTransformerLayer, Context context) {
        if (!lockScreenTransformerLayer.startExit) {
            lockScreenTransformerLayer.hideTransformerLayer();
        }
        EditorActivityViewModel viewModel = EditorActivityViewModel.Companion.getViewModel(context);
        if (viewModel != null) {
            viewModel.setTransformFinished(true);
            LockScreenTransformerAnimationListener onTransformerCallback = viewModel.getOnTransformerCallback();
            if (onTransformerCallback != null) {
                onTransformerCallback.onEnterLockScreenTransformerAnimationEnd();
            }
        }
    }

    public static /* synthetic */ void setTransformerInfo$default(LockScreenTransformerLayer lockScreenTransformerLayer, TransformerInfo transformerInfo, boolean z, boolean z2, int i, Object obj) {
        if ((i & 4) != 0) {
            z2 = true;
        }
        lockScreenTransformerLayer.setTransformerInfo(transformerInfo, z, z2);
    }

    private final void setViewsAlpha(float f) {
        WeakReference weakReference = this.cancelButton;
        CustomButtonView customButtonView = null;
        setViewAlpha(weakReference != null ? (View) weakReference.get() : null, f);
        WeakReference weakReference2 = this.applyButton;
        setViewAlpha(weakReference2 != null ? (View) weakReference2.get() : null, f);
        CustomButtonView customButtonView2 = this.tvCustom;
        if (customButtonView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvCustom");
        } else {
            customButtonView = customButtonView2;
        }
        setViewAlpha(customButtonView, f);
    }

    public static /* synthetic */ void startCurrentTemplatePreviewAnim$default(LockScreenTransformerLayer lockScreenTransformerLayer, boolean z, boolean z2, float f, Animator animator, float f2, TransitionListener transitionListener, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        if ((i & 2) != 0) {
            z2 = false;
        }
        if ((i & 8) != 0) {
            animator = null;
        }
        if ((i & 16) != 0) {
            f2 = 0.18f;
        }
        if ((i & 32) != 0) {
            transitionListener = null;
        }
        lockScreenTransformerLayer.startCurrentTemplatePreviewAnim(z, z2, f, animator, f2, transitionListener);
    }

    public static final void startCurrentTemplatePreviewAnim$lambda$17(BaseTemplateView baseTemplateView, final LockScreenTransformerLayer lockScreenTransformerLayer, long j, long j2, long j3) {
        final View clockLayer = baseTemplateView.getClockLayer();
        lockScreenTransformerLayer.setViewAlpha(clockLayer, 0.0f);
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(0.0f, 1.0f);
        valueAnimator.setDuration(300L);
        valueAnimator.setInterpolator(new DecelerateInterpolator(1.0f));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$$ExternalSyntheticLambda9
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                LockScreenTransformerLayer.startCurrentTemplatePreviewAnim$lambda$17$lambda$16(this.f$0, clockLayer, valueAnimator2);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$startCurrentTemplatePreviewAnim$1$2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator p0) {
                Intrinsics.checkNotNullParameter(p0, "p0");
                this.this$0.setViewAlpha(clockLayer, 1.0f);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator p0) {
                Intrinsics.checkNotNullParameter(p0, "p0");
                this.this$0.setViewAlpha(clockLayer, 1.0f);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator p0) {
                Intrinsics.checkNotNullParameter(p0, "p0");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator p0) {
                Intrinsics.checkNotNullParameter(p0, "p0");
            }
        });
        valueAnimator.start();
        Log.i("Keyguard-Editor-LockScreenTransformerLayer", "delay start alpha anim edit delayTime = " + j + ", startAnimTime = " + j2 + ", post use time = " + (System.currentTimeMillis() - j3));
    }

    public static final void startCurrentTemplatePreviewAnim$lambda$17$lambda$16(LockScreenTransformerLayer lockScreenTransformerLayer, View view, ValueAnimator valueAnimator) {
        Object animatedValue = valueAnimator.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        lockScreenTransformerLayer.setViewAlpha(view, ((Float) animatedValue).floatValue());
    }

    public static final void startExitTransformerAnimation$lambda$32(LockScreenTransformerLayer lockScreenTransformerLayer) {
        TransformerPositionInfo transformerPositionInfo;
        TransformerPositionInfo currentPositionInfo;
        Log.i("Keyguard-Editor-LockScreenTransformerLayer", "startExitTransformerAnimation");
        lockScreenTransformerLayer.invalidateBackup();
        int[] iArr = new int[2];
        lockScreenTransformerLayer.getLocationOnScreen(iArr);
        FrameLayout frameLayout = lockScreenTransformerLayer.myTemplateViewContainer;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("myTemplateViewContainer");
            frameLayout = null;
        }
        startExitTransformerAnimation$lambda$32$lambda$30$centerContainerX(iArr, lockScreenTransformerLayer, frameLayout);
        FrameLayout frameLayout2 = lockScreenTransformerLayer.currentTemplateViewContainer;
        if (frameLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentTemplateViewContainer");
            frameLayout2 = null;
        }
        startExitTransformerAnimation$lambda$32$lambda$30$centerContainerX(iArr, lockScreenTransformerLayer, frameLayout2);
        FrameLayout frameLayout3 = lockScreenTransformerLayer.myTemplateViewContainer;
        if (frameLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("myTemplateViewContainer");
            frameLayout3 = null;
        }
        List listAboveWallpaperLayers = lockScreenTransformerLayer.aboveWallpaperLayers(frameLayout3);
        if (listAboveWallpaperLayers != null) {
            Iterator it = listAboveWallpaperLayers.iterator();
            while (it.hasNext()) {
                ((View) it.next()).setAlpha(1.0f);
            }
        }
        float dimension = lockScreenTransformerLayer.getContext().getResources().getDimension(R.dimen.kg_main_item_preview_radius);
        TransformerInfo transformerInfo = lockScreenTransformerLayer.currentTransformerInfo;
        boolean z = (transformerInfo == null || (currentPositionInfo = transformerInfo.getCurrentPositionInfo()) == null || currentPositionInfo.getItemType() != 1) ? false : true;
        lockScreenTransformerLayer.startExitRadiusAnimation(createTemplateRadiusAnimationListener$default(lockScreenTransformerLayer, (z || !lockScreenTransformerLayer.applySuccess) ? lockScreenTransformerLayer.myLockTemplatePreviewView : lockScreenTransformerLayer.currentLockTemplatePreviewView, dimension, false, 4, null), z);
        boolean z2 = lockScreenTransformerLayer.applySuccess;
        float f = z2 ? 1.0f : 0.0f;
        boolean z3 = (z2 || (transformerPositionInfo = lockScreenTransformerLayer.entranceCurrentPositionInfo) == null || transformerPositionInfo.getItemType() != 4) ? false : true;
        if (!z || z3) {
            startCurrentTemplatePreviewAnim$default(lockScreenTransformerLayer, true, false, f, null, 0.3f, null, 32, null);
        } else if (lockScreenTransformerLayer.applySuccess) {
            FrameLayout frameLayout4 = lockScreenTransformerLayer.currentTemplateViewContainer;
            if (frameLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentTemplateViewContainer");
                frameLayout4 = null;
            }
            frameLayout4.setVisibility(0);
        } else {
            startCurrentTemplatePreviewAnim$default(lockScreenTransformerLayer, true, false, 0.0f, null, 0.45f, null, 32, null);
        }
        if (!z || lockScreenTransformerLayer.applySuccess) {
            lockScreenTransformerLayer.startMyTemplatePreviewExitAnim();
        }
        TextView textView = lockScreenTransformerLayer.tvTitle;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
            textView = null;
        }
        if (textView.getVisibility() == 0) {
            lockScreenTransformerLayer.startTitleAlphaAnim(1.0f, 0.0f, 0L, lockScreenTransformerLayer.createAlphaDismissEaseStyle());
        }
        TextView textView2 = lockScreenTransformerLayer.tvDescription;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvDescription");
            textView2 = null;
        }
        if (textView2.getVisibility() == 0) {
            lockScreenTransformerLayer.startDescriptionAlphaAnim(1.0f, 0.0f, 0L, lockScreenTransformerLayer.createAlphaDismissEaseStyle());
        }
        FrameLayout frameLayout5 = lockScreenTransformerLayer.leftTemplateViewContainer;
        if (frameLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
            frameLayout5 = null;
        }
        if (frameLayout5.getVisibility() == 0) {
            lockScreenTransformerLayer.startLeftPreviewTranslateInAnim(lockScreenTransformerLayer.finalTranslateXForLeft, 0.0f);
        }
        FrameLayout frameLayout6 = lockScreenTransformerLayer.rightTemplateViewContainer;
        if (frameLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
            frameLayout6 = null;
        }
        if (frameLayout6.getVisibility() == 0) {
            lockScreenTransformerLayer.startRightPreviewTranslateInAnim(lockScreenTransformerLayer.finalTranslateXForRight, 0.0f);
        }
        FrameLayout frameLayout7 = lockScreenTransformerLayer.bottomTemplateViewContainer;
        if (frameLayout7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
            frameLayout7 = null;
        }
        if (frameLayout7.getVisibility() == 0) {
            lockScreenTransformerLayer.startBottomPreviewTranslateInAnim(lockScreenTransformerLayer.finalTranslateYForBottom, (lockScreenTransformerLayer.containerHeight * 0.5f) / 2);
        }
        FrameLayout frameLayout8 = lockScreenTransformerLayer.currentTemplateViewContainer;
        if (frameLayout8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentTemplateViewContainer");
            frameLayout8 = null;
        }
        View viewFindViewById = frameLayout8.findViewById(R.id.tv_custom);
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(8);
        }
        lockScreenTransformerLayer.entranceCurrentPositionInfo = null;
    }

    public static final void startExitTransformerAnimation$lambda$32$lambda$30$centerContainerX(int[] iArr, LockScreenTransformerLayer lockScreenTransformerLayer, View view) {
        if (view.getWidth() <= 0) {
            return;
        }
        int[] iArr2 = new int[2];
        view.getLocationOnScreen(iArr2);
        view.setTranslationX(view.getTranslationX() + ((lockScreenTransformerLayer.realScreenWidth / 2.0f) - ((iArr2[0] - iArr[0]) + (view.getWidth() / 2.0f))));
    }

    public static final void startExitTransformerAnimationWithShowBouncer$lambda$28(LockScreenTransformerLayer lockScreenTransformerLayer) {
        lockScreenTransformerLayer.invalidateBackup();
        startCurrentTemplatePreviewAnim$default(lockScreenTransformerLayer, true, true, 1.0f, null, 0.0f, createTemplateRadiusAnimationListener$default(lockScreenTransformerLayer, lockScreenTransformerLayer.currentLockTemplatePreviewView, lockScreenTransformerLayer.getContext().getResources().getDimension(R.dimen.kg_main_item_preview_radius), false, 4, null), 24, null);
    }

    public static final void startTransformerAnimation$lambda$15(LockScreenTransformerLayer lockScreenTransformerLayer, long j, long j2) {
        lockScreenTransformerLayer.startBackupHandler();
        lockScreenTransformerLayer.startCurrentTemplatePreviewAnim(j);
        TextView textView = lockScreenTransformerLayer.tvTitle;
        FrameLayout frameLayout = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
            textView = null;
        }
        if (textView.getVisibility() == 0) {
            lockScreenTransformerLayer.startTitleAlphaAnim(0.0f, 1.0f, 100L, lockScreenTransformerLayer.createAlphaEaseStyle());
        }
        TextView textView2 = lockScreenTransformerLayer.tvDescription;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvDescription");
            textView2 = null;
        }
        if (textView2.getVisibility() == 0) {
            lockScreenTransformerLayer.startDescriptionAlphaAnim(0.0f, 1.0f, 100L, lockScreenTransformerLayer.createAlphaEaseStyle());
        }
        FrameLayout frameLayout2 = lockScreenTransformerLayer.leftTemplateViewContainer;
        if (frameLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
            frameLayout2 = null;
        }
        if (frameLayout2.getVisibility() == 0) {
            lockScreenTransformerLayer.startLeftPreviewTranslateInAnim();
        }
        FrameLayout frameLayout3 = lockScreenTransformerLayer.rightTemplateViewContainer;
        if (frameLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
            frameLayout3 = null;
        }
        if (frameLayout3.getVisibility() == 0) {
            lockScreenTransformerLayer.startRightPreviewTranslateInAnim();
        }
        FrameLayout frameLayout4 = lockScreenTransformerLayer.bottomTemplateViewContainer;
        if (frameLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
        } else {
            frameLayout = frameLayout4;
        }
        if (frameLayout.getVisibility() == 0) {
            lockScreenTransformerLayer.startBottomPreviewTranslateInAnim();
        }
        TransformerPositionInfo transformerPositionInfo = lockScreenTransformerLayer.entranceCurrentPositionInfo;
        if (transformerPositionInfo == null || transformerPositionInfo.getItemType() != 4) {
            lockScreenTransformerLayer.startCustomButtonAlphaAnim(0.0f, 1.0f, 100L, lockScreenTransformerLayer.createCustomButtonAlphaEaseStyle());
        }
        lockScreenTransformerLayer.startCancelButtonAlphaAnim(0.0f, 1.0f, 100L, lockScreenTransformerLayer.createAlphaEaseStyle());
        lockScreenTransformerLayer.startApplyButtonAlphaAnim(0.0f, 1.0f, 100L, lockScreenTransformerLayer.createAlphaEaseStyle());
        lockScreenTransformerLayer.logStartUpTime(j2);
    }

    public static final void startTransformerAnimationUnlock$lambda$29(LockScreenTransformerLayer lockScreenTransformerLayer) {
        lockScreenTransformerLayer.startBackupHandler();
        startCurrentTemplatePreviewAnim$default(lockScreenTransformerLayer, false, false, 1.0f, lockScreenTransformerLayer.createShowAboveWallpaperLayersAnimator(), 0.0f, null, 48, null);
        TextView textView = lockScreenTransformerLayer.tvTitle;
        FrameLayout frameLayout = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
            textView = null;
        }
        if (textView.getVisibility() == 0) {
            lockScreenTransformerLayer.startTitleAlphaAnim(0.0f, 1.0f, 100L, lockScreenTransformerLayer.createAlphaEaseStyle());
        }
        TextView textView2 = lockScreenTransformerLayer.tvDescription;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvDescription");
            textView2 = null;
        }
        if (textView2.getVisibility() == 0) {
            lockScreenTransformerLayer.startDescriptionAlphaAnim(0.0f, 1.0f, 100L, lockScreenTransformerLayer.createAlphaEaseStyle());
        }
        FrameLayout frameLayout2 = lockScreenTransformerLayer.leftTemplateViewContainer;
        if (frameLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
            frameLayout2 = null;
        }
        if (frameLayout2.getVisibility() == 0) {
            lockScreenTransformerLayer.startLeftPreviewTranslateInAnim();
        }
        FrameLayout frameLayout3 = lockScreenTransformerLayer.rightTemplateViewContainer;
        if (frameLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
            frameLayout3 = null;
        }
        if (frameLayout3.getVisibility() == 0) {
            lockScreenTransformerLayer.startRightPreviewTranslateInAnim();
        }
        FrameLayout frameLayout4 = lockScreenTransformerLayer.bottomTemplateViewContainer;
        if (frameLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
        } else {
            frameLayout = frameLayout4;
        }
        if (frameLayout.getVisibility() == 0) {
            lockScreenTransformerLayer.startBottomPreviewTranslateInAnim();
        }
        TransformerPositionInfo transformerPositionInfo = lockScreenTransformerLayer.entranceCurrentPositionInfo;
        if (transformerPositionInfo == null || transformerPositionInfo.getItemType() != 4) {
            lockScreenTransformerLayer.startCustomButtonAlphaAnim(0.0f, 1.0f, 100L, lockScreenTransformerLayer.createCustomButtonAlphaEaseStyle());
        }
        lockScreenTransformerLayer.startCancelButtonAlphaAnim(0.0f, 1.0f, 100L, lockScreenTransformerLayer.createAlphaEaseStyle());
        lockScreenTransformerLayer.startApplyButtonAlphaAnim(0.0f, 1.0f, 100L, lockScreenTransformerLayer.createAlphaEaseStyle());
    }

    public static final void startTransformerAnimationWithShowBouncer$lambda$27(LockScreenTransformerLayer lockScreenTransformerLayer, long j) {
        startCurrentTemplatePreviewAnim$default(lockScreenTransformerLayer, false, true, 1.0f, null, 0.0f, null, 56, null);
        lockScreenTransformerLayer.logStartUpTime(j);
    }

    public static final Unit waitCurrentWallpaperFirstFrameReady$lambda$2(Function0 function0) {
        Log.i("Keyguard-Editor-LockScreenTransformerLayer", "current wallpaper first frame ready");
        function0.invoke();
        return Unit.INSTANCE;
    }

    public final List aboveWallpaperLayers(FrameLayout frameLayout) {
        FrameLayout frameLayout2 = (FrameLayout) frameLayout.findViewById(R.id.kg_transformer_my_lock_template);
        if (frameLayout2 == null) {
            Log.w("Keyguard-Editor-LockScreenTransformerLayer", "aboveWallpaperLayers my_lock_template layer=null");
            return null;
        }
        FrameLayout frameLayout3 = (FrameLayout) frameLayout.findViewById(R.id.kg_transformer_my_home_template);
        if (frameLayout2 instanceof BaseTemplateView) {
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(((BaseTemplateView) frameLayout2).aboveWallpaperLayers());
            Intrinsics.checkNotNull(frameLayout3);
            arrayList.add(frameLayout3);
            return arrayList;
        }
        if (frameLayout2.getChildCount() <= 1) {
            return null;
        }
        int childCount = frameLayout2.getChildCount();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 1; i < childCount; i++) {
            View childAt = frameLayout2.getChildAt(i);
            Intrinsics.checkNotNullExpressionValue(childAt, "getChildAt(...)");
            arrayList2.add(childAt);
        }
        Intrinsics.checkNotNull(frameLayout3);
        arrayList2.add(frameLayout3);
        return arrayList2;
    }

    public final void addEventListener(TransformerEventListener eventListener) {
        Intrinsics.checkNotNullParameter(eventListener, "eventListener");
        this.transformerEventListener.add(eventListener);
    }

    public final void attachExternalViews(WeakReference cancelButton, WeakReference applyButton) {
        Intrinsics.checkNotNullParameter(cancelButton, "cancelButton");
        Intrinsics.checkNotNullParameter(applyButton, "applyButton");
        this.cancelButton = cancelButton;
        this.applyButton = applyButton;
    }

    public final EaseManager.EaseStyle createAlphaDismissEaseStyle() {
        EaseManager.EaseStyle style = EaseManager.getStyle(-2, 0.95f, 0.18f);
        Intrinsics.checkNotNullExpressionValue(style, "getStyle(...)");
        return style;
    }

    public final EaseManager.EaseStyle createAlphaEaseStyle() {
        EaseManager.EaseStyle style = EaseManager.getStyle(-2, 0.95f, 0.6f);
        Intrinsics.checkNotNullExpressionValue(style, "getStyle(...)");
        return style;
    }

    public final EaseManager.EaseStyle createCurrentTemplateAlphaEaseStyle(float f) {
        EaseManager.EaseStyle style = EaseManager.getStyle(-2, 1.0f, f);
        Intrinsics.checkNotNullExpressionValue(style, "getStyle(...)");
        return style;
    }

    public final EaseManager.EaseStyle createCustomButtonAlphaEaseStyle() {
        EaseManager.EaseStyle style = EaseManager.getStyle(-2, 0.95f, 0.43f);
        Intrinsics.checkNotNullExpressionValue(style, "getStyle(...)");
        return style;
    }

    public final EaseManager.EaseStyle createFrameDismissAlphaEaseStyle() {
        EaseManager.EaseStyle style = EaseManager.getStyle(-2, 0.95f, 0.15f);
        Intrinsics.checkNotNullExpressionValue(style, "getStyle(...)");
        return style;
    }

    public final Animator createHideAboveWallpaperLayersAlphaAnimator(FrameLayout frameLayout) {
        final List listAboveWallpaperLayers = aboveWallpaperLayers(frameLayout);
        if (listAboveWallpaperLayers == null) {
            return null;
        }
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(1.0f, 0.0f);
        valueAnimator.setDuration(120L);
        valueAnimator.setInterpolator(new DecelerateInterpolator(1.5f));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$$ExternalSyntheticLambda5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                LockScreenTransformerLayer.createHideAboveWallpaperLayersAlphaAnimator$lambda$34(listAboveWallpaperLayers, valueAnimator2);
            }
        });
        return valueAnimator;
    }

    public final View createHomeTemplatePreviewView(TransformerPositionInfo transformerPositionInfo) {
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(this.realScreenWidth, this.realScreenHeight));
        frameLayout.setPivotY(0.0f);
        frameLayout.setPivotX(this.isRtl ? this.realScreenWidth : 0.0f);
        frameLayout.setScaleY(this.homeTemplateScale);
        frameLayout.setScaleX(this.homeTemplateScale);
        ImageView imageViewPreviewHomeWallpaper = previewHomeWallpaper(transformerPositionInfo);
        if (imageViewPreviewHomeWallpaper != null) {
            frameLayout.addView(imageViewPreviewHomeWallpaper);
        }
        return frameLayout;
    }

    public final View createLockTemplatePreviewView(TransformerPositionInfo transformerPositionInfo) {
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(this.realScreenWidth, this.realScreenHeight));
        frameLayout.setPivotY(0.0f);
        frameLayout.setPivotX(this.isRtl ? this.realScreenWidth : 0.0f);
        frameLayout.setScaleY(this.realTemplateScaleY);
        frameLayout.setScaleX(this.realTemplateScaleX);
        ImageView imageViewPreviewWallpaper = previewWallpaper(transformerPositionInfo);
        if (imageViewPreviewWallpaper != null) {
            frameLayout.addView(imageViewPreviewWallpaper);
        }
        ImageView imageViewPreviewBackground = previewBackground(transformerPositionInfo);
        if (imageViewPreviewBackground != null) {
            frameLayout.addView(imageViewPreviewBackground);
        }
        ImageView imageViewPreviewForeground = previewForeground(transformerPositionInfo);
        if (imageViewPreviewForeground != null) {
            frameLayout.addView(imageViewPreviewForeground);
        }
        return frameLayout;
    }

    public final void createOrAddCurrentTemplatePreviewView(TransformerPositionInfo transformerPositionInfo, boolean z) {
        this.currentLockTemplatePreviewView = createOrInitTemplatePreview(transformerPositionInfo, true, z);
        this.currentHomeTemplatePreviewView = createOrInitHomeTemplatePreview(transformerPositionInfo);
        if (this.currentLockTemplatePreviewView == null) {
            Log.w("Keyguard-Editor-LockScreenTransformerLayer", "createOrAddCurrentTemplatePreviewView failed: " + transformerPositionInfo);
            return;
        }
        RoundOutlineProvider roundOutlineProvider = new RoundOutlineProvider(this.previewRadiusPixel);
        FrameLayout frameLayout = this.currentTemplateViewContainer;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentTemplateViewContainer");
            frameLayout = null;
        }
        View view = this.currentLockTemplatePreviewView;
        Intrinsics.checkNotNull(view);
        LockScreenTransformerLayerKt.addTemplatePreviewView$default(frameLayout, view, roundOutlineProvider, false, 8, null);
        View view2 = this.currentHomeTemplatePreviewView;
        if (view2 != null) {
            new RoundOutlineProvider(this.previewRadiusPixel);
            float f = 2;
            view2.setTranslationY(((this.containerHeight - (this.containerPadding * f)) - (view2.getLayoutParams().height * this.homeTemplateScale)) - (this.isEnter ? this.homeExtraHeight : 0));
            view2.setTranslationX(((this.containerWidth - (this.containerPadding * f)) - (view2.getLayoutParams().width * this.homeTemplateScale)) / 2.0f);
        }
    }

    public final View createOrInitHomeTemplatePreview(TransformerPositionInfo transformerPositionInfo) {
        if (transformerPositionInfo == null) {
            return null;
        }
        int itemType = transformerPositionInfo.getItemType();
        if (itemType == 1 || itemType == 2 || itemType == 4) {
            return createHomeTemplatePreviewView(transformerPositionInfo);
        }
        return null;
    }

    public final View createOrInitTemplatePreview(TransformerPositionInfo transformerPositionInfo, boolean z, boolean z2) {
        if (transformerPositionInfo == null) {
            return null;
        }
        if (z && transformerPositionInfo.getItemType() == 2 && z2) {
            return createTemplateView$default(this, transformerPositionInfo.getTemplateConfig(), true, false, 4, null);
        }
        int itemType = transformerPositionInfo.getItemType();
        if (itemType != -1) {
            if (itemType == 1) {
                return createTemplateView$default(this, transformerPositionInfo.getTemplateConfig(), false, true, 2, null);
            }
            if (itemType != 2 && itemType != 3 && itemType != 4) {
                return null;
            }
        }
        return createLockTemplatePreviewView(transformerPositionInfo);
    }

    public final EaseManager.EaseStyle createScaleEaseStyle() {
        EaseManager.EaseStyle style = EaseManager.getStyle(-2, 0.95f, 0.43f);
        Intrinsics.checkNotNullExpressionValue(style, "getStyle(...)");
        return style;
    }

    public final Animator createShowAboveWallpaperLayersAnimator() {
        View view = this.currentLockTemplatePreviewView;
        if (!(view instanceof BaseTemplateView)) {
            Log.e("Keyguard-Editor-LockScreenTransformerLayer", "current Template is not templateView can not create showAbove Animator");
            return null;
        }
        Intrinsics.checkNotNull(view, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.base.BaseTemplateView");
        final View clockLayer = ((BaseTemplateView) view).getClockLayer();
        setViewAlpha(clockLayer, 0.0f);
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(0.0f, 1.0f);
        valueAnimator.setDuration(300L);
        valueAnimator.setInterpolator(new DecelerateInterpolator(1.0f));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$$ExternalSyntheticLambda10
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                LockScreenTransformerLayer.createShowAboveWallpaperLayersAnimator$lambda$36(this.f$0, clockLayer, valueAnimator2);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer.createShowAboveWallpaperLayersAnimator.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator p0) {
                Intrinsics.checkNotNullParameter(p0, "p0");
                LockScreenTransformerLayer.this.setViewAlpha(clockLayer, 1.0f);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator p0) {
                Intrinsics.checkNotNullParameter(p0, "p0");
                LockScreenTransformerLayer.this.setViewAlpha(clockLayer, 1.0f);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator p0) {
                Intrinsics.checkNotNullParameter(p0, "p0");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator p0) {
                Intrinsics.checkNotNullParameter(p0, "p0");
            }
        });
        return valueAnimator;
    }

    public final TemplateRadiusAnimationListener createTemplateRadiusAnimationListener(View view, float f, boolean z) {
        if (view == null) {
            return null;
        }
        String name = ViewProperty.ALPHA.getName();
        Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
        return new TemplateRadiusAnimationListener(view, f, z, name);
    }

    /* JADX WARN: Code duplicated, block: B:41:0x00cc  */
    /* JADX WARN: Code duplicated, block: B:43:0x00d6  */
    public final BaseTemplateView createTemplateView(CommonConfig commonConfig, boolean z, boolean z2) {
        int i;
        CommonConfig commonConfigDeepCopy;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo;
        String templateId = (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo.getClockInfo()) == null) ? null : clockInfo.getTemplateId();
        if (templateId == null || templateId.length() == 0) {
            Log.w("Keyguard-Editor-LockScreenTransformerLayer", "createTemplateView failed: template = " + commonConfig + ", templateId = " + templateId);
            return null;
        }
        FrameLayout frameLayout = this.currentTemplateViewContainer;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentTemplateViewContainer");
            frameLayout = null;
        }
        View viewFindViewById = frameLayout.findViewById(R.id.tv_custom);
        Wallpaper.Companion companion = Wallpaper.Companion;
        WallpaperInfo wallpaperInfo2 = commonConfig.getLockscreenInfo().getWallpaperInfo();
        String resourceType = wallpaperInfo2 != null ? wallpaperInfo2.getResourceType() : null;
        WallpaperInfo wallpaperInfo3 = commonConfig.getHomeInfo().getWallpaperInfo();
        if (companion.unSupportToEdit(resourceType, wallpaperInfo3 != null ? wallpaperInfo3.getResourceType() : null)) {
            Log.i("Keyguard-Editor-LockScreenTransformerLayer", "createTemplateView: super wallpaper");
            i = 8;
        } else {
            i = 0;
        }
        viewFindViewById.setVisibility(i);
        TemplateViewFactory templateViewFactory = TemplateViewFactory.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        BaseTemplateView baseTemplateViewCreateScaledPreviewTemplateView = templateViewFactory.createScaledPreviewTemplateView(context, templateId, this.layoutParamsForRealTemplateView, this.realTemplateScaleX, this.realTemplateScaleY, true);
        if (baseTemplateViewCreateScaledPreviewTemplateView == null) {
            return null;
        }
        if (z2) {
            baseTemplateViewCreateScaledPreviewTemplateView.setTemplateSource(-1L);
            commonConfigDeepCopy = commonConfig.deepCopy();
            DataUtil dataUtil = DataUtil.INSTANCE;
            WallpaperInfo wallpaperInfo4 = commonConfig.getLockscreenInfo().getWallpaperInfo();
            WallpaperInfo wallpaperInfo5 = commonConfigDeepCopy.getLockscreenInfo().getWallpaperInfo();
            Context context2 = baseTemplateViewCreateScaledPreviewTemplateView.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            dataUtil.resetWallpaperInfoMagicType(wallpaperInfo4, wallpaperInfo5, context2);
            if (commonConfigDeepCopy.getLockscreenInfo().isThirdPartyTheme()) {
                MagicType magicType = MagicType.INSTANCE;
                WallpaperInfo wallpaperInfo6 = commonConfigDeepCopy.getLockscreenInfo().getWallpaperInfo();
                if (magicType.isDepthVideo(wallpaperInfo6 != null ? Integer.valueOf(wallpaperInfo6.getMagicType()) : null)) {
                    wallpaperInfo = commonConfigDeepCopy.getLockscreenInfo().getWallpaperInfo();
                    if (wallpaperInfo != null) {
                        wallpaperInfo.setPositionInfo(null);
                    }
                }
            } else {
                wallpaperInfo = commonConfigDeepCopy.getLockscreenInfo().getWallpaperInfo();
                if (wallpaperInfo != null) {
                    wallpaperInfo.setPositionInfo(null);
                }
            }
        } else {
            commonConfigDeepCopy = commonConfig;
        }
        Bundle clockExtras = commonConfig.getLockscreenInfo().getClockExtras();
        printBundle(clockExtras);
        baseTemplateViewCreateScaledPreviewTemplateView.setExtraParameters(clockExtras);
        baseTemplateViewCreateScaledPreviewTemplateView.loadTemplate(commonConfigDeepCopy);
        if (z) {
            Iterator<T> it = baseTemplateViewCreateScaledPreviewTemplateView.aboveWallpaperLayers().iterator();
            while (it.hasNext()) {
                ((View) it.next()).setVisibility(8);
            }
        }
        return baseTemplateViewCreateScaledPreviewTemplateView;
    }

    public final EaseManager.EaseStyle createTranslationXEaseStyle() {
        EaseManager.EaseStyle style = EaseManager.getStyle(-2, 0.95f, 0.35f);
        Intrinsics.checkNotNullExpressionValue(style, "getStyle(...)");
        return style;
    }

    public final EaseManager.EaseStyle createTranslationYEaseStyle() {
        EaseManager.EaseStyle style = EaseManager.getStyle(-2, 0.95f, 0.45f);
        Intrinsics.checkNotNullExpressionValue(style, "getStyle(...)");
        return style;
    }

    @Nullable
    public final TransformerPositionInfo getEntranceCurrentPositionInfo() {
        return this.entranceCurrentPositionInfo;
    }

    public final float getFinalScaleX() {
        return this.finalScaleX;
    }

    public final float getFinalScaleXWithShowBouncer() {
        return this.finalScaleXWithShowBouncer;
    }

    public final float getFinalScaleY() {
        return this.finalScaleY;
    }

    public final float getFinalScaleYWithShowBouncer() {
        return this.finalScaleYWithShowBouncer;
    }

    public final float getFinalTranslationY() {
        return this.finalTranslationY;
    }

    public final float getFinalTranslationYWithShowBouncer() {
        return this.finalTranslationYWithShowBouncer;
    }

    public final float getPreviewRadiusPixel() {
        return this.previewRadiusPixel;
    }

    public final float getReallyPreviewWidth() {
        return this.previewWidth;
    }

    public final boolean getStartExit() {
        return this.startExit;
    }

    public final void hideTransformerLayer() {
        setVisibility(8);
    }

    public final void inflateAndInit(boolean z) {
        if (this.isInflatedContentView) {
            return;
        }
        if (z) {
            EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), null, null, new AnonymousClass1(null), 3, null);
            return;
        }
        Log.i("Keyguard-Editor-LockScreenTransformerLayer", "inflate content");
        this.isInflatedContentView = true;
        LayoutInflater.from(getContext()).inflate(R.layout.kg_layer_lockscreen_transformer, (ViewGroup) this, true);
        onInit();
    }

    public final void initBottomTemplateContainer() {
        FrameLayout frameLayout = this.bottomTemplateViewContainer;
        FrameLayout frameLayout2 = null;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
            frameLayout = null;
        }
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        layoutParams.height = (int) this.containerHeight;
        FrameLayout frameLayout3 = this.bottomTemplateViewContainer;
        if (frameLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
            frameLayout3 = null;
        }
        frameLayout3.setLayoutParams(layoutParams);
        FrameLayout frameLayout4 = this.bottomTemplateViewContainer;
        if (frameLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
            frameLayout4 = null;
        }
        frameLayout4.setClipToOutline(true);
        FrameLayout frameLayout5 = this.bottomTemplateViewContainer;
        if (frameLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
        } else {
            frameLayout2 = frameLayout5;
        }
        frameLayout2.setOutlineProvider(new RoundOutlineProvider(this.previewRadiusPixel));
    }

    public final void initCurrentTemplateContainer() {
        FrameLayout frameLayout = this.currentTemplateViewContainer;
        FrameLayout frameLayout2 = null;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentTemplateViewContainer");
            frameLayout = null;
        }
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        layoutParams.height = (int) this.containerHeight;
        FrameLayout frameLayout3 = this.currentTemplateViewContainer;
        if (frameLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentTemplateViewContainer");
        } else {
            frameLayout2 = frameLayout3;
        }
        frameLayout2.setLayoutParams(layoutParams);
    }

    public final void initCustomButton(TransformerPositionInfo transformerPositionInfo) {
        CommonConfig templateConfig;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        CustomButtonView customButtonView = null;
        boolean zIsDarkWallpaper = CommonUtils.INSTANCE.isDarkWallpaper((transformerPositionInfo == null || (templateConfig = transformerPositionInfo.getTemplateConfig()) == null || (lockscreenInfo = templateConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo.getCurrentWallpaper());
        ViewUtil viewUtil = ViewUtil.INSTANCE;
        CustomButtonView customButtonView2 = this.tvCustom;
        if (customButtonView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvCustom");
        } else {
            customButtonView = customButtonView2;
        }
        viewUtil.decorateCustomButton(customButtonView, ViewStylerKt.canUseBlur(), zIsDarkWallpaper);
    }

    public final void initLeftTemplateContainer() {
        FrameLayout frameLayout = this.leftTemplateViewContainer;
        FrameLayout frameLayout2 = null;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
            frameLayout = null;
        }
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        layoutParams.height = (int) this.containerHeight;
        FrameLayout frameLayout3 = this.leftTemplateViewContainer;
        if (frameLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
            frameLayout3 = null;
        }
        frameLayout3.setLayoutParams(layoutParams);
        FrameLayout frameLayout4 = this.leftTemplateViewContainer;
        if (frameLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
            frameLayout4 = null;
        }
        frameLayout4.setClipToOutline(true);
        FrameLayout frameLayout5 = this.leftTemplateViewContainer;
        if (frameLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
        } else {
            frameLayout2 = frameLayout5;
        }
        frameLayout2.setOutlineProvider(new RoundOutlineProvider(this.previewRadiusPixel));
    }

    public final void initMyTemplateContainer() {
        FrameLayout frameLayout = this.myTemplateViewContainer;
        FrameLayout frameLayout2 = null;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("myTemplateViewContainer");
            frameLayout = null;
        }
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        layoutParams.height = (int) this.containerHeight;
        FrameLayout frameLayout3 = this.myTemplateViewContainer;
        if (frameLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("myTemplateViewContainer");
        } else {
            frameLayout2 = frameLayout3;
        }
        frameLayout2.setLayoutParams(layoutParams);
    }

    public final void initRightTemplateContainer() {
        FrameLayout frameLayout = this.rightTemplateViewContainer;
        FrameLayout frameLayout2 = null;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
            frameLayout = null;
        }
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        layoutParams.height = (int) this.containerHeight;
        FrameLayout frameLayout3 = this.rightTemplateViewContainer;
        if (frameLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
            frameLayout3 = null;
        }
        frameLayout3.setLayoutParams(layoutParams);
        FrameLayout frameLayout4 = this.rightTemplateViewContainer;
        if (frameLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
            frameLayout4 = null;
        }
        frameLayout4.setClipToOutline(true);
        FrameLayout frameLayout5 = this.rightTemplateViewContainer;
        if (frameLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
        } else {
            frameLayout2 = frameLayout5;
        }
        frameLayout2.setOutlineProvider(new RoundOutlineProvider(this.previewRadiusPixel));
    }

    public final void invalidateBackup() {
        Log.d("Keyguard-Editor-LockScreenTransformerLayer", "lock transformer invalidateBackup");
        this.isBackupInvalid = true;
        Job job = this.delayJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
    }

    public final boolean isBackupInvalid() {
        return this.isBackupInvalid;
    }

    public final boolean isEnterAnimWaitUnlock() {
        return this.isEnterAnimationStarted && !this.isUnlockAnimationStarted;
    }

    public final boolean isEnterAnimationStarted() {
        return this.isEnterAnimationStarted;
    }

    public final boolean isExitAnimationStarted() {
        return this.isExitAnimationStarted;
    }

    public final boolean isInflatedContentView() {
        return this.isInflatedContentView;
    }

    public final boolean isLockedEnterAnimationStarted() {
        return this.isLockedEnterAnimationStarted;
    }

    public final boolean isUnlockAnimationStarted() {
        return this.isUnlockAnimationStarted;
    }

    public final boolean isUnlockEnterAnimationStarted() {
        return this.isUnlockEnterAnimationStarted;
    }

    public final void logStartUpTime(long j) {
        if (j > 0) {
            Log.d("Keyguard-Editor-LockScreenTransformerLayer", "Startup time part2: " + (SystemClock.elapsedRealtime() - j));
        }
    }

    public final void onActivityDestroy() {
        invalidateBackup();
    }

    public final void onAllTransformerCompleted() {
        onEnterTransformerAnimationEnd();
        WeakReference weakReference = this.applyButton;
        View view = weakReference != null ? (View) weakReference.get() : null;
        WeakReference weakReference2 = this.cancelButton;
        Log.i("Keyguard-Editor-LockScreenTransformerLayer", "onAllTransformerCompleted -> applyButton: " + view + ", cancelButton: " + (weakReference2 != null ? (View) weakReference2.get() : null));
        onTransformerAnimationFinished();
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), Dispatchers.getMain(), null, new C01501(null), 2, null);
        WeakReference weakReference3 = this.cancelButton;
        setViewAlpha(weakReference3 != null ? (View) weakReference3.get() : null, 1.0f);
        WeakReference weakReference4 = this.applyButton;
        setViewAlpha(weakReference4 != null ? (View) weakReference4.get() : null, 1.0f);
        Runnable runnable = this.enterAnimationCompleteListener;
        if (runnable != null) {
            runnable.run();
        }
        this.enterAnimationCompleteListener = null;
        invalidateBackup();
    }

    public final void onEnterTransformerAnimationEnd() {
        LockScreenTransformerAnimationListener lockScreenTransformerAnimationListener;
        WeakReference weakReference = this.animationListener;
        if (weakReference == null || (lockScreenTransformerAnimationListener = (LockScreenTransformerAnimationListener) weakReference.get()) == null) {
            return;
        }
        lockScreenTransformerAnimationListener.onEnterLockScreenTransformerAnimationEnd();
    }

    public final void onExitTransformerAnimationBegin() {
        ScenarioRecognitionUtils.INSTANCE.setScenarioStateStart(489L);
        this.isExitAnimationStarted = true;
        this.startExit = true;
        Iterator it = this.transformerEventListener.iterator();
        while (it.hasNext()) {
            ((TransformerEventListener) it.next()).onTransformerBegin(4);
        }
    }

    public final void onExitTransformerAnimationEnd() {
        ScenarioRecognitionUtils.INSTANCE.setScenarioStateEnd(489L);
        this.isExitAnimationStarted = false;
    }

    public final void onExitTransformerAnimationWithBouncerBegin() {
        this.isExitAnimationStarted = true;
        this.startExit = true;
        Iterator it = this.transformerEventListener.iterator();
        while (it.hasNext()) {
            ((TransformerEventListener) it.next()).onTransformerBegin(4);
        }
    }

    public final void onInit() {
        this.layerRoot = (LinearLayout) findViewById(R.id.root);
        this.titleContainer = (RelativeLayout) findViewById(R.id.title_container);
        this.topContainer = (LinearLayout) findViewById(R.id.top_container);
        this.currentTemplateViewContainer = (FrameLayout) findViewById(R.id.current_template_container);
        this.leftTemplateViewContainer = (FrameLayout) findViewById(R.id.left_template_container);
        this.rightTemplateViewContainer = (FrameLayout) findViewById(R.id.right_template_container);
        this.bottomTemplateViewContainer = (FrameLayout) findViewById(R.id.bottom_template_container);
        this.myTemplateViewContainer = (FrameLayout) findViewById(R.id.my_template_container);
        this.tvTitle = (TextView) findViewById(R.id.tv_title);
        this.tvDescription = (TextView) findViewById(R.id.tv_description);
        CustomButtonView customButtonView = (CustomButtonView) findViewById(R.id.tv_custom);
        this.tvCustom = customButtonView;
        TextView textView = null;
        if (customButtonView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvCustom");
            customButtonView = null;
        }
        ViewGroup.LayoutParams layoutParams = customButtonView.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_main_item_custom_button_margin_bottom);
            ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = dimensionPixelSize + resourcesProvider.provideHomeExtraHeight(context);
            CustomButtonView customButtonView2 = this.tvCustom;
            if (customButtonView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvCustom");
                customButtonView2 = null;
            }
            customButtonView2.setLayoutParams(layoutParams);
        }
        CustomButtonView customButtonView3 = this.tvCustom;
        if (customButtonView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvCustom");
            customButtonView3 = null;
        }
        customButtonView3.resetTextSize();
        LargeFontLanguageHelper largeFontLanguageHelper = LargeFontLanguageHelper.INSTANCE;
        TextView textView2 = this.tvTitle;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
            textView2 = null;
        }
        TextView textView3 = this.tvDescription;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvDescription");
        } else {
            textView = textView3;
        }
        largeFontLanguageHelper.adaptTitleAndDescriptionForLargeFontLanguage(textView2, textView, false);
        initCurrentTemplateContainer();
        initMyTemplateContainer();
        initLeftTemplateContainer();
        initRightTemplateContainer();
        initBottomTemplateContainer();
    }

    public final void onScreenConfigurationChanged(boolean z) {
        Log.d("Keyguard-Editor-LockScreenTransformerLayer", "onScreenConfigurationChanged isLockPage = " + z);
        if (this.isInflatedContentView) {
            resetAnimDimens();
            resetLayerViewsDimens();
            resetTransformerLayer();
            resetCurrentTemplateViewInLockPage(z);
            if (z) {
                return;
            }
            resetLeftTemplateViewContainer(this.currentTransformerInfo);
            resetRightTemplateViewContainer(this.currentTransformerInfo);
            resetButtomTemplateViewContainer(this.currentTransformerInfo);
        }
    }

    public final void onTransformerAnimationBegin() {
        ScenarioRecognitionUtils.INSTANCE.setScenarioStateStart(486L);
        this.isEnterAnimationStarted = true;
        this.isUnlockEnterAnimationStarted = true;
        Iterator it = this.transformerEventListener.iterator();
        while (it.hasNext()) {
            ((TransformerEventListener) it.next()).onTransformerBegin(1);
        }
    }

    public final void onTransformerAnimationFinished() {
        if (this.isUnlockEnterAnimationStarted) {
            ScenarioRecognitionUtils.INSTANCE.setScenarioStateEnd(486L);
            Iterator it = this.transformerEventListener.iterator();
            while (it.hasNext()) {
                ((TransformerEventListener) it.next()).onTransformerFinished(1, false);
            }
        } else if (this.isLockedEnterAnimationStarted) {
            ScenarioRecognitionUtils.INSTANCE.setScenarioStateEnd(488L);
            Iterator it2 = this.transformerEventListener.iterator();
            while (it2.hasNext()) {
                ((TransformerEventListener) it2.next()).onTransformerFinished(3, false);
            }
        }
        this.isEnterAnimationStarted = false;
        this.isUnlockEnterAnimationStarted = false;
        this.isLockedEnterAnimationStarted = false;
    }

    public final void onTransformerAnimationUnlockBegin() {
        ScenarioRecognitionUtils.INSTANCE.setScenarioStateStart(488L);
        this.isLockedEnterAnimationStarted = true;
        Iterator it = this.transformerEventListener.iterator();
        while (it.hasNext()) {
            ((TransformerEventListener) it.next()).onTransformerBegin(3);
        }
    }

    public final void onTransformerAnimationUnlockFinished(boolean z) {
        ScenarioRecognitionUtils.INSTANCE.setScenarioStateEnd(487L);
        this.isUnlockAnimationStarted = false;
        Iterator it = this.transformerEventListener.iterator();
        while (it.hasNext()) {
            ((TransformerEventListener) it.next()).onTransformerFinished(2, z);
        }
    }

    public final void onTransformerAnimationWithBouncerBegin() {
        ScenarioRecognitionUtils.INSTANCE.setScenarioStateStart(487L);
        this.isEnterAnimationStarted = true;
        this.isUnlockAnimationStarted = true;
        Iterator it = this.transformerEventListener.iterator();
        while (it.hasNext()) {
            ((TransformerEventListener) it.next()).onTransformerBegin(2);
        }
    }

    public final ImageView previewBackground(TransformerPositionInfo transformerPositionInfo) {
        Bitmap thirdPartyThemePreview;
        PresetTemplateConfig preset;
        TemplateHistoryConfig history;
        Bitmap screenshotImage;
        ImageView imageViewPreviewImageView = previewImageView();
        Integer numValueOf = transformerPositionInfo != null ? Integer.valueOf(transformerPositionInfo.getItemType()) : null;
        if (numValueOf != null && numValueOf.intValue() == -1) {
            if (!this.isEnter) {
                return null;
            }
            imageViewPreviewImageView.setBackgroundResource(DeviceUtil.INSTANCE.isPad() ? R.drawable.kg_negative_card : R.drawable.kg_negative_card_shape);
            return imageViewPreviewImageView;
        }
        if (numValueOf != null && numValueOf.intValue() == 2) {
            TemplateItemBean template = transformerPositionInfo.getTemplate();
            if (template != null && (history = template.getHistory()) != null && (screenshotImage = history.getScreenshotImage()) != null) {
                imageViewPreviewImageView.setImageBitmap(screenshotImage);
                return imageViewPreviewImageView;
            }
        } else {
            if (numValueOf != null && numValueOf.intValue() == 3) {
                TemplateItemBean template2 = transformerPositionInfo.getTemplate();
                if (template2 == null || (preset = template2.getPreset()) == null) {
                    return null;
                }
                imageViewPreviewImageView.setImageBitmap(preset.getPreviewImage());
                return imageViewPreviewImageView;
            }
            if (numValueOf == null || numValueOf.intValue() != 4) {
                return null;
            }
            TemplateItemBean template3 = transformerPositionInfo.getTemplate();
            if (template3 != null && (thirdPartyThemePreview = template3.getThirdPartyThemePreview()) != null) {
                imageViewPreviewImageView.setImageBitmap(thirdPartyThemePreview);
            }
        }
        return imageViewPreviewImageView;
    }

    public final ImageView previewForeground(TransformerPositionInfo transformerPositionInfo) {
        Bitmap presetWallpaper;
        ImageView imageViewPreviewImageView = previewImageView();
        Integer numValueOf = transformerPositionInfo != null ? Integer.valueOf(transformerPositionInfo.getItemType()) : null;
        if (numValueOf == null || numValueOf.intValue() != 3 || (presetWallpaper = transformerPositionInfo.getPresetWallpaper()) == null) {
            return null;
        }
        imageViewPreviewImageView.setImageBitmap(presetWallpaper);
        return imageViewPreviewImageView;
    }

    public final ImageView previewHomeWallpaper(TransformerPositionInfo transformerPositionInfo) {
        TemplateHistoryConfig history;
        ImageView imageViewPreviewImageView = previewImageView();
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        int screenStatus = deviceUtil.getScreenStatus(context);
        currentWallpaperScreenshotUri = null;
        String currentWallpaperScreenshotUri = null;
        Integer numValueOf = transformerPositionInfo != null ? Integer.valueOf(transformerPositionInfo.getItemType()) : null;
        if (numValueOf != null && numValueOf.intValue() == 2) {
            RequestManager requestManagerWith = Glide.with(getContext());
            TemplateItemBean template = transformerPositionInfo.getTemplate();
            if (template != null && (history = template.getHistory()) != null) {
                currentWallpaperScreenshotUri = history.getCurrentWallpaperScreenshotUri(screenStatus);
            }
            ((RequestBuilder) requestManagerWith.load(currentWallpaperScreenshotUri).centerCrop()).into(imageViewPreviewImageView);
            return imageViewPreviewImageView;
        }
        if (numValueOf != null && numValueOf.intValue() == 4) {
            RequestManager requestManagerWith2 = Glide.with(getContext());
            TemplateItemBean template2 = transformerPositionInfo.getTemplate();
            ((RequestBuilder) requestManagerWith2.load(template2 != null ? template2.getThirdPartyThemeHomePreview() : null).centerCrop()).into(imageViewPreviewImageView);
            return imageViewPreviewImageView;
        }
        if (numValueOf == null || numValueOf.intValue() != 1) {
            return null;
        }
        ((RequestBuilder) Glide.with(getContext()).load(this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.Companion.getCurrentScreenshotType(screenStatus, false))).centerCrop()).into(imageViewPreviewImageView);
        return imageViewPreviewImageView;
    }

    public final ImageView previewImageView() {
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    public final ImageView previewWallpaper(TransformerPositionInfo transformerPositionInfo) {
        TemplateItemBean template;
        PresetTemplateConfig preset;
        Bitmap wallpaper;
        TemplateItemBean template2;
        PresetTemplateConfig preset2;
        ImageView imageViewPreviewImageView = previewImageView();
        Log.i("Keyguard-Editor-LockScreenTransformerLayer", "preset wallpaper " + ((transformerPositionInfo == null || (template2 = transformerPositionInfo.getTemplate()) == null || (preset2 = template2.getPreset()) == null) ? null : preset2.getWallpaper()));
        Integer numValueOf = transformerPositionInfo != null ? Integer.valueOf(transformerPositionInfo.getItemType()) : null;
        if (numValueOf == null || numValueOf.intValue() != 3 || (template = transformerPositionInfo.getTemplate()) == null || (preset = template.getPreset()) == null || (wallpaper = preset.getWallpaper()) == null) {
            return null;
        }
        imageViewPreviewImageView.setImageBitmap(wallpaper);
        return imageViewPreviewImageView;
    }

    public final void printBundle(Bundle bundle) {
        Log.d("Keyguard-Editor-LockScreenTransformerLayer", "---------------- LockScreenTransformerLayer clockExtras -----------------");
        Set<String> setKeySet = bundle != null ? bundle.keySet() : null;
        if (setKeySet == null || setKeySet.isEmpty()) {
            Log.i("Keyguard-Editor-LockScreenTransformerLayer", "printBundle: bundle is empty~");
            return;
        }
        for (String str : setKeySet) {
            Log.i("Keyguard-Editor-LockScreenTransformerLayer", "printBundle: key = " + str + ", value = " + bundle.get(str));
        }
    }

    public final void reStartTransformerAnimation() {
        FrameLayout frameLayout = this.leftTemplateViewContainer;
        FrameLayout frameLayout2 = null;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
            frameLayout = null;
        }
        if (frameLayout.getVisibility() == 0) {
            startLeftPreviewTranslateInAnim();
        }
        FrameLayout frameLayout3 = this.rightTemplateViewContainer;
        if (frameLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
            frameLayout3 = null;
        }
        if (frameLayout3.getVisibility() == 0) {
            startRightPreviewTranslateInAnim();
        }
        FrameLayout frameLayout4 = this.bottomTemplateViewContainer;
        if (frameLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
        } else {
            frameLayout2 = frameLayout4;
        }
        if (frameLayout2.getVisibility() == 0) {
            startBottomPreviewTranslateInAnim();
        }
    }

    public final void reloadCurrentTemplateWallpaper() {
        CommonConfig templateConfig;
        Unit unit;
        CommonConfig templateConfig2;
        TransformerPositionInfo transformerPositionInfo = this.entranceCurrentPositionInfo;
        if (transformerPositionInfo != null && (templateConfig = transformerPositionInfo.getTemplateConfig()) != null) {
            TemplateApi.Companion companion = TemplateApi.Companion;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            WallpaperSource wallpaperSourceRequireLockScreenWallpaper$default = TemplateApi.DefaultImpls.requireLockScreenWallpaper$default(companion.getInstance(context), templateConfig, 0, 2, null);
            TransformerPositionInfo transformerPositionInfo2 = this.entranceCurrentPositionInfo;
            if (transformerPositionInfo2 == null || (templateConfig2 = transformerPositionInfo2.getTemplateConfig()) == null) {
                unit = null;
            } else {
                WallpaperInfo wallpaperInfo = templateConfig2.getLockscreenInfo().getWallpaperInfo();
                if (wallpaperInfo != null) {
                    wallpaperInfo.setCurrentWallpaper(wallpaperSourceRequireLockScreenWallpaper$default != null ? wallpaperSourceRequireLockScreenWallpaper$default.getLockWallpaper() : null);
                }
                WallpaperInfo wallpaperInfo2 = templateConfig2.getHomeInfo().getWallpaperInfo();
                if (wallpaperInfo2 != null) {
                    wallpaperInfo2.setCurrentWallpaper(wallpaperSourceRequireLockScreenWallpaper$default != null ? wallpaperSourceRequireLockScreenWallpaper$default.getHomeWallpaper() : null);
                }
                unit = Unit.INSTANCE;
            }
            if (unit != null) {
                return;
            }
        }
        TransformerPositionInfo transformerPositionInfo3 = this.entranceCurrentPositionInfo;
        Log.e("Keyguard-Editor-LockScreenTransformerLayer", "reloadCurrentTemplateWallpaper: config is null " + (transformerPositionInfo3 == null) + " " + ((transformerPositionInfo3 != null ? transformerPositionInfo3.getTemplateConfig() : null) == null));
    }

    public final void resetAnimDimens() {
        float f;
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        this.previewRadiusPixel = resourcesProvider.providePreviewRadius(context);
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        this.outRealSize = resourcesProvider.provideRealSize(context2);
        Context context3 = getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
        this._neighboringInitialScale = resourcesProvider.provideMinScaleForNeighboringItem(context3);
        Point point = this.outRealSize;
        this.realScreenWidth = point.x;
        this.realScreenHeight = point.y;
        Context context4 = getContext();
        Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
        this.containerWidth = resourcesProvider.provideTemplateContainerWidth(context4);
        Context context5 = getContext();
        Intrinsics.checkNotNullExpressionValue(context5, "getContext(...)");
        this.containerPadding = resourcesProvider.provideTemplateContainerPadding(context5);
        CrossListViewUtil crossListViewUtil = CrossListViewUtil.INSTANCE;
        Context context6 = getContext();
        Intrinsics.checkNotNullExpressionValue(context6, "getContext(...)");
        this.containerHeight = crossListViewUtil.calculateTemplateContainerHeight(context6, this.containerWidth, this.outRealSize, this.containerPadding);
        Context context7 = getContext();
        Intrinsics.checkNotNullExpressionValue(context7, "getContext(...)");
        this.previewWidth = resourcesProvider.provideTemplatePreviewWidth(context7);
        Context context8 = getContext();
        Intrinsics.checkNotNullExpressionValue(context8, "getContext(...)");
        float fProvideTemplatePreviewHeight = resourcesProvider.provideTemplatePreviewHeight(context8);
        this.previewHeight = fProvideTemplatePreviewHeight;
        this.finalScaleX = this.previewWidth / this.realScreenWidth;
        this.finalScaleY = fProvideTemplatePreviewHeight / this.realScreenHeight;
        this.layoutParamsForRealTemplateView = new FrameLayout.LayoutParams(this.realScreenWidth, this.realScreenHeight);
        this.realTemplateScaleX = this.previewWidth / this.realScreenWidth;
        this.realTemplateScaleY = this.previewHeight / this.realScreenHeight;
        this.actualTopForCurrentTemplate = getResources().getDimensionPixelSize(R.dimen.kg_main_pager_margin_top) + getResources().getDimensionPixelSize(R.dimen.kg_main_item_floor_title_bar_height) + getResources().getDimensionPixelSize(R.dimen.kg_main_item_floor_pager_margin_top) + this.containerPadding;
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context9 = getContext();
        Intrinsics.checkNotNullExpressionValue(context9, "getContext(...)");
        this.isRtl = deviceUtil.isRtl(context9);
        Context context10 = getContext();
        Intrinsics.checkNotNullExpressionValue(context10, "getContext(...)");
        float fProvideHorizontalPagerOffscreenOffset = resourcesProvider.provideHorizontalPagerOffscreenOffset(context10);
        this.pagerOffscreenOffset = fProvideHorizontalPagerOffscreenOffset;
        this.finalTranslateXForRight = this.isRtl ? fProvideHorizontalPagerOffscreenOffset + (((1 - this._neighboringInitialScale) * this.containerWidth) / 2) + this.containerPadding : ((-fProvideHorizontalPagerOffscreenOffset) - (((1 - this._neighboringInitialScale) * this.containerWidth) / 2)) - this.containerPadding;
        Context context11 = getContext();
        Intrinsics.checkNotNullExpressionValue(context11, "getContext(...)");
        float fProvideBottomPagerOffscreenOffset = resourcesProvider.provideBottomPagerOffscreenOffset(context11);
        this.verticalOffscreenOffset = fProvideBottomPagerOffscreenOffset;
        float f2 = this.containerPadding;
        this.finalTranslateYForBottom = (-fProvideBottomPagerOffscreenOffset) - f2;
        if (this.isRtl) {
            float f3 = 2;
            f = ((-this.pagerOffscreenOffset) - (((1 - this._neighboringInitialScale) * this.containerWidth) / f3)) - (f2 / f3);
        } else {
            float f4 = 2;
            f = this.pagerOffscreenOffset + (((1 - this._neighboringInitialScale) * this.containerWidth) / f4) + (f2 / f4);
        }
        this.finalTranslateXForLeft = f;
        float f5 = this.actualTopForCurrentTemplate;
        int i = this.realScreenHeight;
        float f6 = this.previewHeight;
        float f7 = f5 - ((i - f6) / 2);
        this.finalTranslationY = f7;
        this.currentTemplateFromScaleX = this.realScreenWidth / this.previewWidth;
        this.currentTemplateFromScaleY = i / f6;
        float f8 = this.finalScaleX;
        float f9 = this.unlockFactor;
        this.finalScaleXWithShowBouncer = f8 * f9;
        this.finalScaleYWithShowBouncer = this.finalScaleY * f9;
        this.finalTranslationYWithShowBouncer = f7 * f9;
        EditorServiceManager.Companion companion = EditorServiceManager.Companion;
        if (companion.getInstance().getCurrentState() != EditorServiceManager.EditorState.NULL) {
            companion.getInstance().onScreenConfigurationChange(true);
        }
    }

    public final void resetBottomTemplateContainerDimens(int i, int i2, int i3) {
        FrameLayout frameLayout = this.bottomTemplateViewContainer;
        FrameLayout frameLayout2 = null;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
            frameLayout = null;
        }
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = i;
        }
        if (layoutParams != null) {
            layoutParams.height = i2;
        }
        FrameLayout frameLayout3 = this.bottomTemplateViewContainer;
        if (frameLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
            frameLayout3 = null;
        }
        frameLayout3.setLayoutParams(layoutParams);
        FrameLayout frameLayout4 = this.bottomTemplateViewContainer;
        if (frameLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
        } else {
            frameLayout2 = frameLayout4;
        }
        frameLayout2.setPadding(i3, i3, i3, i3);
    }

    public final void resetButtomTemplateViewContainer(TransformerInfo transformerInfo) {
        if (transformerInfo == null) {
            return;
        }
        TransformerPositionInfo bottomPositionInfo = transformerInfo.getBottomPositionInfo();
        FrameLayout frameLayout = this.bottomTemplateViewContainer;
        FrameLayout frameLayout2 = null;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
            frameLayout = null;
        }
        setVisibility(frameLayout, bottomPositionInfo);
        if (bottomPositionInfo != null) {
            View viewCreateOrInitTemplatePreview$default = createOrInitTemplatePreview$default(this, bottomPositionInfo, false, false, 6, null);
            this.bottomTemplatePreviewView = viewCreateOrInitTemplatePreview$default;
            if (viewCreateOrInitTemplatePreview$default == null) {
                Log.w("Keyguard-Editor-LockScreenTransformerLayer", "createOrInitTemplatePreview -> bottom failed: " + transformerInfo.getBottomPositionInfo());
                return;
            }
            RoundOutlineProvider roundOutlineProvider = new RoundOutlineProvider(this.previewRadiusPixel);
            FrameLayout frameLayout3 = this.bottomTemplateViewContainer;
            if (frameLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
            } else {
                frameLayout2 = frameLayout3;
            }
            View view = this.bottomTemplatePreviewView;
            Intrinsics.checkNotNull(view);
            LockScreenTransformerLayerKt.addTemplatePreviewView$default(frameLayout2, view, roundOutlineProvider, false, 8, null);
        }
    }

    public final void resetCurrentTemplateContainerDimens(int i, int i2, int i3) {
        FrameLayout frameLayout = this.currentTemplateViewContainer;
        FrameLayout frameLayout2 = null;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentTemplateViewContainer");
            frameLayout = null;
        }
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = i;
        }
        if (layoutParams != null) {
            layoutParams.height = i2;
        }
        FrameLayout frameLayout3 = this.currentTemplateViewContainer;
        if (frameLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentTemplateViewContainer");
            frameLayout3 = null;
        }
        frameLayout3.setLayoutParams(layoutParams);
        FrameLayout frameLayout4 = this.currentTemplateViewContainer;
        if (frameLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentTemplateViewContainer");
        } else {
            frameLayout2 = frameLayout4;
        }
        frameLayout2.setPadding(i3, i3, i3, i3);
    }

    public final void resetCurrentTemplateViewInLockPage(boolean z) {
        if (this.currentLockTemplatePreviewView != null) {
            createOrAddCurrentTemplatePreviewView(this.entranceCurrentPositionInfo, false);
            View view = this.currentLockTemplatePreviewView;
            if (view instanceof BaseTemplateView) {
                Intrinsics.checkNotNull(view, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.base.BaseTemplateView");
                View clockLayer = ((BaseTemplateView) view).getClockLayer();
                if (clockLayer != null) {
                    clockLayer.setAlpha(0.01f);
                }
            }
            resetPreviewLayoutParams(this.rightTemplatePreviewView);
            resetPreviewLayoutParams(this.bottomTemplatePreviewView);
            if (this.rightTemplateViewContainer == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
            }
            FrameLayout frameLayout = this.rightTemplateViewContainer;
            FrameLayout frameLayout2 = null;
            if (frameLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
                frameLayout = null;
            }
            Folme.useAt(frameLayout).cancel();
            FrameLayout frameLayout3 = this.rightTemplateViewContainer;
            if (frameLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
                frameLayout3 = null;
            }
            frameLayout3.setTranslationX(0.0f);
            if (this.bottomTemplateViewContainer == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
            }
            FrameLayout frameLayout4 = this.bottomTemplateViewContainer;
            if (frameLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
                frameLayout4 = null;
            }
            Folme.useAt(frameLayout4).cancel();
            FrameLayout frameLayout5 = this.bottomTemplateViewContainer;
            if (frameLayout5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
                frameLayout5 = null;
            }
            frameLayout5.setTranslationY((this.containerHeight * 0.5f) / 2);
            if (z) {
                return;
            }
            resetPreviewLayoutParams(this.leftTemplatePreviewView);
            if (this.leftTemplateViewContainer == null) {
                Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
            }
            FrameLayout frameLayout6 = this.leftTemplateViewContainer;
            if (frameLayout6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
                frameLayout6 = null;
            }
            Folme.useAt(frameLayout6).cancel();
            FrameLayout frameLayout7 = this.leftTemplateViewContainer;
            if (frameLayout7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
            } else {
                frameLayout2 = frameLayout7;
            }
            frameLayout2.setTranslationX(0.0f);
        }
    }

    public final void resetCustomButtonDimens() {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_main_item_custom_button_margin_bottom);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_main_item_custom_button_padding_horizontal);
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.kg_main_item_custom_button_padding_vertical);
        float dimension = getResources().getDimension(R.dimen.kg_main_item_custom_button_text_size);
        CustomButtonView customButtonView = this.tvCustom;
        CustomButtonView customButtonView2 = null;
        if (customButtonView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvCustom");
            customButtonView = null;
        }
        ViewGroup.LayoutParams layoutParams = customButtonView.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = dimensionPixelSize;
            CustomButtonView customButtonView3 = this.tvCustom;
            if (customButtonView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvCustom");
                customButtonView3 = null;
            }
            customButtonView3.setLayoutParams(layoutParams);
        }
        CustomButtonView customButtonView4 = this.tvCustom;
        if (customButtonView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvCustom");
            customButtonView4 = null;
        }
        customButtonView4.setPadding(dimensionPixelSize2, dimensionPixelSize3, dimensionPixelSize2, dimensionPixelSize3);
        CustomButtonView customButtonView5 = this.tvCustom;
        if (customButtonView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvCustom");
            customButtonView5 = null;
        }
        customButtonView5.setTextSize(0, dimension);
        CustomButtonView customButtonView6 = this.tvCustom;
        if (customButtonView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvCustom");
        } else {
            customButtonView2 = customButtonView6;
        }
        customButtonView2.resetTextSize();
    }

    public final void resetLayerRootDimens() {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_main_pager_margin_top);
        LinearLayout linearLayout = this.layerRoot;
        ViewGroup.LayoutParams layoutParams = linearLayout != null ? linearLayout.getLayoutParams() : null;
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = dimensionPixelSize;
            LinearLayout linearLayout2 = this.layerRoot;
            if (linearLayout2 != null) {
                linearLayout2.setLayoutParams(layoutParams);
            }
        }
    }

    public final void resetLayerViewsDimens() {
        resetLayerRootDimens();
        LargeFontLanguageHelper largeFontLanguageHelper = LargeFontLanguageHelper.INSTANCE;
        TextView textView = this.tvTitle;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
            textView = null;
        }
        TextView textView3 = this.tvDescription;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvDescription");
        } else {
            textView2 = textView3;
        }
        largeFontLanguageHelper.adaptTitleAndDescriptionForLargeFontLanguage(textView, textView2, true);
        resetTitleContainerDimens();
        resetTopContainerDimens();
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_main_item_container_width);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_main_item_container_height);
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.kg_main_item_container_padding);
        resetLeftTemplateContainerDimens(dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize3);
        resetMyTemplateContainerDimens(dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize3);
        resetCurrentTemplateContainerDimens(dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize3);
        resetCustomButtonDimens();
        resetRightTemplateContainerDimens(dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize3);
        resetBottomTemplateContainerDimens(dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize3);
    }

    public final void resetLeftTemplateContainerDimens(int i, int i2, int i3) {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_main_item_container_width_minus);
        FrameLayout frameLayout = this.leftTemplateViewContainer;
        FrameLayout frameLayout2 = null;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
            frameLayout = null;
        }
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = i;
        }
        if (layoutParams != null) {
            layoutParams.height = i2;
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) layoutParams).setMarginStart(dimensionPixelSize);
        }
        FrameLayout frameLayout3 = this.leftTemplateViewContainer;
        if (frameLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
            frameLayout3 = null;
        }
        frameLayout3.setLayoutParams(layoutParams);
        FrameLayout frameLayout4 = this.leftTemplateViewContainer;
        if (frameLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
        } else {
            frameLayout2 = frameLayout4;
        }
        frameLayout2.setPadding(i3, i3, i3, i3);
    }

    public final void resetLeftTemplateViewContainer(TransformerInfo transformerInfo) {
        if (transformerInfo == null) {
            return;
        }
        TransformerPositionInfo leftPositionInfo = transformerInfo.getLeftPositionInfo();
        FrameLayout frameLayout = this.leftTemplateViewContainer;
        FrameLayout frameLayout2 = null;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
            frameLayout = null;
        }
        setVisibility(frameLayout, leftPositionInfo);
        if (leftPositionInfo != null) {
            View viewCreateOrInitTemplatePreview$default = createOrInitTemplatePreview$default(this, leftPositionInfo, false, false, 6, null);
            this.leftTemplatePreviewView = viewCreateOrInitTemplatePreview$default;
            if (viewCreateOrInitTemplatePreview$default == null) {
                Log.w("Keyguard-Editor-LockScreenTransformerLayer", "createOrInitTemplatePreview -> left failed: " + transformerInfo.getCurrentPositionInfo());
                return;
            }
            RoundOutlineProvider roundOutlineProvider = new RoundOutlineProvider(this.previewRadiusPixel);
            FrameLayout frameLayout3 = this.leftTemplateViewContainer;
            if (frameLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
            } else {
                frameLayout2 = frameLayout3;
            }
            View view = this.leftTemplatePreviewView;
            Intrinsics.checkNotNull(view);
            LockScreenTransformerLayerKt.addTemplatePreviewView$default(frameLayout2, view, roundOutlineProvider, false, 8, null);
        }
    }

    public final void resetMyTemplateContainerDimens(int i, int i2, int i3) {
        FrameLayout frameLayout = this.myTemplateViewContainer;
        FrameLayout frameLayout2 = null;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("myTemplateViewContainer");
            frameLayout = null;
        }
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = i;
        }
        if (layoutParams != null) {
            layoutParams.height = i2;
        }
        FrameLayout frameLayout3 = this.myTemplateViewContainer;
        if (frameLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("myTemplateViewContainer");
            frameLayout3 = null;
        }
        frameLayout3.setLayoutParams(layoutParams);
        FrameLayout frameLayout4 = this.myTemplateViewContainer;
        if (frameLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("myTemplateViewContainer");
        } else {
            frameLayout2 = frameLayout4;
        }
        frameLayout2.setPadding(i3, i3, i3, i3);
    }

    public final void resetPreviewLayoutParams(View view) {
        if (view != null) {
            view.setLayoutParams(new FrameLayout.LayoutParams(this.realScreenWidth, this.realScreenHeight));
            view.setPivotY(0.0f);
            view.setPivotX(this.isRtl ? this.realScreenWidth : 0.0f);
            view.setScaleY(this.realTemplateScaleY);
            view.setScaleX(this.realTemplateScaleX);
        }
    }

    public final void resetRightTemplateContainerDimens(int i, int i2, int i3) {
        FrameLayout frameLayout = this.rightTemplateViewContainer;
        FrameLayout frameLayout2 = null;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
            frameLayout = null;
        }
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = i;
        }
        if (layoutParams != null) {
            layoutParams.height = i2;
        }
        FrameLayout frameLayout3 = this.rightTemplateViewContainer;
        if (frameLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
            frameLayout3 = null;
        }
        frameLayout3.setLayoutParams(layoutParams);
        FrameLayout frameLayout4 = this.rightTemplateViewContainer;
        if (frameLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
        } else {
            frameLayout2 = frameLayout4;
        }
        frameLayout2.setPadding(i3, i3, i3, i3);
    }

    public final void resetRightTemplateViewContainer(TransformerInfo transformerInfo) {
        if (transformerInfo == null) {
            return;
        }
        TransformerPositionInfo rightPositionInfo = transformerInfo.getRightPositionInfo();
        FrameLayout frameLayout = this.rightTemplateViewContainer;
        FrameLayout frameLayout2 = null;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
            frameLayout = null;
        }
        setVisibility(frameLayout, rightPositionInfo);
        if (rightPositionInfo != null) {
            View viewCreateOrInitTemplatePreview$default = createOrInitTemplatePreview$default(this, rightPositionInfo, false, false, 6, null);
            this.rightTemplatePreviewView = viewCreateOrInitTemplatePreview$default;
            if (viewCreateOrInitTemplatePreview$default == null) {
                Log.w("Keyguard-Editor-LockScreenTransformerLayer", "createOrInitTemplatePreview -> right failed: " + transformerInfo.getRightPositionInfo());
                return;
            }
            RoundOutlineProvider roundOutlineProvider = new RoundOutlineProvider(this.previewRadiusPixel);
            FrameLayout frameLayout3 = this.rightTemplateViewContainer;
            if (frameLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
            } else {
                frameLayout2 = frameLayout3;
            }
            View view = this.rightTemplatePreviewView;
            Intrinsics.checkNotNull(view);
            LockScreenTransformerLayerKt.addTemplatePreviewView$default(frameLayout2, view, roundOutlineProvider, false, 8, null);
        }
    }

    public final void resetTitleContainerDimens() {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_main_item_floor_title_bar_width);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_main_item_floor_title_bar_height);
        RelativeLayout relativeLayout = this.titleContainer;
        ViewGroup.LayoutParams layoutParams = relativeLayout != null ? relativeLayout.getLayoutParams() : null;
        if (layoutParams != null) {
            layoutParams.width = dimensionPixelSize;
        }
        if (layoutParams != null) {
            layoutParams.height = dimensionPixelSize2;
        }
        RelativeLayout relativeLayout2 = this.titleContainer;
        if (relativeLayout2 != null) {
            relativeLayout2.setLayoutParams(layoutParams);
        }
    }

    public final void resetTopContainerDimens() {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_main_item_floor_pager_margin_top);
        LinearLayout linearLayout = this.topContainer;
        ViewGroup.LayoutParams layoutParams = linearLayout != null ? linearLayout.getLayoutParams() : null;
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = dimensionPixelSize;
            LinearLayout linearLayout2 = this.topContainer;
            if (linearLayout2 != null) {
                linearLayout2.setLayoutParams(layoutParams);
            }
        }
    }

    public final void resetTransformerLayer() {
        if (this.isInflatedContentView) {
            initCurrentTemplateContainer();
            initMyTemplateContainer();
            initLeftTemplateContainer();
            initRightTemplateContainer();
            initBottomTemplateContainer();
        }
    }

    public final void setEnterAnimationCompleteListener(@Nullable Runnable runnable) {
        this.enterAnimationCompleteListener = runnable;
    }

    public final void setEnterAnimationStarted(boolean z) {
        this.isEnterAnimationStarted = z;
    }

    public final void setEntranceCurrentPositionInfo(@Nullable TransformerPositionInfo transformerPositionInfo) {
        this.entranceCurrentPositionInfo = transformerPositionInfo;
    }

    public final void setExitAnimationStarted(boolean z) {
        this.isExitAnimationStarted = z;
    }

    public final void setFinalScaleX(float f) {
        this.finalScaleX = f;
    }

    public final void setFinalScaleXWithShowBouncer(float f) {
        this.finalScaleXWithShowBouncer = f;
    }

    public final void setFinalScaleY(float f) {
        this.finalScaleY = f;
    }

    public final void setFinalScaleYWithShowBouncer(float f) {
        this.finalScaleYWithShowBouncer = f;
    }

    public final void setFinalTranslationY(float f) {
        this.finalTranslationY = f;
    }

    public final void setFinalTranslationYWithShowBouncer(float f) {
        this.finalTranslationYWithShowBouncer = f;
    }

    public final void setInflatedContentView(boolean z) {
        this.isInflatedContentView = z;
    }

    public final void setLockScreenTransformerAnimationListener(@NotNull WeakReference<LockScreenTransformerAnimationListener> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        LockScreenTransformerAnimationListener lockScreenTransformerAnimationListener = listener.get();
        WeakReference weakReference = this.animationListener;
        if (Intrinsics.areEqual(lockScreenTransformerAnimationListener, weakReference != null ? (LockScreenTransformerAnimationListener) weakReference.get() : null)) {
            return;
        }
        this.animationListener = listener;
    }

    public final void setLockedEnterAnimationStarted(boolean z) {
        this.isLockedEnterAnimationStarted = z;
    }

    public final void setStartExit(boolean z) {
        this.startExit = z;
    }

    public final void setTransformerInfo(@Nullable TransformerInfo transformerInfo, boolean z, boolean z2) {
        FrameLayout frameLayout;
        FrameLayout frameLayout2;
        FrameLayout frameLayout3;
        View viewCreateOrInitTemplatePreview$default;
        TransformerPositionInfo transformerPositionInfo;
        Integer descriptionRes;
        Integer titleRes;
        LockScreenTransformerLayer lockScreenTransformerLayer = this;
        lockScreenTransformerLayer.applySuccess = z;
        lockScreenTransformerLayer.isEnter = z2;
        lockScreenTransformerLayer.currentTransformerInfo = transformerInfo;
        if (transformerInfo == null) {
            return;
        }
        if (transformerInfo.getTitleRes() == null || ((titleRes = transformerInfo.getTitleRes()) != null && titleRes.intValue() == 0)) {
            TextView textView = lockScreenTransformerLayer.tvTitle;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
                textView = null;
            }
            textView.setVisibility(8);
        } else {
            TextView textView2 = lockScreenTransformerLayer.tvTitle;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
                textView2 = null;
            }
            textView2.setText(transformerInfo.getTitleRes().intValue());
            TextView textView3 = lockScreenTransformerLayer.tvTitle;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
                textView3 = null;
            }
            textView3.setVisibility(0);
        }
        if (transformerInfo.getDescriptionRes() == null || ((descriptionRes = transformerInfo.getDescriptionRes()) != null && descriptionRes.intValue() == 0)) {
            TextView textView4 = lockScreenTransformerLayer.tvDescription;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvDescription");
                textView4 = null;
            }
            textView4.setVisibility(8);
        } else {
            TextView textView5 = lockScreenTransformerLayer.tvDescription;
            if (textView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvDescription");
                textView5 = null;
            }
            textView5.setText(transformerInfo.getDescriptionRes().intValue());
            TextView textView6 = lockScreenTransformerLayer.tvDescription;
            if (textView6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvDescription");
                textView6 = null;
            }
            textView6.setVisibility(0);
        }
        TransformerPositionInfo currentPositionInfo = transformerInfo.getCurrentPositionInfo();
        FrameLayout frameLayout4 = lockScreenTransformerLayer.currentTemplateViewContainer;
        if (frameLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentTemplateViewContainer");
            frameLayout4 = null;
        }
        lockScreenTransformerLayer.setVisibility(frameLayout4, currentPositionInfo);
        if (currentPositionInfo != null) {
            if (z2) {
                lockScreenTransformerLayer.entranceCurrentPositionInfo = currentPositionInfo;
            } else {
                TransformerPositionInfo transformerPositionInfo2 = lockScreenTransformerLayer.entranceCurrentPositionInfo;
                if (transformerPositionInfo2 != null && transformerPositionInfo2.getItemType() == 4) {
                    viewCreateOrInitTemplatePreview$default = createOrInitTemplatePreview$default(lockScreenTransformerLayer, lockScreenTransformerLayer.entranceCurrentPositionInfo, false, false, 6, null);
                    lockScreenTransformerLayer = this;
                } else if (currentPositionInfo.getItemType() == 1) {
                    lockScreenTransformerLayer = this;
                    viewCreateOrInitTemplatePreview$default = createOrInitTemplatePreview$default(lockScreenTransformerLayer, currentPositionInfo, false, false, 6, null);
                } else {
                    lockScreenTransformerLayer = this;
                    viewCreateOrInitTemplatePreview$default = createOrInitTemplatePreview$default(lockScreenTransformerLayer, lockScreenTransformerLayer.entranceCurrentPositionInfo, false, false, 6, null);
                }
                lockScreenTransformerLayer.myLockTemplatePreviewView = viewCreateOrInitTemplatePreview$default;
                if (viewCreateOrInitTemplatePreview$default != null) {
                    viewCreateOrInitTemplatePreview$default.setId(R.id.kg_transformer_my_lock_template);
                }
                TransformerPositionInfo transformerPositionInfo3 = lockScreenTransformerLayer.entranceCurrentPositionInfo;
                View viewCreateOrInitHomeTemplatePreview = ((transformerPositionInfo3 == null || transformerPositionInfo3.getItemType() != 4) && currentPositionInfo.getItemType() == 1) ? lockScreenTransformerLayer.createOrInitHomeTemplatePreview(currentPositionInfo) : lockScreenTransformerLayer.createOrInitHomeTemplatePreview(lockScreenTransformerLayer.entranceCurrentPositionInfo);
                lockScreenTransformerLayer.myHomeTemplatePreviewView = viewCreateOrInitHomeTemplatePreview;
                if (viewCreateOrInitHomeTemplatePreview != null) {
                    viewCreateOrInitHomeTemplatePreview.setId(R.id.kg_transformer_my_home_template);
                }
            }
            if (z2 || z || lockScreenTransformerLayer.currentLockTemplatePreviewView == null || currentPositionInfo.getItemType() != 1 || (transformerPositionInfo = lockScreenTransformerLayer.entranceCurrentPositionInfo) == null || transformerPositionInfo.getItemType() != 1) {
                lockScreenTransformerLayer.createOrAddCurrentTemplatePreviewView(currentPositionInfo, z);
            }
            lockScreenTransformerLayer.initCustomButton(currentPositionInfo);
        }
        TransformerPositionInfo leftPositionInfo = transformerInfo.getLeftPositionInfo();
        FrameLayout frameLayout5 = lockScreenTransformerLayer.leftTemplateViewContainer;
        if (frameLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
            frameLayout5 = null;
        }
        lockScreenTransformerLayer.setVisibility(frameLayout5, leftPositionInfo);
        if (leftPositionInfo != null) {
            View viewCreateOrInitTemplatePreview$default2 = createOrInitTemplatePreview$default(lockScreenTransformerLayer, leftPositionInfo, false, false, 6, null);
            lockScreenTransformerLayer.leftTemplatePreviewView = viewCreateOrInitTemplatePreview$default2;
            if (viewCreateOrInitTemplatePreview$default2 != null) {
                RoundOutlineProvider roundOutlineProvider = new RoundOutlineProvider(lockScreenTransformerLayer.previewRadiusPixel);
                FrameLayout frameLayout6 = lockScreenTransformerLayer.leftTemplateViewContainer;
                if (frameLayout6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
                    frameLayout3 = null;
                } else {
                    frameLayout3 = frameLayout6;
                }
                View view = lockScreenTransformerLayer.leftTemplatePreviewView;
                Intrinsics.checkNotNull(view);
                LockScreenTransformerLayerKt.addTemplatePreviewView$default(frameLayout3, view, roundOutlineProvider, false, 8, null);
            } else {
                Log.w("Keyguard-Editor-LockScreenTransformerLayer", "createOrInitTemplatePreview -> left failed: " + currentPositionInfo);
            }
        }
        TransformerPositionInfo rightPositionInfo = transformerInfo.getRightPositionInfo();
        FrameLayout frameLayout7 = lockScreenTransformerLayer.rightTemplateViewContainer;
        if (frameLayout7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
            frameLayout7 = null;
        }
        lockScreenTransformerLayer.setVisibility(frameLayout7, rightPositionInfo);
        if (rightPositionInfo != null) {
            View viewCreateOrInitTemplatePreview$default3 = createOrInitTemplatePreview$default(lockScreenTransformerLayer, rightPositionInfo, false, false, 6, null);
            lockScreenTransformerLayer.rightTemplatePreviewView = viewCreateOrInitTemplatePreview$default3;
            if (viewCreateOrInitTemplatePreview$default3 != null) {
                RoundOutlineProvider roundOutlineProvider2 = new RoundOutlineProvider(lockScreenTransformerLayer.previewRadiusPixel);
                FrameLayout frameLayout8 = lockScreenTransformerLayer.rightTemplateViewContainer;
                if (frameLayout8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
                    frameLayout2 = null;
                } else {
                    frameLayout2 = frameLayout8;
                }
                View view2 = lockScreenTransformerLayer.rightTemplatePreviewView;
                Intrinsics.checkNotNull(view2);
                LockScreenTransformerLayerKt.addTemplatePreviewView$default(frameLayout2, view2, roundOutlineProvider2, false, 8, null);
            } else {
                Log.w("Keyguard-Editor-LockScreenTransformerLayer", "createOrInitTemplatePreview -> right failed: " + currentPositionInfo);
            }
        }
        TransformerPositionInfo bottomPositionInfo = transformerInfo.getBottomPositionInfo();
        FrameLayout frameLayout9 = lockScreenTransformerLayer.bottomTemplateViewContainer;
        if (frameLayout9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
            frameLayout9 = null;
        }
        lockScreenTransformerLayer.setVisibility(frameLayout9, bottomPositionInfo);
        if (bottomPositionInfo != null) {
            View viewCreateOrInitTemplatePreview$default4 = createOrInitTemplatePreview$default(lockScreenTransformerLayer, bottomPositionInfo, false, false, 6, null);
            lockScreenTransformerLayer.bottomTemplatePreviewView = viewCreateOrInitTemplatePreview$default4;
            if (viewCreateOrInitTemplatePreview$default4 == null) {
                Log.w("Keyguard-Editor-LockScreenTransformerLayer", "createOrInitTemplatePreview -> bottom failed: " + currentPositionInfo);
                return;
            }
            RoundOutlineProvider roundOutlineProvider3 = new RoundOutlineProvider(lockScreenTransformerLayer.previewRadiusPixel);
            FrameLayout frameLayout10 = lockScreenTransformerLayer.bottomTemplateViewContainer;
            if (frameLayout10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
                frameLayout = null;
            } else {
                frameLayout = frameLayout10;
            }
            View view3 = lockScreenTransformerLayer.bottomTemplatePreviewView;
            Intrinsics.checkNotNull(view3);
            LockScreenTransformerLayerKt.addTemplatePreviewView$default(frameLayout, view3, roundOutlineProvider3, false, 8, null);
        }
    }

    public final void setUnlockAnimationStarted(boolean z) {
        this.isUnlockAnimationStarted = z;
    }

    public final void setUnlockEnterAnimationStarted(boolean z) {
        this.isUnlockEnterAnimationStarted = z;
    }

    public final void setViewAlpha(View view, float f) {
        if (view != null) {
            view.setAlpha(f);
        }
    }

    public final void setVisibility(View view, TransformerPositionInfo transformerPositionInfo) {
        view.setVisibility(transformerPositionInfo == null ? 8 : 0);
    }

    public final void setupExitTransformerInitialState(boolean z) {
        FrameLayout frameLayout;
        FrameLayout frameLayout2 = this.leftTemplateViewContainer;
        FrameLayout frameLayout3 = null;
        if (frameLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
            frameLayout2 = null;
        }
        if (frameLayout2.getVisibility() == 0) {
            FrameLayout frameLayout4 = this.leftTemplateViewContainer;
            if (frameLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
                frameLayout4 = null;
            }
            frameLayout4.setTranslationX(this.finalTranslateXForLeft);
            FrameLayout frameLayout5 = this.leftTemplateViewContainer;
            if (frameLayout5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
                frameLayout5 = null;
            }
            frameLayout5.setScaleX(this._neighboringInitialScale);
            FrameLayout frameLayout6 = this.leftTemplateViewContainer;
            if (frameLayout6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
                frameLayout6 = null;
            }
            frameLayout6.setScaleY(this._neighboringInitialScale);
        }
        FrameLayout frameLayout7 = this.rightTemplateViewContainer;
        if (frameLayout7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
            frameLayout7 = null;
        }
        if (frameLayout7.getVisibility() == 0) {
            FrameLayout frameLayout8 = this.rightTemplateViewContainer;
            if (frameLayout8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
                frameLayout8 = null;
            }
            frameLayout8.setTranslationX(this.finalTranslateXForRight);
            FrameLayout frameLayout9 = this.rightTemplateViewContainer;
            if (frameLayout9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
                frameLayout9 = null;
            }
            frameLayout9.setScaleX(this._neighboringInitialScale);
            FrameLayout frameLayout10 = this.rightTemplateViewContainer;
            if (frameLayout10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
                frameLayout10 = null;
            }
            frameLayout10.setScaleY(this._neighboringInitialScale);
        }
        FrameLayout frameLayout11 = this.bottomTemplateViewContainer;
        if (frameLayout11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
            frameLayout11 = null;
        }
        if (frameLayout11.getVisibility() == 0) {
            FrameLayout frameLayout12 = this.bottomTemplateViewContainer;
            if (frameLayout12 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
                frameLayout12 = null;
            }
            frameLayout12.setTranslationY(this.finalTranslateYForBottom);
            FrameLayout frameLayout13 = this.bottomTemplateViewContainer;
            if (frameLayout13 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
                frameLayout13 = null;
            }
            frameLayout13.setScaleX(1.0f);
            FrameLayout frameLayout14 = this.bottomTemplateViewContainer;
            if (frameLayout14 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
                frameLayout14 = null;
            }
            frameLayout14.setScaleY(1.0f);
        }
        this.applySuccess = z;
        if (z || this.myLockTemplatePreviewView == null) {
            return;
        }
        RoundOutlineProvider roundOutlineProvider = new RoundOutlineProvider(this.previewRadiusPixel);
        FrameLayout frameLayout15 = this.myTemplateViewContainer;
        if (frameLayout15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("myTemplateViewContainer");
            frameLayout = null;
        } else {
            frameLayout = frameLayout15;
        }
        View view = this.myLockTemplatePreviewView;
        Intrinsics.checkNotNull(view);
        LockScreenTransformerLayerKt.addTemplatePreviewView$default(frameLayout, view, roundOutlineProvider, false, 8, null);
        RoundOutlineProvider roundOutlineProvider2 = new RoundOutlineProvider(this.previewRadiusPixel);
        FrameLayout frameLayout16 = this.myTemplateViewContainer;
        if (frameLayout16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("myTemplateViewContainer");
            frameLayout16 = null;
        }
        View view2 = this.myHomeTemplatePreviewView;
        Intrinsics.checkNotNull(view2);
        LockScreenTransformerLayerKt.addTemplatePreviewView(frameLayout16, view2, roundOutlineProvider2, false);
        View view3 = this.myLockTemplatePreviewView;
        if (view3 != null && (view3 instanceof BaseTemplateView)) {
            Intrinsics.checkNotNull(view3, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.base.BaseTemplateView");
            BaseTemplateView baseTemplateView = (BaseTemplateView) view3;
            View clockLayer = baseTemplateView.getClockLayer();
            if (clockLayer != null) {
                clockLayer.setAlpha(0.0f);
            }
            View hierarchyLayer = baseTemplateView.getHierarchyLayer();
            if (hierarchyLayer != null) {
                hierarchyLayer.setAlpha(0.0f);
            }
        }
        FrameLayout frameLayout17 = this.myTemplateViewContainer;
        if (frameLayout17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("myTemplateViewContainer");
        } else {
            frameLayout3 = frameLayout17;
        }
        frameLayout3.setVisibility(0);
    }

    public final void setupTransformerInitialState() {
        TextView textView = this.tvTitle;
        FrameLayout frameLayout = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
            textView = null;
        }
        if (textView.getVisibility() == 0) {
            TextView textView2 = this.tvTitle;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
                textView2 = null;
            }
            textView2.setAlpha(0.0f);
        }
        TextView textView3 = this.tvDescription;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvDescription");
            textView3 = null;
        }
        if (textView3.getVisibility() == 0) {
            TextView textView4 = this.tvDescription;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvDescription");
                textView4 = null;
            }
            textView4.setAlpha(0.0f);
        }
        FrameLayout frameLayout2 = this.leftTemplateViewContainer;
        if (frameLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
            frameLayout2 = null;
        }
        if (frameLayout2.getVisibility() == 0) {
            FrameLayout frameLayout3 = this.leftTemplateViewContainer;
            if (frameLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
                frameLayout3 = null;
            }
            frameLayout3.setScaleX(this._neighboringInitialScale * 1.5f);
            FrameLayout frameLayout4 = this.leftTemplateViewContainer;
            if (frameLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
                frameLayout4 = null;
            }
            frameLayout4.setScaleY(this._neighboringInitialScale * 1.5f);
            FrameLayout frameLayout5 = this.leftTemplateViewContainer;
            if (frameLayout5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
                frameLayout5 = null;
            }
            frameLayout5.setTranslationX(0.0f);
        }
        FrameLayout frameLayout6 = this.rightTemplateViewContainer;
        if (frameLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
            frameLayout6 = null;
        }
        if (frameLayout6.getVisibility() == 0) {
            FrameLayout frameLayout7 = this.rightTemplateViewContainer;
            if (frameLayout7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
                frameLayout7 = null;
            }
            frameLayout7.setScaleX(this._neighboringInitialScale * 1.5f);
            FrameLayout frameLayout8 = this.rightTemplateViewContainer;
            if (frameLayout8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
                frameLayout8 = null;
            }
            frameLayout8.setScaleY(this._neighboringInitialScale * 1.5f);
            FrameLayout frameLayout9 = this.rightTemplateViewContainer;
            if (frameLayout9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
                frameLayout9 = null;
            }
            frameLayout9.setTranslationX(0.0f);
        }
        FrameLayout frameLayout10 = this.bottomTemplateViewContainer;
        if (frameLayout10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
            frameLayout10 = null;
        }
        if (frameLayout10.getVisibility() == 0) {
            FrameLayout frameLayout11 = this.bottomTemplateViewContainer;
            if (frameLayout11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
                frameLayout11 = null;
            }
            frameLayout11.setScaleX(1.5f);
            FrameLayout frameLayout12 = this.bottomTemplateViewContainer;
            if (frameLayout12 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
                frameLayout12 = null;
            }
            frameLayout12.setScaleY(1.5f);
            FrameLayout frameLayout13 = this.bottomTemplateViewContainer;
            if (frameLayout13 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
            } else {
                frameLayout = frameLayout13;
            }
            frameLayout.setTranslationY((this.containerHeight * 0.5f) / 2);
        }
        setViewsAlpha(0.0f);
        View view = this.currentLockTemplatePreviewView;
        if (view instanceof BaseTemplateView) {
            Intrinsics.checkNotNull(view, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.base.BaseTemplateView");
            View clockLayer = ((BaseTemplateView) view).getClockLayer();
            if (clockLayer != null) {
                clockLayer.setAlpha(0.01f);
            }
        }
    }

    public final void showTransFormerLayer() {
        removeCallbacks(this.hideTransformLayerRunnable);
        setVisibility(0);
    }

    public final void startApplyButtonAlphaAnim(float f, final float f2, long j, EaseManager.EaseStyle easeStyle) {
        WeakReference weakReference = this.applyButton;
        if ((weakReference != null ? (View) weakReference.get() : null) == null) {
            Log.w("Keyguard-Editor-LockScreenTransformerLayer", "startApplyButtonAlphaAnim: applyButton = null");
            return;
        }
        AnimState animState = new AnimState("alpha_from");
        ViewProperty viewProperty = ViewProperty.ALPHA;
        AnimState animStateAdd = animState.add(viewProperty, f, new long[0]);
        AnimState animStateAdd2 = new AnimState("alpha_to").add(viewProperty, f2, new long[0]);
        AnimConfig animConfigAddListeners = new AnimConfig().setDelay(j).setSpecial(viewProperty, easeStyle, new float[0]).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$startApplyButtonAlphaAnim$animConfig$1
            public final void completedScaleAnim(boolean z) {
                View view;
                Log.w("Keyguard-Editor-LockScreenTransformerLayer", "startApplyButtonAlphaAnim.Completed: isCanceled = " + z + "， toAlpha = " + f2);
                WeakReference weakReference2 = this.applyButton;
                if (weakReference2 == null || (view = (View) weakReference2.get()) == null) {
                    return;
                }
                view.setAlpha(f2);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                completedScaleAnim(true);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                completedScaleAnim(false);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                super.onUpdate(obj, collection);
            }
        });
        WeakReference weakReference2 = this.applyButton;
        Folme.useAt(weakReference2 != null ? (View) weakReference2.get() : null).state().setTo(animStateAdd).to(animStateAdd2, animConfigAddListeners);
    }

    public final void startBackupHandler() {
        this.isBackupInvalid = false;
        Job job = this.delayJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        this.delayJob = BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), Dispatchers.getMain(), null, new C01511(null), 2, null);
    }

    public final void startBottomPreviewTranslateInAnim() {
        AnimState animState = new AnimState("scale_from");
        ViewProperty viewProperty = ViewProperty.TRANSLATION_Y;
        AnimState animStateAdd = animState.add(viewProperty, (this.containerHeight * 0.5f) / 2, new long[0]);
        ViewProperty viewProperty2 = ViewProperty.SCALE_X;
        AnimState animStateAdd2 = animStateAdd.add(viewProperty2, 1.5f, new long[0]);
        ViewProperty viewProperty3 = ViewProperty.SCALE_Y;
        AnimState animStateAdd3 = animStateAdd2.add(viewProperty3, 1.5f, new long[0]);
        AnimState animStateAdd4 = new AnimState("scale_to").add(viewProperty, this.finalTranslateYForBottom, new long[0]).add(viewProperty2, 1.0f, new long[0]).add(viewProperty3, 1.0f, new long[0]);
        AnimConfig animConfigAddListeners = new AnimConfig().setDelay(140L).setSpecial(viewProperty, createTranslationYEaseStyle(), new float[0]).setSpecial(viewProperty2, createScaleEaseStyle(), new float[0]).setSpecial(viewProperty3, createScaleEaseStyle(), new float[0]).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$startBottomPreviewTranslateInAnim$animConfig$1
            public final void completedTranslateAnim() {
                FrameLayout frameLayout = this.this$0.bottomTemplateViewContainer;
                FrameLayout frameLayout2 = null;
                if (frameLayout == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
                    frameLayout = null;
                }
                frameLayout.setTranslationY(this.this$0.finalTranslateYForBottom);
                FrameLayout frameLayout3 = this.this$0.bottomTemplateViewContainer;
                if (frameLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
                    frameLayout3 = null;
                }
                frameLayout3.setScaleX(1.0f);
                FrameLayout frameLayout4 = this.this$0.bottomTemplateViewContainer;
                if (frameLayout4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
                } else {
                    frameLayout2 = frameLayout4;
                }
                frameLayout2.setScaleY(1.0f);
                this.this$0.onAllTransformerCompleted();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                completedTranslateAnim();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                completedTranslateAnim();
            }
        });
        FrameLayout frameLayout = this.bottomTemplateViewContainer;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
            frameLayout = null;
        }
        Folme.useAt(frameLayout).state().setTo(animStateAdd3).to(animStateAdd4, animConfigAddListeners);
    }

    public final void startBottomPreviewTranslateInAnim(float f, final float f2) {
        AnimState animState = new AnimState("scale_from");
        ViewProperty viewProperty = ViewProperty.TRANSLATION_Y;
        AnimState animStateAdd = animState.add(viewProperty, f, new long[0]);
        AnimState animStateAdd2 = new AnimState("scale_to").add(viewProperty, f2, new long[0]);
        AnimConfig animConfigAddListeners = new AnimConfig().setSpecial(viewProperty, createTranslationYEaseStyle(), new float[0]).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$startBottomPreviewTranslateInAnim$animConfig$2
            private final void completedTranslateAnim() {
                FrameLayout frameLayout = this.this$0.bottomTemplateViewContainer;
                if (frameLayout == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
                    frameLayout = null;
                }
                frameLayout.setTranslationY(f2);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                completedTranslateAnim();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                completedTranslateAnim();
            }
        });
        FrameLayout frameLayout = this.bottomTemplateViewContainer;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bottomTemplateViewContainer");
            frameLayout = null;
        }
        Folme.useAt(frameLayout).state().setTo(animStateAdd).to(animStateAdd2, animConfigAddListeners);
    }

    public final void startCancelButtonAlphaAnim(float f, final float f2, long j, EaseManager.EaseStyle easeStyle) {
        WeakReference weakReference = this.cancelButton;
        if ((weakReference != null ? (View) weakReference.get() : null) == null) {
            Log.w("Keyguard-Editor-LockScreenTransformerLayer", "startCancelButtonAlphaAnim: cancelButton = null.");
            return;
        }
        AnimState animState = new AnimState("alpha_from");
        ViewProperty viewProperty = ViewProperty.ALPHA;
        AnimState animStateAdd = animState.add(viewProperty, f, new long[0]);
        AnimState animStateAdd2 = new AnimState("alpha_to").add(viewProperty, f2, new long[0]);
        AnimConfig animConfigAddListeners = new AnimConfig().setDelay(j).setSpecial(viewProperty, easeStyle, new float[0]).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$startCancelButtonAlphaAnim$animConfig$1
            private final void completedScaleAnim(boolean z) {
                View view;
                Log.i("Keyguard-Editor-LockScreenTransformerLayer", "startCancelButtonAlphaAnim.Complete -> isCanceled: " + z + ", toAlpha: " + f2);
                WeakReference weakReference2 = this.cancelButton;
                if (weakReference2 == null || (view = (View) weakReference2.get()) == null) {
                    return;
                }
                view.setAlpha(f2);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                completedScaleAnim(true);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                completedScaleAnim(false);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                super.onUpdate(obj, collection);
            }
        });
        WeakReference weakReference2 = this.cancelButton;
        Folme.useAt(weakReference2 != null ? (View) weakReference2.get() : null).state().setTo(animStateAdd).to(animStateAdd2, animConfigAddListeners);
    }

    public final void startCurrentTemplatePreviewAnim(final long j) {
        FrameLayout frameLayout;
        View view = this.currentLockTemplatePreviewView;
        FrameLayout frameLayout2 = null;
        if (view instanceof BaseTemplateView) {
            Intrinsics.checkNotNull(view, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.base.BaseTemplateView");
            final BaseTemplateView baseTemplateView = (BaseTemplateView) view;
            final long jCurrentTimeMillis = System.currentTimeMillis();
            final long jMax = Math.max(0L, j - jCurrentTimeMillis);
            FrameLayout frameLayout3 = this.currentTemplateViewContainer;
            if (frameLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentTemplateViewContainer");
                frameLayout = null;
            } else {
                frameLayout = frameLayout3;
            }
            frameLayout.postDelayed(new Runnable() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    LockScreenTransformerLayer.startCurrentTemplatePreviewAnim$lambda$17(baseTemplateView, this, jMax, j, jCurrentTimeMillis);
                }
            }, jMax);
        }
        AnimState animState = new AnimState("scale_to");
        ViewProperty viewProperty = ViewProperty.ALPHA;
        AnimState animStateAdd = animState.add(viewProperty, 1.0f, new long[0]);
        AnimConfig special = new AnimConfig().setSpecial(viewProperty, createAlphaEaseStyle(), new float[0]);
        View view2 = this.currentLockTemplatePreviewView;
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isQ18() && view2 != null) {
            float[] fArrScreenRadii = deviceUtil.screenRadii(view2);
            float dimension = getContext().getResources().getDimension(R.dimen.kg_main_item_preview_radius) * RangesKt.coerceAtLeast(1.0f / view2.getScaleX(), 1.0f / view2.getScaleY());
            view2.setClipToOutline(true);
            view2.setOutlineProvider(new RoundOutlineProvider(fArrScreenRadii));
            startEnterRadiusAnimation(createTemplateRadiusAnimationListener(view2, dimension, true));
        }
        FrameLayout frameLayout4 = this.currentTemplateViewContainer;
        if (frameLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentTemplateViewContainer");
        } else {
            frameLayout2 = frameLayout4;
        }
        Folme.useAt(frameLayout2).state().to(animStateAdd, special);
    }

    public final void startCurrentTemplatePreviewAnim(final boolean z, final boolean z2, float f, Animator animator, float f2, final TransitionListener transitionListener) {
        if (animator != null) {
            animator.start();
        }
        Log.i("Keyguard-Editor-LockScreenTransformerLayer", "startCurrentTemplatePreviewAnim");
        AnimState animState = new AnimState("scale_to");
        ViewProperty viewProperty = ViewProperty.ALPHA;
        AnimState animStateAdd = animState.add(viewProperty, f, new long[0]);
        AnimConfig animConfigAddListeners = new AnimConfig().setSpecial(viewProperty, createCurrentTemplateAlphaEaseStyle(f2), new float[0]).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$startCurrentTemplatePreviewAnim$animConfig$1
            public final void completed(boolean z3) {
                if (!z) {
                    if (z2) {
                        this.onTransformerAnimationUnlockFinished(z3);
                    }
                } else {
                    this.onExitTransformerAnimationEnd();
                    Iterator it = this.transformerEventListener.iterator();
                    while (it.hasNext()) {
                        ((TransformerEventListener) it.next()).onTransformerFinished(4, z3);
                    }
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                completed(true);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                completed(false);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                super.onUpdate(obj, collection);
                TransitionListener transitionListener2 = transitionListener;
                if (transitionListener2 != null) {
                    transitionListener2.onUpdate(obj, collection);
                }
            }
        });
        FrameLayout frameLayout = this.currentTemplateViewContainer;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentTemplateViewContainer");
            frameLayout = null;
        }
        Folme.useAt(frameLayout).state().to(animStateAdd, animConfigAddListeners);
    }

    public final void startCustomButtonAlphaAnim(final float f, final float f2, long j, EaseManager.EaseStyle easeStyle) {
        AnimState animState = new AnimState("alpha_from");
        ViewProperty viewProperty = ViewProperty.ALPHA;
        AnimState animStateAdd = animState.add(viewProperty, f, new long[0]);
        AnimState animStateAdd2 = new AnimState("alpha_to").add(viewProperty, f2, new long[0]);
        AnimConfig animConfigAddListeners = new AnimConfig().setDelay(j).setSpecial(viewProperty, easeStyle, new float[0]).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$startCustomButtonAlphaAnim$animConfig$1
            public final void completedScaleAnim() {
                CustomButtonView customButtonView = this.this$0.tvCustom;
                if (customButtonView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvCustom");
                    customButtonView = null;
                }
                customButtonView.setAlpha(f2);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                super.onBegin(obj);
                CustomButtonView customButtonView = this.this$0.tvCustom;
                if (customButtonView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvCustom");
                    customButtonView = null;
                }
                customButtonView.setLayerType(2, null);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                completedScaleAnim();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                completedScaleAnim();
                CustomButtonView customButtonView = this.this$0.tvCustom;
                if (customButtonView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvCustom");
                    customButtonView = null;
                }
                customButtonView.setLayerType(0, null);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                super.onUpdate(obj, collection);
                if (collection == null || collection.isEmpty()) {
                    return;
                }
                float floatValue = UpdateInfo.findBy(collection, ViewProperty.ALPHA).getFloatValue();
                if (Float.isNaN(floatValue) || floatValue < f || floatValue > f2) {
                    return;
                }
                CustomButtonView customButtonView = this.this$0.tvCustom;
                if (customButtonView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvCustom");
                    customButtonView = null;
                }
                customButtonView.setAlpha(floatValue);
            }
        });
        CustomButtonView customButtonView = this.tvCustom;
        CustomButtonView customButtonView2 = null;
        if (customButtonView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvCustom");
            customButtonView = null;
        }
        customButtonView.setAlpha(f);
        CustomButtonView customButtonView3 = this.tvCustom;
        if (customButtonView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvCustom");
        } else {
            customButtonView2 = customButtonView3;
        }
        Folme.useAt(customButtonView2).state().setTo(animStateAdd).to(animStateAdd2, animConfigAddListeners);
    }

    public final void startDescriptionAlphaAnim(float f, final float f2, long j, EaseManager.EaseStyle easeStyle) {
        AnimState animState = new AnimState("alpha_from");
        ViewProperty viewProperty = ViewProperty.ALPHA;
        AnimState animStateAdd = animState.add(viewProperty, f, new long[0]);
        AnimState animStateAdd2 = new AnimState("alpha_to").add(viewProperty, f2, new long[0]);
        AnimConfig animConfigAddListeners = new AnimConfig().setDelay(j).setSpecial(viewProperty, easeStyle, new float[0]).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$startDescriptionAlphaAnim$animConfig$1
            private final void completedScaleAnim() {
                TextView textView = this.this$0.tvDescription;
                if (textView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvDescription");
                    textView = null;
                }
                textView.setAlpha(f2);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                completedScaleAnim();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                completedScaleAnim();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                super.onUpdate(obj, collection);
            }
        });
        TextView textView = this.tvDescription;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvDescription");
            textView = null;
        }
        Folme.useAt(textView).state().setTo(animStateAdd).to(animStateAdd2, animConfigAddListeners);
    }

    public final void startEnterRadiusAnimation(final TransitionListener transitionListener) {
        AnimState animState = new AnimState("alpha_to");
        ViewProperty viewProperty = ViewProperty.ALPHA;
        Folme.useValue("enterRadiusAnimation").setTo(viewProperty.getName(), Float.valueOf(0.0f)).to(animState.add(viewProperty, 1.0f, new long[0]), new AnimConfig().setSpecial(viewProperty, createAlphaEaseStyle(), new float[0]).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$startEnterRadiusAnimation$animConfig$1
            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                TransitionListener transitionListener2 = transitionListener;
                if (transitionListener2 != null) {
                    transitionListener2.onUpdate(obj, collection);
                }
            }
        }));
    }

    public final void startExitRadiusAnimation(final TransitionListener transitionListener, final boolean z) {
        AnimState animState = new AnimState("alpha_to");
        ViewProperty viewProperty = ViewProperty.ALPHA;
        Folme.useValue("radiusAnimation").setTo(viewProperty.getName(), Float.valueOf(1.0f)).to(animState.add(viewProperty, 0, new long[0]), new AnimConfig().setSpecial(viewProperty, createFrameDismissAlphaEaseStyle(), new float[0]).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$startExitRadiusAnimation$animConfig$1
            private final void completed(boolean z2) {
                this.onExitTransformerAnimationEnd();
                if (z) {
                    Iterator it = this.transformerEventListener.iterator();
                    while (it.hasNext()) {
                        ((TransformerEventListener) it.next()).onTransformerFinished(4, z2);
                    }
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                completed(true);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                completed(false);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                TransitionListener transitionListener2 = transitionListener;
                if (transitionListener2 != null) {
                    transitionListener2.onUpdate(obj, collection);
                }
            }
        }));
    }

    public final void startExitTransformerAnimation() {
        onExitTransformerAnimationBegin();
        Log.i("Keyguard-Editor-LockScreenTransformerLayer", "startExitTransformerAnimation, currentTemplateFromScaleX = " + this.currentTemplateFromScaleX + ", finalTranslationY = " + this.finalTranslationY);
        setAlpha(1.0f);
        post(new Runnable() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                LockScreenTransformerLayer.startExitTransformerAnimation$lambda$32(this.f$0);
            }
        });
    }

    public final void startExitTransformerAnimationWithShowBouncer() {
        onExitTransformerAnimationWithBouncerBegin();
        post(new Runnable() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                LockScreenTransformerLayer.startExitTransformerAnimationWithShowBouncer$lambda$28(this.f$0);
            }
        });
    }

    public final void startLeftPreviewTranslateInAnim() {
        AnimState animState = new AnimState("anim_from");
        ViewProperty viewProperty = ViewProperty.TRANSLATION_X;
        AnimState animStateAdd = animState.add(viewProperty, 0.0f, new long[0]);
        ViewProperty viewProperty2 = ViewProperty.SCALE_X;
        AnimState animStateAdd2 = animStateAdd.add(viewProperty2, this._neighboringInitialScale * 1.5f, new long[0]);
        ViewProperty viewProperty3 = ViewProperty.SCALE_Y;
        AnimState animStateAdd3 = animStateAdd2.add(viewProperty3, this._neighboringInitialScale * 1.5f, new long[0]);
        AnimState animStateAdd4 = new AnimState("anim_to").add(viewProperty, this.finalTranslateXForLeft, new long[0]).add(viewProperty2, this._neighboringInitialScale, new long[0]).add(viewProperty3, this._neighboringInitialScale, new long[0]);
        AnimConfig animConfigAddListeners = new AnimConfig().setDelay(120L).setSpecial(viewProperty, createTranslationXEaseStyle(), new float[0]).setSpecial(viewProperty2, createScaleEaseStyle(), new float[0]).setSpecial(viewProperty3, createScaleEaseStyle(), new float[0]).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$startLeftPreviewTranslateInAnim$animConfig$1
            private final void completedTranslateAnim() {
                FrameLayout frameLayout = this.this$0.leftTemplateViewContainer;
                FrameLayout frameLayout2 = null;
                if (frameLayout == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
                    frameLayout = null;
                }
                frameLayout.setTranslationX(this.this$0.finalTranslateXForLeft);
                FrameLayout frameLayout3 = this.this$0.leftTemplateViewContainer;
                if (frameLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
                    frameLayout3 = null;
                }
                frameLayout3.setScaleX(this.this$0._neighboringInitialScale);
                FrameLayout frameLayout4 = this.this$0.leftTemplateViewContainer;
                if (frameLayout4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
                } else {
                    frameLayout2 = frameLayout4;
                }
                frameLayout2.setScaleY(this.this$0._neighboringInitialScale);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                completedTranslateAnim();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                completedTranslateAnim();
            }
        });
        FrameLayout frameLayout = this.leftTemplateViewContainer;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
            frameLayout = null;
        }
        Folme.useAt(frameLayout).state().setTo(animStateAdd3).to(animStateAdd4, animConfigAddListeners);
    }

    public final void startLeftPreviewTranslateInAnim(float f, final float f2) {
        AnimState animState = new AnimState("anim_from");
        ViewProperty viewProperty = ViewProperty.TRANSLATION_X;
        AnimState animStateAdd = animState.add(viewProperty, f, new long[0]);
        AnimState animStateAdd2 = new AnimState("anim_to").add(viewProperty, f2, new long[0]);
        AnimConfig animConfigAddListeners = new AnimConfig().setSpecial(viewProperty, createTranslationXEaseStyle(), new float[0]).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$startLeftPreviewTranslateInAnim$animConfig$2
            private final void completedTranslateAnim() {
                FrameLayout frameLayout = this.this$0.leftTemplateViewContainer;
                if (frameLayout == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
                    frameLayout = null;
                }
                frameLayout.setTranslationX(f2);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                completedTranslateAnim();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                completedTranslateAnim();
            }
        });
        FrameLayout frameLayout = this.leftTemplateViewContainer;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("leftTemplateViewContainer");
            frameLayout = null;
        }
        Folme.useAt(frameLayout).state().setTo(animStateAdd).to(animStateAdd2, animConfigAddListeners);
    }

    public final void startMyTemplatePreviewExitAnim() {
        FrameLayout frameLayout = this.myTemplateViewContainer;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("myTemplateViewContainer");
            frameLayout = null;
        }
        Animator animatorCreateHideAboveWallpaperLayersAlphaAnimator = createHideAboveWallpaperLayersAlphaAnimator(frameLayout);
        if (animatorCreateHideAboveWallpaperLayersAlphaAnimator != null) {
            animatorCreateHideAboveWallpaperLayersAlphaAnimator.start();
        }
    }

    public final void startRightPreviewTranslateInAnim() {
        AnimState animState = new AnimState("anim_from");
        ViewProperty viewProperty = ViewProperty.TRANSLATION_X;
        AnimState animStateAdd = animState.add(viewProperty, 0.0f, new long[0]);
        ViewProperty viewProperty2 = ViewProperty.SCALE_X;
        AnimState animStateAdd2 = animStateAdd.add(viewProperty2, this._neighboringInitialScale * 1.5f, new long[0]);
        ViewProperty viewProperty3 = ViewProperty.SCALE_Y;
        AnimState animStateAdd3 = animStateAdd2.add(viewProperty3, this._neighboringInitialScale * 1.5f, new long[0]);
        AnimState animStateAdd4 = new AnimState("anim_to").add(viewProperty, this.finalTranslateXForRight, new long[0]).add(viewProperty2, this._neighboringInitialScale, new long[0]).add(viewProperty3, this._neighboringInitialScale, new long[0]);
        AnimConfig animConfigAddListeners = new AnimConfig().setDelay(120L).setSpecial(viewProperty, createTranslationXEaseStyle(), new float[0]).setSpecial(viewProperty2, createScaleEaseStyle(), new float[0]).setSpecial(viewProperty3, createScaleEaseStyle(), new float[0]).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$startRightPreviewTranslateInAnim$animConfig$1
            private final void completedTranslateAnim() {
                FrameLayout frameLayout = this.this$0.rightTemplateViewContainer;
                FrameLayout frameLayout2 = null;
                if (frameLayout == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
                    frameLayout = null;
                }
                frameLayout.setTranslationX(this.this$0.finalTranslateXForRight);
                FrameLayout frameLayout3 = this.this$0.rightTemplateViewContainer;
                if (frameLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
                    frameLayout3 = null;
                }
                frameLayout3.setScaleX(this.this$0._neighboringInitialScale);
                FrameLayout frameLayout4 = this.this$0.rightTemplateViewContainer;
                if (frameLayout4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
                } else {
                    frameLayout2 = frameLayout4;
                }
                frameLayout2.setScaleY(this.this$0._neighboringInitialScale);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                completedTranslateAnim();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                completedTranslateAnim();
            }
        });
        FrameLayout frameLayout = this.rightTemplateViewContainer;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
            frameLayout = null;
        }
        Folme.useAt(frameLayout).state().setTo(animStateAdd3).to(animStateAdd4, animConfigAddListeners);
    }

    public final void startRightPreviewTranslateInAnim(float f, final float f2) {
        AnimState animState = new AnimState("anim_from");
        ViewProperty viewProperty = ViewProperty.TRANSLATION_X;
        AnimState animStateAdd = animState.add(viewProperty, f, new long[0]);
        AnimState animStateAdd2 = new AnimState("anim_to").add(viewProperty, f2, new long[0]);
        AnimConfig animConfigAddListeners = new AnimConfig().setSpecial(viewProperty, createTranslationXEaseStyle(), new float[0]).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$startRightPreviewTranslateInAnim$animConfig$2
            private final void completedTranslateAnim() {
                FrameLayout frameLayout = this.this$0.rightTemplateViewContainer;
                if (frameLayout == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
                    frameLayout = null;
                }
                frameLayout.setTranslationX(f2);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                completedTranslateAnim();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                completedTranslateAnim();
            }
        });
        FrameLayout frameLayout = this.rightTemplateViewContainer;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rightTemplateViewContainer");
            frameLayout = null;
        }
        Folme.useAt(frameLayout).state().setTo(animStateAdd).to(animStateAdd2, animConfigAddListeners);
    }

    public final void startTitleAlphaAnim(float f, final float f2, long j, EaseManager.EaseStyle easeStyle) {
        AnimState animState = new AnimState("alpha_from");
        ViewProperty viewProperty = ViewProperty.ALPHA;
        AnimState animStateAdd = animState.add(viewProperty, f, new long[0]);
        AnimState animStateAdd2 = new AnimState("alpha_to").add(viewProperty, f2, new long[0]);
        AnimConfig animConfigAddListeners = new AnimConfig().setDelay(j).setSpecial(viewProperty, easeStyle, new float[0]).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$startTitleAlphaAnim$animConfig$1
            private final void completedScaleAnim() {
                TextView textView = this.this$0.tvTitle;
                if (textView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
                    textView = null;
                }
                textView.setAlpha(f2);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                completedScaleAnim();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                completedScaleAnim();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                super.onUpdate(obj, collection);
            }
        });
        TextView textView = this.tvTitle;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
            textView = null;
        }
        Folme.useAt(textView).state().setTo(animStateAdd).to(animStateAdd2, animConfigAddListeners);
    }

    public final void startTransformerAnimation(final long j, final long j2) {
        TransformerInfo transformerInfo = this.currentTransformerInfo;
        if ((transformerInfo != null ? transformerInfo.getCurrentPositionInfo() : null) == null) {
            Log.e("Keyguard-Editor-LockScreenTransformerLayer", "startTransformerAnimation skipped: currentTransformerInfo?.currentPositionInfo = null");
        } else {
            onTransformerAnimationBegin();
            post(new Runnable() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    LockScreenTransformerLayer.startTransformerAnimation$lambda$15(this.f$0, j2, j);
                }
            });
        }
    }

    public final void startTransformerAnimationUnlock() {
        TransformerInfo transformerInfo = this.currentTransformerInfo;
        if ((transformerInfo != null ? transformerInfo.getCurrentPositionInfo() : null) == null) {
            Log.w("Keyguard-Editor-LockScreenTransformerLayer", "startTransformerAnimationUnlock failed: currentTransformerInfo?.currentPositionInfo = null");
        } else {
            onTransformerAnimationUnlockBegin();
            post(new Runnable() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    LockScreenTransformerLayer.startTransformerAnimationUnlock$lambda$29(this.f$0);
                }
            });
        }
    }

    public final void startTransformerAnimationWithShowBouncer(final long j) {
        onTransformerAnimationWithBouncerBegin();
        post(new Runnable() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                LockScreenTransformerLayer.startTransformerAnimationWithShowBouncer$lambda$27(this.f$0, j);
            }
        });
    }

    public final boolean waitCurrentWallpaperFirstFrameReady(final Function0 callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        View view = this.currentLockTemplatePreviewView;
        BaseTemplateView baseTemplateView = view instanceof BaseTemplateView ? (BaseTemplateView) view : null;
        if (baseTemplateView == null) {
            return false;
        }
        baseTemplateView.setOnceWallpaperReadyListener(new Function0() { // from class: com.miui.keyguard.editor.view.LockScreenTransformerLayer$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return LockScreenTransformerLayer.waitCurrentWallpaperFirstFrameReady$lambda$2(callback);
            }
        });
        return true;
    }
}
