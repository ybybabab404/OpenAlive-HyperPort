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

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.devicestate.v1.$PerScreenMetadataFactory, reason: invalid class name */
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
public final class C$PerScreenMetadataFactory implements AppFunctionSerializableFactory<PerScreenMetadata> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public PerScreenMetadata fromAppFunctionData(@NonNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.devicestate.v1.PerScreenMetadata");
        C$DeviceStateItemMetadataFactory c$DeviceStateItemMetadataFactory = new C$DeviceStateItemMetadataFactory();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("description");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("intentUri");
        List stringList = appFunctionDataWithSpec.getStringList("itemizationTypes");
        if (stringList == null) {
            stringList = CollectionsKt.emptyList();
        }
        List appFunctionDataList = appFunctionDataWithSpec.getAppFunctionDataList("deviceStateItemsMetadata");
        if (appFunctionDataList == null) {
            appFunctionDataList = CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(appFunctionDataList, 10));
        Iterator it = appFunctionDataList.iterator();
        while (it.hasNext()) {
            arrayList.add(c$DeviceStateItemMetadataFactory.fromAppFunctionData((AppFunctionData) it.next()));
        }
        return new PerScreenMetadata(stringOrNull, stringOrNull2, stringList, arrayList);
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
    public AppFunctionData toAppFunctionData(@NonNull PerScreenMetadata appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$DeviceStateItemMetadataFactory c$DeviceStateItemMetadataFactory = new C$DeviceStateItemMetadataFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.devicestate.v1.PerScreenMetadata");
        appFunctionDataBuilder.setString("description", appFunctionSerializable.getDescription());
        String intentUri = appFunctionSerializable.getIntentUri();
        if (intentUri != null) {
            appFunctionDataBuilder.setString("intentUri", intentUri);
        }
        appFunctionDataBuilder.setStringList("itemizationTypes", appFunctionSerializable.getItemizationTypes());
        List deviceStateItemsMetadata = appFunctionSerializable.getDeviceStateItemsMetadata();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(deviceStateItemsMetadata, 10));
        Iterator it = deviceStateItemsMetadata.iterator();
        while (it.hasNext()) {
            arrayList.add(c$DeviceStateItemMetadataFactory.toAppFunctionData((DeviceStateItemMetadata) it.next()));
        }
        appFunctionDataBuilder.setAppFunctionDataList("deviceStateItemsMetadata", arrayList);
        return appFunctionDataBuilder.build();
    }
}
