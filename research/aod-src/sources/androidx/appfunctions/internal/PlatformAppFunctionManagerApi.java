package androidx.appfunctions.internal;

import android.app.appfunctions.AppFunctionException;
import android.app.appfunctions.AppFunctionManager;
import android.app.appfunctions.ExecuteAppFunctionResponse;
import android.os.CancellationSignal;
import android.os.OutcomeReceiver;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.ExecuteAppFunctionRequest;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: PlatformAppFunctionManagerApi.kt */
/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@Metadata
@SourceDebugExtension
public final class PlatformAppFunctionManagerApi implements AppFunctionManagerApi {
    public final Lazy appFunctionManager$delegate;

    @Override // androidx.appfunctions.internal.AppFunctionManagerApi
    public Object executeAppFunction(ExecuteAppFunctionRequest executeAppFunctionRequest, Continuation continuation) {
        final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final CancellationSignal cancellationSignal = new CancellationSignal();
        cancellableContinuationImpl.invokeOnCancellation(new Function1<Throwable, Unit>() { // from class: androidx.appfunctions.internal.PlatformAppFunctionManagerApi$executeAppFunction$2$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((Throwable) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(Throwable th) {
                cancellationSignal.cancel();
            }
        });
        getAppFunctionManager().executeAppFunction(executeAppFunctionRequest.toPlatformClass$appfunctions_release(), new AppSearchUtilsKt$$ExternalSyntheticLambda0(), cancellationSignal, new OutcomeReceiver<ExecuteAppFunctionResponse, AppFunctionException>() { // from class: androidx.appfunctions.internal.PlatformAppFunctionManagerApi$executeAppFunction$2$3
            public void onError(AppFunctionException error) {
                Intrinsics.checkNotNullParameter(error, "error");
                CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                Result.Companion companion = Result.Companion;
                cancellableContinuation.resumeWith(Result.m1061constructorimpl(new androidx.appfunctions.ExecuteAppFunctionResponse.Error(androidx.appfunctions.AppFunctionException.Companion.fromPlatformClass(error))));
            }

            @Override // android.os.OutcomeReceiver
            public /* bridge */ /* synthetic */ void onError(Throwable th) {
                onError(PlatformAppFunctionManagerApi$executeAppFunction$2$3$$ExternalSyntheticApiModelOutline0.m(th));
            }

            /* JADX INFO: renamed from: onResult, reason: avoid collision after fix types in other method */
            public void onResult2(ExecuteAppFunctionResponse result) {
                Intrinsics.checkNotNullParameter(result, "result");
                CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                Result.Companion companion = Result.Companion;
                cancellableContinuation.resumeWith(Result.m1061constructorimpl(androidx.appfunctions.ExecuteAppFunctionResponse.Success.Companion.fromPlatformClass$appfunctions_release(result)));
            }

            @Override // android.os.OutcomeReceiver
            public /* bridge */ /* synthetic */ void onResult(ExecuteAppFunctionResponse executeAppFunctionResponse) {
                onResult2(PlatformAppFunctionManagerApi$executeAppFunction$2$3$$ExternalSyntheticApiModelOutline1.m(executeAppFunctionResponse));
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }

    public final AppFunctionManager getAppFunctionManager() {
        Object value = this.appFunctionManager$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return PlatformAppFunctionManagerApi$$ExternalSyntheticApiModelOutline1.m(value);
    }
}
