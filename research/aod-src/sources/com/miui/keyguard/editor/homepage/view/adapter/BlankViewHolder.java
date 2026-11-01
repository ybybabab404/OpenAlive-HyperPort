package com.miui.keyguard.editor.homepage.view.adapter;

import android.content.Context;
import android.view.View;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback;
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
/* JADX INFO: compiled from: AdapterAndHolderImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class BlankViewHolder extends BaseViewHolder<TemplateItemBean, CrossViewPagerCallback> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BlankViewHolder(Context context) {
        super(new View(context));
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public View getTargetContentView() {
        return null;
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onPageTransformed(int i, float f, float f2, int i2) {
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onViewHolderBound(TemplateItemBean templateItemBean, int i) {
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onViewHolderCreated(View itemView) {
        Intrinsics.checkNotNullParameter(itemView, "itemView");
    }
}
