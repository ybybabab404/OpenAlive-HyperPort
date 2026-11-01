package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes2.dex */
abstract class AbstractBackpressureThrottlingSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
    public volatile boolean cancelled;
    public volatile boolean done;
    public final Subscriber downstream;
    public Throwable error;
    public Subscription upstream;
    public final AtomicLong requested = new AtomicLong();
    public final AtomicReference current = new AtomicReference();

    public AbstractBackpressureThrottlingSubscriber(Subscriber subscriber) {
        this.downstream = subscriber;
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
        if (this.cancelled) {
            return;
        }
        this.cancelled = true;
        this.upstream.cancel();
        if (getAndIncrement() == 0) {
            this.current.lazySet(null);
        }
    }

    public boolean checkTerminated(boolean z, boolean z2, Subscriber subscriber, AtomicReference atomicReference) {
        if (this.cancelled) {
            atomicReference.lazySet(null);
            return true;
        }
        if (!z) {
            return false;
        }
        Throwable th = this.error;
        if (th != null) {
            atomicReference.lazySet(null);
            subscriber.onError(th);
            return true;
        }
        if (!z2) {
            return false;
        }
        subscriber.onComplete();
        return true;
    }

    public void drain() {
        if (getAndIncrement() != 0) {
            return;
        }
        Subscriber subscriber = this.downstream;
        AtomicLong atomicLong = this.requested;
        AtomicReference atomicReference = this.current;
        int iAddAndGet = 1;
        do {
            long j = 0;
            while (true) {
                if (j == atomicLong.get()) {
                    break;
                }
                boolean z = this.done;
                Object andSet = atomicReference.getAndSet(null);
                boolean z2 = andSet == null;
                if (checkTerminated(z, z2, subscriber, atomicReference)) {
                    return;
                }
                if (z2) {
                    break;
                }
                subscriber.onNext(andSet);
                j++;
            }
            if (j == atomicLong.get()) {
                if (checkTerminated(this.done, atomicReference.get() == null, subscriber, atomicReference)) {
                    return;
                }
            }
            if (j != 0) {
                BackpressureHelper.produced(atomicLong, j);
            }
            iAddAndGet = addAndGet(-iAddAndGet);
        } while (iAddAndGet != 0);
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
            drain();
        }
    }
}
