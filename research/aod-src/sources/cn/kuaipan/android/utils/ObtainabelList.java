package cn.kuaipan.android.utils;

import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
public class ObtainabelList<E> extends LinkedList<E> implements IObtainable {
    public static ObtainabelList mPool;
    public static int mPoolSize;
    public static Object mPoolSync = new Object();
    public ObtainabelList next;

    public static ObtainabelList obtain() {
        synchronized (mPoolSync) {
            try {
                ObtainabelList obtainabelList = mPool;
                if (obtainabelList == null) {
                    return new ObtainabelList();
                }
                mPool = obtainabelList.next;
                obtainabelList.next = null;
                mPoolSize--;
                obtainabelList.clear();
                return obtainabelList;
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
                if (i < 80) {
                    mPoolSize = i + 1;
                    this.next = mPool;
                    mPool = this;
                }
                for (E e : this) {
                    if (e instanceof IObtainable) {
                        ((IObtainable) e).recycle();
                    }
                }
                clear();
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
