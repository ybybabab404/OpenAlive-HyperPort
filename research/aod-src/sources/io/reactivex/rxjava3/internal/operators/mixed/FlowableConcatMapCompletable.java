package io.reactivex.rxjava3.internal.operators.mixed;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import io.reactivex.rxjava3.operators.SimpleQueue;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableConcatMapCompletable<T> extends Completable {
    public final ErrorMode errorMode;
    public final Function mapper;
    public final int prefetch;
    public final Flowable source;

    public static final class ConcatMapCompletableObserver<T> extends ConcatMapXMainSubscriber<T> implements Disposable {
        public volatile boolean active;
        public int consumed;
        public final CompletableObserver downstream;
        public final ConcatMapInnerObserver inner;
        public final Function mapper;

        public static final class ConcatMapInnerObserver extends AtomicReference<Disposable> implements CompletableObserver {
            public final ConcatMapCompletableObserver parent;

            public ConcatMapInnerObserver(ConcatMapCompletableObserver concatMapCompletableObserver) {
                this.parent = concatMapCompletableObserver;
            }

            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onComplete() {
                this.parent.innerComplete();
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable th) {
                this.parent.innerError(th);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this, disposable);
            }
        }

        public ConcatMapCompletableObserver(CompletableObserver completableObserver, Function function, ErrorMode errorMode, int i) {
            super(i, errorMode);
            this.downstream = completableObserver;
            this.mapper = function;
            this.inner = new ConcatMapInnerObserver(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            stop();
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
            ErrorMode errorMode = this.errorMode;
            SimpleQueue simpleQueue = this.queue;
            AtomicThrowable atomicThrowable = this.errors;
            boolean z = this.syncFused;
            while (!this.cancelled) {
                if (atomicThrowable.get() != null && (errorMode == ErrorMode.IMMEDIATE || (errorMode == ErrorMode.BOUNDARY && !this.active))) {
                    simpleQueue.clear();
                    atomicThrowable.tryTerminateConsumer(this.downstream);
                    return;
                }
                if (!this.active) {
                    boolean z2 = this.done;
                    try {
                        Object objPoll = simpleQueue.poll();
                        boolean z3 = objPoll == null;
                        if (z2 && z3) {
                            atomicThrowable.tryTerminateConsumer(this.downstream);
                            return;
                        }
                        if (!z3) {
                            int i = this.prefetch;
                            int i2 = i - (i >> 1);
                            if (!z) {
                                int i3 = this.consumed + 1;
                                if (i3 == i2) {
                                    this.consumed = 0;
                                    this.upstream.request(i2);
                                } else {
                                    this.consumed = i3;
                                }
                            }
                            try {
                                Object objApply = this.mapper.apply(objPoll);
                                Objects.requireNonNull(objApply, "The mapper returned a null CompletableSource");
                                CompletableSource completableSource = (CompletableSource) objApply;
                                this.active = true;
                                completableSource.subscribe(this.inner);
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                simpleQueue.clear();
                                this.upstream.cancel();
                                atomicThrowable.tryAddThrowableOrReport(th);
                                atomicThrowable.tryTerminateConsumer(this.downstream);
                                return;
                            }
                        }
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        this.upstream.cancel();
                        atomicThrowable.tryAddThrowableOrReport(th2);
                        atomicThrowable.tryTerminateConsumer(this.downstream);
                        return;
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            }
            simpleQueue.clear();
        }

        public void innerComplete() {
            this.active = false;
            drain();
        }

        public void innerError(Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                if (this.errorMode != ErrorMode.IMMEDIATE) {
                    this.active = false;
                    drain();
                    return;
                }
                this.upstream.cancel();
                this.errors.tryTerminateConsumer(this.downstream);
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.rxjava3.internal.operators.mixed.ConcatMapXMainSubscriber
        public void onSubscribeDownstream() {
            this.downstream.onSubscribe(this);
        }
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        this.source.subscribe((FlowableSubscriber) new ConcatMapCompletableObserver(completableObserver, this.mapper, this.errorMode, this.prefetch));
    }
}
