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
public final class PerScreenMetadata {
    public final String description;
    public final List deviceStateItemsMetadata;
    public final String intentUri;
    public final List itemizationTypes;

    public PerScreenMetadata(String description, String str, List itemizationTypes, List deviceStateItemsMetadata) {
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(itemizationTypes, "itemizationTypes");
        Intrinsics.checkNotNullParameter(deviceStateItemsMetadata, "deviceStateItemsMetadata");
        this.description = description;
        this.intentUri = str;
        this.itemizationTypes = itemizationTypes;
        this.deviceStateItemsMetadata = deviceStateItemsMetadata;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ PerScreenMetadata(String str, String str2, List list, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 4) != 0) {
            list = Collections.EMPTY_LIST;
            Intrinsics.checkNotNullExpressionValue(list, "emptyList(...)");
        }
        if ((i & 8) != 0) {
            list2 = Collections.EMPTY_LIST;
            Intrinsics.checkNotNullExpressionValue(list2, "emptyList(...)");
        }
        this(str, (i & 2) != 0 ? null : str2, list, list2);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PerScreenMetadata)) {
            return false;
        }
        PerScreenMetadata perScreenMetadata = (PerScreenMetadata) obj;
        return Intrinsics.areEqual(this.description, perScreenMetadata.description) && Intrinsics.areEqual(this.intentUri, perScreenMetadata.intentUri) && Intrinsics.areEqual(this.itemizationTypes, perScreenMetadata.itemizationTypes) && Intrinsics.areEqual(this.deviceStateItemsMetadata, perScreenMetadata.deviceStateItemsMetadata);
    }

    public final String getDescription() {
        return this.description;
    }

    public final List getDeviceStateItemsMetadata() {
        return this.deviceStateItemsMetadata;
    }

    public final String getIntentUri() {
        return this.intentUri;
    }

    public final List getItemizationTypes() {
        return this.itemizationTypes;
    }

    public int hashCode() {
        return Objects.hash(this.description, this.intentUri, this.itemizationTypes, this.deviceStateItemsMetadata);
    }
}
