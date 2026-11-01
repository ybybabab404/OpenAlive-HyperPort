package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: LockFreeTaskQueue.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class LockFreeTaskQueueCore<E> {
    private volatile /* synthetic */ Object _next$volatile;
    private volatile /* synthetic */ long _state$volatile;
    public final /* synthetic */ AtomicReferenceArray array;
    public final int capacity;
    public final int mask;
    public final boolean singleConsumer;
    public static final Companion Companion = new Companion(null);
    public static final /* synthetic */ AtomicReferenceFieldUpdater _next$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(LockFreeTaskQueueCore.class, Object.class, "_next$volatile");
    public static final /* synthetic */ AtomicLongFieldUpdater _state$volatile$FU = AtomicLongFieldUpdater.newUpdater(LockFreeTaskQueueCore.class, "_state$volatile");
    public static final Symbol REMOVE_FROZEN = new Symbol("REMOVE_FROZEN");

    /* JADX INFO: compiled from: LockFreeTaskQueue.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final int addFailReason(long j) {
            return (j & 2305843009213693952L) != 0 ? 2 : 1;
        }

        public final long updateHead(long j, int i) {
            return wo(j, 1073741823L) | ((long) i);
        }

        public final long updateTail(long j, int i) {
            return wo(j, 1152921503533105152L) | (((long) i) << 30);
        }

        public final long wo(long j, long j2) {
            return j & (~j2);
        }
    }

    /* JADX INFO: compiled from: LockFreeTaskQueue.kt */
    @Metadata
    public static final class Placeholder {
        public final int index;

        public Placeholder(int i) {
            this.index = i;
        }
    }

    public LockFreeTaskQueueCore(int i, boolean z) {
        this.capacity = i;
        this.singleConsumer = z;
        int i2 = i - 1;
        this.mask = i2;
        this.array = new AtomicReferenceArray(i);
        if (i2 > 1073741823) {
            throw new IllegalStateException("Check failed.");
        }
        if ((i & i2) != 0) {
            throw new IllegalStateException("Check failed.");
        }
    }

    public final int addLast(Object obj) {
        AtomicLongFieldUpdater atomicLongFieldUpdater = _state$volatile$FU;
        while (true) {
            long j = atomicLongFieldUpdater.get(this);
            if ((3458764513820540928L & j) != 0) {
                return Companion.addFailReason(j);
            }
            int i = (int) (1073741823 & j);
            int i2 = (int) ((1152921503533105152L & j) >> 30);
            int i3 = this.mask;
            if (((i2 + 2) & i3) == (i & i3)) {
                return 1;
            }
            if (this.singleConsumer || this.getArray().get(i2 & i3) == null) {
                LockFreeTaskQueueCore<E> lockFreeTaskQueueCore = this;
                if (_state$volatile$FU.compareAndSet(lockFreeTaskQueueCore, j, Companion.updateTail(j, (i2 + 1) & 1073741823))) {
                    lockFreeTaskQueueCore.getArray().set(i2 & i3, obj);
                    LockFreeTaskQueueCore lockFreeTaskQueueCoreFillPlaceholder = lockFreeTaskQueueCore;
                    while ((_state$volatile$FU.get(lockFreeTaskQueueCoreFillPlaceholder) & 1152921504606846976L) != 0 && (lockFreeTaskQueueCoreFillPlaceholder = lockFreeTaskQueueCoreFillPlaceholder.next().fillPlaceholder(i2, obj)) != null) {
                    }
                    return 0;
                }
                this = lockFreeTaskQueueCore;
            } else {
                int i4 = this.capacity;
                if (i4 < 1024 || ((i2 - i) & 1073741823) > (i4 >> 1)) {
                    return 1;
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final LockFreeTaskQueueCore allocateNextCopy(long j) {
        LockFreeTaskQueueCore lockFreeTaskQueueCore = new LockFreeTaskQueueCore(this.capacity * 2, this.singleConsumer);
        int i = (int) (1073741823 & j);
        int i2 = (int) ((1152921503533105152L & j) >> 30);
        while (true) {
            int i3 = this.mask;
            if ((i & i3) == (i3 & i2)) {
                _state$volatile$FU.set(lockFreeTaskQueueCore, Companion.wo(j, 1152921504606846976L));
                return lockFreeTaskQueueCore;
            }
            Object placeholder = getArray().get(this.mask & i);
            if (placeholder == null) {
                placeholder = new Placeholder(i);
            }
            lockFreeTaskQueueCore.getArray().set(lockFreeTaskQueueCore.mask & i, placeholder);
            i++;
        }
    }

    public final LockFreeTaskQueueCore allocateOrGetNextCopy(long j) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _next$volatile$FU;
        while (true) {
            LockFreeTaskQueueCore lockFreeTaskQueueCore = (LockFreeTaskQueueCore) atomicReferenceFieldUpdater.get(this);
            if (lockFreeTaskQueueCore != null) {
                return lockFreeTaskQueueCore;
            }
            _next$volatile$FU.compareAndSet(this, null, allocateNextCopy(j));
        }
    }

    public final boolean close() {
        AtomicLongFieldUpdater atomicLongFieldUpdater = _state$volatile$FU;
        while (true) {
            long j = atomicLongFieldUpdater.get(this);
            if ((j & 2305843009213693952L) != 0) {
                return true;
            }
            if ((1152921504606846976L & j) != 0) {
                return false;
            }
            LockFreeTaskQueueCore<E> lockFreeTaskQueueCore = this;
            if (atomicLongFieldUpdater.compareAndSet(lockFreeTaskQueueCore, j, 2305843009213693952L | j)) {
                return true;
            }
            this = lockFreeTaskQueueCore;
        }
    }

    public final LockFreeTaskQueueCore fillPlaceholder(int i, Object obj) {
        Object obj2 = getArray().get(this.mask & i);
        if (!(obj2 instanceof Placeholder) || ((Placeholder) obj2).index != i) {
            return null;
        }
        getArray().set(i & this.mask, obj);
        return this;
    }

    public final /* synthetic */ AtomicReferenceArray getArray() {
        return this.array;
    }

    public final int getSize() {
        long j = _state$volatile$FU.get(this);
        return 1073741823 & (((int) ((j & 1152921503533105152L) >> 30)) - ((int) (1073741823 & j)));
    }

    public final boolean isEmpty() {
        long j = _state$volatile$FU.get(this);
        return ((int) (1073741823 & j)) == ((int) ((j & 1152921503533105152L) >> 30));
    }

    public final long markFrozen() {
        AtomicLongFieldUpdater atomicLongFieldUpdater = _state$volatile$FU;
        while (true) {
            long j = atomicLongFieldUpdater.get(this);
            if ((j & 1152921504606846976L) != 0) {
                return j;
            }
            long j2 = 1152921504606846976L | j;
            LockFreeTaskQueueCore<E> lockFreeTaskQueueCore = this;
            if (atomicLongFieldUpdater.compareAndSet(lockFreeTaskQueueCore, j, j2)) {
                return j2;
            }
            this = lockFreeTaskQueueCore;
        }
    }

    public final LockFreeTaskQueueCore next() {
        return allocateOrGetNextCopy(markFrozen());
    }

    public final Object removeFirstOrNull() {
        AtomicLongFieldUpdater atomicLongFieldUpdater = _state$volatile$FU;
        while (true) {
            long j = atomicLongFieldUpdater.get(this);
            if ((1152921504606846976L & j) != 0) {
                return REMOVE_FROZEN;
            }
            int i = (int) (1073741823 & j);
            int i2 = this.mask;
            if ((((int) ((1152921503533105152L & j) >> 30)) & i2) == (i2 & i)) {
                return null;
            }
            Object obj = this.getArray().get(this.mask & i);
            if (obj == null) {
                if (this.singleConsumer) {
                    return null;
                }
            } else {
                if (obj instanceof Placeholder) {
                    return null;
                }
                int i3 = (i + 1) & 1073741823;
                LockFreeTaskQueueCore<E> lockFreeTaskQueueCore = this;
                if (_state$volatile$FU.compareAndSet(lockFreeTaskQueueCore, j, Companion.updateHead(j, i3))) {
                    lockFreeTaskQueueCore.getArray().set(lockFreeTaskQueueCore.mask & i, null);
                    return obj;
                }
                if (lockFreeTaskQueueCore.singleConsumer) {
                    LockFreeTaskQueueCore<E> lockFreeTaskQueueCoreRemoveSlowPath = lockFreeTaskQueueCore;
                    do {
                        lockFreeTaskQueueCoreRemoveSlowPath = lockFreeTaskQueueCoreRemoveSlowPath.removeSlowPath(i, i3);
                    } while (lockFreeTaskQueueCoreRemoveSlowPath != null);
                    return obj;
                }
                this = lockFreeTaskQueueCore;
            }
        }
    }

    public final LockFreeTaskQueueCore removeSlowPath(int i, int i2) {
        AtomicLongFieldUpdater atomicLongFieldUpdater = _state$volatile$FU;
        while (true) {
            long j = atomicLongFieldUpdater.get(this);
            int i3 = (int) (1073741823 & j);
            if ((1152921504606846976L & j) != 0) {
                return this.next();
            }
            LockFreeTaskQueueCore<E> lockFreeTaskQueueCore = this;
            if (_state$volatile$FU.compareAndSet(lockFreeTaskQueueCore, j, Companion.updateHead(j, i2))) {
                lockFreeTaskQueueCore.getArray().set(lockFreeTaskQueueCore.mask & i3, null);
                return null;
            }
            this = lockFreeTaskQueueCore;
        }
    }
}
