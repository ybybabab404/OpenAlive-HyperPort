package io.reactivex.rxjava3.schedulers;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes2.dex */
public final class TestScheduler extends Scheduler {
    public long counter;
    public final Queue queue;
    public volatile long time;
    public final boolean useOnScheduleHook;

    public final class TestWorker extends Scheduler.Worker {
        public volatile boolean disposed;

        public final class QueueRemove extends AtomicReference<TimedRunnable> implements Disposable {
            public QueueRemove(TimedRunnable timedRunnable) {
                lazySet(timedRunnable);
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public void dispose() {
                TimedRunnable andSet = getAndSet(null);
                if (andSet != null) {
                    TestScheduler.this.queue.remove(andSet);
                }
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public boolean isDisposed() {
                return get() == null;
            }
        }

        public TestWorker() {
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.disposed = true;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        public long now(TimeUnit timeUnit) {
            return TestScheduler.this.now(timeUnit);
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        public Disposable schedule(Runnable runnable) {
            if (this.disposed) {
                return EmptyDisposable.INSTANCE;
            }
            if (TestScheduler.this.useOnScheduleHook) {
                runnable = RxJavaPlugins.onSchedule(runnable);
            }
            Runnable runnable2 = runnable;
            TestScheduler testScheduler = TestScheduler.this;
            long j = testScheduler.counter;
            testScheduler.counter = 1 + j;
            TimedRunnable timedRunnable = new TimedRunnable(this, 0L, runnable2, j);
            TestScheduler.this.queue.add(timedRunnable);
            return new QueueRemove(timedRunnable);
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        public Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit) {
            if (this.disposed) {
                return EmptyDisposable.INSTANCE;
            }
            if (TestScheduler.this.useOnScheduleHook) {
                runnable = RxJavaPlugins.onSchedule(runnable);
            }
            Runnable runnable2 = runnable;
            long nanos = TestScheduler.this.time + timeUnit.toNanos(j);
            TestScheduler testScheduler = TestScheduler.this;
            long j2 = testScheduler.counter;
            testScheduler.counter = 1 + j2;
            TimedRunnable timedRunnable = new TimedRunnable(this, nanos, runnable2, j2);
            TestScheduler.this.queue.add(timedRunnable);
            return new QueueRemove(timedRunnable);
        }
    }

    public static final class TimedRunnable implements Comparable<TimedRunnable> {
        public final long count;
        public final Runnable run;
        public final TestWorker scheduler;
        public final long time;

        public TimedRunnable(TestWorker testWorker, long j, Runnable runnable, long j2) {
            this.time = j;
            this.run = runnable;
            this.scheduler = testWorker;
            this.count = j2;
        }

        @Override // java.lang.Comparable
        public int compareTo(TimedRunnable timedRunnable) {
            long j = this.time;
            long j2 = timedRunnable.time;
            return j == j2 ? Long.compare(this.count, timedRunnable.count) : Long.compare(j, j2);
        }

        public String toString() {
            return String.format("TimedRunnable(time = %d, run = %s)", Long.valueOf(this.time), this.run.toString());
        }
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public Scheduler.Worker createWorker() {
        return new TestWorker();
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public long now(TimeUnit timeUnit) {
        return timeUnit.convert(this.time, TimeUnit.NANOSECONDS);
    }
}
