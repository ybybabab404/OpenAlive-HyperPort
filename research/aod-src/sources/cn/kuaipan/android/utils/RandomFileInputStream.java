package cn.kuaipan.android.utils;

import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.RandomAccessFile;

/* JADX INFO: loaded from: classes.dex */
public class RandomFileInputStream extends RandomInputStream {
    public final File file;
    public boolean mClosed;
    public long mMarkedPos;
    public long mPos;
    public RandomAccessFile mRFile;

    @Override // java.io.InputStream
    public synchronized int available() {
        return (int) (this.mRFile.length() - this.mPos);
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() {
        this.mRFile.close();
        this.mClosed = true;
    }

    @Override // cn.kuaipan.android.utils.RandomInputStream
    public synchronized long getCurrentPos() {
        return this.mPos;
    }

    @Override // java.io.InputStream
    public void mark(int i) {
        if (this.mClosed) {
            return;
        }
        this.mMarkedPos = this.mPos;
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        return true;
    }

    @Override // cn.kuaipan.android.utils.RandomInputStream
    public synchronized void moveToPos(long j) {
        if (j >= this.mRFile.length()) {
            throw new IOException("Seek possion is not availabel");
        }
        this.mRFile.seek(j);
        this.mPos = j;
    }

    @Override // java.io.InputStream
    public synchronized int read() {
        int i;
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedIOException();
        }
        i = this.mRFile.read();
        if (i >= 0) {
            this.mPos++;
        }
        return i;
    }

    @Override // java.io.InputStream
    public synchronized int read(byte[] bArr) {
        int i;
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedIOException();
        }
        i = this.mRFile.read(bArr);
        if (i > 0) {
            this.mPos += (long) i;
        }
        return i;
    }

    @Override // java.io.InputStream
    public synchronized int read(byte[] bArr, int i, int i2) {
        int i3;
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedIOException();
        }
        i3 = this.mRFile.read(bArr, i, i2);
        if (i3 > 0) {
            this.mPos += (long) i3;
        }
        return i3;
    }

    @Override // java.io.InputStream
    public synchronized void reset() {
        try {
            if (this.mClosed) {
                this.mClosed = false;
                this.mRFile = new RandomAccessFile(this.file, "r");
            }
            this.mRFile.seek(this.mMarkedPos);
            this.mPos = this.mMarkedPos;
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        if (j == 0) {
            return 0L;
        }
        if (j < 0) {
            throw new IOException("Number of bytes to skip cannot be negative");
        }
        int i = j < 8192 ? (int) j : AutoBottomSheetKt.ALIGN_SCREEN_START;
        synchronized (this) {
            long j2 = i;
            try {
                long j3 = 0;
                for (long j4 = j / j2; j4 >= 0; j4--) {
                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedIOException();
                    }
                    long jSkipBytes = this.mRFile.skipBytes(i);
                    j3 += jSkipBytes;
                    this.mPos += jSkipBytes;
                    if (jSkipBytes < j2) {
                        return j3;
                    }
                }
                return j3;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
