package com.miui.keyguard.editor.homepage.view.generator;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: BaseItemViewFactoryImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class FactoryParams {
    public final boolean isHistory;
    public final boolean isPreset;

    public FactoryParams(boolean z, boolean z2) {
        this.isPreset = z;
        this.isHistory = z2;
    }

    public /* synthetic */ FactoryParams(boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, (i & 2) != 0 ? false : z2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FactoryParams)) {
            return false;
        }
        FactoryParams factoryParams = (FactoryParams) obj;
        return this.isPreset == factoryParams.isPreset && this.isHistory == factoryParams.isHistory;
    }

    public int hashCode() {
        return (Boolean.hashCode(this.isPreset) * 31) + Boolean.hashCode(this.isHistory);
    }

    public final boolean isHistory() {
        return this.isHistory;
    }

    public final boolean isPreset() {
        return this.isPreset;
    }

    public String toString() {
        return "FactoryParams(isPreset=" + this.isPreset + ", isHistory=" + this.isHistory + ")";
    }
}
