package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes2.dex */
public final class MaybeZipArray<T, R> extends Maybe<R> {
    public final MaybeSource[] sources;
    public final Function zipper;

    public final class SingletonArrayFunc implements Function<T, R> {
        public SingletonArrayFunc() {
        }

        @Override // io.reactivex.rxjava3.functions.Function
        public Object apply(Object obj) {
            Object objApply = MaybeZipArray.this.zipper.apply(new Object[]{obj});
            Objects.requireNonNull(objApply, "The zipper returned a null value");
            return objApply;
        }
    }

    public static final class ZipCoordinator<T, R> extends AtomicInteger implements Disposable {
        public final MaybeObserver downstream;
        public final ZipMaybeObserver[] observers;
        public Object[] values;
        public final Function zipper;

        public ZipCoordinator(MaybeObserver maybeObserver, int i, Function function) {
            super(i);
            this.downstream = maybeObserver;
            this.zipper = function;
            ZipMaybeObserver[] zipMaybeObserverArr = new ZipMaybeObserver[i];
            for (int i2 = 0; i2 < i; i2++) {
                zipMaybeObserverArr[i2] = new ZipMaybeObserver(this, i2);
            }
            this.observers = zipMaybeObserverArr;
            this.values = new Object[i];
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (getAndSet(0) > 0) {
                for (ZipMaybeObserver zipMaybeObserver : this.observers) {
                    zipMaybeObserver.dispose();
                }
                this.values = null;
            }
        }

        public void disposeExcept(int i) {
            ZipMaybeObserver[] zipMaybeObserverArr = this.observers;
            int length = zipMaybeObserverArr.length;
            for (int i2 = 0; i2 < i; i2++) {
                zipMaybeObserverArr[i2].dispose();
            }
            while (true) {
                i++;
                if (i >= length) {
                    return;
                } else {
                    zipMaybeObserverArr[i].dispose();
                }
            }
        }

        public void innerComplete(int i) {
            if (getAndSet(0) > 0) {
                disposeExcept(i);
                this.values = null;
                this.downstream.onComplete();
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

    public static final class ZipMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T> {
        public final int index;
        public final ZipCoordinator parent;

        public ZipMaybeObserver(ZipCoordinator zipCoordinator, int i) {
            this.parent = zipCoordinator;
            this.index = i;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            this.parent.innerComplete(this.index);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
        public void onError(Throwable th) {
            this.parent.innerError(th, this.index);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(Object obj) {
            this.parent.innerSuccess(obj, this.index);
        }
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver maybeObserver) {
        MaybeSource[] maybeSourceArr = this.sources;
        int length = maybeSourceArr.length;
        if (length == 1) {
            maybeSourceArr[0].subscribe(new MaybeMap.MapMaybeObserver(maybeObserver, new SingletonArrayFunc()));
            return;
        }
        ZipCoordinator zipCoordinator = new ZipCoordinator(maybeObserver, length, this.zipper);
        maybeObserver.onSubscribe(zipCoordinator);
        for (int i = 0; i < length && !zipCoordinator.isDisposed(); i++) {
            MaybeSource maybeSource = maybeSourceArr[i];
            if (maybeSource == null) {
                zipCoordinator.innerError(new NullPointerException("One of the sources is null"), i);
                return;
            }
            maybeSource.subscribe(zipCoordinator.observers[i]);
        }
    }
}
