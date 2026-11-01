package com.miui.maml;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import com.miui.maml.util.MamlLog;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes2.dex */
@Deprecated
public class RenderThread extends Thread {
    private static final String LOG_TAG = "RenderThread";
    private static RenderThread sGlobalThread;
    private static Object sGlobalThreadLock = new Object();
    private CommandThreadHandler mCmdHanlder;
    private HandlerThread mCmdThread;
    private boolean mPaused;
    private ArrayList<RendererController> mRendererControllerList;
    private Object mResumeSignal;
    private boolean mSignaled;
    private Object mSleepSignal;
    private boolean mStarted;
    private boolean mStop;

    public class CommandThreadHandler extends Handler {
        private static final int MSG_PAUSE = 0;
        private static final int MSG_RESUME = 1;

        public CommandThreadHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                RenderThread.this.setPausedImpl(true);
            } else {
                if (i != 1) {
                    return;
                }
                RenderThread.this.setPausedImpl(false);
            }
        }

        public void setPause(boolean z) {
            Message message = new Message();
            message.what = !z ? 1 : 0;
            sendMessage(message);
        }
    }

    public RenderThread() {
        super("MAML RenderThread");
        this.mRendererControllerList = new ArrayList<>();
        this.mPaused = true;
        this.mResumeSignal = new Object();
        this.mSleepSignal = new Object();
        initCmdThread();
    }

    public RenderThread(RendererController rendererController) {
        super("MAML RenderThread");
        this.mRendererControllerList = new ArrayList<>();
        this.mPaused = true;
        this.mResumeSignal = new Object();
        this.mSleepSignal = new Object();
        initCmdThread();
    }

    private void doFinish() {
        synchronized (this.mRendererControllerList) {
            try {
                if (this.mRendererControllerList.size() == 0) {
                    return;
                }
                int size = this.mRendererControllerList.size();
                for (int i = 0; i < size; i++) {
                    this.mRendererControllerList.get(i).finish();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void doInit() {
        synchronized (this.mRendererControllerList) {
            try {
                if (this.mRendererControllerList.size() == 0) {
                    return;
                }
                int size = this.mRendererControllerList.size();
                for (int i = 0; i < size; i++) {
                    RendererController rendererController = this.mRendererControllerList.get(i);
                    rendererController.init();
                    rendererController.requestUpdate();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void doPause() {
        synchronized (this.mRendererControllerList) {
            try {
                if (this.mRendererControllerList.size() == 0) {
                    return;
                }
                int size = this.mRendererControllerList.size();
                for (int i = 0; i < size; i++) {
                    this.mRendererControllerList.get(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void doResume() {
        synchronized (this.mRendererControllerList) {
            try {
                if (this.mRendererControllerList.size() == 0) {
                    return;
                }
                int size = this.mRendererControllerList.size();
                for (int i = 0; i < size; i++) {
                    this.mRendererControllerList.get(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static RenderThread globalThread() {
        return globalThread(false);
    }

    public static RenderThread globalThread(boolean z) {
        RenderThread renderThread;
        synchronized (sGlobalThreadLock) {
            try {
                if (sGlobalThread == null) {
                    sGlobalThread = new RenderThread();
                }
                if (z && !sGlobalThread.isStarted()) {
                    try {
                        sGlobalThread.start();
                    } catch (IllegalThreadStateException unused) {
                    }
                }
                renderThread = sGlobalThread;
            } catch (Throwable th) {
                throw th;
            }
        }
        return renderThread;
    }

    public static void globalThreadStop() {
        synchronized (sGlobalThreadLock) {
            try {
                RenderThread renderThread = sGlobalThread;
                if (renderThread != null) {
                    renderThread.setStop();
                    sGlobalThread = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void initCmdThread() {
        HandlerThread handlerThread = new HandlerThread("cmd");
        this.mCmdThread = handlerThread;
        handlerThread.start();
        this.mCmdHanlder = new CommandThreadHandler(this.mCmdThread.getLooper());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPausedImpl(boolean z) {
        if (this.mStop) {
            signal();
        }
        if (this.mPaused == z) {
            return;
        }
        synchronized (this.mResumeSignal) {
            try {
                this.mPaused = z;
                if (!z) {
                    this.mResumeSignal.notify();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        signal();
    }

    private final void waitSleep(long j) {
        if (this.mSignaled || j <= 0) {
            return;
        }
        synchronized (this.mSleepSignal) {
            if (!this.mSignaled) {
                try {
                    this.mSleepSignal.wait(j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void waiteForResume() {
        try {
            this.mResumeSignal.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addRendererController(RendererController rendererController) {
        synchronized (this.mRendererControllerList) {
            try {
                if (this.mRendererControllerList.contains(rendererController)) {
                    MamlLog.w(LOG_TAG, "addRendererController: RendererController already exists");
                } else {
                    this.mRendererControllerList.add(rendererController);
                    setPaused(false);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean isStarted() {
        return this.mStarted;
    }

    public void removeRendererController(RendererController rendererController) {
        synchronized (this.mRendererControllerList) {
            this.mRendererControllerList.remove(rendererController);
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        MamlLog.i(LOG_TAG, "RenderThread started");
        try {
            doInit();
            this.mStarted = true;
            while (!this.mStop) {
                if (this.mPaused) {
                    synchronized (this.mResumeSignal) {
                        try {
                            if (this.mPaused) {
                                doPause();
                                MamlLog.i(LOG_TAG, "RenderThread paused, waiting for signal");
                                waiteForResume();
                                MamlLog.i(LOG_TAG, "RenderThread resumed");
                                doResume();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
                if (this.mStop) {
                    break;
                }
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                synchronized (this.mRendererControllerList) {
                    try {
                        int size = this.mRendererControllerList.size();
                        long j = Long.MAX_VALUE;
                        boolean z = true;
                        for (int i = 0; i < size && !this.mPaused; i++) {
                            RendererController rendererController = this.mRendererControllerList.get(i);
                            if (!rendererController.isSelfPaused() || rendererController.hasRunnable()) {
                                if (!rendererController.hasInited()) {
                                    rendererController.init();
                                }
                                long jUpdateIfNeeded = rendererController.updateIfNeeded(jElapsedRealtime);
                                if (jUpdateIfNeeded < j) {
                                    j = jUpdateIfNeeded;
                                }
                                z = false;
                            }
                        }
                        if (size == 0 || z) {
                            this.mPaused = true;
                            MamlLog.i(LOG_TAG, "All controllers paused.");
                        } else {
                            waitSleep(j);
                            this.mSignaled = false;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }
            }
        } catch (Exception | OutOfMemoryError e) {
            MamlLog.e(LOG_TAG, e.toString());
        }
        doFinish();
        this.mCmdThread.quit();
        MamlLog.i(LOG_TAG, "RenderThread stopped");
    }

    public void setPaused(boolean z) {
        this.mCmdHanlder.setPause(z);
    }

    public void setStop() {
        this.mStop = true;
        setPaused(false);
    }

    public void signal() {
        if (this.mSignaled) {
            return;
        }
        synchronized (this.mSleepSignal) {
            this.mSignaled = true;
            this.mSleepSignal.notify();
        }
    }
}
