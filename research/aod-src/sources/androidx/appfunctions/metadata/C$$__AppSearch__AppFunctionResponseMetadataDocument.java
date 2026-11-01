package androidx.appfunctions.metadata;

import androidx.annotation.RestrictTo;
import androidx.appsearch.app.DocumentClassFactory;
import androidx.appsearch.app.DocumentClassMappingContext;
import androidx.appsearch.app.GenericDocument;

/* JADX INFO: renamed from: androidx.appfunctions.metadata.$$__AppSearch__AppFunctionResponseMetadataDocument, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public final class C$$__AppSearch__AppFunctionResponseMetadataDocument implements DocumentClassFactory<AppFunctionResponseMetadataDocument> {
    @Override // androidx.appsearch.app.DocumentClassFactory
    public AppFunctionResponseMetadataDocument fromGenericDocument(GenericDocument genericDocument, DocumentClassMappingContext documentClassMappingContext) {
        String namespace = genericDocument.getNamespace();
        String id = genericDocument.getId();
        GenericDocument propertyDocument = genericDocument.getPropertyDocument("valueType");
        String str = null;
        AppFunctionDataTypeMetadataDocument appFunctionDataTypeMetadataDocument = propertyDocument != null ? (AppFunctionDataTypeMetadataDocument) propertyDocument.toDocumentClass(AppFunctionDataTypeMetadataDocument.class, documentClassMappingContext) : null;
        String[] propertyStringArray = genericDocument.getPropertyStringArray("description");
        if (propertyStringArray != null && propertyStringArray.length != 0) {
            str = propertyStringArray[0];
        }
        return new AppFunctionResponseMetadataDocument(namespace, id, appFunctionDataTypeMetadataDocument, str);
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public String getSchemaName() {
        return "AppFunctionResponseMetadataDocument";
    }
}
