package com.xiaomi.micloudsdk.sync.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/* JADX INFO: loaded from: classes2.dex */
public class SyncTimeUtils {
    public static synchronized long getGuardDelayUntilInSec(Context context, String str) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        long jCurrentTimeMillis = System.currentTimeMillis();
        String str2 = String.format("SyncWindowStart_%s", str);
        String str3 = String.format("SyncTimes_%s", str);
        long j = defaultSharedPreferences.getLong(str2, 0L);
        int i = defaultSharedPreferences.getInt(str3, 1);
        if (Math.abs(jCurrentTimeMillis - j) < 600000) {
            if (i > 30) {
                return ((j + 600000) - jCurrentTimeMillis) / 1000;
            }
            defaultSharedPreferences.edit().putInt(str3, i + 1).commit();
            return 0L;
        }
        SharedPreferences.Editor editorEdit = defaultSharedPreferences.edit();
        editorEdit.putLong(str2, jCurrentTimeMillis);
        editorEdit.putInt(str3, 1);
        editorEdit.commit();
        return 0L;
    }
}
