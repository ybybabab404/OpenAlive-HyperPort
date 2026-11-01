package com.google.android.appfunctions.schema.types.v1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.types.v1.$DateTimeFactory, reason: invalid class name */
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
public final class C$DateTimeFactory implements AppFunctionSerializableFactory<DateTime> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public DateTime fromAppFunctionData(@NonNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.types.v1.DateTime");
        C$DateFactory c$DateFactory = new C$DateFactory();
        C$TimeOfDayFactory c$TimeOfDayFactory = new C$TimeOfDayFactory();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("timeZone");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("date");
        if (appFunctionData2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        Date dateFromAppFunctionData = c$DateFactory.fromAppFunctionData(appFunctionData2);
        AppFunctionData appFunctionData3 = appFunctionDataWithSpec.getAppFunctionData("timeOfDay");
        if (appFunctionData3 != null) {
            return new DateTime(stringOrNull, dateFromAppFunctionData, c$TimeOfDayFactory.fromAppFunctionData(appFunctionData3));
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
    public AppFunctionData toAppFunctionData(@NonNull DateTime appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$DateFactory c$DateFactory = new C$DateFactory();
        C$TimeOfDayFactory c$TimeOfDayFactory = new C$TimeOfDayFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.types.v1.DateTime");
        appFunctionDataBuilder.setString("timeZone", appFunctionSerializable.getTimeZone());
        appFunctionDataBuilder.setAppFunctionData("date", c$DateFactory.toAppFunctionData(appFunctionSerializable.getDate()));
        appFunctionDataBuilder.setAppFunctionData("timeOfDay", c$TimeOfDayFactory.toAppFunctionData(appFunctionSerializable.getTimeOfDay()));
        return appFunctionDataBuilder.build();
    }
}
