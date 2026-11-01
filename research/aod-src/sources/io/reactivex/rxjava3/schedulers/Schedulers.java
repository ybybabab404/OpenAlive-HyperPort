package io.reactivex.rxjava3.schedulers;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.schedulers.ComputationScheduler;
import io.reactivex.rxjava3.internal.schedulers.IoScheduler;
import io.reactivex.rxjava3.internal.schedulers.NewThreadScheduler;
import io.reactivex.rxjava3.internal.schedulers.SingleScheduler;
import io.reactivex.rxjava3.internal.schedulers.TrampolineScheduler;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes2.dex */
public final class Schedulers {
    public static final Scheduler SINGLE = RxJavaPlugins.initSingleScheduler(new SingleTask());
    public static final Scheduler COMPUTATION = RxJavaPlugins.initComputationScheduler(new ComputationTask());
    public static final Scheduler IO = RxJavaPlugins.initIoScheduler(new IOTask());
    public static final Scheduler TRAMPOLINE = TrampolineScheduler.instance();
    public static final Scheduler NEW_THREAD = RxJavaPlugins.initNewThreadScheduler(new NewThreadTask());

    public static final class ComputationHolder {
        public static final Scheduler DEFAULT = new ComputationScheduler();
    }

    public static final class ComputationTask implements Supplier<Scheduler> {
        @Override // io.reactivex.rxjava3.functions.Supplier
        public Scheduler get() {
            return ComputationHolder.DEFAULT;
        }
    }

    public static final class IOTask implements Supplier<Scheduler> {
        @Override // io.reactivex.rxjava3.functions.Supplier
        public Scheduler get() {
            return IoHolder.DEFAULT;
        }
    }

    public static final class IoHolder {
        public static final Scheduler DEFAULT = new IoScheduler();
    }

    public static final class NewThreadHolder {
        public static final Scheduler DEFAULT = new NewThreadScheduler();
    }

    public static final class NewThreadTask implements Supplier<Scheduler> {
        @Override // io.reactivex.rxjava3.functions.Supplier
        public Scheduler get() {
            return NewThreadHolder.DEFAULT;
        }
    }

    public static final class SingleHolder {
        public static final Scheduler DEFAULT = new SingleScheduler();
    }

    public static final class SingleTask implements Supplier<Scheduler> {
        @Override // io.reactivex.rxjava3.functions.Supplier
        public Scheduler get() {
            return SingleHolder.DEFAULT;
        }
    }

    public static Scheduler from(Executor executor) {
        return from(executor, false, false);
    }

    public static Scheduler from(Executor executor, boolean z, boolean z2) {
        return RxJavaPlugins.createExecutorScheduler(executor, z, z2);
    }

    public static Scheduler io() {
        return RxJavaPlugins.onIoScheduler(IO);
    }

    public static Scheduler single() {
        return RxJavaPlugins.onSingleScheduler(SINGLE);
    }
}
