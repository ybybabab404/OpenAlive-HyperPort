package com.google.android.appfunctions.schema.memory.v1;

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

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.memory.v1.$MemoryItemFactory, reason: invalid class name */
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
public final class C$MemoryItemFactory implements AppFunctionSerializableFactory<MemoryItem> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public MemoryItem fromAppFunctionData(@NonNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.memory.v1.MemoryItem");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("id");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("title");
        String stringOrNull3 = appFunctionDataWithSpec.getStringOrNull("summary");
        String stringOrNull4 = appFunctionDataWithSpec.getStringOrNull("textContent");
        if (stringOrNull4 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        List stringList = appFunctionDataWithSpec.getStringList("keywords");
        if (stringList == null) {
            stringList = CollectionsKt.emptyList();
        }
        List list = stringList;
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("createdAt");
        if (appFunctionData2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        DateTime dateTimeFromAppFunctionData = c$DateTimeFactory.fromAppFunctionData(appFunctionData2);
        AppFunctionData appFunctionData3 = appFunctionDataWithSpec.getAppFunctionData("lastUpdatedAt");
        if (appFunctionData3 != null) {
            return new MemoryItem(stringOrNull, stringOrNull2, stringOrNull3, stringOrNull4, list, dateTimeFromAppFunctionData, c$DateTimeFactory.fromAppFunctionData(appFunctionData3), appFunctionDataWithSpec.getStringOrNull("additionalMetadataJson"));
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
    public AppFunctionData toAppFunctionData(@NonNull MemoryItem appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.memory.v1.MemoryItem");
        appFunctionDataBuilder.setString("id", appFunctionSerializable.getId());
        String title = appFunctionSerializable.getTitle();
        if (title != null) {
            appFunctionDataBuilder.setString("title", title);
        }
        String summary = appFunctionSerializable.getSummary();
        if (summary != null) {
            appFunctionDataBuilder.setString("summary", summary);
        }
        appFunctionDataBuilder.setString("textContent", appFunctionSerializable.getTextContent());
        appFunctionDataBuilder.setStringList("keywords", appFunctionSerializable.getKeywords());
        appFunctionDataBuilder.setAppFunctionData("createdAt", c$DateTimeFactory.toAppFunctionData(appFunctionSerializable.getCreatedAt()));
        appFunctionDataBuilder.setAppFunctionData("lastUpdatedAt", c$DateTimeFactory.toAppFunctionData(appFunctionSerializable.getLastUpdatedAt()));
        String additionalMetadataJson = appFunctionSerializable.getAdditionalMetadataJson();
        if (additionalMetadataJson != null) {
            appFunctionDataBuilder.setString("additionalMetadataJson", additionalMetadataJson);
        }
        return appFunctionDataBuilder.build();
    }
}
