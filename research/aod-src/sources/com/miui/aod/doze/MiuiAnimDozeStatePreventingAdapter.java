package com.miui.aod.doze;

import android.content.Context;
import android.util.Log;
import com.miui.aod.Utils;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public class MiuiAnimDozeStatePreventingAdapter extends DozeMachine.Service.Delegate {
    public final Context mContext;
    public boolean mFingerprintPress;

    public MiuiAnimDozeStatePreventingAdapter(DozeMachine.Service service, Context context, Executor executor) {
        super(service, executor);
        this.mContext = context;
    }

    public static DozeMachine.Service wrapIfNeeded(DozeMachine.Service service, Context context, Executor executor) {
        return Utils.isAodAnimateEnable(context) ? new MiuiAnimDozeStatePreventingAdapter(service, context, executor) : service;
    }

    @Override // com.miui.aod.doze.DozeMachine.Service.Delegate, com.miui.aod.doze.DozeMachine.Service
    public void fingerprintPressed(boolean z, boolean z2, Runnable runnable) {
        this.mFingerprintPress = z;
        super.fingerprintPressed(z, z2, runnable);
    }

    @Override // com.miui.aod.doze.DozeMachine.Service.Delegate, com.miui.aod.doze.DozeMachine.Service
    public void setDozeScreenState(int i) {
        boolean z = Utils.isGxzwSensor() && Utils.isShowFingerprintIcon(this.mContext, getHost()) && Utils.isFodAodShowEnable(this.mContext);
        if ((i != 2 && i != 3 && i != 4) || getHost().isAnimateShowing() || "reason_fod".equals(getHost().getAodStateReason()) || z || this.mFingerprintPress || !Utils.aodGone(this.mContext)) {
            super.setDozeScreenState(i);
            return;
        }
        Log.i("AnimDozeStatePreventingAdapter", "prevent setDozeScreenState state:" + i);
    }
}
