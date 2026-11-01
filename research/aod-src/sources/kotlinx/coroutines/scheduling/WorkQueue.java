package kotlinx.coroutines.scheduling;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: WorkQueue.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class WorkQueue {
    private volatile /* synthetic */ int blockingTasksInBuffer$volatile;
    public final AtomicReferenceArray buffer = new AtomicReferenceArray(128);
    private volatile /* synthetic */ int consumerIndex$volatile;
    private volatile /* synthetic */ Object lastScheduledTask$volatile;
    private volatile /* synthetic */ int producerIndex$volatile;
    public static final /* synthetic */ AtomicReferenceFieldUpdater lastScheduledTask$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(WorkQueue.class, Object.class, "lastScheduledTask$volatile");
    public static final /* synthetic */ AtomicIntegerFieldUpdater producerIndex$volatile$FU = AtomicIntegerFieldUpdater.newUpdater(WorkQueue.class, "producerIndex$volatile");
    public static final /* synthetic */ AtomicIntegerFieldUpdater consumerIndex$volatile$FU = AtomicIntegerFieldUpdater.newUpdater(WorkQueue.class, "consumerIndex$volatile");
    public static final /* synthetic */ AtomicIntegerFieldUpdater blockingTasksInBuffer$volatile$FU = AtomicIntegerFieldUpdater.newUpdater(WorkQueue.class, "blockingTasksInBuffer$volatile");

    public final Task add(Task task, boolean z) {
        if (z) {
            return addLast(task);
        }
        Task task2 = (Task) lastScheduledTask$volatile$FU.getAndSet(this, task);
        if (task2 == null) {
            return null;
        }
        return addLast(task2);
    }

    public final Task addLast(Task task) {
        if (getBufferSize() == 127) {
            return task;
        }
        if (task.taskContext) {
            blockingTasksInBuffer$volatile$FU.incrementAndGet(this);
        }
        int i = producerIndex$volatile$FU.get(this) & 127;
        while (this.buffer.get(i) != null) {
            Thread.yield();
        }
        this.buffer.lazySet(i, task);
        producerIndex$volatile$FU.incrementAndGet(this);
        return null;
    }

    public final void decrementIfBlocking(Task task) {
        if (task == null || !task.taskContext) {
            return;
        }
        blockingTasksInBuffer$volatile$FU.decrementAndGet(this);
    }

    public final int getBufferSize() {
        return producerIndex$volatile$FU.get(this) - consumerIndex$volatile$FU.get(this);
    }

    public final int getSize$kotlinx_coroutines_core() {
        Object obj = lastScheduledTask$volatile$FU.get(this);
        int bufferSize = getBufferSize();
        return obj != null ? bufferSize + 1 : bufferSize;
    }

    public final void offloadAllWorkTo(GlobalQueue globalQueue) {
        Task task = (Task) lastScheduledTask$volatile$FU.getAndSet(this, null);
        if (task != null) {
            globalQueue.addLast(task);
        }
        while (pollTo(globalQueue)) {
        }
    }

    public final Task poll() {
        Task task = (Task) lastScheduledTask$volatile$FU.getAndSet(this, null);
        return task == null ? pollBuffer() : task;
    }

    public final Task pollBlocking() {
        return pollWithExclusiveMode(true);
    }

    public final Task pollBuffer() {
        Task task;
        while (true) {
            int i = consumerIndex$volatile$FU.get(this);
            if (i - producerIndex$volatile$FU.get(this) == 0) {
                return null;
            }
            int i2 = i & 127;
            if (consumerIndex$volatile$FU.compareAndSet(this, i, i + 1) && (task = (Task) this.buffer.getAndSet(i2, null)) != null) {
                decrementIfBlocking(task);
                return task;
            }
        }
    }

    public final boolean pollTo(GlobalQueue globalQueue) {
        Task taskPollBuffer = pollBuffer();
        if (taskPollBuffer == null) {
            return false;
        }
        globalQueue.addLast(taskPollBuffer);
        return true;
    }

    public final Task pollWithExclusiveMode(boolean z) {
        Task task;
        do {
            task = (Task) lastScheduledTask$volatile$FU.get(this);
            if (task == null || task.taskContext != z) {
                int i = consumerIndex$volatile$FU.get(this);
                int i2 = producerIndex$volatile$FU.get(this);
                while (i != i2) {
                    if (z && blockingTasksInBuffer$volatile$FU.get(this) == 0) {
                        return null;
                    }
                    i2--;
                    Task taskTryExtractFromTheMiddle = tryExtractFromTheMiddle(i2, z);
                    if (taskTryExtractFromTheMiddle != null) {
                        return taskTryExtractFromTheMiddle;
                    }
                }
                return null;
            }
        } while (!lastScheduledTask$volatile$FU.compareAndSet(this, task, null));
        return task;
    }

    public final Task stealWithExclusiveMode(int i) {
        int i2 = consumerIndex$volatile$FU.get(this);
        int i3 = producerIndex$volatile$FU.get(this);
        boolean z = i == 1;
        while (i2 != i3) {
            if (z && blockingTasksInBuffer$volatile$FU.get(this) == 0) {
                return null;
            }
            int i4 = i2 + 1;
            Task taskTryExtractFromTheMiddle = tryExtractFromTheMiddle(i2, z);
            if (taskTryExtractFromTheMiddle != null) {
                return taskTryExtractFromTheMiddle;
            }
            i2 = i4;
        }
        return null;
    }

    public final Task tryExtractFromTheMiddle(int i, boolean z) {
        int i2 = i & 127;
        Task task = (Task) this.buffer.get(i2);
        if (task == null || task.taskContext != z || !this.buffer.compareAndSet(i2, task, null)) {
            return null;
        }
        if (z) {
            blockingTasksInBuffer$volatile$FU.decrementAndGet(this);
        }
        return task;
    }

    public final long trySteal(int i, Ref.ObjectRef objectRef) {
        Task taskPollBuffer = i == 3 ? pollBuffer() : stealWithExclusiveMode(i);
        if (taskPollBuffer == null) {
            return tryStealLastScheduled(i, objectRef);
        }
        objectRef.element = taskPollBuffer;
        return -1L;
    }

    public final long tryStealLastScheduled(int i, Ref.ObjectRef objectRef) {
        Task task;
        do {
            task = (Task) lastScheduledTask$volatile$FU.get(this);
            if (task == null) {
                return -2L;
            }
            if (((task.taskContext ? 1 : 2) & i) == 0) {
                return -2L;
            }
            long jNanoTime = TasksKt.schedulerTimeSource.nanoTime() - task.submissionTime;
            long j = TasksKt.WORK_STEALING_TIME_RESOLUTION_NS;
            if (jNanoTime < j) {
                return j - jNanoTime;
            }
        } while (!lastScheduledTask$volatile$FU.compareAndSet(this, task, null));
        objectRef.element = task;
        return -1L;
    }
}
