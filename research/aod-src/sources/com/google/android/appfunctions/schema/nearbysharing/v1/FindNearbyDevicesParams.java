package com.google.android.appfunctions.schema.nearbysharing.v1;

import androidx.appfunctions.AppFunctionSerializable;
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
public final class FindNearbyDevicesParams {
    public final String deviceType;
    public final Boolean isMyDevice;
    public final int maxCount;
    public final Long maxSearchTimeMillis;
    public final String query;

    public FindNearbyDevicesParams(String str, String str2, Boolean bool, Long l, int i) {
        this.query = str;
        this.deviceType = str2;
        this.isMyDevice = bool;
        this.maxSearchTimeMillis = l;
        this.maxCount = i;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FindNearbyDevicesParams)) {
            return false;
        }
        FindNearbyDevicesParams findNearbyDevicesParams = (FindNearbyDevicesParams) obj;
        return Intrinsics.areEqual(this.query, findNearbyDevicesParams.query) && Intrinsics.areEqual(this.deviceType, findNearbyDevicesParams.deviceType) && Intrinsics.areEqual(this.isMyDevice, findNearbyDevicesParams.isMyDevice) && Intrinsics.areEqual(this.maxSearchTimeMillis, findNearbyDevicesParams.maxSearchTimeMillis) && this.maxCount == findNearbyDevicesParams.maxCount;
    }

    public final String getDeviceType() {
        return this.deviceType;
    }

    public final int getMaxCount() {
        return this.maxCount;
    }

    public final Long getMaxSearchTimeMillis() {
        return this.maxSearchTimeMillis;
    }

    public final String getQuery() {
        return this.query;
    }

    public int hashCode() {
        return Objects.hash(this.query, this.deviceType, this.isMyDevice, this.maxSearchTimeMillis, Integer.valueOf(this.maxCount));
    }

    public final Boolean isMyDevice() {
        return this.isMyDevice;
    }
}
