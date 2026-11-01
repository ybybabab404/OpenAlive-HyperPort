package androidx.appfunctions.metadata;

import androidx.annotation.RestrictTo;
import androidx.appsearch.app.DocumentClassFactory;
import androidx.appsearch.app.DocumentClassMappingContext;
import androidx.appsearch.app.GenericDocument;
import java.util.ArrayList;

/* JADX INFO: renamed from: androidx.appfunctions.metadata.$$__AppSearch__AppFunctionMetadataDocument, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public final class C$$__AppSearch__AppFunctionMetadataDocument implements DocumentClassFactory<AppFunctionMetadataDocument> {
    @Override // androidx.appsearch.app.DocumentClassFactory
    public AppFunctionMetadataDocument fromGenericDocument(GenericDocument genericDocument, DocumentClassMappingContext documentClassMappingContext) {
        ArrayList arrayList;
        String namespace = genericDocument.getNamespace();
        String id = genericDocument.getId();
        boolean propertyBoolean = genericDocument.getPropertyBoolean("enabledByDefault");
        String[] propertyStringArray = genericDocument.getPropertyStringArray("schemaCategory");
        String str = null;
        String str2 = (propertyStringArray == null || propertyStringArray.length == 0) ? null : propertyStringArray[0];
        String[] propertyStringArray2 = genericDocument.getPropertyStringArray("schemaName");
        String str3 = (propertyStringArray2 == null || propertyStringArray2.length == 0) ? null : propertyStringArray2[0];
        long[] propertyLongArray = genericDocument.getPropertyLongArray("schemaVersion");
        Long lValueOf = (propertyLongArray == null || propertyLongArray.length == 0) ? null : Long.valueOf(propertyLongArray[0]);
        GenericDocument[] propertyDocumentArray = genericDocument.getPropertyDocumentArray("parameters");
        if (propertyDocumentArray != null) {
            ArrayList arrayList2 = new ArrayList(propertyDocumentArray.length);
            for (GenericDocument genericDocument2 : propertyDocumentArray) {
                arrayList2.add((AppFunctionParameterMetadataDocument) genericDocument2.toDocumentClass(AppFunctionParameterMetadataDocument.class, documentClassMappingContext));
            }
            arrayList = arrayList2;
        } else {
            arrayList = null;
        }
        GenericDocument propertyDocument = genericDocument.getPropertyDocument("response");
        AppFunctionResponseMetadataDocument appFunctionResponseMetadataDocument = propertyDocument != null ? (AppFunctionResponseMetadataDocument) propertyDocument.toDocumentClass(AppFunctionResponseMetadataDocument.class, documentClassMappingContext) : null;
        String[] propertyStringArray3 = genericDocument.getPropertyStringArray("description");
        if (propertyStringArray3 != null && propertyStringArray3.length != 0) {
            str = propertyStringArray3[0];
        }
        return new AppFunctionMetadataDocument(namespace, id, propertyBoolean, str2, str3, lValueOf, arrayList, appFunctionResponseMetadataDocument, str);
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public String getSchemaName() {
        return "AppFunctionStaticMetadata";
    }
}
