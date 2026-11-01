package com.miui.aod.util;

import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class AodUtils {
    public static final Handler sHandler = new Handler(Looper.getMainLooper());

    public static List getClusterData(List list, int[] iArr) {
        ArrayList arrayList = new ArrayList();
        if (iArr != null && iArr.length > 0) {
            for (int i = 0; i < iArr.length; i++) {
                if (i == 0) {
                    int i2 = iArr[i];
                    if (i2 == -1) {
                        break;
                    }
                    if (i2 == -2) {
                    }
                }
                arrayList.add(list.get(iArr[i]));
            }
            return arrayList;
        }
        return list;
    }

    public static Handler getMainHandler() {
        return sHandler;
    }
}
