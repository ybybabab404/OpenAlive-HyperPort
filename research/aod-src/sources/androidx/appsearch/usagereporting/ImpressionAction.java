package androidx.appsearch.usagereporting;

import androidx.appsearch.annotation.Document;
import androidx.appsearch.app.ExperimentalAppSearchApi;

/* JADX INFO: loaded from: classes.dex */
@Document
@ExperimentalAppSearchApi
public class ImpressionAction extends TakenAction {
    public final String mQuery;
    public final String mReferencedQualifiedId;
    public final int mResultRankGlobal;
    public final int mResultRankInBlock;

    @Document.BuilderProducer
    public static final class Builder extends TakenAction.BuilderImpl<Builder> {
        public String mQuery;
        public String mReferencedQualifiedId;
        public int mResultRankGlobal;
        public int mResultRankInBlock;

        public Builder(String str, String str2, long j, int i) {
            super(str, str2, j, i);
            this.mResultRankInBlock = -1;
            this.mResultRankGlobal = -1;
        }

        @Override // androidx.appsearch.usagereporting.TakenAction.BuilderImpl
        public ImpressionAction build() {
            return new ImpressionAction(this.mNamespace, this.mId, this.mDocumentTtlMillis, this.mActionTimestampMillis, this.mActionType, this.mQuery, this.mReferencedQualifiedId, this.mResultRankInBlock, this.mResultRankGlobal);
        }

        @Override // androidx.appsearch.usagereporting.TakenAction.BuilderImpl
        public /* bridge */ /* synthetic */ TakenAction.BuilderImpl setDocumentTtlMillis(long j) {
            return super.setDocumentTtlMillis(j);
        }

        public Builder setQuery(String str) {
            this.mQuery = str;
            return this;
        }

        public Builder setReferencedQualifiedId(String str) {
            this.mReferencedQualifiedId = str;
            return this;
        }

        public Builder setResultRankGlobal(int i) {
            this.mResultRankGlobal = i;
            return this;
        }

        public Builder setResultRankInBlock(int i) {
            this.mResultRankInBlock = i;
            return this;
        }
    }

    public ImpressionAction(String str, String str2, long j, long j2, int i, String str3, String str4, int i2, int i3) {
        super(str, str2, j, j2, i);
        this.mQuery = str3;
        this.mReferencedQualifiedId = str4;
        this.mResultRankInBlock = i2;
        this.mResultRankGlobal = i3;
    }
}
