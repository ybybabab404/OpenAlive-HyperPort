package miuix.core.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes2.dex */
public final class HyperFeatureResolveHelper {
    public static Class FEATURE_CLASS;
    public static final Map METHOD_CACHE = new ConcurrentHashMap();

    static {
        try {
            FEATURE_CLASS = Class.forName("miui.os.HyperOSCustFeatureResolve");
        } catch (ClassNotFoundException unused) {
            FEATURE_CLASS = null;
        }
    }
}
