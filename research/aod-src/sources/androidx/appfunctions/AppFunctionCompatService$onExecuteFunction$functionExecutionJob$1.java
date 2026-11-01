package androidx.appfunctions;

import android.os.OutcomeReceiver;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
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
@DebugMetadata(c = "androidx.appfunctions.AppFunctionCompatService$onExecuteFunction$functionExecutionJob$1", f = "AppFunctionCompatService.kt", l = {com.miui.maml.R.styleable.AppCompatTheme_panelMenuListWidth, com.miui.maml.R.styleable.AppCompatTheme_searchViewStyle}, m = "invokeSuspend")
public final class AppFunctionCompatService$onExecuteFunction$functionExecutionJob$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ OutcomeReceiver $callback;
    public final /* synthetic */ String $callingPackage;
    public final /* synthetic */ android.app.appfunctions.ExecuteAppFunctionRequest $request;
    public int label;
    public final /* synthetic */ AppFunctionCompatService this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppFunctionCompatService$onExecuteFunction$functionExecutionJob$1(AppFunctionCompatService appFunctionCompatService, android.app.appfunctions.ExecuteAppFunctionRequest executeAppFunctionRequest, String str, OutcomeReceiver outcomeReceiver, Continuation continuation) {
        super(2, continuation);
        this.this$0 = appFunctionCompatService;
        this.$request = executeAppFunctionRequest;
        this.$callingPackage = str;
        this.$callback = outcomeReceiver;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AppFunctionCompatService$onExecuteFunction$functionExecutionJob$1(this.this$0, this.$request, this.$callingPackage, this.$callback, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((AppFunctionCompatService$onExecuteFunction$functionExecutionJob$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0055, code lost:
    
        if (r8 == r0) goto L22;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r7.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L20
            if (r1 == r3) goto L1c
            if (r1 != r2) goto L14
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: androidx.appfunctions.AppFunctionException -> L12
            goto L58
        L12:
            r8 = move-exception
            goto L86
        L14:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L1c:
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: androidx.appfunctions.AppFunctionException -> L12
            goto L3b
        L20:
            kotlin.ResultKt.throwOnFailure(r8)
            androidx.appfunctions.internal.AppFunctionMetadataUtils r8 = androidx.appfunctions.internal.AppFunctionMetadataUtils.INSTANCE     // Catch: androidx.appfunctions.AppFunctionException -> L12
            androidx.appfunctions.AppFunctionCompatService r1 = r7.this$0     // Catch: androidx.appfunctions.AppFunctionException -> L12
            android.app.appfunctions.ExecuteAppFunctionRequest r4 = r7.$request     // Catch: androidx.appfunctions.AppFunctionException -> L12
            java.lang.String r4 = androidx.appfunctions.AppFunctionCompatService$onExecuteFunction$functionExecutionJob$1$$ExternalSyntheticApiModelOutline0.m(r4)     // Catch: androidx.appfunctions.AppFunctionException -> L12
            java.lang.String r5 = "getFunctionIdentifier(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r4, r5)     // Catch: androidx.appfunctions.AppFunctionException -> L12
            r7.label = r3     // Catch: androidx.appfunctions.AppFunctionException -> L12
            java.lang.Object r8 = r8.getAppFunctionMetadata(r1, r4, r7)     // Catch: androidx.appfunctions.AppFunctionException -> L12
            if (r8 != r0) goto L3b
            goto L57
        L3b:
            androidx.appfunctions.metadata.AppFunctionMetadata r8 = (androidx.appfunctions.metadata.AppFunctionMetadata) r8     // Catch: androidx.appfunctions.AppFunctionException -> L12
            if (r8 == 0) goto L5b
            androidx.appfunctions.internal.Dispatchers r1 = androidx.appfunctions.internal.Dispatchers.INSTANCE     // Catch: androidx.appfunctions.AppFunctionException -> L12
            kotlinx.coroutines.CoroutineDispatcher r1 = r1.getMain()     // Catch: androidx.appfunctions.AppFunctionException -> L12
            androidx.appfunctions.AppFunctionCompatService$onExecuteFunction$functionExecutionJob$1$result$1 r3 = new androidx.appfunctions.AppFunctionCompatService$onExecuteFunction$functionExecutionJob$1$result$1     // Catch: androidx.appfunctions.AppFunctionException -> L12
            androidx.appfunctions.AppFunctionCompatService r4 = r7.this$0     // Catch: androidx.appfunctions.AppFunctionException -> L12
            android.app.appfunctions.ExecuteAppFunctionRequest r5 = r7.$request     // Catch: androidx.appfunctions.AppFunctionException -> L12
            r6 = 0
            r3.<init>(r4, r5, r8, r6)     // Catch: androidx.appfunctions.AppFunctionException -> L12
            r7.label = r2     // Catch: androidx.appfunctions.AppFunctionException -> L12
            java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r1, r3, r7)     // Catch: androidx.appfunctions.AppFunctionException -> L12
            if (r8 != r0) goto L58
        L57:
            return r0
        L58:
            androidx.appfunctions.ExecuteAppFunctionResponse r8 = (androidx.appfunctions.ExecuteAppFunctionResponse) r8     // Catch: androidx.appfunctions.AppFunctionException -> L12
            goto L8c
        L5b:
            androidx.appfunctions.AppFunctionFunctionNotFoundException r8 = new androidx.appfunctions.AppFunctionFunctionNotFoundException     // Catch: androidx.appfunctions.AppFunctionException -> L12
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: androidx.appfunctions.AppFunctionException -> L12
            r0.<init>()     // Catch: androidx.appfunctions.AppFunctionException -> L12
            java.lang.String r1 = "No function found with identifier: "
            r0.append(r1)     // Catch: androidx.appfunctions.AppFunctionException -> L12
            android.app.appfunctions.ExecuteAppFunctionRequest r1 = r7.$request     // Catch: androidx.appfunctions.AppFunctionException -> L12
            java.lang.String r1 = androidx.appfunctions.AppFunctionCompatService$onExecuteFunction$functionExecutionJob$1$$ExternalSyntheticApiModelOutline0.m(r1)     // Catch: androidx.appfunctions.AppFunctionException -> L12
            r0.append(r1)     // Catch: androidx.appfunctions.AppFunctionException -> L12
            java.lang.String r1 = " in package: "
            r0.append(r1)     // Catch: androidx.appfunctions.AppFunctionException -> L12
            androidx.appfunctions.AppFunctionCompatService r1 = r7.this$0     // Catch: androidx.appfunctions.AppFunctionException -> L12
            java.lang.String r1 = androidx.appfunctions.AppFunctionCompatService$onExecuteFunction$functionExecutionJob$1$$ExternalSyntheticApiModelOutline1.m(r1)     // Catch: androidx.appfunctions.AppFunctionException -> L12
            r0.append(r1)     // Catch: androidx.appfunctions.AppFunctionException -> L12
            java.lang.String r0 = r0.toString()     // Catch: androidx.appfunctions.AppFunctionException -> L12
            r8.<init>(r0)     // Catch: androidx.appfunctions.AppFunctionException -> L12
            throw r8     // Catch: androidx.appfunctions.AppFunctionException -> L12
        L86:
            androidx.appfunctions.ExecuteAppFunctionResponse$Error r0 = new androidx.appfunctions.ExecuteAppFunctionResponse$Error
            r0.<init>(r8)
            r8 = r0
        L8c:
            boolean r0 = r8 instanceof androidx.appfunctions.ExecuteAppFunctionResponse.Success
            if (r0 == 0) goto La3
            androidx.appfunctions.ExecuteAppFunctionResponse$Success r8 = (androidx.appfunctions.ExecuteAppFunctionResponse.Success) r8
            androidx.appfunctions.AppFunctionCompatService r0 = r7.this$0
            java.lang.String r1 = r7.$callingPackage
            r8.grantUriAccess$appfunctions_release(r0, r1)
            android.os.OutcomeReceiver r7 = r7.$callback
            android.app.appfunctions.ExecuteAppFunctionResponse r8 = r8.toPlatformClass$appfunctions_release()
            r7.onResult(r8)
            goto Lb6
        La3:
            boolean r0 = r8 instanceof androidx.appfunctions.ExecuteAppFunctionResponse.Error
            if (r0 == 0) goto Lb9
            android.os.OutcomeReceiver r7 = r7.$callback
            androidx.appfunctions.ExecuteAppFunctionResponse$Error r8 = (androidx.appfunctions.ExecuteAppFunctionResponse.Error) r8
            androidx.appfunctions.AppFunctionException r8 = r8.getError()
            android.app.appfunctions.AppFunctionException r8 = r8.toPlatformClass()
            r7.onError(r8)
        Lb6:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        Lb9:
            kotlin.NoWhenBranchMatchedException r7 = new kotlin.NoWhenBranchMatchedException
            r7.<init>()
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appfunctions.AppFunctionCompatService$onExecuteFunction$functionExecutionJob$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
