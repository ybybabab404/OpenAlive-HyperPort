package androidx.appfunctions.metadata;

import java.util.List;
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
/* JADX INFO: compiled from: AppFunctionPackageMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class AppFunctionPackageMetadata {
    public static final Companion Companion = new Companion(null);
    public final List appFunctions;
    public final String packageName;

    /* JADX INFO: compiled from: AppFunctionPackageMetadata.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public AppFunctionPackageMetadata(String packageName, List appFunctions) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(appFunctions, "appFunctions");
        this.packageName = packageName;
        this.appFunctions = appFunctions;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual(AppFunctionPackageMetadata.class, obj != null ? obj.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type androidx.appfunctions.metadata.AppFunctionPackageMetadata");
        AppFunctionPackageMetadata appFunctionPackageMetadata = (AppFunctionPackageMetadata) obj;
        return Intrinsics.areEqual(this.packageName, appFunctionPackageMetadata.packageName) && Intrinsics.areEqual(this.appFunctions, appFunctionPackageMetadata.appFunctions);
    }

    public int hashCode() {
        return (this.packageName.hashCode() * 31) + this.appFunctions.hashCode();
    }

    public String toString() {
        return "AppFunctionPackageMetadata(packageName='" + this.packageName + "', appFunctions=" + this.appFunctions + ')';
    }
}
