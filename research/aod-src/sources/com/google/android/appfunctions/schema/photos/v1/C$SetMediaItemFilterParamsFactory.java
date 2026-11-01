package com.google.android.appfunctions.schema.photos.v1;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import androidx.appfunctions.internal.serializableproxies.C$UriFactory;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.photos.v1.$SetMediaItemFilterParamsFactory, reason: invalid class name */
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
public final class C$SetMediaItemFilterParamsFactory implements AppFunctionSerializableFactory<SetMediaItemFilterParams> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public SetMediaItemFilterParams fromAppFunctionData(@NonNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.photos.v1.SetMediaItemFilterParams");
        C$UriFactory c$UriFactory = new C$UriFactory();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("mediaItemId");
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("mediaItemUri");
        Uri uriFromAppFunctionData = appFunctionData2 != null ? c$UriFactory.fromAppFunctionData(appFunctionData2) : null;
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("filter");
        if (stringOrNull2 != null) {
            return new SetMediaItemFilterParams(stringOrNull, uriFromAppFunctionData, stringOrNull2);
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
    public AppFunctionData toAppFunctionData(@NonNull SetMediaItemFilterParams appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$UriFactory c$UriFactory = new C$UriFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.photos.v1.SetMediaItemFilterParams");
        String mediaItemId = appFunctionSerializable.getMediaItemId();
        if (mediaItemId != null) {
            appFunctionDataBuilder.setString("mediaItemId", mediaItemId);
        }
        Uri mediaItemUri = appFunctionSerializable.getMediaItemUri();
        if (mediaItemUri != null) {
            appFunctionDataBuilder.setAppFunctionData("mediaItemUri", c$UriFactory.toAppFunctionData(mediaItemUri));
        }
        appFunctionDataBuilder.setString("filter", appFunctionSerializable.getFilter());
        return appFunctionDataBuilder.build();
    }
}
