package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.internal.fuseable.HasUpstreamPublisher;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
abstract class AbstractFlowableWithUpstream<T, R> extends Flowable<R> implements HasUpstreamPublisher<T> {
    public final Flowable source;

    public AbstractFlowableWithUpstream(Flowable flowable) {
        Objects.requireNonNull(flowable, "source is null");
        this.source = flowable;
    }
}
