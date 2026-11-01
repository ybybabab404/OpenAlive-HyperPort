package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Emitter;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableGenerate<T, S> extends Observable<T> {
    public final Consumer disposeState;
    public final BiFunction generator;
    public final Supplier stateSupplier;

    public static final class GeneratorDisposable<T, S> implements Emitter<T>, Disposable {
        public volatile boolean cancelled;
        public final Consumer disposeState;
        public final Observer downstream;
        public final BiFunction generator;
        public boolean hasNext;
        public Object state;
        public boolean terminate;

        public GeneratorDisposable(Observer observer, BiFunction biFunction, Consumer consumer, Object obj) {
            this.downstream = observer;
            this.generator = biFunction;
            this.disposeState = consumer;
            this.state = obj;
        }

        private void dispose(Object obj) {
            try {
                this.disposeState.accept(obj);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.cancelled = true;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onComplete() {
            if (this.terminate) {
                return;
            }
            this.terminate = true;
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onError(Throwable th) {
            if (this.terminate) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (th == null) {
                th = ExceptionHelper.createNullPointerException("onError called with a null Throwable.");
            }
            this.terminate = true;
            this.downstream.onError(th);
        }

        public void run() {
            Object objApply = this.state;
            if (this.cancelled) {
                this.state = null;
                dispose(objApply);
                return;
            }
            BiFunction biFunction = this.generator;
            while (!this.cancelled) {
                this.hasNext = false;
                try {
                    objApply = biFunction.apply(objApply, this);
                    if (this.terminate) {
                        this.cancelled = true;
                        this.state = null;
                        dispose(objApply);
                        return;
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.state = null;
                    this.cancelled = true;
                    onError(th);
                    dispose(objApply);
                    return;
                }
            }
            this.state = null;
            dispose(objApply);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        try {
            GeneratorDisposable generatorDisposable = new GeneratorDisposable(observer, this.generator, this.disposeState, this.stateSupplier.get());
            observer.onSubscribe(generatorDisposable);
            generatorDisposable.run();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
        }
    }
}
