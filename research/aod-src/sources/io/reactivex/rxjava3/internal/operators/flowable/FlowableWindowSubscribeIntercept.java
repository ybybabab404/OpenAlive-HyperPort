package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.processors.FlowableProcessor;
import java.util.concurrent.atomic.AtomicBoolean;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
final class FlowableWindowSubscribeIntercept<T> extends Flowable<T> {
    public final AtomicBoolean once = new AtomicBoolean();
    public final FlowableProcessor window;

    public FlowableWindowSubscribeIntercept(FlowableProcessor flowableProcessor) {
        this.window = flowableProcessor;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        this.window.subscribe(subscriber);
        this.once.set(true);
    }

    public boolean tryAbandon() {
        return !this.once.get() && this.once.compareAndSet(false, true);
    }
}
