package androidx.appsearch.app;

import android.os.Parcelable;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.SafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;

/* JADX INFO: loaded from: classes.dex */
@SafeParcelable.Class
public final class SetSchemaResponse extends AbstractSafeParcelable {
    public static final Parcelable.Creator CREATOR = new StubCreators.SetSchemaResponseCreator();

    public static final class Builder {
    }

    @SafeParcelable.Class
    public static class MigrationFailure extends AbstractSafeParcelable {
        public static final Parcelable.Creator CREATOR = new StubCreators.MigrationFailureCreator();
        public final String mDocumentId;
        public final String mErrorMessage;
        public final String mNamespace;
        public final int mResultCode;
        public final String mSchemaType;

        public AppSearchResult getAppSearchResult() {
            return AppSearchResult.newFailedResult(this.mResultCode, this.mErrorMessage);
        }

        public String getDocumentId() {
            return this.mDocumentId;
        }

        public String getNamespace() {
            return this.mNamespace;
        }

        public String getSchemaType() {
            return this.mSchemaType;
        }

        public String toString() {
            return "MigrationFailure { schemaType: " + getSchemaType() + ", namespace: " + getNamespace() + ", documentId: " + getDocumentId() + ", appSearchResult: " + getAppSearchResult().toString() + "}";
        }
    }
}
