package io.reactivex.rxjava3.flowables;

import io.reactivex.rxjava3.core.Flowable;

/* JADX INFO: loaded from: classes2.dex */
public abstract class GroupedFlowable<K, T> extends Flowable<T> {
    public final Object key;

    public GroupedFlowable(Object obj) {
        this.key = obj;
    }
}
