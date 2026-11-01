package miuix.core.util;

import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.ref.SoftReference;

/* JADX INFO: loaded from: classes2.dex */
public class IOUtils {
    public static final String LINE_SEPARATOR;
    public static final Pools.Pool STRING_WRITER_POOL;
    public static final ThreadLocal THREAD_LOCAL_BYTE_BUFFER = new ThreadLocal();
    public static final ThreadLocal THREAD_LOCAL_CHAR_BUFFER = new ThreadLocal();
    public static final Pools.Pool BYTE_ARRAY_OUTPUT_STREAM_POOL = Pools.createSoftReferencePool(new Pools.Manager<ByteArrayOutputStream>() { // from class: miuix.core.util.IOUtils.1
        @Override // miuix.core.util.Pools.Manager
        public ByteArrayOutputStream createInstance() {
            return new ByteArrayOutputStream();
        }

        @Override // miuix.core.util.Pools.Manager
        public void onRelease(ByteArrayOutputStream byteArrayOutputStream) {
            byteArrayOutputStream.reset();
        }
    }, 2);
    public static final Pools.Pool CHAR_ARRAY_WRITER_POOL = Pools.createSoftReferencePool(new Pools.Manager<CharArrayWriter>() { // from class: miuix.core.util.IOUtils.2
        @Override // miuix.core.util.Pools.Manager
        public CharArrayWriter createInstance() {
            return new CharArrayWriter();
        }

        @Override // miuix.core.util.Pools.Manager
        public void onRelease(CharArrayWriter charArrayWriter) {
            charArrayWriter.reset();
        }
    }, 2);

    static {
        Pools.SoftReferencePool softReferencePoolCreateSoftReferencePool = Pools.createSoftReferencePool(new Pools.Manager<StringWriter>() { // from class: miuix.core.util.IOUtils.3
            @Override // miuix.core.util.Pools.Manager
            public StringWriter createInstance() {
                return new StringWriter();
            }

            @Override // miuix.core.util.Pools.Manager
            public void onRelease(StringWriter stringWriter) {
                stringWriter.getBuffer().setLength(0);
            }
        }, 2);
        STRING_WRITER_POOL = softReferencePoolCreateSoftReferencePool;
        StringWriter stringWriter = (StringWriter) softReferencePoolCreateSoftReferencePool.acquire();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        printWriter.println();
        printWriter.flush();
        LINE_SEPARATOR = stringWriter.toString();
        printWriter.close();
        softReferencePoolCreateSoftReferencePool.release(stringWriter);
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void closeQuietly(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void closeQuietly(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.flush();
            } catch (IOException unused) {
            }
            try {
                outputStream.close();
            } catch (IOException unused2) {
            }
        }
    }

    public static void closeQuietly(Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException unused) {
            }
        }
    }

    public static long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] byteArrayBuffer = getByteArrayBuffer();
        long j = 0;
        while (true) {
            int i = inputStream.read(byteArrayBuffer);
            if (i == -1) {
                outputStream.flush();
                return j;
            }
            outputStream.write(byteArrayBuffer, 0, i);
            j += (long) i;
        }
    }

    public static byte[] getByteArrayBuffer() {
        ThreadLocal threadLocal = THREAD_LOCAL_BYTE_BUFFER;
        SoftReference softReference = (SoftReference) threadLocal.get();
        byte[] bArr = softReference != null ? (byte[]) softReference.get() : null;
        if (bArr != null) {
            return bArr;
        }
        byte[] bArr2 = new byte[AutoBottomSheetKt.ALIGN_SCREEN_TOP];
        threadLocal.set(new SoftReference(bArr2));
        return bArr2;
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        Pools.Pool pool = BYTE_ARRAY_OUTPUT_STREAM_POOL;
        ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) pool.acquire();
        copy(inputStream, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        pool.release(byteArrayOutputStream);
        return byteArray;
    }
}
