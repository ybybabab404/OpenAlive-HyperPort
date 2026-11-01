package cn.kuaipan.android.exception;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public class ServerMsgException extends KscException {
    public final String origMessage;

    @Override // java.lang.Throwable
    public String getMessage() {
        if (TextUtils.isEmpty(this.origMessage)) {
            return super.getMessage();
        }
        return this.origMessage + "\n" + super.getMessage();
    }
}
