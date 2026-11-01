package cn.kuaipan.android.kss.download;

/* JADX INFO: loaded from: classes.dex */
public class LoadRecorder {
    public LoadMap map;
    public final LoadMap.Space space;

    public void finalize() throws Throwable {
        recycle();
        super.finalize();
    }

    public LoadMap.Space getSpace() {
        return this.space;
    }

    public void recycle() {
        LoadMap loadMap = this.map;
        if (loadMap != null) {
            loadMap.recycleRecorder(this);
            this.map = null;
        }
    }
}
