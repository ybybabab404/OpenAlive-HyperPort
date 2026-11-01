package io.reactivex.rxjava3.internal.operators.mixed;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableSwitchMapSinglePublisher<T, R> extends Flowable<R> {
    public final boolean delayErrors;
    public final Function mapper;
    public final Publisher source;

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        this.source.subscribe(new FlowableSwitchMapSingle.SwitchMapSingleSubscriber(subscriber, this.mapper, this.delayErrors));
    }
}
