package com.miui.keyguard.editor.edit.view;

import android.view.View;
import android.widget.ImageView;
import com.miui.keyguard.editor.R;
import java.util.List;
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
/* JADX INFO: compiled from: DrawableSelectorAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public abstract class DrawableSelectorAdapter extends StyleSelectorAdapter<Integer> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DrawableSelectorAdapter(List data) {
        super(data);
        Intrinsics.checkNotNullParameter(data, "data");
    }

    @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
    public int itemLayoutId() {
        return R.layout.kg_layout_drawable_selector_item;
    }

    @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
    public void onBind(View view, int i) {
        super.onBind(view, i);
        if (view == null) {
            return;
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.drawable_selector_item_icon);
        imageView.setImageResource(((Number) getData().get(i)).intValue());
        Intrinsics.checkNotNull(imageView);
        onBindDrawable(imageView);
        imageView.setContentDescription(view.getContext().getString(R.string.kg_typesetting_string) + (i + 1));
    }

    public void onBindDrawable(ImageView imageView) {
        Intrinsics.checkNotNullParameter(imageView, "imageView");
    }
}
