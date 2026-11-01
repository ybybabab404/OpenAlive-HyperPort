package androidx.appsearch.platformstorage.converter;

import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appsearch.app.AppSearchBatchResult;
import androidx.appsearch.app.AppSearchResult;
import androidx.core.util.Preconditions;
import java.util.Map;
import java.util.function.Function;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@RestrictTo
public final class AppSearchResultToPlatformConverter {
    public static AppSearchBatchResult platformAppSearchBatchResultToJetpack(android.app.appsearch.AppSearchBatchResult appSearchBatchResult, Function function) {
        Preconditions.checkNotNull(appSearchBatchResult);
        Preconditions.checkNotNull(function);
        AppSearchBatchResult.Builder builder = new AppSearchBatchResult.Builder();
        for (Map.Entry entry : appSearchBatchResult.getSuccesses().entrySet()) {
            try {
                builder.setSuccess(entry.getKey(), function.apply(entry.getValue()));
            } catch (Throwable th) {
                builder.setResult(entry.getKey(), AppSearchResult.throwableToFailedResult(th));
            }
        }
        for (Map.Entry entry2 : appSearchBatchResult.getFailures().entrySet()) {
            builder.setFailure(entry2.getKey(), ((android.app.appsearch.AppSearchResult) entry2.getValue()).getResultCode(), ((android.app.appsearch.AppSearchResult) entry2.getValue()).getErrorMessage());
        }
        return builder.build();
    }
}
