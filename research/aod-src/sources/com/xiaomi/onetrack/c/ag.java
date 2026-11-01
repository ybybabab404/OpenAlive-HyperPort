package com.xiaomi.onetrack.c;

import android.os.HandlerThread;
import android.text.TextUtils;
import com.xiaomi.onetrack.api.as;
import com.xiaomi.onetrack.api.be;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.GZIPOutputStream;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class ag {
    public static final String a = "config";
    public static final String b = "appId";
    public static final String c = "version";
    private static final String d = "UploaderEngine";
    private static final String e = "code";
    private static final String f = "UTF-8";
    private ad g;

    public static class a {
        private static final ag a = new ag();

        private a() {
        }
    }

    private ag() {
        h();
    }

    public static ag a() {
        return a.a;
    }

    private void a(HashMap<String, Integer> map) {
        if (map != null) {
            try {
                if (map.size() > 0) {
                    for (String str : map.keySet()) {
                        com.xiaomi.onetrack.h.d.b.a(str, "", com.xiaomi.onetrack.h.d.a.j, "", map.get(str).intValue());
                    }
                }
            } catch (Exception e2) {
                com.xiaomi.onetrack.util.s.b(d, "recordUploadSuccessStatsToDb exception ", e2);
            }
        }
    }

    private boolean a(JSONArray jSONArray) {
        try {
            String strB = com.xiaomi.onetrack.util.aa.a().b();
            String string = jSONArray.toString();
            com.xiaomi.onetrack.util.s.a(d, " payload:" + string);
            byte[] bArrA = a(a(string), com.xiaomi.onetrack.d.f.a().b()[0]);
            com.xiaomi.onetrack.util.s.a(d, "before zip and encrypt, len=" + string.length() + ", after=" + bArrA.length);
            String strA = com.xiaomi.onetrack.g.b.a(strB, bArrA);
            StringBuilder sb = new StringBuilder();
            sb.append("sendDataToServer response: ");
            sb.append(strA);
            com.xiaomi.onetrack.util.s.a(d, sb.toString());
            if (TextUtils.isEmpty(strA)) {
                return false;
            }
            return b(strA);
        } catch (Exception e2) {
            com.xiaomi.onetrack.util.s.b(d, "Exception while uploading ", e2);
            return false;
        }
    }

    public static byte[] a(String str) throws Throwable {
        GZIPOutputStream gZIPOutputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream(str.getBytes(f).length);
            try {
                gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                try {
                    try {
                        gZIPOutputStream.write(str.getBytes(f));
                        gZIPOutputStream.finish();
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        com.xiaomi.onetrack.util.p.a((OutputStream) byteArrayOutputStream);
                        com.xiaomi.onetrack.util.p.a((OutputStream) gZIPOutputStream);
                        return byteArray;
                    } catch (Exception e2) {
                        e = e2;
                        com.xiaomi.onetrack.util.s.b(d, " zipData failed! " + e.toString());
                        com.xiaomi.onetrack.util.p.a((OutputStream) byteArrayOutputStream);
                        com.xiaomi.onetrack.util.p.a((OutputStream) gZIPOutputStream);
                        return null;
                    }
                } catch (Throwable th) {
                    th = th;
                    byteArrayOutputStream2 = byteArrayOutputStream;
                    com.xiaomi.onetrack.util.p.a((OutputStream) byteArrayOutputStream2);
                    com.xiaomi.onetrack.util.p.a((OutputStream) gZIPOutputStream);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                gZIPOutputStream = null;
            } catch (Throwable th2) {
                th = th2;
                gZIPOutputStream = null;
                byteArrayOutputStream2 = byteArrayOutputStream;
                com.xiaomi.onetrack.util.p.a((OutputStream) byteArrayOutputStream2);
                com.xiaomi.onetrack.util.p.a((OutputStream) gZIPOutputStream);
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            byteArrayOutputStream = null;
            gZIPOutputStream = null;
        } catch (Throwable th3) {
            th = th3;
            gZIPOutputStream = null;
            com.xiaomi.onetrack.util.p.a((OutputStream) byteArrayOutputStream2);
            com.xiaomi.onetrack.util.p.a((OutputStream) gZIPOutputStream);
            throw th;
        }
    }

    public static byte[] a(byte[] bArr, String str) {
        if (bArr != null) {
            return com.xiaomi.onetrack.d.a.a(bArr, com.xiaomi.onetrack.d.c.a(str));
        }
        com.xiaomi.onetrack.util.s.b(d, "content is null");
        return null;
    }

    private boolean b(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            int iOptInt = jSONObject.optInt("code");
            if (iOptInt == 0) {
                com.xiaomi.onetrack.util.s.a(d, "成功发送数据到服务端");
                com.xiaomi.onetrack.b.a.a().a(jSONObject);
                return true;
            }
            if (iOptInt == -3) {
                com.xiaomi.onetrack.util.s.b(d, "signature expired, will update");
                com.xiaomi.onetrack.d.f.a().c();
                return false;
            }
            com.xiaomi.onetrack.util.s.b(d, "Error: status code=" + iOptInt);
            return false;
        } catch (Exception e2) {
            com.xiaomi.onetrack.util.s.b(d, "parseUploadingResult exception ", e2);
            return false;
        }
    }

    private boolean c() {
        ArrayList<m> arrayList;
        try {
            if (!g()) {
                return false;
            }
            for (int i = 0; !be.a().c() && i < 2000; i++) {
                p pVarE = r.a().e();
                if (pVarE == null || (arrayList = pVarE.a) == null || arrayList.size() == 0) {
                    com.xiaomi.onetrack.util.s.a(d, "uploadPBCacheData 满足条件的记录为空，即将返回");
                    break;
                }
                com.xiaomi.onetrack.i.a aVarB = com.xiaomi.onetrack.b.q.a().b();
                if (aVarB != null && !TextUtils.isEmpty(aVarB.d)) {
                    q.a(pVarE, aVarB, true);
                    if (pVarE.d) {
                        com.xiaomi.onetrack.util.s.a(d, "uploadPBCacheData No more records");
                        break;
                    }
                }
                com.xiaomi.onetrack.util.s.a(d, "PollingUploader upload token is null");
                return false;
            }
            return true;
        } catch (Exception unused) {
        }
        return true;
    }

    private boolean c(int i) {
        i iVarA;
        if (!e()) {
            return true;
        }
        do {
            iVarA = e.a().a(i);
            if (iVarA == null) {
                com.xiaomi.onetrack.util.s.a(d, "满足条件的记录为空，即将返回, priority=" + i);
                return true;
            }
            ArrayList<Long> arrayList = iVarA.c;
            boolean zA = a(iVarA.a);
            com.xiaomi.onetrack.util.s.a(d, "upload success:" + zA);
            if (!zA) {
                return false;
            }
            a(iVarA.e);
            if (e.a().a(arrayList) == 0) {
                com.xiaomi.onetrack.util.s.b(d, "delete DB failed!", new Throwable());
            }
            return true;
        } while (!iVarA.d);
        com.xiaomi.onetrack.util.s.a(d, "No more records for prio=" + i);
        return true;
    }

    private boolean d() {
        if (!g()) {
            return true;
        }
        for (int i = 0; !be.a().c() && i < 2000; i++) {
            i iVarD = r.a().d();
            if (iVarD == null) {
                com.xiaomi.onetrack.util.s.a(d, "uploadOTCacheData 满足条件的记录为空，即将返回");
                return true;
            }
            ArrayList<Long> arrayList = iVarD.c;
            boolean zA = a(iVarD.a);
            com.xiaomi.onetrack.util.s.a(d, "upload success:" + zA);
            if (!zA) {
                return false;
            }
            a(iVarD.e);
            if (r.a().a(arrayList) == 0) {
                com.xiaomi.onetrack.util.s.b(d, "delete DB failed!", new Throwable());
                break;
            }
            if (iVarD.d) {
                com.xiaomi.onetrack.util.s.a(d, "uploadOTCacheData No more records");
                break;
            }
        }
        return true;
    }

    private boolean e() {
        if (com.xiaomi.onetrack.b.o.a() && com.xiaomi.onetrack.g.c.a() && !com.xiaomi.onetrack.b.o.b()) {
            return true;
        }
        com.xiaomi.onetrack.util.s.a(d, "不用处理消息, available=" + com.xiaomi.onetrack.b.o.a() + ", 是否有网=" + com.xiaomi.onetrack.g.c.a() + ", 数据库是否为空=" + com.xiaomi.onetrack.b.o.b());
        return false;
    }

    private boolean f() {
        if (com.xiaomi.onetrack.g.c.a() && !com.xiaomi.onetrack.util.t.a(d) && !n.a().e()) {
            return true;
        }
        com.xiaomi.onetrack.util.s.a(d, "ps data not match the upload status，即将返回");
        return false;
    }

    private boolean g() {
        if (com.xiaomi.onetrack.g.c.a() && !com.xiaomi.onetrack.util.t.a(d) && r.a().c() > 0) {
            return true;
        }
        com.xiaomi.onetrack.util.s.a(d, "ps data not match the upload status，即将返回");
        return false;
    }

    private void h() {
        HandlerThread handlerThread = new HandlerThread("onetrack_uploader_worker");
        handlerThread.start();
        this.g = new ad(handlerThread.getLooper());
    }

    public synchronized void a(int i, boolean z) {
        try {
            ad adVar = this.g;
            if (adVar != null) {
                adVar.a(i, z);
            } else {
                com.xiaomi.onetrack.util.s.b(d, "*** impossible, upload timer should not be null");
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public void a(boolean z) {
        ad adVar = this.g;
        if (adVar != null) {
            adVar.a(z);
        } else {
            com.xiaomi.onetrack.util.s.b(d, "*** impossible, upload timer should not be null");
        }
    }

    public boolean a(int i) {
        com.xiaomi.onetrack.util.s.a(d, "即将读取数据库并上传数据");
        return as.i ? c(i) : b(i);
    }

    public void b() {
        if (as.i) {
            a().d();
        } else {
            a().c();
        }
    }

    public boolean b(int i) {
        ArrayList<m> arrayList;
        try {
            if (!f()) {
                return false;
            }
            int i2 = 100;
            int i3 = 0;
            while (true) {
                if (com.xiaomi.onetrack.util.s.a) {
                    i2 = 1000;
                }
                if (i3 >= i2) {
                    return true;
                }
                p pVarA = n.a().a(i);
                if (pVarA != null && (arrayList = pVarA.a) != null && arrayList.size() != 0) {
                    com.xiaomi.onetrack.i.a aVarB = com.xiaomi.onetrack.b.q.a().b();
                    if (aVarB != null && !TextUtils.isEmpty(aVarB.d)) {
                        q.a(pVarA, aVarB, false);
                        if (pVarA.d) {
                            com.xiaomi.onetrack.util.s.a(d, "No more records ");
                        } else {
                            i3++;
                        }
                    }
                    com.xiaomi.onetrack.util.s.a(d, "PollingUploader upload token is null");
                    return false;
                }
                com.xiaomi.onetrack.util.s.a(d, "满足条件的记录为空，即将返回");
                return true;
            }
        } catch (Exception unused) {
        }
    }
}
