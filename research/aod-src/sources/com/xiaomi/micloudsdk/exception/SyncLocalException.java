package com.xiaomi.micloudsdk.exception;

/* JADX INFO: loaded from: classes2.dex */
public class SyncLocalException extends Exception {
    public final int mErrorCode;

    public SyncLocalException(int i) {
        super("errorCode: " + i);
        this.mErrorCode = i;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }
}
