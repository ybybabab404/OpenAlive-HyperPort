package io.reactivex.rxjava3.internal.util;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes2.dex */
public class LinkedArrayList {
    public final int capacityHint;
    public Object[] head;
    public volatile int size;

    public Object[] head() {
        return this.head;
    }

    public String toString() {
        int i = this.capacityHint;
        int i2 = this.size;
        ArrayList arrayList = new ArrayList(i2 + 1);
        Object[] objArrHead = head();
        int i3 = 0;
        int i4 = 0;
        while (i3 < i2) {
            arrayList.add(objArrHead[i4]);
            i3++;
            i4++;
            if (i4 == i) {
                objArrHead = (Object[]) objArrHead[i];
                i4 = 0;
            }
        }
        return arrayList.toString();
    }
}
