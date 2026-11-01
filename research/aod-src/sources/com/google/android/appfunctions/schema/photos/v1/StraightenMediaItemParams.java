package com.google.android.appfunctions.schema.photos.v1;

import android.net.Uri;
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
public final class StraightenMediaItemParams {
    public final Boolean autoStraighten;
    public final String mediaItemId;
    public final Uri mediaItemUri;
    public final Double straightenDegrees;

    public StraightenMediaItemParams(String str, Uri uri, Boolean bool, Double d) {
        this.mediaItemId = str;
        this.mediaItemUri = uri;
        this.autoStraighten = bool;
        this.straightenDegrees = d;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof StraightenMediaItemParams)) {
            return false;
        }
        StraightenMediaItemParams straightenMediaItemParams = (StraightenMediaItemParams) obj;
        return Intrinsics.areEqual(this.mediaItemId, straightenMediaItemParams.mediaItemId) && Intrinsics.areEqual(this.mediaItemUri, straightenMediaItemParams.mediaItemUri) && Intrinsics.areEqual(this.autoStraighten, straightenMediaItemParams.autoStraighten) && Intrinsics.areEqual(this.straightenDegrees, straightenMediaItemParams.straightenDegrees);
    }

    public final Boolean getAutoStraighten() {
        return this.autoStraighten;
    }

    public final String getMediaItemId() {
        return this.mediaItemId;
    }

    public final Uri getMediaItemUri() {
        return this.mediaItemUri;
    }

    public final Double getStraightenDegrees() {
        return this.straightenDegrees;
    }

    public int hashCode() {
        return Objects.hash(this.mediaItemId, this.mediaItemUri, this.autoStraighten, this.straightenDegrees);
    }
}
