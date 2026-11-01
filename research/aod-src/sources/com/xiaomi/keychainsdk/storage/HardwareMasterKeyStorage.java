package com.xiaomi.keychainsdk.storage;

import android.annotation.TargetApi;

/* JADX INFO: loaded from: classes2.dex */
@TargetApi(28)
public class HardwareMasterKeyStorage implements MasterKeyStorage {
    public static final HardwareMasterKeyStorage sInstance = new HardwareMasterKeyStorage();
    public final Object mTransferKeyLock = new Object();

    public static class TransferContextInner implements TransferContext {
    }

    public static HardwareMasterKeyStorage getInstanceIfSupport() {
        return sInstance;
    }
}
