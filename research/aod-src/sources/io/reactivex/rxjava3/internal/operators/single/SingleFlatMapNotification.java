package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes2.dex */
public final class SingleFlatMapNotification<T, R> extends Single<R> {
    public final Function onErrorMapper;
    public final Function onSuccessMapper;
    public final SingleSource source;

    public static final class FlatMapSingleObserver<T, R> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable {
        public final SingleObserver downstream;
        public final Function onErrorMapper;
        public final Function onSuccessMapper;
        public Disposable upstream;

        public final class InnerObserver implements SingleObserver<R> {
            public InnerObserver() {
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onError(Throwable th) {
                FlatMapSingleObserver.this.downstream.onError(th);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(FlatMapSingleObserver.this, disposable);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSuccess(Object obj) {
                FlatMapSingleObserver.this.downstream.onSuccess(obj);
            }
        }

        public FlatMapSingleObserver(SingleObserver singleObserver, Function function, Function function2) {
            this.downstream = singleObserver;
            this.onSuccessMapper = function;
            this.onErrorMapper = function2;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
            this.upstream.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onError(Throwable th) {
            try {
                Object objApply = this.onErrorMapper.apply(th);
                Objects.requireNonNull(objApply, "The onErrorMapper returned a null SingleSource");
                SingleSource singleSource = (SingleSource) objApply;
                if (isDisposed()) {
                    return;
                }
                singleSource.subscribe(new InnerObserver());
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.downstream.onError(new CompositeException(th, th2));
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(Object obj) {
            try {
                Object objApply = this.onSuccessMapper.apply(obj);
                Objects.requireNonNull(objApply, "The onSuccessMapper returned a null SingleSource");
                SingleSource singleSource = (SingleSource) objApply;
                if (isDisposed()) {
                    return;
                }
                singleSource.subscribe(new InnerObserver());
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver singleObserver) {
        this.source.subscribe(new FlatMapSingleObserver(singleObserver, this.onSuccessMapper, this.onErrorMapper));
    }
}
