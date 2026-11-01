package miui.cloud.external;

import android.util.ArrayMap;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class CloudSysHelper {
    public static final Map MAIN_SYNCS_WITH_PKG;

    static {
        ArrayMap arrayMap = new ArrayMap();
        MAIN_SYNCS_WITH_PKG = arrayMap;
        arrayMap.put("sms", "com.android.mms");
        arrayMap.put("com.android.contacts", "com.android.contacts");
        arrayMap.put("call_log", "com.android.contacts");
    }
}
