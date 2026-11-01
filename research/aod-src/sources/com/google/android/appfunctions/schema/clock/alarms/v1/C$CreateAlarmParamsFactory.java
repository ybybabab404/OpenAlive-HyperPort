package com.google.android.appfunctions.schema.clock.alarms.v1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.google.android.appfunctions.schema.types.v1.C$TimeOfDayFactory;
import com.google.android.appfunctions.schema.types.v1.TimeOfDay;
import com.miui.maml.data.VariableNames;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.clock.alarms.v1.$CreateAlarmParamsFactory, reason: invalid class name */
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
public final class C$CreateAlarmParamsFactory implements AppFunctionSerializableFactory<CreateAlarmParams> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public CreateAlarmParams fromAppFunctionData(@NonNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.clock.alarms.v1.CreateAlarmParams");
        C$TimeOfDayFactory c$TimeOfDayFactory = new C$TimeOfDayFactory();
        C$DayPatternFactory c$DayPatternFactory = new C$DayPatternFactory();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("label");
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData(VariableNames.VAR_TIME);
        if (appFunctionData2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        TimeOfDay timeOfDayFromAppFunctionData = c$TimeOfDayFactory.fromAppFunctionData(appFunctionData2);
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("alarmStatus");
        AppFunctionData appFunctionData3 = appFunctionDataWithSpec.getAppFunctionData("dayPattern");
        return new CreateAlarmParams(stringOrNull, timeOfDayFromAppFunctionData, stringOrNull2, appFunctionData3 != null ? c$DayPatternFactory.fromAppFunctionData(appFunctionData3) : null, appFunctionDataWithSpec.getStringOrNull("externalId"));
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
    public AppFunctionData toAppFunctionData(@NonNull CreateAlarmParams appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$TimeOfDayFactory c$TimeOfDayFactory = new C$TimeOfDayFactory();
        C$DayPatternFactory c$DayPatternFactory = new C$DayPatternFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.clock.alarms.v1.CreateAlarmParams");
        String label = appFunctionSerializable.getLabel();
        if (label != null) {
            appFunctionDataBuilder.setString("label", label);
        }
        appFunctionDataBuilder.setAppFunctionData(VariableNames.VAR_TIME, c$TimeOfDayFactory.toAppFunctionData(appFunctionSerializable.getTime()));
        String alarmStatus = appFunctionSerializable.getAlarmStatus();
        if (alarmStatus != null) {
            appFunctionDataBuilder.setString("alarmStatus", alarmStatus);
        }
        DayPattern dayPattern = appFunctionSerializable.getDayPattern();
        if (dayPattern != null) {
            appFunctionDataBuilder.setAppFunctionData("dayPattern", c$DayPatternFactory.toAppFunctionData(dayPattern));
        }
        String externalId = appFunctionSerializable.getExternalId();
        if (externalId != null) {
            appFunctionDataBuilder.setString("externalId", externalId);
        }
        return appFunctionDataBuilder.build();
    }
}
