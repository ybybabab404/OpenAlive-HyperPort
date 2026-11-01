package com.xiaomi.opensdk.file.sdk;

import cn.kuaipan.android.http.IKscTransferListener;
import cn.kuaipan.android.kss.KssDef;
import com.xiaomi.opensdk.file.model.MiCloudFileListener;

/* JADX INFO: loaded from: classes2.dex */
public class KssMasterRef implements KssDef {

    public class KscTransferListener extends IKscTransferListener.KscTransferListener {
        public MiCloudFileListener mMiCloudFileListener;

        @Override // cn.kuaipan.android.http.IKscTransferListener.KscTransferListener
        public void onDataReceived(long j, long j2) {
            MiCloudFileListener miCloudFileListener = this.mMiCloudFileListener;
            if (miCloudFileListener != null) {
                miCloudFileListener.onDataReceived(j, j2);
            }
        }

        @Override // cn.kuaipan.android.http.IKscTransferListener.KscTransferListener
        public void onDataSended(long j, long j2) {
            MiCloudFileListener miCloudFileListener = this.mMiCloudFileListener;
            if (miCloudFileListener != null) {
                miCloudFileListener.onDataSended(j, j2);
            }
        }
    }
}
