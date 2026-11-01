package androidx.appfunctions.service;

import android.content.Context;
import android.util.Log;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appfunctions.AppFunctionAppUnknownException;
import androidx.appfunctions.AppFunctionCancelledException;
import androidx.appfunctions.AppFunctionContext;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.AppFunctionException;
import androidx.appfunctions.AppFunctionFunctionNotFoundException;
import androidx.appfunctions.ExecuteAppFunctionRequest;
import androidx.appfunctions.ExecuteAppFunctionResponse;
import androidx.appfunctions.internal.AggregatedAppFunctionInventory;
import androidx.appfunctions.internal.Translator;
import androidx.appfunctions.internal.TranslatorSelector;
import androidx.appfunctions.metadata.AppFunctionComponentsMetadata;
import androidx.appfunctions.metadata.AppFunctionParameterMetadata;
import androidx.appfunctions.metadata.AppFunctionSchemaMetadata;
import androidx.appfunctions.metadata.CompileTimeAppFunctionMetadata;
import androidx.appfunctions.service.internal.AggregatedAppFunctionInvoker;
import androidx.appfunctions.service.internal.AppFunctionDataParameterExtractorKt;
import androidx.appfunctions.service.internal.AppFunctionResponseMetadataReturnValueBuilderKt;
import java.util.Map;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;

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
/* JADX INFO: compiled from: AppFunctionServiceDelegate.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@RequiresApi
@RestrictTo
@SourceDebugExtension
public final class AppFunctionServiceDelegate {
    public final AggregatedAppFunctionInventory aggregatedInventory;
    public final AggregatedAppFunctionInvoker aggregatedInvoker;
    public final Context appContext;
    public final CoroutineContext mainCoroutineContext;
    public final TranslatorSelector translatorSelector;

    /* JADX INFO: renamed from: androidx.appfunctions.service.AppFunctionServiceDelegate$executeFunction$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AppFunctionServiceDelegate.kt */
    @Metadata
    @DebugMetadata(c = "androidx.appfunctions.service.AppFunctionServiceDelegate", f = "AppFunctionServiceDelegate.kt", l = {com.miui.maml.R.styleable.AppCompatTheme_listDividerAlertDialog}, m = "executeFunction")
    public static final class C00711 extends ContinuationImpl {
        public Object L$0;
        public int label;
        public /* synthetic */ Object result;

        public C00711(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AppFunctionServiceDelegate.this.executeFunction(null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.appfunctions.service.AppFunctionServiceDelegate$unsafeInvokeFunction$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AppFunctionServiceDelegate.kt */
    @Metadata
    @DebugMetadata(c = "androidx.appfunctions.service.AppFunctionServiceDelegate", f = "AppFunctionServiceDelegate.kt", l = {141}, m = "unsafeInvokeFunction")
    public static final class C00721 extends ContinuationImpl {
        public Object L$0;
        public Object L$1;
        public Object L$2;
        public int label;
        public /* synthetic */ Object result;

        public C00721(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AppFunctionServiceDelegate.this.unsafeInvokeFunction(null, null, null, null, null, this);
        }
    }

    public AppFunctionServiceDelegate(Context context, CoroutineContext mainCoroutineContext, AggregatedAppFunctionInventory aggregatedInventory, AggregatedAppFunctionInvoker aggregatedInvoker, TranslatorSelector translatorSelector) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(mainCoroutineContext, "mainCoroutineContext");
        Intrinsics.checkNotNullParameter(aggregatedInventory, "aggregatedInventory");
        Intrinsics.checkNotNullParameter(aggregatedInvoker, "aggregatedInvoker");
        Intrinsics.checkNotNullParameter(translatorSelector, "translatorSelector");
        this.mainCoroutineContext = mainCoroutineContext;
        this.aggregatedInventory = aggregatedInventory;
        this.aggregatedInvoker = aggregatedInvoker;
        this.translatorSelector = translatorSelector;
        this.appContext = context.getApplicationContext();
    }

    public final AppFunctionContext buildAppFunctionContext() {
        return new AppFunctionContext() { // from class: androidx.appfunctions.service.AppFunctionServiceDelegate.buildAppFunctionContext.1
            @Override // androidx.appfunctions.AppFunctionContext
            public Context getContext() {
                Context context = AppFunctionServiceDelegate.this.appContext;
                Intrinsics.checkNotNullExpressionValue(context, "access$getAppContext$p(...)");
                return context;
            }
        };
    }

    /* JADX WARN: Code duplicated, block: B:8:0x0016  */
    /* JADX WARN: Multi-variable type inference failed */
    public final Object executeFunction(ExecuteAppFunctionRequest executeAppFunctionRequest, Continuation continuation) throws AppFunctionException {
        C00711 c00711;
        Exception exc;
        ExecuteAppFunctionRequest executeAppFunctionRequest2;
        CancellationException cancellationException;
        ExecuteAppFunctionRequest executeAppFunctionRequest3;
        AppFunctionException appFunctionException;
        ExecuteAppFunctionRequest executeAppFunctionRequest4;
        if (continuation instanceof C00711) {
            c00711 = (C00711) continuation;
            int i = c00711.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c00711.label = i - Integer.MIN_VALUE;
            } else {
                c00711 = new C00711(continuation);
            }
        } else {
            c00711 = new C00711(continuation);
        }
        C00711 c00712 = c00711;
        Object objUnsafeInvokeFunction = c00712.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c00712.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objUnsafeInvokeFunction);
            try {
                CompileTimeAppFunctionMetadata compileTimeAppFunctionMetadata = this.aggregatedInventory.getFunctionIdToMetadataMap().get(executeAppFunctionRequest.getFunctionIdentifier());
                try {
                    if (compileTimeAppFunctionMetadata == null) {
                        Log.d("AppFunctions", executeAppFunctionRequest.getFunctionIdentifier() + " is not available");
                        throw new AppFunctionFunctionNotFoundException(executeAppFunctionRequest.getFunctionIdentifier() + " is not available");
                    }
                    Translator translator = getTranslator(executeAppFunctionRequest, compileTimeAppFunctionMetadata.getSchema());
                    Map mapExtractParameters = extractParameters(executeAppFunctionRequest, compileTimeAppFunctionMetadata, translator);
                    AppFunctionComponentsMetadata componentsMetadata = this.aggregatedInventory.getComponentsMetadata();
                    c00712.L$0 = executeAppFunctionRequest;
                    c00712.label = 1;
                    objUnsafeInvokeFunction = unsafeInvokeFunction(executeAppFunctionRequest, compileTimeAppFunctionMetadata, componentsMetadata, mapExtractParameters, translator, c00712);
                    if (objUnsafeInvokeFunction == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    executeAppFunctionRequest = executeAppFunctionRequest;
                } catch (AppFunctionException e) {
                    appFunctionException = e;
                    executeAppFunctionRequest4 = 1;
                } catch (CancellationException e2) {
                    cancellationException = e2;
                    executeAppFunctionRequest3 = 1;
                    Log.d("AppFunctions", "Invocation of " + executeAppFunctionRequest3.getFunctionIdentifier() + " was cancelled", cancellationException);
                    throw new AppFunctionCancelledException(cancellationException.getMessage());
                } catch (Exception e3) {
                    exc = e3;
                    executeAppFunctionRequest2 = 1;
                    Log.d("AppFunctions", "Failed to invoke " + executeAppFunctionRequest2.getFunctionIdentifier(), exc);
                    throw new AppFunctionAppUnknownException(exc.getMessage());
                }
            } catch (AppFunctionException e4) {
                e = e4;
                appFunctionException = e;
                executeAppFunctionRequest4 = executeAppFunctionRequest;
            } catch (CancellationException e5) {
                e = e5;
                cancellationException = e;
                executeAppFunctionRequest3 = executeAppFunctionRequest;
                Log.d("AppFunctions", "Invocation of " + executeAppFunctionRequest3.getFunctionIdentifier() + " was cancelled", cancellationException);
                throw new AppFunctionCancelledException(cancellationException.getMessage());
            } catch (Exception e6) {
                e = e6;
                exc = e;
                executeAppFunctionRequest2 = executeAppFunctionRequest;
                Log.d("AppFunctions", "Failed to invoke " + executeAppFunctionRequest2.getFunctionIdentifier(), exc);
                throw new AppFunctionAppUnknownException(exc.getMessage());
            }
            Log.d("AppFunctions", "Failed to invoke " + executeAppFunctionRequest4.getFunctionIdentifier(), appFunctionException);
            throw appFunctionException;
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        executeAppFunctionRequest = (ExecuteAppFunctionRequest) c00712.L$0;
        try {
            ResultKt.throwOnFailure(objUnsafeInvokeFunction);
            executeAppFunctionRequest = executeAppFunctionRequest;
        } catch (AppFunctionException e7) {
            e = e7;
            appFunctionException = e;
            executeAppFunctionRequest4 = executeAppFunctionRequest;
            Log.d("AppFunctions", "Failed to invoke " + executeAppFunctionRequest4.getFunctionIdentifier(), appFunctionException);
            throw appFunctionException;
        } catch (CancellationException e8) {
            e = e8;
            cancellationException = e;
            executeAppFunctionRequest3 = executeAppFunctionRequest;
            Log.d("AppFunctions", "Invocation of " + executeAppFunctionRequest3.getFunctionIdentifier() + " was cancelled", cancellationException);
            throw new AppFunctionCancelledException(cancellationException.getMessage());
        } catch (Exception e9) {
            e = e9;
            exc = e;
            executeAppFunctionRequest2 = executeAppFunctionRequest;
            Log.d("AppFunctions", "Failed to invoke " + executeAppFunctionRequest2.getFunctionIdentifier(), exc);
            throw new AppFunctionAppUnknownException(exc.getMessage());
        }
        return (ExecuteAppFunctionResponse) objUnsafeInvokeFunction;
    }

    public final Map extractParameters(ExecuteAppFunctionRequest executeAppFunctionRequest, CompileTimeAppFunctionMetadata compileTimeAppFunctionMetadata, Translator translator) {
        AppFunctionData functionParameters;
        if (translator == null || (functionParameters = translator.upgradeRequest(executeAppFunctionRequest.getFunctionParameters())) == null) {
            functionParameters = executeAppFunctionRequest.getFunctionParameters();
        }
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        for (AppFunctionParameterMetadata appFunctionParameterMetadata : compileTimeAppFunctionMetadata.getParameters()) {
            mapCreateMapBuilder.put(appFunctionParameterMetadata.getName(), AppFunctionDataParameterExtractorKt.unsafeGetParameterValue(functionParameters, appFunctionParameterMetadata));
        }
        return MapsKt.build(mapCreateMapBuilder);
    }

    public final Translator getTranslator(ExecuteAppFunctionRequest executeAppFunctionRequest, AppFunctionSchemaMetadata appFunctionSchemaMetadata) {
        if (executeAppFunctionRequest.getUseJetpackSchema() || appFunctionSchemaMetadata == null) {
            return null;
        }
        return this.translatorSelector.getTranslator(appFunctionSchemaMetadata);
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    public final Object unsafeInvokeFunction(ExecuteAppFunctionRequest executeAppFunctionRequest, CompileTimeAppFunctionMetadata compileTimeAppFunctionMetadata, AppFunctionComponentsMetadata appFunctionComponentsMetadata, Map map, Translator translator, Continuation continuation) throws AppFunctionAppUnknownException {
        C00721 c00721;
        AppFunctionData appFunctionDataDowngradeResponse;
        if (continuation instanceof C00721) {
            c00721 = (C00721) continuation;
            int i = c00721.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c00721.label = i - Integer.MIN_VALUE;
            } else {
                c00721 = new C00721(continuation);
            }
        } else {
            c00721 = new C00721(continuation);
        }
        Object objWithContext = c00721.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c00721.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            CoroutineContext coroutineContext = this.mainCoroutineContext;
            AppFunctionServiceDelegate$unsafeInvokeFunction$result$1 appFunctionServiceDelegate$unsafeInvokeFunction$result$1 = new AppFunctionServiceDelegate$unsafeInvokeFunction$result$1(this, executeAppFunctionRequest, map, null);
            c00721.L$0 = compileTimeAppFunctionMetadata;
            c00721.L$1 = appFunctionComponentsMetadata;
            c00721.L$2 = translator;
            c00721.label = 1;
            objWithContext = BuildersKt.withContext(coroutineContext, appFunctionServiceDelegate$unsafeInvokeFunction$result$1, c00721);
            if (objWithContext == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            translator = (Translator) c00721.L$2;
            appFunctionComponentsMetadata = (AppFunctionComponentsMetadata) c00721.L$1;
            compileTimeAppFunctionMetadata = (CompileTimeAppFunctionMetadata) c00721.L$0;
            ResultKt.throwOnFailure(objWithContext);
        }
        AppFunctionData appFunctionDataUnsafeBuildReturnValue = AppFunctionResponseMetadataReturnValueBuilderKt.unsafeBuildReturnValue(compileTimeAppFunctionMetadata.getResponse(), objWithContext, appFunctionComponentsMetadata);
        if (translator != null && (appFunctionDataDowngradeResponse = translator.downgradeResponse(appFunctionDataUnsafeBuildReturnValue)) != null) {
            appFunctionDataUnsafeBuildReturnValue = appFunctionDataDowngradeResponse;
        }
        return new ExecuteAppFunctionResponse.Success(appFunctionDataUnsafeBuildReturnValue);
    }
}
