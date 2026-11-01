package androidx.appfunctions.internal;

import android.os.OutcomeReceiver;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: PlatformAppFunctionManagerApi.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class PlatformAppFunctionManagerApi$isAppFunctionEnabled$2$2 implements OutcomeReceiver<Boolean, Exception> {
    public final /* synthetic */ CancellableContinuation $cont;

    @Override // android.os.OutcomeReceiver
    public void onError(Exception error) {
        Intrinsics.checkNotNullParameter(error, "error");
        CancellableContinuation cancellableContinuation = this.$cont;
        Result.Companion companion = Result.Companion;
        cancellableContinuation.resumeWith(Result.m1061constructorimpl(ResultKt.createFailure(error)));
    }

    @Override // android.os.OutcomeReceiver
    public void onResult(Boolean bool) {
        if (bool != null) {
            this.$cont.resumeWith(Result.m1061constructorimpl(bool));
            return;
        }
        CancellableContinuation cancellableContinuation = this.$cont;
        Result.Companion companion = Result.Companion;
        cancellableContinuation.resumeWith(Result.m1061constructorimpl(ResultKt.createFailure(new IllegalStateException("Something went wrong"))));
    }
}
