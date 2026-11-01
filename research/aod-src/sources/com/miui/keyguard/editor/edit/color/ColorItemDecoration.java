package com.miui.keyguard.editor.edit.color;

import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.edit.view.decoration.BaseItemDecoration;
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
/* JADX INFO: compiled from: ColorItemDecoration.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ColorItemDecoration extends BaseItemDecoration {
    public final int firstOrLastSpace;
    public final int horizontalSpace;
    public final int verticalSpace;

    @Override // com.miui.keyguard.editor.edit.view.decoration.BaseItemDecoration
    public void setItemOffsets(View view, RecyclerView parent, Rect outRect, RecyclerView.LayoutManager layoutManager, int i, int i2, Resources resources) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(outRect, "outRect");
        Intrinsics.checkNotNullParameter(layoutManager, "layoutManager");
        Intrinsics.checkNotNullParameter(resources, "resources");
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            if (gridLayoutManager.getOrientation() != 0) {
                return;
            }
            int spanCount = gridLayoutManager.getSpanCount();
            int i3 = i / spanCount;
            int i4 = i % spanCount;
            setItemOffsetsInternal(outRect, i3 > 0 ? this.horizontalSpace : this.firstOrLastSpace, i4 > 0 ? this.verticalSpace : 0, i3 + 1 == i2 / spanCount ? this.firstOrLastSpace : 0, 0, resources);
        }
    }
}
