package androidx.appsearch.platformstorage.converter;

import android.annotation.SuppressLint;
import android.app.appsearch.SearchSpec;
import android.content.Context;
import android.os.Build;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appsearch.app.EmbeddingVector;
import androidx.appsearch.app.JoinSpec;
import androidx.appsearch.platformstorage.util.AppSearchVersionUtil;
import androidx.core.util.Preconditions;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@RestrictTo
public final class SearchSpecToPlatformConverter {

    @RequiresApi
    @SuppressLint({"NewApi"})
    public static class ApiHelperForB {
        @DoNotInline
        public static void addEmbeddingParameters(SearchSpec.Builder builder, List<EmbeddingVector> list) {
            android.app.appsearch.EmbeddingVector[] embeddingVectorArr = new android.app.appsearch.EmbeddingVector[list.size()];
            for (int i = 0; i < list.size(); i++) {
                GenericDocumentToPlatformConverter$ApiHelperForB$$ExternalSyntheticApiModelOutline3.m();
                embeddingVectorArr[i] = GenericDocumentToPlatformConverter$ApiHelperForB$$ExternalSyntheticApiModelOutline2.m(list.get(i).getValues(), list.get(i).getModelSignature());
            }
            builder.addEmbeddingParameters(embeddingVectorArr);
        }

        @DoNotInline
        public static void addInformationalRankingExpressions(SearchSpec.Builder builder, List<String> list) {
            builder.addInformationalRankingExpressions(list);
        }

        @DoNotInline
        @SuppressLint({"WrongConstant"})
        public static void setDefaultEmbeddingSearchMetricType(SearchSpec.Builder builder, int i) {
            builder.setDefaultEmbeddingSearchMetricType(i);
        }
    }

    @RequiresApi
    public static class ApiHelperForU {
        @DoNotInline
        public static void copyEnabledFeatures(SearchSpec.Builder builder, androidx.appsearch.app.SearchSpec searchSpec) {
            if (searchSpec.isNumericSearchEnabled()) {
                builder.setNumericSearchEnabled(true);
            }
            if (searchSpec.isVerbatimSearchEnabled()) {
                builder.setVerbatimSearchEnabled(true);
            }
            if (searchSpec.isListFilterQueryLanguageEnabled()) {
                builder.setListFilterQueryLanguageEnabled(true);
            }
        }

        @DoNotInline
        public static void setJoinSpec(Context context, SearchSpec.Builder builder, JoinSpec joinSpec) {
            builder.setJoinSpec(JoinSpecToPlatformConverter.toPlatformJoinSpec(context, joinSpec));
        }

        @DoNotInline
        public static void setPropertyWeights(SearchSpec.Builder builder, Map<String, Map<String, Double>> map) {
            for (Map.Entry<String, Map<String, Double>> entry : map.entrySet()) {
                builder.setPropertyWeights(entry.getKey(), entry.getValue());
            }
        }

        @DoNotInline
        public static void setRankingStrategy(SearchSpec.Builder builder, String str) {
            builder.setRankingStrategy(str);
        }
    }

    @RequiresApi
    public static class ApiHelperForV {
        @DoNotInline
        public static void addFilterProperties(SearchSpec.Builder builder, Map<String, List<String>> map) {
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                builder.addFilterProperties(entry.getKey(), entry.getValue());
            }
        }

        @DoNotInline
        public static void copyEnabledFeatures(SearchSpec.Builder builder, androidx.appsearch.app.SearchSpec searchSpec) {
            if (searchSpec.isListFilterHasPropertyFunctionEnabled()) {
                builder.setListFilterHasPropertyFunctionEnabled(true);
            }
        }

