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
public final class PerScreenDeviceStates {
    public final String description;
    public final List deviceStateItems;
    public final String intentUri;

    public PerScreenDeviceStates(String description, String str, List deviceStateItems) {
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(deviceStateItems, "deviceStateItems");
        this.description = description;
        this.intentUri = str;
        this.deviceStateItems = deviceStateItems;
    }

    public /* synthetic */ PerScreenDeviceStates(String str, String str2, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? CollectionsKt.emptyList() : list);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PerScreenDeviceStates)) {
            return false;
        }
        PerScreenDeviceStates perScreenDeviceStates = (PerScreenDeviceStates) obj;
        return Intrinsics.areEqual(this.description, perScreenDeviceStates.description) && Intrinsics.areEqual(this.intentUri, perScreenDeviceStates.intentUri) && Intrinsics.areEqual(this.deviceStateItems, perScreenDeviceStates.deviceStateItems);
    }

    public final String getDescription() {
        return this.description;
    }

    public final List getDeviceStateItems() {
        return this.deviceStateItems;
    }

    public final String getIntentUri() {
        return this.intentUri;
    }

    public int hashCode() {
        return Objects.hash(this.description, this.intentUri, this.deviceStateItems);
    }
}
