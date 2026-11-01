package com.google.android.appfunctions.schema.devicestate.v1;

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
public final class DeviceStateItem {
    public final String hintText;
    public final String jsonValue;
    public final String key;
    public final Long lastUpdatedEpochMillis;
    public final LocalizedString name;
    public final String purpose;

    public DeviceStateItem(String str, String purpose, LocalizedString localizedString, String str2, Long l, String str3) {
        Intrinsics.checkNotNullParameter(purpose, "purpose");
        this.key = str;
        this.purpose = purpose;
        this.name = localizedString;
        this.jsonValue = str2;
        this.lastUpdatedEpochMillis = l;
        this.hintText = str3;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DeviceStateItem)) {
            return false;
        }
        DeviceStateItem deviceStateItem = (DeviceStateItem) obj;
        return Intrinsics.areEqual(this.key, deviceStateItem.key) && Intrinsics.areEqual(this.purpose, deviceStateItem.purpose) && Intrinsics.areEqual(this.name, deviceStateItem.name) && Intrinsics.areEqual(this.jsonValue, deviceStateItem.jsonValue) && Intrinsics.areEqual(this.lastUpdatedEpochMillis, deviceStateItem.lastUpdatedEpochMillis) && Intrinsics.areEqual(this.hintText, deviceStateItem.hintText);
    }

    public final String getHintText() {
        return this.hintText;
    }

    public final String getJsonValue() {
        return this.jsonValue;
    }

    public final String getKey() {
        return this.key;
    }

    public final Long getLastUpdatedEpochMillis() {
        return this.lastUpdatedEpochMillis;
    }

    public final LocalizedString getName() {
        return this.name;
    }

    public final String getPurpose() {
        return this.purpose;
    }

    public int hashCode() {
        return Objects.hash(this.key, this.purpose, this.name, this.jsonValue, this.lastUpdatedEpochMillis, this.hintText);
    }
}
