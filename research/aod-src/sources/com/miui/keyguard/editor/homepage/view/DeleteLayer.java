package com.miui.keyguard.editor.homepage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.utils.BlendColorInfo;
import com.miui.keyguard.editor.utils.BlendMode;
import com.miui.keyguard.editor.utils.ViewUtil;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
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
    */
/* JADX INFO: compiled from: DeleteLayer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class DeleteLayer extends FrameLayout {
    public final float blurRadius;
    public final int customButtonBlendColor1;
    public final int customButtonBlendColor2;
    public final View deleteButton;
    public final int deleteButtonBlendColor1;
    public final int deleteButtonBlendColor2;
    public final float deleteButtonRadius;
    public final int deleteLayerBackgroundColor;
    public final float previewRadius;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    @JvmOverloads
    public DeleteLayer(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public DeleteLayer(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        this.previewRadius = resourcesProvider.provideTemplateOutlineRadius(context);
        this.customButtonBlendColor1 = resourcesProvider.provideCustomButtonBlendColor1(context);
        this.customButtonBlendColor2 = resourcesProvider.provideCustomButtonBlendColor2(context);
        this.deleteButtonRadius = resourcesProvider.provideDeleteButtonRadius(context);
        this.deleteButtonBlendColor1 = resourcesProvider.provideDeleteButtonBlendColor1(context);
        this.deleteButtonBlendColor2 = resourcesProvider.provideDeleteButtonBlendColor2(context);
        this.blurRadius = resourcesProvider.provideBlurRadius(context);
        this.deleteLayerBackgroundColor = resourcesProvider.provideDeleteLayerBackgroundColor(context);
        ImageView imageView = new ImageView(context);
        imageView.setId(R.id.button_delete);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(imageView.getResources().getDimensionPixelSize(R.dimen.kg_main_item_delete_button_width), imageView.getResources().getDimensionPixelSize(R.dimen.kg_main_item_delete_button_height));
        layoutParams.gravity = 17;
        imageView.setLayoutParams(layoutParams);
        imageView.setClickable(true);
        int dimensionPixelSize = imageView.getResources().getDimensionPixelSize(R.dimen.kg_main_item_delete_padding);
        imageView.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageResource(R.drawable.kg_icon_template_delete);
        imageView.setContentDescription(imageView.getResources().getString(R.string.kg_main_item_delete_button_text));
        this.deleteButton = imageView;
        setContentDescription(getResources().getString(R.string.kg_template_preview_content_description));
        addView(imageView);
    }

    public /* synthetic */ DeleteLayer(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public final void initialize() {
        ViewUtil viewUtil = ViewUtil.INSTANCE;
        ViewUtil.setBackgroundBlurModeWhenBlurEnabled$default(viewUtil, this, 0.0f, 0, 3, null);
        float f = this.previewRadius;
        int i = this.customButtonBlendColor1;
        BlendMode blendMode = BlendMode.SRC_OVER;
        ViewUtil.decorateBackground$default(viewUtil, this, f, new BlendColorInfo[]{new BlendColorInfo(i, blendMode), new BlendColorInfo(this.customButtonBlendColor2, BlendMode.COLOR_BURN)}, this.deleteLayerBackgroundColor, false, 0.0f, 24, null);
        ViewUtil.decorateBackground$default(viewUtil, this.deleteButton, this.deleteButtonRadius, new BlendColorInfo[]{new BlendColorInfo(this.deleteButtonBlendColor1, BlendMode.COLOR_DODGE), new BlendColorInfo(this.deleteButtonBlendColor2, blendMode)}, viewUtil.getColor(this, R.color.kg_main_delete_button_background_for_lite), false, 0.0f, 24, null);
    }
}
