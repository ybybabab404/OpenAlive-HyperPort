package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.functions.BiPredicate;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.rxjava3.internal.subscribers.BasicFuseableSubscriber;
import io.reactivex.rxjava3.operators.ConditionalSubscriber;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableDistinctUntilChanged<T, K> extends AbstractFlowableWithUpstream<T, T> {
    public final BiPredicate comparer;
    public final Function keySelector;

    public static final class DistinctUntilChangedConditionalSubscriber<T, K> extends BasicFuseableConditionalSubscriber<T, T> {
        public final BiPredicate comparer;
        public boolean hasValue;
        public final Function keySelector;
        public Object last;

        public DistinctUntilChangedConditionalSubscriber(ConditionalSubscriber conditionalSubscriber, Function function, BiPredicate biPredicate) {
            super(conditionalSubscriber);
            this.keySelector = function;
            this.comparer = biPredicate;
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
                Object objApply = this.keySelector.apply(objPoll);
                if (!this.hasValue) {
                    this.hasValue = true;
                    this.last = objApply;
                    return objPoll;
                }
                if (!this.comparer.test(this.last, objApply)) {
                    this.last = objApply;
                    return objPoll;
                }
                this.last = objApply;
                if (this.sourceMode != 1) {
                    this.upstream.request(1L);
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
                return false;
            }
            if (this.sourceMode != 0) {
                return this.downstream.tryOnNext(obj);
            }
            try {
                Object objApply = this.keySelector.apply(obj);
                if (this.hasValue) {
                    boolean zTest = this.comparer.test(this.last, objApply);
                    this.last = objApply;
                    if (zTest) {
                        return false;
                    }
                } else {
                    this.hasValue = true;
                    this.last = objApply;
                }
                this.downstream.onNext(obj);
                return true;
            } catch (Throwable th) {
                fail(th);
                return true;
            }
        }
    }

    public static final class DistinctUntilChangedSubscriber<T, K> extends BasicFuseableSubscriber<T, T> implements ConditionalSubscriber<T> {
        public final BiPredicate comparer;
        public boolean hasValue;
        public final Function keySelector;
        public Object last;

        public DistinctUntilChangedSubscriber(Subscriber subscriber, Function function, BiPredicate biPredicate) {
            super(subscriber);
            this.keySelector = function;
            this.comparer = biPredicate;
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
                Object objApply = this.keySelector.apply(objPoll);
                if (!this.hasValue) {
                    this.hasValue = true;
                    this.last = objApply;
                    return objPoll;
                }
                if (!this.comparer.test(this.last, objApply)) {
                    this.last = objApply;
                    return objPoll;
                }
                this.last = objApply;
                if (this.sourceMode != 1) {
                    this.upstream.request(1L);
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
                return false;
            }
            if (this.sourceMode != 0) {
                this.downstream.onNext(obj);
                return true;
            }
            try {
                Object objApply = this.keySelector.apply(obj);
                if (this.hasValue) {
                    boolean zTest = this.comparer.test(this.last, objApply);
                    this.last = objApply;
                    if (zTest) {
                        return false;
                    }
                } else {
                    this.hasValue = true;
                    this.last = objApply;
                }
                this.downstream.onNext(obj);
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
            this.source.subscribe((FlowableSubscriber) new DistinctUntilChangedConditionalSubscriber((ConditionalSubscriber) subscriber, this.keySelector, this.comparer));
        } else {
            this.source.subscribe((FlowableSubscriber) new DistinctUntilChangedSubscriber(subscriber, this.keySelector, this.comparer));
        }
    }
}
