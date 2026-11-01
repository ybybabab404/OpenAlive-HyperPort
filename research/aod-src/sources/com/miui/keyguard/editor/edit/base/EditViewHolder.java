package com.miui.keyguard.editor.edit.base;

import android.graphics.Point;
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
/* JADX INFO: compiled from: CombineEditView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class EditViewHolder {
    public final BaseTemplateView itemView;
    public final int viewType;

    public EditViewHolder(BaseTemplateView itemView, int i) {
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        this.itemView = itemView;
        this.viewType = i;
    }

    public final Point getCenter() {
        return new Point(this.itemView.getLeft() + (this.itemView.getWidth() / 2), this.itemView.getTop() + (this.itemView.getHeight() / 2));
    }

    public final BaseTemplateView getItemView() {
        return this.itemView;
    }

    public final int getViewType() {
        return this.viewType;
    }
}
