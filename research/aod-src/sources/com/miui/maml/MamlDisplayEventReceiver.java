package com.miui.maml;

import android.os.Looper;
import android.view.DisplayEventReceiver;

/* JADX INFO: loaded from: classes2.dex */
public abstract class MamlDisplayEventReceiver extends DisplayEventReceiver {
    public MamlDisplayEventReceiver(Looper looper) {
        super(looper);
    }

    public abstract void onVsync(long j);

    public void onVsync(long j, int i, int i2) {
        onVsync(j);
    }

    public void onVsync(long j, long j2, int i) {
        onVsync(j);
    }

    public void onVsync(long j, long j2, int i, DisplayEventReceiver.VsyncEventData vsyncEventData) {
        onVsync(j);
    }
}
