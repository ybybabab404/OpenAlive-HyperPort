package androidx.appfunctions.internal;

import androidx.appfunctions.AppFunctionSearchSpec;
import androidx.appsearch.app.GlobalSearchSession;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

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
/* JADX INFO: compiled from: AppSearchAppFunctionReader.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@DebugMetadata(c = "androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1", f = "AppSearchAppFunctionReader.kt", l = {103}, m = "invokeSuspend")
public final class AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ ProducerScope $$this$callbackFlow;
    public final /* synthetic */ AppSearchAppFunctionReader.AppSearchChannelObserver $appSearchChannelObserver;
    public final /* synthetic */ AppFunctionSearchSpec $searchFunctionSpec;
    public final /* synthetic */ GlobalSearchSession $session;
    public int label;
    public final /* synthetic */ AppSearchAppFunctionReader this$0;

    /* JADX INFO: renamed from: androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1$1, reason: invalid class name */
    /* JADX INFO: compiled from: AppSearchAppFunctionReader.kt */
    @Metadata
    public static final class AnonymousClass1<T> implements FlowCollector {
        public final /* synthetic */ ProducerScope $$this$callbackFlow;
        public final /* synthetic */ AppFunctionSearchSpec $searchFunctionSpec;
        public final /* synthetic */ GlobalSearchSession $session;
        public final /* synthetic */ AppSearchAppFunctionReader this$0;

        public AnonymousClass1(ProducerScope producerScope, AppSearchAppFunctionReader appSearchAppFunctionReader, GlobalSearchSession globalSearchSession, AppFunctionSearchSpec appFunctionSearchSpec) {
            this.$$this$callbackFlow = producerScope;
            this.this$0 = appSearchAppFunctionReader;
            this.$session = globalSearchSession;
            this.$searchFunctionSpec = appFunctionSearchSpec;
        }

        /* JADX WARN: Code duplicated, block: B:7:0x0013  */
        /* JADX WARN: Code restructure failed: missing block: B:21:0x005e, code lost:
        
            if (r6.send(r8, r7) == r0) goto L22;
         */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object emit(kotlin.Unit r7, kotlin.coroutines.Continuation r8) {
            /*
                r6 = this;
                boolean r7 = r8 instanceof androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1$1$emit$1
                if (r7 == 0) goto L13
                r7 = r8
                androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1$1$emit$1 r7 = (androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1$1$emit$1) r7
                int r0 = r7.label
                r1 = -2147483648(0xffffffff80000000, float:-0.0)
                r2 = r0 & r1
                if (r2 == 0) goto L13
                int r0 = r0 - r1
                r7.label = r0
                goto L18
            L13:
                androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1$1$emit$1 r7 = new androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1$1$emit$1
                r7.<init>(r6, r8)
            L18:
                java.lang.Object r8 = r7.result
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r7.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L3c
                if (r1 == r3) goto L34
                if (r1 != r2) goto L2c
                kotlin.ResultKt.throwOnFailure(r8)
                goto L61
            L2c:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                r6.<init>(r7)
                throw r6
            L34:
                java.lang.Object r6 = r7.L$0
                kotlinx.coroutines.channels.ProducerScope r6 = (kotlinx.coroutines.channels.ProducerScope) r6
                kotlin.ResultKt.throwOnFailure(r8)
                goto L55
            L3c:
                kotlin.ResultKt.throwOnFailure(r8)
                kotlinx.coroutines.channels.ProducerScope r8 = r6.$$this$callbackFlow
                androidx.appfunctions.internal.AppSearchAppFunctionReader r1 = r6.this$0
                androidx.appsearch.app.GlobalSearchSession r4 = r6.$session
                androidx.appfunctions.AppFunctionSearchSpec r6 = r6.$searchFunctionSpec
                r7.L$0 = r8
                r7.label = r3
                java.lang.Object r6 = androidx.appfunctions.internal.AppSearchAppFunctionReader.access$performSearch(r1, r4, r6, r7)
                if (r6 != r0) goto L52
                goto L60
            L52:
                r5 = r8
                r8 = r6
                r6 = r5
            L55:
                r1 = 0
                r7.L$0 = r1
                r7.label = r2
                java.lang.Object r6 = r6.send(r8, r7)
                if (r6 != r0) goto L61
            L60:
                return r0
            L61:
                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.appfunctions.internal.AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1.AnonymousClass1.emit(kotlin.Unit, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1(AppSearchAppFunctionReader.AppSearchChannelObserver appSearchChannelObserver, ProducerScope producerScope, AppSearchAppFunctionReader appSearchAppFunctionReader, GlobalSearchSession globalSearchSession, AppFunctionSearchSpec appFunctionSearchSpec, Continuation continuation) {
        super(2, continuation);
        this.$appSearchChannelObserver = appSearchChannelObserver;
        this.$$this$callbackFlow = producerScope;
        this.this$0 = appSearchAppFunctionReader;
        this.$session = globalSearchSession;
        this.$searchFunctionSpec = appFunctionSearchSpec;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1(this.$appSearchChannelObserver, this.$$this$callbackFlow, this.this$0, this.$session, this.$searchFunctionSpec, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((AppSearchAppFunctionReader$searchAppFunctions$2$observerJob$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow flowM1256debounceHG0u8IE = FlowKt.m1256debounceHG0u8IE(this.$appSearchChannelObserver.observe(), AppSearchAppFunctionReader.Companion.m15getOBSERVER_DEBOUNCE_MILLISUwyO8pc());
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$$this$callbackFlow, this.this$0, this.$session, this.$searchFunctionSpec);
            this.label = 1;
            if (flowM1256debounceHG0u8IE.collect(anonymousClass1, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
