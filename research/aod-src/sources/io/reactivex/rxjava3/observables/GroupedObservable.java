package io.reactivex.rxjava3.observables;

import io.reactivex.rxjava3.core.Observable;

/* JADX INFO: loaded from: classes2.dex */
public abstract class GroupedObservable<K, T> extends Observable<T> {
    public final Object key;

    public GroupedObservable(Object obj) {
        this.key = obj;
    }
}
