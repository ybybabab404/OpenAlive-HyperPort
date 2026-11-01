package kotlinx.coroutines.selects;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.DelayKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: OnTimeout.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class OnTimeoutKt {
    public static final void onTimeout(SelectBuilder selectBuilder, long j, Function1 function1) {
        selectBuilder.invoke(new OnTimeout(j).getSelectClause(), function1);
    }

    /* JADX INFO: renamed from: onTimeout-8Mi8wO0, reason: not valid java name */
    public static final void m1283onTimeout8Mi8wO0(SelectBuilder selectBuilder, long j, Function1 function1) {
        onTimeout(selectBuilder, DelayKt.m1225toDelayMillisLRDsOJo(j), function1);
    }
}
