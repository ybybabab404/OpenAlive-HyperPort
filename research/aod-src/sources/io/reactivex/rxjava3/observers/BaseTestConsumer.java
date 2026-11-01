package io.reactivex.rxjava3.observers;

import io.reactivex.rxjava3.observers.BaseTestConsumer;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes2.dex */
public abstract class BaseTestConsumer<T, U extends BaseTestConsumer<T, U>> {
    public boolean checkSubscriptionOnce;
    public long completions;
    public final CountDownLatch done;
    public final List errors;
    public Thread lastThread;
    public final List values;
}
