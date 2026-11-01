package com.xiaomi.micloudsdk.stat;

/* JADX INFO: loaded from: classes2.dex */
public class GetDownloadFileUrlFailedStatParam {
    public final String description;
    public final String exceptionName;
    public final int httpStatusCode;
    public final String reason;
    public final int responseCode;
    public final String url;

    public static class GetDownloadFileUrlFailedResponse {
    }

    public String toString() {
        return "GetDownloadFileUrlFailedStatParam{url='" + this.url + "', httpStatusCode=" + this.httpStatusCode + ", exceptionName='" + this.exceptionName + "', responseCode=" + this.responseCode + ", description='" + this.description + "', reason='" + this.reason + "'}";
    }
}
