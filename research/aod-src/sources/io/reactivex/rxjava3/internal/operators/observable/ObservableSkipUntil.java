package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.ArrayCompositeDisposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.observers.SerializedObserver;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableSkipUntil<T, U> extends AbstractObservableWithUpstream<T, T> {
    public final ObservableSource other;

    public final class SkipUntil implements Observer<U> {
        public final ArrayCompositeDisposable frc;
        public final SerializedObserver serial;
        public final SkipUntilObserver sus;
        public Disposable upstream;

        public SkipUntil(ArrayCompositeDisposable arrayCompositeDisposable, SkipUntilObserver skipUntilObserver, SerializedObserver serializedObserver) {
            this.frc = arrayCompositeDisposable;
            this.sus = skipUntilObserver;
            this.serial = serializedObserver;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.sus.notSkipping = true;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            this.frc.dispose();
            this.serial.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            this.upstream.dispose();
            this.sus.notSkipping = true;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.frc.setResource(1, disposable);
            }
        }
    }

    public static final class SkipUntilObserver<T> implements Observer<T> {
        public final Observer downstream;
        public final ArrayCompositeDisposable frc;
        public volatile boolean notSkipping;
        public boolean notSkippingLocal;
        public Disposable upstream;

        public SkipUntilObserver(Observer observer, ArrayCompositeDisposable arrayCompositeDisposable) {
            this.downstream = observer;
            this.frc = arrayCompositeDisposable;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.frc.dispose();
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            this.frc.dispose();
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            if (this.notSkippingLocal) {
                this.downstream.onNext(obj);
            } else if (this.notSkipping) {
                this.notSkippingLocal = true;
                this.downstream.onNext(obj);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.frc.setResource(0, disposable);
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        SerializedObserver serializedObserver = new SerializedObserver(observer);
        ArrayCompositeDisposable arrayCompositeDisposable = new ArrayCompositeDisposable(2);
        serializedObserver.onSubscribe(arrayCompositeDisposable);
        SkipUntilObserver skipUntilObserver = new SkipUntilObserver(serializedObserver, arrayCompositeDisposable);
        this.other.subscribe(new SkipUntil(arrayCompositeDisposable, skipUntilObserver, serializedObserver));
        this.source.subscribe(skipUntilObserver);
    }
}
