package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableThrottleLatest<T> extends AbstractObservableWithUpstream<T, T> {
    public final boolean emitLast;
    public final Consumer onDropped;
    public final Scheduler scheduler;
    public final long timeout;
    public final TimeUnit unit;

    public static final class ThrottleLatestObserver<T> extends AtomicInteger implements Observer<T>, Disposable, Runnable {
        public volatile boolean cancelled;
        public volatile boolean done;
        public final Observer downstream;
        public final boolean emitLast;
        public Throwable error;
        public final AtomicReference latest = new AtomicReference();
        public final Consumer onDropped;
        public final long timeout;
        public volatile boolean timerFired;
        public boolean timerRunning;
        public final TimeUnit unit;
        public Disposable upstream;
        public final Scheduler.Worker worker;

        public ThrottleLatestObserver(Observer observer, long j, TimeUnit timeUnit, Scheduler.Worker worker, boolean z, Consumer consumer) {
            this.downstream = observer;
            this.timeout = j;
            this.unit = timeUnit;
            this.worker = worker;
            this.emitLast = z;
            this.onDropped = consumer;
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

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.cancelled = true;
            this.upstream.dispose();
            this.worker.dispose();
            if (getAndIncrement() == 0) {
                clear();
            }
        }

        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            AtomicReference atomicReference = this.latest;
            Observer observer = this.downstream;
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
                    observer.onError(compositeException);
                    this.worker.dispose();
                    return;
                }
                boolean z2 = atomicReference.get() == null;
                if (z) {
                    if (!z2) {
                        Object andSet2 = atomicReference.getAndSet(null);
                        if (this.emitLast) {
                            observer.onNext(andSet2);
                        } else {
                            Consumer consumer = this.onDropped;
                            if (consumer != null) {
                                try {
                                    consumer.accept(andSet2);
                                } catch (Throwable th2) {
                                    Exceptions.throwIfFatal(th2);
                                    observer.onError(th2);
                                    this.worker.dispose();
                                    return;
                                }
                            }
                        }
                    }
                    observer.onComplete();
                    this.worker.dispose();
                    return;
                }
                if (z2) {
                    if (this.timerFired) {
                        this.timerRunning = false;
                        this.timerFired = false;
                    }
                } else if (!this.timerRunning || this.timerFired) {
                    observer.onNext(atomicReference.getAndSet(null));
                    this.timerFired = false;
                    this.timerRunning = true;
                    this.worker.schedule(this, this.timeout, this.unit);
                }
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
            clear();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.done = true;
            drain();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            drain();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            Object andSet = this.latest.getAndSet(obj);
            Consumer consumer = this.onDropped;
            if (consumer != null && andSet != null) {
                try {
                    consumer.accept(andSet);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.upstream.dispose();
                    this.error = th;
                    this.done = true;
                }
            }
            drain();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.timerFired = true;
            drain();
        }
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        this.source.subscribe(new ThrottleLatestObserver(observer, this.timeout, this.unit, this.scheduler.createWorker(), this.emitLast, this.onDropped));
    }
}
