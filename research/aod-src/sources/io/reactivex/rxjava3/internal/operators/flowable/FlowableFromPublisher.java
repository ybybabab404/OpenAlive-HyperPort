package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableFromPublisher<T> extends Flowable<T> {
    public final Publisher publisher;

    public FlowableFromPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        this.publisher.subscribe(subscriber);
    }
}
