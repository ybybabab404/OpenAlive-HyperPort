package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.internal.operators.observable.ObservableFromCompletable;

/* JADX INFO: loaded from: classes2.dex */
public final class CompletableToObservable<T> extends Observable<T> {
    public final CompletableSource source;

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        this.source.subscribe(new ObservableFromCompletable.FromCompletableObserver(observer));
    }
}
