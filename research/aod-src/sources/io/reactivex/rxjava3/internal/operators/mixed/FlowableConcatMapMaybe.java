package io.reactivex.rxjava3.internal.operators.mixed;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import io.reactivex.rxjava3.operators.SimpleQueue;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableConcatMapMaybe<T, R> extends Flowable<R> {
    public final ErrorMode errorMode;
    public final Function mapper;
    public final int prefetch;
    public final Flowable source;

    public static final class ConcatMapMaybeSubscriber<T, R> extends ConcatMapXMainSubscriber<T> implements Subscription {
        public int consumed;
        public final Subscriber downstream;
        public long emitted;
        public final ConcatMapMaybeObserver inner;
        public Object item;
        public final Function mapper;
        public final AtomicLong requested;
        public volatile int state;

        public static final class ConcatMapMaybeObserver<R> extends AtomicReference<Disposable> implements MaybeObserver<R> {
            public final ConcatMapMaybeSubscriber parent;

            public ConcatMapMaybeObserver(ConcatMapMaybeSubscriber concatMapMaybeSubscriber) {
                this.parent = concatMapMaybeSubscriber;
            }

            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onComplete() {
                this.parent.innerComplete();
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
            public void onError(Throwable th) {
                this.parent.innerError(th);
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
            public void onSuccess(Object obj) {
                this.parent.innerSuccess(obj);
            }
        }

        public ConcatMapMaybeSubscriber(Subscriber subscriber, Function function, int i, ErrorMode errorMode) {
            super(i, errorMode);
            this.downstream = subscriber;
            this.mapper = function;
            this.requested = new AtomicLong();
            this.inner = new ConcatMapMaybeObserver(this);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            stop();
        }

        @Override // io.reactivex.rxjava3.internal.operators.mixed.ConcatMapXMainSubscriber
        public void clearValue() {
            this.item = null;
        }

        @Override // io.reactivex.rxjava3.internal.operators.mixed.ConcatMapXMainSubscriber
        public void disposeInner() {
            this.inner.dispose();
        }

        @Override // io.reactivex.rxjava3.internal.operators.mixed.ConcatMapXMainSubscriber
        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            Subscriber subscriber = this.downstream;
            ErrorMode errorMode = this.errorMode;
            SimpleQueue simpleQueue = this.queue;
            AtomicThrowable atomicThrowable = this.errors;
            AtomicLong atomicLong = this.requested;
            int i = this.prefetch;
            int i2 = i - (i >> 1);
            boolean z = this.syncFused;
            int iAddAndGet = 1;
            while (true) {
                if (!this.cancelled) {
                    int i3 = this.state;
                    if (atomicThrowable.get() != null && (errorMode == ErrorMode.IMMEDIATE || (errorMode == ErrorMode.BOUNDARY && i3 == 0))) {
                        break;
                    }
                    if (i3 == 0) {
                        boolean z2 = this.done;
                        try {
                            Object objPoll = simpleQueue.poll();
                            boolean z3 = objPoll == null;
                            if (z2 && z3) {
                                atomicThrowable.tryTerminateConsumer(subscriber);
                                return;
                            }
                            if (!z3) {
                                if (!z) {
                                    int i4 = this.consumed + 1;
                                    if (i4 == i2) {
                                        this.consumed = 0;
                                        this.upstream.request(i2);
                                    } else {
                                        this.consumed = i4;
                                    }
                                }
                                try {
                                    Object objApply = this.mapper.apply(objPoll);
                                    Objects.requireNonNull(objApply, "The mapper returned a null MaybeSource");
                                    MaybeSource maybeSource = (MaybeSource) objApply;
                                    this.state = 1;
                                    maybeSource.subscribe(this.inner);
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    this.upstream.cancel();
                                    simpleQueue.clear();
                                    atomicThrowable.tryAddThrowableOrReport(th);
                                    atomicThrowable.tryTerminateConsumer(subscriber);
                                    return;
                                }
                            }
                        } catch (Throwable th2) {
                            Exceptions.throwIfFatal(th2);
                            this.upstream.cancel();
                            atomicThrowable.tryAddThrowableOrReport(th2);
                            atomicThrowable.tryTerminateConsumer(subscriber);
                            return;
                        }
                    } else if (i3 == 2) {
                        long j = this.emitted;
                        if (j != atomicLong.get()) {
                            Object obj = this.item;
                            this.item = null;
                            subscriber.onNext(obj);
                            this.emitted = j + 1;
                            this.state = 0;
                        }
                    }
                } else {
                    simpleQueue.clear();
                    this.item = null;
                }
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
            simpleQueue.clear();
            this.item = null;
            atomicThrowable.tryTerminateConsumer(subscriber);
        }

        public void innerComplete() {
            this.state = 0;
            drain();
        }

        public void innerError(Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                if (this.errorMode != ErrorMode.END) {
                    this.upstream.cancel();
                }
                this.state = 0;
                drain();
            }
        }

        public void innerSuccess(Object obj) {
            this.item = obj;
            this.state = 2;
            drain();
        }

        @Override // io.reactivex.rxjava3.internal.operators.mixed.ConcatMapXMainSubscriber
        public void onSubscribeDownstream() {
            this.downstream.onSubscribe(this);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            BackpressureHelper.add(this.requested, j);
            drain();
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        this.source.subscribe((FlowableSubscriber) new ConcatMapMaybeSubscriber(subscriber, this.mapper, this.prefetch, this.errorMode));
    }
}
