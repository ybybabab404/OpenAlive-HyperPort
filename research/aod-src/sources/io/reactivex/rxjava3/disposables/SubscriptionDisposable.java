package io.reactivex.rxjava3.disposables;

import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes2.dex */
final class SubscriptionDisposable extends ReferenceDisposable<Subscription> {
    @Override // io.reactivex.rxjava3.disposables.ReferenceDisposable
    public void onDisposed(Subscription subscription) {
        subscription.cancel();
    }
}
