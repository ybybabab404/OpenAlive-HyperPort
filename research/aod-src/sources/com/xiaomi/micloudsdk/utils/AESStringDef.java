package com.xiaomi.micloudsdk.utils;

/* JADX INFO: loaded from: classes2.dex */
public class AESStringDef {
    public String IV;
    public String data;
    public String version;

    public static class InvalidAESDataException extends Exception {
    }

    public String toString() {
        return String.format("%s:%s:%s", this.version, this.IV, this.data);
    }
}
