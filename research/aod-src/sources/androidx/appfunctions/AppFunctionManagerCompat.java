package androidx.appfunctions;

import androidx.annotation.RestrictTo;
import androidx.appfunctions.internal.AppFunctionManagerApi;
import androidx.appfunctions.internal.AppFunctionReader;
import androidx.appfunctions.internal.Translator;
import androidx.appfunctions.internal.TranslatorSelector;
import androidx.appfunctions.metadata.AppFunctionMetadata;
import androidx.appfunctions.metadata.AppFunctionSchemaMetadata;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

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
/* JADX INFO: compiled from: AppFunctionManagerCompat.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class AppFunctionManagerCompat {
    public static final Companion Companion = new Companion(null);
    public final AppFunctionManagerApi appFunctionManagerApi;
    public final AppFunctionReader appFunctionReader;
    public final TranslatorSelector translatorSelector;

    /* JADX INFO: compiled from: AppFunctionManagerCompat.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: AppFunctionManagerCompat.kt */
    @Metadata
    @Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention
    @RestrictTo
    public @interface EnabledState {
    }

    /* JADX INFO: renamed from: androidx.appfunctions.AppFunctionManagerCompat$executeAppFunction$1, reason: invalid class name */
    /* JADX INFO: compiled from: AppFunctionManagerCompat.kt */
    @Metadata
    @DebugMetadata(c = "androidx.appfunctions.AppFunctionManagerCompat", f = "AppFunctionManagerCompat.kt", l = {com.miui.maml.R.styleable.AppCompatTheme_tooltipForegroundColor, 144}, m = "executeAppFunction")
    public static final class AnonymousClass1 extends ContinuationImpl {
        public Object L$0;
        public Object L$1;
        public int label;
        public /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AppFunctionManagerCompat.this.executeAppFunction(null, this);
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    public final Object executeAppFunction(ExecuteAppFunctionRequest executeAppFunctionRequest, Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        AppFunctionMetadata appFunctionMetadata;
        Translator translator;
        AppFunctionSchemaMetadata schema;
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
        Object appFunctionMetadata2 = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = anonymousClass1.label;
        try {
            if (i2 != 0) {
                if (i2 == 1) {
                    executeAppFunctionRequest = (ExecuteAppFunctionRequest) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(appFunctionMetadata2);
                } else {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    translator = (Translator) anonymousClass1.L$1;
                    appFunctionMetadata = (AppFunctionMetadata) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(appFunctionMetadata2);
                }
                return processResponse(translator, appFunctionMetadata, (ExecuteAppFunctionResponse) appFunctionMetadata2);
            }
            ResultKt.throwOnFailure(appFunctionMetadata2);
            AppFunctionReader appFunctionReader = this.appFunctionReader;
            String functionIdentifier = executeAppFunctionRequest.getFunctionIdentifier();
            String targetPackageName = executeAppFunctionRequest.getTargetPackageName();
            anonymousClass1.L$0 = executeAppFunctionRequest;
            anonymousClass1.label = 1;
            appFunctionMetadata2 = appFunctionReader.getAppFunctionMetadata(functionIdentifier, targetPackageName, anonymousClass1);
            if (appFunctionMetadata2 == coroutine_suspended) {
            }
            return coroutine_suspended;
            ExecuteAppFunctionRequest executeAppFunctionRequestCopy$default = executeAppFunctionRequest;
            AppFunctionMetadata appFunctionMetadata3 = (AppFunctionMetadata) appFunctionMetadata2;
            Translator translator2 = (appFunctionMetadata3 == null || (schema = appFunctionMetadata3.getSchema()) == null || schema.getVersion() != 1) ? null : this.translatorSelector.getTranslator(appFunctionMetadata3.getSchema());
            if (translator2 != null) {
                executeAppFunctionRequestCopy$default = ExecuteAppFunctionRequest.copy$default(executeAppFunctionRequestCopy$default, null, null, translator2.downgradeRequest(executeAppFunctionRequestCopy$default.getFunctionParameters()), false, 11, null);
            }
            AppFunctionManagerApi appFunctionManagerApi = this.appFunctionManagerApi;
            anonymousClass1.L$0 = appFunctionMetadata3;
            anonymousClass1.L$1 = translator2;
            anonymousClass1.label = 2;
            Object objExecuteAppFunction = appFunctionManagerApi.executeAppFunction(executeAppFunctionRequestCopy$default, anonymousClass1);
            if (objExecuteAppFunction != coroutine_suspended) {
                appFunctionMetadata = appFunctionMetadata3;
                translator = translator2;
                appFunctionMetadata2 = objExecuteAppFunction;
                return processResponse(translator, appFunctionMetadata, (ExecuteAppFunctionResponse) appFunctionMetadata2);
            }
            return coroutine_suspended;
        } catch (AppFunctionFunctionNotFoundException e) {
            return new ExecuteAppFunctionResponse.Error(e);
        } catch (Exception e2) {
            return new ExecuteAppFunctionResponse.Error(new AppFunctionSystemUnknownException("Something went wrong when querying the app function from AppSearch: " + e2.getMessage()));
        }
    }

    public final ExecuteAppFunctionResponse processResponse(Translator translator, AppFunctionMetadata appFunctionMetadata, ExecuteAppFunctionResponse executeAppFunctionResponse) {
        AppFunctionData returnValue;
        if (!(executeAppFunctionResponse instanceof ExecuteAppFunctionResponse.Success)) {
            return executeAppFunctionResponse;
        }
        if (translator == null || (returnValue = translator.upgradeResponse(((ExecuteAppFunctionResponse.Success) executeAppFunctionResponse).getReturnValue())) == null) {
            returnValue = ((ExecuteAppFunctionResponse.Success) executeAppFunctionResponse).getReturnValue();
        }
        return appFunctionMetadata == null ? new ExecuteAppFunctionResponse.Success(returnValue) : new ExecuteAppFunctionResponse.Success(returnValue.replaceSpecWith$appfunctions_release(appFunctionMetadata.getResponse(), appFunctionMetadata.getComponents()));
    }
}
