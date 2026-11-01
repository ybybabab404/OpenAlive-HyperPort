package miuix.internal.log.message;

import android.util.Log;
import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.Locale;
import miuix.internal.log.util.AppendableFormatter;

/* JADX INFO: loaded from: classes2.dex */
public class StringFormattedMessage extends AbstractMessage {
    public String mFormat;
    public AppendableFormatter mFormatter = new AppendableFormatter();
    public Object[] mParameters;
    public Throwable mThrowable;

    @Override // miuix.internal.log.message.Message
    public void format(Appendable appendable) {
        Object[] objArr;
        String str = this.mFormat;
        if (str == null || (objArr = this.mParameters) == null || objArr.length == 0) {
            try {
                appendable.append(str);
                return;
            } catch (IOException e) {
                Log.e("StringFormattedMessage", "Fail to format message", e);
                return;
            }
        }
        try {
            try {
                this.mFormatter.setAppendable(appendable);
                this.mFormatter.format(Locale.US, this.mFormat, this.mParameters);
            } catch (IllegalFormatException unused) {
                appendable.append(this.mFormat);
            }
        } catch (IOException e2) {
            Log.e("StringFormattedMessage", "Fail to append raw message", e2);
        }
    }

    @Override // miuix.internal.log.message.Message
    public Throwable getThrowable() {
        return this.mThrowable;
    }

    @Override // miuix.internal.log.message.AbstractMessage
    public void onRecycle() {
        this.mFormat = null;
        this.mParameters = null;
        this.mThrowable = null;
        this.mFormatter.setAppendable(null);
    }
}
