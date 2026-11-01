package miui.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.onetrack.util.ac;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeoutException;
import micloud.compat.v18.utils.BusyWaitUtil;
import micloud.net.ConnectivityHelper;
import miui.cloud.common.XLogger;
import miui.cloud.telephony.SubscriptionManager;
import miui.cloud.telephony.TelephonyManager;
import miui.cloud.util.AnonymousDeviceIdUtil;
import miui.cloud.util.SysHelper;
import miui.telephony.exception.IllegalDeviceException;

/* JADX INFO: loaded from: classes2.dex */
public class CloudTelephonyManager {
    public static final String SLOT_ID = SubscriptionManager.getSLOT_KEY();
    public static volatile String sDeviceIdCache;
    public static volatile DeviceIdConfiguration sDeviceIdConfiguration;
    public static volatile DeviceIdConfiguration sDeviceIdConfigurationTestInjection;

    /* JADX INFO: renamed from: miui.telephony.CloudTelephonyManager$5, reason: invalid class name */
    class AnonymousClass5 extends BroadcastReceiver {
        public final /* synthetic */ AsyncFuture val$future;
        public final /* synthetic */ int val$simIndex;
        public final /* synthetic */ TelephonyManager val$tm;

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (TelephonyManager.getIccCardConstants_INTENT_VALUE_ICC_IMSI().equals(intent.getStringExtra(TelephonyManager.getIccCardConstants_INTENT_KEY_ICC_STATE()))) {
                this.val$future.setResult(CloudTelephonyManager.getSimIdByPhoneType(this.val$tm, this.val$simIndex));
            }
        }
    }

    public static class AsyncFuture<V> extends FutureTask<V> {

        /* JADX INFO: renamed from: miui.telephony.CloudTelephonyManager$AsyncFuture$1, reason: invalid class name */
        class AnonymousClass1 implements Callable<Object> {
            @Override // java.util.concurrent.Callable
            public Object call() {
                throw new IllegalStateException("this should never be called");
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void setResult(Object obj) {
            set(obj);
        }
    }

    public interface DeviceIdConfiguration {
        boolean checkValid(Context context, String str);

        long getBusywaitRetryIntervalMillisRecommandation(Context context);

        String tryGetId(Context context);
    }

    public static final class TypedSimId {
        public final int type;
        public final String value;

        public TypedSimId(int i, String str) {
            this.type = i;
            this.value = str;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || TypedSimId.class != obj.getClass()) {
                return false;
            }
            TypedSimId typedSimId = (TypedSimId) obj;
            if (this.type != typedSimId.type) {
                return false;
            }
            String str = this.value;
            return str == null ? typedSimId.value == null : str.equals(typedSimId.value);
        }

        public int hashCode() {
            int i = this.type * 31;
            String str = this.value;
            return i + (str != null ? str.hashCode() : 0);
        }

        public String toPlain() {
            return this.type + ac.b + this.value;
        }

        public String toString() {
            return toPlain();
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: miui.telephony.exception.IllegalDeviceException */
    public static String blockingGetDeviceId(final Context context, long j, boolean z) throws IllegalDeviceException {
        String str;
        ensureNotOnMainThread(context);
        Log.i("CloudTelephonyManager", "blockingGetDeviceId is called by " + context.getPackageName() + " with timeout: " + j);
        String str2 = sDeviceIdCache;
        if (!z && !TextUtils.isEmpty(str2)) {
            return str2;
        }
        final DeviceIdConfiguration deviceIdConfiguration = getDeviceIdConfiguration(context);
        if (j < 0) {
            j = 0;
        }
        try {
            str = (String) BusyWaitUtil.busyWait(new BusyWaitUtil.Action<String>() { // from class: miui.telephony.CloudTelephonyManager.1
                @Override // micloud.compat.v18.utils.BusyWaitUtil.Action
                public String doAction(long j2, long j3) throws BusyWaitUtil.NotAvailableException {
                    String strTryGetId = deviceIdConfiguration.tryGetId(context);
                    if (TextUtils.isEmpty(strTryGetId)) {
                        Log.i("CloudTelephonyManager", "deviceid is empty after " + j3 + " retries");
                        throw new BusyWaitUtil.NotAvailableException();
                    }
                    Log.i("CloudTelephonyManager", "got deviceid after " + j3 + " retries");
                    CloudTelephonyManager.logDeviceId(strTryGetId);
                    return strTryGetId;
                }
            }, j, deviceIdConfiguration.getBusywaitRetryIntervalMillisRecommandation(context));
        } catch (InterruptedException e) {
            Log.e("CloudTelephonyManager", "blockingGetDeviceId, InterruptedException while busy-waiting", e);
            str = null;
        } catch (TimeoutException e2) {
            Log.e("CloudTelephonyManager", "blockingGetDeviceId, busy-wait timeout", e2);
            str = null;
        }
        if (deviceIdConfiguration.checkValid(context, str)) {
            sDeviceIdCache = str;
            return str;
        }
        SysHelper.showInvalidDeviceIdWarning(context, str);
        throw new IllegalDeviceException("can't get a valid device id");
    }

    public static void ensureNotOnMainThread(Context context) {
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper != null && looperMyLooper == context.getMainLooper()) {
            throw new IllegalStateException("calling this from your main thread can lead to deadlock");
        }
    }

    public static DeviceIdConfiguration getDeviceIdConfiguration(Context context) {
        DeviceIdConfiguration deviceIdConfiguration = sDeviceIdConfigurationTestInjection;
        if (deviceIdConfiguration != null) {
            return deviceIdConfiguration;
        }
        DeviceIdConfiguration deviceIdConfiguration2 = sDeviceIdConfiguration;
        if (deviceIdConfiguration2 != null) {
            return deviceIdConfiguration2;
        }
        synchronized (DeviceIdConfiguration.class) {
            try {
                DeviceIdConfiguration deviceIdConfiguration3 = sDeviceIdConfiguration;
                if (deviceIdConfiguration3 != null) {
                    return deviceIdConfiguration3;
                }
                if (AnonymousDeviceIdUtil.isEnforced(context)) {
                    logDeviceIdInfo("getDeviceId: use restrict_imie");
                    DeviceIdConfiguration deviceIdConfiguration4 = new DeviceIdConfiguration() { // from class: miui.telephony.CloudTelephonyManager.2
                        @Override // miui.telephony.CloudTelephonyManager.DeviceIdConfiguration
                        public boolean checkValid(Context context2, String str) {
                            return !TextUtils.isEmpty(str);
                        }

                        @Override // miui.telephony.CloudTelephonyManager.DeviceIdConfiguration
                        public long getBusywaitRetryIntervalMillisRecommandation(Context context2) {
                            return 30000L;
                        }

                        @Override // miui.telephony.CloudTelephonyManager.DeviceIdConfiguration
                        public String tryGetId(Context context2) {
                            if (AnonymousDeviceIdUtil.useOAID()) {
                                CloudTelephonyManager.logDeviceIdInfo("getDeviceId: restrict_imie, try get oaid");
                                String oaid = AnonymousDeviceIdUtil.getOAID(context2);
                                if (!TextUtils.isEmpty(oaid)) {
                                    CloudTelephonyManager.logDeviceIdInfo("getDeviceId: restrict_imei, use oaid");
                                    return oaid;
                                }
                            }
                            Bundle bundleCall = context2.getContentResolver().call(Uri.parse("content://com.xiaomi.cloud.cloudidprovider"), "getCloudId", (String) null, (Bundle) null);
                            if (bundleCall != null) {
                                CloudTelephonyManager.logDeviceIdInfo("getDeviceId: restrict_imie, use cloudId");
                                return bundleCall.getString("result_id");
                            }
                            CloudTelephonyManager.logDeviceIdInfo("getDeviceId: restrict_imie, use androidId");
                            return AnonymousDeviceIdUtil.getAndroidId(context2);
                        }
                    };
                    sDeviceIdConfiguration = deviceIdConfiguration4;
                    return deviceIdConfiguration4;
                }
                if (hasTelephonyFeature(context)) {
                    logDeviceIdInfo("getDeviceId: use no_restrict_imei");
                    DeviceIdConfiguration deviceIdConfiguration5 = new DeviceIdConfiguration() { // from class: miui.telephony.CloudTelephonyManager.3
                        @Override // miui.telephony.CloudTelephonyManager.DeviceIdConfiguration
                        public boolean checkValid(Context context2, String str) {
                            return SysHelper.validateIMEI(str);
                        }

                        @Override // miui.telephony.CloudTelephonyManager.DeviceIdConfiguration
                        public long getBusywaitRetryIntervalMillisRecommandation(Context context2) {
                            return 5000L;
                        }

                        @Override // miui.telephony.CloudTelephonyManager.DeviceIdConfiguration
                        public String tryGetId(Context context2) {
                            return TelephonyManager.getDefault().getMiuiDeviceId();
                        }
                    };
                    sDeviceIdConfiguration = deviceIdConfiguration5;
                    return deviceIdConfiguration5;
                }
                logDeviceIdInfo("getDeviceId: use macAddress");
                DeviceIdConfiguration deviceIdConfiguration6 = new DeviceIdConfiguration() { // from class: miui.telephony.CloudTelephonyManager.4
                    @Override // miui.telephony.CloudTelephonyManager.DeviceIdConfiguration
                    public boolean checkValid(Context context2, String str) {
                        return SysHelper.validateMAC(str);
                    }

                    @Override // miui.telephony.CloudTelephonyManager.DeviceIdConfiguration
                    public long getBusywaitRetryIntervalMillisRecommandation(Context context2) {
                        return 10000L;
                    }

                    @Override // miui.telephony.CloudTelephonyManager.DeviceIdConfiguration
                    public String tryGetId(Context context2) {
                        return ConnectivityHelper.getInstance(context2).getMacAddress();
                    }
                };
                sDeviceIdConfiguration = deviceIdConfiguration6;
                return deviceIdConfiguration6;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static TypedSimId getSimIdByPhoneType(TelephonyManager telephonyManager, int i) {
        int phoneTypeForSlot = telephonyManager.getPhoneTypeForSlot(i);
        Log.v("CloudTelephonyManager", "phone type: " + phoneTypeForSlot);
        if (phoneTypeForSlot == TelephonyManager.getPHONE_TYPE_CDMA()) {
            String simSerialNumberForSlot = telephonyManager.getSimSerialNumberForSlot(i);
            if (TextUtils.isEmpty(simSerialNumberForSlot)) {
                return null;
            }
            return new TypedSimId(1, simSerialNumberForSlot);
        }
        if (phoneTypeForSlot == TelephonyManager.getPHONE_TYPE_GSM()) {
            String subscriberIdForSlot = telephonyManager.getSubscriberIdForSlot(i);
            if (TextUtils.isEmpty(subscriberIdForSlot)) {
                return null;
            }
            return new TypedSimId(2, subscriberIdForSlot);
        }
        if (phoneTypeForSlot != 0) {
            return null;
        }
        String simSerialNumberForSlot2 = telephonyManager.getSimSerialNumberForSlot(i);
        if (TextUtils.isEmpty(simSerialNumberForSlot2)) {
            return null;
        }
        return new TypedSimId(1, simSerialNumberForSlot2);
    }

    public static boolean hasTelephonyFeature(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.telephony");
    }

    public static void logDeviceId(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("getDeviceId: ");
        sb.append((str == null || str.length() < 2) ? "wrongId" : str.substring(0, 2));
        logDeviceIdInfo(sb.toString());
    }

    public static void logDeviceIdInfo(String str) {
        XLogger.logi("CloudTelephonyManager", str);
        Log.i("CloudTelephonyManager", str);
    }
}
