package androidx.appfunctions.metadata;

import kotlin.Metadata;
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
/* JADX INFO: compiled from: AppFunctionSchemaMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class AppFunctionSchemaMetadata {
    public final String category;
    public final String name;
    public final long version;

    public AppFunctionSchemaMetadata(String category, String name, long j) {
        Intrinsics.checkNotNullParameter(category, "category");
        Intrinsics.checkNotNullParameter(name, "name");
        this.category = category;
        this.name = name;
        this.version = j;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual(AppFunctionSchemaMetadata.class, obj != null ? obj.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type androidx.appfunctions.metadata.AppFunctionSchemaMetadata");
        AppFunctionSchemaMetadata appFunctionSchemaMetadata = (AppFunctionSchemaMetadata) obj;
        return this.version == appFunctionSchemaMetadata.version && Intrinsics.areEqual(this.category, appFunctionSchemaMetadata.category) && Intrinsics.areEqual(this.name, appFunctionSchemaMetadata.name);
    }

    public final long getVersion() {
        return this.version;
    }

    public int hashCode() {
        return (((Long.hashCode(this.version) * 31) + this.category.hashCode()) * 31) + this.name.hashCode();
    }

    public String toString() {
        return "AppFunctionSchemaMetadata(category='" + this.category + "', name='" + this.name + "', version=" + this.version + ')';
    }
}
