package com.miui.fastplayer;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Choreographer;
import android.view.Display;
import android.view.WindowManager;
import androidx.exifinterface.media.ExifInterface;

/* JADX INFO: loaded from: classes.dex */
public final class VideoFrameReleaseHelper {
    private static final long ALPHA_VSYNC_OFFSET_PERCENTAGE = 20;
    private static final long MAGIC_VSYNC_OFFSET_PERCENTAGE = 50;
    private static final String TAG = "FastPlayerVideoFrameReleaseHelper";
    private static final long VSYNC_OFFSET_PERCENTAGE = 80;
    private static final long VSYNC_SAMPLE_UPDATE_PERIOD_MS = 500;
    private final DisplayHelper displayHelper;
    private boolean isObserverAdded = false;
    private long vsyncDurationNs;
    private long vsyncOffsetNs;
    private final VSyncSampler vsyncSampler;

    public static final class C {
        public static final int INDEX_UNSET = -1;
        public static final long NANOS_PER_SECOND = 1000000000;
        public static final int NO_VALUE = -1;
        public static final long TIME_END_OF_SOURCE = Long.MIN_VALUE;
        public static final long TIME_UNSET = -9223372036854775807L;

        private C() {
        }
    }

    public interface DisplayHelper {

        public interface Listener {
            void onDefaultDisplayChanged(Display display);
        }

        void register(Listener listener);

        void unregister();
    }

    public final class DisplayHelperListener implements DisplayHelper.Listener {
        private DisplayHelperListener() {
        }

        @Override // com.miui.fastplayer.VideoFrameReleaseHelper.DisplayHelper.Listener
        public void onDefaultDisplayChanged(Display display) {
            if (display == null) {
                Log.w(VideoFrameReleaseHelper.TAG, "Unable to query display refresh rate");
                VideoFrameReleaseHelper.this.vsyncDurationNs = C.TIME_UNSET;
                VideoFrameReleaseHelper.this.vsyncOffsetNs = C.TIME_UNSET;
                return;
            }
            double refreshRate = display.getRefreshRate();
            Log.i(VideoFrameReleaseHelper.TAG, "defaultDisplayRefreshRate: " + refreshRate);
            VideoFrameReleaseHelper.this.vsyncDurationNs = (long) (1.0E9d / refreshRate);
            VideoFrameReleaseHelper videoFrameReleaseHelper = VideoFrameReleaseHelper.this;
            videoFrameReleaseHelper.vsyncOffsetNs = (videoFrameReleaseHelper.vsyncDurationNs * VideoFrameReleaseHelper.VSYNC_OFFSET_PERCENTAGE) / 100;
        }
    }

    public static final class DisplayHelperV16 implements DisplayHelper {
        private final WindowManager windowManager;

        private DisplayHelperV16(WindowManager windowManager) {
            this.windowManager = windowManager;
        }

        public static DisplayHelper maybeBuildNewInstance(Context context) {
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            if (windowManager != null) {
                return new DisplayHelperV16(windowManager);
            }
            return null;
        }

        @Override // com.miui.fastplayer.VideoFrameReleaseHelper.DisplayHelper
        public void register(DisplayHelper.Listener listener) {
            listener.onDefaultDisplayChanged(this.windowManager.getDefaultDisplay());
        }

        @Override // com.miui.fastplayer.VideoFrameReleaseHelper.DisplayHelper
        public void unregister() {
        }
    }

    public static final class DisplayHelperV17 implements DisplayHelper, DisplayManager.DisplayListener {
        private final DisplayManager displayManager;
        private DisplayHelper.Listener listener;

        private DisplayHelperV17(DisplayManager displayManager) {
            this.displayManager = displayManager;
        }

        private Display getDefaultDisplay() {
            return this.displayManager.getDisplay(0);
        }

        public static DisplayHelper maybeBuildNewInstance(Context context) {
            DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
            if (displayManager != null) {
                return new DisplayHelperV17(displayManager);
            }
            return null;
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            DisplayHelper.Listener listener = this.listener;
            if (listener == null || i != 0) {
                return;
            }
            listener.onDefaultDisplayChanged(getDefaultDisplay());
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
        }

