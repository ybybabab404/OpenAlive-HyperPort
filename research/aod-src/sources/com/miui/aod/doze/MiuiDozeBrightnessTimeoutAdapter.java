package com.miui.aod.doze;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.os.PowerManager;
import android.view.Display;
import com.miui.aod.AODApplication;
import com.miui.aod.util.AlarmTimeout;
import com.miui.aod.util.wakelock.SettableWakeLock;
import com.miui.aod.util.wakelock.WakeLock;
import com.miui.aod.utils.DreamServiceUtils;
import com.miui.aod.widget.AODSettings;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public class MiuiDozeBrightnessTimeoutAdapter extends DozeMachine.Service.Delegate {
    public final AlarmTimeout mBrightnessAlarmTimeout;
    public final Display mDisplay;
    public int mLastBrightness;
    public final SettableWakeLock mWakeLock;

    public MiuiDozeBrightnessTimeoutAdapter(Context context, DozeMachine.Service service, Handler handler, Executor executor, AlarmManager alarmManager) {
        super(service, executor);
        this.mLastBrightness = -1;
        this.mDisplay = AODApplication.sInstance.getDisplay();
        this.mBrightnessAlarmTimeout = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener() { // from class: com.miui.aod.doze.MiuiDozeBrightnessTimeoutAdapter$$ExternalSyntheticLambda0
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                this.f$0.onSetDozeScreenBrightnessTimeout();
            }
        }, "MiuiDozeBrightnessTimeoutAdapter", handler);
        this.mWakeLock = new SettableWakeLock(WakeLock.wrap(WakeLock.createPartialInner((PowerManager) context.getSystemService("power"), "MiuiDozeBrightnessTimeoutAdapter")), "MiuiDozeBrightnessTimeoutAdapter");
    }

    @Override // com.miui.aod.doze.DozeMachine.Service.Delegate, com.miui.aod.doze.DozeMachine.Service
    public void finish() {
        super.finish();
        this.mBrightnessAlarmTimeout.cancel();
        this.mBrightnessAlarmTimeout.release();
        this.mWakeLock.setAcquired(false);
        this.mLastBrightness = -1;
    }

    public final void onSetDozeScreenBrightnessTimeout() {
        this.mWakeLock.setAcquired(true);
        super.setDozeScreenBrightness(this.mLastBrightness);
        this.mWakeLock.setAcquired(false);
    }

    @Override // com.miui.aod.doze.DozeMachine.Service.Delegate, com.miui.aod.doze.DozeMachine.Service
    public void setDozeScreenBrightness(int i) {
        if (this.mLastBrightness == -1 || (AODSettings.needKeepScreenOnAtFirst() && 2 == this.mDisplay.getState())) {
            this.mBrightnessAlarmTimeout.cancel();
            this.mLastBrightness = i;
            onSetDozeScreenBrightnessTimeout();
        } else if (i != this.mLastBrightness) {
            this.mBrightnessAlarmTimeout.cancel();
            this.mBrightnessAlarmTimeout.schedule(this.mLastBrightness == DreamServiceUtils.getDefaultHighBrightness() ? 10000L : 5000L, 2);
            this.mLastBrightness = i;
        }
    }
}
