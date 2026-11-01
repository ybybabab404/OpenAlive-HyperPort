package io.reactivex.rxjava3.internal.jdk8;

import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableLastStageObserver<T> extends ObservableStageObserver<T> {
    public final Object defaultItem;
    public final boolean hasDefault;

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        if (isDone()) {
            return;
        }
        Object obj = this.value;
        clear();
        if (obj != null) {
            complete(obj);
        } else if (this.hasDefault) {
            complete(this.defaultItem);
        } else {
            completeExceptionally(new NoSuchElementException());
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(Object obj) {
        this.value = obj;
    }
}
