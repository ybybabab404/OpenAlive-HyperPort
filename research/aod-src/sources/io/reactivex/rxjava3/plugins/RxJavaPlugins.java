package io.reactivex.rxjava3.plugins;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.exceptions.OnErrorNotImplementedException;
import io.reactivex.rxjava3.exceptions.QueueOverflowException;
import io.reactivex.rxjava3.exceptions.UndeliverableException;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.BooleanSupplier;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.schedulers.ExecutorScheduler;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.observables.ConnectableObservable;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import java.util.Objects;
import java.util.concurrent.Executor;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public final class RxJavaPlugins {
    public static volatile Consumer errorHandler;
    public static volatile boolean failNonBlockingScheduler;
    public static volatile BooleanSupplier onBeforeBlocking;
    public static volatile BiFunction onCompletableSubscribe;
    public static volatile Function onConnectableFlowableAssembly;
    public static volatile Function onConnectableObservableAssembly;
    public static volatile Function onFlowableAssembly;
    public static volatile BiFunction onFlowableSubscribe;
    public static volatile Function onInitComputationHandler;
    public static volatile Function onInitIoHandler;
    public static volatile Function onInitNewThreadHandler;
    public static volatile Function onInitSingleHandler;
    public static volatile Function onIoHandler;
    public static volatile BiFunction onMaybeSubscribe;
    public static volatile Function onObservableAssembly;
    public static volatile BiFunction onObservableSubscribe;
    public static volatile BiFunction onParallelSubscribe;
    public static volatile Function onScheduleHandler;
    public static volatile Function onSingleAssembly;
    public static volatile Function onSingleHandler;
    public static volatile BiFunction onSingleSubscribe;

    public static Object apply(BiFunction biFunction, Object obj, Object obj2) {
        try {
            return biFunction.apply(obj, obj2);
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    public static Object apply(Function function, Object obj) {
        try {
            return function.apply(obj);
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    public static Scheduler applyRequireNonNull(Function function, Supplier supplier) {
        Object objApply = apply(function, supplier);
        Objects.requireNonNull(objApply, "Scheduler Supplier result can't be null");
        return (Scheduler) objApply;
    }

    public static Scheduler callRequireNonNull(Supplier supplier) {
        try {
            Object obj = supplier.get();
            Objects.requireNonNull(obj, "Scheduler Supplier result can't be null");
            return (Scheduler) obj;
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    public static Scheduler createExecutorScheduler(Executor executor, boolean z, boolean z2) {
        return new ExecutorScheduler(executor, z, z2);
    }

    public static Scheduler initComputationScheduler(Supplier supplier) {
        Objects.requireNonNull(supplier, "Scheduler Supplier can't be null");
        Function function = onInitComputationHandler;
        return function == null ? callRequireNonNull(supplier) : applyRequireNonNull(function, supplier);
    }

    public static Scheduler initIoScheduler(Supplier supplier) {
        Objects.requireNonNull(supplier, "Scheduler Supplier can't be null");
        Function function = onInitIoHandler;
        return function == null ? callRequireNonNull(supplier) : applyRequireNonNull(function, supplier);
    }

    public static Scheduler initNewThreadScheduler(Supplier supplier) {
        Objects.requireNonNull(supplier, "Scheduler Supplier can't be null");
        Function function = onInitNewThreadHandler;
        return function == null ? callRequireNonNull(supplier) : applyRequireNonNull(function, supplier);
    }

    public static Scheduler initSingleScheduler(Supplier supplier) {
        Objects.requireNonNull(supplier, "Scheduler Supplier can't be null");
        Function function = onInitSingleHandler;
        return function == null ? callRequireNonNull(supplier) : applyRequireNonNull(function, supplier);
    }

    public static boolean isBug(Throwable th) {
        return (th instanceof OnErrorNotImplementedException) || (th instanceof MissingBackpressureException) || (th instanceof QueueOverflowException) || (th instanceof IllegalStateException) || (th instanceof NullPointerException) || (th instanceof IllegalArgumentException) || (th instanceof CompositeException);
    }

    public static boolean isFailOnNonBlockingScheduler() {
        return failNonBlockingScheduler;
    }

    public static Flowable onAssembly(Flowable flowable) {
        Function function = onFlowableAssembly;
        return function != null ? (Flowable) apply(function, flowable) : flowable;
    }

    public static Observable onAssembly(Observable observable) {
        Function function = onObservableAssembly;
        return function != null ? (Observable) apply(function, observable) : observable;
    }

    public static Single onAssembly(Single single) {
        Function function = onSingleAssembly;
        return function != null ? (Single) apply(function, single) : single;
    }

    public static ConnectableFlowable onAssembly(ConnectableFlowable connectableFlowable) {
        Function function = onConnectableFlowableAssembly;
        return function != null ? (ConnectableFlowable) apply(function, connectableFlowable) : connectableFlowable;
    }

    public static ConnectableObservable onAssembly(ConnectableObservable connectableObservable) {
        Function function = onConnectableObservableAssembly;
        return function != null ? (ConnectableObservable) apply(function, connectableObservable) : connectableObservable;
    }

    public static boolean onBeforeBlocking() {
        BooleanSupplier booleanSupplier = onBeforeBlocking;
        if (booleanSupplier == null) {
            return false;
        }
        try {
            return booleanSupplier.getAsBoolean();
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    public static void onError(Throwable th) {
        Consumer consumer = errorHandler;
        if (th == null) {
            th = ExceptionHelper.createNullPointerException("onError called with a null Throwable.");
        } else if (!isBug(th)) {
            th = new UndeliverableException(th);
        }
        if (consumer != null) {
            try {
                consumer.accept(th);
                return;
            } catch (Throwable th2) {
                th2.printStackTrace();
                uncaught(th2);
            }
        }
        th.printStackTrace();
        uncaught(th);
    }

    public static Scheduler onIoScheduler(Scheduler scheduler) {
        Function function = onIoHandler;
        return function == null ? scheduler : (Scheduler) apply(function, scheduler);
    }

    public static Runnable onSchedule(Runnable runnable) {
        Objects.requireNonNull(runnable, "run is null");
        Function function = onScheduleHandler;
        return function == null ? runnable : (Runnable) apply(function, runnable);
    }

    public static Scheduler onSingleScheduler(Scheduler scheduler) {
        Function function = onSingleHandler;
        return function == null ? scheduler : (Scheduler) apply(function, scheduler);
    }

    public static CompletableObserver onSubscribe(Completable completable, CompletableObserver completableObserver) {
        BiFunction biFunction = onCompletableSubscribe;
        return biFunction != null ? (CompletableObserver) apply(biFunction, completable, completableObserver) : completableObserver;
    }

    public static MaybeObserver onSubscribe(Maybe maybe, MaybeObserver maybeObserver) {
        BiFunction biFunction = onMaybeSubscribe;
        return biFunction != null ? (MaybeObserver) apply(biFunction, maybe, maybeObserver) : maybeObserver;
    }

    public static Observer onSubscribe(Observable observable, Observer observer) {
        BiFunction biFunction = onObservableSubscribe;
        return biFunction != null ? (Observer) apply(biFunction, observable, observer) : observer;
    }

    public static SingleObserver onSubscribe(Single single, SingleObserver singleObserver) {
        BiFunction biFunction = onSingleSubscribe;
        return biFunction != null ? (SingleObserver) apply(biFunction, single, singleObserver) : singleObserver;
    }

    public static Subscriber onSubscribe(Flowable flowable, Subscriber subscriber) {
        BiFunction biFunction = onFlowableSubscribe;
        return biFunction != null ? (Subscriber) apply(biFunction, flowable, subscriber) : subscriber;
    }

    public static Subscriber[] onSubscribe(ParallelFlowable parallelFlowable, Subscriber[] subscriberArr) {
        BiFunction biFunction = onParallelSubscribe;
        return biFunction != null ? (Subscriber[]) apply(biFunction, parallelFlowable, subscriberArr) : subscriberArr;
    }

    public static void uncaught(Throwable th) {
        Thread threadCurrentThread = Thread.currentThread();
        threadCurrentThread.getUncaughtExceptionHandler().uncaughtException(threadCurrentThread, th);
    }
}
