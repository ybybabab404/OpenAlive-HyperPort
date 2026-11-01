package io.reactivex.rxjava3.subjects;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.internal.util.NotificationLite;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;

/* JADX INFO: loaded from: classes2.dex */
public final class BehaviorSubject<T> extends Subject<T> {
    public static final BehaviorDisposable[] EMPTY = new BehaviorDisposable[0];
    public static final BehaviorDisposable[] TERMINATED = new BehaviorDisposable[0];
    public long index;
    public final AtomicReference observers;
    public final Lock readLock;
    public final AtomicReference terminalEvent;
    public final AtomicReference value;
    public final Lock writeLock;

    public static final class BehaviorDisposable<T> implements Disposable, AppendOnlyLinkedArrayList.NonThrowingPredicate<Object> {
        public volatile boolean cancelled;
        public final Observer downstream;
        public boolean emitting;
        public boolean fastPath;
        public long index;
        public boolean next;
        public AppendOnlyLinkedArrayList queue;
        public final BehaviorSubject state;

        public BehaviorDisposable(Observer observer, BehaviorSubject behaviorSubject) {
            this.downstream = observer;
            this.state = behaviorSubject;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.state.remove(this);
        }

        public void emitFirst() {
            if (this.cancelled) {
                return;
            }
            synchronized (this) {
                try {
                    if (this.cancelled) {
                        return;
                    }
                    if (this.next) {
                        return;
                    }
                    BehaviorSubject behaviorSubject = this.state;
                    Lock lock = behaviorSubject.readLock;
                    lock.lock();
                    this.index = behaviorSubject.index;
                    Object obj = behaviorSubject.value.get();
                    lock.unlock();
                    this.emitting = obj != null;
                    this.next = true;
                    if (obj == null || test(obj)) {
                        return;
                    }
                    emitLoop();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void emitLoop() {
            AppendOnlyLinkedArrayList appendOnlyLinkedArrayList;
            while (!this.cancelled) {
                synchronized (this) {
                    try {
                        appendOnlyLinkedArrayList = this.queue;
                        if (appendOnlyLinkedArrayList == null) {
                            this.emitting = false;
                            return;
                        }
                        this.queue = null;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                appendOnlyLinkedArrayList.forEachWhile(this);
            }
        }

        public void emitNext(Object obj, long j) {
            if (this.cancelled) {
                return;
            }
            if (!this.fastPath) {
                synchronized (this) {
                    try {
                        if (this.cancelled) {
                            return;
                        }
                        if (this.index == j) {
                            return;
                        }
                        if (this.emitting) {
                            AppendOnlyLinkedArrayList appendOnlyLinkedArrayList = this.queue;
                            if (appendOnlyLinkedArrayList == null) {
                                appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList(4);
                                this.queue = appendOnlyLinkedArrayList;
                            }
                            appendOnlyLinkedArrayList.add(obj);
                            return;
                        }
                        this.next = true;
                        this.fastPath = true;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            test(obj);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.rxjava3.internal.util.AppendOnlyLinkedArrayList.NonThrowingPredicate, io.reactivex.rxjava3.functions.Predicate
        public boolean test(Object obj) {
            return this.cancelled || NotificationLite.accept(obj, this.downstream);
        }
    }

    public boolean add(BehaviorDisposable behaviorDisposable) {
        BehaviorDisposable[] behaviorDisposableArr;
        BehaviorDisposable[] behaviorDisposableArr2;
        do {
            behaviorDisposableArr = (BehaviorDisposable[]) this.observers.get();
            if (behaviorDisposableArr == TERMINATED) {
                return false;
            }
            int length = behaviorDisposableArr.length;
            behaviorDisposableArr2 = new BehaviorDisposable[length + 1];
            System.arraycopy(behaviorDisposableArr, 0, behaviorDisposableArr2, 0, length);
            behaviorDisposableArr2[length] = behaviorDisposable;
        } while (!this.observers.compareAndSet(behaviorDisposableArr, behaviorDisposableArr2));
        return true;
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        if (this.terminalEvent.compareAndSet(null, ExceptionHelper.TERMINATED)) {
            Object objComplete = NotificationLite.complete();
            for (BehaviorDisposable behaviorDisposable : terminate(objComplete)) {
                behaviorDisposable.emitNext(objComplete, this.index);
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(Throwable th) {
        ExceptionHelper.nullCheck(th, "onError called with a null Throwable.");
        if (!this.terminalEvent.compareAndSet(null, th)) {
            RxJavaPlugins.onError(th);
            return;
        }
        Object objError = NotificationLite.error(th);
        for (BehaviorDisposable behaviorDisposable : terminate(objError)) {
            behaviorDisposable.emitNext(objError, this.index);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(Object obj) {
        ExceptionHelper.nullCheck(obj, "onNext called with a null value.");
        if (this.terminalEvent.get() != null) {
            return;
        }
        Object next = NotificationLite.next(obj);
        setCurrent(next);
        for (BehaviorDisposable behaviorDisposable : (BehaviorDisposable[]) this.observers.get()) {
            behaviorDisposable.emitNext(next, this.index);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onSubscribe(Disposable disposable) {
        if (this.terminalEvent.get() != null) {
            disposable.dispose();
        }
    }

    public void remove(BehaviorDisposable behaviorDisposable) {
        BehaviorDisposable[] behaviorDisposableArr;
        BehaviorDisposable[] behaviorDisposableArr2;
        do {
            behaviorDisposableArr = (BehaviorDisposable[]) this.observers.get();
            int length = behaviorDisposableArr.length;
            if (length == 0) {
                return;
            }
            int i = 0;
            while (true) {
                if (i >= length) {
                    i = -1;
                    break;
                } else if (behaviorDisposableArr[i] == behaviorDisposable) {
                    break;
                } else {
                    i++;
                }
            }
            if (i < 0) {
                return;
            }
            if (length == 1) {
                behaviorDisposableArr2 = EMPTY;
            } else {
                BehaviorDisposable[] behaviorDisposableArr3 = new BehaviorDisposable[length - 1];
                System.arraycopy(behaviorDisposableArr, 0, behaviorDisposableArr3, 0, i);
                System.arraycopy(behaviorDisposableArr, i + 1, behaviorDisposableArr3, i, (length - i) - 1);
                behaviorDisposableArr2 = behaviorDisposableArr3;
            }
        } while (!this.observers.compareAndSet(behaviorDisposableArr, behaviorDisposableArr2));
    }

    public void setCurrent(Object obj) {
        this.writeLock.lock();
        this.index++;
        this.value.lazySet(obj);
        this.writeLock.unlock();
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        BehaviorDisposable behaviorDisposable = new BehaviorDisposable(observer, this);
        observer.onSubscribe(behaviorDisposable);
        if (add(behaviorDisposable)) {
            if (behaviorDisposable.cancelled) {
                remove(behaviorDisposable);
                return;
            } else {
                behaviorDisposable.emitFirst();
                return;
            }
        }
        Throwable th = (Throwable) this.terminalEvent.get();
        if (th == ExceptionHelper.TERMINATED) {
            observer.onComplete();
        } else {
            observer.onError(th);
        }
    }

    public BehaviorDisposable[] terminate(Object obj) {
        setCurrent(obj);
        return (BehaviorDisposable[]) this.observers.getAndSet(TERMINATED);
    }
}
