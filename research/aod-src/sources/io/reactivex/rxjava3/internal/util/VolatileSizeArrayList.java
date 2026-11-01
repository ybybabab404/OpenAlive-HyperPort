package io.reactivex.rxjava3.internal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes2.dex */
public final class VolatileSizeArrayList<T> extends AtomicInteger implements List<T>, RandomAccess {
    public final ArrayList list;

    @Override // java.util.List
    public void add(int i, Object obj) {
        this.list.add(i, obj);
        lazySet(this.list.size());
    }

    @Override // java.util.List, java.util.Collection
    public boolean add(Object obj) {
        boolean zAdd = this.list.add(obj);
        lazySet(this.list.size());
        return zAdd;
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection collection) {
        boolean zAddAll = this.list.addAll(i, collection);
        lazySet(this.list.size());
        return zAddAll;
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(Collection collection) {
        boolean zAddAll = this.list.addAll(collection);
        lazySet(this.list.size());
        return zAddAll;
    }

    @Override // java.util.List, java.util.Collection
    public void clear() {
        this.list.clear();
        lazySet(0);
    }

    @Override // java.util.List, java.util.Collection
    public boolean contains(Object obj) {
        return this.list.contains(obj);
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(Collection collection) {
        return this.list.containsAll(collection);
    }

    @Override // java.util.List, java.util.Collection
    public boolean equals(Object obj) {
        return obj instanceof VolatileSizeArrayList ? this.list.equals(((VolatileSizeArrayList) obj).list) : this.list.equals(obj);
    }

    @Override // java.util.List
    public Object get(int i) {
        return this.list.get(i);
    }

    @Override // java.util.List, java.util.Collection
    public int hashCode() {
        return this.list.hashCode();
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        return this.list.indexOf(obj);
    }

    @Override // java.util.List, java.util.Collection
    public boolean isEmpty() {
        return get() == 0;
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Iterator iterator() {
        return this.list.iterator();
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        return this.list.lastIndexOf(obj);
    }

    @Override // java.util.List
    public ListIterator listIterator() {
        return this.list.listIterator();
    }

    @Override // java.util.List
    public ListIterator listIterator(int i) {
        return this.list.listIterator(i);
    }

    @Override // java.util.List
    public Object remove(int i) {
        Object objRemove = this.list.remove(i);
        lazySet(this.list.size());
        return objRemove;
    }

    @Override // java.util.List, java.util.Collection
    public boolean remove(Object obj) {
        boolean zRemove = this.list.remove(obj);
        lazySet(this.list.size());
        return zRemove;
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(Collection collection) {
        boolean zRemoveAll = this.list.removeAll(collection);
        lazySet(this.list.size());
        return zRemoveAll;
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(Collection collection) {
        boolean zRetainAll = this.list.retainAll(collection);
        lazySet(this.list.size());
        return zRetainAll;
    }

    @Override // java.util.List
    public Object set(int i, Object obj) {
        return this.list.set(i, obj);
    }

    @Override // java.util.List, java.util.Collection
    public int size() {
        return get();
    }

    @Override // java.util.List
    public List subList(int i, int i2) {
        return this.list.subList(i, i2);
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        return this.list.toArray();
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray(Object[] objArr) {
        return this.list.toArray(objArr);
    }

    @Override // java.util.concurrent.atomic.AtomicInteger
    public String toString() {
        return this.list.toString();
    }
}
