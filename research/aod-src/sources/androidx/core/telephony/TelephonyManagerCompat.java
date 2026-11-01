package androidx.core.telephony;

import android.annotation.SuppressLint;
import android.telephony.TelephonyManager;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;

/* JADX INFO: loaded from: classes.dex */
public class TelephonyManagerCompat {

    @RequiresApi
    public static class Api23Impl {
        @SuppressLint({"MissingPermission"})
        @Nullable
        @RequiresPermission
        @DoNotInline
        public static String getDeviceId(TelephonyManager telephonyManager, int i) {
            return telephonyManager.getDeviceId(i);
        }
    }

    @RequiresApi
    public static class Api26Impl {
        @SuppressLint({"MissingPermission"})
        @Nullable
        @RequiresPermission
        @DoNotInline
        public static String getImei(TelephonyManager telephonyManager) {
            return telephonyManager.getImei();
        }
    }

    @RequiresApi
    public static class Api30Impl {
        @DoNotInline
        public static int getSubscriptionId(TelephonyManager telephonyManager) {
            return telephonyManager.getSubscriptionId();
        }
    }
}
