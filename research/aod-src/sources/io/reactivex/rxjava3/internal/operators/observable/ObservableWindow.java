package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.subjects.UnicastSubject;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableWindow<T> extends AbstractObservableWithUpstream<T, Observable<T>> {
    public final int capacityHint;
    public final long count;
    public final long skip;

    public static final class WindowExactObserver<T> extends AtomicInteger implements Observer<T>, Disposable, Runnable {
        public final AtomicBoolean cancelled = new AtomicBoolean();
        public final int capacityHint;
        public final long count;
        public final Observer downstream;
        public long size;
        public Disposable upstream;
        public UnicastSubject window;

        public WindowExactObserver(Observer observer, long j, int i) {
            this.downstream = observer;
            this.count = j;
            this.capacityHint = i;
            lazySet(1);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.cancelled.compareAndSet(false, true)) {
                run();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled.get();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            UnicastSubject unicastSubject = this.window;
            if (unicastSubject != null) {
                this.window = null;
                unicastSubject.onComplete();
            }
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            UnicastSubject unicastSubject = this.window;
            if (unicastSubject != null) {
                this.window = null;
                unicastSubject.onError(th);
            }
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            ObservableWindowSubscribeIntercept observableWindowSubscribeIntercept;
            UnicastSubject unicastSubjectCreate = this.window;
            if (unicastSubjectCreate != null || this.cancelled.get()) {
                observableWindowSubscribeIntercept = null;
            } else {
                getAndIncrement();
                unicastSubjectCreate = UnicastSubject.create(this.capacityHint, this);
                this.window = unicastSubjectCreate;
                observableWindowSubscribeIntercept = new ObservableWindowSubscribeIntercept(unicastSubjectCreate);
                this.downstream.onNext(observableWindowSubscribeIntercept);
            }
            if (unicastSubjectCreate != null) {
                unicastSubjectCreate.onNext(obj);
                long j = this.size + 1;
                this.size = j;
                if (j >= this.count) {
                    this.size = 0L;
                    this.window = null;
                    unicastSubjectCreate.onComplete();
                }
                if (observableWindowSubscribeIntercept == null || !observableWindowSubscribeIntercept.tryAbandon()) {
                    return;
                }
                this.window = null;
                unicastSubjectCreate.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (decrementAndGet() == 0) {
                this.upstream.dispose();
            }
        }
    }

    public static final class WindowSkipObserver<T> extends AtomicInteger implements Observer<T>, Disposable, Runnable {
        public final int capacityHint;
        public final long count;
        public final Observer downstream;
        public long firstEmission;
        public long index;
        public final long skip;
        public Disposable upstream;
        public final ArrayDeque windows = new ArrayDeque();
        public final AtomicBoolean cancelled = new AtomicBoolean();

        public WindowSkipObserver(Observer observer, long j, long j2, int i) {
            this.downstream = observer;
            this.count = j;
            this.skip = j2;
            this.capacityHint = i;
            lazySet(1);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.cancelled.compareAndSet(false, true)) {
                run();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled.get();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            ArrayDeque arrayDeque = this.windows;
            while (!arrayDeque.isEmpty()) {
                ((UnicastSubject) arrayDeque.poll()).onComplete();
            }
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            ArrayDeque arrayDeque = this.windows;
            while (!arrayDeque.isEmpty()) {
                ((UnicastSubject) arrayDeque.poll()).onError(th);
            }
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            ObservableWindowSubscribeIntercept observableWindowSubscribeIntercept;
            ArrayDeque arrayDeque = this.windows;
            long j = this.index;
            long j2 = this.skip;
            if (j % j2 != 0 || this.cancelled.get()) {
                observableWindowSubscribeIntercept = null;
            } else {
                getAndIncrement();
                UnicastSubject unicastSubjectCreate = UnicastSubject.create(this.capacityHint, this);
                observableWindowSubscribeIntercept = new ObservableWindowSubscribeIntercept(unicastSubjectCreate);
                arrayDeque.offer(unicastSubjectCreate);
                this.downstream.onNext(observableWindowSubscribeIntercept);
            }
            long j3 = this.firstEmission + 1;
            Iterator it = arrayDeque.iterator();
            while (it.hasNext()) {
                ((UnicastSubject) it.next()).onNext(obj);
            }
            if (j3 >= this.count) {
                ((UnicastSubject) arrayDeque.poll()).onComplete();
                if (arrayDeque.isEmpty() && this.cancelled.get()) {
                    return;
                } else {
                    this.firstEmission = j3 - j2;
                }
            } else {
                this.firstEmission = j3;
            }
            this.index = j + 1;
            if (observableWindowSubscribeIntercept == null || !observableWindowSubscribeIntercept.tryAbandon()) {
                return;
            }
            observableWindowSubscribeIntercept.window.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (decrementAndGet() == 0) {
                this.upstream.dispose();
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        if (this.count == this.skip) {
            this.source.subscribe(new WindowExactObserver(observer, this.count, this.capacityHint));
        } else {
            this.source.subscribe(new WindowSkipObserver(observer, this.count, this.skip, this.capacityHint));
        }
    }
}
