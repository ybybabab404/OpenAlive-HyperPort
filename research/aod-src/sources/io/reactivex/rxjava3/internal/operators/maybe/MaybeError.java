package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;

/* JADX INFO: loaded from: classes2.dex */
public final class MaybeError<T> extends Maybe<T> {
    public final Throwable error;

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver maybeObserver) {
        maybeObserver.onSubscribe(Disposable.disposed());
        maybeObserver.onError(this.error);
    }
}
