package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeOperator;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public final class MaybeLift<T, R> extends AbstractMaybeWithUpstream<T, R> {
    public final MaybeOperator operator;

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver maybeObserver) {
        try {
            MaybeObserver maybeObserverApply = this.operator.apply(maybeObserver);
            Objects.requireNonNull(maybeObserverApply, "The operator returned a null MaybeObserver");
            this.source.subscribe(maybeObserverApply);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, maybeObserver);
        }
    }
}
