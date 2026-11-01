package com.miui.aod.doze;

import android.app.AlarmManager;
import android.os.Handler;
import com.miui.aod.util.AlarmTimeout;

/* JADX INFO: loaded from: classes.dex */
public class DozePauser implements DozeMachine.Part {
    public long delayPausedMils = 0;
    public final DozeMachine mMachine;
    public final AlarmTimeout mPauseTimeout;

    /* JADX INFO: renamed from: com.miui.aod.doze.DozePauser$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$aod$doze$DozeMachine$State;

        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$miui$aod$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.DOZE_AOD_PAUSING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.FINISH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public DozePauser(Handler handler, DozeMachine dozeMachine, AlarmManager alarmManager) {
        this.mMachine = dozeMachine;
        this.mPauseTimeout = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener() { // from class: com.miui.aod.doze.DozePauser$$ExternalSyntheticLambda0
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                this.f$0.onTimeout();
            }
        }, "DozePauser", handler);
    }

    public final void onTimeout() {
        this.mMachine.requestState(DozeMachine.State.DOZE_AOD_PAUSED);
    }

    public void setDelayPausedMils(long j) {
        this.delayPausedMils = j;
    }

    @Override // com.miui.aod.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        int i = AnonymousClass1.$SwitchMap$com$miui$aod$doze$DozeMachine$State[state2.ordinal()];
        if (i != 1) {
            if (i != 2) {
                this.mPauseTimeout.cancel();
                return;
            } else {
                this.mPauseTimeout.cancel();
                this.mPauseTimeout.release();
                return;
            }
        }
        long j = this.delayPausedMils;
        if (j == 0) {
            onTimeout();
        } else {
            this.mPauseTimeout.schedule(j, 1);
            this.delayPausedMils = 0L;
        }
    }
}
