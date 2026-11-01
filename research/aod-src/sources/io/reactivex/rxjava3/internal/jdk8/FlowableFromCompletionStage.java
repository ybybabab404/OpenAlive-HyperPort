package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableFromCompletionStage<T> extends Flowable<T> {
    public final CompletionStage stage;

    public static final class BiConsumerAtomicReference<T> extends AtomicReference<BiConsumer<T, Throwable>> implements BiConsumer<T, Throwable> {
        @Override // java.util.function.BiConsumer
        public void accept(Object obj, Throwable th) {
            BiConsumer<T, Throwable> biConsumer = get();
            if (biConsumer != null) {
                biConsumer.accept(obj, th);
            }
        }
    }

    public static final class CompletionStageHandler<T> extends DeferredScalarSubscription<T> implements BiConsumer<T, Throwable> {
        public final BiConsumerAtomicReference whenReference;

        public CompletionStageHandler(Subscriber subscriber, BiConsumerAtomicReference biConsumerAtomicReference) {
            super(subscriber);
            this.whenReference = biConsumerAtomicReference;
        }

        @Override // java.util.function.BiConsumer
        public void accept(Object obj, Throwable th) {
            if (th != null) {
                this.downstream.onError(th);
            } else if (obj != null) {
                complete(obj);
            } else {
                this.downstream.onError(new NullPointerException("The CompletionStage terminated with null."));
            }
        }

        @Override // io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription, org.reactivestreams.Subscription
        public void cancel() {
            super.cancel();
            this.whenReference.set(null);
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        BiConsumerAtomicReference biConsumerAtomicReference = new BiConsumerAtomicReference();
        CompletionStageHandler completionStageHandler = new CompletionStageHandler(subscriber, biConsumerAtomicReference);
        biConsumerAtomicReference.lazySet(completionStageHandler);
        subscriber.onSubscribe(completionStageHandler);
        this.stage.whenComplete(biConsumerAtomicReference);
    }
}
