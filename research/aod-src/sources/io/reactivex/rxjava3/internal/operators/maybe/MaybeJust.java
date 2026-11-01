package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.operators.ScalarSupplier;

/* JADX INFO: loaded from: classes2.dex */
public final class MaybeJust<T> extends Maybe<T> implements ScalarSupplier<T> {
    public final Object value;

    @Override // io.reactivex.rxjava3.functions.Supplier
    public Object get() {
        return this.value;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver maybeObserver) {
        maybeObserver.onSubscribe(Disposable.disposed());
        maybeObserver.onSuccess(this.value);
    }
}
