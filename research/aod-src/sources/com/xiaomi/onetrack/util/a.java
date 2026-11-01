package com.xiaomi.onetrack.util;

import android.text.TextUtils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class a {
    public static final String a = "onetrack_active";
    public static final String b = "onetrack_bug_report";
    public static final String c = "";
    public static final String d = "region";
    public static final String e = "area";
    public static final String f = "EU";
    public static final String g = "pub_gzipencrypt";
    public static final String h = "pub_sid";
    public static final String i = "true";
    public static final String j = "miui-analytics";
    public static final String k = "onetrack_active";
    public static final String l = "6417a1813b3388817cd5b7c34303539534286cb3";
    public static final String m = "001";
    public static final Set<String> n = new HashSet(Arrays.asList("AT", "BE", "BG", "HR", "CY", "CZ", "DK", "EE", "FI", "FR", "DE", "EL", "GR", "HU", "IS", "IE", "IT", "LV", "LI", "LT", "LU", "MT", "NL", "NO", "PL", "PT", "RO", "SK", "SI", "ES", "SE", "UK", "GB"));
    public static final Set<String> o = new HashSet(Arrays.asList("CN", "RU", "CU", "IR", "KP", "SD", "SY", "TR"));
    private static final String p = "Constants";
    private static final String q = "ot";
    private static final String r = "_";

    public static String a() {
        try {
            String strL = t.l();
            JSONObject jSONObject = new JSONObject();
            if (!TextUtils.isEmpty(strL)) {
                jSONObject.put("region", strL);
                if (n.contains(strL.toUpperCase())) {
                    jSONObject.put(e, f);
                }
            }
            return jSONObject.toString();
        } catch (Exception e2) {
            s.a(p, "getDefaultAttributes exception:" + e2.getMessage());
            return "";
        }
    }

    public static String a(String str) {
        if (s.a) {
            s.a(p, "getDefaultTopic：ot_" + str + r + t.o());
        }
        return u.b + str + r + t.o();
    }

    public static Map<String, String> b() {
        HashMap map = new HashMap();
        try {
            String strL = t.l();
            if (!TextUtils.isEmpty(strL)) {
                map.put("region", strL);
                if (n.contains(strL.toUpperCase())) {
                    map.put(e, f);
                    return map;
                }
            }
        } catch (Exception e2) {
            s.a(p, "getDefaultAttributes exception:" + e2.getMessage());
        }
        return map;
    }
}
