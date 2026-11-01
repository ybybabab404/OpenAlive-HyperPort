package androidx.appfunctions;

import androidx.appfunctions.metadata.AppFunctionMetadata;
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
/* JADX INFO: compiled from: AppFunctionCompatService.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@DebugMetadata(c = "androidx.appfunctions.AppFunctionCompatService$onExecuteFunction$functionExecutionJob$1$result$1", f = "AppFunctionCompatService.kt", l = {96}, m = "invokeSuspend")
public final class AppFunctionCompatService$onExecuteFunction$functionExecutionJob$1$result$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super ExecuteAppFunctionResponse>, Object> {
    public final /* synthetic */ AppFunctionMetadata $appFunctionMetadata;
    public final /* synthetic */ android.app.appfunctions.ExecuteAppFunctionRequest $request;
    public int label;
    public final /* synthetic */ AppFunctionCompatService this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppFunctionCompatService$onExecuteFunction$functionExecutionJob$1$result$1(AppFunctionCompatService appFunctionCompatService, android.app.appfunctions.ExecuteAppFunctionRequest executeAppFunctionRequest, AppFunctionMetadata appFunctionMetadata, Continuation continuation) {
        super(2, continuation);
        this.this$0 = appFunctionCompatService;
        this.$request = executeAppFunctionRequest;
        this.$appFunctionMetadata = appFunctionMetadata;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AppFunctionCompatService$onExecuteFunction$functionExecutionJob$1$result$1(this.this$0, this.$request, this.$appFunctionMetadata, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((AppFunctionCompatService$onExecuteFunction$functionExecutionJob$1$result$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
        AppFunctionCompatService appFunctionCompatService = this.this$0;
        ExecuteAppFunctionRequest executeAppFunctionRequestFromPlatformClass$appfunctions_release = ExecuteAppFunctionRequest.Companion.fromPlatformClass$appfunctions_release(this.$request, this.$appFunctionMetadata);
        this.label = 1;
        Object objExecuteFunction = appFunctionCompatService.executeFunction(executeAppFunctionRequestFromPlatformClass$appfunctions_release, this);
        return objExecuteFunction == coroutine_suspended ? coroutine_suspended : objExecuteFunction;
    }
}
