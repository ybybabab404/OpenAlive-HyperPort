package com.bumptech.glide.load.engine.bitmap_recycle;

import android.util.Log;
import com.bumptech.glide.util.Preconditions;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes.dex */
public final class LruArrayPool implements ArrayPool {
    public int currentSize;
    public final int maxSize;
    public final GroupedLinkedMap groupedMap = new GroupedLinkedMap();
    public final KeyPool keyPool = new KeyPool();
    public final Map sortedSizes = new HashMap();
    public final Map adapters = new HashMap();

    public static final class Key implements Poolable {
        public Class arrayClass;
        public final KeyPool pool;
        public int size;

        public Key(KeyPool keyPool) {
            this.pool = keyPool;
        }

        public boolean equals(Object obj) {
            if (obj instanceof Key) {
                Key key = (Key) obj;
                if (this.size == key.size && this.arrayClass == key.arrayClass) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            int i = this.size * 31;
            Class cls = this.arrayClass;
            return i + (cls != null ? cls.hashCode() : 0);
        }

        public void init(int i, Class cls) {
            this.size = i;
            this.arrayClass = cls;
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.Poolable
        public void offer() {
            this.pool.offer(this);
        }

        public String toString() {
            return "Key{size=" + this.size + "array=" + this.arrayClass + '}';
        }
    }

    public static final class KeyPool extends BaseKeyPool<Key> {
        @Override // com.bumptech.glide.load.engine.bitmap_recycle.BaseKeyPool
        public Key create() {
            return new Key(this);
        }

        public Key get(int i, Class cls) {
            Key key = (Key) get();
            key.init(i, cls);
            return key;
        }
    }

    public LruArrayPool(int i) {
        this.maxSize = i;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool
    public synchronized void clearMemory() {
        evictToSize(0);
    }

    public final void decrementArrayOfSize(int i, Class cls) {
        NavigableMap sizesForAdapter = getSizesForAdapter(cls);
        Integer num = (Integer) sizesForAdapter.get(Integer.valueOf(i));
        if (num != null) {
            if (num.intValue() == 1) {
                sizesForAdapter.remove(Integer.valueOf(i));
                return;
            } else {
                sizesForAdapter.put(Integer.valueOf(i), Integer.valueOf(num.intValue() - 1));
                return;
            }
        }
        throw new NullPointerException("Tried to decrement empty size, size: " + i + ", this: " + this);
    }

    public final void evict() {
        evictToSize(this.maxSize);
    }

    public final void evictToSize(int i) {
        while (this.currentSize > i) {
            Object objRemoveLast = this.groupedMap.removeLast();
            Preconditions.checkNotNull(objRemoveLast);
            ArrayAdapterInterface adapterFromObject = getAdapterFromObject(objRemoveLast);
            this.currentSize -= adapterFromObject.getArrayLength(objRemoveLast) * adapterFromObject.getElementSizeInBytes();
            decrementArrayOfSize(adapterFromObject.getArrayLength(objRemoveLast), objRemoveLast.getClass());
            if (Log.isLoggable(adapterFromObject.getTag(), 2)) {
                Log.v(adapterFromObject.getTag(), "evicted: " + adapterFromObject.getArrayLength(objRemoveLast));
            }
        }
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool
    public synchronized Object get(int i, Class cls) {
        Integer num;
        try {
            num = (Integer) getSizesForAdapter(cls).ceilingKey(Integer.valueOf(i));
        } catch (Throwable th) {
            throw th;
        }
        return getForKey(mayFillRequest(i, num) ? this.keyPool.get(num.intValue(), cls) : this.keyPool.get(i, cls), cls);
    }

    public final ArrayAdapterInterface getAdapterFromObject(Object obj) {
        return getAdapterFromType(obj.getClass());
    }

    public final ArrayAdapterInterface getAdapterFromType(Class cls) {
        ArrayAdapterInterface byteArrayAdapter;
        ArrayAdapterInterface arrayAdapterInterface = (ArrayAdapterInterface) this.adapters.get(cls);
        if (arrayAdapterInterface != null) {
            return arrayAdapterInterface;
        }
        if (cls.equals(int[].class)) {
            byteArrayAdapter = new IntegerArrayAdapter();
        } else {
            if (!cls.equals(byte[].class)) {
                throw new IllegalArgumentException("No array pool found for: " + cls.getSimpleName());
            }
            byteArrayAdapter = new ByteArrayAdapter();
        }
        this.adapters.put(cls, byteArrayAdapter);
        return byteArrayAdapter;
    }

    public final Object getArrayForKey(Key key) {
        return this.groupedMap.get(key);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool
    public synchronized Object getExact(int i, Class cls) {
        return getForKey(this.keyPool.get(i, cls), cls);
    }

    public final Object getForKey(Key key, Class cls) {
        ArrayAdapterInterface adapterFromType = getAdapterFromType(cls);
        Object arrayForKey = getArrayForKey(key);
        if (arrayForKey != null) {
            this.currentSize -= adapterFromType.getArrayLength(arrayForKey) * adapterFromType.getElementSizeInBytes();
            decrementArrayOfSize(adapterFromType.getArrayLength(arrayForKey), cls);
        }
        if (arrayForKey != null) {
            return arrayForKey;
        }
        if (Log.isLoggable(adapterFromType.getTag(), 2)) {
            Log.v(adapterFromType.getTag(), "Allocated " + key.size + " bytes");
        }
        return adapterFromType.newArray(key.size);
    }

    public final NavigableMap getSizesForAdapter(Class cls) {
        NavigableMap navigableMap = (NavigableMap) this.sortedSizes.get(cls);
        if (navigableMap != null) {
            return navigableMap;
        }
        TreeMap treeMap = new TreeMap();
        this.sortedSizes.put(cls, treeMap);
        return treeMap;
    }

    public final boolean isNoMoreThanHalfFull() {
        int i = this.currentSize;
        return i == 0 || this.maxSize / i >= 2;
    }

    public final boolean isSmallEnoughForReuse(int i) {
        return i <= this.maxSize / 2;
    }

    public final boolean mayFillRequest(int i, Integer num) {
        if (num != null) {
            return isNoMoreThanHalfFull() || num.intValue() <= i * 8;
        }
        return false;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool
    public synchronized void put(Object obj) {
        Class<?> cls = obj.getClass();
        ArrayAdapterInterface adapterFromType = getAdapterFromType(cls);
        int arrayLength = adapterFromType.getArrayLength(obj);
        int elementSizeInBytes = adapterFromType.getElementSizeInBytes() * arrayLength;
        if (isSmallEnoughForReuse(elementSizeInBytes)) {
            Key key = this.keyPool.get(arrayLength, cls);
            this.groupedMap.put(key, obj);
            NavigableMap sizesForAdapter = getSizesForAdapter(cls);
            Integer num = (Integer) sizesForAdapter.get(Integer.valueOf(key.size));
            Integer numValueOf = Integer.valueOf(key.size);
            int iIntValue = 1;
            if (num != null) {
                iIntValue = 1 + num.intValue();
            }
            sizesForAdapter.put(numValueOf, Integer.valueOf(iIntValue));
            this.currentSize += elementSizeInBytes;
            evict();
        }
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool
    public synchronized void trimMemory(int i) {
        try {
            if (i >= 40) {
                clearMemory();
            } else if (i >= 20 || i == 15) {
                evictToSize(this.maxSize / 2);
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
