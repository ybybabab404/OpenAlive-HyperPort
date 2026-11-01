package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.rxjava3.internal.subscribers.BasicFuseableSubscriber;
import io.reactivex.rxjava3.operators.ConditionalSubscriber;
import java.util.Objects;
import java.util.Optional;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableMapOptional<T, R> extends Flowable<R> {
    public final Function mapper;
    public final Flowable source;

    public static final class MapOptionalConditionalSubscriber<T, R> extends BasicFuseableConditionalSubscriber<T, R> {
        public final Function mapper;

        public MapOptionalConditionalSubscriber(ConditionalSubscriber conditionalSubscriber, Function function) {
            super(conditionalSubscriber);
            this.mapper = function;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (tryOnNext(obj)) {
                return;
            }
            this.upstream.request(1L);
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public Object poll() {
            while (true) {
                Object objPoll = this.qs.poll();
                if (objPoll == null) {
                    return null;
                }
                Object objApply = this.mapper.apply(objPoll);
                Objects.requireNonNull(objApply, "The mapper returned a null Optional");
                Optional optional = (Optional) objApply;
                if (optional.isPresent()) {
                    return optional.get();
                }
                if (this.sourceMode == 2) {
                    this.qs.request(1L);
                }
            }
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.rxjava3.operators.ConditionalSubscriber
        public boolean tryOnNext(Object obj) {
            if (this.done) {
                return true;
            }
            if (this.sourceMode != 0) {
                this.downstream.onNext(null);
                return true;
            }
            try {
                Object objApply = this.mapper.apply(obj);
                Objects.requireNonNull(objApply, "The mapper returned a null Optional");
                Optional optional = (Optional) objApply;
                if (optional.isPresent()) {
                    return this.downstream.tryOnNext(optional.get());
                }
                return false;
            } catch (Throwable th) {
                fail(th);
                return true;
            }
        }
    }

    public static final class MapOptionalSubscriber<T, R> extends BasicFuseableSubscriber<T, R> implements ConditionalSubscriber<T> {
        public final Function mapper;

        public MapOptionalSubscriber(Subscriber subscriber, Function function) {
            super(subscriber);
            this.mapper = function;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (tryOnNext(obj)) {
                return;
            }
            this.upstream.request(1L);
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public Object poll() {
            while (true) {
                Object objPoll = this.qs.poll();
                if (objPoll == null) {
                    return null;
                }
                Object objApply = this.mapper.apply(objPoll);
                Objects.requireNonNull(objApply, "The mapper returned a null Optional");
                Optional optional = (Optional) objApply;
                if (optional.isPresent()) {
                    return optional.get();
                }
                if (this.sourceMode == 2) {
                    this.qs.request(1L);
                }
            }
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.rxjava3.operators.ConditionalSubscriber
        public boolean tryOnNext(Object obj) {
            if (this.done) {
                return true;
            }
            if (this.sourceMode != 0) {
                this.downstream.onNext(null);
                return true;
            }
            try {
                Object objApply = this.mapper.apply(obj);
                Objects.requireNonNull(objApply, "The mapper returned a null Optional");
                Optional optional = (Optional) objApply;
                if (!optional.isPresent()) {
                    return false;
                }
                this.downstream.onNext(optional.get());
                return true;
            } catch (Throwable th) {
                fail(th);
                return true;
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber) new MapOptionalConditionalSubscriber((ConditionalSubscriber) subscriber, this.mapper));
        } else {
            this.source.subscribe((FlowableSubscriber) new MapOptionalSubscriber(subscriber, this.mapper));
        }
    }
}
