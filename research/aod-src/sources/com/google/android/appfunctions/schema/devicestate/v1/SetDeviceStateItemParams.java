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
public final class SetDeviceStateItemParams {
    public final List itemizationKeys;
    public final String key;
    public final Boolean requestInitiatedWhileUnlocked;
    public final String value;

    public SetDeviceStateItemParams(String key, List itemizationKeys, String value, Boolean bool) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(itemizationKeys, "itemizationKeys");
        Intrinsics.checkNotNullParameter(value, "value");
        this.key = key;
        this.itemizationKeys = itemizationKeys;
        this.value = value;
        this.requestInitiatedWhileUnlocked = bool;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SetDeviceStateItemParams)) {
            return false;
        }
        SetDeviceStateItemParams setDeviceStateItemParams = (SetDeviceStateItemParams) obj;
        return Intrinsics.areEqual(this.key, setDeviceStateItemParams.key) && Intrinsics.areEqual(this.itemizationKeys, setDeviceStateItemParams.itemizationKeys) && Intrinsics.areEqual(this.value, setDeviceStateItemParams.value) && Intrinsics.areEqual(this.requestInitiatedWhileUnlocked, setDeviceStateItemParams.requestInitiatedWhileUnlocked);
    }

    public final List getItemizationKeys() {
        return this.itemizationKeys;
    }

    public final String getKey() {
        return this.key;
    }

    public final Boolean getRequestInitiatedWhileUnlocked() {
        return this.requestInitiatedWhileUnlocked;
    }

    public final String getValue() {
        return this.value;
    }

    public int hashCode() {
        return Objects.hash(this.key, this.itemizationKeys, this.value, this.requestInitiatedWhileUnlocked);
    }
}
