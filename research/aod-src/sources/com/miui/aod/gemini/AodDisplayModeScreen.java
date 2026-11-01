package com.miui.aod.gemini;

import android.content.Context;
import android.content.Intent;
import com.google.android.appfunctions.schema.devicestate.v1.LocalizedString;
import com.google.android.appfunctions.schema.devicestate.v1.SetDeviceStateItemParams;
import com.google.android.appfunctions.schema.devicestate.v1.SetDeviceStateItemResponse;
import com.google.gson.Gson;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.attention.AttentionManager;
import com.miui.aod.utils.MiuiSettingsUtils;
import com.miui.aod.widget.AODSettings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
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
/* JADX INFO: compiled from: AodDisplayModeScreen.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class AodDisplayModeScreen extends GeminiScreenConfig {

    @NotNull
    public static final String ACTION_SETTING = "com.miui.aod.Setting";

    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static final String KEY_END_TIME = "endTime";

    @NotNull
    private static final String KEY_START_TIME = "startTime";

    @NotNull
    public static final String SCREEN_KEYS_AOD_DISPLAY_ITEMS = "AOD_DISPLAY_ITEMS";

    @NotNull
    public static final String SETTINGS_KEY_AOD_BATTERY_SAVER = "AOD_BATTERY_SAVER";

    @NotNull
    public static final String SETTINGS_KEY_AOD_DISPLAY_ITEMS_SCHEDULED = "AOD_DISPLAY_ITEMS_SCHEDULED";

    @NotNull
    public static final String SETTINGS_KEY_AOD_DISPLAY_ITEMS_STYLE = "AOD_DISPLAY_ITEMS_STYLE";

    @NotNull
    private final List<AodStateItemConfig> aodItemsConfig;

    @NotNull
    private final PerAodScreenConfig aodScreenConfig;

    /* JADX INFO: compiled from: AodDisplayModeScreen.kt */
    @Metadata
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: AodDisplayModeScreen.kt */
    @Metadata
    public static final class TimeRangeResult {
        private final long endTime;
        private final long startTime;

        public TimeRangeResult(long j, long j2) {
            this.startTime = j;
            this.endTime = j2;
        }

        public static /* synthetic */ TimeRangeResult copy$default(TimeRangeResult timeRangeResult, long j, long j2, int i, Object obj) {
            if ((i & 1) != 0) {
                j = timeRangeResult.startTime;
            }
            if ((i & 2) != 0) {
                j2 = timeRangeResult.endTime;
            }
            return timeRangeResult.copy(j, j2);
        }

        public final long component1() {
            return this.startTime;
        }

        public final long component2() {
            return this.endTime;
        }

        @NotNull
        public final TimeRangeResult copy(long j, long j2) {
            return new TimeRangeResult(j, j2);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TimeRangeResult)) {
                return false;
            }
            TimeRangeResult timeRangeResult = (TimeRangeResult) obj;
            return this.startTime == timeRangeResult.startTime && this.endTime == timeRangeResult.endTime;
        }

        public final long getEndTime() {
            return this.endTime;
        }

        public final long getStartTime() {
            return this.startTime;
        }

        public int hashCode() {
            return (Long.hashCode(this.startTime) * 31) + Long.hashCode(this.endTime);
        }

        @NotNull
        public String toString() {
            return "TimeRangeResult(startTime=" + this.startTime + ", endTime=" + this.endTime + ")";
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AodDisplayModeScreen(@NotNull Context context, @NotNull Context englishContext) {
        super(context, englishContext);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(englishContext, "englishContext");
        this.aodScreenConfig = new PerAodScreenConfig(Utils.supportDisplayModePage(context), SCREEN_KEYS_AOD_DISPLAY_ITEMS, SetsKt.setOf(AodStateCategory.UNCATEGORIZED));
        boolean z = !getAvailableShowStyles(context).isEmpty();
        String string = context.getString(R.string.hint_text_aod_display_items_style);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        AodStateItemConfig aodStateItemConfig = new AodStateItemConfig(z, SETTINGS_KEY_AOD_DISPLAY_ITEMS_STYLE, SCREEN_KEYS_AOD_DISPLAY_ITEMS, string);
        boolean z2 = (AODSettings.onlySupportKeycodeGoto() || Utils.isPadDevice() || (AODSettings.isO2SDevice() && Utils.isFsAodSameToLock(context))) ? false : true;
        String string2 = context.getString(R.string.hint_text_aod_display_items_scheduled);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        AodStateItemConfig aodStateItemConfig2 = new AodStateItemConfig(z2, SETTINGS_KEY_AOD_DISPLAY_ITEMS_SCHEDULED, SCREEN_KEYS_AOD_DISPLAY_ITEMS, string2);
        String string3 = context.getString(R.string.hint_text_aod_battery_saver);
        Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
        this.aodItemsConfig = CollectionsKt.listOf((Object[]) new AodStateItemConfig[]{aodStateItemConfig, aodStateItemConfig2, new AodStateItemConfig(true, "AOD_BATTERY_SAVER", SCREEN_KEYS_AOD_DISPLAY_ITEMS, string3)});
    }

    private final SetDeviceStateItemResponse aodBatterySaverSet(Context context, String str) {
        if (!Utils.isAodEnable(context)) {
            return new SetDeviceStateItemResponse(false, String.valueOf(Utils.disableAodInPowerSaveModePermitted(context)), context.getString(R.string.aod_failure_reason_precondition_not_met));
        }
        Utils.setDisableAodInPowerSaveModePermitted(context, Boolean.parseBoolean(str));
        return new SetDeviceStateItemResponse(true, String.valueOf(Utils.disableAodInPowerSaveModePermitted(context)), null, 4, null);
    }

    private final SetDeviceStateItemResponse aodDisplayItemsSet(Context context, String str) {
        if (!Utils.isAodEnable(context)) {
            return new SetDeviceStateItemResponse(false, aodDisplayItemsStyle(context), context.getString(R.string.aod_failure_reason_precondition_not_met));
        }
        List<Integer> availableShowStyles = getAvailableShowStyles(context);
        Integer intOrNull = StringsKt.toIntOrNull(str);
        if (intOrNull == null || !availableShowStyles.contains(intOrNull)) {
            return new SetDeviceStateItemResponse(false, aodDisplayItemsStyle(context), context.getString(R.string.aod_failure_reason_not_support));
        }
        Utils.setShowStyle(context, intOrNull.intValue());
        Utils.updateTouchMode(context, false, true);
        return new SetDeviceStateItemResponse(true, aodDisplayItemsStyle(context), null, 4, null);
    }

    private final String aodDisplayItemsStyle(Context context) {
        return String.valueOf(Utils.getShowStyle(context));
    }

    private final SetDeviceStateItemResponse aodScheduledSet(Context context, String str) {
        if (!Utils.isAodEnable(context) && !Utils.isTimingStyle(context)) {
            return new SetDeviceStateItemResponse(false, getScheduledValue(context), context.getString(R.string.aod_failure_reason_precondition_not_met));
        }
        TimeRangeResult timeRange = parseTimeRange(str, Utils.getAodStartTime(context), Utils.getAodEndTime(context));
        if (timeRange == null) {
            return new SetDeviceStateItemResponse(false, getScheduledValue(context), context.getString(R.string.aod_failure_reason_invalid_value));
        }
        long startTime = timeRange.getStartTime() / 60000;
        long endTime = timeRange.getEndTime() / 60000;
        try {
            MiuiSettingsUtils.Secure.putInt(context.getContentResolver(), "aod_start", (int) startTime);
            MiuiSettingsUtils.Secure.putInt(context.getContentResolver(), "aod_end", (int) endTime);
            return new SetDeviceStateItemResponse(true, getScheduledValue(context), null, 4, null);
        } catch (Exception unused) {
            return new SetDeviceStateItemResponse(false, getScheduledValue(context), context.getString(R.string.aod_failure_reason_invalid_value));
        }
    }

    /* JADX WARN: Code duplicated, block: B:16:0x005d  */
    /* JADX WARN: Code duplicated, block: B:20:0x0065  */
    private final List<Integer> getAvailableShowStyles(Context context) {
        boolean z;
        boolean zIsPadDevice = Utils.isPadDevice();
        boolean zIsKeycodeGotoEnable = AODSettings.isKeycodeGotoEnable();
        boolean zOnlySupportKeycodeGoto = AODSettings.onlySupportKeycodeGoto();
        boolean z2 = AODSettings.isO2SDevice() && Utils.isFsAodSameToLock(context);
        boolean zSupportAttention = AttentionManager.supportAttention(context);
        List listListOf = CollectionsKt.listOf((Object[]) new Integer[]{0, 1, 2, 3});
        ArrayList arrayList = new ArrayList();
        for (Object obj : listListOf) {
            int iIntValue = ((Number) obj).intValue();
            if (iIntValue != 0) {
                if (iIntValue != 1) {
                    if (iIntValue != 2) {
                        if (iIntValue == 3) {
                            z = zSupportAttention;
                        }
                    } else if (!zOnlySupportKeycodeGoto && !z2) {
                        z = true;
                    }
                    z = false;
                } else if (zIsPadDevice || zOnlySupportKeycodeGoto || z2) {
                    z = false;
                } else {
                    z = true;
                }
            } else if (zIsPadDevice || !zIsKeycodeGotoEnable) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    private final String getBatterySaverPossibleValue(Context context) {
        String json = new Gson().toJson(new TogglePossibleValue(null, 1, null));
        Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
        return json;
    }

    private final String getScheduledPossibleValue(Context context) {
        String json = new Gson().toJson(new SetAodDeviceStateString(CollectionsKt.listOf(context.getString(R.string.scheduled_possible)), null, 2, null));
        Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
        return json;
    }

    private final String getScheduledValue(Context context) {
        long aodStartTime = Utils.getAodStartTime(context);
        long aodEndTime = Utils.getAodEndTime(context);
        long j = 1000;
        long j2 = 60;
        long j3 = (aodStartTime / j) / j2;
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format("%02d:%02d", Arrays.copyOf(new Object[]{Long.valueOf(j3 / j2), Long.valueOf(j3 % j2)}, 2));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        long j4 = (aodEndTime / j) / j2;
        String str2 = String.format("%02d:%02d", Arrays.copyOf(new Object[]{Long.valueOf(j4 / j2), Long.valueOf(j4 % j2)}, 2));
        Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
        return "startTime:" + str + ",endTime:" + str2;
    }

    private final String getStylePossibleValue(Context context) {
        List<Integer> availableShowStyles = getAvailableShowStyles(context);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(availableShowStyles, 10));
        Iterator<T> it = availableShowStyles.iterator();
        while (it.hasNext()) {
            arrayList.add(String.valueOf(((Number) it.next()).intValue()));
        }
        String json = new Gson().toJson(new PredeterminedFinitePossibleValue(new PredeterminedFinitePossibleValue.PredeterminedFiniteDetail(null, arrayList, 1, null)));
        Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
        return json;
    }

    private final TimeRangeResult parseTimeRange(String str, long j, long j2) {
        String string = StringsKt.trim(new Regex("\\s").replace(str, "")).toString();
        MatchResult matchResultFind$default = Regex.find$default(new Regex("startTime:(\\d{1,2}:\\d{0,2})"), string, 0, 2, null);
        if (matchResultFind$default != null && matchResultFind$default.getGroupValues().size() >= 2) {
            Long timeToMilliseconds = parseTimeToMilliseconds((String) matchResultFind$default.getGroupValues().get(1));
            if (timeToMilliseconds == null) {
                return null;
            }
            j = timeToMilliseconds.longValue();
        }
        MatchResult matchResultFind$default2 = Regex.find$default(new Regex("endTime:(\\d{1,2}:\\d{0,2})"), string, 0, 2, null);
        if (matchResultFind$default2 != null && matchResultFind$default2.getGroupValues().size() >= 2) {
            Long timeToMilliseconds2 = parseTimeToMilliseconds((String) matchResultFind$default2.getGroupValues().get(1));
            if (timeToMilliseconds2 == null) {
                return null;
            }
            j2 = timeToMilliseconds2.longValue();
        }
        return new TimeRangeResult(j, j2);
    }

    private final Long parseTimeToMilliseconds(String str) {
        Integer intOrNull;
        List listSplit$default = StringsKt.split$default(str, new String[]{":"}, false, 0, 6, null);
        if (listSplit$default.isEmpty()) {
            return null;
        }
        int iIntValue = 0;
        Integer intOrNull2 = StringsKt.toIntOrNull((String) listSplit$default.get(0));
        if (intOrNull2 == null) {
            return null;
        }
        int iIntValue2 = intOrNull2.intValue();
        if (listSplit$default.size() <= 1 || ((intOrNull = StringsKt.toIntOrNull((String) listSplit$default.get(1))) != null && (iIntValue = intOrNull.intValue()) >= 0 && iIntValue <= 59)) {
            return Long.valueOf((((long) iIntValue2) * 3600000) + (((long) iIntValue) * 60000));
        }
        return null;
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
        int iHashCode = settingsKey.hashCode();
        if (iHashCode == -2094330118) {
            return !settingsKey.equals("AOD_BATTERY_SAVER") ? "" : String.valueOf(Utils.disableAodInPowerSaveModePermitted(context));
        }
        if (iHashCode != -497514072) {
            return (iHashCode == 1744086060 && settingsKey.equals(SETTINGS_KEY_AOD_DISPLAY_ITEMS_STYLE)) ? aodDisplayItemsStyle(context) : "";
        }
        return !settingsKey.equals(SETTINGS_KEY_AOD_DISPLAY_ITEMS_SCHEDULED) ? "" : getScheduledValue(context);
    }

    @Override // com.miui.aod.gemini.GeminiScreenConfig
    @NotNull
    public String getScreenDescription() {
        String string = getEnglishContext().getString(R.string.description_aod_display_mode);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }

    @Override // com.miui.aod.gemini.GeminiScreenConfig
    @NotNull
    public Intent getScreenIntent() {
        return new Intent(ACTION_SETTING);
    }

    @Override // com.miui.aod.gemini.GeminiScreenConfig
    @NotNull
    public LocalizedString makeUpName(@NotNull String settingsKey) {
        Intrinsics.checkNotNullParameter(settingsKey, "settingsKey");
        int iHashCode = settingsKey.hashCode();
        if (iHashCode != -2094330118) {
            if (iHashCode != -497514072) {
                if (iHashCode == 1744086060 && settingsKey.equals(SETTINGS_KEY_AOD_DISPLAY_ITEMS_STYLE)) {
                    String string = getEnglishContext().getString(R.string.aod_show_style_title);
                    Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                    return new LocalizedString(string, getContext().getResources().getString(R.string.aod_show_style_title));
                }
            } else if (settingsKey.equals(SETTINGS_KEY_AOD_DISPLAY_ITEMS_SCHEDULED)) {
                String string2 = getEnglishContext().getString(R.string.scheduled_style_summary);
                Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                return new LocalizedString(string2, getContext().getResources().getString(R.string.scheduled_style_summary));
            }
        } else if (settingsKey.equals("AOD_BATTERY_SAVER")) {
            String string3 = getEnglishContext().getString(R.string.disable_aod_in_power_save_mode);
            Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
            return new LocalizedString(string3, getContext().getResources().getString(R.string.disable_aod_in_power_save_mode));
        }
        String string4 = getEnglishContext().getString(R.string.aod_mode_title);
        Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
        return new LocalizedString(string4, getContext().getResources().getString(R.string.aod_mode_title));
    }

    @Override // com.miui.aod.gemini.GeminiScreenConfig
    @Nullable
    public String makeUpPossibleValue(@NotNull Context context, @NotNull String settingsKey) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(settingsKey, "settingsKey");
        int iHashCode = settingsKey.hashCode();
        if (iHashCode == -2094330118) {
            if (settingsKey.equals("AOD_BATTERY_SAVER")) {
                return getBatterySaverPossibleValue(context);
            }
            return null;
        }
        if (iHashCode == -497514072) {
            if (settingsKey.equals(SETTINGS_KEY_AOD_DISPLAY_ITEMS_SCHEDULED)) {
                return getScheduledPossibleValue(context);
            }
            return null;
        }
        if (iHashCode == 1744086060 && settingsKey.equals(SETTINGS_KEY_AOD_DISPLAY_ITEMS_STYLE)) {
            return getStylePossibleValue(context);
        }
        return null;
    }

    @Override // com.miui.aod.gemini.GeminiScreenConfig
    @NotNull
    public String makeUpPurpose(@NotNull String settingsKey) {
        Intrinsics.checkNotNullParameter(settingsKey, "settingsKey");
        int iHashCode = settingsKey.hashCode();
        if (iHashCode != -2094330118) {
            if (iHashCode != -497514072) {
                if (iHashCode == 1744086060 && settingsKey.equals(SETTINGS_KEY_AOD_DISPLAY_ITEMS_STYLE)) {
                    String string = getEnglishContext().getString(R.string.aod_show_style_title);
                    Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                    return string;
                }
            } else if (settingsKey.equals(SETTINGS_KEY_AOD_DISPLAY_ITEMS_SCHEDULED)) {
                String string2 = getEnglishContext().getString(R.string.scheduled_style_summary);
                Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                return string2;
            }
        } else if (settingsKey.equals("AOD_BATTERY_SAVER")) {
            String string3 = getEnglishContext().getString(R.string.disable_aod_in_power_save_mode);
            Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
            return string3;
        }
        String string4 = getEnglishContext().getString(R.string.aod_mode_title);
        Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
        return string4;
    }

    @Override // com.miui.aod.gemini.GeminiScreenConfig
    @NotNull
    public Pair<Boolean, SetDeviceStateItemResponse> set(@NotNull SetDeviceStateItemParams setDeviceStateItemParams) {
        Intrinsics.checkNotNullParameter(setDeviceStateItemParams, "setDeviceStateItemParams");
        String key = setDeviceStateItemParams.getKey();
        String value = setDeviceStateItemParams.getValue();
        int iHashCode = key.hashCode();
        if (iHashCode != -2094330118) {
            if (iHashCode != -497514072) {
                if (iHashCode == 1744086060 && key.equals(SETTINGS_KEY_AOD_DISPLAY_ITEMS_STYLE)) {
                    return new Pair<>(Boolean.TRUE, aodDisplayItemsSet(getContext(), value));
                }
            } else if (key.equals(SETTINGS_KEY_AOD_DISPLAY_ITEMS_SCHEDULED)) {
                return new Pair<>(Boolean.TRUE, aodScheduledSet(getContext(), value));
            }
        } else if (key.equals("AOD_BATTERY_SAVER")) {
            return new Pair<>(Boolean.TRUE, aodBatterySaverSet(getContext(), value));
        }
        return new Pair<>(Boolean.FALSE, null);
    }
}
