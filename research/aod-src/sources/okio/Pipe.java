package okio;

import java.io.IOException;

/* JADX INFO: loaded from: classes3.dex */
public final class Pipe {
    public final Buffer buffer;
    public Sink foldedSink;
    public final long maxBufferSize;
    public boolean sinkClosed;
    public boolean sourceClosed;

    public final class PipeSink implements Sink {
        public final /* synthetic */ Pipe this$0;
        public final PushableTimeout timeout;

        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            Sink sink;
            synchronized (this.this$0.buffer) {
                try {
                    Pipe pipe = this.this$0;
                    if (pipe.sinkClosed) {
                        return;
                    }
                    if (pipe.foldedSink != null) {
                        sink = this.this$0.foldedSink;
                    } else {
                        Pipe pipe2 = this.this$0;
                        if (pipe2.sourceClosed && pipe2.buffer.size() > 0) {
                            throw new IOException("source is closed");
                        }
                        Pipe pipe3 = this.this$0;
                        pipe3.sinkClosed = true;
                        pipe3.buffer.notifyAll();
                        sink = null;
                    }
                    if (sink != null) {
                        this.timeout.push(sink.timeout());
                        try {
                            sink.close();
                        } finally {
                            this.timeout.pop();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() {
            Sink sink;
            synchronized (this.this$0.buffer) {
                try {
                    Pipe pipe = this.this$0;
                    if (pipe.sinkClosed) {
                        throw new IllegalStateException("closed");
                    }
                    if (pipe.foldedSink != null) {
                        sink = this.this$0.foldedSink;
                    } else {
                        Pipe pipe2 = this.this$0;
                        if (pipe2.sourceClosed && pipe2.buffer.size() > 0) {
                            throw new IOException("source is closed");
                        }
                        sink = null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (sink != null) {
                this.timeout.push(sink.timeout());
                try {
                    sink.flush();
                } finally {
                    this.timeout.pop();
                }
            }
        }

        @Override // okio.Sink
        public Timeout timeout() {
            return this.timeout;
        }

        @Override // okio.Sink
        public void write(Buffer buffer, long j) {
            Sink sink;
            synchronized (this.this$0.buffer) {
                try {
                    if (!this.this$0.sinkClosed) {
                        while (true) {
                            if (j <= 0) {
                                sink = null;
                                break;
                            }
                            if (this.this$0.foldedSink != null) {
                                sink = this.this$0.foldedSink;
                                break;
                            }
                            Pipe pipe = this.this$0;
                            if (pipe.sourceClosed) {
                                throw new IOException("source is closed");
                            }
                            long size = pipe.maxBufferSize - pipe.buffer.size();
                            if (size == 0) {
                                this.timeout.waitUntilNotified(this.this$0.buffer);
                            } else {
                                long jMin = Math.min(size, j);
                                this.this$0.buffer.write(buffer, jMin);
                                j -= jMin;
                                this.this$0.buffer.notifyAll();
                            }
                        }
                    } else {
                        throw new IllegalStateException("closed");
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (sink != null) {
                this.timeout.push(sink.timeout());
                try {
                    sink.write(buffer, j);
                } finally {
                    this.timeout.pop();
                }
            }
        }
    }

    public final class PipeSource implements Source {
        public final /* synthetic */ Pipe this$0;
        public final Timeout timeout;

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            synchronized (this.this$0.buffer) {
                Pipe pipe = this.this$0;
                pipe.sourceClosed = true;
                pipe.buffer.notifyAll();
            }
        }

        @Override // okio.Source
        public long read(Buffer buffer, long j) {
            synchronized (this.this$0.buffer) {
                try {
                    if (this.this$0.sourceClosed) {
                        throw new IllegalStateException("closed");
                    }
                    while (this.this$0.buffer.size() == 0) {
                        Pipe pipe = this.this$0;
                        if (pipe.sinkClosed) {
                            return -1L;
                        }
                        this.timeout.waitUntilNotified(pipe.buffer);
                    }
                    long j2 = this.this$0.buffer.read(buffer, j);
                    this.this$0.buffer.notifyAll();
                    return j2;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
