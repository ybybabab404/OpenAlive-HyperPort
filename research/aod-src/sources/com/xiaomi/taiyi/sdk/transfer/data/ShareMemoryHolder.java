package com.xiaomi.taiyi.sdk.transfer.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.SharedMemory;
import android.system.ErrnoException;
import android.system.OsConstants;
import com.xiaomi.taiyi.sdk.base.data.ErrorCode;
import com.xiaomi.taiyi.sdk.base.utils.SdkLog;
import com.xiaomi.taiyi.sdk.common.DataFormat;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ReadOnlyBufferException;
import java.nio.ShortBuffer;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ShareMemoryHolder implements Parcelable {
    public static final CREATOR CREATOR = new CREATOR(null);
    public static final int STATE_CLOSED = 2;
    public static final int STATE_OCCUPY = 1;
    public static final int STATE_VACANT = 0;
    private static final String TAG = "ShareMemoryHolder";
    private String id;
    private ByteBuffer innerData;
    private SharedMemory shareMem;
    private int size;
    private int state;

    @Metadata
    public static final class CREATOR implements Parcelable.Creator<ShareMemoryHolder> {
        private CREATOR() {
        }

        public /* synthetic */ CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ShareMemoryHolder createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new ShareMemoryHolder(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ShareMemoryHolder[] newArray(int i) {
            return new ShareMemoryHolder[i];
        }
    }

    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DataFormat.values().length];
            try {
                iArr[DataFormat.ONE_BYTE_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DataFormat.ONE_SHORT_ARRAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DataFormat.ONE_INT_ARRAY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[DataFormat.ONE_LONG_ARRAY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[DataFormat.ONE_FLOAT_ARRAY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[DataFormat.ONE_DOUBLE_ARRAY.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[DataFormat.TWO_BYTE_ARRAY.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[DataFormat.TWO_SHORT_ARRAY.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[DataFormat.TWO_INT_ARRAY.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[DataFormat.TWO_LONG_ARRAY.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[DataFormat.TWO_FLOAT_ARRAY.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[DataFormat.TWO_DOUBLE_ARRAY.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private ShareMemoryHolder() {
        String string = UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        this.id = string;
        this.state = 2;
    }

    public ShareMemoryHolder(int i) throws ErrnoException {
        String string = UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        this.id = string;
        this.state = 2;
        this.size = i;
        SharedMemory sharedMemoryCreate = SharedMemory.create(string, i);
        this.shareMem = sharedMemoryCreate;
        if (sharedMemoryCreate != null) {
            this.state = 0;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ShareMemoryHolder(Parcel parcel) {
        this();
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        readFromParcel(parcel);
    }

    private final <T> Pair<T, Integer> handleRead(Function1<? super ByteBuffer, ? extends T> function1) {
        Pair<ByteBuffer, Integer> map = map();
        ByteBuffer byteBuffer = (ByteBuffer) map.component1();
        int iIntValue = ((Number) map.component2()).intValue();
        if (byteBuffer == null) {
            return new Pair<>(null, Integer.valueOf(iIntValue));
        }
        try {
            byteBuffer.rewind();
            return new Pair<>(function1.invoke(byteBuffer), 0);
        } catch (IndexOutOfBoundsException e) {
            SdkLog.e(TAG, "handleRead IndexOutOfBoundsException: " + e.getMessage());
            return new Pair<>(null, Integer.valueOf(ErrorCode.ERROR_FRAME_READ_BUFFER_INDEX_OUT));
        } catch (BufferUnderflowException e2) {
            SdkLog.e(TAG, "handleRead BufferUnderflowException: " + e2.getMessage());
            return new Pair<>(null, Integer.valueOf(ErrorCode.ERROR_FRAME_READ_BUFFER_UNDER_FLOW));
        } catch (Exception e3) {
            SdkLog.e(TAG, "handleRead Exception: " + e3.getMessage());
            return new Pair<>(null, Integer.valueOf(ErrorCode.ERROR_FRAME_READ_BUFFER_FAILED));
        }
    }

    private final int handleWrite(Function1<? super ByteBuffer, Unit> function1) {
        Pair<ByteBuffer, Integer> map = map();
        ByteBuffer byteBuffer = (ByteBuffer) map.component1();
        int iIntValue = ((Number) map.component2()).intValue();
        try {
            if (byteBuffer == null) {
                return iIntValue;
            }
            try {
                try {
                    byteBuffer.rewind();
                    function1.invoke(byteBuffer);
                    unmap();
                    this.state = 1;
                    return 0;
                } catch (BufferOverflowException e) {
                    SdkLog.e(TAG, "handleWrite: BufferOverflowException " + e.getMessage());
                    unmap();
                    return ErrorCode.ERROR_FRAME_WRITE_BUFFER_OVER_FLOW;
                } catch (ReadOnlyBufferException e2) {
                    SdkLog.e(TAG, "handleWrite: ReadOnlyBufferException " + e2.getMessage());
                    unmap();
                    return ErrorCode.ERROR_FRAME_WRITE_READ_ONLY_BUFFER;
                }
            } catch (ClassCastException e3) {
                SdkLog.e(TAG, "handleWrite: ClassCastException " + e3.getMessage());
                unmap();
                return ErrorCode.ERROR_FRAME_CAST_FAILED;
            } catch (Exception e4) {
                SdkLog.e(TAG, "handleWrite: Exception " + e4.getMessage());
                unmap();
                return ErrorCode.ERROR_FRAME_WRITE_BUFFER_FAILED;
            }
        } catch (Throwable th) {
            unmap();
            throw th;
        }
    }

    public static /* synthetic */ Pair read$default(ShareMemoryHolder shareMemoryHolder, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        return shareMemoryHolder.read(i, z);
    }

    public static /* synthetic */ Pair read$default(ShareMemoryHolder shareMemoryHolder, AIFrame aIFrame, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return shareMemoryHolder.read(aIFrame, z);
    }

    private final Pair<byte[][], Integer> readByte2DArray(final int i, final int i2) {
        return handleRead(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShareMemoryHolder.readByte2DArray$lambda$14(i2, i, (ByteBuffer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final byte[][] readByte2DArray$lambda$14(int i, int i2, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        byte[][] bArr = new byte[i][];
        for (int i3 = 0; i3 < i; i3++) {
            bArr[i3] = new byte[i2];
        }
        for (int i4 = 0; i4 < i; i4++) {
            buffer.get(bArr[i4]);
        }
        return bArr;
    }

    private final Pair<byte[], Integer> readByteArray(final int i) {
        return handleRead(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShareMemoryHolder.readByteArray$lambda$2(i, (ByteBuffer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final byte[] readByteArray$lambda$2(int i, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        byte[] bArr = new byte[i];
        buffer.get(bArr);
        return bArr;
    }

    private final Pair<double[][], Integer> readDouble2DArray(final int i, final int i2) {
        return handleRead(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda18
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShareMemoryHolder.readDouble2DArray$lambda$24(i2, i, (ByteBuffer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final double[][] readDouble2DArray$lambda$24(int i, int i2, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        double[][] dArr = new double[i][];
        for (int i3 = 0; i3 < i; i3++) {
            dArr[i3] = new double[i2];
        }
        DoubleBuffer doubleBufferAsDoubleBuffer = buffer.asDoubleBuffer();
        for (int i4 = 0; i4 < i; i4++) {
            doubleBufferAsDoubleBuffer.get(dArr[i4]);
        }
        return dArr;
    }

    private final Pair<double[], Integer> readDoubleArray(final int i) {
        return handleRead(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShareMemoryHolder.readDoubleArray$lambda$12(i, (ByteBuffer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final double[] readDoubleArray$lambda$12(int i, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        double[] dArr = new double[i];
        buffer.asDoubleBuffer().get(dArr);
        return dArr;
    }

    private final Pair<float[][], Integer> readFloat2DArray(final int i, final int i2) {
        return handleRead(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShareMemoryHolder.readFloat2DArray$lambda$22(i2, i, (ByteBuffer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float[][] readFloat2DArray$lambda$22(int i, int i2, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        float[][] fArr = new float[i][];
        for (int i3 = 0; i3 < i; i3++) {
            fArr[i3] = new float[i2];
        }
        FloatBuffer floatBufferAsFloatBuffer = buffer.asFloatBuffer();
        for (int i4 = 0; i4 < i; i4++) {
            floatBufferAsFloatBuffer.get(fArr[i4]);
        }
        return fArr;
    }

    private final Pair<float[], Integer> readFloatArray(final int i) {
        return handleRead(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShareMemoryHolder.readFloatArray$lambda$10(i, (ByteBuffer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float[] readFloatArray$lambda$10(int i, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        float[] fArr = new float[i];
        buffer.asFloatBuffer().get(fArr);
        return fArr;
    }

    private final Pair<int[][], Integer> readInt2DArray(final int i, final int i2) {
        return handleRead(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShareMemoryHolder.readInt2DArray$lambda$18(i2, i, (ByteBuffer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int[][] readInt2DArray$lambda$18(int i, int i2, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        int[][] iArr = new int[i][];
        for (int i3 = 0; i3 < i; i3++) {
            iArr[i3] = new int[i2];
        }
        IntBuffer intBufferAsIntBuffer = buffer.asIntBuffer();
        for (int i4 = 0; i4 < i; i4++) {
            intBufferAsIntBuffer.get(iArr[i4]);
        }
        return iArr;
    }

    private final Pair<int[], Integer> readIntArray(final int i) {
        return handleRead(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShareMemoryHolder.readIntArray$lambda$6(i, (ByteBuffer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int[] readIntArray$lambda$6(int i, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        int[] iArr = new int[i];
        buffer.asIntBuffer().get(iArr);
        return iArr;
    }

    private final Pair<long[][], Integer> readLong2DArray(final int i, final int i2) {
        return handleRead(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShareMemoryHolder.readLong2DArray$lambda$20(i2, i, (ByteBuffer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final long[][] readLong2DArray$lambda$20(int i, int i2, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        long[][] jArr = new long[i][];
        for (int i3 = 0; i3 < i; i3++) {
            jArr[i3] = new long[i2];
        }
        LongBuffer longBufferAsLongBuffer = buffer.asLongBuffer();
        for (int i4 = 0; i4 < i; i4++) {
            longBufferAsLongBuffer.get(jArr[i4]);
        }
        return jArr;
    }

    private final Pair<long[], Integer> readLongArray(final int i) {
        return handleRead(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda23
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShareMemoryHolder.readLongArray$lambda$8(i, (ByteBuffer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final long[] readLongArray$lambda$8(int i, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        long[] jArr = new long[i];
        buffer.asLongBuffer().get(jArr);
        return jArr;
    }

    private final Pair<short[][], Integer> readShort2DArray(final int i, final int i2) {
        return handleRead(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShareMemoryHolder.readShort2DArray$lambda$16(i2, i, (ByteBuffer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final short[][] readShort2DArray$lambda$16(int i, int i2, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        short[][] sArr = new short[i][];
        for (int i3 = 0; i3 < i; i3++) {
            sArr[i3] = new short[i2];
        }
        ShortBuffer shortBufferAsShortBuffer = buffer.asShortBuffer();
        for (int i4 = 0; i4 < i; i4++) {
            shortBufferAsShortBuffer.get(sArr[i4]);
        }
        return sArr;
    }

    private final Pair<short[], Integer> readShortArray(final int i) {
        return handleRead(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ShareMemoryHolder.readShortArray$lambda$4(i, (ByteBuffer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final short[] readShortArray$lambda$4(int i, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        short[] sArr = new short[i];
        buffer.asShortBuffer().get(sArr);
        return sArr;
    }

    private final int writeByte2DArray(final Object obj) {
        return handleWrite(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda22
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return ShareMemoryHolder.writeByte2DArray$lambda$13(obj, (ByteBuffer) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit writeByte2DArray$lambda$13(Object obj, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Array<kotlin.ByteArray>");
        for (byte[] bArr : (byte[][]) obj) {
            buffer.put(bArr);
        }
        return Unit.INSTANCE;
    }

    private final int writeByteArray(final Object obj) {
        return handleWrite(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return ShareMemoryHolder.writeByteArray$lambda$1(obj, (ByteBuffer) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit writeByteArray$lambda$1(Object obj, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.ByteArray");
        buffer.put((byte[]) obj);
        return Unit.INSTANCE;
    }

    private final int writeDouble2DArray(final Object obj) {
        return handleWrite(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return ShareMemoryHolder.writeDouble2DArray$lambda$23(obj, (ByteBuffer) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit writeDouble2DArray$lambda$23(Object obj, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        DoubleBuffer doubleBufferAsDoubleBuffer = buffer.asDoubleBuffer();
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Array<kotlin.DoubleArray>");
        for (double[] dArr : (double[][]) obj) {
            doubleBufferAsDoubleBuffer.put(dArr);
        }
        return Unit.INSTANCE;
    }

    private final int writeDoubleArray(final Object obj) {
        return handleWrite(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return ShareMemoryHolder.writeDoubleArray$lambda$11(obj, (ByteBuffer) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit writeDoubleArray$lambda$11(Object obj, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        DoubleBuffer doubleBufferAsDoubleBuffer = buffer.asDoubleBuffer();
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.DoubleArray");
        doubleBufferAsDoubleBuffer.put((double[]) obj);
        return Unit.INSTANCE;
    }

    private final int writeFloat2DArray(final Object obj) {
        return handleWrite(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return ShareMemoryHolder.writeFloat2DArray$lambda$21(obj, (ByteBuffer) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit writeFloat2DArray$lambda$21(Object obj, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        FloatBuffer floatBufferAsFloatBuffer = buffer.asFloatBuffer();
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Array<kotlin.FloatArray>");
        for (float[] fArr : (float[][]) obj) {
            floatBufferAsFloatBuffer.put(fArr);
        }
        return Unit.INSTANCE;
    }

    private final int writeFloatArray(final Object obj) {
        return handleWrite(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return ShareMemoryHolder.writeFloatArray$lambda$9(obj, (ByteBuffer) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit writeFloatArray$lambda$9(Object obj, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        FloatBuffer floatBufferAsFloatBuffer = buffer.asFloatBuffer();
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.FloatArray");
        floatBufferAsFloatBuffer.put((float[]) obj);
        return Unit.INSTANCE;
    }

    private final int writeInt2DArray(final Object obj) {
        return handleWrite(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return ShareMemoryHolder.writeInt2DArray$lambda$17(obj, (ByteBuffer) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit writeInt2DArray$lambda$17(Object obj, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        IntBuffer intBufferAsIntBuffer = buffer.asIntBuffer();
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Array<kotlin.IntArray>");
        for (int[] iArr : (int[][]) obj) {
            intBufferAsIntBuffer.put(iArr);
        }
        return Unit.INSTANCE;
    }

    private final int writeIntArray(final Object obj) {
        return handleWrite(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda21
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return ShareMemoryHolder.writeIntArray$lambda$5(obj, (ByteBuffer) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit writeIntArray$lambda$5(Object obj, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        IntBuffer intBufferAsIntBuffer = buffer.asIntBuffer();
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.IntArray");
        intBufferAsIntBuffer.put((int[]) obj);
        return Unit.INSTANCE;
    }

    private final int writeLong2DArray(final Object obj) {
        return handleWrite(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return ShareMemoryHolder.writeLong2DArray$lambda$19(obj, (ByteBuffer) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit writeLong2DArray$lambda$19(Object obj, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        LongBuffer longBufferAsLongBuffer = buffer.asLongBuffer();
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Array<kotlin.LongArray>");
        for (long[] jArr : (long[][]) obj) {
            longBufferAsLongBuffer.put(jArr);
        }
        return Unit.INSTANCE;
    }

    private final int writeLongArray(final Object obj) {
        return handleWrite(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return ShareMemoryHolder.writeLongArray$lambda$7(obj, (ByteBuffer) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit writeLongArray$lambda$7(Object obj, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        LongBuffer longBufferAsLongBuffer = buffer.asLongBuffer();
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.LongArray");
        longBufferAsLongBuffer.put((long[]) obj);
        return Unit.INSTANCE;
    }

    private final int writeShort2DArray(final Object obj) {
        return handleWrite(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda20
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return ShareMemoryHolder.writeShort2DArray$lambda$15(obj, (ByteBuffer) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit writeShort2DArray$lambda$15(Object obj, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        ShortBuffer shortBufferAsShortBuffer = buffer.asShortBuffer();
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Array<kotlin.ShortArray>");
        for (short[] sArr : (short[][]) obj) {
            shortBufferAsShortBuffer.put(sArr);
        }
        return Unit.INSTANCE;
    }

    private final int writeShortArray(final Object obj) {
        return handleWrite(new Function1() { // from class: com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryHolder$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return ShareMemoryHolder.writeShortArray$lambda$3(obj, (ByteBuffer) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit writeShortArray$lambda$3(Object obj, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        ShortBuffer shortBufferAsShortBuffer = buffer.asShortBuffer();
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.ShortArray");
        shortBufferAsShortBuffer.put((short[]) obj);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void close() {
        try {
            unmap();
            SharedMemory sharedMemory = this.shareMem;
            if (sharedMemory != null) {
                sharedMemory.close();
            }
        } catch (Exception e) {
            SdkLog.e(TAG, "close " + this.id + " err. " + e.getMessage());
        } finally {
            this.shareMem = null;
            this.innerData = null;
            this.state = 2;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final void free() {
        unmap();
        this.state = 0;
    }

    public final String getId() {
        return this.id;
    }

    public final SharedMemory getShareMem() {
        return this.shareMem;
    }

    public final int getSize() {
        return this.size;
    }

    public final int getState() {
        return this.state;
    }

    public final boolean isClosed() {
        try {
            if (this.state == 2) {
                return true;
            }
            SharedMemory sharedMemory = this.shareMem;
            return (sharedMemory != null ? sharedMemory.getSize() : 0) <= 0;
        } catch (IllegalStateException e) {
            SdkLog.e(TAG, "isClosed " + this.id + " check err. " + e.getMessage());
            close();
            return false;
        }
    }

    public final boolean isOccupied() {
        return this.state == 1;
    }

    public final boolean isVacant() {
        return this.state == 0 && this.innerData == null;
    }

    public final Pair<ByteBuffer, Integer> map() {
        SharedMemory sharedMemory;
        ByteBuffer byteBuffer = this.innerData;
        if (byteBuffer != null) {
            return new Pair<>(byteBuffer, 0);
        }
        if (!isClosed() && (sharedMemory = this.shareMem) != null) {
            try {
                SdkLog.i(TAG, "map " + this.id);
                sharedMemory.setProtect(OsConstants.PROT_READ | OsConstants.PROT_WRITE);
                ByteBuffer byteBufferMapReadWrite = sharedMemory.mapReadWrite();
                this.innerData = byteBufferMapReadWrite;
                return new Pair<>(byteBufferMapReadWrite, 0);
            } catch (Throwable th) {
                SdkLog.e(TAG, "map " + this.id + " err. " + th.getMessage());
                return new Pair<>(null, Integer.valueOf(ErrorCode.ERROR_SHARE_MEMORY_MAP_FAILED));
            }
        }
        return new Pair<>(null, Integer.valueOf(ErrorCode.ERROR_SHARE_MEMORY_CLOSED));
    }

    public final Pair<byte[], Integer> read(int i, boolean z) {
        Pair<ByteBuffer, Integer> map = map();
        ByteBuffer byteBuffer = (ByteBuffer) map.component1();
        int iIntValue = ((Number) map.component2()).intValue();
        if (byteBuffer == null) {
            return new Pair<>(null, Integer.valueOf(iIntValue));
        }
        byte[] bArr = new byte[i];
        try {
            byteBuffer.rewind();
            byteBuffer.get(bArr);
            return new Pair<>(bArr, 0);
        } catch (IndexOutOfBoundsException unused) {
            return new Pair<>(null, Integer.valueOf(ErrorCode.ERROR_FRAME_READ_BUFFER_INDEX_OUT));
        } catch (BufferUnderflowException unused2) {
            return new Pair<>(null, Integer.valueOf(ErrorCode.ERROR_FRAME_READ_BUFFER_UNDER_FLOW));
        } catch (Exception unused3) {
            return new Pair<>(null, Integer.valueOf(ErrorCode.ERROR_FRAME_READ_BUFFER_FAILED));
        } finally {
            if (z) {
                unmap();
                this.state = 0;
            }
        }
    }

    public final Pair<Object, Integer> read(AIFrame frame, boolean z) {
        Pair<Object, Integer> byteArray;
        Intrinsics.checkNotNullParameter(frame, "frame");
        switch (WhenMappings.$EnumSwitchMapping$0[frame.getFormat().ordinal()]) {
            case 1:
                byteArray = readByteArray(frame.getByteLength());
                break;
            case 2:
                byteArray = readShortArray(frame.getX());
                break;
            case 3:
                byteArray = readIntArray(frame.getX());
                break;
            case 4:
                byteArray = readLongArray(frame.getX());
                break;
            case 5:
                byteArray = readFloatArray(frame.getX());
                break;
            case 6:
                byteArray = readDoubleArray(frame.getX());
                break;
            case 7:
                byteArray = readByte2DArray(frame.getX(), frame.getY());
                break;
            case 8:
                byteArray = readShort2DArray(frame.getX(), frame.getY());
                break;
            case 9:
                byteArray = readInt2DArray(frame.getX(), frame.getY());
                break;
            case 10:
                byteArray = readLong2DArray(frame.getX(), frame.getY());
                break;
            case 11:
                byteArray = readFloat2DArray(frame.getX(), frame.getY());
                break;
            case 12:
                byteArray = readDouble2DArray(frame.getX(), frame.getY());
                break;
            default:
                byteArray = readByteArray(frame.getByteLength());
                break;
        }
        if (z) {
            unmap();
            this.state = 0;
        }
        return byteArray;
    }

    public final void readFromParcel(Parcel parcel) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        String string = parcel.readString();
        if (string == null) {
            string = "";
        }
        this.id = string;
        this.size = parcel.readInt();
        this.state = parcel.readInt();
        this.shareMem = (SharedMemory) parcel.readParcelable(ShareMemoryHolder.class.getClassLoader(), SharedMemory.class);
    }

    public String toString() {
        return "SMH(id=" + this.id + ", size=" + this.size + ", state=" + this.state + ")";
    }

    public final void unmap() {
        ByteBuffer byteBuffer = this.innerData;
        if (byteBuffer != null) {
            try {
                SdkLog.i(TAG, "unmap " + this.id);
                SharedMemory.unmap(byteBuffer);
                byteBuffer.clear();
            } catch (Exception e) {
                SdkLog.e(TAG, "unmap " + this.id + " err. " + e.getMessage());
            }
            this.innerData = null;
        }
    }

    public final int write(AIFrame frame) {
        Intrinsics.checkNotNullParameter(frame, "frame");
        DataFormat format = frame.getFormat();
        Object value = frame.getValue();
        if (value == null) {
            return ErrorCode.ERROR_FRAME_DATA_EMPTY;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[format.ordinal()]) {
            case 1:
                return writeByteArray(value);
            case 2:
                return writeShortArray(value);
            case 3:
                return writeIntArray(value);
            case 4:
                return writeLongArray(value);
            case 5:
                return writeFloatArray(value);
            case 6:
                return writeDoubleArray(value);
            case 7:
                return writeByte2DArray(value);
            case 8:
                return writeShort2DArray(value);
            case 9:
                return writeInt2DArray(value);
            case 10:
                return writeLong2DArray(value);
            case 11:
                return writeFloat2DArray(value);
            case 12:
                return writeDouble2DArray(value);
            default:
                return writeByteArray(value);
        }
    }

    public final int write(byte[] data) {
        Intrinsics.checkNotNullParameter(data, "data");
        Pair<ByteBuffer, Integer> map = map();
        ByteBuffer byteBuffer = (ByteBuffer) map.component1();
        int iIntValue = ((Number) map.component2()).intValue();
        if (byteBuffer == null) {
            return iIntValue;
        }
        try {
            byteBuffer.rewind();
            byteBuffer.put(data);
            unmap();
            this.state = 1;
            return 0;
        } catch (BufferOverflowException unused) {
            unmap();
            return ErrorCode.ERROR_FRAME_WRITE_BUFFER_OVER_FLOW;
        } catch (ReadOnlyBufferException unused2) {
            unmap();
            return ErrorCode.ERROR_FRAME_WRITE_READ_ONLY_BUFFER;
        } catch (Exception unused3) {
            unmap();
            return ErrorCode.ERROR_FRAME_WRITE_BUFFER_FAILED;
        } catch (Throwable th) {
            unmap();
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int i) {
        Intrinsics.checkNotNullParameter(dest, "dest");
        dest.writeString(this.id);
        dest.writeInt(this.size);
        dest.writeInt(this.state);
        dest.writeParcelable(this.shareMem, i);
    }
}
