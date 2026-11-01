package com.miui.aod.widget.notification;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import com.miui.aod.widget.NotificationAnimationView;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import miuix.animation.internal.FolmeCore;

/* JADX INFO: loaded from: classes.dex */
public class NotificationFrameAnimationDrawer implements NotificationAnimationView.AnimationDrawer {
    private static final String TAG = "NotificationFrameAnimationDrawer";
    private final Context mContext;
    private Handler mDecodeHandler;
    private DecodeTask mDecodeTask;
    private HandlerThread mDecodeThread;
    private BitmapInfo mDrawingBitmapInfo;
    private NotificationAnimationView.AnimationDrawer.AnimationStateListener mListener;
    private boolean mRepeatMode;
    private int[] mResArray;
    private final BlockingQueue<BitmapInfo> mBitmapQueue = new ArrayBlockingQueue(4);
    private final Queue<Bitmap> mRecycleBitmapQueue = new ArrayBlockingQueue(2, true);
    private final Object mHandlerLock = new Object();
    private long mLastFrameTime = -1;
    private final int mFrameInterval = 31;
    private final Paint mPaint = new Paint();
    private final Matrix mMatrix = new Matrix();

    public static class BitmapInfo {
        private final boolean isEnd;
        private final Bitmap mBitmap;

        public BitmapInfo(Bitmap bitmap, int i, boolean z) {
            this.mBitmap = bitmap;
            this.isEnd = z;
        }
    }

    public class DecodeTask implements Runnable {
        private final int[] mAnimRes;
        private int mCurrentPosition;
        private boolean mDecoding;

