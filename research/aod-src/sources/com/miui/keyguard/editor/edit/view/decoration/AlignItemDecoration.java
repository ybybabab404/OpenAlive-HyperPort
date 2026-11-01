package com.miui.keyguard.editor.edit.view.decoration;

import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
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
/* JADX INFO: compiled from: AlignItemDecoration.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class AlignItemDecoration extends BaseItemDecoration {
    public final int firstAndLastItemOffset;
    public final Integer otherItemOffset;

    @Override // com.miui.keyguard.editor.edit.view.decoration.BaseItemDecoration
    public void setItemOffsets(View view, RecyclerView parent, Rect outRect, RecyclerView.LayoutManager layoutManager, int i, int i2, Resources resources) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(outRect, "outRect");
        Intrinsics.checkNotNullParameter(layoutManager, "layoutManager");
        Intrinsics.checkNotNullParameter(resources, "resources");
        Integer num = this.otherItemOffset;
        int dimensionPixelOffset = num != null ? resources.getDimensionPixelOffset(num.intValue()) : 0;
        if (i != 0 && i == i2 - 1) {
            dimensionPixelOffset = resources.getDimensionPixelOffset(this.firstAndLastItemOffset);
        }
        setItemOffsetsInternal(outRect, 0, 0, dimensionPixelOffset, 0, resources);
    }
}
