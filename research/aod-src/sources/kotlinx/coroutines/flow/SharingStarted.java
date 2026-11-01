package kotlinx.coroutines.flow;

import kotlin.Metadata;

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
/* JADX INFO: compiled from: SharingStarted.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public interface SharingStarted {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: SharingStarted.kt */
    @Metadata
    public static final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final SharingStarted Eagerly = new StartedEagerly();
        public static final SharingStarted Lazily = new StartedLazily();

        public static /* synthetic */ SharingStarted WhileSubscribed$default(Companion companion, long j, long j2, int i, Object obj) {
            if ((i & 1) != 0) {
                j = 0;
            }
            if ((i & 2) != 0) {
                j2 = Long.MAX_VALUE;
            }
            return companion.WhileSubscribed(j, j2);
        }

        public final SharingStarted WhileSubscribed(long j, long j2) {
            return new StartedWhileSubscribed(j, j2);
        }

        public final SharingStarted getEagerly() {
            return Eagerly;
        }

        public final SharingStarted getLazily() {
            return Lazily;
        }
    }

    Flow command(StateFlow stateFlow);
}
