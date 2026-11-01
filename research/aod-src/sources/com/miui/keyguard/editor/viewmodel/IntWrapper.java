package com.miui.keyguard.editor.viewmodel;

import kotlin.Metadata;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: EditFragmentViewModel.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class IntWrapper {
    public int intValue;
    public boolean onlySet;

    public IntWrapper(int i, boolean z) {
        this.intValue = i;
        this.onlySet = z;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IntWrapper)) {
            return false;
        }
        IntWrapper intWrapper = (IntWrapper) obj;
        return this.intValue == intWrapper.intValue && this.onlySet == intWrapper.onlySet;
    }

    public final int getIntValue() {
        return this.intValue;
    }

    public final boolean getOnlySet() {
        return this.onlySet;
    }

    public int hashCode() {
        return (Integer.hashCode(this.intValue) * 31) + Boolean.hashCode(this.onlySet);
    }

    public final void setIntValue(int i) {
        this.intValue = i;
    }

    public String toString() {
        return "IntWrapper(intValue=" + this.intValue + ", onlySet=" + this.onlySet + ")";
    }
}
