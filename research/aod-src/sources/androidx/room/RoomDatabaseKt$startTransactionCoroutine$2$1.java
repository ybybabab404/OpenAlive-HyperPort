package androidx.room;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CoroutineScope;

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
/* JADX INFO: compiled from: RoomDatabaseExt.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
final class RoomDatabaseKt$startTransactionCoroutine$2$1 implements Runnable {
    public final /* synthetic */ CoroutineContext $context;
    public final /* synthetic */ CancellableContinuation $continuation;
    public final /* synthetic */ RoomDatabase $this_startTransactionCoroutine;
    public final /* synthetic */ Function2 $transactionBlock;

    /* JADX INFO: renamed from: androidx.room.RoomDatabaseKt$startTransactionCoroutine$2$1$1, reason: invalid class name */
    /* JADX INFO: compiled from: RoomDatabaseExt.kt */
    @Metadata
    @DebugMetadata(c = "androidx.room.RoomDatabaseKt$startTransactionCoroutine$2$1$1", f = "RoomDatabaseExt.kt", l = {103}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ CancellableContinuation $continuation;
        public final /* synthetic */ RoomDatabase $this_startTransactionCoroutine;
        public final /* synthetic */ Function2 $transactionBlock;
        public /* synthetic */ Object L$0;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(RoomDatabase roomDatabase, CancellableContinuation cancellableContinuation, Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$this_startTransactionCoroutine = roomDatabase;
            this.$continuation = cancellableContinuation;
            this.$transactionBlock = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_startTransactionCoroutine, this.$continuation, this.$transactionBlock, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Continuation continuation;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineContext.Element element = ((CoroutineScope) this.L$0).getCoroutineContext().get(ContinuationInterceptor.Key);
                Intrinsics.checkNotNull(element);
                CoroutineContext coroutineContextCreateTransactionContext = RoomDatabaseKt.createTransactionContext(this.$this_startTransactionCoroutine, (ContinuationInterceptor) element);
                CancellableContinuation cancellableContinuation = this.$continuation;
                Result.Companion companion = Result.Companion;
                Function2 function2 = this.$transactionBlock;
                this.L$0 = cancellableContinuation;
                this.label = 1;
                obj = BuildersKt.withContext(coroutineContextCreateTransactionContext, function2, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
                continuation = cancellableContinuation;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                continuation = (Continuation) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            continuation.resumeWith(Result.m1061constructorimpl(obj));
            return Unit.INSTANCE;
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            BuildersKt.runBlocking(this.$context.minusKey(ContinuationInterceptor.Key), new AnonymousClass1(this.$this_startTransactionCoroutine, this.$continuation, this.$transactionBlock, null));
        } catch (Throwable th) {
            this.$continuation.cancel(th);
        }
    }
}
