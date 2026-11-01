package com.xiaomi.micloudsdk.stat;

/* JADX INFO: loaded from: classes2.dex */
@Deprecated
public class MiCloudStatManager {
    public boolean mEnable;
    public boolean mIsInitialized;

    public static class Holder {
        public static final MiCloudStatManager _instance = new MiCloudStatManager();
    }

    public MiCloudStatManager() {
        this.mEnable = false;
        this.mIsInitialized = false;
    }
}
