package miuix.core.util;

import java.lang.ref.SoftReference;

/* JADX INFO: loaded from: classes2.dex */
public abstract class SoftReferenceSingleton<T> {
    public SoftReference mInstance = null;

    public Object createInstance(Object obj) {
        return null;
    }

    public final Object get(Object obj) {
        Object objCreateInstance;
        synchronized (this) {
            try {
                SoftReference softReference = this.mInstance;
                if (softReference == null || (objCreateInstance = softReference.get()) == null) {
                    objCreateInstance = createInstance(obj);
                    this.mInstance = new SoftReference(objCreateInstance);
                } else {
                    updateInstance(objCreateInstance, obj);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return objCreateInstance;
    }

    public void updateInstance(Object obj, Object obj2) {
    }
}
