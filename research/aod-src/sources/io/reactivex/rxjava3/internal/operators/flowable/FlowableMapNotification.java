package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.subscribers.SinglePostCompleteSubscriber;
import java.util.Objects;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableMapNotification<T, R> extends AbstractFlowableWithUpstream<T, R> {
    public final Supplier onCompleteSupplier;
    public final Function onErrorMapper;
    public final Function onNextMapper;

    public static final class MapNotificationSubscriber<T, R> extends SinglePostCompleteSubscriber<T, R> {
        public final Supplier onCompleteSupplier;
        public final Function onErrorMapper;
        public final Function onNextMapper;

        public MapNotificationSubscriber(Subscriber subscriber, Function function, Function function2, Supplier supplier) {
            super(subscriber);
            this.onNextMapper = function;
            this.onErrorMapper = function2;
            this.onCompleteSupplier = supplier;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            try {
                Object obj = this.onCompleteSupplier.get();
                Objects.requireNonNull(obj, "The onComplete publisher returned is null");
                complete(obj);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            try {
                Object objApply = this.onErrorMapper.apply(th);
                Objects.requireNonNull(objApply, "The onError publisher returned is null");
                complete(objApply);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.downstream.onError(new CompositeException(th, th2));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            try {
                Object objApply = this.onNextMapper.apply(obj);
                Objects.requireNonNull(objApply, "The onNext publisher returned is null");
                this.produced++;
                this.downstream.onNext(objApply);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        this.source.subscribe((FlowableSubscriber) new MapNotificationSubscriber(subscriber, this.onNextMapper, this.onErrorMapper, this.onCompleteSupplier));
    }
}
