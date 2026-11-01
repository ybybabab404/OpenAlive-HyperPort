package io.reactivex.rxjava3.internal.queue;

import io.reactivex.rxjava3.operators.SimplePlainQueue;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes2.dex */
public final class MpscLinkedQueue<T> implements SimplePlainQueue<T> {
    public final AtomicReference producerNode = new AtomicReference();
    public final AtomicReference consumerNode = new AtomicReference();

    public static final class LinkedQueueNode<E> extends AtomicReference<LinkedQueueNode<E>> {
        public Object value;

        public LinkedQueueNode() {
        }

        public LinkedQueueNode(Object obj) {
            spValue(obj);
        }

        public Object getAndNullValue() {
            Object objLpValue = lpValue();
            spValue(null);
            return objLpValue;
        }

        public Object lpValue() {
            return this.value;
        }

        public LinkedQueueNode lvNext() {
            return get();
        }

        public void soNext(LinkedQueueNode linkedQueueNode) {
            lazySet(linkedQueueNode);
        }

        public void spValue(Object obj) {
            this.value = obj;
        }
    }

    public MpscLinkedQueue() {
        LinkedQueueNode linkedQueueNode = new LinkedQueueNode();
        spConsumerNode(linkedQueueNode);
        xchgProducerNode(linkedQueueNode);
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public void clear() {
        while (poll() != null && !isEmpty()) {
        }
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public boolean isEmpty() {
        return lvConsumerNode() == lvProducerNode();
    }

    public LinkedQueueNode lpConsumerNode() {
        return (LinkedQueueNode) this.consumerNode.get();
    }

    public LinkedQueueNode lvConsumerNode() {
        return (LinkedQueueNode) this.consumerNode.get();
    }

    public LinkedQueueNode lvProducerNode() {
        return (LinkedQueueNode) this.producerNode.get();
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public boolean offer(Object obj) {
        if (obj == null) {
            throw new NullPointerException("Null is not a valid element");
        }
        LinkedQueueNode linkedQueueNode = new LinkedQueueNode(obj);
        xchgProducerNode(linkedQueueNode).soNext(linkedQueueNode);
        return true;
    }

    @Override // io.reactivex.rxjava3.operators.SimplePlainQueue, io.reactivex.rxjava3.operators.SimpleQueue
    public Object poll() {
        LinkedQueueNode linkedQueueNodeLvNext;
        LinkedQueueNode linkedQueueNodeLpConsumerNode = lpConsumerNode();
        LinkedQueueNode linkedQueueNodeLvNext2 = linkedQueueNodeLpConsumerNode.lvNext();
        if (linkedQueueNodeLvNext2 != null) {
            Object andNullValue = linkedQueueNodeLvNext2.getAndNullValue();
            spConsumerNode(linkedQueueNodeLvNext2);
            return andNullValue;
        }
        if (linkedQueueNodeLpConsumerNode == lvProducerNode()) {
            return null;
        }
        do {
            linkedQueueNodeLvNext = linkedQueueNodeLpConsumerNode.lvNext();
        } while (linkedQueueNodeLvNext == null);
        Object andNullValue2 = linkedQueueNodeLvNext.getAndNullValue();
        spConsumerNode(linkedQueueNodeLvNext);
        return andNullValue2;
    }

    public void spConsumerNode(LinkedQueueNode linkedQueueNode) {
        this.consumerNode.lazySet(linkedQueueNode);
    }

    public LinkedQueueNode xchgProducerNode(LinkedQueueNode linkedQueueNode) {
        return (LinkedQueueNode) this.producerNode.getAndSet(linkedQueueNode);
    }
}
