package cn.kuaipan.android.kss.upload;

import cn.kuaipan.android.kss.IKssUploadRequestResult;
import cn.kuaipan.android.kss.KssDef;

/* JADX INFO: loaded from: classes.dex */
public class KssUploadInfo implements KssDef {
    public final UploadFileInfo mFileInfo;
    public final long mGenerateTime;
    public final IKssUploadRequestResult mRequestResult;
    public String mUploadId;
    public boolean mBroken = false;
    public long mMaxChunkSize = 4194304;
    public ServerExpect mExpectInfo = null;

    public KssUploadInfo(UploadFileInfo uploadFileInfo, IKssUploadRequestResult iKssUploadRequestResult, long j) {
        this.mFileInfo = uploadFileInfo;
        this.mRequestResult = iKssUploadRequestResult;
        this.mGenerateTime = j;
    }

    public UploadFileInfo getFileInfo() {
        return this.mFileInfo;
    }

    public long getGenerateTime() {
        return this.mGenerateTime;
    }

    public IKssUploadRequestResult getRequestResult() {
        return this.mRequestResult;
    }

    public String getUploadId() {
        return this.mUploadId;
    }

    public void setUploadId(String str) {
        this.mUploadId = str;
    }
}
