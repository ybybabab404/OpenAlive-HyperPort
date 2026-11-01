package com.miui.aod.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import com.miui.aod.DozeHost;
import com.miui.aod.Utils;
import com.miui.aod.utils.UserHandleUtils;
import com.xiaomi.onetrack.api.am;
import com.xiaomi.onetrack.b.a;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class BatteryController extends BroadcastReceiver {
    public static final String TAG = "BatteryController";
    public static boolean mCharging;
    public static BatteryController sInstance;
    public final ContentObserver mBatteryModeChangeObserver;
    public final BatteryStatus mBatteryStatus;
    public boolean mCharged;
    public final Context mContext;
    public WeakReference mHostRef;
    public boolean mPluggedIn;
    public int mWiredChargeType;
    public int mWirelessChargeType;
    public boolean mHasReceivedBattery = false;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public int mUserId = UserHandleUtils.USER_CURRENT;
    public boolean mIsRegistered = false;
    public final ArrayList mChangeCallbacks = new ArrayList();

    public interface BatteryStateChangeCallback {
        void onBatteryLevelChanged(BatteryStatus batteryStatus);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ChargeSpeed {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface WireState {
    }

    public BatteryController(final Context context) {
        this.mContext = context;
        this.mBatteryStatus = new BatteryStatus(1, 0, getBatteryLevel(context), 0, 0, -1);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("miui.intent.action.ACTION_QUICK_CHARGE_TYPE");
        intentFilter.addAction("miui.intent.action.ACTION_WIRELESS_TX_TYPE");
        intentFilter.addAction("miui.intent.action.ACTION_HVDCP_TYPE");
        intentFilter.setPriority(1001);
        context.registerReceiver(this, intentFilter, 2);
        this.mBatteryModeChangeObserver = new ContentObserver(null) { // from class: com.miui.aod.util.BatteryController.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                PowerSaveModeManager.getInstance(context).onPowerSaveChanged(Utils.isPowerSaveMode(BatteryController.this.mContext));
                if (BatteryController.this.mHostRef == null || BatteryController.this.mHostRef.get() == null) {
                    return;
                }
                ((DozeHost) BatteryController.this.mHostRef.get()).firePowerSaveChanged();
            }
        };
    }

    public static synchronized BatteryController getInstance(Context context) {
        try {
            if (sInstance == null && context != null) {
                BatteryController batteryController = new BatteryController(context.getApplicationContext());
                sInstance = batteryController;
                batteryController.init();
            }
        } catch (Throwable th) {
            throw th;
        }
        return sInstance;
    }

    public static boolean isCharging() {
        return mCharging;
    }

    public static boolean isWirelessSuperRapidCharge(int i) {
        return i >= 9;
    }

    public void addCallback(BatteryStateChangeCallback batteryStateChangeCallback) {
        synchronized (this.mChangeCallbacks) {
            this.mChangeCallbacks.add(batteryStateChangeCallback);
        }
        batteryStateChangeCallback.onBatteryLevelChanged(this.mBatteryStatus);
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

    public final int formatBatteryLevel(int i) {
        if (i < 0) {
            return 0;
        }
        if (i > 100) {
            return 100;
        }
        return i;
    }

    public final int getBatteryLevel(Context context) {
        try {
            return ((BatteryManager) context.getSystemService("batterymanager")).getIntProperty(4);
        } catch (Exception unused) {
            Log.d(TAG, "batteryLevel is 0");
            return 0;
        }
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

    public final int getCurrentChargeDeviceType(int i) {
        if (i == 10) {
            return this.mWirelessChargeType;
        }
        if (i == 11) {
            return this.mWiredChargeType;
        }
        return -1;
    }

    public final void init() {
        if (this.mIsRegistered) {
            return;
        }
        register(this.mUserId);
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

    public void notifyBatteryStatus(Context context, BatteryStateChangeCallback batteryStateChangeCallback) {
        if (batteryStateChangeCallback != null) {
            if (this.mHasReceivedBattery) {
                batteryStateChangeCallback.onBatteryLevelChanged(this.mBatteryStatus);
                return;
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
            Intent intentRegisterReceiver = context.registerReceiver(null, intentFilter, 2);
            if (intentRegisterReceiver != null) {
                int intExtra = intentRegisterReceiver.getIntExtra(a.d, 0);
                if (intentRegisterReceiver.getIntExtra("plugged", 0) != 0) {
                }
                int intExtra2 = intentRegisterReceiver.getIntExtra(am.M, 1);
                int intExtra3 = intentRegisterReceiver.getIntExtra("plugged", 0);
                BatteryStatus batteryStatus = this.mBatteryStatus;
                batteryStatus.level = intExtra;
                batteryStatus.status = intExtra2;
                batteryStatus.plugged = intExtra3;
                batteryStateChangeCallback.onBatteryLevelChanged(batteryStatus);
            }
        }
    }

    public void notifyBatteryStatusChanged() {
        if (this.mBatteryStatus == null) {
            return;
        }
        Log.i(TAG, "notifyBatteryStatusChanged:  status: " + this.mBatteryStatus.status + " isPlugged: " + this.mBatteryStatus.plugged + " level: " + this.mBatteryStatus.level + " wireState: " + this.mBatteryStatus.wireState + " chargeSpeed: " + this.mBatteryStatus.chargeSpeed + " mWiredChargeType: " + this.mWiredChargeType + " mWirelessChargeType: " + this.mWirelessChargeType + " chargeDeviceType: " + this.mBatteryStatus.chargeDeviceType);
        BatteryStatus batteryStatus = this.mBatteryStatus;
        int i = batteryStatus.status;
        int i2 = batteryStatus.plugged;
        int batteryLevel = formatBatteryLevel(batteryStatus.level);
        BatteryStatus batteryStatus2 = this.mBatteryStatus;
        fireBatteryLevelChanged(new BatteryStatus(i, i2, batteryLevel, batteryStatus2.wireState, batteryStatus2.chargeSpeed, batteryStatus2.chargeDeviceType));
    }

    public final void onBatteryStatusChanged(Intent intent) {
        this.mHasReceivedBattery = true;
        int intExtra = intent.getIntExtra(a.d, 0);
        int intExtra2 = intent.getIntExtra("plugged", 0);
        this.mPluggedIn = intExtra2 != 0;
        int intExtra3 = intent.getIntExtra(am.M, 1);
        boolean z = intExtra3 == 5;
        this.mCharged = z;
        boolean z2 = (z || intExtra3 == 2) && this.mPluggedIn;
        if (z2 != mCharging) {
            mCharging = z2;
            if (this.mBatteryModeChangeObserver != null) {
                this.mHandler.post(new Runnable() { // from class: com.miui.aod.util.BatteryController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.mBatteryModeChangeObserver.onChange(false);
                    }
                });
            }
        }
        int iCheckWireState = checkWireState(intExtra2, intExtra3);
        int currentChargeDeviceType = getCurrentChargeDeviceType(iCheckWireState);
        int chargeSpeed = getChargeSpeed(iCheckWireState, currentChargeDeviceType);
        boolean zIsBatteryStatusChanged = isBatteryStatusChanged(intExtra, intExtra2, intExtra3, chargeSpeed);
        BatteryStatus batteryStatus = this.mBatteryStatus;
        batteryStatus.plugged = intExtra2;
        batteryStatus.wireState = iCheckWireState;
        batteryStatus.status = intExtra3;
        if (zIsBatteryStatusChanged) {
            batteryStatus.level = intExtra;
            batteryStatus.chargeDeviceType = currentChargeDeviceType;
            batteryStatus.chargeSpeed = chargeSpeed;
            this.mHandler.post(new Runnable() { // from class: com.miui.aod.util.BatteryController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.notifyBatteryStatusChanged();
                }
            });
        }
    }

    public final void onChargeDeviceTypeChanged(int i) {
        Log.i(TAG, "onChargeDeviceTypeChanged: " + i);
        BatteryStatus batteryStatus = this.mBatteryStatus;
        if (batteryStatus == null || i < 0) {
            return;
        }
        int currentChargeDeviceType = getCurrentChargeDeviceType(batteryStatus.wireState);
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

    public synchronized void onUserChanged(int i) {
        try {
            if (i != this.mUserId) {
                this.mUserId = i;
                if (this.mIsRegistered) {
                    unregister();
                }
                register(this.mUserId);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void register(int i) {
        Log.i(TAG, "register userId: " + i);
        this.mIsRegistered = true;
        UserHandleUtils.registerContentObserverForUser(this.mContext, Settings.System.getUriFor("POWER_SAVE_MODE_OPEN"), false, this.mBatteryModeChangeObserver, i);
        UserHandleUtils.registerContentObserverForUser(this.mContext, Settings.System.getUriFor("EXTREME_POWER_MODE_ENABLE"), false, this.mBatteryModeChangeObserver, i);
        this.mBatteryModeChangeObserver.onChange(false);
    }

    public void removeCallback(BatteryStateChangeCallback batteryStateChangeCallback) {
        synchronized (this.mChangeCallbacks) {
            this.mChangeCallbacks.remove(batteryStateChangeCallback);
        }
    }

    public void setDozeHost(DozeHost dozeHost) {
        this.mHostRef = new WeakReference(dozeHost);
    }

    public final void unregister() {
        Log.i(TAG, "unregister");
        this.mIsRegistered = false;
        this.mContext.getContentResolver().unregisterContentObserver(this.mBatteryModeChangeObserver);
    }
}
