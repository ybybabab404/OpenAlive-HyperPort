package com.google.android.appfunctions.schema.nearbysharing.v1;

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
public final class ShareFilesToNearbyDeviceParams {
    public final List files;
    public final String targetDeviceId;

    public ShareFilesToNearbyDeviceParams(String targetDeviceId, List files) {
        Intrinsics.checkNotNullParameter(targetDeviceId, "targetDeviceId");
        Intrinsics.checkNotNullParameter(files, "files");
        this.targetDeviceId = targetDeviceId;
        this.files = files;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ShareFilesToNearbyDeviceParams)) {
            return false;
        }
        ShareFilesToNearbyDeviceParams shareFilesToNearbyDeviceParams = (ShareFilesToNearbyDeviceParams) obj;
        return Intrinsics.areEqual(this.targetDeviceId, shareFilesToNearbyDeviceParams.targetDeviceId) && Intrinsics.areEqual(this.files, shareFilesToNearbyDeviceParams.files);
    }

    public final List getFiles() {
        return this.files;
    }

    public final String getTargetDeviceId() {
        return this.targetDeviceId;
    }

    public int hashCode() {
        return Objects.hash(this.targetDeviceId, this.files);
    }
}
