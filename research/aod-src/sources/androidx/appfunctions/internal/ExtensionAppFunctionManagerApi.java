package androidx.appfunctions.internal;

import android.os.CancellationSignal;
import android.os.OutcomeReceiver;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.AppFunctionFunctionNotFoundException;
import androidx.appfunctions.AppFunctionSystemUnknownException;
import androidx.appfunctions.ExecuteAppFunctionRequest;
import com.android.extensions.appfunctions.AppFunctionException;
import com.android.extensions.appfunctions.AppFunctionManager;
import com.android.extensions.appfunctions.ExecuteAppFunctionResponse;
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
/* JADX INFO: compiled from: ExtensionAppFunctionManagerApi.kt */
/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@Metadata
@SourceDebugExtension
public final class ExtensionAppFunctionManagerApi implements AppFunctionManagerApi {
    public final Lazy appFunctionManager$delegate;

    @Override // androidx.appfunctions.internal.AppFunctionManagerApi
    public Object executeAppFunction(ExecuteAppFunctionRequest executeAppFunctionRequest, Continuation continuation) {
        final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final CancellationSignal cancellationSignal = new CancellationSignal();
        cancellableContinuationImpl.invokeOnCancellation(new Function1<Throwable, Unit>() { // from class: androidx.appfunctions.internal.ExtensionAppFunctionManagerApi$executeAppFunction$2$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((Throwable) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(Throwable th) {
                cancellationSignal.cancel();
            }
        });
        getAppFunctionManager().executeAppFunction(executeAppFunctionRequest.toPlatformExtensionClass$appfunctions_release(), new AppSearchUtilsKt$$ExternalSyntheticLambda0(), cancellationSignal, new OutcomeReceiver<ExecuteAppFunctionResponse, AppFunctionException>() { // from class: androidx.appfunctions.internal.ExtensionAppFunctionManagerApi$executeAppFunction$2$3
            @Override // android.os.OutcomeReceiver
            public void onError(AppFunctionException error) {
                Intrinsics.checkNotNullParameter(error, "error");
                androidx.appfunctions.AppFunctionException appFunctionExceptionFixAppFunctionExceptionErrorType = this.fixAppFunctionExceptionErrorType(androidx.appfunctions.AppFunctionException.Companion.fromPlatformExtensionsClass(error));
                CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                Result.Companion companion = Result.Companion;
                cancellableContinuation.resumeWith(Result.m1061constructorimpl(new androidx.appfunctions.ExecuteAppFunctionResponse.Error(appFunctionExceptionFixAppFunctionExceptionErrorType)));
            }

            @Override // android.os.OutcomeReceiver
            public void onResult(ExecuteAppFunctionResponse result) {
                Intrinsics.checkNotNullParameter(result, "result");
                CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                Result.Companion companion = Result.Companion;
                cancellableContinuation.resumeWith(Result.m1061constructorimpl(androidx.appfunctions.ExecuteAppFunctionResponse.Success.Companion.fromPlatformExtensionClass$appfunctions_release(result)));
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }

    public final androidx.appfunctions.AppFunctionException fixAppFunctionExceptionErrorType(androidx.appfunctions.AppFunctionException appFunctionException) {
        return ((appFunctionException instanceof AppFunctionSystemUnknownException) && Intrinsics.areEqual(appFunctionException.getErrorMessage(), "IllegalArgumentException: App function not found.")) ? new AppFunctionFunctionNotFoundException("App function not found.") : appFunctionException;
    }

    public final AppFunctionManager getAppFunctionManager() {
        return (AppFunctionManager) this.appFunctionManager$delegate.getValue();
    }
}
