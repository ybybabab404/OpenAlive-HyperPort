package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableMapPublisher<T, U> extends Flowable<U> {
    public final Function mapper;
    public final Publisher source;

    public FlowableMapPublisher(Publisher publisher, Function function) {
        this.source = publisher;
        this.mapper = function;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        this.source.subscribe(new FlowableMap.MapSubscriber(subscriber, this.mapper));
    }
}
