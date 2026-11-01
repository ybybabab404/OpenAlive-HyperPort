package androidx.appsearch.app;

import android.os.Parcelable;
import androidx.annotation.RestrictTo;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.SafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
@SafeParcelable.Class
public final class JoinSpec extends AbstractSafeParcelable {
    public static final Parcelable.Creator CREATOR = new StubCreators.JoinSpecCreator();
    public final int mAggregationScoringStrategy;
    public final String mChildPropertyExpression;
    public final int mMaxJoinedResultCount;
    public final String mNestedQuery;
    public final SearchSpec mNestedSearchSpec;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface AggregationScoringStrategy {
    }

    public static final class Builder {
        public static final SearchSpec EMPTY_SEARCH_SPEC = new SearchSpec.Builder().build();
        public String mChildPropertyExpression;
        public String mNestedQuery = "";
        public SearchSpec mNestedSearchSpec = EMPTY_SEARCH_SPEC;
        public int mMaxJoinedResultCount = 10;
        public int mAggregationScoringStrategy = 0;

        public Builder(String str) {
            Preconditions.checkNotNull(str);
            this.mChildPropertyExpression = str;
        }

        public JoinSpec build() {
            return new JoinSpec(this.mNestedQuery, this.mNestedSearchSpec, this.mChildPropertyExpression, this.mMaxJoinedResultCount, this.mAggregationScoringStrategy);
        }

        public Builder setNestedSearch(String str, SearchSpec searchSpec) {
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(searchSpec);
            this.mNestedQuery = str;
            this.mNestedSearchSpec = searchSpec;
            return this;
        }
    }

    public JoinSpec(String str, SearchSpec searchSpec, String str2, int i, int i2) {
        Objects.requireNonNull(str);
        this.mNestedQuery = str;
        Objects.requireNonNull(searchSpec);
        this.mNestedSearchSpec = searchSpec;
        Objects.requireNonNull(str2);
        this.mChildPropertyExpression = str2;
        this.mMaxJoinedResultCount = i;
        this.mAggregationScoringStrategy = i2;
    }

    public int getAggregationScoringStrategy() {
        return this.mAggregationScoringStrategy;
    }

    public String getChildPropertyExpression() {
        return this.mChildPropertyExpression;
    }

    public int getMaxJoinedResultCount() {
        return this.mMaxJoinedResultCount;
    }

    public String getNestedQuery() {
        return this.mNestedQuery;
    }

    public SearchSpec getNestedSearchSpec() {
        return this.mNestedSearchSpec;
    }
}
