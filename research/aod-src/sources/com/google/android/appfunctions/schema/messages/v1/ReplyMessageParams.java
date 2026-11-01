package com.google.android.appfunctions.schema.messages.v1;

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
public final class ReplyMessageParams {
    public final String body;
    public final List messageAttachments;
    public final String originalMessageId;

    public ReplyMessageParams(String originalMessageId, String body, List messageAttachments) {
        Intrinsics.checkNotNullParameter(originalMessageId, "originalMessageId");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(messageAttachments, "messageAttachments");
        this.originalMessageId = originalMessageId;
        this.body = body;
        this.messageAttachments = messageAttachments;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ReplyMessageParams)) {
            return false;
        }
        ReplyMessageParams replyMessageParams = (ReplyMessageParams) obj;
        return Intrinsics.areEqual(this.originalMessageId, replyMessageParams.originalMessageId) && Intrinsics.areEqual(this.body, replyMessageParams.body) && Intrinsics.areEqual(this.messageAttachments, replyMessageParams.messageAttachments);
    }

    public final String getBody() {
        return this.body;
    }

    public final List getMessageAttachments() {
        return this.messageAttachments;
    }

    public final String getOriginalMessageId() {
        return this.originalMessageId;
    }

    public int hashCode() {
        return Objects.hash(this.originalMessageId, this.body, this.messageAttachments);
    }
}
