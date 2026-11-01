package androidx.appfunctions.metadata;

import androidx.appsearch.annotation.Document;
import java.util.List;
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
/* JADX INFO: compiled from: AppFunctionMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Document
@Metadata
public final class AppFunctionMetadataDocument {
    public final String description;
    public final String id;
    public final boolean isEnabledByDefault;
    public final String namespace;
    public final List parameters;
    public final AppFunctionResponseMetadataDocument response;
    public final String schemaCategory;
    public final String schemaName;
    public final Long schemaVersion;

    public AppFunctionMetadataDocument(String namespace, String id, boolean z, String str, String str2, Long l, List list, AppFunctionResponseMetadataDocument appFunctionResponseMetadataDocument, String str3) {
        Intrinsics.checkNotNullParameter(namespace, "namespace");
        Intrinsics.checkNotNullParameter(id, "id");
        this.namespace = namespace;
        this.id = id;
        this.isEnabledByDefault = z;
        this.schemaCategory = str;
        this.schemaName = str2;
        this.schemaVersion = l;
        this.parameters = list;
        this.response = appFunctionResponseMetadataDocument;
        this.description = str3;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppFunctionMetadataDocument)) {
            return false;
        }
        AppFunctionMetadataDocument appFunctionMetadataDocument = (AppFunctionMetadataDocument) obj;
        return Intrinsics.areEqual(this.namespace, appFunctionMetadataDocument.namespace) && Intrinsics.areEqual(this.id, appFunctionMetadataDocument.id) && this.isEnabledByDefault == appFunctionMetadataDocument.isEnabledByDefault && Intrinsics.areEqual(this.schemaCategory, appFunctionMetadataDocument.schemaCategory) && Intrinsics.areEqual(this.schemaName, appFunctionMetadataDocument.schemaName) && Intrinsics.areEqual(this.schemaVersion, appFunctionMetadataDocument.schemaVersion) && Intrinsics.areEqual(this.parameters, appFunctionMetadataDocument.parameters) && Intrinsics.areEqual(this.response, appFunctionMetadataDocument.response) && Intrinsics.areEqual(this.description, appFunctionMetadataDocument.description);
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getId() {
        return this.id;
    }

    public final List getParameters() {
        return this.parameters;
    }

    public final AppFunctionResponseMetadataDocument getResponse() {
        return this.response;
    }

    public int hashCode() {
        int iHashCode = ((((this.namespace.hashCode() * 31) + this.id.hashCode()) * 31) + Boolean.hashCode(this.isEnabledByDefault)) * 31;
        String str = this.schemaCategory;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.schemaName;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        Long l = this.schemaVersion;
        int iHashCode4 = (iHashCode3 + (l == null ? 0 : l.hashCode())) * 31;
        List list = this.parameters;
        int iHashCode5 = (iHashCode4 + (list == null ? 0 : list.hashCode())) * 31;
        AppFunctionResponseMetadataDocument appFunctionResponseMetadataDocument = this.response;
        int iHashCode6 = (iHashCode5 + (appFunctionResponseMetadataDocument == null ? 0 : appFunctionResponseMetadataDocument.hashCode())) * 31;
        String str3 = this.description;
        return iHashCode6 + (str3 != null ? str3.hashCode() : 0);
    }

    public final boolean isEnabledByDefault() {
        return this.isEnabledByDefault;
    }

    public String toString() {
        return "AppFunctionMetadataDocument(namespace=" + this.namespace + ", id=" + this.id + ", isEnabledByDefault=" + this.isEnabledByDefault + ", schemaCategory=" + this.schemaCategory + ", schemaName=" + this.schemaName + ", schemaVersion=" + this.schemaVersion + ", parameters=" + this.parameters + ", response=" + this.response + ", description=" + this.description + ')';
    }
}
