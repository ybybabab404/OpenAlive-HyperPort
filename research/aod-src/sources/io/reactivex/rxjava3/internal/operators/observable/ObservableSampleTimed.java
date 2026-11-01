package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.observers.SerializedObserver;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableSampleTimed<T> extends AbstractObservableWithUpstream<T, T> {
    public final boolean emitLast;
    public final Consumer onDropped;
    public final long period;
    public final Scheduler scheduler;
    public final TimeUnit unit;

    public static final class SampleTimedEmitLast<T> extends SampleTimedObserver<T> {
        public final AtomicInteger wip;

        public SampleTimedEmitLast(Observer observer, long j, TimeUnit timeUnit, Scheduler scheduler, Consumer consumer) {
            super(observer, j, timeUnit, scheduler, consumer);
            this.wip = new AtomicInteger(1);
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableSampleTimed.SampleTimedObserver
        public void complete() {
            emit();
            if (this.wip.decrementAndGet() == 0) {
                this.downstream.onComplete();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.wip.incrementAndGet() == 2) {
                emit();
                if (this.wip.decrementAndGet() == 0) {
                    this.downstream.onComplete();
                }
            }
        }
    }

    public static final class SampleTimedNoLast<T> extends SampleTimedObserver<T> {
        public SampleTimedNoLast(Observer observer, long j, TimeUnit timeUnit, Scheduler scheduler, Consumer consumer) {
            super(observer, j, timeUnit, scheduler, consumer);
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableSampleTimed.SampleTimedObserver
        public void complete() {
            this.downstream.onComplete();
        }

        @Override // java.lang.Runnable
        public void run() {
            emit();
        }
    }

    public static abstract class SampleTimedObserver<T> extends AtomicReference<T> implements Observer<T>, Disposable, Runnable {
        public final Observer downstream;
        public final Consumer onDropped;
        public final long period;
        public final Scheduler scheduler;
        public final AtomicReference timer = new AtomicReference();
        public final TimeUnit unit;
        public Disposable upstream;

        public SampleTimedObserver(Observer observer, long j, TimeUnit timeUnit, Scheduler scheduler, Consumer consumer) {
            this.downstream = observer;
            this.period = j;
            this.unit = timeUnit;
            this.scheduler = scheduler;
            this.onDropped = consumer;
        }

        public void cancelTimer() {
            DisposableHelper.dispose(this.timer);
        }

        public abstract void complete();

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            cancelTimer();
            this.upstream.dispose();
        }

        public void emit() {
            T andSet = getAndSet(null);
            if (andSet != null) {
                this.downstream.onNext(andSet);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            cancelTimer();
            complete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            cancelTimer();
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            Consumer consumer;
            T andSet = getAndSet(obj);
            if (andSet == null || (consumer = this.onDropped) == null) {
                return;
            }
            try {
                consumer.accept(andSet);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancelTimer();
                this.upstream.dispose();
                this.downstream.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
                Scheduler scheduler = this.scheduler;
                long j = this.period;
                DisposableHelper.replace(this.timer, scheduler.schedulePeriodicallyDirect(this, j, j, this.unit));
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        SerializedObserver serializedObserver = new SerializedObserver(observer);
        if (this.emitLast) {
            this.source.subscribe(new SampleTimedEmitLast(serializedObserver, this.period, this.unit, this.scheduler, this.onDropped));
        } else {
            this.source.subscribe(new SampleTimedNoLast(serializedObserver, this.period, this.unit, this.scheduler, this.onDropped));
        }
    }
}
