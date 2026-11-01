package com.google.android.appfunctions.schema.devicestate.v1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.devicestate.v1.$SetDeviceStateItemResponseFactory, reason: invalid class name */
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
public final class C$SetDeviceStateItemResponseFactory implements AppFunctionSerializableFactory<SetDeviceStateItemResponse> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public SetDeviceStateItemResponse fromAppFunctionData(@NonNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.devicestate.v1.SetDeviceStateItemResponse");
        Boolean booleanOrNull = appFunctionDataWithSpec.getBooleanOrNull("isSuccessful");
        if (booleanOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        boolean zBooleanValue = booleanOrNull.booleanValue();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("currentValue");
        if (stringOrNull != null) {
            return new SetDeviceStateItemResponse(zBooleanValue, stringOrNull, appFunctionDataWithSpec.getStringOrNull("failureReason"));
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
    public AppFunctionData toAppFunctionData(@NonNull SetDeviceStateItemResponse appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.devicestate.v1.SetDeviceStateItemResponse");
        appFunctionDataBuilder.setBoolean("isSuccessful", appFunctionSerializable.isSuccessful());
        appFunctionDataBuilder.setString("currentValue", appFunctionSerializable.getCurrentValue());
        String failureReason = appFunctionSerializable.getFailureReason();
        if (failureReason != null) {
            appFunctionDataBuilder.setString("failureReason", failureReason);
        }
        return appFunctionDataBuilder.build();
    }
}
