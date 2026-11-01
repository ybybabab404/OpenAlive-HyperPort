package io.reactivex.rxjava3.internal.observers;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;

/* JADX INFO: loaded from: classes2.dex */
public final class DisposableAutoReleaseObserver<T> extends AbstractDisposableAutoRelease implements Observer<T> {
    public final Consumer onNext;

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(Object obj) {
        if (get() != DisposableHelper.DISPOSED) {
            try {
                this.onNext.accept(obj);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                get().dispose();
                onError(th);
            }
        }
    }
}
