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
public final class DeviceStateItemMetadata {
    public final String hintText;
    public final String key;
    public final LocalizedString name;
    public final String possibleValues;
    public final String purpose;
    public final String sensitivity;
    public final Boolean writable;

    public DeviceStateItemMetadata(String key, String purpose, LocalizedString localizedString, String str, Boolean bool, String str2, String str3) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(purpose, "purpose");
        this.key = key;
        this.purpose = purpose;
        this.name = localizedString;
        this.sensitivity = str;
        this.writable = bool;
        this.possibleValues = str2;
        this.hintText = str3;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DeviceStateItemMetadata)) {
            return false;
        }
        DeviceStateItemMetadata deviceStateItemMetadata = (DeviceStateItemMetadata) obj;
        return Intrinsics.areEqual(this.key, deviceStateItemMetadata.key) && Intrinsics.areEqual(this.purpose, deviceStateItemMetadata.purpose) && Intrinsics.areEqual(this.name, deviceStateItemMetadata.name) && Intrinsics.areEqual(this.sensitivity, deviceStateItemMetadata.sensitivity) && Intrinsics.areEqual(this.writable, deviceStateItemMetadata.writable) && Intrinsics.areEqual(this.possibleValues, deviceStateItemMetadata.possibleValues) && Intrinsics.areEqual(this.hintText, deviceStateItemMetadata.hintText);
    }

    public final String getHintText() {
        return this.hintText;
    }

    public final String getKey() {
        return this.key;
    }

    public final LocalizedString getName() {
        return this.name;
    }

    public final String getPossibleValues() {
        return this.possibleValues;
    }

    public final String getPurpose() {
        return this.purpose;
    }

    public final String getSensitivity() {
        return this.sensitivity;
    }

    public final Boolean getWritable() {
        return this.writable;
    }

    public int hashCode() {
        return Objects.hash(this.key, this.purpose, this.name, this.sensitivity, this.writable, this.possibleValues, this.hintText);
    }
}
