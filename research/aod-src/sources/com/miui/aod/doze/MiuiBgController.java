package com.miui.aod.doze;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.miui.aod.DozeHost;
import com.miui.aod.util.AlarmTimeout;
import com.miui.aod.widget.AODSettings;
import com.miui.aod.widget.SunSelector;
import java.util.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class MiuiBgController implements DozeMachine.Part {
    public static final HandlerThread sWorkerThread;
    public final AlarmTimeout mChangeBgTimeout;
    public final Context mContext;
    public final DozeHost mHost;
    public boolean mIsSunRiseOpen;

    /* JADX INFO: renamed from: com.miui.aod.doze.MiuiBgController$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
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

    static {
        HandlerThread handlerThread = new HandlerThread("bg_looper");
        sWorkerThread = handlerThread;
        handlerThread.start();
    }

    public MiuiBgController(Context context, Handler handler, AlarmManager alarmManager, DozeMachine.Service service) {
        this.mChangeBgTimeout = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener() { // from class: com.miui.aod.doze.MiuiBgController$$ExternalSyntheticLambda0
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                this.f$0.changeBg();
            }
        }, "SunImageTimeout", handler);
        this.mContext = context;
        this.mHost = service.getHost();
        if (!AODSettings.isNeedTimeChangedStyle(context)) {
            this.mIsSunRiseOpen = false;
            return;
        }
        if (AODSettings.isSunStyle(context)) {
            this.mIsSunRiseOpen = true;
        }
        new Handler(sWorkerThread.getLooper()).post(new Runnable() { // from class: com.miui.aod.doze.MiuiBgController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SunSelector.updateSunRiseTime(this.f$0.mContext);
            }
        });
    }

    public final void changeBg() {
        if (this.mIsSunRiseOpen) {
            Calendar calendar = Calendar.getInstance();
            int i = calendar.get(11);
            int i2 = calendar.get(12);
            int i3 = i * 60;
            int i4 = i3 + i2;
            int iChooseBg = chooseBg(i4);
            this.mHost.setSunImage(iChooseBg);
            int changePoint = SunSelector.getChangePoint((iChooseBg + 1) % SunSelector.getChangePointLength());
            if (changePoint <= i4) {
                if (changePoint < 0) {
                    return;
                } else {
                    changePoint += 1440;
                }
            }
            this.mChangeBgTimeout.schedule(((long) ((changePoint - i3) - i2)) * 60000, 1);
        }
    }

    public final int chooseBg(int i) {
        return SunSelector.getDrawableIndex(i);
    }

    @Override // com.miui.aod.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        int i = AnonymousClass1.$SwitchMap$com$miui$aod$doze$DozeMachine$State[state2.ordinal()];
        if (i == 1) {
            changeBg();
        } else {
            if (i != 2) {
                return;
            }
            this.mChangeBgTimeout.cancel();
            this.mChangeBgTimeout.release();
        }
    }
}
