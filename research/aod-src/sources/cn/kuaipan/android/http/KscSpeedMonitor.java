package cn.kuaipan.android.http;

/* JADX INFO: loaded from: classes.dex */
public class KscSpeedMonitor {
    public final String mHost;
    public long mLatestUpdate;
    public final KscSpeedManager mManager;

    public void recode(long j) {
        if (this.mManager != null) {
            long jCurrent = KscSpeedManager.current();
            this.mManager.recoder(this.mHost, this.mLatestUpdate, jCurrent, j);
            this.mLatestUpdate = jCurrent;
        }
    }
}
