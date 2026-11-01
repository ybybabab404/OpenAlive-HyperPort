package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableMapNotification<T, R> extends AbstractObservableWithUpstream<T, ObservableSource<? extends R>> {
    public final Supplier onCompleteSupplier;
    public final Function onErrorMapper;
    public final Function onNextMapper;

    public static final class MapNotificationObserver<T, R> implements Observer<T>, Disposable {
        public final Observer downstream;
        public final Supplier onCompleteSupplier;
        public final Function onErrorMapper;
        public final Function onNextMapper;
        public Disposable upstream;

        public MapNotificationObserver(Observer observer, Function function, Function function2, Supplier supplier) {
            this.downstream = observer;
            this.onNextMapper = function;
            this.onErrorMapper = function2;
            this.onCompleteSupplier = supplier;
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
            try {
                Object obj = this.onCompleteSupplier.get();
                Objects.requireNonNull(obj, "The onComplete ObservableSource returned is null");
                this.downstream.onNext((ObservableSource) obj);
                this.downstream.onComplete();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            try {
                Object objApply = this.onErrorMapper.apply(th);
                Objects.requireNonNull(objApply, "The onError ObservableSource returned is null");
                this.downstream.onNext((ObservableSource) objApply);
                this.downstream.onComplete();
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.downstream.onError(new CompositeException(th, th2));
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            try {
                Object objApply = this.onNextMapper.apply(obj);
                Objects.requireNonNull(objApply, "The onNext ObservableSource returned is null");
                this.downstream.onNext((ObservableSource) objApply);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
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
        this.source.subscribe(new MapNotificationObserver(observer, this.onNextMapper, this.onErrorMapper, this.onCompleteSupplier));
    }
}
