package com.google.android.appfunctions.schema.phone.v1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.google.android.appfunctions.schema.types.v1.C$DateTimeFactory;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.phone.v1.$CallRecordFactory, reason: invalid class name */
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
public final class C$CallRecordFactory implements AppFunctionSerializableFactory<CallRecord> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public CallRecord fromAppFunctionData(@NonNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.phone.v1.CallRecord");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("id");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("contactId");
        if (stringOrNull2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull3 = appFunctionDataWithSpec.getStringOrNull("callType");
        if (stringOrNull3 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        Boolean booleanOrNull = appFunctionDataWithSpec.getBooleanOrNull("isVideoCall");
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("startTime");
        if (appFunctionData2 != null) {
            return new CallRecord(stringOrNull, stringOrNull2, stringOrNull3, booleanOrNull, c$DateTimeFactory.fromAppFunctionData(appFunctionData2), appFunctionDataWithSpec.getLongOrNull("durationMillis"), appFunctionDataWithSpec.getStringOrNull("sipAddress"), appFunctionDataWithSpec.getStringOrNull("phoneNumber"));
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
    public AppFunctionData toAppFunctionData(@NonNull CallRecord appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.phone.v1.CallRecord");
        appFunctionDataBuilder.setString("id", appFunctionSerializable.getId());
        appFunctionDataBuilder.setString("contactId", appFunctionSerializable.getContactId());
        appFunctionDataBuilder.setString("callType", appFunctionSerializable.getCallType());
        Boolean boolIsVideoCall = appFunctionSerializable.isVideoCall();
        if (boolIsVideoCall != null) {
            appFunctionDataBuilder.setBoolean("isVideoCall", boolIsVideoCall.booleanValue());
        }
        appFunctionDataBuilder.setAppFunctionData("startTime", c$DateTimeFactory.toAppFunctionData(appFunctionSerializable.getStartTime()));
        Long durationMillis = appFunctionSerializable.getDurationMillis();
        if (durationMillis != null) {
            appFunctionDataBuilder.setLong("durationMillis", durationMillis.longValue());
        }
        String sipAddress = appFunctionSerializable.getSipAddress();
        if (sipAddress != null) {
            appFunctionDataBuilder.setString("sipAddress", sipAddress);
        }
        String phoneNumber = appFunctionSerializable.getPhoneNumber();
        if (phoneNumber != null) {
            appFunctionDataBuilder.setString("phoneNumber", phoneNumber);
        }
        return appFunctionDataBuilder.build();
    }
}
