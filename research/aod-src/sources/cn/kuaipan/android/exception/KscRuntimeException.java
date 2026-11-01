package cn.kuaipan.android.exception;

/* JADX INFO: loaded from: classes.dex */
public class KscRuntimeException extends RuntimeException implements IKscError {
    public final String detailMessage;
    public final int errCode;

    public KscRuntimeException(int i) {
        this(i, null, null);
    }

    public KscRuntimeException(int i, String str, Throwable th) {
        String str2;
        StringBuilder sb = new StringBuilder();
        sb.append("ErrCode:");
        sb.append(i);
        if (str == null) {
            str2 = "";
        } else {
            str2 = " details:" + str;
        }
        sb.append(str2);
        super(sb.toString(), KscException.getSerial(th));
        this.errCode = i;
        this.detailMessage = str;
    }
}
