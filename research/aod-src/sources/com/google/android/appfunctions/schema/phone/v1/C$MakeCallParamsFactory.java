package com.google.android.appfunctions.schema.phone.v1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.phone.v1.$MakeCallParamsFactory, reason: invalid class name */
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
public final class C$MakeCallParamsFactory implements AppFunctionSerializableFactory<MakeCallParams> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public MakeCallParams fromAppFunctionData(@NonNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.phone.v1.MakeCallParams");
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("type");
        if (stringOrNull != null) {
            return new MakeCallParams(stringOrNull, appFunctionDataWithSpec.getStringOrNull("phoneNumber"), appFunctionDataWithSpec.getStringOrNull("contactId"), appFunctionDataWithSpec.getBooleanOrNull("shouldRecord"), appFunctionDataWithSpec.getBooleanOrNull("isSpeakerOn"), appFunctionDataWithSpec.getBooleanOrNull("isVideoCall"));
        }
        throw new IllegalStateException("Required value was null.");
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
    public AppFunctionData toAppFunctionData(@NonNull MakeCallParams appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.phone.v1.MakeCallParams");
        appFunctionDataBuilder.setString("type", appFunctionSerializable.getType());
        String phoneNumber = appFunctionSerializable.getPhoneNumber();
        if (phoneNumber != null) {
            appFunctionDataBuilder.setString("phoneNumber", phoneNumber);
        }
        String contactId = appFunctionSerializable.getContactId();
        if (contactId != null) {
            appFunctionDataBuilder.setString("contactId", contactId);
        }
        Boolean shouldRecord = appFunctionSerializable.getShouldRecord();
        if (shouldRecord != null) {
            appFunctionDataBuilder.setBoolean("shouldRecord", shouldRecord.booleanValue());
        }
        Boolean boolIsSpeakerOn = appFunctionSerializable.isSpeakerOn();
        if (boolIsSpeakerOn != null) {
            appFunctionDataBuilder.setBoolean("isSpeakerOn", boolIsSpeakerOn.booleanValue());
        }
        Boolean boolIsVideoCall = appFunctionSerializable.isVideoCall();
        if (boolIsVideoCall != null) {
            appFunctionDataBuilder.setBoolean("isVideoCall", boolIsVideoCall.booleanValue());
        }
        return appFunctionDataBuilder.build();
    }
}
