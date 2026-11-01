package kotlinx.coroutines.internal;

import java.lang.Comparable;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.InternalCoroutinesApi;
import kotlinx.coroutines.internal.ThreadSafeHeapNode;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ThreadSafeHeap.kt */
/* JADX INFO: loaded from: classes2.dex */
@InternalCoroutinesApi
@Metadata
@SourceDebugExtension
public class ThreadSafeHeap<T extends ThreadSafeHeapNode & Comparable<? super T>> {
    public static final /* synthetic */ AtomicIntegerFieldUpdater _size$volatile$FU = AtomicIntegerFieldUpdater.newUpdater(ThreadSafeHeap.class, "_size$volatile");
    private volatile /* synthetic */ int _size$volatile;
    public ThreadSafeHeapNode[] a;

    public final void addImpl(ThreadSafeHeapNode threadSafeHeapNode) {
        threadSafeHeapNode.setHeap(this);
        ThreadSafeHeapNode[] threadSafeHeapNodeArrRealloc = realloc();
        int size = getSize();
        setSize(size + 1);
        threadSafeHeapNodeArrRealloc[size] = threadSafeHeapNode;
        threadSafeHeapNode.setIndex(size);
        siftUpFrom(size);
    }

    public final ThreadSafeHeapNode firstImpl() {
        ThreadSafeHeapNode[] threadSafeHeapNodeArr = this.a;
        if (threadSafeHeapNodeArr != null) {
            return threadSafeHeapNodeArr[0];
        }
        return null;
    }

    public final int getSize() {
        return _size$volatile$FU.get(this);
    }

    public final boolean isEmpty() {
        return getSize() == 0;
    }

    public final ThreadSafeHeapNode peek() {
        ThreadSafeHeapNode threadSafeHeapNodeFirstImpl;
        synchronized (this) {
            threadSafeHeapNodeFirstImpl = firstImpl();
        }
        return threadSafeHeapNodeFirstImpl;
    }

    public final ThreadSafeHeapNode[] realloc() {
        ThreadSafeHeapNode[] threadSafeHeapNodeArr = this.a;
        if (threadSafeHeapNodeArr == null) {
            ThreadSafeHeapNode[] threadSafeHeapNodeArr2 = new ThreadSafeHeapNode[4];
            this.a = threadSafeHeapNodeArr2;
            return threadSafeHeapNodeArr2;
        }
        if (getSize() < threadSafeHeapNodeArr.length) {
            return threadSafeHeapNodeArr;
        }
        Object[] objArrCopyOf = Arrays.copyOf(threadSafeHeapNodeArr, getSize() * 2);
        Intrinsics.checkNotNullExpressionValue(objArrCopyOf, "copyOf(...)");
        ThreadSafeHeapNode[] threadSafeHeapNodeArr3 = (ThreadSafeHeapNode[]) objArrCopyOf;
        this.a = threadSafeHeapNodeArr3;
        return threadSafeHeapNodeArr3;
    }

    public final boolean remove(ThreadSafeHeapNode threadSafeHeapNode) {
        boolean z;
        synchronized (this) {
            if (threadSafeHeapNode.getHeap() == null) {
                z = false;
            } else {
                removeAtImpl(threadSafeHeapNode.getIndex());
                z = true;
            }
        }
        return z;
    }

    /* JADX WARN: Code duplicated, block: B:9:0x003a  */
    public final ThreadSafeHeapNode removeAtImpl(int i) {
        ThreadSafeHeapNode[] threadSafeHeapNodeArr = this.a;
        Intrinsics.checkNotNull(threadSafeHeapNodeArr);
        setSize(getSize() - 1);
        if (i < getSize()) {
            swap(i, getSize());
            int i2 = (i - 1) / 2;
            if (i > 0) {
                ThreadSafeHeapNode threadSafeHeapNode = threadSafeHeapNodeArr[i];
                Intrinsics.checkNotNull(threadSafeHeapNode);
                ThreadSafeHeapNode threadSafeHeapNode2 = threadSafeHeapNodeArr[i2];
                Intrinsics.checkNotNull(threadSafeHeapNode2);
                if (((Comparable) threadSafeHeapNode).compareTo(threadSafeHeapNode2) < 0) {
                    swap(i, i2);
                    siftUpFrom(i2);
                } else {
                    siftDownFrom(i);
                }
            } else {
                siftDownFrom(i);
            }
        }
        ThreadSafeHeapNode threadSafeHeapNode3 = threadSafeHeapNodeArr[getSize()];
        Intrinsics.checkNotNull(threadSafeHeapNode3);
        threadSafeHeapNode3.setHeap(null);
        threadSafeHeapNode3.setIndex(-1);
        threadSafeHeapNodeArr[getSize()] = null;
        return threadSafeHeapNode3;
    }

