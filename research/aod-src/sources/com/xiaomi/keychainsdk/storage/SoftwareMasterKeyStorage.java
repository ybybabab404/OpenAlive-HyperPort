package com.xiaomi.keychainsdk.storage;

/* JADX INFO: loaded from: classes2.dex */
public class SoftwareMasterKeyStorage implements MasterKeyStorage {
    public static final SoftwareMasterKeyStorage sInstance = new SoftwareMasterKeyStorage();

    public static class TransferContextInner implements TransferContext {
    }

    public static SoftwareMasterKeyStorage getInstance() {
        return sInstance;
    }
}
