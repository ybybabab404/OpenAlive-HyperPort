package com.miui.aod.doze;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.service.attention.IAttentionCallback;
import android.util.Log;
import com.android.systemui.plugins.DozeServicePlugin;
import com.miui.aod.DozeHost;
import com.miui.aod.Utils;
import com.miui.aod.attention.AttentionManager;
import com.miui.aod.common.BackgroundThread;
import com.miui.aod.flip.FlipLinkageStyleController;
import com.miui.aod.util.AlarmTimeout;
import com.miui.aod.util.BatteryController;
import com.miui.aod.util.wakelock.WakeLock;
import com.miui.aod.utils.PowerManagerUtils;
import com.miui.aod.widget.AODSettings;
import java.util.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class MiuiShowStyleController implements DozeMachine.Part {
    public static final String TAG = "MiuiShowStyleController";
    public boolean isAttentionAvailable;
    public AlarmTimeout mAonConnectTimeOut;
    public IAttentionCallback mAttentionCallback;
    public AttentionManager mAttentionManager;
    public AlarmTimeout mCheckTimeout;
    public final Context mContext;
    public final Handler mHandler;
    public final AlarmTimeout mHideDozeTimeout;
    public long mHideTime;
    public final DozeHost mHost;
    public final DozeMachine mMachine;
    public final DozeServicePlugin.RequestDoze mRequestDoze;
    public final DozeMachine.Service mService;
    public final AlarmTimeout mShowDozeTimeout;
    public int mShowStyle;
    public long mShowTime;
    public final AlarmTimeout mSmartHideDozeTimeout;
    public final WakeLock mWakeLock;
    public boolean lightByFOD = false;
    public boolean mAttentionDeferred = false;
    public boolean pausingBefore = false;
    public final DozeHost.Callback mHostCallback = new DozeHost.Callback() { // from class: com.miui.aod.doze.MiuiShowStyleController.2
        @Override // com.miui.aod.DozeHost.Callback
        public void fireAodState(boolean z, String str) {
            if (z || !Utils.isShowTemporary(MiuiShowStyleController.this.mContext) || MiuiShowStyleController.this.mHideDozeTimeout.isScheduled()) {
                return;
            }
            if (!str.equals("reason_fod")) {
                MiuiShowStyleController.this.mHost.setAodVisibility(true, null);
            }
            MiuiShowStyleController.this.mHideDozeTimeout.schedule(Utils.getTemporaryStyleDuration(), 1);
        }

        @Override // com.miui.aod.DozeHost.Callback
        public void onAodAnimate(boolean z) {
            if (MiuiShowStyleController.this.mMachine.getState() == DozeMachine.State.DOZE_AOD) {
                if (Utils.isShowSmart(MiuiShowStyleController.this.mContext)) {
                    MiuiShowStyleController.this.mHost.setAodVisibility(true, null);
                    MiuiShowStyleController.this.mSmartHideDozeTimeout.schedule(Utils.getSmartStyleDuration(), 2);
                } else if (Utils.isShowTemporary(MiuiShowStyleController.this.mContext)) {
                    MiuiShowStyleController.this.mHost.setAodVisibility(true, null);
                    MiuiShowStyleController.this.mHideDozeTimeout.schedule(Utils.getTemporaryStyleDuration(), 1);
                }
            }
        }
    };

    /* JADX INFO: renamed from: com.miui.aod.doze.MiuiShowStyleController$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$aod$doze$DozeMachine$State;

        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$miui$aod$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.INITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.DOZE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.FINISH.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$7kCKOuU5nVNBLDW7Wo5DVbI8yzA(MiuiShowStyleController miuiShowStyleController) {
        miuiShowStyleController.onShowDoze(false, false);
        if (Utils.isTimingStyle(miuiShowStyleController.mContext)) {
            miuiShowStyleController.mHost.checkFullAodVisibility(false);
            if (Utils.isUsingSuperWallpaperStyle(miuiShowStyleController.mContext)) {
                PowerManagerUtils.setDozeAfterScreenOff(miuiShowStyleController.mHost.mPowerManager, true);
            }
            miuiShowStyleController.checkTime();
            miuiShowStyleController.mShowDozeTimeout.schedule(miuiShowStyleController.mShowTime, 1);
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$I_-xWOddqQ6xdtes3rY4A--tk_0, reason: not valid java name */
    public static /* synthetic */ void m376$r8$lambda$I_xWOddqQ6xdtes3rY4Atk_0(MiuiShowStyleController miuiShowStyleController) {
        if (miuiShowStyleController.mAttentionCallback != null) {
            miuiShowStyleController.cancelAttentionOnly();
            miuiShowStyleController.mAonConnectTimeOut.schedule(Utils.getAonConnectDuration(), 2);
        }
    }

    public static /* synthetic */ void $r8$lambda$Xy8fvMOTEcrDNgjfprksnZK6k00(MiuiShowStyleController miuiShowStyleController) {
        miuiShowStyleController.mShowDozeTimeout.cancel();
        miuiShowStyleController.mHideDozeTimeout.cancel();
        miuiShowStyleController.mShowDozeTimeout.release();
        miuiShowStyleController.mHideDozeTimeout.release();
    }

    public MiuiShowStyleController(Context context, Handler handler, DozeMachine dozeMachine, AlarmManager alarmManager, DozeMachine.Service service, WakeLock wakeLock) {
        this.mContext = context;
        this.mMachine = dozeMachine;
        this.mService = service;
        this.mHost = service.getHost();
        this.mRequestDoze = service.getRequestDoze();
        this.mHandler = handler;
        this.mWakeLock = wakeLock;
        this.mShowDozeTimeout = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener() { // from class: com.miui.aod.doze.MiuiShowStyleController$$ExternalSyntheticLambda2
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                this.f$0.showDoze();
            }
        }, "DarkenAlarmTimeout", handler);
        this.mHideDozeTimeout = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener() { // from class: com.miui.aod.doze.MiuiShowStyleController$$ExternalSyntheticLambda3
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                this.f$0.hideDoze();
            }
        }, "OffAlarmTimeout", handler);
        this.mSmartHideDozeTimeout = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener() { // from class: com.miui.aod.doze.MiuiShowStyleController$$ExternalSyntheticLambda4
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                this.f$0.smartHide();
            }
        }, "SmartOffAlarmTimeout", handler);
        int showStyle = Utils.getShowStyle(context);
        this.mShowStyle = showStyle;
        this.mShowStyle = Utils.verifyShowStyle(context, showStyle);
        if (Utils.isTimingStyle(context)) {
            checkTime();
        }
        boolean zIsAttentionAvailable = isAttentionAvailable();
        this.isAttentionAvailable = zIsAttentionAvailable;
        if (zIsAttentionAvailable) {
            this.mAttentionManager = new AttentionManager(context, wakeLock);
            this.mCheckTimeout = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener() { // from class: com.miui.aod.doze.MiuiShowStyleController$$ExternalSyntheticLambda5
                @Override // android.app.AlarmManager.OnAlarmListener
                public final void onAlarm() {
                    this.f$0.checkAttentionFirst();
                }
            }, "smart_doze_time_tick", handler);
            this.mAonConnectTimeOut = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener() { // from class: com.miui.aod.doze.MiuiShowStyleController$$ExternalSyntheticLambda6
                @Override // android.app.AlarmManager.OnAlarmListener
                public final void onAlarm() {
                    this.f$0.checkAttention();
                }
            }, "CheckAttentionTimeout", handler);
        }
        if (!Utils.isShowSmart(context) || this.isAttentionAvailable) {
            return;
        }
        Log.wtf(TAG, "MiuiShowStyleController with showSmart but Attention is not available , so we treat it as Always on ...");
    }

    public final void cancelAttention() {
        this.mSmartHideDozeTimeout.cancel();
        cancelAttentionOnly();
    }

    public final void cancelAttentionOnly() {
        IAttentionCallback iAttentionCallback;
        Log.d(TAG, "cancelAttention");
        AttentionManager attentionManager = this.mAttentionManager;
        if (attentionManager != null && (iAttentionCallback = this.mAttentionCallback) != null) {
            attentionManager.cancelAttentionCheck(iAttentionCallback, "cancel");
        }
        releaseAttention();
    }

    public final void checkAttention() {
        Log.d(TAG, "checkAttention");
        this.mAonConnectTimeOut.cancel();
        this.mAttentionCallback = this.mAttentionManager.checkAttention(new AttentionManager.AttentionCallback() { // from class: com.miui.aod.doze.MiuiShowStyleController.1
            @Override // com.miui.aod.attention.AttentionManager.AttentionCallback
            public void onFailure(IAttentionCallback iAttentionCallback, int i) {
                Log.w(MiuiShowStyleController.TAG, "onFailure: " + i, new Throwable());
                MiuiShowStyleController.this.mSmartHideDozeTimeout.cancel();
            }

            @Override // com.miui.aod.attention.AttentionManager.AttentionCallback
            public void onSuccess(IAttentionCallback iAttentionCallback, int i, long j) {
                Log.v(MiuiShowStyleController.TAG, "onSuccess() called with: result = [" + i + "], timestamp = [" + j + "]");
                if (i == 0) {
                    MiuiShowStyleController.this.smartHide();
                } else if (i == 1) {
                    MiuiShowStyleController.this.mSmartHideDozeTimeout.cancel();
                    MiuiShowStyleController.this.smartShow();
                    Log.d(MiuiShowStyleController.TAG, "reSchedule 5s off");
                    MiuiShowStyleController.this.mSmartHideDozeTimeout.schedule(Utils.getSmartStyleDuration(), 1);
                }
            }
        }, "checkAttention");
    }

    public final void checkAttentionFirst() {
        DozeMachine.State state = this.mMachine.getState();
        if (Utils.isShowAlwaysWhenCharging(this.mContext) && BatteryController.isCharging()) {
            Log.i(TAG, "no need to checkAttention, show always when charging.");
            return;
        }
        if (this.mHost.isAodVisible() || state == DozeMachine.State.DOZE_AOD_PAUSING || state == DozeMachine.State.DOZE_AOD_PAUSED) {
            Log.d(TAG, "schedule 5s off for the First time");
            this.mSmartHideDozeTimeout.schedule(Utils.getSmartStyleDuration(), 2);
        }
        checkAttention();
    }

    public final void checkTime() {
        this.mShowTime = 0L;
        this.mHideTime = 0L;
        long aodStartTime = Utils.getAodStartTime(this.mContext);
        long aodEndTime = Utils.getAodEndTime(this.mContext);
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(11);
        long j = (((long) ((i * 60) + calendar.get(12))) * 60000) + (((long) calendar.get(13)) * 1000) + 1;
        long j2 = aodStartTime - j;
        if (aodStartTime <= j) {
            j2 += 86400000;
        }
        if (aodStartTime <= aodEndTime) {
            if (j < aodStartTime || j > aodEndTime) {
                this.mShowTime = j2;
                return;
            } else {
                this.mHideTime = aodEndTime - j;
                return;
            }
        }
        if (j < aodStartTime && j > aodEndTime) {
            this.mShowTime = j2;
            return;
        }
        if (aodEndTime <= j) {
            aodEndTime += 86400000;
        }
        this.mHideTime = aodEndTime - j;
    }

    public final void hideDoze() {
        this.mHandler.post(this.mWakeLock.wrapWithLog(new Runnable() { // from class: com.miui.aod.doze.MiuiShowStyleController$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                MiuiShowStyleController.$r8$lambda$7kCKOuU5nVNBLDW7Wo5DVbI8yzA(this.f$0);
            }
        }, "MiuiDozeTimeController#hideDoze"));
    }

    public final boolean isAttentionAvailable() {
        if (!Utils.isShowSmart(this.mContext) && (!Utils.isShowAlwaysWhenCharging(this.mContext) || BatteryController.isCharging())) {
            return false;
        }
        FlipLinkageStyleController flipLinkageStyleController = FlipLinkageStyleController.INSTANCE;
        return !(flipLinkageStyleController.isUsingFlip(this.mContext) && flipLinkageStyleController.isFlipped() && !Utils.SUPPORT_AOD_OUTER_SCREEN_AON) && AttentionManager.supportAttention(this.mContext);
    }

    public void onAodFire(int i) {
        if ((i == 9 || i == 10) && Utils.isAodEnable(this.mContext)) {
            int showStyle = Utils.getShowStyle(this.mContext);
            if (showStyle == 0) {
                this.mHost.setAodVisibility(true, null, true);
                this.mHideDozeTimeout.schedule(Utils.getTemporaryStyleDuration(), 2);
                return;
            }
            if (showStyle != 1) {
                if (showStyle == 2 || showStyle == 3) {
                    this.mHost.setAodVisibility(true, null, true);
                    return;
                }
                return;
            }
            checkTime();
            scheduleTimingStyle();
            if (Utils.aodDisabledForScheduleMode(this.mContext)) {
                this.mHost.setAodVisibility(false);
                return;
            } else {
                this.mHost.setAodVisibility(true, null, true);
                return;
            }
        }
        if (Utils.isShowTemporary(this.mContext) && i != 6) {
            this.mHideDozeTimeout.schedule(Utils.getTemporaryStyleDuration(), 2);
            if (this.mHost.getContainer() == null || this.mHost.getContainer().getVisibility() != 0) {
                this.mHost.setAodVisibility(true, null);
                return;
            } else {
                Log.d(TAG, "onAodFire: aod is visible, skip");
                return;
            }
        }
        if (Utils.isShowSmart(this.mContext) && i == 8) {
            this.mSmartHideDozeTimeout.schedule(Utils.getSmartStyleDuration(), 2);
            if (this.mHost.getContainer() == null || this.mHost.getContainer().getVisibility() != 0) {
                this.mHost.setAodVisibility(true, null);
            } else {
                Log.d(TAG, "onAodFire: aod is visible, skip");
            }
        }
    }

    public final void onShowDoze(boolean z, boolean z2) {
        String str = TAG;
        Log.i(str, "onShowDoze:" + z);
        DozeMachine.State state = this.mMachine.getState();
        boolean z3 = state == DozeMachine.State.DOZE_AOD_PAUSED;
        boolean z4 = state == DozeMachine.State.DOZE_AOD_PAUSING;
        DozeMachine.State state2 = DozeMachine.State.DOZE_AOD;
        boolean z5 = state == state2;
        boolean z6 = state == DozeMachine.State.DOZE;
        boolean z7 = state == DozeMachine.State.DOZE_PULSING;
        if (!z) {
            if (z5 || z3 || z4 || z7) {
                Log.i(str, "Hide, pausing AOD");
                this.mHost.setAodVisibility(false, new DozeHost.Callback() { // from class: com.miui.aod.doze.MiuiShowStyleController.3
                    @Override // com.miui.aod.DozeHost.Callback
                    public void onDozingRequested(boolean z8) {
                        if (MiuiShowStyleController.this.mService.getDozeMachine() == null || MiuiShowStyleController.this.mService.getDozeMachine().getState() == DozeMachine.State.UNINITIALIZED || MiuiShowStyleController.this.lightByFOD) {
                            return;
                        }
                        MiuiShowStyleController.this.mService.requestState(DozeMachine.State.DOZE);
                    }
                });
                return;
            }
            return;
        }
        if (z6) {
            Log.i(str, "Show, show AOD");
            this.mService.requestState(state2);
        }
        if (z6 || z5 || z7) {
            if (z2) {
                this.mHost.setAodVisibility(true, null);
            } else {
                this.mHost.setAnimationState(false);
                this.mHost.setAodVisibility(true, null);
            }
        }
    }

    @Override // com.miui.aod.doze.DozeMachine.Part
    public void preDestroyForChange() {
        if (this.mAttentionManager != null) {
            boolean zIsAttentionAvailable = isAttentionAvailable();
            this.isAttentionAvailable = zIsAttentionAvailable;
            if (zIsAttentionAvailable) {
                Log.d(TAG, "reschedule SmartOffAlarmTimeout for change");
                this.mSmartHideDozeTimeout.schedule(Utils.getSmartStyleDuration(), 2);
                return;
            }
            Log.d(TAG, "unbind for change");
            this.mCheckTimeout.cancel();
            this.mCheckTimeout.release();
            this.mAonConnectTimeOut.cancel();
            this.mAonConnectTimeOut.release();
            cancelAttention();
            releaseAttention();
            this.mAttentionManager.unbind();
            this.mAttentionManager = null;
        }
    }

    @Override // com.miui.aod.doze.DozeMachine.Part
    public void rebindForChange() {
        if (Utils.aodGone(this.mContext)) {
            this.mMachine.requestState(DozeMachine.State.DOZE);
        } else if (this.mAttentionDeferred && this.isAttentionAvailable && this.mAttentionManager != null) {
            this.mCheckTimeout.schedule(Utils.getAonConnectDuration(), 1);
            this.mAttentionDeferred = false;
        }
    }

    public final void releaseAttention() {
        AttentionManager attentionManager = this.mAttentionManager;
        if (attentionManager != null) {
            attentionManager.releaseAttentionCheck(this.mAttentionCallback);
            this.mAttentionCallback = null;
        }
    }

    public final void scheduleTimingStyle() {
        if (this.mShowTime > 0) {
            BackgroundThread.post(new Runnable() { // from class: com.miui.aod.doze.MiuiShowStyleController$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    MiuiShowStyleController miuiShowStyleController = this.f$0;
                    miuiShowStyleController.mShowDozeTimeout.schedule(miuiShowStyleController.mShowTime, 1);
                }
            });
            Log.i(TAG, "transitionTo: mShowDozeTimeout schedule " + this.mShowTime);
        } else if (this.mHideTime > 0) {
            BackgroundThread.post(new Runnable() { // from class: com.miui.aod.doze.MiuiShowStyleController$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    MiuiShowStyleController miuiShowStyleController = this.f$0;
                    miuiShowStyleController.mHideDozeTimeout.schedule(miuiShowStyleController.mHideTime, 1);
                }
            });
            Log.i(TAG, "transitionTo: mHideDozeTimeout schedule " + this.mHideTime);
        }
        if (this.mHideTime == 0) {
            this.mHost.setAodVisibility(false);
            this.mHost.checkFullAodVisibility(false);
        }
    }

    public void setLightByFOD(boolean z) {
        this.lightByFOD = z;
    }

    public final void showDoze() {
        onShowDoze(true, false);
        if (Utils.isTimingStyle(this.mContext)) {
            updateFullAodStateIfNeed();
            this.mHost.checkFullAodVisibility(true);
            if (Utils.isUsingSuperWallpaperStyle(this.mContext)) {
                PowerManagerUtils.setDozeAfterScreenOff(this.mHost.mPowerManager, false);
            }
            checkTime();
            this.mHideDozeTimeout.schedule(this.mHideTime, 1);
        }
    }

    public final void smartHide() {
        boolean z = this.mMachine.getState() == DozeMachine.State.DOZE_AOD;
        if (!this.lightByFOD || !z) {
            Log.i(TAG, "hideDoze from checkAttention");
            onShowDoze(false, true);
        } else if (this.mHost.isAodVisible()) {
            this.mHost.setAodVisibility(false, null);
            Log.i(TAG, "only turn off the Aod");
        }
    }

    public final void smartShow() {
        cancelAttentionOnly();
        this.mAonConnectTimeOut.schedule(Utils.getAonConnectDuration(), 2);
        boolean z = this.mMachine.getState() == DozeMachine.State.DOZE_AOD;
        if (!z || !this.mHost.isAodVisible()) {
            this.mHost.requestDrawWakelock(Utils.getSmartStyleDuration(), "MiuiShowStyleController#smartShow");
        }
        if (!z) {
            Log.i(TAG, "showDoze from checkAttention");
            onShowDoze(true, true);
        } else {
            if (this.mHost.isAodVisible()) {
                return;
            }
            Log.i(TAG, "only turn on the Aod");
            this.mHost.setAodVisibility(true, null);
        }
    }

    @Override // com.miui.aod.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        AttentionManager attentionManager;
        switch (AnonymousClass4.$SwitchMap$com$miui$aod$doze$DozeMachine$State[state2.ordinal()]) {
            case 1:
                if (this.isAttentionAvailable && (attentionManager = this.mAttentionManager) != null) {
                    attentionManager.bind(new AttentionManager.ConnectionCallback() { // from class: com.miui.aod.doze.MiuiShowStyleController$$ExternalSyntheticLambda0
                        @Override // com.miui.aod.attention.AttentionManager.ConnectionCallback
                        public final void onReconnected() {
                            MiuiShowStyleController.m376$r8$lambda$I_xWOddqQ6xdtes3rY4Atk_0(this.f$0);
                        }
                    });
                    String str = TAG;
                    Log.i(str, "mAttentionManager bind service posted");
                    if (Utils.aodGone(this.mContext)) {
                        Log.i(str, "attention deferred, aodGone");
                        this.mAttentionDeferred = true;
                    } else {
                        this.mCheckTimeout.schedule(Utils.getSmartStyleDuration(), 1);
                        this.mAttentionDeferred = false;
                    }
                }
                if (Utils.isTimingStyle(this.mContext)) {
                    scheduleTimingStyle();
                }
                this.mHost.addCallback(this.mHostCallback);
                break;
            case 2:
                if (!this.pausingBefore) {
                    onAodFire(this.mMachine.getAodReason());
                } else {
                    if (!this.mHost.isAodVisible()) {
                        this.mHost.setAodVisibility(true);
                    }
                    this.pausingBefore = false;
                }
                break;
            case 3:
                if (this.mHost.isFlipping() && !Utils.isGxzwSensor()) {
                    onAodFire(9);
                    break;
                }
                break;
            case 4:
                this.pausingBefore = false;
                if (this.mShowStyle == 0) {
                    this.mHideDozeTimeout.cancel();
                }
                break;
            case 5:
                if (this.mHost.isAodVisible()) {
                    this.pausingBefore = true;
                    this.mHost.setAodVisibility(false);
                }
                break;
            case 6:
                this.mHost.removeCallback(this.mHostCallback);
                BackgroundThread.post(new Runnable() { // from class: com.miui.aod.doze.MiuiShowStyleController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MiuiShowStyleController.$r8$lambda$Xy8fvMOTEcrDNgjfprksnZK6k00(this.f$0);
                    }
                });
                if (this.mAttentionManager != null) {
                    this.mCheckTimeout.cancel();
                    this.mCheckTimeout.release();
                    this.mAonConnectTimeOut.cancel();
                    this.mAonConnectTimeOut.release();
                    cancelAttention();
                    this.mSmartHideDozeTimeout.release();
                    this.mAttentionManager.unbind();
                    releaseAttention();
                }
                break;
        }
    }

    public final void updateFullAodStateIfNeed() {
        if (!Utils.isFsAodSameToLock(this.mContext) || AODSettings.needFullAod()) {
            return;
        }
        try {
            Bundle bundleOnAodRequest = this.mRequestDoze.onAodRequest(1, (Bundle) null);
            if (bundleOnAodRequest == null || !bundleOnAodRequest.containsKey("screenOffNeedFullAodAnim")) {
                return;
            }
            AODSettings.setNeedFullAod(bundleOnAodRequest.getBoolean("screenOffNeedFullAodAnim", false));
        } catch (Throwable unused) {
        }
    }
}
