package com.google.android.appfunctions.schema.notes.v1;

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
public final class UpdateFolderParams {
    public final String folderId;
    public final SetField name;

    public UpdateFolderParams(String folderId, SetField setField) {
        Intrinsics.checkNotNullParameter(folderId, "folderId");
        this.folderId = folderId;
        this.name = setField;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof UpdateFolderParams)) {
            return false;
        }
        UpdateFolderParams updateFolderParams = (UpdateFolderParams) obj;
        return Intrinsics.areEqual(this.folderId, updateFolderParams.folderId) && Intrinsics.areEqual(this.name, updateFolderParams.name);
    }

    public final String getFolderId() {
        return this.folderId;
    }

    public final SetField getName() {
        return this.name;
    }

    public int hashCode() {
        return Objects.hash(this.folderId, this.name);
    }
}