        @DoNotInline
        public static void setSearchSourceLogTag(SearchSpec.Builder builder, String str) {
            builder.setSearchSourceLogTag(str);
        }
    }

    public static SearchSpec toPlatformSearchSpec(Context context, androidx.appsearch.app.SearchSpec searchSpec) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(searchSpec);
        SearchSpec.Builder builder = new SearchSpec.Builder();
        if (searchSpec.getAdvancedRankingExpression().isEmpty()) {
            builder.setRankingStrategy(searchSpec.getRankingStrategy());
        } else {
            if (Build.VERSION.SDK_INT < 34) {
                throw new UnsupportedOperationException("SEARCH_SPEC_ADVANCED_RANKING_EXPRESSION is not available on this AppSearch implementation.");
            }
            ApiHelperForU.setRankingStrategy(builder, searchSpec.getAdvancedRankingExpression());
        }
        builder.setTermMatch(searchSpec.getTermMatch()).addFilterSchemas(searchSpec.getFilterSchemas()).addFilterNamespaces(searchSpec.getFilterNamespaces()).addFilterPackageNames(searchSpec.getFilterPackageNames()).setResultCountPerPage(searchSpec.getResultCountPerPage()).setOrder(searchSpec.getOrder()).setSnippetCount(searchSpec.getSnippetCount()).setSnippetCountPerProperty(searchSpec.getSnippetCountPerProperty()).setMaxSnippetSize(searchSpec.getMaxSnippetSize());
        if (searchSpec.getResultGroupingTypeFlags() != 0) {
            if ((searchSpec.getResultGroupingTypeFlags() & 4) != 0 && Build.VERSION.SDK_INT < 35) {
                throw new UnsupportedOperationException("SEARCH_SPEC_GROUPING_TYPE_PER_SCHEMA is not available on this AppSearch implementation.");
            }
            builder.setResultGrouping(searchSpec.getResultGroupingTypeFlags(), searchSpec.getResultGroupingLimit());
        }
        if (Build.VERSION.SDK_INT >= 34 || AppSearchVersionUtil.getAppSearchVersionCode(context) >= 340800000) {
            for (Map.Entry entry : searchSpec.getProjections().entrySet()) {
                builder.addProjection((String) entry.getKey(), (Collection) entry.getValue());
            }
        }
        if (!searchSpec.getPropertyWeights().isEmpty()) {
            if (Build.VERSION.SDK_INT < 34) {
                throw new UnsupportedOperationException("Property weights are not supported with this backend/Android API level combination.");
            }
            ApiHelperForU.setPropertyWeights(builder, searchSpec.getPropertyWeights());
        }
        if (!searchSpec.getEnabledFeatures().isEmpty()) {
            if (searchSpec.isNumericSearchEnabled() || searchSpec.isVerbatimSearchEnabled() || searchSpec.isListFilterQueryLanguageEnabled()) {
                if (Build.VERSION.SDK_INT < 34) {
                    throw new UnsupportedOperationException("Advanced query features (NUMERIC_SEARCH, VERBATIM_SEARCH and LIST_FILTER_QUERY_LANGUAGE) are not supported with this backend/Android API level combination.");
                }
                ApiHelperForU.copyEnabledFeatures(builder, searchSpec);
            }
            if (searchSpec.isListFilterHasPropertyFunctionEnabled()) {
                if (Build.VERSION.SDK_INT < 35) {
                    throw new UnsupportedOperationException("LIST_FILTER_HAS_PROPERTY_FUNCTION is not available on this AppSearch implementation.");
                }
                ApiHelperForV.copyEnabledFeatures(builder, searchSpec);
            }
            if (searchSpec.isListFilterMatchScoreExpressionFunctionEnabled()) {
                throw new UnsupportedOperationException("LIST_FILTER_MATCH_SCORE_EXPRESSION_FUNCTION is not available on this AppSearch implementation.");
            }
        }
        if (!searchSpec.getEmbeddingParameters().isEmpty()) {
            if (!AppSearchVersionUtil.isAtLeastB()) {
                throw new UnsupportedOperationException("SCHEMA_EMBEDDING_PROPERTY_CONFIG is not available on this AppSearch implementation.");
            }
            ApiHelperForB.addEmbeddingParameters(builder, searchSpec.getEmbeddingParameters());
            ApiHelperForB.setDefaultEmbeddingSearchMetricType(builder, searchSpec.getDefaultEmbeddingSearchMetricType());
        }
        if (!searchSpec.getSearchStringParameters().isEmpty()) {
            throw new UnsupportedOperationException("SEARCH_SPEC_SEARCH_STRING_PARAMETERS is not available on this AppSearch implementation.");
        }
        if (searchSpec.getJoinSpec() != null) {
            if (Build.VERSION.SDK_INT < 34) {
                throw new UnsupportedOperationException("JoinSpec is not available on this AppSearch implementation.");
            }
            ApiHelperForU.setJoinSpec(context, builder, searchSpec.getJoinSpec());
        }
        if (!searchSpec.getFilterProperties().isEmpty()) {
            if (Build.VERSION.SDK_INT < 35) {
                throw new UnsupportedOperationException("SEARCH_SPEC_ADD_FILTER_PROPERTIES is not available on this AppSearch implementation.");
            }
            ApiHelperForV.addFilterProperties(builder, searchSpec.getFilterProperties());
        }
        if (searchSpec.getSearchSourceLogTag() != null) {
            if (Build.VERSION.SDK_INT < 35) {
                throw new UnsupportedOperationException("SEARCH_SPEC_SET_SEARCH_SOURCE_LOG_TAG is not available on this AppSearch implementation.");
            }
            ApiHelperForV.setSearchSourceLogTag(builder, searchSpec.getSearchSourceLogTag());
        }
        if (!searchSpec.getInformationalRankingExpressions().isEmpty()) {
            if (!AppSearchVersionUtil.isAtLeastB()) {
                throw new UnsupportedOperationException("SEARCH_SPEC_ADD_INFORMATIONAL_RANKING_EXPRESSIONS are not available on this AppSearch implementation.");
            }
            ApiHelperForB.addInformationalRankingExpressions(builder, searchSpec.getInformationalRankingExpressions());
        }
        if (!searchSpec.getFilterDocumentIds().isEmpty()) {
            throw new UnsupportedOperationException("SEARCH_SPEC_ADD_FILTER_DOCUMENT_IDS is not available on this AppSearch implementation.");
        }
        if (searchSpec.isScorablePropertyRankingEnabled()) {
            throw new UnsupportedOperationException("SCHEMA_SCORABLE_PROPERTY_CONFIG is not available on this AppSearch implementation.");
        }
        return builder.build();
    }
}
