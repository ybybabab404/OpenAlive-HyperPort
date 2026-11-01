package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableThrottleLatest<T> extends AbstractFlowableWithUpstream<T, T> {
    public final boolean emitLast;
    public final Consumer onDropped;
    public final Scheduler scheduler;
    public final long timeout;
    public final TimeUnit unit;

    public static final class ThrottleLatestSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
        public volatile boolean cancelled;
        public volatile boolean done;
        public final Subscriber downstream;
        public final boolean emitLast;
        public long emitted;
        public Throwable error;
        public final Consumer onDropped;
        public final long timeout;
        public volatile boolean timerFired;
        public boolean timerRunning;
        public final TimeUnit unit;
        public Subscription upstream;
        public final Scheduler.Worker worker;
        public final AtomicReference latest = new AtomicReference();
        public final AtomicLong requested = new AtomicLong();

        public ThrottleLatestSubscriber(Subscriber subscriber, long j, TimeUnit timeUnit, Scheduler.Worker worker, boolean z, Consumer consumer) {
            this.downstream = subscriber;
            this.timeout = j;
            this.unit = timeUnit;
            this.worker = worker;
            this.emitLast = z;
            this.onDropped = consumer;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.upstream.cancel();
            this.worker.dispose();
            if (getAndIncrement() == 0) {
                clear();
            }
        }

        public void clear() {
            if (this.onDropped == null) {
                this.latest.lazySet(null);
                return;
            }
            Object andSet = this.latest.getAndSet(null);
            if (andSet != null) {
                try {
                    this.onDropped.accept(andSet);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            }
        }

        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            AtomicReference atomicReference = this.latest;
            AtomicLong atomicLong = this.requested;
            Subscriber subscriber = this.downstream;
            int iAddAndGet = 1;
            while (!this.cancelled) {
                boolean z = this.done;
                Throwable compositeException = this.error;
                if (z && compositeException != null) {
                    if (this.onDropped != null) {
                        Object andSet = atomicReference.getAndSet(null);
                        if (andSet != null) {
                            try {
                                this.onDropped.accept(andSet);
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                compositeException = new CompositeException(compositeException, th);
                            }
                        }
                    } else {
                        atomicReference.lazySet(null);
                    }
                    subscriber.onError(compositeException);
                    this.worker.dispose();
                    return;
                }
                boolean z2 = atomicReference.get() == null;
                if (z) {
                    if (z2) {
                        subscriber.onComplete();
                    } else {
                        Object andSet2 = atomicReference.getAndSet(null);
                        if (this.emitLast) {
                            long j = this.emitted;
                            if (j != atomicLong.get()) {
                                this.emitted = j + 1;
                                subscriber.onNext(andSet2);
                                subscriber.onComplete();
                            } else {
                                tryDropAndSignalMBE(andSet2);
                            }
                        } else {
                            Consumer consumer = this.onDropped;
                            if (consumer != null) {
                                try {
                                    consumer.accept(andSet2);
                                } catch (Throwable th2) {
                                    Exceptions.throwIfFatal(th2);
                                    subscriber.onError(th2);
                                    this.worker.dispose();
                                    return;
                                }
                            }
                            subscriber.onComplete();
                        }
                    }
                    this.worker.dispose();
                    return;
                }
                if (z2) {
                    if (this.timerFired) {
                        this.timerRunning = false;
                        this.timerFired = false;
                    }
                } else if (!this.timerRunning || this.timerFired) {
                    Object andSet3 = atomicReference.getAndSet(null);
                    long j2 = this.emitted;
                    if (j2 == atomicLong.get()) {
                        this.upstream.cancel();
                        tryDropAndSignalMBE(andSet3);
                        this.worker.dispose();
                        return;
                    } else {
                        subscriber.onNext(andSet3);
                        this.emitted = j2 + 1;
                        this.timerFired = false;
                        this.timerRunning = true;
                        this.worker.schedule(this, this.timeout, this.unit);
                    }
                }
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
            clear();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            Object andSet = this.latest.getAndSet(obj);
            Consumer consumer = this.onDropped;
            if (consumer != null && andSet != null) {
                try {
                    consumer.accept(andSet);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.upstream.cancel();
                    this.error = th;
                    this.done = true;
                }
            }
            drain();
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.timerFired = true;
            drain();
        }

        public void tryDropAndSignalMBE(Object obj) {
            Throwable thCreateDefault = MissingBackpressureException.createDefault();
            Consumer consumer = this.onDropped;
            if (consumer != null) {
                try {
                    consumer.accept(obj);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    thCreateDefault = new CompositeException(thCreateDefault, th);
                }
            }
            this.downstream.onError(thCreateDefault);
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        this.source.subscribe((FlowableSubscriber) new ThrottleLatestSubscriber(subscriber, this.timeout, this.unit, this.scheduler.createWorker(), this.emitLast, this.onDropped));
    }
}
