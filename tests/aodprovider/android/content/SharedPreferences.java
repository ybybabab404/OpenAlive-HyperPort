package android.content;

import java.util.HashMap;
import java.util.Map;

/** In-memory SharedPreferences subset. */
public class SharedPreferences {
    private final Map<String, String> values = new HashMap<>();

    public SharedPreferences put(String key, String value) {
        if (value == null) values.remove(key); else values.put(key, value);
        return this;
    }

    public String getString(String key, String fallback) {
        String value = values.get(key);
        return value == null ? fallback : value;
    }
}
