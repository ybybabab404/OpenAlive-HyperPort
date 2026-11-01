package io.reactivex.rxjava3.internal.operators.parallel;

import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFlattenIterable;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public final class ParallelFlatMapIterable<T, R> extends ParallelFlowable<R> {
    public final Function mapper;
    public final int prefetch;
    public final ParallelFlowable source;

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public int parallelism() {
        return this.source.parallelism();
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public void subscribe(Subscriber[] subscriberArr) {
        Subscriber[] subscriberArrOnSubscribe = RxJavaPlugins.onSubscribe(this, subscriberArr);
        if (validate(subscriberArrOnSubscribe)) {
            int length = subscriberArrOnSubscribe.length;
            Subscriber[] subscriberArr2 = new Subscriber[length];
            for (int i = 0; i < length; i++) {
                subscriberArr2[i] = FlowableFlattenIterable.subscribe(subscriberArrOnSubscribe[i], this.mapper, this.prefetch);
            }
            this.source.subscribe(subscriberArr2);
        }
    }
}
