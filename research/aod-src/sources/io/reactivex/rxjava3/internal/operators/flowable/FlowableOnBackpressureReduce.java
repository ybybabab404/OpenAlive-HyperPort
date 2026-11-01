package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableOnBackpressureReduce<T> extends AbstractFlowableWithUpstream<T, T> {
    public final BiFunction reducer;

    public static final class BackpressureReduceSubscriber<T> extends AbstractBackpressureThrottlingSubscriber<T, T> {
        public final BiFunction reducer;

        public BackpressureReduceSubscriber(Subscriber subscriber, BiFunction biFunction) {
            super(subscriber);
            this.reducer = biFunction;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            Object andSet = this.current.get();
            if (andSet != null) {
                andSet = this.current.getAndSet(null);
            }
            if (andSet == null) {
                this.current.lazySet(obj);
            } else {
                try {
                    AtomicReference atomicReference = this.current;
                    Object objApply = this.reducer.apply(andSet, obj);
                    Objects.requireNonNull(objApply, "The reducer returned a null value");
                    atomicReference.lazySet(objApply);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.upstream.cancel();
                    onError(th);
                    return;
                }
            }
            drain();
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        this.source.subscribe((FlowableSubscriber) new BackpressureReduceSubscriber(subscriber, this.reducer));
    }
}
