package com.xiaomi.keychainsdk.request;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class KeyBagProtocol {
    public static Map sInstanceMap = new HashMap();

    public static class AsyncOperationInterruptedException extends AsyncOperationNotCompleteException {
    }

    public static abstract class AsyncOperationNotCompleteException extends Exception {
    }

    public static class AsyncOperationTimeoutException extends AsyncOperationNotCompleteException {
    }

    public static class BadMasterKeyServerSignatureException extends Exception {
    }

    public static class BadResponseException extends Exception {
    }

    public static class OperationFailedException extends Exception {
        public final int errCode;
        public final String errMessage;
        public final long retryAfter;

        @Override // java.lang.Throwable
        public String toString() {
            return "errorCode: " + this.errCode + " errMessage: " + this.errMessage + " retryAfter: " + this.retryAfter;
        }
    }
}
