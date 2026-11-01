package com.xiaomi.opensdk.exception;

/* JADX INFO: loaded from: classes2.dex */
public class UnretriableException extends Exception {
    public String mDescription;
    public int mErrorCode;

    @Override // java.lang.Throwable
    public String toString() {
        String simpleName = getClass().getSimpleName();
        Throwable cause = getCause();
        if (cause != null) {
            String str = simpleName + "[" + cause.getClass().getSimpleName();
            if (cause.getMessage() != null) {
                str = str + ":" + cause.getMessage();
            }
            simpleName = str + "]";
        }
        if (this.mErrorCode > 0) {
            simpleName = simpleName + "[" + this.mErrorCode + "]";
        }
        if (this.mDescription == null) {
            return simpleName;
        }
        return simpleName + ": " + this.mDescription;
    }
}
