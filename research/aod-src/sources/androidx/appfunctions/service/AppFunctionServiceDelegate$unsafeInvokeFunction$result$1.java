package androidx.appfunctions.service;

import androidx.appfunctions.AppFunctionContext;
import androidx.appfunctions.AppFunctionFunctionNotFoundException;
import androidx.appfunctions.ExecuteAppFunctionRequest;
import androidx.appfunctions.service.internal.AggregatedAppFunctionInvoker;
import java.util.Map;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: AppFunctionServiceDelegate.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@DebugMetadata(c = "androidx.appfunctions.service.AppFunctionServiceDelegate$unsafeInvokeFunction$result$1", f = "AppFunctionServiceDelegate.kt", l = {142}, m = "invokeSuspend")
public final class AppFunctionServiceDelegate$unsafeInvokeFunction$result$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Object>, Object> {
    public final /* synthetic */ Map $parameters;
    public final /* synthetic */ ExecuteAppFunctionRequest $request;
    public int label;
    public final /* synthetic */ AppFunctionServiceDelegate this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppFunctionServiceDelegate$unsafeInvokeFunction$result$1(AppFunctionServiceDelegate appFunctionServiceDelegate, ExecuteAppFunctionRequest executeAppFunctionRequest, Map map, Continuation continuation) {
        super(2, continuation);
        this.this$0 = appFunctionServiceDelegate;
        this.$request = executeAppFunctionRequest;
        this.$parameters = map;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AppFunctionServiceDelegate$unsafeInvokeFunction$result$1(this.this$0, this.$request, this.$parameters, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((AppFunctionServiceDelegate$unsafeInvokeFunction$result$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws AppFunctionFunctionNotFoundException {
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
        AggregatedAppFunctionInvoker aggregatedAppFunctionInvoker = this.this$0.aggregatedInvoker;
        AppFunctionContext appFunctionContextBuildAppFunctionContext = this.this$0.buildAppFunctionContext();
        String functionIdentifier = this.$request.getFunctionIdentifier();
        Map<String, ? extends Object> map = this.$parameters;
        this.label = 1;
        Object objUnsafeInvoke = aggregatedAppFunctionInvoker.unsafeInvoke(appFunctionContextBuildAppFunctionContext, functionIdentifier, map, this);
        return objUnsafeInvoke == coroutine_suspended ? coroutine_suspended : objUnsafeInvoke;
    }
}
