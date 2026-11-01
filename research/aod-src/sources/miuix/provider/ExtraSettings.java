package miuix.provider;

import android.content.ContentResolver;
import android.provider.Settings;

/* JADX INFO: loaded from: classes2.dex */
public class ExtraSettings {

    public static class Secure {
        public static float getFloat(ContentResolver contentResolver, String str, float f) {
            return Settings.Secure.getFloat(contentResolver, str, f);
        }

        public static int getInt(ContentResolver contentResolver, String str) {
            return Settings.Secure.getInt(contentResolver, str);
        }

        public static int getInt(ContentResolver contentResolver, String str, int i) {
            return Settings.Secure.getInt(contentResolver, str, i);
        }

        public static String getString(ContentResolver contentResolver, String str, String str2) {
            String string = Settings.Secure.getString(contentResolver, str);
            return string == null ? str2 : string;
        }

        public static boolean putFloat(ContentResolver contentResolver, String str, float f) {
            return Settings.Secure.putFloat(contentResolver, str, f);
        }

        public static boolean putInt(ContentResolver contentResolver, String str, int i) {
            return Settings.Secure.putInt(contentResolver, str, i);
        }

        public static boolean putString(ContentResolver contentResolver, String str, String str2) {
            return Settings.Secure.putString(contentResolver, str, str2);
        }
    }

    public static class System {
    }
}
