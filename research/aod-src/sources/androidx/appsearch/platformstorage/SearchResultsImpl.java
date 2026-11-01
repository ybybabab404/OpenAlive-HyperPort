package androidx.appsearch.platformstorage;

import android.app.appsearch.AppSearchResult;
import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appsearch.app.GenericDocument;
import androidx.appsearch.app.SearchResult;
import androidx.appsearch.app.SearchResults;
import androidx.appsearch.app.SearchSpec;
import androidx.appsearch.exceptions.AppSearchException;
import androidx.appsearch.platformstorage.converter.SearchResultToPlatformConverter;
import androidx.appsearch.platformstorage.util.AppSearchVersionUtil;
import androidx.collection.ArraySet;
import androidx.concurrent.futures.ResolvableFuture;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@RestrictTo
class SearchResultsImpl implements SearchResults {
    public final Context mContext;
    public final Executor mExecutor;
    public final android.app.appsearch.SearchResults mPlatformResults;
    public final SearchSpec mSearchSpec;

    /* JADX INFO: renamed from: $r8$lambda$C347FT02J_-cc94RBUXGCsUGJI8, reason: not valid java name */
    public static /* synthetic */ void m21$r8$lambda$C347FT02J_cc94RBUXGCsUGJI8(SearchResultsImpl searchResultsImpl, ResolvableFuture resolvableFuture, AppSearchResult appSearchResult) {
        searchResultsImpl.getClass();
        if (!appSearchResult.isSuccess()) {
            resolvableFuture.setException(new AppSearchException(appSearchResult.getResultCode(), appSearchResult.getErrorMessage()));
            return;
        }
        List list = (List) appSearchResult.getResultValue();
        ArrayList arrayList = new ArrayList(list.size());
        Map projections = searchResultsImpl.mSearchSpec.getProjections();
        boolean z = (Build.VERSION.SDK_INT < 34) && ((AppSearchVersionUtil.getAppSearchVersionCode(searchResultsImpl.mContext) > 340800000L ? 1 : (AppSearchVersionUtil.getAppSearchVersionCode(searchResultsImpl.mContext) == 340800000L ? 0 : -1)) < 0) && !projections.isEmpty();
        for (int i = 0; i < list.size(); i++) {
            SearchResult jetpackSearchResult = SearchResultToPlatformConverter.toJetpackSearchResult((android.app.appsearch.SearchResult) list.get(i));
            if (z) {
                arrayList.add(searchResultsImpl.manuallyApplyProjection(jetpackSearchResult, projections));
            } else {
                arrayList.add(jetpackSearchResult);
            }
        }
        resolvableFuture.set(arrayList);
    }

    public SearchResultsImpl(android.app.appsearch.SearchResults searchResults, SearchSpec searchSpec, Executor executor, Context context) {
        this.mPlatformResults = (android.app.appsearch.SearchResults) Preconditions.checkNotNull(searchResults);
        this.mSearchSpec = (SearchSpec) Preconditions.checkNotNull(searchSpec);
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
        this.mContext = (Context) Preconditions.checkNotNull(context);
    }

    public final void applyProjectionToNestedDocument(GenericDocument.Builder builder, GenericDocument genericDocument, String str, Set set) {
        ArraySet arraySet = new ArraySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            if (str2.startsWith(str + ".")) {
                arraySet.add(str2.substring(str.length() + 1));
            }
        }
        if (arraySet.isEmpty()) {
            return;
        }
        GenericDocument.Builder builder2 = new GenericDocument.Builder(genericDocument);
        for (String str3 : genericDocument.getPropertyNames()) {
            GenericDocument propertyDocument = genericDocument.getPropertyDocument(str3);
            if (propertyDocument != null) {
                applyProjectionToNestedDocument(builder2, propertyDocument, str3, arraySet);
            } else if (!arraySet.contains(str3)) {
                builder2.clearProperty(str3);
            }
        }
        builder.setPropertyDocument(str, builder2.build());
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.mPlatformResults.close();
    }

    @Override // androidx.appsearch.app.SearchResults
    public ListenableFuture getNextPageAsync() {
        final ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        this.mPlatformResults.getNextPage(this.mExecutor, new Consumer() { // from class: androidx.appsearch.platformstorage.SearchResultsImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SearchResultsImpl.m21$r8$lambda$C347FT02J_cc94RBUXGCsUGJI8(this.f$0, resolvableFutureCreate, (AppSearchResult) obj);
            }
        });
        return resolvableFutureCreate;
    }

    public final SearchResult manuallyApplyProjection(SearchResult searchResult, Map map) {
        GenericDocument genericDocument = searchResult.getGenericDocument();
        GenericDocument.Builder builder = new GenericDocument.Builder(genericDocument);
        List list = (List) map.get(genericDocument.getSchemaType());
        List list2 = (List) map.get("*");
        ArraySet arraySet = list != null ? new ArraySet(list) : list2 != null ? new ArraySet(list2) : null;
        if (arraySet != null) {
            for (String str : genericDocument.getPropertyNames()) {
                GenericDocument propertyDocument = genericDocument.getPropertyDocument(str);
                if (propertyDocument != null) {
                    applyProjectionToNestedDocument(builder, propertyDocument, str, arraySet);
                } else if (!arraySet.contains(str)) {
                    builder.clearProperty(str);
                }
            }
        }
        SearchResult.Builder genericDocument2 = new SearchResult.Builder(searchResult).setGenericDocument(builder.build());
        List<SearchResult.MatchInfo> matchInfos = searchResult.getMatchInfos();
        if (!matchInfos.isEmpty()) {
            genericDocument2.clearMatchInfos();
        }
        for (SearchResult.MatchInfo matchInfo : matchInfos) {
            String propertyPath = matchInfo.getPropertyPath();
            boolean z = false;
            boolean z2 = list != null && list.contains(propertyPath);
            if (list2 != null && list2.contains(propertyPath)) {
                z = true;
            }
            if (z2 || z) {
                genericDocument2.addMatchInfo(new SearchResult.MatchInfo.Builder(matchInfo).build());
            }
        }
        return genericDocument2.build();
    }
}
