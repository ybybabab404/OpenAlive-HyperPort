package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableZipIterable<T, U, V> extends Observable<V> {
    public final Iterable other;
    public final Observable source;
    public final BiFunction zipper;

    public static final class ZipIterableObserver<T, U, V> implements Observer<T>, Disposable {
        public boolean done;
        public final Observer downstream;
        public final Iterator iterator;
        public Disposable upstream;
        public final BiFunction zipper;

        public ZipIterableObserver(Observer observer, Iterator it, BiFunction biFunction) {
            this.downstream = observer;
            this.iterator = it;
            this.zipper = biFunction;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
        }

        public void fail(Throwable th) {
            this.done = true;
            this.upstream.dispose();
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
            } else {
                this.done = true;
                this.downstream.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            if (this.done) {
                return;
            }
            try {
                Object next = this.iterator.next();
                Objects.requireNonNull(next, "The iterator returned a null value");
                try {
                    Object objApply = this.zipper.apply(obj, next);
                    Objects.requireNonNull(objApply, "The zipper function returned a null value");
                    this.downstream.onNext(objApply);
                    try {
                        if (this.iterator.hasNext()) {
                            return;
                        }
                        this.done = true;
                        this.upstream.dispose();
                        this.downstream.onComplete();
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        fail(th);
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    fail(th2);
                }
            } catch (Throwable th3) {
                Exceptions.throwIfFatal(th3);
                fail(th3);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        try {
            Iterator<T> it = this.other.iterator();
            Objects.requireNonNull(it, "The iterator returned by other is null");
            Iterator<T> it2 = it;
            try {
                if (it2.hasNext()) {
                    this.source.subscribe(new ZipIterableObserver(observer, it2, this.zipper));
                } else {
                    EmptyDisposable.complete(observer);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, observer);
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            EmptyDisposable.error(th2, observer);
        }
    }
}
