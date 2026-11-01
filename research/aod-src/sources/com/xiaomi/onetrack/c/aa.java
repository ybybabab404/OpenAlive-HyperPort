package com.xiaomi.onetrack.c;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes2.dex */
class aa implements Runnable {
    final /* synthetic */ y a;

    public aa(y yVar) {
        this.a = yVar;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        ArrayList<x> arrayList;
        int iA;
        int i = 0;
        int i2 = 100;
        while (true) {
            try {
                if (com.xiaomi.onetrack.util.s.a) {
                    i2 = 1000;
                }
                if (i >= i2) {
                    return;
                }
                ab abVarB = e.a().b();
                if (abVarB != null && (arrayList = abVarB.a) != null && arrayList.size() != 0) {
                    this.a.a(abVarB);
                    boolean zA = n.a().a(abVarB);
                    com.xiaomi.onetrack.util.s.a("TransformChannelManager", "addTransformDataToDB success:" + zA);
                    if (zA && (iA = e.a().a(abVarB.c)) == 0) {
                        com.xiaomi.onetrack.util.s.a("TransformChannelManager", "删除数据不成功，deleted：" + iA);
                    }
                    if (abVarB.d) {
                        com.xiaomi.onetrack.util.s.a("TransformChannelManager", "No more records ");
                        return;
                    }
                    i++;
                }
                com.xiaomi.onetrack.util.s.a("TransformChannelManager", "OTToPS,need transform data is null");
                return;
            } catch (Exception e) {
                com.xiaomi.onetrack.util.s.a("TransformChannelManager", "pSDBToOTDB e：" + e.getMessage());
                return;
            }
        }
    }
}
