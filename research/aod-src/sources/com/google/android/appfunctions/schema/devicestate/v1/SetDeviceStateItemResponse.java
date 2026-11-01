package com.google.android.appfunctions.schema.devicestate.v1;

import androidx.appfunctions.AppFunctionSerializable;
import java.util.Objects;
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
    */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
@AppFunctionSerializable
@Metadata
public final class SetDeviceStateItemResponse {
    public final String currentValue;
    public final String failureReason;
    public final boolean isSuccessful;

    public SetDeviceStateItemResponse(boolean z, String currentValue, String str) {
        Intrinsics.checkNotNullParameter(currentValue, "currentValue");
        this.isSuccessful = z;
        this.currentValue = currentValue;
        this.failureReason = str;
    }

    public /* synthetic */ SetDeviceStateItemResponse(boolean z, String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, str, (i & 4) != 0 ? null : str2);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SetDeviceStateItemResponse)) {
            return false;
        }
        SetDeviceStateItemResponse setDeviceStateItemResponse = (SetDeviceStateItemResponse) obj;
        return this.isSuccessful == setDeviceStateItemResponse.isSuccessful && Intrinsics.areEqual(this.currentValue, setDeviceStateItemResponse.currentValue) && Intrinsics.areEqual(this.failureReason, setDeviceStateItemResponse.failureReason);
    }

    public final String getCurrentValue() {
        return this.currentValue;
    }

    public final String getFailureReason() {
        return this.failureReason;
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.isSuccessful), this.currentValue, this.failureReason);
    }

    public final boolean isSuccessful() {
        return this.isSuccessful;
    }
}