    public final ThreadSafeHeapNode removeFirstOrNull() {
        ThreadSafeHeapNode threadSafeHeapNodeRemoveAtImpl;
        synchronized (this) {
            threadSafeHeapNodeRemoveAtImpl = getSize() > 0 ? removeAtImpl(0) : null;
        }
        return threadSafeHeapNodeRemoveAtImpl;
    }

    public final void setSize(int i) {
        _size$volatile$FU.set(this, i);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x002b  */
    public final void siftDownFrom(int i) {
        while (true) {
            int i2 = i * 2;
            int i3 = i2 + 1;
            if (i3 >= getSize()) {
                return;
            }
            ThreadSafeHeapNode[] threadSafeHeapNodeArr = this.a;
            Intrinsics.checkNotNull(threadSafeHeapNodeArr);
            int i4 = i2 + 2;
            if (i4 < getSize()) {
                ThreadSafeHeapNode threadSafeHeapNode = threadSafeHeapNodeArr[i4];
                Intrinsics.checkNotNull(threadSafeHeapNode);
                ThreadSafeHeapNode threadSafeHeapNode2 = threadSafeHeapNodeArr[i3];
                Intrinsics.checkNotNull(threadSafeHeapNode2);
                if (((Comparable) threadSafeHeapNode).compareTo(threadSafeHeapNode2) >= 0) {
                    i4 = i3;
                }
            } else {
                i4 = i3;
            }
            ThreadSafeHeapNode threadSafeHeapNode3 = threadSafeHeapNodeArr[i];
            Intrinsics.checkNotNull(threadSafeHeapNode3);
            ThreadSafeHeapNode threadSafeHeapNode4 = threadSafeHeapNodeArr[i4];
            Intrinsics.checkNotNull(threadSafeHeapNode4);
            if (((Comparable) threadSafeHeapNode3).compareTo(threadSafeHeapNode4) <= 0) {
                return;
            }
            swap(i, i4);
            i = i4;
        }
    }

    public final void siftUpFrom(int i) {
        while (i > 0) {
            ThreadSafeHeapNode[] threadSafeHeapNodeArr = this.a;
            Intrinsics.checkNotNull(threadSafeHeapNodeArr);
            int i2 = (i - 1) / 2;
            ThreadSafeHeapNode threadSafeHeapNode = threadSafeHeapNodeArr[i2];
            Intrinsics.checkNotNull(threadSafeHeapNode);
            ThreadSafeHeapNode threadSafeHeapNode2 = threadSafeHeapNodeArr[i];
            Intrinsics.checkNotNull(threadSafeHeapNode2);
            if (((Comparable) threadSafeHeapNode).compareTo(threadSafeHeapNode2) <= 0) {
                return;
            }
            swap(i, i2);
            i = i2;
        }
    }

    public final void swap(int i, int i2) {
        ThreadSafeHeapNode[] threadSafeHeapNodeArr = this.a;
        Intrinsics.checkNotNull(threadSafeHeapNodeArr);
        ThreadSafeHeapNode threadSafeHeapNode = threadSafeHeapNodeArr[i2];
        Intrinsics.checkNotNull(threadSafeHeapNode);
        ThreadSafeHeapNode threadSafeHeapNode2 = threadSafeHeapNodeArr[i];
        Intrinsics.checkNotNull(threadSafeHeapNode2);
        threadSafeHeapNodeArr[i] = threadSafeHeapNode;
        threadSafeHeapNodeArr[i2] = threadSafeHeapNode2;
        threadSafeHeapNode.setIndex(i);
        threadSafeHeapNode2.setIndex(i2);
    }
}
