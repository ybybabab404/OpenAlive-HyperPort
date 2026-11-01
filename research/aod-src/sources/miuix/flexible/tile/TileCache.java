package miuix.flexible.tile;

/* JADX INFO: loaded from: classes2.dex */
public class TileCache {

    public interface TileParamsGetter {
    }

    public static long makeItemSpec(int i, int i2, int i3, int i4) {
        return ((((long) i2) & 4194303) << 20) | ((((long) i) & 4194303) << 42) | ((((long) i3) & 1023) << 10) | (((long) i4) & 1023);
    }
}
