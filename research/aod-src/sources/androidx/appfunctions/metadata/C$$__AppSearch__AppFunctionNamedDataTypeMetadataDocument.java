package androidx.appfunctions.metadata;

import androidx.annotation.RestrictTo;
import androidx.appsearch.app.DocumentClassFactory;
import androidx.appsearch.app.DocumentClassMappingContext;
import androidx.appsearch.app.GenericDocument;
import com.xiaomi.onetrack.api.bf;

/* JADX INFO: renamed from: androidx.appfunctions.metadata.$$__AppSearch__AppFunctionNamedDataTypeMetadataDocument, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public final class C$$__AppSearch__AppFunctionNamedDataTypeMetadataDocument implements DocumentClassFactory<AppFunctionNamedDataTypeMetadataDocument> {
    @Override // androidx.appsearch.app.DocumentClassFactory
    public AppFunctionNamedDataTypeMetadataDocument fromGenericDocument(GenericDocument genericDocument, DocumentClassMappingContext documentClassMappingContext) {
        String namespace = genericDocument.getNamespace();
        String id = genericDocument.getId();
        String[] propertyStringArray = genericDocument.getPropertyStringArray(bf.a);
        String str = (propertyStringArray == null || propertyStringArray.length == 0) ? null : propertyStringArray[0];
        GenericDocument propertyDocument = genericDocument.getPropertyDocument("dataTypeMetadata");
        return new AppFunctionNamedDataTypeMetadataDocument(namespace, id, str, propertyDocument != null ? (AppFunctionDataTypeMetadataDocument) propertyDocument.toDocumentClass(AppFunctionDataTypeMetadataDocument.class, documentClassMappingContext) : null);
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public String getSchemaName() {
        return "AppFunctionNamedDataTypeMetadataDocument";
    }
}
