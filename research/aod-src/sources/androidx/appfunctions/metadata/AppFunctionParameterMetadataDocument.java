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
/* JADX INFO: compiled from: AppFunctionParameterMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Document
@Metadata
public final class AppFunctionParameterMetadataDocument {
    public final AppFunctionDataTypeMetadataDocument dataTypeMetadata;
    public final String description;
    public final String id;
    public final boolean isRequired;
    public final String name;
    public final String namespace;

    public AppFunctionParameterMetadataDocument(String namespace, String id, String name, boolean z, AppFunctionDataTypeMetadataDocument dataTypeMetadata, String str) {
        Intrinsics.checkNotNullParameter(namespace, "namespace");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(dataTypeMetadata, "dataTypeMetadata");
        this.namespace = namespace;
        this.id = id;
        this.name = name;
        this.isRequired = z;
        this.dataTypeMetadata = dataTypeMetadata;
        this.description = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppFunctionParameterMetadataDocument)) {
            return false;
        }
        AppFunctionParameterMetadataDocument appFunctionParameterMetadataDocument = (AppFunctionParameterMetadataDocument) obj;
        return Intrinsics.areEqual(this.namespace, appFunctionParameterMetadataDocument.namespace) && Intrinsics.areEqual(this.id, appFunctionParameterMetadataDocument.id) && Intrinsics.areEqual(this.name, appFunctionParameterMetadataDocument.name) && this.isRequired == appFunctionParameterMetadataDocument.isRequired && Intrinsics.areEqual(this.dataTypeMetadata, appFunctionParameterMetadataDocument.dataTypeMetadata) && Intrinsics.areEqual(this.description, appFunctionParameterMetadataDocument.description);
    }

    public int hashCode() {
        int iHashCode = ((((((((this.namespace.hashCode() * 31) + this.id.hashCode()) * 31) + this.name.hashCode()) * 31) + Boolean.hashCode(this.isRequired)) * 31) + this.dataTypeMetadata.hashCode()) * 31;
        String str = this.description;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    public final AppFunctionParameterMetadata toAppFunctionParameterMetadata() {
        String str = this.name;
        boolean z = this.isRequired;
        AppFunctionDataTypeMetadata appFunctionDataTypeMetadata = this.dataTypeMetadata.toAppFunctionDataTypeMetadata();
        String str2 = this.description;
        if (str2 == null) {
            str2 = "";
        }
        return new AppFunctionParameterMetadata(str, z, appFunctionDataTypeMetadata, str2);
    }

    public String toString() {
        return "AppFunctionParameterMetadataDocument(namespace=" + this.namespace + ", id=" + this.id + ", name=" + this.name + ", isRequired=" + this.isRequired + ", dataTypeMetadata=" + this.dataTypeMetadata + ", description=" + this.description + ')';
    }
}
