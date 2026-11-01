package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.Objects;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableZipIterable<T, U, V> extends AbstractFlowableWithUpstream<T, V> {
    public final Iterable other;
    public final BiFunction zipper;

    public static final class ZipIterableSubscriber<T, U, V> implements FlowableSubscriber<T>, Subscription {
        public boolean done;
        public final Subscriber downstream;
        public final Iterator iterator;
        public Subscription upstream;
        public final BiFunction zipper;

        public ZipIterableSubscriber(Subscriber subscriber, Iterator it, BiFunction biFunction) {
            this.downstream = subscriber;
            this.iterator = it;
            this.zipper = biFunction;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
        }

        public void fail(Throwable th) {
            Exceptions.throwIfFatal(th);
            this.done = true;
            this.upstream.cancel();
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
            } else {
                this.done = true;
                this.downstream.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (this.done) {
                return;
            }
            try {
                Object next = this.iterator.next();
                Objects.requireNonNull(next, "The iterator returned a null value");
                try {
                    Object objApply = this.zipper.apply(obj, next);
                    Objects.requireNonNull(objApply, "The zipper function returned a null value");
                    this.downstream.onNext(objApply);
                    try {
                        if (this.iterator.hasNext()) {
                            return;
                        }
                        this.done = true;
                        this.upstream.cancel();
                        this.downstream.onComplete();
                    } catch (Throwable th) {
                        fail(th);
                    }
                } catch (Throwable th2) {
                    fail(th2);
                }
            } catch (Throwable th3) {
                fail(th3);
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.upstream.request(j);
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        try {
            Iterator<T> it = this.other.iterator();
            Objects.requireNonNull(it, "The iterator returned by other is null");
            Iterator<T> it2 = it;
            try {
                if (it2.hasNext()) {
                    this.source.subscribe((FlowableSubscriber) new ZipIterableSubscriber(subscriber, it2, this.zipper));
                } else {
                    EmptySubscription.complete(subscriber);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptySubscription.error(th, subscriber);
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            EmptySubscription.error(th2, subscriber);
        }
    }
}
