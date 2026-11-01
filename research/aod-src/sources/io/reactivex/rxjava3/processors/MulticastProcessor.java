package io.reactivex.rxjava3.processors;

import io.reactivex.rxjava3.annotations.BackpressureSupport;
import io.reactivex.rxjava3.annotations.SchedulerSupport;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.operators.QueueSubscription;
import io.reactivex.rxjava3.operators.SimpleQueue;
import io.reactivex.rxjava3.operators.SpscArrayQueue;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes2.dex */
@BackpressureSupport
@SchedulerSupport
public final class MulticastProcessor<T> extends FlowableProcessor<T> {
    public static final MulticastSubscription[] EMPTY = new MulticastSubscription[0];
    public static final MulticastSubscription[] TERMINATED = new MulticastSubscription[0];
    public final int bufferSize;
    public int consumed;
    public volatile boolean done;
    public volatile Throwable error;
    public int fusionMode;
    public final int limit;
    public volatile SimpleQueue queue;
    public final boolean refcount;
    public final AtomicReference subscribers;
    public final AtomicReference upstream;
    public final AtomicInteger wip;

    public static final class MulticastSubscription<T> extends AtomicLong implements Subscription {
        public final Subscriber downstream;
        public long emitted;
        public final MulticastProcessor parent;

        public MulticastSubscription(Subscriber subscriber, MulticastProcessor multicastProcessor) {
            this.downstream = subscriber;
            this.parent = multicastProcessor;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.parent.remove(this);
            }
        }

        public void onComplete() {
            if (get() != Long.MIN_VALUE) {
                this.downstream.onComplete();
            }
        }

        public void onError(Throwable th) {
            if (get() != Long.MIN_VALUE) {
                this.downstream.onError(th);
            }
        }

