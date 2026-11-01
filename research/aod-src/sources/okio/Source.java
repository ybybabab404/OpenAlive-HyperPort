package okio;

import java.io.Closeable;

/* JADX INFO: loaded from: classes3.dex */
public interface Source extends Closeable {
    @Override // java.io.Closeable, java.lang.AutoCloseable
    void close();

    long read(Buffer buffer, long j);
}
