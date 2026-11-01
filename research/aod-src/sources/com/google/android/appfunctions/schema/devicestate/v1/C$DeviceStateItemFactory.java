package com.google.android.appfunctions.schema.devicestate.v1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.xiaomi.onetrack.api.bf;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.devicestate.v1.$DeviceStateItemFactory, reason: invalid class name */
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
public final class C$DeviceStateItemFactory implements AppFunctionSerializableFactory<DeviceStateItem> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public DeviceStateItem fromAppFunctionData(@NonNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.devicestate.v1.DeviceStateItem");
        C$LocalizedStringFactory c$LocalizedStringFactory = new C$LocalizedStringFactory();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("key");
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("purpose");
        if (stringOrNull2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData(bf.a);
        return new DeviceStateItem(stringOrNull, stringOrNull2, appFunctionData2 != null ? c$LocalizedStringFactory.fromAppFunctionData(appFunctionData2) : null, appFunctionDataWithSpec.getStringOrNull("jsonValue"), appFunctionDataWithSpec.getLongOrNull("lastUpdatedEpochMillis"), appFunctionDataWithSpec.getStringOrNull("hintText"));
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
    public AppFunctionData toAppFunctionData(@NonNull DeviceStateItem appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$LocalizedStringFactory c$LocalizedStringFactory = new C$LocalizedStringFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.devicestate.v1.DeviceStateItem");
        String key = appFunctionSerializable.getKey();
        if (key != null) {
            appFunctionDataBuilder.setString("key", key);
        }
        appFunctionDataBuilder.setString("purpose", appFunctionSerializable.getPurpose());
        LocalizedString name = appFunctionSerializable.getName();
        if (name != null) {
            appFunctionDataBuilder.setAppFunctionData(bf.a, c$LocalizedStringFactory.toAppFunctionData(name));
        }
        String jsonValue = appFunctionSerializable.getJsonValue();
        if (jsonValue != null) {
            appFunctionDataBuilder.setString("jsonValue", jsonValue);
        }
        Long lastUpdatedEpochMillis = appFunctionSerializable.getLastUpdatedEpochMillis();
        if (lastUpdatedEpochMillis != null) {
            appFunctionDataBuilder.setLong("lastUpdatedEpochMillis", lastUpdatedEpochMillis.longValue());
        }
        String hintText = appFunctionSerializable.getHintText();
        if (hintText != null) {
            appFunctionDataBuilder.setString("hintText", hintText);
        }
        return appFunctionDataBuilder.build();
    }
}
