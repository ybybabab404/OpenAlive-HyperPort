package com.miui.keyguard.editor.homepage.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.TransitionKt;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.homepage.view.generator.FactoryParams;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.view.RoundOutlineProvider;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
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
/* JADX INFO: compiled from: TemplatePreviewView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class TemplatePreviewView extends FrameLayout {
    public View animationLayer;
    public final int homeExtraHeight;
    public ImageView homeLayer;
    public ImageView previewLayer;
    public ImageView previewLayerBackground;
    public final float previewRadius;
    public Drawable selectDrawable;
    public Drawable unselectDrawable;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    @JvmOverloads
    public TemplatePreviewView(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public TemplatePreviewView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        this.previewRadius = resourcesProvider.providePreviewRadius(context);
        this.homeExtraHeight = resourcesProvider.provideHomeExtraHeight(context);
    }

    public /* synthetic */ TemplatePreviewView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public static /* synthetic */ void switchHomeLayer$default(TemplatePreviewView templatePreviewView, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z2 = true;
        }
        templatePreviewView.switchHomeLayer(z, z2);
    }

    public static /* synthetic */ void switchPreview$default(TemplatePreviewView templatePreviewView, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z2 = true;
        }
        templatePreviewView.switchPreview(z, z2);
    }

    public final void addPreviewBackground() {
        if (DeviceUtil.INSTANCE.isPad()) {
            return;
        }
        View view = this.previewLayerBackground;
        if (view != null) {
            Intrinsics.checkNotNull(view);
            if (indexOfChild(view) != -1) {
                return;
            }
        }
        ImageView imageView = new ImageView(getContext());
        imageView.setId(R.id.kg_template_negative_card_mask);
        imageView.setImageDrawable(this.unselectDrawable);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        Context context = imageView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        float fProvideTemplateContainerHeight = resourcesProvider.provideTemplateContainerHeight(context, new FactoryParams(true, false, 2, null));
        Context context2 = imageView.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        imageView.setLayoutParams(new FrameLayout.LayoutParams(-1, (int) (fProvideTemplateContainerHeight - (2 * resourcesProvider.provideTemplateContainerPadding(context2)))));
        ViewUtil viewUtil = ViewUtil.INSTANCE;
        viewUtil.setGravity(imageView, 49);
        imageView.setClipToOutline(true);
        imageView.setOutlineProvider(new RoundOutlineProvider(this.previewRadius));
        viewUtil.setSmoothCornerEnabled(imageView, true);
        this.previewLayerBackground = imageView;
        addView(imageView);
    }

    public final void alphaHideAnimationLayer() {
        IStateStyle iStateStyleState = Folme.useAt(this.animationLayer).state();
        AnimState animState = new AnimState();
        ViewProperty viewProperty = ViewProperty.ALPHA;
        iStateStyleState.setTo(animState.add(viewProperty, 1.0f, new long[0])).to(new AnimState().add(viewProperty, 0.0f, new long[0]), new AnimConfig().setSpecial(viewProperty, EaseManager.getStyle(-2, 0.95f, 0.5f), new float[0]).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.homepage.view.TemplatePreviewView.alphaHideAnimationLayer.1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                super.onBegin(obj);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                View view = TemplatePreviewView.this.animationLayer;
                if (view != null) {
                    view.setAlpha(0.0f);
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                View view = TemplatePreviewView.this.animationLayer;
                if (view != null) {
                    view.setAlpha(0.0f);
                }
            }
        }));
    }

    @Nullable
    public final View getHomeLayer() {
        return this.homeLayer;
    }

    @NotNull
    public final View getLockLayer() {
        ImageView imageView = this.previewLayer;
        Intrinsics.checkNotNull(imageView);
        return imageView;
    }

    public final void init(FactoryParams params) {
        Intrinsics.checkNotNullParameter(params, "params");
        ImageView imageView = new ImageView(getContext());
        imageView.setId(R.id.kg_template_preview_lock);
        this.previewLayer = imageView;
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (params.isPreset()) {
            ImageView imageView2 = this.previewLayer;
            if (imageView2 != null) {
                imageView2.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            }
            ImageView imageView3 = this.previewLayerBackground;
            if (imageView3 != null) {
                imageView3.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            }
        } else {
            ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            int iProvideTemplateContainerWidth = (int) resourcesProvider.provideTemplateContainerWidth(context);
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            float fProvideTemplateContainerHeight = resourcesProvider.provideTemplateContainerHeight(context2, new FactoryParams(true, false, 2, null));
            Context context3 = getContext();
            Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
            int iProvideTemplateContainerPadding = (int) (fProvideTemplateContainerHeight - (2 * resourcesProvider.provideTemplateContainerPadding(context3)));
            Context context4 = getContext();
            Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
            float fProvideHomeScale = resourcesProvider.provideHomeScale(context4);
            ImageView imageView4 = this.previewLayer;
            if (imageView4 != null) {
                imageView4.setLayoutParams(new FrameLayout.LayoutParams(-1, iProvideTemplateContainerPadding));
            }
            ImageView imageView5 = this.previewLayer;
            if (imageView5 != null) {
                ViewUtil.INSTANCE.setGravity(imageView5, 49);
            }
            ImageView imageView6 = this.previewLayer;
            if (imageView6 != null) {
                imageView6.setClipToOutline(true);
                imageView6.setOutlineProvider(new RoundOutlineProvider(this.previewRadius));
                ViewUtil.INSTANCE.setSmoothCornerEnabled(imageView6, true);
            }
            ImageView imageView7 = new ImageView(getContext());
            imageView7.setId(R.id.kg_template_preview_home);
            this.homeLayer = imageView7;
            imageView7.setLayoutParams(new FrameLayout.LayoutParams((int) (iProvideTemplateContainerWidth * fProvideHomeScale), (int) (iProvideTemplateContainerPadding * fProvideHomeScale)));
            ImageView imageView8 = this.homeLayer;
            if (imageView8 != null) {
                ViewUtil.INSTANCE.setGravity(imageView8, 81);
            }
            ImageView imageView9 = this.homeLayer;
            if (imageView9 != null) {
                imageView9.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            ImageView imageView10 = this.homeLayer;
            if (imageView10 != null) {
                imageView10.setAlpha(imageView10.getResources().getFloat(R.dimen.kg_corss_home_alpha));
                imageView10.setClipToOutline(true);
                imageView10.setOutlineProvider(new RoundOutlineProvider(this.previewRadius));
                ViewUtil.INSTANCE.setSmoothCornerEnabled(imageView10, true);
            }
            ImageView imageView11 = this.homeLayer;
            if (imageView11 != null) {
                imageView11.setTranslationY(-this.homeExtraHeight);
            }
            addView(this.homeLayer);
        }
        View view = new View(getContext()) { // from class: com.miui.keyguard.editor.homepage.view.TemplatePreviewView.init.5
            @Override // android.view.View
            public boolean hasOverlappingRendering() {
                return false;
            }
        };
        view.setId(R.id.kg_template_animation_layer);
        this.animationLayer = view;
        view.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        addView(this.previewLayer);
        addView(this.animationLayer);
    }

    public final void setHomePreviewImage(@Nullable Bitmap bitmap) {
        ImageView imageView = this.homeLayer;
        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

    public final void setLoadingDrawable(@Nullable Drawable drawable) {
        View view = this.animationLayer;
        if (view != null) {
            view.setBackground(drawable);
        }
    }

    public final void setNavigateImage(@Nullable Drawable drawable, @Nullable Drawable drawable2, boolean z) {
        ImageView imageView;
        this.selectDrawable = drawable;
        this.unselectDrawable = drawable2;
        if (DeviceUtil.INSTANCE.isPad() && (imageView = this.previewLayer) != null) {
            imageView.setImageDrawable(drawable);
        }
        switchAnim(z);
    }

    public final void setPreviewImage(@Nullable Bitmap bitmap, @Nullable Drawable drawable, boolean z) {
        ImageView imageView = this.previewLayer;
        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
        }
        ImageView imageView2 = this.previewLayer;
        if (imageView2 != null) {
            imageView2.setForeground(drawable);
        }
        switchAnim(z);
    }

    public final void switchAnim(boolean z) {
        if (z) {
            alphaHideAnimationLayer();
            return;
        }
        View view = this.animationLayer;
        if (view != null) {
            view.setAlpha(0.0f);
        }
    }

    public final void switchHomeLayer(boolean z, boolean z2) {
        ImageView imageView = this.homeLayer;
        if (imageView == null) {
            return;
        }
        int i = z ? 0 : -this.homeExtraHeight;
        Folme.clean(imageView);
        if (z2) {
            Folme.useAt(this.homeLayer).state().to(new AnimState("anim_to").add(ViewProperty.TRANSLATION_Y, i, new long[0]), new AnimConfig().setEase(EaseManager.getStyle(-2, 0.65d, 0.5d)));
            return;
        }
        ImageView imageView2 = this.homeLayer;
        if (imageView2 != null) {
            imageView2.setTranslationY(i);
        }
    }

    public final void switchPreview(final boolean z, boolean z2) {
        Drawable drawable;
        ImageView imageView;
        ImageView imageView2;
        if (DeviceUtil.INSTANCE.isPad() || (drawable = this.selectDrawable) == null || this.unselectDrawable == null) {
            return;
        }
        if (z && (imageView2 = this.previewLayer) != null) {
            imageView2.setImageDrawable(drawable);
        }
        if (z2) {
            ImageView imageView3 = this.previewLayerBackground;
            if (imageView3 != null) {
                AnimState animState = new AnimState("dismiss_start");
                ViewProperty viewProperty = ViewProperty.ALPHA;
                Folme.use((View) imageView3).state().setTo(animState.add(viewProperty, z ? 1.0f : 0.0f, new long[0])).to(new AnimState("dismiss_end").add(viewProperty, z ? 0.0f : 1.0f, new long[0]), TransitionKt.getAnimConfig(0.95f, 0.3f, new TransitionListener() { // from class: com.miui.keyguard.editor.homepage.view.TemplatePreviewView$switchPreview$1$config$1
                    @Override // miuix.animation.listener.TransitionListener
                    public void onComplete(Object obj) {
                        ImageView imageView4;
                        if (z || (imageView4 = this.previewLayer) == null) {
                            return;
                        }
                        imageView4.setImageDrawable(null);
                    }
                }));
                return;
            }
            return;
        }
        ImageView imageView4 = this.previewLayerBackground;
        if (imageView4 != null) {
            imageView4.setAlpha(z ? 0.0f : 1.0f);
        }
        if (z || (imageView = this.previewLayer) == null) {
            return;
        }
        imageView.setImageDrawable(null);
    }
}
