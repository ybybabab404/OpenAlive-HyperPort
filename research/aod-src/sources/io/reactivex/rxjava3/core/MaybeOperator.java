package io.reactivex.rxjava3.core;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface MaybeOperator<Downstream, Upstream> {
    MaybeObserver apply(MaybeObserver maybeObserver);
}
