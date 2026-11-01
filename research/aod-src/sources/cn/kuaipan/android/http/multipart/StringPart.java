package cn.kuaipan.android.http.multipart;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.util.EncodingUtils;

/* JADX INFO: loaded from: classes.dex */
public class StringPart extends PartBase {
    public byte[] content;
    public String value;

    public final byte[] getContent() {
        if (this.content == null) {
            this.content = EncodingUtils.getBytes(this.value, getCharSet());
        }
        return this.content;
    }

    @Override // cn.kuaipan.android.http.multipart.Part
    public long lengthOfData() {
        return getContent().length;
    }

    @Override // cn.kuaipan.android.http.multipart.Part
    public void sendData(OutputStream outputStream) throws IOException {
        outputStream.write(getContent());
    }
}
