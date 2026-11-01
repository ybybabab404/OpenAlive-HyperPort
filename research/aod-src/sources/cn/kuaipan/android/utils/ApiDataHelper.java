package cn.kuaipan.android.utils;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ApiDataHelper {

    public interface IKscData {

        public interface Parser<T extends IKscData> {
        }
    }

    public static Number asNumber(Object obj, Number number) {
        if (obj == null) {
            return number;
        }
        if (obj instanceof Number) {
            return (Number) obj;
        }
        String string = obj.toString();
        try {
            return Long.valueOf(Long.parseLong(string));
        } catch (NumberFormatException unused) {
            return Double.valueOf(Double.parseDouble(string));
        }
    }

    public static String asString(Map map, String str) {
        if (map == null) {
            throw new IllegalArgumentException("DataMap can't be null when parse.");
        }
        Object obj = map.get(str);
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }
}
