package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.operators.SimplePlainQueue;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableCreate<T> extends Flowable<T> {
    public final BackpressureStrategy backpressure;
    public final FlowableOnSubscribe source;

    /* JADX INFO: renamed from: io.reactivex.rxjava3.internal.operators.flowable.FlowableCreate$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$io$reactivex$rxjava3$core$BackpressureStrategy;

        static {
            int[] iArr = new int[BackpressureStrategy.values().length];
            $SwitchMap$io$reactivex$rxjava3$core$BackpressureStrategy = iArr;
            try {
                iArr[BackpressureStrategy.MISSING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$reactivex$rxjava3$core$BackpressureStrategy[BackpressureStrategy.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$reactivex$rxjava3$core$BackpressureStrategy[BackpressureStrategy.DROP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$io$reactivex$rxjava3$core$BackpressureStrategy[BackpressureStrategy.LATEST.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static abstract class BaseEmitter<T> extends AtomicLong implements FlowableEmitter<T>, Subscription {
        public final Subscriber downstream;
        public final SequentialDisposable serial = new SequentialDisposable();

        public BaseEmitter(Subscriber subscriber) {
            this.downstream = subscriber;
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            this.serial.dispose();
            onUnsubscribed();
        }

        public void completeDownstream() {
            if (isCancelled()) {
                return;
            }
            try {
                this.downstream.onComplete();
            } finally {
                this.serial.dispose();
            }
        }

        public boolean errorDownstream(Throwable th) {
            if (isCancelled()) {
                return false;
            }
            try {
                this.downstream.onError(th);
                return true;
            } finally {
                this.serial.dispose();
            }
        }

        public final boolean isCancelled() {
            return this.serial.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onComplete() {
            completeDownstream();
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public final void onError(Throwable th) {
            if (th == null) {
                th = ExceptionHelper.createNullPointerException("onError called with a null Throwable.");
            }
            if (signalError(th)) {
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onRequested() {
        }

        public void onUnsubscribed() {
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this, j);
                onRequested();
            }
        }

        public boolean signalError(Throwable th) {
            return errorDownstream(th);
        }

        @Override // java.util.concurrent.atomic.AtomicLong
        public String toString() {
            return String.format("%s{%s}", getClass().getSimpleName(), super.toString());
        }
    }

    public static final class BufferAsyncEmitter<T> extends BaseEmitter<T> {
        public volatile boolean done;
        public Throwable error;
        public final SpscLinkedArrayQueue queue;
        public final AtomicInteger wip;

        public BufferAsyncEmitter(Subscriber subscriber, int i) {
            super(subscriber);
            this.queue = new SpscLinkedArrayQueue(i);
            this.wip = new AtomicInteger();
        }

        public void drain() {
            if (this.wip.getAndIncrement() != 0) {
                return;
            }
            Subscriber subscriber = this.downstream;
            SpscLinkedArrayQueue spscLinkedArrayQueue = this.queue;
            int iAddAndGet = 1;
            do {
                long j = get();
                long j2 = 0;
                while (j2 != j) {
                    if (isCancelled()) {
                        spscLinkedArrayQueue.clear();
                        return;
                    }
                    boolean z = this.done;
                    Object objPoll = spscLinkedArrayQueue.poll();
                    boolean z2 = objPoll == null;
                    if (z && z2) {
                        Throwable th = this.error;
                        if (th != null) {
                            errorDownstream(th);
                            return;
                        } else {
                            completeDownstream();
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
                    if (isCancelled()) {
                        spscLinkedArrayQueue.clear();
                        return;
                    }
                    boolean z3 = this.done;
                    boolean zIsEmpty = spscLinkedArrayQueue.isEmpty();
                    if (z3 && zIsEmpty) {
                        Throwable th2 = this.error;
                        if (th2 != null) {
                            errorDownstream(th2);
                            return;
                        } else {
                            completeDownstream();
                            return;
                        }
                    }
                }
                if (j2 != 0) {
                    BackpressureHelper.produced(this, j2);
                }
                iAddAndGet = this.wip.addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableCreate.BaseEmitter, io.reactivex.rxjava3.core.Emitter
        public void onComplete() {
            this.done = true;
            drain();
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onNext(Object obj) {
            if (this.done || isCancelled()) {
                return;
            }
            if (obj == null) {
                onError(ExceptionHelper.createNullPointerException("onNext called with a null value."));
            } else {
                this.queue.offer(obj);
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableCreate.BaseEmitter
        public void onRequested() {
            drain();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableCreate.BaseEmitter
        public void onUnsubscribed() {
            if (this.wip.getAndIncrement() == 0) {
                this.queue.clear();
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableCreate.BaseEmitter
        public boolean signalError(Throwable th) {
            if (this.done || isCancelled()) {
                return false;
            }
            this.error = th;
            this.done = true;
            drain();
            return true;
        }
    }

    public static final class DropAsyncEmitter<T> extends NoOverflowBaseAsyncEmitter<T> {
        public DropAsyncEmitter(Subscriber subscriber) {
            super(subscriber);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableCreate.NoOverflowBaseAsyncEmitter
        public void onOverflow() {
        }
    }

    public static final class ErrorAsyncEmitter<T> extends NoOverflowBaseAsyncEmitter<T> {
        public ErrorAsyncEmitter(Subscriber subscriber) {
            super(subscriber);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableCreate.NoOverflowBaseAsyncEmitter
        public void onOverflow() {
            onError(new MissingBackpressureException("create: Could not emit value due to lack of requests"));
        }
    }

    public static final class LatestAsyncEmitter<T> extends BaseEmitter<T> {
        public volatile boolean done;
        public Throwable error;
        public final AtomicReference queue;
        public final AtomicInteger wip;

        public LatestAsyncEmitter(Subscriber subscriber) {
            super(subscriber);
            this.queue = new AtomicReference();
            this.wip = new AtomicInteger();
        }

        public void drain() {
            if (this.wip.getAndIncrement() != 0) {
                return;
            }
            Subscriber subscriber = this.downstream;
            AtomicReference atomicReference = this.queue;
            int iAddAndGet = 1;
            do {
                long j = get();
                long j2 = 0;
                while (true) {
                    if (j2 == j) {
                        break;
                    }
                    if (isCancelled()) {
                        atomicReference.lazySet(null);
                        return;
                    }
                    boolean z = this.done;
                    Object andSet = atomicReference.getAndSet(null);
                    boolean z2 = andSet == null;
                    if (z && z2) {
                        Throwable th = this.error;
                        if (th != null) {
                            errorDownstream(th);
                            return;
                        } else {
                            completeDownstream();
                            return;
                        }
                    }
                    if (z2) {
                        break;
                    }
                    subscriber.onNext(andSet);
                    j2++;
                }
                if (j2 == j) {
                    if (isCancelled()) {
                        atomicReference.lazySet(null);
                        return;
                    }
                    boolean z3 = this.done;
                    boolean z4 = atomicReference.get() == null;
                    if (z3 && z4) {
                        Throwable th2 = this.error;
                        if (th2 != null) {
                            errorDownstream(th2);
                            return;
                        } else {
                            completeDownstream();
                            return;
                        }
                    }
                }
                if (j2 != 0) {
                    BackpressureHelper.produced(this, j2);
                }
                iAddAndGet = this.wip.addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableCreate.BaseEmitter, io.reactivex.rxjava3.core.Emitter
        public void onComplete() {
            this.done = true;
            drain();
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onNext(Object obj) {
            if (this.done || isCancelled()) {
                return;
            }
            if (obj == null) {
                onError(ExceptionHelper.createNullPointerException("onNext called with a null value."));
            } else {
                this.queue.set(obj);
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableCreate.BaseEmitter
        public void onRequested() {
            drain();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableCreate.BaseEmitter
        public void onUnsubscribed() {
            if (this.wip.getAndIncrement() == 0) {
                this.queue.lazySet(null);
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableCreate.BaseEmitter
        public boolean signalError(Throwable th) {
            if (this.done || isCancelled()) {
                return false;
            }
            this.error = th;
            this.done = true;
            drain();
            return true;
        }
    }

    public static final class MissingEmitter<T> extends BaseEmitter<T> {
        public MissingEmitter(Subscriber subscriber) {
            super(subscriber);
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onNext(Object obj) {
            long j;
            if (isCancelled()) {
                return;
            }
            if (obj == null) {
                onError(ExceptionHelper.createNullPointerException("onNext called with a null value."));
                return;
            }
            this.downstream.onNext(obj);
            do {
                j = get();
                if (j == 0) {
                    return;
                }
            } while (!compareAndSet(j, j - 1));
        }
    }

    public static abstract class NoOverflowBaseAsyncEmitter<T> extends BaseEmitter<T> {
        public NoOverflowBaseAsyncEmitter(Subscriber subscriber) {
            super(subscriber);
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public final void onNext(Object obj) {
            if (isCancelled()) {
                return;
            }
            if (obj == null) {
                onError(ExceptionHelper.createNullPointerException("onNext called with a null value."));
            } else if (get() == 0) {
                onOverflow();
            } else {
                this.downstream.onNext(obj);
                BackpressureHelper.produced(this, 1L);
            }
        }

        public abstract void onOverflow();
    }

    public static final class SerializedEmitter<T> extends AtomicInteger implements FlowableEmitter<T> {
        public volatile boolean done;
        public final BaseEmitter emitter;
        public final AtomicThrowable errors;
        public final SimplePlainQueue queue;

        public void drain() {
            if (getAndIncrement() == 0) {
                drainLoop();
            }
        }

        public void drainLoop() {
            BaseEmitter baseEmitter = this.emitter;
            SimplePlainQueue simplePlainQueue = this.queue;
            AtomicThrowable atomicThrowable = this.errors;
            int iAddAndGet = 1;
            while (!baseEmitter.isCancelled()) {
                if (atomicThrowable.get() != null) {
                    simplePlainQueue.clear();
                    atomicThrowable.tryTerminateConsumer(baseEmitter);
                    return;
                }
                boolean z = this.done;
                Object objPoll = simplePlainQueue.poll();
                boolean z2 = objPoll == null;
                if (z && z2) {
                    baseEmitter.onComplete();
                    return;
                } else if (z2) {
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    baseEmitter.onNext(objPoll);
                }
            }
            simplePlainQueue.clear();
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onComplete() {
            if (this.emitter.isCancelled() || this.done) {
                return;
            }
            this.done = true;
            drain();
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onError(Throwable th) {
            if (tryOnError(th)) {
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // java.util.concurrent.atomic.AtomicInteger
        public String toString() {
            return this.emitter.toString();
        }

        public boolean tryOnError(Throwable th) {
            if (!this.emitter.isCancelled() && !this.done) {
                if (th == null) {
                    th = ExceptionHelper.createNullPointerException("onError called with a null Throwable.");
                }
                if (this.errors.tryAddThrowable(th)) {
                    this.done = true;
                    drain();
                    return true;
                }
            }
            return false;
        }
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        BaseEmitter missingEmitter;
        int i = AnonymousClass1.$SwitchMap$io$reactivex$rxjava3$core$BackpressureStrategy[this.backpressure.ordinal()];
        if (i == 1) {
            missingEmitter = new MissingEmitter(subscriber);
        } else if (i == 2) {
            missingEmitter = new ErrorAsyncEmitter(subscriber);
        } else if (i != 3) {
            missingEmitter = i != 4 ? new BufferAsyncEmitter(subscriber, Flowable.bufferSize()) : new LatestAsyncEmitter(subscriber);
        } else {
            missingEmitter = new DropAsyncEmitter(subscriber);
        }
        subscriber.onSubscribe(missingEmitter);
        try {
            this.source.subscribe(missingEmitter);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            missingEmitter.onError(th);
        }
    }
}
