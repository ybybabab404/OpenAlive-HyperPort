package androidx.appfunctions.metadata;

import androidx.appsearch.annotation.Document;
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
/* JADX INFO: compiled from: AppFunctionDataTypeMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Document
@Metadata
public final class AppFunctionNamedDataTypeMetadataDocument {
    public final AppFunctionDataTypeMetadataDocument dataTypeMetadata;
    public final String id;
    public final String name;
    public final String namespace;

    public AppFunctionNamedDataTypeMetadataDocument(String namespace, String id, String name, AppFunctionDataTypeMetadataDocument dataTypeMetadata) {
        Intrinsics.checkNotNullParameter(namespace, "namespace");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(dataTypeMetadata, "dataTypeMetadata");
        this.namespace = namespace;
        this.id = id;
        this.name = name;
        this.dataTypeMetadata = dataTypeMetadata;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppFunctionNamedDataTypeMetadataDocument)) {
            return false;
        }
        AppFunctionNamedDataTypeMetadataDocument appFunctionNamedDataTypeMetadataDocument = (AppFunctionNamedDataTypeMetadataDocument) obj;
        return Intrinsics.areEqual(this.namespace, appFunctionNamedDataTypeMetadataDocument.namespace) && Intrinsics.areEqual(this.id, appFunctionNamedDataTypeMetadataDocument.id) && Intrinsics.areEqual(this.name, appFunctionNamedDataTypeMetadataDocument.name) && Intrinsics.areEqual(this.dataTypeMetadata, appFunctionNamedDataTypeMetadataDocument.dataTypeMetadata);
    }

    public final AppFunctionDataTypeMetadataDocument getDataTypeMetadata() {
        return this.dataTypeMetadata;
    }

    public final String getName() {
        return this.name;
    }

    public int hashCode() {
        return (((((this.namespace.hashCode() * 31) + this.id.hashCode()) * 31) + this.name.hashCode()) * 31) + this.dataTypeMetadata.hashCode();
    }

    public String toString() {
        return "AppFunctionNamedDataTypeMetadataDocument(namespace=" + this.namespace + ", id=" + this.id + ", name=" + this.name + ", dataTypeMetadata=" + this.dataTypeMetadata + ')';
    }
}
