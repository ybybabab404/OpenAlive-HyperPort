package io.reactivex.rxjava3.processors;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.internal.functions.ObjectHelper;
import io.reactivex.rxjava3.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes2.dex */
public final class UnicastProcessor<T> extends FlowableProcessor<T> {
    public volatile boolean cancelled;
    public final boolean delayError;
    public volatile boolean done;
    public boolean enableOperatorFusion;
    public Throwable error;
    public final AtomicReference onTerminate;
    public final SpscLinkedArrayQueue queue;
    public final AtomicReference downstream = new AtomicReference();
    public final AtomicBoolean once = new AtomicBoolean();
    public final BasicIntQueueSubscription wip = new UnicastQueueSubscription();
    public final AtomicLong requested = new AtomicLong();

    public final class UnicastQueueSubscription extends BasicIntQueueSubscription<T> {
        public UnicastQueueSubscription() {
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (UnicastProcessor.this.cancelled) {
                return;
            }
            UnicastProcessor.this.cancelled = true;
            UnicastProcessor.this.doTerminate();
            UnicastProcessor.this.downstream.lazySet(null);
            if (UnicastProcessor.this.wip.getAndIncrement() == 0) {
                UnicastProcessor.this.downstream.lazySet(null);
                UnicastProcessor unicastProcessor = UnicastProcessor.this;
                if (unicastProcessor.enableOperatorFusion) {
                    return;
                }
                unicastProcessor.queue.clear();
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public void clear() {
            UnicastProcessor.this.queue.clear();
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean isEmpty() {
            return UnicastProcessor.this.queue.isEmpty();
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public Object poll() {
            return UnicastProcessor.this.queue.poll();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(UnicastProcessor.this.requested, j);
                UnicastProcessor.this.drain();
            }
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            UnicastProcessor.this.enableOperatorFusion = true;
            return 2;
        }
    }

    public UnicastProcessor(int i, Runnable runnable, boolean z) {
        this.queue = new SpscLinkedArrayQueue(i);
        this.onTerminate = new AtomicReference(runnable);
        this.delayError = z;
    }

    public static UnicastProcessor create() {
        return new UnicastProcessor(Flowable.bufferSize(), null, true);
    }

    public static UnicastProcessor create(int i) {
        ObjectHelper.verifyPositive(i, "capacityHint");
        return new UnicastProcessor(i, null, true);
    }

    public static UnicastProcessor create(int i, Runnable runnable) {
        return create(i, runnable, true);
    }

    public static UnicastProcessor create(int i, Runnable runnable, boolean z) {
        Objects.requireNonNull(runnable, "onTerminate");
        ObjectHelper.verifyPositive(i, "capacityHint");
        return new UnicastProcessor(i, runnable, z);
    }

    public boolean checkTerminated(boolean z, boolean z2, boolean z3, Subscriber subscriber, SpscLinkedArrayQueue spscLinkedArrayQueue) {
        if (this.cancelled) {
            spscLinkedArrayQueue.clear();
            this.downstream.lazySet(null);
            return true;
        }
        if (!z2) {
            return false;
        }
        if (z && this.error != null) {
            spscLinkedArrayQueue.clear();
            this.downstream.lazySet(null);
            subscriber.onError(this.error);
            return true;
        }
        if (!z3) {
            return false;
        }
        Throwable th = this.error;
        this.downstream.lazySet(null);
        if (th != null) {
            subscriber.onError(th);
        } else {
            subscriber.onComplete();
        }
        return true;
    }

    public void doTerminate() {
        Runnable runnable = (Runnable) this.onTerminate.getAndSet(null);
        if (runnable != null) {
            runnable.run();
        }
    }

    public void drain() {
        if (this.wip.getAndIncrement() != 0) {
            return;
        }
        Subscriber subscriber = (Subscriber) this.downstream.get();
        int iAddAndGet = 1;
        while (subscriber == null) {
            iAddAndGet = this.wip.addAndGet(-iAddAndGet);
            if (iAddAndGet == 0) {
                return;
            } else {
                subscriber = (Subscriber) this.downstream.get();
            }
        }
        if (this.enableOperatorFusion) {
            drainFused(subscriber);
        } else {
            drainRegular(subscriber);
        }
    }

    public void drainFused(Subscriber subscriber) {
        SpscLinkedArrayQueue spscLinkedArrayQueue = this.queue;
        boolean z = this.delayError;
        int iAddAndGet = 1;
        while (!this.cancelled) {
            boolean z2 = this.done;
            if (!z && z2 && this.error != null) {
                spscLinkedArrayQueue.clear();
                this.downstream.lazySet(null);
                subscriber.onError(this.error);
                return;
            }
            subscriber.onNext(null);
            if (z2) {
                this.downstream.lazySet(null);
                Throwable th = this.error;
                if (th != null) {
                    subscriber.onError(th);
                    return;
                } else {
                    subscriber.onComplete();
                    return;
                }
            }
            iAddAndGet = this.wip.addAndGet(-iAddAndGet);
            if (iAddAndGet == 0) {
                return;
            }
        }
        this.downstream.lazySet(null);
    }

    public void drainRegular(Subscriber subscriber) {
        SpscLinkedArrayQueue spscLinkedArrayQueue = this.queue;
        boolean z = !this.delayError;
        int iAddAndGet = 1;
        do {
            long j = this.requested.get();
            long j2 = 0;
            while (j != j2) {
                boolean z2 = this.done;
                Object objPoll = spscLinkedArrayQueue.poll();
                boolean z3 = objPoll == null;
                if (checkTerminated(z, z2, z3, subscriber, spscLinkedArrayQueue)) {
                    return;
                }
                if (z3) {
                    break;
                }
                subscriber.onNext(objPoll);
                j2++;
            }
            if (j == j2 && checkTerminated(z, this.done, spscLinkedArrayQueue.isEmpty(), subscriber, spscLinkedArrayQueue)) {
                return;
            }
            if (j2 != 0 && j != Long.MAX_VALUE) {
                this.requested.addAndGet(-j2);
            }
            iAddAndGet = this.wip.addAndGet(-iAddAndGet);
        } while (iAddAndGet != 0);
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (this.done || this.cancelled) {
            return;
        }
        this.done = true;
        doTerminate();
        drain();
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        ExceptionHelper.nullCheck(th, "onError called with a null Throwable.");
        if (this.done || this.cancelled) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.error = th;
        this.done = true;
        doTerminate();
        drain();
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(Object obj) {
        ExceptionHelper.nullCheck(obj, "onNext called with a null value.");
        if (this.done || this.cancelled) {
            return;
        }
        this.queue.offer(obj);
        drain();
    }

    @Override // org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        if (this.done || this.cancelled) {
            subscription.cancel();
        } else {
            subscription.request(Long.MAX_VALUE);
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        if (this.once.get() || !this.once.compareAndSet(false, true)) {
            EmptySubscription.error(new IllegalStateException("This processor allows only a single Subscriber"), subscriber);
            return;
        }
        subscriber.onSubscribe(this.wip);
        this.downstream.set(subscriber);
        if (this.cancelled) {
            this.downstream.lazySet(null);
        } else {
            drain();
        }
    }
}
