package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.operators.SimplePlainQueue;
import io.reactivex.rxjava3.operators.SpscArrayQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableMergeWithSingle<T> extends AbstractFlowableWithUpstream<T, T> {
    public final SingleSource other;

    public static final class MergeWithObserver<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        public volatile boolean cancelled;
        public int consumed;
        public final Subscriber downstream;
        public long emitted;
        public final int limit;
        public volatile boolean mainDone;
        public volatile int otherState;
        public final int prefetch;
        public volatile SimplePlainQueue queue;
        public Object singleItem;
        public final AtomicReference mainSubscription = new AtomicReference();
        public final OtherObserver otherObserver = new OtherObserver(this);
        public final AtomicThrowable errors = new AtomicThrowable();
        public final AtomicLong requested = new AtomicLong();

        public static final class OtherObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T> {
            public final MergeWithObserver parent;

            public OtherObserver(MergeWithObserver mergeWithObserver) {
                this.parent = mergeWithObserver;
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onError(Throwable th) {
                this.parent.otherError(th);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSuccess(Object obj) {
                this.parent.otherSuccess(obj);
            }
        }

        public MergeWithObserver(Subscriber subscriber) {
            this.downstream = subscriber;
            int iBufferSize = Flowable.bufferSize();
            this.prefetch = iBufferSize;
            this.limit = iBufferSize - (iBufferSize >> 2);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            SubscriptionHelper.cancel(this.mainSubscription);
            DisposableHelper.dispose(this.otherObserver);
            this.errors.tryTerminateAndReport();
            if (getAndIncrement() == 0) {
                this.queue = null;
                this.singleItem = null;
            }
        }

        public void drain() {
            if (getAndIncrement() == 0) {
                drainLoop();
            }
        }

        public void drainLoop() {
            Subscriber subscriber = this.downstream;
            long j = this.emitted;
            int i = this.consumed;
            int i2 = this.limit;
            int i3 = 1;
            int iAddAndGet = 1;
            while (true) {
                long j2 = this.requested.get();
                while (j != j2) {
                    if (this.cancelled) {
                        this.singleItem = null;
                        this.queue = null;
                        return;
                    }
                    if (this.errors.get() != null) {
                        this.singleItem = null;
                        this.queue = null;
                        this.errors.tryTerminateConsumer(this.downstream);
                        return;
                    }
                    int i4 = this.otherState;
                    if (i4 == i3) {
                        Object obj = this.singleItem;
                        this.singleItem = null;
                        this.otherState = 2;
                        subscriber.onNext(obj);
                        j++;
                    } else {
                        boolean z = this.mainDone;
                        SimplePlainQueue simplePlainQueue = this.queue;
                        Object objPoll = simplePlainQueue != null ? simplePlainQueue.poll() : null;
                        boolean z2 = objPoll == null;
                        if (z && z2 && i4 == 2) {
                            this.queue = null;
                            subscriber.onComplete();
                            return;
                        } else {
                            if (z2) {
                                break;
                            }
                            subscriber.onNext(objPoll);
                            j++;
                            i++;
                            if (i == i2) {
                                ((Subscription) this.mainSubscription.get()).request(i2);
                                i = 0;
                            }
                            i3 = 1;
                        }
                    }
                }
                if (j == j2) {
                    if (this.cancelled) {
                        this.singleItem = null;
                        this.queue = null;
                        return;
                    }
                    if (this.errors.get() != null) {
                        this.singleItem = null;
                        this.queue = null;
                        this.errors.tryTerminateConsumer(this.downstream);
                        return;
                    }
                    boolean z3 = this.mainDone;
                    SimplePlainQueue simplePlainQueue2 = this.queue;
                    boolean z4 = simplePlainQueue2 == null || simplePlainQueue2.isEmpty();
                    if (z3 && z4 && this.otherState == 2) {
                        this.queue = null;
                        subscriber.onComplete();
                        return;
                    }
                }
                this.emitted = j;
                this.consumed = i;
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                } else {
                    i3 = 1;
                }
            }
        }

        public SimplePlainQueue getOrCreateQueue() {
            SimplePlainQueue simplePlainQueue = this.queue;
            if (simplePlainQueue != null) {
                return simplePlainQueue;
            }
            SpscArrayQueue spscArrayQueue = new SpscArrayQueue(Flowable.bufferSize());
            this.queue = spscArrayQueue;
            return spscArrayQueue;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.mainDone = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                DisposableHelper.dispose(this.otherObserver);
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (compareAndSet(0, 1)) {
                long j = this.emitted;
                if (this.requested.get() != j) {
                    SimplePlainQueue simplePlainQueue = this.queue;
                    if (simplePlainQueue == null || simplePlainQueue.isEmpty()) {
                        this.emitted = j + 1;
                        this.downstream.onNext(obj);
                        int i = this.consumed + 1;
                        if (i == this.limit) {
                            this.consumed = 0;
                            ((Subscription) this.mainSubscription.get()).request(i);
                        } else {
                            this.consumed = i;
                        }
                    } else {
                        simplePlainQueue.offer(obj);
                    }
                } else {
                    getOrCreateQueue().offer(obj);
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else {
                getOrCreateQueue().offer(obj);
                if (getAndIncrement() != 0) {
                    return;
                }
            }
            drainLoop();
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this.mainSubscription, subscription, this.prefetch);
        }

        public void otherError(Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                SubscriptionHelper.cancel(this.mainSubscription);
                drain();
            }
        }

        public void otherSuccess(Object obj) {
            if (compareAndSet(0, 1)) {
                long j = this.emitted;
                if (this.requested.get() != j) {
                    this.emitted = j + 1;
                    this.downstream.onNext(obj);
                    this.otherState = 2;
                } else {
                    this.singleItem = obj;
                    this.otherState = 1;
                    if (decrementAndGet() == 0) {
                        return;
                    }
                }
            } else {
                this.singleItem = obj;
                this.otherState = 1;
                if (getAndIncrement() != 0) {
                    return;
                }
            }
            drainLoop();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            BackpressureHelper.add(this.requested, j);
            drain();
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        MergeWithObserver mergeWithObserver = new MergeWithObserver(subscriber);
        subscriber.onSubscribe(mergeWithObserver);
        this.source.subscribe((FlowableSubscriber) mergeWithObserver);
        this.other.subscribe(mergeWithObserver.otherObserver);
    }
}
