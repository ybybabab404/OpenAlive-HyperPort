package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;

/* JADX INFO: loaded from: classes2.dex */
public final class CompletableToSingle<T> extends Single<T> {
    public final Object completionValue;
    public final Supplier completionValueSupplier;
    public final CompletableSource source;

    public final class ToSingle implements CompletableObserver {
        public final SingleObserver observer;

        public ToSingle(SingleObserver singleObserver) {
            this.observer = singleObserver;
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            Object obj;
            CompletableToSingle completableToSingle = CompletableToSingle.this;
            Supplier supplier = completableToSingle.completionValueSupplier;
            if (supplier != null) {
                try {
                    obj = supplier.get();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.observer.onError(th);
                    return;
                }
            } else {
                obj = completableToSingle.completionValue;
            }
            if (obj == null) {
                this.observer.onError(new NullPointerException("The value supplied is null"));
            } else {
                this.observer.onSuccess(obj);
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable th) {
            this.observer.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            this.observer.onSubscribe(disposable);
        }
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver singleObserver) {
        this.source.subscribe(new ToSingle(singleObserver));
    }
}
