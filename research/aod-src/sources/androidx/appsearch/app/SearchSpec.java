package androidx.appsearch.app;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.RestrictTo;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.SafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.appsearch.util.BundleUtil;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
@SafeParcelable.Class
public final class SearchSpec extends AbstractSafeParcelable {
    public static final Parcelable.Creator CREATOR = new StubCreators.SearchSpecCreator();
    public final String mAdvancedRankingExpression;
    public final int mDefaultEmbeddingSearchMetricType;
    public final List mEmbeddingParameters;
    public final List mEnabledFeatures;
    public final List mFilterDocumentIds;
    public final int mGroupingLimit;
    public final List mInformationalRankingExpressions;
    public final JoinSpec mJoinSpec;
    public final int mMaxSnippetSize;
    public final List mNamespaces;
    public final int mOrder;
    public final List mPackageNames;
    public final Bundle mProjectionTypePropertyMasks;
    public final int mRankingStrategy;
    public final int mResultCountPerPage;
    public final int mResultGroupingTypeFlags;
    public final boolean mRetrieveEmbeddingMatchInfos;
    public final List mSchemas;
    public final String mSearchSourceLogTag;
    public final List mSearchStringParameters;
    public final int mSnippetCount;
    public final int mSnippetCountPerProperty;
    public final int mTermMatchType;
    public final Bundle mTypePropertyFilters;
    public final Bundle mTypePropertyWeightsField;

    public static final class Builder {
        public JoinSpec mJoinSpec;
        public String mSearchSourceLogTag;
        public List mSchemas = new ArrayList();
        public List mNamespaces = new ArrayList();
        public Bundle mTypePropertyFilters = new Bundle();
        public List mPackageNames = new ArrayList();
        public ArraySet mEnabledFeatures = new ArraySet();
        public Bundle mProjectionTypePropertyMasks = new Bundle();
        public Bundle mTypePropertyWeights = new Bundle();
        public List mEmbeddingParameters = new ArrayList();
        public List mSearchStringParameters = new ArrayList();
        public List mFilterDocumentIds = new ArrayList();
        public int mResultCountPerPage = 10;
        public int mTermMatchType = 2;
        public int mDefaultEmbeddingSearchMetricType = 1;
        public int mSnippetCount = 0;
        public int mSnippetCountPerProperty = 10000;
        public int mMaxSnippetSize = 0;
        public int mRankingStrategy = 0;
        public int mOrder = 0;
        public int mGroupingTypeFlags = 0;
        public int mGroupingLimit = 0;
        public String mAdvancedRankingExpression = "";
        public List mInformationalRankingExpressions = new ArrayList();
        public boolean mRetrieveEmbeddingMatchInfos = false;
        public boolean mBuilt = false;

        public Builder addFilterDocumentClasses(Collection collection) {
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            ArrayList arrayList = new ArrayList(collection.size());
            DocumentClassFactoryRegistry documentClassFactoryRegistry = DocumentClassFactoryRegistry.getInstance();
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                arrayList.add(documentClassFactoryRegistry.getOrCreateFactory((Class) it.next()).getSchemaName());
            }
            addFilterSchemas(arrayList);
            return this;
        }

        public Builder addFilterDocumentClasses(Class... clsArr) {
            Preconditions.checkNotNull(clsArr);
            resetIfBuilt();
            return addFilterDocumentClasses(Arrays.asList(clsArr));
        }

        public Builder addFilterNamespaces(Collection collection) {
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            this.mNamespaces.addAll(collection);
            return this;
        }

        public Builder addFilterNamespaces(String... strArr) {
            Preconditions.checkNotNull(strArr);
            resetIfBuilt();
            return addFilterNamespaces(Arrays.asList(strArr));
        }

        public Builder addFilterPackageNames(Collection collection) {
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            this.mPackageNames.addAll(collection);
            return this;
        }

        public Builder addFilterPackageNames(String... strArr) {
            Preconditions.checkNotNull(strArr);
            resetIfBuilt();
            return addFilterPackageNames(Arrays.asList(strArr));
        }

        public Builder addFilterSchemas(Collection collection) {
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            this.mSchemas.addAll(collection);
            return this;
        }

