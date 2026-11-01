package androidx.appsearch.app;

import android.util.Log;
import androidx.annotation.AnyThread;
import androidx.collection.ArrayMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@AnyThread
public abstract class AppSearchDocumentClassMap {
    public static final Object sLock = new Object();
    public static volatile Map sCachedAppSearchClasses = new ArrayMap();

    public static Class getAppSearchDocumentClass(String str) throws ClassNotFoundException {
        Class cls;
        Object obj = sLock;
        synchronized (obj) {
            cls = (Class) sCachedAppSearchClasses.get(str);
        }
        if (cls != null) {
            return cls;
        }
        Class<?> cls2 = Class.forName(str);
        synchronized (obj) {
            sCachedAppSearchClasses.put(str, cls2);
        }
        return cls2;
    }

    public static Class getAssignableClassBySchemaName(Map map, String str, Class cls) {
        List list = (List) map.get(str);
        if (list == null) {
            return null;
        }
        for (int i = 0; i < list.size(); i++) {
            String str2 = (String) list.get(i);
            try {
                Class<?> appSearchDocumentClass = getAppSearchDocumentClass(str2);
                if (cls.isAssignableFrom(appSearchDocumentClass)) {
                    return appSearchDocumentClass.asSubclass(cls);
                }
                continue;
            } catch (ClassNotFoundException unused) {
                Log.w("AppSearchDocumentClassM", "Failed to load document class \"" + str2 + "\". Perhaps the class was proguarded out?");
            }
        }
        return null;
    }
}
