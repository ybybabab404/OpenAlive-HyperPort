package com.xiaomi.onetrack.a.c;

import com.xiaomi.onetrack.util.s;
import com.xiaomi.onetrack.util.t;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes2.dex */
public class c {
    private static final String a = "AdMonitorUploader";

    public static void a() {
        ArrayList<com.xiaomi.onetrack.a.b.a> arrayList;
        try {
            if (b()) {
                s.a(a, "即将读取数据库并上传数据");
                int i = 0;
                while (i <= 20) {
                    com.xiaomi.onetrack.a.c.a().e();
                    a aVarC = com.xiaomi.onetrack.a.c.a().c();
                    if (aVarC != null && (arrayList = aVarC.b) != null && arrayList.size() > 0) {
                        ArrayList<Integer> arrayList2 = new ArrayList<>();
                        ArrayList<Integer> arrayList3 = new ArrayList<>();
                        for (com.xiaomi.onetrack.a.b.a aVar : aVarC.b) {
                            boolean zA = com.xiaomi.onetrack.g.b.a(aVar);
                            int iB = aVar.b();
                            if (zA) {
                                arrayList2.add(Integer.valueOf(iB));
                            } else {
                                arrayList3.add(Integer.valueOf(iB));
                            }
                        }
                        if (arrayList2.size() > 0) {
                            com.xiaomi.onetrack.a.c.a().a(arrayList2);
                        }
                        if (arrayList3.size() > 0) {
                            com.xiaomi.onetrack.a.c.a().b(arrayList3);
                        }
                        i++;
                        if (aVarC.c) {
                            s.a(a, "No more ad monitor records");
                            return;
                        }
                    }
                    s.a(a, "满足条件的adMonitor记录为空，即将返回");
                    return;
                }
            }
        } catch (Throwable th) {
            s.a(a, "uploadData Throwable:" + th.getMessage());
        }
    }

    private static boolean b() {
        if (t.a(a)) {
            s.a(a, "the device is not provisioned, stop poll!");
            return false;
        }
        if (!com.xiaomi.onetrack.g.c.a()) {
            s.a(a, "network is unconnected, stop poll!");
            return false;
        }
        if (com.xiaomi.onetrack.a.c.a().b() > 0) {
            return true;
        }
        s.a(a, "no data remain in db, stop poll!");
        return false;
    }
}
