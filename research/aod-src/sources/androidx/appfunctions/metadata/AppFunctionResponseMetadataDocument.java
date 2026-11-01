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
/* JADX INFO: compiled from: AppFunctionResponseMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Document
@Metadata
public final class AppFunctionResponseMetadataDocument {
    public final String description;
    public final String id;
    public final String namespace;
    public final AppFunctionDataTypeMetadataDocument valueType;

    public AppFunctionResponseMetadataDocument(String namespace, String id, AppFunctionDataTypeMetadataDocument valueType, String str) {
        Intrinsics.checkNotNullParameter(namespace, "namespace");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(valueType, "valueType");
        this.namespace = namespace;
        this.id = id;
        this.valueType = valueType;
        this.description = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppFunctionResponseMetadataDocument)) {
            return false;
        }
        AppFunctionResponseMetadataDocument appFunctionResponseMetadataDocument = (AppFunctionResponseMetadataDocument) obj;
        return Intrinsics.areEqual(this.namespace, appFunctionResponseMetadataDocument.namespace) && Intrinsics.areEqual(this.id, appFunctionResponseMetadataDocument.id) && Intrinsics.areEqual(this.valueType, appFunctionResponseMetadataDocument.valueType) && Intrinsics.areEqual(this.description, appFunctionResponseMetadataDocument.description);
    }

    public int hashCode() {
        int iHashCode = ((((this.namespace.hashCode() * 31) + this.id.hashCode()) * 31) + this.valueType.hashCode()) * 31;
        String str = this.description;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    public final AppFunctionResponseMetadata toAppFunctionResponseMetadata() {
        AppFunctionDataTypeMetadata appFunctionDataTypeMetadata = this.valueType.toAppFunctionDataTypeMetadata();
        String str = this.description;
        if (str == null) {
            str = "";
        }
        return new AppFunctionResponseMetadata(appFunctionDataTypeMetadata, str);
    }

    public String toString() {
        return "AppFunctionResponseMetadataDocument(namespace=" + this.namespace + ", id=" + this.id + ", valueType=" + this.valueType + ", description=" + this.description + ')';
    }
}
