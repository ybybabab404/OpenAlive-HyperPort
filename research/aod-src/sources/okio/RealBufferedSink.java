package okio;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes3.dex */
final class RealBufferedSink implements BufferedSink {
    public final Buffer buffer;
    public boolean closed;
    public final Sink sink;

    /* JADX INFO: renamed from: okio.RealBufferedSink$1, reason: invalid class name */
    class AnonymousClass1 extends OutputStream {
        public final /* synthetic */ RealBufferedSink this$0;

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws Throwable {
            this.this$0.close();
        }

        @Override // java.io.OutputStream, java.io.Flushable
        public void flush() {
            RealBufferedSink realBufferedSink = this.this$0;
            if (realBufferedSink.closed) {
                return;
            }
            realBufferedSink.flush();
        }

        public String toString() {
            return this.this$0 + ".outputStream()";
        }

        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
            RealBufferedSink realBufferedSink = this.this$0;
            if (realBufferedSink.closed) {
                throw new IOException("closed");
            }
            realBufferedSink.buffer.writeByte((int) ((byte) i));
            this.this$0.emitCompleteSegments();
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            RealBufferedSink realBufferedSink = this.this$0;
            if (realBufferedSink.closed) {
                throw new IOException("closed");
            }
            realBufferedSink.buffer.write(bArr, i, i2);
            this.this$0.emitCompleteSegments();
        }
    }

    @Override // okio.BufferedSink
    public Buffer buffer() {
        return this.buffer;
    }

    @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws Throwable {
        if (this.closed) {
            return;
        }
        Buffer buffer = this.buffer;
        long j = buffer.size;
        if (j > 0) {
            this.sink.write(buffer, j);
        }
        th = null;
        try {
            this.sink.close();
        } catch (Throwable th) {
            if (th == null) {
                th = th;
            }
        }
        this.closed = true;
        if (th != null) {
            Util.sneakyRethrow(th);
        }
    }

    @Override // okio.BufferedSink
    public BufferedSink emitCompleteSegments() {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        long jCompleteSegmentByteCount = this.buffer.completeSegmentByteCount();
        if (jCompleteSegmentByteCount > 0) {
            this.sink.write(this.buffer, jCompleteSegmentByteCount);
        }
        return this;
    }

    @Override // okio.BufferedSink, okio.Sink, java.io.Flushable
    public void flush() {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        Buffer buffer = this.buffer;
        long j = buffer.size;
        if (j > 0) {
            this.sink.write(buffer, j);
        }
        this.sink.flush();
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        return !this.closed;
    }

    @Override // okio.Sink
    public Timeout timeout() {
        return this.sink.timeout();
    }

    public String toString() {
        return "buffer(" + this.sink + ")";
    }

    @Override // java.nio.channels.WritableByteChannel
    public int write(ByteBuffer byteBuffer) {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        int iWrite = this.buffer.write(byteBuffer);
        emitCompleteSegments();
        return iWrite;
    }

    @Override // okio.Sink
    public void write(Buffer buffer, long j) {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.write(buffer, j);
        emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeByte(int i) {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeByte(i);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeIntLe(int i) {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeIntLe(i);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeUtf8(String str) {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeUtf8(str);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeUtf8(String str, int i, int i2) {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeUtf8(str, i, i2);
        return emitCompleteSegments();
    }
}
