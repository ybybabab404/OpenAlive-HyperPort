package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.QueueOverflowException;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.operators.QueueSubscription;
import io.reactivex.rxjava3.operators.SimpleQueue;
import io.reactivex.rxjava3.operators.SpscArrayQueue;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableFlatMapStream<T, R> extends Flowable<R> {
    public final Function mapper;
    public final int prefetch;
    public final Flowable source;

    public static final class FlatMapStreamSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        public volatile boolean cancelled;
        public int consumed;
        public AutoCloseable currentCloseable;
        public Iterator currentIterator;
        public final Subscriber downstream;
        public long emitted;
        public final Function mapper;
        public final int prefetch;
        public SimpleQueue queue;
        public int sourceMode;
        public Subscription upstream;
        public volatile boolean upstreamDone;
        public final AtomicLong requested = new AtomicLong();
        public final AtomicThrowable error = new AtomicThrowable();

        public FlatMapStreamSubscriber(Subscriber subscriber, Function function, int i) {
            this.downstream = subscriber;
            this.mapper = function;
            this.prefetch = i;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.upstream.cancel();
            drain();
        }

        public void clearCurrentRethrowCloseError() throws Exception {
            this.currentIterator = null;
            AutoCloseable autoCloseable = this.currentCloseable;
            this.currentCloseable = null;
            if (autoCloseable != null) {
                autoCloseable.close();
            }
        }

        public void clearCurrentSuppressCloseError() {
            try {
                clearCurrentRethrowCloseError();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r12v0 */
        /* JADX WARN: Type inference failed for: r12v1, types: [boolean, int] */
        /* JADX WARN: Type inference failed for: r12v2 */
        /* JADX WARN: Type inference failed for: r16v0 */
        /* JADX WARN: Type inference failed for: r16v1 */
        /* JADX WARN: Type inference failed for: r16v2 */
        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            Subscriber subscriber = this.downstream;
            SimpleQueue simpleQueue = this.queue;
            AtomicThrowable atomicThrowable = this.error;
            Iterator<T> it = this.currentIterator;
            long j = this.requested.get();
            long j2 = this.emitted;
            int i = this.prefetch;
            int i2 = i - (i >> 2);
            int i3 = 0;
            ?? r12 = 1;
            boolean z = this.sourceMode != 1;
            long j3 = j2;
            int iAddAndGet = 1;
            long j4 = j;
            Iterator<T> it2 = it;
            while (true) {
                if (this.cancelled) {
                    simpleQueue.clear();
                    clearCurrentSuppressCloseError();
                } else {
                    boolean z2 = this.upstreamDone;
                    if (atomicThrowable.get() != null) {
                        subscriber.onError(atomicThrowable.get());
                        this.cancelled = r12;
                    } else if (it2 == null) {
                        try {
                            Object objPoll = simpleQueue.poll();
                            ?? r16 = objPoll == null ? r12 : i3;
                            if (z2 && r16 != 0) {
                                subscriber.onComplete();
                                this.cancelled = r12;
                            } else if (r16 == 0) {
                                if (z) {
                                    int i4 = this.consumed + r12;
                                    this.consumed = i4;
                                    if (i4 == i2) {
                                        this.consumed = i3;
                                        this.upstream.request(i2);
                                    }
                                }
                                try {
                                    Object objApply = this.mapper.apply(objPoll);
                                    Objects.requireNonNull(objApply, "The mapper returned a null Stream");
                                    Stream stream = (Stream) objApply;
                                    it2 = stream.iterator();
                                    if (it2.hasNext()) {
                                        this.currentIterator = it2;
                                        this.currentCloseable = stream;
                                    } else {
                                        it2 = null;
                                    }
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    trySignalError(subscriber, th);
                                }
                            }
                            if (it2 == null && j3 != j4) {
                                try {
                                    T next = it2.next();
                                    Objects.requireNonNull(next, "The Stream.Iterator returned a null value");
                                    if (!this.cancelled) {
                                        subscriber.onNext(next);
                                        j3++;
                                        if (!this.cancelled) {
                                            try {
                                                if (!it2.hasNext()) {
                                                    try {
                                                        clearCurrentRethrowCloseError();
                                                        it2 = null;
                                                    } catch (Throwable th2) {
                                                        th = th2;
                                                        it2 = null;
                                                        Exceptions.throwIfFatal(th);
                                                        trySignalError(subscriber, th);
                                                    }
                                                }
                                            } catch (Throwable th3) {
                                                th = th3;
                                            }
                                        }
                                    }
                                } catch (Throwable th4) {
                                    Exceptions.throwIfFatal(th4);
                                    trySignalError(subscriber, th4);
                                }
                            }
                        } catch (Throwable th5) {
                            Exceptions.throwIfFatal(th5);
                            trySignalError(subscriber, th5);
                        }
                    } else if (it2 == null) {
                    }
                    i3 = 0;
                    r12 = 1;
                }
                this.emitted = j3;
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
                j4 = this.requested.get();
                i3 = 0;
                r12 = 1;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.upstreamDone = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!this.error.compareAndSet(null, th)) {
                RxJavaPlugins.onError(th);
            } else {
                this.upstreamDone = true;
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (this.sourceMode == 2 || this.queue.offer(obj)) {
                drain();
            } else {
                this.upstream.cancel();
                onError(new QueueOverflowException());
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueSubscription;
                        this.upstreamDone = true;
                        this.downstream.onSubscribe(this);
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueSubscription;
                        this.downstream.onSubscribe(this);
                        subscription.request(this.prefetch);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.prefetch);
                this.downstream.onSubscribe(this);
                subscription.request(this.prefetch);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                drain();
            }
        }

        public void trySignalError(Subscriber subscriber, Throwable th) {
            if (!this.error.compareAndSet(null, th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.upstream.cancel();
            this.cancelled = true;
            subscriber.onError(th);
        }
    }

    public static Subscriber subscribe(Subscriber subscriber, Function function, int i) {
        return new FlatMapStreamSubscriber(subscriber, function, i);
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        Stream stream;
        Publisher publisher = this.source;
        if (!(publisher instanceof Supplier)) {
            publisher.subscribe(subscribe(subscriber, this.mapper, this.prefetch));
            return;
        }
        try {
            Object obj = ((Supplier) publisher).get();
            if (obj != null) {
                Object objApply = this.mapper.apply(obj);
                Objects.requireNonNull(objApply, "The mapper returned a null Stream");
                stream = (Stream) objApply;
            } else {
                stream = null;
            }
            if (stream != null) {
                FlowableFromStream.subscribeStream(subscriber, stream);
            } else {
                EmptySubscription.complete(subscriber);
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, subscriber);
        }
    }
}
