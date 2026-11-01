package com.google.android.appfunctions.schema.messages.v1;

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

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.messages.v1.$FindMessagesParamsFactory, reason: invalid class name */
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
public final class C$FindMessagesParamsFactory implements AppFunctionSerializableFactory<FindMessagesParams> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public FindMessagesParams fromAppFunctionData(@NonNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.messages.v1.FindMessagesParams");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("senderId");
        List stringList = appFunctionDataWithSpec.getStringList("recipientsIds");
        if (stringList == null) {
            stringList = CollectionsKt.emptyList();
        }
        List list = stringList;
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("startTime");
        DateTime dateTimeFromAppFunctionData = appFunctionData2 != null ? c$DateTimeFactory.fromAppFunctionData(appFunctionData2) : null;
        AppFunctionData appFunctionData3 = appFunctionDataWithSpec.getAppFunctionData("endTime");
        DateTime dateTimeFromAppFunctionData2 = appFunctionData3 != null ? c$DateTimeFactory.fromAppFunctionData(appFunctionData3) : null;
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("query");
        Boolean booleanOrNull = appFunctionDataWithSpec.getBooleanOrNull("read");
        Integer intOrNull = appFunctionDataWithSpec.getIntOrNull("maxCount");
        if (intOrNull != null) {
            return new FindMessagesParams(stringOrNull, list, dateTimeFromAppFunctionData, dateTimeFromAppFunctionData2, stringOrNull2, booleanOrNull, intOrNull.intValue());
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
    public AppFunctionData toAppFunctionData(@NonNull FindMessagesParams appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.messages.v1.FindMessagesParams");
        String senderId = appFunctionSerializable.getSenderId();
        if (senderId != null) {
            appFunctionDataBuilder.setString("senderId", senderId);
        }
        appFunctionDataBuilder.setStringList("recipientsIds", appFunctionSerializable.getRecipientsIds());
        DateTime startTime = appFunctionSerializable.getStartTime();
        if (startTime != null) {
            appFunctionDataBuilder.setAppFunctionData("startTime", c$DateTimeFactory.toAppFunctionData(startTime));
        }
        DateTime endTime = appFunctionSerializable.getEndTime();
        if (endTime != null) {
            appFunctionDataBuilder.setAppFunctionData("endTime", c$DateTimeFactory.toAppFunctionData(endTime));
        }
        String query = appFunctionSerializable.getQuery();
        if (query != null) {
            appFunctionDataBuilder.setString("query", query);
        }
        Boolean read = appFunctionSerializable.getRead();
        if (read != null) {
            appFunctionDataBuilder.setBoolean("read", read.booleanValue());
        }
        appFunctionDataBuilder.setInt("maxCount", appFunctionSerializable.getMaxCount());
        return appFunctionDataBuilder.build();
    }
}
