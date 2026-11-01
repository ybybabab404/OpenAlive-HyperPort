package com.miui.aod.gemini;

import android.content.Context;
import android.content.Intent;
import com.google.android.appfunctions.schema.devicestate.v1.DeviceStateItem;
import com.google.android.appfunctions.schema.devicestate.v1.DeviceStateItemMetadata;
import com.google.android.appfunctions.schema.devicestate.v1.LocalizedString;
import com.google.android.appfunctions.schema.devicestate.v1.PerScreenDeviceStates;
import com.google.android.appfunctions.schema.devicestate.v1.PerScreenMetadata;
import com.google.android.appfunctions.schema.devicestate.v1.SetDeviceStateItemParams;
import com.google.android.appfunctions.schema.devicestate.v1.SetDeviceStateItemResponse;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: GeminiScreenConfig.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public abstract class GeminiScreenConfig {

    @NotNull
    private final Context context;

    @NotNull
    private final Context englishContext;

    public GeminiScreenConfig(@NotNull Context context, @NotNull Context englishContext) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(englishContext, "englishContext");
        this.context = context;
        this.englishContext = englishContext;
    }

    @NotNull
    public abstract List<AodStateItemConfig> getAodItemsConfig();

    @NotNull
    public abstract PerAodScreenConfig getAodScreenConfig();

    @NotNull
    public abstract String getConfigValue(@NotNull String str, @NotNull Context context);

    @NotNull
    public final Context getContext() {
        return this.context;
    }

    @NotNull
    public final Context getEnglishContext() {
        return this.englishContext;
    }

    @NotNull
    public abstract String getScreenDescription();

    @Nullable
    public abstract Intent getScreenIntent();

    public final boolean isScreenEnabled() {
        return getAodScreenConfig().getEnabled();
    }

    @Nullable
    public final PerScreenDeviceStates makeUpDeviceStates(@NotNull Context context, @NotNull AodStateCategory aodStateCategory) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(aodStateCategory, "aodStateCategory");
        if (!getAodScreenConfig().getEnabled()) {
            return null;
        }
        List<AodStateItemConfig> aodItemsConfig = getAodItemsConfig();
        ArrayList<AodStateItemConfig> arrayList = new ArrayList();
        for (Object obj : aodItemsConfig) {
            if (((AodStateItemConfig) obj).getEnabled()) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList, 10));
        for (AodStateItemConfig aodStateItemConfig : arrayList) {
            arrayList2.add(new DeviceStateItem(aodStateItemConfig.getSettingKey(), makeUpPurpose(aodStateItemConfig.getSettingKey()), makeUpName(aodStateItemConfig.getSettingKey()), getConfigValue(aodStateItemConfig.getSettingKey(), context), null, aodStateItemConfig.getHintText()));
        }
        return new PerScreenDeviceStates(getScreenDescription(), AodConfigKt.makeUpIntentUri(getScreenIntent()), arrayList2);
    }

    @Nullable
    public final PerScreenMetadata makeUpMetaData(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (!getAodScreenConfig().getEnabled()) {
            return null;
        }
        List<AodStateItemConfig> aodItemsConfig = getAodItemsConfig();
        ArrayList<AodStateItemConfig> arrayList = new ArrayList();
        for (Object obj : aodItemsConfig) {
            if (((AodStateItemConfig) obj).getEnabled()) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList, 10));
        for (AodStateItemConfig aodStateItemConfig : arrayList) {
            arrayList2.add(new DeviceStateItemMetadata(aodStateItemConfig.getSettingKey(), makeUpPurpose(aodStateItemConfig.getSettingKey()), makeUpName(aodStateItemConfig.getSettingKey()), null, Boolean.TRUE, makeUpPossibleValue(context, aodStateItemConfig.getSettingKey()), aodStateItemConfig.getHintText()));
        }
        return new PerScreenMetadata(getScreenDescription(), AodConfigKt.makeUpIntentUri(getScreenIntent()), null, arrayList2, 4, null);
    }

    @Nullable
    public abstract LocalizedString makeUpName(@NotNull String str);

    @Nullable
    public abstract String makeUpPossibleValue(@NotNull Context context, @NotNull String str);

    @NotNull
    public abstract String makeUpPurpose(@NotNull String str);

    @NotNull
    public abstract Pair<Boolean, SetDeviceStateItemResponse> set(@NotNull SetDeviceStateItemParams setDeviceStateItemParams);
}
