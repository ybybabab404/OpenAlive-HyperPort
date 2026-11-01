package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.observables.GroupedObservable;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableGroupBy<T, K, V> extends AbstractObservableWithUpstream<T, GroupedObservable<K, V>> {
    public final int bufferSize;
    public final boolean delayError;
    public final Function keySelector;
    public final Function valueSelector;

    public static final class GroupByObserver<T, K, V> extends AtomicInteger implements Observer<T>, Disposable {
        public static final Object NULL_KEY = new Object();
        public final int bufferSize;
        public final boolean delayError;
        public final Observer downstream;
        public final Function keySelector;
        public Disposable upstream;
        public final Function valueSelector;
        public final AtomicBoolean cancelled = new AtomicBoolean();
        public final Map groups = new ConcurrentHashMap();

        public GroupByObserver(Observer observer, Function function, Function function2, int i, boolean z) {
            this.downstream = observer;
            this.keySelector = function;
            this.valueSelector = function2;
            this.bufferSize = i;
            this.delayError = z;
            lazySet(1);
        }

        public void cancel(Object obj) {
            if (obj == null) {
                obj = NULL_KEY;
            }
            this.groups.remove(obj);
            if (decrementAndGet() == 0) {
                this.upstream.dispose();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.cancelled.compareAndSet(false, true) && decrementAndGet() == 0) {
                this.upstream.dispose();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled.get();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            ArrayList arrayList = new ArrayList(this.groups.values());
            this.groups.clear();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((GroupedUnicast) it.next()).onComplete();
            }
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            ArrayList arrayList = new ArrayList(this.groups.values());
            this.groups.clear();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((GroupedUnicast) it.next()).onError(th);
            }
            this.downstream.onError(th);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            boolean z;
            try {
                Object objApply = this.keySelector.apply(obj);
                Object obj2 = objApply != null ? objApply : NULL_KEY;
                GroupedUnicast groupedUnicastCreateWith = (GroupedUnicast) this.groups.get(obj2);
                if (groupedUnicastCreateWith != null) {
                    z = false;
                } else {
                    if (this.cancelled.get()) {
                        return;
                    }
                    groupedUnicastCreateWith = GroupedUnicast.createWith(objApply, this.bufferSize, this, this.delayError);
                    this.groups.put(obj2, groupedUnicastCreateWith);
                    getAndIncrement();
                    z = true;
                }
                try {
                    Object objApply2 = this.valueSelector.apply(obj);
                    Objects.requireNonNull(objApply2, "The value supplied is null");
                    groupedUnicastCreateWith.onNext(objApply2);
                    if (z) {
                        this.downstream.onNext(groupedUnicastCreateWith);
                        if (groupedUnicastCreateWith.state.tryAbandon()) {
                            cancel(objApply);
                            groupedUnicastCreateWith.onComplete();
                        }
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.upstream.dispose();
                    if (z) {
                        this.downstream.onNext(groupedUnicastCreateWith);
                    }
                    onError(th);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.upstream.dispose();
                onError(th2);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public static final class GroupedUnicast<K, T> extends GroupedObservable<K, T> {
        public final State state;

        public GroupedUnicast(Object obj, State state) {
            super(obj);
            this.state = state;
        }

        public static GroupedUnicast createWith(Object obj, int i, GroupByObserver groupByObserver, boolean z) {
            return new GroupedUnicast(obj, new State(i, groupByObserver, obj, z));
        }

        public void onComplete() {
            this.state.onComplete();
        }

        public void onError(Throwable th) {
            this.state.onError(th);
        }

        public void onNext(Object obj) {
            this.state.onNext(obj);
        }

        @Override // io.reactivex.rxjava3.core.Observable
        public void subscribeActual(Observer observer) {
            this.state.subscribe(observer);
        }
    }

    public static final class State<T, K> extends AtomicInteger implements Disposable, ObservableSource<T> {
        public final boolean delayError;
        public volatile boolean done;
        public Throwable error;
        public final Object key;
        public final GroupByObserver parent;
        public final SpscLinkedArrayQueue queue;
        public final AtomicBoolean cancelled = new AtomicBoolean();
        public final AtomicReference actual = new AtomicReference();
        public final AtomicInteger once = new AtomicInteger();

        public State(int i, GroupByObserver groupByObserver, Object obj, boolean z) {
            this.queue = new SpscLinkedArrayQueue(i);
            this.parent = groupByObserver;
            this.key = obj;
            this.delayError = z;
        }

        public void cancelParent() {
            if ((this.once.get() & 2) == 0) {
                this.parent.cancel(this.key);
            }
        }

        public boolean checkTerminated(boolean z, boolean z2, Observer observer, boolean z3) {
            if (this.cancelled.get()) {
                this.queue.clear();
                this.actual.lazySet(null);
                cancelParent();
                return true;
            }
            if (!z) {
                return false;
            }
            if (z3) {
                if (!z2) {
                    return false;
                }
                Throwable th = this.error;
                this.actual.lazySet(null);
                if (th != null) {
                    observer.onError(th);
                } else {
                    observer.onComplete();
                }
                return true;
            }
            Throwable th2 = this.error;
            if (th2 != null) {
                this.queue.clear();
                this.actual.lazySet(null);
                observer.onError(th2);
                return true;
            }
            if (!z2) {
                return false;
            }
            this.actual.lazySet(null);
            observer.onComplete();
            return true;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.cancelled.compareAndSet(false, true) && getAndIncrement() == 0) {
                this.actual.lazySet(null);
                cancelParent();
            }
        }

        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            SpscLinkedArrayQueue spscLinkedArrayQueue = this.queue;
            boolean z = this.delayError;
            Observer observer = (Observer) this.actual.get();
            int iAddAndGet = 1;
            while (true) {
                if (observer != null) {
                    while (true) {
                        boolean z2 = this.done;
                        Object objPoll = spscLinkedArrayQueue.poll();
                        boolean z3 = objPoll == null;
                        if (checkTerminated(z2, z3, observer, z)) {
                            return;
                        }
                        if (z3) {
                            break;
                        } else {
                            observer.onNext(objPoll);
                        }
                    }
                }
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
                if (observer == null) {
                    observer = (Observer) this.actual.get();
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled.get();
        }

        public void onComplete() {
            this.done = true;
            drain();
        }

        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            drain();
        }

        public void onNext(Object obj) {
            this.queue.offer(obj);
            drain();
        }

        @Override // io.reactivex.rxjava3.core.ObservableSource
        public void subscribe(Observer observer) {
            int i;
            do {
                i = this.once.get();
                if ((i & 1) != 0) {
                    EmptyDisposable.error(new IllegalStateException("Only one Observer allowed!"), observer);
                    return;
                }
            } while (!this.once.compareAndSet(i, i | 1));
            observer.onSubscribe(this);
            this.actual.lazySet(observer);
            if (this.cancelled.get()) {
                this.actual.lazySet(null);
            } else {
                drain();
            }
        }

        public boolean tryAbandon() {
            return this.once.get() == 0 && this.once.compareAndSet(0, 2);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        this.source.subscribe(new GroupByObserver(observer, this.keySelector, this.valueSelector, this.bufferSize, this.delayError));
    }
}
