package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.BackpressureOverflowStrategy;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableOnBackpressureBufferStrategy<T> extends AbstractFlowableWithUpstream<T, T> {
    public final long bufferSize;
    public final Consumer onDropped;
    public final Action onOverflow;
    public final BackpressureOverflowStrategy strategy;

    /* JADX INFO: renamed from: io.reactivex.rxjava3.internal.operators.flowable.FlowableOnBackpressureBufferStrategy$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$io$reactivex$rxjava3$core$BackpressureOverflowStrategy;

        static {
            int[] iArr = new int[BackpressureOverflowStrategy.values().length];
            $SwitchMap$io$reactivex$rxjava3$core$BackpressureOverflowStrategy = iArr;
            try {
                iArr[BackpressureOverflowStrategy.DROP_LATEST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$reactivex$rxjava3$core$BackpressureOverflowStrategy[BackpressureOverflowStrategy.DROP_OLDEST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static final class OnBackpressureBufferStrategySubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        public final long bufferSize;
        public volatile boolean cancelled;
        public volatile boolean done;
        public final Subscriber downstream;
        public Throwable error;
        public final Consumer onDropped;
        public final Action onOverflow;
        public final BackpressureOverflowStrategy strategy;
        public Subscription upstream;
        public final AtomicLong requested = new AtomicLong();
        public final Deque deque = new ArrayDeque();

        public OnBackpressureBufferStrategySubscriber(Subscriber subscriber, Action action, BackpressureOverflowStrategy backpressureOverflowStrategy, long j, Consumer consumer) {
            this.downstream = subscriber;
            this.onOverflow = action;
            this.strategy = backpressureOverflowStrategy;
            this.bufferSize = j;
            this.onDropped = consumer;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.upstream.cancel();
            if (getAndIncrement() == 0) {
                clear(this.deque);
            }
        }

        public void clear(Deque deque) {
            synchronized (deque) {
                deque.clear();
            }
        }

        public void drain() {
            boolean zIsEmpty;
            Object objPoll;
            if (getAndIncrement() != 0) {
                return;
            }
            Deque deque = this.deque;
            Subscriber subscriber = this.downstream;
            int iAddAndGet = 1;
            do {
                long j = this.requested.get();
                long j2 = 0;
                while (j2 != j) {
                    if (this.cancelled) {
                        clear(deque);
                        return;
                    }
                    boolean z = this.done;
                    synchronized (deque) {
                        objPoll = deque.poll();
                    }
                    boolean z2 = objPoll == null;
                    if (z) {
                        Throwable th = this.error;
                        if (th != null) {
                            clear(deque);
                            subscriber.onError(th);
                            return;
                        } else if (z2) {
                            subscriber.onComplete();
                            return;
                        }
                    }
                    if (z2) {
                        break;
                    }
                    subscriber.onNext(objPoll);
                    j2++;
                }
                if (j2 == j) {
                    if (this.cancelled) {
                        clear(deque);
                        return;
                    }
                    boolean z3 = this.done;
                    synchronized (deque) {
                        zIsEmpty = deque.isEmpty();
                    }
                    if (z3) {
                        Throwable th2 = this.error;
                        if (th2 != null) {
                            clear(deque);
                            subscriber.onError(th2);
                            return;
                        } else if (zIsEmpty) {
                            subscriber.onComplete();
                            return;
                        }
                    }
                }
                if (j2 != 0) {
                    BackpressureHelper.produced(this.requested, j2);
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
            boolean z;
            boolean z2;
            boolean z3;
            Action action;
            Object objPollLast;
            if (this.done) {
                return;
            }
            Deque deque = this.deque;
            synchronized (deque) {
                try {
                    z = false;
                    z2 = true;
                    if (deque.size() == this.bufferSize) {
                        int i = AnonymousClass1.$SwitchMap$io$reactivex$rxjava3$core$BackpressureOverflowStrategy[this.strategy.ordinal()];
                        if (i == 1) {
                            objPollLast = deque.pollLast();
                            deque.offer(obj);
                        } else if (i != 2) {
                            z3 = false;
                        } else {
                            objPollLast = deque.poll();
                            deque.offer(obj);
                        }
                        obj = objPollLast;
                        z3 = false;
                        z = true;
                        z2 = false;
                    } else {
                        deque.offer(obj);
                        obj = null;
                        z3 = true;
                        z2 = false;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (z && (action = this.onOverflow) != null) {
                try {
                    action.run();
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    this.upstream.cancel();
                    onError(th2);
                }
            }
            Consumer consumer = this.onDropped;
            if (consumer != null && obj != null) {
                try {
                    consumer.accept(obj);
                } catch (Throwable th3) {
                    Exceptions.throwIfFatal(th3);
                    this.upstream.cancel();
                    onError(th3);
                }
            }
            if (z2) {
                this.upstream.cancel();
                onError(MissingBackpressureException.createDefault());
            }
            if (z3) {
                drain();
            }
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

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        this.source.subscribe((FlowableSubscriber) new OnBackpressureBufferStrategySubscriber(subscriber, this.onOverflow, this.strategy, this.bufferSize, this.onDropped));
    }
}
