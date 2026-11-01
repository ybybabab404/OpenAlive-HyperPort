package kotlinx.coroutines.flow;

import com.xiaomi.onetrack.g.d;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;
import kotlinx.coroutines.flow.internal.FusibleFlow;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;
import kotlinx.coroutines.internal.Symbol;

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
/* JADX INFO: compiled from: StateFlow.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
final class StateFlowImpl<T> extends AbstractSharedFlow<StateFlowSlot> implements MutableStateFlow<T>, CancellableFlow<T>, FusibleFlow<T> {
    public static final /* synthetic */ AtomicReferenceFieldUpdater _state$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(StateFlowImpl.class, Object.class, "_state$volatile");
    private volatile /* synthetic */ Object _state$volatile;
    public int sequence;

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.StateFlowImpl$collect$1, reason: invalid class name */
    /* JADX INFO: compiled from: StateFlow.kt */
    @Metadata
    @DebugMetadata(c = "kotlinx.coroutines.flow.StateFlowImpl", f = "StateFlow.kt", l = {389, d.b, 406}, m = "collect")
    public static final class AnonymousClass1 extends ContinuationImpl {
        public Object L$0;
        public Object L$1;
        public Object L$2;
        public Object L$3;
        public Object L$4;
        public int label;
        public /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return StateFlowImpl.this.collect(null, this);
        }
    }

    public StateFlowImpl(Object obj) {
        this._state$volatile = obj;
    }

    /* JADX WARN: Code duplicated, block: B:35:0x00ac A[Catch: all -> 0x0078, PHI: r2 r6 r10 r11 r12
  0x00ac: PHI (r2v7 ??) = (r2v18 ??), (r2v19 ??), (r2v20 ??), (r2v21 ??) binds: [B:51:0x00e4, B:53:0x00f6, B:34:0x009d, B:15:0x0041] A[DONT_GENERATE, DONT_INLINE]
  0x00ac: PHI (r6v3 kotlinx.coroutines.flow.FlowCollector) = 
  (r6v2 kotlinx.coroutines.flow.FlowCollector)
  (r6v2 kotlinx.coroutines.flow.FlowCollector)
  (r6v5 kotlinx.coroutines.flow.FlowCollector)
  (r6v9 kotlinx.coroutines.flow.FlowCollector)
 binds: [B:51:0x00e4, B:53:0x00f6, B:34:0x009d, B:15:0x0041] A[DONT_GENERATE, DONT_INLINE]
  0x00ac: PHI (r10v7 'this' kotlinx.coroutines.flow.StateFlowImpl<T>) = 
  (r10v6 'this' kotlinx.coroutines.flow.StateFlowImpl<T>)
  (r10v6 'this' kotlinx.coroutines.flow.StateFlowImpl<T>)
  (r10v9 'this' kotlinx.coroutines.flow.StateFlowImpl<T>)
  (r10v14 'this' kotlinx.coroutines.flow.StateFlowImpl<T>)
 binds: [B:51:0x00e4, B:53:0x00f6, B:34:0x009d, B:15:0x0041] A[DONT_GENERATE, DONT_INLINE]
  0x00ac: PHI (r11v4 kotlinx.coroutines.Job) = 
  (r11v3 kotlinx.coroutines.Job)
  (r11v3 kotlinx.coroutines.Job)
  (r11v6 kotlinx.coroutines.Job)
  (r11v11 kotlinx.coroutines.Job)
 binds: [B:51:0x00e4, B:53:0x00f6, B:34:0x009d, B:15:0x0041] A[DONT_GENERATE, DONT_INLINE]
  0x00ac: PHI (r12v9 java.lang.Object) = (r12v8 java.lang.Object), (r12v8 java.lang.Object), (r12v18 java.lang.Object), (r12v19 java.lang.Object) binds: [B:51:0x00e4, B:53:0x00f6, B:34:0x009d, B:15:0x0041] A[DONT_GENERATE, DONT_INLINE], TryCatch #1 {all -> 0x0078, blocks: (B:35:0x00ac, B:37:0x00b6, B:39:0x00bb, B:50:0x00e0, B:52:0x00e6, B:41:0x00c1, B:45:0x00c8, B:24:0x0074, B:34:0x009d, B:29:0x0087, B:31:0x008b), top: B:58:0x0024 }] */
    /* JADX WARN: Code duplicated, block: B:37:0x00b6 A[Catch: all -> 0x0078, TryCatch #1 {all -> 0x0078, blocks: (B:35:0x00ac, B:37:0x00b6, B:39:0x00bb, B:50:0x00e0, B:52:0x00e6, B:41:0x00c1, B:45:0x00c8, B:24:0x0074, B:34:0x009d, B:29:0x0087, B:31:0x008b), top: B:58:0x0024 }] */
    /* JADX WARN: Code duplicated, block: B:39:0x00bb A[Catch: all -> 0x0078, TryCatch #1 {all -> 0x0078, blocks: (B:35:0x00ac, B:37:0x00b6, B:39:0x00bb, B:50:0x00e0, B:52:0x00e6, B:41:0x00c1, B:45:0x00c8, B:24:0x0074, B:34:0x009d, B:29:0x0087, B:31:0x008b), top: B:58:0x0024 }] */
    /* JADX WARN: Code duplicated, block: B:43:0x00c5  */
    /* JADX WARN: Code duplicated, block: B:44:0x00c7  */
    /* JADX WARN: Code duplicated, block: B:47:0x00da  */
    /* JADX WARN: Code duplicated, block: B:48:0x00db  */
    /* JADX WARN: Code duplicated, block: B:52:0x00e6 A[Catch: all -> 0x0078, TRY_LEAVE, TryCatch #1 {all -> 0x0078, blocks: (B:35:0x00ac, B:37:0x00b6, B:39:0x00bb, B:50:0x00e0, B:52:0x00e6, B:41:0x00c1, B:45:0x00c8, B:24:0x0074, B:34:0x009d, B:29:0x0087, B:31:0x008b), top: B:58:0x0024 }] */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [int] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v2, types: [kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot] */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r2v23 */
    /* JADX WARN: Type inference failed for: r2v24 */
    /* JADX WARN: Type inference failed for: r2v25 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.lang.Object, kotlinx.coroutines.flow.StateFlowSlot] */
    /* JADX WARN: Type inference failed for: r2v7, types: [java.lang.Object] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:51:0x00e4 -> B:35:0x00ac). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:53:0x00f6 -> B:35:0x00ac). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @Override // kotlinx.coroutines.flow.SharedFlow, kotlinx.coroutines.flow.Flow
    public java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector r11, kotlin.coroutines.Continuation r12) {
        /*
            Method dump skipped, instruction units count: 253
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.StateFlowImpl.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // kotlinx.coroutines.flow.internal.AbstractSharedFlow
    public StateFlowSlot createSlot() {
        return new StateFlowSlot();
    }

    @Override // kotlinx.coroutines.flow.internal.AbstractSharedFlow
    public StateFlowSlot[] createSlotArray(int i) {
        return new StateFlowSlot[i];
    }

    @Override // kotlinx.coroutines.flow.FlowCollector
    public Object emit(Object obj, Continuation continuation) {
        setValue(obj);
        return Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.flow.internal.FusibleFlow
    public Flow fuse(CoroutineContext coroutineContext, int i, BufferOverflow bufferOverflow) {
        return StateFlowKt.fuseStateFlow(this, coroutineContext, i, bufferOverflow);
    }

    @Override // kotlinx.coroutines.flow.MutableStateFlow, kotlinx.coroutines.flow.StateFlow
    public Object getValue() {
        Symbol symbol = NullSurrogateKt.NULL;
        Object obj = _state$volatile$FU.get(this);
        if (obj == symbol) {
            return null;
        }
        return obj;
    }

    @Override // kotlinx.coroutines.flow.MutableSharedFlow
    public void resetReplayCache() {
        throw new UnsupportedOperationException("MutableStateFlow.resetReplayCache is not supported");
    }

    @Override // kotlinx.coroutines.flow.MutableStateFlow
    public void setValue(Object obj) {
        if (obj == null) {
            obj = NullSurrogateKt.NULL;
        }
        updateState(null, obj);
    }

    @Override // kotlinx.coroutines.flow.MutableSharedFlow
    public boolean tryEmit(Object obj) {
        setValue(obj);
        return true;
    }

    public final boolean updateState(Object obj, Object obj2) {
        int i;
        AbstractSharedFlowSlot[] slots;
        synchronized (this) {
            Object obj3 = _state$volatile$FU.get(this);
            if (obj != null && !Intrinsics.areEqual(obj3, obj)) {
                return false;
            }
            if (Intrinsics.areEqual(obj3, obj2)) {
                return true;
            }
            _state$volatile$FU.set(this, obj2);
            int i2 = this.sequence;
            if ((i2 & 1) != 0) {
                this.sequence = i2 + 2;
                return true;
            }
            int i3 = i2 + 1;
            this.sequence = i3;
            AbstractSharedFlowSlot[] slots2 = getSlots();
            Unit unit = Unit.INSTANCE;
            while (true) {
                StateFlowSlot[] stateFlowSlotArr = (StateFlowSlot[]) slots2;
                if (stateFlowSlotArr != null) {
                    for (StateFlowSlot stateFlowSlot : stateFlowSlotArr) {
                        if (stateFlowSlot != null) {
                            stateFlowSlot.makePending();
                        }
                    }
                }
                synchronized (this) {
                    i = this.sequence;
                    if (i == i3) {
                        this.sequence = i3 + 1;
                        return true;
                    }
                    slots = getSlots();
                    Unit unit2 = Unit.INSTANCE;
                }
                slots2 = slots;
                i3 = i;
            }
        }
    }
}
