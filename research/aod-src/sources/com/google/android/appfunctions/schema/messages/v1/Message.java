package com.google.android.appfunctions.schema.messages.v1;

import androidx.appfunctions.AppFunctionSerializable;
import com.google.android.appfunctions.schema.types.v1.DateTime;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
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
public final class Message {
    public static final Companion Companion = new Companion(null);
    public final String body;
    public final DateTime dateRead;
    public final DateTime dateReceived;
    public final DateTime dateSent;
    public final String id;
    public final List messageAttachments;
    public final String messageType;
    public final List recipientsIds;
    public final String senderId;

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    @Metadata
    public static final class Companion {

        /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
        @Metadata
        @Retention(RetentionPolicy.SOURCE)
        @kotlin.annotation.Retention
        public @interface MessageType {
        }

        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public Message(String id, String messageType, String body, String senderId, List recipientsIds, DateTime dateTime, DateTime dateTime2, DateTime dateTime3, List messageAttachments) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(messageType, "messageType");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(senderId, "senderId");
        Intrinsics.checkNotNullParameter(recipientsIds, "recipientsIds");
        Intrinsics.checkNotNullParameter(messageAttachments, "messageAttachments");
        this.id = id;
        this.messageType = messageType;
        this.body = body;
        this.senderId = senderId;
        this.recipientsIds = recipientsIds;
        this.dateSent = dateTime;
        this.dateReceived = dateTime2;
        this.dateRead = dateTime3;
        this.messageAttachments = messageAttachments;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Message)) {
            return false;
        }
        Message message = (Message) obj;
        return Intrinsics.areEqual(this.id, message.id) && Intrinsics.areEqual(this.messageType, message.messageType) && Intrinsics.areEqual(this.body, message.body) && Intrinsics.areEqual(this.senderId, message.senderId) && Intrinsics.areEqual(this.recipientsIds, message.recipientsIds) && Intrinsics.areEqual(this.dateSent, message.dateSent) && Intrinsics.areEqual(this.dateReceived, message.dateReceived) && Intrinsics.areEqual(this.dateRead, message.dateRead) && Intrinsics.areEqual(this.messageAttachments, message.messageAttachments);
    }

    public final String getBody() {
        return this.body;
    }

    public final DateTime getDateRead() {
        return this.dateRead;
    }

    public final DateTime getDateReceived() {
        return this.dateReceived;
    }

    public final DateTime getDateSent() {
        return this.dateSent;
    }

    public final String getId() {
        return this.id;
    }

    public final List getMessageAttachments() {
        return this.messageAttachments;
    }

    public final String getMessageType() {
        return this.messageType;
    }

    public final List getRecipientsIds() {
        return this.recipientsIds;
    }

    public final String getSenderId() {
        return this.senderId;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.messageType, this.body, this.senderId, this.recipientsIds, this.dateSent, this.dateReceived, this.dateRead, this.messageAttachments);
    }
}
