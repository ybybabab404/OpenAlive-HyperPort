package micloud.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes2.dex */
public class ConnectivityHelper {
    public static ConnectivityHelper sInstance;
    public final ConnectivityManager mConnectivityManager;
    public volatile String mMacAddress;
    public final WifiManager mWifiManager;

    public ConnectivityHelper(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.mConnectivityManager = (ConnectivityManager) applicationContext.getSystemService("connectivity");
        this.mWifiManager = (WifiManager) applicationContext.getSystemService("wifi");
    }

    public static synchronized ConnectivityHelper getInstance(Context context) {
        try {
            if (sInstance == null) {
                sInstance = new ConnectivityHelper(context);
            }
        } catch (Throwable th) {
            throw th;
        }
        return sInstance;
    }

    public String getMacAddress() {
        String macAddress = this.mMacAddress;
        if (TextUtils.isEmpty(macAddress)) {
            WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
            if (connectionInfo != null) {
                macAddress = connectionInfo.getMacAddress();
            }
            if (!TextUtils.isEmpty(macAddress)) {
                this.mMacAddress = macAddress;
            }
        }
        return macAddress;
    }
}
