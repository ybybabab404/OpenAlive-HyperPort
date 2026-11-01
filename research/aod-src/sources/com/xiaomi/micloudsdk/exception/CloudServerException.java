package com.xiaomi.micloudsdk.exception;

import com.xiaomi.opensdk.exception.ServerException;

/* JADX INFO: loaded from: classes2.dex */
public class CloudServerException extends ServerException {
    public int code;
    public int retryTime;
    public int statusCode;

    public long get5xxServerExceptionRetryTime() {
        int i;
        if (this.statusCode != 503 || (i = this.retryTime) <= 0) {
            return 2147483647L;
        }
        return i;
    }

    public boolean is5xxServerException() {
        return this.statusCode / 100 == 5;
    }
}
