package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.queue.MpscLinkedQueue;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.operators.SimplePlainQueue;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.subjects.UnicastSubject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableWindowBoundarySelector<T, B, V> extends AbstractObservableWithUpstream<T, Observable<T>> {
    public final int bufferSize;
    public final Function closingIndicator;
    public final ObservableSource open;

    public static final class WindowBoundaryMainObserver<T, B, V> extends AtomicInteger implements Observer<T>, Disposable, Runnable {
        public final int bufferSize;
        public final Function closingIndicator;
        public final Observer downstream;
        public final ObservableSource open;
        public volatile boolean openDone;
        public Disposable upstream;
        public volatile boolean upstreamCanceled;
        public volatile boolean upstreamDone;
        public final SimplePlainQueue queue = new MpscLinkedQueue();
        public final CompositeDisposable resources = new CompositeDisposable();
        public final List windows = new ArrayList();
        public final AtomicLong windowCount = new AtomicLong(1);
        public final AtomicBoolean downstreamDisposed = new AtomicBoolean();
        public final AtomicThrowable error = new AtomicThrowable();
        public final WindowStartObserver startObserver = new WindowStartObserver(this);
        public final AtomicLong requested = new AtomicLong();

        public static final class WindowEndObserverIntercept<T, V> extends Observable<T> implements Observer<V>, Disposable {
            public final WindowBoundaryMainObserver parent;
            public final UnicastSubject window;
            public final AtomicReference upstream = new AtomicReference();
            public final AtomicBoolean once = new AtomicBoolean();

            public WindowEndObserverIntercept(WindowBoundaryMainObserver windowBoundaryMainObserver, UnicastSubject unicastSubject) {
                this.parent = windowBoundaryMainObserver;
                this.window = unicastSubject;
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public void dispose() {
                DisposableHelper.dispose(this.upstream);
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public boolean isDisposed() {
                return this.upstream.get() == DisposableHelper.DISPOSED;
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
                this.parent.close(this);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable th) {
                if (isDisposed()) {
                    RxJavaPlugins.onError(th);
                } else {
                    this.parent.closeError(th);
                }
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(Object obj) {
                if (DisposableHelper.dispose(this.upstream)) {
                    this.parent.close(this);
                }
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this.upstream, disposable);
            }

            @Override // io.reactivex.rxjava3.core.Observable
            public void subscribeActual(Observer observer) {
                this.window.subscribe(observer);
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

        public static final class WindowStartObserver<B> extends AtomicReference<Disposable> implements Observer<B> {
            public final WindowBoundaryMainObserver parent;

            public WindowStartObserver(WindowBoundaryMainObserver windowBoundaryMainObserver) {
                this.parent = windowBoundaryMainObserver;
            }

            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
                this.parent.openComplete();
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable th) {
                this.parent.openError(th);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(Object obj) {
                this.parent.open(obj);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }
        }

        public WindowBoundaryMainObserver(Observer observer, ObservableSource observableSource, Function function, int i) {
            this.downstream = observer;
            this.open = observableSource;
            this.closingIndicator = function;
            this.bufferSize = i;
        }

        public void close(WindowEndObserverIntercept windowEndObserverIntercept) {
            this.queue.offer(windowEndObserverIntercept);
            drain();
        }

        public void closeError(Throwable th) {
            this.upstream.dispose();
            this.startObserver.dispose();
            this.resources.dispose();
            if (this.error.tryAddThrowableOrReport(th)) {
                this.upstreamDone = true;
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.downstreamDisposed.compareAndSet(false, true)) {
                if (this.windowCount.decrementAndGet() != 0) {
                    this.startObserver.dispose();
                    return;
                }
                this.upstream.dispose();
                this.startObserver.dispose();
                this.resources.dispose();
                this.error.tryTerminateAndReport();
                this.upstreamCanceled = true;
                drain();
            }
        }

        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            Observer observer = this.downstream;
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
                        terminateDownstream(observer);
                        this.upstreamCanceled = true;
                    } else if (z2) {
                        if (this.openDone && list.size() == 0) {
                            this.upstream.dispose();
                            this.startObserver.dispose();
                            this.resources.dispose();
                            terminateDownstream(observer);
                            this.upstreamCanceled = true;
                        }
                    } else if (objPoll instanceof WindowStartItem) {
                        if (!this.downstreamDisposed.get()) {
                            try {
                                Object objApply = this.closingIndicator.apply(((WindowStartItem) objPoll).item);
                                Objects.requireNonNull(objApply, "The closingIndicator returned a null ObservableSource");
                                ObservableSource observableSource = (ObservableSource) objApply;
                                this.windowCount.getAndIncrement();
                                UnicastSubject unicastSubjectCreate = UnicastSubject.create(this.bufferSize, this);
                                WindowEndObserverIntercept windowEndObserverIntercept = new WindowEndObserverIntercept(this, unicastSubjectCreate);
                                observer.onNext(windowEndObserverIntercept);
                                if (windowEndObserverIntercept.tryAbandon()) {
                                    unicastSubjectCreate.onComplete();
                                } else {
                                    list.add(unicastSubjectCreate);
                                    this.resources.add(windowEndObserverIntercept);
                                    observableSource.subscribe(windowEndObserverIntercept);
                                }
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                this.upstream.dispose();
                                this.startObserver.dispose();
                                this.resources.dispose();
                                Exceptions.throwIfFatal(th);
                                this.error.tryAddThrowableOrReport(th);
                                this.upstreamDone = true;
                            }
                        }
                    } else if (objPoll instanceof WindowEndObserverIntercept) {
                        UnicastSubject unicastSubject = ((WindowEndObserverIntercept) objPoll).window;
                        list.remove(unicastSubject);
                        this.resources.delete((Disposable) objPoll);
                        unicastSubject.onComplete();
                    } else {
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            ((UnicastSubject) it.next()).onNext(objPoll);
                        }
                    }
                }
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.downstreamDisposed.get();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.startObserver.dispose();
            this.resources.dispose();
            this.upstreamDone = true;
            drain();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            this.startObserver.dispose();
            this.resources.dispose();
            if (this.error.tryAddThrowableOrReport(th)) {
                this.upstreamDone = true;
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            this.queue.offer(obj);
            drain();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
                this.open.subscribe(this.startObserver);
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
            this.upstream.dispose();
            this.resources.dispose();
            if (this.error.tryAddThrowableOrReport(th)) {
                this.upstreamDone = true;
                drain();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.windowCount.decrementAndGet() == 0) {
                this.upstream.dispose();
                this.startObserver.dispose();
                this.resources.dispose();
                this.error.tryTerminateAndReport();
                this.upstreamCanceled = true;
                drain();
            }
        }

        public void terminateDownstream(Observer observer) {
            Throwable thTerminate = this.error.terminate();
            if (thTerminate == null) {
                Iterator it = this.windows.iterator();
                while (it.hasNext()) {
                    ((UnicastSubject) it.next()).onComplete();
                }
                observer.onComplete();
                return;
            }
            if (thTerminate != ExceptionHelper.TERMINATED) {
                Iterator it2 = this.windows.iterator();
                while (it2.hasNext()) {
                    ((UnicastSubject) it2.next()).onError(thTerminate);
                }
                observer.onError(thTerminate);
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        this.source.subscribe(new WindowBoundaryMainObserver(observer, this.open, this.closingIndicator, this.bufferSize));
    }
}
