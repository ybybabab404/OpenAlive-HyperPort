package io.reactivex.rxjava3.internal.observers;

/* JADX INFO: loaded from: classes2.dex */
public interface InnerQueuedObserverSupport<T> {
    void drain();

    void innerComplete(InnerQueuedObserver innerQueuedObserver);

    void innerError(InnerQueuedObserver innerQueuedObserver, Throwable th);

    void innerNext(InnerQueuedObserver innerQueuedObserver, Object obj);
}
