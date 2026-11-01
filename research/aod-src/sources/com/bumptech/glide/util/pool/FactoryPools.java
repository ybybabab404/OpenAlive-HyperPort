package com.bumptech.glide.util.pool;

import android.util.Log;
import androidx.core.util.Pools;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class FactoryPools {
    public static final Resetter EMPTY_RESETTER = new Resetter<Object>() { // from class: com.bumptech.glide.util.pool.FactoryPools.1
        @Override // com.bumptech.glide.util.pool.FactoryPools.Resetter
        public void reset(Object obj) {
        }
    };

    public interface Factory<T> {
        Object create();
    }

    public static final class FactoryPool<T> implements Pools.Pool<T> {
        public final Factory factory;
        public final Pools.Pool pool;
        public final Resetter resetter;

        public FactoryPool(Pools.Pool pool, Factory factory, Resetter resetter) {
            this.pool = pool;
            this.factory = factory;
            this.resetter = resetter;
        }

        @Override // androidx.core.util.Pools.Pool
        public Object acquire() {
            Object objAcquire = this.pool.acquire();
            if (objAcquire == null) {
                objAcquire = this.factory.create();
                if (Log.isLoggable("FactoryPools", 2)) {
                    Log.v("FactoryPools", "Created new " + objAcquire.getClass());
                }
            }
            if (objAcquire instanceof Poolable) {
                ((Poolable) objAcquire).getVerifier().setRecycled(false);
            }
            return objAcquire;
        }

        @Override // androidx.core.util.Pools.Pool
        public boolean release(Object obj) {
            if (obj instanceof Poolable) {
                ((Poolable) obj).getVerifier().setRecycled(true);
            }
            this.resetter.reset(obj);
            return this.pool.release(obj);
        }
    }

    public interface Poolable {
        StateVerifier getVerifier();
    }

    public interface Resetter<T> {
        void reset(Object obj);
    }

    public static Pools.Pool build(Pools.Pool pool, Factory factory) {
        return build(pool, factory, emptyResetter());
    }

    public static Pools.Pool build(Pools.Pool pool, Factory factory, Resetter resetter) {
        return new FactoryPool(pool, factory, resetter);
    }

    public static Resetter emptyResetter() {
        return EMPTY_RESETTER;
    }

    public static Pools.Pool threadSafe(int i, Factory factory) {
        return build(new Pools.SynchronizedPool(i), factory);
    }

    public static Pools.Pool threadSafeList() {
        return threadSafeList(20);
    }

    public static Pools.Pool threadSafeList(int i) {
        return build(new Pools.SynchronizedPool(i), new Factory<List<Object>>() { // from class: com.bumptech.glide.util.pool.FactoryPools.2
            @Override // com.bumptech.glide.util.pool.FactoryPools.Factory
            public List create() {
                return new ArrayList();
            }
        }, new Resetter<List<Object>>() { // from class: com.bumptech.glide.util.pool.FactoryPools.3
            @Override // com.bumptech.glide.util.pool.FactoryPools.Resetter
            public void reset(List list) {
                list.clear();
            }
        });
    }
}
