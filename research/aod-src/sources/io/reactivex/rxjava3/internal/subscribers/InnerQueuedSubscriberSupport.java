package io.reactivex.rxjava3.internal.subscribers;

/* JADX INFO: loaded from: classes2.dex */
public interface InnerQueuedSubscriberSupport<T> {
    void drain();

    void innerComplete(InnerQueuedSubscriber innerQueuedSubscriber);

    void innerError(InnerQueuedSubscriber innerQueuedSubscriber, Throwable th);

    void innerNext(InnerQueuedSubscriber innerQueuedSubscriber, Object obj);
}
