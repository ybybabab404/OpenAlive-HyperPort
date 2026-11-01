package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes2.dex */
public final class SingleZipArray<T, R> extends Single<R> {
    public final SingleSource[] sources;
    public final Function zipper;

    public final class SingletonArrayFunc implements Function<T, R> {
        public SingletonArrayFunc() {
        }

        @Override // io.reactivex.rxjava3.functions.Function
        public Object apply(Object obj) {
            Object objApply = SingleZipArray.this.zipper.apply(new Object[]{obj});
            Objects.requireNonNull(objApply, "The zipper returned a null value");
            return objApply;
        }
    }

    public static final class ZipCoordinator<T, R> extends AtomicInteger implements Disposable {
        public final SingleObserver downstream;
        public final ZipSingleObserver[] observers;
        public Object[] values;
        public final Function zipper;

        public ZipCoordinator(SingleObserver singleObserver, int i, Function function) {
            super(i);
            this.downstream = singleObserver;
            this.zipper = function;
            ZipSingleObserver[] zipSingleObserverArr = new ZipSingleObserver[i];
            for (int i2 = 0; i2 < i; i2++) {
                zipSingleObserverArr[i2] = new ZipSingleObserver(this, i2);
            }
            this.observers = zipSingleObserverArr;
            this.values = new Object[i];
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (getAndSet(0) > 0) {
                for (ZipSingleObserver zipSingleObserver : this.observers) {
                    zipSingleObserver.dispose();
                }
                this.values = null;
            }
        }

        public void disposeExcept(int i) {
            ZipSingleObserver[] zipSingleObserverArr = this.observers;
            int length = zipSingleObserverArr.length;
            for (int i2 = 0; i2 < i; i2++) {
                zipSingleObserverArr[i2].dispose();
            }
            while (true) {
                i++;
                if (i >= length) {
                    return;
                } else {
                    zipSingleObserverArr[i].dispose();
                }
            }
        }

        public void innerError(Throwable th, int i) {
            if (getAndSet(0) <= 0) {
                RxJavaPlugins.onError(th);
                return;
            }
            disposeExcept(i);
            this.values = null;
            this.downstream.onError(th);
        }

        public void innerSuccess(Object obj, int i) {
            Object[] objArr = this.values;
            if (objArr != null) {
                objArr[i] = obj;
            }
            if (decrementAndGet() == 0) {
                try {
                    Object objApply = this.zipper.apply(objArr);
                    Objects.requireNonNull(objApply, "The zipper returned a null value");
                    this.values = null;
                    this.downstream.onSuccess(objApply);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.values = null;
                    this.downstream.onError(th);
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() <= 0;
        }
    }

    public static final class ZipSingleObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T> {
        public final int index;
        public final ZipCoordinator parent;

        public ZipSingleObserver(ZipCoordinator zipCoordinator, int i) {
            this.parent = zipCoordinator;
            this.index = i;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onError(Throwable th) {
            this.parent.innerError(th, this.index);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(Object obj) {
            this.parent.innerSuccess(obj, this.index);
        }
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver singleObserver) {
        SingleSource[] singleSourceArr = this.sources;
        int length = singleSourceArr.length;
        if (length == 1) {
            singleSourceArr[0].subscribe(new SingleMap.MapSingleObserver(singleObserver, new SingletonArrayFunc()));
            return;
        }
        ZipCoordinator zipCoordinator = new ZipCoordinator(singleObserver, length, this.zipper);
        singleObserver.onSubscribe(zipCoordinator);
        for (int i = 0; i < length && !zipCoordinator.isDisposed(); i++) {
            SingleSource singleSource = singleSourceArr[i];
            if (singleSource == null) {
                zipCoordinator.innerError(new NullPointerException("One of the sources is null"), i);
                return;
            }
            singleSource.subscribe(zipCoordinator.observers[i]);
        }
    }
}
