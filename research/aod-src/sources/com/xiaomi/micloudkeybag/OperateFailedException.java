package com.xiaomi.micloudkeybag;

/* JADX INFO: loaded from: classes2.dex */
public class OperateFailedException extends Exception {
    public final int errorCode;

    @Override // java.lang.Throwable
    public String toString() {
        return "OperateFailedException, errorCode: " + this.errorCode;
    }
}
