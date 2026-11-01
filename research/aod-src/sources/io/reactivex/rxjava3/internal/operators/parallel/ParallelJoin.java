package io.reactivex.rxjava3.internal.operators.parallel;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.QueueOverflowException;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.operators.SimplePlainQueue;
import io.reactivex.rxjava3.operators.SpscArrayQueue;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes2.dex */
public final class ParallelJoin<T> extends Flowable<T> {
    public final boolean delayErrors;
    public final int prefetch;
    public final ParallelFlowable source;

    public static final class JoinInnerSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
        public final int limit;
        public final JoinSubscriptionBase parent;
        public final int prefetch;
        public long produced;
        public volatile SimplePlainQueue queue;

        public JoinInnerSubscriber(JoinSubscriptionBase joinSubscriptionBase, int i) {
            this.parent = joinSubscriptionBase;
            this.prefetch = i;
            this.limit = i - (i >> 2);
        }

        public boolean cancel() {
            return SubscriptionHelper.cancel(this);
        }

        public SimplePlainQueue getQueue() {
            SimplePlainQueue simplePlainQueue = this.queue;
            if (simplePlainQueue != null) {
                return simplePlainQueue;
            }
            SpscArrayQueue spscArrayQueue = new SpscArrayQueue(this.prefetch);
            this.queue = spscArrayQueue;
            return spscArrayQueue;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.parent.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            this.parent.onNext(this, obj);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, this.prefetch);
        }

        public void request(long j) {
            long j2 = this.produced + j;
            if (j2 < this.limit) {
                this.produced = j2;
            } else {
                this.produced = 0L;
                get().request(j2);
            }
        }

        public void requestOne() {
            long j = this.produced + 1;
            if (j != this.limit) {
                this.produced = j;
            } else {
                this.produced = 0L;
                get().request(j);
            }
        }
    }

    public static final class JoinSubscription<T> extends JoinSubscriptionBase<T> {
        public JoinSubscription(Subscriber subscriber, int i, int i2) {
            super(subscriber, i, i2);
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            drainLoop();
        }

        public void drainLoop() {
            boolean z;
            long j;
            boolean z2;
            Object objPoll;
            JoinInnerSubscriber[] joinInnerSubscriberArr = this.subscribers;
            int length = joinInnerSubscriberArr.length;
            Subscriber subscriber = this.downstream;
            int iAddAndGet = 1;
            do {
                long j2 = this.requested.get();
                long j3 = 0;
                do {
                    z = false;
                    if (j3 == j2) {
                        j = 0;
                        break;
                    }
                    if (this.cancelled) {
                        cleanup();
                        return;
                    }
                    Throwable th = this.errors.get();
                    if (th != null) {
                        cleanup();
                        subscriber.onError(th);
                        return;
                    }
                    boolean z3 = this.done.get() == 0;
                    z2 = true;
                    for (JoinInnerSubscriber joinInnerSubscriber : joinInnerSubscriberArr) {
                        j = 0;
                        SimplePlainQueue simplePlainQueue = joinInnerSubscriber.queue;
                        if (simplePlainQueue != null && (objPoll = simplePlainQueue.poll()) != null) {
                            subscriber.onNext(objPoll);
                            joinInnerSubscriber.requestOne();
                            j3++;
                            if (j3 == j2) {
                                break;
                            } else {
                                z2 = false;
                            }
                        }
                    }
                    j = 0;
                    if (z3 && z2) {
                        subscriber.onComplete();
                        return;
                    }
                } while (!z2);
                if (j3 == j2) {
                    if (this.cancelled) {
                        cleanup();
                        return;
                    }
                    Throwable th2 = this.errors.get();
                    if (th2 != null) {
                        cleanup();
                        subscriber.onError(th2);
                        return;
                    }
                    boolean z4 = this.done.get() == 0;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            z = true;
                            break;
                        }
                        SimplePlainQueue simplePlainQueue2 = joinInnerSubscriberArr[i].queue;
                        if (simplePlainQueue2 != null && !simplePlainQueue2.isEmpty()) {
                            break;
                        } else {
                            i++;
                        }
                    }
                    if (z4 && z) {
                        subscriber.onComplete();
                        return;
                    }
                }
                if (j3 != j) {
                    BackpressureHelper.produced(this.requested, j3);
                }
                iAddAndGet = addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void onComplete() {
            this.done.decrementAndGet();
            drain();
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void onError(Throwable th) {
            if (this.errors.compareAndSet(null, th)) {
                cancelAll();
                drain();
            } else if (th != this.errors.get()) {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void onNext(JoinInnerSubscriber joinInnerSubscriber, Object obj) {
            if (get() == 0 && compareAndSet(0, 1)) {
                if (this.requested.get() != 0) {
                    this.downstream.onNext(obj);
                    if (this.requested.get() != Long.MAX_VALUE) {
                        this.requested.decrementAndGet();
                    }
                    joinInnerSubscriber.request(1L);
                } else if (!joinInnerSubscriber.getQueue().offer(obj)) {
                    cancelAll();
                    QueueOverflowException queueOverflowException = new QueueOverflowException();
                    if (this.errors.compareAndSet(null, queueOverflowException)) {
                        this.downstream.onError(queueOverflowException);
                        return;
                    } else {
                        RxJavaPlugins.onError(queueOverflowException);
                        return;
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else if (!joinInnerSubscriber.getQueue().offer(obj)) {
                cancelAll();
                onError(new QueueOverflowException());
                return;
            } else if (getAndIncrement() != 0) {
                return;
            }
            drainLoop();
        }
    }

    public static abstract class JoinSubscriptionBase<T> extends AtomicInteger implements Subscription {
        public volatile boolean cancelled;
        public final Subscriber downstream;
        public final JoinInnerSubscriber[] subscribers;
        public final AtomicThrowable errors = new AtomicThrowable();
        public final AtomicLong requested = new AtomicLong();
        public final AtomicInteger done = new AtomicInteger();

        public JoinSubscriptionBase(Subscriber subscriber, int i, int i2) {
            this.downstream = subscriber;
            JoinInnerSubscriber[] joinInnerSubscriberArr = new JoinInnerSubscriber[i];
            for (int i3 = 0; i3 < i; i3++) {
                joinInnerSubscriberArr[i3] = new JoinInnerSubscriber(this, i2);
            }
            this.subscribers = joinInnerSubscriberArr;
            this.done.lazySet(i);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            cancelAll();
            if (getAndIncrement() == 0) {
                cleanup();
            }
        }

        public void cancelAll() {
            for (JoinInnerSubscriber joinInnerSubscriber : this.subscribers) {
                joinInnerSubscriber.cancel();
            }
        }

        public void cleanup() {
            for (JoinInnerSubscriber joinInnerSubscriber : this.subscribers) {
                joinInnerSubscriber.queue = null;
            }
        }

        public abstract void drain();

        public abstract void onComplete();

        public abstract void onError(Throwable th);

        public abstract void onNext(JoinInnerSubscriber joinInnerSubscriber, Object obj);

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                drain();
            }
        }
    }

    public static final class JoinSubscriptionDelayError<T> extends JoinSubscriptionBase<T> {
        public JoinSubscriptionDelayError(Subscriber subscriber, int i, int i2) {
            super(subscriber, i, i2);
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            drainLoop();
        }

        public void drainLoop() {
            boolean z;
            long j;
            boolean z2;
            Object objPoll;
            JoinInnerSubscriber[] joinInnerSubscriberArr = this.subscribers;
            int length = joinInnerSubscriberArr.length;
            Subscriber subscriber = this.downstream;
            int iAddAndGet = 1;
            do {
                long j2 = this.requested.get();
                long j3 = 0;
                do {
                    z = false;
                    if (j3 == j2) {
                        j = 0;
                        break;
                    }
                    if (this.cancelled) {
                        cleanup();
                        return;
                    }
                    boolean z3 = this.done.get() == 0;
                    z2 = true;
                    for (JoinInnerSubscriber joinInnerSubscriber : joinInnerSubscriberArr) {
                        j = 0;
                        SimplePlainQueue simplePlainQueue = joinInnerSubscriber.queue;
                        if (simplePlainQueue != null && (objPoll = simplePlainQueue.poll()) != null) {
                            subscriber.onNext(objPoll);
                            joinInnerSubscriber.requestOne();
                            j3++;
                            if (j3 == j2) {
                                break;
                            } else {
                                z2 = false;
                            }
                        }
                    }
                    j = 0;
                    if (z3 && z2) {
                        this.errors.tryTerminateConsumer(subscriber);
                        return;
                    }
                } while (!z2);
                if (j3 == j2) {
                    if (this.cancelled) {
                        cleanup();
                        return;
                    }
                    boolean z4 = this.done.get() == 0;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            z = true;
                            break;
                        }
                        SimplePlainQueue simplePlainQueue2 = joinInnerSubscriberArr[i].queue;
                        if (simplePlainQueue2 != null && !simplePlainQueue2.isEmpty()) {
                            break;
                        } else {
                            i++;
                        }
                    }
                    if (z4 && z) {
                        this.errors.tryTerminateConsumer(subscriber);
                        return;
                    }
                }
                if (j3 != j) {
                    BackpressureHelper.produced(this.requested, j3);
                }
                iAddAndGet = addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void onComplete() {
            this.done.decrementAndGet();
            drain();
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void onError(Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                this.done.decrementAndGet();
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.parallel.ParallelJoin.JoinSubscriptionBase
        public void onNext(JoinInnerSubscriber joinInnerSubscriber, Object obj) {
            if (get() == 0 && compareAndSet(0, 1)) {
                if (this.requested.get() != 0) {
                    this.downstream.onNext(obj);
                    if (this.requested.get() != Long.MAX_VALUE) {
                        this.requested.decrementAndGet();
                    }
                    joinInnerSubscriber.request(1L);
                } else if (!joinInnerSubscriber.getQueue().offer(obj)) {
                    joinInnerSubscriber.cancel();
                    this.errors.tryAddThrowableOrReport(new QueueOverflowException());
                    this.done.decrementAndGet();
                    drainLoop();
                    return;
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else {
                if (!joinInnerSubscriber.getQueue().offer(obj)) {
                    joinInnerSubscriber.cancel();
                    this.errors.tryAddThrowableOrReport(new QueueOverflowException());
                    this.done.decrementAndGet();
                }
                if (getAndIncrement() != 0) {
                    return;
                }
            }
            drainLoop();
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        JoinSubscriptionBase joinSubscriptionDelayError = this.delayErrors ? new JoinSubscriptionDelayError(subscriber, this.source.parallelism(), this.prefetch) : new JoinSubscription(subscriber, this.source.parallelism(), this.prefetch);
        subscriber.onSubscribe(joinSubscriptionDelayError);
        this.source.subscribe(joinSubscriptionDelayError.subscribers);
    }
}
