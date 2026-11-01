package cn.kuaipan.android.utils;

import cn.kuaipan.android.kss.upload.UploadDescriptorFile;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class RandomFileDescriptorInputStream extends RandomInputStream {
    public boolean mClosed;
    public FileInputStream mFileInputStream;
    public long mMarkedPos;
    public long mPos;
    public final UploadDescriptorFile uploadFile;

    @Override // java.io.InputStream
    public synchronized int available() {
        return (int) (this.uploadFile.fileSize - this.mPos);
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() {
        this.mFileInputStream.close();
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
        if (j >= this.uploadFile.fileSize) {
            throw new IOException("Seek position is not available");
        }
        this.mFileInputStream.getChannel().position(j);
        this.mPos = j;
    }

    @Override // java.io.InputStream
    public synchronized int read() {
        int i;
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedIOException();
        }
        i = this.mFileInputStream.getChannel().read(ByteBuffer.allocate(1));
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
        i = this.mFileInputStream.getChannel().read(ByteBuffer.wrap(bArr));
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
        byte[] bArr2 = new byte[i2];
        i3 = this.mFileInputStream.getChannel().read(ByteBuffer.wrap(bArr2));
        System.arraycopy(bArr2, 0, bArr, i, i2);
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
                this.mFileInputStream = this.uploadFile.openFileInputStream();
            }
            this.mFileInputStream.getChannel().position(this.mMarkedPos);
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
        long j2 = this.uploadFile.fileSize;
        long j3 = this.mPos;
        if (j2 - j3 < j) {
            this.mPos = j2;
            this.mFileInputStream.getChannel().position(this.mPos);
            return this.uploadFile.fileSize - this.mPos;
        }
        this.mPos = j3 + j;
        this.mFileInputStream.getChannel().position(this.mPos);
        return j;
    }
}
