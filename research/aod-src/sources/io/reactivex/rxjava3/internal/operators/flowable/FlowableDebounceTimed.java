package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.subscribers.SerializedSubscriber;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableDebounceTimed<T> extends AbstractFlowableWithUpstream<T, T> {
    public final Consumer onDropped;
    public final Scheduler scheduler;
    public final long timeout;
    public final TimeUnit unit;

    public static final class DebounceEmitter<T> extends AtomicReference<Disposable> implements Runnable, Disposable {
        public final long idx;
        public final AtomicBoolean once = new AtomicBoolean();
        public final DebounceTimedSubscriber parent;
        public final Object value;

        public DebounceEmitter(Object obj, long j, DebounceTimedSubscriber debounceTimedSubscriber) {
            this.value = obj;
            this.idx = j;
            this.parent = debounceTimedSubscriber;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public void emit() {
            if (this.once.compareAndSet(false, true)) {
                this.parent.emit(this.idx, this.value, this);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() == DisposableHelper.DISPOSED;
        }

        @Override // java.lang.Runnable
        public void run() {
            emit();
        }

        public void setResource(Disposable disposable) {
            DisposableHelper.replace(this, disposable);
        }
    }

    public static final class DebounceTimedSubscriber<T> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
        public boolean done;
        public final Subscriber downstream;
        public volatile long index;
        public final Consumer onDropped;
        public final long timeout;
        public DebounceEmitter timer;
        public final TimeUnit unit;
        public Subscription upstream;
        public final Scheduler.Worker worker;

        public DebounceTimedSubscriber(Subscriber subscriber, long j, TimeUnit timeUnit, Scheduler.Worker worker, Consumer consumer) {
            this.downstream = subscriber;
            this.timeout = j;
            this.unit = timeUnit;
            this.worker = worker;
            this.onDropped = consumer;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
            this.worker.dispose();
        }

        public void emit(long j, Object obj, DebounceEmitter debounceEmitter) {
            if (j == this.index) {
                if (get() == 0) {
                    cancel();
                    this.downstream.onError(MissingBackpressureException.createDefault());
                } else {
                    this.downstream.onNext(obj);
                    BackpressureHelper.produced(this, 1L);
                    debounceEmitter.dispose();
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            DebounceEmitter debounceEmitter = this.timer;
            if (debounceEmitter != null) {
                debounceEmitter.dispose();
            }
            if (debounceEmitter != null) {
                debounceEmitter.emit();
            }
            this.downstream.onComplete();
            this.worker.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            DebounceEmitter debounceEmitter = this.timer;
            if (debounceEmitter != null) {
                debounceEmitter.dispose();
            }
            this.downstream.onError(th);
            this.worker.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (this.done) {
                return;
            }
            long j = this.index + 1;
            this.index = j;
            DebounceEmitter debounceEmitter = this.timer;
            if (debounceEmitter != null) {
                debounceEmitter.dispose();
            }
            Consumer consumer = this.onDropped;
            if (consumer != null && debounceEmitter != null) {
                try {
                    consumer.accept(debounceEmitter.value);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.upstream.cancel();
                    this.done = true;
                    this.downstream.onError(th);
                    this.worker.dispose();
                }
            }
            DebounceEmitter debounceEmitter2 = new DebounceEmitter(obj, j, this);
            this.timer = debounceEmitter2;
            debounceEmitter2.setResource(this.worker.schedule(debounceEmitter2, this.timeout, this.unit));
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
                BackpressureHelper.add(this, j);
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        this.source.subscribe((FlowableSubscriber) new DebounceTimedSubscriber(new SerializedSubscriber(subscriber), this.timeout, this.unit, this.scheduler.createWorker(), this.onDropped));
    }
}
