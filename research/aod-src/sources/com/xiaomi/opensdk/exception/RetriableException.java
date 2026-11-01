package com.xiaomi.opensdk.exception;

/* JADX INFO: loaded from: classes2.dex */
public class RetriableException extends Exception {
    public final String mDescription;
    public final long mRetrytime;

    @Override // java.lang.Throwable
    public String toString() {
        String simpleName = getClass().getSimpleName();
        if (this.mDescription == null) {
            return simpleName;
        }
        return simpleName + ": " + this.mDescription + ", retry after " + (this.mRetrytime / 1000) + " seconds";
    }
}
