package io.reactivex.rxjava3.parallel;

import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import java.util.Objects;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public abstract class ParallelFlowable<T> {
    public abstract int parallelism();

    public abstract void subscribe(Subscriber[] subscriberArr);

    public final boolean validate(Subscriber[] subscriberArr) {
        Objects.requireNonNull(subscriberArr, "subscribers is null");
        int iParallelism = parallelism();
        if (subscriberArr.length == iParallelism) {
            return true;
        }
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("parallelism = " + iParallelism + ", subscribers = " + subscriberArr.length);
        int length = subscriberArr.length;
        for (int i = 0; i < length; i++) {
            EmptySubscription.error(illegalArgumentException, subscriberArr[i]);
        }
        return false;
    }
}
