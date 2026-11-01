package com.miui.keyguard.editor.homepage.view.adapter;

import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: AdapterAndHolderImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class PreviewTemplateAdapter extends TemplateAdapter {
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: AdapterAndHolderImpl.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public PreviewTemplateAdapter() {
        super(false, 1, null);
    }

    public final boolean canLoop() {
        return getMData().size() >= 3;
    }

    public final int getInitialPosition() {
        if (canLoop()) {
            return 5000 - (5000 % getMData().size());
        }
        return 0;
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseAdapter
    public TemplateItemBean getItem(int i) {
        return (TemplateItemBean) super.getItem(i % getMData().size());
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (canLoop()) {
            return 10000;
        }
        return getMData().size();
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.TemplateAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        TemplateItemBean item = getItem(i);
        if (item != null) {
            return item.getItemType();
        }
        return -1;
    }

    public final int getRealPosition(int i) {
        return i % getMData().size();
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseViewHolder holder, int i) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        holder.setX(i);
        holder.setY(getY());
        holder.itemView.setTag(R.id.kg_template_holder, holder);
        holder.onViewHolderBound(getItem(getRealPosition(i)), getRealPosition(i));
    }
}
