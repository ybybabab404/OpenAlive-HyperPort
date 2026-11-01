package com.miui.lockscreeninfo.compat;

import android.content.ContentResolver;
import android.provider.Settings;
import com.miui.lockscreeninfo.ReflectUtils;

/* JADX INFO: loaded from: classes2.dex */
public class SettingsCompat {

    public static class Global {
    }

    public static class Secure {
        public static int getIntForUser(ContentResolver contentResolver, String str, int i, int i2) {
            Class cls = Integer.TYPE;
            return ((Integer) ReflectUtils.invokeObject(Settings.Secure.class, null, "getIntForUser", cls, 0, new Class[]{ContentResolver.class, String.class, cls, cls}, contentResolver, str, Integer.valueOf(i), Integer.valueOf(i2))).intValue();
        }

        public static String getStringForUser(ContentResolver contentResolver, String str, int i) {
            return (String) ReflectUtils.invokeObject(Settings.Secure.class, null, "getStringForUser", String.class, "", new Class[]{ContentResolver.class, String.class, Integer.TYPE}, contentResolver, str, Integer.valueOf(i));
        }
    }

    public static class System {
    }
}
