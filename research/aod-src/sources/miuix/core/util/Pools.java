package miuix.core.util;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import miuix.core.util.concurrent.ConcurrentRingQueue;

/* JADX INFO: loaded from: classes2.dex */
public final class Pools {
    public static final HashMap mInstanceHolderMap = new HashMap();
    public static final HashMap mSoftReferenceInstanceHolderMap = new HashMap();
    public static final Pool mStringBuilderPool = createSoftReferencePool(new Manager<StringBuilder>() { // from class: miuix.core.util.Pools.1
        @Override // miuix.core.util.Pools.Manager
        public StringBuilder createInstance() {
            return new StringBuilder();
        }

        @Override // miuix.core.util.Pools.Manager
        public void onRelease(StringBuilder sb) {
            sb.setLength(0);
        }
    }, 4);

    public static abstract class BasePool<T> implements Pool<T> {
        public final Object mFinalizeGuardian;
        public IInstanceHolder mInstanceHolder;
        public final Manager mManager;
        public final int mSize;

        public BasePool(Manager manager, int i) {
            Object obj = new Object() { // from class: miuix.core.util.Pools.BasePool.1
                public void finalize() throws Throwable {
                    try {
                        BasePool.this.close();
                    } finally {
                        super.finalize();
                    }
                }
            };
            this.mFinalizeGuardian = obj;
            if (manager == null || i < 1) {
                this.mSize = obj.hashCode();
                throw new IllegalArgumentException("manager cannot be null and size cannot less then 1");
            }
            this.mManager = manager;
            this.mSize = i;
            Object objCreateInstance = manager.createInstance();
            if (objCreateInstance == null) {
                throw new IllegalStateException("manager create instance cannot return null");
            }
            this.mInstanceHolder = createInstanceHolder(objCreateInstance.getClass(), i);
            doRelease(objCreateInstance);
        }

        @Override // miuix.core.util.Pools.Pool
        public Object acquire() {
            return doAcquire();
        }

        public void close() {
            IInstanceHolder iInstanceHolder = this.mInstanceHolder;
            if (iInstanceHolder != null) {
                destroyInstanceHolder(iInstanceHolder, this.mSize);
                this.mInstanceHolder = null;
            }
        }

        public abstract IInstanceHolder createInstanceHolder(Class cls, int i);

        public abstract void destroyInstanceHolder(IInstanceHolder iInstanceHolder, int i);

        public final Object doAcquire() {
            IInstanceHolder iInstanceHolder = this.mInstanceHolder;
            if (iInstanceHolder == null) {
                throw new IllegalStateException("Cannot acquire object after close()");
            }
            Object objCreateInstance = iInstanceHolder.get();
            if (objCreateInstance == null && (objCreateInstance = this.mManager.createInstance()) == null) {
                throw new IllegalStateException("manager create instance cannot return null");
            }
            this.mManager.onAcquire(objCreateInstance);
            return objCreateInstance;
        }

        public final void doRelease(Object obj) {
            if (this.mInstanceHolder == null) {
                throw new IllegalStateException("Cannot release object after close()");
            }
            if (obj == null) {
                return;
            }
            this.mManager.onRelease(obj);
            if (this.mInstanceHolder.put(obj)) {
                return;
            }
            this.mManager.onDestroy(obj);
        }

        @Override // miuix.core.util.Pools.Pool
        public void release(Object obj) {
            doRelease(obj);
        }
    }

    public interface IInstanceHolder<T> {
        Object get();

        boolean put(Object obj);
    }

    public static class InstanceHolder<T> implements IInstanceHolder<T> {
        public final Class mClazz;
        public final ConcurrentRingQueue mQueue;

        public InstanceHolder(Class cls, int i) {
            this.mClazz = cls;
            this.mQueue = new ConcurrentRingQueue(i, false, true);
        }

        @Override // miuix.core.util.Pools.IInstanceHolder
        public Object get() {
            return this.mQueue.get();
        }

        public Class getElementClass() {
            return this.mClazz;
        }

        @Override // miuix.core.util.Pools.IInstanceHolder
        public boolean put(Object obj) {
            return this.mQueue.put(obj);
        }

