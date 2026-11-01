package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes2.dex */
public final class ParallelCollector<T, A, R> extends Flowable<R> {
    public final Collector collector;
    public final ParallelFlowable source;

    public static final class ParallelCollectorInnerSubscriber<T, A, R> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
        public final BiConsumer accumulator;
        public final BinaryOperator combiner;
        public Object container;
        public boolean done;
        public final ParallelCollectorSubscriber parent;

        public ParallelCollectorInnerSubscriber(ParallelCollectorSubscriber parallelCollectorSubscriber, Object obj, BiConsumer biConsumer, BinaryOperator binaryOperator) {
            this.parent = parallelCollectorSubscriber;
            this.accumulator = biConsumer;
            this.combiner = binaryOperator;
            this.container = obj;
        }

        public void cancel() {
            SubscriptionHelper.cancel(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            Object obj = this.container;
            this.container = null;
            this.done = true;
            this.parent.innerComplete(obj, this.combiner);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.container = null;
            this.done = true;
            this.parent.innerError(th);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (this.done) {
                return;
            }
            try {
                this.accumulator.accept(this.container, obj);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                get().cancel();
                onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
        }
    }

    public static final class ParallelCollectorSubscriber<T, A, R> extends DeferredScalarSubscription<R> {
        public final AtomicReference current;
        public final AtomicThrowable error;
        public final Function finisher;
        public final AtomicInteger remaining;
        public final ParallelCollectorInnerSubscriber[] subscribers;

        public ParallelCollectorSubscriber(Subscriber subscriber, int i, Collector collector) {
            super(subscriber);
            this.current = new AtomicReference();
            this.remaining = new AtomicInteger();
            this.error = new AtomicThrowable();
            this.finisher = collector.finisher();
            ParallelCollectorInnerSubscriber[] parallelCollectorInnerSubscriberArr = new ParallelCollectorInnerSubscriber[i];
            for (int i2 = 0; i2 < i; i2++) {
                parallelCollectorInnerSubscriberArr[i2] = new ParallelCollectorInnerSubscriber(this, collector.supplier().get(), collector.accumulator(), collector.combiner());
            }
            this.subscribers = parallelCollectorInnerSubscriberArr;
            this.remaining.lazySet(i);
        }

        public SlotPair addValue(Object obj) {
            SlotPair slotPair;
            int iTryAcquireSlot;
            while (true) {
                slotPair = (SlotPair) this.current.get();
                if (slotPair == null) {
                    slotPair = new SlotPair();
                    if (!this.current.compareAndSet(null, slotPair)) {
                        continue;
                    }
                }
                iTryAcquireSlot = slotPair.tryAcquireSlot();
                if (iTryAcquireSlot >= 0) {
                    break;
                }
                this.current.compareAndSet(slotPair, null);
            }
            if (iTryAcquireSlot == 0) {
                slotPair.first = obj;
            } else {
                slotPair.second = obj;
            }
            if (!slotPair.releaseSlot()) {
                return null;
            }
            this.current.compareAndSet(slotPair, null);
            return slotPair;
        }

        @Override // io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription, org.reactivestreams.Subscription
        public void cancel() {
            for (ParallelCollectorInnerSubscriber parallelCollectorInnerSubscriber : this.subscribers) {
                parallelCollectorInnerSubscriber.cancel();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void innerComplete(Object obj, BinaryOperator binaryOperator) {
            while (true) {
                SlotPair slotPairAddValue = addValue(obj);
                if (slotPairAddValue == null) {
                    break;
                }
                try {
                    obj = binaryOperator.apply(slotPairAddValue.first, slotPairAddValue.second);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    innerError(th);
                    return;
                }
            }
            if (this.remaining.decrementAndGet() == 0) {
                SlotPair slotPair = (SlotPair) this.current.get();
                this.current.lazySet(null);
                try {
                    Object objApply = this.finisher.apply(slotPair.first);
                    Objects.requireNonNull(objApply, "The finisher returned a null value");
                    complete(objApply);
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    innerError(th2);
                }
            }
        }

        public void innerError(Throwable th) {
            if (this.error.compareAndSet(null, th)) {
                cancel();
                this.downstream.onError(th);
            } else if (th != this.error.get()) {
                RxJavaPlugins.onError(th);
            }
        }
    }

    public static final class SlotPair<T> extends AtomicInteger {
        public Object first;
        public final AtomicInteger releaseIndex = new AtomicInteger();
        public Object second;

        public boolean releaseSlot() {
            return this.releaseIndex.incrementAndGet() == 2;
        }

        public int tryAcquireSlot() {
            int i;
            do {
                i = get();
                if (i >= 2) {
                    return -1;
                }
            } while (!compareAndSet(i, i + 1));
            return i;
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        try {
            ParallelCollectorSubscriber parallelCollectorSubscriber = new ParallelCollectorSubscriber(subscriber, this.source.parallelism(), this.collector);
            subscriber.onSubscribe(parallelCollectorSubscriber);
            this.source.subscribe(parallelCollectorSubscriber.subscribers);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, subscriber);
        }
    }
}
