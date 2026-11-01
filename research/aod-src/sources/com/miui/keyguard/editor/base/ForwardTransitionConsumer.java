package com.miui.keyguard.editor.base;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.utils.UpdateTransitionListener;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.view.RoundOutlineProvider;
import com.miui.maml.folme.AnimatedProperty;
import java.util.Collection;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
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
    */
/* JADX INFO: compiled from: Transition.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
final class ForwardTransitionConsumer implements Consumer<OutlineElement> {
    public final int currentId;
    public final Handler handler;
    public final TransitionHost holder;
    public final OutlineElement homeElement;
    public final View homeTemplateView;
    public final TransitionLayer layer;
    public final View lockTemplateView;
    public final TransitionPerformerImpl transitionPerformer;

    public ForwardTransitionConsumer(View lockTemplateView, View homeTemplateView, TransitionHost transitionHost, TransitionLayer layer, OutlineElement outlineElement, Handler handler, TransitionPerformerImpl transitionPerformer, int i) {
        Intrinsics.checkNotNullParameter(lockTemplateView, "lockTemplateView");
        Intrinsics.checkNotNullParameter(homeTemplateView, "homeTemplateView");
        Intrinsics.checkNotNullParameter(layer, "layer");
        Intrinsics.checkNotNullParameter(handler, "handler");
        Intrinsics.checkNotNullParameter(transitionPerformer, "transitionPerformer");
        this.lockTemplateView = lockTemplateView;
        this.homeTemplateView = homeTemplateView;
        this.holder = transitionHost;
        this.layer = layer;
        this.homeElement = outlineElement;
        this.handler = handler;
        this.transitionPerformer = transitionPerformer;
        this.currentId = i;
    }

    public static final void accept$lambda$5$lambda$4(final OutlineElement outlineElement, final ForwardTransitionConsumer forwardTransitionConsumer) {
        int i;
        int i2;
        ImageView element;
        ImageView element2;
        ImageView element3;
        outlineElement.setVisibility(0);
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        Context context = outlineElement.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        final float fProvidePreviewRadius = resourcesProvider.providePreviewRadius(context);
        outlineElement.getElement().setOutlineProvider(new RoundOutlineProvider(fProvidePreviewRadius));
        OutlineElement outlineElement2 = forwardTransitionConsumer.homeElement;
        if (outlineElement2 != null) {
            outlineElement2.setVisibility(0);
        }
        OutlineElement outlineElement3 = forwardTransitionConsumer.homeElement;
        if (outlineElement3 != null && (element3 = outlineElement3.getElement()) != null) {
            element3.setOutlineProvider(new RoundOutlineProvider(fProvidePreviewRadius));
        }
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        forwardTransitionConsumer.lockTemplateView.getGlobalVisibleRect(rect);
        forwardTransitionConsumer.homeTemplateView.getGlobalVisibleRect(rect2);
        if (ViewUtil.INSTANCE.isRtl(outlineElement)) {
            i = rect2.right;
            i2 = rect.right;
        } else {
            i = rect2.left;
            i2 = rect.left;
        }
        int i3 = i - i2;
        rect2.set(rect);
        rect2.offset(i3, 0);
        Rect rect3 = new Rect();
        outlineElement.getElement().getGlobalVisibleRect(rect3);
        Rect rect4 = new Rect();
        OutlineElement outlineElement4 = forwardTransitionConsumer.homeElement;
        if (outlineElement4 != null && (element2 = outlineElement4.getElement()) != null) {
            element2.getGlobalVisibleRect(rect4);
        }
        final float fWidth = rect.width() / rect3.width();
        final float fHeight = rect.height() / rect3.height();
        float fWidth2 = rect2.width() / rect4.width();
        float fHeight2 = rect2.height() / rect4.height();
        ViewOutlineProvider outlineProvider = forwardTransitionConsumer.lockTemplateView.getOutlineProvider();
        Intrinsics.checkNotNull(outlineProvider, "null cannot be cast to non-null type com.miui.keyguard.editor.view.RoundOutlineProvider");
        float radius = ((RoundOutlineProvider) outlineProvider).getRadius();
        Context context2 = outlineElement.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        final float fProvideTemplateEditScale = (radius * resourcesProvider.provideTemplateEditScale(context2)) / fWidth;
        ViewOutlineProvider outlineProvider2 = forwardTransitionConsumer.homeTemplateView.getOutlineProvider();
        Intrinsics.checkNotNull(outlineProvider2, "null cannot be cast to non-null type com.miui.keyguard.editor.view.RoundOutlineProvider");
        float radius2 = ((RoundOutlineProvider) outlineProvider2).getRadius();
        Context context3 = outlineElement.getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
        final float fProvideTemplateEditScale2 = (radius2 * resourcesProvider.provideTemplateEditScale(context3)) / fWidth2;
        outlineElement.getGlobalVisibleRect(rect3);
        OutlineElement outlineElement5 = forwardTransitionConsumer.homeElement;
        if (outlineElement5 != null) {
            outlineElement5.getGlobalVisibleRect(rect4);
        }
        float f = 1;
        outlineElement.setPivotX(((rect3.left - rect.left) + (outlineElement.getSpacing() * fWidth)) / (fWidth - f));
        outlineElement.setPivotY(((rect3.top - rect.top) + (outlineElement.getSpacing() * fWidth)) / (fHeight - f));
        int iCenterX = rect2.centerX() - rect4.centerX();
        int iCenterY = rect2.centerY() - rect4.centerY();
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        final Runnable runnable = new Runnable() { // from class: com.miui.keyguard.editor.base.ForwardTransitionConsumer$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ForwardTransitionConsumer.accept$lambda$5$lambda$4$lambda$0(this.f$0, booleanRef, outlineElement);
            }
        };
        forwardTransitionConsumer.handler.postDelayed(runnable, forwardTransitionConsumer, 1000L);
        Log.d("Keyguard-Editor-Transition", "forward transition start lockTargetScaleX=" + fWidth + " lockTargetScaleY=" + fHeight + " homeTargetScaleX=" + fWidth2 + " homeTargetScaleY=" + fHeight2 + " homeTransitionX=" + iCenterX + " homeTransitionY=" + iCenterY + " lockTemplateRadius=" + fProvideTemplateEditScale + " homeTemplateRadius=" + fProvideTemplateEditScale2);
        final float scaleX = outlineElement.getScaleX();
        final float scaleY = outlineElement.getScaleY();
        ViewOutlineProvider outlineProvider3 = outlineElement.getElement().getOutlineProvider();
        Intrinsics.checkNotNull(outlineProvider3, "null cannot be cast to non-null type com.miui.keyguard.editor.view.RoundOutlineProvider");
        final RoundOutlineProvider roundOutlineProvider = (RoundOutlineProvider) outlineProvider3;
        OutlineElement outlineElement6 = forwardTransitionConsumer.homeElement;
        ViewOutlineProvider outlineProvider4 = (outlineElement6 == null || (element = outlineElement6.getElement()) == null) ? null : element.getOutlineProvider();
        final RoundOutlineProvider roundOutlineProvider2 = outlineProvider4 instanceof RoundOutlineProvider ? (RoundOutlineProvider) outlineProvider4 : null;
        final RoundOutlineProvider roundOutlineProvider3 = roundOutlineProvider2;
        Folme.useValue(new Object[0]).setTo("progress", Float.valueOf(0.0f)).to("progress", Float.valueOf(1.0f), TransitionKt.getAnimConfig(0.95f, 0.38f).addListeners(new UpdateTransitionListener(new Function0() { // from class: com.miui.keyguard.editor.base.ForwardTransitionConsumer$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return ForwardTransitionConsumer.accept$lambda$5$lambda$4$lambda$1(outlineElement, fWidth, fHeight, roundOutlineProvider, fProvideTemplateEditScale, roundOutlineProvider2, fProvideTemplateEditScale2, forwardTransitionConsumer, runnable);
            }
        }, new Function2() { // from class: com.miui.keyguard.editor.base.ForwardTransitionConsumer$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ForwardTransitionConsumer.accept$lambda$5$lambda$4$lambda$2(fProvidePreviewRadius, fProvideTemplateEditScale, fProvideTemplateEditScale2, scaleX, fWidth, scaleY, fHeight, outlineElement, roundOutlineProvider, roundOutlineProvider3, forwardTransitionConsumer, obj, (Collection) obj2);
            }
        })));
        OutlineElement outlineElement7 = forwardTransitionConsumer.homeElement;
        if (outlineElement7 != null) {
            Folme.use((View) outlineElement7).state().setup("performForwardTransition.home").add(ViewProperty.SCALE_X, fWidth2).add(ViewProperty.SCALE_Y, fHeight2).add((FloatProperty) ViewProperty.TRANSLATION_X, iCenterX).add((FloatProperty) ViewProperty.TRANSLATION_Y, iCenterY).to("performForwardTransition.home", TransitionKt.getAnimConfig(0.95f, 0.38f));
        }
    }

    public static final void accept$lambda$5$lambda$4$lambda$0(final ForwardTransitionConsumer forwardTransitionConsumer, Ref.BooleanRef booleanRef, final OutlineElement outlineElement) {
        forwardTransitionConsumer.handler.removeCallbacksAndMessages(forwardTransitionConsumer);
        if (booleanRef.element) {
            return;
        }
        booleanRef.element = true;
        if (forwardTransitionConsumer.layer.getTransitionContainer().getVisibility() == 8) {
            return;
        }
        forwardTransitionConsumer.beforeTransitionDismiss();
        Folme.useValue(new Object[0]).setTo(AnimatedProperty.PROPERTY_NAME_ALPHA, Float.valueOf(1.0f)).to(AnimatedProperty.PROPERTY_NAME_ALPHA, Float.valueOf(0.0f), new AnimConfig().setEase(EaseManager.getStyle(-2, 0.95f, 0.38f)).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.base.ForwardTransitionConsumer$accept$1$1$transitionComplete$1$alphaConfig$1
            public final void onAlphaComplete() {
                TransitionHost holder = forwardTransitionConsumer.getHolder();
                if (holder != null) {
                    holder.onForwardTransitionEnd();
                }
                forwardTransitionConsumer.getLayer().onTransitionComplete(false);
                forwardTransitionConsumer.getTransitionPerformer().resetTransitionStatus(forwardTransitionConsumer.getCurrentId());
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                super.onCancel(obj);
                onAlphaComplete();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                onAlphaComplete();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                ImageView element;
                super.onUpdate(obj, collection);
                UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, AnimatedProperty.PROPERTY_NAME_ALPHA);
                if (updateInfoFindByName != null) {
                    OutlineElement outlineElement2 = outlineElement;
                    ForwardTransitionConsumer forwardTransitionConsumer2 = forwardTransitionConsumer;
                    float floatValue = updateInfoFindByName.getFloatValue();
                    outlineElement2.getElement().setAlpha(floatValue);
                    OutlineElement homeElement = forwardTransitionConsumer2.getHomeElement();
                    if (homeElement == null || (element = homeElement.getElement()) == null) {
                        return;
                    }
                    element.setAlpha(floatValue);
                }
            }
        }));
        forwardTransitionConsumer.afterTransitionDismiss();
    }

    public static final Unit accept$lambda$5$lambda$4$lambda$1(OutlineElement outlineElement, float f, float f2, RoundOutlineProvider roundOutlineProvider, float f3, RoundOutlineProvider roundOutlineProvider2, float f4, ForwardTransitionConsumer forwardTransitionConsumer, Runnable runnable) {
        ImageView element;
        outlineElement.setScaleX(f);
        outlineElement.setScaleY(f2);
        roundOutlineProvider.setRadius(f3);
        if (roundOutlineProvider2 != null) {
            roundOutlineProvider2.setRadius(f4);
        }
        outlineElement.getElement().invalidateOutline();
        OutlineElement outlineElement2 = forwardTransitionConsumer.homeElement;
        if (outlineElement2 != null && (element = outlineElement2.getElement()) != null) {
            element.invalidateOutline();
        }
        runnable.run();
        Log.d("Keyguard-Editor-Transition", "radius forward transition complete");
        return Unit.INSTANCE;
    }

    public static final Unit accept$lambda$5$lambda$4$lambda$2(float f, float f2, float f3, float f4, float f5, float f6, float f7, OutlineElement outlineElement, RoundOutlineProvider roundOutlineProvider, RoundOutlineProvider roundOutlineProvider2, ForwardTransitionConsumer forwardTransitionConsumer, Object obj, Collection collection) {
        ImageView element;
        float floatValue = UpdateInfo.findByName(collection, "progress").getFloatValue();
        float f8 = ((f2 - f) * floatValue) + f;
        float f9 = f + ((f3 - f) * floatValue);
        outlineElement.setScaleX(f4 + ((f5 - f4) * floatValue));
        outlineElement.setScaleY(f6 + ((f7 - f6) * floatValue));
        roundOutlineProvider.setRadius(f8);
        if (roundOutlineProvider2 != null) {
            roundOutlineProvider2.setRadius(f9);
        }
        outlineElement.getElement().invalidateOutline();
        OutlineElement outlineElement2 = forwardTransitionConsumer.homeElement;
        if (outlineElement2 != null && (element = outlineElement2.getElement()) != null) {
            element.invalidateOutline();
        }
        return Unit.INSTANCE;
    }

    @Override // java.util.function.Consumer
    public void accept(final OutlineElement outlineElement) {
        if (outlineElement != null) {
            this.lockTemplateView.post(new Runnable() { // from class: com.miui.keyguard.editor.base.ForwardTransitionConsumer$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ForwardTransitionConsumer.accept$lambda$5$lambda$4(outlineElement, this);
                }
            });
        }
    }

    public final void afterTransitionDismiss() {
        View view = this.lockTemplateView;
        if (view instanceof BaseTemplateView) {
            ((BaseTemplateView) view).onForwardTransitionComplete();
        }
        View view2 = this.homeTemplateView;
        if (view2 instanceof BaseTemplateView) {
            ((BaseTemplateView) view2).onForwardTransitionComplete();
        }
    }

    public final void beforeTransitionDismiss() {
        this.lockTemplateView.setAlpha(1.0f);
        this.homeTemplateView.setAlpha(1.0f);
        this.layer.onBeforeTransitionGone(true);
    }

    public final void finishForwardTransition() {
        beforeTransitionDismiss();
        afterTransitionDismiss();
    }

    public final int getCurrentId() {
        return this.currentId;
    }

    public final TransitionHost getHolder() {
        return this.holder;
    }

    public final OutlineElement getHomeElement() {
        return this.homeElement;
    }

    public final TransitionLayer getLayer() {
        return this.layer;
    }

    public final TransitionPerformerImpl getTransitionPerformer() {
        return this.transitionPerformer;
    }
}
