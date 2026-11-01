package androidx.appsearch.usagereporting;

import androidx.annotation.RestrictTo;
import androidx.appsearch.annotation.Document;
import androidx.appsearch.app.ExperimentalAppSearchApi;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
@Document
@ExperimentalAppSearchApi
public abstract class TakenAction {
    public final long mActionTimestampMillis;
    public final int mActionType;
    public final long mDocumentTtlMillis;
    public final String mId;
    public final String mNamespace;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface ActionType {
    }

    @Document.BuilderProducer
    public static final class Builder extends BuilderImpl<Builder> {
        public Builder(String str, String str2, long j, int i) {
            super(str, str2, j, i);
        }
    }

    public static class BuilderImpl<T extends BuilderImpl<T>> {
        public long mActionTimestampMillis;
        public int mActionType;
        public long mDocumentTtlMillis = 5184000000L;
        public final String mId;
        public final String mNamespace;

        public BuilderImpl(String str, String str2, long j, int i) {
            this.mNamespace = (String) Preconditions.checkNotNull(str);
            this.mId = (String) Preconditions.checkNotNull(str2);
            this.mActionTimestampMillis = j;
            this.mActionType = i;
        }

        public TakenAction build() {
            throw new UnsupportedOperationException();
        }

        public BuilderImpl setDocumentTtlMillis(long j) {
            this.mDocumentTtlMillis = j;
            return this;
        }
    }

    public TakenAction(String str, String str2, long j, long j2, int i) {
        this.mNamespace = (String) Preconditions.checkNotNull(str);
        this.mId = (String) Preconditions.checkNotNull(str2);
        this.mDocumentTtlMillis = j;
        this.mActionTimestampMillis = j2;
        this.mActionType = i;
    }
}
