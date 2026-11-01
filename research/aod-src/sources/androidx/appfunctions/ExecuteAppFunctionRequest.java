package androidx.appfunctions;

import android.app.appsearch.GenericDocument;
import android.os.Bundle;
import androidx.appfunctions.metadata.AppFunctionMetadata;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
/* JADX INFO: compiled from: ExecuteAppFunctionRequest.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class ExecuteAppFunctionRequest {
    public static final Companion Companion = new Companion(null);
    public final String functionIdentifier;
    public final AppFunctionData functionParameters;
    public final String targetPackageName;
    public final boolean useJetpackSchema;

    /* JADX INFO: compiled from: ExecuteAppFunctionRequest.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final AppFunctionData createAppFunctionDataWithParameterSpec(AppFunctionMetadata appFunctionMetadata, String str, AppFunctionData appFunctionData) {
            return appFunctionMetadata != null ? appFunctionData.replaceSpecWith$appfunctions_release(appFunctionMetadata.getParameters(), appFunctionMetadata.getComponents()) : appFunctionData;
        }

        public final ExecuteAppFunctionRequest fromPlatformClass$appfunctions_release(android.app.appfunctions.ExecuteAppFunctionRequest request, AppFunctionMetadata appFunctionMetadata) {
            Intrinsics.checkNotNullParameter(request, "request");
            String targetPackageName = request.getTargetPackageName();
            Intrinsics.checkNotNullExpressionValue(targetPackageName, "getTargetPackageName(...)");
            String functionIdentifier = request.getFunctionIdentifier();
            Intrinsics.checkNotNullExpressionValue(functionIdentifier, "getFunctionIdentifier(...)");
            String functionIdentifier2 = request.getFunctionIdentifier();
            Intrinsics.checkNotNullExpressionValue(functionIdentifier2, "getFunctionIdentifier(...)");
            GenericDocument parameters = request.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            Bundle bundle = request.getExtras().getBundle("androidXAppfunctionsExtraParameters");
            if (bundle == null) {
                bundle = Bundle.EMPTY;
            }
            Intrinsics.checkNotNull(bundle);
            return new ExecuteAppFunctionRequest(targetPackageName, functionIdentifier, createAppFunctionDataWithParameterSpec(appFunctionMetadata, functionIdentifier2, new AppFunctionData(parameters, bundle)), request.getExtras().getBoolean("androidXAppfunctionsExtraUseJetpackSchema", false));
        }

        public final ExecuteAppFunctionRequest fromPlatformExtensionClass$appfunctions_release(com.android.extensions.appfunctions.ExecuteAppFunctionRequest request, AppFunctionMetadata appFunctionMetadata) {
            Intrinsics.checkNotNullParameter(request, "request");
            String targetPackageName = request.getTargetPackageName();
            Intrinsics.checkNotNullExpressionValue(targetPackageName, "getTargetPackageName(...)");
            String functionIdentifier = request.getFunctionIdentifier();
            Intrinsics.checkNotNullExpressionValue(functionIdentifier, "getFunctionIdentifier(...)");
            String functionIdentifier2 = request.getFunctionIdentifier();
            Intrinsics.checkNotNullExpressionValue(functionIdentifier2, "getFunctionIdentifier(...)");
            GenericDocument parameters = request.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            Bundle bundle = request.getExtras().getBundle("androidXAppfunctionsExtraParameters");
            if (bundle == null) {
                bundle = Bundle.EMPTY;
            }
            Intrinsics.checkNotNull(bundle);
            return new ExecuteAppFunctionRequest(targetPackageName, functionIdentifier, createAppFunctionDataWithParameterSpec(appFunctionMetadata, functionIdentifier2, new AppFunctionData(parameters, bundle)), request.getExtras().getBoolean("androidXAppfunctionsExtraUseJetpackSchema", false));
        }
    }

    public ExecuteAppFunctionRequest(String targetPackageName, String functionIdentifier, AppFunctionData functionParameters, boolean z) {
        Intrinsics.checkNotNullParameter(targetPackageName, "targetPackageName");
        Intrinsics.checkNotNullParameter(functionIdentifier, "functionIdentifier");
        Intrinsics.checkNotNullParameter(functionParameters, "functionParameters");
        this.targetPackageName = targetPackageName;
        this.functionIdentifier = functionIdentifier;
        this.functionParameters = functionParameters;
        this.useJetpackSchema = z;
    }

    public static /* synthetic */ ExecuteAppFunctionRequest copy$default(ExecuteAppFunctionRequest executeAppFunctionRequest, String str, String str2, AppFunctionData appFunctionData, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            str = executeAppFunctionRequest.targetPackageName;
        }
        if ((i & 2) != 0) {
            str2 = executeAppFunctionRequest.functionIdentifier;
        }
        if ((i & 4) != 0) {
            appFunctionData = executeAppFunctionRequest.functionParameters;
        }
        if ((i & 8) != 0) {
            z = executeAppFunctionRequest.useJetpackSchema;
        }
        return executeAppFunctionRequest.copy(str, str2, appFunctionData, z);
    }

    public final ExecuteAppFunctionRequest copy(String targetPackageName, String functionIdentifier, AppFunctionData functionParameters, boolean z) {
        Intrinsics.checkNotNullParameter(targetPackageName, "targetPackageName");
        Intrinsics.checkNotNullParameter(functionIdentifier, "functionIdentifier");
        Intrinsics.checkNotNullParameter(functionParameters, "functionParameters");
        return new ExecuteAppFunctionRequest(targetPackageName, functionIdentifier, functionParameters, z);
    }

    public final String getFunctionIdentifier() {
        return this.functionIdentifier;
    }

    public final AppFunctionData getFunctionParameters() {
        return this.functionParameters;
    }

    public final String getTargetPackageName() {
        return this.targetPackageName;
    }

    public final boolean getUseJetpackSchema() {
        return this.useJetpackSchema;
    }

    public final android.app.appfunctions.ExecuteAppFunctionRequest toPlatformClass$appfunctions_release() {
        ExecuteAppFunctionRequest$$ExternalSyntheticApiModelOutline1.m();
        android.app.appfunctions.ExecuteAppFunctionRequest.Builder parameters = ExecuteAppFunctionRequest$$ExternalSyntheticApiModelOutline0.m(this.targetPackageName, this.functionIdentifier).setParameters(this.functionParameters.getGenericDocument$appfunctions_release());
        Bundle bundle = new Bundle();
        bundle.putBundle("androidXAppfunctionsExtraParameters", this.functionParameters.getExtras$appfunctions_release());
        bundle.putBoolean("androidXAppfunctionsExtraUseJetpackSchema", this.useJetpackSchema);
        android.app.appfunctions.ExecuteAppFunctionRequest executeAppFunctionRequestBuild = parameters.setExtras(bundle).build();
        Intrinsics.checkNotNullExpressionValue(executeAppFunctionRequestBuild, "build(...)");
        return executeAppFunctionRequestBuild;
    }

    public final com.android.extensions.appfunctions.ExecuteAppFunctionRequest toPlatformExtensionClass$appfunctions_release() {
        com.android.extensions.appfunctions.ExecuteAppFunctionRequest.Builder parameters = new com.android.extensions.appfunctions.ExecuteAppFunctionRequest.Builder(this.targetPackageName, this.functionIdentifier).setParameters(this.functionParameters.getGenericDocument$appfunctions_release());
        Bundle bundle = new Bundle();
        bundle.putBundle("androidXAppfunctionsExtraParameters", this.functionParameters.getExtras$appfunctions_release());
        bundle.putBoolean("androidXAppfunctionsExtraUseJetpackSchema", this.useJetpackSchema);
        com.android.extensions.appfunctions.ExecuteAppFunctionRequest executeAppFunctionRequestBuild = parameters.setExtras(bundle).build();
        Intrinsics.checkNotNullExpressionValue(executeAppFunctionRequestBuild, "build(...)");
        return executeAppFunctionRequestBuild;
    }

    public String toString() {
        return "ExecuteAppFunctionRequest(targetPackageName=" + this.targetPackageName + ", functionIdentifier=" + this.functionIdentifier + ", functionParameters=" + this.functionParameters + ')';
    }
}
