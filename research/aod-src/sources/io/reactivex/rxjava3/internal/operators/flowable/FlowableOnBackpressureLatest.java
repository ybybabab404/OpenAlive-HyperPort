package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Consumer;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableOnBackpressureLatest<T> extends AbstractFlowableWithUpstream<T, T> {
    public final Consumer onDropped;

    public static final class BackpressureLatestSubscriber<T> extends AbstractBackpressureThrottlingSubscriber<T, T> {
        public final Consumer onDropped;

        public BackpressureLatestSubscriber(Subscriber subscriber, Consumer consumer) {
            super(subscriber);
            this.onDropped = consumer;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            Object andSet = this.current.getAndSet(obj);
            Consumer consumer = this.onDropped;
            if (consumer != null && andSet != null) {
                try {
                    consumer.accept(andSet);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.upstream.cancel();
                    this.downstream.onError(th);
                }
            }
            drain();
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        this.source.subscribe((FlowableSubscriber) new BackpressureLatestSubscriber(subscriber, this.onDropped));
    }
}
