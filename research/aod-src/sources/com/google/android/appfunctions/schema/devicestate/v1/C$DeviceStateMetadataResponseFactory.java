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

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.devicestate.v1.$DeviceStateMetadataResponseFactory, reason: invalid class name */
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
public final class C$DeviceStateMetadataResponseFactory implements AppFunctionSerializableFactory<DeviceStateMetadataResponse> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public DeviceStateMetadataResponse fromAppFunctionData(@NonNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.devicestate.v1.DeviceStateMetadataResponse");
        C$PerScreenMetadataFactory c$PerScreenMetadataFactory = new C$PerScreenMetadataFactory();
        C$ItemizationTypeFactory c$ItemizationTypeFactory = new C$ItemizationTypeFactory();
        List appFunctionDataList = appFunctionDataWithSpec.getAppFunctionDataList("perScreenMetadata");
        if (appFunctionDataList == null) {
            appFunctionDataList = CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(appFunctionDataList, 10));
        Iterator it = appFunctionDataList.iterator();
        while (it.hasNext()) {
            arrayList.add(c$PerScreenMetadataFactory.fromAppFunctionData((AppFunctionData) it.next()));
        }
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("deviceLocale");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("globalHintText");
        List appFunctionDataList2 = appFunctionDataWithSpec.getAppFunctionDataList("itemizationTypes");
        if (appFunctionDataList2 == null) {
            appFunctionDataList2 = CollectionsKt.emptyList();
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(appFunctionDataList2, 10));
        Iterator it2 = appFunctionDataList2.iterator();
        while (it2.hasNext()) {
            arrayList2.add(c$ItemizationTypeFactory.fromAppFunctionData((AppFunctionData) it2.next()));
        }
        return new DeviceStateMetadataResponse(arrayList, stringOrNull, stringOrNull2, arrayList2, appFunctionDataWithSpec.getStringOrNull("defaultValue"));
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
    public AppFunctionData toAppFunctionData(@NonNull DeviceStateMetadataResponse appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$PerScreenMetadataFactory c$PerScreenMetadataFactory = new C$PerScreenMetadataFactory();
        C$ItemizationTypeFactory c$ItemizationTypeFactory = new C$ItemizationTypeFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.devicestate.v1.DeviceStateMetadataResponse");
        List perScreenMetadata = appFunctionSerializable.getPerScreenMetadata();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(perScreenMetadata, 10));
        Iterator it = perScreenMetadata.iterator();
        while (it.hasNext()) {
            arrayList.add(c$PerScreenMetadataFactory.toAppFunctionData((PerScreenMetadata) it.next()));
        }
        appFunctionDataBuilder.setAppFunctionDataList("perScreenMetadata", arrayList);
        appFunctionDataBuilder.setString("deviceLocale", appFunctionSerializable.getDeviceLocale());
        String globalHintText = appFunctionSerializable.getGlobalHintText();
        if (globalHintText != null) {
            appFunctionDataBuilder.setString("globalHintText", globalHintText);
        }
        List itemizationTypes = appFunctionSerializable.getItemizationTypes();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(itemizationTypes, 10));
        Iterator it2 = itemizationTypes.iterator();
        while (it2.hasNext()) {
            arrayList2.add(c$ItemizationTypeFactory.toAppFunctionData((ItemizationType) it2.next()));
        }
        appFunctionDataBuilder.setAppFunctionDataList("itemizationTypes", arrayList2);
        String defaultValue = appFunctionSerializable.getDefaultValue();
        if (defaultValue != null) {
            appFunctionDataBuilder.setString("defaultValue", defaultValue);
        }
        return appFunctionDataBuilder.build();
    }
}
