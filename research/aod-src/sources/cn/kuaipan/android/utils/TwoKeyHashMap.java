package cn.kuaipan.android.utils;

import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class TwoKeyHashMap<E, K, V> extends AbstractMap<String, V> {
    public Entry[] arr;
    public int arrSize;
    public Set entrySet;
    public float loadFactor;
    public int modCount;
    public int size;
    public int threshold;
    public Collection values;

    public static class Entry<E, K, V> implements Map.Entry<String, V> {
        public int hash;
        public Object key1;
        public Object key2;
        public Entry next;
        public Object value;

        public Entry(int i, Object obj, Object obj2, Object obj3, Entry entry) {
            this.hash = i;
            this.key1 = obj;
            this.key2 = obj2;
            this.value = obj3;
            this.next = entry;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            Object key1 = entry.getKey1();
            Object key2 = entry.getKey2();
            Object value = entry.getValue();
            Object obj2 = this.key1;
            return (obj2 != null || key1 == null) && (this.key2 != null || key2 == null) && ((this.value != null || value == null) && obj2.equals(entry.getKey1()) && this.key2.equals(entry.getKey2()) && this.value.equals(value));
        }

        @Override // java.util.Map.Entry
        public String getKey() {
            return this.key1.toString() + this.key2.toString();
        }

        public Object getKey1() {
            return this.key1;
        }

        public Object getKey2() {
            return this.key2;
        }

        @Override // java.util.Map.Entry
        public Object getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            Object obj = this.key1;
            int iHashCode = obj == null ? 0 : obj.hashCode();
            Object obj2 = this.key2;
            int iHashCode2 = iHashCode + (obj2 == null ? 0 : obj2.hashCode());
            Object obj3 = this.value;
            return iHashCode2 ^ (obj3 != null ? obj3.hashCode() : 0);
        }

        @Override // java.util.Map.Entry
        public Object setValue(Object obj) {
            Object obj2 = this.value;
            this.value = obj;
            return obj2;
        }
    }

    public class EntryIteratorImpl implements Iterator<Map.Entry<String, V>> {
        public Entry curr_entry;
        public boolean found;
        public Entry returned_entry;
        public int startModCount;
        public int curr = -1;
        public int returned_index = -1;

        public EntryIteratorImpl() {
            this.startModCount = TwoKeyHashMap.this.modCount;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.found) {
                return true;
            }
            Entry entry = this.curr_entry;
            if (entry != null) {
                this.curr_entry = entry.next;
            }
            if (this.curr_entry == null) {
                this.curr++;
                while (this.curr < TwoKeyHashMap.this.arr.length) {
                    Entry[] entryArr = TwoKeyHashMap.this.arr;
                    int i = this.curr;
                    if (entryArr[i] != null) {
                        break;
                    }
                    this.curr = i + 1;
                }
                if (this.curr < TwoKeyHashMap.this.arr.length) {
                    this.curr_entry = TwoKeyHashMap.this.arr[this.curr];
                }
            }
            boolean z = this.curr_entry != null;
            this.found = z;
            return z;
        }

        @Override // java.util.Iterator
        public Map.Entry next() {
            if (TwoKeyHashMap.this.modCount != this.startModCount) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.found = false;
            this.returned_index = this.curr;
            Entry entry = this.curr_entry;
            this.returned_entry = entry;
            return entry;
        }

        @Override // java.util.Iterator
        public void remove() {
            Entry entry;
            if (this.returned_index == -1) {
                throw new IllegalStateException();
            }
            if (TwoKeyHashMap.this.modCount != this.startModCount) {
                throw new ConcurrentModificationException();
            }
            Entry entry2 = TwoKeyHashMap.this.arr[this.returned_index];
            Entry entry3 = null;
            while (true) {
                entry = this.returned_entry;
                if (entry2 == entry) {
                    break;
                }
                entry3 = entry2;
                entry2 = entry2.next;
            }
            if (entry3 != null) {
                entry3.next = entry.next;
            } else {
                TwoKeyHashMap.this.arr[this.returned_index] = this.returned_entry.next;
            }
            TwoKeyHashMap.access$010(TwoKeyHashMap.this);
            TwoKeyHashMap.access$308(TwoKeyHashMap.this);
            this.startModCount++;
            this.returned_index = -1;
        }
    }

    public class EntrySetImpl extends AbstractSet<Map.Entry<String, V>> {
        public EntrySetImpl() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            TwoKeyHashMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            Entry entryFindEntry = TwoKeyHashMap.this.findEntry(entry.getKey1(), entry.getKey2());
            if (entryFindEntry == null) {
                return false;
            }
            Object value = entry.getValue();
            Object value2 = entryFindEntry.getValue();
            if (value == null) {
                return value2 == null;
            }
            return value.equals(value2);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return TwoKeyHashMap.this.size == 0;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator iterator() {
            return TwoKeyHashMap.this.createEntrySetIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            return TwoKeyHashMap.this.removeEntry(entry.getKey1(), entry.getKey2()) != null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return TwoKeyHashMap.this.size;
        }
    }

    public class ValueIteratorImpl implements Iterator<V> {
        public EntryIteratorImpl itr;

        public ValueIteratorImpl() {
            this.itr = new EntryIteratorImpl();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.itr.hasNext();
        }

        @Override // java.util.Iterator
        public Object next() {
            return this.itr.next().getValue();
        }

        @Override // java.util.Iterator
        public void remove() {
            this.itr.remove();
        }
    }

    public class ValuesCollectionImpl extends AbstractCollection<V> {
        public ValuesCollectionImpl() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            TwoKeyHashMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            return TwoKeyHashMap.this.containsValue(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return TwoKeyHashMap.this.size == 0;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator iterator() {
            return TwoKeyHashMap.this.createValueCollectionIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return TwoKeyHashMap.this.size;
        }
    }

    public TwoKeyHashMap() {
        this(16, 0.75f);
    }

    public TwoKeyHashMap(int i, float f) {
        this.threshold = 0;
        if (i < 0) {
            throw new IllegalArgumentException("initialCapacity should be >= 0");
        }
        if (f <= 0.0f) {
            throw new IllegalArgumentException("initialLoadFactor should be > 0");
        }
        this.loadFactor = f;
        i = i == Integer.MAX_VALUE ? i - 1 : i;
        i = i <= 0 ? 1 : i;
        this.arrSize = i;
        this.threshold = (int) (i * f);
        this.arr = new Entry[i + 1];
    }

    public static /* synthetic */ int access$010(TwoKeyHashMap twoKeyHashMap) {
        int i = twoKeyHashMap.size;
        twoKeyHashMap.size = i - 1;
        return i;
    }

    public static /* synthetic */ int access$308(TwoKeyHashMap twoKeyHashMap) {
        int i = twoKeyHashMap.modCount;
        twoKeyHashMap.modCount = i + 1;
        return i;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        this.modCount++;
        this.size = 0;
        Entry[] entryArr = this.arr;
        Arrays.fill(entryArr, 0, entryArr.length, (Object) null);
    }

    public Entry createEntry(int i, Object obj, Object obj2, Object obj3, Entry entry) {
        return new Entry(i, obj, obj2, obj3, entry);
    }

    public Iterator createEntrySetIterator() {
        return new EntryIteratorImpl();
    }

    public Iterator createValueCollectionIterator() {
        return new ValueIteratorImpl();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set entrySet() {
        if (this.entrySet == null) {
            this.entrySet = new EntrySetImpl();
        }
        return this.entrySet;
    }

    public final Entry findEntry(Object obj, Object obj2) {
        if (obj == null && obj2 == null) {
            return this.arr[this.arrSize];
        }
        int iHashCode = obj.hashCode() + obj2.hashCode();
        for (Entry entry = this.arr[(Integer.MAX_VALUE & iHashCode) % this.arrSize]; entry != null; entry = entry.next) {
            if (iHashCode == entry.hash && obj.equals(entry.getKey1()) && obj2.equals(entry.getKey2())) {
                return entry;
            }
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    public Object put(Object obj, Object obj2, Object obj3) {
        if (obj == null && obj2 == null) {
            int i = this.arrSize;
            Entry[] entryArr = this.arr;
            Entry entry = entryArr[i];
            if (entry != null) {
                Object obj4 = entry.value;
                entry.value = obj3;
                return obj4;
            }
            entryArr[i] = createEntry(0, null, null, obj3, null);
            this.size++;
            this.modCount++;
            return null;
        }
        int iHashCode = obj.hashCode() + obj2.hashCode();
        int i2 = (Integer.MAX_VALUE & iHashCode) % this.arrSize;
        for (Entry entry2 = this.arr[i2]; entry2 != null; entry2 = entry2.next) {
            if (iHashCode == entry2.hash && obj.equals(entry2.getKey1()) && obj2.equals(entry2.getKey2())) {
                Object obj5 = entry2.value;
                entry2.value = obj3;
                return obj5;
            }
        }
        Entry[] entryArr2 = this.arr;
        entryArr2[i2] = createEntry(iHashCode, obj, obj2, obj3, entryArr2[i2]);
        int i3 = this.size + 1;
        this.size = i3;
        this.modCount++;
        if (i3 > this.threshold) {
            rehash();
        }
        return null;
    }

    public void rehash() {
        Entry[] entryArr;
        int i = ((this.arrSize + 1) * 2) + 1;
        if (i < 0) {
            i = 2147483646;
        }
        Entry[] entryArr2 = new Entry[i + 1];
        int i2 = 0;
        while (true) {
            entryArr = this.arr;
            if (i2 >= entryArr.length - 1) {
                break;
            }
            Entry entry = entryArr[i2];
            while (entry != null) {
                Entry entry2 = entry.next;
                int i3 = (entry.hash & Integer.MAX_VALUE) % i;
                entry.next = entryArr2[i3];
                entryArr2[i3] = entry;
                entry = entry2;
            }
            i2++;
        }
        entryArr2[i] = entryArr[this.arrSize];
        this.arrSize = i;
        if (i == Integer.MAX_VALUE) {
            this.loadFactor *= 10.0f;
        }
        this.threshold = (int) (i * this.loadFactor);
        this.arr = entryArr2;
    }

    public final Entry removeEntry(Object obj, Object obj2) {
        if (obj == null && obj2 == null) {
            int i = this.arrSize;
            Entry[] entryArr = this.arr;
            Entry entry = entryArr[i];
            if (entry == null) {
                return null;
            }
            entryArr[i] = null;
            this.size--;
            this.modCount++;
            return entry;
        }
        int iHashCode = obj.hashCode() + obj2.hashCode();
        int i2 = (Integer.MAX_VALUE & iHashCode) % this.arrSize;
        Entry entry2 = this.arr[i2];
        Entry entry3 = entry2;
        while (entry2 != null) {
            if (iHashCode == entry2.hash && obj.equals(entry2.getKey1()) && obj2.equals(entry2.getKey2())) {
                if (entry3 == entry2) {
                    this.arr[i2] = entry2.next;
                } else {
                    entry3.next = entry2.next;
                }
                this.size--;
                this.modCount++;
                return entry2;
            }
            entry3 = entry2;
            entry2 = entry2.next;
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.size;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Collection values() {
        if (this.values == null) {
            this.values = new ValuesCollectionImpl();
        }
        return this.values;
    }
}
