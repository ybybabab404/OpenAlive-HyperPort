package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.internal.subscriptions.ScalarSubscription;
import io.reactivex.rxjava3.operators.ScalarSupplier;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableJust<T> extends Flowable<T> implements ScalarSupplier<T> {
    public final Object value;

    public FlowableJust(Object obj) {
        this.value = obj;
    }

    @Override // io.reactivex.rxjava3.functions.Supplier
    public Object get() {
        return this.value;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        subscriber.onSubscribe(new ScalarSubscription(subscriber, this.value));
    }
}
