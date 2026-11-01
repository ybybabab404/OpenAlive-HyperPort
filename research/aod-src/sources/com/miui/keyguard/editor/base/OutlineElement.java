package com.miui.keyguard.editor.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.homepage.view.PageTransitionLayer;
import com.miui.keyguard.editor.view.RoundOutlineProvider;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

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
final class OutlineElement extends FrameLayout {
    public ImageView element;
    public final int spacing;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OutlineElement(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_main_item_container_padding);
        this.spacing = dimensionPixelSize;
        ImageView imageView = new ImageView(context);
        this.element = imageView;
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageView imageView2 = this.element;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        layoutParams.leftMargin = dimensionPixelSize;
        layoutParams.topMargin = dimensionPixelSize;
        layoutParams.rightMargin = dimensionPixelSize;
        layoutParams.bottomMargin = dimensionPixelSize;
        imageView2.setLayoutParams(layoutParams);
        this.element.setClipToOutline(true);
        this.element.setOutlineProvider(new RoundOutlineProvider(getResources().getDimension(R.dimen.kg_preview_image_corner_radius)));
        addView(this.element);
    }

    public final ImageView getElement() {
        return this.element;
    }

    public final int getSpacing() {
        return this.spacing;
    }

    public final void prepare(PageTransitionLayer layer, Rect previewRect) {
        Intrinsics.checkNotNullParameter(layer, "layer");
        Intrinsics.checkNotNullParameter(previewRect, "previewRect");
        if (Intrinsics.areEqual(getParent(), layer)) {
            layer.removeView(this);
        }
        Rect rect = new Rect(previewRect.left - getSpacing(), previewRect.top - getSpacing(), previewRect.right + getSpacing(), previewRect.bottom + getSpacing());
        setLayoutParams(new ViewGroup.LayoutParams(rect.width(), rect.height()));
        layer.addView(this, rect);
    }

    public final void reset(PageTransitionLayer layer, Rect templateRect, Rect previewRect) {
        Intrinsics.checkNotNullParameter(layer, "layer");
        Intrinsics.checkNotNullParameter(templateRect, "templateRect");
        Intrinsics.checkNotNullParameter(previewRect, "previewRect");
        if (previewRect.width() <= 0 || previewRect.height() <= 0) {
            Log.e("Keyguard-Editor-Transition", "previewRect is empty");
            return;
        }
        prepare(layer, previewRect);
        setPivotX(0.0f);
        setPivotY(0.0f);
        setScaleX(templateRect.width() / previewRect.width());
        setScaleY(templateRect.height() / previewRect.height());
        setTranslationX(templateRect.left - previewRect.left);
        setTranslationY(templateRect.top - previewRect.top);
    }

    public final void setElementBitmap(Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        this.element.setImageBitmap(bitmap);
    }

    public final void setElementForeground(Drawable foreground) {
        Intrinsics.checkNotNullParameter(foreground, "foreground");
        this.element.setForeground(foreground);
    }
}
