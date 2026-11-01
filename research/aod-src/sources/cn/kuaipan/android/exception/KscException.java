package cn.kuaipan.android.exception;

import org.apache.http.conn.HttpHostConnectException;

/* JADX INFO: loaded from: classes.dex */
public class KscException extends Exception implements IKscError {
    public final String detailMessage;
    public final int errCode;

    public KscException(int i, String str, Throwable th) {
        String str2;
        StringBuilder sb = new StringBuilder();
        sb.append("ErrCode: ");
        sb.append(i);
        if (str == null) {
            str2 = "";
        } else {
            str2 = "\n" + str;
        }
        sb.append(str2);
        super(sb.toString(), getSerial(th));
        this.errCode = i;
        this.detailMessage = str;
    }

    public static Throwable getSerial(Throwable th) {
        return (th != null && (th instanceof HttpHostConnectException)) ? new HttpHostConnectExceptionWrapper((HttpHostConnectException) th) : th;
    }
}
