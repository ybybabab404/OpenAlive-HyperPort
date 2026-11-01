package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.functions.Function;
import org.reactivestreams.Publisher;

/* JADX INFO: loaded from: classes2.dex */
public enum MaybeToPublisher implements Function<MaybeSource<Object>, Publisher<Object>> {
    INSTANCE;

    @Override // io.reactivex.rxjava3.functions.Function
    public Publisher apply(MaybeSource maybeSource) {
        return new MaybeToFlowable(maybeSource);
    }
}