        private DecodeTask(int[] iArr, int i) {
            this.mDecoding = false;
            this.mAnimRes = iArr;
            this.mCurrentPosition = i % iArr.length;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDecoding(boolean z) {
            this.mDecoding = z;
        }

        private boolean shouldFinish() {
            int[] iArr = this.mAnimRes;
            return iArr.length == 0 || !this.mDecoding || this.mCurrentPosition >= iArr.length;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (shouldFinish()) {
                setDecoding(false);
                return;
            }
            Bitmap bitmapDecodeBitmap = NotificationFrameAnimationDrawer.this.decodeBitmap(this.mAnimRes[this.mCurrentPosition]);
            boolean z = this.mCurrentPosition >= this.mAnimRes.length - 1;
            try {
                if (bitmapDecodeBitmap == null) {
                    throw new NullPointerException("bitmap is null");
                }
                bitmapDecodeBitmap.prepareToDraw();
                NotificationFrameAnimationDrawer.this.mBitmapQueue.put(new BitmapInfo(bitmapDecodeBitmap, this.mCurrentPosition, z));
                this.mCurrentPosition++;
                if (NotificationFrameAnimationDrawer.this.mRepeatMode && this.mCurrentPosition >= this.mAnimRes.length) {
                    this.mCurrentPosition = 0;
                }
                if (shouldFinish()) {
                    setDecoding(false);
                    return;
                }
                synchronized (NotificationFrameAnimationDrawer.this.mHandlerLock) {
                    try {
                        if (NotificationFrameAnimationDrawer.this.mDecodeHandler != null) {
                            NotificationFrameAnimationDrawer.this.mDecodeHandler.post(this);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (Exception e) {
                Log.e(NotificationFrameAnimationDrawer.TAG, "error...", e);
            }
        }
    }

    public NotificationFrameAnimationDrawer(Context context) {
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap decodeBitmap(int i) {
        try {
            Bitmap bitmapPoll = this.mRecycleBitmapQueue.size() >= 2 ? this.mRecycleBitmapQueue.poll() : null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            options.inSampleSize = 2;
            options.inBitmap = bitmapPoll;
            return BitmapFactory.decodeResource(this.mContext.getResources(), i, options);
        } catch (Exception e) {
            Log.e(TAG, "error...", e);
            return null;
        }
    }

    private void dequeueBitmap(BitmapInfo bitmapInfo, long j) {
        BitmapInfo bitmapInfo2 = this.mDrawingBitmapInfo;
        if (bitmapInfo2 != null) {
            try {
                if (!this.mRecycleBitmapQueue.offer(bitmapInfo2.mBitmap)) {
                    this.mDrawingBitmapInfo.mBitmap.recycle();
                }
            } catch (Exception e) {
                Log.e(TAG, "error...", e);
            }
        }
        this.mDrawingBitmapInfo = bitmapInfo;
        this.mBitmapQueue.poll();
        this.mLastFrameTime = j;
    }

    private void drawBitmap(Canvas canvas, Bitmap bitmap, Paint paint, Matrix matrix) {
        if (canvas == null || bitmap == null || bitmap.isRecycled()) {
            return;
        }
        matrix.reset();
        int width = bitmap.getWidth();
        int width2 = canvas.getWidth();
        int height = bitmap.getHeight();
        int height2 = canvas.getHeight();
        float f = width2;
        float f2 = (f * 1.0f) / width;
        float f3 = height2;
        matrix.postTranslate(Math.round((width2 - width) * 0.5f), Math.round((height2 - height) * 0.5f));
        matrix.postScale(f2, (1.0f * f3) / height, f / 2.0f, f3 / 2.0f);
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    private void prepareDecodeThread() {
        if (this.mDecodeThread == null) {
            HandlerThread handlerThread = new HandlerThread("aod_frame_animation");
            this.mDecodeThread = handlerThread;
            handlerThread.start();
        }
        synchronized (this.mHandlerLock) {
            try {
                if (this.mDecodeHandler == null) {
                    this.mDecodeHandler = new Handler(this.mDecodeThread.getLooper());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.miui.aod.widget.NotificationAnimationView.AnimationDrawer
    public long getDuration() {
        int[] iArr = this.mResArray;
        if (iArr != null) {
            return ((long) iArr.length) * ((long) this.mFrameInterval);
        }
        return 0L;
    }

    @Override // com.miui.aod.widget.NotificationAnimationView.AnimationDrawer
    public boolean onAnimationDraw(Canvas canvas, long j) {
        NotificationAnimationView.AnimationDrawer.AnimationStateListener animationStateListener;
        BitmapInfo bitmapInfoPeek = this.mBitmapQueue.peek();
        if (bitmapInfoPeek != null) {
            long j2 = this.mLastFrameTime;
            if (j2 == -1 || j - j2 >= ((long) this.mFrameInterval) * FolmeCore.NANOS_TO_MS) {
                dequeueBitmap(bitmapInfoPeek, j);
            }
            drawBitmap(canvas, this.mDrawingBitmapInfo.mBitmap, this.mPaint, this.mMatrix);
            if (this.mDrawingBitmapInfo.isEnd && (animationStateListener = this.mListener) != null && !this.mRepeatMode) {
                animationStateListener.onAnimationEnd();
            }
            if (!this.mRepeatMode && this.mDrawingBitmapInfo.isEnd) {
                return false;
            }
        }
        return true;
    }

    @Override // com.miui.aod.widget.NotificationAnimationView.AnimationDrawer
    public void release() {
        DecodeTask decodeTask = this.mDecodeTask;
        if (decodeTask != null) {
            decodeTask.setDecoding(false);
            this.mDecodeTask = null;
        }
        synchronized (this.mHandlerLock) {
            try {
                Handler handler = this.mDecodeHandler;
                if (handler != null) {
                    handler.removeCallbacksAndMessages(null);
                    this.mDecodeHandler = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        HandlerThread handlerThread = this.mDecodeThread;
        if (handlerThread != null) {
            handlerThread.quit();
            this.mDecodeThread = null;
        }
        if (!this.mBitmapQueue.isEmpty()) {
            while (true) {
                BitmapInfo bitmapInfoPoll = this.mBitmapQueue.poll();
                if (bitmapInfoPoll == null) {
                    break;
                } else if (bitmapInfoPoll.mBitmap != null && !bitmapInfoPoll.mBitmap.isRecycled()) {
                    bitmapInfoPoll.mBitmap.recycle();
                }
            }
        }
        if (!this.mRecycleBitmapQueue.isEmpty()) {
            while (true) {
                Bitmap bitmapPoll = this.mRecycleBitmapQueue.poll();
                if (bitmapPoll == null) {
                    break;
                } else if (!bitmapPoll.isRecycled()) {
                    bitmapPoll.recycle();
                }
            }
        }
        BitmapInfo bitmapInfo = this.mDrawingBitmapInfo;
        if (bitmapInfo != null && bitmapInfo.mBitmap != null && !this.mDrawingBitmapInfo.mBitmap.isRecycled()) {
            this.mDrawingBitmapInfo.mBitmap.recycle();
        }
        setAnimationListener(null);
    }

    @Override // com.miui.aod.widget.NotificationAnimationView.AnimationDrawer
    public void setAnimationListener(NotificationAnimationView.AnimationDrawer.AnimationStateListener animationStateListener) {
        this.mListener = animationStateListener;
    }

    public void setFrameArray(int[] iArr) {
        this.mResArray = iArr;
    }

    @Override // com.miui.aod.widget.NotificationAnimationView.AnimationDrawer
    public void setRepeatMode(boolean z) {
        this.mRepeatMode = z;
    }

    public void startAnimation() {
        if (this.mResArray == null) {
            return;
        }
        this.mLastFrameTime = -1L;
        this.mBitmapQueue.clear();
        prepareDecodeThread();
        DecodeTask decodeTask = new DecodeTask(this.mResArray, 0);
        this.mDecodeTask = decodeTask;
        decodeTask.setDecoding(true);
        this.mDecodeHandler.post(this.mDecodeTask);
    }
}
