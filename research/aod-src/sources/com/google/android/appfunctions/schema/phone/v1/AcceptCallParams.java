package com.google.android.appfunctions.schema.phone.v1;

import androidx.appfunctions.AppFunctionSerializable;
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
public final class AcceptCallParams {
    public final Boolean isSpeakerOn;
    public final Boolean isVideoCall;
    public final Boolean shouldRecord;

    public AcceptCallParams(Boolean bool, Boolean bool2, Boolean bool3) {
        this.shouldRecord = bool;
        this.isSpeakerOn = bool2;
        this.isVideoCall = bool3;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AcceptCallParams)) {
            return false;
        }
        AcceptCallParams acceptCallParams = (AcceptCallParams) obj;
        return Intrinsics.areEqual(this.shouldRecord, acceptCallParams.shouldRecord) && Intrinsics.areEqual(this.isSpeakerOn, acceptCallParams.isSpeakerOn) && Intrinsics.areEqual(this.isVideoCall, acceptCallParams.isVideoCall);
    }

    public final Boolean getShouldRecord() {
        return this.shouldRecord;
    }

    public int hashCode() {
        return Objects.hash(this.shouldRecord, this.isSpeakerOn, this.isVideoCall);
    }

    public final Boolean isSpeakerOn() {
        return this.isSpeakerOn;
    }

    public final Boolean isVideoCall() {
        return this.isVideoCall;
    }
}
