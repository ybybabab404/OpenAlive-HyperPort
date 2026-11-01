package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.internal.fuseable.HasUpstreamObservableSource;

/* JADX INFO: loaded from: classes2.dex */
abstract class AbstractObservableWithUpstream<T, U> extends Observable<U> implements HasUpstreamObservableSource<T> {
    public final ObservableSource source;

    public AbstractObservableWithUpstream(ObservableSource observableSource) {
        this.source = observableSource;
    }
}
