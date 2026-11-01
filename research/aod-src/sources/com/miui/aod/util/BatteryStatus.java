package com.miui.aod.util;

/* JADX INFO: loaded from: classes.dex */
public class BatteryStatus {
    public int chargeDeviceType;
    public int chargeSpeed;
    public int level;
    public int plugged;
    public int status;
    public int wireState;

    public BatteryStatus(int i, int i2, int i3, int i4, int i5, int i6) {
        this.status = i;
        this.plugged = i2;
        this.level = i3;
        this.wireState = i4;
        this.chargeSpeed = i5;
        this.chargeDeviceType = i6;
    }

    public static boolean isPluggedIn(int i) {
        return i == 1 || i == 2 || i == 4;
    }

    public int getLevel() {
        return this.level;
    }

    public boolean isCharging() {
        return isPluggedIn() && isChargingOrFull();
    }

    public boolean isChargingOrFull() {
        int i = this.status;
        return i == 2 || i == 5;
    }

    public boolean isPluggedIn() {
        return isPluggedIn(this.plugged);
    }

    public boolean isQuickCharge() {
        return this.chargeSpeed == 1;
    }

    public boolean isSuperQuickCharge() {
        return this.chargeSpeed == 2;
    }

    public boolean isSuperSuperQuickCharge() {
        return this.chargeSpeed == 3;
    }
}
