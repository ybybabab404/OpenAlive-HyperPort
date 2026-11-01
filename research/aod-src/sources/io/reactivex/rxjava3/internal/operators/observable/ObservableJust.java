package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.operators.ScalarSupplier;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableJust<T> extends Observable<T> implements ScalarSupplier<T> {
    public final Object value;

    public ObservableJust(Object obj) {
        this.value = obj;
    }

    @Override // io.reactivex.rxjava3.functions.Supplier
    public Object get() {
        return this.value;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        ObservableScalarXMap.ScalarDisposable scalarDisposable = new ObservableScalarXMap.ScalarDisposable(observer, this.value);
        observer.onSubscribe(scalarDisposable);
        scalarDisposable.run();
    }
}
