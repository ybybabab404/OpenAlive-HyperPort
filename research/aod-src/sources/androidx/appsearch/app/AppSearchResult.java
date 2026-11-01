package androidx.appsearch.app;

import android.util.Log;
import androidx.annotation.OptIn;
import androidx.annotation.RestrictTo;
import androidx.appsearch.exceptions.AppSearchException;
import androidx.core.util.ObjectsCompat;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public final class AppSearchResult<ValueType> {
    public final String mErrorMessage;
    public final int mResultCode;
    public final Object mResultValue;

    @OptIn
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface ResultCode {
    }

    public AppSearchResult(int i, Object obj, String str) {
        this.mResultCode = i;
        this.mResultValue = obj;
        this.mErrorMessage = str;
    }

    public static AppSearchResult newFailedResult(int i, String str) {
        return new AppSearchResult(i, null, str);
    }

    public static AppSearchResult newSuccessfulResult(Object obj) {
        return new AppSearchResult(0, obj, null);
    }

    public static AppSearchResult throwableToFailedResult(Throwable th) {
        int i;
        boolean z = th instanceof AppSearchException;
        if (!z || ((AppSearchException) th).getResultCode() != 6) {
            Log.w("AppSearchResult", "Converting throwable to failed result.", th);
        }
        if (z) {
            return ((AppSearchException) th).toAppSearchResult();
        }
        String simpleName = th.getClass().getSimpleName();
        if ((th instanceof IllegalStateException) || (th instanceof NullPointerException)) {
            i = 2;
        } else if (th instanceof IllegalArgumentException) {
            i = 3;
        } else if (th instanceof IOException) {
            i = 4;
        } else {
            i = th instanceof SecurityException ? 8 : 1;
        }
        return newFailedResult(i, simpleName + ": " + th.getMessage());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppSearchResult)) {
            return false;
        }
        AppSearchResult appSearchResult = (AppSearchResult) obj;
        return this.mResultCode == appSearchResult.mResultCode && ObjectsCompat.equals(this.mResultValue, appSearchResult.mResultValue) && ObjectsCompat.equals(this.mErrorMessage, appSearchResult.mErrorMessage);
    }

    public int getResultCode() {
        return this.mResultCode;
    }

    public Object getResultValue() {
        if (isSuccess()) {
            return this.mResultValue;
        }
        throw new IllegalStateException("AppSearchResult is a failure: " + this);
    }

    public int hashCode() {
        return ObjectsCompat.hash(Integer.valueOf(this.mResultCode), this.mResultValue, this.mErrorMessage);
    }

    public boolean isSuccess() {
        return getResultCode() == 0;
    }

    public String toString() {
        if (isSuccess()) {
            return "[SUCCESS]: " + this.mResultValue;
        }
        return "[FAILURE(" + this.mResultCode + ")]: " + this.mErrorMessage;
    }
}
