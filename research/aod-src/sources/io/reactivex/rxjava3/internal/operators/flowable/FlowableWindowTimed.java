package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import io.reactivex.rxjava3.internal.queue.MpscLinkedQueue;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.operators.SimplePlainQueue;
import io.reactivex.rxjava3.processors.UnicastProcessor;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableWindowTimed<T> extends AbstractFlowableWithUpstream<T, Flowable<T>> {
    public final int bufferSize;
    public final long maxSize;
    public final boolean restartTimerOnMaxSize;
    public final Scheduler scheduler;
    public final long timeskip;
    public final long timespan;
    public final TimeUnit unit;

    public static abstract class AbstractWindowSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        public final int bufferSize;
        public volatile boolean done;
        public final Subscriber downstream;
        public long emitted;
        public Throwable error;
        public final long timespan;
        public final TimeUnit unit;
        public Subscription upstream;
        public volatile boolean upstreamCancelled;
        public final SimplePlainQueue queue = new MpscLinkedQueue();
        public final AtomicLong requested = new AtomicLong();
        public final AtomicBoolean downstreamCancelled = new AtomicBoolean();
        public final AtomicInteger windowCount = new AtomicInteger(1);

        public AbstractWindowSubscriber(Subscriber subscriber, long j, TimeUnit timeUnit, int i) {
            this.downstream = subscriber;
            this.timespan = j;
            this.unit = timeUnit;
            this.bufferSize = i;
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            if (this.downstreamCancelled.compareAndSet(false, true)) {
                windowDone();
            }
        }

        public abstract void cleanupResources();

        public abstract void createFirstWindow();

        public abstract void drain();

        @Override // org.reactivestreams.Subscriber
        public final void onComplete() {
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onError(Throwable th) {
            this.error = th;
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onNext(Object obj) {
            this.queue.offer(obj);
            drain();
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public final void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                createFirstWindow();
            }
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
            }
        }

        public final void windowDone() {
            if (this.windowCount.decrementAndGet() == 0) {
                cleanupResources();
                this.upstream.cancel();
                this.upstreamCancelled = true;
                drain();
            }
        }
    }

    public static final class WindowExactBoundedSubscriber<T> extends AbstractWindowSubscriber<T> implements Runnable {
        public long count;
        public final long maxSize;
        public final boolean restartTimerOnMaxSize;
        public final Scheduler scheduler;
        public final SequentialDisposable timer;
        public UnicastProcessor window;
        public final Scheduler.Worker worker;

        public static final class WindowBoundaryRunnable implements Runnable {
            public final long index;
            public final WindowExactBoundedSubscriber parent;

            public WindowBoundaryRunnable(WindowExactBoundedSubscriber windowExactBoundedSubscriber, long j) {
                this.parent = windowExactBoundedSubscriber;
                this.index = j;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.parent.boundary(this);
            }
        }

        public WindowExactBoundedSubscriber(Subscriber subscriber, long j, TimeUnit timeUnit, Scheduler scheduler, int i, long j2, boolean z) {
            super(subscriber, j, timeUnit, i);
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

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.AbstractWindowSubscriber
        public void cleanupResources() {
            this.timer.dispose();
            Scheduler.Worker worker = this.worker;
            if (worker != null) {
                worker.dispose();
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.AbstractWindowSubscriber
        public void createFirstWindow() {
            if (this.downstreamCancelled.get()) {
                return;
            }
            if (this.requested.get() == 0) {
                this.upstream.cancel();
                this.downstream.onError(FlowableWindowTimed.missingBackpressureMessage(this.emitted));
                cleanupResources();
                this.upstreamCancelled = true;
                return;
            }
            this.emitted = 1L;
            this.windowCount.getAndIncrement();
            this.window = UnicastProcessor.create(this.bufferSize, this);
            FlowableWindowSubscribeIntercept flowableWindowSubscribeIntercept = new FlowableWindowSubscribeIntercept(this.window);
            this.downstream.onNext(flowableWindowSubscribeIntercept);
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
            if (flowableWindowSubscribeIntercept.tryAbandon()) {
                this.window.onComplete();
            }
            this.upstream.request(Long.MAX_VALUE);
        }

        public UnicastProcessor createNewWindow(UnicastProcessor unicastProcessor) {
            if (unicastProcessor != null) {
                unicastProcessor.onComplete();
                unicastProcessor = null;
            }
            if (this.downstreamCancelled.get()) {
                cleanupResources();
                return unicastProcessor;
            }
            long j = this.emitted;
            if (this.requested.get() == j) {
                this.upstream.cancel();
                cleanupResources();
                this.upstreamCancelled = true;
                this.downstream.onError(FlowableWindowTimed.missingBackpressureMessage(j));
                return unicastProcessor;
            }
            long j2 = j + 1;
            this.emitted = j2;
            this.windowCount.getAndIncrement();
            UnicastProcessor unicastProcessorCreate = UnicastProcessor.create(this.bufferSize, this);
            this.window = unicastProcessorCreate;
            FlowableWindowSubscribeIntercept flowableWindowSubscribeIntercept = new FlowableWindowSubscribeIntercept(unicastProcessorCreate);
            this.downstream.onNext(flowableWindowSubscribeIntercept);
            if (this.restartTimerOnMaxSize) {
                SequentialDisposable sequentialDisposable = this.timer;
                Scheduler.Worker worker = this.worker;
                WindowBoundaryRunnable windowBoundaryRunnable = new WindowBoundaryRunnable(this, j2);
                long j3 = this.timespan;
                sequentialDisposable.update(worker.schedulePeriodically(windowBoundaryRunnable, j3, j3, this.unit));
            }
            if (flowableWindowSubscribeIntercept.tryAbandon()) {
                unicastProcessorCreate.onComplete();
            }
            return unicastProcessorCreate;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.AbstractWindowSubscriber
        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            SimplePlainQueue simplePlainQueue = this.queue;
            Subscriber subscriber = this.downstream;
            UnicastProcessor unicastProcessorCreateNewWindow = this.window;
            int iAddAndGet = 1;
            while (true) {
                if (this.upstreamCancelled) {
                    simplePlainQueue.clear();
                    unicastProcessorCreateNewWindow = null;
                    this.window = null;
                } else {
                    boolean z = this.done;
                    Object objPoll = simplePlainQueue.poll();
                    boolean z2 = objPoll == null;
                    if (z && z2) {
                        Throwable th = this.error;
                        if (th != null) {
                            if (unicastProcessorCreateNewWindow != null) {
                                unicastProcessorCreateNewWindow.onError(th);
                            }
                            subscriber.onError(th);
                        } else {
                            if (unicastProcessorCreateNewWindow != null) {
                                unicastProcessorCreateNewWindow.onComplete();
                            }
                            subscriber.onComplete();
                        }
                        cleanupResources();
                        this.upstreamCancelled = true;
                    } else if (!z2) {
                        if (objPoll instanceof WindowBoundaryRunnable) {
                            if (((WindowBoundaryRunnable) objPoll).index == this.emitted || !this.restartTimerOnMaxSize) {
                                this.count = 0L;
                                unicastProcessorCreateNewWindow = createNewWindow(unicastProcessorCreateNewWindow);
                            }
                        } else if (unicastProcessorCreateNewWindow != null) {
                            unicastProcessorCreateNewWindow.onNext(objPoll);
                            long j = this.count + 1;
                            if (j == this.maxSize) {
                                this.count = 0L;
                                unicastProcessorCreateNewWindow = createNewWindow(unicastProcessorCreateNewWindow);
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

    public static final class WindowExactUnboundedSubscriber<T> extends AbstractWindowSubscriber<T> implements Runnable {
        public static final Object NEXT_WINDOW = new Object();
        public final Scheduler scheduler;
        public final SequentialDisposable timer;
        public UnicastProcessor window;
        public final Runnable windowRunnable;

        public final class WindowRunnable implements Runnable {
            public WindowRunnable() {
            }

            @Override // java.lang.Runnable
            public void run() {
                WindowExactUnboundedSubscriber.this.windowDone();
            }
        }

        public WindowExactUnboundedSubscriber(Subscriber subscriber, long j, TimeUnit timeUnit, Scheduler scheduler, int i) {
            super(subscriber, j, timeUnit, i);
            this.scheduler = scheduler;
            this.timer = new SequentialDisposable();
            this.windowRunnable = new WindowRunnable();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.AbstractWindowSubscriber
        public void cleanupResources() {
            this.timer.dispose();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.AbstractWindowSubscriber
        public void createFirstWindow() {
            if (this.downstreamCancelled.get()) {
                return;
            }
            if (this.requested.get() == 0) {
                this.upstream.cancel();
                this.downstream.onError(FlowableWindowTimed.missingBackpressureMessage(this.emitted));
                cleanupResources();
                this.upstreamCancelled = true;
                return;
            }
            this.windowCount.getAndIncrement();
            this.window = UnicastProcessor.create(this.bufferSize, this.windowRunnable);
            this.emitted = 1L;
            FlowableWindowSubscribeIntercept flowableWindowSubscribeIntercept = new FlowableWindowSubscribeIntercept(this.window);
            this.downstream.onNext(flowableWindowSubscribeIntercept);
            SequentialDisposable sequentialDisposable = this.timer;
            Scheduler scheduler = this.scheduler;
            long j = this.timespan;
            sequentialDisposable.replace(scheduler.schedulePeriodicallyDirect(this, j, j, this.unit));
            if (flowableWindowSubscribeIntercept.tryAbandon()) {
                this.window.onComplete();
            }
            this.upstream.request(Long.MAX_VALUE);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.AbstractWindowSubscriber
        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            SimplePlainQueue simplePlainQueue = this.queue;
            Subscriber subscriber = this.downstream;
            UnicastProcessor unicastProcessorCreate = this.window;
            int iAddAndGet = 1;
            while (true) {
                if (this.upstreamCancelled) {
                    simplePlainQueue.clear();
                    this.window = null;
                    unicastProcessorCreate = null;
                } else {
                    boolean z = this.done;
                    Object objPoll = simplePlainQueue.poll();
                    boolean z2 = objPoll == null;
                    if (z && z2) {
                        Throwable th = this.error;
                        if (th != null) {
                            if (unicastProcessorCreate != null) {
                                unicastProcessorCreate.onError(th);
                            }
                            subscriber.onError(th);
                        } else {
                            if (unicastProcessorCreate != null) {
                                unicastProcessorCreate.onComplete();
                            }
                            subscriber.onComplete();
                        }
                        cleanupResources();
                        this.upstreamCancelled = true;
                    } else if (!z2) {
                        if (objPoll == NEXT_WINDOW) {
                            if (unicastProcessorCreate != null) {
                                unicastProcessorCreate.onComplete();
                                this.window = null;
                                unicastProcessorCreate = null;
                            }
                            if (this.downstreamCancelled.get()) {
                                this.timer.dispose();
                            } else {
                                long j = this.requested.get();
                                long j2 = this.emitted;
                                if (j == j2) {
                                    this.upstream.cancel();
                                    cleanupResources();
                                    this.upstreamCancelled = true;
                                    subscriber.onError(FlowableWindowTimed.missingBackpressureMessage(this.emitted));
                                } else {
                                    this.emitted = j2 + 1;
                                    this.windowCount.getAndIncrement();
                                    unicastProcessorCreate = UnicastProcessor.create(this.bufferSize, this.windowRunnable);
                                    this.window = unicastProcessorCreate;
                                    FlowableWindowSubscribeIntercept flowableWindowSubscribeIntercept = new FlowableWindowSubscribeIntercept(unicastProcessorCreate);
                                    subscriber.onNext(flowableWindowSubscribeIntercept);
                                    if (flowableWindowSubscribeIntercept.tryAbandon()) {
                                        unicastProcessorCreate.onComplete();
                                    }
                                }
                            }
                        } else if (unicastProcessorCreate != null) {
                            unicastProcessorCreate.onNext(objPoll);
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

    public static final class WindowSkipSubscriber<T> extends AbstractWindowSubscriber<T> implements Runnable {
        public final long timeskip;
        public final List windows;
        public final Scheduler.Worker worker;
        public static final Object WINDOW_OPEN = new Object();
        public static final Object WINDOW_CLOSE = new Object();

        public static final class WindowBoundaryRunnable implements Runnable {
            public final boolean isOpen;
            public final WindowSkipSubscriber parent;

            public WindowBoundaryRunnable(WindowSkipSubscriber windowSkipSubscriber, boolean z) {
                this.parent = windowSkipSubscriber;
                this.isOpen = z;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.parent.boundary(this.isOpen);
            }
        }

        public WindowSkipSubscriber(Subscriber subscriber, long j, long j2, TimeUnit timeUnit, Scheduler.Worker worker, int i) {
            super(subscriber, j, timeUnit, i);
            this.timeskip = j2;
            this.worker = worker;
            this.windows = new LinkedList();
        }

        public void boundary(boolean z) {
            this.queue.offer(z ? WINDOW_OPEN : WINDOW_CLOSE);
            drain();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.AbstractWindowSubscriber
        public void cleanupResources() {
            this.worker.dispose();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.AbstractWindowSubscriber
        public void createFirstWindow() {
            if (this.downstreamCancelled.get()) {
                return;
            }
            if (this.requested.get() == 0) {
                this.upstream.cancel();
                this.downstream.onError(FlowableWindowTimed.missingBackpressureMessage(this.emitted));
                cleanupResources();
                this.upstreamCancelled = true;
                return;
            }
            this.emitted = 1L;
            this.windowCount.getAndIncrement();
            UnicastProcessor unicastProcessorCreate = UnicastProcessor.create(this.bufferSize, this);
            this.windows.add(unicastProcessorCreate);
            FlowableWindowSubscribeIntercept flowableWindowSubscribeIntercept = new FlowableWindowSubscribeIntercept(unicastProcessorCreate);
            this.downstream.onNext(flowableWindowSubscribeIntercept);
            this.worker.schedule(new WindowBoundaryRunnable(this, false), this.timespan, this.unit);
            Scheduler.Worker worker = this.worker;
            WindowBoundaryRunnable windowBoundaryRunnable = new WindowBoundaryRunnable(this, true);
            long j = this.timeskip;
            worker.schedulePeriodically(windowBoundaryRunnable, j, j, this.unit);
            if (flowableWindowSubscribeIntercept.tryAbandon()) {
                unicastProcessorCreate.onComplete();
                this.windows.remove(unicastProcessorCreate);
            }
            this.upstream.request(Long.MAX_VALUE);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.AbstractWindowSubscriber
        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            SimplePlainQueue simplePlainQueue = this.queue;
            Subscriber subscriber = this.downstream;
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
                                ((UnicastProcessor) it.next()).onError(th);
                            }
                            subscriber.onError(th);
                        } else {
                            Iterator it2 = list.iterator();
                            while (it2.hasNext()) {
                                ((UnicastProcessor) it2.next()).onComplete();
                            }
                            subscriber.onComplete();
                        }
                        cleanupResources();
                        this.upstreamCancelled = true;
                    } else if (!z2) {
                        if (objPoll == WINDOW_OPEN) {
                            if (!this.downstreamCancelled.get()) {
                                long j = this.emitted;
                                if (this.requested.get() != j) {
                                    this.emitted = j + 1;
                                    this.windowCount.getAndIncrement();
                                    UnicastProcessor unicastProcessorCreate = UnicastProcessor.create(this.bufferSize, this);
                                    list.add(unicastProcessorCreate);
                                    FlowableWindowSubscribeIntercept flowableWindowSubscribeIntercept = new FlowableWindowSubscribeIntercept(unicastProcessorCreate);
                                    subscriber.onNext(flowableWindowSubscribeIntercept);
                                    this.worker.schedule(new WindowBoundaryRunnable(this, false), this.timespan, this.unit);
                                    if (flowableWindowSubscribeIntercept.tryAbandon()) {
                                        unicastProcessorCreate.onComplete();
                                    }
                                } else {
                                    this.upstream.cancel();
                                    MissingBackpressureException missingBackpressureExceptionMissingBackpressureMessage = FlowableWindowTimed.missingBackpressureMessage(j);
                                    Iterator it3 = list.iterator();
                                    while (it3.hasNext()) {
                                        ((UnicastProcessor) it3.next()).onError(missingBackpressureExceptionMissingBackpressureMessage);
                                    }
                                    subscriber.onError(missingBackpressureExceptionMissingBackpressureMessage);
                                    cleanupResources();
                                    this.upstreamCancelled = true;
                                }
                            }
                        } else if (objPoll != WINDOW_CLOSE) {
                            Iterator it4 = list.iterator();
                            while (it4.hasNext()) {
                                ((UnicastProcessor) it4.next()).onNext(objPoll);
                            }
                        } else if (!list.isEmpty()) {
                            ((UnicastProcessor) list.remove(0)).onComplete();
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

    public static MissingBackpressureException missingBackpressureMessage(long j) {
        return new MissingBackpressureException("Unable to emit the next window (#" + j + ") due to lack of requests. Please make sure the downstream is ready to consume windows.");
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        if (this.timespan != this.timeskip) {
            this.source.subscribe((FlowableSubscriber) new WindowSkipSubscriber(subscriber, this.timespan, this.timeskip, this.unit, this.scheduler.createWorker(), this.bufferSize));
        } else if (this.maxSize == Long.MAX_VALUE) {
            this.source.subscribe((FlowableSubscriber) new WindowExactUnboundedSubscriber(subscriber, this.timespan, this.unit, this.scheduler, this.bufferSize));
        } else {
            this.source.subscribe((FlowableSubscriber) new WindowExactBoundedSubscriber(subscriber, this.timespan, this.unit, this.scheduler, this.bufferSize, this.maxSize, this.restartTimerOnMaxSize));
        }
    }
}
