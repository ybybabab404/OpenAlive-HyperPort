package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.schedulers.Timed;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes2.dex */
public final class SingleTimeInterval<T> extends Single<Timed<T>> {
    public final Scheduler scheduler;
    public final SingleSource source;
    public final boolean start;
    public final TimeUnit unit;

    public static final class TimeIntervalSingleObserver<T> implements SingleObserver<T>, Disposable {
        public final SingleObserver downstream;
        public final Scheduler scheduler;
        public final long startTime;
        public final TimeUnit unit;
        public Disposable upstream;

        public TimeIntervalSingleObserver(SingleObserver singleObserver, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
            this.downstream = singleObserver;
            this.unit = timeUnit;
            this.scheduler = scheduler;
            this.startTime = z ? scheduler.now(timeUnit) : 0L;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(Object obj) {
            this.downstream.onSuccess(new Timed(obj, this.scheduler.now(this.unit) - this.startTime, this.unit));
        }
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver singleObserver) {
        this.source.subscribe(new TimeIntervalSingleObserver(singleObserver, this.unit, this.scheduler, this.start));
    }
}
