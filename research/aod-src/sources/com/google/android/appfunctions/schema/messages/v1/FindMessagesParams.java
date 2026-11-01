package com.google.android.appfunctions.schema.messages.v1;

import androidx.appfunctions.AppFunctionSerializable;
import com.google.android.appfunctions.schema.types.v1.DateTime;
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
public final class FindMessagesParams {
    public final DateTime endTime;
    public final int maxCount;
    public final String query;
    public final Boolean read;
    public final List recipientsIds;
    public final String senderId;
    public final DateTime startTime;

    public FindMessagesParams(String str, List recipientsIds, DateTime dateTime, DateTime dateTime2, String str2, Boolean bool, int i) {
        Intrinsics.checkNotNullParameter(recipientsIds, "recipientsIds");
        this.senderId = str;
        this.recipientsIds = recipientsIds;
        this.startTime = dateTime;
        this.endTime = dateTime2;
        this.query = str2;
        this.read = bool;
        this.maxCount = i;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FindMessagesParams)) {
            return false;
        }
        FindMessagesParams findMessagesParams = (FindMessagesParams) obj;
        return Intrinsics.areEqual(this.senderId, findMessagesParams.senderId) && Intrinsics.areEqual(this.recipientsIds, findMessagesParams.recipientsIds) && Intrinsics.areEqual(this.startTime, findMessagesParams.startTime) && Intrinsics.areEqual(this.endTime, findMessagesParams.endTime) && Intrinsics.areEqual(this.query, findMessagesParams.query) && Intrinsics.areEqual(this.read, findMessagesParams.read) && this.maxCount == findMessagesParams.maxCount;
    }

    public final DateTime getEndTime() {
        return this.endTime;
    }

    public final int getMaxCount() {
        return this.maxCount;
    }

    public final String getQuery() {
        return this.query;
    }

    public final Boolean getRead() {
        return this.read;
    }

    public final List getRecipientsIds() {
        return this.recipientsIds;
    }

    public final String getSenderId() {
        return this.senderId;
    }

    public final DateTime getStartTime() {
        return this.startTime;
    }

    public int hashCode() {
        return Objects.hash(this.senderId, this.recipientsIds, this.startTime, this.endTime, this.query, this.read, Integer.valueOf(this.maxCount));
    }
}
