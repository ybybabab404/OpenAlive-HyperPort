package androidx.appfunctions.metadata;

import androidx.annotation.RestrictTo;
import androidx.appsearch.app.DocumentClassFactory;
import androidx.appsearch.app.DocumentClassMappingContext;
import androidx.appsearch.app.GenericDocument;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: androidx.appfunctions.metadata.$$__AppSearch__AppFunctionDataTypeMetadataDocument, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public final class C$$__AppSearch__AppFunctionDataTypeMetadataDocument implements DocumentClassFactory<AppFunctionDataTypeMetadataDocument> {
    @Override // androidx.appsearch.app.DocumentClassFactory
    public AppFunctionDataTypeMetadataDocument fromGenericDocument(GenericDocument genericDocument, DocumentClassMappingContext documentClassMappingContext) {
        String namespace = genericDocument.getNamespace();
        String id = genericDocument.getId();
        int propertyLong = (int) genericDocument.getPropertyLong("type");
        GenericDocument propertyDocument = genericDocument.getPropertyDocument("itemType");
        String str = null;
        AppFunctionDataTypeMetadataDocument appFunctionDataTypeMetadataDocument = propertyDocument != null ? (AppFunctionDataTypeMetadataDocument) propertyDocument.toDocumentClass(AppFunctionDataTypeMetadataDocument.class, documentClassMappingContext) : null;
        GenericDocument[] propertyDocumentArray = genericDocument.getPropertyDocumentArray("properties");
        List arrayList = Collections.EMPTY_LIST;
        if (propertyDocumentArray != null) {
            arrayList = new ArrayList(propertyDocumentArray.length);
            for (GenericDocument genericDocument2 : propertyDocumentArray) {
                arrayList.add((AppFunctionNamedDataTypeMetadataDocument) genericDocument2.toDocumentClass(AppFunctionNamedDataTypeMetadataDocument.class, documentClassMappingContext));
            }
        }
        List list = arrayList;
        GenericDocument[] propertyDocumentArray2 = genericDocument.getPropertyDocumentArray("allOf");
        List arrayList2 = Collections.EMPTY_LIST;
        if (propertyDocumentArray2 != null) {
            arrayList2 = new ArrayList(propertyDocumentArray2.length);
            for (GenericDocument genericDocument3 : propertyDocumentArray2) {
                arrayList2.add((AppFunctionDataTypeMetadataDocument) genericDocument3.toDocumentClass(AppFunctionDataTypeMetadataDocument.class, documentClassMappingContext));
            }
        }
        List list2 = arrayList2;
        String[] propertyStringArray = genericDocument.getPropertyStringArray("required");
        List listAsList = Collections.EMPTY_LIST;
        List listAsList2 = propertyStringArray != null ? Arrays.asList(propertyStringArray) : listAsList;
        String[] propertyStringArray2 = genericDocument.getPropertyStringArray("dataTypeReference");
        String str2 = (propertyStringArray2 == null || propertyStringArray2.length == 0) ? null : propertyStringArray2[0];
        boolean propertyBoolean = genericDocument.getPropertyBoolean("isNullable");
        String[] propertyStringArray3 = genericDocument.getPropertyStringArray("objectQualifiedName");
        String str3 = (propertyStringArray3 == null || propertyStringArray3.length == 0) ? null : propertyStringArray3[0];
        String[] propertyStringArray4 = genericDocument.getPropertyStringArray("description");
        if (propertyStringArray4 != null && propertyStringArray4.length != 0) {
            str = propertyStringArray4[0];
        }
        String str4 = str;
        String[] propertyStringArray5 = genericDocument.getPropertyStringArray("enumValues");
        if (propertyStringArray5 != null) {
            listAsList = Arrays.asList(propertyStringArray5);
        }
        return new AppFunctionDataTypeMetadataDocument(namespace, id, propertyLong, appFunctionDataTypeMetadataDocument, list, list2, listAsList2, str2, propertyBoolean, str3, str4, listAsList);
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public String getSchemaName() {
        return "AppFunctionDataTypeMetadataDocument";
    }
}
