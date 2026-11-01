package com.xiaomi.micloudsdk.stat;

/* JADX INFO: loaded from: classes2.dex */
public class DownloadFileFailedStatParam {
    public final String exceptionName;
    public final int httpStatusCode;
    public final String url;

    public String toString() {
        return "DownloadFileFailedStatParam{url='" + this.url + "', httpStatusCode=" + this.httpStatusCode + ", exceptionName='" + this.exceptionName + "'}";
    }
}
