package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableReduceSeedSingle<T, R> extends Single<R> {
    public final BiFunction reducer;
    public final Object seed;
    public final ObservableSource source;

    public static final class ReduceSeedObserver<T, R> implements Observer<T>, Disposable {
        public final SingleObserver downstream;
        public final BiFunction reducer;
        public Disposable upstream;
        public Object value;

        public ReduceSeedObserver(SingleObserver singleObserver, BiFunction biFunction, Object obj) {
            this.downstream = singleObserver;
            this.value = obj;
            this.reducer = biFunction;
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
            Object obj = this.value;
            if (obj != null) {
                this.value = null;
                this.downstream.onSuccess(obj);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            if (this.value == null) {
                RxJavaPlugins.onError(th);
            } else {
                this.value = null;
                this.downstream.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            Object obj2 = this.value;
            if (obj2 != null) {
                try {
                    Object objApply = this.reducer.apply(obj2, obj);
                    Objects.requireNonNull(objApply, "The reducer returned a null value");
                    this.value = objApply;
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.upstream.dispose();
                    onError(th);
                }
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

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver singleObserver) {
        this.source.subscribe(new ReduceSeedObserver(singleObserver, this.reducer, this.seed));
    }
}
