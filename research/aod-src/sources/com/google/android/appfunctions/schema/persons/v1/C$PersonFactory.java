package com.google.android.appfunctions.schema.persons.v1;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import androidx.appfunctions.internal.serializableproxies.C$UriFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.persons.v1.$PersonFactory, reason: invalid class name */
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
@RequiresApi
@Metadata
public final class C$PersonFactory implements AppFunctionSerializableFactory<Person> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public Person fromAppFunctionData(@NonNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.persons.v1.Person");
        C$PersonPhoneNumberFactory c$PersonPhoneNumberFactory = new C$PersonPhoneNumberFactory();
        C$PersonEmailAddressFactory c$PersonEmailAddressFactory = new C$PersonEmailAddressFactory();
        C$UriFactory c$UriFactory = new C$UriFactory();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("id");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("displayName");
        if (stringOrNull2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull3 = appFunctionDataWithSpec.getStringOrNull("givenName");
        String stringOrNull4 = appFunctionDataWithSpec.getStringOrNull("middleName");
        String stringOrNull5 = appFunctionDataWithSpec.getStringOrNull("familyName");
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("externalUri");
        Uri uriFromAppFunctionData = appFunctionData2 != null ? c$UriFactory.fromAppFunctionData(appFunctionData2) : null;
        List appFunctionDataList = appFunctionDataWithSpec.getAppFunctionDataList("phoneNumbers");
        if (appFunctionDataList == null) {
            appFunctionDataList = CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(appFunctionDataList, 10));
        Iterator it = appFunctionDataList.iterator();
        while (it.hasNext()) {
            arrayList.add(c$PersonPhoneNumberFactory.fromAppFunctionData((AppFunctionData) it.next()));
        }
        List appFunctionDataList2 = appFunctionDataWithSpec.getAppFunctionDataList("emailAddresses");
        if (appFunctionDataList2 == null) {
            appFunctionDataList2 = CollectionsKt.emptyList();
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(appFunctionDataList2, 10));
        Iterator it2 = appFunctionDataList2.iterator();
        while (it2.hasNext()) {
            arrayList2.add(c$PersonEmailAddressFactory.fromAppFunctionData((AppFunctionData) it2.next()));
        }
        return new Person(stringOrNull, stringOrNull2, stringOrNull3, stringOrNull4, stringOrNull5, uriFromAppFunctionData, arrayList, arrayList2);
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public /* bridge */ /* synthetic */ AppFunctionData.Builder getAppFunctionDataBuilder(@NotNull String str) {
        return super.getAppFunctionDataBuilder(str);
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public /* bridge */ /* synthetic */ AppFunctionData getAppFunctionDataWithSpec(@NotNull AppFunctionData appFunctionData, @NotNull String str) {
        return super.getAppFunctionDataWithSpec(appFunctionData, str);
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public AppFunctionData toAppFunctionData(@NonNull Person appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$PersonPhoneNumberFactory c$PersonPhoneNumberFactory = new C$PersonPhoneNumberFactory();
        C$PersonEmailAddressFactory c$PersonEmailAddressFactory = new C$PersonEmailAddressFactory();
        C$UriFactory c$UriFactory = new C$UriFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.persons.v1.Person");
        appFunctionDataBuilder.setString("id", appFunctionSerializable.getId());
        appFunctionDataBuilder.setString("displayName", appFunctionSerializable.getDisplayName());
        String givenName = appFunctionSerializable.getGivenName();
        if (givenName != null) {
            appFunctionDataBuilder.setString("givenName", givenName);
        }
        String middleName = appFunctionSerializable.getMiddleName();
        if (middleName != null) {
            appFunctionDataBuilder.setString("middleName", middleName);
        }
        String familyName = appFunctionSerializable.getFamilyName();
        if (familyName != null) {
            appFunctionDataBuilder.setString("familyName", familyName);
        }
        Uri externalUri = appFunctionSerializable.getExternalUri();
        if (externalUri != null) {
            appFunctionDataBuilder.setAppFunctionData("externalUri", c$UriFactory.toAppFunctionData(externalUri));
        }
        List phoneNumbers = appFunctionSerializable.getPhoneNumbers();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(phoneNumbers, 10));
        Iterator it = phoneNumbers.iterator();
        while (it.hasNext()) {
            arrayList.add(c$PersonPhoneNumberFactory.toAppFunctionData((PersonPhoneNumber) it.next()));
        }
        appFunctionDataBuilder.setAppFunctionDataList("phoneNumbers", arrayList);
        List emailAddresses = appFunctionSerializable.getEmailAddresses();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(emailAddresses, 10));
        Iterator it2 = emailAddresses.iterator();
        while (it2.hasNext()) {
            arrayList2.add(c$PersonEmailAddressFactory.toAppFunctionData((PersonEmailAddress) it2.next()));
        }
        appFunctionDataBuilder.setAppFunctionDataList("emailAddresses", arrayList2);
        return appFunctionDataBuilder.build();
    }
}
