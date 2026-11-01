package com.miui.aod.doze;

import android.app.AlarmManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventCallback;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;
import com.miui.aod.AODStyleController;
import com.miui.aod.DozeHost;
import com.miui.aod.Utils;
import com.miui.aod.category.SmartCoverStyleInfo;
import com.miui.aod.common.BackgroundThread;
import com.miui.aod.util.AlarmTimeout;
import com.miui.aod.util.CancelableWakeLockTask;
import com.miui.aod.util.wakelock.WakeLock;
import com.miui.aod.utils.DreamServiceUtils;
import com.miui.aod.widget.AODSettings;
import com.xiaomi.onetrack.util.ac;

/* JADX INFO: loaded from: classes.dex */
public class MiuiDozeScreenBrightnessController implements DozeMachine.Part {
    public static final String TAG = "MiuiDozeScreenBrightnessController";
    public final Context mContext;
    public final DozePauser mDozePauser;
    public final Handler mHandler;
    public boolean mHasScheduledScreenOffTimeout;
    public final Sensor mLightSensor;
    public final DozeMachine mMachine;
    public boolean mNeedKeepScreenOnAtFirst;
    public boolean mNonUISensorRegistered;
    public final Sensor mNonUiSensor;
    public final AlarmTimeout mOffTimeout;
    public final CancelableWakeLockTask mRegisterTask;
    public boolean mRegistered;
    public final SensorManager mSensorManager;
    public final DozeMachine.Service mService;
    public final WakeLock mWakeLock;
    public boolean mLight = true;
    public boolean mIsFingerprintPressed = false;
    public final DozeHost.Callback mHostCallback = new DozeHost.Callback() { // from class: com.miui.aod.doze.MiuiDozeScreenBrightnessController.2
        @Override // com.miui.aod.DozeHost.Callback
        public void fireAodState(boolean z, String str) {
            if (z || Utils.aodGone(MiuiDozeScreenBrightnessController.this.mContext)) {
                return;
            }
            Log.i(MiuiDozeScreenBrightnessController.TAG, "fireAodState: " + str);
            if ("reason_fod".equals(str) && !MiuiDozeScreenBrightnessController.this.mLight && MiuiDozeScreenBrightnessController.this.mHasScheduledScreenOffTimeout) {
                return;
            }
            if (MiuiDozeScreenBrightnessController.this.mLight && MiuiDozeScreenBrightnessController.this.isPocketMode) {
                return;
            }
            MiuiDozeScreenBrightnessController miuiDozeScreenBrightnessController = MiuiDozeScreenBrightnessController.this;
            miuiDozeScreenBrightnessController.checkToScreenOff(miuiDozeScreenBrightnessController.mLight);
        }

        @Override // com.miui.aod.DozeHost.Callback
        public void onFingerprintPressed(boolean z, boolean z2) {
            if (z) {
                MiuiDozeScreenBrightnessController.this.mIsFingerprintPressed = true;
            }
        }
    };
    public boolean isPocketMode = false;
    public final SensorEventListener mNonUISensorEventListener = new SensorEventCallback() { // from class: com.miui.aod.doze.MiuiDozeScreenBrightnessController.3
        @Override // android.hardware.SensorEventCallback, android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (MiuiDozeScreenBrightnessController.this.shouldBlockSensor) {
                Log.d(MiuiDozeScreenBrightnessController.TAG, "onSensorChanged: blocked...");
                return;
            }
            String str = MiuiDozeScreenBrightnessController.TAG;
            Log.i(str, "nonui sensor event:" + sensorEvent.sensor.getType() + ", value:" + sensorEvent.values[0]);
            if (33171027 == sensorEvent.sensor.getType()) {
                int i = (int) sensorEvent.values[0];
                DozeMachine.State state = MiuiDozeScreenBrightnessController.this.mMachine.getState();
                boolean z = state == DozeMachine.State.DOZE_AOD_PAUSED;
                DozeMachine.State state2 = DozeMachine.State.DOZE_AOD_PAUSING;
                boolean z2 = state == state2;
                DozeMachine.State state3 = DozeMachine.State.DOZE_AOD;
                boolean z3 = state == state3;
                boolean z4 = state == DozeMachine.State.DOZE_PULSING;
                if (i == 1 || i == 2) {
                    if (z4 && MiuiDozeScreenBrightnessController.this.mIsFingerprintPressed) {
                        Log.i(str, "nonui discarded during fingerprint pressing, value:" + i);
                    } else {
                        MiuiDozeScreenBrightnessController.this.isPocketMode = true;
                        if (z3 || z4) {
                            MiuiDozeScreenBrightnessController.this.log("nonui Prox NEAR", true);
                            if (i == 1) {
                                MiuiDozeScreenBrightnessController.this.mDozePauser.setDelayPausedMils(5000L);
                            }
                            MiuiDozeScreenBrightnessController.this.mService.requestState(state2);
                            MiuiDozeScreenBrightnessController.this.stopNotificationAnimForSensor();
                        }
                    }
                } else if (i == 0) {
                    MiuiDozeScreenBrightnessController.this.isPocketMode = false;
                    if (!MiuiDozeScreenBrightnessController.this.mLight) {
                        MiuiDozeScreenBrightnessController.this.scheduleScreenOff();
                    }
                    if (z || z2) {
                        MiuiDozeScreenBrightnessController.this.log("nonui Prox NEAR", false);
                        MiuiDozeScreenBrightnessController.this.mService.requestState(state3);
                    }
                }
            }
            if (MiuiDozeScreenBrightnessController.this.mValue != -1) {
                MiuiDozeScreenBrightnessController miuiDozeScreenBrightnessController = MiuiDozeScreenBrightnessController.this;
                miuiDozeScreenBrightnessController.sensorReactChange(miuiDozeScreenBrightnessController.mValue);
                MiuiDozeScreenBrightnessController.this.mValue = -1;
            }
        }
    };
    public int mValue = -1;
    public final SensorEventListener mSensorEventListener = new SensorEventCallback() { // from class: com.miui.aod.doze.MiuiDozeScreenBrightnessController.4
        @Override // android.hardware.SensorEventCallback, android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (MiuiDozeScreenBrightnessController.this.shouldBlockSensor) {
                Log.d(MiuiDozeScreenBrightnessController.TAG, "onSensorChanged: blocked....");
                return;
            }
            String str = MiuiDozeScreenBrightnessController.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("sensor event:");
            sb.append(sensorEvent.sensor.getType());
            sb.append(", value:");
            boolean z = false;
            sb.append(sensorEvent.values[0]);
            sb.append(" isPocketMode:");
            sb.append(MiuiDozeScreenBrightnessController.this.isPocketMode);
            Log.i(str, sb.toString());
            if (33171029 == sensorEvent.sensor.getType()) {
                int i = (int) sensorEvent.values[0];
                if (!MiuiDozeScreenBrightnessController.this.isPocketMode) {
                    MiuiDozeScreenBrightnessController.this.sensorReactChange(i);
                    return;
                }
                MiuiDozeScreenBrightnessController.this.mValue = i;
                MiuiDozeScreenBrightnessController miuiDozeScreenBrightnessController = MiuiDozeScreenBrightnessController.this;
                if (i != 2 && i != 1 && i != 3) {
                    z = true;
                }
                miuiDozeScreenBrightnessController.mLight = z;
            }
        }
    };
    public boolean shouldBlockSensor = false;

    /* JADX INFO: renamed from: com.miui.aod.doze.MiuiDozeScreenBrightnessController$5, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass5 {
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
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.DOZE_INTO_COVERMODE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.DOZE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.FINISH.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$JOkky8YVMlRxm7HyLrBG7Szvan8(MiuiDozeScreenBrightnessController miuiDozeScreenBrightnessController) {
        miuiDozeScreenBrightnessController.getClass();
        Log.i(TAG, "unregister sensor listener");
        miuiDozeScreenBrightnessController.mSensorManager.unregisterListener(miuiDozeScreenBrightnessController.mSensorEventListener);
        miuiDozeScreenBrightnessController.mRegistered = false;
    }

    /* JADX INFO: renamed from: $r8$lambda$PzcMsQULXojZBknQLh0J_-SOeU4, reason: not valid java name */
    public static /* synthetic */ void m343$r8$lambda$PzcMsQULXojZBknQLh0J_SOeU4(MiuiDozeScreenBrightnessController miuiDozeScreenBrightnessController) {
        miuiDozeScreenBrightnessController.getClass();
        Log.i(TAG, "unregister non ui sensor listener");
        miuiDozeScreenBrightnessController.mSensorManager.unregisterListener(miuiDozeScreenBrightnessController.mNonUISensorEventListener);
        miuiDozeScreenBrightnessController.mNonUISensorRegistered = false;
    }

    /* JADX INFO: renamed from: $r8$lambda$s68YRNR-PsPWuWQK-iMV36Qjras, reason: not valid java name */
    public static /* synthetic */ void m345$r8$lambda$s68YRNRPsPWuWQKiMV36Qjras(MiuiDozeScreenBrightnessController miuiDozeScreenBrightnessController, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        miuiDozeScreenBrightnessController.getClass();
        if (z && (z2 || z3)) {
            miuiDozeScreenBrightnessController.log("Brightness Light", false);
            miuiDozeScreenBrightnessController.mService.requestState(DozeMachine.State.DOZE_AOD);
        } else if (z4 && z5) {
            miuiDozeScreenBrightnessController.log("Brightness Dark", true);
            miuiDozeScreenBrightnessController.mService.requestState(DozeMachine.State.DOZE_AOD_PAUSING);
        }
    }

    public MiuiDozeScreenBrightnessController(Handler handler, DozeMachine dozeMachine, AlarmManager alarmManager, DozeMachine.Service service, DozePauser dozePauser, SensorManager sensorManager, Context context) {
        this.mHasScheduledScreenOffTimeout = false;
        this.mContext = context;
        this.mHandler = handler;
        this.mMachine = dozeMachine;
        this.mService = service;
        this.mSensorManager = sensorManager;
        this.mDozePauser = dozePauser;
        String str = TAG;
        WakeLock wakeLockWrap = WakeLock.wrap(WakeLock.createPartialInner(context, str));
        this.mWakeLock = wakeLockWrap;
        this.mOffTimeout = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener() { // from class: com.miui.aod.doze.MiuiDozeScreenBrightnessController$$ExternalSyntheticLambda2
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                this.f$0.onOffTimeout();
            }
        }, "BrightnessOffAlarmTimeout", handler);
        this.mLightSensor = sensorManager.getDefaultSensor(33171029, true);
        this.mNonUiSensor = sensorManager.getDefaultSensor(33171027, true);
        this.mRegisterTask = new CancelableWakeLockTask(new Runnable() { // from class: com.miui.aod.doze.MiuiDozeScreenBrightnessController.1
            @Override // java.lang.Runnable
            public void run() {
                if (AODSettings.isSmartCoverClose()) {
                    return;
                }
                if (MiuiDozeScreenBrightnessController.this.mLightSensor != null && !MiuiDozeScreenBrightnessController.this.mRegistered) {
                    Log.i(MiuiDozeScreenBrightnessController.TAG, "register sensor listener");
                    MiuiDozeScreenBrightnessController miuiDozeScreenBrightnessController = MiuiDozeScreenBrightnessController.this;
                    miuiDozeScreenBrightnessController.mRegistered = miuiDozeScreenBrightnessController.mSensorManager.registerListener(MiuiDozeScreenBrightnessController.this.mSensorEventListener, MiuiDozeScreenBrightnessController.this.mLightSensor, 3, 0, MiuiDozeScreenBrightnessController.this.mHandler);
                }
                if (MiuiDozeScreenBrightnessController.this.mNonUiSensor == null || MiuiDozeScreenBrightnessController.this.mNonUISensorRegistered) {
                    return;
                }
                MiuiDozeScreenBrightnessController miuiDozeScreenBrightnessController2 = MiuiDozeScreenBrightnessController.this;
                miuiDozeScreenBrightnessController2.mNonUISensorRegistered = miuiDozeScreenBrightnessController2.mSensorManager.registerListener(MiuiDozeScreenBrightnessController.this.mNonUISensorEventListener, MiuiDozeScreenBrightnessController.this.mNonUiSensor, 3, 0, MiuiDozeScreenBrightnessController.this.mHandler);
            }
        }, wakeLockWrap, handler, str);
        this.mHasScheduledScreenOffTimeout = false;
    }

    public final void cancelScheduleScreenOff() {
        this.mOffTimeout.cancel();
        this.mHasScheduledScreenOffTimeout = false;
    }

    public final void checkToScreenOff(final boolean z) {
        if (!this.mLight) {
            this.mHasScheduledScreenOffTimeout = false;
        }
        if (Utils.isShowTemporary(this.mContext)) {
            return;
        }
        final boolean z2 = !z;
        DozeMachine.State state = this.mMachine.getState();
        final boolean z3 = state == DozeMachine.State.DOZE_AOD_PAUSED;
        final boolean z4 = state == DozeMachine.State.DOZE_AOD_PAUSING;
        final boolean z5 = state == DozeMachine.State.DOZE_AOD;
        this.mHandler.post(this.mWakeLock.wrapWithLog(new Runnable() { // from class: com.miui.aod.doze.MiuiDozeScreenBrightnessController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                MiuiDozeScreenBrightnessController.m345$r8$lambda$s68YRNRPsPWuWQKiMV36Qjras(this.f$0, z, z3, z4, z2, z5);
            }
        }, "light: " + z));
    }

    public final void log(String str, boolean z) {
        String str2 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(ac.b);
        sb.append(z ? " pausing" : "unpausing");
        sb.append(" AOD");
        Log.i(str2, sb.toString());
    }

    public final void onOffTimeout() {
        checkToScreenOff(this.mLight);
    }

    @Override // com.miui.aod.doze.DozeMachine.Part
    public void preDestroyForChange() {
        this.shouldBlockSensor = true;
    }

    @Override // com.miui.aod.doze.DozeMachine.Part
    public void rebindForChange() {
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.aod.doze.MiuiDozeScreenBrightnessController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.shouldBlockSensor = false;
            }
        }, 600L);
    }

    public final void scheduleScreenOff() {
        this.mOffTimeout.schedule(300000L, 1);
        this.mHasScheduledScreenOffTimeout = true;
    }

    public final void sensorReactChange(int i) {
        int[] iArr;
        if (this.isPocketMode) {
            return;
        }
        DozeMachine.State state = this.mMachine.getState();
        boolean z = state == DozeMachine.State.DOZE_AOD_PAUSED;
        DozeMachine.State state2 = DozeMachine.State.DOZE_AOD_PAUSING;
        boolean z2 = state == state2;
        DozeMachine.State state3 = DozeMachine.State.DOZE_AOD;
        boolean z3 = state == state3;
        if (i == 2 || i == 1) {
            this.mLight = false;
            if (z3) {
                log("Prox NEAR", true);
                this.mService.requestState(state2);
                stopNotificationAnimForSensor();
            }
        } else if (z || z2) {
            log("Prox NEAR", false);
            this.mService.requestState(state3);
        }
        if (i == 3) {
            this.mLight = false;
            int[] iArr2 = Utils.sLightValues;
            if (iArr2 == null || iArr2.length < 1 || DreamServiceUtils.isSupportFloatBrightness()) {
                this.mService.setDozeScreenBrightness(DreamServiceUtils.getDefaultLowBrightness());
            } else {
                this.mService.setDozeScreenBrightness(Utils.sLightValues[0]);
            }
            scheduleScreenOff();
        } else {
            cancelScheduleScreenOff();
        }
        if (i >= 4 && (iArr = Utils.sLightValues) != null && iArr.length >= 6 && !DreamServiceUtils.isSupportFloatBrightness()) {
            this.mLight = true;
            checkToScreenOff(true);
            int i2 = i - 4;
            int[] iArr3 = Utils.sLightValues;
            if (i2 >= iArr3.length) {
                Log.e(TAG, "onSensorChanged: valueIndex >= Utils.sLightValues.length");
                return;
            } else {
                this.mService.setDozeScreenBrightness(iArr3[i2]);
                return;
            }
        }
        if (i == 5) {
            this.mLight = true;
            checkToScreenOff(true);
            this.mService.setDozeScreenBrightness(DreamServiceUtils.getDefaultLowBrightness());
        } else if (i == 4) {
            this.mLight = true;
            checkToScreenOff(true);
            this.mService.setDozeScreenBrightness(DreamServiceUtils.getDefaultHighBrightness());
        }
    }

    public final void stopNotificationAnimForSensor() {
        if (this.mService.getHost() == null || this.mService.getHost().getAODView() == null || !this.mService.getHost().isAnimateShowing()) {
            return;
        }
        this.mService.getHost().getAODView().stopNotificationAnim();
        this.mService.getHost().setNotificationAnimate(false);
        this.mService.getHost().fireAnimateState();
    }

    @Override // com.miui.aod.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        switch (AnonymousClass5.$SwitchMap$com$miui$aod$doze$DozeMachine$State[state2.ordinal()]) {
            case 1:
                this.mNeedKeepScreenOnAtFirst = AODSettings.needKeepScreenOnAtFirst();
                this.mService.getHost().addCallback(this.mHostCallback);
                return;
            case 2:
                if (AODSettings.isSmartCoverClose() && (AODStyleController.getStyleInfo(this.mContext) instanceof SmartCoverStyleInfo)) {
                    this.mService.setDozeScreenBrightness(DreamServiceUtils.getDefaultHighBrightness());
                } else if (this.mNeedKeepScreenOnAtFirst) {
                    this.mNeedKeepScreenOnAtFirst = false;
                    this.mRegisterTask.execute(6000L);
                } else {
                    this.mRegisterTask.execute(Utils.needDelayRegisterSensor() ? 400L : 0L);
                }
                this.mIsFingerprintPressed = false;
                return;
            case 3:
                cancelScheduleScreenOff();
                return;
            case 4:
                this.mService.setDozeScreenBrightness(DreamServiceUtils.getDefaultHighBrightness());
                break;
            case 5:
            case 6:
                break;
            default:
                return;
        }
        this.mIsFingerprintPressed = false;
        cancelScheduleScreenOff();
        this.mOffTimeout.release();
        this.mRegisterTask.remove();
        if (state2 == DozeMachine.State.FINISH) {
            this.mService.getHost().removeCallback(this.mHostCallback);
        }
        if (this.mLightSensor != null && this.mRegistered) {
            BackgroundThread.post(new Runnable() { // from class: com.miui.aod.doze.MiuiDozeScreenBrightnessController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MiuiDozeScreenBrightnessController.$r8$lambda$JOkky8YVMlRxm7HyLrBG7Szvan8(this.f$0);
                }
            });
        }
        if (this.mNonUiSensor != null && this.mNonUISensorRegistered) {
            BackgroundThread.post(new Runnable() { // from class: com.miui.aod.doze.MiuiDozeScreenBrightnessController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    MiuiDozeScreenBrightnessController.m343$r8$lambda$PzcMsQULXojZBknQLh0J_SOeU4(this.f$0);
                }
            });
        }
        WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null) {
            wakeLock.release("MiuiDozeScreenBrightnessController#transitionTo");
        }
    }
}
