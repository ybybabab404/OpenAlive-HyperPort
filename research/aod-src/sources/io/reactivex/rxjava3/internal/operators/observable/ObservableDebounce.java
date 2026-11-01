package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.observers.SerializedObserver;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableDebounce<T, U> extends AbstractObservableWithUpstream<T, T> {
    public final Function debounceSelector;

    public static final class DebounceObserver<T, U> implements Observer<T>, Disposable {
        public final Function debounceSelector;
        public final AtomicReference debouncer = new AtomicReference();
        public boolean done;
        public final Observer downstream;
        public volatile long index;
        public Disposable upstream;

        public static final class DebounceInnerObserver<T, U> extends DisposableObserver<U> {
            public boolean done;
            public final long index;
            public final AtomicBoolean once = new AtomicBoolean();
            public final DebounceObserver parent;
            public final Object value;

            public DebounceInnerObserver(DebounceObserver debounceObserver, long j, Object obj) {
                this.parent = debounceObserver;
                this.index = j;
                this.value = obj;
            }

            public void emit() {
                if (this.once.compareAndSet(false, true)) {
                    this.parent.emit(this.index, this.value);
                }
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
                if (this.done) {
                    return;
                }
                this.done = true;
                emit();
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable th) {
                if (this.done) {
                    RxJavaPlugins.onError(th);
                } else {
                    this.done = true;
                    this.parent.onError(th);
                }
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(Object obj) {
                if (this.done) {
                    return;
                }
                this.done = true;
                dispose();
                emit();
            }
        }

        public DebounceObserver(Observer observer, Function function) {
            this.downstream = observer;
            this.debounceSelector = function;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
            DisposableHelper.dispose(this.debouncer);
        }

        public void emit(long j, Object obj) {
            if (j == this.index) {
                this.downstream.onNext(obj);
            }
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
            Disposable disposable = (Disposable) this.debouncer.get();
            if (disposable != DisposableHelper.DISPOSED) {
                DebounceInnerObserver debounceInnerObserver = (DebounceInnerObserver) disposable;
                if (debounceInnerObserver != null) {
                    debounceInnerObserver.emit();
                }
                DisposableHelper.dispose(this.debouncer);
                this.downstream.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            DisposableHelper.dispose(this.debouncer);
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            if (this.done) {
                return;
            }
            long j = this.index + 1;
            this.index = j;
            Disposable disposable = (Disposable) this.debouncer.get();
            if (disposable != null) {
                disposable.dispose();
            }
            try {
                Object objApply = this.debounceSelector.apply(obj);
                Objects.requireNonNull(objApply, "The ObservableSource supplied is null");
                ObservableSource observableSource = (ObservableSource) objApply;
                DebounceInnerObserver debounceInnerObserver = new DebounceInnerObserver(this, j, obj);
                if (this.debouncer.compareAndSet(disposable, debounceInnerObserver)) {
                    observableSource.subscribe(debounceInnerObserver);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                dispose();
                this.downstream.onError(th);
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
        this.source.subscribe(new DebounceObserver(new SerializedObserver(observer), this.debounceSelector));
    }
}
