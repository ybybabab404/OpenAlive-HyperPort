package android.content;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/** Android Context subset used by AodWallpaperProvider. */
public class Context {
    private File filesDir;
    private File cacheDir;
    private final Map<String, SharedPreferences> prefs = new HashMap<>();

    public Context(File filesDir, File cacheDir) {
        this.filesDir = filesDir;
        this.cacheDir = cacheDir;
    }

    public File getFilesDir() { return filesDir; }
    public File getCacheDir() { return cacheDir; }

    public SharedPreferences getSharedPreferences(String name, int mode) {
        SharedPreferences value = prefs.get(name);
        if (value == null) {
            value = new SharedPreferences();
            prefs.put(name, value);
        }
        return value;
    }
}
