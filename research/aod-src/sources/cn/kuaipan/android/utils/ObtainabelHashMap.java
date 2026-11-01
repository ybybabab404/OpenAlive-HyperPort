package cn.kuaipan.android.utils;

import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class ObtainabelHashMap<K, V> extends HashMap<K, V> implements IObtainable {
    public static ObtainabelHashMap mPool;
    public static int mPoolSize;
    public static Object mPoolSync = new Object();
    public ObtainabelHashMap next;

    public static ObtainabelHashMap obtain() {
        synchronized (mPoolSync) {
            try {
                ObtainabelHashMap obtainabelHashMap = mPool;
                if (obtainabelHashMap == null) {
                    return new ObtainabelHashMap();
                }
                mPool = obtainabelHashMap.next;
                obtainabelHashMap.next = null;
                mPoolSize--;
                obtainabelHashMap.clear();
                return obtainabelHashMap;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // cn.kuaipan.android.utils.IObtainable
    public void recycle() {
        synchronized (mPoolSync) {
            try {
                int i = mPoolSize;
                if (i < 500) {
                    mPoolSize = i + 1;
                    this.next = mPool;
                    mPool = this;
                }
                for (V v : values()) {
                    if (v instanceof IObtainable) {
                        ((IObtainable) v).recycle();
                    }
                }
                clear();
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
