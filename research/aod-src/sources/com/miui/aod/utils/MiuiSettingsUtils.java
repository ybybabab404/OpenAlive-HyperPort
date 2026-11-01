package com.miui.aod.utils;

import android.content.ContentResolver;
import android.os.SystemProperties;
import android.provider.Settings;
import miui.util.FeatureParser;

/* JADX INFO: loaded from: classes.dex */
public final class MiuiSettingsUtils {

    public static class Secure {
        public static int getInt(ContentResolver contentResolver, String str, int i) {
            return Settings.Secure.getIntForUser(contentResolver, str, i, UserHandleUtils.USER_CURRENT);
        }

        public static String getString(ContentResolver contentResolver, String str) {
            return Settings.Secure.getStringForUser(contentResolver, str, UserHandleUtils.USER_CURRENT);
        }

        public static boolean putInt(ContentResolver contentResolver, String str, int i) {
            return Settings.Secure.putIntForUser(contentResolver, str, i, UserHandleUtils.USER_CURRENT);
        }

        public static boolean putString(ContentResolver contentResolver, String str, String str2) {
            return Settings.Secure.putStringForUser(contentResolver, str, str2, UserHandleUtils.USER_CURRENT);
        }
    }

    public static class System {
        public static int getInt(ContentResolver contentResolver, String str, int i) {
            return Settings.System.getIntForUser(contentResolver, str, i, UserHandleUtils.USER_CURRENT);
        }

        public static String getString(ContentResolver contentResolver, String str) {
            return Settings.System.getStringForUser(contentResolver, str, UserHandleUtils.USER_CURRENT);
        }

        public static boolean putInt(ContentResolver contentResolver, String str, int i) {
            return Settings.System.putIntForUser(contentResolver, str, i, UserHandleUtils.USER_CURRENT);
        }

        public static boolean putString(ContentResolver contentResolver, String str, String str2) {
            return Settings.System.putStringForUser(contentResolver, str, str2, UserHandleUtils.USER_CURRENT);
        }
    }

    private MiuiSettingsUtils() {
    }

    public static boolean getBooleanFromFeatureParse(String str, boolean z) {
        return FeatureParser.getBoolean(str, z);
    }

    public static boolean getBooleanFromSystemProperties(String str, boolean z) {
        return SystemProperties.getBoolean(str, z);
    }

    public static float getFloatFromFeatureParse(String str, float f) {
        return FeatureParser.getFloat(str, f).floatValue();
    }

    public static int getIntFromSecure(ContentResolver contentResolver, String str, int i, int i2) {
        return Settings.Secure.getIntForUser(contentResolver, str, i, i2);
    }

    public static int getIntFromSystem(ContentResolver contentResolver, String str, int i, int i2) {
        return Settings.System.getIntForUser(contentResolver, str, i, i2);
    }

    public static int getIntFromSystemProperties(String str, int i) {
        return SystemProperties.getInt(str, i);
    }

    public static long getLongFromSystem(ContentResolver contentResolver, String str, long j, int i) {
        return Settings.System.getLongForUser(contentResolver, str, j, i);
    }

    public static String getStringFromSystem(ContentResolver contentResolver, String str, int i) {
        return Settings.System.getStringForUser(contentResolver, str, i);
    }

    public static String getStringFromSystemProperties(String str, String str2) {
        return SystemProperties.get(str, str2);
    }

    public static boolean putIntToSecure(ContentResolver contentResolver, String str, int i, int i2) {
        return Settings.Secure.putIntForUser(contentResolver, str, i, i2);
    }

    public static boolean putIntToSystem(ContentResolver contentResolver, String str, int i, int i2) {
        return Settings.System.putIntForUser(contentResolver, str, i, i2);
    }

    public static boolean putLongToSystem(ContentResolver contentResolver, String str, long j, int i) {
        return Settings.System.putLongForUser(contentResolver, str, j, i);
    }
}
