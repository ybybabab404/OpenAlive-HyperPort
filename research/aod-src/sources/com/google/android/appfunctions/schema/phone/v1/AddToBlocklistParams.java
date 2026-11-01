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
public final class AddToBlocklistParams {
    public final String contactId;
    public final String phoneNumber;
    public final String type;

    public AddToBlocklistParams(String type, String str, String str2) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
        this.phoneNumber = str;
        this.contactId = str2;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AddToBlocklistParams)) {
            return false;
        }
        AddToBlocklistParams addToBlocklistParams = (AddToBlocklistParams) obj;
        return Intrinsics.areEqual(this.type, addToBlocklistParams.type) && Intrinsics.areEqual(this.phoneNumber, addToBlocklistParams.phoneNumber) && Intrinsics.areEqual(this.contactId, addToBlocklistParams.contactId);
    }

    public final String getContactId() {
        return this.contactId;
    }

    public final String getPhoneNumber() {
        return this.phoneNumber;
    }

    public final String getType() {
        return this.type;
    }

    public int hashCode() {
        return Objects.hash(this.type, this.phoneNumber, this.contactId);
    }
}
