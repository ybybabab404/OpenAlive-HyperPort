package cn.kuaipan.android.http;

import com.xiaomi.opensdk.file.model.MiCloudTransferStopper;

/* JADX INFO: loaded from: classes.dex */
public interface KssTransferStopper {

    public static class KssTransferStopperFromMiCloudTransferStopper implements KssTransferStopper {
        public final MiCloudTransferStopper mMiCloudTransferStopper;

        @Override // cn.kuaipan.android.http.KssTransferStopper
        public boolean checkStop() {
            return this.mMiCloudTransferStopper.checkStop();
        }
    }

    boolean checkStop();
}
