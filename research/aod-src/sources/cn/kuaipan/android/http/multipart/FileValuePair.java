package cn.kuaipan.android.http.multipart;

import android.text.TextUtils;
import java.io.File;
import org.apache.http.NameValuePair;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.LangUtils;

/* JADX INFO: loaded from: classes.dex */
public class FileValuePair implements NameValuePair, Cloneable {
    public final File file;
    public final String name;

    public Object clone() {
        return super.clone();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof FileValuePair) {
            FileValuePair fileValuePair = (FileValuePair) obj;
            if (TextUtils.equals(this.name, fileValuePair.name) && LangUtils.equals(this.file, fileValuePair.file)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.apache.http.NameValuePair
    public String getName() {
        return this.name;
    }

    @Override // org.apache.http.NameValuePair
    public String getValue() {
        return this.file.getName();
    }

    public int hashCode() {
        return LangUtils.hashCode(LangUtils.hashCode(17, this.name), this.file);
    }

    public String toString() {
        int length = this.name.length();
        File file = this.file;
        if (file != null) {
            length += file.getPath().length() + 12;
        }
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(length);
        charArrayBuffer.append(this.name);
        if (this.file != null) {
            charArrayBuffer.append("=File[path=");
            charArrayBuffer.append(this.file.getPath());
            charArrayBuffer.append("]");
        }
        return charArrayBuffer.toString();
    }
}
