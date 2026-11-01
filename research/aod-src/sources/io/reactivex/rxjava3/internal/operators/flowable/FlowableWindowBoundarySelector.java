package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.queue.MpscLinkedQueue;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.operators.SimplePlainQueue;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.processors.UnicastProcessor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableWindowBoundarySelector<T, B, V> extends AbstractFlowableWithUpstream<T, Flowable<T>> {
    public final int bufferSize;
    public final Function closingIndicator;
    public final Publisher open;

    public static final class WindowBoundaryMainSubscriber<T, B, V> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
        public final int bufferSize;
        public final Function closingIndicator;
        public final Subscriber downstream;
        public long emitted;
        public final Publisher open;
        public volatile boolean openDone;
        public Subscription upstream;
        public volatile boolean upstreamCanceled;
        public volatile boolean upstreamDone;
        public final SimplePlainQueue queue = new MpscLinkedQueue();
        public final CompositeDisposable resources = new CompositeDisposable();
        public final List windows = new ArrayList();
        public final AtomicLong windowCount = new AtomicLong(1);
        public final AtomicBoolean downstreamCancelled = new AtomicBoolean();
        public final AtomicThrowable error = new AtomicThrowable();
        public final WindowStartSubscriber startSubscriber = new WindowStartSubscriber(this);
        public final AtomicLong requested = new AtomicLong();

        public static final class WindowEndSubscriberIntercept<T, V> extends Flowable<T> implements FlowableSubscriber<V>, Disposable {
            public final WindowBoundaryMainSubscriber parent;
            public final UnicastProcessor window;
            public final AtomicReference upstream = new AtomicReference();
            public final AtomicBoolean once = new AtomicBoolean();

            public WindowEndSubscriberIntercept(WindowBoundaryMainSubscriber windowBoundaryMainSubscriber, UnicastProcessor unicastProcessor) {
                this.parent = windowBoundaryMainSubscriber;
                this.window = unicastProcessor;
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public void dispose() {
                SubscriptionHelper.cancel(this.upstream);
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public boolean isDisposed() {
                return this.upstream.get() == SubscriptionHelper.CANCELLED;
            }

            @Override // org.reactivestreams.Subscriber
            public void onComplete() {
                this.parent.close(this);
            }

            @Override // org.reactivestreams.Subscriber
            public void onError(Throwable th) {
                if (isDisposed()) {
                    RxJavaPlugins.onError(th);
                } else {
                    this.parent.closeError(th);
                }
            }

            @Override // org.reactivestreams.Subscriber
            public void onNext(Object obj) {
                if (SubscriptionHelper.cancel(this.upstream)) {
                    this.parent.close(this);
                }
            }

            @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
            public void onSubscribe(Subscription subscription) {
                if (SubscriptionHelper.setOnce(this.upstream, subscription)) {
                    subscription.request(Long.MAX_VALUE);
                }
            }

            @Override // io.reactivex.rxjava3.core.Flowable
            public void subscribeActual(Subscriber subscriber) {
                this.window.subscribe(subscriber);
                this.once.set(true);
            }

            public boolean tryAbandon() {
                return !this.once.get() && this.once.compareAndSet(false, true);
            }
        }

        public static final class WindowStartItem<B> {
            public final Object item;

            public WindowStartItem(Object obj) {
                this.item = obj;
            }
        }

        public static final class WindowStartSubscriber<B> extends AtomicReference<Subscription> implements FlowableSubscriber<B> {
            public final WindowBoundaryMainSubscriber parent;

            public WindowStartSubscriber(WindowBoundaryMainSubscriber windowBoundaryMainSubscriber) {
                this.parent = windowBoundaryMainSubscriber;
            }

            public void cancel() {
                SubscriptionHelper.cancel(this);
            }

            @Override // org.reactivestreams.Subscriber
            public void onComplete() {
                this.parent.openComplete();
            }

            @Override // org.reactivestreams.Subscriber
            public void onError(Throwable th) {
                this.parent.openError(th);
            }

            @Override // org.reactivestreams.Subscriber
            public void onNext(Object obj) {
                this.parent.open(obj);
            }

            @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
            public void onSubscribe(Subscription subscription) {
                if (SubscriptionHelper.setOnce(this, subscription)) {
                    subscription.request(Long.MAX_VALUE);
                }
            }
        }

        public WindowBoundaryMainSubscriber(Subscriber subscriber, Publisher publisher, Function function, int i) {
            this.downstream = subscriber;
            this.open = publisher;
            this.closingIndicator = function;
            this.bufferSize = i;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.downstreamCancelled.compareAndSet(false, true)) {
                if (this.windowCount.decrementAndGet() != 0) {
                    this.startSubscriber.cancel();
                    return;
                }
                this.upstream.cancel();
                this.startSubscriber.cancel();
                this.resources.dispose();
                this.error.tryTerminateAndReport();
                this.upstreamCanceled = true;
                drain();
            }
        }

        public void close(WindowEndSubscriberIntercept windowEndSubscriberIntercept) {
            this.queue.offer(windowEndSubscriberIntercept);
            drain();
        }

        public void closeError(Throwable th) {
            this.upstream.cancel();
            this.startSubscriber.cancel();
            this.resources.dispose();
            if (this.error.tryAddThrowableOrReport(th)) {
                this.upstreamDone = true;
                drain();
            }
        }

        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            Subscriber subscriber = this.downstream;
            SimplePlainQueue simplePlainQueue = this.queue;
            List list = this.windows;
            int iAddAndGet = 1;
            while (true) {
                if (this.upstreamCanceled) {
                    simplePlainQueue.clear();
                    list.clear();
                } else {
                    boolean z = this.upstreamDone;
                    Object objPoll = simplePlainQueue.poll();
                    boolean z2 = objPoll == null;
                    if (z && (z2 || this.error.get() != null)) {
                        terminateDownstream(subscriber);
                        this.upstreamCanceled = true;
                    } else if (z2) {
                        if (this.openDone && list.size() == 0) {
                            this.upstream.cancel();
                            this.startSubscriber.cancel();
                            this.resources.dispose();
                            terminateDownstream(subscriber);
                            this.upstreamCanceled = true;
                        }
                    } else if (objPoll instanceof WindowStartItem) {
                        if (!this.downstreamCancelled.get()) {
                            long j = this.emitted;
                            if (this.requested.get() != j) {
                                this.emitted = j + 1;
                                try {
                                    Object objApply = this.closingIndicator.apply(((WindowStartItem) objPoll).item);
                                    Objects.requireNonNull(objApply, "The closingIndicator returned a null Publisher");
                                    Publisher publisher = (Publisher) objApply;
                                    this.windowCount.getAndIncrement();
                                    UnicastProcessor unicastProcessorCreate = UnicastProcessor.create(this.bufferSize, this);
                                    WindowEndSubscriberIntercept windowEndSubscriberIntercept = new WindowEndSubscriberIntercept(this, unicastProcessorCreate);
                                    subscriber.onNext(windowEndSubscriberIntercept);
                                    if (windowEndSubscriberIntercept.tryAbandon()) {
                                        unicastProcessorCreate.onComplete();
                                    } else {
                                        list.add(unicastProcessorCreate);
                                        this.resources.add(windowEndSubscriberIntercept);
                                        publisher.subscribe(windowEndSubscriberIntercept);
                                    }
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    this.upstream.cancel();
                                    this.startSubscriber.cancel();
                                    this.resources.dispose();
                                    Exceptions.throwIfFatal(th);
                                    this.error.tryAddThrowableOrReport(th);
                                    this.upstreamDone = true;
                                }
                            } else {
                                this.upstream.cancel();
                                this.startSubscriber.cancel();
                                this.resources.dispose();
                                this.error.tryAddThrowableOrReport(FlowableWindowTimed.missingBackpressureMessage(j));
                                this.upstreamDone = true;
                            }
                        }
                    } else if (objPoll instanceof WindowEndSubscriberIntercept) {
                        UnicastProcessor unicastProcessor = ((WindowEndSubscriberIntercept) objPoll).window;
                        list.remove(unicastProcessor);
                        this.resources.delete((Disposable) objPoll);
                        unicastProcessor.onComplete();
                    } else {
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            ((UnicastProcessor) it.next()).onNext(objPoll);
                        }
                    }
                }
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.startSubscriber.cancel();
            this.resources.dispose();
            this.upstreamDone = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.startSubscriber.cancel();
            this.resources.dispose();
            if (this.error.tryAddThrowableOrReport(th)) {
                this.upstreamDone = true;
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            this.queue.offer(obj);
            drain();
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                this.open.subscribe(this.startSubscriber);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void open(Object obj) {
            this.queue.offer(new WindowStartItem(obj));
            drain();
        }

        public void openComplete() {
            this.openDone = true;
            drain();
        }

        public void openError(Throwable th) {
            this.upstream.cancel();
            this.resources.dispose();
            if (this.error.tryAddThrowableOrReport(th)) {
                this.upstreamDone = true;
                drain();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.windowCount.decrementAndGet() == 0) {
                this.upstream.cancel();
                this.startSubscriber.cancel();
                this.resources.dispose();
                this.error.tryTerminateAndReport();
                this.upstreamCanceled = true;
                drain();
            }
        }

        public void terminateDownstream(Subscriber subscriber) {
            Throwable thTerminate = this.error.terminate();
            if (thTerminate == null) {
                Iterator it = this.windows.iterator();
                while (it.hasNext()) {
                    ((UnicastProcessor) it.next()).onComplete();
                }
                subscriber.onComplete();
                return;
            }
            if (thTerminate != ExceptionHelper.TERMINATED) {
                Iterator it2 = this.windows.iterator();
                while (it2.hasNext()) {
                    ((UnicastProcessor) it2.next()).onError(thTerminate);
                }
                subscriber.onError(thTerminate);
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        this.source.subscribe((FlowableSubscriber) new WindowBoundaryMainSubscriber(subscriber, this.open, this.closingIndicator, this.bufferSize));
    }
}
