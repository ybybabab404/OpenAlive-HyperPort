package com.xiaomi.micloudkeybag;

import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes2.dex */
public abstract class OnKeyBagCallFinishedListener<T> extends IOnKeyBagCallFinishedListener.Stub {
    public CountDownLatch mCountDownLatch;
    public Object mResult;
    public int mResultCode;

    public void onDecryptFinished(int i, byte[] bArr) {
        throw new RuntimeException("Not implemented!");
    }

    public void onEncryptFinished(int i, byte[] bArr) {
        throw new RuntimeException("Not implemented!");
    }

    public void onIsSupportedFinished(int i, boolean z) {
        throw new RuntimeException("Not implemented!");
    }

    public void setResult(int i, Object obj) {
        this.mResultCode = i;
        this.mResult = obj;
        this.mCountDownLatch.countDown();
    }
}
