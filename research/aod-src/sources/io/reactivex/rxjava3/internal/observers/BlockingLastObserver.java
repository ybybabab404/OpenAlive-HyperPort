package io.reactivex.rxjava3.internal.observers;

/* JADX INFO: loaded from: classes2.dex */
public final class BlockingLastObserver<T> extends BlockingBaseObserver<T> {
    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(Throwable th) {
        this.value = null;
        this.error = th;
        countDown();
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(Object obj) {
        this.value = obj;
    }
}
