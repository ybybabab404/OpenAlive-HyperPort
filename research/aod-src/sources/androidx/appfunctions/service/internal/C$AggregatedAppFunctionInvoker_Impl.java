package androidx.appfunctions.service.internal;

import com.miui.aod.gemini.C$AodDeviceStateSetter_AppFunctionInvoker;
import com.miui.aod.gemini.C$AodMetadataManager_AppFunctionInvoker;
import com.miui.aod.gemini.C$AodStateManager_AppFunctionInvoker;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: renamed from: androidx.appfunctions.service.internal.$AggregatedAppFunctionInvoker_Impl, reason: invalid class name */
/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: $AggregatedAppFunctionInvoker_Impl.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class C$AggregatedAppFunctionInvoker_Impl extends AggregatedAppFunctionInvoker {
    public final List invokers = CollectionsKt.listOf((Object[]) new AppFunctionInvoker[]{new C$AodDeviceStateSetter_AppFunctionInvoker(), new C$AodMetadataManager_AppFunctionInvoker(), new C$AodStateManager_AppFunctionInvoker()});

    @Override // androidx.appfunctions.service.internal.AggregatedAppFunctionInvoker
    @NotNull
    public List<AppFunctionInvoker> getInvokers() {
        return this.invokers;
    }
}
