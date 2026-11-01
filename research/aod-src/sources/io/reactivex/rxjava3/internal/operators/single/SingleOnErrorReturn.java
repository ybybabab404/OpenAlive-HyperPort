package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;

/* JADX INFO: loaded from: classes2.dex */
public final class SingleOnErrorReturn<T> extends Single<T> {
    public final SingleSource source;
    public final Object value;
    public final Function valueSupplier;

    public final class OnErrorReturn implements SingleObserver<T> {
        public final SingleObserver observer;

        public OnErrorReturn(SingleObserver singleObserver) {
            this.observer = singleObserver;
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onError(Throwable th) {
            Object objApply;
            SingleOnErrorReturn singleOnErrorReturn = SingleOnErrorReturn.this;
            Function function = singleOnErrorReturn.valueSupplier;
            if (function != null) {
                try {
                    objApply = function.apply(th);
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    this.observer.onError(new CompositeException(th, th2));
                    return;
                }
            } else {
                objApply = singleOnErrorReturn.value;
            }
            if (objApply != null) {
                this.observer.onSuccess(objApply);
                return;
            }
            NullPointerException nullPointerException = new NullPointerException("Value supplied was null");
            nullPointerException.initCause(th);
            this.observer.onError(nullPointerException);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSubscribe(Disposable disposable) {
            this.observer.onSubscribe(disposable);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(Object obj) {
            this.observer.onSuccess(obj);
        }
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver singleObserver) {
        this.source.subscribe(new OnErrorReturn(singleObserver));
    }
}
