package okio;

import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;

/* JADX INFO: loaded from: classes3.dex */
public interface BufferedSource extends Source, ReadableByteChannel {
    Buffer buffer();

    boolean exhausted();

    long indexOf(byte b);

    long indexOf(ByteString byteString);

    long indexOfElement(ByteString byteString);

    InputStream inputStream();

    BufferedSource peek();

    byte readByte();

    int readIntLe();

    short readShort();

    short readShortLe();

    boolean request(long j);

    void require(long j);

    int select(Options options);

    void skip(long j);
}
