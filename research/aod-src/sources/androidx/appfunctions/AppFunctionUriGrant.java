package androidx.appfunctions;

import android.net.Uri;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
/* JADX INFO: compiled from: AppFunctionUriGrant.kt */
/* JADX INFO: loaded from: classes.dex */
@AppFunctionSerializable
@Metadata
public final class AppFunctionUriGrant {
    public static final Companion Companion = new Companion(null);
    public final int modeFlags;
    public final Uri uri;

    /* JADX INFO: compiled from: AppFunctionUriGrant.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean isAccessUriMode(int i) {
            return (i & 3) != 0;
        }

        public final boolean isAllowedFlags(int i) {
            return (i & (-132)) == 0;
        }
    }

    /* JADX INFO: compiled from: AppFunctionUriGrant.kt */
    @Metadata
    @Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention
    public @interface GrantUriMode {
    }

    public AppFunctionUriGrant(Uri uri, int i) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        this.uri = uri;
        this.modeFlags = i;
        Companion companion = Companion;
        if (!companion.isAllowedFlags(i)) {
            throw new IllegalArgumentException("Contains invalid flags: Allowed flags are FLAG_GRANT_READ_URI_PERMISSION, FLAG_GRANT_WRITE_URI_PERMISSION and FLAG_GRANT_PREFIX_URI_PERMISSION");
        }
        if (!companion.isAccessUriMode(i)) {
            throw new IllegalArgumentException("Must set either FLAG_GRANT_READ_URI_PERMISSION or FLAG_GRANT_WRITE_URI_PERMISSION to specify the access mode");
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppFunctionUriGrant)) {
            return false;
        }
        AppFunctionUriGrant appFunctionUriGrant = (AppFunctionUriGrant) obj;
        return Intrinsics.areEqual(this.uri, appFunctionUriGrant.uri) && this.modeFlags == appFunctionUriGrant.modeFlags;
    }

    public final int getModeFlags() {
        return this.modeFlags;
    }

    public final Uri getUri() {
        return this.uri;
    }

    public int hashCode() {
        return Objects.hash(this.uri, Integer.valueOf(this.modeFlags));
    }

    public String toString() {
        return "AppFunctionUriGrant(uri=" + this.uri + ", modeFlags=" + this.modeFlags + ')';
    }
}
