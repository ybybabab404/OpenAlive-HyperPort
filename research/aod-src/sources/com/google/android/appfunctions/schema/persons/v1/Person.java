package com.google.android.appfunctions.schema.persons.v1;

import android.net.Uri;
import androidx.appfunctions.AppFunctionSerializable;
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
public final class Person {
    public static final Companion Companion = new Companion(null);
    public final String displayName;
    public final List emailAddresses;
    public final Uri externalUri;
    public final String familyName;
    public final String givenName;
    public final String id;
    public final String middleName;
    public final List phoneNumbers;

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public Person(String id, String displayName, String str, String str2, String str3, Uri uri, List phoneNumbers, List emailAddresses) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(displayName, "displayName");
        Intrinsics.checkNotNullParameter(phoneNumbers, "phoneNumbers");
        Intrinsics.checkNotNullParameter(emailAddresses, "emailAddresses");
        this.id = id;
        this.displayName = displayName;
        this.givenName = str;
        this.middleName = str2;
        this.familyName = str3;
        this.externalUri = uri;
        this.phoneNumbers = phoneNumbers;
        this.emailAddresses = emailAddresses;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Person)) {
            return false;
        }
        Person person = (Person) obj;
        return Intrinsics.areEqual(this.id, person.id) && Intrinsics.areEqual(this.displayName, person.displayName) && Intrinsics.areEqual(this.givenName, person.givenName) && Intrinsics.areEqual(this.middleName, person.middleName) && Intrinsics.areEqual(this.familyName, person.familyName) && Intrinsics.areEqual(this.externalUri, person.externalUri) && Intrinsics.areEqual(this.phoneNumbers, person.phoneNumbers) && Intrinsics.areEqual(this.emailAddresses, person.emailAddresses);
    }

    public final String getDisplayName() {
        return this.displayName;
    }

    public final List getEmailAddresses() {
        return this.emailAddresses;
    }

    public final Uri getExternalUri() {
        return this.externalUri;
    }

    public final String getFamilyName() {
        return this.familyName;
    }

    public final String getGivenName() {
        return this.givenName;
    }

    public final String getId() {
        return this.id;
    }

    public final String getMiddleName() {
        return this.middleName;
    }

    public final List getPhoneNumbers() {
        return this.phoneNumbers;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.displayName, this.givenName, this.middleName, this.familyName, this.externalUri, this.phoneNumbers, this.emailAddresses);
    }
}
