package miui.cloud;

import java.util.HashMap;

/* JADX INFO: loaded from: classes2.dex */
public class AuthoritiesModel {
    public static final HashMap AUTHORITY_TO_MIUI_APP_PKG_MAP;
    public static final HashMap AUTHORITY_TO_PKG_MAP;
    public static final IFilter UNAVAILABLE_AUTHORITIES_FILTER;

    public interface IFilter {
    }

    public interface ISorter {
    }

    public static class UnAvailableAuthoritiesFilter implements IFilter {
        public UnAvailableAuthoritiesFilter() {
        }
    }

    static {
        HashMap map = new HashMap();
        AUTHORITY_TO_MIUI_APP_PKG_MAP = map;
        HashMap map2 = new HashMap();
        AUTHORITY_TO_PKG_MAP = map2;
        map.put("sms", "com.android.mms");
        map.put("com.android.contacts", "com.android.contacts");
        map.put("call_log", "com.android.contacts");
        map2.put("com.android.calendar", new String[]{"com.android.calendar", "com.xiaomi.calendar"});
        UNAVAILABLE_AUTHORITIES_FILTER = new UnAvailableAuthoritiesFilter();
    }
}
