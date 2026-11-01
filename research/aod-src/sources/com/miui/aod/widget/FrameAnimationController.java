package com.miui.aod.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.Choreographer;
import android.view.TextureView;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import miuix.animation.internal.FolmeCore;

/* JADX INFO: loaded from: classes.dex */
public class FrameAnimationController {
    public AnimationCallBack mAnimationCallback;
    public final Context mContext;
    public Handler mDecodeHandler;
    public DecodeTask mDecodeTask;
    public HandlerThread mDecodeThread;
    public FrameUpdate mFrameUpdate;
    public final TextureView mTextureView;
    public volatile int mFrameInterval = 32;
    public final BlockingQueue mBitmapQueue = new ArrayBlockingQueue(5);
    public final Queue mRecycleBitmapQueue = new ArrayBlockingQueue(3, true);
    public final Object mHandlerLock = new Object();
    public final Handler mMainHandler = new Handler(Looper.getMainLooper());

    public interface AnimationCallBack {
        default void onAnimationEnd() {
        }

        default void onAnimationStart() {
        }
    }

    public static class BitmapInfo {
        public final boolean isEnd;
        public final Bitmap mBitmap;
        public final int mPosition;

        public BitmapInfo(Bitmap bitmap, int i, boolean z) {
            this.mBitmap = bitmap;
            this.mPosition = i;
            this.isEnd = z;
        }
    }

    public class DecodeTask implements Runnable {
        public final int[] mAnimRes;
        public final String[] mAnimResPath;
        public int mCurrentPosition;
        public boolean mDecoding;
        public final boolean mFromFile;

        public DecodeTask(int[] iArr, int i) {
            this.mDecoding = false;
            this.mAnimRes = iArr;
            this.mAnimResPath = null;
            this.mCurrentPosition = i % iArr.length;
            this.mFromFile = false;
        }

