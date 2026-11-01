package com.xiaomi.opensdk.exception;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes2.dex */
public class AuthenticationException extends Exception {
    @Override // java.lang.Throwable
    public String toString() {
        if (TextUtils.isEmpty(getMessage())) {
            return getClass().getSimpleName() + ": 鉴权失败";
        }
        return getClass().getSimpleName() + ": 鉴权失败, " + getMessage();
    }
}