        public synchronized void resize(int i) {
            try {
                int capacity = i + this.mQueue.getCapacity();
                if (capacity <= 0) {
                    synchronized (Pools.mInstanceHolderMap) {
                        Pools.mInstanceHolderMap.remove(getElementClass());
                    }
                } else {
                    if (capacity > 0) {
                        this.mQueue.increaseCapacity(capacity);
                    } else {
                        this.mQueue.decreaseCapacity(-capacity);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static abstract class Manager<T> {
        public abstract Object createInstance();

        public void onAcquire(Object obj) {
        }

        public void onDestroy(Object obj) {
        }

        public void onRelease(Object obj) {
        }
    }

    public interface Pool<T> {
        Object acquire();

        void release(Object obj);
    }

    public static class SimplePool<T> extends BasePool<T> {
        @Override // miuix.core.util.Pools.BasePool, miuix.core.util.Pools.Pool
        public /* bridge */ /* synthetic */ Object acquire() {
            return super.acquire();
        }

        @Override // miuix.core.util.Pools.BasePool
        public /* bridge */ /* synthetic */ void close() {
            super.close();
        }

        @Override // miuix.core.util.Pools.BasePool
        public final IInstanceHolder createInstanceHolder(Class cls, int i) {
            return Pools.onPoolCreate(cls, i);
        }

        @Override // miuix.core.util.Pools.BasePool
        public final void destroyInstanceHolder(IInstanceHolder iInstanceHolder, int i) {
            Pools.onPoolClose((InstanceHolder) iInstanceHolder, i);
        }

        @Override // miuix.core.util.Pools.BasePool, miuix.core.util.Pools.Pool
        public /* bridge */ /* synthetic */ void release(Object obj) {
            super.release(obj);
        }
    }

    public static class SoftReferenceInstanceHolder<T> implements IInstanceHolder<T> {
        public final Class mClazz;
        public volatile SoftReference[] mElements;
        public volatile int mIndex = 0;
        public volatile int mSize;

        public SoftReferenceInstanceHolder(Class cls, int i) {
            this.mClazz = cls;
            this.mSize = i;
            this.mElements = new SoftReference[i];
        }

        @Override // miuix.core.util.Pools.IInstanceHolder
        public synchronized Object get() {
            int i = this.mIndex;
            SoftReference[] softReferenceArr = this.mElements;
            while (i != 0) {
                i--;
                SoftReference softReference = softReferenceArr[i];
                if (softReference != null) {
                    Object obj = softReference.get();
                    softReferenceArr[i] = null;
                    if (obj != null) {
                        this.mIndex = i;
                        return obj;
                    }
                }
            }
            return null;
        }

        public Class getElementClass() {
            return this.mClazz;
        }

        @Override // miuix.core.util.Pools.IInstanceHolder
        public synchronized boolean put(Object obj) {
            int i;
            try {
                int i2 = this.mIndex;
                SoftReference[] softReferenceArr = this.mElements;
                if (i2 < this.mSize) {
                    softReferenceArr[i2] = new SoftReference(obj);
                    this.mIndex = i2 + 1;
                    return true;
                }
                for (0; i < i2; i + 1) {
                    SoftReference softReference = softReferenceArr[i];
                    i = (softReference == null || softReference.get() == null) ? 0 : i + 1;
                    softReferenceArr[i] = new SoftReference(obj);
                    return true;
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }

        public synchronized void resize(int i) {
            try {
                int i2 = i + this.mSize;
                if (i2 <= 0) {
                    synchronized (Pools.mSoftReferenceInstanceHolderMap) {
                        Pools.mSoftReferenceInstanceHolderMap.remove(getElementClass());
                    }
                    return;
                }
                this.mSize = i2;
                SoftReference[] softReferenceArr = this.mElements;
                int i3 = this.mIndex;
                if (i2 > softReferenceArr.length) {
                    SoftReference[] softReferenceArr2 = new SoftReference[i2];
                    System.arraycopy(softReferenceArr, 0, softReferenceArr2, 0, i3);
                    this.mElements = softReferenceArr2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static class SoftReferencePool<T> extends BasePool<T> {
        public SoftReferencePool(Manager manager, int i) {
            super(manager, i);
        }

        @Override // miuix.core.util.Pools.BasePool, miuix.core.util.Pools.Pool
        public /* bridge */ /* synthetic */ Object acquire() {
            return super.acquire();
        }

        @Override // miuix.core.util.Pools.BasePool
        public /* bridge */ /* synthetic */ void close() {
            super.close();
        }

        @Override // miuix.core.util.Pools.BasePool
        public final IInstanceHolder createInstanceHolder(Class cls, int i) {
            return Pools.onSoftReferencePoolCreate(cls, i);
        }

        @Override // miuix.core.util.Pools.BasePool
        public final void destroyInstanceHolder(IInstanceHolder iInstanceHolder, int i) {
            Pools.onSoftReferencePoolClose((SoftReferenceInstanceHolder) iInstanceHolder, i);
        }

        @Override // miuix.core.util.Pools.BasePool, miuix.core.util.Pools.Pool
        public /* bridge */ /* synthetic */ void release(Object obj) {
            super.release(obj);
        }
    }

    public static SoftReferencePool createSoftReferencePool(Manager manager, int i) {
        return new SoftReferencePool(manager, i);
    }

    public static Pool getStringBuilderPool() {
        return mStringBuilderPool;
    }

    public static void onPoolClose(InstanceHolder instanceHolder, int i) {
        synchronized (mInstanceHolderMap) {
            instanceHolder.resize(-i);
        }
    }

    public static InstanceHolder onPoolCreate(Class cls, int i) {
        InstanceHolder instanceHolder;
        HashMap map = mInstanceHolderMap;
        synchronized (map) {
            try {
                instanceHolder = (InstanceHolder) map.get(cls);
                if (instanceHolder == null) {
                    instanceHolder = new InstanceHolder(cls, i);
                    map.put(cls, instanceHolder);
                } else {
                    instanceHolder.resize(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return instanceHolder;
    }

    public static void onSoftReferencePoolClose(SoftReferenceInstanceHolder softReferenceInstanceHolder, int i) {
        synchronized (mSoftReferenceInstanceHolderMap) {
            softReferenceInstanceHolder.resize(-i);
        }
    }

    public static SoftReferenceInstanceHolder onSoftReferencePoolCreate(Class cls, int i) {
        SoftReferenceInstanceHolder softReferenceInstanceHolder;
        HashMap map = mSoftReferenceInstanceHolderMap;
        synchronized (map) {
            try {
                softReferenceInstanceHolder = (SoftReferenceInstanceHolder) map.get(cls);
                if (softReferenceInstanceHolder == null) {
                    softReferenceInstanceHolder = new SoftReferenceInstanceHolder(cls, i);
                    map.put(cls, softReferenceInstanceHolder);
                } else {
                    softReferenceInstanceHolder.resize(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return softReferenceInstanceHolder;
    }
}
