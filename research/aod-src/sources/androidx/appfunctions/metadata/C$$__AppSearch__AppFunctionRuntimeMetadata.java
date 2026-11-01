package androidx.appfunctions.metadata;

import androidx.annotation.RestrictTo;
import androidx.appsearch.app.DocumentClassFactory;
import androidx.appsearch.app.DocumentClassMappingContext;
import androidx.appsearch.app.GenericDocument;

/* JADX INFO: renamed from: androidx.appfunctions.metadata.$$__AppSearch__AppFunctionRuntimeMetadata, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public final class C$$__AppSearch__AppFunctionRuntimeMetadata implements DocumentClassFactory<AppFunctionRuntimeMetadata> {
    @Override // androidx.appsearch.app.DocumentClassFactory
    public AppFunctionRuntimeMetadata fromGenericDocument(GenericDocument genericDocument, DocumentClassMappingContext documentClassMappingContext) {
        String id = genericDocument.getId();
        String namespace = genericDocument.getNamespace();
        String[] propertyStringArray = genericDocument.getPropertyStringArray("functionId");
        String str = null;
        String str2 = (propertyStringArray == null || propertyStringArray.length == 0) ? null : propertyStringArray[0];
        String[] propertyStringArray2 = genericDocument.getPropertyStringArray("packageName");
        String str3 = (propertyStringArray2 == null || propertyStringArray2.length == 0) ? null : propertyStringArray2[0];
        long propertyLong = genericDocument.getPropertyLong("enabled");
        String[] propertyStringArray3 = genericDocument.getPropertyStringArray("appFunctionStaticMetadataQualifiedId");
        if (propertyStringArray3 != null && propertyStringArray3.length != 0) {
            str = propertyStringArray3[0];
        }
        return new AppFunctionRuntimeMetadata(id, namespace, str2, str3, propertyLong, str);
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public String getSchemaName() {
        return "AppFunctionRuntimeMetadata";
    }
}
