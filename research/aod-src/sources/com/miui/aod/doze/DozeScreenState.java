package com.miui.aod.doze;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.miui.aod.AODApplication;
import com.miui.aod.AODUpdatePositionController;
import com.miui.aod.AODView;
import com.miui.aod.DozeHost;
import com.miui.aod.Utils;
import com.miui.aod.category.FlipLinkageClockCategoryInfo;
import com.miui.aod.monitor.AodViewMonitor;
import com.miui.aod.util.AlarmTimeout;
import com.miui.aod.util.wakelock.SettableWakeLock;
import com.miui.aod.util.wakelock.WakeLock;
import com.miui.aod.utils.CommonUtils;
import com.miui.aod.utils.PowerManagerUtils;
import com.miui.aod.widget.AODSettings;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class DozeScreenState implements DozeMachine.Part {
    public final Context mContext;
    public final DozeMachine.Service mDozeService;
    public final Handler mHandler;
    public final DozeHost mHost;
    public final Runnable mResetScreenTask;
    public final AlarmTimeout mResetScreenTaskTimeout;
    public final SettableWakeLock mWakeLock;
    public final Runnable mApplyPendingScreenState = new Runnable() { // from class: com.miui.aod.doze.DozeScreenState$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.applyPendingScreenState();
        }
    };
    public final Runnable mDozeFollowupSample = new Runnable() { // from class: com.miui.aod.doze.DozeScreenState.1
        @Override // java.lang.Runnable
        public void run() {
            try {
                AODView aODView = DozeScreenState.this.mHost.getAODView();
                if (aODView == null || !aODView.isAttachedToWindow()) {
                    return;
                }
                AodViewMonitor.sampleOnAodEnter(aODView, AODSettings.getClockStyleInfo(DozeScreenState.this.mContext));
            } catch (Exception e) {
                Log.w("DozeScreenState", "doze-state sample runnable error", e);
            }
        }
    };
    public int mPendingScreenState = 0;
    public int mCurrentDisplayState = 0;
    public boolean isDestroyed = false;
    public boolean mFingerprintPressing = false;
    public final DozeHost.Callback mHostCallback = new DozeHost.Callback() { // from class: com.miui.aod.doze.DozeScreenState.2
        @Override // com.miui.aod.DozeHost.Callback
        public void onFingerprintPressed(boolean z, boolean z2) {
            Log.d("DozeScreenState", "onFingerprintPressed: " + z);
            DozeScreenState.this.mFingerprintPressing = z;
            if (Utils.isGxzwSensor() && z && DozeScreenState.this.mCurrentDisplayState == 2 && DozeScreenState.this.mResetScreenTaskTimeout.isScheduled() && !DozeScreenState.this.mHost.isFullAod()) {
                DozeScreenState.this.mDozeService.getRequestDoze().onRequestClockHide();
            }
        }
    };

    /* JADX INFO: renamed from: com.miui.aod.doze.DozeScreenState$3, reason: invalid class name */
    public class AnonymousClass3 implements Runnable {
        public AnonymousClass3() {
        }

        @Override // java.lang.Runnable
        public void run() {
            AODUpdatePositionController positionController;
            if (DozeScreenState.this.mFingerprintPressing) {
                DozeScreenState.this.mHandler.postDelayed(this, 1000L);
                return;
            }
            if (!DozeScreenState.this.mHost.isFullAod()) {
                DozeScreenState.this.mDozeService.setDozeScreenState(1);
                if (!DozeScreenState.this.isDestroyed) {
                    DozeScreenState.this.mDozeService.getRequestDoze().onRequestClockHide();
                    if (DozeScreenState.this.mHost.getAODView() != null && (positionController = DozeScreenState.this.mHost.getAODView().getPositionController()) != null) {
                        positionController.setCanMove(true);
                        positionController.cancelAlarm();
                    }
                }
            }
            DozeScreenState.this.mCurrentDisplayState = 1;
            DozeScreenState.this.mHandler.postDelayed(DozeScreenState.this.mApplyPendingScreenState, 100L);
            DozeScreenState.this.mHandler.postDelayed(new Runnable() { // from class: com.miui.aod.doze.DozeScreenState$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DozeScreenState.this.mHost.requestDrawWakelock(4000L, "DozeScreenState#mResetScreenTask");
                }
            }, 100L);
        }
    }

    public DozeScreenState(Context context, DozeMachine.Service service, AlarmManager alarmManager, Handler handler, WakeLock wakeLock) {
        final AnonymousClass3 anonymousClass3 = new AnonymousClass3();
        this.mResetScreenTask = anonymousClass3;
        this.mContext = context;
        this.mDozeService = service;
        this.mHandler = handler;
        this.mWakeLock = new SettableWakeLock(wakeLock, "DozeScreenState");
        this.mHost = service.getHost();
        Objects.requireNonNull(anonymousClass3);
        this.mResetScreenTaskTimeout = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener() { // from class: com.miui.aod.doze.DozeScreenState$$ExternalSyntheticLambda1
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                anonymousClass3.run();
            }
        }, "DozeScreenState", handler);
    }

    public final void applyPendingScreenState() {
        Log.w("DozeScreenState", "applyPendingScreenState: mPendingScreenState " + this.mPendingScreenState);
        if (applyScreenState(this.mPendingScreenState)) {
            this.mPendingScreenState = 0;
        }
    }

    public final boolean applyScreenState(int i) {
        int i2;
        if (Utils.isGxzwSensor() && this.mFingerprintPressing && i == 4 && (i2 = this.mCurrentDisplayState) != 0 && i2 != 1) {
            Log.w("DozeScreenState", "applyScreenState: mFingerprintPressing");
            this.mPendingScreenState = 4;
            if (!CommonUtils.hasCallbacks(this.mHandler, this.mApplyPendingScreenState)) {
                this.mHandler.postDelayed(this.mApplyPendingScreenState, 1000L);
            }
            return false;
        }
        if (i != 0) {
            Log.w("DozeScreenState", "applyScreenState: setDozeScreenState " + i);
            if (this.mResetScreenTaskTimeout.isScheduled()) {
                Log.i("DozeScreenState", "mResetScreenTask is pending ");
                this.mPendingScreenState = i;
            } else {
                this.mDozeService.setDozeScreenState(i);
                this.mCurrentDisplayState = i;
                this.mPendingScreenState = 0;
                if ((i == 3 || i == 4) && !this.mHost.isFullAod() && this.mHost.isAodVisible()) {
                    try {
                        if (!(AODSettings.getClockStyleInfo(this.mContext) instanceof FlipLinkageClockCategoryInfo)) {
                            this.mHandler.removeCallbacks(this.mDozeFollowupSample);
                            this.mHandler.postDelayed(this.mDozeFollowupSample, 250L);
                        }
                    } catch (Throwable th) {
                        Log.w("DozeScreenState", "doze-state sample trigger error", th);
                    }
                }
                this.mWakeLock.setAcquired(false);
            }
        }
        return true;
    }

    @Override // com.miui.aod.doze.DozeMachine.Part
    public void destroy() {
        this.mHandler.removeCallbacks(this.mApplyPendingScreenState);
        this.mHandler.removeCallbacks(this.mDozeFollowupSample);
        this.mResetScreenTaskTimeout.cancel();
        this.mResetScreenTaskTimeout.release();
        this.isDestroyed = true;
    }

    @Override // com.miui.aod.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        int iScreenState = state2.screenState();
        DozeMachine.State state3 = DozeMachine.State.INITIALIZED;
        boolean z = false;
        if (state2 == state3) {
            this.mHost.addCallback(this.mHostCallback);
        } else if (state2 == DozeMachine.State.FINISH) {
            this.mPendingScreenState = 0;
            this.mHandler.removeCallbacks(this.mApplyPendingScreenState);
            this.mHandler.removeCallbacks(this.mDozeFollowupSample);
            this.mResetScreenTaskTimeout.cancel();
            applyScreenState(iScreenState);
            this.mWakeLock.setAcquired(false);
            this.mHost.removeCallback(this.mHostCallback);
            this.mFingerprintPressing = false;
            return;
        }
        if (iScreenState == 0) {
            return;
        }
        boolean zHasCallbacks = CommonUtils.hasCallbacks(this.mHandler, this.mApplyPendingScreenState);
        if (!zHasCallbacks && state != state3) {
            applyScreenState(iScreenState);
            return;
        }
        this.mPendingScreenState = iScreenState;
        boolean zNeedKeepScreenOnAtFirst = AODSettings.needKeepScreenOnAtFirst();
        if (!zHasCallbacks && Utils.isUsingSuperWallpaperStyle(AODApplication.sInstance)) {
            PowerManagerUtils.setDozeAfterScreenOff(this.mHost.mPowerManager, !zNeedKeepScreenOnAtFirst);
        }
        if (state == state3 && state2 == DozeMachine.State.DOZE_AOD && zNeedKeepScreenOnAtFirst) {
            z = true;
        }
        if (z) {
            this.mWakeLock.setAcquired(true);
        }
        if (zHasCallbacks) {
            return;
        }
        if (z) {
            this.mResetScreenTaskTimeout.schedule(6000L, 2);
            return;
        }
        if (!AODSettings.needFullAod() && (Utils.isUsingSuperWallpaperStyle(AODApplication.sInstance) || (Utils.isLinkageState(AODApplication.sInstance) && AODSettings.needLinkageWallpaperAnim()))) {
            this.mHandler.post(new Runnable() { // from class: com.miui.aod.doze.DozeScreenState$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.mDozeService.getRequestDoze().onRequestClockHide();
                }
            });
        }
        this.mHandler.post(this.mApplyPendingScreenState);
    }
}
