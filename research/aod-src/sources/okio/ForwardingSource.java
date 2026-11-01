package okio;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ForwardingSource implements Source {
    public final Source delegate;

    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.delegate.close();
    }

    @Override // okio.Source
    public long read(Buffer buffer, long j) {
        return this.delegate.read(buffer, j);
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.delegate.toString() + ")";
    }
}
