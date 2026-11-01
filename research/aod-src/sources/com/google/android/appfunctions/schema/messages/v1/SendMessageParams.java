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
public final class SendMessageParams {
    public final String body;
    public final List messageAttachments;
    public final List recipientsIds;

    public SendMessageParams(String body, List recipientsIds, List messageAttachments) {
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(recipientsIds, "recipientsIds");
        Intrinsics.checkNotNullParameter(messageAttachments, "messageAttachments");
        this.body = body;
        this.recipientsIds = recipientsIds;
        this.messageAttachments = messageAttachments;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SendMessageParams)) {
            return false;
        }
        SendMessageParams sendMessageParams = (SendMessageParams) obj;
        return Intrinsics.areEqual(this.body, sendMessageParams.body) && Intrinsics.areEqual(this.recipientsIds, sendMessageParams.recipientsIds) && Intrinsics.areEqual(this.messageAttachments, sendMessageParams.messageAttachments);
    }

    public final String getBody() {
        return this.body;
    }

    public final List getMessageAttachments() {
        return this.messageAttachments;
    }

    public final List getRecipientsIds() {
        return this.recipientsIds;
    }

    public int hashCode() {
        return Objects.hash(this.body, this.recipientsIds, this.messageAttachments);
    }
}
