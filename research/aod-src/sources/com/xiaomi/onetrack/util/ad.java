package com.xiaomi.onetrack.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes2.dex */
public class ad {
    private static final String A = "app_config_region";
    private static final String B = "loc_token";
    private static final String C = "loc_config";
    private static final String D = "sdk_token_init_time";
    private static final String E = "get_remote_monitor_config_ticket_";
    private static final String F = "monitor_config_data";
    private static final String G = "last_track_ot_monitor_event_time";
    private static final String H = "report_ot_failed_ticket_";
    private static final String I = "check_request_token";
    private static final String J = "next_update_caught_exception_conf_time";
    private static final String K = "net_ad_monitor_config_data";
    private static final String L = "ad_m_caught_exception_day_count";
    private static final String M = "last_rebind_time";
    private static final String a = "onetrack";
    private static final String b = "one_track_pref";
    private static SharedPreferences c = null;
    private static SharedPreferences.Editor d = null;
    private static final String e = "last_upload_active_time";
    private static final String f = "last_collect_crash_time";
    private static final String g = "report_crash_ticket";
    private static final String h = "last_secret_key_time";
    private static final String i = "next_update_common_conf_time";
    private static final String j = "common_cloud_data";
    private static final String k = "common_config_hash";
    private static final String l = "secret_key_data";
    private static final String m = "region_rul";
    private static final String n = "pref_instance_id";
    private static final String o = "pref_instance_id_last_use_time";
    private static final String p = "network_access_enabled";
    private static final String q = "anonymous_enabled";
    private static final String r = "onetrack_first_open";
    private static final String s = "dau_last_time";
    private static final String t = "onetrack_user_id";
    private static final String u = "onetrack_user_type";
    private static final String v = "custom_id";
    private static final String w = "page_end";
    private static final String x = "last_app_version";
    private static final String y = "first_launch_time";
    private static final String z = "pref_custom_privacy_policy_";

    public static long A() {
        return b(D, 0L);
    }

    public static long B() {
        return b(G, 0L);
    }

    public static String C() {
        return c(I, "");
    }

    public static long D() {
        return b(J, 0L);
    }

    public static String E() {
        return c(K, "");
    }

    public static long F() {
        return b(L, 0L);
    }

    public static long G() {
        return b(M, 0L);
    }

    private static void H() {
        if (d != null) {
            return;
        }
        synchronized (ad.class) {
            try {
                if (d == null) {
                    SharedPreferences sharedPreferences = com.xiaomi.onetrack.f.a.a().getSharedPreferences(b, 0);
                    c = sharedPreferences;
                    d = sharedPreferences.edit();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static long a() {
        return b(e, 0L);
    }

    public static String a(Context context) {
        return c(v, "");
    }

    public static void a(long j2) {
        c(e, j2);
    }

    public static void a(Context context, String str) {
        d(v, str);
    }

    public static void a(String str) {
        d(l, str);
    }

    private static void a(String str, float f2) {
        H();
        d.putFloat(str, f2).apply();
    }

    public static void a(String str, long j2) {
        c(E + str, j2);
    }

    public static void a(String str, String str2) {
        d(F + str, str2);
    }

    public static void a(String str, boolean z2) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        c(z + str, z2);
    }

    public static void a(boolean z2) {
        c(p, z2);
    }

    private static float b(String str, float f2) {
        H();
        return c.getFloat(str, f2);
    }

    public static long b() {
        return b(f, 0L);
    }

    private static long b(String str, long j2) {
        H();
        return c.getLong(str, j2);
    }

    public static void b(long j2) {
        c(f, j2);
    }

    public static void b(String str) {
        d(m, str);
    }

    public static void b(String str, String str2) {
        d(H + str, str2);
    }

    public static void b(boolean z2) {
        c(q, z2);
    }

    private static boolean b(String str, boolean z2) {
        H();
        return c.getBoolean(str, z2);
    }

    public static long c() {
        return b(g, 0L);
    }

    private static String c(String str, String str2) {
        H();
        return c.getString(str, str2);
    }

    public static void c(long j2) {
        c(g, j2);
    }

    public static void c(String str) {
        d(k, str);
    }

    private static void c(String str, long j2) {
        H();
        d.putLong(str, j2).apply();
    }

    private static void c(String str, boolean z2) {
        H();
        d.putBoolean(str, z2).apply();
    }

    public static void c(boolean z2) {
        c(r, z2);
    }

    public static String d() {
        return c(l, "");
    }

    public static void d(long j2) {
        c(h, j2);
    }

    public static void d(String str) {
        d(j, str);
    }

    private static void d(String str, String str2) {
        H();
        d.putString(str, str2).apply();
    }

    public static String e() {
        return c(m, "");
    }

    public static void e(long j2) {
        c(i, j2);
    }

    public static void e(String str) {
        d(n, str);
        f(af.a());
    }

    public static long f() {
        return b(h, 0L);
    }

    public static void f(long j2) {
        c(o, j2);
    }

    public static void f(String str) {
        d(t, str);
    }

    public static long g() {
        return b(i, 0L);
    }

    public static void g(long j2) {
        c(s, j2);
    }

    public static void g(String str) {
        d(u, str);
    }

    public static String h() {
        return c(k, "");
    }

    public static void h(long j2) {
        c(y, j2);
    }

    public static void h(String str) {
        d(w, str);
    }

    public static String i() {
        return c(j, "");
    }

    public static void i(long j2) {
        c(D, j2);
    }

    public static void i(String str) {
        d(x, str);
    }

    public static String j() {
        return c(n, "");
    }

    public static void j(long j2) {
        c(G, j2);
    }

    public static boolean j(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        return b(z + str, true);
    }

    public static long k() {
        return b(o, 0L);
    }

    public static void k(long j2) {
        c(J, j2);
    }

    public static void k(String str) {
        d(A, str);
    }

    public static void l(long j2) {
        H();
        c(L, j2);
    }

    public static void l(String str) {
        d(B, str);
    }

    public static boolean l() {
        return b(p, true);
    }

    public static void m(long j2) {
        c(M, j2);
    }

    public static void m(String str) {
        d(C, str);
    }

    public static boolean m() {
        return b(q, false);
    }

    public static long n(String str) {
        return b(E + str, 0L);
    }

    public static boolean n() {
        return b(r, true);
    }

    public static long o() {
        return b(s, 0L);
    }

    public static String o(String str) {
        return c(F + str, "");
    }

    public static String p() {
        return c(t, "");
    }

    public static String p(String str) {
        return c(H + str, "");
    }

    public static void q() {
        t(t);
    }

    public static void q(String str) {
        t(H + str);
    }

    public static String r() {
        return c(u, "");
    }

    public static void r(String str) {
        d(I, str);
    }

    public static void s() {
        t(u);
    }

    public static void s(String str) {
        d(K, str);
    }

    public static long t() {
        return b(p, 0L);
    }

    private static void t(String str) {
        H();
        d.remove(str).apply();
    }

    public static String u() {
        return c(w, "");
    }

    public static String v() {
        return c(x, "");
    }

    public static long w() {
        return b(y, 0L);
    }

    public static String x() {
        return c(A, "");
    }

    public static String y() {
        return c(B, "");
    }

    public static String z() {
        return c(C, "");
    }
}
