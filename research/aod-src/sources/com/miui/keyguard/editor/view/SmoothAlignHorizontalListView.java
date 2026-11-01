package com.miui.keyguard.editor.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.view.EditorDialogHorizontalRecyclerView;
import com.miui.keyguard.editor.utils.ViewUtil;
import java.util.LinkedList;
import kotlin.Metadata;
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
/* JADX INFO: compiled from: SmoothAlignHorizontalListView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SmoothAlignHorizontalListView extends EditorDialogHorizontalRecyclerView {
    public final boolean isRtl;
    public final int listNormalColor;
    public final int listSelectColor;
    public final LinkedList scrolledListenerOnce;
    public final int titleItemPaddingDiff;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SmoothAlignHorizontalListView(@NotNull Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SmoothAlignHorizontalListView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.smoothAlignListViewStyle);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SmoothAlignHorizontalListView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.scrolledListenerOnce = new LinkedList();
        this.titleItemPaddingDiff = Math.abs(context.getResources().getDimensionPixelSize(R.dimen.kg_filter_title_padding_end) - context.getResources().getDimensionPixelSize(R.dimen.kg_filter_title_padding_start));
        this.isRtl = ViewUtil.INSTANCE.isRtl(this);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SmoothAlignListView, i, 0);
        this.listSelectColor = typedArrayObtainStyledAttributes.getColor(R.styleable.SmoothAlignListView_selectColor, -16776961);
        this.listNormalColor = typedArrayObtainStyledAttributes.getColor(R.styleable.SmoothAlignListView_normalColor, -16777216);
        typedArrayObtainStyledAttributes.recycle();
    }

    public final int getListNormalColor() {
        return this.listNormalColor;
    }

    public final int getListSelectColor() {
        return this.listSelectColor;
    }
}
