package com.google.android.appfunctions.schema.photos.v1;

import android.net.Uri;
import androidx.appfunctions.AppFunctionSerializable;
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
public final class SetMediaItemFilterParams {
    public static final Companion Companion = new Companion(null);
    public final String filter;
    public final String mediaItemId;
    public final Uri mediaItemUri;

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    @Metadata
    public static final class Companion {

        /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
        @Metadata
        @Retention(RetentionPolicy.SOURCE)
        @kotlin.annotation.Retention
        public @interface FilterType {
        }

        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SetMediaItemFilterParams(String str, Uri uri, String filter) {
        Intrinsics.checkNotNullParameter(filter, "filter");
        this.mediaItemId = str;
        this.mediaItemUri = uri;
        this.filter = filter;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SetMediaItemFilterParams)) {
            return false;
        }
        SetMediaItemFilterParams setMediaItemFilterParams = (SetMediaItemFilterParams) obj;
        return Intrinsics.areEqual(this.mediaItemId, setMediaItemFilterParams.mediaItemId) && Intrinsics.areEqual(this.mediaItemUri, setMediaItemFilterParams.mediaItemUri) && Intrinsics.areEqual(this.filter, setMediaItemFilterParams.filter);
    }

    public final String getFilter() {
        return this.filter;
    }

    public final String getMediaItemId() {
        return this.mediaItemId;
    }

    public final Uri getMediaItemUri() {
        return this.mediaItemUri;
    }

    public int hashCode() {
        return Objects.hash(this.mediaItemId, this.mediaItemUri, this.filter);
    }
}
