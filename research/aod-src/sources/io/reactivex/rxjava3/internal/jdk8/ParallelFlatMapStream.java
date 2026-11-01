package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public final class ParallelFlatMapStream<T, R> extends ParallelFlowable<R> {
    public final Function mapper;
    public final int prefetch;
    public final ParallelFlowable source;

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public int parallelism() {
        return this.source.parallelism();
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public void subscribe(Subscriber[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            Subscriber[] subscriberArr2 = new Subscriber[length];
            for (int i = 0; i < length; i++) {
                subscriberArr2[i] = FlowableFlatMapStream.subscribe(subscriberArr[i], this.mapper, this.prefetch);
            }
            this.source.subscribe(subscriberArr2);
        }
    }
}
