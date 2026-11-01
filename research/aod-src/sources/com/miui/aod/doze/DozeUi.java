package com.miui.aod.doze;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import com.miui.aod.AODStyleController;
import com.miui.aod.DozeHost;
import com.miui.aod.util.AlarmTimeout;
import com.miui.aod.util.wakelock.DelayedWakeLock;
import com.miui.aod.util.wakelock.WakeLock;
import com.miui.aod.utils.CommonUtils;
import java.util.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class DozeUi implements DozeMachine.Part {
    public final Context mContext;
    public final Handler mHandler;
    public final DozeHost mHost;
    public long mLastTimeTickElapsed = 0;
    public DozeMachine mMachine;
    public final AlarmTimeout mTimeTicker;
    public final AlarmTimeout mUpdatePositionTimer;
    public final WakeLock mWakeLock;

    /* JADX INFO: renamed from: com.miui.aod.doze.DozeUi$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$aod$doze$DozeMachine$State;

        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$miui$aod$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.DOZE_AOD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.DOZE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.DOZE_INTO_COVERMODE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.DOZE_REQUEST_PULSE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.FINISH.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$hcsYtMFpriAAcGJ3Zsxmld9oaRE() {
    }

    public DozeUi(Context context, AlarmManager alarmManager, DozeMachine dozeMachine, Handler handler, DozeMachine.Service service) {
        this.mContext = context;
        this.mMachine = dozeMachine;
        this.mWakeLock = new DelayedWakeLock(handler, WakeLock.wrap(WakeLock.createDrawInner(context, "DozeUi")));
        this.mHandler = handler;
        this.mTimeTicker = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener() { // from class: com.miui.aod.doze.DozeUi$$ExternalSyntheticLambda0
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                this.f$0.onTimeTick();
            }
        }, "doze_time_tick", handler);
        this.mHost = service.getHost();
        this.mUpdatePositionTimer = AODStyleController.isBigTimeStyle(context) ? new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener() { // from class: com.miui.aod.doze.DozeUi$$ExternalSyntheticLambda1
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                this.f$0.onUpdatePosition();
            }
        }, "update_position_timer", handler) : null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTimeTick() {
        verifyLastTimeTick();
        this.mHost.dozeTimeTick(true);
        this.mHandler.post(this.mWakeLock.wrapWithLog(new Runnable() { // from class: com.miui.aod.doze.DozeUi$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                DozeUi.$r8$lambda$hcsYtMFpriAAcGJ3Zsxmld9oaRE();
            }
        }, "DozeUi"));
        scheduleTimeTick();
    }

    @Override // com.miui.aod.doze.DozeMachine.Part
    public void destroy() {
        this.mMachine = null;
    }

    public final void onUpdatePosition() {
        this.mHost.onUpdatePositionTimer();
        scheduleUpdatePositionTimer();
    }

    public final void pulseWhileDozing(int i) {
        if (i == 15) {
            return;
        }
        this.mMachine.requestState(DozeMachine.State.DOZE_PULSING);
        this.mMachine.requestState(DozeMachine.State.DOZE_PULSE_DONE);
    }

    public final long roundToNextMinute(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        calendar.set(14, 0);
        calendar.set(13, 0);
        calendar.add(12, 1);
        return calendar.getTimeInMillis();
    }

    public final void scheduleTimeTick() {
        if (this.mTimeTicker.isScheduled()) {
            return;
        }
        this.mTimeTicker.schedule(roundToNextMinute(System.currentTimeMillis()) - System.currentTimeMillis(), 1);
        this.mLastTimeTickElapsed = SystemClock.elapsedRealtime();
    }

    public final void scheduleUpdatePositionTimer() {
        AlarmTimeout alarmTimeout = this.mUpdatePositionTimer;
        if (alarmTimeout == null || alarmTimeout.isScheduled()) {
            return;
        }
        this.mUpdatePositionTimer.schedule(30000L, 1);
    }

    @Override // com.miui.aod.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        switch (AnonymousClass1.$SwitchMap$com$miui$aod$doze$DozeMachine$State[state2.ordinal()]) {
            case 1:
                if (state == DozeMachine.State.DOZE_AOD_PAUSED || state == DozeMachine.State.DOZE) {
                    this.mHost.dozeTimeTick(false);
                    this.mHandler.postDelayed(this.mWakeLock.wrapWithLog(new Runnable() { // from class: com.miui.aod.doze.DozeUi$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.mHost.dozeTimeTick(false);
                        }
                    }, "DozeUi"), 500L);
                }
                scheduleTimeTick();
                scheduleUpdatePositionTimer();
                break;
            case 2:
                scheduleTimeTick();
                scheduleUpdatePositionTimer();
                break;
            case 3:
            case 4:
                unscheduleTimeTick();
                unscheduleUpdatePositionTimer();
                break;
            case 5:
                this.mHost.updateClockType();
                break;
            case 6:
                scheduleTimeTick();
                scheduleUpdatePositionTimer();
                pulseWhileDozing(this.mMachine.getPulseReason());
                break;
            case 7:
                this.mHost.startDozing();
                break;
            case 8:
                this.mHost.stopDozing();
                unscheduleTimeTick();
                unscheduleUpdatePositionTimer();
                this.mTimeTicker.release();
                WakeLock wakeLock = this.mWakeLock;
                if (wakeLock != null) {
                    wakeLock.release("DozeUi#transitionTo");
                }
                break;
        }
    }

    public final void unscheduleTimeTick() {
        if (this.mTimeTicker.isScheduled()) {
            verifyLastTimeTick();
            this.mTimeTicker.cancel();
        }
    }

    public final void unscheduleUpdatePositionTimer() {
        AlarmTimeout alarmTimeout = this.mUpdatePositionTimer;
        if (alarmTimeout == null || !alarmTimeout.isScheduled()) {
            return;
        }
        this.mUpdatePositionTimer.cancel();
    }

    public final void verifyLastTimeTick() {
        long jElapsedRealtime = SystemClock.elapsedRealtime() - this.mLastTimeTickElapsed;
        if (jElapsedRealtime > 90000) {
            String shortElapsedTime = CommonUtils.formatShortElapsedTime(this.mContext, jElapsedRealtime);
            DozeLog.traceMissedTick(shortElapsedTime);
            Log.e("DozeMachine", "Missed AOD time tick by " + shortElapsedTime);
        }
    }
}
