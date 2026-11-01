package com.miui.keyguard.editor.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.PresetTemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.bean.TemplateItemConfig;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.homepage.view.PageTransitionLayer;
import com.miui.keyguard.editor.homepage.view.adapter.PreviewHolder;
import com.miui.keyguard.editor.homepage.view.adapter.TemplatePreviewViewHolder;
import com.miui.keyguard.editor.utils.BitmapUtil;
import com.miui.keyguard.editor.utils.DeviceScreenshotHelper;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.UpdateTransitionListener;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.utils.task.SingleTask;
import com.miui.keyguard.editor.utils.task.Task;
import com.miui.keyguard.editor.view.RoundOutlineProvider;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;

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
/* JADX INFO: compiled from: Transition.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public class TransitionPerformerImpl implements TransitionPerformer {
    public AtomicInteger forwardStatus;
    public Consumer forwardTransitionConsumerRef;
    public final Handler handler;
    public OutlineElement homeTransitionElement;
    public volatile boolean isForwardTransition;
    public OutlineElement lockTransitionElement;
    public PreviewHolder previewHolder;
    public volatile int slideId;
    public View[] slideView;
    public final PageTransitionLayer transitionContainer;
    public TransitionHost transitionHost;
    public final TransitionLayer transitionLayer;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.base.TransitionPerformerImpl$prepareForwardTransition$2, reason: invalid class name */
    /* JADX INFO: compiled from: Transition.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.base.TransitionPerformerImpl$prepareForwardTransition$2", f = "Transition.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass2 extends SuspendLambda implements Function4<Bitmap, Integer, Bitmap, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Rect $homePreviewRect;
        public final /* synthetic */ Rect $lockPreviewRect;
        public final /* synthetic */ PreviewHolder $previewHolder;
        public final /* synthetic */ long $screenshotStartTime;
        public final /* synthetic */ View $shareHomeElement;
        public /* synthetic */ Object L$0;
        public /* synthetic */ Object L$1;
        public int label;
        public final /* synthetic */ TransitionPerformerImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(long j, View view, TransitionPerformerImpl transitionPerformerImpl, Rect rect, Rect rect2, PreviewHolder previewHolder, Continuation continuation) {
            super(4, continuation);
            this.$screenshotStartTime = j;
            this.$shareHomeElement = view;
            this.this$0 = transitionPerformerImpl;
            this.$lockPreviewRect = rect;
            this.$homePreviewRect = rect2;
            this.$previewHolder = previewHolder;
        }

        public static final Pair invokeSuspend$lambda$0(PreviewHolder previewHolder) {
            TemplateApi.Companion companion = TemplateApi.Companion;
            Context context = previewHolder.getItemView().getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            TemplateApi companion2 = companion.getInstance(context);
            return new Pair(companion2.getCurrentLockScreenShot(), companion2.getCurrentHomeScreenShot());
        }

        public static final void invokeSuspend$lambda$1(TransitionPerformerImpl transitionPerformerImpl, Rect rect, Rect rect2, Pair pair, Throwable th) {
            Log.d("Keyguard-Editor-Transition", "fallback result: lock=" + (pair.getFirst() != null) + ", home=" + (pair.getSecond() != null));
            TransitionPerformerImpl.onTransitionBitmapReady$default(transitionPerformerImpl, (Bitmap) pair.getFirst(), (Bitmap) pair.getSecond(), rect, rect2, null, 16, null);
        }

        public final Object invoke(Bitmap bitmap, int i, Bitmap bitmap2, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$screenshotStartTime, this.$shareHomeElement, this.this$0, this.$lockPreviewRect, this.$homePreviewRect, this.$previewHolder, continuation);
            anonymousClass2.L$0 = bitmap;
            anonymousClass2.L$1 = bitmap2;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.jvm.functions.Function4
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            return invoke((Bitmap) obj, ((Number) obj2).intValue(), (Bitmap) obj3, (Continuation) obj4);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Bitmap bitmap = (Bitmap) this.L$0;
            Bitmap bitmap2 = (Bitmap) this.L$1;
            Log.d("Keyguard-Editor-Transition", "takeScreenshot completed, duration=" + (System.currentTimeMillis() - this.$screenshotStartTime) + "ms, result=" + (bitmap != null));
            DeviceScreenshotHelper.INSTANCE.setCurrentHomeTemplateWeak(null);
            View view = this.$shareHomeElement;
            BaseTemplateView baseTemplateView = view instanceof BaseTemplateView ? (BaseTemplateView) view : null;
            if (baseTemplateView != null) {
                baseTemplateView.setEditTransition(false);
            }
            if (bitmap != null) {
                TransitionPerformerImpl.onTransitionBitmapReady$default(this.this$0, bitmap, bitmap2, this.$lockPreviewRect, this.$homePreviewRect, null, 16, null);
            } else {
                Log.d("Keyguard-Editor-Transition", "takeScreenshot failed, fallback to disk cache");
                final PreviewHolder previewHolder = this.$previewHolder;
                SingleTask singleTaskCreate = Task.create(new Supplier() { // from class: com.miui.keyguard.editor.base.TransitionPerformerImpl$prepareForwardTransition$2$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return TransitionPerformerImpl.AnonymousClass2.invokeSuspend$lambda$0(previewHolder);
                    }
                });
                final TransitionPerformerImpl transitionPerformerImpl = this.this$0;
                final Rect rect = this.$lockPreviewRect;
                final Rect rect2 = this.$homePreviewRect;
                singleTaskCreate.post(new BiConsumer() { // from class: com.miui.keyguard.editor.base.TransitionPerformerImpl$prepareForwardTransition$2$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj2, Object obj3) {
                        TransitionPerformerImpl.AnonymousClass2.invokeSuspend$lambda$1(transitionPerformerImpl, rect, rect2, (Pair) obj2, (Throwable) obj3);
                    }
                });
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.base.TransitionPerformerImpl$prepareForwardTransition$3, reason: invalid class name */
    /* JADX INFO: compiled from: Transition.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.base.TransitionPerformerImpl$prepareForwardTransition$3", f = "Transition.kt", l = {255}, m = "invokeSuspend")
    public static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Rect $homePreviewRect;
        public final /* synthetic */ Rect $lockPreviewRect;
        public final /* synthetic */ TemplatePreviewViewHolder $preview;
        public final /* synthetic */ PreviewHolder $previewHolder;
        public Object L$0;
        public Object L$1;
        public Object L$2;
        public Object L$3;
        public Object L$4;
        public Object L$5;
        public int label;
        public final /* synthetic */ TransitionPerformerImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(TemplatePreviewViewHolder templatePreviewViewHolder, TransitionPerformerImpl transitionPerformerImpl, PreviewHolder previewHolder, Rect rect, Rect rect2, Continuation continuation) {
            super(2, continuation);
            this.$preview = templatePreviewViewHolder;
            this.this$0 = transitionPerformerImpl;
            this.$previewHolder = previewHolder;
            this.$lockPreviewRect = rect;
            this.$homePreviewRect = rect2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.$preview, this.this$0, this.$previewHolder, this.$lockPreviewRect, this.$homePreviewRect, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:43:0x00cd  */
        /* JADX WARN: Code duplicated, block: B:46:0x00d2 A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:47:0x00d4  */
        /* JADX WARN: Code duplicated, block: B:49:0x00f5 A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:50:0x00f7  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            PreviewHolder previewHolder;
            Rect rect;
            TemplateItemBean templateItemBean;
            Bitmap screenshotImage;
            Bitmap bitmap;
            TransitionPerformerImpl transitionPerformerImpl;
            TemplateItemBean templateItemBean2;
            Rect rect2;
            Bitmap bitmap2;
            Rect rect3;
            Rect rect4;
            TransitionPerformerImpl transitionPerformerImpl2;
            Drawable foreground;
            PresetTemplateConfig preset;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                TemplatePreviewViewHolder templatePreviewViewHolder = this.$preview;
                TransitionPerformerImpl transitionPerformerImpl3 = this.this$0;
                previewHolder = this.$previewHolder;
                rect = this.$lockPreviewRect;
                Rect rect5 = this.$homePreviewRect;
                templateItemBean = (TemplateItemBean) templatePreviewViewHolder.getItemData();
                Integer numBoxInt = templateItemBean != null ? Boxing.boxInt(templateItemBean.getItemType()) : null;
                if (numBoxInt != null && numBoxInt.intValue() == 2) {
                    TemplateHistoryConfig history = templateItemBean.getHistory();
                    Bitmap homeScreenshot = history != null ? history.getHomeScreenshot() : null;
                    TemplateHistoryConfig history2 = templateItemBean.getHistory();
                    if (history2 != null) {
                        bitmap = homeScreenshot;
                        screenshotImage = history2.getScreenshotImage();
                    } else {
                        bitmap = homeScreenshot;
                        screenshotImage = null;
                    }
                } else if (numBoxInt != null && numBoxInt.intValue() == 3) {
                    PresetTemplateConfig preset2 = templateItemBean.getPreset();
                    Bitmap previewImage = preset2 != null ? preset2.getPreviewImage() : null;
                    CoroutineDispatcher io2 = Dispatchers.getIO();
                    TransitionPerformerImpl$prepareForwardTransition$3$1$lockBitmap$1 transitionPerformerImpl$prepareForwardTransition$3$1$lockBitmap$1 = new TransitionPerformerImpl$prepareForwardTransition$3$1$lockBitmap$1(templateItemBean, previewImage, null);
                    this.L$0 = transitionPerformerImpl3;
                    this.L$1 = previewHolder;
                    this.L$2 = rect;
                    this.L$3 = rect5;
                    this.L$4 = templateItemBean;
                    this.L$5 = previewImage;
                    this.label = 1;
                    Object objWithContext = BuildersKt.withContext(io2, transitionPerformerImpl$prepareForwardTransition$3$1$lockBitmap$1, this);
                    if (objWithContext == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    obj = objWithContext;
                    transitionPerformerImpl = transitionPerformerImpl3;
                    templateItemBean2 = templateItemBean;
                    rect2 = rect5;
                    bitmap2 = previewImage;
                } else {
                    screenshotImage = null;
                    bitmap = null;
                }
                rect3 = rect;
                rect4 = rect5;
                transitionPerformerImpl2 = transitionPerformerImpl3;
                if (templateItemBean != null || (preset = templateItemBean.getPreset()) == null) {
                    foreground = null;
                } else {
                    foreground = preset.getForeground();
                }
                if (screenshotImage == null) {
                    Log.i("Keyguard-Editor-Transition", "use screen shot:" + (templateItemBean != null ? Boxing.boxInt(templateItemBean.getItemType()) : null));
                    transitionPerformerImpl2.takePreviewScreenShot(previewHolder, rect3, rect4);
                } else {
                    Log.i("Keyguard-Editor-Transition", "use cache:" + (templateItemBean != null ? Boxing.boxInt(templateItemBean.getItemType()) : null));
                    transitionPerformerImpl2.onTransitionBitmapReady(screenshotImage, bitmap, rect3, rect4, foreground);
                }
                return Unit.INSTANCE;
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            bitmap2 = (Bitmap) this.L$5;
            templateItemBean2 = (TemplateItemBean) this.L$4;
            rect2 = (Rect) this.L$3;
            rect = (Rect) this.L$2;
            previewHolder = (PreviewHolder) this.L$1;
            transitionPerformerImpl = (TransitionPerformerImpl) this.L$0;
            ResultKt.throwOnFailure(obj);
            transitionPerformerImpl2 = transitionPerformerImpl;
            bitmap = (Bitmap) obj;
            screenshotImage = bitmap2;
            templateItemBean = templateItemBean2;
            rect4 = rect2;
            rect3 = rect;
            if (templateItemBean != null) {
                foreground = null;
            } else {
                foreground = null;
            }
            if (screenshotImage == null) {
                if (templateItemBean != null) {
                }
                Log.i("Keyguard-Editor-Transition", "use screen shot:" + (templateItemBean != null ? Boxing.boxInt(templateItemBean.getItemType()) : null));
                transitionPerformerImpl2.takePreviewScreenShot(previewHolder, rect3, rect4);
            } else {
                if (templateItemBean != null) {
                }
                Log.i("Keyguard-Editor-Transition", "use cache:" + (templateItemBean != null ? Boxing.boxInt(templateItemBean.getItemType()) : null));
                transitionPerformerImpl2.onTransitionBitmapReady(screenshotImage, bitmap, rect3, rect4, foreground);
            }
            return Unit.INSTANCE;
        }
    }

    public TransitionPerformerImpl(TransitionLayer transitionLayer) {
        Intrinsics.checkNotNullParameter(transitionLayer, "transitionLayer");
        this.transitionLayer = transitionLayer;
        this.transitionContainer = transitionLayer.getTransitionContainer();
        this.forwardStatus = new AtomicInteger(TransitionPerformer.Companion.getFORWARD_FREE());
        this.handler = new Handler(Looper.getMainLooper());
    }

    public static /* synthetic */ void onTransitionBitmapReady$default(TransitionPerformerImpl transitionPerformerImpl, Bitmap bitmap, Bitmap bitmap2, Rect rect, Rect rect2, Drawable drawable, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onTransitionBitmapReady");
        }
        if ((i & 16) != 0) {
            drawable = null;
        }
        transitionPerformerImpl.onTransitionBitmapReady(bitmap, bitmap2, rect, rect2, drawable);
    }

    public static final void performBackwardTransition$lambda$14(final TransitionPerformerImpl transitionPerformerImpl, final BaseTemplateView baseTemplateView, final BaseTemplateView baseTemplateView2, final Function0 function0) {
        Log.d("Keyguard-Editor-Transition", "performBackwardTransition launch runnable " + (transitionPerformerImpl.lockTransitionElement != null));
        OutlineElement outlineElement = transitionPerformerImpl.lockTransitionElement;
        if (outlineElement != null) {
            TransitionHost transitionHost = transitionPerformerImpl.transitionHost;
            Intrinsics.checkNotNull(transitionHost);
            Rect lockPreviewRect = transitionHost.getLockPreviewRect();
            TransitionHost transitionHost2 = transitionPerformerImpl.transitionHost;
            Intrinsics.checkNotNull(transitionHost2);
            Rect homePreviewRect = transitionHost2.getHomePreviewRect();
            if (homePreviewRect == null) {
                homePreviewRect = new Rect(lockPreviewRect);
                Context context = baseTemplateView.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                Matrix presetHomeMatrix = transitionPerformerImpl.getPresetHomeMatrix(context, lockPreviewRect);
                RectF rectF = new RectF();
                presetHomeMatrix.mapRect(rectF, new RectF(homePreviewRect));
                Rect rect = new Rect();
                rectF.roundOut(rect);
                homePreviewRect.set(rect);
            }
            Rect rect2 = homePreviewRect;
            Rect rect3 = new Rect();
            Rect rect4 = new Rect();
            ViewUtil viewUtil = ViewUtil.INSTANCE;
            viewUtil.getShowRect(baseTemplateView, rect3);
            viewUtil.getShowRect(baseTemplateView2, rect4);
            float fWidth = lockPreviewRect.width() / baseTemplateView.getWidth();
            float fHeight = lockPreviewRect.height() / baseTemplateView.getHeight();
            float fWidth2 = rect2.width() / baseTemplateView2.getWidth();
            float fHeight2 = rect2.height() / baseTemplateView2.getHeight();
            outlineElement.reset(transitionPerformerImpl.transitionContainer, rect3, lockPreviewRect);
            outlineElement.reset(transitionPerformerImpl.transitionContainer, rect4, rect2);
            Log.d("Keyguard-Editor-Transition", "lockTransitionElement  " + (transitionPerformerImpl.transitionHost != null));
            TransitionHost transitionHost3 = transitionPerformerImpl.transitionHost;
            if (transitionHost3 != null) {
                transitionHost3.performBackwardTransition();
            }
            final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
            final Runnable runnable = new Runnable() { // from class: com.miui.keyguard.editor.base.TransitionPerformerImpl$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    TransitionPerformerImpl.performBackwardTransition$lambda$14$lambda$13$lambda$7(booleanRef, transitionPerformerImpl, baseTemplateView, baseTemplateView2, function0);
                }
            };
            EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
            Context context2 = baseTemplateView.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            final Job jobLaunch$default = BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context2), Dispatchers.getMain(), null, new TransitionPerformerImpl$performBackwardTransition$1$1$1(runnable, null), 2, null);
            Log.i("Keyguard-Editor-Transition", "backward transition start");
            baseTemplateView.onBackwardTransitionStart();
            baseTemplateView2.onBackwardTransitionStart();
            IStateStyle upVar = Folme.use((View) baseTemplateView).state().setup("performBackwardTransition.shareElement");
            ViewProperty viewProperty = ViewProperty.SCALE_X;
            IStateStyle iStateStyleAdd = upVar.add(viewProperty, lockPreviewRect.width() / baseTemplateView.getWidth());
            ViewProperty viewProperty2 = ViewProperty.SCALE_Y;
            IStateStyle iStateStyleAdd2 = iStateStyleAdd.add(viewProperty2, fHeight);
            ViewProperty viewProperty3 = ViewProperty.TRANSLATION_Y;
            IStateStyle iStateStyleAdd3 = iStateStyleAdd2.add((FloatProperty) viewProperty3, lockPreviewRect.centerY() - rect3.centerY());
            ViewProperty viewProperty4 = ViewProperty.TRANSLATION_X;
            iStateStyleAdd3.add((FloatProperty) viewProperty4, lockPreviewRect.centerX() - rect3.centerX()).to("performBackwardTransition.shareElement", TransitionKt.getDefaultConfig().addListeners(new UpdateTransitionListener(new Function0() { // from class: com.miui.keyguard.editor.base.TransitionPerformerImpl$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return TransitionPerformerImpl.performBackwardTransition$lambda$14$lambda$13$lambda$8(jobLaunch$default, runnable);
                }
            }, null, 2, null)));
            Folme.use((View) baseTemplateView2).state().setup("performBackwardTransition.shareElement").add(viewProperty, rect2.width() / baseTemplateView2.getWidth()).add(viewProperty2, fHeight2).add((FloatProperty) viewProperty3, rect2.centerY() - rect4.centerY()).add((FloatProperty) viewProperty4, rect2.centerX() - rect4.centerX()).add(ViewProperty.ALPHA, baseTemplateView2.getResources().getFloat(R.dimen.kg_corss_home_alpha)).to("performBackwardTransition.shareElement", TransitionKt.getDefaultConfig());
            Folme.useAt(outlineElement).state().setup("performBackwardTransition.transitionElement").add((FloatProperty) viewProperty, 1.0f).add((FloatProperty) viewProperty2, 1.0f).add((FloatProperty) viewProperty4, 0).add((FloatProperty) viewProperty3, 0).to("performBackwardTransition.transitionElement", TransitionKt.getDefaultConfig());
            ViewOutlineProvider outlineProvider = baseTemplateView.getOutlineProvider();
            Intrinsics.checkNotNull(outlineProvider, "null cannot be cast to non-null type com.miui.keyguard.editor.view.RoundOutlineProvider");
            float radius = ((RoundOutlineProvider) outlineProvider).getRadius();
            ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
            Context context3 = outlineElement.getContext();
            Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
            final float fProvidePreviewRadius = resourcesProvider.providePreviewRadius(context3) / fWidth;
            Context context4 = outlineElement.getContext();
            Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
            final float fProvidePreviewRadius2 = resourcesProvider.providePreviewRadius(context4) / fWidth2;
            ViewOutlineProvider outlineProvider2 = baseTemplateView.getOutlineProvider();
            Intrinsics.checkNotNull(outlineProvider2, "null cannot be cast to non-null type com.miui.keyguard.editor.view.RoundOutlineProvider");
            final RoundOutlineProvider roundOutlineProvider = (RoundOutlineProvider) outlineProvider2;
            ViewOutlineProvider outlineProvider3 = baseTemplateView2.getOutlineProvider();
            final RoundOutlineProvider roundOutlineProvider2 = outlineProvider3 instanceof RoundOutlineProvider ? (RoundOutlineProvider) outlineProvider3 : null;
            Folme.useValue(new Object[0]).setTo("lockRadius", Float.valueOf(radius)).to("lockRadius", Float.valueOf(fProvidePreviewRadius), TransitionKt.getAnimConfig(0.25f).addListeners(new UpdateTransitionListener(new Function0() { // from class: com.miui.keyguard.editor.base.TransitionPerformerImpl$$ExternalSyntheticLambda6
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return TransitionPerformerImpl.performBackwardTransition$lambda$14$lambda$13$lambda$9(roundOutlineProvider, fProvidePreviewRadius, baseTemplateView);
                }
            }, new Function2() { // from class: com.miui.keyguard.editor.base.TransitionPerformerImpl$$ExternalSyntheticLambda7
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return TransitionPerformerImpl.performBackwardTransition$lambda$14$lambda$13$lambda$10(roundOutlineProvider, baseTemplateView, obj, (Collection) obj2);
                }
            })));
            Folme.useValue(new Object[0]).setTo("homeRadius", Float.valueOf(radius)).to("homeRadius", Float.valueOf(fProvidePreviewRadius2), TransitionKt.getAnimConfig(0.25f).addListeners(new UpdateTransitionListener(new Function0() { // from class: com.miui.keyguard.editor.base.TransitionPerformerImpl$$ExternalSyntheticLambda8
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return TransitionPerformerImpl.performBackwardTransition$lambda$14$lambda$13$lambda$11(roundOutlineProvider2, fProvidePreviewRadius2, baseTemplateView2);
                }
            }, new Function2() { // from class: com.miui.keyguard.editor.base.TransitionPerformerImpl$$ExternalSyntheticLambda9
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return TransitionPerformerImpl.performBackwardTransition$lambda$14$lambda$13$lambda$12(roundOutlineProvider2, baseTemplateView2, obj, (Collection) obj2);
                }
            })));
        }
    }

    public static final Unit performBackwardTransition$lambda$14$lambda$13$lambda$10(RoundOutlineProvider roundOutlineProvider, BaseTemplateView baseTemplateView, Object obj, Collection collection) {
        roundOutlineProvider.setRadius(UpdateInfo.findByName(collection, "lockRadius").getFloatValue());
        baseTemplateView.invalidateOutline();
        return Unit.INSTANCE;
    }

    public static final Unit performBackwardTransition$lambda$14$lambda$13$lambda$11(RoundOutlineProvider roundOutlineProvider, float f, BaseTemplateView baseTemplateView) {
        if (roundOutlineProvider != null) {
            roundOutlineProvider.setRadius(f);
        }
        baseTemplateView.invalidateOutline();
        return Unit.INSTANCE;
    }

    public static final Unit performBackwardTransition$lambda$14$lambda$13$lambda$12(RoundOutlineProvider roundOutlineProvider, BaseTemplateView baseTemplateView, Object obj, Collection collection) {
        float floatValue = UpdateInfo.findByName(collection, "homeRadius").getFloatValue();
        if (roundOutlineProvider != null) {
            roundOutlineProvider.setRadius(floatValue);
        }
        baseTemplateView.invalidateOutline();
        return Unit.INSTANCE;
    }

    public static final void performBackwardTransition$lambda$14$lambda$13$lambda$7(Ref.BooleanRef booleanRef, final TransitionPerformerImpl transitionPerformerImpl, BaseTemplateView baseTemplateView, BaseTemplateView baseTemplateView2, final Function0 function0) {
        if (booleanRef.element) {
            return;
        }
        booleanRef.element = true;
        if (transitionPerformerImpl.transitionContainer.getVisibility() == 8) {
            Log.d("Keyguard-Editor-Transition", "transitionContainer is gone");
            return;
        }
        baseTemplateView.setVisibility(8);
        baseTemplateView2.setVisibility(8);
        TransitionHost transitionHost = transitionPerformerImpl.transitionHost;
        if (transitionHost != null) {
            transitionHost.onBackwardTransitionEnd();
        }
        transitionPerformerImpl.transitionLayer.onBeforeTransitionGone(false);
        transitionPerformerImpl.transitionLayer.onTransitionComplete(true);
        baseTemplateView.post(new Runnable() { // from class: com.miui.keyguard.editor.base.TransitionPerformerImpl$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                function0.invoke();
            }
        });
        if (DeviceUtil.INSTANCE.isLiteDevice() || transitionPerformerImpl.previewHolder == null) {
            return;
        }
        AnimState animState = new AnimState("alpha_from");
        ViewProperty viewProperty = ViewProperty.ALPHA;
        AnimState animStateAdd = animState.add(viewProperty, 0.0f, new long[0]);
        AnimState animStateAdd2 = new AnimState("alpha_to").add(viewProperty, 1.0f, new long[0]);
        EaseManager.EaseStyle style = EaseManager.getStyle(-2, 0.95f, 0.3f);
        Intrinsics.checkNotNullExpressionValue(style, "getStyle(...)");
        AnimConfig animConfigAddListeners = new AnimConfig().setDelay(200L).setSpecial(viewProperty, style, new float[0]).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.base.TransitionPerformerImpl$performBackwardTransition$1$1$transitionComplete$1$animConfig$1
            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                View customButton;
                PreviewHolder previewHolder = this.this$0.previewHolder;
                if (previewHolder == null || (customButton = previewHolder.getCustomButton()) == null) {
                    return;
                }
                customButton.setAlpha(1.0f);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                View customButton;
                PreviewHolder previewHolder = this.this$0.previewHolder;
                if (previewHolder == null || (customButton = previewHolder.getCustomButton()) == null) {
                    return;
                }
                customButton.setAlpha(1.0f);
            }
        });
        PreviewHolder previewHolder = transitionPerformerImpl.previewHolder;
        Folme.useAt(previewHolder != null ? previewHolder.getCustomButton() : null).state().setTo(animStateAdd).to(animStateAdd2, animConfigAddListeners);
    }

    public static final Unit performBackwardTransition$lambda$14$lambda$13$lambda$8(Job job, Runnable runnable) {
        Log.i("Keyguard-Editor-Transition", "backward transition end");
        Job.DefaultImpls.cancel$default(job, null, 1, null);
        runnable.run();
        return Unit.INSTANCE;
    }

    public static final Unit performBackwardTransition$lambda$14$lambda$13$lambda$9(RoundOutlineProvider roundOutlineProvider, float f, BaseTemplateView baseTemplateView) {
        roundOutlineProvider.setRadius(f);
        baseTemplateView.invalidateOutline();
        return Unit.INSTANCE;
    }

    public static final void performForwardTransition$lambda$3(ForwardTransitionConsumer forwardTransitionConsumer, TransitionPerformerImpl transitionPerformerImpl, OutlineElement outlineElement) {
        forwardTransitionConsumer.accept(outlineElement);
        TransitionHost transitionHost = transitionPerformerImpl.transitionHost;
        if (transitionHost != null) {
            transitionHost.performForwardTransition(transitionPerformerImpl);
        }
    }

    public static final Pair takePreviewScreenShot$lambda$1(PreviewHolder previewHolder, TemplateItemBean templateItemBean) {
        TemplateItemConfig bindItemConfig;
        Bitmap bitmapDrawToBitmap = ViewUtil.INSTANCE.drawToBitmap(previewHolder.getLockPreview());
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = previewHolder.getLockPreview().getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        int screenStatus = deviceUtil.getScreenStatus(context);
        BitmapUtil bitmapUtil = BitmapUtil.INSTANCE;
        String wallpaperPath = null;
        if (templateItemBean.getItemType() == 2) {
            TemplateHistoryConfig history = templateItemBean.getHistory();
            if (history != null) {
                wallpaperPath = history.getCurrentWallpaperScreenshotUri(screenStatus);
            }
        } else {
            PresetTemplateConfig preset = templateItemBean.getPreset();
            if (preset != null && (bindItemConfig = preset.getBindItemConfig()) != null) {
                wallpaperPath = bindItemConfig.getWallpaperPath();
            }
        }
        return new Pair(bitmapDrawToBitmap, BitmapUtil.fromFile$default(bitmapUtil, wallpaperPath, 0, 0, null, 14, null));
    }

    public static final void takePreviewScreenShot$lambda$2(TransitionPerformerImpl transitionPerformerImpl, Rect rect, Rect rect2, Pair pair) {
        onTransitionBitmapReady$default(transitionPerformerImpl, (Bitmap) pair.getFirst(), (Bitmap) pair.getSecond(), rect, rect2, null, 16, null);
    }

    public final AtomicInteger getForwardStatus() {
        return this.forwardStatus;
    }

    public final Matrix getPresetHomeMatrix(Context context, Rect rect) {
        float fProvideHomeScale = ResourcesProvider.INSTANCE.provideHomeScale(context);
        Matrix matrix = new Matrix();
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.kg_main_item_container_padding);
        matrix.postScale(fProvideHomeScale, fProvideHomeScale, rect.width() / 2.0f, rect.height() / 2.0f);
        float f = dimensionPixelSize;
        matrix.postTranslate(f, f);
        return matrix;
    }

    public final boolean isForwardTransition() {
        return this.isForwardTransition;
    }

    @Override // com.miui.keyguard.editor.base.TransitionPerformer
    public boolean onConfigChanged() {
        Folme.clean(this.lockTransitionElement);
        return this.forwardStatus.get() != TransitionPerformer.Companion.getFORWARD_FREE();
    }

    public final void onTransitionBitmapReady(Bitmap bitmap, Bitmap bitmap2, Rect rect, Rect rect2, Drawable drawable) {
        prepareTransition(bitmap, bitmap2, rect, rect2, drawable);
        Log.i("Keyguard-Editor-Transition", "drawToBitmap end forwardTransitionConsumerRef = " + this.forwardTransitionConsumerRef + " lockBitmap=" + bitmap + " homeBitmap=" + bitmap2);
        Consumer consumer = this.forwardTransitionConsumerRef;
        if (consumer != null) {
            consumer.accept(this.lockTransitionElement);
        }
        this.forwardTransitionConsumerRef = null;
        this.isForwardTransition = false;
    }

    @Override // com.miui.keyguard.editor.base.TransitionPerformer
    public void performBackwardTransition(final BaseTemplateView lockTemplateView, final BaseTemplateView homeTemplateView, final Function0 callback) {
        Intrinsics.checkNotNullParameter(lockTemplateView, "lockTemplateView");
        Intrinsics.checkNotNullParameter(homeTemplateView, "homeTemplateView");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Log.d("Keyguard-Editor-Transition", "performBackwardTransition");
        this.transitionContainer.setVisibility(0);
        this.transitionContainer.post(new Runnable() { // from class: com.miui.keyguard.editor.base.TransitionPerformerImpl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                TransitionPerformerImpl.performBackwardTransition$lambda$14(this.f$0, lockTemplateView, homeTemplateView, callback);
            }
        });
    }

    @Override // com.miui.keyguard.editor.base.TransitionPerformer
    public void performForwardTransition(View lockTemplateView, View homeTemplateView, boolean z) {
        Intrinsics.checkNotNullParameter(lockTemplateView, "lockTemplateView");
        Intrinsics.checkNotNullParameter(homeTemplateView, "homeTemplateView");
        Log.d("Keyguard-Editor-Transition", "performForwardTransition forceForward=" + z);
        final ForwardTransitionConsumer forwardTransitionConsumer = new ForwardTransitionConsumer(lockTemplateView, homeTemplateView, this.transitionHost, this.transitionLayer, this.homeTransitionElement, this.handler, this, this.slideId);
        if (z) {
            forwardTransitionConsumer.finishForwardTransition();
            return;
        }
        Consumer consumer = new Consumer() { // from class: com.miui.keyguard.editor.base.TransitionPerformerImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TransitionPerformerImpl.performForwardTransition$lambda$3(forwardTransitionConsumer, this, (OutlineElement) obj);
            }
        };
        OutlineElement outlineElement = this.lockTransitionElement;
        if (outlineElement != null) {
            consumer.accept(outlineElement);
        } else {
            Log.d("Keyguard-Editor-Transition", "transitionElement is null");
            this.forwardTransitionConsumerRef = consumer;
        }
    }

    @Override // com.miui.keyguard.editor.base.TransitionPerformer
    public void performSlideIn() {
        Log.d("Keyguard-Editor-Transition", "performSlideIn");
        View[] viewArr = this.slideView;
        if (viewArr != null) {
            View view = viewArr[0];
            if (view != null) {
                Object tag = view.getTag(R.id.kg_transition_slides_translation);
                Intrinsics.checkNotNull(tag, "null cannot be cast to non-null type kotlin.Float");
                float fFloatValue = ((Float) tag).floatValue();
                Log.i("Keyguard-Editor-Transition", "left.translationX to " + fFloatValue);
                Folme.useAt(view).state().setup("left.performSlideIn").add(ViewProperty.TRANSLATION_X, fFloatValue).to("left.performSlideIn", TransitionKt.getDefaultConfig());
            }
            View view2 = viewArr[1];
            if (view2 != null) {
                Object tag2 = view2.getTag(R.id.kg_transition_slides_translation);
                Intrinsics.checkNotNull(tag2, "null cannot be cast to non-null type kotlin.Float");
                float fFloatValue2 = ((Float) tag2).floatValue();
                Log.i("Keyguard-Editor-Transition", "right.translationX to " + fFloatValue2);
                Folme.useAt(view2).state().setup("right.performSlideIn").add(ViewProperty.TRANSLATION_X, fFloatValue2).to("right.performSlideIn", TransitionKt.getDefaultConfig());
            }
            View view3 = viewArr[2];
            if (view3 != null) {
                Object tag3 = view3.getTag(R.id.kg_transition_slides_translation);
                Intrinsics.checkNotNull(tag3, "null cannot be cast to non-null type kotlin.Float");
                float fFloatValue3 = ((Float) tag3).floatValue();
                Log.i("Keyguard-Editor-Transition", "bottom.translationY to " + fFloatValue3);
                Folme.useAt(view3).state().setup("bottom.performSlideIn").add(ViewProperty.TRANSLATION_Y, fFloatValue3).to("bottom.performSlideIn", TransitionKt.getDefaultConfig());
            }
        }
    }

    @Override // com.miui.keyguard.editor.base.TransitionPerformer
    public void performSlideOut(View view, View view2, View view3) {
        this.slideView = new View[]{view, view2, view3};
        Log.i("Keyguard-Editor-Transition", "performSlideOut");
        if (view != null) {
            Log.i("Keyguard-Editor-Transition", "left.translationX = " + view.getTranslationX());
            view.setTag(R.id.kg_transition_slides_translation, Float.valueOf(view.getTranslationX()));
            Folme.clean(view);
            Folme.useAt(view).state().setup("left.performSlideOut").add(ViewProperty.TRANSLATION_X, ViewUtil.INSTANCE.isRtl(view) ? view.getTranslationX() + view.getWidth() : view.getTranslationX() - view.getWidth()).to("left.performSlideOut", TransitionKt.getDefaultConfig());
        }
        if (view2 != null) {
            Log.i("Keyguard-Editor-Transition", "right.translationX = " + view2.getTranslationX());
            view2.setTag(R.id.kg_transition_slides_translation, Float.valueOf(view2.getTranslationX()));
            Folme.clean(view2);
            Folme.useAt(view2).state().setup("right.performSlideOut").add(ViewProperty.TRANSLATION_X, ViewUtil.INSTANCE.isRtl(view2) ? view2.getTranslationX() - view2.getWidth() : view2.getTranslationX() + view2.getWidth()).to("right.performSlideOut", TransitionKt.getDefaultConfig());
        }
        if (view3 != null) {
            Log.i("Keyguard-Editor-Transition", "bottom.translationY = " + view3.getTranslationY());
            view3.setTag(R.id.kg_transition_slides_translation, Float.valueOf(view3.getTranslationY()));
            Folme.useAt(view3).state().setup("bottom.performSlideOut").add(ViewProperty.TRANSLATION_Y, view3.getTranslationY() + ((float) view3.getHeight())).to("bottom.performSlideOut", TransitionKt.getDefaultConfig());
        }
    }

    @Override // com.miui.keyguard.editor.base.TransitionPerformer
    public void prepareForwardTransition(PreviewHolder previewHolder, TransitionHost holder) {
        Intrinsics.checkNotNullParameter(previewHolder, "previewHolder");
        Intrinsics.checkNotNullParameter(holder, "holder");
        this.isForwardTransition = true;
        Log.d("Keyguard-Editor-Transition", "prepareForwardTransition");
        this.slideId++;
        this.forwardStatus.compareAndSet(TransitionPerformer.Companion.getFORWARD_FREE(), this.slideId);
        this.transitionHost = holder;
        View lockPreview = previewHolder.getLockPreview();
        View homePreview = previewHolder.getHomePreview();
        this.previewHolder = previewHolder;
        this.lockTransitionElement = null;
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        RectF rectF = new RectF();
        lockPreview.getGlobalVisibleRect(rect);
        if (homePreview != null) {
            ViewUtil.INSTANCE.getShowRect(homePreview, rect2);
        } else {
            Context context = lockPreview.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            Matrix presetHomeMatrix = getPresetHomeMatrix(context, rect);
            rect2.set(rect);
            presetHomeMatrix.mapRect(rectF, new RectF(rect2));
            Rect rect3 = new Rect();
            rectF.roundOut(rect3);
            rect2.set(rect3);
        }
        if (!(lockPreview instanceof BaseTemplateView)) {
            TemplatePreviewViewHolder templatePreviewViewHolder = previewHolder instanceof TemplatePreviewViewHolder ? (TemplatePreviewViewHolder) previewHolder : null;
            if (templatePreviewViewHolder == null) {
                Log.d("Keyguard-Editor-Transition", "preview is null");
                takePreviewScreenShot(previewHolder, rect, rect2);
                return;
            } else {
                EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
                Context context2 = lockPreview.getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
                BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context2), null, null, new AnonymousClass3(templatePreviewViewHolder, this, previewHolder, rect, rect2, null), 3, null);
                return;
            }
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        Log.d("Keyguard-Editor-Transition", "takeScreenshot from live preview, start at " + jCurrentTimeMillis);
        BaseTemplateView baseTemplateView = homePreview instanceof BaseTemplateView ? (BaseTemplateView) homePreview : null;
        if (baseTemplateView != null) {
            DeviceScreenshotHelper.INSTANCE.setCurrentHomeTemplateWeak(new WeakReference(baseTemplateView));
            baseTemplateView.setEditTransition(true);
        }
        ViewUtil.INSTANCE.takeScreenshot((BaseTemplateView) lockPreview, new AnonymousClass2(jCurrentTimeMillis, homePreview, this, rect, rect2, previewHolder, null));
    }

    public final void prepareTransition(Bitmap bitmap, Bitmap bitmap2, Rect rect, Rect rect2, Drawable drawable) {
        if (bitmap != null) {
            this.transitionContainer.setVisibility(0);
            Context context = this.transitionContainer.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            OutlineElement outlineElement = new OutlineElement(context);
            outlineElement.setVisibility(4);
            outlineElement.setImportantForAccessibility(2);
            if (bitmap2 != null) {
                outlineElement.setElementBitmap(bitmap2);
            } else {
                Log.i("Keyguard-Editor-Transition", "homeBitmap = null");
            }
            if (drawable != null) {
                outlineElement.setElementForeground(drawable);
            }
            outlineElement.prepare(this.transitionContainer, rect2);
            this.homeTransitionElement = outlineElement;
            Context context2 = this.transitionContainer.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            OutlineElement outlineElement2 = new OutlineElement(context2);
            outlineElement2.setVisibility(4);
            outlineElement2.setImportantForAccessibility(2);
            outlineElement2.setElementBitmap(bitmap);
            if (drawable != null) {
                outlineElement2.setElementForeground(drawable);
            }
            outlineElement2.prepare(this.transitionContainer, rect);
            this.lockTransitionElement = outlineElement2;
        }
    }

    public void resetTransitionStatus(int i) {
        this.forwardStatus.compareAndSet(i, TransitionPerformer.Companion.getFORWARD_FREE());
    }

    public final void takePreviewScreenShot(final PreviewHolder previewHolder, final Rect rect, final Rect rect2) {
        Object previewData = previewHolder.getPreviewData();
        Intrinsics.checkNotNull(previewData, "null cannot be cast to non-null type com.miui.keyguard.editor.homepage.bean.TemplateItemBean");
        final TemplateItemBean templateItemBean = (TemplateItemBean) previewData;
        Task.create(new Supplier() { // from class: com.miui.keyguard.editor.base.TransitionPerformerImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return TransitionPerformerImpl.takePreviewScreenShot$lambda$1(previewHolder, templateItemBean);
            }
        }).post(new Consumer() { // from class: com.miui.keyguard.editor.base.TransitionPerformerImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TransitionPerformerImpl.takePreviewScreenShot$lambda$2(this.f$0, rect, rect2, (Pair) obj);
            }
        });
    }
}
