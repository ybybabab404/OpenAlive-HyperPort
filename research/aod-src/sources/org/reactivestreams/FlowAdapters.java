package org.reactivestreams;

import java.util.concurrent.Flow;

/* JADX INFO: loaded from: classes3.dex */
public final class FlowAdapters {

    public static final class FlowPublisherFromReactive<T> implements Flow.Publisher<T> {
        public final Publisher reactiveStreams;

        @Override // java.util.concurrent.Flow.Publisher
        public void subscribe(Flow.Subscriber subscriber) {
            this.reactiveStreams.subscribe(subscriber == null ? null : new ReactiveToFlowSubscriber(subscriber));
        }
    }

    public static final class FlowToReactiveProcessor<T, U> implements Flow.Processor<T, U> {
        public final Processor reactiveStreams;

        @Override // java.util.concurrent.Flow.Subscriber
        public void onComplete() {
            this.reactiveStreams.onComplete();
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onError(Throwable th) {
            this.reactiveStreams.onError(th);
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onNext(Object obj) {
            this.reactiveStreams.onNext(obj);
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onSubscribe(Flow.Subscription subscription) {
            this.reactiveStreams.onSubscribe(subscription == null ? null : new ReactiveToFlowSubscription(subscription));
        }

        @Override // java.util.concurrent.Flow.Publisher
        public void subscribe(Flow.Subscriber subscriber) {
            this.reactiveStreams.subscribe(subscriber == null ? null : new ReactiveToFlowSubscriber(subscriber));
        }
    }

    public static final class FlowToReactiveSubscriber<T> implements Flow.Subscriber<T> {
        public final Subscriber reactiveStreams;

        public FlowToReactiveSubscriber(Subscriber subscriber) {
            this.reactiveStreams = subscriber;
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onComplete() {
            this.reactiveStreams.onComplete();
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onError(Throwable th) {
            this.reactiveStreams.onError(th);
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onNext(Object obj) {
            this.reactiveStreams.onNext(obj);
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onSubscribe(Flow.Subscription subscription) {
            this.reactiveStreams.onSubscribe(subscription == null ? null : new ReactiveToFlowSubscription(subscription));
        }
    }

    public static final class FlowToReactiveSubscription implements Flow.Subscription {
        public final Subscription reactiveStreams;

        public FlowToReactiveSubscription(Subscription subscription) {
            this.reactiveStreams = subscription;
        }

        @Override // java.util.concurrent.Flow.Subscription
        public void cancel() {
            this.reactiveStreams.cancel();
        }

        @Override // java.util.concurrent.Flow.Subscription
        public void request(long j) {
            this.reactiveStreams.request(j);
        }
    }

    public static final class ReactivePublisherFromFlow<T> implements Publisher<T> {
        public final Flow.Publisher flow;

        @Override // org.reactivestreams.Publisher
        public void subscribe(Subscriber subscriber) {
            this.flow.subscribe(subscriber == null ? null : new FlowToReactiveSubscriber(subscriber));
        }
    }

    public static final class ReactiveToFlowProcessor<T, U> implements Processor<T, U> {
        public final Flow.Processor flow;

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.flow.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.flow.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            this.flow.onNext(obj);
        }

        @Override // org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.flow.onSubscribe(subscription == null ? null : new FlowToReactiveSubscription(subscription));
        }

        @Override // org.reactivestreams.Publisher
        public void subscribe(Subscriber subscriber) {
            this.flow.subscribe(subscriber == null ? null : new FlowToReactiveSubscriber(subscriber));
        }
    }

    public static final class ReactiveToFlowSubscriber<T> implements Subscriber<T> {
        public final Flow.Subscriber flow;

        public ReactiveToFlowSubscriber(Flow.Subscriber subscriber) {
            this.flow = subscriber;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.flow.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.flow.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            this.flow.onNext(obj);
        }

        @Override // org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.flow.onSubscribe(subscription == null ? null : new FlowToReactiveSubscription(subscription));
        }
    }

    public static final class ReactiveToFlowSubscription implements Subscription {
        public final Flow.Subscription flow;

        public ReactiveToFlowSubscription(Flow.Subscription subscription) {
            this.flow = subscription;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.flow.cancel();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.flow.request(j);
        }
    }
}
