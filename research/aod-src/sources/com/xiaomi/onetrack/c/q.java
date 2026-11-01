package com.xiaomi.onetrack.c;

import android.text.TextUtils;
import com.xiaomi.onetrack.api.an;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class q {
    private static final String a = "%s/v1/projects/%s/topics/%s:publish";
    private static String b = "";
    private static final byte[] c = {104, 116, 116, 112, 115, 58, 47, 47, 112, 117, 98, 115, 117, 98, 46, 103, 111, 111, 103, 108, 101, 97, 112, 105, 115, 46, 99, 111, 109};
    private static final String d = "PSUploadDispense";

    public static class a {
        public boolean a;
        public boolean b;

        public a(boolean z, boolean z2) {
            this.a = z;
            this.b = z2;
        }
    }

    private static a a(com.xiaomi.onetrack.i.a aVar, String str, String str2, List<m> list) throws Throwable {
        if (aVar != null) {
            try {
                if (!TextUtils.isEmpty(aVar.d) && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && list != null && list.size() != 0) {
                    com.xiaomi.onetrack.g.d dVarA = com.xiaomi.onetrack.g.b.a(String.format(a, a(), str, str2), b(list), aVar.d);
                    if (dVarA != null) {
                        int i = dVarA.d;
                        if (i == 401) {
                            aVar.i = true;
                            com.xiaomi.onetrack.i.b.a().e();
                            return a(com.xiaomi.onetrack.b.q.a().c(), str, str2, list);
                        }
                        if (i == 404) {
                            if ("NOT_FOUND".equals(dVarA.g) && !TextUtils.isEmpty(dVarA.f) && (dVarA.f.contains(str) || dVarA.f.contains(str2))) {
                                return new a(false, true);
                            }
                        } else if (i == 200) {
                            com.xiaomi.onetrack.i.b.a().f();
                            return new a(true, false);
                        }
                    }
                    return new a(false, false);
                }
            } catch (Exception e) {
                com.xiaomi.onetrack.util.s.b(d, "Exception while uploading ", e);
            }
        }
        com.xiaomi.onetrack.util.s.a(d, "token or data is null,return false");
        return new a(false, false);
    }

    private static String a() {
        if (TextUtils.isEmpty(b)) {
            b = com.xiaomi.onetrack.util.aa.a().a(c);
        }
        return b;
    }

    public static void a(p pVar, com.xiaomi.onetrack.i.a aVar, boolean z) {
        try {
            for (Map.Entry<String, List<m>> entry : d(pVar.a).entrySet()) {
                String key = entry.getKey();
                List<m> value = entry.getValue();
                if (value != null && value.size() != 0) {
                    for (Map.Entry<String, List<m>> entry2 : e(value).entrySet()) {
                        String key2 = entry2.getKey();
                        List<m> value2 = entry2.getValue();
                        if (value2 != null && value2.size() != 0 && a(key, key2, value2, aVar, z)) {
                            a(value2);
                        }
                    }
                }
            }
        } catch (Throwable unused) {
        }
    }

    private static void a(List<m> list) {
        try {
            HashMap map = new HashMap();
            Iterator<m> it = list.iterator();
            while (it.hasNext()) {
                JSONObject jSONObjectOptJSONObject = new JSONObject(it.next().e()).optJSONObject(an.b);
                if (jSONObjectOptJSONObject != null && !com.xiaomi.onetrack.util.u.b(jSONObjectOptJSONObject.optString(com.xiaomi.onetrack.f.b.C0047b.a)) && com.xiaomi.onetrack.util.af.b(jSONObjectOptJSONObject.optLong(com.xiaomi.onetrack.f.b.C0047b.p, 0L))) {
                    String strOptString = jSONObjectOptJSONObject.optString(com.xiaomi.onetrack.f.b.C0047b.l);
                    map.put(strOptString, Integer.valueOf((map.get(strOptString) == null ? 0 : ((Integer) map.get(strOptString)).intValue()) + 1));
                }
            }
            if (map.size() == 0) {
                return;
            }
            for (String str : map.keySet()) {
                com.xiaomi.onetrack.h.d.b.a(str, "", com.xiaomi.onetrack.h.d.a.j, "", ((Integer) map.get(str)).intValue());
            }
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.b(d, "recordUploadSuccessStatsToDb exception ", e);
        }
    }

    private static boolean a(String str, String str2, List<m> list, com.xiaomi.onetrack.i.a aVar, boolean z) throws Throwable {
        if (list == null || list.size() <= 0) {
            return false;
        }
        List<m> listC = c(list);
        if (aVar.i) {
            com.xiaomi.onetrack.util.s.a(d, "token is inValid,need get new");
            aVar = com.xiaomi.onetrack.b.q.a().b();
        }
        a aVarA = a(aVar, str, str2, listC);
        com.xiaomi.onetrack.util.s.a(d, "uploadGzipEncryptData success:" + aVarA.a);
        if (aVarA.a) {
            ArrayList<Long> arrayList = new ArrayList<>();
            Iterator<m> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(Long.valueOf(it.next().b()));
            }
            com.xiaomi.onetrack.util.s.a(d, "uploadGzipEncryptData deleted:" + (z ? r.a().a(arrayList) : n.a().a(arrayList)));
            return true;
        }
        if (!aVarA.b) {
            return false;
        }
        com.xiaomi.onetrack.util.s.a(d, "projectID:" + str + ",topic:" + str2 + "projectOrTopicInvalid, deleted:" + n.a().b(list));
        return false;
    }

    private static String b(List<m> list) {
        if (list == null) {
            return "";
        }
        try {
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            for (m mVar : list) {
                JSONObject jSONObject2 = new JSONObject();
                Map<String, String> mapF = mVar.f();
                if (mapF != null) {
                    JSONObject jSONObject3 = new JSONObject();
                    for (Map.Entry<String, String> entry : mapF.entrySet()) {
                        if (!TextUtils.isEmpty(entry.getKey())) {
                            jSONObject3.put(entry.getKey(), entry.getValue());
                        }
                    }
                    jSONObject2.put("attributes", jSONObject3);
                }
                jSONObject2.put("data", mVar.e());
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("messages", jSONArray);
            return jSONObject.toString();
        } catch (Exception unused) {
            return "";
        }
    }

    private static List<m> c(List<m> list) throws Throwable {
        if (list == null || list.size() == 0) {
            com.xiaomi.onetrack.util.s.a(d, "messages is empty, return null");
            return null;
        }
        HashMap map = new HashMap();
        ArrayList arrayList = new ArrayList();
        String str = com.xiaomi.onetrack.d.f.a().b()[1];
        String str2 = com.xiaomi.onetrack.d.f.a().b()[0];
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        try {
            for (m mVar : list) {
                Map<String, String> mapF = mVar.f();
                if (mapF != null) {
                    if (map.get(mapF) == null) {
                        map.put(mapF, new JSONArray());
                    }
                    ((JSONArray) map.get(mapF)).put(mVar.e());
                }
            }
            for (Map.Entry entry : map.entrySet()) {
                Map map2 = (Map) entry.getKey();
                map2.put(com.xiaomi.onetrack.util.a.g, com.xiaomi.onetrack.util.a.i);
                map2.put(com.xiaomi.onetrack.util.a.h, str);
                JSONArray jSONArray = (JSONArray) entry.getValue();
                com.xiaomi.onetrack.util.s.a(d, "zip before : " + jSONArray.toString().length());
                byte[] bArrA = ag.a(jSONArray.toString());
                com.xiaomi.onetrack.util.s.a(d, "zip after : " + bArrA.length);
                arrayList.add(new m(-1L, null, null, com.xiaomi.onetrack.d.c.a(ag.a(bArrA, str2)), map2, -1L));
            }
        } catch (Exception e) {
            com.xiaomi.onetrack.util.s.a(d, "sortByAttribute error: " + e.getMessage());
        }
        return arrayList;
    }

    private static Map<String, List<m>> d(List<m> list) {
        HashMap map = new HashMap();
        for (int i = 0; i < list.size(); i++) {
            m mVar = list.get(i);
            String strC = mVar.c();
            if (map.get(strC) == null) {
                map.put(strC, new ArrayList());
            }
            ((List) map.get(strC)).add(mVar);
        }
        return map;
    }

    private static Map<String, List<m>> e(List<m> list) {
        HashMap map = new HashMap();
        for (int i = 0; i < list.size(); i++) {
            m mVar = list.get(i);
            String strD = mVar.d();
            if (map.get(strD) == null) {
                map.put(strD, new ArrayList());
            }
            ((List) map.get(strD)).add(mVar);
        }
        return map;
    }
}
