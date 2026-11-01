package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableError<T> extends Observable<T> {
    public final Supplier errorSupplier;

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        try {
            th = (Throwable) ExceptionHelper.nullCheck(this.errorSupplier.get(), "Supplier returned a null Throwable.");
        } catch (Throwable th) {
            th = th;
            Exceptions.throwIfFatal(th);
        }
        EmptyDisposable.error(th, observer);
    }
}
