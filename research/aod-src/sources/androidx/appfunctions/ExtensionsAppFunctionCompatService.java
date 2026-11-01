package androidx.appfunctions;

import android.os.CancellationSignal;
import android.os.OutcomeReceiver;
import androidx.annotation.CallSuper;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.internal.Dispatchers;
import com.android.extensions.appfunctions.AppFunctionService;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;
import org.jetbrains.annotations.NotNull;

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
/* JADX INFO: compiled from: ExtensionsAppFunctionCompatService.kt */
/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@Metadata
public abstract class ExtensionsAppFunctionCompatService extends AppFunctionService {

    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    public static final String SERVICE_INTERFACE = "android.app.appfunctions.AppFunctionService";
    public final CoroutineScope workerCoroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.INSTANCE.getWorker());

    /* JADX INFO: compiled from: ExtensionsAppFunctionCompatService.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public abstract Object executeFunction(ExecuteAppFunctionRequest executeAppFunctionRequest, Continuation continuation);

    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        CoroutineScopeKt.cancel$default(this.workerCoroutineScope, null, 1, null);
    }

    public final void onExecuteFunction(@NotNull com.android.extensions.appfunctions.ExecuteAppFunctionRequest request, @NotNull String callingPackage, @NotNull CancellationSignal cancellationSignal, @NotNull OutcomeReceiver<com.android.extensions.appfunctions.ExecuteAppFunctionResponse, com.android.extensions.appfunctions.AppFunctionException> callback) {
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(callingPackage, "callingPackage");
        Intrinsics.checkNotNullParameter(cancellationSignal, "cancellationSignal");
        Intrinsics.checkNotNullParameter(callback, "callback");
        final Job jobLaunch$default = BuildersKt__Builders_commonKt.launch$default(this.workerCoroutineScope, null, null, new ExtensionsAppFunctionCompatService$onExecuteFunction$functionExecutionJob$1(this, request, callingPackage, callback, null), 3, null);
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.appfunctions.ExtensionsAppFunctionCompatService$$ExternalSyntheticLambda0
            @Override // android.os.CancellationSignal.OnCancelListener
            public final void onCancel() {
                Job.DefaultImpls.cancel$default(jobLaunch$default, null, 1, null);
            }
        });
    }
}
