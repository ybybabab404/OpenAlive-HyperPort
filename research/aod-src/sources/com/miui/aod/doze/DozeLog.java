package com.miui.aod.doze;

import android.content.Context;
import android.util.Log;
import com.miui.aod.util.SystemPropertiesUtils;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;

/* JADX INFO: loaded from: classes.dex */
public class DozeLog {
    public static final boolean DEBUG = Log.isLoggable("DozeLog", 3);
    public static final SimpleDateFormat FORMAT;
    public static final int SIZE;
    public static int sCount;
    public static SummaryStats sEmergencyCallStats;
    public static String[] sMessages;
    public static SummaryStats sNotificationPulseStats;
    public static SummaryStats sPickupPulseNearVibrationStats;
    public static SummaryStats sPickupPulseNotNearVibrationStats;
    public static int sPosition;
    public static SummaryStats[][] sProxStats;
    public static boolean sPulsing;
    public static boolean sRegisterKeyguardCallback;
    public static SummaryStats sScreenOnNotPulsingStats;
    public static SummaryStats sScreenOnPulsingStats;
    public static long sSince;
    public static long[] sTimes;

    public static class SummaryStats {
        public SummaryStats() {
        }
    }

    static {
        SIZE = SystemPropertiesUtils.INSTANCE.getInt("ro.debuggable", 0) == 1 ? 400 : 50;
        FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
        sRegisterKeyguardCallback = true;
    }

    public static int aodReasonToInt(String str) {
        if ("reason_keycode_goto".equals(str)) {
            return 7;
        }
        return "reason_fod".equals(str) ? 6 : -1;
    }

    public static void init(Context context) {
        synchronized (DozeLog.class) {
            try {
                if (sMessages == null) {
                    int i = SIZE;
                    sTimes = new long[i];
                    sMessages = new String[i];
                    sSince = System.currentTimeMillis();
                    sPickupPulseNearVibrationStats = new SummaryStats();
                    sPickupPulseNotNearVibrationStats = new SummaryStats();
                    sNotificationPulseStats = new SummaryStats();
                    sScreenOnPulsingStats = new SummaryStats();
                    sScreenOnNotPulsingStats = new SummaryStats();
                    sEmergencyCallStats = new SummaryStats();
                    sProxStats = (SummaryStats[][]) Array.newInstance((Class<?>) SummaryStats.class, 6, 2);
                    for (int i2 = 0; i2 < 6; i2++) {
                        sProxStats[i2][0] = new SummaryStats();
                        sProxStats[i2][1] = new SummaryStats();
                    }
                    log("init");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void log(String str) {
        synchronized (DozeLog.class) {
            try {
                if (sMessages == null) {
                    return;
                }
                sTimes[sPosition] = System.currentTimeMillis();
                String[] strArr = sMessages;
                int i = sPosition;
                strArr[i] = str;
                int i2 = SIZE;
                sPosition = (i + 1) % i2;
                sCount = Math.min(sCount + 1, i2);
                if (DEBUG) {
                    Log.d("DozeLog", str);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void traceDozing(Context context, boolean z) {
        sPulsing = false;
        init(context);
        log("dozing " + z);
    }

    public static void traceMissedTick(String str) {
        log("missedTick by=" + str);
    }

    public static void tracePulseDropped(Context context, boolean z, DozeMachine.State state, boolean z2) {
        init(context);
        log("pulseDropped pulsePending=" + z + " state=" + state + " blocked=" + z2);
    }

    public static void traceState(DozeMachine.State state) {
        log("state " + state);
    }
}
