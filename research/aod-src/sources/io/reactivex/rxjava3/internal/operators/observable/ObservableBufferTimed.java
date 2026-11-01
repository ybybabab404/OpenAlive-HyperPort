package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.observers.QueueDrainObserver;
import io.reactivex.rxjava3.internal.queue.MpscLinkedQueue;
import io.reactivex.rxjava3.internal.util.QueueDrainHelper;
import io.reactivex.rxjava3.observers.SerializedObserver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableBufferTimed<T, U extends Collection<? super T>> extends AbstractObservableWithUpstream<T, U> {
    public final Supplier bufferSupplier;
    public final int maxSize;
    public final boolean restartTimerOnMaxSize;
    public final Scheduler scheduler;
    public final long timeskip;
    public final long timespan;
    public final TimeUnit unit;

    public static final class BufferExactBoundedObserver<T, U extends Collection<? super T>> extends QueueDrainObserver<T, U, U> implements Runnable, Disposable {
        public Collection buffer;
        public final Supplier bufferSupplier;
        public long consumerIndex;
        public final int maxSize;
        public long producerIndex;
        public final boolean restartTimerOnMaxSize;
        public Disposable timer;
        public final long timespan;
        public final TimeUnit unit;
        public Disposable upstream;
        public final Scheduler.Worker w;

        public BufferExactBoundedObserver(Observer observer, Supplier supplier, long j, TimeUnit timeUnit, int i, boolean z, Scheduler.Worker worker) {
            super(observer, new MpscLinkedQueue());
            this.bufferSupplier = supplier;
            this.timespan = j;
            this.unit = timeUnit;
            this.maxSize = i;
            this.restartTimerOnMaxSize = z;
            this.w = worker;
        }

        @Override // io.reactivex.rxjava3.internal.observers.QueueDrainObserver, io.reactivex.rxjava3.internal.util.ObservableQueueDrain
        public void accept(Observer observer, Collection collection) {
            observer.onNext(collection);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.upstream.dispose();
            this.w.dispose();
            synchronized (this) {
                this.buffer = null;
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            Collection collection;
            this.w.dispose();
            synchronized (this) {
                collection = this.buffer;
                this.buffer = null;
            }
            if (collection != null) {
                this.queue.offer(collection);
                this.done = true;
                if (enter()) {
                    QueueDrainHelper.drainLoop(this.queue, this.downstream, false, this, this);
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            synchronized (this) {
                this.buffer = null;
            }
            this.downstream.onError(th);
            this.w.dispose();
        }

        /* JADX WARN: Bottom block not found for handler: all -> 0x005e */
        /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:43:0x0074
            	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1478)
            	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
            	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
            */
        @Override // io.reactivex.rxjava3.core.Observer
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void onNext(java.lang.Object r8) {
            /*
                r7 = this;
                monitor-enter(r7)
                java.util.Collection r0 = r7.buffer     // Catch: java.lang.Throwable -> L6f
                if (r0 != 0) goto Lb
                monitor-exit(r7)     // Catch: java.lang.Throwable -> L7
                return
            L7:
                r0 = move-exception
                r8 = r0
                r1 = r7
                goto L72
            Lb:
                r0.add(r8)     // Catch: java.lang.Throwable -> L6f
                int r8 = r0.size()     // Catch: java.lang.Throwable -> L6f
                int r1 = r7.maxSize     // Catch: java.lang.Throwable -> L6f
                if (r8 >= r1) goto L18
                monitor-exit(r7)     // Catch: java.lang.Throwable -> L7
                return
            L18:
                r8 = 0
                r7.buffer = r8     // Catch: java.lang.Throwable -> L6f
                long r1 = r7.producerIndex     // Catch: java.lang.Throwable -> L6f
                r3 = 1
                long r1 = r1 + r3
                r7.producerIndex = r1     // Catch: java.lang.Throwable -> L6f
                monitor-exit(r7)     // Catch: java.lang.Throwable -> L6f
                boolean r8 = r7.restartTimerOnMaxSize
                if (r8 == 0) goto L2c
                io.reactivex.rxjava3.disposables.Disposable r8 = r7.timer
                r8.dispose()
            L2c:
                r8 = 0
                r7.fastPathOrderedEmit(r0, r8, r7)
                io.reactivex.rxjava3.functions.Supplier r8 = r7.bufferSupplier     // Catch: java.lang.Throwable -> L60
                java.lang.Object r8 = r8.get()     // Catch: java.lang.Throwable -> L60
                java.lang.String r0 = "The buffer supplied is null"
                java.util.Objects.requireNonNull(r8, r0)     // Catch: java.lang.Throwable -> L60
                java.util.Collection r8 = (java.util.Collection) r8     // Catch: java.lang.Throwable -> L60
                monitor-enter(r7)
                r7.buffer = r8     // Catch: java.lang.Throwable -> L59
                long r0 = r7.consumerIndex     // Catch: java.lang.Throwable -> L59
                long r0 = r0 + r3
                r7.consumerIndex = r0     // Catch: java.lang.Throwable -> L59
                monitor-exit(r7)     // Catch: java.lang.Throwable -> L59
                boolean r8 = r7.restartTimerOnMaxSize
                if (r8 == 0) goto L58
                io.reactivex.rxjava3.core.Scheduler$Worker r0 = r7.w
                long r2 = r7.timespan
                java.util.concurrent.TimeUnit r6 = r7.unit
                r4 = r2
                r1 = r7
                io.reactivex.rxjava3.disposables.Disposable r7 = r0.schedulePeriodically(r1, r2, r4, r6)
                r1.timer = r7
            L58:
                return
            L59:
                r0 = move-exception
                r1 = r7
            L5b:
                r7 = r0
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L5e
                throw r7
            L5e:
                r0 = move-exception
                goto L5b
            L60:
                r0 = move-exception
                r1 = r7
                r7 = r0
                io.reactivex.rxjava3.exceptions.Exceptions.throwIfFatal(r7)
                io.reactivex.rxjava3.core.Observer r8 = r1.downstream
                r8.onError(r7)
                r1.dispose()
                return
            L6f:
                r0 = move-exception
                r1 = r7
            L71:
                r8 = r0
            L72:
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L74
                throw r8
            L74:
                r0 = move-exception
                goto L71
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.observable.ObservableBufferTimed.BufferExactBoundedObserver.onNext(java.lang.Object):void");
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                try {
                    Object obj = this.bufferSupplier.get();
                    Objects.requireNonNull(obj, "The buffer supplied is null");
                    this.buffer = (Collection) obj;
                    this.downstream.onSubscribe(this);
                    Scheduler.Worker worker = this.w;
                    long j = this.timespan;
                    this.timer = worker.schedulePeriodically(this, j, j, this.unit);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    disposable.dispose();
                    EmptyDisposable.error(th, this.downstream);
                    this.w.dispose();
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                Object obj = this.bufferSupplier.get();
                Objects.requireNonNull(obj, "The bufferSupplier returned a null buffer");
                Collection collection = (Collection) obj;
                synchronized (this) {
                    Collection collection2 = this.buffer;
                    if (collection2 != null && this.producerIndex == this.consumerIndex) {
                        this.buffer = collection;
                        fastPathOrderedEmit(collection2, false, this);
                    }
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                dispose();
                this.downstream.onError(th);
            }
        }
    }

    public static final class BufferExactUnboundedObserver<T, U extends Collection<? super T>> extends QueueDrainObserver<T, U, U> implements Runnable, Disposable {
        public Collection buffer;
        public final Supplier bufferSupplier;
        public final Scheduler scheduler;
        public final AtomicReference timer;
        public final long timespan;
        public final TimeUnit unit;
        public Disposable upstream;

        public BufferExactUnboundedObserver(Observer observer, Supplier supplier, long j, TimeUnit timeUnit, Scheduler scheduler) {
            super(observer, new MpscLinkedQueue());
            this.timer = new AtomicReference();
            this.bufferSupplier = supplier;
            this.timespan = j;
            this.unit = timeUnit;
            this.scheduler = scheduler;
        }

        @Override // io.reactivex.rxjava3.internal.observers.QueueDrainObserver, io.reactivex.rxjava3.internal.util.ObservableQueueDrain
        public void accept(Observer observer, Collection collection) {
            this.downstream.onNext(collection);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.timer);
            this.upstream.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.timer.get() == DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            Collection collection;
            synchronized (this) {
                collection = this.buffer;
                this.buffer = null;
            }
            if (collection != null) {
                this.queue.offer(collection);
                this.done = true;
                if (enter()) {
                    QueueDrainHelper.drainLoop(this.queue, this.downstream, false, null, this);
                }
            }
            DisposableHelper.dispose(this.timer);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            synchronized (this) {
                this.buffer = null;
            }
            this.downstream.onError(th);
            DisposableHelper.dispose(this.timer);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            synchronized (this) {
                try {
                    Collection collection = this.buffer;
                    if (collection == null) {
                        return;
                    }
                    collection.add(obj);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                try {
                    Object obj = this.bufferSupplier.get();
                    Objects.requireNonNull(obj, "The buffer supplied is null");
                    this.buffer = (Collection) obj;
                    this.downstream.onSubscribe(this);
                    if (DisposableHelper.isDisposed((Disposable) this.timer.get())) {
                        return;
                    }
                    Scheduler scheduler = this.scheduler;
                    long j = this.timespan;
                    DisposableHelper.set(this.timer, scheduler.schedulePeriodicallyDirect(this, j, j, this.unit));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    dispose();
                    EmptyDisposable.error(th, this.downstream);
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            Collection collection;
            try {
                Object obj = this.bufferSupplier.get();
                Objects.requireNonNull(obj, "The bufferSupplier returned a null buffer");
                Collection collection2 = (Collection) obj;
                synchronized (this) {
                    try {
                        collection = this.buffer;
                        if (collection != null) {
                            this.buffer = collection2;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (collection == null) {
                    DisposableHelper.dispose(this.timer);
                } else {
                    fastPathEmit(collection, false, this);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.downstream.onError(th2);
                dispose();
            }
        }
    }

    public static final class BufferSkipBoundedObserver<T, U extends Collection<? super T>> extends QueueDrainObserver<T, U, U> implements Runnable, Disposable {
        public final Supplier bufferSupplier;
        public final List buffers;
        public final long timeskip;
        public final long timespan;
        public final TimeUnit unit;
        public Disposable upstream;
        public final Scheduler.Worker w;

        public final class RemoveFromBuffer implements Runnable {
            public final Collection b;

            public RemoveFromBuffer(Collection collection) {
                this.b = collection;
            }

            @Override // java.lang.Runnable
            public void run() {
                synchronized (BufferSkipBoundedObserver.this) {
                    BufferSkipBoundedObserver.this.buffers.remove(this.b);
                }
                BufferSkipBoundedObserver bufferSkipBoundedObserver = BufferSkipBoundedObserver.this;
                bufferSkipBoundedObserver.fastPathOrderedEmit(this.b, false, bufferSkipBoundedObserver.w);
            }
        }

        public final class RemoveFromBufferEmit implements Runnable {
            public final Collection buffer;

            public RemoveFromBufferEmit(Collection collection) {
                this.buffer = collection;
            }

            @Override // java.lang.Runnable
            public void run() {
                synchronized (BufferSkipBoundedObserver.this) {
                    BufferSkipBoundedObserver.this.buffers.remove(this.buffer);
                }
                BufferSkipBoundedObserver bufferSkipBoundedObserver = BufferSkipBoundedObserver.this;
                bufferSkipBoundedObserver.fastPathOrderedEmit(this.buffer, false, bufferSkipBoundedObserver.w);
            }
        }

        public BufferSkipBoundedObserver(Observer observer, Supplier supplier, long j, long j2, TimeUnit timeUnit, Scheduler.Worker worker) {
            super(observer, new MpscLinkedQueue());
            this.bufferSupplier = supplier;
            this.timespan = j;
            this.timeskip = j2;
            this.unit = timeUnit;
            this.w = worker;
            this.buffers = new LinkedList();
        }

        @Override // io.reactivex.rxjava3.internal.observers.QueueDrainObserver, io.reactivex.rxjava3.internal.util.ObservableQueueDrain
        public void accept(Observer observer, Collection collection) {
            observer.onNext(collection);
        }

        public void clear() {
            synchronized (this) {
                this.buffers.clear();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            clear();
            this.upstream.dispose();
            this.w.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            ArrayList arrayList;
            synchronized (this) {
                arrayList = new ArrayList(this.buffers);
                this.buffers.clear();
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.queue.offer((Collection) it.next());
            }
            this.done = true;
            if (enter()) {
                QueueDrainHelper.drainLoop(this.queue, this.downstream, false, this.w, this);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            this.done = true;
            clear();
            this.downstream.onError(th);
            this.w.dispose();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            synchronized (this) {
                try {
                    Iterator it = this.buffers.iterator();
                    while (it.hasNext()) {
                        ((Collection) it.next()).add(obj);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                try {
                    Object obj = this.bufferSupplier.get();
                    Objects.requireNonNull(obj, "The buffer supplied is null");
                    Collection collection = (Collection) obj;
                    this.buffers.add(collection);
                    this.downstream.onSubscribe(this);
                    Scheduler.Worker worker = this.w;
                    long j = this.timeskip;
                    worker.schedulePeriodically(this, j, j, this.unit);
                    this.w.schedule(new RemoveFromBufferEmit(collection), this.timespan, this.unit);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    disposable.dispose();
                    EmptyDisposable.error(th, this.downstream);
                    this.w.dispose();
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.cancelled) {
                return;
            }
            try {
                Object obj = this.bufferSupplier.get();
                Objects.requireNonNull(obj, "The bufferSupplier returned a null buffer");
                Collection collection = (Collection) obj;
                synchronized (this) {
                    try {
                        if (this.cancelled) {
                            return;
                        }
                        this.buffers.add(collection);
                        this.w.schedule(new RemoveFromBuffer(collection), this.timespan, this.unit);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.downstream.onError(th2);
                dispose();
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        if (this.timespan == this.timeskip && this.maxSize == Integer.MAX_VALUE) {
            this.source.subscribe(new BufferExactUnboundedObserver(new SerializedObserver(observer), this.bufferSupplier, this.timespan, this.unit, this.scheduler));
            return;
        }
        Scheduler.Worker workerCreateWorker = this.scheduler.createWorker();
        if (this.timespan == this.timeskip) {
            this.source.subscribe(new BufferExactBoundedObserver(new SerializedObserver(observer), this.bufferSupplier, this.timespan, this.unit, this.maxSize, this.restartTimerOnMaxSize, workerCreateWorker));
        } else {
            this.source.subscribe(new BufferSkipBoundedObserver(new SerializedObserver(observer), this.bufferSupplier, this.timespan, this.timeskip, this.unit, workerCreateWorker));
        }
    }
}
