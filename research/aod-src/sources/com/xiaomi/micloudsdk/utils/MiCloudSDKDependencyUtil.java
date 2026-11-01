package com.xiaomi.micloudsdk.utils;

import android.util.Log;

/* JADX INFO: loaded from: classes2.dex */
public class MiCloudSDKDependencyUtil {
    public static final int SDKEnvironment;

    static {
        int sDKEnvironment = getSDKEnvironment();
        SDKEnvironment = sDKEnvironment;
        Log.i("MiCloudSDKDependencyUtil", "MiCloudSDK environment: " + sDKEnvironment);
    }

    public static int getSDKEnvironment() {
        Class clsLoadClass = ReflectUtils.loadClass("com.xiaomi.micloudsdk.os.MiCloudSdkVersion");
        if (clsLoadClass != null) {
            return ReflectUtils.getStaticFieldIntValue(clsLoadClass, "version");
        }
        if (ReflectUtils.loadClass("miui.cloud.helper.BroadcastIntentHelper") != null) {
            return 25;
        }
        return ReflectUtils.loadClass("com.xiaomi.micloudsdk.utils.MiCloudRuntimeConstants") != null ? 18 : -1;
    }
}
