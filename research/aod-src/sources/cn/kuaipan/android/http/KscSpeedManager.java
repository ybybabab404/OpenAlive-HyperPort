package cn.kuaipan.android.http;

import android.os.SystemClock;
import android.util.SparseArray;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class KscSpeedManager {
    public long mLatestEraseTime;
    public final int mRecodeDuration;
    public final HashMap mRecordMap;

    public static int computeKey(long j) {
        return (int) ((j / 1000) % 3600000);
    }

    public static long current() {
        return SystemClock.elapsedRealtime();
    }

    public final void appendRecoder(String str, int i, float f) {
        SparseArray sparseArray = (SparseArray) this.mRecordMap.get(str);
        if (sparseArray == null) {
            sparseArray = new SparseArray();
            this.mRecordMap.put(str, sparseArray);
        }
        sparseArray.put(i, Float.valueOf(((Float) sparseArray.get(i, Float.valueOf(0.0f))).floatValue() + f));
        if (str != null) {
            appendRecoder(null, i, f);
        }
    }

    public final void appendRecoders(String str, int i, int i2, float f) {
        if (i2 >= i) {
            while (i <= i2) {
                appendRecoder(str, i, f);
                i++;
            }
            return;
        }
        while (i < 3600000) {
            appendRecoder(str, i, f);
            i++;
        }
        for (int i3 = 0; i3 <= i2; i3++) {
            appendRecoder(str, i3, f);
        }
    }

    public final void eraseExpired() {
        long jCurrent = current();
        if (jCurrent - this.mLatestEraseTime <= 300000) {
            return;
        }
        int iComputeKey = computeKey(jCurrent);
        int i = iComputeKey - this.mRecodeDuration;
        boolean z = iComputeKey < i;
        LinkedList linkedList = new LinkedList();
        for (Map.Entry entry : this.mRecordMap.entrySet()) {
            String str = (String) entry.getKey();
            SparseArray sparseArray = (SparseArray) entry.getValue();
            if (z) {
                int i2 = 0;
                while (i2 < sparseArray.size()) {
                    int iKeyAt = sparseArray.keyAt(i2);
                    if (iKeyAt > iComputeKey && iKeyAt < i) {
                        sparseArray.delete(iKeyAt);
                    } else if (iKeyAt >= i) {
                        break;
                    } else {
                        i2++;
                    }
                }
            } else {
                int i3 = 0;
                while (i3 < sparseArray.size()) {
                    int iKeyAt2 = sparseArray.keyAt(i3);
                    if (iKeyAt2 > iComputeKey || iKeyAt2 < i) {
                        sparseArray.delete(iKeyAt2);
                    } else {
                        i3++;
                    }
                }
            }
            if (sparseArray.size() <= 0) {
                linkedList.add(str);
            }
        }
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            this.mRecordMap.remove((String) it.next());
        }
        this.mLatestEraseTime = jCurrent;
    }

    public synchronized void recoder(String str, long j, long j2, float f) {
        if (j2 < j || f < 0.0f) {
            return;
        }
        try {
            long j3 = j / 1000;
            long j4 = j2 / 1000;
            if (j4 == j3) {
                appendRecoder(str, computeKey(j), f);
            } else {
                long j5 = j4 - j3;
                if (j5 <= 1) {
                    float f2 = j2 - j;
                    float f3 = ((1000 - (j % 1000)) * f) / f2;
                    int iComputeKey = computeKey(j);
                    int iComputeKey2 = computeKey(j2);
                    appendRecoder(str, iComputeKey, f3);
                    appendRecoder(str, iComputeKey2, (f * (j2 % 1000)) / f2);
                } else {
                    float f4 = j2 - j;
                    float f5 = ((1000 - (j % 1000)) * f) / f4;
                    float f6 = ((j2 % 1000) * f) / f4;
                    int iComputeKey3 = computeKey(j);
                    int iComputeKey4 = computeKey(j2);
                    appendRecoder(str, iComputeKey3, f5);
                    appendRecoder(str, iComputeKey4, f6);
                    appendRecoders(str, iComputeKey3 + 1, iComputeKey4 - 1, ((f - f5) - f6) / (j5 - 1));
                }
            }
            eraseExpired();
        } catch (Throwable th) {
            throw th;
        }
    }
}
