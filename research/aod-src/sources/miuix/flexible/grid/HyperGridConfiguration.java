package miuix.flexible.grid;

/* JADX INFO: loaded from: classes2.dex */
public class HyperGridConfiguration {
    public static HyperGridConfiguration sPool;
    public static int sPoolSize;
    public static final Object sPoolSync = new Object();
    public float cellWidth;
    public int columnCount;
    public float columnSpacing;
    public HyperGridConfiguration next;

    public static HyperGridConfiguration obtain() {
        synchronized (sPoolSync) {
            try {
                HyperGridConfiguration hyperGridConfiguration = sPool;
                if (hyperGridConfiguration == null) {
                    return new HyperGridConfiguration();
                }
                sPool = hyperGridConfiguration.next;
                hyperGridConfiguration.next = null;
                sPoolSize--;
                return hyperGridConfiguration;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void recycle() {
        synchronized (sPoolSync) {
            try {
                int i = sPoolSize;
                if (i < 10) {
                    this.next = sPool;
                    sPool = this;
                    sPoolSize = i + 1;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
