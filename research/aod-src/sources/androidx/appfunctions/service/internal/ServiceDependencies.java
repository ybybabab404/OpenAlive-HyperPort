package androidx.appfunctions.service.internal;

import androidx.annotation.RequiresApi;
import androidx.appfunctions.internal.ClassUtilsKt;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ServiceDependencies.kt */
/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@Metadata
public final class ServiceDependencies {
    public static final ServiceDependencies INSTANCE = new ServiceDependencies();
    public static final Lazy aggregatedAppFunctionInvoker$delegate = LazyKt.lazy(new Function0() { // from class: androidx.appfunctions.service.internal.ServiceDependencies$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return ServiceDependencies.aggregatedAppFunctionInvoker_delegate$lambda$0();
        }
    });

    public static final AggregatedAppFunctionInvoker aggregatedAppFunctionInvoker_delegate$lambda$0() {
        return (AggregatedAppFunctionInvoker) ClassUtilsKt.findImpl(AggregatedAppFunctionInvoker.class, "$", "_Impl");
    }

    public final AggregatedAppFunctionInvoker getAggregatedAppFunctionInvoker$appfunctions_service_release() {
        return (AggregatedAppFunctionInvoker) aggregatedAppFunctionInvoker$delegate.getValue();
    }
}
