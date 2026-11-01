package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableCombineLatest<T, R> extends Observable<R> {
    public final int bufferSize;
    public final Function combiner;
    public final boolean delayError;
    public final ObservableSource[] sources;
    public final Iterable sourcesIterable;

    public static final class CombinerObserver<T, R> extends AtomicReference<Disposable> implements Observer<T> {
        public final int index;
        public final LatestCoordinator parent;

        public CombinerObserver(LatestCoordinator latestCoordinator, int i) {
            this.parent = latestCoordinator;
            this.index = i;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.parent.innerComplete(this.index);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            this.parent.innerError(this.index, th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            this.parent.innerNext(this.index, obj);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }
    }

    public static final class LatestCoordinator<T, R> extends AtomicInteger implements Disposable {
        public int active;
        public volatile boolean cancelled;
        public final Function combiner;
        public int complete;
        public final boolean delayError;
        public volatile boolean done;
        public final Observer downstream;
        public final AtomicThrowable errors = new AtomicThrowable();
        public Object[] latest;
        public final CombinerObserver[] observers;
        public final SpscLinkedArrayQueue queue;

        public LatestCoordinator(Observer observer, Function function, int i, int i2, boolean z) {
            this.downstream = observer;
            this.combiner = function;
            this.delayError = z;
            this.latest = new Object[i];
            CombinerObserver[] combinerObserverArr = new CombinerObserver[i];
            for (int i3 = 0; i3 < i; i3++) {
                combinerObserverArr[i3] = new CombinerObserver(this, i3);
            }
            this.observers = combinerObserverArr;
            this.queue = new SpscLinkedArrayQueue(i2);
        }

        public void cancelSources() {
            for (CombinerObserver combinerObserver : this.observers) {
                combinerObserver.dispose();
            }
        }

        public void clear(SpscLinkedArrayQueue spscLinkedArrayQueue) {
            synchronized (this) {
                this.latest = null;
            }
            spscLinkedArrayQueue.clear();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            cancelSources();
            drain();
        }

        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            SpscLinkedArrayQueue spscLinkedArrayQueue = this.queue;
            Observer observer = this.downstream;
            boolean z = this.delayError;
            int iAddAndGet = 1;
            while (!this.cancelled) {
                if (!z && this.errors.get() != null) {
                    cancelSources();
                    clear(spscLinkedArrayQueue);
                    this.errors.tryTerminateConsumer(observer);
                    return;
                }
                boolean z2 = this.done;
                Object[] objArr = (Object[]) spscLinkedArrayQueue.poll();
                boolean z3 = objArr == null;
                if (z2 && z3) {
                    clear(spscLinkedArrayQueue);
                    this.errors.tryTerminateConsumer(observer);
                    return;
                }
                if (z3) {
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    try {
                        Object objApply = this.combiner.apply(objArr);
                        Objects.requireNonNull(objApply, "The combiner returned a null value");
                        observer.onNext(objApply);
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.errors.tryAddThrowableOrReport(th);
                        cancelSources();
                        clear(spscLinkedArrayQueue);
                        this.errors.tryTerminateConsumer(observer);
                        return;
                    }
                }
            }
            clear(spscLinkedArrayQueue);
            this.errors.tryTerminateAndReport();
        }

        /* JADX WARN: Code duplicated, block: B:16:0x001b A[Catch: all -> 0x0007, TryCatch #0 {all -> 0x0007, blocks: (B:3:0x0001, B:5:0x0005, B:9:0x0009, B:14:0x0013, B:17:0x001d, B:16:0x001b), top: B:24:0x0001 }] */
        public void innerComplete(int i) {
            synchronized (this) {
                try {
                    Object[] objArr = this.latest;
                    if (objArr == null) {
                        return;
                    }
                    boolean z = objArr[i] == null;
                    if (z) {
                        this.done = true;
                    } else {
                        int i2 = this.complete + 1;
                        this.complete = i2;
                        if (i2 == objArr.length) {
                            this.done = true;
                        }
                    }
                    if (z) {
                        cancelSources();
                    }
                    drain();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX WARN: Code duplicated, block: B:20:0x0027 A[Catch: all -> 0x0014, TryCatch #0 {all -> 0x0014, blocks: (B:7:0x000e, B:9:0x0012, B:13:0x0016, B:18:0x001f, B:21:0x0029, B:20:0x0027), top: B:29:0x000e }] */
        public void innerError(int i, Throwable th) {
            if (this.errors.tryAddThrowableOrReport(th)) {
                boolean z = true;
                if (this.delayError) {
                    synchronized (this) {
                        try {
                            Object[] objArr = this.latest;
                            if (objArr == null) {
                                return;
                            }
                            boolean z2 = objArr[i] == null;
                            if (z2) {
                                this.done = true;
                            } else {
                                int i2 = this.complete + 1;
                                this.complete = i2;
                                if (i2 == objArr.length) {
                                    this.done = true;
                                }
                            }
                            z = z2;
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
                }
                if (z) {
                    cancelSources();
                }
                drain();
            }
        }

        public void innerNext(int i, Object obj) {
            boolean z;
            synchronized (this) {
                try {
                    Object[] objArr = this.latest;
                    if (objArr == null) {
                        return;
                    }
                    Object obj2 = objArr[i];
                    int i2 = this.active;
                    if (obj2 == null) {
                        i2++;
                        this.active = i2;
                    }
                    objArr[i] = obj;
                    if (i2 == objArr.length) {
                        this.queue.offer(objArr.clone());
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        drain();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        public void subscribe(ObservableSource[] observableSourceArr) {
            CombinerObserver[] combinerObserverArr = this.observers;
            int length = combinerObserverArr.length;
            this.downstream.onSubscribe(this);
            for (int i = 0; i < length && !this.done && !this.cancelled; i++) {
                observableSourceArr[i].subscribe(combinerObserverArr[i]);
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        int length;
        ObservableSource[] observableSourceArr = this.sources;
        if (observableSourceArr == null) {
            observableSourceArr = new ObservableSource[8];
            try {
                length = 0;
                for (ObservableSource observableSource : this.sourcesIterable) {
                    if (length == observableSourceArr.length) {
                        ObservableSource[] observableSourceArr2 = new ObservableSource[(length >> 2) + length];
                        System.arraycopy(observableSourceArr, 0, observableSourceArr2, 0, length);
                        observableSourceArr = observableSourceArr2;
                    }
                    int i = length + 1;
                    Objects.requireNonNull(observableSource, "The Iterator returned a null ObservableSource");
                    observableSourceArr[length] = observableSource;
                    length = i;
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, observer);
                return;
            }
        } else {
            length = observableSourceArr.length;
        }
        int i2 = length;
        if (i2 == 0) {
            EmptyDisposable.complete(observer);
        } else {
            new LatestCoordinator(observer, this.combiner, i2, this.bufferSize, this.delayError).subscribe(observableSourceArr);
        }
    }
}
