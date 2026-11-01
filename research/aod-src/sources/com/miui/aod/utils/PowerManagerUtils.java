package com.miui.aod.utils;

import android.os.PowerManager;

/* JADX INFO: loaded from: classes.dex */
public final class PowerManagerUtils {
    private PowerManagerUtils() {
    }

    public static void goToSleep(PowerManager powerManager, long j) {
        powerManager.goToSleep(j);
    }

    public static boolean isAmbientDisplaySuppressed(PowerManager powerManager) {
        return powerManager.isAmbientDisplaySuppressed();
    }

    public static void setDozeAfterScreenOff(PowerManager powerManager, boolean z) {
        powerManager.setDozeAfterScreenOff(z);
    }

    public static void wakeUp(PowerManager powerManager, long j, String str) {
        powerManager.wakeUp(j, str);
    }
}
