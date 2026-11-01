package com.google.android.appfunctions.schema.notes.v1;

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
public final class CreateNoteParams {
    public final List attachments;
    public final String content;
    public final String externalId;
    public final String folderId;
    public final String title;

    public CreateNoteParams(String title, String str, List attachments, String str2, String str3) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(attachments, "attachments");
        this.title = title;
        this.content = str;
        this.attachments = attachments;
        this.folderId = str2;
        this.externalId = str3;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CreateNoteParams)) {
            return false;
        }
        CreateNoteParams createNoteParams = (CreateNoteParams) obj;
        return Intrinsics.areEqual(this.title, createNoteParams.title) && Intrinsics.areEqual(this.content, createNoteParams.content) && Intrinsics.areEqual(this.attachments, createNoteParams.attachments) && Intrinsics.areEqual(this.folderId, createNoteParams.folderId) && Intrinsics.areEqual(this.externalId, createNoteParams.externalId);
    }

    public final List getAttachments() {
        return this.attachments;
    }

    public final String getContent() {
        return this.content;
    }

    public final String getExternalId() {
        return this.externalId;
    }

    public final String getFolderId() {
        return this.folderId;
    }

    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.title, this.content, this.attachments, this.folderId, this.externalId);
    }
}
