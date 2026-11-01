package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFromCompletable;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public final class CompletableToFlowable<T> extends Flowable<T> {
    public final CompletableSource source;

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        this.source.subscribe(new FlowableFromCompletable.FromCompletableObserver(subscriber));
    }
}
