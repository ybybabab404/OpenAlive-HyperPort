package cn.kuaipan.kss;

/* JADX INFO: loaded from: classes.dex */
public interface KssDownload {

    public interface BlockDownloadInfo {
    }

    public interface DownloadTransControl {

        public enum EndState {
            Completed,
            Interrupt,
            Transing,
            DataCorrupted
        }
    }

    public interface RequestDownloadInfo {
    }
}
