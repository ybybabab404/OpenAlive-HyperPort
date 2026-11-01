package androidx.appsearch.platformstorage;

import android.app.appsearch.AppSearchResult;
import android.app.appsearch.SearchSuggestionResult;
import android.app.appsearch.SearchSuggestionSpec;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appsearch.app.AppSearchSession;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@RestrictTo
class SearchSessionImpl implements AppSearchSession {
    public final android.app.appsearch.AppSearchSession mPlatformSession;

    @RequiresApi
    public static class ApiHelperForU {
        @DoNotInline
        public static void searchSuggestion(android.app.appsearch.AppSearchSession appSearchSession, String str, SearchSuggestionSpec searchSuggestionSpec, Executor executor, Consumer<AppSearchResult<List<SearchSuggestionResult>>> consumer) {
            appSearchSession.searchSuggestion(str, searchSuggestionSpec, executor, consumer);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.mPlatformSession.close();
    }
}
