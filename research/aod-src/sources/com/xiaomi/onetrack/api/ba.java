package com.xiaomi.onetrack.api;

import android.text.TextUtils;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.util.DeviceUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class ba implements ap {
    private static final String a = "PubSubTrackLocalImp";
    private static final int b = 102400;
    private static final int c = 512000;
    private static final int d = 2;
    private Configuration e;
    private com.xiaomi.onetrack.util.y f;

    public ba(Configuration configuration, com.xiaomi.onetrack.util.y yVar) {
        this.e = configuration;
        this.f = yVar;
        com.xiaomi.onetrack.util.j.a(new bb(this, configuration));
    }

    private void a(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) {
            return;
        }
        com.xiaomi.onetrack.util.s.a(a, String.format(str3, str, str2));
    }

    private void a(String str, JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, boolean z) {
        String appId = this.e.getAppId();
        if (z) {
            appId = this.e.getAdEventAppId();
        }
        String str2 = appId;
        if (a(str2, str, jSONObject2, jSONObject3)) {
            String projectId = this.e.getProjectId();
            String strA = com.xiaomi.onetrack.util.a.a(str2);
            Map<String, String> mapB = com.xiaomi.onetrack.util.a.b();
            if (z) {
                com.xiaomi.onetrack.f.d.b(projectId, strA, str2, com.xiaomi.onetrack.f.a.e(), str, jSONObject.toString(), mapB);
            } else {
                com.xiaomi.onetrack.f.d.a(projectId, strA, str2, com.xiaomi.onetrack.f.a.e(), str, jSONObject.toString(), mapB);
            }
        }
    }

    private void a(JSONObject jSONObject) {
        try {
            jSONObject.put("ot_sender", com.xiaomi.onetrack.f.a.e());
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b(a, "fillSender error：" + e.getMessage());
        }
    }

    private void a(JSONObject jSONObject, Set<String> set) {
        if (jSONObject == null || set == null || set.size() == 0) {
            com.xiaomi.onetrack.util.s.a(a, "jsonObject is null or bannedParams is empty");
            return;
        }
        com.xiaomi.onetrack.util.s.a(a, "jsonObject: " + jSONObject.toString() + ", bannedParams: " + set.toString());
        try {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                if (set.contains(itKeys.next())) {
                    itKeys.remove();
                }
            }
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b(a, "filterParams error：" + e.toString());
        }
    }

    private boolean a(String str, String str2, JSONObject jSONObject, JSONObject jSONObject2) {
        String strOptString;
        boolean z;
        String strA;
        boolean z2;
        JSONArray jSONArrayA;
        boolean z3 = true;
        if (com.xiaomi.onetrack.b.o.b(str)) {
            com.xiaomi.onetrack.util.s.a(a, str + ",This appId and device in whiteList");
            b(jSONObject);
            return true;
        }
        if (com.xiaomi.onetrack.b.o.c(str)) {
            com.xiaomi.onetrack.util.s.a(a, "This app disabled tracking data, skip it.");
            return false;
        }
        if (!com.xiaomi.onetrack.b.o.a(str)) {
            com.xiaomi.onetrack.util.s.a(a, "This appId " + str + " is oldUploadByCloudControl");
            return b(str, str2, jSONObject, jSONObject2);
        }
        if (jSONObject2 == null) {
            strOptString = "";
        } else {
            try {
                strOptString = jSONObject2.optString(am.af);
            } catch (Exception e) {
                e = e;
                z = z3;
                com.xiaomi.onetrack.util.s.b(a, "shouldUploadByCloudControl error：" + e.getMessage());
                return z;
            }
        }
        JSONObject jSONObjectA = com.xiaomi.onetrack.b.j.a().a(str, str2);
        StringBuilder sb = new StringBuilder();
        sb.append("eventConfig: ");
        sb.append(jSONObjectA != null ? jSONObjectA.toString() : "");
        com.xiaomi.onetrack.util.s.a(a, sb.toString());
        if (!TextUtils.isEmpty(strOptString) && (jSONArrayA = com.xiaomi.onetrack.b.o.a(jSONObjectA, str2, com.xiaomi.onetrack.b.a.m, (JSONArray) null)) != null) {
            int i = 0;
            while (true) {
                if (i >= jSONArrayA.length()) {
                    z = z3;
                    strA = "";
                    z2 = false;
                    break;
                }
                JSONObject jSONObject3 = jSONArrayA.getJSONObject(i);
                String strA2 = com.xiaomi.onetrack.b.o.a(jSONObject3, str2, com.xiaomi.onetrack.b.a.n, "");
                z = z3;
                try {
                    a(strA2, strOptString, "needIds: %s, tip: %s");
                    if (b(strOptString, strA2)) {
                        if (!a(jSONObject3, str, str2)) {
                            strA = com.xiaomi.onetrack.b.o.a(jSONObject3, str2, com.xiaomi.onetrack.b.a.g, "");
                            z2 = z;
                            break;
                        }
                        return false;
                    }
                    i++;
                    z3 = z;
                } catch (Exception e2) {
                    e = e2;
                    com.xiaomi.onetrack.util.s.b(a, "shouldUploadByCloudControl error：" + e.getMessage());
                    return z;
                }
            }
        } else {
            z = z3;
            strA = "";
            z2 = false;
            break;
        }
        if (!z2 && jSONObjectA != null) {
            if (a(jSONObjectA, str, str2)) {
                return false;
            }
            strA = com.xiaomi.onetrack.b.o.a(jSONObjectA, str2, com.xiaomi.onetrack.b.a.g, "");
        }
        String strB = com.xiaomi.onetrack.b.j.a().b(str);
        com.xiaomi.onetrack.util.s.a(a, "bannedParamsForApp: " + strB + ", bannedParamsForEvent: " + strA);
        Set<String> setA = com.xiaomi.onetrack.util.ac.a(strB, strA, com.xiaomi.onetrack.util.ac.b);
        a(jSONObject, setA);
        a(jSONObject2, setA);
        a(jSONObject);
        b(jSONObject);
        return z;
    }

    private boolean a(JSONObject jSONObject, String str) {
        boolean z = true;
        try {
            String strA = com.xiaomi.onetrack.b.o.a(jSONObject, str, com.xiaomi.onetrack.b.a.k, "");
            if (TextUtils.isEmpty(strA)) {
                return true;
            }
            com.xiaomi.onetrack.util.s.a(a, "shouldUploadByCycle " + str + "_cycle: " + strA);
            String[] strArrSplit = strA.split(com.xiaomi.onetrack.util.ac.b);
            int iD = com.xiaomi.onetrack.util.af.d();
            int iC = com.xiaomi.onetrack.util.af.c();
            int length = strArrSplit.length;
            boolean z2 = true;
            int i = 0;
            while (i < length) {
                try {
                    String str2 = strArrSplit[i];
                    int i2 = Integer.parseInt(str2.substring(1));
                    if (str2.startsWith(AnimatedProperty.PROPERTY_NAME_W)) {
                        if (iD == i2) {
                            return true;
                        }
                    } else if (str2.startsWith("m") && iC == i2) {
                        return true;
                    }
                    i++;
                    z2 = false;
                } catch (Exception e) {
                    e = e;
                    z = z2;
                }
            }
            return z2;
        } catch (Exception e2) {
            e = e2;
        }
        com.xiaomi.onetrack.util.s.a(a, "shouldUploadByCycle " + str + "_exception: " + e.getMessage());
        return z;
    }

    private boolean a(JSONObject jSONObject, String str, String str2) {
        if (com.xiaomi.onetrack.b.o.a(jSONObject, str2, com.xiaomi.onetrack.b.a.a, false)) {
            com.xiaomi.onetrack.util.s.a(a, " This event disabled tracking data , skip it.");
            com.xiaomi.onetrack.h.d.b.a(this.e.getAppId(), str2, com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.m, 1);
            c.a(this, this.e, this.f, str2, com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.m);
            return true;
        }
        if (!a(jSONObject, str2)) {
            com.xiaomi.onetrack.util.s.a(a, "This event should not upload by Cycle , skip it.");
            com.xiaomi.onetrack.h.d.b.a(this.e.getAppId(), str2, com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.n, 1);
            c.a(this, this.e, this.f, str2, com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.q);
            return true;
        }
        if (b(jSONObject, str, str2)) {
            return false;
        }
        com.xiaomi.onetrack.util.s.a(a, " This event should not upload by sampling , skip it.");
        com.xiaomi.onetrack.h.d.b.a(this.e.getAppId(), str2, com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.n, 1);
        c.a(this, this.e, this.f, str2, com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.n);
        return true;
    }

    private void b(JSONObject jSONObject) {
        com.xiaomi.onetrack.i.a aVarB;
        try {
            if (!jSONObject.has("ot_pb_r") || (aVarB = com.xiaomi.onetrack.b.q.a().b()) == null || TextUtils.isEmpty(aVarB.h)) {
                return;
            }
            jSONObject.put("ot_pb_r", aVarB.h);
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b(a, "fillPBIPReigon error：" + e.getMessage());
        }
    }

    private boolean b(String str, String str2) {
        List<String> listA;
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str)) {
            try {
                String[] strArrSplit = str.split(com.xiaomi.onetrack.util.ac.a);
                if (strArrSplit != null && strArrSplit.length >= 5 && (listA = com.xiaomi.onetrack.util.ac.a(str2, com.xiaomi.onetrack.util.ac.b)) != null && listA.contains(strArrSplit[4])) {
                    return true;
                }
            } catch (Exception e) {
                com.xiaomi.onetrack.util.s.b(a, "isMatchId error：" + e.toString());
            }
        }
        return false;
    }

    private boolean b(String str, String str2, JSONObject jSONObject, JSONObject jSONObject2) {
        String strA = "";
        try {
            JSONObject jSONObjectA = com.xiaomi.onetrack.b.j.a().a(str, str2);
            String strA2 = com.xiaomi.onetrack.b.o.a(jSONObjectA, str2, com.xiaomi.onetrack.b.a.f, "");
            String strOptString = jSONObject2 == null ? "" : jSONObject2.optString(am.af);
            com.xiaomi.onetrack.util.s.a(a, "tip: " + strOptString + ", needIds: " + strA2);
            if (c(strOptString, strA2)) {
                if (com.xiaomi.onetrack.b.o.a(jSONObjectA, str2, com.xiaomi.onetrack.b.a.a, false)) {
                    com.xiaomi.onetrack.util.s.a(a, " This event disabled tracking data , skip it.");
                    com.xiaomi.onetrack.h.d.b.a(this.e.getAppId(), str2, com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.m, 1);
                    c.a(this, this.e, this.f, str2, com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.m);
                    return false;
                }
                if (!b(jSONObjectA, str, str2)) {
                    com.xiaomi.onetrack.util.s.a(a, " This event should not upload by sampling , skip it.");
                    com.xiaomi.onetrack.h.d.b.a(this.e.getAppId(), str2, com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.n, 1);
                    c.a(this, this.e, this.f, str2, com.xiaomi.onetrack.h.d.a.i, com.xiaomi.onetrack.h.d.a.n);
                    return false;
                }
                strA = com.xiaomi.onetrack.b.o.a(jSONObjectA, str2, com.xiaomi.onetrack.b.a.g, "");
            }
            String strB = com.xiaomi.onetrack.b.j.a().b(str);
            com.xiaomi.onetrack.util.s.a(a, "bannedParamsForApp: " + strB + ", bannedParamsForEvent: " + strA);
            Set<String> setA = com.xiaomi.onetrack.util.ac.a(strB, strA, com.xiaomi.onetrack.util.ac.b);
            a(jSONObject, setA);
            a(jSONObject2, setA);
            return true;
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b(a, "oldEventOrNeedIdByCloudControl error：" + e.getMessage());
            return true;
        }
    }

    private boolean b(JSONObject jSONObject, String str, String str2) {
        if (com.xiaomi.onetrack.f.b.a(this.f.b())) {
            com.xiaomi.onetrack.util.s.a(a, "basicModeEnable is open,notUploadBySampling return false");
            return true;
        }
        long jA = com.xiaomi.onetrack.b.j.a().a(jSONObject, str, str2);
        long jAbs = Math.abs(DeviceUtil.b(com.xiaomi.onetrack.f.a.b()).hashCode()) % 100;
        boolean z = jA > jAbs;
        com.xiaomi.onetrack.util.s.a(a, "shouldUploadBySampling " + str2 + ",  shouldUpload=" + z + ", sample=" + jA + ", val=" + jAbs);
        return z;
    }

    private boolean c(String str, String str2) {
        List<String> listA;
        if (TextUtils.isEmpty(str2)) {
            return true;
        }
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            String[] strArrSplit = str.split(com.xiaomi.onetrack.util.ac.a);
            return strArrSplit != null && strArrSplit.length >= 5 && (listA = com.xiaomi.onetrack.util.ac.a(str2, com.xiaomi.onetrack.util.ac.b)) != null && listA.contains(strArrSplit[4]);
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b(a, "isMatchId error：" + e.toString());
        }
    }

    private boolean c(JSONObject jSONObject) {
        try {
            return jSONObject.optBoolean(com.xiaomi.onetrack.f.b.C0047b.D, false);
        } catch (Throwable unused) {
            com.xiaomi.onetrack.util.s.a(a, "");
            return false;
        }
    }

    @Override // com.xiaomi.onetrack.api.ap
    public void a(int i) {
        if (i == 2) {
            com.xiaomi.onetrack.c.b.a(new bd(this));
        }
    }

    @Override // com.xiaomi.onetrack.api.ap
    public void a(String str, String str2) {
        JSONObject jSONObject;
        JSONObject jSONObjectOptJSONObject;
        JSONObject jSONObjectOptJSONObject2 = null;
        try {
            jSONObject = new JSONObject(str2);
            try {
                jSONObjectOptJSONObject = jSONObject.optJSONObject(an.b);
                try {
                    jSONObjectOptJSONObject2 = jSONObject.optJSONObject(an.a);
                } catch (JSONException e) {
                    e = e;
                    com.xiaomi.onetrack.util.s.a(a, " data JSONException e:" + e.getMessage());
                }
            } catch (JSONException e2) {
                e = e2;
                jSONObjectOptJSONObject = null;
            }
        } catch (JSONException e3) {
            e = e3;
            jSONObject = null;
            jSONObjectOptJSONObject = null;
        }
        JSONObject jSONObject2 = jSONObjectOptJSONObject2;
        JSONObject jSONObject3 = jSONObject;
        JSONObject jSONObject4 = jSONObjectOptJSONObject;
        boolean zC = c(jSONObject4);
        com.xiaomi.onetrack.util.y yVar = this.f;
        if (yVar != null && !yVar.a(str) && !zC) {
            com.xiaomi.onetrack.util.s.a(a, "The privacy policy is not permitted, and the event is not basic or recommend event or custom dau event, skip it.");
            return;
        }
        if (!a(str, str2, zC)) {
            com.xiaomi.onetrack.h.d.b.a(this.e.getAppId(), str, com.xiaomi.onetrack.h.d.a.h, com.xiaomi.onetrack.h.d.a.m, 1);
            c.a(this, this.e, this.f, str, com.xiaomi.onetrack.h.d.a.h, com.xiaomi.onetrack.h.d.a.m);
            return;
        }
        if (!com.xiaomi.onetrack.c.j.b()) {
            com.xiaomi.onetrack.util.s.a(a, "track network is unaccessable, cta");
            com.xiaomi.onetrack.c.j.a(str, str2);
            return;
        }
        com.xiaomi.onetrack.c.j.a(this);
        if (com.xiaomi.onetrack.util.s.a && !str.equalsIgnoreCase("onetrack_bug_report")) {
            com.xiaomi.onetrack.util.s.a(a, "track data:" + str2);
        }
        com.xiaomi.onetrack.c.a.a(new bc(this, zC));
        a(str, jSONObject3, jSONObject4, jSONObject2, zC);
    }

    @Override // com.xiaomi.onetrack.api.ap
    public void a(boolean z) {
        if (as.i) {
            return;
        }
        com.xiaomi.onetrack.c.j.a(this);
    }

    public boolean a(String str, String str2, boolean z) {
        if (OneTrack.isDisable() || OneTrack.isUseSystemNetTrafficOnly()) {
            com.xiaomi.onetrack.util.s.a(a, "Tracking data is disabled or onetrack use system net traffic only, skip it.");
            return false;
        }
        Configuration configuration = this.e;
        if (configuration == null || TextUtils.isEmpty(configuration.getProjectId()) || TextUtils.isEmpty(this.e.getPrivateKeyId())) {
            com.xiaomi.onetrack.util.s.a(a, "projectId or pKeyId is empty, so return");
            return false;
        }
        if (str != null && str.equals("onetrack_bug_report")) {
            return true;
        }
        if (z) {
            if (str2 != null && str2.length() > c) {
                com.xiaomi.onetrack.util.s.a(a, "ad Event size exceed limitation!");
                return false;
            }
        } else if (str2 != null && str2.length() * 2 > b) {
            com.xiaomi.onetrack.util.s.a(a, "Event size exceed limitation!");
            return false;
        }
        return true;
    }
}
