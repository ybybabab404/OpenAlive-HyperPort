package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableBuffer<T, U extends Collection<? super T>> extends AbstractObservableWithUpstream<T, U> {
    public final Supplier bufferSupplier;
    public final int count;
    public final int skip;

    public static final class BufferExactObserver<T, U extends Collection<? super T>> implements Observer<T>, Disposable {
        public Collection buffer;
        public final Supplier bufferSupplier;
        public final int count;
        public final Observer downstream;
        public int size;
        public Disposable upstream;

        public BufferExactObserver(Observer observer, int i, Supplier supplier) {
            this.downstream = observer;
            this.count = i;
            this.bufferSupplier = supplier;
        }

        public boolean createBuffer() {
            try {
                Object obj = this.bufferSupplier.get();
                Objects.requireNonNull(obj, "Empty buffer supplied");
                this.buffer = (Collection) obj;
                return true;
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.buffer = null;
                Disposable disposable = this.upstream;
                if (disposable == null) {
                    EmptyDisposable.error(th, this.downstream);
                    return false;
                }
                disposable.dispose();
                this.downstream.onError(th);
                return false;
            }
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
            Collection collection = this.buffer;
            if (collection != null) {
                this.buffer = null;
                if (!collection.isEmpty()) {
                    this.downstream.onNext(collection);
                }
                this.downstream.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            this.buffer = null;
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            Collection collection = this.buffer;
            if (collection != null) {
                collection.add(obj);
                int i = this.size + 1;
                this.size = i;
                if (i >= this.count) {
                    this.downstream.onNext(collection);
                    this.size = 0;
                    createBuffer();
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

    public static final class BufferSkipObserver<T, U extends Collection<? super T>> extends AtomicBoolean implements Observer<T>, Disposable {
        public final Supplier bufferSupplier;
        public final ArrayDeque buffers = new ArrayDeque();
        public final int count;
        public final Observer downstream;
        public long index;
        public final int skip;
        public Disposable upstream;

        public BufferSkipObserver(Observer observer, int i, int i2, Supplier supplier) {
            this.downstream = observer;
            this.count = i;
            this.skip = i2;
            this.bufferSupplier = supplier;
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
            while (!this.buffers.isEmpty()) {
                this.downstream.onNext(this.buffers.poll());
            }
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            this.buffers.clear();
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            long j = this.index;
            this.index = 1 + j;
            if (j % ((long) this.skip) == 0) {
                try {
                    this.buffers.offer((Collection) ExceptionHelper.nullCheck(this.bufferSupplier.get(), "The bufferSupplier returned a null Collection."));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.buffers.clear();
                    this.upstream.dispose();
                    this.downstream.onError(th);
                    return;
                }
            }
            Iterator it = this.buffers.iterator();
            while (it.hasNext()) {
                Collection collection = (Collection) it.next();
                collection.add(obj);
                if (this.count <= collection.size()) {
                    it.remove();
                    this.downstream.onNext(collection);
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

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        int i = this.skip;
        int i2 = this.count;
        if (i != i2) {
            this.source.subscribe(new BufferSkipObserver(observer, this.count, this.skip, this.bufferSupplier));
            return;
        }
        BufferExactObserver bufferExactObserver = new BufferExactObserver(observer, i2, this.bufferSupplier);
        if (bufferExactObserver.createBuffer()) {
            this.source.subscribe(bufferExactObserver);
        }
    }
}
