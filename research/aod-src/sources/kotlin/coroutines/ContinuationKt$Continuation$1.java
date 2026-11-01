package kotlin.coroutines;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.jvm.functions.Function1;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: Continuation.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ContinuationKt$Continuation$1 implements Continuation<Object> {
    public final /* synthetic */ CoroutineContext $context;
    public final /* synthetic */ Function1 $resumeWith;

    @Override // kotlin.coroutines.Continuation
    public CoroutineContext getContext() {
        return this.$context;
    }

    @Override // kotlin.coroutines.Continuation
    public void resumeWith(Object obj) {
        this.$resumeWith.invoke(Result.m1060boximpl(obj));
    }
}
