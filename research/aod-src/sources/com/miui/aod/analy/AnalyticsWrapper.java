package com.miui.aod.analy;

import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;
import com.miui.aod.utils.ReflectUtil;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import java.util.Map;
import miui.os.Build;

/* JADX INFO: loaded from: classes.dex */
public class AnalyticsWrapper {
    public static boolean mAnalyticEnable;
    public static OneTrack mOneTrack;

    public static OneTrack buildOneTrack(Context context, boolean z, OneTrack.Mode mode) {
        boolean z2 = !Build.IS_INTERNATIONAL_BUILD && isUserExperienceProgramEnabled(context);
        mAnalyticEnable = z2;
        if (!z2 || context == null) {
            return null;
        }
        OneTrack oneTrackCreateInstance = OneTrack.createInstance(context, new Configuration.Builder().setAppId("31000000235").setChannel(context.getPackageName()).setMode(mode).setExceptionCatcherEnable(true).build());
        OneTrack.setUseSystemNetTrafficOnly();
        OneTrack.setDebugMode(z);
        return oneTrackCreateInstance;
    }

    public static void init(Context context, boolean z) {
        mOneTrack = buildOneTrack(context, z, OneTrack.Mode.APP);
    }

    public static boolean isUserExperienceProgramEnabled(Context context) {
        try {
            return ((Boolean) ReflectUtil.callStaticObjectMethod(Class.forName("android.provider.MiuiSettings$Secure"), "isUserExperienceProgramEnable", new Class[]{ContentResolver.class}, context.getContentResolver())).booleanValue();
        } catch (Exception e) {
            Log.i("AnalyticsWrapper", e.toString());
            return false;
        }
    }

    public static void trackEvent(String str, Map map) {
        OneTrack oneTrack;
        if (!mAnalyticEnable || (oneTrack = mOneTrack) == null) {
            return;
        }
        oneTrack.track(str, map);
    }
}
