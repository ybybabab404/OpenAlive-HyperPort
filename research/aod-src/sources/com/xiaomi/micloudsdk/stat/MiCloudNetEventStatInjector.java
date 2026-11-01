package com.xiaomi.micloudsdk.stat;

/* JADX INFO: loaded from: classes2.dex */
public class MiCloudNetEventStatInjector {
    public IMiCloudNetEventStatCallback mIMiCloudStatCallback;
    public boolean mIsDownloadCallbackInitialized;
    public boolean mIsInitialized;

    public static class MiCloudNetEventStatInjectorHolder {
        public static final MiCloudNetEventStatInjector instance = new MiCloudNetEventStatInjector();
    }

    public MiCloudNetEventStatInjector() {
        this.mIsInitialized = false;
        this.mIsDownloadCallbackInitialized = false;
    }

    public static MiCloudNetEventStatInjector getInstance() {
        return MiCloudNetEventStatInjectorHolder.instance;
    }

    public void addNetFailedEvent(NetFailedStatParam netFailedStatParam) {
        IMiCloudNetEventStatCallback iMiCloudNetEventStatCallback = this.mIMiCloudStatCallback;
        if (iMiCloudNetEventStatCallback != null) {
            iMiCloudNetEventStatCallback.onAddNetFailedEvent(netFailedStatParam);
        }
    }

    public void addNetSuccessEvent(NetSuccessStatParam netSuccessStatParam) {
        IMiCloudNetEventStatCallback iMiCloudNetEventStatCallback = this.mIMiCloudStatCallback;
        if (iMiCloudNetEventStatCallback != null) {
            iMiCloudNetEventStatCallback.onAddNetSuccessEvent(netSuccessStatParam);
        }
    }
}
