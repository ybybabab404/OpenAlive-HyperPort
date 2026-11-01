package io.reactivex.rxjava3.internal.util;

import io.reactivex.rxjava3.core.Observer;

/* JADX INFO: loaded from: classes2.dex */
public interface ObservableQueueDrain<T, U> {
    void accept(Observer observer, Object obj);

    boolean cancelled();

    boolean done();

    Throwable error();

    int leave(int i);
}
