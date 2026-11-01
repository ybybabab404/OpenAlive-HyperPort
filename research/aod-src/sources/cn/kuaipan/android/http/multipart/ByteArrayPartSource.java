package cn.kuaipan.android.http.multipart;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class ByteArrayPartSource implements PartSource {
    public byte[] bytes;
    public String fileName;

    @Override // cn.kuaipan.android.http.multipart.PartSource
    public InputStream createInputStream() {
        return new ByteArrayInputStream(this.bytes);
    }

    @Override // cn.kuaipan.android.http.multipart.PartSource
    public String getFileName() {
        return this.fileName;
    }

    @Override // cn.kuaipan.android.http.multipart.PartSource
    public long getLength() {
        return this.bytes.length;
    }
}
