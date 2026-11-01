package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Emitter;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.functions.Functions;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableInternalHelper {

    public static final class BufferedReplaySupplier<T> implements Supplier<ConnectableFlowable<T>> {
        public final int bufferSize;
        public final boolean eagerTruncate;
        public final Flowable parent;

        @Override // io.reactivex.rxjava3.functions.Supplier
        public ConnectableFlowable get() {
            return this.parent.replay(this.bufferSize, this.eagerTruncate);
        }
    }

    public static final class BufferedTimedReplay<T> implements Supplier<ConnectableFlowable<T>> {
        public final int bufferSize;
        public final boolean eagerTruncate;
        public final Flowable parent;
        public final Scheduler scheduler;
        public final long time;
        public final TimeUnit unit;

        @Override // io.reactivex.rxjava3.functions.Supplier
        public ConnectableFlowable get() {
            return this.parent.replay(this.bufferSize, this.time, this.unit, this.scheduler, this.eagerTruncate);
        }
    }

    public static final class FlatMapIntoIterable<T, U> implements Function<T, Publisher<U>> {
        public final Function mapper;

        @Override // io.reactivex.rxjava3.functions.Function
        public Publisher apply(Object obj) {
            Object objApply = this.mapper.apply(obj);
            Objects.requireNonNull(objApply, "The mapper returned a null Iterable");
            return new FlowableFromIterable((Iterable) objApply);
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

    public static final class FlatMapWithCombinerOuter<T, R, U> implements Function<T, Publisher<R>> {
        public final BiFunction combiner;
        public final Function mapper;

        @Override // io.reactivex.rxjava3.functions.Function
        public Publisher apply(Object obj) {
            Object objApply = this.mapper.apply(obj);
            Objects.requireNonNull(objApply, "The mapper returned a null Publisher");
            return new FlowableMapPublisher((Publisher) objApply, new FlatMapWithCombinerInner(this.combiner, obj));
        }
    }

    public static final class ItemDelayFunction<T, U> implements Function<T, Publisher<T>> {
        public final Function itemDelay;

        @Override // io.reactivex.rxjava3.functions.Function
        public Publisher apply(Object obj) {
            Object objApply = this.itemDelay.apply(obj);
            Objects.requireNonNull(objApply, "The itemDelay returned a null Publisher");
            return new FlowableTakePublisher((Publisher) objApply, 1L).map(Functions.justFunction(obj)).defaultIfEmpty(obj);
        }
    }

    public static final class ReplaySupplier<T> implements Supplier<ConnectableFlowable<T>> {
        public final Flowable parent;

        @Override // io.reactivex.rxjava3.functions.Supplier
        public ConnectableFlowable get() {
            return this.parent.replay();
        }
    }

    public enum RequestMax implements Consumer<Subscription> {
        INSTANCE;

        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Subscription subscription) {
            subscription.request(Long.MAX_VALUE);
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

    public static final class SubscriberOnComplete<T> implements Action {
        public final Subscriber subscriber;

        @Override // io.reactivex.rxjava3.functions.Action
        public void run() {
            this.subscriber.onComplete();
        }
    }

    public static final class SubscriberOnError<T> implements Consumer<Throwable> {
        public final Subscriber subscriber;

        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Throwable th) {
            this.subscriber.onError(th);
        }
    }

    public static final class SubscriberOnNext<T> implements Consumer<T> {
        public final Subscriber subscriber;

        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Object obj) {
            this.subscriber.onNext(obj);
        }
    }

    public static final class TimedReplay<T> implements Supplier<ConnectableFlowable<T>> {
        public final boolean eagerTruncate;
        public final Flowable parent;
        public final Scheduler scheduler;
        public final long time;
        public final TimeUnit unit;

        @Override // io.reactivex.rxjava3.functions.Supplier
        public ConnectableFlowable get() {
            return this.parent.replay(this.time, this.unit, this.scheduler, this.eagerTruncate);
        }
    }
}
