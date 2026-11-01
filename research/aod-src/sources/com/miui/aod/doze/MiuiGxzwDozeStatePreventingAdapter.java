package com.miui.aod.doze;

import android.content.Context;
import android.util.Log;
import com.miui.aod.Utils;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public class MiuiGxzwDozeStatePreventingAdapter extends DozeMachine.Service.Delegate {
    public boolean mAod2On;
    public int mAodBrightness;
    public final Context mContext;
    public boolean mFingerprintPress;

    public MiuiGxzwDozeStatePreventingAdapter(DozeMachine.Service service, Context context, Executor executor) {
        super(service, executor);
        this.mAodBrightness = 17;
        this.mAod2On = false;
        this.mContext = context;
    }

    public static DozeMachine.Service wrapIfNeeded(DozeMachine.Service service, Context context, Executor executor) {
        return SettingsHolder.mMiuiKeyguard == 2 ? new MiuiGxzwDozeStatePreventingAdapter(service, context, executor) : service;
    }

    @Override // com.miui.aod.doze.DozeMachine.Service.Delegate, com.miui.aod.doze.DozeMachine.Service
    public void fingerprintPressed(boolean z, boolean z2, Runnable runnable) {
        super.fingerprintPressed(z, z2, runnable);
        if (this.mFingerprintPress == z) {
            return;
        }
        this.mFingerprintPress = z;
        if (Utils.SUPPORT_LOW_BRIGHTNESS_FOD && Utils.isGxzwSensor() && !z2 && z && this.mAodBrightness >= 17) {
            return;
        }
        if (z) {
            runnable.run();
            this.mAod2On = true;
        } else if (this.mAod2On) {
            runnable.run();
            this.mAod2On = false;
        }
    }

    @Override // com.miui.aod.doze.DozeMachine.Service.Delegate, com.miui.aod.doze.DozeMachine.Service
    public void finish() {
        super.finish();
        this.mAodBrightness = 17;
        this.mFingerprintPress = false;
        this.mAod2On = false;
    }

    @Override // com.miui.aod.doze.DozeMachine.Service.Delegate, com.miui.aod.doze.DozeMachine.Service
    public void requestState(DozeMachine.State state) {
        Log.i("MiuiGxzwDozeStatePreventingAdapter", "requestState: " + state);
        if (state == DozeMachine.State.DOZE_AOD || state == DozeMachine.State.DOZE_AOD_PAUSING || state == DozeMachine.State.DOZE_AOD_PAUSED) {
            super.requestState(state);
            return;
        }
        if (state == DozeMachine.State.DOZE) {
            if (Utils.isShowFingerprintIcon(this.mContext, getHost()) || this.mFingerprintPress) {
                return;
            }
            super.requestState(state);
            return;
        }
        if (state != DozeMachine.State.FINISH || Utils.isUnlockWithFingerprintPossible(this.mContext, getHost())) {
            return;
        }
        super.requestState(state);
    }

    @Override // com.miui.aod.doze.DozeMachine.Service.Delegate, com.miui.aod.doze.DozeMachine.Service
    public void setDozeScreenBrightness(int i) {
        this.mAodBrightness = i;
        if (!Utils.SUPPORT_LOW_BRIGHTNESS_FOD || !this.mFingerprintPress) {
            super.setDozeScreenBrightness(i);
            return;
        }
        Log.i("MiuiGxzwDozeStatePreventingAdapter", "prevent setDozeScreenBrightness: " + i);
    }
}
