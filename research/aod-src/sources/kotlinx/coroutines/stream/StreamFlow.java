package kotlinx.coroutines.stream;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.stream.Stream;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

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
/* JADX INFO: compiled from: Stream.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
final class StreamFlow<T> implements Flow<T> {
    public static final /* synthetic */ AtomicIntegerFieldUpdater consumed$volatile$FU = AtomicIntegerFieldUpdater.newUpdater(StreamFlow.class, "consumed$volatile");
    private volatile /* synthetic */ int consumed$volatile;
    public final Stream stream;

    /* JADX INFO: renamed from: kotlinx.coroutines.stream.StreamFlow$collect$1, reason: invalid class name */
    /* JADX INFO: compiled from: Stream.kt */
    @Metadata
    @DebugMetadata(c = "kotlinx.coroutines.stream.StreamFlow", f = "Stream.kt", l = {22}, m = "collect")
    public static final class AnonymousClass1 extends ContinuationImpl {
        public Object L$0;
        public Object L$1;
        public Object L$2;
        public int label;
        public /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return StreamFlow.this.collect(null, this);
        }
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0060 A[Catch: all -> 0x0073, TRY_LEAVE, TryCatch #0 {all -> 0x0073, blocks: (B:22:0x005a, B:24:0x0060), top: B:37:0x005a }] */
    /* JADX WARN: Code duplicated, block: B:44:0x0072 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:46:? A[LOOP:0: B:37:0x005a->B:46:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Override // kotlinx.coroutines.flow.Flow
    public Object collect(FlowCollector flowCollector, Continuation continuation) throws Throwable {
        AnonymousClass1 anonymousClass1;
        StreamFlow<T> streamFlow;
        Throwable th;
        Iterator<T> it;
        FlowCollector flowCollector2;
        T next;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        } else {
            anonymousClass1 = new AnonymousClass1(continuation);
        }
        Object obj = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            if (!consumed$volatile$FU.compareAndSet(this, 0, 1)) {
                throw new IllegalStateException("Stream.consumeAsFlow can be collected only once");
            }
            try {
                streamFlow = this;
                it = this.stream.iterator();
                flowCollector2 = flowCollector;
                while (it.hasNext()) {
                    next = it.next();
                    anonymousClass1.L$0 = streamFlow;
                    anonymousClass1.L$1 = flowCollector2;
                    anonymousClass1.L$2 = it;
                    anonymousClass1.label = 1;
                    if (flowCollector2.emit(next, anonymousClass1) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
                streamFlow.stream.close();
                return Unit.INSTANCE;
            } catch (Throwable th2) {
                streamFlow = this;
                th = th2;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            it = (Iterator) anonymousClass1.L$2;
            FlowCollector flowCollector3 = (FlowCollector) anonymousClass1.L$1;
            StreamFlow<T> streamFlow2 = (StreamFlow) anonymousClass1.L$0;
            try {
                ResultKt.throwOnFailure(obj);
                flowCollector2 = flowCollector3;
                streamFlow = streamFlow2;
                while (it.hasNext()) {
                    try {
                        next = it.next();
                        anonymousClass1.L$0 = streamFlow;
                        anonymousClass1.L$1 = flowCollector2;
                        anonymousClass1.L$2 = it;
                        anonymousClass1.label = 1;
                        if (flowCollector2.emit(next, anonymousClass1) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                    }
                }
                streamFlow.stream.close();
                return Unit.INSTANCE;
            } catch (Throwable th4) {
                th = th4;
                streamFlow = streamFlow2;
            }
        }
        streamFlow.stream.close();
        throw th;
    }
}
