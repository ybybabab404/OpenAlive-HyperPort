package io.reactivex.rxjava3.internal.subscribers;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.QueueDrain;
import io.reactivex.rxjava3.internal.util.QueueDrainHelper;
import io.reactivex.rxjava3.operators.SimplePlainQueue;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public abstract class QueueDrainSubscriber<T, U, V> extends QueueDrainSubscriberPad4 implements FlowableSubscriber<T>, QueueDrain<U, V> {
    public volatile boolean cancelled;
    public volatile boolean done;
    public final Subscriber downstream;
    public Throwable error;
    public final SimplePlainQueue queue;

    public QueueDrainSubscriber(Subscriber subscriber, SimplePlainQueue simplePlainQueue) {
        this.downstream = subscriber;
        this.queue = simplePlainQueue;
    }

    public boolean accept(Subscriber subscriber, Object obj) {
        return false;
    }

    @Override // io.reactivex.rxjava3.internal.util.QueueDrain
    public final boolean cancelled() {
        return this.cancelled;
    }

    @Override // io.reactivex.rxjava3.internal.util.QueueDrain
    public final boolean done() {
        return this.done;
    }

    public final boolean enter() {
        return this.wip.getAndIncrement() == 0;
    }

    @Override // io.reactivex.rxjava3.internal.util.QueueDrain
    public final Throwable error() {
        return this.error;
    }

    public final boolean fastEnter() {
        return this.wip.get() == 0 && this.wip.compareAndSet(0, 1);
    }

    public final void fastPathEmitMax(Object obj, boolean z, Disposable disposable) {
        Subscriber subscriber = this.downstream;
        SimplePlainQueue simplePlainQueue = this.queue;
        if (fastEnter()) {
            long j = this.requested.get();
            if (j == 0) {
                disposable.dispose();
                subscriber.onError(MissingBackpressureException.createDefault());
                return;
            } else {
                if (accept(subscriber, obj) && j != Long.MAX_VALUE) {
                    produced(1L);
                }
                if (leave(-1) == 0) {
                    return;
                }
            }
        } else {
            simplePlainQueue.offer(obj);
            if (!enter()) {
                return;
            }
        }
        QueueDrainHelper.drainMaxLoop(simplePlainQueue, subscriber, z, disposable, this);
    }

    public final void fastPathOrderedEmitMax(Object obj, boolean z, Disposable disposable) {
        Subscriber subscriber = this.downstream;
        SimplePlainQueue simplePlainQueue = this.queue;
        if (fastEnter()) {
            long j = this.requested.get();
            if (j == 0) {
                this.cancelled = true;
                disposable.dispose();
                subscriber.onError(MissingBackpressureException.createDefault());
                return;
            } else if (simplePlainQueue.isEmpty()) {
                if (accept(subscriber, obj) && j != Long.MAX_VALUE) {
                    produced(1L);
                }
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                simplePlainQueue.offer(obj);
            }
        } else {
            simplePlainQueue.offer(obj);
            if (!enter()) {
                return;
            }
        }
        QueueDrainHelper.drainMaxLoop(simplePlainQueue, subscriber, z, disposable, this);
    }

    @Override // io.reactivex.rxjava3.internal.util.QueueDrain
    public final int leave(int i) {
        return this.wip.addAndGet(i);
    }

    @Override // io.reactivex.rxjava3.internal.util.QueueDrain
    public final long produced(long j) {
        return this.requested.addAndGet(-j);
    }

    @Override // io.reactivex.rxjava3.internal.util.QueueDrain
    public final long requested() {
        return this.requested.get();
    }

    public final void requested(long j) {
        if (SubscriptionHelper.validate(j)) {
            BackpressureHelper.add(this.requested, j);
        }
    }
}
