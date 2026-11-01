package androidx.appfunctions.metadata;

import androidx.appsearch.annotation.Document;
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
/* JADX INFO: compiled from: AppFunctionRuntimeMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Document
@Metadata
public final class AppFunctionRuntimeMetadata {
    public static final Companion Companion = new Companion(null);
    public final String appFunctionStaticMetadataQualifiedId;
    public final long enabled;
    public final String functionId;
    public final String id;
    public final String namespace;
    public final String packageName;

    /* JADX INFO: compiled from: AppFunctionRuntimeMetadata.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public AppFunctionRuntimeMetadata(String id, String namespace, String functionId, String packageName, long j, String appFunctionStaticMetadataQualifiedId) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(namespace, "namespace");
        Intrinsics.checkNotNullParameter(functionId, "functionId");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(appFunctionStaticMetadataQualifiedId, "appFunctionStaticMetadataQualifiedId");
        this.id = id;
        this.namespace = namespace;
        this.functionId = functionId;
        this.packageName = packageName;
        this.enabled = j;
        this.appFunctionStaticMetadataQualifiedId = appFunctionStaticMetadataQualifiedId;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppFunctionRuntimeMetadata)) {
            return false;
        }
        AppFunctionRuntimeMetadata appFunctionRuntimeMetadata = (AppFunctionRuntimeMetadata) obj;
        return Intrinsics.areEqual(this.id, appFunctionRuntimeMetadata.id) && Intrinsics.areEqual(this.namespace, appFunctionRuntimeMetadata.namespace) && Intrinsics.areEqual(this.functionId, appFunctionRuntimeMetadata.functionId) && Intrinsics.areEqual(this.packageName, appFunctionRuntimeMetadata.packageName) && this.enabled == appFunctionRuntimeMetadata.enabled && Intrinsics.areEqual(this.appFunctionStaticMetadataQualifiedId, appFunctionRuntimeMetadata.appFunctionStaticMetadataQualifiedId);
    }

    public final long getEnabled() {
        return this.enabled;
    }

    public int hashCode() {
        return (((((((((this.id.hashCode() * 31) + this.namespace.hashCode()) * 31) + this.functionId.hashCode()) * 31) + this.packageName.hashCode()) * 31) + Long.hashCode(this.enabled)) * 31) + this.appFunctionStaticMetadataQualifiedId.hashCode();
    }

    public String toString() {
        return "AppFunctionRuntimeMetadata(id=" + this.id + ", namespace=" + this.namespace + ", functionId=" + this.functionId + ", packageName=" + this.packageName + ", enabled=" + this.enabled + ", appFunctionStaticMetadataQualifiedId=" + this.appFunctionStaticMetadataQualifiedId + ')';
    }
}
