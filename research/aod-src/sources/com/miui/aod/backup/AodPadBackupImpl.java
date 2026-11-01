package com.miui.aod.backup;

import android.content.Context;
import android.util.Log;
import com.miui.aod.Utils;
import com.miui.aod.category.CategoryConstants;
import com.xiaomi.settingsdk.backup.ICloudBackup;
import com.xiaomi.settingsdk.backup.data.DataPackage;
import com.xiaomi.settingsdk.backup.data.SettingItem;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONException;
import org.json.JSONObject;

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
/* JADX INFO: compiled from: AodPadBackupImpl.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class AodPadBackupImpl implements ICloudBackup {
    public static final Companion Companion = new Companion(null);
    public static final String[] SUPPORT_CATEGORIES = {CategoryConstants.CATEGORY_BIG_TIME};

    /* JADX INFO: compiled from: AodPadBackupImpl.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public int getCurrentVersion(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return 1;
    }

    public void onBackupSettings(Context context, DataPackage dataPackage) throws JSONException {
        Intrinsics.checkNotNullParameter(context, "context");
        if (dataPackage == null) {
            return;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("setting_aod_enable", Utils.isAodEnable(context));
        jSONObject.put("setting_show_mode", Utils.getShowStyle(context));
        jSONObject.put("setting_permit_disable_aod_in_power_save_mode", Utils.disableAodInPowerSaveModePermitted(context));
        String aodUsingCategoryName = Utils.getAodUsingCategoryName(context);
        jSONObject.put("setting_category_name", aodUsingCategoryName);
        jSONObject.put("setting_category_param", Utils.getAodUsingCategoryParam(context, aodUsingCategoryName));
        Log.i("AodPadBackupImpl", "onBackupSettings " + jSONObject);
        dataPackage.addKeyJson("json_key_aod_Settings_pad", jSONObject);
    }

    public final void onRestoreCategoryInfo(Context context, String str, String str2) {
        if (ArraysKt.contains(SUPPORT_CATEGORIES, str)) {
            Utils.setAodUsingCategory(context, str, str2);
            Utils.setAodUsingStyleState(context, Intrinsics.areEqual(CategoryConstants.CATEGORY_LINKAGE, str) ? 1 : 2);
            return;
        }
        Log.w("AodPadBackupImpl", "onRestoreCategoryInfo not support category: " + str + ", value: " + str2);
    }

    public void onRestoreSettings(Context context, DataPackage dataPackage, int i) {
        SettingItem settingItem;
        Intrinsics.checkNotNullParameter(context, "context");
        Object value = (dataPackage == null || (settingItem = dataPackage.get("json_key_aod_Settings_pad")) == null) ? null : settingItem.getValue();
        if (value == null) {
            return;
        }
        Log.i("AodPadBackupImpl", "onRestoreSettings value:" + value);
        if (value instanceof JSONObject) {
            JSONObject jSONObject = (JSONObject) value;
            if (jSONObject.has("setting_aod_enable")) {
                Utils.setAodEnable(context, jSONObject.optBoolean("setting_aod_enable", false));
                Utils.setAodModeUserSet(context, jSONObject.optBoolean("setting_aod_enable", false));
            }
            if (jSONObject.has("setting_show_mode")) {
                onRestoreShowStyle(context, jSONObject.optInt("setting_show_mode", Utils.getDefaultShowStyle(context)));
            }
            if (jSONObject.has("setting_permit_disable_aod_in_power_save_mode")) {
                Utils.setDisableAodInPowerSaveModePermitted(context, jSONObject.optBoolean("setting_permit_disable_aod_in_power_save_mode", true));
            }
            if (jSONObject.has("setting_category_name") && jSONObject.has("setting_category_param")) {
                String strOptString = jSONObject.optString("setting_category_name", CategoryConstants.getDefaultCategoryName(context));
                String strOptString2 = jSONObject.optString("setting_category_param", CategoryConstants.DEFAULT_PARAM);
                Intrinsics.checkNotNull(strOptString);
                Intrinsics.checkNotNull(strOptString2);
                onRestoreCategoryInfo(context, strOptString, strOptString2);
            }
        }
    }

    public final void onRestoreShowStyle(Context context, int i) {
        Utils.setShowStyle(context, Utils.resolveShowStyle(context, i));
    }
}
