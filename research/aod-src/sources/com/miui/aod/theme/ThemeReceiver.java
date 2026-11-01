package com.miui.aod.theme;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.miui.aod.AODApplication;
import com.miui.aod.AODStyleController;
import com.miui.aod.Utils;
import com.miui.aod.common.BaseBroadcastReceiver;
import com.miui.aod.db.HistoryEntity;
import com.miui.aod.db.HistoryProviderHelper;
import com.miui.aod.theme.beans.ThemeBean;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public class ThemeReceiver extends BaseBroadcastReceiver {
    @Override // com.miui.aod.common.BaseBroadcastReceiver, android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        boolean zExists;
        boolean z;
        ThemeBean themeBean;
        String cateName;
        String jsonParameter;
        onReceiveBefore();
        String stringExtra = intent.getStringExtra("theme_path");
        String stringExtra2 = intent.getStringExtra("identity");
        String stringExtra3 = intent.getStringExtra("meta");
        boolean booleanExtra = intent.getBooleanExtra("add_history", true);
        boolean z2 = !booleanExtra;
        boolean booleanExtra2 = intent.getBooleanExtra("is_debug", false);
        boolean booleanExtra3 = intent.getBooleanExtra("is_restore", false);
        boolean booleanExtra4 = intent.getBooleanExtra("hasLockScreen", false);
        if (TextUtils.isEmpty(stringExtra)) {
            stringExtra = "/data/system/theme/aod";
        }
        if (new File("/data/system/theme_magic/spaod").exists()) {
            zExists = true;
            stringExtra = "/data/system/theme_magic/spaod";
            z = true;
        } else {
            zExists = new File(stringExtra).exists();
            z = false;
        }
        if (!zExists || booleanExtra3) {
            String str = stringExtra;
            Log.w("AodThemeReceiver", str + " themeAodExist " + zExists + " isRestore " + booleanExtra3);
            if ("/data/system/theme/aod".equals(str) && booleanExtra3 && (themeBean = AODStyleController.getStyleInfo(context).getThemeBean()) != null && themeBean.mTemporaryTheme == 1) {
                List preHistories = HistoryProviderHelper.getInstance(AODApplication.sInstance).getPreHistories(1);
                if (preHistories.isEmpty()) {
                    cateName = null;
                    jsonParameter = null;
                } else {
                    HistoryEntity historyEntity = (HistoryEntity) preHistories.get(0);
                    cateName = historyEntity.getCateName();
                    jsonParameter = historyEntity.getJsonParameter();
                }
                Utils.setAodUsingCategory(context, cateName, jsonParameter);
            }
        } else {
            if (TextUtils.isEmpty(stringExtra2)) {
                Log.e("AodThemeReceiver", "onReceive: identity null, generate Random identity");
                stringExtra2 = String.valueOf(Math.abs(new Random().nextLong()));
            }
            Log.w("AodThemeReceiver", "onReceive: identity " + stringExtra2 + " path: " + stringExtra + " meta: " + stringExtra3 + " temporaryTheme " + z2 + " hasLockscreen " + booleanExtra4 + " isDebug " + booleanExtra2);
            ArrayList arrayList = new ArrayList();
            if (Utils.isUri(stringExtra3)) {
                ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra("preview");
                if (stringArrayListExtra == null || stringArrayListExtra.size() <= 0) {
                    Log.e("AodThemeReceiver", "onReceive: preview Array empty!");
                } else {
                    for (String str2 : stringArrayListExtra) {
                        if (str2.contains("preview_aod") || str2.contains("preview_spaod")) {
                            arrayList.add(str2);
                        }
                    }
                }
            } else {
                String stringExtra4 = intent.getStringExtra("preview");
                if (TextUtils.isEmpty(stringExtra4)) {
                    Log.e("AodThemeReceiver", "onReceive: previewDirPath null");
                } else {
                    File file = new File(stringExtra4);
                    if (file.exists()) {
                        File[] fileArrListFiles = file.listFiles();
                        if (fileArrListFiles == null || fileArrListFiles.length <= 0) {
                            Log.e("AodThemeReceiver", "onReceive: listFiles null or empty ");
                        } else {
                            for (File file2 : fileArrListFiles) {
                                if (file2.getName().contains("preview_aod") || file2.getName().contains("preview_spaod")) {
                                    arrayList.add(file2.getAbsolutePath());
                                }
                            }
                        }
                    } else {
                        Log.e("AodThemeReceiver", "onReceive: previewDir not exist");
                    }
                }
            }
            ThemeDataManager.getInstance(context).loadTheme(stringExtra, stringExtra2, stringExtra3, arrayList, !booleanExtra || booleanExtra2, z);
            if (!booleanExtra4) {
                Utils.setFsAodStyle(context, 0);
            }
        }
        onReceiveAfter();
    }
}
