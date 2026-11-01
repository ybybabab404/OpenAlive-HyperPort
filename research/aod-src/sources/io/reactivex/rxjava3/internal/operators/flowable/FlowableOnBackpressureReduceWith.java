package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Supplier;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableOnBackpressureReduceWith<T, R> extends AbstractFlowableWithUpstream<T, R> {
    public final BiFunction reducer;
    public final Supplier supplier;

    public static final class BackpressureReduceWithSubscriber<T, R> extends AbstractBackpressureThrottlingSubscriber<T, R> {
        public final BiFunction reducer;
        public final Supplier supplier;

        public BackpressureReduceWithSubscriber(Subscriber subscriber, Supplier supplier, BiFunction biFunction) {
            super(subscriber);
            this.reducer = biFunction;
            this.supplier = supplier;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            Object andSet = this.current.get();
            if (andSet != null) {
                andSet = this.current.getAndSet(null);
            }
            try {
                if (andSet == null) {
                    AtomicReference atomicReference = this.current;
                    BiFunction biFunction = this.reducer;
                    Object obj2 = this.supplier.get();
                    Objects.requireNonNull(obj2, "The supplier returned a null value");
                    Object objApply = biFunction.apply(obj2, obj);
                    Objects.requireNonNull(objApply, "The reducer returned a null value");
                    atomicReference.lazySet(objApply);
                } else {
                    AtomicReference atomicReference2 = this.current;
                    Object objApply2 = this.reducer.apply(andSet, obj);
                    Objects.requireNonNull(objApply2, "The reducer returned a null value");
                    atomicReference2.lazySet(objApply2);
                }
                drain();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.cancel();
                onError(th);
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        this.source.subscribe((FlowableSubscriber) new BackpressureReduceWithSubscriber(subscriber, this.supplier, this.reducer));
    }
}
