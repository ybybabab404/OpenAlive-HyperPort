package com.xiaomi.micloudsdk.utils;

/* JADX INFO: loaded from: classes2.dex */
public class CloudCoder {

    public enum CIPHER_MODE {
        ENCRYPT,
        DECRYPT
    }

    public static String hashDeviceInfo(String str) {
        return DeviceIdHasher.hashDeviceInfo(str, 8);
    }
}
