package com.google.android.appfunctions.schema.devicestate.v1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.devicestate.v1.$DeviceStateResponseFactory, reason: invalid class name */
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
public final class C$DeviceStateResponseFactory implements AppFunctionSerializableFactory<DeviceStateResponse> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public DeviceStateResponse fromAppFunctionData(@NonNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.devicestate.v1.DeviceStateResponse");
        C$PerScreenDeviceStatesFactory c$PerScreenDeviceStatesFactory = new C$PerScreenDeviceStatesFactory();
        List appFunctionDataList = appFunctionDataWithSpec.getAppFunctionDataList("perScreenDeviceStates");
        if (appFunctionDataList == null) {
            appFunctionDataList = CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(appFunctionDataList, 10));
        Iterator it = appFunctionDataList.iterator();
        while (it.hasNext()) {
            arrayList.add(c$PerScreenDeviceStatesFactory.fromAppFunctionData((AppFunctionData) it.next()));
        }
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("deviceLocale");
        if (stringOrNull != null) {
            return new DeviceStateResponse(arrayList, stringOrNull, appFunctionDataWithSpec.getStringOrNull("globalHintText"));
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
    public AppFunctionData toAppFunctionData(@NonNull DeviceStateResponse appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$PerScreenDeviceStatesFactory c$PerScreenDeviceStatesFactory = new C$PerScreenDeviceStatesFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.devicestate.v1.DeviceStateResponse");
        List perScreenDeviceStates = appFunctionSerializable.getPerScreenDeviceStates();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(perScreenDeviceStates, 10));
        Iterator it = perScreenDeviceStates.iterator();
        while (it.hasNext()) {
            arrayList.add(c$PerScreenDeviceStatesFactory.toAppFunctionData((PerScreenDeviceStates) it.next()));
        }
        appFunctionDataBuilder.setAppFunctionDataList("perScreenDeviceStates", arrayList);
        appFunctionDataBuilder.setString("deviceLocale", appFunctionSerializable.getDeviceLocale());
        String globalHintText = appFunctionSerializable.getGlobalHintText();
        if (globalHintText != null) {
            appFunctionDataBuilder.setString("globalHintText", globalHintText);
        }
        return appFunctionDataBuilder.build();
    }
}
