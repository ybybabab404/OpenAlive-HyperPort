package com.miui.aod.doze;

import android.util.Log;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class MiuiFullAodAdapter extends DozeMachine.Service.Delegate {
    public static final String TAG = "MiuiFullAodAdapter";

    public MiuiFullAodAdapter(DozeMachine.Service service, Executor executor) {
        super(service, executor);
    }

    @Override // com.miui.aod.doze.DozeMachine.Service.Delegate, com.miui.aod.doze.DozeMachine.Service
    public void setDozeScreenBrightness(int i) {
        if (getHost() == null || !getHost().isFullAod()) {
            super.setDozeScreenBrightness(i);
        } else {
            Log.d(TAG, "setDozeScreenBrightness: skip for full aod ");
        }
    }
}
