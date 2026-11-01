package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableFromSupplier<T> extends Flowable<T> implements Supplier<T> {
    public final Supplier supplier;

    @Override // io.reactivex.rxjava3.functions.Supplier
    public Object get() {
        Object obj = this.supplier.get();
        Objects.requireNonNull(obj, "The supplier returned a null value");
        return obj;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        DeferredScalarSubscription deferredScalarSubscription = new DeferredScalarSubscription(subscriber);
        subscriber.onSubscribe(deferredScalarSubscription);
        try {
            Object obj = this.supplier.get();
            Objects.requireNonNull(obj, "The supplier returned a null value");
            deferredScalarSubscription.complete(obj);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            if (deferredScalarSubscription.isCancelled()) {
                RxJavaPlugins.onError(th);
            } else {
                subscriber.onError(th);
            }
        }
    }
}
