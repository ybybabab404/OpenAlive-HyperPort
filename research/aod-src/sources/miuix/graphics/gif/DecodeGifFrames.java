package miuix.graphics.gif;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import miuix.io.ResettableInputStream;

/* JADX INFO: compiled from: DecodeGifImageHelper.java */
/* JADX INFO: loaded from: classes2.dex */
class DecodeGifFrames extends Handler {
    public Handler mCallerHandler;
    public DecodeGifImageHelper.GifDecodeResult mDecodeResult;
    public ResettableInputStream mGifSource;
    public HandlerThread mHandlerThread;
    public long mMaxDecodeSize;

    public void decode(int i) {
        if (this.mDecodeResult != null) {
            return;
        }
        this.mDecodeResult = new DecodeGifImageHelper.GifDecodeResult();
        sendMessage(obtainMessage(1, i, 0));
    }

    public void finalize() throws Throwable {
        this.mHandlerThread.quit();
        super.finalize();
    }

    public DecodeGifImageHelper.GifDecodeResult getAndClearDecodeResult() {
        DecodeGifImageHelper.GifDecodeResult gifDecodeResult = this.mDecodeResult;
        this.mDecodeResult = null;
        return gifDecodeResult;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        if (message.what == 1) {
            DecodeGifImageHelper.GifDecodeResult gifDecodeResultDecode = DecodeGifImageHelper.decode(this.mGifSource, this.mMaxDecodeSize, message.arg1);
            DecodeGifImageHelper.GifDecodeResult gifDecodeResult = this.mDecodeResult;
            gifDecodeResult.mGifDecoder = gifDecodeResultDecode.mGifDecoder;
            gifDecodeResult.mIsDecodeOk = gifDecodeResultDecode.mIsDecodeOk;
            this.mCallerHandler.sendEmptyMessage(1);
        }
    }
}
