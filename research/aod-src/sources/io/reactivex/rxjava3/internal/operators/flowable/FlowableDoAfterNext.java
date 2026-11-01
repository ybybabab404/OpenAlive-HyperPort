package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.rxjava3.internal.subscribers.BasicFuseableSubscriber;
import io.reactivex.rxjava3.operators.ConditionalSubscriber;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableDoAfterNext<T> extends AbstractFlowableWithUpstream<T, T> {
    public final Consumer onAfterNext;

    public static final class DoAfterConditionalSubscriber<T> extends BasicFuseableConditionalSubscriber<T, T> {
        public final Consumer onAfterNext;

        public DoAfterConditionalSubscriber(ConditionalSubscriber conditionalSubscriber, Consumer consumer) {
            super(conditionalSubscriber);
            this.onAfterNext = consumer;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            this.downstream.onNext(obj);
            if (this.sourceMode == 0) {
                try {
                    this.onAfterNext.accept(obj);
                } catch (Throwable th) {
                    fail(th);
                }
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public Object poll() {
            Object objPoll = this.qs.poll();
            if (objPoll != null) {
                this.onAfterNext.accept(objPoll);
            }
            return objPoll;
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.rxjava3.operators.ConditionalSubscriber
        public boolean tryOnNext(Object obj) {
            boolean zTryOnNext = this.downstream.tryOnNext(obj);
            try {
                this.onAfterNext.accept(obj);
                return zTryOnNext;
            } catch (Throwable th) {
                fail(th);
                return zTryOnNext;
            }
        }
    }

    public static final class DoAfterSubscriber<T> extends BasicFuseableSubscriber<T, T> {
        public final Consumer onAfterNext;

        public DoAfterSubscriber(Subscriber subscriber, Consumer consumer) {
            super(subscriber);
            this.onAfterNext = consumer;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (this.done) {
                return;
            }
            this.downstream.onNext(obj);
            if (this.sourceMode == 0) {
                try {
                    this.onAfterNext.accept(obj);
                } catch (Throwable th) {
                    fail(th);
                }
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public Object poll() {
            Object objPoll = this.qs.poll();
            if (objPoll != null) {
                this.onAfterNext.accept(objPoll);
            }
            return objPoll;
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber) new DoAfterConditionalSubscriber((ConditionalSubscriber) subscriber, this.onAfterNext));
        } else {
            this.source.subscribe((FlowableSubscriber) new DoAfterSubscriber(subscriber, this.onAfterNext));
        }
    }
}
