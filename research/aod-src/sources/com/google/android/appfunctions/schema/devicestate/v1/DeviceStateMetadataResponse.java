package com.google.android.appfunctions.schema.devicestate.v1;

import androidx.appfunctions.AppFunctionSerializable;
import java.util.Collections;
import java.util.List;
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
public final class DeviceStateMetadataResponse {
    public final String defaultValue;
    public final String deviceLocale;
    public final String globalHintText;
    public final List itemizationTypes;
    public final List perScreenMetadata;

    public DeviceStateMetadataResponse(List perScreenMetadata, String deviceLocale, String str, List itemizationTypes, String str2) {
        Intrinsics.checkNotNullParameter(perScreenMetadata, "perScreenMetadata");
        Intrinsics.checkNotNullParameter(deviceLocale, "deviceLocale");
        Intrinsics.checkNotNullParameter(itemizationTypes, "itemizationTypes");
        this.perScreenMetadata = perScreenMetadata;
        this.deviceLocale = deviceLocale;
        this.globalHintText = str;
        this.itemizationTypes = itemizationTypes;
        this.defaultValue = str2;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ DeviceStateMetadataResponse(List list, String str, String str2, List list2, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            list = Collections.EMPTY_LIST;
            Intrinsics.checkNotNullExpressionValue(list, "emptyList(...)");
        }
        str2 = (i & 4) != 0 ? null : str2;
        if ((i & 8) != 0) {
            list2 = Collections.EMPTY_LIST;
            Intrinsics.checkNotNullExpressionValue(list2, "emptyList(...)");
        }
        this(list, str, str2, list2, (i & 16) != 0 ? null : str3);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DeviceStateMetadataResponse)) {
            return false;
        }
        DeviceStateMetadataResponse deviceStateMetadataResponse = (DeviceStateMetadataResponse) obj;
        return Intrinsics.areEqual(this.perScreenMetadata, deviceStateMetadataResponse.perScreenMetadata) && Intrinsics.areEqual(this.deviceLocale, deviceStateMetadataResponse.deviceLocale) && Intrinsics.areEqual(this.globalHintText, deviceStateMetadataResponse.globalHintText) && Intrinsics.areEqual(this.itemizationTypes, deviceStateMetadataResponse.itemizationTypes) && Intrinsics.areEqual(this.defaultValue, deviceStateMetadataResponse.defaultValue);
    }

    public final String getDefaultValue() {
        return this.defaultValue;
    }

    public final String getDeviceLocale() {
        return this.deviceLocale;
    }

    public final String getGlobalHintText() {
        return this.globalHintText;
    }

    public final List getItemizationTypes() {
        return this.itemizationTypes;
    }

    public final List getPerScreenMetadata() {
        return this.perScreenMetadata;
    }

    public int hashCode() {
        return Objects.hash(this.perScreenMetadata, this.deviceLocale, this.globalHintText, this.itemizationTypes, this.defaultValue);
    }
}
