package micloud.compat.v18.backup;

/* JADX INFO: loaded from: classes2.dex */
public class InstalldOperateFailedException extends Exception {
    public final int errCode;

    public InstalldOperateFailedException(int i, Throwable th) {
        super(th);
        this.errCode = i;
    }
}
