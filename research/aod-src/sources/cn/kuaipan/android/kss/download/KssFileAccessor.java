package cn.kuaipan.android.kss.download;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

/* JADX INFO: loaded from: classes.dex */
public class KssFileAccessor implements KssAccessor {
    public boolean mClosed;
    public RandomAccessFile mFileAccessor;
    public FileLock mFileLocker;

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
                if (this.mFileAccessor != null) {
                }
            } catch (IOException unused) {
            }
        } finally {
            try {
                RandomAccessFile randomAccessFile = this.mFileAccessor;
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                    this.mFileAccessor = null;
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
