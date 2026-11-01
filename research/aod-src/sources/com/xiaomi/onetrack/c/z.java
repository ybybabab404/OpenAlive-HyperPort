package com.xiaomi.onetrack.c;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes2.dex */
class z implements Runnable {
    final /* synthetic */ y a;

    public z(y yVar) {
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
                ab abVarC = n.a().c();
                if (abVarC != null && (arrayList = abVarC.a) != null && arrayList.size() != 0) {
                    boolean zA = e.a().a(abVarC);
                    com.xiaomi.onetrack.util.s.a("TransformChannelManager", "addTransformDataToDB success:" + zA);
                    if (zA && (iA = n.a().a(abVarC.c)) == 0) {
                        com.xiaomi.onetrack.util.s.a("TransformChannelManager", "删除数据不成功，deleted：" + iA);
                    }
                    if (abVarC.d) {
                        com.xiaomi.onetrack.util.s.a("TransformChannelManager", "No more records ");
                        return;
                    }
                    i++;
                }
                com.xiaomi.onetrack.util.s.a("TransformChannelManager", "PSToOT,need transform data is null");
                return;
            } catch (Exception e) {
                com.xiaomi.onetrack.util.s.a("TransformChannelManager", "pSDBToOTDB e：" + e.getMessage());
                return;
            }
        }
    }
}