        @Override // com.miui.fastplayer.VideoFrameReleaseHelper.DisplayHelper
        public void register(DisplayHelper.Listener listener) {
            this.listener = listener;
            this.displayManager.registerDisplayListener(this, Util.createHandlerForCurrentLooper());
            listener.onDefaultDisplayChanged(getDefaultDisplay());
        }

        @Override // com.miui.fastplayer.VideoFrameReleaseHelper.DisplayHelper
        public void unregister() {
            this.displayManager.unregisterDisplayListener(this);
            this.listener = null;
        }
    }

    public static final class Util {
        public static final String DEVICE;
        public static final String MANUFACTURER;
        public static final String MODEL;
        public static final int SDK_INT;

        static {
            int i;
            String str = Build.VERSION.CODENAME;
            if (ExifInterface.LATITUDE_SOUTH.equals(str)) {
                i = 31;
            } else {
                i = "R".equals(str) ? 30 : Build.VERSION.SDK_INT;
            }
            SDK_INT = i;
            DEVICE = Build.DEVICE;
            MANUFACTURER = Build.MANUFACTURER;
            MODEL = Build.MODEL;
        }

        private Util() {
        }

        public static Handler createHandler(Looper looper, Handler.Callback callback) {
            return new Handler(looper, callback);
        }

        public static Handler createHandlerForCurrentLooper() {
            return createHandlerForCurrentLooper(null);
        }

        public static Handler createHandlerForCurrentLooper(Handler.Callback callback) {
            return createHandler(Looper.myLooper(), callback);
        }
    }

    public static final class VSyncSampler implements Choreographer.FrameCallback, Handler.Callback {
        private static final int CREATE_CHOREOGRAPHER = 0;
        private static final VSyncSampler INSTANCE = new VSyncSampler();
        private static final int MSG_ADD_OBSERVER = 1;
        private static final int MSG_REMOVE_OBSERVER = 2;
        private Choreographer choreographer;
        private final HandlerThread choreographerOwnerThread;
        private final Handler handler;
        private int observerCount;
        public volatile long sampledVsyncTimeNs = C.TIME_UNSET;

        private VSyncSampler() {
            HandlerThread handlerThread = new HandlerThread("FastPlayer:FrameReleaseChoreographer");
            this.choreographerOwnerThread = handlerThread;
            handlerThread.start();
            Handler handlerCreateHandler = Util.createHandler(handlerThread.getLooper(), this);
            this.handler = handlerCreateHandler;
            handlerCreateHandler.sendEmptyMessage(0);
        }

        private void addObserverInternal() {
            Choreographer choreographer;
            int i = this.observerCount + 1;
            this.observerCount = i;
            if (i != 1 || (choreographer = this.choreographer) == null) {
                return;
            }
            choreographer.postFrameCallback(this);
        }

        private void createChoreographerInstanceInternal() {
            this.choreographer = Choreographer.getInstance();
        }

        public static VSyncSampler getInstance() {
            return INSTANCE;
        }

        private void removeObserverInternal() {
            Choreographer choreographer;
            int i = this.observerCount - 1;
            this.observerCount = i;
            if (i != 0 || (choreographer = this.choreographer) == null) {
                return;
            }
            choreographer.removeFrameCallback(this);
        }

