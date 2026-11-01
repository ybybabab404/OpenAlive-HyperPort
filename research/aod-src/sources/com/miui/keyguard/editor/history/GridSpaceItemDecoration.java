package com.miui.keyguard.editor.history;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.utils.DeviceUtil;
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
/* JADX INFO: compiled from: GridSpaceItemDecoration.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {
    public final boolean includeEdge;
    public final int spacing;
    public final int spanCount;

    public GridSpaceItemDecoration(int i, int i2, boolean z) {
        this.spanCount = i;
        this.spacing = i2;
        this.includeEdge = z;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        Intrinsics.checkNotNullParameter(outRect, "outRect");
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(state, "state");
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        if (childAdapterPosition < 0) {
            childAdapterPosition = 0;
        }
        int i = this.spanCount;
        int i2 = childAdapterPosition % i;
        if (this.includeEdge) {
            int i3 = this.spacing;
            int i4 = i3 - ((i2 * i3) / i);
            int i5 = ((i2 + 1) * i3) / i;
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            Context context = view.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            outRect.left = deviceUtil.isRtl(context) ? i5 : i4;
            Context context2 = view.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            if (!deviceUtil.isRtl(context2)) {
                i4 = i5;
            }
            outRect.right = i4;
            if (childAdapterPosition < this.spanCount) {
                outRect.top = this.spacing;
            }
            outRect.bottom = this.spacing;
            return;
        }
        int i6 = this.spacing;
        int i7 = (i2 * i6) / i;
        int i8 = i6 - (((i2 + 1) * i6) / i);
        DeviceUtil deviceUtil2 = DeviceUtil.INSTANCE;
        Context context3 = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
        outRect.left = deviceUtil2.isRtl(context3) ? i8 : i7;
        Context context4 = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
        if (!deviceUtil2.isRtl(context4)) {
            i7 = i8;
        }
        outRect.right = i7;
        if (childAdapterPosition >= this.spanCount) {
            outRect.top = this.spacing;
        }
    }
}
