package com.miui.keyguard.editor.homepage.view.viewpager;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.homepage.view.CrossViewPager;
import com.miui.keyguard.editor.homepage.view.CustomButtonView;
import com.miui.keyguard.editor.homepage.view.DeleteLayer;
import com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder;
import com.miui.keyguard.editor.utils.ViewStylerKt;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.view.RoundOutlineProvider;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.FloatProperty;
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
/* JADX INFO: compiled from: TemplateDeleteLayer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class TemplateDeleteLayer extends FrameLayout {
    public static final Companion Companion = new Companion(null);
    public BaseItemViewHolder animViewHolder;
    public final float blurRadius;
    public int containHeight;
    public final int customButtonBackgroundColor;
    public final int customButtonBlendColor1;
    public final int customButtonBlendColor2;
    public final float customButtonRadius;
    public DeleteLayer deleteLayer;
    public boolean isLeftDark;
    public boolean isRightDark;
    public boolean isTargetDark;
    public FrameLayout leftContainer;
    public ImageView leftContent;
    public TextView leftCustomBtn;
    public View leftItemView;
    public FrameLayout leftNextContainer;
    public ImageView leftNextContent;
    public View leftNextItemView;
    public View leftNextView;
    public View leftView;
    public FrameLayout rightContainer;
    public ImageView rightContent;
    public TextView rightCustomBtn;
    public View rightItemView;
    public FrameLayout rightNextContainer;
    public ImageView rightNextContent;
    public View rightNextItemView;
    public View rightNextView;
    public View rightView;
    public FrameLayout targetContainer;
    public float targetContainerScaleX;
    public float targetContainerScaleY;
    public float targetContainerX;
    public float targetContainerY;
    public ImageView targetContent;
    public TextView targetCustomBtn;
    public View targetItemView;
    public View targetView;
    public BaseItemViewHolder targetViewHolder;

    /* JADX INFO: compiled from: TemplateDeleteLayer.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.homepage.view.viewpager.TemplateDeleteLayer$setContent$1, reason: invalid class name */
    /* JADX INFO: compiled from: TemplateDeleteLayer.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.homepage.view.viewpager.TemplateDeleteLayer$setContent$1", f = "TemplateDeleteLayer.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function4<Bitmap, Integer, Bitmap, Continuation<? super Unit>, Object> {
        public final /* synthetic */ ImageView $content;
        public /* synthetic */ Object L$0;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ImageView imageView, Continuation continuation) {
            super(4, continuation);
            this.$content = imageView;
        }

        public final Object invoke(Bitmap bitmap, int i, Bitmap bitmap2, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$content, continuation);
            anonymousClass1.L$0 = bitmap;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
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
            this.$content.setImageBitmap((Bitmap) this.L$0);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    @JvmOverloads
    public TemplateDeleteLayer(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public TemplateDeleteLayer(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        this.containHeight = (int) ResourcesProvider.provideTemplateContainerHeight$default(resourcesProvider, context, null, 2, null);
        this.targetContainerScaleX = 1.0f;
        this.targetContainerScaleY = 1.0f;
        this.customButtonRadius = resourcesProvider.provideCustomButtonRadius(context);
        this.customButtonBlendColor1 = resourcesProvider.provideCustomButtonBlendColor1(context);
        this.customButtonBlendColor2 = resourcesProvider.provideCustomButtonBlendColor2(context);
        this.customButtonBackgroundColor = resourcesProvider.provideCustomButtonBackgroundColor(context);
        this.blurRadius = resourcesProvider.provideBlurRadius(context);
        LayoutInflater.from(context).inflate(R.layout.kg_template_delete_layer, this);
        this.targetContainer = (FrameLayout) findViewById(R.id.target_container);
        this.leftContainer = (FrameLayout) findViewById(R.id.left_container);
        this.leftNextContainer = (FrameLayout) findViewById(R.id.left_next_container);
        this.rightContainer = (FrameLayout) findViewById(R.id.right_container);
        this.rightNextContainer = (FrameLayout) findViewById(R.id.right_next_container);
        this.targetContent = (ImageView) findViewById(R.id.target_content);
        this.leftContent = (ImageView) findViewById(R.id.left_content);
        this.leftNextContent = (ImageView) findViewById(R.id.left_next_content);
        this.rightContent = (ImageView) findViewById(R.id.right_content);
        this.rightNextContent = (ImageView) findViewById(R.id.right_next_content);
        this.targetCustomBtn = (TextView) findViewById(R.id.target_custom);
        this.rightCustomBtn = (TextView) findViewById(R.id.right_custom);
        this.leftCustomBtn = (TextView) findViewById(R.id.left_custom);
        this.deleteLayer = (DeleteLayer) findViewById(R.id.delete_layer);
    }

    public /* synthetic */ TemplateDeleteLayer(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public static /* synthetic */ AnimConfig createCommonAnimConfig$default(TemplateDeleteLayer templateDeleteLayer, TransitionListener transitionListener, float f, float f2, int i, Object obj) {
        if ((i & 2) != 0) {
            f = 0.95f;
        }
        if ((i & 4) != 0) {
            f2 = 0.35f;
        }
        return templateDeleteLayer.createCommonAnimConfig(transitionListener, f, f2);
    }

    public static final Unit startDeleteTransformAnim$lambda$0(TemplateDeleteLayer templateDeleteLayer, Function0 function0) {
        BaseItemViewHolder baseItemViewHolder = templateDeleteLayer.animViewHolder;
        if (baseItemViewHolder != null) {
            baseItemViewHolder.switchHome(true, true);
        }
        function0.invoke();
        return Unit.INSTANCE;
    }

    public static final void startDeleteTransformAnim$lambda$5(TemplateDeleteLayer templateDeleteLayer, Function0 function0) {
        BaseItemViewHolder baseItemViewHolder = templateDeleteLayer.animViewHolder;
        if (baseItemViewHolder != null) {
            baseItemViewHolder.switchHome(false, false);
        }
        View view = templateDeleteLayer.rightItemView;
        if (view == null) {
            if (templateDeleteLayer.leftItemView != null) {
                templateDeleteLayer.startLeftAnim(function0);
            }
            if (templateDeleteLayer.leftNextItemView != null) {
                templateDeleteLayer.startLeftNextAnim();
                return;
            }
            return;
        }
        if (view != null) {
            templateDeleteLayer.startRightAnim(function0);
        }
        if (templateDeleteLayer.rightNextItemView != null) {
            templateDeleteLayer.startRightNextAnim();
        }
    }

    public final void copyUI() {
        View view;
        View view2;
        View view3;
        View view4;
        View view5 = this.targetItemView;
        if (view5 != null && this.targetView != null) {
            FrameLayout frameLayout = this.targetContainer;
            Intrinsics.checkNotNull(view5);
            setTargetContainerLayout(frameLayout, view5);
            setContent(this.targetContent, this.targetView);
        }
        if (this.rightItemView != null && (view4 = this.rightView) != null) {
            setContent(this.rightContent, view4);
            FrameLayout frameLayout2 = this.rightContainer;
            View view6 = this.rightItemView;
            Intrinsics.checkNotNull(view6);
            setNeighboringContainerLayout(frameLayout2, view6);
        }
        if (this.rightNextItemView != null && (view3 = this.rightNextView) != null) {
            setContent(this.rightNextContent, view3);
            FrameLayout frameLayout3 = this.rightNextContainer;
            View view7 = this.rightNextItemView;
            Intrinsics.checkNotNull(view7);
            setNeighboringContainerLayout(frameLayout3, view7);
        }
        if (this.rightItemView == null) {
            if (this.leftItemView != null && (view2 = this.leftView) != null) {
                setContent(this.leftContent, view2);
                FrameLayout frameLayout4 = this.leftContainer;
                View view8 = this.leftItemView;
                Intrinsics.checkNotNull(view8);
                setNeighboringContainerLayout(frameLayout4, view8);
            }
            if (this.leftNextItemView != null && (view = this.leftNextView) != null) {
                setContent(this.leftNextContent, view);
                FrameLayout frameLayout5 = this.leftNextContainer;
                View view9 = this.leftNextItemView;
                Intrinsics.checkNotNull(view9);
                setNeighboringContainerLayout(frameLayout5, view9);
            }
            this.leftContainer.setVisibility(0);
            this.leftNextContainer.setVisibility(0);
        }
        TextView textView = this.leftCustomBtn;
        Intrinsics.checkNotNull(textView, "null cannot be cast to non-null type com.miui.keyguard.editor.homepage.view.CustomButtonView");
        setCustomBtn((CustomButtonView) textView, this.isLeftDark);
        TextView textView2 = this.rightCustomBtn;
        Intrinsics.checkNotNull(textView2, "null cannot be cast to non-null type com.miui.keyguard.editor.homepage.view.CustomButtonView");
        setCustomBtn((CustomButtonView) textView2, this.isRightDark);
        TextView textView3 = this.targetCustomBtn;
        Intrinsics.checkNotNull(textView3, "null cannot be cast to non-null type com.miui.keyguard.editor.homepage.view.CustomButtonView");
        setCustomBtn((CustomButtonView) textView3, this.isTargetDark);
        View view10 = this.targetItemView;
        if (view10 != null) {
            view10.setAlpha(0.0f);
        }
        View view11 = this.rightItemView;
        if (view11 != null) {
            view11.setAlpha(0.0f);
        }
        View view12 = this.rightNextItemView;
        if (view12 != null) {
            view12.setAlpha(0.0f);
        }
    }

    public final AnimConfig createCommonAnimConfig(TransitionListener transitionListener, float f, float f2) {
        AnimConfig animConfig = new AnimConfig();
        animConfig.setEase(EaseManager.getStyle(-2, f, f2));
        if (transitionListener != null) {
            animConfig.addListeners(transitionListener);
        }
        return animConfig;
    }

    public final void initLayout(int i, int i2, CrossViewPager crossViewPager) {
        Intrinsics.checkNotNullParameter(crossViewPager, "crossViewPager");
        Log.i("Keyguard-Editor-TemplateDeleteLayer", "deletePager X:" + i + " Y:" + i2);
        BaseItemViewHolder baseItemViewHolder = (BaseItemViewHolder) crossViewPager.getViewHolder(i, i2);
        if (baseItemViewHolder == null) {
            Log.e("Keyguard-Editor-TemplateDeleteLayer", "initLayout targetViewHolder == null");
            return;
        }
        this.targetViewHolder = baseItemViewHolder;
        this.targetView = baseItemViewHolder.getLockPreview();
        this.targetItemView = baseItemViewHolder.itemView;
        this.isTargetDark = baseItemViewHolder.isDarkWallpaper();
        BaseItemViewHolder baseItemViewHolder2 = (BaseItemViewHolder) crossViewPager.getViewHolder(i - 1, i2);
        this.leftView = baseItemViewHolder2 != null ? baseItemViewHolder2.getLockPreview() : null;
        this.leftItemView = baseItemViewHolder2 != null ? baseItemViewHolder2.itemView : null;
        this.isLeftDark = baseItemViewHolder2 != null ? baseItemViewHolder2.isDarkWallpaper() : false;
        BaseItemViewHolder baseItemViewHolder3 = (BaseItemViewHolder) crossViewPager.getViewHolder(i - 2, i2);
        this.leftNextView = baseItemViewHolder3 != null ? baseItemViewHolder3.getLockPreview() : null;
        this.leftNextItemView = baseItemViewHolder3 != null ? baseItemViewHolder3.itemView : null;
        BaseItemViewHolder baseItemViewHolder4 = (BaseItemViewHolder) crossViewPager.getViewHolder(i + 1, i2);
        this.rightView = baseItemViewHolder4 != null ? baseItemViewHolder4.getLockPreview() : null;
        this.rightItemView = baseItemViewHolder4 != null ? baseItemViewHolder4.itemView : null;
        this.isRightDark = baseItemViewHolder4 != null ? baseItemViewHolder4.isDarkWallpaper() : false;
        BaseItemViewHolder baseItemViewHolder5 = (BaseItemViewHolder) crossViewPager.getViewHolder(i + 2, i2);
        this.rightNextView = baseItemViewHolder5 != null ? baseItemViewHolder5.getLockPreview() : null;
        this.rightNextItemView = baseItemViewHolder5 != null ? baseItemViewHolder5.itemView : null;
        this.leftCustomBtn.setVisibility(baseItemViewHolder2 != null ? baseItemViewHolder2.shouldShowCustomButton() : true ? 0 : 8);
        this.deleteLayer.initialize();
        if (this.rightItemView == null) {
            baseItemViewHolder = baseItemViewHolder2;
        }
        this.animViewHolder = baseItemViewHolder;
        copyUI();
    }

    public final void onAnimComplete() {
        List<View> deleteLayerViews;
        BaseItemViewHolder baseItemViewHolder = this.targetViewHolder;
        if (baseItemViewHolder != null && (deleteLayerViews = baseItemViewHolder.getDeleteLayerViews()) != null) {
            for (View view : deleteLayerViews) {
                Folme.useAt(view).state().cancel();
                view.setAlpha(0.0f);
                view.setVisibility(8);
            }
        }
        View view2 = this.targetItemView;
        if (view2 != null) {
            view2.setAlpha(1.0f);
        }
        View view3 = this.leftItemView;
        if (view3 != null) {
            view3.setAlpha(1.0f);
        }
        View view4 = this.leftNextItemView;
        if (view4 != null) {
            view4.setAlpha(1.0f);
        }
        View view5 = this.rightItemView;
        if (view5 != null) {
            view5.setAlpha(1.0f);
        }
        View view6 = this.rightNextItemView;
        if (view6 != null) {
            view6.setAlpha(1.0f);
        }
    }

    public final void setContent(ImageView imageView, View view) {
        imageView.setClipToOutline(true);
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        imageView.setOutlineProvider(new RoundOutlineProvider(resourcesProvider.providePreviewRadius(context)));
        if (view instanceof BaseTemplateView) {
            ViewUtil.INSTANCE.takeScreenshot((BaseTemplateView) view, new AnonymousClass1(imageView, null));
        } else {
            imageView.setImageBitmap(view != null ? ViewUtil.INSTANCE.drawToBitmap(view) : null);
        }
    }

    public final void setCustomBtn(CustomButtonView customButtonView, boolean z) {
        customButtonView.setTextSize(0, getContext().getResources().getDimension(R.dimen.kg_main_item_custom_button_text_size));
        ViewUtil viewUtil = ViewUtil.INSTANCE;
        ViewUtil.setBackgroundBlurModeWhenBlurEnabled$default(viewUtil, customButtonView, this.blurRadius, 0, 2, null);
        viewUtil.decorateCustomButton(customButtonView, ViewStylerKt.canUseBlur(), z);
        customButtonView.resetTextSize();
    }

    public final void setNeighboringContainerLayout(ViewGroup viewGroup, View view) {
        ViewUtil.INSTANCE.setHeight(viewGroup, this.containHeight);
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        viewGroup.setScaleX(resourcesProvider.provideMinScaleForNeighboringItem(context));
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        viewGroup.setScaleY(resourcesProvider.provideMinScaleForNeighboringItem(context2));
        viewGroup.setPivotX(view.getPivotX());
        viewGroup.setPivotY(view.getPivotY());
        viewGroup.setTranslationX(view.getTranslationX());
        viewGroup.setX(view.getX());
    }

    public final void setTargetContainerLayout(ViewGroup viewGroup, View view) {
        ViewUtil.INSTANCE.setHeight(viewGroup, this.containHeight);
        viewGroup.setTranslationX(view.getTranslationX());
        viewGroup.setX(view.getX());
    }

    public final void startDeleteTransformAnim(final Function0 callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        startTargetAnim();
        final Function0 function0 = new Function0() { // from class: com.miui.keyguard.editor.homepage.view.viewpager.TemplateDeleteLayer$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return TemplateDeleteLayer.startDeleteTransformAnim$lambda$0(this.f$0, callback);
            }
        };
        postDelayed(new Runnable() { // from class: com.miui.keyguard.editor.homepage.view.viewpager.TemplateDeleteLayer$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                TemplateDeleteLayer.startDeleteTransformAnim$lambda$5(this.f$0, function0);
            }
        }, 100L);
    }

    public final void startLeftAnim(final Function0 function0) {
        Folme.useAt(this.leftContainer).state().add(ViewProperty.X, this.targetContainerX).add(ViewProperty.Y, this.targetContainerY).to("left.translate", createCommonAnimConfig$default(this, null, 0.0f, 0.0f, 6, null));
        Folme.useAt(this.leftContainer).state().add(ViewProperty.SCALE_X, this.targetContainerScaleX).add(ViewProperty.SCALE_Y, this.targetContainerScaleY).to("left.scale", createCommonAnimConfig(new TransitionListener() { // from class: com.miui.keyguard.editor.homepage.view.viewpager.TemplateDeleteLayer.startLeftAnim.1
            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                TemplateDeleteLayer.this.onAnimComplete();
                function0.invoke();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                TemplateDeleteLayer.this.onAnimComplete();
                function0.invoke();
            }
        }, 0.95f, 0.4f));
        if (this.leftCustomBtn.getVisibility() == 0) {
            Folme.useAt(this.leftCustomBtn).state().add((FloatProperty) ViewProperty.ALPHA, 1).to("left.customBtn", createCommonAnimConfig$default(this, null, 0.0f, 0.0f, 6, null));
        }
    }

    public final void startLeftNextAnim() {
        float x = this.leftContainer.getX();
        float y = this.leftContainer.getY();
        float scaleX = this.leftContainer.getScaleX();
        float scaleY = this.leftContainer.getScaleY();
        Folme.useAt(this.leftNextContainer).state().add(ViewProperty.X, x).add(ViewProperty.Y, y).to("leftNext.translate", createCommonAnimConfig$default(this, null, 0.0f, 0.0f, 6, null));
        Folme.useAt(this.leftNextContainer).state().add(ViewProperty.SCALE_X, scaleX).add(ViewProperty.SCALE_Y, scaleY).to("leftNext.scale", createCommonAnimConfig(null, 0.95f, 0.4f));
    }

    public final void startRightAnim(final Function0 function0) {
        Folme.useAt(this.rightContainer).state().add(ViewProperty.X, this.targetContainerX).add(ViewProperty.Y, this.targetContainerY).to("right.translate", createCommonAnimConfig$default(this, null, 0.0f, 0.0f, 6, null));
        Folme.useAt(this.rightContainer).state().add(ViewProperty.SCALE_X, this.targetContainerScaleX).add(ViewProperty.SCALE_Y, this.targetContainerScaleY).to("right.scale", createCommonAnimConfig(new TransitionListener() { // from class: com.miui.keyguard.editor.homepage.view.viewpager.TemplateDeleteLayer.startRightAnim.1
            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                TemplateDeleteLayer.this.onAnimComplete();
                function0.invoke();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                TemplateDeleteLayer.this.onAnimComplete();
                function0.invoke();
            }
        }, 0.95f, 0.4f));
        Folme.useAt(this.rightCustomBtn).state().add((FloatProperty) ViewProperty.ALPHA, 1).to("right.customBtn", createCommonAnimConfig$default(this, null, 0.0f, 0.0f, 6, null));
    }

    public final void startRightNextAnim() {
        float x = this.rightContainer.getX();
        float y = this.rightContainer.getY();
        float scaleX = this.rightContainer.getScaleX();
        float scaleY = this.rightContainer.getScaleY();
        IStateStyle iStateStyleState = Folme.useAt(this.rightNextContainer).state();
        ViewProperty viewProperty = ViewProperty.X;
        iStateStyleState.add(viewProperty, x).add(ViewProperty.Y, y).to("rightNext.translate", createCommonAnimConfig$default(this, null, 0.0f, 0.0f, 6, null));
        Folme.useAt(this.rightNextContainer).state().add(viewProperty, x).add(ViewProperty.SCALE_X, scaleX).add(ViewProperty.SCALE_Y, scaleY).to("rightNext.scale", createCommonAnimConfig(null, 0.95f, 0.4f));
    }

    public final void startTargetAnim() {
        if (this.rightItemView == null) {
            View view = this.leftItemView;
            if (view != null) {
                view.setAlpha(0.0f);
            }
            View view2 = this.leftNextItemView;
            if (view2 != null) {
                view2.setAlpha(0.0f);
            }
        }
        Folme.useAt(this.targetContainer).state().add((FloatProperty) ViewProperty.SCALE_X, 0.2f).add((FloatProperty) ViewProperty.SCALE_Y, 0.2f).add((FloatProperty) ViewProperty.ALPHA, 0.0f).to("target", createCommonAnimConfig$default(this, null, 0.0f, 0.0f, 6, null));
    }
}
