package kotlin.io.path;

import java.nio.file.Path;
import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
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
/* JADX INFO: compiled from: PathRecursiveFunctions.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
final class PathsKt__PathRecursiveFunctionsKt$copyToRecursively$1 implements Function3 {
    public static final PathsKt__PathRecursiveFunctionsKt$copyToRecursively$1 INSTANCE = new PathsKt__PathRecursiveFunctionsKt$copyToRecursively$1();

    @Override // kotlin.jvm.functions.Function3
    public final Void invoke(Path path, Path path2, Exception exception) throws Exception {
        Intrinsics.checkNotNullParameter(path, "<unused var>");
        Intrinsics.checkNotNullParameter(path2, "<unused var>");
        Intrinsics.checkNotNullParameter(exception, "exception");
        throw exception;
    }
}
