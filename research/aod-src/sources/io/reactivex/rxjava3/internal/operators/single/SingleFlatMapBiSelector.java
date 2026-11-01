package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes2.dex */
public final class SingleFlatMapBiSelector<T, U, R> extends Single<R> {
    public final Function mapper;
    public final BiFunction resultSelector;
    public final SingleSource source;

    public static final class FlatMapBiMainObserver<T, U, R> implements SingleObserver<T>, Disposable {
        public final InnerObserver inner;
        public final Function mapper;

        public static final class InnerObserver<T, U, R> extends AtomicReference<Disposable> implements SingleObserver<U> {
            public final SingleObserver downstream;
            public final BiFunction resultSelector;
            public Object value;

            public InnerObserver(SingleObserver singleObserver, BiFunction biFunction) {
                this.downstream = singleObserver;
                this.resultSelector = biFunction;
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onError(Throwable th) {
                this.downstream.onError(th);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSuccess(Object obj) {
                Object obj2 = this.value;
                this.value = null;
                try {
                    Object objApply = this.resultSelector.apply(obj2, obj);
                    Objects.requireNonNull(objApply, "The resultSelector returned a null value");
                    this.downstream.onSuccess(objApply);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.downstream.onError(th);
                }
            }
        }

        public FlatMapBiMainObserver(SingleObserver singleObserver, Function function, BiFunction biFunction) {
            this.inner = new InnerObserver(singleObserver, biFunction);
            this.mapper = function;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.inner);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.inner.get());
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onError(Throwable th) {
            this.inner.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this.inner, disposable)) {
                this.inner.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(Object obj) {
            try {
                Object objApply = this.mapper.apply(obj);
                Objects.requireNonNull(objApply, "The mapper returned a null MaybeSource");
                SingleSource singleSource = (SingleSource) objApply;
                if (DisposableHelper.replace(this.inner, null)) {
                    InnerObserver innerObserver = this.inner;
                    innerObserver.value = obj;
                    singleSource.subscribe(innerObserver);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.inner.downstream.onError(th);
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver singleObserver) {
        this.source.subscribe(new FlatMapBiMainObserver(singleObserver, this.mapper, this.resultSelector));
    }
}
