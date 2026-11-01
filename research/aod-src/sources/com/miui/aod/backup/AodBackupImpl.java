package com.miui.aod.backup;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miui.aod.Utils;
import com.miui.aod.attention.AttentionManager;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.category.ImageSelectorCategoryInfo;
import com.miui.aod.db.HistoryEntity;
import com.miui.aod.db.HistoryProviderHelper;
import com.miui.aod.db.ThemeEntity;
import com.miui.aod.db.ThemeProviderHelper;
import com.miui.aod.superwallpaper.SuperWallpaperReceiver;
import com.miui.aod.theme.utils.ThemeUtils;
import com.miui.aod.util.CategoryPrefs;
import com.miui.aod.util.WallPaperUtils;
import com.miui.aod.util.ZipUtils;
import com.miui.aod.utils.MiuiSettingsUtils;
import com.miui.aod.widget.AODSettings;
import com.miui.aod.widget.notification.NotificationStyleManager;
import com.miui.keyguard.shortcuts.utils.DataUtils;
import com.tencent.mmkv.MMKV;
import com.xiaomi.onetrack.api.am;
import com.xiaomi.settingsdk.backup.ICloudBackup;
import com.xiaomi.settingsdk.backup.data.DataPackage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import miui.app.backup.FullBackupAgent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AodBackupImpl implements ICloudBackup {
    public final FullBackupAgent mFullBackupAgent;

    public AodBackupImpl(FullBackupAgent fullBackupAgent) {
        this.mFullBackupAgent = fullBackupAgent;
    }

    public final void backUpHistoryData(Context context, DataPackage dataPackage) {
        boolean z;
        try {
            String imageCacheDir = ImageSelectorCategoryInfo.getImageCacheDir(context);
            if (new File(imageCacheDir).exists()) {
                String str = imageCacheDir + ".aodBackup";
                if (ZipUtils.zipFileFlat(new File(imageCacheDir), new File(str))) {
                    this.mFullBackupAgent.addAttachedFile(str);
                    z = false;
                } else {
                    z = true;
                }
            } else {
                z = false;
            }
            List allHistories = HistoryProviderHelper.getInstance(context).getAllHistories();
            if (allHistories == null || allHistories.isEmpty()) {
                return;
            }
            int size = allHistories.size();
            Gson gson = new Gson();
            ArrayList arrayList = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                HistoryEntity historyEntity = (HistoryEntity) allHistories.get(i);
                if (!z || !CategoryConstants.CATEGORY_IMAGE_BG_SELECTOR.equals(historyEntity.getCateName())) {
                    arrayList.add(gson.toJson(allHistories.get(i)));
                }
            }
            dataPackage.addKeyValue("history_json", gson.toJson(arrayList));
        } catch (Exception e) {
            Log.e("AodBackupImpl", "backUpHistoryData: ", e);
        }
    }

    public final void backUpThemeData(Context context, DataPackage dataPackage) {
        try {
            List allThemes = ThemeProviderHelper.getInstance(context).getAllThemes();
            if (allThemes == null || allThemes.isEmpty()) {
                return;
            }
            int size = allThemes.size();
            Gson gson = new Gson();
            ArrayList arrayList = new ArrayList(size);
            String rootDir = ThemeUtils.getRootDir(context);
            for (int i = 0; i < size; i++) {
                ThemeEntity themeEntity = (ThemeEntity) allThemes.get(i);
                String str = rootDir + File.separator + themeEntity.getThemeDirName();
                String str2 = str + ".aodBackup";
                if (ZipUtils.zipFileFlat(new File(str), new File(str2))) {
                    this.mFullBackupAgent.addAttachedFile(str2);
                    arrayList.add(gson.toJson(themeEntity));
                }
            }
            dataPackage.addKeyValue("theme_entity_json", gson.toJson(arrayList));
        } catch (Exception e) {
            Log.e("AodBackupImpl", "backUpThemeData: ", e);
        }
    }

    public final void checkLinkageStyle(Context context, String str, String str2) {
        if ("aod_category_name".equals(str)) {
            Utils.setAodUsingStyleState(context, CategoryConstants.CATEGORY_LINKAGE.equals(str2) ? 1 : 2);
        }
    }

    public int getCurrentVersion(Context context) {
        return 1;
    }

    public void onBackupSettings(Context context, DataPackage dataPackage) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("setting_aod_enable", Utils.isAodEnable(context));
            jSONObject.put("setting_show_mode", Utils.getShowStyle(context));
            jSONObject.put("setting_support_aon", AttentionManager.supportAttention(context));
            ContentResolver contentResolver = context.getContentResolver();
            jSONObject.put("setting_start_time", Settings.Secure.getInt(contentResolver, "aod_start", 420));
            int i = Settings.Secure.getInt(contentResolver, "aod_end", 1380);
            boolean zIsUsingSuperWallpaperStyle = Utils.isUsingSuperWallpaperStyle(context);
            jSONObject.put("setting_end_time", i);
            jSONObject.put("setting_permit_disable_aod_in_power_save_mode", Utils.disableAodInPowerSaveModePermitted(context));
            jSONObject.put("aod_using_super_wallpaper", zIsUsingSuperWallpaperStyle);
            jSONObject.put("wakeup_for_keyguard_notification", Utils.getKeyguardNotificationStatus(context));
            jSONObject.put("setting_full_aod_enable", Utils.isFsAodSameToLock(context));
            jSONObject.put("setting_full_aod_notification_enable", Utils.isFullAodNotificationEnable(context));
            jSONObject.put("setting_category_name", Utils.getAodUsingCategoryName(context));
            jSONObject.put("setting_category_param", Utils.getAodUsingCategoryParam(context, Utils.getAodUsingCategoryName(context)));
            jSONObject.put(NotificationStyleManager.KEY_ANIMATION_STYLE, MiuiSettingsUtils.Secure.getString(context.getContentResolver(), NotificationStyleManager.KEY_ANIMATION_STYLE));
            dataPackage.addKeyJson("json_key_aod_Settings", jSONObject);
            Log.i("AodBackupImpl", "onBackupSettings: " + jSONObject);
        } catch (JSONException e) {
            Log.e("AodBackupImpl", "backupConfig()", e);
        }
        backUpHistoryData(context, dataPackage);
        backUpThemeData(context, dataPackage);
        DataUtils.backupShortcutSettings(context, dataPackage);
    }

    public void onRestoreSettings(Context context, DataPackage dataPackage, int i) {
        if (dataPackage == null) {
            return;
        }
        boolean z = false;
        if (dataPackage.get("json_key_aod_Settings") != null) {
            JSONObject jSONObject = (JSONObject) dataPackage.get("json_key_aod_Settings").getValue();
            if (jSONObject != null) {
                Log.i("AodBackupImpl", "onRestoreSettings: " + jSONObject.toString());
                Utils.setAodEnable(context, jSONObject.optBoolean("setting_aod_enable", false));
                Utils.setAodModeUserSet(context, jSONObject.optBoolean("setting_aod_enable", false));
                ContentResolver contentResolver = context.getContentResolver();
                int iOptInt = jSONObject.optInt("setting_show_mode", Utils.getDefaultShowStyle(context));
                if (iOptInt != 0 || AODSettings.isKeycodeGotoEnable()) {
                    if (iOptInt == 2 || iOptInt == 3) {
                        boolean zOptBoolean = jSONObject.optBoolean("setting_support_aon", false);
                        boolean zSupportAttention = AttentionManager.supportAttention(context);
                        if (!zOptBoolean && iOptInt == 2 && zSupportAttention) {
                            iOptInt = 3;
                        }
                        if (zOptBoolean && iOptInt == 3 && !zSupportAttention) {
                            iOptInt = 2;
                        }
                    }
                    if (AODSettings.onlySupportKeycodeGoto() && !AttentionManager.supportAttention(context)) {
                        iOptInt = 0;
                    }
                    if (iOptInt != 0 && iOptInt != 3 && AttentionManager.supportAttention(context) && AODSettings.onlySupportKeycodeGoto()) {
                        iOptInt = 0;
                    }
                    Settings.Secure.putInt(contentResolver, "aod_show_style", iOptInt);
                    Settings.Secure.putInt(contentResolver, "aod_start", jSONObject.optInt("setting_start_time", 420));
                    Settings.Secure.putInt(contentResolver, "aod_end", jSONObject.optInt("setting_end_time", 1380));
                }
                boolean zOptBoolean2 = jSONObject.optBoolean("setting_full_aod_enable", false);
                if (AODSettings.isO2SDevice() && zOptBoolean2 && (iOptInt == 2 || iOptInt == 1)) {
                    Settings.Secure.putInt(contentResolver, "aod_show_style", 0);
                }
                Utils.updateTouchMode(context, false, true);
                if (Utils.isSupportFsAod()) {
                    Utils.setFsAodStyle(context, jSONObject.optBoolean("setting_full_aod_enable", false) ? 1 : 0);
                    Utils.setFullAodNotificationEnable(context, jSONObject.optBoolean("setting_full_aod_notification_enable", false));
                }
                Settings.Secure.putInt(contentResolver, "permit_disable_aod_in_power_save_mode", jSONObject.optBoolean("setting_permit_disable_aod_in_power_save_mode", true) ? 1 : 0);
                WallPaperUtils.updateWallpaperSupportsAmbientModeState();
                if (WallPaperUtils.isWallpaperSupportsAmbientMode()) {
                    Utils.setUsingSuperWallpaperStyleState(context, jSONObject.optBoolean("aod_using_super_wallpaper", false));
                }
                Utils.putKeyguardNotificationStatus(context, jSONObject.optInt("wakeup_for_keyguard_notification", Utils.getKeyguardNotificationStatus(context)));
                if (jSONObject.has("setting_category_name") && jSONObject.has("setting_category_param")) {
                    Utils.setAodUsingCategory(context, jSONObject.optString("setting_category_name"), jSONObject.optString("setting_category_param"));
                    Utils.setAodUsingStyleState(context, CategoryConstants.CATEGORY_LINKAGE.equals(jSONObject.optString("setting_category_name")) ? 1 : 2);
                }
                if (jSONObject.has(NotificationStyleManager.KEY_ANIMATION_STYLE)) {
                    MiuiSettingsUtils.Secure.putString(context.getContentResolver(), NotificationStyleManager.KEY_ANIMATION_STYLE, jSONObject.optString(NotificationStyleManager.KEY_ANIMATION_STYLE));
                    z = true;
                }
            } else {
                Log.e("AodBackupImpl", "onRestoreSettings: job null ");
            }
        } else {
            Log.e("AodBackupImpl", "onRestoreSettings: dataPackage " + dataPackage);
        }
        restoreCategoryPrefsString(context, dataPackage, z);
        restoreHistoryData(context, dataPackage);
        restoreThemeData(context, dataPackage);
        if (!Utils.isUsingSuperWallpaperStyle(context)) {
            SuperWallpaperReceiver.fallbackDefaultAod(context);
        }
        DataUtils.restoreShortcutData(context, dataPackage);
    }

    public final void restoreCategoryPrefsString(Context context, DataPackage dataPackage, boolean z) {
        JSONObject jSONObject;
        JSONArray jSONArrayOptJSONArray;
        try {
            if (dataPackage.get("cate_prefs_json") == null || (jSONObject = (JSONObject) dataPackage.get("cate_prefs_json").getValue()) == null || (jSONArrayOptJSONArray = jSONObject.optJSONArray("data")) == null) {
                return;
            }
            int length = jSONArrayOptJSONArray.length();
            MMKV mmkv = (MMKV) CategoryPrefs.get(context);
            String str = null;
            for (int i = 0; i < length; i++) {
                JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i);
                if (jSONObjectOptJSONObject != null) {
                    String strOptString = jSONObjectOptJSONObject.optString("key");
                    String strOptString2 = jSONObjectOptJSONObject.optString(am.t);
                    if (!TextUtils.isEmpty(strOptString) && !TextUtils.isEmpty(strOptString2)) {
                        if (NotificationStyleManager.KEY_ANIMATION_STYLE.equals(strOptString) && !z) {
                            NotificationStyleManager.setSelectedStyleName(context, strOptString2);
                        }
                        if ("aod_category_name".equals(strOptString)) {
                            str = strOptString;
                        }
                        checkLinkageStyle(context, strOptString, strOptString2);
                        mmkv.encode(strOptString, strOptString2);
                        Log.i("AodBackupImpl", "restoreCategoryPrefsString: " + jSONObjectOptJSONObject.toString());
                    }
                }
            }
            if (TextUtils.isEmpty(str)) {
                return;
            }
            Utils.setAodUsingCategory(context, str, CategoryPrefs.getString(context, str, CategoryConstants.DEFAULT_PARAM));
        } catch (Exception e) {
            Log.e("AodBackupImpl", "restoreCategoryPrefsString: ", e);
        }
    }

    public final void restoreHistoryData(Context context, DataPackage dataPackage) {
        try {
            Log.i("AodBackupImpl", "restoreHistoryData:");
            if (dataPackage == null || dataPackage.get("history_json") == null) {
                return;
            }
            Gson gson = new Gson();
            List<String> list = (List) gson.fromJson((String) dataPackage.get("history_json").getValue(), new TypeToken<List<String>>() { // from class: com.miui.aod.backup.AodBackupImpl.2
            }.getType());
            HistoryProviderHelper.getInstance(context).clearHistory();
            if (list == null || list.isEmpty()) {
                return;
            }
            Log.i("AodBackupImpl", "restoreHistoryData: count " + list.size());
            for (String str : list) {
                HistoryEntity historyEntity = (HistoryEntity) gson.fromJson(str, HistoryEntity.class);
                Log.i("AodBackupImpl", "restoreHistoryData: " + str);
                HistoryProviderHelper.getInstance(context).insert(historyEntity);
            }
        } catch (Exception e) {
            Log.e("AodBackupImpl", "restoreHistoryData: ", e);
        }
    }

    public final void restoreThemeData(Context context, DataPackage dataPackage) {
        if (dataPackage != null) {
            try {
                if (dataPackage.get("theme_entity_json") != null) {
                    Gson gson = new Gson();
                    List list = (List) gson.fromJson((String) dataPackage.get("theme_entity_json").getValue(), new TypeToken<List<String>>() { // from class: com.miui.aod.backup.AodBackupImpl.1
                    }.getType());
                    ThemeProviderHelper.getInstance(context).clearTheme();
                    ThemeUtils.clearThemeResource(context, Collections.EMPTY_SET);
                    if (list == null || list.isEmpty()) {
                        return;
                    }
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        ThemeEntity themeEntity = (ThemeEntity) gson.fromJson((String) it.next(), ThemeEntity.class);
                        Log.i("AodBackupImpl", "restoreThemeData: " + themeEntity.getName());
                        ThemeProviderHelper.getInstance(context).insert(themeEntity);
                    }
                }
            } catch (Exception e) {
                Log.e("AodBackupImpl", "restoreThemeData: ", e);
            }
        }
    }
}
