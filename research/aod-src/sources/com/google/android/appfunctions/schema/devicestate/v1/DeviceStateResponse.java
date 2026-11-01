package com.google.android.appfunctions.schema.devicestate.v1;

import androidx.appfunctions.AppFunctionSerializable;
import java.util.List;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
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
public final class DeviceStateResponse {
    public final String deviceLocale;
    public final String globalHintText;
    public final List perScreenDeviceStates;

    public DeviceStateResponse(List perScreenDeviceStates, String deviceLocale, String str) {
        Intrinsics.checkNotNullParameter(perScreenDeviceStates, "perScreenDeviceStates");
        Intrinsics.checkNotNullParameter(deviceLocale, "deviceLocale");
        this.perScreenDeviceStates = perScreenDeviceStates;
        this.deviceLocale = deviceLocale;
        this.globalHintText = str;
    }

    public /* synthetic */ DeviceStateResponse(List list, String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CollectionsKt.emptyList() : list, str, (i & 4) != 0 ? null : str2);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DeviceStateResponse)) {
            return false;
        }
        DeviceStateResponse deviceStateResponse = (DeviceStateResponse) obj;
        return Intrinsics.areEqual(this.perScreenDeviceStates, deviceStateResponse.perScreenDeviceStates) && Intrinsics.areEqual(this.deviceLocale, deviceStateResponse.deviceLocale) && Intrinsics.areEqual(this.globalHintText, deviceStateResponse.globalHintText);
    }

    public final String getDeviceLocale() {
        return this.deviceLocale;
    }

    public final String getGlobalHintText() {
        return this.globalHintText;
    }

    public final List getPerScreenDeviceStates() {
        return this.perScreenDeviceStates;
    }

    public int hashCode() {
        return Objects.hash(this.perScreenDeviceStates, this.deviceLocale, this.globalHintText);
    }
}
