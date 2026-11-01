package cn.kuaipan.android.http;

import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.RandomAccessFile;

/* JADX INFO: loaded from: classes.dex */
public class RandomInputBuffer {
    public byte[] buf;
    public File bufFile;
    public final NetCacheManager cache;
    public boolean eof;
    public RandomAccessFile file;
    public boolean fileAssigned;
    public InputStream in;
    public int pos;

    public synchronized int available(int i) {
        InputStream inputStream;
        try {
            inputStream = this.in;
            if (inputStream == null) {
                throw new IOException("RandomInputBuffer has been closed.");
            }
        } catch (Throwable th) {
            throw th;
        }
        return (!this.eof ? inputStream.available() + this.pos : this.pos) - i;
    }

    public synchronized void close() {
        try {
            InputStream inputStream = this.in;
            if (inputStream != null) {
                inputStream.close();
                this.in = null;
            }
            RandomAccessFile randomAccessFile = this.file;
            if (randomAccessFile != null) {
                randomAccessFile.close();
                this.file = null;
            }
            File file = this.bufFile;
            if (file != null) {
                this.cache.releaseCache(file);
                this.bufFile = null;
            }
            this.buf = null;
            this.pos = 0;
            this.eof = false;
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0052  */
    /* JADX WARN: Code duplicated, block: B:32:0x0057  */
    /* JADX WARN: Code duplicated, block: B:39:0x007a  */
    /* JADX WARN: Code duplicated, block: B:40:0x0080  */
    /* JADX WARN: Code duplicated, block: B:41:0x0083  */
    /* JADX WARN: Code duplicated, block: B:43:0x0095  */
    /* JADX WARN: Code duplicated, block: B:44:0x00aa  */
    public final void fillBuf(int i) throws IOException {
        int i2;
        byte[] bArr;
        int i3;
        int length;
        if (this.eof || i <= this.pos) {
            return;
        }
        if (i % AutoBottomSheetKt.ALIGN_SCREEN_START != 0) {
            i = ((i / AutoBottomSheetKt.ALIGN_SCREEN_START) * AutoBottomSheetKt.ALIGN_SCREEN_START) + AutoBottomSheetKt.ALIGN_SCREEN_START;
        }
        if (!this.fileAssigned && i > this.buf.length) {
            this.fileAssigned = true;
            try {
                try {
                    File fileAssignCache = this.cache.assignCache();
                    this.bufFile = fileAssignCache;
                    if (fileAssignCache != null) {
                        RandomAccessFile randomAccessFile = new RandomAccessFile(this.bufFile, "rw");
                        this.file = randomAccessFile;
                        randomAccessFile.write(this.buf, 0, this.pos);
                    }
                } catch (InterruptedIOException e) {
                    throw e;
                } catch (Exception unused) {
                    RandomAccessFile randomAccessFile2 = this.file;
                    if (randomAccessFile2 != null) {
                        randomAccessFile2.close();
                    }
                    this.file = null;
                    if (this.file == null) {
                        InputStream inputStream = this.in;
                        byte[] bArr2 = this.buf;
                        i2 = inputStream.read(bArr2, 0, Math.min(bArr2.length, i - this.pos));
                        if (i2 >= 0) {
                            this.eof = true;
                            return;
                        }
                        this.file.seek(this.pos);
                        this.file.write(this.buf, 0, i2);
                        this.pos += i2;
                        return;
                    }
                    bArr = this.buf;
                    if (i > bArr.length) {
                        length = bArr.length;
                        do {
                            length *= 2;
                        } while (length < i);
                        byte[] bArr3 = new byte[length];
                        byte[] bArr4 = this.buf;
                        System.arraycopy(bArr4, 0, bArr3, 0, bArr4.length);
                        this.buf = bArr3;
                    }
                    InputStream inputStream2 = this.in;
                    byte[] bArr5 = this.buf;
                    int i4 = this.pos;
                    i3 = inputStream2.read(bArr5, i4, Math.min(bArr5.length - i4, i - i4));
                    if (i3 >= 0) {
                        this.pos += i3;
                    } else {
                        this.eof = true;
                    }
                }
            } catch (InterruptedIOException e2) {
                throw e2;
            } catch (Exception unused2) {
                this.file = null;
            }
        }
        if (this.file == null) {
            InputStream inputStream3 = this.in;
            byte[] bArr6 = this.buf;
            i2 = inputStream3.read(bArr6, 0, Math.min(bArr6.length, i - this.pos));
            if (i2 >= 0) {
                this.eof = true;
                return;
            }
            this.file.seek(this.pos);
            this.file.write(this.buf, 0, i2);
            this.pos += i2;
            return;
        }
        bArr = this.buf;
        if (i > bArr.length) {
            length = bArr.length;
            do {
                length *= 2;
            } while (length < i);
            byte[] bArr7 = new byte[length];
            byte[] bArr8 = this.buf;
            System.arraycopy(bArr8, 0, bArr7, 0, bArr8.length);
            this.buf = bArr7;
        }
        InputStream inputStream4 = this.in;
        byte[] bArr9 = this.buf;
        int i5 = this.pos;
        i3 = inputStream4.read(bArr9, i5, Math.min(bArr9.length - i5, i - i5));
        if (i3 >= 0) {
            this.pos += i3;
        } else {
            this.eof = true;
        }
    }

    public int read(int i) {
        byte[] bArr = new byte[1];
        if (read(i, bArr) > 0) {
            return bArr[0];
        }
        return -1;
    }

    public int read(int i, byte[] bArr) {
        return read(i, bArr, 0, bArr.length);
    }

    public synchronized int read(int i, byte[] bArr, int i2, int i3) {
        try {
            if (this.in == null) {
                throw new IOException("RandomInputBuffer has been closed.");
            }
            if (bArr == null) {
                throw new NullPointerException("buffer == null");
            }
            if ((i2 | i3) < 0 || i2 > bArr.length - i3) {
                throw new IndexOutOfBoundsException();
            }
            if (i3 == 0) {
                return 0;
            }
            int i4 = i + i3;
            if (i4 > this.pos) {
                fillBuf(i4);
            }
            return readFromBuf(i, bArr, i2, i3);
        } catch (Throwable th) {
            throw th;
        }
    }

    public final int readFromBuf(int i, byte[] bArr, int i2, int i3) throws IOException {
        if (i3 == 0) {
            return 0;
        }
        if (this.eof && i >= this.pos) {
            return -1;
        }
        int i4 = this.pos;
        if (i > i4) {
            throw new IndexOutOfBoundsException("from > pos");
        }
        if (i == i4) {
            return 0;
        }
        int iMin = Math.min(i4 - i, i3);
        RandomAccessFile randomAccessFile = this.file;
        if (randomAccessFile == null) {
            System.arraycopy(this.buf, i, bArr, i2, iMin);
            return iMin;
        }
        randomAccessFile.seek(i);
        return this.file.read(bArr, i2, iMin);
    }
}
