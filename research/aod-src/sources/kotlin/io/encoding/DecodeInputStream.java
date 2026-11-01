package kotlin.io.encoding;

import java.io.IOException;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.internal.TransitionInfo;

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
final class DecodeInputStream extends InputStream {
    public final Base64 base64;
    public final byte[] byteBuffer;
    public int byteBufferEndIndex;
    public int byteBufferStartIndex;
    public final InputStream input;
    public boolean isClosed;
    public boolean isEOF;
    public final byte[] singleByteBuffer;
    public final byte[] symbolBuffer;

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.isClosed) {
            return;
        }
        this.isClosed = true;
        this.input.close();
    }

    public final void copyByteBufferInto(byte[] bArr, int i, int i2) {
        byte[] bArr2 = this.byteBuffer;
        int i3 = this.byteBufferStartIndex;
        ArraysKt.copyInto(bArr2, bArr, i, i3, i3 + i2);
        this.byteBufferStartIndex += i2;
        resetByteBufferIfEmpty();
    }

    public final int decodeSymbolBufferInto(byte[] bArr, int i, int i2, int i3) {
        int i4 = this.byteBufferEndIndex;
        this.byteBufferEndIndex = i4 + this.base64.decodeIntoByteArray(this.symbolBuffer, this.byteBuffer, i4, 0, i3);
        int iMin = Math.min(getByteBufferLength(), i2 - i);
        copyByteBufferInto(bArr, i, iMin);
        shiftByteBufferToStartIfNeeded();
        return iMin;
    }

    public final int getByteBufferLength() {
        return this.byteBufferEndIndex - this.byteBufferStartIndex;
    }

    public final int handlePaddingSymbol(int i) throws IOException {
        this.symbolBuffer[i] = 61;
        if ((i & 3) != 2) {
            return i + 1;
        }
        int nextSymbol = readNextSymbol();
        if (nextSymbol >= 0) {
            this.symbolBuffer[i + 1] = (byte) nextSymbol;
        }
        return i + 2;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        int i = this.byteBufferStartIndex;
        if (i < this.byteBufferEndIndex) {
            int i2 = this.byteBuffer[i] & TransitionInfo.INIT;
            this.byteBufferStartIndex = i + 1;
            resetByteBufferIfEmpty();
            return i2;
        }
        int i3 = read(this.singleByteBuffer, 0, 1);
        if (i3 == -1) {
            return -1;
        }
        if (i3 == 1) {
            return this.singleByteBuffer[0] & TransitionInfo.INIT;
        }
        throw new IllegalStateException("Unreachable");
    }

    @Override // java.io.InputStream
    public int read(byte[] destination, int i, int i2) throws IOException {
        int i3;
        boolean z;
        boolean z2;
        Intrinsics.checkNotNullParameter(destination, "destination");
        if (i < 0 || i2 < 0 || (i3 = i + i2) > destination.length) {
            throw new IndexOutOfBoundsException("offset: " + i + ", length: " + i2 + ", buffer size: " + destination.length);
        }
        if (this.isClosed) {
            throw new IOException("The input stream is closed.");
        }
        if (this.isEOF) {
            return -1;
        }
        if (i2 == 0) {
            return 0;
        }
        if (getByteBufferLength() >= i2) {
            copyByteBufferInto(destination, i, i2);
            return i2;
        }
        int byteBufferLength = (((i2 - getByteBufferLength()) + 2) / 3) * 4;
        int iDecodeSymbolBufferInto = i;
        while (true) {
            z = this.isEOF;
            if (z || byteBufferLength <= 0) {
                break;
            }
            int iMin = Math.min(this.symbolBuffer.length, byteBufferLength);
            int iHandlePaddingSymbol = 0;
            while (true) {
                z2 = this.isEOF;
                if (z2 || iHandlePaddingSymbol >= iMin) {
                    break;
                }
                int nextSymbol = readNextSymbol();
                if (nextSymbol == -1) {
                    this.isEOF = true;
                } else if (nextSymbol != 61) {
                    this.symbolBuffer[iHandlePaddingSymbol] = (byte) nextSymbol;
                    iHandlePaddingSymbol++;
                } else {
                    iHandlePaddingSymbol = handlePaddingSymbol(iHandlePaddingSymbol);
                    this.isEOF = true;
                }
            }
            if (!z2 && iHandlePaddingSymbol != iMin) {
                throw new IllegalStateException("Check failed.");
            }
            byteBufferLength -= iHandlePaddingSymbol;
            iDecodeSymbolBufferInto += decodeSymbolBufferInto(destination, iDecodeSymbolBufferInto, i3, iHandlePaddingSymbol);
        }
        if (iDecodeSymbolBufferInto == i && z) {
            return -1;
        }
        return iDecodeSymbolBufferInto - i;
    }

    public final int readNextSymbol() throws IOException {
        int i;
        if (!this.base64.isMimeScheme$kotlin_stdlib()) {
            return this.input.read();
        }
        do {
            i = this.input.read();
            if (i == -1) {
                break;
            }
        } while (!Base64Kt.isInMimeAlphabet(i));
        return i;
    }

    public final void resetByteBufferIfEmpty() {
        if (this.byteBufferStartIndex == this.byteBufferEndIndex) {
            this.byteBufferStartIndex = 0;
            this.byteBufferEndIndex = 0;
        }
    }

    public final void shiftByteBufferToStartIfNeeded() {
        byte[] bArr = this.byteBuffer;
        int length = bArr.length;
        int i = this.byteBufferEndIndex;
        if ((this.symbolBuffer.length / 4) * 3 > length - i) {
            ArraysKt.copyInto(bArr, bArr, 0, this.byteBufferStartIndex, i);
            this.byteBufferEndIndex -= this.byteBufferStartIndex;
            this.byteBufferStartIndex = 0;
        }
    }
}
