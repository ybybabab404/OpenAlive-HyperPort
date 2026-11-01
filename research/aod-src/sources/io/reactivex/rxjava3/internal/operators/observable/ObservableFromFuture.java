package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.observers.DeferredScalarDisposable;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableFromFuture<T> extends Observable<T> {
    public final Future future;
    public final long timeout;
    public final TimeUnit unit;

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        DeferredScalarDisposable deferredScalarDisposable = new DeferredScalarDisposable(observer);
        observer.onSubscribe(deferredScalarDisposable);
        if (deferredScalarDisposable.isDisposed()) {
            return;
        }
        try {
            TimeUnit timeUnit = this.unit;
            deferredScalarDisposable.complete(ExceptionHelper.nullCheck(timeUnit != null ? this.future.get(this.timeout, timeUnit) : this.future.get(), "Future returned a null value."));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            if (deferredScalarDisposable.isDisposed()) {
                return;
            }
            observer.onError(th);
        }
    }
}
