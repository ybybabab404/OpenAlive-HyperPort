package miuix.flexible.tile;

import android.util.SparseIntArray;

/* JADX INFO: loaded from: classes2.dex */
public class DefaultTileSizeStrategy {
    public static final SparseIntArray[] SIZE_DATA3 = new SparseIntArray[5];

    static {
        int i = 0;
        while (true) {
            SparseIntArray[] sparseIntArrayArr = SIZE_DATA3;
            if (i >= sparseIntArrayArr.length) {
                sparseIntArrayArr[0].put(0, makeSize(3, 2));
                sparseIntArrayArr[1].put(0, makeSize(2, 1));
                sparseIntArrayArr[3].put(3, makeSize(3, 2));
                sparseIntArrayArr[4].put(3, makeSize(2, 1));
                return;
            }
            sparseIntArrayArr[i] = new SparseIntArray();
            i++;
        }
    }

    public static int makeSize(int i, int i2) {
        return (int) TileCache.makeItemSpec(0, 0, i, i2);
    }
}
