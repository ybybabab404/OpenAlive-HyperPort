package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.processors.FlowableProcessor;
import io.reactivex.rxjava3.processors.UnicastProcessor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes2.dex */
public class SchedulerWhen extends Scheduler implements Disposable {
    public final Scheduler actualScheduler;
    public Disposable disposable;
    public final FlowableProcessor workerProcessor;
    public static final Disposable SUBSCRIBED = new SubscribedDisposable();
    public static final Disposable DISPOSED = Disposable.disposed();

    public static final class CreateWorkerFunction implements Function<ScheduledAction, Completable> {
        public final Scheduler.Worker actualWorker;

        public final class WorkerCompletable extends Completable {
            public final ScheduledAction action;

            public WorkerCompletable(ScheduledAction scheduledAction) {
                this.action = scheduledAction;
            }

            @Override // io.reactivex.rxjava3.core.Completable
            public void subscribeActual(CompletableObserver completableObserver) {
                completableObserver.onSubscribe(this.action);
                this.action.call(CreateWorkerFunction.this.actualWorker, completableObserver);
            }
        }

        public CreateWorkerFunction(Scheduler.Worker worker) {
            this.actualWorker = worker;
        }

        @Override // io.reactivex.rxjava3.functions.Function
        public Completable apply(ScheduledAction scheduledAction) {
            return new WorkerCompletable(scheduledAction);
        }
    }

    public static class DelayedAction extends ScheduledAction {
        public final Runnable action;
        public final long delayTime;
        public final TimeUnit unit;

        public DelayedAction(Runnable runnable, long j, TimeUnit timeUnit) {
            this.action = runnable;
            this.delayTime = j;
            this.unit = timeUnit;
        }

        @Override // io.reactivex.rxjava3.internal.schedulers.SchedulerWhen.ScheduledAction
        public Disposable callActual(Scheduler.Worker worker, CompletableObserver completableObserver) {
            return worker.schedule(new OnCompletedAction(this.action, completableObserver), this.delayTime, this.unit);
        }
    }

    public static class ImmediateAction extends ScheduledAction {
        public final Runnable action;

        public ImmediateAction(Runnable runnable) {
            this.action = runnable;
        }

        @Override // io.reactivex.rxjava3.internal.schedulers.SchedulerWhen.ScheduledAction
        public Disposable callActual(Scheduler.Worker worker, CompletableObserver completableObserver) {
            return worker.schedule(new OnCompletedAction(this.action, completableObserver));
        }
    }

    public static class OnCompletedAction implements Runnable {
        public final Runnable action;
        public final CompletableObserver actionCompletable;

        public OnCompletedAction(Runnable runnable, CompletableObserver completableObserver) {
            this.action = runnable;
            this.actionCompletable = completableObserver;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.action.run();
            } finally {
                this.actionCompletable.onComplete();
            }
        }
    }

    public static final class QueueWorker extends Scheduler.Worker {
        public final FlowableProcessor actionProcessor;
        public final Scheduler.Worker actualWorker;
        public final AtomicBoolean unsubscribed = new AtomicBoolean();

        public QueueWorker(FlowableProcessor flowableProcessor, Scheduler.Worker worker) {
            this.actionProcessor = flowableProcessor;
            this.actualWorker = worker;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.unsubscribed.compareAndSet(false, true)) {
                this.actionProcessor.onComplete();
                this.actualWorker.dispose();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.unsubscribed.get();
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        public Disposable schedule(Runnable runnable) {
            ImmediateAction immediateAction = new ImmediateAction(runnable);
            this.actionProcessor.onNext(immediateAction);
            return immediateAction;
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        public Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit) {
            DelayedAction delayedAction = new DelayedAction(runnable, j, timeUnit);
            this.actionProcessor.onNext(delayedAction);
            return delayedAction;
        }
    }

    public static abstract class ScheduledAction extends AtomicReference<Disposable> implements Disposable {
        public ScheduledAction() {
            super(SchedulerWhen.SUBSCRIBED);
        }

        public void call(Scheduler.Worker worker, CompletableObserver completableObserver) {
            Disposable disposable;
            Disposable disposable2 = get();
            if (disposable2 != SchedulerWhen.DISPOSED && disposable2 == (disposable = SchedulerWhen.SUBSCRIBED)) {
                Disposable disposableCallActual = callActual(worker, completableObserver);
                if (compareAndSet(disposable, disposableCallActual)) {
                    return;
                }
                disposableCallActual.dispose();
            }
        }

        public abstract Disposable callActual(Scheduler.Worker worker, CompletableObserver completableObserver);

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            getAndSet(SchedulerWhen.DISPOSED).dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get().isDisposed();
        }
    }

    public static final class SubscribedDisposable implements Disposable {
        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return false;
        }
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public Scheduler.Worker createWorker() {
        Scheduler.Worker workerCreateWorker = this.actualScheduler.createWorker();
        FlowableProcessor serialized = UnicastProcessor.create().toSerialized();
        Flowable map = serialized.map(new CreateWorkerFunction(workerCreateWorker));
        QueueWorker queueWorker = new QueueWorker(serialized, workerCreateWorker);
        this.workerProcessor.onNext(map);
        return queueWorker;
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        this.disposable.dispose();
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.disposable.isDisposed();
    }
}
