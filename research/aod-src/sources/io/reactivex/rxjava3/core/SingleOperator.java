package io.reactivex.rxjava3.core;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface SingleOperator<Downstream, Upstream> {
    SingleObserver apply(SingleObserver singleObserver);
}
