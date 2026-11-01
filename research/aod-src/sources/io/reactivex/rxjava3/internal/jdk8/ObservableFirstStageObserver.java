package io.reactivex.rxjava3.internal.jdk8;

import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableFirstStageObserver<T> extends ObservableStageObserver<T> {
    public final Object defaultItem;
    public final boolean hasDefault;

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        if (isDone()) {
            return;
        }
        clear();
        if (this.hasDefault) {
            complete(this.defaultItem);
        } else {
            completeExceptionally(new NoSuchElementException());
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(Object obj) {
        complete(obj);
    }
}
