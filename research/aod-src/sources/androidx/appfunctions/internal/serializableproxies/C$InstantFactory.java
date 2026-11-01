package androidx.appfunctions.internal.serializableproxies;

import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import java.time.Instant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: renamed from: androidx.appfunctions.internal.serializableproxies.$InstantFactory, reason: invalid class name */
/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: $InstantFactory.kt */
/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@Metadata
@RestrictTo
public final class C$InstantFactory implements AppFunctionSerializableFactory<Instant> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public Instant fromAppFunctionData(@NotNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "androidx.appfunctions.internal.serializableproxies.AppFunctionLocalDateTime");
        Long longOrNull = appFunctionDataWithSpec.getLongOrNull("epochSecond");
        if (longOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        long jLongValue = longOrNull.longValue();
        Integer intOrNull = appFunctionDataWithSpec.getIntOrNull("nanoAdjustment");
        if (intOrNull != null) {
            return new AppFunctionInstant(jLongValue, intOrNull.intValue()).toInstant();
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
    public AppFunctionData toAppFunctionData(@NotNull Instant appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        AppFunctionInstant appFunctionInstantFromInstant = AppFunctionInstant.Companion.fromInstant(appFunctionSerializable);
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("androidx.appfunctions.internal.serializableproxies.AppFunctionInstant");
        appFunctionDataBuilder.setLong("epochSecond", appFunctionInstantFromInstant.getEpochSecond());
        appFunctionDataBuilder.setInt("nanoAdjustment", appFunctionInstantFromInstant.getNanoAdjustment());
        return appFunctionDataBuilder.build();
    }
}
