package androidx.appsearch.usagereporting;

import androidx.appsearch.app.DocumentClassFactory;
import androidx.appsearch.app.DocumentClassMappingContext;
import androidx.appsearch.app.GenericDocument;

/* JADX INFO: renamed from: androidx.appsearch.usagereporting.$$__AppSearch__TakenAction, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public final class C$$__AppSearch__TakenAction implements DocumentClassFactory<TakenAction> {
    @Override // androidx.appsearch.app.DocumentClassFactory
    public TakenAction fromGenericDocument(GenericDocument genericDocument, DocumentClassMappingContext documentClassMappingContext) {
        String namespace = genericDocument.getNamespace();
        String id = genericDocument.getId();
        long ttlMillis = genericDocument.getTtlMillis();
        TakenAction.Builder builder = new TakenAction.Builder(namespace, id, genericDocument.getCreationTimestampMillis(), (int) genericDocument.getPropertyLong("actionType"));
        builder.setDocumentTtlMillis(ttlMillis);
        return builder.build();
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public String getSchemaName() {
        return "builtin:TakenAction";
    }
}
