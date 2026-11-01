package com.xiaomi.onetrack;

import android.content.Context;
import android.content.Intent;
import com.xiaomi.onetrack.api.am;
import com.xiaomi.onetrack.api.as;
import com.xiaomi.onetrack.f.a;
import com.xiaomi.onetrack.util.ac;
import com.xiaomi.onetrack.util.j;
import com.xiaomi.onetrack.util.s;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class OneTrack {
    private static final String a = "OneTrack";
    private static boolean b = false;
    private static volatile int c = 0;
    private static volatile boolean d = false;
    private static boolean e;
    private static boolean f;
    private static HttpRequestProperty h;
    private as g;

    public enum HttpRequestProperty {
        KEEP_ALIVE("keep-alive"),
        CLOSE("close");

        private String a;

        HttpRequestProperty(String str) {
            this.a = str;
        }

        public String getType() {
            return this.a;
        }
    }

    public interface ICommonPropertyProvider {
        Map<String, Object> getDynamicProperty(String str);
    }

    public interface IEventHook {
        boolean isCustomDauEvent(String str);

        boolean isRecommendEvent(String str);

        boolean isTrackIPRegionEvent(String str);
    }

    public enum Mode {
        APP("app"),
        PLUGIN("plugin"),
        SDK("sdk");

        private String a;

        Mode(String str) {
            this.a = str;
        }

        public String getType() {
            return this.a;
        }
    }

    public enum NetType {
        NOT_CONNECTED("NONE"),
        MOBILE_2G("2G"),
        MOBILE_3G("3G"),
        MOBILE_4G("4G"),
        MOBILE_5G("5G"),
        WIFI("WIFI"),
        ETHERNET("ETHERNET"),
        UNKNOWN("UNKNOWN"),
        CONNECTED("CONNECTED");

        private String a;

        NetType(String str) {
            this.a = str;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.a;
        }
    }

    public enum UserIdType {
        XIAOMI("xiaomi"),
        PHONE_NUMBER("phone_number"),
        WEIXIN("weixin"),
        WEIBO("weibo"),
        QQ("qq"),
        OTHER("other");

        private String a;

        UserIdType(String str) {
            this.a = str;
        }

        public String getUserIdType() {
            return this.a;
        }
    }

    private OneTrack(Context context, Configuration configuration) {
        a.a(context.getApplicationContext());
        this.g = new as(context, configuration);
        setEventHook(new DefaultEventHook());
    }

    private static void a(Context context) {
        if (context == null) {
            throw new IllegalStateException("context is null!");
        }
        a.a(context.getApplicationContext());
    }

    public static OneTrack createInstance(Context context, Configuration configuration) {
        return new OneTrack(context, configuration);
    }

    public static boolean getForceDeviceProvisioned() {
        return d;
    }

    public static int getGlobalBasicModeEnable() {
        return c;
    }

    public static HttpRequestProperty getHttpReqPropConnection() {
        return h;
    }

    public static boolean isDisable() {
        return b;
    }

    public static boolean isRestrictGetNetworkInfo() {
        return f;
    }

    public static boolean isUseSystemNetTrafficOnly() {
        return e;
    }

    public static void registerCrashHook(Context context) {
        CrashAnalysis.a(context);
    }

    public static String sdkVersion() {
        return BuildConfig.SDK_VERSION;
    }

    public static void setAccessNetworkEnable(Context context, final boolean z) {
        a(context);
        j.a(new Runnable() { // from class: com.xiaomi.onetrack.OneTrack.1
            @Override // java.lang.Runnable
            public void run() {
                com.xiaomi.onetrack.c.j.a(z);
                com.xiaomi.onetrack.c.j.b(z);
            }
        });
    }

    public static void setDebugMode(boolean z) {
        s.a(z);
    }

    public static void setDisable(boolean z) {
        b = z;
    }

    public static void setForceDeviceProvisioned() {
        d = true;
    }

    public static void setGlobalBasicModeEnable(boolean z) {
        if (z) {
            c = 2;
        } else {
            c = 1;
        }
    }

    public static void setHttpReqPropConnection(HttpRequestProperty httpRequestProperty) {
        h = httpRequestProperty;
    }

    public static void setRestrictGetNetworkInfo(boolean z) {
        f = z;
    }

    public static void setTestMode(boolean z) {
        s.b(z);
    }

    public static void setUseSystemNetTrafficOnly() {
        e = true;
    }

    public void adTrack(String str, Map<String, Object> map) {
        this.g.a(str, map, (List<String>) null);
    }

    public void adTrack(String str, Map<String, Object> map, List<String> list) {
        this.g.a(str, map, list);
    }

    public String appActiveBroadcast(Intent intent) throws OnMainThreadException {
        return this.g.a(intent);
    }

    public void clearCommonProperty() {
        this.g.c();
    }

    public String getAndroidId(Context context) throws OnMainThreadException {
        return this.g.d(context);
    }

    public String getInstanceId() throws OnMainThreadException {
        return this.g.e();
    }

    public String getOAID(Context context) throws OnMainThreadException {
        return this.g.c(context);
    }

    public boolean hasMemoryOaid() {
        return this.g.g();
    }

    public void login(String str, UserIdType userIdType, Map<String, Object> map) {
        login(str, userIdType, map, false);
    }

    public void login(String str, UserIdType userIdType, Map<String, Object> map, boolean z) {
        this.g.a(str, userIdType, map, z);
    }

    public void logout() {
        logout(null, false);
    }

    public void logout(Map<String, Object> map, boolean z) {
        this.g.a(map, z);
    }

    public void refreshGetOaidTime() {
        this.g.f();
    }

    public void removeCommonProperty(String str) {
        this.g.b(str);
    }

    public void setBasicModeEnable(boolean z) {
        this.g.c(z);
    }

    public void setCloseOaidDependMsaSDK(boolean z) {
        this.g.d(z);
    }

    public void setCommonProperty(Map<String, Object> map) {
        this.g.c(map);
    }

    public void setCustomPrivacyPolicyAccepted(boolean z) {
        this.g.b(z);
    }

    public void setDynamicCommonProperty(ICommonPropertyProvider iCommonPropertyProvider) {
        this.g.a(iCommonPropertyProvider);
    }

    public void setEventHook(IEventHook iEventHook) {
        this.g.a(iEventHook);
    }

    public void setInstanceId(String str) {
        this.g.d(str);
    }

    public void setRegion(String str) {
        this.g.c(str);
    }

    public void setUserProfile(String str, Object obj) {
        this.g.a(str, obj);
    }

    public void setUserProfile(Map<String, Object> map) {
        this.g.a(map);
    }

    public void track(String str, List<String> list, Map<String, Object> map) {
        HashMap map2 = new HashMap();
        if (map != null) {
            map2.putAll(map);
        }
        map2.put(am.ag, String.join(ac.b, list));
        this.g.a(str, (Map<String, Object>) map2);
    }

    public void track(String str, Map<String, Object> map) {
        this.g.a(str, map);
    }

    public void trackEventFromH5(String str) {
        this.g.a(str);
    }

    public void trackException(String str, String str2, String str3, String str4, String str5, long j) {
        this.g.a(str, str2, str3, str4, str5, j, null, true);
    }

    public void trackPluginEvent(String str, String str2, Map<String, Object> map) {
        this.g.a(str, str2, map);
    }

    public void trackServiceQualityEvent(ServiceQualityEvent serviceQualityEvent) {
        this.g.a(serviceQualityEvent);
    }

    public void userProfileIncrement(String str, Number number) {
        this.g.a(str, number);
    }

    public void userProfileIncrement(Map<String, ? extends Number> map) {
        this.g.b(map);
    }
}
