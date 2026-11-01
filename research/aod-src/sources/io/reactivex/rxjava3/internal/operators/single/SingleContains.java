package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiPredicate;

/* JADX INFO: loaded from: classes2.dex */
public final class SingleContains<T> extends Single<Boolean> {
    public final BiPredicate comparer;
    public final SingleSource source;
    public final Object value;

    public final class ContainsSingleObserver implements SingleObserver<T> {
        public final SingleObserver downstream;

        public ContainsSingleObserver(SingleObserver singleObserver) {
            this.downstream = singleObserver;
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSubscribe(Disposable disposable) {
            this.downstream.onSubscribe(disposable);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(Object obj) {
            try {
                SingleContains singleContains = SingleContains.this;
                this.downstream.onSuccess(Boolean.valueOf(singleContains.comparer.test(obj, singleContains.value)));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver singleObserver) {
        this.source.subscribe(new ContainsSingleObserver(singleObserver));
    }
}