        public DecodeTask(String[] strArr, int i) {
            this.mDecoding = false;
            this.mAnimResPath = strArr;
            this.mAnimRes = null;
            this.mCurrentPosition = i % strArr.length;
            this.mFromFile = true;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (shouldFinish()) {
                setDecoding(false);
                return;
            }
            Bitmap bitmapDecodeBitmap = null;
            if (this.mFromFile) {
                String[] strArr = this.mAnimResPath;
                if (strArr != null) {
                    bitmapDecodeBitmap = FrameAnimationController.this.decodeBitmap(strArr[this.mCurrentPosition]);
                }
            } else {
                int[] iArr = this.mAnimRes;
                if (iArr != null) {
                    bitmapDecodeBitmap = FrameAnimationController.this.decodeBitmap(iArr[this.mCurrentPosition]);
                }
            }
            if (bitmapDecodeBitmap != null) {
                try {
                    boolean z = this.mCurrentPosition >= (this.mFromFile ? this.mAnimResPath.length : this.mAnimRes.length - 1);
                    bitmapDecodeBitmap.prepareToDraw();
                    FrameAnimationController.this.mBitmapQueue.put(new BitmapInfo(bitmapDecodeBitmap, this.mCurrentPosition, z));
                } catch (Exception e) {
                    Log.e("FrameAnimationController", "error...", e);
                }
            }
            this.mCurrentPosition++;
            if (shouldFinish()) {
                setDecoding(false);
                return;
            }
            synchronized (FrameAnimationController.this.mHandlerLock) {
                try {
                    if (FrameAnimationController.this.mDecodeHandler != null) {
                        FrameAnimationController.this.mDecodeHandler.post(this);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setDecoding(boolean z) {
            this.mDecoding = z;
        }

        public final boolean shouldFinish() {
            if (this.mFromFile) {
                String[] strArr = this.mAnimResPath;
                return strArr == null || strArr.length == 0 || !this.mDecoding || this.mCurrentPosition >= strArr.length;
            }
            int[] iArr = this.mAnimRes;
            return iArr == null || iArr.length == 0 || !this.mDecoding || this.mCurrentPosition >= iArr.length;
        }
    }

    public class FrameUpdate implements Choreographer.FrameCallback {
        public volatile boolean mDrawing;
        public BitmapInfo mDrawingBitmapInfo;
        public long mLastFrameTime;
        public final Matrix mMatrix;
        public final Paint mPaint;

        public FrameUpdate() {
            this.mLastFrameTime = -1L;
            this.mPaint = new Paint();
            this.mMatrix = new Matrix();
        }

        public final BitmapInfo dequeueBitmap(BitmapInfo bitmapInfo) {
            BitmapInfo bitmapInfo2 = this.mDrawingBitmapInfo;
            if (bitmapInfo2 != null) {
                try {
                    Bitmap bitmap = bitmapInfo2.mBitmap;
                    if (bitmap != null && !FrameAnimationController.this.mRecycleBitmapQueue.contains(bitmap) && !FrameAnimationController.this.mRecycleBitmapQueue.offer(bitmap)) {
                        bitmap.recycle();
                    }
                } catch (Exception e) {
                    Log.e("FrameAnimationController", "error...", e);
                }
            }
            FrameAnimationController.this.mBitmapQueue.poll();
            return bitmapInfo;
        }

        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long j) {
            BitmapInfo bitmapInfo = (BitmapInfo) FrameAnimationController.this.mBitmapQueue.peek();
            if (bitmapInfo == null) {
                if (this.mDrawing) {
                    Choreographer.getInstance().postFrameCallback(this);
                    return;
                }
                return;
            }
            long j2 = this.mLastFrameTime;
            if (j2 == -1) {
                Handler handler = FrameAnimationController.this.mMainHandler;
                final FrameAnimationController frameAnimationController = FrameAnimationController.this;
                handler.post(new Runnable() { // from class: com.miui.aod.widget.FrameAnimationController$FrameUpdate$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        frameAnimationController.notifyAnimationStart();
                    }
                });
            } else {
                long j3 = j - j2;
                if (this.mDrawing && j3 < ((long) FrameAnimationController.this.mFrameInterval) * FolmeCore.NANOS_TO_MS) {
                    Choreographer.getInstance().postFrameCallback(this);
                    return;
                }
            }
            BitmapInfo bitmapInfoDequeueBitmap = dequeueBitmap(bitmapInfo);
            this.mDrawingBitmapInfo = bitmapInfoDequeueBitmap;
            FrameAnimationController.this.drawBitmap(bitmapInfoDequeueBitmap.mBitmap, this.mPaint, this.mMatrix, bitmapInfo.mPosition);
            this.mLastFrameTime = j;
            if (bitmapInfo.isEnd) {
                Handler handler2 = FrameAnimationController.this.mMainHandler;
                final FrameAnimationController frameAnimationController2 = FrameAnimationController.this;
                handler2.post(new Runnable() { // from class: com.miui.aod.widget.FrameAnimationController$FrameUpdate$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        frameAnimationController2.notifyAnimationEnd();
                    }
                });
            }
            if (!this.mDrawing || bitmapInfo.isEnd) {
                return;
            }
            Choreographer.getInstance().postFrameCallback(this);
        }

        public void setDrawing(boolean z) {
            this.mDrawing = z;
            if (this.mDrawing) {
                return;
            }
            Choreographer.getInstance().removeFrameCallback(this);
        }
    }

    public FrameAnimationController(TextureView textureView, TextureView.SurfaceTextureListener surfaceTextureListener) {
        this.mTextureView = textureView;
        textureView.setSurfaceTextureListener(surfaceTextureListener);
        this.mContext = textureView.getContext();
    }

    public final Bitmap decodeBitmap(int i) {
        try {
            Bitmap bitmap = this.mRecycleBitmapQueue.size() >= 2 ? (Bitmap) this.mRecycleBitmapQueue.poll() : null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            options.inBitmap = bitmap;
            return BitmapFactory.decodeResource(this.mContext.getResources(), i, options);
        } catch (Exception e) {
            Log.e("FrameAnimationController", "error...", e);
            return null;
        }
    }

