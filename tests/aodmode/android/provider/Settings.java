package android.provider;

import android.content.ContentResolver;

import java.util.LinkedHashMap;
import java.util.Map;

/** In-memory secure settings + failure switches for every read/write path. */
public final class Settings {
    private Settings() {}

    public static final class Secure {
        public static final Map<String, String> values = new LinkedHashMap<>();
        public static boolean failPlainGet;
        public static boolean failPlainPut;
        public static boolean failAllGet;
        public static boolean failAllPut;
        public static int writes;

        private Secure() {}

        private static int parse(String value, int fallback) {
            if (value == null || value.isEmpty() || "null".equals(value)) return fallback;
            return Integer.parseInt(value);
        }

        public static int getInt(ContentResolver resolver, String key, int fallback) {
            if (failPlainGet || failAllGet) throw new SecurityException("plain get blocked");
            return parse(values.get(key), fallback);
        }

        public static boolean putInt(ContentResolver resolver, String key, int value) {
            if (failPlainPut || failAllPut) throw new SecurityException("plain put blocked");
            writes++;
            values.put(key, Integer.toString(value));
            return true;
        }

        public static boolean putString(ContentResolver resolver, String key, String value) {
            if (failPlainPut || failAllPut) throw new SecurityException("plain put blocked");
            writes++;
            if (value == null) values.remove(key); else values.put(key, value);
            return true;
        }

        public static int getIntForUser(ContentResolver resolver, String key, int fallback, int user) {
            if (failAllGet) throw new SecurityException("user get blocked");
            return parse(values.get(key), fallback);
        }

        public static boolean putIntForUser(ContentResolver resolver, String key, int value, int user) {
            if (failAllPut) throw new SecurityException("user put blocked");
            writes++;
            values.put(key, Integer.toString(value));
            return true;
        }

        public static boolean putStringForUser(ContentResolver resolver, String key, String value,
                                               int user) {
            if (failAllPut) throw new SecurityException("user put blocked");
            writes++;
            if (value == null) values.remove(key); else values.put(key, value);
            return true;
        }
    }
}
