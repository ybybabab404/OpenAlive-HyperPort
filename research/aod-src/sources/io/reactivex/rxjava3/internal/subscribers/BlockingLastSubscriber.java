package io.reactivex.rxjava3.internal.subscribers;

/* JADX INFO: loaded from: classes2.dex */
public final class BlockingLastSubscriber<T> extends BlockingBaseSubscriber<T> {
    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        this.value = null;
        this.error = th;
        countDown();
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(Object obj) {
        this.value = obj;
    }
}
