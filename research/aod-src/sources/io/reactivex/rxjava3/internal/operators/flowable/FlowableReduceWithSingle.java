package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import java.util.Objects;
import org.reactivestreams.Publisher;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableReduceWithSingle<T, R> extends Single<R> {
    public final BiFunction reducer;
    public final Supplier seedSupplier;
    public final Publisher source;

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver singleObserver) {
        try {
            Object obj = this.seedSupplier.get();
            Objects.requireNonNull(obj, "The seedSupplier returned a null value");
            this.source.subscribe(new FlowableReduceSeedSingle.ReduceSeedObserver(singleObserver, this.reducer, obj));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, singleObserver);
        }
    }
}
