package com.xiaomi.keychainsdk.storage;

/* JADX INFO: loaded from: classes2.dex */
public class MasterKeyStorageManager {
    public static final MasterKeyStorageManager sInstance = new MasterKeyStorageManager();
    public final SoftwareMasterKeyStorage mSoftwareStorage = SoftwareMasterKeyStorage.getInstance();
    public final HardwareMasterKeyStorage mHardwareStorage = HardwareMasterKeyStorage.getInstanceIfSupport();

    public class KeyImporterInner implements KeyImporter {
    }
}
