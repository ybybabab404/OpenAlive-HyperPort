package androidx.appsearch.app;

import java.util.Collections;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class DocumentClassMappingContext {
    public static final DocumentClassMappingContext EMPTY = new DocumentClassMappingContext(null, null);
    public final Map mDocumentClassMap;
    public final Map mParentTypeMap;

    public DocumentClassMappingContext(Map map, Map map2) {
        this.mDocumentClassMap = map != null ? Collections.unmodifiableMap(map) : Collections.EMPTY_MAP;
        this.mParentTypeMap = map2 != null ? Collections.unmodifiableMap(map2) : Collections.EMPTY_MAP;
    }

    public Map getDocumentClassMap() {
        return this.mDocumentClassMap;
    }

    public Map getParentTypeMap() {
        return this.mParentTypeMap;
    }
}
