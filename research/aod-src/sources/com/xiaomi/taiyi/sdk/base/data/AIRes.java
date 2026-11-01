package com.xiaomi.taiyi.sdk.base.data;

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
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public class AIRes {
    private String id = "";
    private long version;

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof AIRes)) {
            return false;
        }
        AIRes aIRes = (AIRes) obj;
        return Intrinsics.areEqual(this.id, aIRes.id) && this.version == aIRes.version && Intrinsics.areEqual(obj.getClass(), getClass());
    }

    public final String getId() {
        return this.id;
    }

    public final long getVersion() {
        return this.version;
    }

    public int hashCode() {
        return (Long.hashCode(this.version) * 31) + this.id.hashCode();
    }

    public final void setId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.id = str;
    }

    public final void setVersion(long j) {
        this.version = j;
    }
}
