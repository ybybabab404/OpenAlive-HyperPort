package io.reactivex.rxjava3.internal.observers;

/* JADX INFO: loaded from: classes2.dex */
public final class BlockingFirstObserver<T> extends BlockingBaseObserver<T> {
    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(Throwable th) {
        if (this.value == null) {
            this.error = th;
        }
        countDown();
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(Object obj) {
        if (this.value == null) {
            this.value = obj;
            this.upstream.dispose();
            countDown();
        }
    }
}
