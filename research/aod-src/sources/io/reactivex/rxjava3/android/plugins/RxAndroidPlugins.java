package io.reactivex.rxjava3.android.plugins;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes2.dex */
public final class RxAndroidPlugins {
    public static volatile Function onInitMainThreadHandler;
    public static volatile Function onMainThreadHandler;

    public static Object apply(Function function, Object obj) {
        try {
            return function.apply(obj);
        } catch (Throwable th) {
            throw Exceptions.propagate(th);
        }
    }

    public static Scheduler applyRequireNonNull(Function function, Callable callable) {
        Scheduler scheduler = (Scheduler) apply(function, callable);
        if (scheduler != null) {
            return scheduler;
        }
        throw new NullPointerException("Scheduler Callable returned null");
    }

    public static Scheduler callRequireNonNull(Callable callable) {
        try {
            Scheduler scheduler = (Scheduler) callable.call();
            if (scheduler != null) {
                return scheduler;
            }
            throw new NullPointerException("Scheduler Callable returned null");
        } catch (Throwable th) {
            throw Exceptions.propagate(th);
        }
    }

    public static Scheduler initMainThreadScheduler(Callable callable) {
        if (callable == null) {
            throw new NullPointerException("scheduler == null");
        }
        Function function = onInitMainThreadHandler;
        return function == null ? callRequireNonNull(callable) : applyRequireNonNull(function, callable);
    }

    public static Scheduler onMainThreadScheduler(Scheduler scheduler) {
        if (scheduler == null) {
            throw new NullPointerException("scheduler == null");
        }
        Function function = onMainThreadHandler;
        return function == null ? scheduler : (Scheduler) apply(function, scheduler);
    }
}
