package androidx.appsearch.platformstorage.converter;

import android.annotation.SuppressLint;
import android.app.appsearch.SearchResult;
import android.os.Build;
import android.util.Log;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appsearch.platformstorage.util.AppSearchVersionUtil;
import androidx.core.util.Preconditions;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@RestrictTo
public class SearchResultToPlatformConverter {

    @RequiresApi
    public static class ApiHelperForB {
        @DoNotInline
        @SuppressLint({"NewApi"})
        public static List<Double> getInformationalRankingSignals(SearchResult searchResult) {
            return searchResult.getInformationalRankingSignals();
        }

        @DoNotInline
        public static Map<String, List<String>> getParentTypeMap(SearchResult searchResult) {
            return searchResult.getParentTypeMap();
        }
    }

    @RequiresApi
    public static class ApiHelperForT {
        @DoNotInline
        public static int getSubmatchRangeEnd(SearchResult.MatchInfo matchInfo) {
            return matchInfo.getSubmatchRange().getEnd();
        }

        @DoNotInline
        public static int getSubmatchRangeStart(SearchResult.MatchInfo matchInfo) {
            return matchInfo.getSubmatchRange().getStart();
        }
    }

    @RequiresApi
    public static class ApiHelperForU {
        @DoNotInline
        public static List<SearchResult> getJoinedResults(SearchResult searchResult) {
            return searchResult.getJoinedResults();
        }
    }

    public static androidx.appsearch.app.SearchResult.MatchInfo toJetpackMatchInfo(SearchResult.MatchInfo matchInfo) {
        Preconditions.checkNotNull(matchInfo);
        androidx.appsearch.app.SearchResult.MatchInfo.Builder snippetRange = new androidx.appsearch.app.SearchResult.MatchInfo.Builder(matchInfo.getPropertyPath()).setExactMatchRange(new androidx.appsearch.app.SearchResult.MatchRange(matchInfo.getExactMatchRange().getStart(), matchInfo.getExactMatchRange().getEnd())).setSnippetRange(new androidx.appsearch.app.SearchResult.MatchRange(matchInfo.getSnippetRange().getStart(), matchInfo.getSnippetRange().getEnd()));
        snippetRange.setSubmatchRange(new androidx.appsearch.app.SearchResult.MatchRange(ApiHelperForT.getSubmatchRangeStart(matchInfo), ApiHelperForT.getSubmatchRangeEnd(matchInfo)));
        return snippetRange.build();
    }

    public static androidx.appsearch.app.SearchResult toJetpackSearchResult(SearchResult searchResult) {
        Preconditions.checkNotNull(searchResult);
        androidx.appsearch.app.SearchResult.Builder rankingSignal = new androidx.appsearch.app.SearchResult.Builder(searchResult.getPackageName(), searchResult.getDatabaseName()).setGenericDocument(GenericDocumentToPlatformConverter.toJetpackGenericDocument(searchResult.getGenericDocument())).setRankingSignal(searchResult.getRankingSignal());
        List<SearchResult.MatchInfo> matchInfos = searchResult.getMatchInfos();
        for (int i = 0; i < matchInfos.size(); i++) {
            rankingSignal.addMatchInfo(toJetpackMatchInfo(matchInfos.get(i)));
        }
        if (Build.VERSION.SDK_INT >= 34) {
            Iterator<SearchResult> it = ApiHelperForU.getJoinedResults(searchResult).iterator();
            while (it.hasNext()) {
                rankingSignal.addJoinedResult(toJetpackSearchResult(it.next()));
            }
        }
        if (AppSearchVersionUtil.isAtLeastB()) {
            List<Double> informationalRankingSignals = ApiHelperForB.getInformationalRankingSignals(searchResult);
            for (int i2 = 0; i2 < informationalRankingSignals.size(); i2++) {
                rankingSignal.addInformationalRankingSignal(informationalRankingSignals.get(i2).doubleValue());
            }
            try {
                rankingSignal.setParentTypeMap(ApiHelperForB.getParentTypeMap(searchResult));
            } catch (NoSuchMethodError e) {
                Log.e("AppSearchSearchResPlatC", "Failed to set parent type map.", e);
            }
        }
        return rankingSignal.build();
    }
}
