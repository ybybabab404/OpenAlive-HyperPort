package miui.cloud.common;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import com.xiaomi.micloudsdk.utils.CloudCoder;
import com.xiaomi.onetrack.api.bf;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.HashSet;
import miui.telephony.CloudTelephonyManager;
import miui.telephony.exception.IllegalDeviceException;

/* JADX INFO: loaded from: classes2.dex */
public class XDeviceInfo {
    public static XDeviceInfo sInstance = null;
    public static KeyStoreType sKeyStoreType = null;
    public static long sLastEmptyDeviceIdTime = -1;
    public final String IMEI;
    public final String MAC;
    public final String SN;
    public final String deviceId;
    public final KeyStoreType keyStoreType;
    public final String model;
    public final PhoneType type;

    /* JADX INFO: renamed from: miui.cloud.common.XDeviceInfo$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {
        public final /* synthetic */ XCallback val$callback;
        public final /* synthetic */ Context val$context;

        @Override // java.lang.Runnable
        public void run() {
            ((DeviceInfoReayListener) this.val$callback.asInterface()).onDeviceInfoReay(XDeviceInfo.syncGet(this.val$context));
        }
    }

    /* JADX INFO: renamed from: miui.cloud.common.XDeviceInfo$2, reason: invalid class name */
    class AnonymousClass2 implements Runnable {
        public final /* synthetic */ XCallback val$callback;
        public final /* synthetic */ Context val$context;
        public final /* synthetic */ boolean val$reloadDeviceId;

        @Override // java.lang.Runnable
        public void run() {
            ((DeviceInfoReayListener) this.val$callback.asInterface()).onDeviceInfoReay(XDeviceInfo.syncGet(this.val$context, this.val$reloadDeviceId));
        }
    }

    public interface DeviceInfoReayListener {
        void onDeviceInfoReay(XDeviceInfo xDeviceInfo);
    }

    public enum KeyStoreType {
        TZ("TZ"),
        NONE("NONE");

        public String mDesc;

        KeyStoreType(String str) {
            this.mDesc = str;
        }
    }

    public enum PhoneType {
        PAD("pad"),
        PHONE(bf.d);

        public String mDesc;

        PhoneType(String str) {
            this.mDesc = str;
        }
    }

    static {
        HashSet hashSet = new HashSet();
        hashSet.add("leo");
        hashSet.add("andromeda");
        hashSet.add("begonia");
        hashSet.add("davinciin");
        hashSet.add("raphaelin");
        hashSet.add("begoniain");
        hashSet.add("hennessy");
        hashSet.add("olivelite");
        hashSet.add("olivewood");
        hashSet.add("libra");
        hashSet.add("aqua");
        hashSet.add("gemini");
        hashSet.add("gold");
        hashSet.add("vela");
        hashSet.add("kenzo");
        hashSet.add("grus");
        hashSet.add("tucana");
        hashSet.add("ido");
        hashSet.add("hydrogen");
        hashSet.add("helium");
        hashSet.add("kate");
        hashSet.add("land");
        hashSet.add("lavender");
        hashSet.add("markw");
        hashSet.add("nikel");
        hashSet.add("omega");
        hashSet.add("cepheus");
        hashSet.add("capricorn");
        hashSet.add("laurus");
        hashSet.add("prada");
        hashSet.add("lithium");
        hashSet.add("scorpio");
        hashSet.add("natrium");
        hashSet.add("rolex");
        hashSet.add("mido");
        hashSet.add("santoni");
        hashSet.add("ginkgo");
        hashSet.add("sagit");
        hashSet.add("centaur");
        hashSet.add("oxygen");
        hashSet.add("tiffany");
        hashSet.add("ulysse");
        hashSet.add("ugglite");
        hashSet.add("chiron");
        hashSet.add("ugg");
        hashSet.add("jason");
        hashSet.add("riva");
        hashSet.add("crux");
        hashSet.add("vince");
        hashSet.add("rosy");
        hashSet.add("meri");
        hashSet.add("davinci");
        hashSet.add("pine");
        hashSet.add("whyred");
        hashSet.add("dipper");
        hashSet.add("onc");
        hashSet.add("polaris");
        hashSet.add("pyxis");
        hashSet.add("ysl");
        hashSet.add("wayne");
        hashSet.add("nitrogen");
        hashSet.add("sirius");
        hashSet.add("sakura");
        hashSet.add("sakura_india");
        hashSet.add("beryllium");
        hashSet.add("violet");
        hashSet.add("raphael");
        hashSet.add("cactus");
        hashSet.add("cereus");
        hashSet.add("lotus");
        hashSet.add("willow");
        hashSet.add("clover");
        hashSet.add("ursa");
        hashSet.add("olive");
        hashSet.add("tulip");
        hashSet.add("draco");
        hashSet.add("platina");
        hashSet.add("perseus");
        hashSet.add("equuleus");
        sKeyStoreType = hashSet.contains(Build.DEVICE.toLowerCase()) ? KeyStoreType.TZ : KeyStoreType.NONE;
    }

    public XDeviceInfo(Context context, XDeviceInfo xDeviceInfo, boolean z) {
        PhoneType phoneType = xDeviceInfo.type;
        this.type = phoneType;
        String strBlockingGetNakedDeviceId = blockingGetNakedDeviceId(context, z);
        this.deviceId = TextUtils.isEmpty(strBlockingGetNakedDeviceId) ? strBlockingGetNakedDeviceId : CloudCoder.hashDeviceInfo(strBlockingGetNakedDeviceId);
        this.IMEI = phoneType != PhoneType.PHONE ? "" : strBlockingGetNakedDeviceId;
        this.SN = xDeviceInfo.SN;
        this.MAC = xDeviceInfo.MAC;
        this.model = xDeviceInfo.model;
        this.keyStoreType = xDeviceInfo.keyStoreType;
    }

    public XDeviceInfo(Context context, boolean z) {
        PhoneType phoneType = miui.os.Build.IS_TABLET ? PhoneType.PAD : PhoneType.PHONE;
        this.type = phoneType;
        String strBlockingGetNakedDeviceId = blockingGetNakedDeviceId(context, z);
        this.deviceId = TextUtils.isEmpty(strBlockingGetNakedDeviceId) ? strBlockingGetNakedDeviceId : CloudCoder.hashDeviceInfo(strBlockingGetNakedDeviceId);
        this.IMEI = phoneType != PhoneType.PHONE ? "" : strBlockingGetNakedDeviceId;
        this.SN = Build.SERIAL;
        this.MAC = getMAC(context);
        this.model = Build.MODEL;
        this.keyStoreType = getKeyStoreTypeUnblocking();
    }

    public static synchronized String blockingGetNakedDeviceId(Context context, boolean z) {
        String strBlockingGetDeviceId;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        long j = sLastEmptyDeviceIdTime;
        if (j != -1 && jElapsedRealtime - j < 180000) {
            return "";
        }
        try {
            strBlockingGetDeviceId = CloudTelephonyManager.blockingGetDeviceId(context, 60000L, z);
        } catch (IllegalDeviceException unused) {
            XLogger.loge("Failed to get the device id.");
            strBlockingGetDeviceId = null;
        }
        if (TextUtils.isEmpty(strBlockingGetDeviceId)) {
            sLastEmptyDeviceIdTime = jElapsedRealtime;
            return "";
        }
        sLastEmptyDeviceIdTime = -1L;
        return strBlockingGetDeviceId;
    }

    public static KeyStoreType getKeyStoreTypeUnblocking() {
        return KeyStoreType.TZ;
    }

    public static synchronized XDeviceInfo syncGet(Context context) {
        return syncGet(context, false);
    }

    public static synchronized XDeviceInfo syncGet(Context context, boolean z) {
        try {
            if (Looper.getMainLooper().getThread().equals(Thread.currentThread())) {
                throw new IllegalStateException("syncGet can not be called in the main thread. ");
            }
            XDeviceInfo xDeviceInfo = sInstance;
            if (xDeviceInfo == null) {
                sInstance = new XDeviceInfo(context, z);
            } else {
                sInstance = new XDeviceInfo(context, xDeviceInfo, z);
            }
        } catch (Throwable th) {
            throw th;
        }
        return sInstance;
    }

    public final String getMAC(Context context) {
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                String name = networkInterface.getName();
                if (name != null && name.toLowerCase().indexOf("wlan") != -1) {
                    try {
                        byte[] hardwareAddress = networkInterface.getHardwareAddress();
                        if (hardwareAddress != null) {
                            StringBuilder sb = new StringBuilder();
                            for (byte b : hardwareAddress) {
                                sb.append(String.format("%02X:", Byte.valueOf(b)));
                            }
                            if (sb.length() > 0) {
                                sb.deleteCharAt(sb.length() - 1);
                            }
                            return sb.toString();
                        }
                    } catch (SocketException e) {
                        XLogger.log("Failed to get MAC for " + name + ", continue. ", e);
                    }
                }
            }
            return "N/A";
        } catch (SocketException e2) {
            XLogger.log("Failed to get MAC. ", e2);
            return "N/A";
        }
    }

    public String toString() {
        return String.format("type: %s, deviceid: %s, IMEM: %s, SN: %s, MAC: %s, model: %s, keyStoreType: %s", this.type.name(), this.deviceId, this.IMEI, this.SN, this.MAC, this.model, this.keyStoreType);
    }
}
