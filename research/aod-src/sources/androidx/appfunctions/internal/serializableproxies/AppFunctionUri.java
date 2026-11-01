package androidx.appfunctions.internal.serializableproxies;

import android.net.Uri;
import androidx.annotation.RestrictTo;
import androidx.appfunctions.AppFunctionSerializableProxy;
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
/* JADX INFO: compiled from: AppFunctionUri.kt */
/* JADX INFO: loaded from: classes.dex */
@AppFunctionSerializableProxy
@Metadata
@RestrictTo
public final class AppFunctionUri {
    public static final Companion Companion = new Companion(null);
    public final String uri;

    /* JADX INFO: compiled from: AppFunctionUri.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final AppFunctionUri fromUri(Uri androidUri) {
            Intrinsics.checkNotNullParameter(androidUri, "androidUri");
            String string = androidUri.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return new AppFunctionUri(string);
        }
    }

    public AppFunctionUri(String uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        this.uri = uri;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof AppFunctionUri) && Intrinsics.areEqual(this.uri, ((AppFunctionUri) obj).uri);
    }

    public final String getUri() {
        return this.uri;
    }

    public int hashCode() {
        return this.uri.hashCode();
    }

    public String toString() {
        return "AppFunctionUri(uri=" + this.uri + ')';
    }

    public final Uri toUri() {
        Uri uri = Uri.parse(this.uri);
        Intrinsics.checkNotNullExpressionValue(uri, "parse(...)");
        return uri;
    }
}
