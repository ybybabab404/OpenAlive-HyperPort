package com.miui.aod.gemini;

import android.content.Context;
import android.content.Intent;
import com.google.android.appfunctions.schema.devicestate.v1.LocalizedString;
import com.google.android.appfunctions.schema.devicestate.v1.SetDeviceStateItemParams;
import com.google.android.appfunctions.schema.devicestate.v1.SetDeviceStateItemResponse;
import com.miui.aod.R;
import com.miui.aod.Utils;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: AodNotificationAnimationScreen.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class AodNotificationAnimationScreen extends GeminiScreenConfig {

    @NotNull
    public static final String ACTION_NOTIFICATION_ANIMATION = "com.miui.aod.notification_animation";

    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    public static final String SCREEN_KEYS_AOD_NOTIFICATION = "ALWAYS_ON_DISPLAY_NOTIFICATION";

    @NotNull
    public static final String SETTINGS_KEY_AOD_NOTIFICATION = "AOD_NOTIFICATION";

    @NotNull
    private final List<AodStateItemConfig> aodItemsConfig;

    @NotNull
    private final PerAodScreenConfig aodScreenConfig;

    /* JADX INFO: compiled from: AodNotificationAnimationScreen.kt */
    @Metadata
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AodNotificationAnimationScreen(@NotNull Context context, @NotNull Context englishContext) {
        super(context, englishContext);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(englishContext, "englishContext");
        this.aodScreenConfig = new PerAodScreenConfig(Utils.isSupportAodAnimateDevice(context), SCREEN_KEYS_AOD_NOTIFICATION, SetsKt.setOf(AodStateCategory.UNCATEGORIZED));
        boolean zIsSupportAodAnimateDevice = Utils.isSupportAodAnimateDevice(context);
        String string = context.getString(R.string.hint_text_aod_notification);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        this.aodItemsConfig = CollectionsKt.listOf(new AodStateItemConfig(zIsSupportAodAnimateDevice, SETTINGS_KEY_AOD_NOTIFICATION, SCREEN_KEYS_AOD_NOTIFICATION, string));
    }

    @Override // com.miui.aod.gemini.GeminiScreenConfig
    @NotNull
    public List<AodStateItemConfig> getAodItemsConfig() {
        return this.aodItemsConfig;
    }

    @Override // com.miui.aod.gemini.GeminiScreenConfig
    @NotNull
    public PerAodScreenConfig getAodScreenConfig() {
        return this.aodScreenConfig;
    }

    @Override // com.miui.aod.gemini.GeminiScreenConfig
    @NotNull
    public String getConfigValue(@NotNull String settingsKey, @NotNull Context context) {
        Intrinsics.checkNotNullParameter(settingsKey, "settingsKey");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!Intrinsics.areEqual(settingsKey, SETTINGS_KEY_AOD_NOTIFICATION)) {
            return "";
        }
        String string = context.getString(R.string.personalize_notify_style_title);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }

    @Override // com.miui.aod.gemini.GeminiScreenConfig
    @NotNull
    public String getScreenDescription() {
        String string = getEnglishContext().getString(R.string.description_aod_notification);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }

    @Override // com.miui.aod.gemini.GeminiScreenConfig
    @NotNull
    public Intent getScreenIntent() {
        return new Intent(ACTION_NOTIFICATION_ANIMATION);
    }

    @Override // com.miui.aod.gemini.GeminiScreenConfig
    @Nullable
    public LocalizedString makeUpName(@NotNull String settingsKey) {
        Intrinsics.checkNotNullParameter(settingsKey, "settingsKey");
        if (Intrinsics.areEqual(settingsKey, SETTINGS_KEY_AOD_NOTIFICATION)) {
            String string = getEnglishContext().getString(R.string.select_notification_animation_style);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            return new LocalizedString(string, getContext().getResources().getString(R.string.select_notification_animation_style));
        }
        String string2 = getEnglishContext().getString(R.string.personalize_notify_style_title);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        return new LocalizedString(string2, getContext().getResources().getString(R.string.personalize_notify_style_title));
    }

    @Override // com.miui.aod.gemini.GeminiScreenConfig
    @Nullable
    public String makeUpPossibleValue(@NotNull Context context, @NotNull String settingsKey) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(settingsKey, "settingsKey");
        return null;
    }

    @Override // com.miui.aod.gemini.GeminiScreenConfig
    @NotNull
    public String makeUpPurpose(@NotNull String settingsKey) {
        Intrinsics.checkNotNullParameter(settingsKey, "settingsKey");
        if (Intrinsics.areEqual(settingsKey, SETTINGS_KEY_AOD_NOTIFICATION)) {
            String string = getEnglishContext().getString(R.string.select_notification_animation_style);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            return string;
        }
        String string2 = getEnglishContext().getString(R.string.personalize_notify_style_title);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        return string2;
    }

    @Override // com.miui.aod.gemini.GeminiScreenConfig
    @NotNull
    public Pair<Boolean, SetDeviceStateItemResponse> set(@NotNull SetDeviceStateItemParams setDeviceStateItemParams) {
        Intrinsics.checkNotNullParameter(setDeviceStateItemParams, "setDeviceStateItemParams");
        String key = setDeviceStateItemParams.getKey();
        setDeviceStateItemParams.getValue();
        return Intrinsics.areEqual(key, SETTINGS_KEY_AOD_NOTIFICATION) ? new Pair<>(Boolean.TRUE, null) : new Pair<>(Boolean.FALSE, null);
    }
}
