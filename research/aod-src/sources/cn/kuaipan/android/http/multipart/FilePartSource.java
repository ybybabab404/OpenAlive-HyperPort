package cn.kuaipan.android.http.multipart;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class FilePartSource implements PartSource {
    public File file;
    public String fileName;

    @Override // cn.kuaipan.android.http.multipart.PartSource
    public InputStream createInputStream() {
        return this.file != null ? new FileInputStream(this.file) : new ByteArrayInputStream(new byte[0]);
    }

    @Override // cn.kuaipan.android.http.multipart.PartSource
    public String getFileName() {
        String str = this.fileName;
        return str == null ? "noname" : str;
    }

    @Override // cn.kuaipan.android.http.multipart.PartSource
    public long getLength() {
        File file = this.file;
        if (file != null) {
            return file.length();
        }
        return 0L;
    }
}