        public SearchSpec build() {
            int i;
            JoinSpec joinSpec = this.mJoinSpec;
            if (joinSpec != null) {
                if (this.mRankingStrategy != 8 && joinSpec.getAggregationScoringStrategy() != 0) {
                    throw new IllegalStateException("Aggregate scoring strategy has been set in the nested JoinSpec, but ranking strategy is not RANKING_STRATEGY_JOIN_AGGREGATE_SCORE");
                }
            } else if (this.mRankingStrategy == 8) {
                throw new IllegalStateException("Attempting to rank based on joined documents, but no JoinSpec provided");
            }
            if (!this.mTypePropertyWeights.isEmpty() && (i = this.mRankingStrategy) != 3 && i != 9) {
                throw new IllegalArgumentException("Property weights are only compatible with the RANKING_STRATEGY_RELEVANCE_SCORE and RANKING_STRATEGY_ADVANCED_RANKING_EXPRESSION ranking strategies.");
            }
            this.mBuilt = true;
            return new SearchSpec(this.mTermMatchType, this.mSchemas, this.mNamespaces, this.mTypePropertyFilters, this.mPackageNames, this.mResultCountPerPage, this.mRankingStrategy, this.mOrder, this.mSnippetCount, this.mSnippetCountPerProperty, this.mMaxSnippetSize, this.mProjectionTypePropertyMasks, this.mGroupingTypeFlags, this.mGroupingLimit, this.mTypePropertyWeights, this.mJoinSpec, this.mAdvancedRankingExpression, new ArrayList(this.mEnabledFeatures), this.mSearchSourceLogTag, this.mEmbeddingParameters, this.mDefaultEmbeddingSearchMetricType, this.mInformationalRankingExpressions, this.mSearchStringParameters, this.mFilterDocumentIds, this.mRetrieveEmbeddingMatchInfos);
        }

        public final void modifyEnabledFeature(String str, boolean z) {
            resetIfBuilt();
            if (z) {
                this.mEnabledFeatures.add(str);
            } else {
                this.mEnabledFeatures.remove(str);
            }
        }

        public final void resetIfBuilt() {
            if (this.mBuilt) {
                this.mSchemas = new ArrayList(this.mSchemas);
                this.mTypePropertyFilters = BundleUtil.deepCopy(this.mTypePropertyFilters);
                this.mNamespaces = new ArrayList(this.mNamespaces);
                this.mPackageNames = new ArrayList(this.mPackageNames);
                this.mProjectionTypePropertyMasks = BundleUtil.deepCopy(this.mProjectionTypePropertyMasks);
                this.mTypePropertyWeights = BundleUtil.deepCopy(this.mTypePropertyWeights);
                this.mEmbeddingParameters = new ArrayList(this.mEmbeddingParameters);
                this.mInformationalRankingExpressions = new ArrayList(this.mInformationalRankingExpressions);
                this.mSearchStringParameters = new ArrayList(this.mSearchStringParameters);
                this.mFilterDocumentIds = new ArrayList(this.mFilterDocumentIds);
                this.mBuilt = false;
            }
        }

        public Builder setJoinSpec(JoinSpec joinSpec) {
            resetIfBuilt();
            this.mJoinSpec = (JoinSpec) Preconditions.checkNotNull(joinSpec);
            return this;
        }

        public Builder setListFilterQueryLanguageEnabled(boolean z) {
            modifyEnabledFeature("LIST_FILTER_QUERY_LANGUAGE", z);
            return this;
        }

        public Builder setNumericSearchEnabled(boolean z) {
            modifyEnabledFeature("NUMERIC_SEARCH", z);
            return this;
        }

