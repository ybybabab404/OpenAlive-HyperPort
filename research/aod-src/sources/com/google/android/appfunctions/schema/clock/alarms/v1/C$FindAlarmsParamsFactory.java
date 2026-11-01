package com.google.android.appfunctions.schema.clock.alarms.v1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.google.android.appfunctions.schema.types.v1.C$DateTimeFactory;
import com.google.android.appfunctions.schema.types.v1.DateTime;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.clock.alarms.v1.$FindAlarmsParamsFactory, reason: invalid class name */
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
public final class C$FindAlarmsParamsFactory implements AppFunctionSerializableFactory<FindAlarmsParams> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public FindAlarmsParams fromAppFunctionData(@NonNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.clock.alarms.v1.FindAlarmsParams");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        C$DayPatternFactory c$DayPatternFactory = new C$DayPatternFactory();
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("startTime");
        DateTime dateTimeFromAppFunctionData = appFunctionData2 != null ? c$DateTimeFactory.fromAppFunctionData(appFunctionData2) : null;
        AppFunctionData appFunctionData3 = appFunctionDataWithSpec.getAppFunctionData("endTime");
        DateTime dateTimeFromAppFunctionData2 = appFunctionData3 != null ? c$DateTimeFactory.fromAppFunctionData(appFunctionData3) : null;
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("label");
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("alarmStatus");
        AppFunctionData appFunctionData4 = appFunctionDataWithSpec.getAppFunctionData("dayPattern");
        DayPattern dayPatternFromAppFunctionData = appFunctionData4 != null ? c$DayPatternFactory.fromAppFunctionData(appFunctionData4) : null;
        Integer intOrNull = appFunctionDataWithSpec.getIntOrNull("maxCount");
        if (intOrNull != null) {
            return new FindAlarmsParams(dateTimeFromAppFunctionData, dateTimeFromAppFunctionData2, stringOrNull, stringOrNull2, dayPatternFromAppFunctionData, intOrNull.intValue(), appFunctionDataWithSpec.getStringOrNull("sortingOrder"));
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
    public AppFunctionData toAppFunctionData(@NonNull FindAlarmsParams appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        C$DayPatternFactory c$DayPatternFactory = new C$DayPatternFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.clock.alarms.v1.FindAlarmsParams");
        DateTime startTime = appFunctionSerializable.getStartTime();
        if (startTime != null) {
            appFunctionDataBuilder.setAppFunctionData("startTime", c$DateTimeFactory.toAppFunctionData(startTime));
        }
        DateTime endTime = appFunctionSerializable.getEndTime();
        if (endTime != null) {
            appFunctionDataBuilder.setAppFunctionData("endTime", c$DateTimeFactory.toAppFunctionData(endTime));
        }
        String label = appFunctionSerializable.getLabel();
        if (label != null) {
            appFunctionDataBuilder.setString("label", label);
        }
        String alarmStatus = appFunctionSerializable.getAlarmStatus();
        if (alarmStatus != null) {
            appFunctionDataBuilder.setString("alarmStatus", alarmStatus);
        }
        DayPattern dayPattern = appFunctionSerializable.getDayPattern();
        if (dayPattern != null) {
            appFunctionDataBuilder.setAppFunctionData("dayPattern", c$DayPatternFactory.toAppFunctionData(dayPattern));
        }
        appFunctionDataBuilder.setInt("maxCount", appFunctionSerializable.getMaxCount());
        String sortingOrder = appFunctionSerializable.getSortingOrder();
        if (sortingOrder != null) {
            appFunctionDataBuilder.setString("sortingOrder", sortingOrder);
        }
        return appFunctionDataBuilder.build();
    }
}
