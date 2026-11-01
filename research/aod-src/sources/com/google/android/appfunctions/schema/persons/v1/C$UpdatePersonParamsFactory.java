package com.google.android.appfunctions.schema.persons.v1;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import androidx.appfunctions.internal.serializableproxies.C$UriFactory;
import com.google.android.appfunctions.schema.types.v1.C$SetFieldFactory;
import com.google.android.appfunctions.schema.types.v1.SetField;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.persons.v1.$UpdatePersonParamsFactory, reason: invalid class name */
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
public final class C$UpdatePersonParamsFactory implements AppFunctionSerializableFactory<UpdatePersonParams> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public UpdatePersonParams fromAppFunctionData(@NonNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.persons.v1.UpdatePersonParams");
        C$SetFieldFactory c$SetFieldFactory = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory2 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory3 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.SerializableTypeParameter(Uri.class, new C$UriFactory()));
        C$SetFieldFactory c$SetFieldFactory4 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.SerializableListTypeParameter(PersonPhoneNumber.class, new C$PersonPhoneNumberFactory()));
        C$SetFieldFactory c$SetFieldFactory5 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.SerializableListTypeParameter(PersonEmailAddress.class, new C$PersonEmailAddressFactory()));
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("personId");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("displayName");
        SetField setFieldFromAppFunctionData = appFunctionData2 != null ? c$SetFieldFactory.fromAppFunctionData(appFunctionData2) : null;
        AppFunctionData appFunctionData3 = appFunctionDataWithSpec.getAppFunctionData("givenName");
        SetField setFieldFromAppFunctionData2 = appFunctionData3 != null ? c$SetFieldFactory2.fromAppFunctionData(appFunctionData3) : null;
        AppFunctionData appFunctionData4 = appFunctionDataWithSpec.getAppFunctionData("middleName");
        SetField setFieldFromAppFunctionData3 = appFunctionData4 != null ? c$SetFieldFactory2.fromAppFunctionData(appFunctionData4) : null;
        AppFunctionData appFunctionData5 = appFunctionDataWithSpec.getAppFunctionData("familyName");
        SetField setFieldFromAppFunctionData4 = appFunctionData5 != null ? c$SetFieldFactory2.fromAppFunctionData(appFunctionData5) : null;
        AppFunctionData appFunctionData6 = appFunctionDataWithSpec.getAppFunctionData("externalUri");
        SetField setFieldFromAppFunctionData5 = appFunctionData6 != null ? c$SetFieldFactory3.fromAppFunctionData(appFunctionData6) : null;
        AppFunctionData appFunctionData7 = appFunctionDataWithSpec.getAppFunctionData("phoneNumbers");
        SetField setFieldFromAppFunctionData6 = appFunctionData7 != null ? c$SetFieldFactory4.fromAppFunctionData(appFunctionData7) : null;
        AppFunctionData appFunctionData8 = appFunctionDataWithSpec.getAppFunctionData("emailAddresses");
        return new UpdatePersonParams(stringOrNull, setFieldFromAppFunctionData, setFieldFromAppFunctionData2, setFieldFromAppFunctionData3, setFieldFromAppFunctionData4, setFieldFromAppFunctionData5, setFieldFromAppFunctionData6, appFunctionData8 != null ? c$SetFieldFactory5.fromAppFunctionData(appFunctionData8) : null);
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
    public AppFunctionData toAppFunctionData(@NonNull UpdatePersonParams appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$SetFieldFactory c$SetFieldFactory = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory2 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.PrimitiveTypeParameter(String.class));
        C$SetFieldFactory c$SetFieldFactory3 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.SerializableTypeParameter(Uri.class, new C$UriFactory()));
        C$SetFieldFactory c$SetFieldFactory4 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.SerializableListTypeParameter(PersonPhoneNumber.class, new C$PersonPhoneNumberFactory()));
        C$SetFieldFactory c$SetFieldFactory5 = new C$SetFieldFactory(new AppFunctionSerializableFactory.TypeParameter.SerializableListTypeParameter(PersonEmailAddress.class, new C$PersonEmailAddressFactory()));
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.persons.v1.UpdatePersonParams");
        appFunctionDataBuilder.setString("personId", appFunctionSerializable.getPersonId());
        SetField displayName = appFunctionSerializable.getDisplayName();
        if (displayName != null) {
            appFunctionDataBuilder.setAppFunctionData("displayName", c$SetFieldFactory.toAppFunctionData(displayName));
        }
        SetField givenName = appFunctionSerializable.getGivenName();
        if (givenName != null) {
            appFunctionDataBuilder.setAppFunctionData("givenName", c$SetFieldFactory2.toAppFunctionData(givenName));
        }
        SetField middleName = appFunctionSerializable.getMiddleName();
        if (middleName != null) {
            appFunctionDataBuilder.setAppFunctionData("middleName", c$SetFieldFactory2.toAppFunctionData(middleName));
        }
        SetField familyName = appFunctionSerializable.getFamilyName();
        if (familyName != null) {
            appFunctionDataBuilder.setAppFunctionData("familyName", c$SetFieldFactory2.toAppFunctionData(familyName));
        }
        SetField externalUri = appFunctionSerializable.getExternalUri();
        if (externalUri != null) {
            appFunctionDataBuilder.setAppFunctionData("externalUri", c$SetFieldFactory3.toAppFunctionData(externalUri));
        }
        SetField phoneNumbers = appFunctionSerializable.getPhoneNumbers();
        if (phoneNumbers != null) {
            appFunctionDataBuilder.setAppFunctionData("phoneNumbers", c$SetFieldFactory4.toAppFunctionData(phoneNumbers));
        }
        SetField emailAddresses = appFunctionSerializable.getEmailAddresses();
        if (emailAddresses != null) {
            appFunctionDataBuilder.setAppFunctionData("emailAddresses", c$SetFieldFactory5.toAppFunctionData(emailAddresses));
        }
        return appFunctionDataBuilder.build();
    }
}
