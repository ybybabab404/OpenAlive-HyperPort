package com.xiaomi.micloudsdk.request.utils;

import android.annotation.SuppressLint;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
@SuppressLint({"NewApi"})
public class CloudRelocationUtils {
    public static final int[] SERVER_RETRY_INTERVALS = {1000, 2000, 5000, 10000};
    public static final String URL_RELOCATION_QUERY = MiCloudConstants.URL.URL_RELOCATION_BASE + "/mic/relocation/v3/user/record";
    public static volatile Map sHostsCacheInner = new HashMap();
    public static Object sUpdateMiCloudHostsLock = new Object();

    public enum UpdateStatus {
        UPDATING,
        SUCCESS,
        FAILED
    }
}
