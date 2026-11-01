package androidx.appsearch.exceptions;

import androidx.appsearch.app.AppSearchResult;

/* JADX INFO: loaded from: classes.dex */
public class AppSearchException extends Exception {
    public final int mResultCode;

    public AppSearchException(int i, String str) {
        this(i, str, null);
    }

    public AppSearchException(int i, String str, Throwable th) {
        super(str, th);
        this.mResultCode = i;
    }

    public int getResultCode() {
        return this.mResultCode;
    }

    public AppSearchResult toAppSearchResult() {
        return AppSearchResult.newFailedResult(this.mResultCode, getMessage());
    }
}
