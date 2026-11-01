package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlinx.coroutines.selects.SelectClause1;

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
/* JADX INFO: compiled from: Channel.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public interface ReceiveChannel<E> {

    /* JADX INFO: compiled from: Channel.kt */
    @Metadata
    public static final class DefaultImpls {
        public static /* synthetic */ void cancel$default(ReceiveChannel receiveChannel, CancellationException cancellationException, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: cancel");
            }
            if ((i & 1) != 0) {
                cancellationException = null;
            }
            receiveChannel.cancel(cancellationException);
        }

        /* JADX WARN: Code duplicated, block: B:7:0x0013  */
        public static Object receiveOrNull(ReceiveChannel receiveChannel, Continuation continuation) {
            ReceiveChannel$receiveOrNull$1 receiveChannel$receiveOrNull$1;
            Object objMo1234receiveCatchingJP2dKIU;
            if (continuation instanceof ReceiveChannel$receiveOrNull$1) {
                receiveChannel$receiveOrNull$1 = (ReceiveChannel$receiveOrNull$1) continuation;
                int i = receiveChannel$receiveOrNull$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    receiveChannel$receiveOrNull$1.label = i - Integer.MIN_VALUE;
                } else {
                    receiveChannel$receiveOrNull$1 = new ReceiveChannel$receiveOrNull$1(continuation);
                }
            } else {
                receiveChannel$receiveOrNull$1 = new ReceiveChannel$receiveOrNull$1(continuation);
            }
            Object obj = receiveChannel$receiveOrNull$1.result;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = receiveChannel$receiveOrNull$1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                receiveChannel$receiveOrNull$1.label = 1;
                objMo1234receiveCatchingJP2dKIU = receiveChannel.mo1234receiveCatchingJP2dKIU(receiveChannel$receiveOrNull$1);
                if (objMo1234receiveCatchingJP2dKIU == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                objMo1234receiveCatchingJP2dKIU = ((ChannelResult) obj).m1248unboximpl();
            }
            return ChannelResult.m1243getOrNullimpl(objMo1234receiveCatchingJP2dKIU);
        }
    }

    void cancel(CancellationException cancellationException);

    SelectClause1 getOnReceive();

    SelectClause1 getOnReceiveCatching();

    ChannelIterator iterator();

    /* JADX INFO: renamed from: receiveCatching-JP2dKIU */
    Object mo1234receiveCatchingJP2dKIU(Continuation continuation);

    /* JADX INFO: renamed from: tryReceive-PtdJZtk */
    Object mo1236tryReceivePtdJZtk();
}
