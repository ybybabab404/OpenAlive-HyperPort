package kotlinx.coroutines.flow;

import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
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
/* JADX INFO: compiled from: Transform.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
final class FlowKt__TransformKt$chunked$2$1<T> implements FlowCollector {
    public final /* synthetic */ Ref.ObjectRef $result;
    public final /* synthetic */ int $size;
    public final /* synthetic */ FlowCollector $this_flow;

    public FlowKt__TransformKt$chunked$2$1(Ref.ObjectRef objectRef, int i, FlowCollector flowCollector) {
        this.$result = objectRef;
        this.$size = i;
        this.$this_flow = flowCollector;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    public final Object emit(Object obj, Continuation continuation) {
        FlowKt__TransformKt$chunked$2$1$emit$1 flowKt__TransformKt$chunked$2$1$emit$1;
        if (continuation instanceof FlowKt__TransformKt$chunked$2$1$emit$1) {
            flowKt__TransformKt$chunked$2$1$emit$1 = (FlowKt__TransformKt$chunked$2$1$emit$1) continuation;
            int i = flowKt__TransformKt$chunked$2$1$emit$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                flowKt__TransformKt$chunked$2$1$emit$1.label = i - Integer.MIN_VALUE;
            } else {
                flowKt__TransformKt$chunked$2$1$emit$1 = new FlowKt__TransformKt$chunked$2$1$emit$1(this, continuation);
            }
        } else {
            flowKt__TransformKt$chunked$2$1$emit$1 = new FlowKt__TransformKt$chunked$2$1$emit$1(this, continuation);
        }
        Object obj2 = flowKt__TransformKt$chunked$2$1$emit$1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = flowKt__TransformKt$chunked$2$1$emit$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj2);
            ArrayList arrayList = (ArrayList) this.$result.element;
            if (arrayList == null) {
                arrayList = new ArrayList(this.$size);
                this.$result.element = arrayList;
            }
            arrayList.add(obj);
            if (arrayList.size() == this.$size) {
                FlowCollector flowCollector = this.$this_flow;
                flowKt__TransformKt$chunked$2$1$emit$1.L$0 = this;
                flowKt__TransformKt$chunked$2$1$emit$1.label = 1;
                if (flowCollector.emit(arrayList, flowKt__TransformKt$chunked$2$1$emit$1) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            return Unit.INSTANCE;
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        this = (FlowKt__TransformKt$chunked$2$1) flowKt__TransformKt$chunked$2$1$emit$1.L$0;
        ResultKt.throwOnFailure(obj2);
        this.$result.element = null;
        return Unit.INSTANCE;
    }
}
