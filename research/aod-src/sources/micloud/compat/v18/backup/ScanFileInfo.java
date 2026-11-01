package micloud.compat.v18.backup;

/* JADX INFO: loaded from: classes2.dex */
public class ScanFileInfo implements ScanInfo {
    public final String md5;
    public final String path;
    public final String sha1;
    public final long size;

    public ScanFileInfo(String str, String str2, String str3, long j) {
        this.path = str;
        this.md5 = str2;
        this.sha1 = str3;
        this.size = j;
    }
}
