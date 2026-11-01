package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Notification;
import io.reactivex.rxjava3.internal.util.BlockingHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;

/* JADX INFO: loaded from: classes2.dex */
public final class BlockingFlowableLatest<T> implements Iterable<T> {
    public final Publisher source;

    public static final class LatestSubscriberIterator<T> extends DisposableSubscriber<Notification<T>> implements Iterator<T> {
        public Notification iteratorNotification;
        public final Semaphore notify = new Semaphore(0);
        public final AtomicReference value = new AtomicReference();

        @Override // java.util.Iterator
        public boolean hasNext() {
            Notification notification = this.iteratorNotification;
            if (notification != null && notification.isOnError()) {
                throw ExceptionHelper.wrapOrThrow(this.iteratorNotification.getError());
            }
            Notification notification2 = this.iteratorNotification;
            if ((notification2 == null || notification2.isOnNext()) && this.iteratorNotification == null) {
                try {
                    BlockingHelper.verifyNonBlocking();
                    this.notify.acquire();
                    Notification notification3 = (Notification) this.value.getAndSet(null);
                    this.iteratorNotification = notification3;
                    if (notification3.isOnError()) {
                        throw ExceptionHelper.wrapOrThrow(notification3.getError());
                    }
                } catch (InterruptedException e) {
                    dispose();
                    this.iteratorNotification = Notification.createOnError(e);
                    throw ExceptionHelper.wrapOrThrow(e);
                }
            }
            return this.iteratorNotification.isOnNext();
        }

        @Override // java.util.Iterator
        public Object next() {
            if (!hasNext() || !this.iteratorNotification.isOnNext()) {
                throw new NoSuchElementException();
            }
            Object value = this.iteratorNotification.getValue();
            this.iteratorNotification = null;
            return value;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            RxJavaPlugins.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Notification notification) {
            if (this.value.getAndSet(notification) == null) {
                this.notify.release();
            }
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Read-only iterator.");
        }
    }

    @Override // java.lang.Iterable
    public Iterator iterator() {
        LatestSubscriberIterator latestSubscriberIterator = new LatestSubscriberIterator();
        Flowable.fromPublisher(this.source).materialize().subscribe((FlowableSubscriber) latestSubscriberIterator);
        return latestSubscriberIterator;
    }
}
