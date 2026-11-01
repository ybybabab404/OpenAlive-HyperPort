package cn.kuaipan.android.kss.upload;

import android.os.ParcelFileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class UploadDescriptorFile {
    public final long fileSize;
    public final ParcelFileDescriptor pFd;

    public FileInputStream openFileInputStream() throws IOException {
        ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream(this.pFd.dup());
        autoCloseInputStream.getChannel().position(0L);
        return autoCloseInputStream;
    }
}
