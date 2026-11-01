package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableElementAt<T> extends AbstractObservableWithUpstream<T, T> {
    public final Object defaultValue;
    public final boolean errorOnFewer;
    public final long index;

    public static final class ElementAtObserver<T> implements Observer<T>, Disposable {
        public long count;
        public final Object defaultValue;
        public boolean done;
        public final Observer downstream;
        public final boolean errorOnFewer;
        public final long index;
        public Disposable upstream;

        public ElementAtObserver(Observer observer, long j, Object obj, boolean z) {
            this.downstream = observer;
            this.index = j;
            this.defaultValue = obj;
            this.errorOnFewer = z;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
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
            Object obj = this.defaultValue;
            if (obj == null && this.errorOnFewer) {
                this.downstream.onError(new NoSuchElementException());
                return;
            }
            if (obj != null) {
                this.downstream.onNext(obj);
            }
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
            long j = this.count;
            if (j != this.index) {
                this.count = j + 1;
                return;
            }
            this.done = true;
            this.upstream.dispose();
            this.downstream.onNext(obj);
            this.downstream.onComplete();
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
        this.source.subscribe(new ElementAtObserver(observer, this.index, this.defaultValue, this.errorOnFewer));
    }
}
