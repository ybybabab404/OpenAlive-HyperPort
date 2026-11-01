package com.miui.maml.util;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes2.dex */
public class SharedPreferenceHelper {
    private static final int MODE = 0;
    private static final String TAG = "SharedPreferenceHelper";
    private static HashMap<String, SharedPreferenceHelper> sInstanceMap;
    private final SharedPreferences mSharedPreferences;

    private SharedPreferenceHelper(Context context, String str) {
        this.mSharedPreferences = context.getSharedPreferences(str, 0);
    }

    public static synchronized SharedPreferenceHelper getInstance(Context context, String str) {
        SharedPreferenceHelper sharedPreferenceHelper;
        try {
            if (sInstanceMap == null) {
                sInstanceMap = new HashMap<>();
            }
            sharedPreferenceHelper = sInstanceMap.get(str);
            if (sharedPreferenceHelper == null) {
                sharedPreferenceHelper = new SharedPreferenceHelper(context, str);
                sInstanceMap.put(str, sharedPreferenceHelper);
            }
        } catch (Throwable th) {
            throw th;
        }
        return sharedPreferenceHelper;
    }

    public void clearSharePreference() {
        SharedPreferences.Editor editorEdit = this.mSharedPreferences.edit();
        editorEdit.clear();
        editorEdit.commit();
    }

    public boolean containsKey(String str) {
        return this.mSharedPreferences.contains(str);
    }

    public void deleteKey(String str) {
        SharedPreferences.Editor editorEdit = this.mSharedPreferences.edit();
        editorEdit.remove(str);
        editorEdit.commit();
    }

    public float load(String str, float f) {
        try {
            return this.mSharedPreferences.getFloat(str, f);
        } catch (Exception e) {
            MamlLog.e(TAG, "SharedPreferences load failed " + e.getMessage());
            return f;
        }
    }

    public int load(String str, int i) {
        try {
            return this.mSharedPreferences.getInt(str, i);
        } catch (Exception e) {
            MamlLog.e(TAG, "SharedPreferences load failed " + e.getMessage());
            return i;
        }
    }

    public long load(String str, long j) {
        try {
            return this.mSharedPreferences.getLong(str, j);
        } catch (Exception e) {
            MamlLog.e(TAG, "SharedPreferences load failed " + e.getMessage());
            return j;
        }
    }

    public String load(String str, String str2) {
        try {
            return this.mSharedPreferences.getString(str, str2);
        } catch (Exception e) {
            MamlLog.e(TAG, "SharedPreferences load failed " + e.getMessage());
            return str2;
        }
    }

    public boolean load(String str, boolean z) {
        try {
            return this.mSharedPreferences.getBoolean(str, z);
        } catch (Exception e) {
            MamlLog.e(TAG, "SharedPreferences load failed " + e.getMessage());
            return z;
        }
    }

    public Set<String> loadStringSet(String str) {
        return this.mSharedPreferences.getStringSet(str, new HashSet());
    }

    public boolean save(String str, float f) {
        SharedPreferences.Editor editorEdit = this.mSharedPreferences.edit();
        try {
            editorEdit.putFloat(str, f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return editorEdit.commit();
    }

    public boolean save(String str, int i) {
        SharedPreferences.Editor editorEdit = this.mSharedPreferences.edit();
        try {
            editorEdit.putInt(str, i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return editorEdit.commit();
    }

    public boolean save(String str, long j) {
        SharedPreferences.Editor editorEdit = this.mSharedPreferences.edit();
        try {
            editorEdit.putLong(str, j);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return editorEdit.commit();
    }

    public boolean save(String str, String str2) {
        SharedPreferences.Editor editorEdit = this.mSharedPreferences.edit();
        try {
            editorEdit.putString(str, str2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return editorEdit.commit();
    }

    public boolean save(String str, boolean z) {
        SharedPreferences.Editor editorEdit = this.mSharedPreferences.edit();
        editorEdit.putBoolean(str, z);
        return editorEdit.commit();
    }
}
