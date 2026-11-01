package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableDelaySubscriptionOther<T, U> extends Observable<T> {
    public final ObservableSource main;
    public final ObservableSource other;

    public final class DelayObserver implements Observer<U> {
        public final Observer child;
        public boolean done;
        public final SequentialDisposable serial;

        public final class OnComplete implements Observer<T> {
            public OnComplete() {
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
                DelayObserver.this.child.onComplete();
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable th) {
                DelayObserver.this.child.onError(th);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(Object obj) {
                DelayObserver.this.child.onNext(obj);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onSubscribe(Disposable disposable) {
                DelayObserver.this.serial.update(disposable);
            }
        }

        public DelayObserver(SequentialDisposable sequentialDisposable, Observer observer) {
            this.serial = sequentialDisposable;
            this.child = observer;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            ObservableDelaySubscriptionOther.this.main.subscribe(new OnComplete());
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
            } else {
                this.done = true;
                this.child.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            this.serial.update(disposable);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        SequentialDisposable sequentialDisposable = new SequentialDisposable();
        observer.onSubscribe(sequentialDisposable);
        this.other.subscribe(new DelayObserver(sequentialDisposable, observer));
    }
}
