package androidx.appfunctions.internal.serializableproxies;

import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.miui.maml.data.VariableNames;
import java.time.LocalDateTime;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: renamed from: androidx.appfunctions.internal.serializableproxies.$LocalDateTimeFactory, reason: invalid class name */
/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: $LocalDateTimeFactory.kt */
/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@Metadata
@RestrictTo
public final class C$LocalDateTimeFactory implements AppFunctionSerializableFactory<LocalDateTime> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public LocalDateTime fromAppFunctionData(@NotNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "androidx.appfunctions.internal.serializableproxies.AppFunctionLocalDateTime");
        Integer intOrNull = appFunctionDataWithSpec.getIntOrNull(VariableNames.VAR_YEAR);
        if (intOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        int iIntValue = intOrNull.intValue();
        Integer intOrNull2 = appFunctionDataWithSpec.getIntOrNull(VariableNames.VAR_MONTH);
        if (intOrNull2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        int iIntValue2 = intOrNull2.intValue();
        Integer intOrNull3 = appFunctionDataWithSpec.getIntOrNull("dayOfMonth");
        if (intOrNull3 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        int iIntValue3 = intOrNull3.intValue();
        Integer intOrNull4 = appFunctionDataWithSpec.getIntOrNull("hour");
        if (intOrNull4 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        int iIntValue4 = intOrNull4.intValue();
        Integer intOrNull5 = appFunctionDataWithSpec.getIntOrNull(VariableNames.VAR_MINUTE);
        if (intOrNull5 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        int iIntValue5 = intOrNull5.intValue();
        Integer intOrNull6 = appFunctionDataWithSpec.getIntOrNull(VariableNames.VAR_SECOND);
        if (intOrNull6 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        int iIntValue6 = intOrNull6.intValue();
        Integer intOrNull7 = appFunctionDataWithSpec.getIntOrNull("nanoOfSecond");
        if (intOrNull7 != null) {
            return new AppFunctionLocalDateTime(iIntValue, iIntValue2, iIntValue3, iIntValue4, iIntValue5, iIntValue6, intOrNull7.intValue()).toLocalDateTime();
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
    public AppFunctionData toAppFunctionData(@NotNull LocalDateTime appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        AppFunctionLocalDateTime appFunctionLocalDateTimeFromLocalDateTime = AppFunctionLocalDateTime.Companion.fromLocalDateTime(appFunctionSerializable);
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("androidx.appfunctions.internal.serializableproxies.AppFunctionLocalDateTime");
        appFunctionDataBuilder.setInt(VariableNames.VAR_YEAR, appFunctionLocalDateTimeFromLocalDateTime.getYear());
        appFunctionDataBuilder.setInt(VariableNames.VAR_MONTH, appFunctionLocalDateTimeFromLocalDateTime.getMonth());
        appFunctionDataBuilder.setInt("dayOfMonth", appFunctionLocalDateTimeFromLocalDateTime.getDayOfMonth());
        appFunctionDataBuilder.setInt("hour", appFunctionLocalDateTimeFromLocalDateTime.getHour());
        appFunctionDataBuilder.setInt(VariableNames.VAR_MINUTE, appFunctionLocalDateTimeFromLocalDateTime.getMinute());
        appFunctionDataBuilder.setInt(VariableNames.VAR_SECOND, appFunctionLocalDateTimeFromLocalDateTime.getSecond());
        appFunctionDataBuilder.setInt("nanoOfSecond", appFunctionLocalDateTimeFromLocalDateTime.getNanoOfSecond());
        return appFunctionDataBuilder.build();
    }
}
