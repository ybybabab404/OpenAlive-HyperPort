package com.miui.keyguard.editor.edit.view.decoration;

import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.clock.ClassicSelectorBean;
import com.miui.keyguard.editor.edit.view.ClassicTextStyleSelectorAdapter;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
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
/* JADX INFO: compiled from: GridEditorItemDecoration.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class GridEditorItemDecoration extends BaseItemDecoration {
    public final Function1 getLabelOffset;
    public final int itemWidthResId;
    public final int leftAndRightOffsetResId;
    public final int rowGapOffsetResId;
    public final int topAndBottomOffsetResId;

    @Override // com.miui.keyguard.editor.edit.view.decoration.BaseItemDecoration
    public void setItemOffsets(View view, RecyclerView parent, Rect outRect, RecyclerView.LayoutManager layoutManager, int i, int i2, Resources resources) {
        int measuredWidth;
        List data;
        ClassicSelectorBean classicSelectorBean;
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(outRect, "outRect");
        Intrinsics.checkNotNullParameter(layoutManager, "layoutManager");
        Intrinsics.checkNotNullParameter(resources, "resources");
        RecyclerView.Adapter adapter = parent.getAdapter();
        ClassicTextStyleSelectorAdapter classicTextStyleSelectorAdapter = adapter instanceof ClassicTextStyleSelectorAdapter ? (ClassicTextStyleSelectorAdapter) adapter : null;
        int classic_selector_bean_type_normal = (classicTextStyleSelectorAdapter == null || (data = classicTextStyleSelectorAdapter.getData()) == null || (classicSelectorBean = (ClassicSelectorBean) data.get(i)) == null) ? ClassicSelectorBean.Companion.getCLASSIC_SELECTOR_BEAN_TYPE_NORMAL() : classicSelectorBean.getBeanType();
        int dimensionPixelOffset = resources.getDimensionPixelOffset(this.leftAndRightOffsetResId);
        ClassicSelectorBean.Companion companion = ClassicSelectorBean.Companion;
        if (classic_selector_bean_type_normal != companion.getCLASSIC_SELECTOR_BEAN_TYPE_NORMAL()) {
            if (classic_selector_bean_type_normal == companion.getCLASSIC_SELECTOR_BEAN_TYPE_LABEL()) {
                setItemOffsetsInternal(outRect, dimensionPixelOffset, resources.getDimensionPixelOffset(R.dimen.kg_classic_text_editor_item_type_label_margin_top), dimensionPixelOffset, 0, resources);
                return;
            } else {
                setItemOffsetsInternal(outRect, dimensionPixelOffset, 0, dimensionPixelOffset, resources.getDimensionPixelOffset(R.dimen.kg_classic_text_editor_item_type_label_margin_bottom), resources);
                return;
            }
        }
        GridLayoutManager gridLayoutManager = layoutManager instanceof GridLayoutManager ? (GridLayoutManager) layoutManager : null;
        if (gridLayoutManager == null) {
            return;
        }
        int spanCount = gridLayoutManager.getSpanCount();
        int i3 = i2 % spanCount;
        int i4 = (i2 / spanCount) + (i3 + ((((i3 ^ spanCount) & ((-i3) | i3)) >> 31) & spanCount) > 0 ? 1 : 0);
        int iIntValue = i - ((Number) this.getLabelOffset.invoke(Integer.valueOf(i))).intValue();
        int i5 = iIntValue / spanCount;
        int dimensionPixelOffset2 = resources.getDimensionPixelOffset(this.topAndBottomOffsetResId);
        int i6 = i5 == 0 ? dimensionPixelOffset2 : 0;
        if (i5 != i4 - 1) {
            dimensionPixelOffset2 = resources.getDimensionPixelOffset(this.rowGapOffsetResId);
        }
        int dimensionPixelOffset3 = resources.getDimensionPixelOffset(this.itemWidthResId);
        int measuredWidth2 = (parent.getMeasuredWidth() - (dimensionPixelOffset3 * spanCount)) / spanCount;
        int i7 = iIntValue % spanCount;
        int i8 = i7 + ((((i7 ^ spanCount) & ((-i7) | i7)) >> 31) & spanCount);
        if (spanCount > 1) {
            measuredWidth = (i8 == 0 ? dimensionPixelOffset : (i8 * dimensionPixelOffset3) + dimensionPixelOffset) - ((dimensionPixelOffset3 + measuredWidth2) * i8);
        } else {
            measuredWidth = (parent.getMeasuredWidth() - dimensionPixelOffset3) / 2;
        }
        setItemOffsetsInternal(outRect, measuredWidth, i6, spanCount > 1 ? (dimensionPixelOffset + (dimensionPixelOffset3 * i8)) - (((i8 + 1) * dimensionPixelOffset3) + (measuredWidth2 * i8)) : 0, dimensionPixelOffset2, resources);
    }
}
