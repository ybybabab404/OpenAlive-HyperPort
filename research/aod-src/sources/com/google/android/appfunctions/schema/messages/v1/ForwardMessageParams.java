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
public final class ForwardMessageParams {
    public final String messageId;
    public final List recipientsIds;

    public ForwardMessageParams(String messageId, List recipientsIds) {
        Intrinsics.checkNotNullParameter(messageId, "messageId");
        Intrinsics.checkNotNullParameter(recipientsIds, "recipientsIds");
        this.messageId = messageId;
        this.recipientsIds = recipientsIds;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ForwardMessageParams)) {
            return false;
        }
        ForwardMessageParams forwardMessageParams = (ForwardMessageParams) obj;
        return Intrinsics.areEqual(this.messageId, forwardMessageParams.messageId) && Intrinsics.areEqual(this.recipientsIds, forwardMessageParams.recipientsIds);
    }

    public final String getMessageId() {
        return this.messageId;
    }

    public final List getRecipientsIds() {
        return this.recipientsIds;
    }

    public int hashCode() {
        return Objects.hash(this.messageId, this.recipientsIds);
    }
}
