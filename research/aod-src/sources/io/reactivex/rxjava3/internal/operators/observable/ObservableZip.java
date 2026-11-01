package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableZip<T, R> extends Observable<R> {
    public final int bufferSize;
    public final boolean delayError;
    public final ObservableSource[] sources;
    public final Iterable sourcesIterable;
    public final Function zipper;

    public static final class ZipCoordinator<T, R> extends AtomicInteger implements Disposable {
        public volatile boolean cancelled;
        public final boolean delayError;
        public final Observer downstream;
        public final ZipObserver[] observers;
        public final Object[] row;
        public final Function zipper;

        public ZipCoordinator(Observer observer, Function function, int i, boolean z) {
            this.downstream = observer;
            this.zipper = function;
            this.observers = new ZipObserver[i];
            this.row = new Object[i];
            this.delayError = z;
        }

        public void cancel() {
            clear();
            cancelSources();
        }

        public void cancelSources() {
            for (ZipObserver zipObserver : this.observers) {
                zipObserver.dispose();
            }
        }

        public boolean checkTerminated(boolean z, boolean z2, Observer observer, boolean z3, ZipObserver zipObserver) {
            if (this.cancelled) {
                cancel();
                return true;
            }
            if (!z) {
                return false;
            }
            if (z3) {
                if (!z2) {
                    return false;
                }
                Throwable th = zipObserver.error;
                this.cancelled = true;
                cancel();
                if (th != null) {
                    observer.onError(th);
                } else {
                    observer.onComplete();
                }
                return true;
            }
            Throwable th2 = zipObserver.error;
            if (th2 != null) {
                this.cancelled = true;
                cancel();
                observer.onError(th2);
                return true;
            }
            if (!z2) {
                return false;
            }
            this.cancelled = true;
            cancel();
            observer.onComplete();
            return true;
        }

        public void clear() {
            for (ZipObserver zipObserver : this.observers) {
                zipObserver.queue.clear();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            cancelSources();
            if (getAndIncrement() == 0) {
                clear();
            }
        }

        public void drain() {
            Throwable th;
            if (getAndIncrement() != 0) {
                return;
            }
            ZipObserver[] zipObserverArr = this.observers;
            Observer observer = this.downstream;
            Object[] objArr = this.row;
            boolean z = this.delayError;
            int iAddAndGet = 1;
            while (true) {
                int i = 0;
                int i2 = 0;
                for (ZipObserver zipObserver : zipObserverArr) {
                    if (objArr[i2] == null) {
                        boolean z2 = zipObserver.done;
                        Object objPoll = zipObserver.queue.poll();
                        boolean z3 = objPoll == null;
                        if (checkTerminated(z2, z3, observer, z, zipObserver)) {
                            return;
                        }
                        if (z3) {
                            i++;
                        } else {
                            objArr[i2] = objPoll;
                        }
                    } else if (zipObserver.done && !z && (th = zipObserver.error) != null) {
                        this.cancelled = true;
                        cancel();
                        observer.onError(th);
                        return;
                    }
                    i2++;
                }
                if (i != 0) {
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    try {
                        Object objApply = this.zipper.apply(objArr.clone());
                        Objects.requireNonNull(objApply, "The zipper returned a null value");
                        observer.onNext(objApply);
                        Arrays.fill(objArr, (Object) null);
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        cancel();
                        observer.onError(th2);
                        return;
                    }
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        public void subscribe(ObservableSource[] observableSourceArr, int i) {
            ZipObserver[] zipObserverArr = this.observers;
            int length = zipObserverArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                zipObserverArr[i2] = new ZipObserver(this, i);
            }
            lazySet(0);
            this.downstream.onSubscribe(this);
            for (int i3 = 0; i3 < length && !this.cancelled; i3++) {
                observableSourceArr[i3].subscribe(zipObserverArr[i3]);
            }
        }
    }

    public static final class ZipObserver<T, R> implements Observer<T> {
        public volatile boolean done;
        public Throwable error;
        public final ZipCoordinator parent;
        public final SpscLinkedArrayQueue queue;
        public final AtomicReference upstream = new AtomicReference();

        public ZipObserver(ZipCoordinator zipCoordinator, int i) {
            this.parent = zipCoordinator;
            this.queue = new SpscLinkedArrayQueue(i);
        }

        public void dispose() {
            DisposableHelper.dispose(this.upstream);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            this.parent.drain();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            this.queue.offer(obj);
            this.parent.drain();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.upstream, disposable);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        int length;
        ObservableSource[] observableSourceArr = this.sources;
        if (observableSourceArr == null) {
            observableSourceArr = new ObservableSource[8];
            length = 0;
            for (ObservableSource observableSource : this.sourcesIterable) {
                if (length == observableSourceArr.length) {
                    ObservableSource[] observableSourceArr2 = new ObservableSource[(length >> 2) + length];
                    System.arraycopy(observableSourceArr, 0, observableSourceArr2, 0, length);
                    observableSourceArr = observableSourceArr2;
                }
                observableSourceArr[length] = observableSource;
                length++;
            }
        } else {
            length = observableSourceArr.length;
        }
        if (length == 0) {
            EmptyDisposable.complete(observer);
        } else {
            new ZipCoordinator(observer, this.zipper, length, this.delayError).subscribe(observableSourceArr, this.bufferSize);
        }
    }
}
