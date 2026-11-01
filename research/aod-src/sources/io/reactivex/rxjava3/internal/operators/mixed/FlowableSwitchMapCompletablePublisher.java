package io.reactivex.rxjava3.internal.operators.mixed;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.functions.Function;
import org.reactivestreams.Publisher;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableSwitchMapCompletablePublisher<T> extends Completable {
    public final boolean delayErrors;
    public final Function mapper;
    public final Publisher source;

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        this.source.subscribe(new FlowableSwitchMapCompletable.SwitchMapCompletableObserver(completableObserver, this.mapper, this.delayErrors));
    }
}
