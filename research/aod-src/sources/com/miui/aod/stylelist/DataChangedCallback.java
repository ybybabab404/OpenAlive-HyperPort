package com.miui.aod.stylelist;

import androidx.recyclerview.widget.RecyclerView;
import java.lang.ref.WeakReference;
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
/* JADX INFO: compiled from: DataChangedCallback.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class DataChangedCallback {
    public final WeakReference weakAdapter;

    public DataChangedCallback(RecyclerView.Adapter adapter) {
        Intrinsics.checkNotNullParameter(adapter, "adapter");
        this.weakAdapter = new WeakReference(adapter);
    }

    public final void onDataChanged(int i, boolean z) {
        RecyclerView.Adapter adapter;
        RecyclerView.Adapter adapter2 = (RecyclerView.Adapter) this.weakAdapter.get();
        if (adapter2 != null) {
            adapter2.notifyItemChanged(i, Boolean.TRUE);
        }
        if (z && (adapter = (RecyclerView.Adapter) this.weakAdapter.get()) != null) {
            adapter.notifyItemChanged(i, 1);
        }
        RecyclerView.Adapter adapter3 = (RecyclerView.Adapter) this.weakAdapter.get();
        if (adapter3 != null) {
            adapter3.notifyItemChanged(i, 2);
        }
    }
}
