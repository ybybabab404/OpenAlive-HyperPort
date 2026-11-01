package io.reactivex.rxjava3.processors;

import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.internal.util.NotificationLite;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes2.dex */
public final class BehaviorProcessor<T> extends FlowableProcessor<T> {
    public long index;
    public final Lock readLock;
    public final AtomicReference subscribers;
    public final AtomicReference terminalEvent;
    public final AtomicReference value;
    public final Lock writeLock;
    public static final Object[] EMPTY_ARRAY = new Object[0];
    public static final BehaviorSubscription[] EMPTY = new BehaviorSubscription[0];
    public static final BehaviorSubscription[] TERMINATED = new BehaviorSubscription[0];

    public static final class BehaviorSubscription<T> extends AtomicLong implements Subscription, AppendOnlyLinkedArrayList.NonThrowingPredicate<Object> {
        public volatile boolean cancelled;
        public final Subscriber downstream;
        public boolean emitting;
        public boolean fastPath;
        public long index;
        public boolean next;
        public AppendOnlyLinkedArrayList queue;
        public final BehaviorProcessor state;

        public BehaviorSubscription(Subscriber subscriber, BehaviorProcessor behaviorProcessor) {
            this.downstream = subscriber;
            this.state = behaviorProcessor;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.state.remove(this);
        }

