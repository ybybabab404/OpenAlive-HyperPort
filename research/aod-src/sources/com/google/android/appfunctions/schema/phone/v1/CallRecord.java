package com.google.android.appfunctions.schema.phone.v1;

import androidx.appfunctions.AppFunctionSerializable;
import com.google.android.appfunctions.schema.types.v1.DateTime;
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
public final class CallRecord {
    public static final Companion Companion = new Companion(null);
    public final String callType;
    public final String contactId;
    public final Long durationMillis;
    public final String id;
    public final Boolean isVideoCall;
    public final String phoneNumber;
    public final String sipAddress;
    public final DateTime startTime;

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    @Metadata
    public static final class Companion {

        /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
        @Metadata
        @Retention(RetentionPolicy.SOURCE)
        @kotlin.annotation.Retention
        public @interface CallType {
        }

        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public CallRecord(String id, String contactId, String callType, Boolean bool, DateTime startTime, Long l, String str, String str2) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(contactId, "contactId");
        Intrinsics.checkNotNullParameter(callType, "callType");
        Intrinsics.checkNotNullParameter(startTime, "startTime");
        this.id = id;
        this.contactId = contactId;
        this.callType = callType;
        this.isVideoCall = bool;
        this.startTime = startTime;
        this.durationMillis = l;
        this.sipAddress = str;
        this.phoneNumber = str2;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CallRecord)) {
            return false;
        }
        CallRecord callRecord = (CallRecord) obj;
        return Intrinsics.areEqual(this.id, callRecord.id) && Intrinsics.areEqual(this.contactId, callRecord.contactId) && Intrinsics.areEqual(this.callType, callRecord.callType) && Intrinsics.areEqual(this.isVideoCall, callRecord.isVideoCall) && Intrinsics.areEqual(this.startTime, callRecord.startTime) && Intrinsics.areEqual(this.durationMillis, callRecord.durationMillis) && Intrinsics.areEqual(this.sipAddress, callRecord.sipAddress) && Intrinsics.areEqual(this.phoneNumber, callRecord.phoneNumber);
    }

    public final String getCallType() {
        return this.callType;
    }

    public final String getContactId() {
        return this.contactId;
    }

    public final Long getDurationMillis() {
        return this.durationMillis;
    }

    public final String getId() {
        return this.id;
    }

    public final String getPhoneNumber() {
        return this.phoneNumber;
    }

    public final String getSipAddress() {
        return this.sipAddress;
    }

    public final DateTime getStartTime() {
        return this.startTime;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.contactId, this.callType, this.isVideoCall, this.startTime, this.durationMillis, this.sipAddress, this.phoneNumber);
    }

    public final Boolean isVideoCall() {
        return this.isVideoCall;
    }
}