        public void onNext(Object obj) {
            if (get() != Long.MIN_VALUE) {
                this.emitted++;
                this.downstream.onNext(obj);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                long jAddCancel = BackpressureHelper.addCancel(this, j);
                if (jAddCancel == Long.MIN_VALUE || jAddCancel == Long.MAX_VALUE) {
                    return;
                }
                this.parent.drain();
            }
        }
    }

    public boolean add(MulticastSubscription multicastSubscription) {
        MulticastSubscription[] multicastSubscriptionArr;
        MulticastSubscription[] multicastSubscriptionArr2;
        do {
            multicastSubscriptionArr = (MulticastSubscription[]) this.subscribers.get();
            if (multicastSubscriptionArr == TERMINATED) {
                return false;
            }
            int length = multicastSubscriptionArr.length;
            multicastSubscriptionArr2 = new MulticastSubscription[length + 1];
            System.arraycopy(multicastSubscriptionArr, 0, multicastSubscriptionArr2, 0, length);
            multicastSubscriptionArr2[length] = multicastSubscription;
        } while (!this.subscribers.compareAndSet(multicastSubscriptionArr, multicastSubscriptionArr2));
        return true;
    }

    public void drain() {
        Object objPoll;
        if (this.wip.getAndIncrement() != 0) {
            return;
        }
        AtomicReference atomicReference = this.subscribers;
        int i = this.consumed;
        int i2 = this.limit;
        int i3 = this.fusionMode;
        int iAddAndGet = 1;
        while (true) {
            SimpleQueue simpleQueue = this.queue;
            if (simpleQueue != null) {
                MulticastSubscription[] multicastSubscriptionArr = (MulticastSubscription[]) atomicReference.get();
                if (multicastSubscriptionArr.length != 0) {
                    int i4 = 0;
                    long jMin = -1;
                    for (MulticastSubscription multicastSubscription : multicastSubscriptionArr) {
                        long j = multicastSubscription.get();
                        if (j >= 0) {
                            jMin = jMin == -1 ? j - multicastSubscription.emitted : Math.min(jMin, j - multicastSubscription.emitted);
                        }
                    }
                    int i5 = i;
                    while (true) {
                        if (jMin > 0) {
                            MulticastSubscription[] multicastSubscriptionArr2 = (MulticastSubscription[]) atomicReference.get();
                            if (multicastSubscriptionArr2 == TERMINATED) {
                                simpleQueue.clear();
                                return;
                            }
                            if (multicastSubscriptionArr == multicastSubscriptionArr2) {
                                boolean z = this.done;
                                try {
                                    objPoll = simpleQueue.poll();
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    SubscriptionHelper.cancel(this.upstream);
                                    this.error = th;
                                    this.done = true;
                                    objPoll = null;
                                    z = true;
                                }
                                boolean z2 = objPoll == null;
                                if (z && z2) {
                                    Throwable th2 = this.error;
                                    if (th2 != null) {
                                        MulticastSubscription[] multicastSubscriptionArr3 = (MulticastSubscription[]) atomicReference.getAndSet(TERMINATED);
                                        int length = multicastSubscriptionArr3.length;
                                        while (i4 < length) {
                                            multicastSubscriptionArr3[i4].onError(th2);
                                            i4++;
                                        }
                                        return;
                                    }
                                    MulticastSubscription[] multicastSubscriptionArr4 = (MulticastSubscription[]) atomicReference.getAndSet(TERMINATED);
                                    int length2 = multicastSubscriptionArr4.length;
                                    while (i4 < length2) {
                                        multicastSubscriptionArr4[i4].onComplete();
                                        i4++;
                                    }
                                    return;
                                }
                                if (!z2) {
                                    for (MulticastSubscription multicastSubscription2 : multicastSubscriptionArr) {
                                        multicastSubscription2.onNext(objPoll);
                                    }
                                    jMin--;
                                    if (i3 != 1 && (i5 = i5 + 1) == i2) {
                                        ((Subscription) this.upstream.get()).request(i2);
                                        i5 = 0;
                                    }
                                }
                            }
                            i = i5;
                        }
                        if (jMin == 0) {
                            MulticastSubscription[] multicastSubscriptionArr5 = (MulticastSubscription[]) atomicReference.get();
                            MulticastSubscription[] multicastSubscriptionArr6 = TERMINATED;
                            if (multicastSubscriptionArr5 == multicastSubscriptionArr6) {
                                simpleQueue.clear();
                                return;
                            }
                            if (multicastSubscriptionArr != multicastSubscriptionArr5) {
                                i = i5;
                            } else if (this.done && simpleQueue.isEmpty()) {
                                Throwable th3 = this.error;
                                if (th3 != null) {
                                    MulticastSubscription[] multicastSubscriptionArr7 = (MulticastSubscription[]) atomicReference.getAndSet(multicastSubscriptionArr6);
                                    int length3 = multicastSubscriptionArr7.length;
                                    while (i4 < length3) {
                                        multicastSubscriptionArr7[i4].onError(th3);
                                        i4++;
                                    }
                                    return;
                                }
                                MulticastSubscription[] multicastSubscriptionArr8 = (MulticastSubscription[]) atomicReference.getAndSet(multicastSubscriptionArr6);
                                int length4 = multicastSubscriptionArr8.length;
                                while (i4 < length4) {
                                    multicastSubscriptionArr8[i4].onComplete();
                                    i4++;
                                }
                                return;
                            }
                        }
                        i = i5;
                    }
                }
            }
            this.consumed = i;
            iAddAndGet = this.wip.addAndGet(-iAddAndGet);
            if (iAddAndGet == 0) {
                return;
            }
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        this.done = true;
        drain();
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        ExceptionHelper.nullCheck(th, "onError called with a null Throwable.");
        if (this.done) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.error = th;
        this.done = true;
        drain();
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(Object obj) {
        if (this.done) {
            return;
        }
        if (this.fusionMode == 0) {
            ExceptionHelper.nullCheck(obj, "onNext called with a null value.");
            if (!this.queue.offer(obj)) {
                SubscriptionHelper.cancel(this.upstream);
                onError(MissingBackpressureException.createDefault());
                return;
            }
        }
        drain();
    }

    @Override // org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.setOnce(this.upstream, subscription)) {
            if (subscription instanceof QueueSubscription) {
                QueueSubscription queueSubscription = (QueueSubscription) subscription;
                int iRequestFusion = queueSubscription.requestFusion(3);
                if (iRequestFusion == 1) {
                    this.fusionMode = iRequestFusion;
                    this.queue = queueSubscription;
                    this.done = true;
                    drain();
                    return;
                }
                if (iRequestFusion == 2) {
                    this.fusionMode = iRequestFusion;
                    this.queue = queueSubscription;
                    subscription.request(this.bufferSize);
                    return;
                }
            }
            this.queue = new SpscArrayQueue(this.bufferSize);
            subscription.request(this.bufferSize);
        }
    }

    public void remove(MulticastSubscription multicastSubscription) {
        while (true) {
            MulticastSubscription[] multicastSubscriptionArr = (MulticastSubscription[]) this.subscribers.get();
            int length = multicastSubscriptionArr.length;
            if (length == 0) {
                return;
            }
            int i = 0;
            while (true) {
                if (i >= length) {
                    i = -1;
                    break;
                } else if (multicastSubscriptionArr[i] == multicastSubscription) {
                    break;
                } else {
                    i++;
                }
            }
            if (i < 0) {
                return;
            }
            if (length != 1) {
                MulticastSubscription[] multicastSubscriptionArr2 = new MulticastSubscription[length - 1];
                System.arraycopy(multicastSubscriptionArr, 0, multicastSubscriptionArr2, 0, i);
                System.arraycopy(multicastSubscriptionArr, i + 1, multicastSubscriptionArr2, i, (length - i) - 1);
                if (this.subscribers.compareAndSet(multicastSubscriptionArr, multicastSubscriptionArr2)) {
                    return;
                }
            } else if (this.refcount) {
                if (this.subscribers.compareAndSet(multicastSubscriptionArr, TERMINATED)) {
                    SubscriptionHelper.cancel(this.upstream);
                    this.done = true;
                    return;
                }
            } else if (this.subscribers.compareAndSet(multicastSubscriptionArr, EMPTY)) {
                return;
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        Throwable th;
        MulticastSubscription multicastSubscription = new MulticastSubscription(subscriber, this);
        subscriber.onSubscribe(multicastSubscription);
        if (add(multicastSubscription)) {
            if (multicastSubscription.get() == Long.MIN_VALUE) {
                remove(multicastSubscription);
                return;
            } else {
                drain();
                return;
            }
        }
        if (!this.done || (th = this.error) == null) {
            subscriber.onComplete();
        } else {
            subscriber.onError(th);
        }
    }
}