        public void emitFirst() {
            if (this.cancelled) {
                return;
            }
            synchronized (this) {
                try {
                    if (this.cancelled) {
                        return;
                    }
                    if (this.next) {
                        return;
                    }
                    BehaviorProcessor behaviorProcessor = this.state;
                    Lock lock = behaviorProcessor.readLock;
                    lock.lock();
                    this.index = behaviorProcessor.index;
                    Object obj = behaviorProcessor.value.get();
                    lock.unlock();
                    this.emitting = obj != null;
                    this.next = true;
                    if (obj == null || test(obj)) {
                        return;
                    }
                    emitLoop();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void emitLoop() {
            AppendOnlyLinkedArrayList appendOnlyLinkedArrayList;
            while (!this.cancelled) {
                synchronized (this) {
                    try {
                        appendOnlyLinkedArrayList = this.queue;
                        if (appendOnlyLinkedArrayList == null) {
                            this.emitting = false;
                            return;
                        }
                        this.queue = null;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                appendOnlyLinkedArrayList.forEachWhile(this);
            }
        }

        public void emitNext(Object obj, long j) {
            if (this.cancelled) {
                return;
            }
            if (!this.fastPath) {
                synchronized (this) {
                    try {
                        if (this.cancelled) {
                            return;
                        }
                        if (this.index == j) {
                            return;
                        }
                        if (this.emitting) {
                            AppendOnlyLinkedArrayList appendOnlyLinkedArrayList = this.queue;
                            if (appendOnlyLinkedArrayList == null) {
                                appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList(4);
                                this.queue = appendOnlyLinkedArrayList;
                            }
                            appendOnlyLinkedArrayList.add(obj);
                            return;
                        }
                        this.next = true;
                        this.fastPath = true;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            test(obj);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this, j);
            }
        }

        @Override // io.reactivex.rxjava3.internal.util.AppendOnlyLinkedArrayList.NonThrowingPredicate, io.reactivex.rxjava3.functions.Predicate
        public boolean test(Object obj) {
            if (this.cancelled) {
                return true;
            }
            if (NotificationLite.isComplete(obj)) {
                this.downstream.onComplete();
                return true;
            }
            if (NotificationLite.isError(obj)) {
                this.downstream.onError(NotificationLite.getError(obj));
                return true;
            }
            long j = get();
            if (j == 0) {
                cancel();
                this.downstream.onError(MissingBackpressureException.createDefault());
                return true;
            }
            this.downstream.onNext(NotificationLite.getValue(obj));
            if (j == Long.MAX_VALUE) {
                return false;
            }
            decrementAndGet();
            return false;
        }
    }

    public boolean add(BehaviorSubscription behaviorSubscription) {
        BehaviorSubscription[] behaviorSubscriptionArr;
        BehaviorSubscription[] behaviorSubscriptionArr2;
        do {
            behaviorSubscriptionArr = (BehaviorSubscription[]) this.subscribers.get();
            if (behaviorSubscriptionArr == TERMINATED) {
                return false;
            }
            int length = behaviorSubscriptionArr.length;
            behaviorSubscriptionArr2 = new BehaviorSubscription[length + 1];
            System.arraycopy(behaviorSubscriptionArr, 0, behaviorSubscriptionArr2, 0, length);
            behaviorSubscriptionArr2[length] = behaviorSubscription;
        } while (!this.subscribers.compareAndSet(behaviorSubscriptionArr, behaviorSubscriptionArr2));
        return true;
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (this.terminalEvent.compareAndSet(null, ExceptionHelper.TERMINATED)) {
            Object objComplete = NotificationLite.complete();
            for (BehaviorSubscription behaviorSubscription : terminate(objComplete)) {
                behaviorSubscription.emitNext(objComplete, this.index);
            }
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        ExceptionHelper.nullCheck(th, "onError called with a null Throwable.");
        if (!this.terminalEvent.compareAndSet(null, th)) {
            RxJavaPlugins.onError(th);
            return;
        }
        Object objError = NotificationLite.error(th);
        for (BehaviorSubscription behaviorSubscription : terminate(objError)) {
            behaviorSubscription.emitNext(objError, this.index);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(Object obj) {
        ExceptionHelper.nullCheck(obj, "onNext called with a null value.");
        if (this.terminalEvent.get() != null) {
            return;
        }
        Object next = NotificationLite.next(obj);
        setCurrent(next);
        for (BehaviorSubscription behaviorSubscription : (BehaviorSubscription[]) this.subscribers.get()) {
            behaviorSubscription.emitNext(next, this.index);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        if (this.terminalEvent.get() != null) {
            subscription.cancel();
        } else {
            subscription.request(Long.MAX_VALUE);
        }
    }

    public void remove(BehaviorSubscription behaviorSubscription) {
        BehaviorSubscription[] behaviorSubscriptionArr;
        BehaviorSubscription[] behaviorSubscriptionArr2;
        do {
            behaviorSubscriptionArr = (BehaviorSubscription[]) this.subscribers.get();
            int length = behaviorSubscriptionArr.length;
            if (length == 0) {
                return;
            }
            int i = 0;
            while (true) {
                if (i >= length) {
                    i = -1;
                    break;
                } else if (behaviorSubscriptionArr[i] == behaviorSubscription) {
                    break;
                } else {
                    i++;
                }
            }
            if (i < 0) {
                return;
            }
            if (length == 1) {
                behaviorSubscriptionArr2 = EMPTY;
            } else {
                BehaviorSubscription[] behaviorSubscriptionArr3 = new BehaviorSubscription[length - 1];
                System.arraycopy(behaviorSubscriptionArr, 0, behaviorSubscriptionArr3, 0, i);
                System.arraycopy(behaviorSubscriptionArr, i + 1, behaviorSubscriptionArr3, i, (length - i) - 1);
                behaviorSubscriptionArr2 = behaviorSubscriptionArr3;
            }
        } while (!this.subscribers.compareAndSet(behaviorSubscriptionArr, behaviorSubscriptionArr2));
    }

    public void setCurrent(Object obj) {
        Lock lock = this.writeLock;
        lock.lock();
        this.index++;
        this.value.lazySet(obj);
        lock.unlock();
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        BehaviorSubscription behaviorSubscription = new BehaviorSubscription(subscriber, this);
        subscriber.onSubscribe(behaviorSubscription);
        if (add(behaviorSubscription)) {
            if (behaviorSubscription.cancelled) {
                remove(behaviorSubscription);
                return;
            } else {
                behaviorSubscription.emitFirst();
                return;
            }
        }
        Throwable th = (Throwable) this.terminalEvent.get();
        if (th == ExceptionHelper.TERMINATED) {
            subscriber.onComplete();
        } else {
            subscriber.onError(th);
        }
    }

    public BehaviorSubscription[] terminate(Object obj) {
        setCurrent(obj);
        return (BehaviorSubscription[]) this.subscribers.getAndSet(TERMINATED);
    }
}
