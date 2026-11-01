package miuix.graphics.gif;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.IOException;
import java.util.List;
import miuix.io.ResettableInputStream;

/* JADX INFO: loaded from: classes2.dex */
public class DecodeGifImageHelper {
    public DecodeGifFrames mDecodeGifFrames;
    public List mFrames;
    public int mMaxFrames;
    public int mRealFrameCount;

    /* JADX INFO: renamed from: miuix.graphics.gif.DecodeGifImageHelper$1, reason: invalid class name */
    class AnonymousClass1 extends Handler {
        public final /* synthetic */ DecodeGifImageHelper this$0;

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            DecodeGifImageHelper decodeGifImageHelper = this.this$0;
            if (decodeGifImageHelper.handleDecodeFramesResult(decodeGifImageHelper.mDecodeGifFrames.getAndClearDecodeResult())) {
                this.this$0.decodeNextFrames();
            }
        }
    }

    public static class GifDecodeResult {
        public GifDecoder mGifDecoder;
        public boolean mIsDecodeOk;
    }

    public static class GifFrame {
        public int mDuration;
        public Bitmap mImage;
        public int mIndex;

        public GifFrame(Bitmap bitmap, int i, int i2) {
            this.mImage = bitmap;
            this.mDuration = i;
            this.mIndex = i2;
        }
    }

    public static GifDecodeResult decode(ResettableInputStream resettableInputStream, long j, int i) {
        GifDecodeResult gifDecodeResult = new GifDecodeResult();
        gifDecodeResult.mGifDecoder = null;
        gifDecodeResult.mIsDecodeOk = false;
        try {
            resettableInputStream.reset();
            GifDecoder gifDecoder = new GifDecoder();
            gifDecodeResult.mGifDecoder = gifDecoder;
            gifDecoder.setStartFrame(i);
            gifDecoder.setMaxDecodeSize(j);
            gifDecodeResult.mIsDecodeOk = gifDecoder.read(resettableInputStream) == 0;
            resettableInputStream.close();
        } catch (IOException unused) {
        }
        return gifDecodeResult;
    }

    public final int calcFrameIndex(int i) {
        int i2 = this.mRealFrameCount;
        return i2 == 0 ? i : i % i2;
    }

    public void decodeNextFrames() {
        int size = this.mFrames.size();
        int i = this.mMaxFrames;
        if (i <= 3) {
            if (size > 2) {
                return;
            }
        } else if (size > i / 2) {
            return;
        }
        this.mDecodeGifFrames.decode(calcFrameIndex(getLastFrameIndex() + 1));
    }

    public final int getLastFrameIndex() {
        List list = this.mFrames;
        return ((GifFrame) list.get(list.size() - 1)).mIndex;
    }

    public boolean handleDecodeFramesResult(GifDecodeResult gifDecodeResult) {
        GifDecoder gifDecoder;
        if (!gifDecodeResult.mIsDecodeOk || (gifDecoder = gifDecodeResult.mGifDecoder) == null) {
            return false;
        }
        Log.d("dumpFrameIndex", String.format("Thread#%d: decoded %d frames [%s] [%d]", Long.valueOf(Thread.currentThread().getId()), Integer.valueOf(gifDecodeResult.mGifDecoder.getFrameCount()), Boolean.valueOf(gifDecodeResult.mIsDecodeOk), Integer.valueOf(this.mRealFrameCount)));
        if (gifDecoder.isDecodeToTheEnd()) {
            this.mRealFrameCount = gifDecoder.getRealFrameCount();
        }
        int frameCount = gifDecoder.getFrameCount();
        if (frameCount > 0) {
            int lastFrameIndex = getLastFrameIndex();
            for (int i = 0; i < frameCount; i++) {
                this.mFrames.add(new GifFrame(gifDecoder.getFrame(i), gifDecoder.getDelay(i), calcFrameIndex(lastFrameIndex + 1 + i)));
            }
        }
        return true;
    }
}
