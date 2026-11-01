package io.reactivex.rxjava3.internal.util;

import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public interface QueueDrain<T, U> {
    boolean accept(Subscriber subscriber, Object obj);

    boolean cancelled();

    boolean done();

    Throwable error();

    int leave(int i);

    long produced(long j);

    long requested();
}
