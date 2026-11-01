package com.google.android.appfunctions.schema.photos.v1;

import androidx.appfunctions.AppFunctionSerializable;
import com.google.android.appfunctions.schema.types.v1.DateTime;
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
public final class MediaItem {
    public static final Companion Companion = new Companion(null);
    public final String albumId;
    public final DateTime dateCreated;
    public final String id;
    public final String location;
    public final Boolean starred;
    public final String title;
    public final String type;

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    @Metadata
    public static final class Companion {

        /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
        @Metadata
        @Retention(RetentionPolicy.SOURCE)
        @kotlin.annotation.Retention
        public @interface MediaItemType {
        }

        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public MediaItem(String id, String type, DateTime dateCreated, String str, Boolean bool, String str2, String str3) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(dateCreated, "dateCreated");
        this.id = id;
        this.type = type;
        this.dateCreated = dateCreated;
        this.title = str;
        this.starred = bool;
        this.location = str2;
        this.albumId = str3;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof MediaItem)) {
            return false;
        }
        MediaItem mediaItem = (MediaItem) obj;
        return Intrinsics.areEqual(this.id, mediaItem.id) && Intrinsics.areEqual(this.type, mediaItem.type) && Intrinsics.areEqual(this.dateCreated, mediaItem.dateCreated) && Intrinsics.areEqual(this.title, mediaItem.title) && Intrinsics.areEqual(this.starred, mediaItem.starred) && Intrinsics.areEqual(this.location, mediaItem.location) && Intrinsics.areEqual(this.albumId, mediaItem.albumId);
    }

    public final String getAlbumId() {
        return this.albumId;
    }

    public final DateTime getDateCreated() {
        return this.dateCreated;
    }

    public final String getId() {
        return this.id;
    }

    public final String getLocation() {
        return this.location;
    }

    public final Boolean getStarred() {
        return this.starred;
    }

    public final String getTitle() {
        return this.title;
    }

    public final String getType() {
        return this.type;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.type, this.dateCreated, this.title, this.starred, this.location, this.albumId);
    }
}
