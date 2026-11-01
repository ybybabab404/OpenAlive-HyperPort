package com.xiaomi.micloudsdk.micloudrichmedia;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/* JADX INFO: loaded from: classes2.dex */
class Utils {
    public int mCurrentUploadChunk;

    public class DownloadFileInfo {
    }

    public class NetworkConnectivityChangedReceiver extends BroadcastReceiver {
        public final /* synthetic */ Utils this$0;

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Utils utils = this.this$0;
            utils.mCurrentUploadChunk = utils.getChunkSize(context);
        }
    }

    public static int getActiveNetworkType(Context context) {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null) {
            return -1;
        }
        return activeNetworkInfo.getType();
    }

    public static boolean isWifi(Context context) {
        return getActiveNetworkType(context) == 1;
    }

    public final int getChunkSize(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (!connectivityManager.isActiveNetworkMetered()) {
                return 102400;
            }
            if (isWifi(context)) {
                return 51200;
            }
        }
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(0);
        if (networkInfo == null) {
            return 0;
        }
        return NetworkInfo.State.CONNECTED == networkInfo.getState() ? 51200 : 0;
    }
}
