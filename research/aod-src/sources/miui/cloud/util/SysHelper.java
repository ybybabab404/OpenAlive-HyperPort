package miui.cloud.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;
import miui.cloud.common.XLogger;
import miui.os.Build;

/* JADX INFO: loaded from: classes2.dex */
public class SysHelper {
    public static boolean isEmptyDeviceId(String str) {
        return TextUtils.isEmpty(str) || "0".equals(str) || "null".equalsIgnoreCase(str);
    }

    public static void showInvalidDeviceIdWarning(Context context, String str) {
        if (Build.IS_STABLE_VERSION) {
            return;
        }
        final Context applicationContext = context.getApplicationContext();
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: miui.cloud.util.SysHelper.1
            @Override // java.lang.Runnable
            public void run() {
                Toast.makeText(applicationContext, "Can't get a valid device ID", 1).show();
            }
        });
        XLogger.loge("SysHelper", "Can't get a valid device ID");
    }

    public static boolean validateIMEI(String str) {
        return !isEmptyDeviceId(str) && str.length() >= 14;
    }

    public static boolean validateMAC(String str) {
        return !isEmptyDeviceId(str);
    }
}
