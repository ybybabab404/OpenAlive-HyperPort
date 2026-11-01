package cn.kuaipan.android.http.multipart;

import java.io.IOException;
import java.io.InputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NullCipher;

/* JADX INFO: loaded from: classes.dex */
public class CipherFilePartSource extends FilePartSource {
    public Cipher cipher;
    public long length;

    @Override // cn.kuaipan.android.http.multipart.FilePartSource, cn.kuaipan.android.http.multipart.PartSource
    public InputStream createInputStream() {
        InputStream inputStreamCreateInputStream = super.createInputStream();
        return this.cipher == null ? new CipherInputStream(inputStreamCreateInputStream, new NullCipher()) : new CipherInputStream(inputStreamCreateInputStream, this.cipher);
    }

    @Override // cn.kuaipan.android.http.multipart.FilePartSource, cn.kuaipan.android.http.multipart.PartSource
    public long getLength() {
        if (this.length == -1) {
            byte[] bArr = new byte[1024];
            InputStream inputStreamCreateInputStream = null;
            try {
                try {
                    inputStreamCreateInputStream = createInputStream();
                    int i = 0;
                    while (true) {
                        int i2 = inputStreamCreateInputStream.read(bArr);
                        if (i2 < 0) {
                            break;
                        }
                        i += i2;
                    }
                    this.length = i;
                } finally {
                    try {
                        inputStreamCreateInputStream.close();
                    } catch (Exception unused) {
                    }
                }
            } catch (IOException unused2) {
                this.length = 0L;
            }
        }
        return this.length;
    }
}
