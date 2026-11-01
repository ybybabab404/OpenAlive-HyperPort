package io.reactivex.rxjava3.operators;

import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import io.reactivex.rxjava3.internal.util.Pow2;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;

/* JADX INFO: loaded from: classes2.dex */
public final class SpscLinkedArrayQueue<T> implements SimplePlainQueue<T> {
    public AtomicReferenceArray consumerBuffer;
    public final int consumerMask;
    public AtomicReferenceArray producerBuffer;
    public long producerLookAhead;
    public int producerLookAheadStep;
    public final int producerMask;
    public static final int MAX_LOOK_AHEAD_STEP = Integer.getInteger("jctools.spsc.max.lookahead.step", AutoBottomSheetKt.ALIGN_SCREEN_TOP).intValue();
    public static final Object HAS_NEXT = new Object();
    public final AtomicLong producerIndex = new AtomicLong();
    public final AtomicLong consumerIndex = new AtomicLong();

    public SpscLinkedArrayQueue(int i) {
        int iRoundToPowerOfTwo = Pow2.roundToPowerOfTwo(Math.max(8, i));
        int i2 = iRoundToPowerOfTwo - 1;
        AtomicReferenceArray atomicReferenceArray = new AtomicReferenceArray(iRoundToPowerOfTwo + 1);
        this.producerBuffer = atomicReferenceArray;
        this.producerMask = i2;
        adjustLookAheadStep(iRoundToPowerOfTwo);
        this.consumerBuffer = atomicReferenceArray;
        this.consumerMask = i2;
        this.producerLookAhead = iRoundToPowerOfTwo - 2;
        soProducerIndex(0L);
    }

    public static int calcDirectOffset(int i) {
        return i;
    }

    public static int calcWrappedOffset(long j, int i) {
        return calcDirectOffset(((int) j) & i);
    }

    public static Object lvElement(AtomicReferenceArray atomicReferenceArray, int i) {
        return atomicReferenceArray.get(i);
    }

    private void soConsumerIndex(long j) {
        this.consumerIndex.lazySet(j);
    }

    public static void soElement(AtomicReferenceArray atomicReferenceArray, int i, Object obj) {
        atomicReferenceArray.lazySet(i, obj);
    }

    private void soProducerIndex(long j) {
        this.producerIndex.lazySet(j);
    }

