package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import org.reactivestreams.Publisher;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableElementAtMaybePublisher<T> extends Maybe<T> {
    public final long index;
    public final Publisher source;

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver maybeObserver) {
        this.source.subscribe(new FlowableElementAtMaybe.ElementAtSubscriber(maybeObserver, this.index));
    }
}
