package com.miui.aod.gemini;

import android.content.Context;
import android.content.Intent;
import com.google.android.appfunctions.schema.devicestate.v1.LocalizedString;
import com.google.android.appfunctions.schema.devicestate.v1.SetDeviceStateItemParams;
import com.google.android.appfunctions.schema.devicestate.v1.SetDeviceStateItemResponse;
import com.google.gson.Gson;
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
/* JADX INFO: compiled from: AodStateScreen.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class AodStateScreen extends GeminiScreenConfig {

    @NotNull
    public static final String ACTION_STYLE_LIST = "com.miui.aod.style_list";

    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    public static final String SCREEN_KEYS_AOD = "ALWAYS_ON_DISPLAY";

    @NotNull
    public static final String SETTINGS_KEY_AOD_BATTERY_SAVER = "AOD_BATTERY_SAVER";

    @NotNull
    public static final String SETTINGS_KEY_AOD_CHARGING_ALWAYS = "AOD_CHARGING_ALWAYS";

    @NotNull
    public static final String SETTINGS_KEY_AOD_FULLSCREEN = "AOD_FULLSCREEN";

    @NotNull
    public static final String SETTINGS_KEY_AOD_SWITCH = "AOD_SWITCH";

    @NotNull
    private final List<AodStateItemConfig> aodItemsConfig;

    @NotNull
    private final PerAodScreenConfig aodScreenConfig;

    /* JADX INFO: compiled from: AodStateScreen.kt */
    @Metadata
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AodStateScreen(@NotNull Context context, @NotNull Context englishContext) {
        super(context, englishContext);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(englishContext, "englishContext");
        this.aodScreenConfig = new PerAodScreenConfig(Utils.supportAod(context), SCREEN_KEYS_AOD, SetsKt.setOf(AodStateCategory.UNCATEGORIZED));
        String string = context.getString(R.string.hint_text_aod_switch);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        AodStateItemConfig aodStateItemConfig = new AodStateItemConfig(true, SETTINGS_KEY_AOD_SWITCH, SCREEN_KEYS_AOD, string);
        boolean zIsSupportFsAod = Utils.isSupportFsAod();
        String string2 = context.getString(R.string.hint_text_aod_fullscreen);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        AodStateItemConfig aodStateItemConfig2 = new AodStateItemConfig(zIsSupportFsAod, SETTINGS_KEY_AOD_FULLSCREEN, SCREEN_KEYS_AOD, string2);
        boolean zIsPadDevice = Utils.isPadDevice();
        String string3 = context.getString(R.string.hint_text_aod_charging_always);
        Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
        AodStateItemConfig aodStateItemConfig3 = new AodStateItemConfig(zIsPadDevice, SETTINGS_KEY_AOD_CHARGING_ALWAYS, SCREEN_KEYS_AOD, string3);
        boolean zIsPadDevice2 = Utils.isPadDevice();
        String string4 = context.getString(R.string.hint_text_aod_battery_saver);
        Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
        this.aodItemsConfig = CollectionsKt.listOf((Object[]) new AodStateItemConfig[]{aodStateItemConfig, aodStateItemConfig2, aodStateItemConfig3, new AodStateItemConfig(zIsPadDevice2, "AOD_BATTERY_SAVER", SCREEN_KEYS_AOD, string4)});
    }

    private final SetDeviceStateItemResponse aodFsSet(Context context, String str) {
        if (!Utils.isSupportFsAod()) {
            return new SetDeviceStateItemResponse(false, fullScreenAodStyle(context), context.getString(R.string.aod_failure_reason_not_support));
        }
        if (!Utils.isAodEnable(context)) {
            return new SetDeviceStateItemResponse(false, fullScreenAodStyle(context), context.getString(R.string.aod_failure_reason_precondition_not_met));
        }
        int i = Integer.parseInt(str);
        boolean zIsFsAodSameToLock = Utils.isFsAodSameToLock(context);
        boolean z = i == 1;
        if (zIsFsAodSameToLock == z) {
            return new SetDeviceStateItemResponse(true, fullScreenAodStyle(context), null, 4, null);
        }
        if (z && !Utils.isFullAodSupport(context)) {
            return new SetDeviceStateItemResponse(false, fullScreenAodStyle(context), context.getString(R.string.aod_failure_reason_not_support));
        }
        Utils.setFsAodStyle(context, i);
        return new SetDeviceStateItemResponse(true, fullScreenAodStyle(context), null, 4, null);
    }

    private final SetDeviceStateItemResponse aodSwitchSet(Context context, String str) {
        boolean z = Boolean.parseBoolean(str);
        if (Utils.isAodEnable(context) == z) {
            return new SetDeviceStateItemResponse(true, aodSwitchState(context), null, 4, null);
        }
        if (z && Utils.isInvertColorsEnable(context)) {
            return new SetDeviceStateItemResponse(false, aodSwitchState(context), context.getString(R.string.aod_failure_reason_precondition_not_met));
        }
        Utils.setAodEnable(context, z);
        Utils.setAodModeUserSet(context, z);
        Utils.updateTouchMode(context, false, true);
        return new SetDeviceStateItemResponse(true, aodSwitchState(context), null, 4, null);
    }

    private final String aodSwitchState(Context context) {
        return String.valueOf(Utils.isAodEnable(context));
    }

    private final SetDeviceStateItemResponse batterySaverSet(Context context, String str) {
        if (!Utils.isAodEnable(context)) {
            return new SetDeviceStateItemResponse(false, String.valueOf(Utils.disableAodInPowerSaveModePermitted(context)), context.getString(R.string.aod_failure_reason_precondition_not_met));
        }
        boolean z = Boolean.parseBoolean(str);
        if (Utils.disableAodInPowerSaveModePermitted(context) == z) {
            return new SetDeviceStateItemResponse(true, String.valueOf(Utils.disableAodInPowerSaveModePermitted(context)), null, 4, null);
        }
        Utils.setDisableAodInPowerSaveModePermitted(context, z);
        return new SetDeviceStateItemResponse(true, String.valueOf(Utils.disableAodInPowerSaveModePermitted(context)), null, 4, null);
    }

    private final SetDeviceStateItemResponse chargingAlwaysSet(Context context, String str) {
        if (!Utils.isAodEnable(context)) {
            return new SetDeviceStateItemResponse(false, chargingAlwaysState(context), context.getString(R.string.aod_failure_reason_precondition_not_met));
        }
        boolean z = Boolean.parseBoolean(str);
        if (Utils.isShowAlwaysWhenCharging(context) == z) {
            return new SetDeviceStateItemResponse(true, chargingAlwaysState(context), null, 4, null);
        }
        Utils.setShowStyle(context, z ? 2 : 3);
        Utils.updateTouchMode(context, false, true);
        return new SetDeviceStateItemResponse(true, chargingAlwaysState(context), null, 4, null);
    }

    private final String chargingAlwaysState(Context context) {
        return String.valueOf(Utils.isShowAlwaysWhenCharging(context));
    }

    private final String fullScreenAodStyle(Context context) {
        return Utils.isFsAodSameToLock(context) ? "1" : "0";
    }

    private final String getFullScreenPossibleValue(Context context) {
        String json = new Gson().toJson(new PredeterminedFinitePossibleValue(new PredeterminedFinitePossibleValue.PredeterminedFiniteDetail(null, CollectionsKt.listOf((Object[]) new String[]{"1", "0"}), 1, null)));
        Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
        return json;
    }

    private final String getSwitchPossibleValue(Context context) {
        String json = new Gson().toJson(new TogglePossibleValue(null, 1, null));
        Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
        return json;
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
        switch (settingsKey.hashCode()) {
            case -2094330118:
                return !settingsKey.equals("AOD_BATTERY_SAVER") ? "" : String.valueOf(Utils.disableAodInPowerSaveModePermitted(context));
            case -1819112003:
                return settingsKey.equals(SETTINGS_KEY_AOD_SWITCH) ? aodSwitchState(context) : "";
            case 434087732:
                return !settingsKey.equals(SETTINGS_KEY_AOD_CHARGING_ALWAYS) ? "" : chargingAlwaysState(context);
            case 1665588676:
                return !settingsKey.equals(SETTINGS_KEY_AOD_FULLSCREEN) ? "" : fullScreenAodStyle(context);
            default:
                return "";
        }
    }

    @Override // com.miui.aod.gemini.GeminiScreenConfig
    @NotNull
    public String getScreenDescription() {
        if (Utils.isPadDevice()) {
            String string = getEnglishContext().getString(R.string.description_aod_state_pad);
            Intrinsics.checkNotNull(string);
            return string;
        }
        String string2 = getEnglishContext().getString(R.string.description_aod_state);
        Intrinsics.checkNotNull(string2);
        return string2;
    }

    @Override // com.miui.aod.gemini.GeminiScreenConfig
    @NotNull
    public Intent getScreenIntent() {
        return new Intent(ACTION_STYLE_LIST);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.miui.aod.gemini.GeminiScreenConfig
    @Nullable
    public LocalizedString makeUpName(@NotNull String settingsKey) {
        Intrinsics.checkNotNullParameter(settingsKey, "settingsKey");
        switch (settingsKey.hashCode()) {
            case -2094330118:
                if (settingsKey.equals("AOD_BATTERY_SAVER")) {
                    String string = getEnglishContext().getString(R.string.disable_aod_in_power_save_mode);
                    Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                    return new LocalizedString(string, getContext().getResources().getString(R.string.disable_aod_in_power_save_mode));
                }
                break;
            case -1819112003:
                if (settingsKey.equals(SETTINGS_KEY_AOD_SWITCH)) {
                    String string2 = getEnglishContext().getString(R.string.ambient_display_screen_title);
                    Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                    return new LocalizedString(string2, getContext().getResources().getString(R.string.ambient_display_screen_title));
                }
                break;
            case 434087732:
                if (settingsKey.equals(SETTINGS_KEY_AOD_CHARGING_ALWAYS)) {
                    String string3 = getEnglishContext().getString(R.string.aod_show_mode_title_des_pad);
                    Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
                    return new LocalizedString(string3, getContext().getResources().getString(R.string.aod_show_mode_title_des_pad));
                }
                break;
            case 1665588676:
                if (settingsKey.equals(SETTINGS_KEY_AOD_FULLSCREEN)) {
                    String string4 = getEnglishContext().getString(R.string.aod_resting_screen_style_title);
                    Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
                    return new LocalizedString(string4, getContext().getResources().getString(R.string.aod_resting_screen_style_title));
                }
                break;
        }
        String string5 = getEnglishContext().getString(R.string.aod_mode_title);
        Intrinsics.checkNotNullExpressionValue(string5, "getString(...)");
        return new LocalizedString(string5, getContext().getResources().getString(R.string.aod_mode_title));
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.miui.aod.gemini.GeminiScreenConfig
    @Nullable
    public String makeUpPossibleValue(@NotNull Context context, @NotNull String settingsKey) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(settingsKey, "settingsKey");
        switch (settingsKey.hashCode()) {
            case -2094330118:
                if (settingsKey.equals("AOD_BATTERY_SAVER")) {
                    return getSwitchPossibleValue(context);
                }
                return null;
            case -1819112003:
                if (settingsKey.equals(SETTINGS_KEY_AOD_SWITCH)) {
                    return getSwitchPossibleValue(context);
                }
                return null;
            case 434087732:
                if (settingsKey.equals(SETTINGS_KEY_AOD_CHARGING_ALWAYS)) {
                    return getSwitchPossibleValue(context);
                }
                return null;
            case 1665588676:
                if (settingsKey.equals(SETTINGS_KEY_AOD_FULLSCREEN)) {
                    return getFullScreenPossibleValue(context);
                }
                return null;
            default:
                return null;
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.miui.aod.gemini.GeminiScreenConfig
    @NotNull
    public String makeUpPurpose(@NotNull String settingsKey) {
        Intrinsics.checkNotNullParameter(settingsKey, "settingsKey");
        switch (settingsKey.hashCode()) {
            case -2094330118:
                if (settingsKey.equals("AOD_BATTERY_SAVER")) {
                    String string = getEnglishContext().getString(R.string.disable_aod_in_power_save_mode);
                    Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                    return string;
                }
                break;
            case -1819112003:
                if (settingsKey.equals(SETTINGS_KEY_AOD_SWITCH)) {
                    String string2 = getEnglishContext().getString(R.string.ambient_display_screen_title);
                    Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                    return string2;
                }
                break;
            case 434087732:
                if (settingsKey.equals(SETTINGS_KEY_AOD_CHARGING_ALWAYS)) {
                    String string3 = getEnglishContext().getString(R.string.aod_show_mode_title_des_pad);
                    Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
                    return string3;
                }
                break;
            case 1665588676:
                if (settingsKey.equals(SETTINGS_KEY_AOD_FULLSCREEN)) {
                    String string4 = getEnglishContext().getString(R.string.aod_resting_screen_style_title);
                    Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
                    return string4;
                }
                break;
        }
        String string5 = getEnglishContext().getString(R.string.aod_mode_title);
        Intrinsics.checkNotNullExpressionValue(string5, "getString(...)");
        return string5;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.miui.aod.gemini.GeminiScreenConfig
    @NotNull
    public Pair<Boolean, SetDeviceStateItemResponse> set(@NotNull SetDeviceStateItemParams setDeviceStateItemParams) {
        Intrinsics.checkNotNullParameter(setDeviceStateItemParams, "setDeviceStateItemParams");
        String key = setDeviceStateItemParams.getKey();
        String value = setDeviceStateItemParams.getValue();
        switch (key.hashCode()) {
            case -2094330118:
                if (key.equals("AOD_BATTERY_SAVER")) {
                    return Utils.isPadDevice() ? new Pair<>(Boolean.TRUE, batterySaverSet(getContext(), value)) : new Pair<>(Boolean.FALSE, null);
                }
                break;
            case -1819112003:
                if (key.equals(SETTINGS_KEY_AOD_SWITCH)) {
                    return new Pair<>(Boolean.TRUE, aodSwitchSet(getContext(), value));
                }
                break;
            case 434087732:
                if (key.equals(SETTINGS_KEY_AOD_CHARGING_ALWAYS)) {
                    return Utils.isPadDevice() ? new Pair<>(Boolean.TRUE, chargingAlwaysSet(getContext(), value)) : new Pair<>(Boolean.FALSE, null);
                }
                break;
            case 1665588676:
                if (key.equals(SETTINGS_KEY_AOD_FULLSCREEN)) {
                    return new Pair<>(Boolean.TRUE, aodFsSet(getContext(), value));
                }
                break;
        }
        return new Pair<>(Boolean.FALSE, null);
    }
}
