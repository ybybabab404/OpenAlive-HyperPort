package org.greenrobot.greendao.rx;

import java.util.concurrent.Callable;
import org.greenrobot.greendao.annotation.apihint.Experimental;
import org.greenrobot.greendao.annotation.apihint.Internal;
import rx.Observable;
import rx.Scheduler;

/* JADX INFO: loaded from: classes3.dex */
@Internal
class RxBase {
    protected final Scheduler scheduler;

    public RxBase() {
        this.scheduler = null;
    }

    @Experimental
    public RxBase(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Experimental
    public Scheduler getScheduler() {
        return this.scheduler;
    }

    public <R> Observable<R> wrap(Callable<R> callable) {
        return wrap(RxUtils.fromCallable(callable));
    }

    public <R> Observable<R> wrap(Observable<R> observable) {
        Scheduler scheduler = this.scheduler;
        return scheduler != null ? observable.subscribeOn(scheduler) : observable;
    }
}
