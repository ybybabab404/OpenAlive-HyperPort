package com.miui.fastplayer;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Surface;
import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
public class FastPlayer {
    public static final int ALPHA_VIDEO_EFFECT = 12;
    public static final int BLUR_EFFECT = 8;
    public static final int BRIGHTNESS_EFFECT = 7;
    public static final int EVENT_END_PLAYER = 6;
    public static final int EVENT_END_STREAM = 5;
    public static final int EVENT_FIRST_FRAME = 2;
    public static final int EVENT_FRAME_PTS = 13;
    public static final int EVENT_OPENDECODER_FAIL = 9;
    public static final int EVENT_PAUSE = 4;
    public static final int EVENT_PRODUCE_PROGRESS = 11;
    public static final int EVENT_PRODUCE_STATE = 12;
    public static final int EVENT_READY = 1;
    public static final int EVENT_RUN = 3;
    public static final int EVENT_SOFTDECODER = 7;
    public static final int EVENT_START = 10;
    public static final int EVENT_UNDEFINED = 0;
    public static final int EVENT_UNSUPPORT = 8;
    public static final int METADATA_KEY_ALBUM = 1;
    public static final int METADATA_KEY_ALBUMARTIST = 13;
    public static final int METADATA_KEY_ARTIST = 2;
    public static final int METADATA_KEY_AUTHOR = 3;
    public static final int METADATA_KEY_BITRATE = 20;
    public static final int METADATA_KEY_CAPTURE_FRAMERATE = 25;
    public static final int METADATA_KEY_CAPTURE_ORIGIN_TRACK = 26;
    public static final int METADATA_KEY_CD_TRACK_NUMBER = 0;
    public static final int METADATA_KEY_CHANNELS = 101;
    public static final int METADATA_KEY_CODEC_NAME = 103;
    public static final int METADATA_KEY_COMPILATION = 15;
    public static final int METADATA_KEY_COMPOSER = 4;
    public static final int METADATA_KEY_COM_ANDROID_MANUFACTURER = 56;
    public static final int METADATA_KEY_COM_ANDROID_MODEL = 57;
    public static final int METADATA_KEY_COM_ANDROID_VERSION = 55;
    public static final int METADATA_KEY_DATE = 5;
    public static final int METADATA_KEY_DISC_NUMBER = 14;
    public static final int METADATA_KEY_DURATION = 9;
    public static final int METADATA_KEY_EXIST_MI_RECORD_LOG = 58;
    public static final int METADATA_KEY_EXIST_MI_RECORD_MIMOVIE = 59;
    public static final int METADATA_KEY_EXIST_MI_SUBTITLE = 53;
    public static final int METADATA_KEY_EXIST_MI_VIDEO_TAGS = 54;
    public static final int METADATA_KEY_GENRE = 6;
    public static final int METADATA_KEY_HAS_AUDIO = 16;
    public static final int METADATA_KEY_HAS_VIDEO = 17;
    public static final int METADATA_KEY_IS_DRM = 22;
    public static final int METADATA_KEY_LANGUAGE = 102;
    public static final int METADATA_KEY_LOCATION = 23;
    public static final int METADATA_KEY_MIMETYPE = 12;
    public static final int METADATA_KEY_NUM_AUDIO_TRACKS = 61;
    public static final int METADATA_KEY_NUM_TRACKS = 10;
    public static final int METADATA_KEY_NUM_VIDEO_TRACKS = 51;
    public static final int METADATA_KEY_PROFILE = 104;
    public static final int METADATA_KEY_REAL_8K_VIDEO = 62;
    public static final int METADATA_KEY_REAL_FRAMERATE = 50;
    public static final int METADATA_KEY_SAMPLERATE = 100;
    public static final int METADATA_KEY_TIMED_TEXT_LANGUAGES = 21;
    public static final int METADATA_KEY_TITLE = 7;
    public static final int METADATA_KEY_VIDEO_DURATION = 60;
    public static final int METADATA_KEY_VIDEO_HDR = 52;
    public static final int METADATA_KEY_VIDEO_HEIGHT = 19;
    public static final int METADATA_KEY_VIDEO_ROTATION = 24;
    public static final int METADATA_KEY_VIDEO_WIDTH = 18;
    public static final int METADATA_KEY_WRITER = 11;
    public static final int METADATA_KEY_XIAOMI_VIDEO_TYPE = 200;
    public static final int METADATA_KEY_YEAR = 8;
    public static final int MORUGLASS_EFFECT = 5;
    public static final int MORU_COMGLASS_EFFECT = 11;
    public static final int MORU_PRISMGLASS_EFFECT = 10;
    public static final int PLAYER_END = 4;
    public static final int PLAYER_PAUSE = 3;
    public static final int PLAYER_READY = 1;
    public static final int PLAYER_RUN = 2;
    public static final int PLAYER_UNDEFINED = 0;
    public static final int POOR_BLUR_EFFECT = 9;
    public static final int RECTGLASS_EFFECT = 6;
    private static final String TAG = "FastPlayer";
    public static final int TARGET_SIZE_MICRO_THUMBNAIL = 96;
    public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT = 1;
    public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING = 2;
    private Condition mCondition;
    private Handler mGLHandler;
    private Object mGLThreadLock;
    private boolean mIsGLThreadStart;
    private boolean mIsPlay;
    private long mLastRenderTime;
    private ReentrantLock mLock;
    private Looper mLooper;
    private long mMaxFpsTime;
    private long mNativeContext;
    private boolean mNeedPauseRender;
    private OpenGLCallback mOpenGLCallback;
    private String mOutputPath;
    private boolean mPauseRender;
    private boolean mRenderPic;
    private Thread mRenderThread;
    private float[] mSTMatrix;
    private Surface mSurface;
    private SurfaceTexture mSurfaceTexture;
    private VideoFrameReleaseHelper mVideoFrameReleaseHelper;
    private boolean mRelease = false;
    private MediaEncoder mMediaEncoder = null;
    private float mFrameRate = 0.0f;
    private int mHeight = 0;
    private int mWidth = 0;
    private int mDataSourceId = -1;
    private Surface mSurfaceInput = null;
    private Surface mSurfaceInput2 = null;
    private Surface mSurfaceInput3 = null;
    private int mRenderMode = 0;
    private Bitmap mBitmap = null;

