package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import io.reactivex.rxjava3.internal.queue.MpscLinkedQueue;
import io.reactivex.rxjava3.operators.SimplePlainQueue;
import io.reactivex.rxjava3.subjects.UnicastSubject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableWindowTimed<T> extends AbstractObservableWithUpstream<T, Observable<T>> {
    public final int bufferSize;
    public final long maxSize;
    public final boolean restartTimerOnMaxSize;
    public final Scheduler scheduler;
    public final long timeskip;
    public final long timespan;
    public final TimeUnit unit;

    public static abstract class AbstractWindowObserver<T> extends AtomicInteger implements Observer<T>, Disposable {
        public final int bufferSize;
        public volatile boolean done;
        public final Observer downstream;
        public long emitted;
        public Throwable error;
        public final long timespan;
        public final TimeUnit unit;
        public Disposable upstream;
        public volatile boolean upstreamCancelled;
        public final SimplePlainQueue queue = new MpscLinkedQueue();
        public final AtomicBoolean downstreamCancelled = new AtomicBoolean();
        public final AtomicInteger windowCount = new AtomicInteger(1);

        public AbstractWindowObserver(Observer observer, long j, TimeUnit timeUnit, int i) {
            this.downstream = observer;
            this.timespan = j;
            this.unit = timeUnit;
            this.bufferSize = i;
        }

        public abstract void cleanupResources();

        public abstract void createFirstWindow();

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public final void dispose() {
            if (this.downstreamCancelled.compareAndSet(false, true)) {
                windowDone();
            }
        }

        public abstract void drain();

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public final boolean isDisposed() {
            return this.downstreamCancelled.get();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public final void onComplete() {
            this.done = true;
            drain();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public final void onError(Throwable th) {
            this.error = th;
            this.done = true;
            drain();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public final void onNext(Object obj) {
            this.queue.offer(obj);
            drain();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public final void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
                createFirstWindow();
            }
        }

        public final void windowDone() {
            if (this.windowCount.decrementAndGet() == 0) {
                cleanupResources();
                this.upstream.dispose();
                this.upstreamCancelled = true;
                drain();
            }
        }
    }

    public static final class WindowExactBoundedObserver<T> extends AbstractWindowObserver<T> implements Runnable {
        public long count;
        public final long maxSize;
        public final boolean restartTimerOnMaxSize;
        public final Scheduler scheduler;
        public final SequentialDisposable timer;
        public UnicastSubject window;
        public final Scheduler.Worker worker;

        public static final class WindowBoundaryRunnable implements Runnable {
            public final long index;
            public final WindowExactBoundedObserver parent;

            public WindowBoundaryRunnable(WindowExactBoundedObserver windowExactBoundedObserver, long j) {
                this.parent = windowExactBoundedObserver;
                this.index = j;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.parent.boundary(this);
            }
        }

        public WindowExactBoundedObserver(Observer observer, long j, TimeUnit timeUnit, Scheduler scheduler, int i, long j2, boolean z) {
            super(observer, j, timeUnit, i);
            this.scheduler = scheduler;
            this.maxSize = j2;
            this.restartTimerOnMaxSize = z;
            if (z) {
                this.worker = scheduler.createWorker();
            } else {
                this.worker = null;
            }
            this.timer = new SequentialDisposable();
        }

        public void boundary(WindowBoundaryRunnable windowBoundaryRunnable) {
            this.queue.offer(windowBoundaryRunnable);
            drain();
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableWindowTimed.AbstractWindowObserver
        public void cleanupResources() {
            this.timer.dispose();
            Scheduler.Worker worker = this.worker;
            if (worker != null) {
                worker.dispose();
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableWindowTimed.AbstractWindowObserver
        public void createFirstWindow() {
            if (this.downstreamCancelled.get()) {
                return;
            }
            this.emitted = 1L;
            this.windowCount.getAndIncrement();
            UnicastSubject unicastSubjectCreate = UnicastSubject.create(this.bufferSize, this);
            this.window = unicastSubjectCreate;
            ObservableWindowSubscribeIntercept observableWindowSubscribeIntercept = new ObservableWindowSubscribeIntercept(unicastSubjectCreate);
            this.downstream.onNext(observableWindowSubscribeIntercept);
            WindowBoundaryRunnable windowBoundaryRunnable = new WindowBoundaryRunnable(this, 1L);
            if (this.restartTimerOnMaxSize) {
                SequentialDisposable sequentialDisposable = this.timer;
                Scheduler.Worker worker = this.worker;
                long j = this.timespan;
                sequentialDisposable.replace(worker.schedulePeriodically(windowBoundaryRunnable, j, j, this.unit));
            } else {
                SequentialDisposable sequentialDisposable2 = this.timer;
                Scheduler scheduler = this.scheduler;
                long j2 = this.timespan;
                sequentialDisposable2.replace(scheduler.schedulePeriodicallyDirect(windowBoundaryRunnable, j2, j2, this.unit));
            }
            if (observableWindowSubscribeIntercept.tryAbandon()) {
                this.window.onComplete();
            }
        }

        public UnicastSubject createNewWindow(UnicastSubject unicastSubject) {
            if (unicastSubject != null) {
                unicastSubject.onComplete();
                unicastSubject = null;
            }
            if (this.downstreamCancelled.get()) {
                cleanupResources();
                return unicastSubject;
            }
            long j = this.emitted + 1;
            this.emitted = j;
            this.windowCount.getAndIncrement();
            UnicastSubject unicastSubjectCreate = UnicastSubject.create(this.bufferSize, this);
            this.window = unicastSubjectCreate;
            ObservableWindowSubscribeIntercept observableWindowSubscribeIntercept = new ObservableWindowSubscribeIntercept(unicastSubjectCreate);
            this.downstream.onNext(observableWindowSubscribeIntercept);
            if (this.restartTimerOnMaxSize) {
                SequentialDisposable sequentialDisposable = this.timer;
                Scheduler.Worker worker = this.worker;
                WindowBoundaryRunnable windowBoundaryRunnable = new WindowBoundaryRunnable(this, j);
                long j2 = this.timespan;
                sequentialDisposable.update(worker.schedulePeriodically(windowBoundaryRunnable, j2, j2, this.unit));
            }
            if (observableWindowSubscribeIntercept.tryAbandon()) {
                unicastSubjectCreate.onComplete();
            }
            return unicastSubjectCreate;
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableWindowTimed.AbstractWindowObserver
        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            SimplePlainQueue simplePlainQueue = this.queue;
            Observer observer = this.downstream;
            UnicastSubject unicastSubjectCreateNewWindow = this.window;
            int iAddAndGet = 1;
            while (true) {
                if (this.upstreamCancelled) {
                    simplePlainQueue.clear();
                    unicastSubjectCreateNewWindow = null;
                    this.window = null;
                } else {
                    boolean z = this.done;
                    Object objPoll = simplePlainQueue.poll();
                    boolean z2 = objPoll == null;
                    if (z && z2) {
                        Throwable th = this.error;
                        if (th != null) {
                            if (unicastSubjectCreateNewWindow != null) {
                                unicastSubjectCreateNewWindow.onError(th);
                            }
                            observer.onError(th);
                        } else {
                            if (unicastSubjectCreateNewWindow != null) {
                                unicastSubjectCreateNewWindow.onComplete();
                            }
                            observer.onComplete();
                        }
                        cleanupResources();
                        this.upstreamCancelled = true;
                    } else if (!z2) {
                        if (objPoll instanceof WindowBoundaryRunnable) {
                            if (((WindowBoundaryRunnable) objPoll).index == this.emitted || !this.restartTimerOnMaxSize) {
                                this.count = 0L;
                                unicastSubjectCreateNewWindow = createNewWindow(unicastSubjectCreateNewWindow);
                            }
                        } else if (unicastSubjectCreateNewWindow != null) {
                            unicastSubjectCreateNewWindow.onNext(objPoll);
                            long j = this.count + 1;
                            if (j == this.maxSize) {
                                this.count = 0L;
                                unicastSubjectCreateNewWindow = createNewWindow(unicastSubjectCreateNewWindow);
                            } else {
                                this.count = j;
                            }
                        }
                    }
                }
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            windowDone();
        }
    }

    public static final class WindowExactUnboundedObserver<T> extends AbstractWindowObserver<T> implements Runnable {
        public static final Object NEXT_WINDOW = new Object();
        public final Scheduler scheduler;
        public final SequentialDisposable timer;
        public UnicastSubject window;
        public final Runnable windowRunnable;

        public final class WindowRunnable implements Runnable {
            public WindowRunnable() {
            }

            @Override // java.lang.Runnable
            public void run() {
                WindowExactUnboundedObserver.this.windowDone();
            }
        }

        public WindowExactUnboundedObserver(Observer observer, long j, TimeUnit timeUnit, Scheduler scheduler, int i) {
            super(observer, j, timeUnit, i);
            this.scheduler = scheduler;
            this.timer = new SequentialDisposable();
            this.windowRunnable = new WindowRunnable();
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableWindowTimed.AbstractWindowObserver
        public void cleanupResources() {
            this.timer.dispose();
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableWindowTimed.AbstractWindowObserver
        public void createFirstWindow() {
            if (this.downstreamCancelled.get()) {
                return;
            }
            this.windowCount.getAndIncrement();
            UnicastSubject unicastSubjectCreate = UnicastSubject.create(this.bufferSize, this.windowRunnable);
            this.window = unicastSubjectCreate;
            this.emitted = 1L;
            ObservableWindowSubscribeIntercept observableWindowSubscribeIntercept = new ObservableWindowSubscribeIntercept(unicastSubjectCreate);
            this.downstream.onNext(observableWindowSubscribeIntercept);
            SequentialDisposable sequentialDisposable = this.timer;
            Scheduler scheduler = this.scheduler;
            long j = this.timespan;
            sequentialDisposable.replace(scheduler.schedulePeriodicallyDirect(this, j, j, this.unit));
            if (observableWindowSubscribeIntercept.tryAbandon()) {
                this.window.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableWindowTimed.AbstractWindowObserver
        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            SimplePlainQueue simplePlainQueue = this.queue;
            Observer observer = this.downstream;
            UnicastSubject unicastSubjectCreate = this.window;
            int iAddAndGet = 1;
            while (true) {
                if (this.upstreamCancelled) {
                    simplePlainQueue.clear();
                    this.window = null;
                    unicastSubjectCreate = null;
                } else {
                    boolean z = this.done;
                    Object objPoll = simplePlainQueue.poll();
                    boolean z2 = objPoll == null;
                    if (z && z2) {
                        Throwable th = this.error;
                        if (th != null) {
                            if (unicastSubjectCreate != null) {
                                unicastSubjectCreate.onError(th);
                            }
                            observer.onError(th);
                        } else {
                            if (unicastSubjectCreate != null) {
                                unicastSubjectCreate.onComplete();
                            }
                            observer.onComplete();
                        }
                        cleanupResources();
                        this.upstreamCancelled = true;
                    } else if (!z2) {
                        if (objPoll == NEXT_WINDOW) {
                            if (unicastSubjectCreate != null) {
                                unicastSubjectCreate.onComplete();
                                this.window = null;
                                unicastSubjectCreate = null;
                            }
                            if (this.downstreamCancelled.get()) {
                                this.timer.dispose();
                            } else {
                                this.emitted++;
                                this.windowCount.getAndIncrement();
                                unicastSubjectCreate = UnicastSubject.create(this.bufferSize, this.windowRunnable);
                                this.window = unicastSubjectCreate;
                                ObservableWindowSubscribeIntercept observableWindowSubscribeIntercept = new ObservableWindowSubscribeIntercept(unicastSubjectCreate);
                                observer.onNext(observableWindowSubscribeIntercept);
                                if (observableWindowSubscribeIntercept.tryAbandon()) {
                                    unicastSubjectCreate.onComplete();
                                }
                            }
                        } else if (unicastSubjectCreate != null) {
                            unicastSubjectCreate.onNext(objPoll);
                        }
                    }
                }
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.queue.offer(NEXT_WINDOW);
            drain();
        }
    }

    public static final class WindowSkipObserver<T> extends AbstractWindowObserver<T> implements Runnable {
        public final long timeskip;
        public final List windows;
        public final Scheduler.Worker worker;
        public static final Object WINDOW_OPEN = new Object();
        public static final Object WINDOW_CLOSE = new Object();

        public static final class WindowBoundaryRunnable implements Runnable {
            public final boolean isOpen;
            public final WindowSkipObserver parent;

            public WindowBoundaryRunnable(WindowSkipObserver windowSkipObserver, boolean z) {
                this.parent = windowSkipObserver;
                this.isOpen = z;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.parent.boundary(this.isOpen);
            }
        }

        public WindowSkipObserver(Observer observer, long j, long j2, TimeUnit timeUnit, Scheduler.Worker worker, int i) {
            super(observer, j, timeUnit, i);
            this.timeskip = j2;
            this.worker = worker;
            this.windows = new LinkedList();
        }

        public void boundary(boolean z) {
            this.queue.offer(z ? WINDOW_OPEN : WINDOW_CLOSE);
            drain();
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableWindowTimed.AbstractWindowObserver
        public void cleanupResources() {
            this.worker.dispose();
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableWindowTimed.AbstractWindowObserver
        public void createFirstWindow() {
            if (this.downstreamCancelled.get()) {
                return;
            }
            this.emitted = 1L;
            this.windowCount.getAndIncrement();
            UnicastSubject unicastSubjectCreate = UnicastSubject.create(this.bufferSize, this);
            this.windows.add(unicastSubjectCreate);
            ObservableWindowSubscribeIntercept observableWindowSubscribeIntercept = new ObservableWindowSubscribeIntercept(unicastSubjectCreate);
            this.downstream.onNext(observableWindowSubscribeIntercept);
            this.worker.schedule(new WindowBoundaryRunnable(this, false), this.timespan, this.unit);
            Scheduler.Worker worker = this.worker;
            WindowBoundaryRunnable windowBoundaryRunnable = new WindowBoundaryRunnable(this, true);
            long j = this.timeskip;
            worker.schedulePeriodically(windowBoundaryRunnable, j, j, this.unit);
            if (observableWindowSubscribeIntercept.tryAbandon()) {
                unicastSubjectCreate.onComplete();
                this.windows.remove(unicastSubjectCreate);
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.observable.ObservableWindowTimed.AbstractWindowObserver
        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            SimplePlainQueue simplePlainQueue = this.queue;
            Observer observer = this.downstream;
            List list = this.windows;
            int iAddAndGet = 1;
            while (true) {
                if (this.upstreamCancelled) {
                    simplePlainQueue.clear();
                    list.clear();
                } else {
                    boolean z = this.done;
                    Object objPoll = simplePlainQueue.poll();
                    boolean z2 = objPoll == null;
                    if (z && z2) {
                        Throwable th = this.error;
                        if (th != null) {
                            Iterator it = list.iterator();
                            while (it.hasNext()) {
                                ((UnicastSubject) it.next()).onError(th);
                            }
                            observer.onError(th);
                        } else {
                            Iterator it2 = list.iterator();
                            while (it2.hasNext()) {
                                ((UnicastSubject) it2.next()).onComplete();
                            }
                            observer.onComplete();
                        }
                        cleanupResources();
                        this.upstreamCancelled = true;
                    } else if (!z2) {
                        if (objPoll == WINDOW_OPEN) {
                            if (!this.downstreamCancelled.get()) {
                                this.emitted++;
                                this.windowCount.getAndIncrement();
                                UnicastSubject unicastSubjectCreate = UnicastSubject.create(this.bufferSize, this);
                                list.add(unicastSubjectCreate);
                                ObservableWindowSubscribeIntercept observableWindowSubscribeIntercept = new ObservableWindowSubscribeIntercept(unicastSubjectCreate);
                                observer.onNext(observableWindowSubscribeIntercept);
                                this.worker.schedule(new WindowBoundaryRunnable(this, false), this.timespan, this.unit);
                                if (observableWindowSubscribeIntercept.tryAbandon()) {
                                    unicastSubjectCreate.onComplete();
                                }
                            }
                        } else if (objPoll != WINDOW_CLOSE) {
                            Iterator it3 = list.iterator();
                            while (it3.hasNext()) {
                                ((UnicastSubject) it3.next()).onNext(objPoll);
                            }
                        } else if (!list.isEmpty()) {
                            ((UnicastSubject) list.remove(0)).onComplete();
                        }
                    }
                }
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            windowDone();
        }
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        if (this.timespan != this.timeskip) {
            this.source.subscribe(new WindowSkipObserver(observer, this.timespan, this.timeskip, this.unit, this.scheduler.createWorker(), this.bufferSize));
        } else if (this.maxSize == Long.MAX_VALUE) {
            this.source.subscribe(new WindowExactUnboundedObserver(observer, this.timespan, this.unit, this.scheduler, this.bufferSize));
        } else {
            this.source.subscribe(new WindowExactBoundedObserver(observer, this.timespan, this.unit, this.scheduler, this.bufferSize, this.maxSize, this.restartTimerOnMaxSize));
        }
    }
}
