package com.miui.maml;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import androidx.collection.ArraySet;
import com.miui.maml.util.MamlLog;
import java.lang.ref.WeakReference;
import miuix.animation.internal.FolmeCore;

/* JADX INFO: loaded from: classes2.dex */
public class RenderVsyncUpdater {
    private static final String LOG_TAG = "RenderVsyncUpdater";
    private FrameDisplayEventReceiver mDisplayEventReceiver;
    private Handler mHandler;
    private boolean mPaused;
    private ArraySet<WeakReference<RendererController>> mRendererControllerList;
    private final Runnable mScheduleFrame;
    private boolean mStopRefresh;
    private int mSyncInterval;
    private long mVsyncLeft;

    public final class FrameDisplayEventReceiver extends MamlDisplayEventReceiver implements Runnable {
        public FrameDisplayEventReceiver(Looper looper) {
            super(looper);
        }

        @Override // com.miui.maml.MamlDisplayEventReceiver
        public void onVsync(long j) {
            RenderVsyncUpdater.access$122(RenderVsyncUpdater.this, 1L);
            RenderVsyncUpdater.this.mHandler.sendMessageAtTime(Message.obtain(RenderVsyncUpdater.this.mHandler, this), j / FolmeCore.NANOS_TO_MS);
        }

        @Override // java.lang.Runnable
        public void run() {
            if (RenderVsyncUpdater.this.mVsyncLeft <= 0) {
                RenderVsyncUpdater.this.scheduleFrame();
            } else {
                if (RenderVsyncUpdater.this.mPaused || RenderVsyncUpdater.this.mStopRefresh) {
                    return;
                }
                scheduleVsync();
            }
        }
    }

    public static class RenderVsyncUpdaterHolder {
        public static final RenderVsyncUpdater INSTANCE = new RenderVsyncUpdater();

        private RenderVsyncUpdaterHolder() {
        }
    }

    private RenderVsyncUpdater() {
        this.mRendererControllerList = new ArraySet<>();
        this.mSyncInterval = 16;
        this.mScheduleFrame = new Runnable() { // from class: com.miui.maml.RenderVsyncUpdater$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.scheduleFrame();
            }
        };
        Looper looperMyLooper = "android.ui".equals(Thread.currentThread().getName()) ? Looper.myLooper() : Looper.getMainLooper();
        this.mHandler = new Handler(looperMyLooper);
        this.mDisplayEventReceiver = new FrameDisplayEventReceiver(looperMyLooper);
    }

    public static /* synthetic */ long access$122(RenderVsyncUpdater renderVsyncUpdater, long j) {
        long j2 = renderVsyncUpdater.mVsyncLeft - j;
        renderVsyncUpdater.mVsyncLeft = j2;
        return j2;
    }

    private void doRunUpdater() {
        if (this.mVsyncLeft > 0) {
            this.mDisplayEventReceiver.scheduleVsync();
        } else {
            if (this.mHandler.hasCallbacks(this.mScheduleFrame)) {
                return;
            }
            this.mHandler.post(this.mScheduleFrame);
        }
    }

    public static RenderVsyncUpdater getInstance() {
        return RenderVsyncUpdaterHolder.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleFrame() {
        long j;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        synchronized (this.mRendererControllerList) {
            try {
                int size = this.mRendererControllerList.size();
                boolean z = true;
                j = Long.MAX_VALUE;
                for (int i = size - 1; i >= 0; i--) {
                    RendererController rendererController = (RendererController) ((WeakReference) this.mRendererControllerList.valueAt(i)).get();
                    if (rendererController == null) {
                        this.mRendererControllerList.removeAt(i);
                    } else if (!rendererController.isSelfPaused() || rendererController.hasRunnable()) {
                        if (!rendererController.hasInited()) {
                            rendererController.init();
                        }
                        long jUpdateIfNeeded = rendererController.updateIfNeeded(jElapsedRealtime);
                        if (jUpdateIfNeeded < j) {
                            z = false;
                            j = jUpdateIfNeeded;
                        } else {
                            z = false;
                        }
                    }
                }
                if (size == 0 || z) {
                    this.mPaused = true;
                    MamlLog.i(LOG_TAG, "All controllers paused.");
                } else {
                    this.mPaused = false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        boolean z2 = j == Long.MAX_VALUE;
        this.mStopRefresh = z2;
        if (z2 || this.mPaused || j <= 0) {
            return;
        }
        long j2 = j / ((long) this.mSyncInterval);
        this.mVsyncLeft = j2;
        if (j2 > 0) {
            this.mVsyncLeft = j2 - 1;
        }
        this.mDisplayEventReceiver.scheduleVsync();
    }

    public void addRendererController(RendererController rendererController) {
        synchronized (this.mRendererControllerList) {
            try {
                int size = this.mRendererControllerList.size();
                for (int i = 0; i < size; i++) {
                    if (((RendererController) ((WeakReference) this.mRendererControllerList.valueAt(i)).get()) == rendererController) {
                        return;
                    }
                }
                this.mRendererControllerList.add(new WeakReference(rendererController));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void forceUpdate() {
        this.mVsyncLeft = 0L;
        doRunUpdater();
    }

    public void onResume() {
        this.mPaused = false;
        this.mStopRefresh = false;
        forceUpdate();
    }

    public void removeRendererController(RendererController rendererController) {
        synchronized (this.mRendererControllerList) {
            try {
                for (int size = this.mRendererControllerList.size() - 1; size >= 0; size--) {
                    RendererController rendererController2 = (RendererController) ((WeakReference) this.mRendererControllerList.valueAt(size)).get();
                    if (rendererController2 != null && rendererController2 != rendererController) {
                    }
                    this.mRendererControllerList.removeAt(size);
                    break;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setSyncInterval(int i) {
        this.mSyncInterval = i;
    }

    public void triggerUpdate() {
        doRunUpdater();
    }
}
