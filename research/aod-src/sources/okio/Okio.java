package okio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: loaded from: classes3.dex */
public final class Okio {
    public static final Logger logger = Logger.getLogger(Okio.class.getName());

    /* JADX INFO: renamed from: okio.Okio$1, reason: invalid class name */
    final class AnonymousClass1 implements Sink {
        public final /* synthetic */ OutputStream val$out;
        public final /* synthetic */ Timeout val$timeout;

        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.val$out.close();
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            this.val$out.flush();
        }

        @Override // okio.Sink
        public Timeout timeout() {
            return this.val$timeout;
        }

        public String toString() {
            return "sink(" + this.val$out + ")";
        }

        @Override // okio.Sink
        public void write(Buffer buffer, long j) throws IOException {
            Util.checkOffsetAndCount(buffer.size, 0L, j);
            while (j > 0) {
                this.val$timeout.throwIfReached();
                Segment segment = buffer.head;
                int iMin = (int) Math.min(j, segment.limit - segment.pos);
                this.val$out.write(segment.data, segment.pos, iMin);
                int i = segment.pos + iMin;
                segment.pos = i;
                long j2 = iMin;
                j -= j2;
                buffer.size -= j2;
                if (i == segment.limit) {
                    buffer.head = segment.pop();
                    SegmentPool.recycle(segment);
                }
            }
        }
    }

    /* JADX INFO: renamed from: okio.Okio$3, reason: invalid class name */
    final class AnonymousClass3 implements Sink {
        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() {
        }

        @Override // okio.Sink
        public Timeout timeout() {
            return Timeout.NONE;
        }

        @Override // okio.Sink
        public void write(Buffer buffer, long j) {
            buffer.skip(j);
        }
    }

    /* JADX INFO: renamed from: okio.Okio$4, reason: invalid class name */
    final class AnonymousClass4 extends AsyncTimeout {
        public final /* synthetic */ Socket val$socket;

        @Override // okio.AsyncTimeout
        public IOException newTimeoutException(IOException iOException) {
            SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
            if (iOException != null) {
                socketTimeoutException.initCause(iOException);
            }
            return socketTimeoutException;
        }

        @Override // okio.AsyncTimeout
        public void timedOut() {
            try {
                this.val$socket.close();
            } catch (AssertionError e) {
                if (!Okio.isAndroidGetsocknameError(e)) {
                    throw e;
                }
                Okio.logger.log(Level.WARNING, "Failed to close timed out socket " + this.val$socket, (Throwable) e);
            } catch (Exception e2) {
                Okio.logger.log(Level.WARNING, "Failed to close timed out socket " + this.val$socket, (Throwable) e2);
            }
        }
    }

    public static BufferedSource buffer(Source source) {
        return new RealBufferedSource(source);
    }

    public static boolean isAndroidGetsocknameError(AssertionError assertionError) {
        return (assertionError.getCause() == null || assertionError.getMessage() == null || !assertionError.getMessage().contains("getsockname failed")) ? false : true;
    }

    public static Source source(InputStream inputStream) {
        return source(inputStream, new Timeout());
    }

    public static Source source(final InputStream inputStream, final Timeout timeout) {
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        }
        if (timeout != null) {
            return new Source() { // from class: okio.Okio.2
                @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    inputStream.close();
                }

                @Override // okio.Source
                public long read(Buffer buffer, long j) throws IOException {
                    if (j < 0) {
                        throw new IllegalArgumentException("byteCount < 0: " + j);
                    }
                    if (j == 0) {
                        return 0L;
                    }
                    try {
                        timeout.throwIfReached();
                        Segment segmentWritableSegment = buffer.writableSegment(1);
                        int i = inputStream.read(segmentWritableSegment.data, segmentWritableSegment.limit, (int) Math.min(j, 8192 - segmentWritableSegment.limit));
                        if (i == -1) {
                            return -1L;
                        }
                        segmentWritableSegment.limit += i;
                        long j2 = i;
                        buffer.size += j2;
                        return j2;
                    } catch (AssertionError e) {
                        if (Okio.isAndroidGetsocknameError(e)) {
                            throw new IOException(e);
                        }
                        throw e;
                    }
                }

                public String toString() {
                    return "source(" + inputStream + ")";
                }
            };
        }
        throw new IllegalArgumentException("timeout == null");
    }
}
