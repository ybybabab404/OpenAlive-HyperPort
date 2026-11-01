package com.miui.keyguard.editor.utils.segment;

import android.util.Log;
import com.miui.maml.R;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineExceptionHandler;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.selects.SelectImplementation;

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
/* JADX INFO: compiled from: Utils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class UtilsKt {
    public static final Lazy getDefaultDelayJob$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.utils.segment.UtilsKt$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return UtilsKt.getDefaultDelayJob_delegate$lambda$1();
        }
    });
    public static final Lazy getDefaultWorkJob$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.utils.segment.UtilsKt$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return UtilsKt.getDefaultWorkJob_delegate$lambda$3();
        }
    });

    /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.segment.UtilsKt$runSyncWithTimeOut$1, reason: invalid class name */
    /* JADX INFO: compiled from: Utils.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.utils.segment.UtilsKt$runSyncWithTimeOut$1", f = "Utils.kt", l = {53}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<Object>, Object> {
        public final /* synthetic */ Function1 $b;
        public final /* synthetic */ Object $defaultValue;
        public final /* synthetic */ CoroutineDispatcher $dispatcher;
        public final /* synthetic */ long $timeOut;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(CoroutineDispatcher coroutineDispatcher, long j, Object obj, Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$dispatcher = coroutineDispatcher;
            this.$timeOut = j;
            this.$defaultValue = obj;
            this.$b = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$dispatcher, this.$timeOut, this.$defaultValue, this.$b, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            CoroutineDispatcher coroutineDispatcher = this.$dispatcher;
            long j = this.$timeOut;
            Object obj2 = this.$defaultValue;
            Function1 function1 = this.$b;
            this.label = 1;
            Object objRunWithTimeOut = UtilsKt.runWithTimeOut(coroutineDispatcher, j, obj2, function1, this);
            return objRunWithTimeOut == coroutine_suspended ? coroutine_suspended : objRunWithTimeOut;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.segment.UtilsKt$runWithTimeOut$2, reason: invalid class name */
    /* JADX INFO: compiled from: Utils.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.utils.segment.UtilsKt$runWithTimeOut$2", f = "Utils.kt", l = {R.styleable.AppCompatTheme_editTextStyle, R.styleable.AppCompatTheme_textAppearancePopupMenuHeader}, m = "invokeSuspend")
    @SourceDebugExtension
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<Object>, Object> {
        public final /* synthetic */ Function1 $b;
        public final /* synthetic */ CoroutineContext $context;
        public final /* synthetic */ Object $defaultValue;
        public final /* synthetic */ long $timeOut;
        public /* synthetic */ Object L$0;
        public Object L$1;
        public int label;

        /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.segment.UtilsKt$runWithTimeOut$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: Utils.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.utils.segment.UtilsKt$runWithTimeOut$2$1", f = "Utils.kt", l = {R.styleable.AppCompatTheme_listChoiceIndicatorSingleAnimated}, m = "invokeSuspend")
        public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<Object>, Object> {
            public final /* synthetic */ Function1 $b;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(Function1 function1, Continuation continuation) {
                super(2, continuation);
                this.$b = function1;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$b, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i != 0) {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return obj;
                }
                ResultKt.throwOnFailure(obj);
                Function1 function1 = this.$b;
                this.label = 1;
                Object objInvoke = function1.invoke(this);
                return objInvoke == coroutine_suspended ? coroutine_suspended : objInvoke;
            }
        }

        /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.segment.UtilsKt$runWithTimeOut$2$2, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: Utils.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.utils.segment.UtilsKt$runWithTimeOut$2$2", f = "Utils.kt", l = {R.styleable.AppCompatTheme_listPreferredItemHeightLarge}, m = "invokeSuspend")
        public static final class C00362 extends SuspendLambda implements Function2<CoroutineScope, Continuation<Object>, Object> {
            public final /* synthetic */ Object $defaultValue;
            public final /* synthetic */ long $timeOut;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00362(long j, Object obj, Continuation continuation) {
                super(2, continuation);
                this.$timeOut = j;
                this.$defaultValue = obj;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00362(this.$timeOut, this.$defaultValue, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00362) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    long j = this.$timeOut;
                    this.label = 1;
                    if (DelayKt.delay(j, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                Log.i("Keyguard-Editor-Editor", "runWithTimeOut: timed out");
                return this.$defaultValue;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(long j, Function1 function1, CoroutineContext coroutineContext, Object obj, Continuation continuation) {
            super(2, continuation);
            this.$timeOut = j;
            this.$b = function1;
            this.$context = coroutineContext;
            this.$defaultValue = obj;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$timeOut, this.$b, this.$context, this.$defaultValue, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Deferred deferred;
            Deferred deferred2;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                if (this.$timeOut <= 0) {
                    Function1 function1 = this.$b;
                    this.label = 1;
                    Object objInvoke = function1.invoke(this);
                    if (objInvoke != coroutine_suspended) {
                        return objInvoke;
                    }
                } else {
                    Deferred deferredAsync$default = BuildersKt__Builders_commonKt.async$default(coroutineScope, this.$context, null, new AnonymousClass1(this.$b, null), 2, null);
                    Deferred deferredAsync$default2 = BuildersKt__Builders_commonKt.async$default(coroutineScope, UtilsKt.getGetDefaultDelayJob(), null, new C00362(this.$timeOut, this.$defaultValue, null), 2, null);
                    SelectImplementation selectImplementation = new SelectImplementation(getContext());
                    selectImplementation.invoke(deferredAsync$default.getOnAwait(), new UtilsKt$runWithTimeOut$2$result$1$1(null));
                    selectImplementation.invoke(deferredAsync$default2.getOnAwait(), new UtilsKt$runWithTimeOut$2$result$1$2(null));
                    this.L$0 = deferredAsync$default;
                    this.L$1 = deferredAsync$default2;
                    this.label = 2;
                    Object objDoSelect = selectImplementation.doSelect(this);
                    if (objDoSelect != coroutine_suspended) {
                        obj = objDoSelect;
                        deferred = deferredAsync$default;
                        deferred2 = deferredAsync$default2;
                    }
                }
                return coroutine_suspended;
            }
            if (i == 1) {
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            deferred2 = (Deferred) this.L$1;
            deferred = (Deferred) this.L$0;
            ResultKt.throwOnFailure(obj);
            Job.DefaultImpls.cancel$default(deferred2, null, 1, null);
            if (!deferred.isCompleted() && deferred.isActive()) {
                Log.w("Keyguard-Editor-Editor", "runWithTimeOut: cancelling workTask after timeout");
                Job.DefaultImpls.cancel$default(deferred, null, 1, null);
            }
            return obj;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.segment.UtilsKt$waitingFinish$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Utils.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.utils.segment.UtilsKt$waitingFinish$1", f = "Utils.kt", l = {42}, m = "invokeSuspend")
    public static final class C01471 extends SuspendLambda implements Function2<CoroutineScope, Continuation<Object>, Object> {
        public final /* synthetic */ Deferred $this_waitingFinish;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01471(Deferred deferred, Continuation continuation) {
            super(2, continuation);
            this.$this_waitingFinish = deferred;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01471(this.$this_waitingFinish, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01471) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            Deferred deferred = this.$this_waitingFinish;
            this.label = 1;
            Object objAwait = deferred.await(this);
            return objAwait == coroutine_suspended ? coroutine_suspended : objAwait;
        }
    }

    public static final CoroutineScope getDefaultCoroutineScope() {
        return CoroutineScopeKt.CoroutineScope(Dispatchers.getMain().plus(SupervisorKt.SupervisorJob$default(null, 1, null)));
    }

    public static final CoroutineContext getDefaultDelayJob_delegate$lambda$1() {
        return SupervisorKt.SupervisorJob$default(null, 1, null).plus(Dispatchers.getDefault()).plus(new UtilsKt$getDefaultDelayJob_delegate$lambda$1$$inlined$CoroutineExceptionHandler$1(CoroutineExceptionHandler.Key));
    }

    public static final CoroutineContext getDefaultWorkJob_delegate$lambda$3() {
        return SupervisorKt.SupervisorJob$default(null, 1, null).plus(new UtilsKt$getDefaultWorkJob_delegate$lambda$3$$inlined$CoroutineExceptionHandler$1(CoroutineExceptionHandler.Key));
    }

    public static final CoroutineContext getGetDefaultDelayJob() {
        return (CoroutineContext) getDefaultDelayJob$delegate.getValue();
    }

    public static final CoroutineContext getGetDefaultWorkJob() {
        return (CoroutineContext) getDefaultWorkJob$delegate.getValue();
    }

    public static final Object runSyncWithTimeOut(CoroutineDispatcher dispatcher, long j, Object obj, Function1 b) {
        Intrinsics.checkNotNullParameter(dispatcher, "dispatcher");
        Intrinsics.checkNotNullParameter(b, "b");
        return BuildersKt__BuildersKt.runBlocking$default(null, new AnonymousClass1(dispatcher, j, obj, b, null), 1, null);
    }

    public static final Object runWithTimeOut(CoroutineDispatcher coroutineDispatcher, long j, Object obj, Function1 function1, Continuation continuation) {
        CoroutineContext coroutineContextPlus = getGetDefaultWorkJob().plus(coroutineDispatcher);
        return BuildersKt.withContext(coroutineContextPlus, new AnonymousClass2(j, function1, coroutineContextPlus, obj, null), continuation);
    }

    public static final void waitingFinish(Deferred deferred) {
        Intrinsics.checkNotNullParameter(deferred, "<this>");
        BuildersKt__BuildersKt.runBlocking$default(null, new C01471(deferred, null), 1, null);
    }
}