    public interface ChangeOpenGLSurfaceListener {
        void onOpenGLSurfaceChanged(boolean z);
    }

    public static final class ChangeOpenGLSurfaceParam {
        public boolean mChange;
        public boolean mChange2;
        public boolean mChange3;
        public ChangeOpenGLSurfaceListener mListener;
        public Surface mSurface;
        public Surface mSurface2;
        public Surface mSurface3;

        public ChangeOpenGLSurfaceParam(Surface surface, boolean z, Surface surface2, boolean z2, Surface surface3, boolean z3, ChangeOpenGLSurfaceListener changeOpenGLSurfaceListener) {
            this.mSurface = surface;
            this.mSurface2 = surface2;
            this.mSurface3 = surface3;
            this.mChange = z;
            this.mChange2 = z2;
            this.mChange3 = z3;
            this.mListener = changeOpenGLSurfaceListener;
        }
    }

    public interface FastPlayerListener {
        int playerModeChange(int i, int i2, long j);
    }

    public interface OpenGLCallback {
        void onFrameRendered();
    }

    public static final class OpenGLVisibleItem {
        public boolean mVisible;
        public boolean mVisible2;
        public boolean mVisible3;

        public OpenGLVisibleItem(boolean z, boolean z2, boolean z3) {
            this.mVisible = z;
            this.mVisible2 = z2;
            this.mVisible3 = z3;
        }
    }

    public static final class ProduceErrorReason {
        public static final long AI_CONVERT_TO_RGB_FAILED = 2;
        public static final long AI_INIT_FAILD = 1;
        public static final long AI_INVALID_INPUT = 3;
        public static final long AI_INVALID_OUTPUT = 4;
        public static final long AI_NOT_SUPPORT = 6;
        public static final long AI_SEG_FAILED = 5;
        public static final long CANCEL = 1000;
        public static final long DRAIN_OUT_FAILED = 101;
        public static final long INIT_DECODER_FAILED = 100;
        public static final long NONE = 0;
    }

    static {
        try {
            System.loadLibrary("miffmpeg_fastplayer");
            System.loadLibrary("fastplayer");
            native_init();
        } catch (Throwable th) {
            Log.w(TAG, "Unable to load the library: " + th);
        }
    }

