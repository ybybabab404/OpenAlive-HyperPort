package miuix.core.util;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import miuix.animation.internal.TransitionInfo;

/* JADX INFO: loaded from: classes2.dex */
public class DirectIndexedFile {

    /* JADX INFO: renamed from: miuix.core.util.DirectIndexedFile$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$miuix$core$util$DirectIndexedFile$DataItemDescriptor$Type;

        static {
            int[] iArr = new int[DataItemDescriptor.Type.values().length];
            $SwitchMap$miuix$core$util$DirectIndexedFile$DataItemDescriptor$Type = iArr;
            try {
                iArr[DataItemDescriptor.Type.STRING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$miuix$core$util$DirectIndexedFile$DataItemDescriptor$Type[DataItemDescriptor.Type.BYTE_ARRAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$miuix$core$util$DirectIndexedFile$DataItemDescriptor$Type[DataItemDescriptor.Type.SHORT_ARRAY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$miuix$core$util$DirectIndexedFile$DataItemDescriptor$Type[DataItemDescriptor.Type.INTEGER_ARRAY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$miuix$core$util$DirectIndexedFile$DataItemDescriptor$Type[DataItemDescriptor.Type.LONG_ARRAY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$miuix$core$util$DirectIndexedFile$DataItemDescriptor$Type[DataItemDescriptor.Type.BYTE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$miuix$core$util$DirectIndexedFile$DataItemDescriptor$Type[DataItemDescriptor.Type.SHORT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$miuix$core$util$DirectIndexedFile$DataItemDescriptor$Type[DataItemDescriptor.Type.INTEGER.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$miuix$core$util$DirectIndexedFile$DataItemDescriptor$Type[DataItemDescriptor.Type.LONG.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    public static class Builder {

        public class DataItemHolder {
        }

        public static class IndexData {
        }

        public class Item implements Comparable<Item> {
            public int mIndex;

            @Override // java.lang.Comparable
            public int compareTo(Item item) {
                return this.mIndex - item.mIndex;
            }

            public boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                return (obj instanceof Item) && this.mIndex == ((Item) obj).mIndex;
            }

            public int hashCode() {
                return this.mIndex;
            }
        }
    }

    public static class DataInputRandom implements InputFile {
        public RandomAccessFile mFile;

        @Override // java.io.DataInput
        public boolean readBoolean() {
            return this.mFile.readBoolean();
        }

        @Override // java.io.DataInput
        public byte readByte() {
            return this.mFile.readByte();
        }

        @Override // java.io.DataInput
        public char readChar() {
            return this.mFile.readChar();
        }

        @Override // java.io.DataInput
        public double readDouble() {
            return this.mFile.readDouble();
        }

        @Override // java.io.DataInput
        public float readFloat() {
            return this.mFile.readFloat();
        }

        @Override // java.io.DataInput
        public void readFully(byte[] bArr) throws IOException {
            this.mFile.readFully(bArr);
        }

        @Override // java.io.DataInput
        public void readFully(byte[] bArr, int i, int i2) throws IOException {
            this.mFile.readFully(bArr, i, i2);
        }

        @Override // java.io.DataInput
        public int readInt() {
            return this.mFile.readInt();
        }

        @Override // java.io.DataInput
        public String readLine() {
            return this.mFile.readLine();
        }

        @Override // java.io.DataInput
        public long readLong() {
            return this.mFile.readLong();
        }

        @Override // java.io.DataInput
        public short readShort() {
            return this.mFile.readShort();
        }

        @Override // java.io.DataInput
        public String readUTF() {
            return this.mFile.readUTF();
        }

        @Override // java.io.DataInput
        public int readUnsignedByte() {
            return this.mFile.readUnsignedByte();
        }

        @Override // java.io.DataInput
        public int readUnsignedShort() {
            return this.mFile.readUnsignedShort();
        }

        @Override // java.io.DataInput
        public int skipBytes(int i) {
            return this.mFile.skipBytes(i);
        }
    }

    public static class DataInputStream implements InputFile {
        public InputStream mInputFile;
        public long mInputPos;

        @Override // java.io.DataInput
        public boolean readBoolean() {
            this.mInputPos++;
            return this.mInputFile.read() != 0;
        }

        @Override // java.io.DataInput
        public byte readByte() {
            this.mInputPos++;
            return (byte) this.mInputFile.read();
        }

        @Override // java.io.DataInput
        public char readChar() {
            byte[] bArr = new byte[2];
            this.mInputPos += 2;
            if (this.mInputFile.read(bArr) == 2) {
                return (char) (((char) (bArr[1] & TransitionInfo.INIT)) | ((bArr[0] << 8) & 65280));
            }
            return (char) 0;
        }

        @Override // java.io.DataInput
        public double readDouble() throws IOException {
            throw new IOException();
        }

        @Override // java.io.DataInput
        public float readFloat() throws IOException {
            throw new IOException();
        }

        @Override // java.io.DataInput
        public void readFully(byte[] bArr) throws IOException {
            this.mInputPos += (long) this.mInputFile.read(bArr);
        }

        @Override // java.io.DataInput
        public void readFully(byte[] bArr, int i, int i2) throws IOException {
            this.mInputPos += (long) this.mInputFile.read(bArr, i, i2);
        }

        @Override // java.io.DataInput
        public int readInt() {
            byte[] bArr = new byte[4];
            this.mInputPos += 4;
            if (this.mInputFile.read(bArr) == 4) {
                return (bArr[3] & TransitionInfo.INIT) | ((bArr[2] << 8) & 65280) | ((bArr[1] << 16) & 16711680) | ((bArr[0] << 24) & (-16777216));
            }
            return 0;
        }

        @Override // java.io.DataInput
        public String readLine() throws IOException {
            throw new IOException();
        }

        @Override // java.io.DataInput
        public long readLong() {
            byte[] bArr = new byte[8];
            this.mInputPos += 8;
            if (this.mInputFile.read(bArr) != 8) {
                return 0L;
            }
            return ((((long) bArr[0]) << 56) & (-72057594037927936L)) | ((long) (bArr[7] & TransitionInfo.INIT)) | (((long) (bArr[6] << 8)) & 65280) | (((long) (bArr[5] << 16)) & 16711680) | (((long) (bArr[4] << 24)) & 4278190080L) | ((((long) bArr[3]) << 32) & 1095216660480L) | ((((long) bArr[2]) << 40) & 280375465082880L) | ((((long) bArr[1]) << 48) & 71776119061217280L);
        }

        @Override // java.io.DataInput
        public short readShort() {
            byte[] bArr = new byte[2];
            this.mInputPos += 2;
            if (this.mInputFile.read(bArr) == 2) {
                return (short) (((short) (bArr[1] & TransitionInfo.INIT)) | ((bArr[0] << 8) & 65280));
            }
            return (short) 0;
        }

        @Override // java.io.DataInput
        public String readUTF() throws IOException {
            throw new IOException();
        }

        @Override // java.io.DataInput
        public int readUnsignedByte() {
            this.mInputPos++;
            return (byte) this.mInputFile.read();
        }

        @Override // java.io.DataInput
        public int readUnsignedShort() {
            byte[] bArr = new byte[2];
            this.mInputPos += 2;
            if (this.mInputFile.read(bArr) == 2) {
                return (short) (((short) (bArr[1] & TransitionInfo.INIT)) | ((bArr[0] << 8) & 65280));
            }
            return 0;
        }

        @Override // java.io.DataInput
        public int skipBytes(int i) {
            int iSkip = (int) this.mInputFile.skip(i);
            this.mInputPos += (long) iSkip;
            return iSkip;
        }
    }

    public static class DataItemDescriptor {
        public static byte[] sBuffer = new byte[1024];

        public enum Type {
            BYTE,
            SHORT,
            INTEGER,
            LONG,
            STRING,
            BYTE_ARRAY,
            SHORT_ARRAY,
            INTEGER_ARRAY,
            LONG_ARRAY
        }
    }

    public static class DescriptionPair {
    }

    public static class FileHeader {
        public static final byte[] FILE_TAG = {73, 68, 70, 32};
    }

    public static class IndexGroupDescriptor {
    }

    public interface InputFile extends DataInput {
    }

    public static class Reader {

        public static class IndexData {
        }
    }
}
