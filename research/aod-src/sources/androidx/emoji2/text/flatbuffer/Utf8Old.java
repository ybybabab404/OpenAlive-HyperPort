package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes.dex */
public class Utf8Old extends Utf8 {
    public static final ThreadLocal CACHE = ThreadLocal.withInitial(new Supplier() { // from class: androidx.emoji2.text.flatbuffer.Utf8Old$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return Utf8Old.$r8$lambda$H6DtQOdY71VKmQKkbdDLin5kXeM();
        }
    });

    public static class Cache {
        public final CharsetDecoder decoder;
        public final CharsetEncoder encoder;
        public CharSequence lastInput = null;
        public ByteBuffer lastOutput = null;

        public Cache() {
            Charset charset = StandardCharsets.UTF_8;
            this.encoder = charset.newEncoder();
            this.decoder = charset.newDecoder();
        }
    }

    public static /* synthetic */ Cache $r8$lambda$H6DtQOdY71VKmQKkbdDLin5kXeM() {
        return new Cache();
    }
}
