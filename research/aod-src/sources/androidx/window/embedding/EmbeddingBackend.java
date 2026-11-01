package androidx.window.embedding;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.RestrictTo;
import androidx.core.util.Consumer;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
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
/* JADX INFO: compiled from: EmbeddingBackend.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@RestrictTo
public interface EmbeddingBackend {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: EmbeddingBackend.kt */
    @Metadata
    public static final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static Function1 decorator = new Function1<EmbeddingBackend, EmbeddingBackend>() { // from class: androidx.window.embedding.EmbeddingBackend$Companion$decorator$1
            @Override // kotlin.jvm.functions.Function1
            public final EmbeddingBackend invoke(EmbeddingBackend it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it;
            }
        };

        public final EmbeddingBackend getInstance(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return (EmbeddingBackend) decorator.invoke(ExtensionEmbeddingBackend.Companion.getInstance(context));
        }
    }

    void addRule(EmbeddingRule embeddingRule);

    void addSplitListenerForActivity(Activity activity, Executor executor, Consumer consumer);

    SplitController.SplitSupportStatus getSplitSupportStatus();

    boolean isActivityEmbedded(Activity activity);

    void removeSplitListenerForActivity(Consumer consumer);
}
