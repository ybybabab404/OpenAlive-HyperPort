package micloud.compat.v18.backup;

/* JADX INFO: loaded from: classes2.dex */
public class PackageManagerOperateFailedException extends Exception {
    public final int errCode;

    public PackageManagerOperateFailedException(int i, Throwable th) {
        super(th);
        this.errCode = i;
    }
}
