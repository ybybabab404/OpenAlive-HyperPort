package cn.kuaipan.android.kss;

import cn.kuaipan.android.http.IKscTransferListener;

/* JADX INFO: loaded from: classes.dex */
public class FileTranceListener {
    public long mOffset;
    public final boolean mSendMode;
    public final IKscTransferListener mTotalListener;

    public class ChunkListaner implements IKscTransferListener {
        public FileTranceListener mParent;
        public long mReceiveOffset;
        public long mSendOffset;

        @Override // cn.kuaipan.android.http.IKscTransferListener
        public void received(long j) {
            this.mParent.received(j);
            this.mReceiveOffset += j;
        }

        @Override // cn.kuaipan.android.http.IKscTransferListener
        public void sended(long j) {
            this.mParent.sended(j);
            this.mSendOffset += j;
        }

        @Override // cn.kuaipan.android.http.IKscTransferListener
        public void setReceivePos(long j) {
            received(j - this.mReceiveOffset);
        }

        @Override // cn.kuaipan.android.http.IKscTransferListener
        public void setReceiveTotal(long j) {
            this.mParent.setReceiveTotal(j);
        }

        @Override // cn.kuaipan.android.http.IKscTransferListener
        public void setSendPos(long j) {
            sended(j - this.mSendOffset);
        }

        @Override // cn.kuaipan.android.http.IKscTransferListener
        public void setSendTotal(long j) {
            this.mParent.setSendTotal(j);
        }
    }

    public final void received(long j) {
        if (this.mSendMode) {
            this.mOffset += j;
        }
        this.mTotalListener.received(j);
    }

    public final void sended(long j) {
        if (!this.mSendMode) {
            this.mOffset += j;
        }
        this.mTotalListener.sended(j);
    }

    public void setReceiveTotal(long j) {
        if (this.mSendMode) {
            this.mTotalListener.setReceiveTotal(this.mOffset + j);
        }
    }

    public void setSendTotal(long j) {
        if (this.mSendMode) {
            return;
        }
        this.mTotalListener.setSendTotal(this.mOffset + j);
    }
}
