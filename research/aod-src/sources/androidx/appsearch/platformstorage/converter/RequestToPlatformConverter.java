package androidx.appsearch.platformstorage.converter;

import android.app.appsearch.GenericDocument;
import android.app.appsearch.GetByDocumentIdRequest;
import android.app.appsearch.PutDocumentsRequest;
import android.app.appsearch.exceptions.AppSearchException;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.util.Preconditions;
import java.util.Collection;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@RestrictTo
public final class RequestToPlatformConverter {

    @RequiresApi
    public static class ApiHelperForV {
        @DoNotInline
        public static void addTakenActionGenericDocuments(PutDocumentsRequest.Builder builder, GenericDocument genericDocument) {
            try {
                builder.addTakenActionGenericDocuments(new GenericDocument[]{genericDocument});
            } catch (AppSearchException e) {
                throw new RuntimeException("Unexpected AppSearchException which should not be possible", e);
            }
        }
    }

    public static GetByDocumentIdRequest toPlatformGetByDocumentIdRequest(androidx.appsearch.app.GetByDocumentIdRequest getByDocumentIdRequest) {
        Preconditions.checkNotNull(getByDocumentIdRequest);
        GetByDocumentIdRequest.Builder builderAddIds = new GetByDocumentIdRequest.Builder(getByDocumentIdRequest.getNamespace()).addIds(getByDocumentIdRequest.getIds());
        for (Map.Entry entry : getByDocumentIdRequest.getProjections().entrySet()) {
            builderAddIds.addProjection((String) entry.getKey(), (Collection) entry.getValue());
        }
        return builderAddIds.build();
    }
}
