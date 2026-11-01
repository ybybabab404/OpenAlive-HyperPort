package androidx.appfunctions.internal;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.RestrictTo;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.ExecutorCoroutineDispatcher;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.android.HandlerDispatcher;
import kotlinx.coroutines.android.HandlerDispatcherKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: Dispatchers.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@RestrictTo
public final class Dispatchers {
    public static final Dispatchers INSTANCE = new Dispatchers();
    public static final Lazy Main$delegate = LazyKt.lazy(new Function0() { // from class: androidx.appfunctions.internal.Dispatchers$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Dispatchers.Main_delegate$lambda$0();
        }
    });
    public static final Lazy Worker$delegate = LazyKt.lazy(new Function0() { // from class: androidx.appfunctions.internal.Dispatchers$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Dispatchers.Worker_delegate$lambda$1();
        }
    });

    public static final HandlerDispatcher Main_delegate$lambda$0() {
        return HandlerDispatcherKt.from$default(new Handler(Looper.getMainLooper()), null, 1, null);
    }

    public static final ExecutorCoroutineDispatcher Worker_delegate$lambda$1() {
        ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
        Intrinsics.checkNotNullExpressionValue(executorServiceNewSingleThreadExecutor, "newSingleThreadExecutor(...)");
        return ExecutorsKt.from(executorServiceNewSingleThreadExecutor);
    }

    public final CoroutineDispatcher getMain() {
        return (CoroutineDispatcher) Main$delegate.getValue();
    }

    public final CoroutineDispatcher getWorker() {
        return (CoroutineDispatcher) Worker$delegate.getValue();
    }
}
