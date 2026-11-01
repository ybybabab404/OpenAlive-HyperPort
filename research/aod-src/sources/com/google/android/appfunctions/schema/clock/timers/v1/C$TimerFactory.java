package com.google.android.appfunctions.schema.clock.timers.v1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.clock.timers.v1.$TimerFactory, reason: invalid class name */
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
public final class C$TimerFactory implements AppFunctionSerializableFactory<Timer> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public Timer fromAppFunctionData(@NonNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.clock.timers.v1.Timer");
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("id");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("label");
        Long longOrNull = appFunctionDataWithSpec.getLongOrNull("originalDurationMillis");
        if (longOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        long jLongValue = longOrNull.longValue();
        Long longOrNull2 = appFunctionDataWithSpec.getLongOrNull("durationMillis");
        if (longOrNull2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        long jLongValue2 = longOrNull2.longValue();
        String stringOrNull3 = appFunctionDataWithSpec.getStringOrNull("timerState");
        if (stringOrNull3 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        Long longOrNull3 = appFunctionDataWithSpec.getLongOrNull("remainingDurationMillis");
        if (longOrNull3 != null) {
            return new Timer(stringOrNull, stringOrNull2, jLongValue, jLongValue2, stringOrNull3, longOrNull3.longValue(), appFunctionDataWithSpec.getLongOrNull("expireTimeEpochMillis"), appFunctionDataWithSpec.getBooleanOrNull("isFiring"));
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
    public AppFunctionData toAppFunctionData(@NonNull Timer appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.clock.timers.v1.Timer");
        appFunctionDataBuilder.setString("id", appFunctionSerializable.getId());
        String label = appFunctionSerializable.getLabel();
        if (label != null) {
            appFunctionDataBuilder.setString("label", label);
        }
        appFunctionDataBuilder.setLong("originalDurationMillis", appFunctionSerializable.getOriginalDurationMillis());
        appFunctionDataBuilder.setLong("durationMillis", appFunctionSerializable.getDurationMillis());
        appFunctionDataBuilder.setString("timerState", appFunctionSerializable.getTimerState());
        appFunctionDataBuilder.setLong("remainingDurationMillis", appFunctionSerializable.getRemainingDurationMillis());
        Long expireTimeEpochMillis = appFunctionSerializable.getExpireTimeEpochMillis();
        if (expireTimeEpochMillis != null) {
            appFunctionDataBuilder.setLong("expireTimeEpochMillis", expireTimeEpochMillis.longValue());
        }
        Boolean boolIsFiring = appFunctionSerializable.isFiring();
        if (boolIsFiring != null) {
            appFunctionDataBuilder.setBoolean("isFiring", boolIsFiring.booleanValue());
        }
        return appFunctionDataBuilder.build();
    }
}
