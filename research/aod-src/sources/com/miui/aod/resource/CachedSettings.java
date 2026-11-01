package com.miui.aod.resource;

import android.content.Context;
import android.os.Looper;
import androidx.collection.ArrayMap;
import com.miui.aod.Utils;
import com.miui.aod.utils.MiuiSettingsUtils;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public interface CachedSettings {
    public static final Map cacheBoolean = new ArrayMap();

    public interface Callable<V> extends java.util.concurrent.Callable<V> {
        @Override // java.util.concurrent.Callable
        Object call();
    }

    public interface Secure {
        public static final Map cache = new ArrayMap();

        static int getInt(Context context, String str, int i) {
            synchronized (CachedSettings.class) {
                try {
                    if (Looper.myLooper() != Looper.getMainLooper() || Utils.isAodProcess()) {
                        return MiuiSettingsUtils.Secure.getInt(context.getContentResolver(), str, i);
                    }
                    Map map = cache;
                    Integer numValueOf = (Integer) map.get(str);
                    if (numValueOf == null) {
                        numValueOf = Integer.valueOf(MiuiSettingsUtils.Secure.getInt(context.getContentResolver(), str, i));
                        map.put(str, numValueOf);
                    }
                    return numValueOf.intValue();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    static void cleanCache() {
        Secure.cache.clear();
    }

    static boolean getBoolean(String str, Callable callable) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return ((Boolean) callable.call()).booleanValue();
        }
        Map map = cacheBoolean;
        Boolean bool = (Boolean) map.get(str);
        if (bool == null) {
            bool = (Boolean) callable.call();
            map.put(str, bool);
        }
        return bool.booleanValue();
    }
}
