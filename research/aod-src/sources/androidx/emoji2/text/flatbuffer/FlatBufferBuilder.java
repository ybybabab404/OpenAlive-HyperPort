package androidx.emoji2.text.flatbuffer;

import java.io.InputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import miuix.animation.internal.TransitionInfo;

/* JADX INFO: loaded from: classes.dex */
public class FlatBufferBuilder {

    public static class ByteBufferBackedInputStream extends InputStream {
        public ByteBuffer buf;

        @Override // java.io.InputStream
        public int read() {
            try {
                return this.buf.get() & TransitionInfo.INIT;
            } catch (BufferUnderflowException unused) {
                return -1;
            }
        }
    }

    public static abstract class ByteBufferFactory {
    }

    public static final class HeapByteBufferFactory extends ByteBufferFactory {
        public static final HeapByteBufferFactory INSTANCE = new HeapByteBufferFactory();
    }
}
