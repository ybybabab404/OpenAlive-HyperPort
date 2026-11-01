package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.flowables.GroupedFlowable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.EmptyComponent;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableGroupBy<T, K, V> extends AbstractFlowableWithUpstream<T, GroupedFlowable<K, V>> {
    public final int bufferSize;
    public final boolean delayError;
    public final Function keySelector;
    public final Function mapFactory;
    public final Function valueSelector;

    public static final class EvictionAction<K, V> implements Consumer<GroupedUnicast<K, V>> {
        public final Queue evictedGroups;

        public EvictionAction(Queue queue) {
            this.evictedGroups = queue;
        }

        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(GroupedUnicast groupedUnicast) {
            this.evictedGroups.offer(groupedUnicast);
        }
    }

    public static final class GroupBySubscriber<T, K, V> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
        public static final Object NULL_KEY = new Object();
        public final int bufferSize;
        public final boolean delayError;
        public boolean done;
        public final Subscriber downstream;
        public long emittedGroups;
        public final Queue evictedGroups;
        public final Map groups;
        public final Function keySelector;
        public final int limit;
        public Subscription upstream;
        public final Function valueSelector;
        public final AtomicBoolean cancelled = new AtomicBoolean();
        public final AtomicInteger groupCount = new AtomicInteger(1);
        public final AtomicLong groupConsumed = new AtomicLong();

        public GroupBySubscriber(Subscriber subscriber, Function function, Function function2, int i, boolean z, Map map, Queue queue) {
            this.downstream = subscriber;
            this.keySelector = function;
            this.valueSelector = function2;
            this.bufferSize = i;
            this.limit = i - (i >> 2);
            this.delayError = z;
            this.groups = map;
            this.evictedGroups = queue;
        }

        public static MissingBackpressureException groupHangWarning(long j) {
            return new MissingBackpressureException("Unable to emit a new group (#" + j + ") due to lack of requests. Please make sure the downstream can always accept a new group as well as each group is consumed in order for the whole operator to be able to proceed.");
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled.compareAndSet(false, true)) {
                completeEvictions();
                if (this.groupCount.decrementAndGet() == 0) {
                    this.upstream.cancel();
                }
            }
        }

        public void cancel(Object obj) {
            if (obj == null) {
                obj = NULL_KEY;
            }
            if (this.groups.remove(obj) == null || this.groupCount.decrementAndGet() != 0) {
                return;
            }
            this.upstream.cancel();
        }

        public final void completeEvictions() {
            if (this.evictedGroups != null) {
                int i = 0;
                while (true) {
                    GroupedUnicast groupedUnicast = (GroupedUnicast) this.evictedGroups.poll();
                    if (groupedUnicast == null) {
                        break;
                    } else if (groupedUnicast.state.tryComplete()) {
                        i++;
                    }
                }
                if (i != 0) {
                    this.groupCount.addAndGet(-i);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            Iterator<V> it = this.groups.values().iterator();
            while (it.hasNext()) {
                ((GroupedUnicast) it.next()).onComplete();
            }
            this.groups.clear();
            completeEvictions();
            this.done = true;
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            Iterator<V> it = this.groups.values().iterator();
            while (it.hasNext()) {
                ((GroupedUnicast) it.next()).onError(th);
            }
            this.groups.clear();
            completeEvictions();
            this.downstream.onError(th);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            boolean z;
            if (this.done) {
                return;
            }
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
                    this.groupCount.getAndIncrement();
                    z = true;
                }
                try {
                    groupedUnicastCreateWith.onNext(ExceptionHelper.nullCheck(this.valueSelector.apply(obj), "The valueSelector returned a null value."));
                    completeEvictions();
                    if (z) {
                        if (this.emittedGroups == get()) {
                            this.upstream.cancel();
                            onError(groupHangWarning(this.emittedGroups));
                            return;
                        }
                        this.emittedGroups++;
                        this.downstream.onNext(groupedUnicastCreateWith);
                        if (groupedUnicastCreateWith.state.tryAbandon()) {
                            cancel(objApply);
                            groupedUnicastCreateWith.onComplete();
                            requestGroup(1L);
                        }
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.upstream.cancel();
                    if (z) {
                        if (this.emittedGroups == get()) {
                            MissingBackpressureException missingBackpressureExceptionGroupHangWarning = groupHangWarning(this.emittedGroups);
                            missingBackpressureExceptionGroupHangWarning.initCause(th);
                            onError(missingBackpressureExceptionGroupHangWarning);
                            return;
                        }
                        this.downstream.onNext(groupedUnicastCreateWith);
                    }
                    onError(th);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.upstream.cancel();
                onError(th2);
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                subscription.request(this.bufferSize);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this, j);
            }
        }

        public void requestGroup(long j) {
            long j2;
            long jAddCap;
            AtomicLong atomicLong = this.groupConsumed;
            int i = this.limit;
            do {
                j2 = atomicLong.get();
                jAddCap = BackpressureHelper.addCap(j2, j);
            } while (!atomicLong.compareAndSet(j2, jAddCap));
            while (true) {
                long j3 = i;
                if (jAddCap < j3) {
                    return;
                }
                if (atomicLong.compareAndSet(jAddCap, jAddCap - j3)) {
                    this.upstream.request(j3);
                }
                jAddCap = atomicLong.get();
            }
        }
    }

    public static final class GroupedUnicast<K, T> extends GroupedFlowable<K, T> {
        public final State state;

        public GroupedUnicast(Object obj, State state) {
            super(obj);
            this.state = state;
        }

        public static GroupedUnicast createWith(Object obj, int i, GroupBySubscriber groupBySubscriber, boolean z) {
            return new GroupedUnicast(obj, new State(i, groupBySubscriber, obj, z));
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

        @Override // io.reactivex.rxjava3.core.Flowable
        public void subscribeActual(Subscriber subscriber) {
            this.state.subscribe(subscriber);
        }
    }

    public static final class State<T, K> extends BasicIntQueueSubscription<T> implements Publisher<T> {
        public final boolean delayError;
        public volatile boolean done;
        public Throwable error;
        public final Object key;
        public boolean outputFused;
        public final GroupBySubscriber parent;
        public int produced;
        public final SpscLinkedArrayQueue queue;
        public final AtomicLong requested = new AtomicLong();
        public final AtomicBoolean cancelled = new AtomicBoolean();
        public final AtomicReference actual = new AtomicReference();
        public final AtomicInteger once = new AtomicInteger();
        public final AtomicBoolean evictOnce = new AtomicBoolean();

        public State(int i, GroupBySubscriber groupBySubscriber, Object obj, boolean z) {
            this.queue = new SpscLinkedArrayQueue(i);
            this.parent = groupBySubscriber;
            this.key = obj;
            this.delayError = z;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled.compareAndSet(false, true)) {
                cancelParent();
                drain();
            }
        }

        public void cancelParent() {
            if ((this.once.get() & 2) == 0 && this.evictOnce.compareAndSet(false, true)) {
                this.parent.cancel(this.key);
            }
        }

        public boolean checkTerminated(boolean z, boolean z2, Subscriber subscriber, boolean z3, long j, boolean z4) {
            if (this.cancelled.get()) {
                cleanupQueue(j, z4);
                return true;
            }
            if (!z) {
                return false;
            }
            if (z3) {
                if (!z2) {
                    return false;
                }
                this.cancelled.lazySet(true);
                Throwable th = this.error;
                if (th != null) {
                    subscriber.onError(th);
                } else {
                    subscriber.onComplete();
                    replenishParent(j, z4);
                }
                return true;
            }
            Throwable th2 = this.error;
            if (th2 != null) {
                this.queue.clear();
                this.cancelled.lazySet(true);
                subscriber.onError(th2);
                return true;
            }
            if (!z2) {
                return false;
            }
            this.cancelled.lazySet(true);
            subscriber.onComplete();
            replenishParent(j, z4);
            return true;
        }

        public void cleanupQueue(long j, boolean z) {
            while (this.queue.poll() != null) {
                j++;
            }
            replenishParent(j, z);
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public void clear() {
            SpscLinkedArrayQueue spscLinkedArrayQueue = this.queue;
            while (spscLinkedArrayQueue.poll() != null) {
                this.produced++;
            }
            tryReplenish();
        }

        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            if (this.outputFused) {
                drainFused();
            } else {
                drainNormal();
            }
        }

        public void drainFused() {
            Throwable th;
            SpscLinkedArrayQueue spscLinkedArrayQueue = this.queue;
            Subscriber subscriber = (Subscriber) this.actual.get();
            int iAddAndGet = 1;
            while (true) {
                if (subscriber != null) {
                    if (this.cancelled.get()) {
                        return;
                    }
                    boolean z = this.done;
                    if (z && !this.delayError && (th = this.error) != null) {
                        spscLinkedArrayQueue.clear();
                        subscriber.onError(th);
                        return;
                    }
                    subscriber.onNext(null);
                    if (z) {
                        Throwable th2 = this.error;
                        if (th2 != null) {
                            subscriber.onError(th2);
                            return;
                        } else {
                            subscriber.onComplete();
                            return;
                        }
                    }
                }
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
                if (subscriber == null) {
                    subscriber = (Subscriber) this.actual.get();
                }
            }
        }

        public void drainNormal() {
            SpscLinkedArrayQueue spscLinkedArrayQueue = this.queue;
            boolean z = this.delayError;
            Subscriber subscriber = (Subscriber) this.actual.get();
            AtomicBoolean atomicBoolean = this.cancelled;
            Subscriber subscriber2 = subscriber;
            int iAddAndGet = 1;
            while (true) {
                if (atomicBoolean.get()) {
                    cleanupQueue(0L, false);
                } else if (subscriber2 != null) {
                    long j = this.requested.get();
                    long j2 = 0;
                    while (true) {
                        if (j2 != j) {
                            boolean z2 = this.done;
                            Object objPoll = spscLinkedArrayQueue.poll();
                            boolean z3 = objPoll == null;
                            if (checkTerminated(z2, z3, subscriber2, z, j2, !z3)) {
                                continue;
                            } else if (!z3) {
                                subscriber2.onNext(objPoll);
                                j2++;
                            }
                        }
                        if (j2 != j || !checkTerminated(this.done, spscLinkedArrayQueue.isEmpty(), subscriber2, z, j2, false)) {
                            if (j2 != 0) {
                                BackpressureHelper.produced(this.requested, j2);
                                requestParent(j2);
                            }
                        }
                    }
                }
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
                if (subscriber2 == null) {
                    subscriber2 = (Subscriber) this.actual.get();
                }
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean isEmpty() {
            if (this.queue.isEmpty()) {
                tryReplenish();
                return true;
            }
            tryReplenish();
            return false;
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

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public Object poll() {
            Object objPoll = this.queue.poll();
            if (objPoll != null) {
                this.produced++;
                return objPoll;
            }
            tryReplenish();
            return null;
        }

        public void replenishParent(long j, boolean z) {
            if (z) {
                j++;
            }
            if (j != 0) {
                requestParent(j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int i) {
            return 0;
        }

        public void requestParent(long j) {
            if ((this.once.get() & 2) == 0) {
                this.parent.requestGroup(j);
            }
        }

        @Override // org.reactivestreams.Publisher
        public void subscribe(Subscriber subscriber) {
            int i;
            do {
                i = this.once.get();
                if ((i & 1) != 0) {
                    EmptySubscription.error(new IllegalStateException("Only one Subscriber allowed!"), subscriber);
                    return;
                }
            } while (!this.once.compareAndSet(i, i | 1));
            subscriber.onSubscribe(this);
            this.actual.lazySet(subscriber);
            if (this.cancelled.get()) {
                this.actual.lazySet(null);
            } else {
                drain();
            }
        }

        public boolean tryAbandon() {
            return this.once.get() == 0 && this.once.compareAndSet(0, 2);
        }

        public boolean tryComplete() {
            boolean zCompareAndSet = this.evictOnce.compareAndSet(false, true);
            this.done = true;
            drain();
            return zCompareAndSet;
        }

        public void tryReplenish() {
            int i = this.produced;
            if (i != 0) {
                this.produced = 0;
                requestParent(i);
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        Throwable th;
        Map concurrentHashMap;
        ConcurrentLinkedQueue concurrentLinkedQueue;
        try {
            if (this.mapFactory == null) {
                try {
                    concurrentHashMap = new ConcurrentHashMap();
                    concurrentLinkedQueue = null;
                } catch (Throwable th2) {
                    th = th2;
                    Exceptions.throwIfFatal(th);
                    subscriber.onSubscribe(EmptyComponent.INSTANCE);
                    subscriber.onError(th);
                    return;
                }
            } else {
                concurrentLinkedQueue = new ConcurrentLinkedQueue();
                concurrentHashMap = (Map) this.mapFactory.apply(new EvictionAction(concurrentLinkedQueue));
            }
            this.source.subscribe((FlowableSubscriber) new GroupBySubscriber(subscriber, this.keySelector, this.valueSelector, this.bufferSize, this.delayError, concurrentHashMap, concurrentLinkedQueue));
        } catch (Throwable th3) {
            th = th3;
        }
    }
}
