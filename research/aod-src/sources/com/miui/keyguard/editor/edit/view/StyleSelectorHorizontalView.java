package com.miui.keyguard.editor.edit.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.miui.keyguard.editor.R;
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
/* JADX INFO: compiled from: StyleSelectorHorizontalView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class StyleSelectorHorizontalView<T> extends BaseStyleSelectorView<T> {
    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    @JvmOverloads
    public StyleSelectorHorizontalView(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public StyleSelectorHorizontalView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ StyleSelectorHorizontalView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    @Override // com.miui.keyguard.editor.edit.view.BaseStyleSelectorView
    public EditorDialogHorizontalRecyclerView createItemList() {
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        EditorDialogHorizontalRecyclerView editorDialogHorizontalRecyclerView = new EditorDialogHorizontalRecyclerView(context, null, 0, 6, null);
        editorDialogHorizontalRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        Resources resources = editorDialogHorizontalRecyclerView.getResources();
        int i = R.dimen.kg_classic_clock_font_margin_left;
        editorDialogHorizontalRecyclerView.setPadding(resources.getDimensionPixelSize(i), 0, editorDialogHorizontalRecyclerView.getResources().getDimensionPixelSize(i), 0);
        editorDialogHorizontalRecyclerView.setClipToPadding(false);
        return editorDialogHorizontalRecyclerView;
    }
}
