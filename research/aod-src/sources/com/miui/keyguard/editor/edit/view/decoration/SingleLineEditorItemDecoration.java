package com.miui.keyguard.editor.edit.view.decoration;

import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.R;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
/* JADX INFO: compiled from: SingleLineEditorItemDecoration.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SingleLineEditorItemDecoration extends BaseItemDecoration {
    public final int firstAndLastItemOffset;
    public final Integer otherItemOffset;

    public SingleLineEditorItemDecoration(int i, Integer num) {
        this.firstAndLastItemOffset = i;
        this.otherItemOffset = num;
    }

    public /* synthetic */ SingleLineEditorItemDecoration(int i, Integer num, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? R.dimen.kg_editor_first_and_last_item_offset : i, (i2 & 2) != 0 ? null : num);
    }

    @Override // com.miui.keyguard.editor.edit.view.decoration.BaseItemDecoration
    public void setItemOffsets(View view, RecyclerView parent, Rect outRect, RecyclerView.LayoutManager layoutManager, int i, int i2, Resources resources) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(outRect, "outRect");
        Intrinsics.checkNotNullParameter(layoutManager, "layoutManager");
        Intrinsics.checkNotNullParameter(resources, "resources");
        Integer num = this.otherItemOffset;
        int dimensionPixelOffset = 0;
        int dimensionPixelOffset2 = num != null ? resources.getDimensionPixelOffset(num.intValue()) : 0;
        if (i == 0) {
            dimensionPixelOffset = resources.getDimensionPixelOffset(this.firstAndLastItemOffset);
        } else if (i == i2 - 1) {
            dimensionPixelOffset2 = resources.getDimensionPixelOffset(this.firstAndLastItemOffset);
        }
        setItemOffsetsInternal(outRect, dimensionPixelOffset, 0, dimensionPixelOffset2, 0, resources);
    }
}
