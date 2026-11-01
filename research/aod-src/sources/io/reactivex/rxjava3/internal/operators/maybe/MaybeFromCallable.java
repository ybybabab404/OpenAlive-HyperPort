package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes2.dex */
public final class MaybeFromCallable<T> extends Maybe<T> implements Supplier<T> {
    public final Callable callable;

    @Override // io.reactivex.rxjava3.functions.Supplier
    public Object get() {
        return this.callable.call();
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver maybeObserver) {
        Disposable disposableEmpty = Disposable.empty();
        maybeObserver.onSubscribe(disposableEmpty);
        if (disposableEmpty.isDisposed()) {
            return;
        }
        try {
            Object objCall = this.callable.call();
            if (disposableEmpty.isDisposed()) {
                return;
            }
            if (objCall == null) {
                maybeObserver.onComplete();
            } else {
                maybeObserver.onSuccess(objCall);
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            if (disposableEmpty.isDisposed()) {
                RxJavaPlugins.onError(th);
            } else {
                maybeObserver.onError(th);
            }
        }
    }
}
