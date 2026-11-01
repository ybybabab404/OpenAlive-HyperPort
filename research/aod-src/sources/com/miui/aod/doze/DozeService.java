package com.miui.aod.doze;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.service.dreams.DreamService;
import android.util.Log;
import android.view.IDisplayFoldListener;
import android.view.ViewGroup;
import com.android.systemui.plugins.DozeServicePlugin;
import com.miui.aod.AodViewManager;
import com.miui.aod.DozeHost;
import com.miui.aod.Utils;
import com.miui.aod.resource.DrawableCache;
import com.miui.aod.util.DisplayUtils;
import com.miui.aod.util.foldchange.FoldDisplayChangeUtils;
import com.miui.aod.util.wakelock.WakeLock;
import com.miui.aod.utils.CommonUtils;
import com.miui.aod.utils.DreamServiceUtils;
import com.miui.aod.widget.AODSettings;

/* JADX INFO: loaded from: classes.dex */
public final class DozeService implements DozeMachine.Service, DreamServiceCallback {
    public static boolean mDreamStart = false;
    public final DozeServicePluginImpl dozeServicePlugin;
    public boolean mAcquire;
    public int mCurrentBrightness;
    public DozeMachine mDozeMachine;
    public final DreamService mDreamService;
    public Handler mHandler;
    public int mPendingBrightness;
    public final DozeServicePlugin.RequestDoze mRequestDoze;
    public int mScreenState;
    public WakeLock mWakeLock;
    public final Context pluginContext;
    public final Context sysuiContext;
    public ViewGroup viewGroup;
    public final Runnable mDisplayFoldChangedRunnable = new Runnable() { // from class: com.miui.aod.doze.DozeService.1
        @Override // java.lang.Runnable
        public void run() {
            if (DozeService.this.getHost() == null) {
                return;
            }
            Log.d("DozeService", "AodOnDisplayFoldChanged delay set STATE_DOZE_SUSPEND getAnimationState is " + DozeService.this.getHost().getAnimationState());
            if (DozeService.this.getHost().getAnimationState()) {
                return;
            }
            DozeService.this.getHost().setAnimationState(false);
            if (Utils.isNeedSetScreenOff(DozeService.this.sysuiContext)) {
                DozeService.this.setDozeScreenState(4);
            }
        }
    };
    public final Runnable mDisplayFoldSetWindowRunnable = new Runnable() { // from class: com.miui.aod.doze.DozeService$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            DozeService.$r8$lambda$SbfdNnCdPZ0cYzRJIMst_tdLITo(this.f$0);
        }
    };
    public boolean mFolded = false;
    public final DisplayFoldListener mFoldListener = new DisplayFoldListener();
    public boolean isNeedResetState = false;
    public final Runnable mResetDozeSuspendTask = new Runnable() { // from class: com.miui.aod.doze.DozeService$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.setDozeScreenState(4);
        }
    };

    public class DisplayFoldListener extends IDisplayFoldListener.Stub {
        /* JADX INFO: renamed from: $r8$lambda$33BPLLlfxo9yv2PhhtM-UTRGx_E, reason: not valid java name */
        public static /* synthetic */ void m326$r8$lambda$33BPLLlfxo9yv2PhhtMUTRGx_E(DisplayFoldListener displayFoldListener) {
            if (DozeService.this.getHost() != null) {
                DozeService.this.getHost().removeAODViewRunnable.run();
            }
        }

        public DisplayFoldListener() {
        }

        @Override // android.view.IDisplayFoldListener
        public void onDisplayFoldChanged(int i, boolean z) {
            if (Utils.isFoldDevice()) {
                if (DozeService.this.getHost() != null && DozeService.this.mFolded && !z) {
                    DozeService.this.mFolded = z;
                    DozeService.this.mHandler.post(new Runnable() { // from class: com.miui.aod.doze.DozeService$DisplayFoldListener$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            DozeService.DisplayFoldListener.m326$r8$lambda$33BPLLlfxo9yv2PhhtMUTRGx_E(this.f$0);
                        }
                    });
                    return;
                }
                if (DozeService.this.mFolded == z) {
                    return;
                }
                if (!DozeService.this.mFolded && DozeService.this.getHost() != null) {
                    Log.d("DozeService", "AodOnDisplayFoldChanged fold change, mFolded is : false folded true displayId " + i);
                    DozeService.this.mHandler.post(DozeService.this.mDisplayFoldSetWindowRunnable);
                    if (Utils.isNeedSetScreenOff(DozeService.this.sysuiContext)) {
                        Log.d("DozeService", " AodOnDisplayFoldChanged isNeedResetState " + DozeService.this.isNeedResetState);
                        DozeService.this.isNeedResetState = true;
                        DozeService.this.setDozeScreenState(1);
                    }
                }
            }
            DozeService.this.mFolded = z;
            if (Utils.isFoldDevice() && DozeService.this.isNeedResetState && DozeService.this.mFolded && DozeService.mDreamStart && DozeService.this.getHost() != null) {
                DozeService.this.mHandler.postDelayed(DozeService.this.mDisplayFoldChangedRunnable, 1000L);
                DozeService.this.getHost().requestDrawWakelock(2000L, "DozeService#DisplayFoldListener");
                DozeService.this.isNeedResetState = false;
            }
        }

        public void removeAllCallbacks() {
            if (DozeService.this.mHandler != null) {
                DozeService.this.mHandler.removeCallbacks(DozeService.this.mDisplayFoldSetWindowRunnable);
                DozeService.this.mHandler.removeCallbacks(DozeService.this.mDisplayFoldChangedRunnable);
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$SbfdNnCdPZ0cYzRJIMst_tdLITo(DozeService dozeService) {
        if (dozeService.getHost() == null) {
            return;
        }
        dozeService.getHost().setAodWindow();
    }

    public static /* synthetic */ void $r8$lambda$iSUChEe5qES3fjqmlzt5ZSPgpxs(DozeService dozeService) {
        dozeService.mWakeLock.release("DozeService");
        dozeService.mAcquire = false;
    }

    /* JADX INFO: renamed from: $r8$lambda$lAehDiGPgRBQjH2k8-l7H3KU9-c, reason: not valid java name */
    public static /* synthetic */ void m317$r8$lambda$lAehDiGPgRBQjH2k8l7H3KU9c(DozeService dozeService) {
        if (dozeService.mScreenState != 1) {
            int i = dozeService.mPendingBrightness;
            if (i == 0) {
                i = dozeService.mCurrentBrightness;
            }
            dozeService.setDozeScreenBrightness(i);
            dozeService.mPendingBrightness = 0;
        }
    }

    public DozeService(DozeServicePlugin.RequestDoze requestDoze, Context context, Context context2, DozeServicePluginImpl dozeServicePluginImpl) {
        DreamService dreamService = (DreamService) requestDoze;
        this.mDreamService = dreamService;
        this.mRequestDoze = requestDoze;
        this.sysuiContext = context;
        this.pluginContext = context2;
        this.dozeServicePlugin = dozeServicePluginImpl;
        DreamServiceUtils.setDebug(dreamService, true);
    }

    public void assembleMachine() {
        this.mDozeMachine = DozeFactory.assembleMachine(this, this.mRequestDoze);
    }

    public boolean create(boolean z) {
        if (getHost() == null) {
            finish();
            return false;
        }
        if (!Utils.supportAod(this.sysuiContext)) {
            finish();
            return false;
        }
        if (!Utils.isAodEnable(this.sysuiContext)) {
            boolean z2 = SettingsHolder.mDisplayReversion == 1;
            if ((!Utils.isGxzwSensor() || !Utils.isUnlockWithFingerprintPossible(this.sysuiContext, getHost()) || z2) && Utils.getKeyguardNotificationStatus(this.sysuiContext) != 2) {
                AodViewManager.INSTANCE.clearAodViewCache();
                DrawableCache.getInstance(this.sysuiContext).releaseBitmapCache();
                finish();
                return false;
            }
        }
        if (!z) {
            assembleMachine();
        }
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mWakeLock = WakeLock.wrap(WakeLock.createPartialInner(this.sysuiContext, "DozeService"));
        if (Utils.isFoldDevice() || Utils.isFlipDevice()) {
            FoldDisplayChangeUtils.registerDisplayFoldListener(this.mFoldListener);
        }
        return true;
    }

    public void destroy() {
        DozeMachine dozeMachine = this.mDozeMachine;
        if (dozeMachine != null) {
            dozeMachine.destroy();
        }
        this.mDozeMachine = null;
        if (Utils.isFoldDevice() || Utils.isFlipDevice()) {
            this.mFoldListener.removeAllCallbacks();
            FoldDisplayChangeUtils.unRegisterDisplayFoldListener(this.mFoldListener);
        }
    }

    public void dreamingStarted(ViewGroup viewGroup) {
        if (this.mDozeMachine == null) {
            return;
        }
        mDreamStart = true;
        this.viewGroup = viewGroup;
        if (!DozeHost.preDisplayedAod(getHost())) {
            this.viewGroup.removeAllViews();
        }
        viewGroup.setVisibility(0);
        this.mDozeMachine.requestState(DozeMachine.State.INITIALIZED);
        startDozing();
    }

    public void dreamingStopped() {
        if (getHost() != null) {
            getHost().releaseDrawWakelock();
        }
        mDreamStart = false;
        this.mHandler.removeCallbacks(this.mDisplayFoldChangedRunnable);
        this.mHandler.removeCallbacks(this.mResetDozeSuspendTask);
        this.mDozeMachine.requestState(DozeMachine.State.FINISH);
    }

    @Override // com.miui.aod.doze.DozeMachine.Service
    public void finish() {
        this.mDreamService.finish();
    }

    @Override // com.miui.aod.doze.DozeMachine.Service
    public DozeMachine getDozeMachine() {
        DozeMachine dozeMachine;
        if (!mDreamStart || (dozeMachine = this.mDozeMachine) == null) {
            return null;
        }
        return dozeMachine;
    }

    @Override // com.miui.aod.doze.DozeMachine.Service
    public DozeHost getHost() {
        return this.dozeServicePlugin.getHost();
    }

    @Override // com.miui.aod.doze.DozeMachine.Service
    public DozeServicePlugin.RequestDoze getRequestDoze() {
        return this.mRequestDoze;
    }

    @Override // com.miui.aod.doze.DozeMachine.Service
    public ViewGroup getSysuiContainer() {
        return this.viewGroup;
    }

    public boolean isDestroyed() {
        return this.mDozeMachine == null;
    }

    @Override // com.miui.aod.doze.DozeMachine.Service
    public boolean isFolded() {
        return this.mFolded;
    }

    @Override // com.miui.aod.doze.DozeMachine.Service
    public void requestState(DozeMachine.State state) {
        DozeMachine dozeMachine = this.mDozeMachine;
        if (dozeMachine != null) {
            dozeMachine.requestState(state);
        }
    }

    @Override // com.miui.aod.doze.DozeMachine.Service
    public void setDozeScreenBrightness(int i) {
        DozeHost host = getHost();
        if (host != null && host.isFullAod()) {
            Log.d("DozeService", "setDozeScreenBrightness: skip for full aod ... ");
            return;
        }
        Log.w("DozeService", "try to setDozeScreenBrightness: " + i + " mScreenState: " + this.mScreenState);
        if (mDreamStart) {
            if (this.mScreenState == 2) {
                this.mPendingBrightness = i;
                return;
            }
            Log.d("DozeService", "Doze setDozeScreenBrightness:" + i);
            if (host != null) {
                if (DisplayUtils.getDisplayState(this.sysuiContext) == 4) {
                    Log.w("DozeService", "setDozeScreenBrightness: display state is doze_suspend");
                    host.requestDrawWakelock(2000L, "DozeService#setDozeScreenBrightness");
                }
                if (Utils.isHardWareVendorMediaTek()) {
                    host.invalidateAODView();
                }
            } else {
                Log.e("DozeService", "setDozeScreenBrightness: dozeHost is null");
            }
            DreamServiceUtils.setDozeScreenBrightness(this.mDreamService, i);
            this.mCurrentBrightness = i;
        }
    }

    @Override // com.miui.aod.doze.DozeMachine.Service
    public void setDozeScreenState(int i) {
        int i2;
        if (isDestroyed()) {
            Log.i("DozeService", "setDozeScreenState: execute destroy process, skip ...");
            return;
        }
        DozeHost host = getHost();
        if (i == 4 && CommonUtils.hasCallbacks(this.mHandler, this.mResetDozeSuspendTask)) {
            Log.e("DozeService", "setDozeScreenState: " + i + "  skip ...");
            return;
        }
        this.mHandler.removeCallbacks(this.mResetDozeSuspendTask);
        if (host != null) {
            if (i == 1) {
                host.initMaskViewIfNeeded();
            }
            if (i == 4 || i == 6 || i == 3) {
                host.startMaskAnimationIfNeeded();
            }
        }
        DreamServiceUtils.setDozeScreenState(this.mDreamService, i);
        Log.e("DozeService", "setDozeScreenState: " + i + " mPendingBrightness " + this.mPendingBrightness + " mCurrentBrightness " + this.mCurrentBrightness);
        int i3 = this.mScreenState;
        this.mScreenState = i;
        if (i3 == 2 && ((i == 3 || i == 4) && (this.mCurrentBrightness != 0 || this.mPendingBrightness != 0))) {
            if (host != null) {
                host.requestDrawWakelock(500L, "DozeService#setDozeScreenState");
            }
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.aod.doze.DozeService$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    DozeService.m317$r8$lambda$lAehDiGPgRBQjH2k8l7H3KU9c(this.f$0);
                }
            }, 200L);
        }
        int i4 = this.mScreenState;
        if ((i4 == 3 || i4 == 4) && AODSettings.needKeepScreenOnAtFirst() && (i2 = this.mPendingBrightness) != 0) {
            if (i2 == 1 || !Utils.isVideoScreenDevice()) {
                setDozeScreenBrightness(this.mPendingBrightness);
            }
            this.mPendingBrightness = 0;
        }
        DozeMachine dozeMachine = this.mDozeMachine;
        if (dozeMachine != null) {
            dozeMachine.onScreenState(i);
        }
    }

    public final void start() {
        DreamServiceUtils.startDozing(this.mDreamService);
        if (this.mAcquire) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.aod.doze.DozeService$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    DozeService.$r8$lambda$iSUChEe5qES3fjqmlzt5ZSPgpxs(this.f$0);
                }
            }, 1000L);
        }
    }

    public void startDozing() {
        if (!this.mAcquire) {
            this.mWakeLock.acquire("DozeService");
            this.mAcquire = true;
        }
        start();
    }
}
