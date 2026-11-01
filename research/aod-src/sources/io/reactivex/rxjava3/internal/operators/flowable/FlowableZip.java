package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.operators.QueueSubscription;
import io.reactivex.rxjava3.operators.SimpleQueue;
import io.reactivex.rxjava3.operators.SpscArrayQueue;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableZip<T, R> extends Flowable<R> {
    public final int bufferSize;
    public final boolean delayError;
    public final Publisher[] sources;
    public final Iterable sourcesIterable;
    public final Function zipper;

    public static final class ZipCoordinator<T, R> extends AtomicInteger implements Subscription {
        public volatile boolean cancelled;
        public final Object[] current;
        public final boolean delayErrors;
        public final Subscriber downstream;
        public final AtomicThrowable errors;
        public final AtomicLong requested;
        public final ZipSubscriber[] subscribers;
        public final Function zipper;

        public ZipCoordinator(Subscriber subscriber, Function function, int i, int i2, boolean z) {
            this.downstream = subscriber;
            this.zipper = function;
            this.delayErrors = z;
            ZipSubscriber[] zipSubscriberArr = new ZipSubscriber[i];
            for (int i3 = 0; i3 < i; i3++) {
                zipSubscriberArr[i3] = new ZipSubscriber(this, i2);
            }
            this.current = new Object[i];
            this.subscribers = zipSubscriberArr;
            this.requested = new AtomicLong();
            this.errors = new AtomicThrowable();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            cancelAll();
        }

        public void cancelAll() {
            for (ZipSubscriber zipSubscriber : this.subscribers) {
                zipSubscriber.cancel();
            }
        }

        public void drain() {
            long j;
            Object objPoll;
            Object objPoll2;
            if (getAndIncrement() != 0) {
                return;
            }
            Subscriber subscriber = this.downstream;
            ZipSubscriber[] zipSubscriberArr = this.subscribers;
            int length = zipSubscriberArr.length;
            Object[] objArr = this.current;
            int iAddAndGet = 1;
            do {
                long j2 = this.requested.get();
                long j3 = 0;
                while (true) {
                    if (j2 == j3) {
                        j = 0;
                        break;
                    }
                    if (this.cancelled) {
                        return;
                    }
                    if (!this.delayErrors && this.errors.get() != null) {
                        cancelAll();
                        this.errors.tryTerminateConsumer(subscriber);
                        return;
                    }
                    boolean z = false;
                    for (int i = 0; i < length; i++) {
                        ZipSubscriber zipSubscriber = zipSubscriberArr[i];
                        if (objArr[i] == null) {
                            boolean z2 = zipSubscriber.done;
                            SimpleQueue simpleQueue = zipSubscriber.queue;
                            if (simpleQueue != null) {
                                try {
                                    objPoll2 = simpleQueue.poll();
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    this.errors.tryAddThrowableOrReport(th);
                                    if (!this.delayErrors) {
                                        cancelAll();
                                        this.errors.tryTerminateConsumer(subscriber);
                                        return;
                                    } else {
                                        objPoll2 = null;
                                        z2 = true;
                                    }
                                }
                            } else {
                                objPoll2 = null;
                            }
                            boolean z3 = objPoll2 == null;
                            if (z2 && z3) {
                                cancelAll();
                                this.errors.tryTerminateConsumer(subscriber);
                                return;
                            } else if (z3) {
                                z = true;
                            } else {
                                objArr[i] = objPoll2;
                            }
                        }
                    }
                    j = 0;
                    if (z) {
                        break;
                    }
                    try {
                        Object objApply = this.zipper.apply(objArr.clone());
                        Objects.requireNonNull(objApply, "The zipper returned a null value");
                        subscriber.onNext(objApply);
                        j3++;
                        Arrays.fill(objArr, (Object) null);
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        cancelAll();
                        this.errors.tryAddThrowableOrReport(th2);
                        this.errors.tryTerminateConsumer(subscriber);
                        return;
                    }
                }
                if (j2 == j3) {
                    if (this.cancelled) {
                        return;
                    }
                    if (!this.delayErrors && this.errors.get() != null) {
                        cancelAll();
                        this.errors.tryTerminateConsumer(subscriber);
                        return;
                    }
                    for (int i2 = 0; i2 < length; i2++) {
                        ZipSubscriber zipSubscriber2 = zipSubscriberArr[i2];
                        if (objArr[i2] == null) {
                            boolean z4 = zipSubscriber2.done;
                            SimpleQueue simpleQueue2 = zipSubscriber2.queue;
                            if (simpleQueue2 != null) {
                                try {
                                    objPoll = simpleQueue2.poll();
                                } catch (Throwable th3) {
                                    Exceptions.throwIfFatal(th3);
                                    this.errors.tryAddThrowableOrReport(th3);
                                    if (!this.delayErrors) {
                                        cancelAll();
                                        this.errors.tryTerminateConsumer(subscriber);
                                        return;
                                    } else {
                                        objPoll = null;
                                        z4 = true;
                                    }
                                }
                            } else {
                                objPoll = null;
                            }
                            boolean z5 = objPoll == null;
                            if (z4 && z5) {
                                cancelAll();
                                this.errors.tryTerminateConsumer(subscriber);
                                return;
                            } else if (!z5) {
                                objArr[i2] = objPoll;
                            }
                        }
                    }
                }
                if (j3 != j) {
                    for (ZipSubscriber zipSubscriber3 : zipSubscriberArr) {
                        zipSubscriber3.request(j3);
                    }
                    if (j2 != Long.MAX_VALUE) {
                        this.requested.addAndGet(-j3);
                    }
                }
                iAddAndGet = addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        public void error(ZipSubscriber zipSubscriber, Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                zipSubscriber.done = true;
                drain();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                drain();
            }
        }

        public void subscribe(Publisher[] publisherArr, int i) {
            ZipSubscriber[] zipSubscriberArr = this.subscribers;
            for (int i2 = 0; i2 < i && !this.cancelled; i2++) {
                if (!this.delayErrors && this.errors.get() != null) {
                    return;
                }
                publisherArr[i2].subscribe(zipSubscriberArr[i2]);
            }
        }
    }

    public static final class ZipSubscriber<T, R> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Subscription {
        public volatile boolean done;
        public final int limit;
        public final ZipCoordinator parent;
        public final int prefetch;
        public long produced;
        public SimpleQueue queue;
        public int sourceMode;

        public ZipSubscriber(ZipCoordinator zipCoordinator, int i) {
            this.parent = zipCoordinator;
            this.prefetch = i;
            this.limit = i - (i >> 2);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.error(this, th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (this.sourceMode != 2) {
                this.queue.offer(obj);
            }
            this.parent.drain();
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.parent.drain();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueSubscription;
                        subscription.request(this.prefetch);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.prefetch);
                subscription.request(this.prefetch);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (this.sourceMode != 1) {
                long j2 = this.produced + j;
                if (j2 < this.limit) {
                    this.produced = j2;
                } else {
                    this.produced = 0L;
                    get().request(j2);
                }
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        int length;
        Publisher[] publisherArr = this.sources;
        if (publisherArr == null) {
            publisherArr = new Publisher[8];
            length = 0;
            for (Publisher publisher : this.sourcesIterable) {
                if (length == publisherArr.length) {
                    Publisher[] publisherArr2 = new Publisher[(length >> 2) + length];
                    System.arraycopy(publisherArr, 0, publisherArr2, 0, length);
                    publisherArr = publisherArr2;
                }
                publisherArr[length] = publisher;
                length++;
            }
        } else {
            length = publisherArr.length;
        }
        int i = length;
        if (i == 0) {
            EmptySubscription.complete(subscriber);
            return;
        }
        ZipCoordinator zipCoordinator = new ZipCoordinator(subscriber, this.zipper, i, this.bufferSize, this.delayError);
        subscriber.onSubscribe(zipCoordinator);
        zipCoordinator.subscribe(publisherArr, i);
    }
}
