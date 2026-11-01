package com.google.android.appfunctions.schema.nearbysharing.v1;

import androidx.appfunctions.AppFunctionSerializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
@AppFunctionSerializable
@Metadata
public final class NearbyDevice {
    public static final Companion Companion = new Companion(null);
    public final String deviceName;
    public final String deviceType;
    public final Boolean isMyDevice;
    public final String nearbyDeviceId;

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    @Metadata
    public static final class Companion {

        /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
        @Metadata
        @Retention(RetentionPolicy.SOURCE)
        @kotlin.annotation.Retention
        public @interface DeviceType {
        }

        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public NearbyDevice(String nearbyDeviceId, String deviceName, String str, Boolean bool) {
        Intrinsics.checkNotNullParameter(nearbyDeviceId, "nearbyDeviceId");
        Intrinsics.checkNotNullParameter(deviceName, "deviceName");
        this.nearbyDeviceId = nearbyDeviceId;
        this.deviceName = deviceName;
        this.deviceType = str;
        this.isMyDevice = bool;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof NearbyDevice)) {
            return false;
        }
        NearbyDevice nearbyDevice = (NearbyDevice) obj;
        return Intrinsics.areEqual(this.nearbyDeviceId, nearbyDevice.nearbyDeviceId) && Intrinsics.areEqual(this.deviceName, nearbyDevice.deviceName) && Intrinsics.areEqual(this.deviceType, nearbyDevice.deviceType) && Intrinsics.areEqual(this.isMyDevice, nearbyDevice.isMyDevice);
    }

    public final String getDeviceName() {
        return this.deviceName;
    }

    public final String getDeviceType() {
        return this.deviceType;
    }

    public final String getNearbyDeviceId() {
        return this.nearbyDeviceId;
    }

    public int hashCode() {
        return Objects.hash(this.nearbyDeviceId, this.deviceName, this.deviceType, this.isMyDevice);
    }

    public final Boolean isMyDevice() {
        return this.isMyDevice;
    }
}
