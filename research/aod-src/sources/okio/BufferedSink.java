package okio;

import java.nio.channels.WritableByteChannel;

/* JADX INFO: loaded from: classes3.dex */
public interface BufferedSink extends Sink, WritableByteChannel {
    Buffer buffer();

    BufferedSink emitCompleteSegments();

    @Override // okio.Sink, java.io.Flushable
    void flush();

    BufferedSink writeByte(int i);

    BufferedSink writeIntLe(int i);

    BufferedSink writeUtf8(String str);

    BufferedSink writeUtf8(String str, int i, int i2);
}
