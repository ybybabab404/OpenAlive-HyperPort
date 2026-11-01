package cn.kuaipan.android.kss;

/* JADX INFO: loaded from: classes.dex */
public interface IKssUploadRequestResult {

    public static class Block {
        public boolean exist;
        public String meta;

        public Block(String str, boolean z) {
            this.meta = str;
            this.exist = z;
        }
    }
}
