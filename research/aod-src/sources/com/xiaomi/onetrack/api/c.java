package com.xiaomi.onetrack.api;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OnMainThreadException;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.ServiceQualityEvent;
import com.xiaomi.onetrack.util.DeviceUtil;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public abstract class c {
    protected static ExecutorService b = null;
    public static volatile boolean h = false;
    private static final String i = "BaseOneTrackImp";
    protected ap a;
    protected Context c;
    protected aq d;
    protected Configuration e;
    protected OneTrack.ICommonPropertyProvider f;
    protected com.xiaomi.onetrack.util.y g;
    private Handler j;

    public c(Context context, Configuration configuration) {
        Context applicationContext = context.getApplicationContext();
        this.c = applicationContext;
        this.e = configuration;
        e(applicationContext);
        Log.d(i, "OneTrackImp init : " + configuration.toString());
        Log.d(i, "OneTrackImp sdk ver : 3.1.7");
        com.xiaomi.onetrack.util.s.a(i, "BuildConfig.Channel:global");
    }

    private String a(long j, String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(am.ab, str);
        jSONObject.put(am.aa, j);
        return jSONObject.toString();
    }

    public static void a(ap apVar, Configuration configuration, com.xiaomi.onetrack.util.y yVar, String str, String str2, String str3) {
        try {
            if (!com.xiaomi.onetrack.h.a.a.a().b(configuration.getAppId(), str)) {
                com.xiaomi.onetrack.util.s.a(i, "trackFailedEvent, should not record");
            } else {
                apVar.a(am.g, an.a(str, str2, str3, configuration, yVar));
                com.xiaomi.onetrack.h.a.a.a().c(configuration.getAppId(), str);
            }
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b(i, "trackFailedEvent error: " + e.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, long j) {
        b.execute(new r(this, str, j));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JSONObject d(Map<String, Object> map) throws Throwable {
        try {
            JSONObject jSONObjectA = com.xiaomi.onetrack.util.u.a(map, false);
            String strA = com.xiaomi.onetrack.util.n.a(com.xiaomi.onetrack.util.u.a(this.e));
            return com.xiaomi.onetrack.util.u.a(jSONObjectA, !TextUtils.isEmpty(strA) ? new JSONObject(strA) : null);
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b(i, "getCommonPropertyForException failed, e: " + e.getMessage());
            return null;
        }
    }

    private void e(Context context) {
        com.xiaomi.onetrack.util.t.a(this.e.isInternational(), this.e.getRegion(), this.e.getMode());
        OneTrack.Mode mode = this.e.getMode();
        OneTrack.Mode mode2 = OneTrack.Mode.APP;
        if (mode == mode2) {
            com.xiaomi.onetrack.util.t.a(this.e.isOverrideMiuiRegionSetting());
        }
        if (b == null) {
            b = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new d(this));
        }
        this.g = new com.xiaomi.onetrack.util.y(this.e);
        a(context);
        m();
        if (this.e.getMode() == mode2) {
            b(context);
            if (this.e.isExceptionCatcherEnable()) {
                com.xiaomi.onetrack.util.j.a(new o(this, context));
            }
        }
        b.execute(new z(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e(boolean z) {
        b.execute(new s(this, z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean e(String str) {
        boolean zA = com.xiaomi.onetrack.util.u.a(str);
        if (!zA) {
            com.xiaomi.onetrack.util.s.b(i, String.format("Invalid eventname: %s. Eventname can only consist of numbers, letters, underscores ,and can not start with a number or \"onetrack_\" or \"ot_\"", str));
        }
        return !zA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean f(String str) {
        if ("onetrack_dau".equals(str) || am.ai.equals(str) || "ot_login".equals(str) || "ot_logout".equals(str)) {
            return false;
        }
        boolean zA = com.xiaomi.onetrack.util.u.a(str);
        if (!zA) {
            com.xiaomi.onetrack.util.s.b(i, String.format("Invalid eventname: %s. Eventname can only consist of numbers, letters, underscores ,and can not start with a number or \"onetrack_\" or \"ot_\"", str));
        }
        return !zA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JSONObject g(String str) throws Throwable {
        try {
            OneTrack.ICommonPropertyProvider iCommonPropertyProvider = this.f;
            JSONObject jSONObjectA = com.xiaomi.onetrack.util.u.a(iCommonPropertyProvider != null ? iCommonPropertyProvider.getDynamicProperty(str) : null, false);
            String strA = com.xiaomi.onetrack.util.n.a(com.xiaomi.onetrack.util.u.a(this.e));
            return com.xiaomi.onetrack.util.u.a(jSONObjectA, !TextUtils.isEmpty(strA) ? new JSONObject(strA) : null);
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b(i, "getCommonProperty failed, e: " + e.getMessage());
            return null;
        }
    }

    private void m() {
        Handler handler = new Handler(Looper.getMainLooper());
        this.j = handler;
        if (handler.getLooper() != null) {
            com.xiaomi.onetrack.util.j.a(new ac(this));
            this.j.postDelayed(new ad(this), ((long) (Math.random() * 1000.0d * 20.0d)) + 10000);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void n() {
        com.xiaomi.onetrack.util.s.a(i, "triggerOTMonitorBusiness");
        com.xiaomi.onetrack.util.j.a(new ae(this));
        Handler handler = this.j;
        if (handler == null || handler.getLooper() == null) {
            return;
        }
        this.j.postDelayed(new af(this), 1800000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void o() {
        b.execute(new p(this));
    }

    private void p() {
        b.execute(new y(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void q() {
        if (com.xiaomi.onetrack.c.j.d()) {
            b.execute(new aa(this));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void r() {
        if (i()) {
            return;
        }
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            long jB = com.xiaomi.onetrack.util.ad.B();
            if (jCurrentTimeMillis - jB > 0 && com.xiaomi.onetrack.util.af.a(jB)) {
                com.xiaomi.onetrack.util.s.a(i, "trackMonitorEvent has been tracked today");
                return;
            }
            int i2 = 0;
            do {
                com.xiaomi.onetrack.h.c.a aVarB = com.xiaomi.onetrack.h.b.b.a().b();
                if (aVarB != null) {
                    j().a(am.f, an.a(aVarB, this.e, this.g));
                    com.xiaomi.onetrack.h.b.b.a().a(aVarB.b(), aVarB.a());
                }
                i2++;
                if (aVarB == null) {
                    break;
                }
            } while (i2 <= 100);
            com.xiaomi.onetrack.util.ad.j(System.currentTimeMillis());
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b(i, "trackMonitorEvent error: " + e.toString());
        }
    }

    private static boolean s() {
        try {
            int i2 = com.xiaomi.onetrack.f.a.b().getPackageManager().getPackageInfo(be.a, 0).versionCode;
            if (i2 >= 2020062900) {
                return true;
            }
            com.xiaomi.onetrack.util.s.a(i, "system analytics version: " + i2);
        } catch (Throwable th) {
            com.xiaomi.onetrack.util.s.b(i, "isSupportEmptyEvent error:" + th.getMessage());
        }
        return false;
    }

    private boolean t() {
        try {
            if (TextUtils.isEmpty(this.e.getAdEventAppId()) || OneTrack.isUseSystemNetTrafficOnly()) {
                return true;
            }
            int i2 = com.xiaomi.onetrack.f.a.b().getPackageManager().getPackageInfo(be.a, 0).versionCode;
            com.xiaomi.onetrack.util.s.a(i, "system analytics version: " + i2);
            return i2 >= 2022042900;
        } catch (Throwable th) {
            com.xiaomi.onetrack.util.s.b(i, "isSupportAdMonitor error:" + th.getMessage());
            return true;
        }
    }

    public String a(Intent intent) throws OnMainThreadException {
        if (com.xiaomi.onetrack.util.t.k()) {
            com.xiaomi.onetrack.util.s.a(i, "this appActiveBroadcast method is not supported in the international version");
            return "";
        }
        if (com.xiaomi.onetrack.util.z.a()) {
            throw new OnMainThreadException("Can't call this method on main thread");
        }
        if (intent != null && a.a().c()) {
            return a.a().a(intent);
        }
        com.xiaomi.onetrack.util.s.b(i, "Not allowed to call,intent is null or Not specify the package name");
        return "";
    }

    public abstract void a(Context context);

    public void a(OneTrack.ICommonPropertyProvider iCommonPropertyProvider) {
        this.f = iCommonPropertyProvider;
    }

    public void a(OneTrack.IEventHook iEventHook) {
        this.g.a(iEventHook);
    }

    public void a(ServiceQualityEvent serviceQualityEvent) {
        if (serviceQualityEvent == null) {
            return;
        }
        b.execute(new x(this, serviceQualityEvent));
    }

    public void a(String str) {
        b.execute(new f(this, str));
    }

    public void a(String str, OneTrack.UserIdType userIdType, Map<String, Object> map, boolean z) {
        b.execute(new j(this, str, userIdType, z, map));
    }

    public void a(String str, Number number) {
        b.execute(new l(this, str, number));
    }

    public void a(String str, Object obj) {
        b.execute(new i(this, obj, str));
    }

    public void a(String str, String str2, String str3, String str4, String str5, long j, Map<String, Object> map, boolean z) {
        b.execute(new g(this, z, str3, map, str, str2, str5, str4, j));
    }

    public void a(String str, String str2, Map<String, Object> map) {
        b.execute(new ag(this, str2, map, str));
    }

    public void a(String str, Map<String, Object> map) {
        b.execute(new ah(this, str, map));
    }

    public void a(String str, Map<String, Object> map, List<String> list) {
        b.execute(new e(this, str, map, list));
    }

    public void a(String str, boolean z) {
        b.execute(new q(this, str, z));
    }

    public void a(Map<String, Object> map) {
        b.execute(new h(this, map));
    }

    public void a(Map<String, Object> map, boolean z) {
        b.execute(new m(this, z, map));
    }

    public void a(boolean z) {
        com.xiaomi.onetrack.util.s.a = z;
    }

    public abstract boolean a();

    public void b(Context context) {
        ((Application) context).registerActivityLifecycleCallbacks(new n(this));
    }

    public void b(String str) {
        b.execute(new v(this, str));
    }

    public void b(Map<String, ? extends Number> map) {
        b.execute(new k(this, map));
    }

    public void b(boolean z) {
        if (this.e.isUseCustomPrivacyPolicy()) {
            b.execute(new ab(this, z));
        }
    }

    public boolean b() {
        if (h) {
            return false;
        }
        if (com.xiaomi.onetrack.util.t.m()) {
            return TextUtils.equals(com.xiaomi.onetrack.util.t.n(), com.xiaomi.onetrack.util.t.l());
        }
        if (this.e.isOverrideMiuiRegionSetting()) {
            return TextUtils.equals(com.xiaomi.onetrack.util.t.n(), this.e.getRegion());
        }
        return true;
    }

    public String c(Context context) throws OnMainThreadException {
        if (com.xiaomi.onetrack.util.z.a()) {
            throw new OnMainThreadException("Can't call this method on main thread");
        }
        return DeviceUtil.e(context);
    }

    public void c() {
        b.execute(new u(this));
    }

    public void c(String str) {
        if (!TextUtils.isEmpty(str)) {
            str = str.toUpperCase();
        }
        com.xiaomi.onetrack.util.t.a(this.e.isInternational(), str, this.e.getMode());
        com.xiaomi.onetrack.b.a.a().d(str);
    }

    public void c(Map<String, Object> map) {
        if (map == null) {
            return;
        }
        b.execute(new t(this, map));
    }

    public void c(boolean z) {
        com.xiaomi.onetrack.util.y yVar = this.g;
        if (yVar != null) {
            yVar.b(z);
        }
    }

    public OneTrack.ICommonPropertyProvider d() {
        return this.f;
    }

    public String d(Context context) throws OnMainThreadException {
        if (com.xiaomi.onetrack.util.z.a()) {
            throw new OnMainThreadException("Can't call this method on main thread");
        }
        return DeviceUtil.f(context);
    }

    public void d(String str) {
        b.execute(new w(this, str));
    }

    public void d(boolean z) {
        com.xiaomi.onetrack.util.oaid.a.a().a(z);
    }

    public String e() throws OnMainThreadException {
        if (com.xiaomi.onetrack.util.z.a()) {
            throw new OnMainThreadException("Can't call this method on main thread");
        }
        return com.xiaomi.onetrack.util.r.a().b();
    }

    public void f() {
        DeviceUtil.e();
    }

    public boolean g() {
        return DeviceUtil.f();
    }

    public void h() {
        try {
            if (!i() && this.e.getMode() == OneTrack.Mode.APP) {
                long jD = com.xiaomi.onetrack.f.a.d();
                String strA = a(jD, com.xiaomi.onetrack.f.a.c());
                String strV = com.xiaomi.onetrack.util.ad.v();
                if (TextUtils.isEmpty(strV)) {
                    com.xiaomi.onetrack.util.ad.i(strA);
                    return;
                }
                JSONObject jSONObject = new JSONObject(strV);
                long jOptLong = jSONObject.optLong(am.aa);
                String strOptString = jSONObject.optString(am.ab);
                if (jOptLong != jD) {
                    com.xiaomi.onetrack.h.d.b.a(this.e.getAppId(), am.m, com.xiaomi.onetrack.h.d.a.e, "", 1);
                    com.xiaomi.onetrack.util.ad.i(strA);
                    j().a(am.m, an.a(jOptLong, strOptString, jD, com.xiaomi.onetrack.f.a.f(), this.e, this.g));
                }
            }
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b(i, "trackUpgradeEvent error: " + e.toString());
            if (e instanceof JSONException) {
                com.xiaomi.onetrack.h.d.b.a(this.e.getAppId(), am.m, com.xiaomi.onetrack.h.d.a.g, "", 1);
                a(j(), this.e, this.g, am.m, com.xiaomi.onetrack.h.d.a.g, "");
            }
        }
    }

    public boolean i() {
        if (!OneTrack.isDisable()) {
            return false;
        }
        com.xiaomi.onetrack.util.s.a(i, "isDisable is true,Not allowed Track");
        return true;
    }

    public ap j() {
        return this.a;
    }

    public boolean k() {
        if (com.xiaomi.onetrack.util.s.a) {
            com.xiaomi.onetrack.util.s.a(i, "enable:" + l() + " isSupportEmptyEvent: " + s() + "_isSupportAdMonitor():" + t());
        }
        return l() && s() && t();
    }

    public boolean l() {
        try {
            int componentEnabledSetting = com.xiaomi.onetrack.f.a.b().getPackageManager().getComponentEnabledSetting(new ComponentName(be.a, be.b));
            return componentEnabledSetting == 1 || componentEnabledSetting == 0;
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b(i, "enable error:" + e.toString());
            return false;
        }
    }
}
