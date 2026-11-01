package okio;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ForwardingSink implements Sink {
    public final Sink delegate;

    @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.delegate.close();
    }

    @Override // okio.Sink, java.io.Flushable
    public void flush() {
        this.delegate.flush();
    }

    @Override // okio.Sink
    public Timeout timeout() {
        return this.delegate.timeout();
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.delegate.toString() + ")";
    }

    @Override // okio.Sink
    public void write(Buffer buffer, long j) {
        this.delegate.write(buffer, j);
    }
}
