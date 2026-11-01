package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public final class SingleFlattenStreamAsFlowable<T, R> extends Flowable<R> {
    public final Function mapper;
    public final Single source;

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        this.source.subscribe(new MaybeFlattenStreamAsFlowable.FlattenStreamMultiObserver(subscriber, this.mapper));
    }
}
