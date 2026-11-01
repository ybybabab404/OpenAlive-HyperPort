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

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.devicestate.v1.$PerScreenDeviceStatesFactory, reason: invalid class name */
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
public final class C$PerScreenDeviceStatesFactory implements AppFunctionSerializableFactory<PerScreenDeviceStates> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public PerScreenDeviceStates fromAppFunctionData(@NonNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.devicestate.v1.PerScreenDeviceStates");
        C$DeviceStateItemFactory c$DeviceStateItemFactory = new C$DeviceStateItemFactory();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("description");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("intentUri");
        List appFunctionDataList = appFunctionDataWithSpec.getAppFunctionDataList("deviceStateItems");
        if (appFunctionDataList == null) {
            appFunctionDataList = CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(appFunctionDataList, 10));
        Iterator it = appFunctionDataList.iterator();
        while (it.hasNext()) {
            arrayList.add(c$DeviceStateItemFactory.fromAppFunctionData((AppFunctionData) it.next()));
        }
        return new PerScreenDeviceStates(stringOrNull, stringOrNull2, arrayList);
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
    public AppFunctionData toAppFunctionData(@NonNull PerScreenDeviceStates appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$DeviceStateItemFactory c$DeviceStateItemFactory = new C$DeviceStateItemFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.devicestate.v1.PerScreenDeviceStates");
        appFunctionDataBuilder.setString("description", appFunctionSerializable.getDescription());
        String intentUri = appFunctionSerializable.getIntentUri();
        if (intentUri != null) {
            appFunctionDataBuilder.setString("intentUri", intentUri);
        }
        List deviceStateItems = appFunctionSerializable.getDeviceStateItems();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(deviceStateItems, 10));
        Iterator it = deviceStateItems.iterator();
        while (it.hasNext()) {
            arrayList.add(c$DeviceStateItemFactory.toAppFunctionData((DeviceStateItem) it.next()));
        }
        appFunctionDataBuilder.setAppFunctionDataList("deviceStateItems", arrayList);
        return appFunctionDataBuilder.build();
    }
}
