package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableCache<T> extends AbstractObservableWithUpstream<T, T> implements Observer<T> {
    public static final CacheDisposable[] EMPTY = new CacheDisposable[0];
    public static final CacheDisposable[] TERMINATED = new CacheDisposable[0];
    public final int capacityHint;
    public volatile boolean done;
    public Throwable error;
    public final Node head;
    public final AtomicReference observers;
    public final AtomicBoolean once;
    public volatile long size;
    public Node tail;
    public int tailOffset;

    public static final class CacheDisposable<T> extends AtomicInteger implements Disposable {
        public volatile boolean disposed;
        public final Observer downstream;
        public long index;
        public Node node;
        public int offset;
        public final ObservableCache parent;

        public CacheDisposable(Observer observer, ObservableCache observableCache) {
            this.downstream = observer;
            this.parent = observableCache;
            this.node = observableCache.head;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.disposed) {
                return;
            }
            this.disposed = true;
            this.parent.remove(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }
    }

    public static final class Node<T> {
        public volatile Node next;
        public final Object[] values;

        public Node(int i) {
            this.values = new Object[i];
        }
    }

    public void add(CacheDisposable cacheDisposable) {
        CacheDisposable[] cacheDisposableArr;
        CacheDisposable[] cacheDisposableArr2;
        do {
            cacheDisposableArr = (CacheDisposable[]) this.observers.get();
            if (cacheDisposableArr == TERMINATED) {
                return;
            }
            int length = cacheDisposableArr.length;
            cacheDisposableArr2 = new CacheDisposable[length + 1];
            System.arraycopy(cacheDisposableArr, 0, cacheDisposableArr2, 0, length);
            cacheDisposableArr2[length] = cacheDisposable;
        } while (!this.observers.compareAndSet(cacheDisposableArr, cacheDisposableArr2));
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        this.done = true;
        for (CacheDisposable cacheDisposable : (CacheDisposable[]) this.observers.getAndSet(TERMINATED)) {
            replay(cacheDisposable);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(Throwable th) {
        this.error = th;
        this.done = true;
        for (CacheDisposable cacheDisposable : (CacheDisposable[]) this.observers.getAndSet(TERMINATED)) {
            replay(cacheDisposable);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(Object obj) {
        int i = this.tailOffset;
        if (i == this.capacityHint) {
            Node node = new Node(i);
            node.values[0] = obj;
            this.tailOffset = 1;
            this.tail.next = node;
            this.tail = node;
        } else {
            this.tail.values[i] = obj;
            this.tailOffset = i + 1;
        }
        this.size++;
        for (CacheDisposable cacheDisposable : (CacheDisposable[]) this.observers.get()) {
            replay(cacheDisposable);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onSubscribe(Disposable disposable) {
    }

    public void remove(CacheDisposable cacheDisposable) {
        CacheDisposable[] cacheDisposableArr;
        CacheDisposable[] cacheDisposableArr2;
        do {
            cacheDisposableArr = (CacheDisposable[]) this.observers.get();
            int length = cacheDisposableArr.length;
            if (length == 0) {
                return;
            }
            int i = 0;
            while (true) {
                if (i >= length) {
                    i = -1;
                    break;
                } else if (cacheDisposableArr[i] == cacheDisposable) {
                    break;
                } else {
                    i++;
                }
            }
            if (i < 0) {
                return;
            }
            if (length == 1) {
                cacheDisposableArr2 = EMPTY;
            } else {
                CacheDisposable[] cacheDisposableArr3 = new CacheDisposable[length - 1];
                System.arraycopy(cacheDisposableArr, 0, cacheDisposableArr3, 0, i);
                System.arraycopy(cacheDisposableArr, i + 1, cacheDisposableArr3, i, (length - i) - 1);
                cacheDisposableArr2 = cacheDisposableArr3;
            }
        } while (!this.observers.compareAndSet(cacheDisposableArr, cacheDisposableArr2));
    }

    public void replay(CacheDisposable cacheDisposable) {
        if (cacheDisposable.getAndIncrement() != 0) {
            return;
        }
        long j = cacheDisposable.index;
        int i = cacheDisposable.offset;
        Node node = cacheDisposable.node;
        Observer observer = cacheDisposable.downstream;
        int i2 = this.capacityHint;
        int iAddAndGet = 1;
        while (!cacheDisposable.disposed) {
            boolean z = this.done;
            boolean z2 = this.size == j;
            if (z && z2) {
                cacheDisposable.node = null;
                Throwable th = this.error;
                if (th != null) {
                    observer.onError(th);
                    return;
                } else {
                    observer.onComplete();
                    return;
                }
            }
            if (z2) {
                cacheDisposable.index = j;
                cacheDisposable.offset = i;
                cacheDisposable.node = node;
                iAddAndGet = cacheDisposable.addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            } else {
                if (i == i2) {
                    node = node.next;
                    i = 0;
                }
                observer.onNext(node.values[i]);
                i++;
                j++;
            }
        }
        cacheDisposable.node = null;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        CacheDisposable cacheDisposable = new CacheDisposable(observer, this);
        observer.onSubscribe(cacheDisposable);
        add(cacheDisposable);
        if (this.once.get() || !this.once.compareAndSet(false, true)) {
            replay(cacheDisposable);
        } else {
            this.source.subscribe(this);
        }
    }
}
