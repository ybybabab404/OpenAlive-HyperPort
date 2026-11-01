package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;

/* JADX INFO: loaded from: classes2.dex */
public final class SingleFlattenStreamAsObservable<T, R> extends Observable<R> {
    public final Function mapper;
    public final Single source;

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        this.source.subscribe(new MaybeFlattenStreamAsObservable.FlattenStreamMultiObserver(observer, this.mapper));
    }
}
