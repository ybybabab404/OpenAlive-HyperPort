package cn.kuaipan.kss;

/* JADX INFO: loaded from: classes.dex */
public interface KssUpload {

    public interface RequestUploadInfo {

        public enum RequestUploadState {
            NeedUpload,
            FileExisted
        }
    }

    public interface UploadResult {
    }

    public interface UploadTransControl {
    }
}
