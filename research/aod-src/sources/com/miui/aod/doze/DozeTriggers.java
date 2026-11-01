package com.miui.aod.doze;

import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.miui.aod.DozeHost;
import com.miui.aod.Utils;
import com.miui.aod.common.BackgroundThread;
import com.miui.aod.util.Assert;
import com.miui.aod.util.LooperExecutor;
import com.miui.aod.widget.AODSettings;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class DozeTriggers implements DozeMachine.Part {
    public final boolean mAllowPulseTriggers;
    public final Context mContext;
    public DozeHost mHost;
    public final DozeMachine mMachine;
    public boolean mNeedKeepScreenOnAtFirst;
    public boolean mPulsePending;
    public final DozeMachine.Service mService;
    public final MiuiShowStyleController mShowStyleController;
    public final UiModeManager mUiModeManager;
    public final LooperExecutor mainExecutor;
    public final DozeHost.Callback mHostCallback = new AnonymousClass1();
    public final TriggerReceiver mBroadcastReceiver = new TriggerReceiver(this);

    /* JADX INFO: renamed from: com.miui.aod.doze.DozeTriggers$1, reason: invalid class name */
    public class AnonymousClass1 implements DozeHost.Callback {
        public final Runnable resetFpStateRunnable = new Runnable() { // from class: com.miui.aod.doze.DozeTriggers$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DozeTriggers.AnonymousClass1.$r8$lambda$v32AALx8kkZ9Z2cEz804RfsnHmE(this.f$0);
            }
        };
        public final Runnable requestDozeAodRunnable = new Runnable() { // from class: com.miui.aod.doze.DozeTriggers$1$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.requestDozeAod();
            }
        };

        /* JADX INFO: renamed from: $r8$lambda$gV0KSR2y971dTnspw-ukL_X3elw, reason: not valid java name */
        public static /* synthetic */ void m336$r8$lambda$gV0KSR2y971dTnspwukL_X3elw(AnonymousClass1 anonymousClass1) {
            if (DozeTriggers.this.mMachine.getState() == DozeMachine.State.DOZE_PULSING) {
                Log.w("DozeTriggers", "change display to aod");
                DozeTriggers.this.mainExecutor.getHandler().removeCallbacks(anonymousClass1.resetFpStateRunnable);
                DozeTriggers.this.mainExecutor.getHandler().removeCallbacks(anonymousClass1.requestDozeAodRunnable);
                DozeTriggers.this.mainExecutor.getHandler().postDelayed(anonymousClass1.requestDozeAodRunnable, 2000L);
            }
        }

        public static /* synthetic */ void $r8$lambda$lR6D_dKVcI3wmJXyyuP0M_AynMw(AnonymousClass1 anonymousClass1) {
            anonymousClass1.getClass();
            Log.w("DozeTriggers", "change display to on");
            DozeTriggers.this.mainExecutor.getHandler().removeCallbacks(anonymousClass1.requestDozeAodRunnable);
            DozeMachine.State state = DozeTriggers.this.mMachine.getState();
            DozeMachine.State state2 = DozeMachine.State.DOZE_PULSING;
            if (state != state2) {
                DozeTriggers.this.mMachine.requestPulse(15);
                DozeTriggers.this.mMachine.requestState(state2);
            }
            DozeTriggers.this.mainExecutor.getHandler().postDelayed(anonymousClass1.resetFpStateRunnable, 2000L);
        }

        public static /* synthetic */ void $r8$lambda$v32AALx8kkZ9Z2cEz804RfsnHmE(AnonymousClass1 anonymousClass1) {
            if (DozeTriggers.this.mHost != null) {
                DozeTriggers.this.mHost.fireFingerprintPressed(false, true);
            }
        }

        public AnonymousClass1() {
        }

        @Override // com.miui.aod.DozeHost.Callback
        public void fireAodState(boolean z, String str) {
            if ("reason_fod".equals(str) && !DozeTriggers.this.mHost.isFingerprintUnlockAvailable()) {
                Log.e("DozeTriggers", "fireAodState: shouldIgnoreFodRequest: " + str);
                return;
            }
            DozeMachine.State state = DozeTriggers.this.mMachine.getState();
            boolean z2 = state == DozeMachine.State.DOZE_AOD_PAUSED;
            boolean z3 = state == DozeMachine.State.DOZE_AOD_PAUSING;
            boolean z4 = state == DozeMachine.State.DOZE_AOD;
            DozeMachine.State state2 = DozeMachine.State.DOZE;
            boolean z5 = state == state2;
            boolean zIsAnimateShowing = DozeTriggers.this.mHost.isAnimateShowing();
            boolean zIsAodVisible = DozeTriggers.this.mHost.isAodVisible();
            boolean zIsShowSmart = Utils.isShowSmart(DozeTriggers.this.mContext);
            Log.i("DozeTriggers", "fireAodState: " + z + " reason: " + str + " paused:" + z2 + " pausing:" + z3 + " dozeAod:" + z4 + " doze:" + z5 + " isAodClockVisible:" + zIsAodVisible + " isSmartStyle:" + zIsShowSmart);
            StringBuilder sb = new StringBuilder();
            sb.append("fireAodStatereason: ");
            sb.append(str);
            Log.d("DozeTriggers", sb.toString());
            if (z) {
                if (zIsShowSmart) {
                    DozeTriggers.this.mShowStyleController.setLightByFOD(true);
                }
                if (!z2 && !z3 && !z5) {
                    DozeTriggers.this.mShowStyleController.onAodFire(DozeLog.aodReasonToInt(str));
                    return;
                }
                DozeTriggers.this.mMachine.requestAod(DozeLog.aodReasonToInt(str));
                if ("reason_fod".equals(str)) {
                    DozeTriggers.this.mHost.requestDrawWakelock(3000L, "DozeTriggers#DozeHost.Callback");
                    return;
                }
                return;
            }
            if (zIsShowSmart) {
                DozeTriggers.this.mShowStyleController.setLightByFOD(false);
            }
            if (z4 || z2 || z3) {
                if ((Utils.aodGone(DozeTriggers.this.mContext) || !zIsAodVisible) && !zIsAnimateShowing) {
                    if (zIsShowSmart && DozeTriggers.this.mHost.isAodVisible()) {
                        return;
                    }
                    if ((z2 || z3) && DozeTriggers.this.mShowStyleController.pausingBefore) {
                        return;
                    }
                    DozeTriggers.this.mMachine.requestState(state2);
                }
            }
        }

        @Override // com.miui.aod.DozeHost.Callback
        public void notifyKeycodeGoto() {
            if (Utils.isShowSmart(DozeTriggers.this.mContext)) {
                DozeTriggers.this.mShowStyleController.setLightByFOD(true);
            }
        }

        @Override // com.miui.aod.DozeHost.Callback
        public void onAodAnimate(boolean z) {
            DozeMachine.State state = DozeTriggers.this.mMachine.getState();
            boolean z2 = state == DozeMachine.State.DOZE_AOD_PAUSED;
            boolean z3 = state == DozeMachine.State.DOZE_AOD_PAUSING;
            boolean z4 = state == DozeMachine.State.DOZE_AOD;
            DozeMachine.State state2 = DozeMachine.State.DOZE;
            boolean z5 = state == state2;
            Log.w("DozeTriggers", "onAodAnimate: show " + z + " state " + state);
            if (z) {
                if (z2 || z3 || z5) {
                    DozeTriggers.this.mMachine.requestAod(8);
                    return;
                }
                return;
            }
            if (Utils.aodGone(DozeTriggers.this.mContext)) {
                if (z4 || z2 || z3) {
                    DozeTriggers.this.mMachine.requestState(state2);
                }
            }
        }

        @Override // com.miui.aod.DozeHost.Callback
        public void onFingerprintPressed(boolean z, boolean z2) {
            boolean z3 = Utils.SUPPORT_LOW_BRIGHTNESS_FOD;
            if (z3 || z2) {
                if (!z) {
                    DozeTriggers.this.mService.fingerprintPressed(false, z2, new Runnable() { // from class: com.miui.aod.doze.DozeTriggers$1$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            DozeTriggers.AnonymousClass1.m336$r8$lambda$gV0KSR2y971dTnspwukL_X3elw(this.f$0);
                        }
                    });
                    return;
                } else {
                    DozeTriggers.this.mainExecutor.getHandler().removeCallbacks(this.resetFpStateRunnable);
                    DozeTriggers.this.mService.fingerprintPressed(true, z2, new Runnable() { // from class: com.miui.aod.doze.DozeTriggers$1$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            DozeTriggers.AnonymousClass1.$r8$lambda$lR6D_dKVcI3wmJXyyuP0M_AynMw(this.f$0);
                        }
                    });
                    return;
                }
            }
            Log.w("DozeTriggers", "not turn screen on, turnScreenOn: " + z2 + ", SUPPORT_LOW_BRIGHTNESS_FOD: " + z3);
        }

        @Override // com.miui.aod.DozeHost.PowerSaveChangeCallback
        public void onPowerSaveChanged(boolean z) {
            if (Utils.needDisableAodInPowerSaveMode(DozeTriggers.this.mContext)) {
                Log.i("DozeTriggers", "onPowerSaveChanged: requestState(DozeMachine.State.FINISH)");
                DozeTriggers.this.mainExecutor.execute(new Runnable() { // from class: com.miui.aod.doze.DozeTriggers$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        DozeTriggers.this.mService.requestState(DozeMachine.State.FINISH);
                    }
                });
            }
        }

        public final void requestDozeAod() {
            if (DozeTriggers.this.mMachine.getState() == DozeMachine.State.DOZE_PULSING) {
                DozeTriggers.this.mMachine.requestAod(6);
                if (Utils.isGxzwSensor()) {
                    return;
                }
                resetScreenState();
            }
        }

        public void resetScreenState() {
            fireAodState(false, "reason_fod");
        }
    }

    /* JADX INFO: renamed from: com.miui.aod.doze.DozeTriggers$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$aod$doze$DozeMachine$State;

        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$miui$aod$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.INITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.FINISH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static class TriggerReceiver extends BroadcastReceiver {
        public final WeakReference mRef;
        public boolean mRegistered;

        public TriggerReceiver(DozeTriggers dozeTriggers) {
            this.mRef = new WeakReference(dozeTriggers);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            final DozeTriggers dozeTriggers = (DozeTriggers) this.mRef.get();
            if (dozeTriggers == null) {
                unregister(context);
                return;
            }
            if ("com.android.systemui.doze.pulse".equals(intent.getAction())) {
                Log.d("DozeTriggers", "Received pulse intent");
                dozeTriggers.mainExecutor.execute(new Runnable() { // from class: com.miui.aod.doze.DozeTriggers$TriggerReceiver$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        dozeTriggers.requestPulse(0);
                    }
                });
            }
            if (UiModeManager.ACTION_ENTER_CAR_MODE.equals(intent.getAction())) {
                Log.i("DozeTriggers", "TriggerReceiver: requestState(DozeMachine.State.FINISH) CAR_MODE");
                dozeTriggers.mainExecutor.execute(new Runnable() { // from class: com.miui.aod.doze.DozeTriggers$TriggerReceiver$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        dozeTriggers.mMachine.requestState(DozeMachine.State.FINISH);
                    }
                });
            }
        }

        public void register(Context context) {
            if (this.mRegistered) {
                return;
            }
            IntentFilter intentFilter = new IntentFilter("com.android.systemui.doze.pulse");
            intentFilter.addAction(UiModeManager.ACTION_ENTER_CAR_MODE);
            Utils.broadcastDispatcher.registerReceiver(context, this, intentFilter);
            this.mRegistered = true;
        }

        public void unregister(Context context) {
            if (this.mRegistered) {
                Utils.broadcastDispatcher.unregisterReceiver(context, this);
                this.mRegistered = false;
            }
        }
    }

    public DozeTriggers(Context context, DozeMachine dozeMachine, LooperExecutor looperExecutor, boolean z, DozeMachine.Service service, MiuiShowStyleController miuiShowStyleController) {
        this.mContext = context;
        this.mMachine = dozeMachine;
        this.mainExecutor = looperExecutor;
        this.mAllowPulseTriggers = z;
        this.mUiModeManager = (UiModeManager) context.getSystemService(UiModeManager.class);
        this.mService = service;
        this.mHost = service.getHost();
        this.mShowStyleController = miuiShowStyleController;
    }

    public final boolean canPulse() {
        return this.mMachine.getState() == DozeMachine.State.DOZE || this.mMachine.getState() == DozeMachine.State.DOZE_AOD;
    }

    public final void checkTriggersAtInit() {
        if (this.mUiModeManager.getCurrentModeType() == 3) {
            this.mService.requestState(DozeMachine.State.FINISH);
        }
    }

    public final void continuePulseRequest(int i) {
        this.mPulsePending = false;
        if (this.mHost.isPulsingBlocked() || !canPulse()) {
            DozeLog.tracePulseDropped(this.mContext, this.mPulsePending, this.mMachine.getState(), this.mHost.isPulsingBlocked());
        } else {
            this.mMachine.requestPulse(i);
        }
    }

    public final void requestPulse(int i) {
        Assert.isMainThread();
        if (!this.mPulsePending && this.mAllowPulseTriggers && canPulse()) {
            continuePulseRequest(i);
        } else if (this.mAllowPulseTriggers) {
            DozeLog.tracePulseDropped(this.mContext, this.mPulsePending, this.mMachine.getState(), this.mHost.isPulsingBlocked());
        }
    }

    @Override // com.miui.aod.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        int i = AnonymousClass2.$SwitchMap$com$miui$aod$doze$DozeMachine$State[state2.ordinal()];
        if (i != 1) {
            if (i != 2) {
                return;
            }
            this.mBroadcastReceiver.unregister(this.mContext);
            this.mHost.removeCallback(this.mHostCallback);
            if (this.mNeedKeepScreenOnAtFirst) {
                BackgroundThread.post(new Runnable() { // from class: com.miui.aod.doze.DozeTriggers$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Utils.updateTouchModeForKeepScreenOnIfNeeded(1, this.f$0.mService.isFolded());
                    }
                });
                return;
            }
            return;
        }
        this.mNeedKeepScreenOnAtFirst = AODSettings.needKeepScreenOnAtFirst();
        this.mBroadcastReceiver.register(this.mContext);
        this.mHost.addCallback(this.mHostCallback);
        checkTriggersAtInit();
        if (this.mNeedKeepScreenOnAtFirst) {
            BackgroundThread.post(new Runnable() { // from class: com.miui.aod.doze.DozeTriggers$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Utils.updateTouchModeForKeepScreenOnIfNeeded(0, this.f$0.mService.isFolded());
                }
            });
        }
    }
}
