package com.google.android.appfunctions.schema.calendar.v1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.google.android.appfunctions.schema.types.v1.C$DateTimeFactory;
import com.google.android.appfunctions.schema.types.v1.DateTime;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.calendar.v1.$FindEventsParamsFactory, reason: invalid class name */
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
public final class C$FindEventsParamsFactory implements AppFunctionSerializableFactory<FindEventsParams> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public FindEventsParams fromAppFunctionData(@NonNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.calendar.v1.FindEventsParams");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("startDate");
        DateTime dateTimeFromAppFunctionData = appFunctionData2 != null ? c$DateTimeFactory.fromAppFunctionData(appFunctionData2) : null;
        AppFunctionData appFunctionData3 = appFunctionDataWithSpec.getAppFunctionData("endDate");
        DateTime dateTimeFromAppFunctionData2 = appFunctionData3 != null ? c$DateTimeFactory.fromAppFunctionData(appFunctionData3) : null;
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("query");
        List stringList = appFunctionDataWithSpec.getStringList("attendeeIds");
        if (stringList == null) {
            stringList = CollectionsKt.emptyList();
        }
        List list = stringList;
        Integer intOrNull = appFunctionDataWithSpec.getIntOrNull("maxCount");
        if (intOrNull != null) {
            return new FindEventsParams(dateTimeFromAppFunctionData, dateTimeFromAppFunctionData2, stringOrNull, list, intOrNull.intValue(), appFunctionDataWithSpec.getStringOrNull("orderBy"));
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
    public AppFunctionData toAppFunctionData(@NonNull FindEventsParams appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.calendar.v1.FindEventsParams");
        DateTime startDate = appFunctionSerializable.getStartDate();
        if (startDate != null) {
            appFunctionDataBuilder.setAppFunctionData("startDate", c$DateTimeFactory.toAppFunctionData(startDate));
        }
        DateTime endDate = appFunctionSerializable.getEndDate();
        if (endDate != null) {
            appFunctionDataBuilder.setAppFunctionData("endDate", c$DateTimeFactory.toAppFunctionData(endDate));
        }
        String query = appFunctionSerializable.getQuery();
        if (query != null) {
            appFunctionDataBuilder.setString("query", query);
        }
        appFunctionDataBuilder.setStringList("attendeeIds", appFunctionSerializable.getAttendeeIds());
        appFunctionDataBuilder.setInt("maxCount", appFunctionSerializable.getMaxCount());
        String orderBy = appFunctionSerializable.getOrderBy();
        if (orderBy != null) {
            appFunctionDataBuilder.setString("orderBy", orderBy);
        }
        return appFunctionDataBuilder.build();
    }
}
