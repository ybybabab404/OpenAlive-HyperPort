package androidx.appfunctions.service;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.RestrictTo;
import androidx.appfunctions.AppFunctionAppUnknownException;
import androidx.appfunctions.AppFunctionException;
import androidx.appfunctions.ExecuteAppFunctionRequest;
import androidx.appfunctions.ExecuteAppFunctionResponse;
import androidx.appfunctions.ExtensionsAppFunctionCompatService;
import androidx.appfunctions.internal.AggregatedAppFunctionInventory;
import androidx.appfunctions.internal.Dependencies;
import androidx.appfunctions.internal.Dispatchers;
import androidx.appfunctions.service.internal.ServiceDependencies;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
/* JADX INFO: compiled from: ExtensionAppFunctionService.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SuppressLint({"NewApi"})
@RestrictTo
public final class ExtensionAppFunctionService extends ExtensionsAppFunctionCompatService {
    public AppFunctionServiceDelegate delegate;

    /* JADX INFO: renamed from: androidx.appfunctions.service.ExtensionAppFunctionService$executeFunction$1, reason: invalid class name */
    /* JADX INFO: compiled from: ExtensionAppFunctionService.kt */
    @Metadata
    @DebugMetadata(c = "androidx.appfunctions.service.ExtensionAppFunctionService", f = "ExtensionAppFunctionService.kt", l = {54}, m = "executeFunction")
    public static final class AnonymousClass1 extends ContinuationImpl {
        public int label;
        public /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ExtensionAppFunctionService.this.executeFunction(null, this);
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Override // androidx.appfunctions.ExtensionsAppFunctionCompatService
    @Nullable
    public Object executeFunction(@NotNull ExecuteAppFunctionRequest executeAppFunctionRequest, @NotNull Continuation<? super ExecuteAppFunctionResponse> continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        } else {
            anonymousClass1 = new AnonymousClass1(continuation);
        }
        Object objExecuteFunction = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = anonymousClass1.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(objExecuteFunction);
                AppFunctionServiceDelegate appFunctionServiceDelegate = this.delegate;
                if (appFunctionServiceDelegate == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("delegate");
                    appFunctionServiceDelegate = null;
                }
                anonymousClass1.label = 1;
                objExecuteFunction = appFunctionServiceDelegate.executeFunction(executeAppFunctionRequest, anonymousClass1);
                if (objExecuteFunction == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(objExecuteFunction);
            }
            return (ExecuteAppFunctionResponse) objExecuteFunction;
        } catch (AppFunctionException e) {
            return new ExecuteAppFunctionResponse.Error(e);
        } catch (Exception e2) {
            return new ExecuteAppFunctionResponse.Error(new AppFunctionAppUnknownException(e2.getMessage()));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onCreate() {
        super.onCreate();
        Context context = (Context) this;
        CoroutineDispatcher main = Dispatchers.INSTANCE.getMain();
        Dependencies dependencies = Dependencies.INSTANCE;
        AggregatedAppFunctionInventory aggregatedAppFunctionInventory = dependencies.getAggregatedAppFunctionInventory();
        if (aggregatedAppFunctionInventory == null) {
            throw new IllegalStateException("Required value was null.");
        }
        this.delegate = new AppFunctionServiceDelegate(context, main, aggregatedAppFunctionInventory, ServiceDependencies.INSTANCE.getAggregatedAppFunctionInvoker$appfunctions_service_release(), dependencies.getTranslatorSelector());
    }
}