    public FastPlayer(Context context) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mLock = reentrantLock;
        this.mCondition = reentrantLock.newCondition();
        this.mIsPlay = false;
        this.mOutputPath = null;
        this.mPauseRender = false;
        this.mRenderPic = false;
        this.mLastRenderTime = -1L;
        this.mMaxFpsTime = 0L;
        this.mNeedPauseRender = false;
        this.mRenderThread = null;
        this.mGLHandler = null;
        this.mLooper = null;
        this.mVideoFrameReleaseHelper = null;
        this.mGLThreadLock = new Object();
        this.mIsGLThreadStart = false;
        this.mOpenGLCallback = null;
        this.mSurfaceTexture = null;
        this.mSurface = null;
        this.mSTMatrix = new float[16];
        VideoFrameReleaseHelper videoFrameReleaseHelper = new VideoFrameReleaseHelper(context);
        this.mVideoFrameReleaseHelper = videoFrameReleaseHelper;
        videoFrameReleaseHelper.start();
        try {
            native_setup(this.mVideoFrameReleaseHelper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0030 A[Catch: all -> 0x0026, TryCatch #0 {, blocks: (B:4:0x001a, B:6:0x001e, B:12:0x002c, B:14:0x0030, B:15:0x0037, B:11:0x0029), top: B:20:0x001a, inners: #1 }] */
    private void HandleDataThread() {
        Thread thread = new Thread(new Runnable() { // from class: com.miui.fastplayer.FastPlayer.2
            @Override // java.lang.Runnable
            public void run() {
                synchronized (FastPlayer.this.mGLThreadLock) {
                    FastPlayer.this.mIsGLThreadStart = true;
                    FastPlayer.this.mGLThreadLock.notifyAll();
                }
                boolean z = true;
                while (!FastPlayer.this.mRelease) {
                    if (z) {
                        try {
                            if (FastPlayer.this.mIsPlay) {
                                FastPlayer fastPlayer = FastPlayer.this;
                                fastPlayer.setOpenGLSurface(fastPlayer.mSurfaceInput, null, FastPlayer.this.mBitmap, FastPlayer.this.mRenderMode);
                                int i = FastPlayer.this.gettextureid();
                                Log.e(FastPlayer.TAG, "setSurface textureid: " + i);
                                FastPlayer.this.mSurfaceTexture = new SurfaceTexture(i);
                                FastPlayer.this.mSurfaceTexture.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() { // from class: com.miui.fastplayer.FastPlayer.2.1
                                    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
                                    public synchronized void onFrameAvailable(SurfaceTexture surfaceTexture) {
                                        ReentrantLock reentrantLock;
                                        try {
                                            try {
                                                FastPlayer.this.mLock.lock();
                                                FastPlayer.this.mRenderPic = true;
                                                FastPlayer.this.mCondition.signal();
                                                reentrantLock = FastPlayer.this.mLock;
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                reentrantLock = FastPlayer.this.mLock;
                                            }
                                            reentrantLock.unlock();
                                        } catch (Throwable th) {
                                            FastPlayer.this.mLock.unlock();
                                            throw th;
                                        }
                                    }
                                });
                                FastPlayer.this.mSurface = new Surface(FastPlayer.this.mSurfaceTexture);
                                FastPlayer fastPlayer2 = FastPlayer.this;
                                fastPlayer2.setSurface(fastPlayer2.mSurface);
                            } else {
                                FastPlayer fastPlayer3 = FastPlayer.this;
                                fastPlayer3.mFrameRate = Float.parseFloat(fastPlayer3.extractMetadata(50, fastPlayer3.mDataSourceId));
                                FastPlayer fastPlayer4 = FastPlayer.this;
                                fastPlayer4.mHeight = Integer.parseInt(fastPlayer4.extractMetadata(19, fastPlayer4.mDataSourceId));
                                FastPlayer fastPlayer5 = FastPlayer.this;
                                fastPlayer5.mWidth = Integer.parseInt(fastPlayer5.extractMetadata(18, fastPlayer5.mDataSourceId));
                                FastPlayer.this.setWaitMode2Player(true);
                                FastPlayer.this.mMediaEncoder = new MediaEncoder();
                                FastPlayer.this.mMediaEncoder.setMediaInfo(FastPlayer.this.mFrameRate, FastPlayer.this.mHeight, FastPlayer.this.mWidth);
                                FastPlayer.this.mMediaEncoder.setFilePath(FastPlayer.this.mOutputPath);
                                Surface surfaceConfigure = FastPlayer.this.mMediaEncoder.configure();
                                FastPlayer fastPlayer6 = FastPlayer.this;
                                fastPlayer6.setOpenGLSurface(surfaceConfigure, null, fastPlayer6.mBitmap, FastPlayer.this.mRenderMode);
                                int i2 = FastPlayer.this.gettextureid();
                                Log.w(FastPlayer.TAG, "settranscodePath textureid: " + i2);
                                FastPlayer.this.mSurfaceTexture = new SurfaceTexture(i2);
                                FastPlayer.this.mSurfaceTexture.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() { // from class: com.miui.fastplayer.FastPlayer.2.2
                                    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
                                    public synchronized void onFrameAvailable(SurfaceTexture surfaceTexture) {
                                        FastPlayer.this.mRenderPic = true;
                                    }
                                });
                                FastPlayer.this.mSurface = new Surface(FastPlayer.this.mSurfaceTexture);
                                FastPlayer fastPlayer7 = FastPlayer.this;
                                fastPlayer7.setSurface(fastPlayer7.mSurface);
                                FastPlayer.this.mMediaEncoder.start();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        z = false;
                    }
                    try {
                        if (FastPlayer.this.mIsPlay) {
                            FastPlayer.this.mLock.lock();
                            if (FastPlayer.this.mPauseRender && !FastPlayer.this.mRenderPic) {
                                long jCurrentTimeMillis = System.currentTimeMillis();
                                if (jCurrentTimeMillis - FastPlayer.this.mLastRenderTime > FastPlayer.this.mMaxFpsTime) {
                                    FastPlayer.this.mLastRenderTime = jCurrentTimeMillis;
                                    FastPlayer fastPlayer8 = FastPlayer.this;
                                    fastPlayer8.renderPicture(fastPlayer8.mSTMatrix);
                                }
                                FastPlayer.this.mPauseRender = false;
                            }
                            if (FastPlayer.this.mRenderPic) {
                                FastPlayer.this.mSurfaceTexture.updateTexImage();
                                FastPlayer.this.mSurfaceTexture.getTransformMatrix(FastPlayer.this.mSTMatrix);
                                long jCurrentTimeMillis2 = System.currentTimeMillis();
                                if (jCurrentTimeMillis2 - FastPlayer.this.mLastRenderTime > FastPlayer.this.mMaxFpsTime) {
                                    FastPlayer.this.mLastRenderTime = jCurrentTimeMillis2;
                                    FastPlayer fastPlayer9 = FastPlayer.this;
                                    fastPlayer9.renderPicture(fastPlayer9.mSTMatrix);
                                }
                                FastPlayer.this.mRenderPic = false;
                            }
                            if (!FastPlayer.this.mRelease) {
                                FastPlayer.this.mCondition.await();
                            }
                            FastPlayer.this.mLock.unlock();
                        } else {
                            if (FastPlayer.this.mPauseRender) {
                                FastPlayer fastPlayer10 = FastPlayer.this;
                                fastPlayer10.renderPicture(fastPlayer10.mSTMatrix);
                                FastPlayer.this.mPauseRender = false;
                            }
                            if (FastPlayer.this.mRenderPic) {
                                FastPlayer.this.mRenderPic = false;
                                FastPlayer.this.mSurfaceTexture.updateTexImage();
                                FastPlayer.this.mSurfaceTexture.getTransformMatrix(FastPlayer.this.mSTMatrix);
                                FastPlayer fastPlayer11 = FastPlayer.this;
                                fastPlayer11.renderPicture(fastPlayer11.mSTMatrix);
                            }
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                Log.w(FastPlayer.TAG, "Thread finished");
            }
        });
        this.mRenderThread = thread;
        thread.start();
        Log.d(TAG, "wait gl render thread start");
        synchronized (this.mGLThreadLock) {
            if (!this.mIsGLThreadStart) {
                try {
                    this.mGLThreadLock.wait(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!this.mIsGLThreadStart) {
                    Log.d(TAG, "gl render thread not start in 1000ms");
                }
            } else if (!this.mIsGLThreadStart) {
                Log.d(TAG, "gl render thread not start in 1000ms");
            }
            throw th;
        }
        Log.d(TAG, "gl render thread started");
    }

    private native void _addDataSource(FileDescriptor fileDescriptor, long j, long j2, int i, boolean z) throws IllegalStateException, IOException, IllegalArgumentException;

    private native void _addDataSource(String str, int i) throws IllegalArgumentException;

    private native void _addDataSource(String str, Map<String, String> map, int i) throws IllegalArgumentException;

    private native void _addShaderBitmap(Bitmap bitmap, int i);

    private native void _addShaderSource(String str, int i) throws IllegalArgumentException;

    /* JADX INFO: Access modifiers changed from: private */
    public native int _changeOpenGLSurface(Surface surface, boolean z, Surface surface2, boolean z2, Surface surface3, boolean z3);

    private native void _disableShader(boolean z) throws IllegalArgumentException;

    private native String _extractMetadata(int i, int i2);

    private native String _extractMetadata(int i, int i2, int i3);

    private native long _getCurrentPosition() throws IllegalArgumentException;

    private native Bitmap _getDepthFrameAtTime(String str, long j);

    private native int _getPlayerMode();

    private native int _initializeModel(String str);

    private final native void _native_finalize();

    private native int _pause();

    private native void _playAudio(boolean z) throws IllegalArgumentException;

    private native int _produceAlphaVideo(FileDescriptor fileDescriptor);

    private native int _produceAlphaVideo(String str);

    private native void _removeDataSource(int i);

    private native int _seekto(float f, long j, int i, boolean z);

    private native void _setBlurEffectUniform(float[] fArr, float[] fArr2);

    private native void _setBrightnessEffect(boolean z) throws IllegalArgumentException;

    private native void _setBrightnessEffectUniform(float[] fArr, float f, int i, int i2);

    private native int _setDecoderMode(int i);

    private native void _setHue(float f);

    private native int _setLoop(boolean z, int i);

    private native void _setMoruGlassEffectUniform(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16, float f17, float f18);

    private native void _setMoruPrismGlassEffectUniform(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16, float f17, float f18, float f19);

    private native int _setOpenGLReleased(boolean z);

    private native int _setOpenGLSurface(Surface surface, Surface surface2, Bitmap bitmap, int i);

    private native int _setOpenGLSurface3(Surface surface, Surface surface2, Surface surface3, Bitmap bitmap, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public native int _setOpenGLSurfaceVisible(boolean z, boolean z2, boolean z3);

    private native int _setPlayEndTime(long j);

    private native int _setPlayerSpeed(float f, int i);

    private native void _setPoorBlurEffectUniform(float[] fArr, float f, int i, int i2);

    private native void _setRectGlassEffectUniform(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8);

    private native void _setState(float f);

    private native int _setSurface(Surface surface) throws IllegalStateException;

    private native int _setUseMatrix(float[] fArr);

    private native void _setVideoClipping(long j, long j2, int i);

    private native void _setVideoScalingMode(int i, int i2, int i3);

    private native void _setVolume(int i) throws IllegalArgumentException;

    private native void _set_callback(FastPlayerListener fastPlayerListener);

    private native int _start();

    private int changeOpenGLSurfaceInternal(Surface surface, boolean z, Surface surface2, boolean z2, Surface surface3, boolean z3, ChangeOpenGLSurfaceListener changeOpenGLSurfaceListener) {
        Log.d(TAG, "changeOpenGLSurfaceInternal surface: " + surface + " surface2: " + surface2 + " surface3: " + surface3 + " change: " + z + " change2: " + z2 + " change3: " + z3);
        if (this.mGLHandler == null) {
            Log.e(TAG, "changeOpenGLSurfaceInternal mGLHander null");
            _changeOpenGLSurface(surface, z, surface2, z2, surface3, z3);
            return 0;
        }
        Message message = new Message();
        message.what = 2;
        message.obj = new ChangeOpenGLSurfaceParam(surface, z, surface2, z2, surface3, z3, changeOpenGLSurfaceListener);
        if (this.mGLHandler.sendMessage(message)) {
            Log.d(TAG, "changeOpenGLSurfaceInternal sendMessage success");
            return 0;
        }
        Log.e(TAG, "changeOpenGLSurfaceInternal sendMessage failed");
        _changeOpenGLSurface(surface, z, surface2, z2, surface3, z3);
        if (changeOpenGLSurfaceListener == null) {
            return -1;
        }
        changeOpenGLSurfaceListener.onOpenGLSurfaceChanged(false);
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native int gettextureid();

    private static native void native_init();

    private native void native_setup(VideoFrameReleaseHelper videoFrameReleaseHelper);

    private native void nativerelease();

    /* JADX INFO: Access modifiers changed from: private */
    public int renderPicture(float[] fArr) {
        return renderPicture(fArr, 0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native int renderPicture(float[] fArr, long j);

    private void setMoruPrismGlassEffectUniform(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16, float f17, float f18, float f19) {
        try {
            _setMoruPrismGlassEffectUniform(f, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16, f17, f18, f19);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setOpenGLSurfaceVisibleInternal(boolean z, boolean z2, boolean z3) {
        if (this.mGLHandler == null) {
            Log.e(TAG, "mGLHander null");
            _setOpenGLSurfaceVisible(z, z2, z3);
        } else {
            Message message = new Message();
            message.what = 1;
            message.obj = new OpenGLVisibleItem(z, z2, z3);
            this.mGLHandler.sendMessage(message);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native void setWaitMode2Player(boolean z);

    /* JADX WARN: Code duplicated, block: B:27:0x0066 A[PHI: r3 r4 r10
  0x0066: PHI (r3v2 android.content.res.AssetFileDescriptor) = (r3v1 android.content.res.AssetFileDescriptor), (r3v3 android.content.res.AssetFileDescriptor) binds: [B:30:0x0072, B:26:0x0064] A[DONT_GENERATE, DONT_INLINE]
  0x0066: PHI (r4v4 ??) = (r4v14 ??), (r4v15 ??) binds: [B:30:0x0072, B:26:0x0064] A[DONT_GENERATE, DONT_INLINE]
  0x0066: PHI (r10v4 int) = (r10v2 int), (r10v5 int) binds: [B:30:0x0072, B:26:0x0064] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v16 */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v18 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [com.miui.fastplayer.FastPlayer] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6, types: [long] */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    public void addDataSource(Context context, Uri uri, int i) throws IOException, SecurityException, IllegalArgumentException {
        ?? declaredLength;
        int i2;
        ?? r4;
        ?? r5;
        if (uri == null) {
            throw new IllegalArgumentException();
        }
        AssetFileDescriptor assetFileDescriptorOpenAssetFileDescriptor = null;
        try {
            try {
                assetFileDescriptorOpenAssetFileDescriptor = context.getContentResolver().openAssetFileDescriptor(uri, "r");
                if (assetFileDescriptorOpenAssetFileDescriptor == null) {
                    Log.d(TAG, "open fd failed, fd is NULL!");
                    Log.d(TAG, "open fd failed, finally!");
                    if (assetFileDescriptorOpenAssetFileDescriptor != null) {
                        assetFileDescriptorOpenAssetFileDescriptor.close();
                        return;
                    }
                    return;
                }
                declaredLength = assetFileDescriptorOpenAssetFileDescriptor.getDeclaredLength();
                try {
                    if (declaredLength < 0) {
                        FastPlayer fastPlayer = this;
                        i2 = i;
                        fastPlayer._addDataSource(assetFileDescriptorOpenAssetFileDescriptor.getFileDescriptor(), 0L, -1L, i2, true);
                        declaredLength = fastPlayer;
                    } else {
                        FastPlayer fastPlayer2 = this;
                        i2 = i;
                        fastPlayer2._addDataSource(assetFileDescriptorOpenAssetFileDescriptor.getFileDescriptor(), assetFileDescriptorOpenAssetFileDescriptor.getStartOffset(), assetFileDescriptorOpenAssetFileDescriptor.getDeclaredLength(), i2, true);
                        declaredLength = fastPlayer2;
                    }
                    Log.d(TAG, "open fd failed, finally!");
                    assetFileDescriptorOpenAssetFileDescriptor.close();
                } catch (IOException unused) {
                    Log.d(TAG, "open fd failed, IOException!");
                    Log.d(TAG, "open fd failed, finally!");
                    r5 = declaredLength;
                    r4 = declaredLength;
                    if (assetFileDescriptorOpenAssetFileDescriptor != null) {
                        assetFileDescriptorOpenAssetFileDescriptor.close();
                        r4 = r5;
                    }
                    r4.addDataSource(uri.toString(), i2);
                    r4.mDataSourceId = i2;
                } catch (SecurityException unused2) {
                    Log.d(TAG, "open fd failed, SecurityException!");
                    Log.d(TAG, "open fd failed, finally!");
                    r5 = declaredLength;
                    r4 = declaredLength;
                    if (assetFileDescriptorOpenAssetFileDescriptor != null) {
                        assetFileDescriptorOpenAssetFileDescriptor.close();
                        r4 = r5;
                    }
                    r4.addDataSource(uri.toString(), i2);
                    r4.mDataSourceId = i2;
                }
            } catch (Throwable th) {
                Log.d(TAG, "open fd failed, finally!");
                if (0 == 0) {
                    throw th;
                }
                assetFileDescriptorOpenAssetFileDescriptor.close();
                throw th;
            }
        } catch (IOException unused3) {
            declaredLength = this;
            i2 = i;
        } catch (SecurityException unused4) {
            declaredLength = this;
            i2 = i;
        }
    }

    public void addDataSource(FileDescriptor fileDescriptor, int i) throws IllegalStateException, IOException, IllegalArgumentException {
        addDataSource(fileDescriptor, 0L, -1L, i);
        this.mDataSourceId = i;
    }

    public void addDataSource(FileDescriptor fileDescriptor, long j, long j2, int i) throws IllegalStateException, IOException, IllegalArgumentException {
        _addDataSource(fileDescriptor, j, j2, i, false);
        this.mDataSourceId = i;
    }

    public void addDataSource(String str, int i) {
        try {
            _addDataSource(str, i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDataSource(String str, Map<String, String> map, int i) throws IllegalArgumentException {
        _addDataSource(str, map, i);
        this.mDataSourceId = i;
    }

    public int addShaderBitmap(Context context, int i, int i2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(context.getResources(), i, options);
        if (bitmapDecodeResource == null) {
            Log.i(TAG, "decode bitmap failed");
            return -1;
        }
        addShaderBitmap(bitmapDecodeResource, i2);
        bitmapDecodeResource.recycle();
        return 0;
    }

    public void addShaderBitmap(Bitmap bitmap, int i) {
        try {
            _addShaderBitmap(bitmap, i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addShaderSource(Context context, int i, int i2) throws IOException, SecurityException {
        try {
            InputStream inputStreamOpenRawResource = context.getResources().openRawResource(i2);
            if (inputStreamOpenRawResource == null) {
                throw new SecurityException("can not get InputStream");
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamOpenRawResource, "UTF-8"));
            String str = "";
            while (true) {
                try {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    str = (str + line) + "\n";
                } catch (IOException unused) {
                    throw new IOException("IOException occurred");
                }
            }
            inputStreamOpenRawResource.close();
            if (str == null) {
                throw new SecurityException("read shader source failed");
            }
            addShaderSource(str, i);
        } catch (UnsupportedEncodingException unused2) {
            throw new UnsupportedEncodingException("UnsupportedEncodingException occrred");
        }
    }

    public void addShaderSource(String str, int i) {
        try {
            _addShaderSource(str, i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int changeOpenGLSurface(Surface surface, boolean z, Surface surface2, boolean z2, Surface surface3, boolean z3) {
        return changeOpenGLSurfaceInternal(surface, z, surface2, z2, surface3, z3, null);
    }

    public int changeOpenGLSurface(Surface surface, boolean z, Surface surface2, boolean z2, Surface surface3, boolean z3, ChangeOpenGLSurfaceListener changeOpenGLSurfaceListener) {
        return changeOpenGLSurfaceInternal(surface, z, surface2, z2, surface3, z3, changeOpenGLSurfaceListener);
    }

    public void disableShader(boolean z) {
        _disableShader(z);
    }

    public String extractMetadata(int i, int i2) {
        try {
            return _extractMetadata(i, i2);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String extractMetadata(int i, int i2, int i3) {
        try {
            return _extractMetadata(i, i2, i3);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public long getCurrentPosition() {
        return _getCurrentPosition() / 1000;
    }

    public long getCurrentPositionUs() {
        return _getCurrentPosition();
    }

    public Bitmap getLastDepthFrame(String str) {
        return _getDepthFrameAtTime(str, -1L);
    }

    public int getPlayerMode() {
        try {
            return _getPlayerMode();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int initializeModel(String str) {
        if (str == null) {
            return -1;
        }
        return _initializeModel(str);
    }

    public final void native_finalize() {
        _native_finalize();
    }

    public int pause() {
        try {
            return _pause();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void pauseRenderFrame() {
        if (!this.mRelease && this.mNeedPauseRender) {
            this.mPauseRender = true;
            try {
                this.mLock.lock();
                this.mCondition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                this.mLock.unlock();
            }
        }
    }

    public void playAudio(boolean z) {
        _playAudio(z);
    }

    public int produceAlphaVideo(FileDescriptor fileDescriptor) {
        return _produceAlphaVideo(fileDescriptor);
    }

    public int produceAlphaVideo(String str) {
        return _produceAlphaVideo(str);
    }

    public void release() {
        this.mRelease = true;
        try {
            try {
                this.mLock.lock();
                this.mCondition.signal();
            } catch (Throwable th) {
                this.mLock.unlock();
                throw th;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mLock.unlock();
        synchronized (this.mGLThreadLock) {
            try {
                if (this.mIsGLThreadStart) {
                    Looper looper = this.mLooper;
                    if (looper != null) {
                        looper.quit();
                    }
                    if (this.mRenderThread != null) {
                        _setOpenGLReleased(true);
                        try {
                            this.mRenderThread.join();
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                        this.mRenderThread = null;
                        this.mGLHandler = null;
                    }
                    this.mIsGLThreadStart = false;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        nativerelease();
        MediaEncoder mediaEncoder = this.mMediaEncoder;
        if (mediaEncoder != null) {
            mediaEncoder.stop();
            this.mMediaEncoder.release();
        }
        Surface surface = this.mSurface;
        if (surface != null) {
            surface.release();
        }
        SurfaceTexture surfaceTexture = this.mSurfaceTexture;
        if (surfaceTexture != null) {
            surfaceTexture.release();
        }
        VideoFrameReleaseHelper videoFrameReleaseHelper = this.mVideoFrameReleaseHelper;
        if (videoFrameReleaseHelper != null) {
            videoFrameReleaseHelper.stop();
            this.mVideoFrameReleaseHelper = null;
        }
    }

    public void removeDataSource(int i) {
        try {
            _removeDataSource(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int resetPlayEndTime() {
        try {
            return _setPlayEndTime(-1L);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int seekto(float f, long j, int i) {
        return seekto(f, j, i, false);
    }

    public int seekto(float f, long j, int i, boolean z) {
        try {
            return _seekto(f, j * 1000, i, z);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void setBlurEffectUniform(float[] fArr, float[] fArr2) {
        try {
            _setBlurEffectUniform(fArr, fArr2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBrightnessEffect(boolean z) {
        try {
            _setBrightnessEffect(z);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBrightnessEffectUniform(float[] fArr, float f, int i, int i2) {
        try {
            _setBrightnessEffectUniform(fArr, f, i, i2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCallback(FastPlayerListener fastPlayerListener) {
        set_callback(fastPlayerListener);
    }

    public int setDecoderMode(int i) {
        try {
            return _setDecoderMode(i);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void setHue(float f) {
        try {
            _setHue(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int setLoop(boolean z, int i) {
        try {
            return _setLoop(z, i);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void setMaxFps(int i) {
        if (i > 0) {
            this.mNeedPauseRender = true;
            this.mMaxFpsTime = 1000 / i;
        } else {
            this.mNeedPauseRender = false;
            this.mMaxFpsTime = 0L;
        }
    }

    public void setMoruComGlassEffectUniform(float f, float f2, float f3, float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, float[] fArr5) {
        if (fArr.length != 4 || fArr2.length != 4 || fArr3.length != 4 || fArr4.length != 2 || fArr5.length != 2) {
            Log.e(TAG, "sMoruPrismGlass, Array input error, please check it");
            return;
        }
        try {
            setMoruPrismGlassEffectUniform(f, f2, f3, fArr[0], fArr[1], fArr[2], fArr[3], fArr2[0], fArr2[1], fArr2[2], fArr2[3], fArr3[0], fArr3[1], fArr3[2], fArr3[3], fArr4[0], fArr4[1], fArr5[0], fArr5[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMoruGlassEffectUniform(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16, float f17, float f18) {
        try {
            _setMoruGlassEffectUniform(f, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16, f17, f18);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMoruGlassEffectUniform(float f, float f2, float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, float[] fArr5) {
        if (fArr.length != 4 || fArr2.length != 4 || fArr3.length != 4 || fArr4.length != 2 || fArr5.length != 2) {
            Log.e(TAG, " Array input error, please check it");
            return;
        }
        try {
            setMoruGlassEffectUniform(f, f2, fArr[0], fArr[1], fArr[2], fArr[3], fArr2[0], fArr2[1], fArr2[2], fArr2[3], fArr3[0], fArr3[1], fArr3[2], fArr3[3], fArr4[0], fArr4[1], fArr5[0], fArr5[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMoruPrismGlassEffectUniform(float f, float f2, float f3, float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, float[] fArr5) {
        if (fArr.length != 4 || fArr2.length != 4 || fArr3.length != 4 || fArr4.length != 2 || fArr5.length != 2) {
            Log.e(TAG, "sMoruPrismGlass, Array input error, please check it");
            return;
        }
        try {
            setMoruPrismGlassEffectUniform(f, f2, f3, fArr[0], fArr[1], fArr[2], fArr[3], fArr2[0], fArr2[1], fArr2[2], fArr2[3], fArr3[0], fArr3[1], fArr3[2], fArr3[3], fArr4[0], fArr4[1], fArr5[0], fArr5[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOpenGLCallback(OpenGLCallback openGLCallback) {
        this.mOpenGLCallback = openGLCallback;
    }

    public int setOpenGLSurface(Surface surface, Surface surface2, Bitmap bitmap, int i) {
        try {
            _setOpenGLSurface(surface, surface2, bitmap, i);
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int setOpenGLSurface(Surface surface, Surface surface2, Surface surface3, Bitmap bitmap, int i) {
        try {
            return _setOpenGLSurface3(surface, surface2, surface3, bitmap, i);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void setOpenGLSurfaceVisible(boolean z) {
        setOpenGLSurfaceVisible(z, false, false);
    }

    public void setOpenGLSurfaceVisible(boolean z, boolean z2) {
        setOpenGLSurfaceVisible(z, z2, false);
    }

    public void setOpenGLSurfaceVisible(boolean z, boolean z2, boolean z3) {
        setOpenGLSurfaceVisibleInternal(z, z2, z3);
    }

    public int setPlayEndTimeMs(long j) {
        if (j > 0) {
            j *= 1000;
        }
        try {
            return _setPlayEndTime(j);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int setPlayEndTimeUs(long j) {
        try {
            return _setPlayEndTime(j);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int setPlayTime(long j) {
        try {
            return _setPlayEndTime(j);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int setPlayerSpeed(float f, int i) {
        try {
            return _setPlayerSpeed(f, i);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void setPoorBlurEffectUniform(float[] fArr, float f, int i, int i2) {
        try {
            _setPoorBlurEffectUniform(fArr, f, i, i2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRectGlassEffectUniform(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        try {
            _setRectGlassEffectUniform(f, f2, f3, f4, f5, f6, f7, f8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRectGlassEffectUniform(float[] fArr, float[] fArr2) {
        if (fArr.length != 4 || fArr2.length != 4) {
            Log.e(TAG, " Array input error, please check it");
            return;
        }
        try {
            _setRectGlassEffectUniform(fArr[0], fArr[1], fArr[2], fArr[3], fArr2[0], fArr2[1], fArr2[2], fArr2[3]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setState(float f) {
        try {
            _setState(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int setSurface(Surface surface) {
        if (surface == null) {
            return -1;
        }
        try {
            return _setSurface(surface);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void setSurface(Surface surface, int i) {
        try {
            setSurface(surface, null, i, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSurface(Surface surface, Surface surface2, int i, Bitmap bitmap) {
        setSurface(surface, surface2, null, i, bitmap);
    }

    public void setSurface(Surface surface, Surface surface2, Surface surface3, int i, Bitmap bitmap) {
        if (i == 12) {
            if (surface == null && surface2 == null && surface3 == null) {
                Log.e(TAG, "setSurface null");
                return;
            }
        } else if (surface == null) {
            return;
        }
        this.mSurfaceInput = surface;
        this.mSurfaceInput2 = surface2;
        this.mSurfaceInput3 = surface3;
        this.mRenderMode = i;
        this.mBitmap = bitmap;
        this.mIsPlay = true;
        Thread thread = new Thread(new Runnable() { // from class: com.miui.fastplayer.FastPlayer.1
            @Override // java.lang.Runnable
            public void run() {
                FastPlayer fastPlayer = FastPlayer.this;
                int openGLSurface = fastPlayer.setOpenGLSurface(fastPlayer.mSurfaceInput, FastPlayer.this.mSurfaceInput2, FastPlayer.this.mSurfaceInput3, FastPlayer.this.mBitmap, FastPlayer.this.mRenderMode);
                if (openGLSurface != 0) {
                    Log.e(FastPlayer.TAG, "setOpenGLSurface failed ret: " + openGLSurface);
                    synchronized (FastPlayer.this.mGLThreadLock) {
                        FastPlayer.this.mRenderThread = null;
                        FastPlayer.this.mGLThreadLock.notifyAll();
                    }
                    return;
                }
                int i2 = FastPlayer.this.gettextureid();
                Log.e(FastPlayer.TAG, "setSurface textureid: " + i2);
                if (i2 <= 0) {
                    Log.e(FastPlayer.TAG, "invalid textureid: " + i2);
                    synchronized (FastPlayer.this.mGLThreadLock) {
                        FastPlayer.this.mRenderThread = null;
                        FastPlayer.this.mGLThreadLock.notifyAll();
                    }
                    return;
                }
                FastPlayer.this.mSurfaceTexture = new SurfaceTexture(i2);
                Looper.prepare();
                FastPlayer.this.mLooper = Looper.myLooper();
                FastPlayer.this.mGLHandler = new Handler() { // from class: com.miui.fastplayer.FastPlayer.1.1
                    @Override // android.os.Handler
                    public void handleMessage(Message message) {
                        int i_changeOpenGLSurface;
                        super.handleMessage(message);
                        int i3 = message.what;
                        if (i3 == 0) {
                            try {
                                FastPlayer.this.mSurfaceTexture.updateTexImage();
                                FastPlayer.this.mSurfaceTexture.getTransformMatrix(FastPlayer.this.mSTMatrix);
                                long timestamp = FastPlayer.this.mSurfaceTexture.getTimestamp();
                                Log.d(FastPlayer.TAG, "updateTexImage time: " + timestamp);
                                FastPlayer fastPlayer2 = FastPlayer.this;
                                fastPlayer2.renderPicture(fastPlayer2.mSTMatrix, timestamp);
                                if (FastPlayer.this.mOpenGLCallback != null) {
                                    FastPlayer.this.mOpenGLCallback.onFrameRendered();
                                    return;
                                }
                                return;
                            } catch (IllegalStateException e) {
                                Log.e(FastPlayer.TAG, "updateTexImage failed", e);
                                return;
                            }
                        }
                        if (i3 == 1) {
                            Log.d(FastPlayer.TAG, "setOpenGLSurfaceVisible in RenderThread begin");
                            OpenGLVisibleItem openGLVisibleItem = (OpenGLVisibleItem) message.obj;
                            try {
                                FastPlayer.this._setOpenGLSurfaceVisible(openGLVisibleItem.mVisible, openGLVisibleItem.mVisible2, openGLVisibleItem.mVisible3);
                            } catch (Exception e2) {
                                Log.d(FastPlayer.TAG, "setOpenGLSurfaceVisibleInternal exception");
                                e2.printStackTrace();
                            }
                            Log.d(FastPlayer.TAG, "setOpenGLSurfaceVisible in RenderThread end");
                            return;
                        }
                        if (i3 != 2) {
                            return;
                        }
                        ChangeOpenGLSurfaceParam changeOpenGLSurfaceParam = (ChangeOpenGLSurfaceParam) message.obj;
                        try {
                            Log.d(FastPlayer.TAG, "excute changeOpenglSurface in RenderThread surface1:" + changeOpenGLSurfaceParam.mSurface + " surface2:" + changeOpenGLSurfaceParam.mSurface2 + " surface3:" + changeOpenGLSurfaceParam.mSurface3 + " change:" + changeOpenGLSurfaceParam.mChange + " change2:" + changeOpenGLSurfaceParam.mChange2 + " change3:" + changeOpenGLSurfaceParam.mChange3 + " listener:" + changeOpenGLSurfaceParam.mListener);
                            i_changeOpenGLSurface = FastPlayer.this._changeOpenGLSurface(changeOpenGLSurfaceParam.mSurface, changeOpenGLSurfaceParam.mChange, changeOpenGLSurfaceParam.mSurface2, changeOpenGLSurfaceParam.mChange2, changeOpenGLSurfaceParam.mSurface3, changeOpenGLSurfaceParam.mChange3);
                        } catch (Exception e3) {
                            Log.d(FastPlayer.TAG, "excute changeOpenglSurface in RenderThread exception");
                            e3.printStackTrace();
                            i_changeOpenGLSurface = -1;
                        }
                        if (changeOpenGLSurfaceParam.mListener != null) {
                            Log.d(FastPlayer.TAG, "changeOpenglSurface end callback to caller, ret: " + i_changeOpenGLSurface + ", listener:" + changeOpenGLSurfaceParam.mListener);
                            changeOpenGLSurfaceParam.mListener.onOpenGLSurfaceChanged(i_changeOpenGLSurface == 0);
                        }
                    }
                };
                FastPlayer.this.mSurfaceTexture.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() { // from class: com.miui.fastplayer.FastPlayer.1.2
                    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
                    public synchronized void onFrameAvailable(SurfaceTexture surfaceTexture) {
                        Message message = new Message();
                        message.what = 0;
                        if (FastPlayer.this.mGLHandler != null) {
                            FastPlayer.this.mGLHandler.sendMessage(message);
                        }
                    }
                }, FastPlayer.this.mGLHandler);
                FastPlayer.this.mSurface = new Surface(FastPlayer.this.mSurfaceTexture);
                FastPlayer fastPlayer2 = FastPlayer.this;
                fastPlayer2.setSurface(fastPlayer2.mSurface);
                synchronized (FastPlayer.this.mGLThreadLock) {
                    FastPlayer.this.mIsGLThreadStart = true;
                    FastPlayer.this.mGLThreadLock.notifyAll();
                }
                Looper.loop();
            }
        });
        this.mRenderThread = thread;
        thread.start();
        Log.d(TAG, "wait gl render thread start");
        synchronized (this.mGLThreadLock) {
            if (!this.mIsGLThreadStart) {
                try {
                    this.mGLThreadLock.wait(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (this.mIsGLThreadStart) {
                Log.d(TAG, "gl render thread started");
            } else {
                Log.d(TAG, "gl render thread not start in 1000ms");
            }
        }
    }

    public void setTranscodePath(String str, int i, Bitmap bitmap) {
        this.mOutputPath = str;
        this.mRenderMode = i;
        this.mBitmap = bitmap;
        HandleDataThread();
    }

    public void setUseMatrix(float[] fArr) {
        _setUseMatrix(fArr);
    }

    public void setVideoClipping(long j, long j2, int i) {
        _setVideoClipping(j, j2, i);
    }

    public void setVideoScalingMode(int i) {
        try {
            int i2 = Integer.parseInt(extractMetadata(24, 0));
            int i3 = Integer.parseInt(extractMetadata(18, 0));
            int i4 = Integer.parseInt(extractMetadata(19, 0));
            Log.d(TAG, "setVideoScalingMode, mode: " + i + ", rotation: " + i2 + ", width: " + i3 + ", height: " + i4);
            if (i2 != 90 && i2 != 270) {
                _setVideoScalingMode(i, i3, i4);
                return;
            }
            _setVideoScalingMode(i, i4, i3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setVolume(int i) {
        _setVolume(i);
    }

    public void set_callback(FastPlayerListener fastPlayerListener) {
        try {
            _set_callback(fastPlayerListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int start() {
        try {
            this.mPauseRender = false;
            return _start();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void startVsyncObserver() {
        Log.d(TAG, "startVsyncObserver start");
        VideoFrameReleaseHelper videoFrameReleaseHelper = this.mVideoFrameReleaseHelper;
        if (videoFrameReleaseHelper != null) {
            videoFrameReleaseHelper.startObserver();
        }
        Log.d(TAG, "startVsyncObserver end");
    }

    public void stopVsyncObserver() {
        Log.d(TAG, "stopVsyncObserver start");
        VideoFrameReleaseHelper videoFrameReleaseHelper = this.mVideoFrameReleaseHelper;
        if (videoFrameReleaseHelper != null) {
            videoFrameReleaseHelper.stopObserver();
        }
        Log.d(TAG, "stopVsyncObserver end");
    }
}
