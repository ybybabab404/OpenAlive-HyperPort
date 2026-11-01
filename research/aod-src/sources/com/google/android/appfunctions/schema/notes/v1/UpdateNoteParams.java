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
public final class UpdateNoteParams {
    public final SetField attachments;
    public final SetField content;
    public final SetField folderId;
    public final String noteId;
    public final SetField title;

    public UpdateNoteParams(String noteId, SetField setField, SetField setField2, SetField setField3, SetField setField4) {
        Intrinsics.checkNotNullParameter(noteId, "noteId");
        this.noteId = noteId;
        this.title = setField;
        this.content = setField2;
        this.attachments = setField3;
        this.folderId = setField4;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof UpdateNoteParams)) {
            return false;
        }
        UpdateNoteParams updateNoteParams = (UpdateNoteParams) obj;
        return Intrinsics.areEqual(this.noteId, updateNoteParams.noteId) && Intrinsics.areEqual(this.title, updateNoteParams.title) && Intrinsics.areEqual(this.content, updateNoteParams.content) && Intrinsics.areEqual(this.attachments, updateNoteParams.attachments) && Intrinsics.areEqual(this.folderId, updateNoteParams.folderId);
    }

    public final SetField getAttachments() {
        return this.attachments;
    }

    public final SetField getContent() {
        return this.content;
    }

    public final SetField getFolderId() {
        return this.folderId;
    }

    public final String getNoteId() {
        return this.noteId;
    }

    public final SetField getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.noteId, this.title, this.content, this.attachments, this.folderId);
    }
}
