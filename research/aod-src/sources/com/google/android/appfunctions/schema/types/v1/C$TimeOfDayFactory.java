package com.google.android.appfunctions.schema.types.v1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.types.v1.$TimeOfDayFactory, reason: invalid class name */
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
public final class C$TimeOfDayFactory implements AppFunctionSerializableFactory<TimeOfDay> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public TimeOfDay fromAppFunctionData(@NonNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.types.v1.TimeOfDay");
        Integer intOrNull = appFunctionDataWithSpec.getIntOrNull("hours");
        if (intOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        int iIntValue = intOrNull.intValue();
        Integer intOrNull2 = appFunctionDataWithSpec.getIntOrNull("minutes");
        if (intOrNull2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        int iIntValue2 = intOrNull2.intValue();
        Integer intOrNull3 = appFunctionDataWithSpec.getIntOrNull("seconds");
        if (intOrNull3 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        int iIntValue3 = intOrNull3.intValue();
        Integer intOrNull4 = appFunctionDataWithSpec.getIntOrNull("nanos");
        if (intOrNull4 != null) {
            return new TimeOfDay(iIntValue, iIntValue2, iIntValue3, intOrNull4.intValue());
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
    public AppFunctionData toAppFunctionData(@NonNull TimeOfDay appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.types.v1.TimeOfDay");
        appFunctionDataBuilder.setInt("hours", appFunctionSerializable.getHours());
        appFunctionDataBuilder.setInt("minutes", appFunctionSerializable.getMinutes());
        appFunctionDataBuilder.setInt("seconds", appFunctionSerializable.getSeconds());
        appFunctionDataBuilder.setInt("nanos", appFunctionSerializable.getNanos());
        return appFunctionDataBuilder.build();
    }
}
