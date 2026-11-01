package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableFromUnsafeSource<T> extends Observable<T> {
    public final ObservableSource source;

    public ObservableFromUnsafeSource(ObservableSource observableSource) {
        this.source = observableSource;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        this.source.subscribe(observer);
    }
}