        public void addObserver() {
            this.handler.sendEmptyMessage(1);
        }

        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long j) {
            Log.d(VideoFrameReleaseHelper.TAG, "doFrame, postdely vsyncTimeNs: " + (j / 1000));
            this.sampledVsyncTimeNs = j;
            Choreographer choreographer = this.choreographer;
            if (choreographer != null) {
                choreographer.postFrameCallbackDelayed(this, VideoFrameReleaseHelper.VSYNC_SAMPLE_UPDATE_PERIOD_MS);
            }
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                createChoreographerInstanceInternal();
                return true;
            }
            if (i == 1) {
                addObserverInternal();
                return true;
            }
            if (i != 2) {
                return false;
            }
            removeObserverInternal();
            return true;
        }

        public void removeObserver() {
            this.handler.sendEmptyMessage(2);
        }
    }

    public VideoFrameReleaseHelper(Context context) {
        DisplayHelper displayHelperMaybeBuildDisplayHelper = maybeBuildDisplayHelper(context);
        this.displayHelper = displayHelperMaybeBuildDisplayHelper;
        this.vsyncSampler = displayHelperMaybeBuildDisplayHelper != null ? VSyncSampler.getInstance() : null;
        this.vsyncDurationNs = C.TIME_UNSET;
        this.vsyncOffsetNs = C.TIME_UNSET;
    }

    private static long closestVsync(long j, long j2, long j3) {
        long j4;
        long j5 = j2 + (((j - j2) / j3) * j3);
        if (j <= j5) {
            j5 -= j3;
            j4 = j5;
        } else {
            j4 = j5 + j3;
        }
        long j6 = (j3 * ALPHA_VSYNC_OFFSET_PERCENTAGE) / 100;
        long j7 = j5 + j6;
        if (System.nanoTime() <= j7) {
            return j7;
        }
        Log.d(TAG, "jump to next vsync");
        return j4 + j6;
    }

    private static DisplayHelper maybeBuildDisplayHelper(Context context) {
        DisplayHelper displayHelperMaybeBuildNewInstance = null;
        if (context != null) {
            Context applicationContext = context.getApplicationContext();
            displayHelperMaybeBuildNewInstance = Util.SDK_INT >= 17 ? DisplayHelperV17.maybeBuildNewInstance(applicationContext) : null;
            if (displayHelperMaybeBuildNewInstance == null) {
                return DisplayHelperV16.maybeBuildNewInstance(applicationContext);
            }
        }
        return displayHelperMaybeBuildNewInstance;
    }

    public long adjustReleaseTime(long j) {
        VSyncSampler vSyncSampler = this.vsyncSampler;
        if (vSyncSampler != null && this.vsyncDurationNs != C.TIME_UNSET) {
            long j2 = vSyncSampler.sampledVsyncTimeNs;
            if (j2 != C.TIME_UNSET) {
                return closestVsync(j, j2, this.vsyncDurationNs);
            }
        }
        return j;
    }

    public long getSampledVsyncTimeNs() {
        if (this.vsyncSampler.sampledVsyncTimeNs == C.TIME_UNSET) {
            return -1L;
        }
        return this.vsyncSampler.sampledVsyncTimeNs;
    }

    public long getVsyncDurationNs() {
        long j = this.vsyncDurationNs;
        if (j == C.TIME_UNSET) {
            return -1L;
        }
        return j;
    }

    public void start() {
        VSyncSampler vSyncSampler;
        if (this.displayHelper == null || (vSyncSampler = this.vsyncSampler) == null) {
            return;
        }
        vSyncSampler.addObserver();
        this.displayHelper.register(new DisplayHelperListener());
        this.isObserverAdded = true;
        Log.d(TAG, "start VideoFrameReleaseHelper success");
    }

    public void startObserver() {
        VSyncSampler vSyncSampler;
        Log.d(TAG, "startObserver start isObserverAdded:" + this.isObserverAdded);
        if (this.displayHelper != null && (vSyncSampler = this.vsyncSampler) != null && !this.isObserverAdded) {
            vSyncSampler.addObserver();
            this.isObserverAdded = true;
            Log.d(TAG, "startObserver success");
        }
        Log.d(TAG, "startObserver end");
    }

    public void stop() {
        DisplayHelper displayHelper = this.displayHelper;
        if (displayHelper == null || this.vsyncSampler == null) {
            return;
        }
        displayHelper.unregister();
        this.vsyncSampler.removeObserver();
        this.isObserverAdded = false;
        Log.d(TAG, "stop VideoFrameReleaseHelper success");
    }

    public void stopObserver() {
        VSyncSampler vSyncSampler;
        Log.d(TAG, "stopObserver start isObserverAdded:" + this.isObserverAdded);
        if (this.displayHelper != null && (vSyncSampler = this.vsyncSampler) != null && this.isObserverAdded && vSyncSampler.sampledVsyncTimeNs != C.TIME_UNSET) {
            this.vsyncSampler.removeObserver();
            this.isObserverAdded = false;
            Log.d(TAG, "stopObserver success");
        }
        Log.d(TAG, "stopObserver end");
    }
}
