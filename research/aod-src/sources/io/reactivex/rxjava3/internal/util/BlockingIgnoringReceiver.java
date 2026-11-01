package io.reactivex.rxjava3.internal.util;

import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes2.dex */
public final class BlockingIgnoringReceiver extends CountDownLatch implements Consumer<Throwable>, Action {
    public Throwable error;

    @Override // io.reactivex.rxjava3.functions.Consumer
    public void accept(Throwable th) {
        this.error = th;
        countDown();
    }

    @Override // io.reactivex.rxjava3.functions.Action
    public void run() {
        countDown();
    }
}
