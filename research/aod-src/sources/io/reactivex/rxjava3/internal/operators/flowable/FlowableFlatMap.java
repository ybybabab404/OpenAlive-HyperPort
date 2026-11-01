package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.QueueOverflowException;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.operators.QueueSubscription;
import io.reactivex.rxjava3.operators.SimplePlainQueue;
import io.reactivex.rxjava3.operators.SimpleQueue;
import io.reactivex.rxjava3.operators.SpscArrayQueue;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableFlatMap<T, U> extends AbstractFlowableWithUpstream<T, U> {
    public final int bufferSize;
    public final boolean delayErrors;
    public final Function mapper;
    public final int maxConcurrency;

    public static final class InnerSubscriber<T, U> extends AtomicReference<Subscription> implements FlowableSubscriber<U>, Disposable {
        public final int bufferSize;
        public volatile boolean done;
        public int fusionMode;
        public final long id;
        public final int limit;
        public final MergeSubscriber parent;
        public long produced;
        public volatile SimpleQueue queue;

        public InnerSubscriber(MergeSubscriber mergeSubscriber, int i, long j) {
            this.id = j;
            this.parent = mergeSubscriber;
            this.bufferSize = i;
            this.limit = i >> 2;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            SubscriptionHelper.cancel(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() == SubscriptionHelper.CANCELLED;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            lazySet(SubscriptionHelper.CANCELLED);
            this.parent.innerError(this, th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (this.fusionMode != 2) {
                this.parent.tryEmit(obj, this);
            } else {
                this.parent.drain();
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.fusionMode = iRequestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.parent.drain();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.fusionMode = iRequestFusion;
                        this.queue = queueSubscription;
                    }
                }
                subscription.request(this.bufferSize);
            }
        }

        public void requestMore(long j) {
            if (this.fusionMode != 1) {
                long j2 = this.produced + j;
                if (j2 < this.limit) {
                    this.produced = j2;
                } else {
                    this.produced = 0L;
                    get().request(j2);
                }
            }
        }
    }

    public static final class MergeSubscriber<T, U> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        public final int bufferSize;
        public volatile boolean cancelled;
        public final boolean delayErrors;
        public volatile boolean done;
        public final Subscriber downstream;
        public final AtomicThrowable errors = new AtomicThrowable();
        public long lastId;
        public int lastIndex;
        public final Function mapper;
        public final int maxConcurrency;
        public volatile SimplePlainQueue queue;
        public final AtomicLong requested;
        public int scalarEmitted;
        public final int scalarLimit;
        public final AtomicReference subscribers;
        public long uniqueId;
        public Subscription upstream;
        public static final InnerSubscriber[] EMPTY = new InnerSubscriber[0];
        public static final InnerSubscriber[] CANCELLED = new InnerSubscriber[0];

        public MergeSubscriber(Subscriber subscriber, Function function, boolean z, int i, int i2) {
            AtomicReference atomicReference = new AtomicReference();
            this.subscribers = atomicReference;
            this.requested = new AtomicLong();
            this.downstream = subscriber;
            this.mapper = function;
            this.delayErrors = z;
            this.maxConcurrency = i;
            this.bufferSize = i2;
            this.scalarLimit = Math.max(1, i >> 1);
            atomicReference.lazySet(EMPTY);
        }

        public boolean addInner(InnerSubscriber innerSubscriber) {
            InnerSubscriber[] innerSubscriberArr;
            InnerSubscriber[] innerSubscriberArr2;
            do {
                innerSubscriberArr = (InnerSubscriber[]) this.subscribers.get();
                if (innerSubscriberArr == CANCELLED) {
                    innerSubscriber.dispose();
                    return false;
                }
                int length = innerSubscriberArr.length;
                innerSubscriberArr2 = new InnerSubscriber[length + 1];
                System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr2, 0, length);
                innerSubscriberArr2[length] = innerSubscriber;
            } while (!this.subscribers.compareAndSet(innerSubscriberArr, innerSubscriberArr2));
            return true;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SimplePlainQueue simplePlainQueue;
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.upstream.cancel();
            disposeAll();
            if (getAndIncrement() != 0 || (simplePlainQueue = this.queue) == null) {
                return;
            }
            simplePlainQueue.clear();
        }

        public boolean checkTerminate() {
            if (this.cancelled) {
                clearScalarQueue();
                return true;
            }
            if (this.delayErrors || this.errors.get() == null) {
                return false;
            }
            clearScalarQueue();
            this.errors.tryTerminateConsumer(this.downstream);
            return true;
        }

        public void clearScalarQueue() {
            SimplePlainQueue simplePlainQueue = this.queue;
            if (simplePlainQueue != null) {
                simplePlainQueue.clear();
            }
        }

        public void disposeAll() {
            AtomicReference atomicReference = this.subscribers;
            InnerSubscriber[] innerSubscriberArr = CANCELLED;
            InnerSubscriber[] innerSubscriberArr2 = (InnerSubscriber[]) atomicReference.getAndSet(innerSubscriberArr);
            if (innerSubscriberArr2 != innerSubscriberArr) {
                for (InnerSubscriber innerSubscriber : innerSubscriberArr2) {
                    innerSubscriber.dispose();
                }
                this.errors.tryTerminateAndReport();
            }
        }

        public void drain() {
            if (getAndIncrement() == 0) {
                drainLoop();
            }
        }

        /* JADX WARN: Code duplicated, block: B:102:0x015d  */
        /* JADX WARN: Code duplicated, block: B:106:0x0167  */
        /* JADX WARN: Code duplicated, block: B:108:0x016d  */
        /* JADX WARN: Code duplicated, block: B:124:0x00e1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:129:0x01a5 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:130:0x01a5 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:132:0x01a5 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:141:0x0175 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:142:0x0165 A[EDGE_INSN: B:142:0x0165->B:105:0x0165 BREAK  A[LOOP:2: B:56:0x00b9->B:109:0x016e], SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:145:0x016e A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:148:0x00ca A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:150:0x0117 A[EDGE_INSN: B:150:0x0117->B:82:0x0117 BREAK  A[LOOP:4: B:65:0x00d5->B:73:0x00e8], SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:57:0x00bb  */
        /* JADX WARN: Code duplicated, block: B:60:0x00c3  */
        /* JADX WARN: Code duplicated, block: B:64:0x00d1  */
        /* JADX WARN: Code duplicated, block: B:67:0x00d9  */
        /* JADX WARN: Code duplicated, block: B:73:0x00e8 A[LOOP:4: B:65:0x00d5->B:73:0x00e8, LOOP_END] */
        /* JADX WARN: Code duplicated, block: B:84:0x011b A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:85:0x011d  */
        /* JADX WARN: Code duplicated, block: B:86:0x0128  */
        /* JADX WARN: Code duplicated, block: B:88:0x0135  */
        public void drainLoop() {
            boolean z;
            long j;
            int i;
            long j2;
            boolean z2;
            long j3;
            int i2;
            int i3;
            boolean z3;
            InnerSubscriber innerSubscriber;
            Object objPoll;
            SimpleQueue simpleQueue;
            int i4;
            boolean z4;
            long j4;
            long jAddAndGet;
            Subscriber subscriber = this.downstream;
            int iAddAndGet = 1;
            while (!checkTerminate()) {
                SimplePlainQueue simplePlainQueue = this.queue;
                long jAddAndGet2 = this.requested.get();
                boolean z5 = jAddAndGet2 == Long.MAX_VALUE;
                if (simplePlainQueue != null) {
                    long j5 = 0;
                    j = 0;
                    while (true) {
                        if (jAddAndGet2 == 0) {
                            z = true;
                            break;
                        }
                        z = true;
                        Object objPoll2 = simplePlainQueue.poll();
                        if (checkTerminate()) {
                            return;
                        }
                        if (objPoll2 == null) {
                            break;
                        }
                        subscriber.onNext(objPoll2);
                        j++;
                        j5++;
                        jAddAndGet2--;
                    }
                    if (j5 != 0) {
                        jAddAndGet2 = z5 ? Long.MAX_VALUE : this.requested.addAndGet(-j5);
                    }
                } else {
                    z = true;
                    j = 0;
                }
                boolean z6 = this.done;
                SimplePlainQueue simplePlainQueue2 = this.queue;
                InnerSubscriber[] innerSubscriberArr = (InnerSubscriber[]) this.subscribers.get();
                int length = innerSubscriberArr.length;
                if (z6 && ((simplePlainQueue2 == null || simplePlainQueue2.isEmpty()) && length == 0)) {
                    this.errors.tryTerminateConsumer(this.downstream);
                    return;
                }
                if (length != 0) {
                    long j6 = this.lastId;
                    int i5 = this.lastIndex;
                    if (length > i5) {
                        j2 = 0;
                        if (innerSubscriberArr[i5].id != j6) {
                        }
                        j3 = jAddAndGet2;
                        i2 = 0;
                        i3 = i5;
                        z3 = false;
                        while (true) {
                            if (i2 < length) {
                                i = iAddAndGet;
                                innerSubscriberArr = innerSubscriberArr;
                                break;
                            }
                            if (checkTerminate()) {
                                return;
                            }
                            innerSubscriber = innerSubscriberArr[i3];
                            objPoll = null;
                            while (true) {
                                simpleQueue = innerSubscriber.queue;
                                if (simpleQueue != null) {
                                    i = iAddAndGet;
                                    i2 = i2;
                                    innerSubscriberArr = innerSubscriberArr;
                                    break;
                                }
                                i = iAddAndGet;
                                j4 = j2;
                                while (j3 != j2) {
                                    if (checkTerminate()) {
                                        return;
                                    }
                                    try {
                                        objPoll = simpleQueue.poll();
                                        if (objPoll == null) {
                                            break;
                                        }
                                        subscriber.onNext(objPoll);
                                        j3--;
                                        j4++;
                                    } catch (Throwable th) {
                                        Exceptions.throwIfFatal(th);
                                        innerSubscriber.dispose();
                                        this.errors.tryAddThrowableOrReport(th);
                                        if (!this.delayErrors) {
                                            this.upstream.cancel();
                                        }
                                        if (checkTerminate()) {
                                            return;
                                        }
                                        removeInner(innerSubscriber);
                                        i4 = i2 + 1;
                                        innerSubscriberArr = innerSubscriberArr;
                                        z3 = z;
                                    }
                                }
                                if (j4 != j2) {
                                    if (z5) {
                                        jAddAndGet = Long.MAX_VALUE;
                                    } else {
                                        jAddAndGet = this.requested.addAndGet(-j4);
                                    }
                                    innerSubscriber.requestMore(j4);
                                    j3 = jAddAndGet;
                                } else {
                                    i2 = i2;
                                    innerSubscriberArr = innerSubscriberArr;
                                }
                                if (j3 == j2 || objPoll == null) {
                                    break;
                                }
                                innerSubscriberArr = innerSubscriberArr;
                                iAddAndGet = i;
                                i2 = i2;
                            }
                            z4 = innerSubscriber.done;
                            SimpleQueue simpleQueue2 = innerSubscriber.queue;
                            if (z4 && (simpleQueue2 == null || simpleQueue2.isEmpty())) {
                                removeInner(innerSubscriber);
                                if (checkTerminate()) {
                                    return;
                                }
                                j++;
                                z3 = z;
                            }
                            if (j3 == j2) {
                                break;
                            }
                            i3++;
                            i4 = i2;
                            if (i3 == length) {
                                i3 = 0;
                            }
                            i2 = i4 + 1;
                            innerSubscriberArr = innerSubscriberArr;
                            iAddAndGet = i;
                        }
                        z2 = z3;
                        this.lastIndex = i3;
                        this.lastId = innerSubscriberArr[i3].id;
                    } else {
                        j2 = 0;
                    }
                    if (length <= i5) {
                        i5 = 0;
                    }
                    for (int i6 = 0; i6 < length && innerSubscriberArr[i5].id != j6; i6++) {
                        i5++;
                        if (i5 == length) {
                            i5 = 0;
                        }
                    }
                    this.lastIndex = i5;
                    this.lastId = innerSubscriberArr[i5].id;
                    j3 = jAddAndGet2;
                    i2 = 0;
                    i3 = i5;
                    z3 = false;
                    while (true) {
                        if (i2 < length) {
                            i = iAddAndGet;
                            innerSubscriberArr = innerSubscriberArr;
                            break;
                        }
                        if (checkTerminate()) {
                            return;
                        }
                        innerSubscriber = innerSubscriberArr[i3];
                        objPoll = null;
                        while (true) {
                            simpleQueue = innerSubscriber.queue;
                            if (simpleQueue != null) {
                                i = iAddAndGet;
                                j4 = j2;
                                while (j3 != j2) {
                                    if (checkTerminate()) {
                                        return;
                                    }
                                    objPoll = simpleQueue.poll();
                                    if (objPoll == null) {
                                        break;
                                        break;
                                    } else {
                                        subscriber.onNext(objPoll);
                                        j3--;
                                        j4++;
                                    }
                                }
                                if (j4 != j2) {
                                    if (z5) {
                                        jAddAndGet = this.requested.addAndGet(-j4);
                                    } else {
                                        jAddAndGet = Long.MAX_VALUE;
                                    }
                                    innerSubscriber.requestMore(j4);
                                    j3 = jAddAndGet;
                                } else {
                                    i2 = i2;
                                    innerSubscriberArr = innerSubscriberArr;
                                }
                                if (j3 == j2) {
                                    break;
                                }
                                break;
                                break;
                            }
                            i = iAddAndGet;
                            i2 = i2;
                            innerSubscriberArr = innerSubscriberArr;
                            break;
                            innerSubscriberArr = innerSubscriberArr;
                            iAddAndGet = i;
                            i2 = i2;
                        }
                        z4 = innerSubscriber.done;
                        SimpleQueue simpleQueue3 = innerSubscriber.queue;
                        if (z4) {
                            removeInner(innerSubscriber);
                            if (checkTerminate()) {
                                return;
                            }
                            j++;
                            z3 = z;
                        }
                        if (j3 == j2) {
                            break;
                            break;
                        }
                        i3++;
                        i4 = i2;
                        if (i3 == length) {
                            i3 = 0;
                        }
                        i2 = i4 + 1;
                        innerSubscriberArr = innerSubscriberArr;
                        iAddAndGet = i;
                    }
                    z2 = z3;
                    this.lastIndex = i3;
                    this.lastId = innerSubscriberArr[i3].id;
                } else {
                    i = iAddAndGet;
                    j2 = 0;
                    z2 = false;
                }
                long j7 = j;
                if (j7 != j2 && !this.cancelled) {
                    this.upstream.request(j7);
                }
                if (z2) {
                    iAddAndGet = i;
                } else {
                    iAddAndGet = addAndGet(-i);
                    if (iAddAndGet == 0) {
                        return;
                    }
                }
            }
        }

        public SimpleQueue getMainQueue() {
            SimplePlainQueue spscLinkedArrayQueue = this.queue;
            if (spscLinkedArrayQueue == null) {
                spscLinkedArrayQueue = this.maxConcurrency == Integer.MAX_VALUE ? new SpscLinkedArrayQueue(this.bufferSize) : new SpscArrayQueue(this.maxConcurrency);
                this.queue = spscLinkedArrayQueue;
            }
            return spscLinkedArrayQueue;
        }

        public void innerError(InnerSubscriber innerSubscriber, Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                innerSubscriber.done = true;
                if (!this.delayErrors) {
                    this.upstream.cancel();
                    for (InnerSubscriber innerSubscriber2 : (InnerSubscriber[]) this.subscribers.getAndSet(CANCELLED)) {
                        innerSubscriber2.dispose();
                    }
                }
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (this.errors.tryAddThrowableOrReport(th)) {
                this.done = true;
                if (!this.delayErrors) {
                    for (InnerSubscriber innerSubscriber : (InnerSubscriber[]) this.subscribers.getAndSet(CANCELLED)) {
                        innerSubscriber.dispose();
                    }
                }
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (this.done) {
                return;
            }
            try {
                Object objApply = this.mapper.apply(obj);
                Objects.requireNonNull(objApply, "The mapper returned a null Publisher");
                Publisher publisher = (Publisher) objApply;
                if (!(publisher instanceof Supplier)) {
                    int i = this.bufferSize;
                    long j = this.uniqueId;
                    this.uniqueId = 1 + j;
                    InnerSubscriber innerSubscriber = new InnerSubscriber(this, i, j);
                    if (addInner(innerSubscriber)) {
                        publisher.subscribe(innerSubscriber);
                        return;
                    }
                    return;
                }
                try {
                    Object obj2 = ((Supplier) publisher).get();
                    if (obj2 != null) {
                        tryEmitScalar(obj2);
                        return;
                    }
                    if (this.maxConcurrency == Integer.MAX_VALUE || this.cancelled) {
                        return;
                    }
                    int i2 = this.scalarEmitted + 1;
                    this.scalarEmitted = i2;
                    int i3 = this.scalarLimit;
                    if (i2 == i3) {
                        this.scalarEmitted = 0;
                        this.upstream.request(i3);
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.errors.tryAddThrowableOrReport(th);
                    drain();
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.upstream.cancel();
                onError(th2);
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                if (this.cancelled) {
                    return;
                }
                int i = this.maxConcurrency;
                if (i == Integer.MAX_VALUE) {
                    subscription.request(Long.MAX_VALUE);
                } else {
                    subscription.request(i);
                }
            }
        }

        public void removeInner(InnerSubscriber innerSubscriber) {
            InnerSubscriber[] innerSubscriberArr;
            InnerSubscriber[] innerSubscriberArr2;
            do {
                innerSubscriberArr = (InnerSubscriber[]) this.subscribers.get();
                int length = innerSubscriberArr.length;
                if (length == 0) {
                    return;
                }
                int i = 0;
                while (true) {
                    if (i >= length) {
                        i = -1;
                        break;
                    } else if (innerSubscriberArr[i] == innerSubscriber) {
                        break;
                    } else {
                        i++;
                    }
                }
                if (i < 0) {
                    return;
                }
                if (length == 1) {
                    innerSubscriberArr2 = EMPTY;
                } else {
                    InnerSubscriber[] innerSubscriberArr3 = new InnerSubscriber[length - 1];
                    System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr3, 0, i);
                    System.arraycopy(innerSubscriberArr, i + 1, innerSubscriberArr3, i, (length - i) - 1);
                    innerSubscriberArr2 = innerSubscriberArr3;
                }
            } while (!this.subscribers.compareAndSet(innerSubscriberArr, innerSubscriberArr2));
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                drain();
            }
        }

        public void tryEmit(Object obj, InnerSubscriber innerSubscriber) {
            if (get() == 0 && compareAndSet(0, 1)) {
                long j = this.requested.get();
                SimpleQueue spscArrayQueue = innerSubscriber.queue;
                if (j == 0 || !(spscArrayQueue == null || spscArrayQueue.isEmpty())) {
                    if (spscArrayQueue == null) {
                        spscArrayQueue = new SpscArrayQueue(this.bufferSize);
                        innerSubscriber.queue = spscArrayQueue;
                    }
                    if (!spscArrayQueue.offer(obj)) {
                        onError(new QueueOverflowException());
                    }
                } else {
                    this.downstream.onNext(obj);
                    if (j != Long.MAX_VALUE) {
                        this.requested.decrementAndGet();
                    }
                    innerSubscriber.requestMore(1L);
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else {
                SimpleQueue spscArrayQueue2 = innerSubscriber.queue;
                if (spscArrayQueue2 == null) {
                    spscArrayQueue2 = new SpscArrayQueue(this.bufferSize);
                    innerSubscriber.queue = spscArrayQueue2;
                }
                if (!spscArrayQueue2.offer(obj)) {
                    onError(new QueueOverflowException());
                    return;
                } else if (getAndIncrement() != 0) {
                    return;
                }
            }
            drainLoop();
        }

        public void tryEmitScalar(Object obj) {
            if (get() == 0 && compareAndSet(0, 1)) {
                long j = this.requested.get();
                SimpleQueue mainQueue = this.queue;
                if (j == 0 || !(mainQueue == null || mainQueue.isEmpty())) {
                    if (mainQueue == null) {
                        mainQueue = getMainQueue();
                    }
                    if (!mainQueue.offer(obj)) {
                        onError(new QueueOverflowException());
                    }
                } else {
                    this.downstream.onNext(obj);
                    if (j != Long.MAX_VALUE) {
                        this.requested.decrementAndGet();
                    }
                    if (this.maxConcurrency != Integer.MAX_VALUE && !this.cancelled) {
                        int i = this.scalarEmitted + 1;
                        this.scalarEmitted = i;
                        int i2 = this.scalarLimit;
                        if (i == i2) {
                            this.scalarEmitted = 0;
                            this.upstream.request(i2);
                        }
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else if (!getMainQueue().offer(obj)) {
                onError(new QueueOverflowException());
                return;
            } else if (getAndIncrement() != 0) {
                return;
            }
            drainLoop();
        }
    }

    public static FlowableSubscriber subscribe(Subscriber subscriber, Function function, boolean z, int i, int i2) {
        return new MergeSubscriber(subscriber, function, z, i, i2);
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        if (FlowableScalarXMap.tryScalarXMapSubscribe(this.source, subscriber, this.mapper)) {
            return;
        }
        this.source.subscribe(subscribe(subscriber, this.mapper, this.delayErrors, this.maxConcurrency, this.bufferSize));
    }
}
