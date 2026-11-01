package io.reactivex.rxjava3.core;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface ObservableOperator<Downstream, Upstream> {
    Observer apply(Observer observer);
}
