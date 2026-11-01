package com.google.android.appfunctions.schema.devicestate.v1;

import androidx.appfunctions.AppFunctionSerializable;
import java.util.List;
import java.util.Objects;
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
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
@AppFunctionSerializable
@Metadata
public final class GetDeviceStateItemParams {
    public final List itemizationKeys;
    public final String key;

    public GetDeviceStateItemParams(String key, List itemizationKeys) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(itemizationKeys, "itemizationKeys");
        this.key = key;
        this.itemizationKeys = itemizationKeys;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GetDeviceStateItemParams)) {
            return false;
        }
        GetDeviceStateItemParams getDeviceStateItemParams = (GetDeviceStateItemParams) obj;
        return Intrinsics.areEqual(this.key, getDeviceStateItemParams.key) && Intrinsics.areEqual(this.itemizationKeys, getDeviceStateItemParams.itemizationKeys);
    }

    public final List getItemizationKeys() {
        return this.itemizationKeys;
    }

    public final String getKey() {
        return this.key;
    }

    public int hashCode() {
        return Objects.hash(this.key, this.itemizationKeys);
    }
}
