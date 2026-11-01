package micloud.compat.independent.request;

import android.content.Context;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.utils.MiCloudSDKDependencyUtil;
import com.xiaomi.onetrack.g.a;
import micloud.compat.independent.sync.GdprUtilsCompat;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class RequestInjectorCompat {
    private static final IRequestInjectorCompat sRequestInjectorCompat;

    static {
        if (MiCloudSDKDependencyUtil.SDKEnvironment >= 18) {
            sRequestInjectorCompat = new RequestInjectorCompact_V18();
        } else {
            sRequestInjectorCompat = new RequestInjectorCompat_Base();
        }
    }

    private RequestInjectorCompat() {
    }

    public static void checkResponse(Context context, String str) {
        if (str != null && isPrivacyError(str)) {
            GdprUtilsCompat.notifyPrivacyDenied(context);
        }
    }

    public static void handleCloudServerException(Context context, CloudServerException cloudServerException) {
        int i;
        int i2 = cloudServerException.code;
        if (i2 == 503 && (i = cloudServerException.retryTime) > 0) {
            sRequestInjectorCompat.sendDataInTransferBroadcast(context, i);
        } else if (i2 == 52003) {
            GdprUtilsCompat.notifyPrivacyDenied(context);
        }
    }

    private static boolean isPrivacyError(String str) {
        try {
            return new JSONObject(str).optInt(a.d, 0) == 52003;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
