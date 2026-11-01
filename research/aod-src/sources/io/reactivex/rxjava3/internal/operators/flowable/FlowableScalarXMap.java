package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.ScalarSubscription;
import java.util.Objects;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableScalarXMap {

    public static final class ScalarXMapFlowable<T, R> extends Flowable<R> {
        public final Function mapper;
        public final Object value;

        @Override // io.reactivex.rxjava3.core.Flowable
        public void subscribeActual(Subscriber subscriber) {
            try {
                Object objApply = this.mapper.apply(this.value);
                Objects.requireNonNull(objApply, "The mapper returned a null Publisher");
                Publisher publisher = (Publisher) objApply;
                if (!(publisher instanceof Supplier)) {
                    publisher.subscribe(subscriber);
                    return;
                }
                try {
                    Object obj = ((Supplier) publisher).get();
                    if (obj == null) {
                        EmptySubscription.complete(subscriber);
                    } else {
                        subscriber.onSubscribe(new ScalarSubscription(subscriber, obj));
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    EmptySubscription.error(th, subscriber);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                EmptySubscription.error(th2, subscriber);
            }
        }
    }

    public static boolean tryScalarXMapSubscribe(Publisher publisher, Subscriber subscriber, Function function) {
        if (!(publisher instanceof Supplier)) {
            return false;
        }
        try {
            Object obj = ((Supplier) publisher).get();
            if (obj == null) {
                EmptySubscription.complete(subscriber);
                return true;
            }
            try {
                Object objApply = function.apply(obj);
                Objects.requireNonNull(objApply, "The mapper returned a null Publisher");
                Publisher publisher2 = (Publisher) objApply;
                if (publisher2 instanceof Supplier) {
                    try {
                        Object obj2 = ((Supplier) publisher2).get();
                        if (obj2 == null) {
                            EmptySubscription.complete(subscriber);
                            return true;
                        }
                        subscriber.onSubscribe(new ScalarSubscription(subscriber, obj2));
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        EmptySubscription.error(th, subscriber);
                        return true;
                    }
                } else {
                    publisher2.subscribe(subscriber);
                }
                return true;
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                EmptySubscription.error(th2, subscriber);
                return true;
            }
        } catch (Throwable th3) {
            Exceptions.throwIfFatal(th3);
            EmptySubscription.error(th3, subscriber);
            return true;
        }
    }
}
