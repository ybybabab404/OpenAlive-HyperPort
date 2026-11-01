package cn.kuaipan.android.kss.upload;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.entity.InputStreamEntity;

/* JADX INFO: loaded from: classes.dex */
public class KssInputStreamEntity extends InputStreamEntity {
    public final InputStream content;

    @Override // org.apache.http.entity.InputStreamEntity, org.apache.http.HttpEntity
    public InputStream getContent() throws IOException {
        this.content.reset();
        return super.getContent();
    }

    @Override // org.apache.http.entity.InputStreamEntity, org.apache.http.HttpEntity
    public boolean isRepeatable() {
        return super.isRepeatable() || this.content.markSupported();
    }

    @Override // org.apache.http.entity.InputStreamEntity, org.apache.http.HttpEntity
    public void writeTo(OutputStream outputStream) throws IOException {
        this.content.reset();
        super.writeTo(outputStream);
    }
}
