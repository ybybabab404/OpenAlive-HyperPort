package androidx.appsearch.app.aidl;

import androidx.annotation.RestrictTo;
import androidx.appsearch.app.AppSearchBatchResult;
import androidx.appsearch.app.ExperimentalAppSearchApi;

/* JADX INFO: loaded from: classes.dex */
@ExperimentalAppSearchApi
@RestrictTo
public final class AppSearchBatchResultParcelV2<KeyType, ValueType> {
    public final AppSearchBatchResult mResult;

    public AppSearchBatchResult getResult() {
        return this.mResult;
    }
}
