package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.fuseable.HasUpstreamPublisher;
import io.reactivex.rxjava3.internal.subscribers.SubscriberResourceWrapper;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.internal.util.NotificationLite;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Timed;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableReplay<T> extends ConnectableFlowable<T> implements HasUpstreamPublisher<T> {
    public static final Supplier DEFAULT_UNBOUNDED_FACTORY = new DefaultUnboundedFactory();
    public final Supplier bufferFactory;
    public final AtomicReference current;
    public final Publisher onSubscribe;
    public final Flowable source;

    public static abstract class BoundedReplayBuffer<T> extends AtomicReference<Node> implements ReplayBuffer<T> {
        public final boolean eagerTruncate;
        public long index;
        public int size;
        public Node tail;

        public BoundedReplayBuffer(boolean z) {
            this.eagerTruncate = z;
            Node node = new Node(null, 0L);
            this.tail = node;
            set(node);
        }

        public final void addLast(Node node) {
            this.tail.set(node);
            this.tail = node;
            this.size++;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.ReplayBuffer
        public final void complete() {
            Object objEnterTransform = enterTransform(NotificationLite.complete(), true);
            long j = this.index + 1;
            this.index = j;
            addLast(new Node(objEnterTransform, j));
            truncateFinal();
        }

        public Object enterTransform(Object obj, boolean z) {
            return obj;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.ReplayBuffer
        public final void error(Throwable th) {
            Object objEnterTransform = enterTransform(NotificationLite.error(th), true);
            long j = this.index + 1;
            this.index = j;
            addLast(new Node(objEnterTransform, j));
            truncateFinal();
        }

        public Node getHead() {
            return get();
        }

        public Object leaveTransform(Object obj) {
            return obj;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.ReplayBuffer
        public final void next(Object obj) {
            Object objEnterTransform = enterTransform(NotificationLite.next(obj), false);
            long j = this.index + 1;
            this.index = j;
            addLast(new Node(objEnterTransform, j));
            truncate();
        }

        public final void removeFirst() {
            Node node = get().get();
            if (node == null) {
                throw new IllegalStateException("Empty list!");
            }
            this.size--;
            setFirst(node);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.ReplayBuffer
        public final void replay(InnerSubscription innerSubscription) {
            synchronized (innerSubscription) {
                try {
                    if (innerSubscription.emitting) {
                        innerSubscription.missed = true;
                        return;
                    }
                    innerSubscription.emitting = true;
                    while (true) {
                        long j = innerSubscription.get();
                        boolean z = j == Long.MAX_VALUE;
                        Node head = (Node) innerSubscription.index();
                        if (head == null) {
                            head = getHead();
                            innerSubscription.index = head;
                            BackpressureHelper.add(innerSubscription.totalRequested, head.index);
                        }
                        long j2 = 0;
                        while (j != 0) {
                            if (!innerSubscription.isDisposed()) {
                                Node node = head.get();
                                if (node == null) {
                                    break;
                                }
                                Object objLeaveTransform = leaveTransform(node.value);
                                try {
                                    if (NotificationLite.accept(objLeaveTransform, innerSubscription.child)) {
                                        innerSubscription.index = null;
                                        return;
                                    } else {
                                        j2++;
                                        j--;
                                        head = node;
                                    }
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    innerSubscription.index = null;
                                    innerSubscription.dispose();
                                    if (NotificationLite.isError(objLeaveTransform) || NotificationLite.isComplete(objLeaveTransform)) {
                                        RxJavaPlugins.onError(th);
                                        return;
                                    } else {
                                        innerSubscription.child.onError(th);
                                        return;
                                    }
                                }
                            } else {
                                innerSubscription.index = null;
                                return;
                            }
                        }
                        if (j == 0 && innerSubscription.isDisposed()) {
                            innerSubscription.index = null;
                            return;
                        }
                        if (j2 != 0) {
                            innerSubscription.index = head;
                            if (!z) {
                                innerSubscription.produced(j2);
                            }
                        }
                        synchronized (innerSubscription) {
                            try {
                                if (!innerSubscription.missed) {
                                    innerSubscription.emitting = false;
                                    return;
                                }
                                innerSubscription.missed = false;
                            } catch (Throwable th2) {
                                throw th2;
                            }
                        }
                    }
                } catch (Throwable th3) {
                    throw th3;
                }
            }
        }

        public final void setFirst(Node node) {
            if (this.eagerTruncate) {
                Node node2 = new Node(null, node.index);
                node2.lazySet(node.get());
                node = node2;
            }
            set(node);
        }

        public final void trimHead() {
            Node node = get();
            if (node.value != null) {
                Node node2 = new Node(null, 0L);
                node2.lazySet(node.get());
                set(node2);
            }
        }

        public abstract void truncate();

        public void truncateFinal() {
            trimHead();
        }
    }

    public static final class DefaultUnboundedFactory implements Supplier<Object> {
        @Override // io.reactivex.rxjava3.functions.Supplier
        public Object get() {
            return new UnboundedReplayBuffer(16);
        }
    }

    public static final class InnerSubscription<T> extends AtomicLong implements Subscription, Disposable {
        public final Subscriber child;
        public boolean emitting;
        public Object index;
        public boolean missed;
        public final ReplaySubscriber parent;
        public final AtomicLong totalRequested = new AtomicLong();

        public InnerSubscription(ReplaySubscriber replaySubscriber, Subscriber subscriber) {
            this.parent = replaySubscriber;
            this.child = subscriber;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.parent.remove(this);
                this.parent.manageRequests();
                this.index = null;
            }
        }

        public Object index() {
            return this.index;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() == Long.MIN_VALUE;
        }

        public long produced(long j) {
            return BackpressureHelper.producedCancel(this, j);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (!SubscriptionHelper.validate(j) || BackpressureHelper.addCancel(this, j) == Long.MIN_VALUE) {
                return;
            }
            BackpressureHelper.add(this.totalRequested, j);
            this.parent.manageRequests();
            this.parent.buffer.replay(this);
        }
    }

    public static final class MulticastFlowable<R, U> extends Flowable<R> {
        public final Supplier connectableFactory;
        public final Function selector;

        public final class DisposableConsumer implements Consumer<Disposable> {
            public final SubscriberResourceWrapper srw;

            public DisposableConsumer(SubscriberResourceWrapper subscriberResourceWrapper) {
                this.srw = subscriberResourceWrapper;
            }

            @Override // io.reactivex.rxjava3.functions.Consumer
            public void accept(Disposable disposable) {
                this.srw.setResource(disposable);
            }
        }

        @Override // io.reactivex.rxjava3.core.Flowable
        public void subscribeActual(Subscriber subscriber) {
            try {
                ConnectableFlowable connectableFlowable = (ConnectableFlowable) ExceptionHelper.nullCheck(this.connectableFactory.get(), "The connectableFactory returned a null ConnectableFlowable.");
                try {
                    Publisher publisher = (Publisher) ExceptionHelper.nullCheck(this.selector.apply(connectableFlowable), "The selector returned a null Publisher.");
                    SubscriberResourceWrapper subscriberResourceWrapper = new SubscriberResourceWrapper(subscriber);
                    publisher.subscribe(subscriberResourceWrapper);
                    connectableFlowable.connect(new DisposableConsumer(subscriberResourceWrapper));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    EmptySubscription.error(th, subscriber);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                EmptySubscription.error(th2, subscriber);
            }
        }
    }

    public static final class Node extends AtomicReference<Node> {
        public final long index;
        public final Object value;

        public Node(Object obj, long j) {
            this.value = obj;
            this.index = j;
        }
    }

    public interface ReplayBuffer<T> {
        void complete();

        void error(Throwable th);

        void next(Object obj);

        void replay(InnerSubscription innerSubscription);
    }

    public static final class ReplayBufferSupplier<T> implements Supplier<ReplayBuffer<T>> {
        public final int bufferSize;
        public final boolean eagerTruncate;

        public ReplayBufferSupplier(int i, boolean z) {
            this.bufferSize = i;
            this.eagerTruncate = z;
        }

        @Override // io.reactivex.rxjava3.functions.Supplier
        public ReplayBuffer get() {
            return new SizeBoundReplayBuffer(this.bufferSize, this.eagerTruncate);
        }
    }

    public static final class ReplayPublisher<T> implements Publisher<T> {
        public final Supplier bufferFactory;
        public final AtomicReference curr;

        public ReplayPublisher(AtomicReference atomicReference, Supplier supplier) {
            this.curr = atomicReference;
            this.bufferFactory = supplier;
        }

        @Override // org.reactivestreams.Publisher
        public void subscribe(Subscriber subscriber) {
            ReplaySubscriber replaySubscriber;
            while (true) {
                replaySubscriber = (ReplaySubscriber) this.curr.get();
                if (replaySubscriber != null) {
                    break;
                }
                try {
                    ReplaySubscriber replaySubscriber2 = new ReplaySubscriber((ReplayBuffer) this.bufferFactory.get(), this.curr);
                    if (this.curr.compareAndSet(null, replaySubscriber2)) {
                        replaySubscriber = replaySubscriber2;
                        break;
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    EmptySubscription.error(th, subscriber);
                    return;
                }
            }
            InnerSubscription innerSubscription = new InnerSubscription(replaySubscriber, subscriber);
            subscriber.onSubscribe(innerSubscription);
            replaySubscriber.add(innerSubscription);
            if (innerSubscription.isDisposed()) {
                replaySubscriber.remove(innerSubscription);
            } else {
                replaySubscriber.manageRequests();
                replaySubscriber.buffer.replay(innerSubscription);
            }
        }
    }

    public static final class ReplaySubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Disposable {
        public static final InnerSubscription[] EMPTY = new InnerSubscription[0];
        public static final InnerSubscription[] TERMINATED = new InnerSubscription[0];
        public final ReplayBuffer buffer;
        public final AtomicReference current;
        public boolean done;
        public long requestedFromUpstream;
        public final AtomicInteger management = new AtomicInteger();
        public final AtomicReference subscribers = new AtomicReference(EMPTY);
        public final AtomicBoolean shouldConnect = new AtomicBoolean();

        public ReplaySubscriber(ReplayBuffer replayBuffer, AtomicReference atomicReference) {
            this.buffer = replayBuffer;
            this.current = atomicReference;
        }

        public boolean add(InnerSubscription innerSubscription) {
            InnerSubscription[] innerSubscriptionArr;
            InnerSubscription[] innerSubscriptionArr2;
            do {
                innerSubscriptionArr = (InnerSubscription[]) this.subscribers.get();
                if (innerSubscriptionArr == TERMINATED) {
                    return false;
                }
                int length = innerSubscriptionArr.length;
                innerSubscriptionArr2 = new InnerSubscription[length + 1];
                System.arraycopy(innerSubscriptionArr, 0, innerSubscriptionArr2, 0, length);
                innerSubscriptionArr2[length] = innerSubscription;
            } while (!this.subscribers.compareAndSet(innerSubscriptionArr, innerSubscriptionArr2));
            return true;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.subscribers.set(TERMINATED);
            this.current.compareAndSet(this, null);
            SubscriptionHelper.cancel(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.subscribers.get() == TERMINATED;
        }

        public void manageRequests() {
            AtomicInteger atomicInteger = this.management;
            if (atomicInteger.getAndIncrement() != 0) {
                return;
            }
            int iAddAndGet = 1;
            while (!isDisposed()) {
                Subscription subscription = get();
                if (subscription != null) {
                    long j = this.requestedFromUpstream;
                    long jMax = j;
                    for (InnerSubscription innerSubscription : (InnerSubscription[]) this.subscribers.get()) {
                        jMax = Math.max(jMax, innerSubscription.totalRequested.get());
                    }
                    long j2 = jMax - j;
                    if (j2 != 0) {
                        this.requestedFromUpstream = jMax;
                        subscription.request(j2);
                    }
                }
                iAddAndGet = atomicInteger.addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.buffer.complete();
            for (InnerSubscription innerSubscription : (InnerSubscription[]) this.subscribers.getAndSet(TERMINATED)) {
                this.buffer.replay(innerSubscription);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.buffer.error(th);
            for (InnerSubscription innerSubscription : (InnerSubscription[]) this.subscribers.getAndSet(TERMINATED)) {
                this.buffer.replay(innerSubscription);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (this.done) {
                return;
            }
            this.buffer.next(obj);
            for (InnerSubscription innerSubscription : (InnerSubscription[]) this.subscribers.get()) {
                this.buffer.replay(innerSubscription);
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                manageRequests();
                for (InnerSubscription innerSubscription : (InnerSubscription[]) this.subscribers.get()) {
                    this.buffer.replay(innerSubscription);
                }
            }
        }

        public void remove(InnerSubscription innerSubscription) {
            InnerSubscription[] innerSubscriptionArr;
            InnerSubscription[] innerSubscriptionArr2;
            do {
                innerSubscriptionArr = (InnerSubscription[]) this.subscribers.get();
                int length = innerSubscriptionArr.length;
                if (length == 0) {
                    return;
                }
                int i = 0;
                while (true) {
                    if (i >= length) {
                        i = -1;
                        break;
                    } else if (innerSubscriptionArr[i].equals(innerSubscription)) {
                        break;
                    } else {
                        i++;
                    }
                }
                if (i < 0) {
                    return;
                }
                if (length == 1) {
                    innerSubscriptionArr2 = EMPTY;
                } else {
                    InnerSubscription[] innerSubscriptionArr3 = new InnerSubscription[length - 1];
                    System.arraycopy(innerSubscriptionArr, 0, innerSubscriptionArr3, 0, i);
                    System.arraycopy(innerSubscriptionArr, i + 1, innerSubscriptionArr3, i, (length - i) - 1);
                    innerSubscriptionArr2 = innerSubscriptionArr3;
                }
            } while (!this.subscribers.compareAndSet(innerSubscriptionArr, innerSubscriptionArr2));
        }
    }

    public static final class ScheduledReplayBufferSupplier<T> implements Supplier<ReplayBuffer<T>> {
        public final int bufferSize;
        public final boolean eagerTruncate;
        public final long maxAge;
        public final Scheduler scheduler;
        public final TimeUnit unit;

        public ScheduledReplayBufferSupplier(int i, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
            this.bufferSize = i;
            this.maxAge = j;
            this.unit = timeUnit;
            this.scheduler = scheduler;
            this.eagerTruncate = z;
        }

        @Override // io.reactivex.rxjava3.functions.Supplier
        public ReplayBuffer get() {
            return new SizeAndTimeBoundReplayBuffer(this.bufferSize, this.maxAge, this.unit, this.scheduler, this.eagerTruncate);
        }
    }

    public static final class SizeAndTimeBoundReplayBuffer<T> extends BoundedReplayBuffer<T> {
        public final int limit;
        public final long maxAge;
        public final Scheduler scheduler;
        public final TimeUnit unit;

        public SizeAndTimeBoundReplayBuffer(int i, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
            super(z);
            this.scheduler = scheduler;
            this.limit = i;
            this.maxAge = j;
            this.unit = timeUnit;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.BoundedReplayBuffer
        public Object enterTransform(Object obj, boolean z) {
            return new Timed(obj, z ? Long.MAX_VALUE : this.scheduler.now(this.unit), this.unit);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.BoundedReplayBuffer
        public Node getHead() {
            Node node;
            long jNow = this.scheduler.now(this.unit) - this.maxAge;
            Node node2 = get();
            Node node3 = node2.get();
            while (true) {
                Node node4 = node3;
                node = node2;
                node2 = node4;
                if (node2 == null) {
                    break;
                }
                Timed timed = (Timed) node2.value;
                if (NotificationLite.isComplete(timed.value()) || NotificationLite.isError(timed.value()) || timed.time() > jNow) {
                    break;
                }
                node3 = node2.get();
            }
            return node;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.BoundedReplayBuffer
        public Object leaveTransform(Object obj) {
            return ((Timed) obj).value();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.BoundedReplayBuffer
        public void truncate() {
            Node node;
            long jNow = this.scheduler.now(this.unit) - this.maxAge;
            Node node2 = get();
            Node node3 = node2.get();
            int i = 0;
            while (true) {
                Node node4 = node3;
                node = node2;
                node2 = node4;
                int i2 = this.size;
                if (i2 > 1) {
                    if (i2 <= this.limit) {
                        if (((Timed) node2.value).time() > jNow) {
                            break;
                        }
                        i++;
                        this.size--;
                        node3 = node2.get();
                    } else {
                        i++;
                        this.size = i2 - 1;
                        node3 = node2.get();
                    }
                } else {
                    break;
                }
            }
            if (i != 0) {
                setFirst(node);
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.BoundedReplayBuffer
        public void truncateFinal() {
            Node node;
            long jNow = this.scheduler.now(this.unit) - this.maxAge;
            Node node2 = get();
            Node node3 = node2.get();
            int i = 0;
            while (true) {
                Node node4 = node3;
                node = node2;
                node2 = node4;
                if (this.size <= 1 || ((Timed) node2.value).time() > jNow) {
                    break;
                }
                i++;
                this.size--;
                node3 = node2.get();
            }
            if (i != 0) {
                setFirst(node);
            }
        }
    }

    public static final class SizeBoundReplayBuffer<T> extends BoundedReplayBuffer<T> {
        public final int limit;

        public SizeBoundReplayBuffer(int i, boolean z) {
            super(z);
            this.limit = i;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.BoundedReplayBuffer
        public void truncate() {
            if (this.size > this.limit) {
                removeFirst();
            }
        }
    }

    public static final class UnboundedReplayBuffer<T> extends ArrayList<Object> implements ReplayBuffer<T> {
        public volatile int size;

        public UnboundedReplayBuffer(int i) {
            super(i);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.ReplayBuffer
        public void complete() {
            add(NotificationLite.complete());
            this.size++;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.ReplayBuffer
        public void error(Throwable th) {
            add(NotificationLite.error(th));
            this.size++;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.ReplayBuffer
        public void next(Object obj) {
            add(NotificationLite.next(obj));
            this.size++;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableReplay.ReplayBuffer
        public void replay(InnerSubscription innerSubscription) {
            synchronized (innerSubscription) {
                try {
                    if (innerSubscription.emitting) {
                        innerSubscription.missed = true;
                        return;
                    }
                    innerSubscription.emitting = true;
                    Subscriber subscriber = innerSubscription.child;
                    while (!innerSubscription.isDisposed()) {
                        int i = this.size;
                        Integer num = (Integer) innerSubscription.index();
                        int iIntValue = num != null ? num.intValue() : 0;
                        long j = innerSubscription.get();
                        long j2 = j;
                        long j3 = 0;
                        while (j2 != 0 && iIntValue < i) {
                            Object obj = get(iIntValue);
                            try {
                                if (NotificationLite.accept(obj, subscriber) || innerSubscription.isDisposed()) {
                                    return;
                                }
                                iIntValue++;
                                j2--;
                                j3++;
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                innerSubscription.dispose();
                                if (NotificationLite.isError(obj) || NotificationLite.isComplete(obj)) {
                                    RxJavaPlugins.onError(th);
                                    return;
                                } else {
                                    subscriber.onError(th);
                                    return;
                                }
                            }
                        }
                        if (j3 != 0) {
                            innerSubscription.index = Integer.valueOf(iIntValue);
                            if (j != Long.MAX_VALUE) {
                                innerSubscription.produced(j3);
                            }
                        }
                        synchronized (innerSubscription) {
                            try {
                                if (!innerSubscription.missed) {
                                    innerSubscription.emitting = false;
                                    return;
                                }
                                innerSubscription.missed = false;
                            } catch (Throwable th2) {
                                throw th2;
                            }
                        }
                    }
                } catch (Throwable th3) {
                    throw th3;
                }
            }
        }
    }

    public FlowableReplay(Publisher publisher, Flowable flowable, AtomicReference atomicReference, Supplier supplier) {
        this.onSubscribe = publisher;
        this.source = flowable;
        this.current = atomicReference;
        this.bufferFactory = supplier;
    }

    public static ConnectableFlowable create(Flowable flowable, int i, boolean z) {
        return i == Integer.MAX_VALUE ? createFrom(flowable) : create(flowable, new ReplayBufferSupplier(i, z));
    }

    public static ConnectableFlowable create(Flowable flowable, long j, TimeUnit timeUnit, Scheduler scheduler, int i, boolean z) {
        return create(flowable, new ScheduledReplayBufferSupplier(i, j, timeUnit, scheduler, z));
    }

    public static ConnectableFlowable create(Flowable flowable, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        return create(flowable, j, timeUnit, scheduler, Integer.MAX_VALUE, z);
    }

    public static ConnectableFlowable create(Flowable flowable, Supplier supplier) {
        AtomicReference atomicReference = new AtomicReference();
        return RxJavaPlugins.onAssembly((ConnectableFlowable) new FlowableReplay(new ReplayPublisher(atomicReference, supplier), flowable, atomicReference, supplier));
    }

    public static ConnectableFlowable createFrom(Flowable flowable) {
        return create(flowable, DEFAULT_UNBOUNDED_FACTORY);
    }

    @Override // io.reactivex.rxjava3.flowables.ConnectableFlowable
    public void connect(Consumer consumer) {
        ReplaySubscriber replaySubscriber;
        while (true) {
            replaySubscriber = (ReplaySubscriber) this.current.get();
            if (replaySubscriber != null && !replaySubscriber.isDisposed()) {
                break;
            }
            try {
                ReplaySubscriber replaySubscriber2 = new ReplaySubscriber((ReplayBuffer) this.bufferFactory.get(), this.current);
                if (this.current.compareAndSet(replaySubscriber, replaySubscriber2)) {
                    replaySubscriber = replaySubscriber2;
                    break;
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                throw ExceptionHelper.wrapOrThrow(th);
            }
        }
        boolean z = !replaySubscriber.shouldConnect.get() && replaySubscriber.shouldConnect.compareAndSet(false, true);
        try {
            consumer.accept(replaySubscriber);
            if (z) {
                this.source.subscribe((FlowableSubscriber) replaySubscriber);
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            if (z) {
                replaySubscriber.shouldConnect.compareAndSet(true, false);
            }
            Exceptions.throwIfFatal(th2);
            throw ExceptionHelper.wrapOrThrow(th2);
        }
    }

    @Override // io.reactivex.rxjava3.flowables.ConnectableFlowable
    public void reset() {
        ReplaySubscriber replaySubscriber = (ReplaySubscriber) this.current.get();
        if (replaySubscriber == null || !replaySubscriber.isDisposed()) {
            return;
        }
        this.current.compareAndSet(replaySubscriber, null);
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        this.onSubscribe.subscribe(subscriber);
    }
}
