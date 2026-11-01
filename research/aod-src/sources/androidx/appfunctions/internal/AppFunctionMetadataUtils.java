package androidx.appfunctions.internal;

import android.content.Context;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.metadata.AppFunctionMetadata;
import androidx.appfunctions.metadata.CompileTimeAppFunctionMetadata;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: AppFunctionMetadataUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@Metadata
public final class AppFunctionMetadataUtils {
    public static final AppFunctionMetadataUtils INSTANCE = new AppFunctionMetadataUtils();

    public final Object getAppFunctionMetadata(Context context, String str, Continuation continuation) {
        Dependencies dependencies = Dependencies.INSTANCE;
        AppFunctionInventory appFunctionInventory$appfunctions_release = dependencies.getAppFunctionInventory$appfunctions_release();
        if (appFunctionInventory$appfunctions_release == null) {
            AppSearchAppFunctionReader appSearchAppFunctionReader = new AppSearchAppFunctionReader(context, dependencies.getSchemaAppFunctionInventory$appfunctions_release());
            String packageName = context.getPackageName();
            Intrinsics.checkNotNullExpressionValue(packageName, "getPackageName(...)");
            return appSearchAppFunctionReader.getAppFunctionMetadata(str, packageName, continuation);
        }
        CompileTimeAppFunctionMetadata compileTimeAppFunctionMetadata = (CompileTimeAppFunctionMetadata) appFunctionInventory$appfunctions_release.getFunctionIdToMetadataMap().get(str);
        if (compileTimeAppFunctionMetadata == null) {
            return null;
        }
        String id = compileTimeAppFunctionMetadata.getId();
        String packageName2 = context.getPackageName();
        Intrinsics.checkNotNullExpressionValue(packageName2, "getPackageName(...)");
        return new AppFunctionMetadata(id, packageName2, compileTimeAppFunctionMetadata.isEnabledByDefault(), compileTimeAppFunctionMetadata.getSchema(), compileTimeAppFunctionMetadata.getParameters(), compileTimeAppFunctionMetadata.getResponse(), appFunctionInventory$appfunctions_release.getComponentsMetadata(), compileTimeAppFunctionMetadata.getDescription());
    }
}
