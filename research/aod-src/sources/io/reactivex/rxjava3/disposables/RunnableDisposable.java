package io.reactivex.rxjava3.disposables;

/* JADX INFO: loaded from: classes2.dex */
final class RunnableDisposable extends ReferenceDisposable<Runnable> {
    public RunnableDisposable(Runnable runnable) {
        super(runnable);
    }

    @Override // io.reactivex.rxjava3.disposables.ReferenceDisposable
    public void onDisposed(Runnable runnable) {
        runnable.run();
    }

    @Override // java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "RunnableDisposable(disposed=" + isDisposed() + ", " + get() + ")";
    }
}
