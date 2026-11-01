package com.google.android.appfunctions.schema.persons.v1;

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
public final class UpdatePersonParams {
    public final SetField displayName;
    public final SetField emailAddresses;
    public final SetField externalUri;
    public final SetField familyName;
    public final SetField givenName;
    public final SetField middleName;
    public final String personId;
    public final SetField phoneNumbers;

    public UpdatePersonParams(String personId, SetField setField, SetField setField2, SetField setField3, SetField setField4, SetField setField5, SetField setField6, SetField setField7) {
        Intrinsics.checkNotNullParameter(personId, "personId");
        this.personId = personId;
        this.displayName = setField;
        this.givenName = setField2;
        this.middleName = setField3;
        this.familyName = setField4;
        this.externalUri = setField5;
        this.phoneNumbers = setField6;
        this.emailAddresses = setField7;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof UpdatePersonParams)) {
            return false;
        }
        UpdatePersonParams updatePersonParams = (UpdatePersonParams) obj;
        return Intrinsics.areEqual(this.personId, updatePersonParams.personId) && Intrinsics.areEqual(this.displayName, updatePersonParams.displayName) && Intrinsics.areEqual(this.givenName, updatePersonParams.givenName) && Intrinsics.areEqual(this.middleName, updatePersonParams.middleName) && Intrinsics.areEqual(this.familyName, updatePersonParams.familyName) && Intrinsics.areEqual(this.externalUri, updatePersonParams.externalUri) && Intrinsics.areEqual(this.phoneNumbers, updatePersonParams.phoneNumbers) && Intrinsics.areEqual(this.emailAddresses, updatePersonParams.emailAddresses);
    }

    public final SetField getDisplayName() {
        return this.displayName;
    }

    public final SetField getEmailAddresses() {
        return this.emailAddresses;
    }

    public final SetField getExternalUri() {
        return this.externalUri;
    }

    public final SetField getFamilyName() {
        return this.familyName;
    }

    public final SetField getGivenName() {
        return this.givenName;
    }

    public final SetField getMiddleName() {
        return this.middleName;
    }

    public final String getPersonId() {
        return this.personId;
    }

    public final SetField getPhoneNumbers() {
        return this.phoneNumbers;
    }

    public int hashCode() {
        return Objects.hash(this.personId, this.displayName, this.givenName, this.middleName, this.familyName, this.externalUri, this.phoneNumbers, this.emailAddresses);
    }
}
