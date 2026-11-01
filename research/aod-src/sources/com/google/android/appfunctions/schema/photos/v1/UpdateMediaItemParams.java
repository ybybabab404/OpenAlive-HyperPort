package com.google.android.appfunctions.schema.photos.v1;

import androidx.appfunctions.AppFunctionSerializable;
import com.google.android.appfunctions.schema.types.v1.SetField;
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
public final class UpdateMediaItemParams {
    public final SetField albumId;
    public final String mediaItemId;
    public final SetField starred;
    public final SetField title;

    public UpdateMediaItemParams(String mediaItemId, SetField setField, SetField setField2, SetField setField3) {
        Intrinsics.checkNotNullParameter(mediaItemId, "mediaItemId");
        this.mediaItemId = mediaItemId;
        this.title = setField;
        this.starred = setField2;
        this.albumId = setField3;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof UpdateMediaItemParams)) {
            return false;
        }
        UpdateMediaItemParams updateMediaItemParams = (UpdateMediaItemParams) obj;
        return Intrinsics.areEqual(this.mediaItemId, updateMediaItemParams.mediaItemId) && Intrinsics.areEqual(this.title, updateMediaItemParams.title) && Intrinsics.areEqual(this.starred, updateMediaItemParams.starred) && Intrinsics.areEqual(this.albumId, updateMediaItemParams.albumId);
    }

    public final SetField getAlbumId() {
        return this.albumId;
    }

    public final String getMediaItemId() {
        return this.mediaItemId;
    }

    public final SetField getStarred() {
        return this.starred;
    }

    public final SetField getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.mediaItemId, this.title, this.starred, this.albumId);
    }
}
