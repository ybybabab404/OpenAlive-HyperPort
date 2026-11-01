package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.schedulers.Timed;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes2.dex */
public final class MaybeTimeInterval<T> extends Maybe<Timed<T>> {
    public final Scheduler scheduler;
    public final MaybeSource source;
    public final boolean start;
    public final TimeUnit unit;

    public static final class TimeIntervalMaybeObserver<T> implements MaybeObserver<T>, Disposable {
        public final MaybeObserver downstream;
        public final Scheduler scheduler;
        public final long startTime;
        public final TimeUnit unit;
        public Disposable upstream;

        public TimeIntervalMaybeObserver(MaybeObserver maybeObserver, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
            this.downstream = maybeObserver;
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

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(Object obj) {
            this.downstream.onSuccess(new Timed(obj, this.scheduler.now(this.unit) - this.startTime, this.unit));
        }
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver maybeObserver) {
        this.source.subscribe(new TimeIntervalMaybeObserver(maybeObserver, this.unit, this.scheduler, this.start));
    }
}
