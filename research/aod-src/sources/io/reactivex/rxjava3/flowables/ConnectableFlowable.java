package io.reactivex.rxjava3.flowables;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public abstract class ConnectableFlowable<T> extends Flowable<T> {
    public abstract void connect(Consumer consumer);

    public abstract void reset();
}
