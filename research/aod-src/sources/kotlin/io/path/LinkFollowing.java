package kotlin.io.path;

import java.nio.file.FileVisitOption;
import java.nio.file.LinkOption;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: PathTreeWalk.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class LinkFollowing {
    public static final LinkFollowing INSTANCE = new LinkFollowing();
    public static final LinkOption[] nofollowLinkOption = {LinkOption.NOFOLLOW_LINKS};
    public static final LinkOption[] followLinkOption = new LinkOption[0];
    public static final Set nofollowVisitOption = SetsKt.emptySet();
    public static final Set followVisitOption = SetsKt.setOf(FileVisitOption.FOLLOW_LINKS);

    public final LinkOption[] toLinkOptions(boolean z) {
        return z ? followLinkOption : nofollowLinkOption;
    }

    public final Set toVisitOptions(boolean z) {
        return z ? followVisitOption : nofollowVisitOption;
    }
}
