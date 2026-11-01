package androidx.appsearch.usagereporting;

import androidx.appsearch.annotation.Document;
import androidx.appsearch.app.ExperimentalAppSearchApi;

/* JADX INFO: loaded from: classes.dex */
@Document
@ExperimentalAppSearchApi
public class SearchAction extends TakenAction {
    public final int mFetchedResultCount;
    public final String mQuery;

    @Document.BuilderProducer
    public static final class Builder extends TakenAction.BuilderImpl<Builder> {
        public int mFetchedResultCount;
        public String mQuery;

        public Builder(String str, String str2, long j, int i) {
            super(str, str2, j, i);
            this.mFetchedResultCount = -1;
        }

        @Override // androidx.appsearch.usagereporting.TakenAction.BuilderImpl
        public SearchAction build() {
            return new SearchAction(this.mNamespace, this.mId, this.mDocumentTtlMillis, this.mActionTimestampMillis, this.mActionType, this.mQuery, this.mFetchedResultCount);
        }

        @Override // androidx.appsearch.usagereporting.TakenAction.BuilderImpl
        public /* bridge */ /* synthetic */ TakenAction.BuilderImpl setDocumentTtlMillis(long j) {
            return super.setDocumentTtlMillis(j);
        }

        public Builder setFetchedResultCount(int i) {
            this.mFetchedResultCount = i;
            return this;
        }

        public Builder setQuery(String str) {
            this.mQuery = str;
            return this;
        }
    }

    public SearchAction(String str, String str2, long j, long j2, int i, String str3, int i2) {
        super(str, str2, j, j2, i);
        this.mQuery = str3;
        this.mFetchedResultCount = i2;
    }
}
