package io.reactivex.rxjava3.operators;

import io.reactivex.rxjava3.core.FlowableSubscriber;

/* JADX INFO: loaded from: classes2.dex */
public interface ConditionalSubscriber<T> extends FlowableSubscriber<T> {
    boolean tryOnNext(Object obj);
}
