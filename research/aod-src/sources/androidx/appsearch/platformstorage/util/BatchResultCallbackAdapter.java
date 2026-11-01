package androidx.appsearch.platformstorage.util;

import android.app.appsearch.AppSearchBatchResult;
import android.app.appsearch.BatchResultCallback;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appsearch.platformstorage.converter.AppSearchResultToPlatformConverter;
import androidx.concurrent.futures.ResolvableFuture;
import androidx.core.util.Preconditions;
import java.util.function.Function;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@RestrictTo
public final class BatchResultCallbackAdapter<K, PlatformValue, JetpackValue> implements BatchResultCallback<K, PlatformValue> {
    public final ResolvableFuture mFuture;
    public final Function mResultTransformer;
    public final Function mValueMapper;

    public BatchResultCallbackAdapter(ResolvableFuture resolvableFuture, Function function) {
        this(resolvableFuture, function, null);
    }

    public BatchResultCallbackAdapter(ResolvableFuture resolvableFuture, Function function, Function function2) {
        this.mFuture = (ResolvableFuture) Preconditions.checkNotNull(resolvableFuture);
        this.mValueMapper = (Function) Preconditions.checkNotNull(function);
        this.mResultTransformer = function2;
    }

    @Override // android.app.appsearch.BatchResultCallback
    public void onResult(AppSearchBatchResult appSearchBatchResult) {
        androidx.appsearch.app.AppSearchBatchResult appSearchBatchResultPlatformAppSearchBatchResultToJetpack = AppSearchResultToPlatformConverter.platformAppSearchBatchResultToJetpack(appSearchBatchResult, this.mValueMapper);
        Function function = this.mResultTransformer;
        if (function != null) {
            appSearchBatchResultPlatformAppSearchBatchResultToJetpack = (androidx.appsearch.app.AppSearchBatchResult) function.apply(appSearchBatchResultPlatformAppSearchBatchResultToJetpack);
        }
        this.mFuture.set(appSearchBatchResultPlatformAppSearchBatchResultToJetpack);
    }

    @Override // android.app.appsearch.BatchResultCallback
    public void onSystemError(Throwable th) {
        this.mFuture.setException(th);
    }
}
