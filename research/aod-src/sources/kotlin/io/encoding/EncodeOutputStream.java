package kotlin.io.encoding;

import java.io.IOException;
import java.io.OutputStream;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: Base64IOStream.kt */
/* JADX INFO: loaded from: classes2.dex */
@ExperimentalEncodingApi
@Metadata
final class EncodeOutputStream extends OutputStream {
    public final Base64 base64;
    public final byte[] byteBuffer;
    public int byteBufferLength;
    public boolean isClosed;
    public int lineLength;
    public final OutputStream output;
    public final byte[] symbolBuffer;

    public final void checkOpen() throws IOException {
        if (this.isClosed) {
            throw new IOException("The output stream is closed.");
        }
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.isClosed) {
            return;
        }
        this.isClosed = true;
        if (this.byteBufferLength != 0) {
            encodeByteBufferIntoOutput();
        }
        this.output.close();
    }

    public final int copyIntoByteBuffer(byte[] bArr, int i, int i2) {
        int iMin = Math.min(3 - this.byteBufferLength, i2 - i);
        ArraysKt.copyInto(bArr, this.byteBuffer, this.byteBufferLength, i, i + iMin);
        int i3 = this.byteBufferLength + iMin;
        this.byteBufferLength = i3;
        if (i3 == 3) {
            encodeByteBufferIntoOutput();
        }
        return iMin;
    }

    public final void encodeByteBufferIntoOutput() {
        if (encodeIntoOutput(this.byteBuffer, 0, this.byteBufferLength) != 4) {
            throw new IllegalStateException("Check failed.");
        }
        this.byteBufferLength = 0;
    }

    public final int encodeIntoOutput(byte[] bArr, int i, int i2) throws IOException {
        int iEncodeIntoByteArray = this.base64.encodeIntoByteArray(bArr, this.symbolBuffer, 0, i, i2);
        if (this.lineLength == 0) {
            this.output.write(Base64.Default.getMimeLineSeparatorSymbols$kotlin_stdlib());
            this.lineLength = 76;
            if (iEncodeIntoByteArray > 76) {
                throw new IllegalStateException("Check failed.");
            }
        }
        this.output.write(this.symbolBuffer, 0, iEncodeIntoByteArray);
        this.lineLength -= iEncodeIntoByteArray;
        return iEncodeIntoByteArray;
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        checkOpen();
        this.output.flush();
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        checkOpen();
        byte[] bArr = this.byteBuffer;
        int i2 = this.byteBufferLength;
        int i3 = i2 + 1;
        this.byteBufferLength = i3;
        bArr[i2] = (byte) i;
        if (i3 == 3) {
            encodeByteBufferIntoOutput();
        }
    }

    @Override // java.io.OutputStream
    public void write(byte[] source, int i, int i2) throws IOException {
        int i3;
        Intrinsics.checkNotNullParameter(source, "source");
        checkOpen();
        if (i < 0 || i2 < 0 || (i3 = i + i2) > source.length) {
            throw new IndexOutOfBoundsException("offset: " + i + ", length: " + i2 + ", source size: " + source.length);
        }
        if (i2 == 0) {
            return;
        }
        int i4 = this.byteBufferLength;
        if (i4 >= 3) {
            throw new IllegalStateException("Check failed.");
        }
        if (i4 != 0) {
            i += copyIntoByteBuffer(source, i, i3);
            if (this.byteBufferLength != 0) {
                return;
            }
        }
        while (i + 3 <= i3) {
            int iMin = Math.min((this.base64.isMimeScheme$kotlin_stdlib() ? this.lineLength : this.symbolBuffer.length) / 4, (i3 - i) / 3);
            int i5 = (iMin * 3) + i;
            if (encodeIntoOutput(source, i, i5) != iMin * 4) {
                throw new IllegalStateException("Check failed.");
            }
            i = i5;
        }
        ArraysKt.copyInto(source, this.byteBuffer, 0, i, i3);
        this.byteBufferLength = i3 - i;
    }
}
