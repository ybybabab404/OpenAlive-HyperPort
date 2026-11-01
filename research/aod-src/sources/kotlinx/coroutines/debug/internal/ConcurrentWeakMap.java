package kotlinx.coroutines.debug.internal;

import com.xiaomi.onetrack.api.am;
import java.lang.ref.ReferenceQueue;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.AbstractMutableMap;
import kotlin.collections.AbstractMutableSet;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.markers.KMutableIterator;
import kotlin.jvm.internal.markers.KMutableMap;
import kotlin.ranges.RangesKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: ConcurrentWeakMap.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class ConcurrentWeakMap<K, V> extends AbstractMutableMap<K, V> {
    public static final /* synthetic */ AtomicIntegerFieldUpdater _size$volatile$FU = AtomicIntegerFieldUpdater.newUpdater(ConcurrentWeakMap.class, "_size$volatile");
    public static final /* synthetic */ AtomicReferenceFieldUpdater core$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(ConcurrentWeakMap.class, Object.class, "core$volatile");
    private volatile /* synthetic */ int _size$volatile;
    private volatile /* synthetic */ Object core$volatile;
    public final ReferenceQueue weakRefQueue;

    /* JADX INFO: compiled from: ConcurrentWeakMap.kt */
    @Metadata
    public final class Core {
        public static final /* synthetic */ AtomicIntegerFieldUpdater load$volatile$FU = AtomicIntegerFieldUpdater.newUpdater(Core.class, "load$volatile");
        public final int allocated;
        public final /* synthetic */ AtomicReferenceArray keys;
        private volatile /* synthetic */ int load$volatile;
        public final int shift;
        public final int threshold;
        public final /* synthetic */ AtomicReferenceArray values;

        /* JADX INFO: compiled from: ConcurrentWeakMap.kt */
        @Metadata
        @SourceDebugExtension
        public final class KeyValueIterator<E> implements Iterator<E>, KMutableIterator {
            public final Function2 factory;
            public int index = -1;
            public Object key;
            public Object value;

            public KeyValueIterator(Function2 function2) {
                this.factory = function2;
                findNext();
            }

            public final void findNext() {
                T t;
                while (true) {
                    int i = this.index + 1;
                    this.index = i;
                    if (i >= Core.this.allocated) {
                        return;
                    }
                    HashedWeakRef hashedWeakRef = (HashedWeakRef) Core.this.getKeys().get(this.index);
                    if (hashedWeakRef != null && (t = hashedWeakRef.get()) != 0) {
                        this.key = t;
                        Object obj = Core.this.getValues().get(this.index);
                        if (obj instanceof Marked) {
                            obj = ((Marked) obj).ref;
                        }
                        if (obj != null) {
                            this.value = obj;
                            return;
                        }
                    }
                }
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.index < Core.this.allocated;
            }

            @Override // java.util.Iterator
            public Object next() {
                if (this.index >= Core.this.allocated) {
                    throw new NoSuchElementException();
                }
                Function2 function2 = this.factory;
                Object obj = this.key;
                if (obj == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("key");
                    obj = Unit.INSTANCE;
                }
                Object obj2 = this.value;
                if (obj2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(am.t);
                    obj2 = Unit.INSTANCE;
                }
                Object objInvoke = function2.invoke(obj, obj2);
                findNext();
                return objInvoke;
            }

            @Override // java.util.Iterator
            public Void remove() {
                ConcurrentWeakMapKt.noImpl();
                throw new KotlinNothingValueException();
            }
        }

        public Core(int i) {
            this.allocated = i;
            this.shift = Integer.numberOfLeadingZeros(i) + 1;
            this.threshold = (i * 2) / 3;
            this.keys = new AtomicReferenceArray(i);
            this.values = new AtomicReferenceArray(i);
        }

        public static /* synthetic */ Object putImpl$default(Core core, Object obj, Object obj2, HashedWeakRef hashedWeakRef, int i, Object obj3) {
            if ((i & 4) != 0) {
                hashedWeakRef = null;
            }
            return core.putImpl(obj, obj2, hashedWeakRef);
        }

        public final Object getImpl(Object obj) {
            int iIndex = index(obj.hashCode());
            while (true) {
                HashedWeakRef hashedWeakRef = (HashedWeakRef) getKeys().get(iIndex);
                if (hashedWeakRef == null) {
                    return null;
                }
                T t = hashedWeakRef.get();
                if (Intrinsics.areEqual(obj, t)) {
                    Object obj2 = getValues().get(iIndex);
                    return obj2 instanceof Marked ? ((Marked) obj2).ref : obj2;
                }
                if (t == 0) {
                    removeCleanedAt(iIndex);
                }
                if (iIndex == 0) {
                    iIndex = this.allocated;
                }
                iIndex--;
            }
        }

        public final /* synthetic */ AtomicReferenceArray getKeys() {
            return this.keys;
        }

        public final /* synthetic */ AtomicReferenceArray getValues() {
            return this.values;
        }

        public final int index(int i) {
            return (i * (-1640531527)) >>> this.shift;
        }

        public final Iterator keyValueIterator(Function2 function2) {
            return new KeyValueIterator(function2);
        }

        public final Object putImpl(Object obj, Object obj2, HashedWeakRef hashedWeakRef) {
            int i;
            Object obj3;
            int iIndex = index(obj.hashCode());
            boolean z = false;
            while (true) {
                HashedWeakRef hashedWeakRef2 = (HashedWeakRef) getKeys().get(iIndex);
                if (hashedWeakRef2 != null) {
                    T t = hashedWeakRef2.get();
                    if (Intrinsics.areEqual(obj, t)) {
                        if (!z) {
                            break;
                        }
                        load$volatile$FU.decrementAndGet(this);
                        break;
                    }
                    if (t == 0) {
                        removeCleanedAt(iIndex);
                    }
                    if (iIndex == 0) {
                        iIndex = this.allocated;
                    }
                    iIndex--;
                } else if (obj2 != null) {
                    if (!z) {
                        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = load$volatile$FU;
                        do {
                            i = atomicIntegerFieldUpdater.get(this);
                            if (i >= this.threshold) {
                                return ConcurrentWeakMapKt.REHASH;
                            }
                        } while (!atomicIntegerFieldUpdater.compareAndSet(this, i, i + 1));
                        z = true;
                    }
                    if (hashedWeakRef == null) {
                        hashedWeakRef = new HashedWeakRef(obj, ConcurrentWeakMap.this.weakRefQueue);
                    }
                    if (getKeys().compareAndSet(iIndex, null, hashedWeakRef)) {
                        break;
                    }
                } else {
                    return null;
                }
            }
            do {
                obj3 = getValues().get(iIndex);
                if (obj3 instanceof Marked) {
                    return ConcurrentWeakMapKt.REHASH;
                }
            } while (!getValues().compareAndSet(iIndex, obj3, obj2));
            return obj3;
        }

        public final Core rehash() {
            Object obj;
            while (true) {
                Core core = new Core(Integer.highestOneBit(RangesKt.coerceAtLeast(ConcurrentWeakMap.this.size(), 4)) * 4);
                int i = this.allocated;
                for (int i2 = 0; i2 < i; i2++) {
                    HashedWeakRef hashedWeakRef = (HashedWeakRef) getKeys().get(i2);
                    Object obj2 = hashedWeakRef != null ? hashedWeakRef.get() : null;
                    if (hashedWeakRef != null && obj2 == null) {
                        removeCleanedAt(i2);
                    }
                    do {
                        obj = getValues().get(i2);
                        if (obj instanceof Marked) {
                            obj = ((Marked) obj).ref;
                            break;
                        }
                    } while (!getValues().compareAndSet(i2, obj, ConcurrentWeakMapKt.mark(obj)));
                    if (obj2 == null || obj == null || core.putImpl(obj2, obj, hashedWeakRef) != ConcurrentWeakMapKt.REHASH) {
                    }
                }
                return core;
            }
        }

        public final void removeCleanedAt(int i) {
            Object obj;
            do {
                obj = getValues().get(i);
                if (obj == null || (obj instanceof Marked)) {
                    return;
                }
            } while (!getValues().compareAndSet(i, obj, null));
            ConcurrentWeakMap.this.decrementSize();
        }
    }

    /* JADX INFO: compiled from: ConcurrentWeakMap.kt */
    @Metadata
    public static final class Entry<K, V> implements Map.Entry<K, V>, KMutableMap.Entry {
        public final Object key;
        public final Object value;

        public Entry(Object obj, Object obj2) {
            this.key = obj;
            this.value = obj2;
        }

        @Override // java.util.Map.Entry
        public Object getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public Object getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public Object setValue(Object obj) {
            ConcurrentWeakMapKt.noImpl();
            throw new KotlinNothingValueException();
        }
    }

    /* JADX INFO: compiled from: ConcurrentWeakMap.kt */
    @Metadata
    public final class KeyValueSet<E> extends AbstractMutableSet<E> {
        public final Function2 factory;

        public KeyValueSet(Function2 function2) {
            this.factory = function2;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean add(Object obj) {
            ConcurrentWeakMapKt.noImpl();
            throw new KotlinNothingValueException();
        }

        @Override // kotlin.collections.AbstractMutableSet
        public int getSize() {
            return ConcurrentWeakMap.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator iterator() {
            return ((Core) ConcurrentWeakMap.getCore$volatile$FU().get(ConcurrentWeakMap.this)).keyValueIterator(this.factory);
        }
    }

    public ConcurrentWeakMap(boolean z) {
        this.core$volatile = new Core(16);
        this.weakRefQueue = z ? new ReferenceQueue() : null;
    }

    public /* synthetic */ ConcurrentWeakMap(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z);
    }

    public static final Map.Entry _get_entries_$lambda$1(Object obj, Object obj2) {
        return new Entry(obj, obj2);
    }

    public static final Object _get_keys_$lambda$0(Object obj, Object obj2) {
        return obj;
    }

    public static final /* synthetic */ AtomicReferenceFieldUpdater getCore$volatile$FU() {
        return core$volatile$FU;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        Iterator it = keySet().iterator();
        while (it.hasNext()) {
            remove(it.next());
        }
    }

    public final void decrementSize() {
        _size$volatile$FU.decrementAndGet(this);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object get(Object obj) {
        if (obj == null) {
            return null;
        }
        return ((Core) core$volatile$FU.get(this)).getImpl(obj);
    }

    @Override // kotlin.collections.AbstractMutableMap
    public Set getEntries() {
        return new KeyValueSet(new Function2() { // from class: kotlinx.coroutines.debug.internal.ConcurrentWeakMap$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ConcurrentWeakMap._get_entries_$lambda$1(obj, obj2);
            }
        });
    }

    @Override // kotlin.collections.AbstractMutableMap
    public Set getKeys() {
        return new KeyValueSet(new Function2() { // from class: kotlinx.coroutines.debug.internal.ConcurrentWeakMap$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ConcurrentWeakMap._get_keys_$lambda$0(obj, obj2);
            }
        });
    }

    @Override // kotlin.collections.AbstractMutableMap
    public int getSize() {
        return _size$volatile$FU.get(this);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object put(Object obj, Object obj2) {
        Object objPutImpl$default = Core.putImpl$default((Core) core$volatile$FU.get(this), obj, obj2, null, 4, null);
        if (objPutImpl$default == ConcurrentWeakMapKt.REHASH) {
            objPutImpl$default = putSynchronized(obj, obj2);
        }
        if (objPutImpl$default == null) {
            _size$volatile$FU.incrementAndGet(this);
        }
        return objPutImpl$default;
    }

    public final synchronized Object putSynchronized(Object obj, Object obj2) {
        Object objPutImpl$default;
        Core coreRehash = (Core) core$volatile$FU.get(this);
        while (true) {
            Object obj3 = obj;
            Object obj4 = obj2;
            objPutImpl$default = Core.putImpl$default(coreRehash, obj3, obj4, null, 4, null);
            if (objPutImpl$default == ConcurrentWeakMapKt.REHASH) {
                coreRehash = coreRehash.rehash();
                core$volatile$FU.set(this, coreRehash);
                obj = obj3;
                obj2 = obj4;
            }
        }
        return objPutImpl$default;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object remove(Object obj) {
        if (obj == null) {
            return null;
        }
        Object objPutImpl$default = Core.putImpl$default((Core) core$volatile$FU.get(this), obj, null, null, 4, null);
        if (objPutImpl$default == ConcurrentWeakMapKt.REHASH) {
            objPutImpl$default = putSynchronized(obj, null);
        }
        if (objPutImpl$default != null) {
            _size$volatile$FU.decrementAndGet(this);
        }
        return objPutImpl$default;
    }
}
