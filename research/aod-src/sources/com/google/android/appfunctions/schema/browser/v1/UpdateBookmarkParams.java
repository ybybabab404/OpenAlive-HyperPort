package com.google.android.appfunctions.schema.browser.v1;

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
public final class UpdateBookmarkParams {
    public final String bookmarkId;
    public final SetField name;
    public final SetField url;

    public UpdateBookmarkParams(String bookmarkId, SetField setField, SetField setField2) {
        Intrinsics.checkNotNullParameter(bookmarkId, "bookmarkId");
        this.bookmarkId = bookmarkId;
        this.name = setField;
        this.url = setField2;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof UpdateBookmarkParams)) {
            return false;
        }
        UpdateBookmarkParams updateBookmarkParams = (UpdateBookmarkParams) obj;
        return Intrinsics.areEqual(this.bookmarkId, updateBookmarkParams.bookmarkId) && Intrinsics.areEqual(this.name, updateBookmarkParams.name) && Intrinsics.areEqual(this.url, updateBookmarkParams.url);
    }

    public final String getBookmarkId() {
        return this.bookmarkId;
    }

    public final SetField getName() {
        return this.name;
    }

    public final SetField getUrl() {
        return this.url;
    }

    public int hashCode() {
        return Objects.hash(this.bookmarkId, this.name, this.url);
    }
}