    public final void adjustLookAheadStep(int i) {
        this.producerLookAheadStep = Math.min(i / 4, MAX_LOOK_AHEAD_STEP);
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public void clear() {
        while (true) {
            if (poll() == null && isEmpty()) {
                return;
            }
        }
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public boolean isEmpty() {
        return lvProducerIndex() == lvConsumerIndex();
    }

    public final long lpConsumerIndex() {
        return this.consumerIndex.get();
    }

    public final long lpProducerIndex() {
        return this.producerIndex.get();
    }

    public final long lvConsumerIndex() {
        return this.consumerIndex.get();
    }

    public final AtomicReferenceArray lvNextBufferAndUnlink(AtomicReferenceArray atomicReferenceArray, int i) {
        int iCalcDirectOffset = calcDirectOffset(i);
        AtomicReferenceArray atomicReferenceArray2 = (AtomicReferenceArray) lvElement(atomicReferenceArray, iCalcDirectOffset);
        soElement(atomicReferenceArray, iCalcDirectOffset, null);
        return atomicReferenceArray2;
    }

    public final long lvProducerIndex() {
        return this.producerIndex.get();
    }

    public final Object newBufferPeek(AtomicReferenceArray atomicReferenceArray, long j, int i) {
        this.consumerBuffer = atomicReferenceArray;
        return lvElement(atomicReferenceArray, calcWrappedOffset(j, i));
    }

    public final Object newBufferPoll(AtomicReferenceArray atomicReferenceArray, long j, int i) {
        this.consumerBuffer = atomicReferenceArray;
        int iCalcWrappedOffset = calcWrappedOffset(j, i);
        Object objLvElement = lvElement(atomicReferenceArray, iCalcWrappedOffset);
        if (objLvElement != null) {
            soElement(atomicReferenceArray, iCalcWrappedOffset, null);
            soConsumerIndex(j + 1);
        }
        return objLvElement;
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public boolean offer(Object obj) {
        if (obj == null) {
            throw new NullPointerException("Null is not a valid element");
        }
        AtomicReferenceArray atomicReferenceArray = this.producerBuffer;
        long jLpProducerIndex = lpProducerIndex();
        int i = this.producerMask;
        int iCalcWrappedOffset = calcWrappedOffset(jLpProducerIndex, i);
        if (jLpProducerIndex < this.producerLookAhead) {
            return writeToQueue(atomicReferenceArray, obj, jLpProducerIndex, iCalcWrappedOffset);
        }
        long j = ((long) this.producerLookAheadStep) + jLpProducerIndex;
        if (lvElement(atomicReferenceArray, calcWrappedOffset(j, i)) == null) {
            this.producerLookAhead = j - 1;
            return writeToQueue(atomicReferenceArray, obj, jLpProducerIndex, iCalcWrappedOffset);
        }
        if (lvElement(atomicReferenceArray, calcWrappedOffset(jLpProducerIndex + 1, i)) == null) {
            return writeToQueue(atomicReferenceArray, obj, jLpProducerIndex, iCalcWrappedOffset);
        }
        resize(atomicReferenceArray, jLpProducerIndex, iCalcWrappedOffset, obj, i);
        return true;
    }

    public boolean offer(Object obj, Object obj2) {
        AtomicReferenceArray atomicReferenceArray = this.producerBuffer;
        long jLvProducerIndex = lvProducerIndex();
        int i = this.producerMask;
        long j = 2 + jLvProducerIndex;
        if (lvElement(atomicReferenceArray, calcWrappedOffset(j, i)) == null) {
            int iCalcWrappedOffset = calcWrappedOffset(jLvProducerIndex, i);
            soElement(atomicReferenceArray, iCalcWrappedOffset + 1, obj2);
            soElement(atomicReferenceArray, iCalcWrappedOffset, obj);
            soProducerIndex(j);
            return true;
        }
        AtomicReferenceArray atomicReferenceArray2 = new AtomicReferenceArray(atomicReferenceArray.length());
        this.producerBuffer = atomicReferenceArray2;
        int iCalcWrappedOffset2 = calcWrappedOffset(jLvProducerIndex, i);
        soElement(atomicReferenceArray2, iCalcWrappedOffset2 + 1, obj2);
        soElement(atomicReferenceArray2, iCalcWrappedOffset2, obj);
        soNext(atomicReferenceArray, atomicReferenceArray2);
        soElement(atomicReferenceArray, iCalcWrappedOffset2, HAS_NEXT);
        soProducerIndex(j);
        return true;
    }

    public Object peek() {
        AtomicReferenceArray atomicReferenceArray = this.consumerBuffer;
        long jLpConsumerIndex = lpConsumerIndex();
        int i = this.consumerMask;
        Object objLvElement = lvElement(atomicReferenceArray, calcWrappedOffset(jLpConsumerIndex, i));
        return objLvElement == HAS_NEXT ? newBufferPeek(lvNextBufferAndUnlink(atomicReferenceArray, i + 1), jLpConsumerIndex, i) : objLvElement;
    }

    @Override // io.reactivex.rxjava3.operators.SimplePlainQueue, io.reactivex.rxjava3.operators.SimpleQueue
    public Object poll() {
        AtomicReferenceArray atomicReferenceArray = this.consumerBuffer;
        long jLpConsumerIndex = lpConsumerIndex();
        int i = this.consumerMask;
        int iCalcWrappedOffset = calcWrappedOffset(jLpConsumerIndex, i);
        Object objLvElement = lvElement(atomicReferenceArray, iCalcWrappedOffset);
        boolean z = objLvElement == HAS_NEXT;
        if (objLvElement == null || z) {
            if (z) {
                return newBufferPoll(lvNextBufferAndUnlink(atomicReferenceArray, i + 1), jLpConsumerIndex, i);
            }
            return null;
        }
        soElement(atomicReferenceArray, iCalcWrappedOffset, null);
        soConsumerIndex(jLpConsumerIndex + 1);
        return objLvElement;
    }

    public final void resize(AtomicReferenceArray atomicReferenceArray, long j, int i, Object obj, long j2) {
        AtomicReferenceArray atomicReferenceArray2 = new AtomicReferenceArray(atomicReferenceArray.length());
        this.producerBuffer = atomicReferenceArray2;
        this.producerLookAhead = (j2 + j) - 1;
        soElement(atomicReferenceArray2, i, obj);
        soNext(atomicReferenceArray, atomicReferenceArray2);
        soElement(atomicReferenceArray, i, HAS_NEXT);
        soProducerIndex(j + 1);
    }

    public int size() {
        long jLvConsumerIndex = lvConsumerIndex();
        while (true) {
            long jLvProducerIndex = lvProducerIndex();
            long jLvConsumerIndex2 = lvConsumerIndex();
            if (jLvConsumerIndex == jLvConsumerIndex2) {
                return (int) (jLvProducerIndex - jLvConsumerIndex2);
            }
            jLvConsumerIndex = jLvConsumerIndex2;
        }
    }

    public final void soNext(AtomicReferenceArray atomicReferenceArray, AtomicReferenceArray atomicReferenceArray2) {
        soElement(atomicReferenceArray, calcDirectOffset(atomicReferenceArray.length() - 1), atomicReferenceArray2);
    }

    public final boolean writeToQueue(AtomicReferenceArray atomicReferenceArray, Object obj, long j, int i) {
        soElement(atomicReferenceArray, i, obj);
        soProducerIndex(j + 1);
        return true;
    }
}