    public final Bitmap decodeBitmap(String str) {
        try {
            Bitmap bitmap = this.mRecycleBitmapQueue.size() >= 2 ? (Bitmap) this.mRecycleBitmapQueue.poll() : null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            options.inBitmap = bitmap;
            return BitmapFactory.decodeFile(str, options);
        } catch (Exception e) {
            Log.e("FrameAnimationController", "error...", e);
            return null;
        }
    }

    public final void drawBitmap(Bitmap bitmap, Paint paint, Matrix matrix, int i) {
        Canvas canvasLockCanvas = this.mTextureView.lockCanvas();
        if (canvasLockCanvas == null || bitmap == null) {
            return;
        }
        try {
            matrix.reset();
            int width = bitmap.getWidth();
            int width2 = this.mTextureView.getWidth();
            int height = bitmap.getHeight();
            int height2 = this.mTextureView.getHeight();
            float fRound = Math.round((width2 - width) * 0.5f);
            float fRound2 = Math.round((height2 - height) * 0.5f);
            float f = width2;
            float f2 = (f * 1.0f) / width;
            float f3 = height2;
            float fMin = Math.min(f2, (1.0f * f3) / height);
            matrix.postTranslate(fRound, fRound2);
            matrix.postScale(fMin, fMin, f / 2.0f, f3 / 2.0f);
            canvasLockCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
            canvasLockCanvas.drawBitmap(bitmap, matrix, paint);
        } finally {
            unlockCanvasAndPostSafely(canvasLockCanvas);
        }
    }

    public void notifyAnimationEnd() {
        AnimationCallBack animationCallBack = this.mAnimationCallback;
        if (animationCallBack != null) {
            animationCallBack.onAnimationEnd();
        }
    }

    public void notifyAnimationStart() {
        AnimationCallBack animationCallBack = this.mAnimationCallback;
        if (animationCallBack != null) {
            animationCallBack.onAnimationStart();
        }
    }

    public final void prepareDecodeThread() {
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
                BitmapInfo bitmapInfo = (BitmapInfo) this.mBitmapQueue.poll();
                if (bitmapInfo == null) {
                    break;
                } else if (bitmapInfo.mBitmap != null && !bitmapInfo.mBitmap.isRecycled()) {
                    bitmapInfo.mBitmap.recycle();
                }
            }
        }
        if (!this.mRecycleBitmapQueue.isEmpty()) {
            while (true) {
                Bitmap bitmap = (Bitmap) this.mRecycleBitmapQueue.poll();
                if (bitmap == null) {
                    break;
                } else if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
        }
        FrameUpdate frameUpdate = this.mFrameUpdate;
        if (frameUpdate != null) {
            frameUpdate.setDrawing(false);
            Choreographer.getInstance().removeFrameCallback(this.mFrameUpdate);
            this.mFrameUpdate = null;
        }
        this.mBitmapQueue.clear();
    }

    public void setAnimationCallBack(AnimationCallBack animationCallBack) {
        this.mAnimationCallback = animationCallBack;
    }

    public void setFrameInterval(int i) {
        if (i < 0) {
            throw new UnsupportedOperationException("frameInterval < 0");
        }
        this.mFrameInterval = i;
    }

    public void startAnimation(int[] iArr, int i) {
        startAnimationInternal(new DecodeTask(iArr, i));
    }

    public void startAnimation(String[] strArr, int i) {
        startAnimationInternal(new DecodeTask(strArr, i));
    }

    public final void startAnimationInternal(DecodeTask decodeTask) {
        release();
        prepareDecodeThread();
        this.mBitmapQueue.clear();
        this.mDecodeTask = decodeTask;
        decodeTask.setDecoding(true);
        this.mDecodeHandler.post(this.mDecodeTask);
        FrameUpdate frameUpdate = new FrameUpdate();
        this.mFrameUpdate = frameUpdate;
        frameUpdate.setDrawing(true);
        Choreographer.getInstance().postFrameCallback(this.mFrameUpdate);
    }

    public final void unlockCanvasAndPostSafely(Canvas canvas) {
        TextureView textureView = this.mTextureView;
        if (textureView == null || !textureView.isAvailable()) {
            return;
        }
        this.mTextureView.unlockCanvasAndPost(canvas);
    }
}
