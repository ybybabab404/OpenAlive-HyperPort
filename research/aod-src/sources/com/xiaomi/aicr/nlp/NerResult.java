package com.xiaomi.aicr.nlp;

/* JADX INFO: loaded from: classes2.dex */
public class NerResult {
    public int exactType;
    public String sdkVersion;
    public int serviceVersion;
    public int statusCode;

    public String toString() {
        return "NerResult{type=" + this.exactType + ", statusCode=" + this.statusCode + ", sdkVersion=" + this.sdkVersion + ", serviceVersion=" + this.serviceVersion + '}';
    }
}
