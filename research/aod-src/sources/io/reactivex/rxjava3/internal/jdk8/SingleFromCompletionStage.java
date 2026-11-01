package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;

/* JADX INFO: loaded from: classes2.dex */
public final class SingleFromCompletionStage<T> extends Single<T> {
    public final CompletionStage stage;

    public static final class CompletionStageHandler<T> implements Disposable, BiConsumer<T, Throwable> {
        public final SingleObserver downstream;
        public final FlowableFromCompletionStage.BiConsumerAtomicReference whenReference;

        public CompletionStageHandler(SingleObserver singleObserver, FlowableFromCompletionStage.BiConsumerAtomicReference biConsumerAtomicReference) {
            this.downstream = singleObserver;
            this.whenReference = biConsumerAtomicReference;
        }

        @Override // java.util.function.BiConsumer
        public void accept(Object obj, Throwable th) {
            if (th != null) {
                this.downstream.onError(th);
            } else if (obj != null) {
                this.downstream.onSuccess(obj);
            } else {
                this.downstream.onError(new NullPointerException("The CompletionStage terminated with null."));
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.whenReference.set(null);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.whenReference.get() == null;
        }
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver singleObserver) {
        FlowableFromCompletionStage.BiConsumerAtomicReference biConsumerAtomicReference = new FlowableFromCompletionStage.BiConsumerAtomicReference();
        CompletionStageHandler completionStageHandler = new CompletionStageHandler(singleObserver, biConsumerAtomicReference);
        biConsumerAtomicReference.lazySet(completionStageHandler);
        singleObserver.onSubscribe(completionStageHandler);
        this.stage.whenComplete(biConsumerAtomicReference);
    }
}
