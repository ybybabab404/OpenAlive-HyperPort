package kotlinx.coroutines.flow.internal;

import com.miui.maml.R;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.CompletableJob;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.JobKt__JobKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.internal.ThreadContextKt;

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
/* JADX INFO: compiled from: Combine.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1", f = "Combine.kt", l = {R.styleable.AppCompatTheme_windowFixedWidthMinor}, m = "invokeSuspend")
final class CombineKt$zipImpl$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Flow $flow;
    public final /* synthetic */ Flow $flow2;
    public final /* synthetic */ FlowCollector $this_unsafeFlow;
    public final /* synthetic */ Function3 $transform;
    public /* synthetic */ Object L$0;
    public Object L$1;
    public int label;

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$2, reason: invalid class name */
    /* JADX INFO: compiled from: Combine.kt */
    @Metadata
    @DebugMetadata(c = "kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$2", f = "Combine.kt", l = {R.styleable.AppCompatTheme_windowMinWidthMajor}, m = "invokeSuspend")
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<Unit, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Object $cnt;
        public final /* synthetic */ CompletableJob $collectJob;
        public final /* synthetic */ Flow $flow;
        public final /* synthetic */ CoroutineContext $scopeContext;
        public final /* synthetic */ ReceiveChannel $second;
        public final /* synthetic */ FlowCollector $this_unsafeFlow;
        public final /* synthetic */ Function3 $transform;
        public int label;

        /* JADX INFO: renamed from: kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: Combine.kt */
        @Metadata
        public static final class AnonymousClass1<T> implements FlowCollector {
            public final /* synthetic */ Object $cnt;
            public final /* synthetic */ CompletableJob $collectJob;
            public final /* synthetic */ CoroutineContext $scopeContext;
            public final /* synthetic */ ReceiveChannel $second;
            public final /* synthetic */ FlowCollector $this_unsafeFlow;
            public final /* synthetic */ Function3 $transform;

            /* JADX INFO: renamed from: kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$2$1$1, reason: invalid class name and collision with other inner class name */
            /* JADX INFO: compiled from: Combine.kt */
            @Metadata
            @DebugMetadata(c = "kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$2$1$1", f = "Combine.kt", l = {R.styleable.AppCompatTheme_windowNoTitle, 129, 129}, m = "invokeSuspend")
            @SourceDebugExtension
            public static final class C00571 extends SuspendLambda implements Function2<Unit, Continuation<? super Unit>, Object> {
                public final /* synthetic */ CompletableJob $collectJob;
                public final /* synthetic */ ReceiveChannel $second;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ Function3 $transform;
                public final /* synthetic */ Object $value;
                public Object L$0;
                public int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C00571(ReceiveChannel receiveChannel, FlowCollector flowCollector, Function3 function3, Object obj, CompletableJob completableJob, Continuation continuation) {
                    super(2, continuation);
                    this.$second = receiveChannel;
                    this.$this_unsafeFlow = flowCollector;
                    this.$transform = function3;
                    this.$value = obj;
                    this.$collectJob = completableJob;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C00571(this.$second, this.$this_unsafeFlow, this.$transform, this.$value, this.$collectJob, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Unit unit, Continuation continuation) {
                    return ((C00571) create(unit, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Code restructure failed: missing block: B:28:0x006e, code lost:
                
                    if (r1.emit(r9, r8) == r0) goto L29;
                 */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final java.lang.Object invokeSuspend(java.lang.Object r9) throws java.lang.Throwable {
                    /*
                        r8 = this;
                        java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r1 = r8.label
                        r2 = 0
                        r3 = 3
                        r4 = 2
                        r5 = 1
                        if (r1 == 0) goto L30
                        if (r1 == r5) goto L26
                        if (r1 == r4) goto L1e
                        if (r1 != r3) goto L16
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto L71
                    L16:
                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                        java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                        r8.<init>(r9)
                        throw r8
                    L1e:
                        java.lang.Object r1 = r8.L$0
                        kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto L66
                    L26:
                        kotlin.ResultKt.throwOnFailure(r9)
                        kotlinx.coroutines.channels.ChannelResult r9 = (kotlinx.coroutines.channels.ChannelResult) r9
                        java.lang.Object r9 = r9.m1248unboximpl()
                        goto L3e
                    L30:
                        kotlin.ResultKt.throwOnFailure(r9)
                        kotlinx.coroutines.channels.ReceiveChannel r9 = r8.$second
                        r8.label = r5
                        java.lang.Object r9 = r9.mo1234receiveCatchingJP2dKIU(r8)
                        if (r9 != r0) goto L3e
                        goto L70
                    L3e:
                        kotlinx.coroutines.CompletableJob r1 = r8.$collectJob
                        boolean r5 = r9 instanceof kotlinx.coroutines.channels.ChannelResult.Failed
                        if (r5 == 0) goto L50
                        java.lang.Throwable r8 = kotlinx.coroutines.channels.ChannelResult.m1242exceptionOrNullimpl(r9)
                        if (r8 != 0) goto L4f
                        kotlinx.coroutines.flow.internal.AbortFlowException r8 = new kotlinx.coroutines.flow.internal.AbortFlowException
                        r8.<init>(r1)
                    L4f:
                        throw r8
                    L50:
                        kotlinx.coroutines.flow.FlowCollector r1 = r8.$this_unsafeFlow
                        kotlin.jvm.functions.Function3 r5 = r8.$transform
                        java.lang.Object r6 = r8.$value
                        kotlinx.coroutines.internal.Symbol r7 = kotlinx.coroutines.flow.internal.NullSurrogateKt.NULL
                        if (r9 != r7) goto L5b
                        r9 = r2
                    L5b:
                        r8.L$0 = r1
                        r8.label = r4
                        java.lang.Object r9 = r5.invoke(r6, r9, r8)
                        if (r9 != r0) goto L66
                        goto L70
                    L66:
                        r8.L$0 = r2
                        r8.label = r3
                        java.lang.Object r8 = r1.emit(r9, r8)
                        if (r8 != r0) goto L71
                    L70:
                        return r0
                    L71:
                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                        return r8
                    */
                    throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1.AnonymousClass2.AnonymousClass1.C00571.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }

            public AnonymousClass1(CoroutineContext coroutineContext, Object obj, ReceiveChannel receiveChannel, FlowCollector flowCollector, Function3 function3, CompletableJob completableJob) {
                this.$scopeContext = coroutineContext;
                this.$cnt = obj;
                this.$second = receiveChannel;
                this.$this_unsafeFlow = flowCollector;
                this.$transform = function3;
                this.$collectJob = completableJob;
            }

            /* JADX WARN: Code duplicated, block: B:7:0x0013  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj, Continuation continuation) {
                CombineKt$zipImpl$1$1$2$1$emit$1 combineKt$zipImpl$1$1$2$1$emit$1;
                if (continuation instanceof CombineKt$zipImpl$1$1$2$1$emit$1) {
                    combineKt$zipImpl$1$1$2$1$emit$1 = (CombineKt$zipImpl$1$1$2$1$emit$1) continuation;
                    int i = combineKt$zipImpl$1$1$2$1$emit$1.label;
                    if ((i & Integer.MIN_VALUE) != 0) {
                        combineKt$zipImpl$1$1$2$1$emit$1.label = i - Integer.MIN_VALUE;
                    } else {
                        combineKt$zipImpl$1$1$2$1$emit$1 = new CombineKt$zipImpl$1$1$2$1$emit$1(this, continuation);
                    }
                } else {
                    combineKt$zipImpl$1$1$2$1$emit$1 = new CombineKt$zipImpl$1$1$2$1$emit$1(this, continuation);
                }
                Object obj2 = combineKt$zipImpl$1$1$2$1$emit$1.result;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i2 = combineKt$zipImpl$1$1$2$1$emit$1.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj2);
                    CoroutineContext coroutineContext = this.$scopeContext;
                    Unit unit = Unit.INSTANCE;
                    Object obj3 = this.$cnt;
                    C00571 c00571 = new C00571(this.$second, this.$this_unsafeFlow, this.$transform, obj, this.$collectJob, null);
                    combineKt$zipImpl$1$1$2$1$emit$1.label = 1;
                    if (ChannelFlowKt.withContextUndispatched(coroutineContext, unit, obj3, c00571, combineKt$zipImpl$1$1$2$1$emit$1) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj2);
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Flow flow, CoroutineContext coroutineContext, Object obj, ReceiveChannel receiveChannel, FlowCollector flowCollector, Function3 function3, CompletableJob completableJob, Continuation continuation) {
            super(2, continuation);
            this.$flow = flow;
            this.$scopeContext = coroutineContext;
            this.$cnt = obj;
            this.$second = receiveChannel;
            this.$this_unsafeFlow = flowCollector;
            this.$transform = function3;
            this.$collectJob = completableJob;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$flow, this.$scopeContext, this.$cnt, this.$second, this.$this_unsafeFlow, this.$transform, this.$collectJob, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Unit unit, Continuation continuation) {
            return ((AnonymousClass2) create(unit, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flow = this.$flow;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$scopeContext, this.$cnt, this.$second, this.$this_unsafeFlow, this.$transform, this.$collectJob);
                this.label = 1;
                if (flow.collect(anonymousClass1, this) == coroutine_suspended) {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CombineKt$zipImpl$1$1(Flow flow, Flow flow2, FlowCollector flowCollector, Function3 function3, Continuation continuation) {
        super(2, continuation);
        this.$flow2 = flow;
        this.$flow = flow2;
        this.$this_unsafeFlow = flowCollector;
        this.$transform = function3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CombineKt$zipImpl$1$1 combineKt$zipImpl$1$1 = new CombineKt$zipImpl$1$1(this.$flow2, this.$flow, this.$this_unsafeFlow, this.$transform, continuation);
        combineKt$zipImpl$1$1.L$0 = obj;
        return combineKt$zipImpl$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((CombineKt$zipImpl$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        ReceiveChannel receiveChannel;
        CompletableJob completableJob;
        ReceiveChannel receiveChannel2;
        CompletableJob completableJob2;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            ReceiveChannel receiveChannelProduce$default = ProduceKt.produce$default(coroutineScope, null, 0, new CombineKt$zipImpl$1$1$second$1(this.$flow2, null), 3, null);
            final CompletableJob completableJobJob$default = JobKt__JobKt.Job$default(null, 1, null);
            Intrinsics.checkNotNull(receiveChannelProduce$default, "null cannot be cast to non-null type kotlinx.coroutines.channels.SendChannel<*>");
            ((SendChannel) receiveChannelProduce$default).invokeOnClose(new Function1<Throwable, Unit>() { // from class: kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Object invoke(Object obj2) {
                    invoke((Throwable) obj2);
                    return Unit.INSTANCE;
                }

                public final void invoke(Throwable th) {
                    if (completableJobJob$default.isActive()) {
                        completableJobJob$default.cancel(new AbortFlowException(completableJobJob$default));
                    }
                }
            });
            try {
                CoroutineContext coroutineContext = coroutineScope.getCoroutineContext();
                Object objThreadContextElements = ThreadContextKt.threadContextElements(coroutineContext);
                CoroutineContext coroutineContextPlus = coroutineScope.getCoroutineContext().plus(completableJobJob$default);
                try {
                    try {
                        Unit unit = Unit.INSTANCE;
                        try {
                            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$flow, coroutineContext, objThreadContextElements, receiveChannelProduce$default, this.$this_unsafeFlow, this.$transform, completableJobJob$default, null);
                            receiveChannel = receiveChannelProduce$default;
                            completableJob = completableJobJob$default;
                            try {
                                this.L$0 = receiveChannel;
                                this.L$1 = completableJob;
                                this.label = 1;
                                if (ChannelFlowKt.withContextUndispatched$default(coroutineContextPlus, unit, null, anonymousClass2, this, 4, null) == coroutine_suspended) {
                                    return coroutine_suspended;
                                }
                                receiveChannel2 = receiveChannel;
                            } catch (AbortFlowException e) {
                                e = e;
                                completableJob2 = completableJob;
                                receiveChannel2 = receiveChannel;
                                FlowExceptions_commonKt.checkOwnership(e, completableJob2);
                            } catch (Throwable th) {
                                th = th;
                                receiveChannel2 = receiveChannel;
                                ReceiveChannel.DefaultImpls.cancel$default(receiveChannel2, null, 1, null);
                                throw th;
                            }
                        } catch (AbortFlowException e2) {
                            e = e2;
                            receiveChannel = receiveChannelProduce$default;
                            completableJob = completableJobJob$default;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        receiveChannel = receiveChannelProduce$default;
                    }
                } catch (AbortFlowException e3) {
                    e = e3;
                    completableJob = completableJobJob$default;
                    receiveChannel = receiveChannelProduce$default;
                }
            } catch (AbortFlowException e4) {
                e = e4;
                receiveChannel = receiveChannelProduce$default;
                completableJob = completableJobJob$default;
            } catch (Throwable th3) {
                th = th3;
                receiveChannel = receiveChannelProduce$default;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            completableJob2 = (CompletableJob) this.L$1;
            receiveChannel2 = (ReceiveChannel) this.L$0;
            try {
                try {
                    ResultKt.throwOnFailure(obj);
                } catch (AbortFlowException e5) {
                    e = e5;
                    FlowExceptions_commonKt.checkOwnership(e, completableJob2);
                }
            } catch (Throwable th4) {
                th = th4;
                ReceiveChannel.DefaultImpls.cancel$default(receiveChannel2, null, 1, null);
                throw th;
            }
        }
        ReceiveChannel.DefaultImpls.cancel$default(receiveChannel2, null, 1, null);
        return Unit.INSTANCE;
    }
}
