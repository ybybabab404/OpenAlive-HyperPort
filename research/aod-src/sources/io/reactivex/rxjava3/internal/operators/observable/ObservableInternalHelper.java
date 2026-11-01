package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Emitter;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.functions.Functions;
import io.reactivex.rxjava3.observables.ConnectableObservable;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableInternalHelper {

    public static final class BufferedReplaySupplier<T> implements Supplier<ConnectableObservable<T>> {
        public final int bufferSize;
        public final boolean eagerTruncate;
        public final Observable parent;

        @Override // io.reactivex.rxjava3.functions.Supplier
        public ConnectableObservable get() {
            return this.parent.replay(this.bufferSize, this.eagerTruncate);
        }
    }

    public static final class BufferedTimedReplaySupplier<T> implements Supplier<ConnectableObservable<T>> {
        public final int bufferSize;
        public final boolean eagerTruncate;
        public final Observable parent;
        public final Scheduler scheduler;
        public final long time;
        public final TimeUnit unit;

        @Override // io.reactivex.rxjava3.functions.Supplier
        public ConnectableObservable get() {
            return this.parent.replay(this.bufferSize, this.time, this.unit, this.scheduler, this.eagerTruncate);
        }
    }

    public static final class FlatMapIntoIterable<T, U> implements Function<T, ObservableSource<U>> {
        public final Function mapper;

        @Override // io.reactivex.rxjava3.functions.Function
        public ObservableSource apply(Object obj) {
            Object objApply = this.mapper.apply(obj);
            Objects.requireNonNull(objApply, "The mapper returned a null Iterable");
            return new ObservableFromIterable((Iterable) objApply);
        }
    }

    public static final class FlatMapWithCombinerInner<U, R, T> implements Function<U, R> {
        public final BiFunction combiner;
        public final Object t;

        public FlatMapWithCombinerInner(BiFunction biFunction, Object obj) {
            this.combiner = biFunction;
            this.t = obj;
        }

        @Override // io.reactivex.rxjava3.functions.Function
        public Object apply(Object obj) {
            return this.combiner.apply(this.t, obj);
        }
    }

    public static final class FlatMapWithCombinerOuter<T, R, U> implements Function<T, ObservableSource<R>> {
        public final BiFunction combiner;
        public final Function mapper;

        @Override // io.reactivex.rxjava3.functions.Function
        public ObservableSource apply(Object obj) {
            Object objApply = this.mapper.apply(obj);
            Objects.requireNonNull(objApply, "The mapper returned a null ObservableSource");
            return new ObservableMap((ObservableSource) objApply, new FlatMapWithCombinerInner(this.combiner, obj));
        }
    }

    public static final class ItemDelayFunction<T, U> implements Function<T, ObservableSource<T>> {
        public final Function itemDelay;

        @Override // io.reactivex.rxjava3.functions.Function
        public ObservableSource apply(Object obj) {
            Object objApply = this.itemDelay.apply(obj);
            Objects.requireNonNull(objApply, "The itemDelay returned a null ObservableSource");
            return new ObservableTake((ObservableSource) objApply, 1L).map(Functions.justFunction(obj)).defaultIfEmpty(obj);
        }
    }

    public enum MapToInt implements Function<Object, Object> {
        INSTANCE;

        @Override // io.reactivex.rxjava3.functions.Function
        public Object apply(Object obj) {
            return 0;
        }
    }

    public static final class ObserverOnComplete<T> implements Action {
        public final Observer observer;

        @Override // io.reactivex.rxjava3.functions.Action
        public void run() {
            this.observer.onComplete();
        }
    }

    public static final class ObserverOnError<T> implements Consumer<Throwable> {
        public final Observer observer;

        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Throwable th) {
            this.observer.onError(th);
        }
    }

    public static final class ObserverOnNext<T> implements Consumer<T> {
        public final Observer observer;

        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Object obj) {
            this.observer.onNext(obj);
        }
    }

    public static final class ReplaySupplier<T> implements Supplier<ConnectableObservable<T>> {
        public final Observable parent;

        @Override // io.reactivex.rxjava3.functions.Supplier
        public ConnectableObservable get() {
            return this.parent.replay();
        }
    }

    public static final class SimpleBiGenerator<T, S> implements BiFunction<S, Emitter<T>, S> {
        public final BiConsumer consumer;

        @Override // io.reactivex.rxjava3.functions.BiFunction
        public Object apply(Object obj, Emitter emitter) {
            this.consumer.accept(obj, emitter);
            return obj;
        }
    }

    public static final class SimpleGenerator<T, S> implements BiFunction<S, Emitter<T>, S> {
        public final Consumer consumer;

        @Override // io.reactivex.rxjava3.functions.BiFunction
        public Object apply(Object obj, Emitter emitter) {
            this.consumer.accept(emitter);
            return obj;
        }
    }

    public static final class TimedReplayCallable<T> implements Supplier<ConnectableObservable<T>> {
        public final boolean eagerTruncate;
        public final Observable parent;
        public final Scheduler scheduler;
        public final long time;
        public final TimeUnit unit;

        @Override // io.reactivex.rxjava3.functions.Supplier
        public ConnectableObservable get() {
            return this.parent.replay(this.time, this.unit, this.scheduler, this.eagerTruncate);
        }
    }
}
