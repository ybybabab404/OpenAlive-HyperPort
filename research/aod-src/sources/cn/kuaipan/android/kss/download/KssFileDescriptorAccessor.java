package cn.kuaipan.android.kss.download;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;

/* JADX INFO: loaded from: classes.dex */
public class KssFileDescriptorAccessor implements KssAccessor {
    public boolean mClosed;
    public FileInputStream mFileInputStream;
    public FileLock mFileLocker;
    public FileOutputStream mFileOutputStream;

    public void close() {
        if (this.mClosed) {
            return;
        }
        this.mClosed = true;
        try {
            FileLock fileLock = this.mFileLocker;
            if (fileLock != null) {
                fileLock.release();
                this.mFileLocker = null;
            }
            try {
                FileOutputStream fileOutputStream = this.mFileOutputStream;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                    this.mFileOutputStream = null;
                }
                if (this.mFileInputStream != null) {
                }
            } catch (IOException unused) {
            }
        } finally {
            try {
                FileOutputStream fileOutputStream2 = this.mFileOutputStream;
                if (fileOutputStream2 != null) {
                    fileOutputStream2.close();
                    this.mFileOutputStream = null;
                }
                FileInputStream fileInputStream = this.mFileInputStream;
                if (fileInputStream != null) {
                    fileInputStream.close();
                    this.mFileInputStream = null;
                }
            } catch (IOException unused2) {
            }
        }
    }

    public void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }
}
