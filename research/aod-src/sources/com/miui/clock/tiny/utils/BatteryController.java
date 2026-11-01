package com.miui.clock.tiny.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.xiaomi.onetrack.api.am;
import com.xiaomi.onetrack.b.a;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class BatteryController extends BroadcastReceiver {
    public static final String TAG = "BatteryController";
    public static BatteryController sInstance;
    public boolean mCharging;
    public final Context mContext;
    public boolean mQuickCharging;
    public int mWiredChargeType;
    public int mWirelessChargeType;
    public boolean mHasReceivedBattery = false;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final ArrayList mChangeCallbacks = new ArrayList();
    public final BatteryStatus mBatteryStatus = new BatteryStatus(1, 0, 0, 0, 0, -1);

    public interface BatteryStateChangeCallback {
        void onBatteryLevelChanged(BatteryStatus batteryStatus);

        void onChargeStateChanged(boolean z, boolean z2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ChargeSpeed {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface WireState {
    }

    public BatteryController(Context context) {
        this.mContext = context;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("miui.intent.action.ACTION_QUICK_CHARGE_TYPE");
        intentFilter.addAction("miui.intent.action.ACTION_WIRELESS_TX_TYPE");
        intentFilter.addAction("miui.intent.action.ACTION_HVDCP_TYPE");
        intentFilter.setPriority(1001);
        context.registerReceiver(this, intentFilter, 2);
    }

    public static synchronized BatteryController getInstance(Context context) {
        try {
            if (sInstance == null) {
                Context context2 = ContextUtils.sGlobalContext;
                if (context2 != null) {
                    context = context2;
                } else if (context == null) {
                    context = null;
                } else if (context.getApplicationContext() != null) {
                    context = context.getApplicationContext();
                }
                if (context != null) {
                    sInstance = new BatteryController(context);
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return sInstance;
    }

    public static boolean isWirelessSuperRapidCharge(int i) {
        return i >= 9;
    }

    public void addCallback(BatteryStateChangeCallback batteryStateChangeCallback) {
        synchronized (this.mChangeCallbacks) {
            this.mChangeCallbacks.add(batteryStateChangeCallback);
        }
        if (this.mHasReceivedBattery) {
            batteryStateChangeCallback.onBatteryLevelChanged(this.mBatteryStatus);
            batteryStateChangeCallback.onChargeStateChanged(this.mCharging, this.mQuickCharging);
        }
    }

    public final int checkWireState(int i, int i2) {
        boolean z = i == 4;
        boolean z2 = i == 1 || i == 2;
        if (i2 != 2 && i2 != 5 && i2 != 4) {
            return -1;
        }
        if (z) {
            return 10;
        }
        return z2 ? 11 : -1;
    }

    public final void fireBatteryLevelChanged(BatteryStatus batteryStatus) {
        synchronized (this.mChangeCallbacks) {
            try {
                int size = this.mChangeCallbacks.size();
                for (int i = 0; i < size; i++) {
                    ((BatteryStateChangeCallback) this.mChangeCallbacks.get(i)).onBatteryLevelChanged(batteryStatus);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void fireChargeStateChanged(boolean z, boolean z2) {
        synchronized (this.mChangeCallbacks) {
            try {
                int size = this.mChangeCallbacks.size();
                for (int i = 0; i < size; i++) {
                    ((BatteryStateChangeCallback) this.mChangeCallbacks.get(i)).onChargeStateChanged(z, z2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int formatBatteryLevel(int i) {
        if (i < 0) {
            return 0;
        }
        if (i > 100) {
            return 100;
        }
        return i;
    }

    public final int getChargeSpeed(int i, int i2) {
        if (i == 10) {
            return isWirelessSuperRapidCharge(i2) ? 2 : 0;
        }
        if (i == 11) {
            if (isSuperSuperRapidCharge(i2)) {
                return 3;
            }
            if (isSuperRapidCharge(i2)) {
                return 2;
            }
            if (isRapidCharge(i2)) {
                return 1;
            }
        }
        return 0;
    }

    public final int getCurrentChargeDeviceType(int i, int i2) {
        if (i == 10) {
            return this.mWirelessChargeType;
        }
        if (i == 11) {
            return this.mWiredChargeType;
        }
        return -1;
    }

    public final boolean isBatteryStatusChanged(int i, int i2, int i3, int i4) {
        BatteryStatus batteryStatus = this.mBatteryStatus;
        return (i == batteryStatus.level && i2 == batteryStatus.plugged && i3 == batteryStatus.status && i4 == batteryStatus.chargeSpeed) ? false : true;
    }

    public boolean isRapidCharge(int i) {
        return i == 1;
    }

    public boolean isSuperRapidCharge(int i) {
        return i == 2 || i == 3;
    }

    public boolean isSuperSuperRapidCharge(int i) {
        return i == 4;
    }

    public final void notifyBatteryStatusChanged() {
        if (this.mBatteryStatus == null) {
            return;
        }
        Log.i(TAG, "notifyBatteryStatusChanged:  status: " + this.mBatteryStatus.status + " isPlugged: " + this.mBatteryStatus.plugged + " level: " + this.mBatteryStatus.level + " wireState: " + this.mBatteryStatus.wireState + " chargeSpeed: " + this.mBatteryStatus.chargeSpeed + " mWiredChargeType: " + this.mWiredChargeType + " mWirelessChargeType: " + this.mWirelessChargeType + " chargeDeviceType: " + this.mBatteryStatus.chargeDeviceType + " SUPPORT_BROADCAST_QUICK_CHARGE: true");
        BatteryStatus batteryStatus = this.mBatteryStatus;
        int i = batteryStatus.status;
        int i2 = batteryStatus.plugged;
        int batteryLevel = formatBatteryLevel(batteryStatus.level);
        BatteryStatus batteryStatus2 = this.mBatteryStatus;
        BatteryStatus batteryStatus3 = new BatteryStatus(i, i2, batteryLevel, batteryStatus2.wireState, batteryStatus2.chargeSpeed, batteryStatus2.chargeDeviceType);
        if (this.mCharging != batteryStatus3.isCharging() || this.mQuickCharging != batteryStatus3.isQuickCharge()) {
            boolean zIsCharging = batteryStatus3.isCharging();
            this.mCharging = zIsCharging;
            this.mQuickCharging = zIsCharging && batteryStatus3.isQuickCharge();
        }
        fireBatteryLevelChanged(batteryStatus3);
        fireChargeStateChanged(this.mCharging, this.mQuickCharging);
    }

    public final void onBatteryStatusChanged(Intent intent) {
        this.mHasReceivedBattery = true;
        int intExtra = intent.getIntExtra(a.d, 0);
        int intExtra2 = intent.getIntExtra(am.M, 1);
        int intExtra3 = intent.getIntExtra("plugged", 0);
        int iCheckWireState = checkWireState(intExtra3, intExtra2);
        int currentChargeDeviceType = getCurrentChargeDeviceType(iCheckWireState, this.mBatteryStatus.chargeDeviceType);
        int chargeSpeed = getChargeSpeed(iCheckWireState, currentChargeDeviceType);
        boolean zIsBatteryStatusChanged = isBatteryStatusChanged(intExtra, intExtra3, intExtra2, chargeSpeed);
        BatteryStatus batteryStatus = this.mBatteryStatus;
        batteryStatus.plugged = intExtra3;
        batteryStatus.wireState = iCheckWireState;
        batteryStatus.status = intExtra2;
        if (zIsBatteryStatusChanged) {
            batteryStatus.level = intExtra;
            batteryStatus.chargeDeviceType = currentChargeDeviceType;
            batteryStatus.chargeSpeed = chargeSpeed;
            this.mHandler.post(new Runnable() { // from class: com.miui.clock.tiny.utils.BatteryController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.notifyBatteryStatusChanged();
                }
            });
        }
    }

    public final void onChargeDeviceTypeChanged(int i) {
        BatteryStatus batteryStatus = this.mBatteryStatus;
        if (batteryStatus == null || i < 0) {
            return;
        }
        int currentChargeDeviceType = getCurrentChargeDeviceType(batteryStatus.wireState, i);
        BatteryStatus batteryStatus2 = this.mBatteryStatus;
        batteryStatus2.chargeSpeed = getChargeSpeed(batteryStatus2.wireState, currentChargeDeviceType);
        BatteryStatus batteryStatus3 = this.mBatteryStatus;
        if (currentChargeDeviceType != batteryStatus3.chargeDeviceType) {
            batteryStatus3.chargeDeviceType = i;
            notifyBatteryStatusChanged();
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.intent.action.BATTERY_CHANGED".equals(action)) {
            onBatteryStatusChanged(intent);
            return;
        }
        if ("miui.intent.action.ACTION_QUICK_CHARGE_TYPE".equals(action)) {
            int intExtra = intent.getIntExtra("miui.intent.extra.quick_charge_type", -1);
            this.mWiredChargeType = intExtra;
            onChargeDeviceTypeChanged(intExtra);
        } else if ("miui.intent.action.ACTION_WIRELESS_TX_TYPE".equals(action)) {
            int intExtra2 = intent.getIntExtra("miui.intent.extra.wireless_tx_type", -1);
            this.mWirelessChargeType = intExtra2;
            onChargeDeviceTypeChanged(intExtra2);
        } else if ("miui.intent.action.ACTION_HVDCP_TYPE".equals(action)) {
            int intExtra3 = intent.getIntExtra("miui.intent.extra.hvdcp_type", -1);
            this.mWiredChargeType = intExtra3;
            onChargeDeviceTypeChanged(intExtra3);
        }
    }

    public void removeCallback(BatteryStateChangeCallback batteryStateChangeCallback) {
        synchronized (this.mChangeCallbacks) {
            this.mChangeCallbacks.remove(batteryStateChangeCallback);
        }
    }
}