        public Builder setVerbatimSearchEnabled(boolean z) {
            modifyEnabledFeature("VERBATIM_SEARCH", z);
            return this;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface EmbeddingSearchMetricType {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface GroupingType {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface Order {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface RankingStrategy {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface TermMatch {
    }

    public SearchSpec(int i, List list, List list2, Bundle bundle, List list3, int i2, int i3, int i4, int i5, int i6, int i7, Bundle bundle2, int i8, int i9, Bundle bundle3, JoinSpec joinSpec, String str, List list4, String str2, List list5, int i10, List list6, List list7, List list8, boolean z) {
        this.mTermMatchType = i;
        this.mSchemas = Collections.unmodifiableList((List) Preconditions.checkNotNull(list));
        this.mNamespaces = Collections.unmodifiableList((List) Preconditions.checkNotNull(list2));
        this.mTypePropertyFilters = (Bundle) Preconditions.checkNotNull(bundle);
        this.mPackageNames = Collections.unmodifiableList((List) Preconditions.checkNotNull(list3));
        this.mResultCountPerPage = i2;
        this.mRankingStrategy = i3;
        this.mOrder = i4;
        this.mSnippetCount = i5;
        this.mSnippetCountPerProperty = i6;
        this.mMaxSnippetSize = i7;
        this.mProjectionTypePropertyMasks = (Bundle) Preconditions.checkNotNull(bundle2);
        this.mResultGroupingTypeFlags = i8;
        this.mGroupingLimit = i9;
        this.mTypePropertyWeightsField = (Bundle) Preconditions.checkNotNull(bundle3);
        this.mJoinSpec = joinSpec;
        this.mAdvancedRankingExpression = (String) Preconditions.checkNotNull(str);
        this.mEnabledFeatures = Collections.unmodifiableList((List) Preconditions.checkNotNull(list4));
        this.mSearchSourceLogTag = str2;
        if (list5 != null) {
            this.mEmbeddingParameters = Collections.unmodifiableList(list5);
        } else {
            this.mEmbeddingParameters = Collections.EMPTY_LIST;
        }
        this.mDefaultEmbeddingSearchMetricType = i10;
        if (list6 != null) {
            this.mInformationalRankingExpressions = Collections.unmodifiableList(list6);
        } else {
            this.mInformationalRankingExpressions = Collections.EMPTY_LIST;
        }
        this.mSearchStringParameters = list7 != null ? Collections.unmodifiableList(list7) : Collections.EMPTY_LIST;
        this.mFilterDocumentIds = list8 != null ? Collections.unmodifiableList(list8) : Collections.EMPTY_LIST;
        this.mRetrieveEmbeddingMatchInfos = z;
    }

    public String getAdvancedRankingExpression() {
        return this.mAdvancedRankingExpression;
    }

    public int getDefaultEmbeddingSearchMetricType() {
        return this.mDefaultEmbeddingSearchMetricType;
    }

    public List getEmbeddingParameters() {
        return this.mEmbeddingParameters;
    }

    public List getEnabledFeatures() {
        return this.mEnabledFeatures;
    }

    public List getFilterDocumentIds() {
        return this.mFilterDocumentIds;
    }

    public List getFilterNamespaces() {
        List list = this.mNamespaces;
        return list == null ? Collections.EMPTY_LIST : list;
    }

    public List getFilterPackageNames() {
        List list = this.mPackageNames;
        return list == null ? Collections.EMPTY_LIST : list;
    }

    public Map getFilterProperties() {
        Set<String> setKeySet = this.mTypePropertyFilters.keySet();
        ArrayMap arrayMap = new ArrayMap(setKeySet.size());
        for (String str : setKeySet) {
            arrayMap.put(str, (List) Preconditions.checkNotNull(this.mTypePropertyFilters.getStringArrayList(str)));
        }
        return arrayMap;
    }

    public List getFilterSchemas() {
        List list = this.mSchemas;
        return list == null ? Collections.EMPTY_LIST : list;
    }

    public List getInformationalRankingExpressions() {
        return this.mInformationalRankingExpressions;
    }

    public JoinSpec getJoinSpec() {
        return this.mJoinSpec;
    }

    public int getMaxSnippetSize() {
        return this.mMaxSnippetSize;
    }

    public int getOrder() {
        return this.mOrder;
    }

    public Map getProjections() {
        Set<String> setKeySet = this.mProjectionTypePropertyMasks.keySet();
        ArrayMap arrayMap = new ArrayMap(setKeySet.size());
        for (String str : setKeySet) {
            ArrayList<String> stringArrayList = this.mProjectionTypePropertyMasks.getStringArrayList(str);
            Objects.requireNonNull(stringArrayList);
            arrayMap.put(str, stringArrayList);
        }
        return arrayMap;
    }

    public Map getPropertyWeights() {
        Set<String> setKeySet = this.mTypePropertyWeightsField.keySet();
        ArrayMap arrayMap = new ArrayMap(setKeySet.size());
        for (String str : setKeySet) {
            Bundle bundle = this.mTypePropertyWeightsField.getBundle(str);
            if (bundle != null) {
                Set<String> setKeySet2 = bundle.keySet();
                ArrayMap arrayMap2 = new ArrayMap(setKeySet2.size());
                for (String str2 : setKeySet2) {
                    arrayMap2.put(str2, Double.valueOf(bundle.getDouble(str2)));
                }
                arrayMap.put(str, arrayMap2);
            }
        }
        return arrayMap;
    }

    public int getRankingStrategy() {
        return this.mRankingStrategy;
    }

    public int getResultCountPerPage() {
        return this.mResultCountPerPage;
    }

    public int getResultGroupingLimit() {
        return this.mGroupingLimit;
    }

    public int getResultGroupingTypeFlags() {
        return this.mResultGroupingTypeFlags;
    }

    public String getSearchSourceLogTag() {
        return this.mSearchSourceLogTag;
    }

    public List getSearchStringParameters() {
        return this.mSearchStringParameters;
    }

    public int getSnippetCount() {
        return this.mSnippetCount;
    }

    public int getSnippetCountPerProperty() {
        return this.mSnippetCountPerProperty;
    }

    public int getTermMatch() {
        return this.mTermMatchType;
    }

    public boolean isListFilterHasPropertyFunctionEnabled() {
        return this.mEnabledFeatures.contains("LIST_FILTER_HAS_PROPERTY_FUNCTION");
    }

    public boolean isListFilterMatchScoreExpressionFunctionEnabled() {
        return this.mEnabledFeatures.contains("LIST_FILTER_MATCH_SCORE_EXPRESSION_FUNCTION");
    }

    public boolean isListFilterQueryLanguageEnabled() {
        return this.mEnabledFeatures.contains("LIST_FILTER_QUERY_LANGUAGE");
    }

    public boolean isNumericSearchEnabled() {
        return this.mEnabledFeatures.contains("NUMERIC_SEARCH");
    }

    public boolean isScorablePropertyRankingEnabled() {
        return this.mEnabledFeatures.contains("SCHEMA_SCORABLE_PROPERTY_CONFIG");
    }

    public boolean isVerbatimSearchEnabled() {
        return this.mEnabledFeatures.contains("VERBATIM_SEARCH");
    }
}
